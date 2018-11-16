import React, { Component } from "react";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";
import Cards from "./pages/Cards";
import Login from "./pages/Login";
import Invoices from "./pages/Invoices";
import "./App.css";

class App extends Component {
  render() {
    return (
      <div className="container">
          <Router>
            <Switch>
              <Route path="/login" component={Login} />
              <Route path="/invoices/list" component={Invoices} />
              <Route exact path="/invoices/:invoiceId" component={Cards} />
              <Redirect exact from="/" to="/login" />
            </Switch>
          </Router>
      </div>
    );
  }
}

export default App;
