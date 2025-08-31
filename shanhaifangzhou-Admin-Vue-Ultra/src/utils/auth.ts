// 认证相关工具函数
import Cookies from 'js-cookie'

// Token相关常量
const TOKEN_KEY = import.meta.env.VITE_APP_TOKEN_KEY || 'shanhaifangzhou_token'
const REFRESH_TOKEN_KEY = import.meta.env.VITE_APP_REFRESH_TOKEN_KEY || 'shanhaifangzhou_refresh_token'
const USER_INFO_KEY = import.meta.env.VITE_APP_USER_INFO_KEY || 'shanhaifangzhou_user_info'

// Token过期时间（7天）
const TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000

/**
 * 获取Token
 */
export function getToken(): string {
  return Cookies.get(TOKEN_KEY) || ''
}

/**
 * 设置Token
 */
export function setToken(token: string): void {
  Cookies.set(TOKEN_KEY, token, { expires: TOKEN_EXPIRE_TIME / (24 * 60 * 60 * 1000) })
}

/**
 * 移除Token
 */
export function removeToken(): void {
  Cookies.remove(TOKEN_KEY)
}

/**
 * 获取刷新Token
 */
export function getRefreshToken(): string {
  return Cookies.get(REFRESH_TOKEN_KEY) || ''
}

/**
 * 设置刷新Token
 */
export function setRefreshToken(token: string): void {
  Cookies.set(REFRESH_TOKEN_KEY, token, { expires: TOKEN_EXPIRE_TIME / (24 * 60 * 60 * 1000) })
}

/**
 * 移除刷新Token
 */
export function removeRefreshToken(): void {
  Cookies.remove(REFRESH_TOKEN_KEY)
}

/**
 * 获取用户信息
 */
export function getUserInfo(): any {
  const userInfo = localStorage.getItem(USER_INFO_KEY)
  return userInfo ? JSON.parse(userInfo) : null
}

/**
 * 设置用户信息
 */
export function setUserInfo(userInfo: any): void {
  localStorage.setItem(USER_INFO_KEY, JSON.stringify(userInfo))
}

/**
 * 移除用户信息
 */
export function removeUserInfo(): void {
  localStorage.removeItem(USER_INFO_KEY)
}

/**
 * 清除所有认证信息
 */
export function clearAuth(): void {
  removeToken()
  removeRefreshToken()
  removeUserInfo()
}

/**
 * 检查Token是否过期
 */
export function isTokenExpired(): boolean {
  const token = getToken()
  if (!token) return true
  
  try {
    const payload = JSON.parse(atob(token.split('.')[1]))
    const currentTime = Date.now() / 1000
    return payload.exp < currentTime
  } catch (error) {
    return true
  }
}

/**
 * 检查用户是否已登录
 */
export function isLoggedIn(): boolean {
  const token = getToken()
  return !!token && !isTokenExpired()
}

/**
 * 获取Token中的用户信息
 */
export function getTokenUserInfo(): any {
  const token = getToken()
  if (!token) return null
  
  try {
    const payload = JSON.parse(atob(token.split('.')[1]))
    return {
      username: payload.sub,
      userId: payload.userId,
      roles: payload.roles || [],
      permissions: payload.permissions || []
    }
  } catch (error) {
    return null
  }
}

/**
 * 格式化Token过期时间
 */
export function formatTokenExpireTime(): string {
  const token = getToken()
  if (!token) return ''
  
  try {
    const payload = JSON.parse(atob(token.split('.')[1]))
    const expireTime = new Date(payload.exp * 1000)
    return expireTime.toLocaleString()
  } catch (error) {
    return ''
  }
}

/**
 * 检查权限
 */
export function hasPermission(permission: string): boolean {
  const userInfo = getUserInfo()
  if (!userInfo || !userInfo.permissions) return false
  
  return userInfo.permissions.includes(permission)
}

/**
 * 检查角色
 */
export function hasRole(role: string): boolean {
  const userInfo = getUserInfo()
  if (!userInfo || !userInfo.roles) return false
  
  return userInfo.roles.includes(role)
}

/**
 * 检查是否为超级管理员
 */
export function isSuperAdmin(): boolean {
  return hasRole('SUPER_ADMIN') || hasRole('ADMIN')
}

/**
 * 获取用户头像
 */
export function getUserAvatar(): string {
  const userInfo = getUserInfo()
  return userInfo?.avatar || '/default-avatar.png'
}

/**
 * 获取用户昵称
 */
export function getUserNickname(): string {
  const userInfo = getUserInfo()
  return userInfo?.nickname || userInfo?.username || '未知用户'
}

/**
 * 获取用户部门
 */
export function getUserDepartment(): string {
  const userInfo = getUserInfo()
  return userInfo?.deptName || '未知部门'
}

/**
 * 获取用户角色
 */
export function getUserRoles(): string[] {
  const userInfo = getUserInfo()
  return userInfo?.roles || []
}

/**
 * 获取用户权限
 */
export function getUserPermissions(): string[] {
  const userInfo = getUserInfo()
  return userInfo?.permissions || []
}

/**
 * 检查用户状态
 */
export function isUserEnabled(): boolean {
  const userInfo = getUserInfo()
  return userInfo?.status === 1
}

/**
 * 检查用户是否被锁定
 */
export function isUserLocked(): boolean {
  const userInfo = getUserInfo()
  return userInfo?.status === 0
}

/**
 * 获取用户最后登录时间
 */
export function getLastLoginTime(): string {
  const userInfo = getUserInfo()
  return userInfo?.lastLoginTime || ''
}

/**
 * 获取用户最后登录IP
 */
export function getLastLoginIp(): string {
  const userInfo = getUserInfo()
  return userInfo?.lastLoginIp || ''
}

/**
 * 生成随机Token（用于测试）
 */
export function generateRandomToken(): string {
  const chars = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789'
  let result = ''
  for (let i = 0; i < 32; i++) {
    result += chars.charAt(Math.floor(Math.random() * chars.length))
  }
  return result
}

/**
 * 验证Token格式
 */
export function validateTokenFormat(token: string): boolean {
  if (!token) return false
  
  // 检查是否为JWT格式（三段，用.分隔）
  const parts = token.split('.')
  if (parts.length !== 3) return false
  
  // 检查每段是否为base64编码
  try {
    parts.forEach(part => {
      if (part) {
        atob(part)
      }
    })
    return true
  } catch (error) {
    return false
  }
}

/**
 * 获取Token剩余有效期（秒）
 */
export function getTokenRemainingTime(): number {
  const token = getToken()
  if (!token) return 0
  
  try {
    const payload = JSON.parse(atob(token.split('.')[1]))
    const currentTime = Math.floor(Date.now() / 1000)
    return Math.max(0, payload.exp - currentTime)
  } catch (error) {
    return 0
  }
}

/**
 * 检查Token是否需要刷新（剩余时间少于5分钟）
 */
export function needRefreshToken(): boolean {
  const remainingTime = getTokenRemainingTime()
  return remainingTime < 5 * 60 // 5分钟
}
