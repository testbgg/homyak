import React, { Component } from "react";
import classNames from "classnames";
import CreditCard from "./CreditCard";
import DebetCard from "./DebetCard";
import CashInOutCard from "./CashInOutCard";
import "./Cards.sass";

class Cards extends Component {
  state = {
    type: "debet"
  };

  onChange = key => {
    this.setState({ type: key });
  };

  render() {
    const {
      location: {
        pathname,
        state: { cards }
      }
    } = this.props;
    const { type } = this.state;
    const toggleClasses = toggleType =>
      classNames({
        "cards__toggle-button": true,
        "cards__toggle-button--active": toggleType === type
      });
    return (
      <div>
        <div>
          <header className="cards__header" />
          <div className="cards__toggler">
            <div
              className={toggleClasses("debet")}
              onClick={() => this.onChange("debet")}
            >
              Дебетовые
            </div>
            <div
              className={toggleClasses("cashinout")}
              onClick={() => this.onChange("cashinout")}
            >
              Cash IN/OUT
            </div>
            <div
              className={toggleClasses("credit")}
              onClick={() => this.onChange("credit")}
            >
              Кредитные
            </div>
          </div>
          <main className="cards__cards">
            {type === "credit" && (
              <CreditCard
                pathname={pathname}
                cards={cards.filter(card => card.type === "Credit")}
              />
            )}
            {type === "debet" && (
              <DebetCard
                pathname={pathname}
                cards={cards.filter(card => card.type === "Debit")}
              />
            )}
            {type === "cashinout" && (
              <CashInOutCard
                pathname={pathname}
                cards={cards.filter(card => card.type === "Cashinout")}
              />
            )}
          </main>
        </div>
      </div>
    );
  }
}

export default Cards;
