import React, { Fragment, Component } from 'react';
import { Form, Icon, Input, Button } from 'antd';
import { Link } from 'react-router-dom';

const FormItem = Form.Item;

export default class Login extends Component {
  componentDidMount() {
    // To disabled submit button at the beginning.
  }

  handleSubmit = e => {
    e.preventDefault();
  };

  render() {
    return (
      <Fragment>
        <div className="App-background-top" />
        <Form layout="inline" onSubmit={this.handleSubmit}>
          <FormItem>
            <Input
              prefix={<Icon type="user" style={{ color: 'rgba(0,0,0,.25)' }} />}
              placeholder="Username"
            />
          </FormItem>
          <FormItem>
            <Input
              prefix={<Icon type="lock" style={{ color: 'rgba(0,0,0,.25)' }} />}
              type="password"
              placeholder="Password"
            />
          </FormItem>
          <FormItem>
            <Link to="/invoices/list">
              <Button type="primary" htmlType="submit">
                Войти
              </Button>
            </Link>
          </FormItem>
        </Form>
        <div className="App-background-bottom" />
      </Fragment>
    );
  }
}
