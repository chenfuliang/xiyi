export default [
  {
    path: '/ord',
    component: () => import('@/layout/index.vue'),
    meta: {
      title: '订单管理',
    },
    icon: 'course-课程',
    children: [
      {
        path: 'list',
        alias: ['table'],
        icon: 'course-课程列表',
        component: () => import('@/views/order/list.vue'),
        meta: {
          title: '订单列表',
          affix: true,
        },
      },
      {
        path: 'add',
        component: () => import('@/views/order/add.vue'),
        icon: 'course-添加课程',
        meta: {
          title: '添加/修改订单',
        },
      },
      {
        path: 'edit/:id',
        component: () => import('@/views/order/add.vue'),
        meta: {
          title: '编辑订单',
          affix: true,
        },
        hidden: true,
      },
      {
        path: 'track/:id',
        component: () => import('@/views/order/track.vue'),
        meta: {
          title: '追踪订单',
          affix: true,
        },
        hidden: true,
      },
    ],
  },
]
