import React, { Component } from "react";
import "./Invoices.sass";

export default class Invoices extends Component {
  render() {
    return (
      <div>
        <header>
          <h1>Корпоративные карты</h1>
        </header>
        <section>
          <div className="invoices__to-card-invoice">
            <button>Привязать Л/C</button>
          </div>
          <div className="invoices__list"></div>
        </section>
      </div>
    );
  }
}
