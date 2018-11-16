import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import './Cards.sass';
export default class CashInOutCard extends Component {
  render() {
    return (
      <Link to={`${this.props.pathname}/cashinout`}>
        <div>CashInOutCard</div>
      </Link>
    );
  }
}
