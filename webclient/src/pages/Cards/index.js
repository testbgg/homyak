import React, { Component } from 'react';
import classNames from 'classnames';
import axios from 'axios';
import { Link } from 'react-router-dom';
import { Breadcrumb } from 'antd';
import Card from './Card';

import './Cards.sass';

class Cards extends Component {
  state = {
    type: 'debit',
    cards: [],
    employees: []
  };

  componentDidMount() {
    this.fetchCards.call(this);
  }

  async fetchCards() {
    const {
      match: {
        params: { invoiceId }
      }
    } = this.props;
    const { data: cards } = await axios.get(`/api/invoices/${invoiceId}/cards`);
    const { data: employees } = await axios.get('/api/employees');
    this.setState({ cards, employees });
  }

  onChange = key => {
    this.setState({ type: key });
  };

  render() {
    const {
      location: { pathname },
      match: {
        params: { invoiceId }
      }
    } = this.props;
    const { type, cards, employees } = this.state;
    const toggleClasses = toggleType =>
      classNames({
        'cards__toggle-button': true,
        'cards__toggle-button--active': toggleType === type
      });
    return (
      <div className="container">
        <Breadcrumb>
          <Breadcrumb.Item>
            <Link to="../invoices">
              Расчетные счета
            </Link>
          </Breadcrumb.Item>
          <Breadcrumb.Item>Корпоративные карты</Breadcrumb.Item>
        </Breadcrumb>
        <div>
          <header>
            <h1>Корпоративные карты</h1>
          </header>
          <div className="cards__toggler">
            <div
              className={toggleClasses('debit')}
              onClick={() => this.onChange('debit')}
            >
              Дебетовые
            </div>
            <div
              className={toggleClasses('cashinout')}
              onClick={() => this.onChange('cashinout')}
            >
              Cash IN/OUT
            </div>
            <div
              className={toggleClasses('credit')}
              onClick={() => this.onChange('credit')}
            >
              Кредитные
            </div>
          </div>
          <main className="cards__cards">
            {type === 'credit' && (
              <Card
                pathname={pathname}
                cards={cards.filter(card => card.type === 'Credit')}
                invoiceId={invoiceId}
                fetchCards={this.fetchCards.bind(this)}
                employees={employees}
                type="Credit"
              />
            )}
            {type === 'debit' && (
              <Card
                pathname={pathname}
                cards={cards.filter(card => card.type === 'Debit')}
                invoiceId={invoiceId}
                fetchCards={this.fetchCards.bind(this)}
                employees={employees}
                type="Debit"
              />
            )}
            {type === 'cashinout' && (
              <Card
                pathname={pathname}
                cards={cards.filter(card => card.type === 'Cash in/out')}
                invoiceId={invoiceId}
                fetchCards={this.fetchCards.bind(this)}
                employees={employees}
                type="Cash in/out"
              />
            )}
          </main>
        </div>
      </div>
    );
  }
}

export default Cards;
