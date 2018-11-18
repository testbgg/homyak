import day from 'dayjs';

const TableColumns = [
  {
    title: 'Сумма',
    dataIndex: 'cash'
  },
  {
    title: 'Описание',
    dataIndex: 'description'
  },
  {
    title: 'ID',
    dataIndex: 'id'
  },
  {
    title: 'Тип',
    dataIndex: 'type'
  },
  {
    title: 'Дата операции',
    dataIndex: 'operationDate',
    render: operationDate => day(operationDate).format('YYYY-MM-DD HH:mm:ss')
  }
];

export default TableColumns;
