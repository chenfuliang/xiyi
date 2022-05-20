export default [
  {
    path: '/cus',
    component: () => import('@/layout/index.vue'),
    meta: {
      title: '客户管理',
    },
    icon: 'course-课程',
    children: [
      {
        path: 'list',
        alias: ['table'],
        icon: 'course-课程列表',
        component: () => import('@/views/customer/list.vue'),
        meta: {
          title: '客户列表',
          affix: true,
        },
      },
      {
        path: 'add',
        component: () => import('@/views/customer/add.vue'),
        icon: 'course-添加课程',
        meta: {
          title: '添加/修改客户',
        },
      },
      {
        path: 'edit/:id',
        component: () => import('@/views/customer/add.vue'),
        meta: {
          title: '编辑客户',
          affix: true,
        },
        hidden: true,
      },
    ],
  },
]
