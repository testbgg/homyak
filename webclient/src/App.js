import React, { Component } from "react";
import {
  BrowserRouter as Router,
  Route,
  Switch,
  Redirect
} from "react-router-dom";
import "./App.css";

const Login = () => <div> Логин </div>;
const Cards = () => <div> Пароль </div>;

class App extends Component {
  render() {
    return (
      <div className="App">
        <Router>
          <Switch>
            <Route path="/login" component={Login} />
            <Route path="/cards" component={Cards} />
            <Redirect exact from="/" to="/login" />
          </Switch>
        </Router>
      </div>
    );
  }
}

export default App;
