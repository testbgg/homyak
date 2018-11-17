const TableColumns = [
  {
    title: "Номер карты",
    dataIndex: "number"
  },
  {
    title: "Имя сотрудника",
    dataIndex: "owner",
    render: owner => owner ? `${owner.firstName} ${owner.secondName}` : 'Карта без владельца'
  },
  {
    title: "Дневной лимит",
    dataIndex: "dayLimit"
  },
  {
    title: "Месячный лимит",
    dataIndex: "monthLimit"
  }
];

export default TableColumns;
