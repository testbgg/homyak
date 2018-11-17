import React, { Component } from "react";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect,
  Link
} from "react-router-dom";
import Cards from "./pages/Cards";
import Login from "./pages/Login";
import Invoices from "./pages/Invoices";
import Operations from "./pages/Operations";
import "./App.css";

const Header = () => (
  <header>
    <div className="container invoices__header-top">
      <Link to="/invoices">
        <div className="invoices__logo" />
      </Link>
      <div>Patient login</div>
    </div>
    <nav className="invoices__navigation">
      <div className="container invoices__navigations--in-container">
        <span className="invoices__navigation-item">Главная</span>
        <span className="invoices__navigation-item">Выписки</span>
        <span className="invoices__navigation-item">Платежи</span>
        <span className="invoices__navigation-item invoices__navigation-item--with-pseudo">
          Валюта
        </span>
        <span className="invoices__navigation-item invoices__navigation-item--selected">
          Карты
        </span>
        <span className="invoices__navigation-item">Кредиты</span>
        <span className="invoices__navigation-item">Депозиты</span>
        <span className="invoices__navigation-item">Сообщения</span>
      </div>
    </nav>
  </header>
);

class App extends Component {
  render() {
    return (
      <div>
        <Router>
          <div>
            <Route>
              {({ location }) => location.pathname !== "/login" && <Header />}
            </Route>
            <Switch>
              <Route path="/login" component={Login} />
              <Route exact path="/invoices" component={Invoices} />
              <Route path="/invoices/:invoiceId" component={Cards} />
              <Route
                path="/cards/:cardId/operations"
                component={Operations}
              />
              <Redirect exact from="/" to="/login" />
            </Switch>
          </div>
        </Router>
      </div>
    );
  }
}

export default App;
