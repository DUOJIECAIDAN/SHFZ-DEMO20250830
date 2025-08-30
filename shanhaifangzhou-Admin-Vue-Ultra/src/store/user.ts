import { defineStore } from 'pinia'
import { login, logout, getUserInfo } from '@/api/auth'
import { getToken, setToken, removeToken } from '@/utils/auth'
import type { LoginForm, UserInfo } from '@/types/auth'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: getToken(),
    userInfo: {} as UserInfo,
    roles: [] as string[],
    permissions: [] as string[]
  }),

  actions: {
    // 登录
    async login(loginForm: LoginForm) {
      try {
        const { data } = await login(loginForm)
        this.token = data.token
        setToken(data.token)
        return data
      } catch (error) {
        throw error
      }
    },

    // 获取用户信息
    async getUserInfo() {
      try {
        const { data } = await getUserInfo()
        this.userInfo = data.user
        this.roles = data.roles
        this.permissions = data.permissions
        return data
      } catch (error) {
        throw error
      }
    },

    // 登出
    async logout() {
      try {
        await logout()
        this.token = ''
        this.userInfo = {}
        this.roles = []
        this.permissions = []
        removeToken()
      } catch (error) {
        throw error
      }
    }
  }
})
