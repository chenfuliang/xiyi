export default [
  {
    path: '/invest',
    component: () => import('@/layout/index.vue'),
    meta: {
      title: '余额记录管理',
    },
    icon: 'invest-充值记录管理',
    hidden: false,
    children: [
      {
        path: 'list',
        alias: ['table'],
        icon: 'invest-充值记录列表',
        component: () => import('@/views/invest/list.vue'),
        meta: {
          title: '充值记录列表',
          affix: true,
        },
        hidden: false,
      },
    ],
  },
]
