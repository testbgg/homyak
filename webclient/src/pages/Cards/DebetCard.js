import React, { Component } from 'react';
import { Link } from 'react-router-dom';
import './Cards.sass';
export default class DebetCard extends Component {
  render() {
    return (
      <Link to="./DebetCard">
        <div>Debet Card</div>
      </Link>
    );
  }
}
