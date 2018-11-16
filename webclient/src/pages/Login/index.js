import React from 'react';
import { Form, Icon, Input, Button } from 'antd';

const FormItem = Form.Item;

export default class Login extends React.Component {
  componentDidMount() {
    // To disabled submit button at the beginning.
  }

  handleSubmit = e => {
    e.preventDefault();
  };

  render() {
    return (
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
          <Button type="primary" htmlType="submit">
            Log in
          </Button>
        </FormItem>
      </Form>
    );
  }
}
