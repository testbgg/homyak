import React, { Component } from 'react';
import { withRouter } from 'react-router-dom';
import CreditCard from './CreditCard';
import DebetCard from './DebetCard';
import CashInOutCard from './CashInOutCard';
import './Cards.sass';
class Cards extends Component {
  render() {
    const {
      location: { pathname }
    } = this.props;
    return (
      <div>
        <div>
          <header className="Cards__header">
            <div>left</div>
            <div>right</div>
          </header>
          <main className="Cards__cards">
            <CreditCard pathname={pathname} />
            <DebetCard pathname={pathname} />
            <CashInOutCard pathname={pathname} />
          </main>
        </div>
      </div>
    );
  }
}

export default withRouter(Cards);
