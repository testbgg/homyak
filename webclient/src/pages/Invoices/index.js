import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import _isEmpty from "lodash/isEmpty";
import { Button, Icon, Modal, Table, Select } from "antd";
import "./Invoices.sass";

const Option = Select.Option;

const columns = [
  {
    title: "Номер Л/C",
    dataIndex: "number"
  }
];
// rowSelection object indicates the need for row selection

export default class Invoices extends Component {
  state = {
    invoices: [],
    visibleInvoices: false,
    visibleCreateInvoice: false,
    confirmLoading: false,
    newInvoiceTypeCurrency: "",
    selectedInvoicesWithoutCard: []
  };

  async componentDidMount() {
    const { data: invoices } = await axios.get("/api/invoices");
    this.setState({ invoices });
  }

  showModal = state => {
    this.setState({
      [state]: true
    });
  };

  handleChange = value => {
    this.setState({ newInvoiceTypeCurrency: value });
  };

  handleOk = state => {
    this.setState({
      confirmLoading: true
    });
    setTimeout(() => {
      this.setState({
        [state]: false,
        confirmLoading: false
      });
    }, 2000);
  };

  handleCarded = state => {
    this.setState({
      confirmLoading: true
    });
    axios.post("/api/invoices/mark-as-carded", {
      ids: this.state.selectedInvoicesWithoutCard
    });
  };

  handleCancel = state => {
    console.log("Clicked cancel button");
    this.setState({
      [state]: false
    });
  };

  selectRow = rows => {
    this.setState({ selectedInvoicesWithoutCard: rows.map(row => row.id) });
  };

  render() {
    const {
      invoices,
      visibleInvoices,
      visibleCreateInvoice,
      confirmLoading
    } = this.state;
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => this.selectRow(selectedRows),
      getCheckboxProps: record => ({
        disabled: record.name === "Disabled User", // Column configuration not to be checked
        name: record.name
      })
    };
    const invoicesCarded = invoices.filter(invoice => invoice.card);
    const invoicesWithoutCarded = invoices.filter(invoice => !invoice.card);
    return (
      <div>
        <header>
          <h1>Корпоративные карты</h1>
        </header>
        <section>
          <div className="invoices__to-card-invoice">
            <Button onClick={() => this.showModal("visibleInvoices")}>
              Привязать Л/C <Icon type="diff" />
            </Button>
            <Button onClick={() => this.showModal("visibleCreateInvoice")}>
              Создать Л/C <Icon type="plus-circle" />
            </Button>
            <Modal
              title="Выберите счет"
              visible={visibleInvoices}
              onOk={() => this.handleCarded("visibleInvoices")}
              confirmLoading={confirmLoading}
              onCancel={() => this.handleCancel("visibleInvoices")}
            >
              <Table
                rowSelection={rowSelection}
                columns={columns}
                dataSource={invoicesWithoutCarded}
              />
            </Modal>
            <Modal
              title="Создать счет"
              visible={visibleCreateInvoice}
              onOk={() => this.handleOk("visibleCreateInvoice")}
              confirmLoading={confirmLoading}
              onCancel={() => this.handleCancel("visibleCreateInvoice")}
            >
              <Select
                placeholder="Выберите тип валюты"
                style={{ width: 220 }}
                onChange={this.handleChange}
              >
                <Option value="local">LOCAL</Option>
                <Option value="foreign">FOREIGN</Option>
              </Select>
            </Modal>
          </div>
          {!_isEmpty(invoicesCarded) && (
            <div className="row invoices__list">
              {invoicesCarded.map(({ id, number, cash, currencyType, cards }) => (
                <div className="col-xs-12 col-sm-4" key={id}>
                  <Link to={{ pathname: `/invoices/${id}`, state: { cards } }}>
                    <div className="invoices__invoice" key={id}>
                      <div className="invoices__invoice-number">
                        Л/C № {number}
                      </div>
                      <div className="invoices__invoice-cash">
                        На счету: {cash}
                      </div>
                      <div className="invoices__invoice-cash">
                        Тип валюты: {currencyType}
                      </div>
                      <div className="invoices__invoice-cash">
                        Карт привязано: {cards.length}
                      </div>
                    </div>
                  </Link>
                </div>
              ))}
            </div>
          )}
        </section>
      </div>
    );
  }
}
