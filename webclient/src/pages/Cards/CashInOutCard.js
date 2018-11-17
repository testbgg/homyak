import React, { Component } from "react";
import { Table, Button, Icon, Modal, InputNumber } from "antd";
import axios from "axios";

const columns = [
  {
    title: "Номер карты",
    dataIndex: "number",
    render: text => <a href="javascript:;">{text}</a>
  },
  {
    title: "Дневной лимит",
    dataIndex: "dayLimit"
  },
  {
    title: "Месячный лимит",
    dataIndex: "monthLimit"
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

export default class CashinOutCard extends Component {
  state = {
    visible: false,
    confirmLoading: false,
    newCardForm: {
      dayLimit: 1000,
      monthLimit: 10000
    }
  };

  handleOk = () => {
    const { invoiceId, fetchCards } = this.props;
    const {
      newCardForm: { dayLimit, monthLimit }
    } = this.state;
    this.setState({
      confirmLoading: true
    });
    axios
      .post("/api/cards", {
        type: "Cash in/out",
        invoiceId: Number(invoiceId),
        employeeId: null,
        dayLimit: dayLimit,
        monthLimit: monthLimit
      })
      .then(() => {
        fetchCards();
        this.setState({
          visible: false,
          confirmLoading: false
        });
      });
  };

  handleCancel = () => {
    this.setState({
      visible: false
    });
  };

  showModal = () => {
    this.setState({
      visible: true
    });
  };

  render() {
    const { cards } = this.props;
    const { visible, confirmLoading, newCardForm } = this.state;
    return (
      <>
        <Button className="cards__button" onClick={this.showModal}>
          Выпуск новой карты
          <Icon type="plus-circle" />
        </Button>
        <Table
          rowSelection={rowSelection}
          columns={columns}
          dataSource={cards}
        />
        <Modal
          title="Выпуск новой карты"
          visible={visible}
          onOk={this.handleOk}
          confirmLoading={confirmLoading}
          onCancel={this.handleCancel}
        >
          <div className="cards__form">
            <div className="cards__form-element">
              <p>Дневной лимит:</p>
              <InputNumber
                defaultValue={newCardForm.dayLimit}
                min={0}
                formatter={value => `${value}`}
                onChange={value => this.onChange("dayLimit", value)}
              />
            </div>
            <div className="cards__form-element">
              <p>Месячный лимит:</p>
              <InputNumber
                defaultValue={newCardForm.monthLimit}
                min={0}
                formatter={value => `${value}`}
                onChange={value => this.onChange("monthLimit", value)}
              />
            </div>
          </div>
        </Modal>
      </>
    );
  }
}
