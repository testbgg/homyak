import React, { Component } from "react";
import { Link } from "react-router-dom";
import { Button, Icon, Modal } from "antd";
import "./Invoices.sass";

export default class Invoices extends Component {
  state = {
    invoices: [
      { id: "123213213", number: "1232132213" },
      { id: "123213212", number: "1232132213" }
    ],
    visible: false,
    confirmLoading: false
  };

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
              <p>ТУТ СПИСОК</p>
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
