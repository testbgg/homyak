import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import _isEmpty from "lodash/isEmpty";
import { Button, Icon, Modal, Table, Select } from "antd";
import "./Invoices.sass";

const Option = Select.Option;

const columns = [
  {
    title: "Name",
    dataIndex: "name",
    render: text => <a href="javascript:;">{text}</a>
  },
  {
    title: "Age",
    dataIndex: "age"
  },
  {
    title: "Address",
    dataIndex: "address"
  }
];
const data = [
  {
    key: "1",
    name: "John Brown",
    age: 32,
    address: "New York No. 1 Lake Park"
  },
  {
    key: "2",
    name: "Jim Green",
    age: 42,
    address: "London No. 1 Lake Park"
  },
  {
    key: "3",
    name: "Joe Black",
    age: 32,
    address: "Sidney No. 1 Lake Park"
  },
  {
    key: "4",
    name: "Disabled User",
    age: 99,
    address: "Sidney No. 1 Lake Park"
  }
];

// rowSelection object indicates the need for row selection
const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(
      `selectedRowKeys: ${selectedRowKeys}`,
      "selectedRows: ",
      selectedRows
    );
  },
  getCheckboxProps: record => ({
    disabled: record.name === "Disabled User", // Column configuration not to be checked
    name: record.name
  })
};

export default class Invoices extends Component {
  state = {
    invoices: [],
    visibleInvoices: false,
    visibleCreateInvoice: false,
    confirmLoading: false,
    newInvoiceTypeCurrency: ""
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

  handleCancel = state => {
    console.log("Clicked cancel button");
    this.setState({
      [state]: false
    });
  };

  render() {
    const {
      invoices,
      visibleInvoices,
      visibleCreateInvoice,
      confirmLoading
    } = this.state;
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
              onOk={() => this.handleOk("visibleInvoices")}
              confirmLoading={confirmLoading}
              onCancel={() => this.handleCancel("visibleInvoices")}
            >
              <Table
                rowSelection={rowSelection}
                columns={columns}
                dataSource={invoices.filter(invoice => !invoice.card)}
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
          {!_isEmpty(invoices) && (
            <div className="row invoices__list">
              {invoices.map(({ id, number, cash, currencyType, cards }) => (
                <div className="col-xs-12 col-sm-4" key={id}>
                  <Link
                    to={{ pathname: `/invoices/${number}`, state: { cards } }}
                  >
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
