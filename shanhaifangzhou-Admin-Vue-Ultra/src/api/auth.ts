// 认证相关API接口
import { request } from '@/utils/request'
import type { LoginForm, LoginResponse, UserInfo, ApiResponse } from '@/types/auth'

/**
 * 用户登录
 */
export function login(data: LoginForm): Promise<ApiResponse<LoginResponse>> {
  return request.post('/auth/login', data)
}

/**
 * 用户登出
 */
export function logout(): Promise<ApiResponse<null>> {
  return request.post('/auth/logout')
}

/**
 * 获取用户信息
 */
export function getUserInfo(): Promise<ApiResponse<UserInfo>> {
  return request.get('/auth/userinfo')
}

/**
 * 刷新Token
 */
export function refreshToken(refreshToken: string): Promise<ApiResponse<LoginResponse>> {
  return request.post('/auth/refresh', { refreshToken })
}

/**
 * 修改密码
 */
export function changePassword(data: {
  oldPassword: string
  newPassword: string
  confirmPassword: string
}): Promise<ApiResponse<null>> {
  return request.put('/auth/password', data)
}

/**
 * 更新用户信息
 */
export function updateUserInfo(data: {
  nickname?: string
  email?: string
  phone?: string
  avatar?: string
  gender?: number
}): Promise<ApiResponse<null>> {
  return request.put('/auth/userinfo', data)
}

/**
 * 获取验证码
 */
export function getCaptcha(): Promise<ApiResponse<{
  captchaId: string
  captchaImage: string
}>> {
  return request.get('/auth/captcha')
}

/**
 * 验证验证码
 */
export function verifyCaptcha(captchaId: string, captchaCode: string): Promise<ApiResponse<boolean>> {
  return request.post('/auth/captcha/verify', { captchaId, captchaCode })
}

/**
 * 发送邮箱验证码
 */
export function sendEmailCode(email: string): Promise<ApiResponse<null>> {
  return request.post('/auth/email/code', { email })
}

/**
 * 验证邮箱验证码
 */
export function verifyEmailCode(email: string, code: string): Promise<ApiResponse<boolean>> {
  return request.post('/auth/email/verify', { email, code })
}

/**
 * 发送短信验证码
 */
export function sendSmsCode(phone: string): Promise<ApiResponse<null>> {
  return request.post('/auth/sms/code', { phone })
}

/**
 * 验证短信验证码
 */
export function verifySmsCode(phone: string, code: string): Promise<ApiResponse<boolean>> {
  return request.post('/auth/sms/verify', { phone, code })
}

/**
 * 忘记密码
 */
export function forgotPassword(data: {
  username: string
  email: string
  code: string
  newPassword: string
  confirmPassword: string
}): Promise<ApiResponse<null>> {
  return request.post('/auth/forgot-password', data)
}

/**
 * 重置密码
 */
export function resetPassword(data: {
  username: string
  phone: string
  code: string
  newPassword: string
  confirmPassword: string
}): Promise<ApiResponse<null>> {
  return request.post('/auth/reset-password', data)
}

/**
 * 绑定邮箱
 */
export function bindEmail(data: {
  email: string
  code: string
}): Promise<ApiResponse<null>> {
  return request.post('/auth/bind-email', data)
}

/**
 * 绑定手机
 */
export function bindPhone(data: {
  phone: string
  code: string
}): Promise<ApiResponse<null>> {
  return request.post('/auth/bind-phone', data)
}

/**
 * 解绑邮箱
 */
export function unbindEmail(): Promise<ApiResponse<null>> {
  return request.delete('/auth/bind-email')
}

/**
 * 解绑手机
 */
export function unbindPhone(): Promise<ApiResponse<null>> {
  return request.delete('/auth/bind-phone')
}

/**
 * 获取登录历史
 */
export function getLoginHistory(params: {
  pageNum: number
  pageSize: number
  startTime?: string
  endTime?: string
}): Promise<ApiResponse<{
  records: Array<{
    id: number
    username: string
    loginTime: string
    loginIp: string
    loginLocation: string
    browser: string
    os: string
    status: number
    message: string
  }>
  total: number
  current: number
  size: number
  pages: number
}>> {
  return request.get('/auth/login-history', params)
}

/**
 * 获取在线用户列表
 */
export function getOnlineUsers(params: {
  pageNum: number
  pageSize: number
  username?: string
  ipaddr?: string
}): Promise<ApiResponse<{
  records: Array<{
    id: number
    username: string
    nickname: string
    deptName: string
    loginIp: string
    loginLocation: string
    browser: string
    os: string
    loginTime: string
    lastAccessTime: string
  }>
  total: number
  current: number
  size: number
  pages: number
}>> {
  return request.get('/auth/online-users', params)
}

/**
 * 强制下线用户
 */
export function forceLogout(sessionId: string): Promise<ApiResponse<null>> {
  return request.delete(`/auth/force-logout/${sessionId}`)
}

/**
 * 获取用户权限列表
 */
export function getUserPermissions(): Promise<ApiResponse<string[]>> {
  return request.get('/auth/permissions')
}

/**
 * 获取用户角色列表
 */
export function getUserRoles(): Promise<ApiResponse<string[]>> {
  return request.get('/auth/roles')
}

/**
 * 检查用户是否有指定权限
 */
export function checkPermission(permission: string): Promise<ApiResponse<boolean>> {
  return request.get(`/auth/check-permission/${permission}`)
}

/**
 * 检查用户是否有指定角色
 */
export function checkRole(role: string): Promise<ApiResponse<boolean>> {
  return request.get(`/auth/check-role/${role}`)
}