import React from "react";
import { Table, Button, Icon } from "antd";

const columns = [
  {
    title: "Name",
    dataIndex: "name",
    render: text => <a href="javascript:;">{text}</a>
  },
  {
    title: "Age",
    dataIndex: "age"
  },
  {
    title: "Address",
    dataIndex: "address"
  }
];

// rowSelection object indicates the need for row selection
const rowSelection = {
  onChange: (selectedRowKeys, selectedRows) => {
    console.log(
      `selectedRowKeys: ${selectedRowKeys}`,
      "selectedRows: ",
      selectedRows
    );
  },
  getCheckboxProps: record => ({
    disabled: record.name === "Disabled User", // Column configuration not to be checked
    name: record.name
  })
};

export default function CashinOutCard({ cards }) {
  return (
    <>
      <Button className="cards__button" onClick={() => console.log("Я выпускаю карты")}>
        Выпуск новой карты
      </Button>
      <Table rowSelection={rowSelection} columns={columns} dataSource={cards} />
    </>
  );
}
