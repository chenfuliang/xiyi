export default [
  {
    path: '/cloth',
    component: () => import('@/layout/index.vue'),
    meta: {
      title: '衣物管理',
    },
    icon: 'course-课程',
    children: [
      {
        path: 'list',
        alias: ['table'],
        icon: 'course-添加课程',
        component: () => import('@/views/cloth/list.vue'),
        meta: {
          title: '衣物列表',
          affix: true,
        },
      },
      {
        path: 'add',
        component: () => import('@/views/cloth/add.vue'),
        icon: 'course-添加课程',
        meta: {
          title: '添加或更新衣物',
          affix: true,
        },
      },
      {
        path: 'edit/:id',
        component: () => import('@/views/cloth/add.vue'),
        icon: 'course-添加课程',
        meta: {
          title: '添加或更新衣物',
          affix: true,
        },
        hidden: true,
      },
    ],
  },
]
