import React, { Component } from "react";
import "./Invoices.sass";

export default class Invoices extends Component {
  state = {
    invoices: [
      { id: "123213213", number: "1232132213" },
      { id: "123213212", number: "1232132213" }
    ]
  };
  render() {
    const {invoices} = this.state;
    return (
      <div>
        <header>
          <h1>Корпоративные карты</h1>
        </header>
        <section>
          <div className="invoices__to-card-invoice">
            <button>Привязать Л/C</button>
          </div>
          <div className="invoices__list">
            {invoices.map(({ id, number }) => (
              <div className="invoices__invoice" key={id}>
                {number}
              </div>
            ))}
          </div>
        </section>
      </div>
    );
  }
}
