import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import './Cards.sass';
export default class CreditCard extends Component {
  render() {
    return (
      <Link to="./CreditCard">
        <div>Credit Card</div>
      </Link>
    );
  }
}
