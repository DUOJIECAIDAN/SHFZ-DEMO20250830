import { defineStore } from 'pinia'
import { ref } from 'vue'
import { getMenuList } from '@/api/menu'
import type { AppRouteRecordRaw } from '@/router'
import { asyncRoutes, constantRoutes } from '@/router'

export const usePermissionStore = defineStore('permission', () => {
  // 状态
  const routes = ref<AppRouteRecordRaw[]>([])
  const addRoutes = ref<AppRouteRecordRaw[]>([])

  // 生成路由
  const generateRoutes = async () => {
    try {
      // 获取菜单列表
      const { data } = await getMenuList()
      const accessedRoutes = filterAsyncRoutes(asyncRoutes, data)
      addRoutes.value = accessedRoutes
      routes.value = constantRoutes.concat(accessedRoutes)
      return accessedRoutes
    } catch (error) {
      console.error('生成路由失败:', error)
      return []
    }
  }

  // 过滤异步路由
  const filterAsyncRoutes = (routes: AppRouteRecordRaw[], menus: any[]): AppRouteRecordRaw[] => {
    const res: AppRouteRecordRaw[] = []

    routes.forEach(route => {
      const tmp = { ...route }
      if (hasPermission(tmp, menus)) {
        if (tmp.children) {
          tmp.children = filterAsyncRoutes(tmp.children, menus)
        }
        res.push(tmp)
      }
    })

    return res
  }

  // 检查权限
  const hasPermission = (route: AppRouteRecordRaw, menus: any[]): boolean => {
    if (route.meta && route.meta.permissions) {
      return menus.some(menu => 
        route.meta?.permissions?.includes(menu.perms)
      )
    }
    return true
  }

  return {
    // 状态
    routes,
    addRoutes,
    // 方法
    generateRoutes,
    filterAsyncRoutes,
    hasPermission,
  }
})

// 静态路由
export const constantRoutes: AppRouteRecordRaw[] = [
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
]

// 动态路由
export const asyncRoutes: AppRouteRecordRaw[] = [
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
]