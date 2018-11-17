import React from "react";
import { Link } from "react-router-dom";
import { Icon } from "antd";

const TableColumns = [
  {
    title: "Номер карты",
    dataIndex: "number"
  },
  {
    title: "Имя сотрудника",
    dataIndex: "owner",
    render: owner =>
      owner ? `${owner.firstName} ${owner.secondName}` : "Карта без владельца"
  },
  {
    title: "Дневной лимит",
    dataIndex: "dayLimit"
  },
  {
    title: "Месячный лимит",
    dataIndex: "monthLimit"
  },
  {
    title: "Выписка по карте",
    dataIndex: "key",
    render: key => (
      <Link to={`/cards/${key}/operations`}>
        <Icon type="file-text" />
      </Link>
    )
  }
];

export default TableColumns;
