import React, { Component } from "react";
import { Table, Button, Icon, Modal } from "antd";
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
    newCardForm: {}
  };

  handleOk = () => {
    const { invoiceId } = this.props;
    this.setState({
      confirmLoading: true
    });
    axios.post("/api/cards", {
      type: "Cash in/out",
      invoiceId: Number(invoiceId),
      employeeId: null
    });
    setTimeout(() => {
      this.setState({
        visible: false,
        confirmLoading: false
      });
    }, 2000);
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
    const { visible, confirmLoading } = this.state;
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
          Вот тут
        </Modal>
      </>
    );
  }
}
