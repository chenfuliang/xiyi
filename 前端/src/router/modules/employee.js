import add from '@/views/employee/add.vue'
export default [
  {
    path: '/emp',
    component: () => import('@/layout/index.vue'),
    meta: {
      title: '员工管理',
    },
    icon: 'course-课程',
    children: [
      {
        path: 'list',
        alias: ['table'],
        icon: 'course-课程列表',
        component: () => import('@/views/employee/list.vue'),
        meta: {
          title: '员工列表',
          affix: true,
        },
      },
      {
        path: 'add',
        component: add,
        icon: 'course-添加课程',
        meta: {
          title: '添加/修改员工',
        },
      },
      {
        path: 'edit/:id',
        component: () => add,
        meta: {
          title: '编辑员工',
          affix: true,
        },
        hidden: true,
      },
    ],
  },
]
