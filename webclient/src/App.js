import React, { Component } from 'react';
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from 'react-router-dom';
import Cards from './pages/Cards';
import Login from './pages/Login';
import './App.css';

const Invoices = () => <div> Пароль </div>;

class App extends Component {
  render() {
    return (
      <div className="App">
        <Router>
          <Switch>
            <Route path="/login" component={Login} />
            <Route path="/invoices" component={Invoices} />
            <Route path="/invoices/:invoiceId" component={Cards} />
            <Redirect exact from="/" to="/login" />
          </Switch>
        </Router>
      </div>
    );
  }
}

export default App;
