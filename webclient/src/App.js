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
import { Row } from "antd";
import "./App.css";

class App extends Component {
  render() {
    return (
      <div>
        <Row type="flex">
          <Router>
            <Switch>
              <Route path="/login" component={Login} />
              <Route path="/invoices" component={Invoices} />
              <Route path="/invoices/:invoiceId" component={Cards} />
              <Redirect exact from="/" to="/login" />
            </Switch>
          </Router>
        </Row>
      </div>
    );
  }
}

export default App;
