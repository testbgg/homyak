import React, { Component } from "react";
import { Table, Button, Icon, Modal, InputNumber, Select } from "antd";
import _isEmpty from "lodash/isEmpty";
import axios from "axios";
import columns from './TableColumns';

export default class CashInOut extends Component {
  state = {
    visible: false,
    visibleLimits: false,
    confirmLoading: false,
    newCardForm: {
      dayLimit: 1000,
      monthLimit: 10000
    },
    updateLimits: {
      dayLimit: 1000,
      monthLimit: 10000
    },
    selectedRowKeys: []
  };

  handleOk = () => {
    const { invoiceId, fetchCards, type } = this.props;
    const {
      newCardForm: { dayLimit, monthLimit, employeeId }
    } = this.state;
    this.setState({
      confirmLoading: true
    });
    axios
      .post("/api/cards", {
        type: type,
        invoiceId: Number(invoiceId),
        employeeId: employeeId,
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

  setLimits = () => {
    const { fetchCards } = this.props;
    const {
      updateLimits: { dayLimit, monthLimit },
      selectedRowKeys
    } = this.state;
    this.setState({
      confirmLoading: true
    });
    axios
      .put("/api/cards/limits", {
        ids: selectedRowKeys,
        dayLimit: dayLimit,
        monthLimit: monthLimit
      })
      .then(() => {
        fetchCards();
        this.setState({
          visibleLimits: false,
          confirmLoading: false
        });
      });
  };

  handleCancel = modalVisible => {
    this.setState({
      [modalVisible]: false
    });
  };

  showModal = modalVisible => {
    this.setState({
      [modalVisible]: true
    });
  };

  onChange = (formElement, value, stateObject) => {
    this.setState(prevState => ({
      [stateObject]: { ...prevState[stateObject], [formElement]: value }
    }));
  };

  onSelect = rowKeys => {
    this.setState({ selectedRowKeys: rowKeys });
  };
  render() {
    const { cards, employees } = this.props;
    const {
      visible,
      confirmLoading,
      newCardForm,
      selectedRowKeys,
      visibleLimits,
      updateLimits
    } = this.state;
    const rowSelection = {
      onChange: selectedRowKeys => this.onSelect(selectedRowKeys),
      getCheckboxProps: record => ({
        disabled: record.name === "Disabled User", // Column configuration not to be checked
        name: record.name
      })
    };
    const rowSelected = _isEmpty(selectedRowKeys);
    return (
      <>
        <div className="cards__buttons">
          <Button
            className="cards__button"
            onClick={() => this.showModal("visible")}
          >
            Выпуск новой карты
            <Icon type="plus-circle" />
          </Button>
          <Button
            className="cards__button"
            onClick={() => this.showModal("visibleLimits")}
            disabled={rowSelected}
          >
            Установить лимиты на карту(-ы)
            <Icon type="plus-circle" />
          </Button>
        </div>
        <Table
          rowSelection={rowSelection}
          columns={columns}
          dataSource={cards.map(card => ({ ...card, key: card.id }))}
        />
        <Modal
          title="Выпуск новой карты"
          visible={visible}
          onOk={this.handleOk}
          confirmLoading={confirmLoading}
          onCancel={() => this.handleCancel("visible")}
        >
          <div className="cards__form">
            <div className="cards__form-element">
              <p>Выберите сотрудника</p>
              <Select
                showSearch
                style={{ width: 200 }}
                placeholder="Список сотрудников"
                optionFilterProp="children"
                onChange={value =>
                  this.onChange("employeeId", value, "newCardForm")
                }
                filterOption={(input, option) =>
                  option.props.children
                    .toLowerCase()
                    .indexOf(input.toLowerCase()) >= 0
                }
              >
                {employees.map(({ id, firstName, secondName }) => (
                  <Select.Option value={id} key={id}>
                    {`${firstName} ${secondName}`}
                  </Select.Option>
                ))}
              </Select>
            </div>
            <div className="cards__form-element">
              <p>Дневной лимит:</p>
              <InputNumber
                defaultValue={newCardForm.dayLimit}
                min={0}
                formatter={value => `${value}`}
                onChange={value =>
                  this.onChange("dayLimit", value, "newCardForm")
                }
              />
            </div>
            <div className="cards__form-element">
              <p>Месячный лимит:</p>
              <InputNumber
                defaultValue={newCardForm.monthLimit}
                min={0}
                formatter={value => `${value}`}
                onChange={value =>
                  this.onChange("monthLimit", value, "newCardForm")
                }
              />
            </div>
          </div>
        </Modal>
        <Modal
          title="Установите лимиты на карту(-ы)"
          visible={visibleLimits}
          onOk={this.setLimits}
          confirmLoading={confirmLoading}
          onCancel={() => this.handleCancel("visibleLimits")}
        >
          <div className="cards__form">
            <div className="cards__form-element">
              <p>Дневной лимит:</p>
              <InputNumber
                defaultValue={updateLimits.dayLimit}
                min={0}
                formatter={value => `${value}`}
                onChange={value =>
                  this.onChange("dayLimit", value, "updateLimits")
                }
              />
            </div>
            <div className="cards__form-element">
              <p>Месячный лимит:</p>
              <InputNumber
                defaultValue={updateLimits.monthLimit}
                min={0}
                formatter={value => `${value}`}
                onChange={value =>
                  this.onChange("monthLimit", value, "updateLimits")
                }
              />
            </div>
          </div>
        </Modal>
      </>
    );
  }
}
