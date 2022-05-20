import add from '@/views/member/add.vue'
export default [
  {
    path: '/member',
    component: () => import('@/layout/index.vue'),
    meta: {
      title: '会员管理',
      affix: true,
    },
    icon: 'course-课程',
    children: [
      {
        path: 'list',
        alias: ['table'],
        icon: 'course-课程',
        component: () => import('@/views/member/list.vue'),
        meta: {
          title: '会员列表',
          affix: true,
        },
        hidden: false,
      },
      {
        path: 'edit/:id',
        component: () => add,
        icon: 'course-课程',
        meta: {
          title: '编辑会员',
          affix: true,
        },
        hidden: true,
      },
    ],
  },
]
