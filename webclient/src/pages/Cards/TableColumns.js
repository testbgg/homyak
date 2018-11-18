import React from 'react';
import { Link, Route } from 'react-router-dom';
import { Icon } from 'antd';
import day from 'dayjs';

const getStatus = status => {
  switch (status) {
    case 'ACTIVE':
      return 'Активна';
    case 'BLOCKED':
      return 'Заблокирована';
    case 'REQUESTED':
      return 'Заказана';
    case 'CLOSED':
      return 'Закрыта';
    default:
      return;
  }
};

const TableColumns = [
  {
    title: 'Номер карты',
    dataIndex: 'number'
  },
  {
    title: 'Имя сотрудника',
    dataIndex: 'owner',
    render: owner =>
      owner ? `${owner.firstName} ${owner.secondName}` : 'Карта без владельца'
  },
  {
    title: 'Дневной лимит',
    dataIndex: 'dayLimit'
  },
  {
    title: 'Месячный лимит',
    dataIndex: 'monthLimit'
  },
  {
    title: 'Статус',
    dataIndex: 'state',
    render: state => getStatus(state)
  },
  {
    title: 'Срок действия',
    dataIndex: 'validUntil',
    render: validUntil => (validUntil ? day(validUntil).format('MM/YYYY') : '-')
  },
  {
    title: 'Выписка',
    dataIndex: 'key',
    render: key => (
      <Route>
        {({ location }) => (
          <Link
            to={{
              pathname: `/cards/${key}/operations`,
              invoiceId: location.pathname
            }}
          >
            <Icon type="file-text" />
          </Link>
        )}
      </Route>
    )
  }
];

export default TableColumns;
