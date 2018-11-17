import React, { Component } from "react";
import { Link } from "react-router-dom";
import axios from 'axios';
import { Button, Icon, Modal, Table } from "antd";
import "./Invoices.sass";

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
    invoices: [
      { id: "123213213", number: "1232132213" },
      { id: "123213212", number: "1232132213" }
    ],
    visible: false,
    confirmLoading: false
  };

  async componentDidMount() {
    await axios.post("/api/fill-db");
    const { data }  = axios.get("/api/invoices");
    console.log(data);
  }

  showModal = () => {
    this.setState({
      visible: true
    });
  };

  handleOk = () => {
    this.setState({
      confirmLoading: true
    });
    setTimeout(() => {
      this.setState({
        visible: false,
        confirmLoading: false
      });
    }, 2000);
  };

  handleCancel = () => {
    console.log("Clicked cancel button");
    this.setState({
      visible: false
    });
  };

  render() {
    const { invoices, visible, confirmLoading } = this.state;
    return (
      <div>
        <header>
          <h1>Корпоративные карты</h1>
        </header>
        <section>
          <div className="invoices__to-card-invoice">
            <Button onClick={this.showModal}>
              Привязать Л/C <Icon type="plus-circle" />
            </Button>
            <Modal
              title="Выберите счет"
              visible={visible}
              onOk={this.handleOk}
              confirmLoading={confirmLoading}
              onCancel={this.handleCancel}
            >
              <Table
                rowSelection={rowSelection}
                columns={columns}
                dataSource={data}
              />
            </Modal>
          </div>
          <div className="invoices__list">
            {invoices.map(({ id, number }) => (
              <Link to={`/invoices/${id}`} key={id}>
                <div className="invoices__invoice" key={id}>
                  Л/C № {number}
                </div>
              </Link>
            ))}
          </div>
        </section>
      </div>
    );
  }
}
