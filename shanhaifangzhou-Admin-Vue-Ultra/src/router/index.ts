import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'
import { useUserStore } from '@/store/modules/user'
import { usePermissionStore } from '@/store/modules/permission'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

// 路由元信息类型
export interface RouteMeta {
  title?: string
  icon?: string
  hidden?: boolean
  roles?: string[]
  permissions?: string[]
  keepAlive?: boolean
  breadcrumb?: boolean
}

// 扩展路由记录类型
export interface AppRouteRecordRaw extends Omit<RouteRecordRaw, 'meta'> {
  meta?: RouteMeta
  children?: AppRouteRecordRaw[]
}

// 路由配置
const routes: AppRouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/login/index.vue'),
    meta: {
      title: '登录',
      hidden: true,
    },
  },
  {
    path: '/',
    name: 'Layout',
    component: () => import('@/layout/index.vue'),
    redirect: '/dashboard',
    meta: {
      title: '首页',
      hidden: true,
    },
    children: [
      {
        path: 'dashboard',
        name: 'Dashboard',
        component: () => import('@/views/dashboard/index.vue'),
        meta: {
          title: '控制台',
          icon: 'Odometer',
          keepAlive: true,
        },
      },
    ],
  },
  {
    path: '/system',
    name: 'System',
    component: () => import('@/layout/index.vue'),
    redirect: '/system/user',
    meta: {
      title: '系统管理',
      icon: 'Setting',
    },
    children: [
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/user/index.vue'),
        meta: {
          title: '用户管理',
          icon: 'User',
          permissions: ['system:user:list'],
        },
      },
      {
        path: 'role',
        name: 'Role',
        component: () => import('@/views/role/index.vue'),
        meta: {
          title: '角色管理',
          icon: 'UserFilled',
          permissions: ['system:role:list'],
        },
      },
      {
        path: 'menu',
        name: 'Menu',
        component: () => import('@/views/menu/index.vue'),
        meta: {
          title: '菜单管理',
          icon: 'Menu',
          permissions: ['system:menu:list'],
        },
      },
      {
        path: 'dept',
        name: 'Dept',
        component: () => import('@/views/dept/index.vue'),
        meta: {
          title: '部门管理',
          icon: 'OfficeBuilding',
          permissions: ['system:dept:list'],
        },
      },
      {
        path: 'dict',
        name: 'Dict',
        component: () => import('@/views/dict/index.vue'),
        meta: {
          title: '字典管理',
          icon: 'Collection',
          permissions: ['system:dict:list'],
        },
      },
      {
        path: 'log',
        name: 'Log',
        component: () => import('@/views/log/index.vue'),
        meta: {
          title: '操作日志',
          icon: 'Document',
          permissions: ['system:log:list'],
        },
      },
    ],
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'NotFound',
    component: () => import('@/views/error/404.vue'),
    meta: {
      title: '404',
      hidden: true,
    },
  },
]

// 创建路由实例
const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior: () => ({ left: 0, top: 0 }),
})

// 路由守卫
router.beforeEach(async (to, from, next) => {
  NProgress.start()
  
  const userStore = useUserStore()
  const permissionStore = usePermissionStore()
  
  // 设置页面标题
  document.title = to.meta?.title ? `${to.meta.title} - 山海方舟管理系统` : '山海方舟管理系统'
  
  // 检查是否需要登录
  if (to.path === '/login') {
    if (userStore.token) {
      next({ path: '/' })
    } else {
      next()
    }
    return
  }
  
  // 检查是否已登录
  if (!userStore.token) {
    next({ path: '/login', query: { redirect: to.fullPath } })
    return
  }
  
  // 检查是否已获取用户信息
  if (!userStore.userInfo) {
    try {
      await userStore.getUserInfo()
      // 生成动态路由
      const accessRoutes = await permissionStore.generateRoutes()
      accessRoutes.forEach(route => {
        router.addRoute(route)
      })
      next({ ...to, replace: true })
    } catch (error) {
      userStore.logout()
      next({ path: '/login', query: { redirect: to.fullPath } })
    }
    return
  }
  
  next()
})

router.afterEach(() => {
  NProgress.done()
})

export default router
