import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";
import Cards from "./pages/Cards";
import "./App.css";

const Login = () => <div> Логин </div>;
const Invoices = () => <div> Пароль </div>;

class App extends Component {
  render() {
    return (
      <div className="App">
        <div className="App-background-top" />
        <Router>
          <Switch>
            <Route path="/login" component={Login} />
            <Route path="/invoices" component={Invoices} />
            <Route path="/invoices/:invoiceId" component={Cards} />
            <Redirect exact from="/" to="/login" />
          </Switch>
        </Router>
        <div className="App-background-bottom" />
      </div>
    );
  }
}

export default App;
