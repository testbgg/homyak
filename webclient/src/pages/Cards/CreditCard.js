import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import './Cards.sass';
export default class CreditCard extends Component {
  render() {
    return (
      <Link to={`${this.props.pathname}/credit`}>
        <div>Credit Card</div>
      </Link>
    );
  }
}
