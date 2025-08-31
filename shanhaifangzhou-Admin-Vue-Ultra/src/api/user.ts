// 用户管理相关API接口
import { request } from '@/utils/request'
import type { ApiResponse, PageResponse } from '@/types/auth'

// 用户信息接口
export interface UserInfo {
  id: number
  username: string
  nickname: string
  email: string
  phone: string
  avatar: string
  gender: number
  status: number
  deptId: number
  deptName: string
  postId: number
  postName: string
  userType: number
  loginType: number
  isFirstLogin: number
  passwordErrorCount: number
  passwordErrorTime: string
  lockTime: string
  lockReason: string
  onlineStatus: number
  lastActiveTime: string
  lastLoginTime: string
  lastLoginIp: string
  userSource: number
  extData: string
  createTime: string
  updateTime: string
  createBy: string
  updateBy: string
  remark: string
  roleIds: number[]
  roleNames: string[]
}

// 用户查询参数
export interface UserQueryParams {
  pageNum: number
  pageSize: number
  username?: string
  nickname?: string
  email?: string
  phone?: string
  status?: number
  deptId?: number
  roleId?: number
  userType?: number
  startTime?: string
  endTime?: string
  onlineStatus?: number
}

// 用户创建参数
export interface UserCreateParams {
  username: string
  password: string
  confirmPassword: string
  nickname: string
  email?: string
  phone?: string
  gender?: number
  deptId: number
  postId?: number
  roleIds: number[]
  userType?: number
  remark?: string
}

// 用户更新参数
export interface UserUpdateParams {
  id: number
  nickname: string
  email?: string
  phone?: string
  avatar?: string
  gender?: number
  status?: number
  deptId: number
  postId?: number
  roleIds: number[]
  userType?: number
  remark?: string
}

// 密码修改参数
export interface PasswordChangeParams {
  id: number
  oldPassword: string
  newPassword: string
}

// 密码重置参数
export interface PasswordResetParams {
  id: number
  newPassword: string
}

// 用户状态更新参数
export interface UserStatusParams {
  id: number
  status: number
}

// 用户锁定参数
export interface UserLockParams {
  id: number
  reason: string
}

// 角色分配参数
export interface RoleAssignParams {
  userId: number
  roleIds: number[]
}

/**
 * 分页查询用户列表
 */
export function getUserPage(params: UserQueryParams): Promise<ApiResponse<PageResponse<UserInfo>>> {
  return request.get('/system/user/page', params)
}

/**
 * 根据ID查询用户详情
 */
export function getUserById(id: number): Promise<ApiResponse<UserInfo>> {
  return request.get(`/system/user/${id}`)
}

/**
 * 创建用户
 */
export function createUser(data: UserCreateParams): Promise<ApiResponse<null>> {
  return request.post('/system/user', data)
}

/**
 * 更新用户
 */
export function updateUser(data: UserUpdateParams): Promise<ApiResponse<null>> {
  return request.put('/system/user', data)
}

/**
 * 删除用户
 */
export function deleteUser(id: number): Promise<ApiResponse<null>> {
  return request.delete(`/system/user/${id}`)
}

/**
 * 批量删除用户
 */
export function batchDeleteUsers(ids: number[]): Promise<ApiResponse<null>> {
  return request.delete('/system/user/batch', { ids })
}

/**
 * 重置用户密码
 */
export function resetPassword(id: number, newPassword: string): Promise<ApiResponse<null>> {
  return request.put(`/system/user/${id}/password/reset`, null, {
    params: { newPassword }
  })
}

/**
 * 修改用户密码
 */
export function changePassword(data: PasswordChangeParams): Promise<ApiResponse<null>> {
  return request.put(`/system/user/${data.id}/password/change`, null, {
    params: { oldPassword: data.oldPassword, newPassword: data.newPassword }
  })
}

/**
 * 更新用户状态
 */
export function updateUserStatus(id: number, status: number): Promise<ApiResponse<null>> {
  return request.put(`/system/user/${id}/status`, null, {
    params: { status }
  })
}

/**
 * 锁定用户账号
 */
export function lockUser(id: number, reason: string): Promise<ApiResponse<null>> {
  return request.put(`/system/user/${id}/lock`, null, {
    params: { reason }
  })
}

/**
 * 解锁用户账号
 */
export function unlockUser(id: number): Promise<ApiResponse<null>> {
  return request.put(`/system/user/${id}/unlock`)
}

/**
 * 强制下线用户
 */
export function forceLogout(id: number): Promise<ApiResponse<null>> {
  return request.put(`/system/user/${id}/force-logout`)
}

/**
 * 分配用户角色
 */
export function assignUserRoles(data: RoleAssignParams): Promise<ApiResponse<null>> {
  return request.post(`/system/user/${data.userId}/roles`, data.roleIds)
}

/**
 * 获取用户角色ID列表
 */
export function getUserRoles(userId: number): Promise<ApiResponse<number[]>> {
  return request.get(`/system/user/${userId}/roles`)
}

/**
 * 根据部门ID查询用户列表
 */
export function getUsersByDeptId(deptId: number): Promise<ApiResponse<UserInfo[]>> {
  return request.get(`/system/user/dept/${deptId}`)
}

/**
 * 根据角色ID查询用户列表
 */
export function getUsersByRoleId(roleId: number): Promise<ApiResponse<UserInfo[]>> {
  return request.get(`/system/user/role/${roleId}`)
}

/**
 * 获取在线用户列表
 */
export function getOnlineUsers(): Promise<ApiResponse<UserInfo[]>> {
  return request.get('/system/user/online')
}

/**
 * 检查用户名是否唯一
 */
export function checkUsernameUnique(username: string, excludeId?: number): Promise<ApiResponse<boolean>> {
  const params: any = { username }
  if (excludeId) {
    params.excludeId = excludeId
  }
  return request.get('/system/user/check-username', params)
}

/**
 * 检查邮箱是否唯一
 */
export function checkEmailUnique(email: string, excludeId?: number): Promise<ApiResponse<boolean>> {
  const params: any = { email }
  if (excludeId) {
    params.excludeId = excludeId
  }
  return request.get('/system/user/check-email', params)
}

/**
 * 检查手机号是否唯一
 */
export function checkPhoneUnique(phone: string, excludeId?: number): Promise<ApiResponse<boolean>> {
  const params: any = { phone }
  if (excludeId) {
    params.excludeId = excludeId
  }
  return request.get('/system/user/check-phone', params)
}

/**
 * 获取用户统计信息
 */
export function getUserStats(): Promise<ApiResponse<{
  totalUsers: number
  enabledUsers: number
  disabledUsers: number
  onlineUsers: number
  systemUsers: number
  businessUsers: number
}>> {
  return request.get('/system/user/stats')
}

/**
 * 获取用户登录统计
 */
export function getUserLoginStats(days: number = 7): Promise<ApiResponse<Array<{
  loginDate: string
  loginCount: number
}>>> {
  return request.get('/system/user/login-stats', { days })
}