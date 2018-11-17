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
      location: { pathname }
    } = this.props;
    const { type } = this.state;
    const toggleClasses = toggleType =>
      classNames({
        "Cards__toggle-button": true,
        "Cards__toggle-button--active": toggleType === type
      });
    return (
      <div>
        <div>
          <header className="Cards__header">
            <div>left</div>
            <div>right</div>
          </header>
          <div className="Cards__toggler">
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
          <main className="Cards__cards">
            {type === "credit" && <CreditCard pathname={pathname} />}
            {type === "debet" && <DebetCard pathname={pathname} />}
            {type === "cashinout" && <CashInOutCard pathname={pathname} />}
          </main>
        </div>
      </div>
    );
  }
}

export default Cards;
