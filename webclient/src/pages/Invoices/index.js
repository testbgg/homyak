import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import _isEmpty from 'lodash/isEmpty';
import { Button, Icon, Modal, Table, Select, Popconfirm, message } from 'antd';
import './Invoices.sass';

const Option = Select.Option;

const columns = [
  {
    title: 'Номер расчетного счета',
    dataIndex: 'number'
  }
];
// rowSelection object indicates the need for row selection

export default class Invoices extends Component {
  state = {
    invoices: [],
    visibleInvoices: false,
    visibleCreateInvoice: false,
    confirmLoading: false,
    newInvoiceTypeCurrency: '',
    selectedInvoicesWithoutCard: [],
    visiblePopup: false
  };

  async componentDidMount() {
    this.fetchInvoices.call(this);
  }

  async fetchInvoices() {
    const { data: invoices } = await axios.get('/api/invoices');
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

  handleCreateInvoice = modalVisible => {
    const { newInvoiceTypeCurrency } = this.state;
    this.setState({
      confirmLoading: true
    });
    axios
      .post('/api/invoices', {
        currencyType: newInvoiceTypeCurrency
      })
      .then(() => {
        this.setState(
          {
            [modalVisible]: false,
            confirmLoading: false
          },
          () => {
            this.fetchInvoices.call(this);
            message.success('Счет создан');
          }
        );
      });
  };

  handlePopup = () => {
    this.setState({ visiblePopup: true });
  };
  handleCarded = modalVisible => {
    const { selectedInvoicesWithoutCard } = this.state;
    this.setState({
      confirmLoading: true
    });
    axios
      .post('/api/invoices/mark-as-carded', {
        ids: selectedInvoicesWithoutCard
      })
      .then(() => {
        this.setState(
          {
            [modalVisible]: false,
            confirmLoading: false,
            visiblePopup: false
          },
          () => {
            this.fetchInvoices.call(this);
            message.success('Расчетный счет успешно переведен в карточные');
          }
        );
      });
  };

  handleCancel = state => {
    this.setState({
      [state]: false
    });
  };

  handleClosePopup = () => {
    this.setState({
      visiblePopup: false
    });
  };

  selectRow = rows => {
    this.setState({ selectedInvoicesWithoutCard: rows });
  };

  render() {
    const {
      invoices,
      visibleInvoices,
      visibleCreateInvoice,
      confirmLoading,
      selectedInvoicesWithoutCard
    } = this.state;
    const rowSelection = {
      onChange: selectedRowKeys => this.selectRow(selectedRowKeys),
      getCheckboxProps: record => ({
        disabled: record.name === 'Disabled User', // Column configuration not to be checked
        name: record.name
      })
    };
    const invoicesCarded = invoices.filter(invoice => invoice.card);
    const invoicesWithoutCarded = invoices.filter(invoice => !invoice.card);
    return (
      <div className="container">
        <div>
          <h1>Карточные Р/C</h1>
        </div>
        <section>
          <div className="invoices__to-card-invoice">
            <Button onClick={() => this.showModal('visibleInvoices')}>
              Привязать расчетный счет <Icon type="diff" />
            </Button>
            <Button onClick={() => this.showModal('visibleCreateInvoice')}>
              Создать расчетный счет <Icon type="plus-circle" />
            </Button>
            {visibleInvoices && (
              <Modal
                title="Выберите счет"
                visible={visibleInvoices}
                onOk={this.handlePopup}
                confirmLoading={confirmLoading}
                onCancel={() => this.handleCancel('visibleInvoices')}
                okButtonProps={{
                  disabled: _isEmpty(selectedInvoicesWithoutCard)
                }}
              >
                <Table
                  rowSelection={rowSelection}
                  columns={columns}
                  dataSource={invoicesWithoutCarded.map(invoice => ({
                    ...invoice,
                    key: invoice.id
                  }))}
                />
              </Modal>
            )}
            {visibleCreateInvoice && (
              <Modal
                title="Создать счет"
                visible={visibleCreateInvoice}
                onOk={() => this.handleCreateInvoice('visibleCreateInvoice')}
                confirmLoading={confirmLoading}
                onCancel={() => this.handleCancel('visibleCreateInvoice')}
                okButtonProps={{
                  disabled: _isEmpty(this.state.newInvoiceTypeCurrency)
                }}
              >
                <Select
                  placeholder="Выберите тип валюты"
                  style={{ width: 220 }}
                  onChange={this.handleChange}
                >
                  <Option value="LOCAL">LOCAL</Option>
                  <Option value="FOREIGN">FOREIGN</Option>
                </Select>
              </Modal>
            )}
            <Popconfirm
              title="Вы согласны с правилами перевода Р/C в карточные?"
              visible={this.state.visiblePopup}
              onConfirm={() => this.handleCarded('visibleInvoices')}
              onCancel={this.handleClosePopup}
              okText="Да"
              cancelText="Нет"
            />
            <br />
          </div>
          {!_isEmpty(invoicesCarded) && (
            <div className="row invoices__list">
              {invoicesCarded.map(
                ({ id, number, cash, currencyType, cards }) => (
                  <div className="col-xs-12 col-sm-4" key={id}>
                    <Link
                      to={{ pathname: `/invoices/${id}`, state: { cards } }}
                    >
                      <div className="invoices__invoice" key={id}>
                        <div className="invoices__invoice-number">
                          Р/C № {number}
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
                )
              )}
            </div>
          )}
        </section>
      </div>
    );
  }
}
