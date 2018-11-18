import React, { Component } from 'react';
import { Table } from 'antd';
import { Breadcrumb } from 'antd';
import { Link } from 'react-router-dom';
import axios from 'axios';
import columns from './TableColumns';

export default class Operations extends Component {
  state = {
    operations: []
  };

  async componentDidMount() {
    const {
      match: {
        params: { cardId }
      }
    } = this.props;
    await axios.post(`/api/operations/call`, {
      cardIds: [cardId],
      amount: 100,
      description: 'Оплата'
    });
    const { data: operations } = await axios.get(
      `/api/cards/${cardId}/operations`
    );
    this.setState({ operations });
  }
  render() {
    const { operations } = this.state;

    return (
      <div className="container">
        <Breadcrumb>
          <Breadcrumb.Item>
            <Link to="../../invoices">Расчетные счета</Link>
          </Breadcrumb.Item>
          <Breadcrumb.Item>
            <Link to={`${this.props.location.invoiceId}`}>
              Корпоративные карты
            </Link>
          </Breadcrumb.Item>
          <Breadcrumb.Item>Выписка по карте</Breadcrumb.Item>
        </Breadcrumb>
        <header>
          <h1>Выписка по карте</h1>
        </header>
        <Table
          dataSource={operations.map(card => ({ ...card, key: card.id }))}
          columns={columns}
        />
      </div>
    );
  }
}
