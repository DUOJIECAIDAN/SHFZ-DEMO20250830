import { defineStore } from 'pinia'
import { ref } from 'vue'
import { login, logout, getUserInfo } from '@/api/auth'
import { removeToken, setToken, getToken } from '@/utils/auth'
import type { LoginForm, UserInfo } from '@/types/auth'

export const useUserStore = defineStore('user', () => {
  // 状态
  const token = ref<string>(getToken() || '')
  const userInfo = ref<UserInfo | null>(null)
  const roles = ref<string[]>([])
  const permissions = ref<string[]>([])

  // 登录
  const loginAction = async (loginForm: LoginForm) => {
    try {
      const { data } = await login(loginForm)
      const { accessToken } = data
      token.value = accessToken
      setToken(accessToken)
      return data
    } catch (error) {
      throw error
    }
  }

  // 获取用户信息
  const getUserInfoAction = async () => {
    try {
      const { data } = await getUserInfo()
      userInfo.value = data
      roles.value = data.roles || []
      permissions.value = data.permissions || []
      return data
    } catch (error) {
      throw error
    }
  }

  // 登出
  const logoutAction = async () => {
    try {
      await logout()
      resetUserInfo()
    } catch (error) {
      console.error('登出失败:', error)
    } finally {
      resetUserInfo()
    }
  }

  // 重置用户信息
  const resetUserInfo = () => {
    token.value = ''
    userInfo.value = null
    roles.value = []
    permissions.value = []
    removeToken()
  }

  // 检查权限
  const hasPermission = (permission: string) => {
    return permissions.value.includes(permission)
  }

  // 检查角色
  const hasRole = (role: string) => {
    return roles.value.includes(role)
  }

  return {
    // 状态
    token,
    userInfo,
    roles,
    permissions,
    // 方法
    loginAction,
    getUserInfoAction,
    logoutAction,
    resetUserInfo,
    hasPermission,
    hasRole,
  }
})