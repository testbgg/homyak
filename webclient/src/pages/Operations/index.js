import React, { Component } from "react";
import { Table } from "antd";
import axios from "axios";

export default class Operations extends Component {
  state = {
    operations: []
  };

  async componentDidMount() {
    const {
      match: {
        params: { cardId }
      }
    } = this.props;
    await axios.post(`/api/cards/${cardId}/call`, { amount: 100, description: 'Оплата'});
    const { data: operations } = await axios.get(
      `/api/cards/${cardId}/operations`
    );
    this.setState({ operations });
  }
  render() {
    return <Table />;
  }
}
