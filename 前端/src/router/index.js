import { createRouter, createWebHashHistory } from 'vue-router'

import redirect from './modules/redirect'
import error from './modules/error'
import login from './modules/login'
import lock from './modules/lock'
import employee from './modules/employee'
import customer from './modules/customer'
import order from './modules/order'
import cloth from './modules/cloth'
import inves from './modules/invest'
import member from './modules/member'
export const fixedRoutes = [
  ...employee,
  ...customer,
  ...order,
  ...cloth,
  ...inves,
  ...member,
]
// 动态菜单
// export const asyncRoutes = [...test]

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      redirect: '/home',
    },
    ...redirect, // 统一的重定向配置
    ...login,
    ...lock,
    ...fixedRoutes,
    ...error,
  ],
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    } else {
      return { left: 0, top: 0 }
    }
  },
})

export default router
