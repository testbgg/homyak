import React, { Component } from 'react';
import CreditCard from './CreditCard';
import DebetCard from './DebetCard';
import CashInOutCard from './CashInOutCard';
import './Cards.sass';
export default class Cards extends Component {
  render() {
    return (
      <div>
        <div>
          <header className="Cards__header">
            <div>left</div>
            <div>right</div>
          </header>
          <main className="Cards__cards">
            <CreditCard />
            <DebetCard />
            <CashInOutCard />
          </main>
        </div>
      </div>
    );
  }
}
