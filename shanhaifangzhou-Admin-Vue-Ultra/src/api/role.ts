// 角色管理相关API接口
import { request } from '@/utils/request'
import type { ApiResponse, PageResponse } from '@/types/auth'

// 角色信息接口
export interface RoleInfo {
  id: number
  roleName: string
  roleCode: string
  roleDesc: string
  roleType: number
  status: number
  sort: number
  dataScope: number
  roleLevel: number
  isSystem: number
  isDeletable: number
  isEditable: number
  roleIcon: string
  roleColor: string
  extData: string
  createTime: string
  updateTime: string
  createBy: string
  updateBy: string
  remark: string
  menuIds: number[]
  menuNames: string[]
  userCount: number
  deptIds: number[]
}

// 角色查询参数
export interface RoleQueryParams {
  pageNum: number
  pageSize: number
  roleName?: string
  roleCode?: string
  status?: number
  roleType?: number
  roleLevel?: number
  isSystem?: number
  dataScope?: number
  startTime?: string
  endTime?: string
}

// 角色创建参数
export interface RoleCreateParams {
  roleName: string
  roleCode: string
  roleDesc?: string
  roleType?: number
  status?: number
  sort?: number
  dataScope?: number
  roleLevel?: number
  roleIcon?: string
  roleColor?: string
  menuIds?: number[]
  deptIds?: number[]
  remark?: string
}

// 角色更新参数
export interface RoleUpdateParams {
  id: number
  roleName: string
  roleCode: string
  roleDesc?: string
  roleType?: number
  status?: number
  sort?: number
  dataScope?: number
  roleLevel?: number
  roleIcon?: string
  roleColor?: string
  menuIds?: number[]
  deptIds?: number[]
  remark?: string
}

// 角色状态更新参数
export interface RoleStatusParams {
  id: number
  status: number
}

// 角色排序更新参数
export interface RoleSortParams {
  id: number
  sort: number
}

// 菜单权限分配参数
export interface MenuAssignParams {
  roleId: number
  menuIds: number[]
}

// 数据权限分配参数
export interface DeptAssignParams {
  roleId: number
  deptIds: number[]
}

// 角色复制参数
export interface RoleCopyParams {
  sourceRoleId: number
  newRoleName: string
  newRoleCode: string
}

/**
 * 分页查询角色列表
 */
export function getRolePage(params: RoleQueryParams): Promise<ApiResponse<PageResponse<RoleInfo>>> {
  return request.get('/system/role/page', params)
}

/**
 * 根据ID查询角色详情
 */
export function getRoleById(id: number): Promise<ApiResponse<RoleInfo>> {
  return request.get(`/system/role/${id}`)
}

/**
 * 创建角色
 */
export function createRole(data: RoleCreateParams): Promise<ApiResponse<null>> {
  return request.post('/system/role', data)
}

/**
 * 更新角色
 */
export function updateRole(data: RoleUpdateParams): Promise<ApiResponse<null>> {
  return request.put('/system/role', data)
}

/**
 * 删除角色
 */
export function deleteRole(id: number): Promise<ApiResponse<null>> {
  return request.delete(`/system/role/${id}`)
}

/**
 * 批量删除角色
 */
export function batchDeleteRoles(ids: number[]): Promise<ApiResponse<null>> {
  return request.delete('/system/role/batch', { ids })
}

/**
 * 更新角色状态
 */
export function updateRoleStatus(id: number, status: number): Promise<ApiResponse<null>> {
  return request.put(`/system/role/${id}/status`, null, {
    params: { status }
  })
}

/**
 * 更新角色排序
 */
export function updateRoleSort(id: number, sort: number): Promise<ApiResponse<null>> {
  return request.put(`/system/role/${id}/sort`, null, {
    params: { sort }
  })
}

/**
 * 分配角色菜单权限
 */
export function assignRoleMenus(data: MenuAssignParams): Promise<ApiResponse<null>> {
  return request.post(`/system/role/${data.roleId}/menus`, data.menuIds)
}

/**
 * 分配角色数据权限
 */
export function assignRoleDepts(data: DeptAssignParams): Promise<ApiResponse<null>> {
  return request.post(`/system/role/${data.roleId}/depts`, data.deptIds)
}

/**
 * 获取角色菜单ID列表
 */
export function getRoleMenus(roleId: number): Promise<ApiResponse<number[]>> {
  return request.get(`/system/role/${roleId}/menus`)
}

/**
 * 获取角色部门ID列表
 */
export function getRoleDepts(roleId: number): Promise<ApiResponse<number[]>> {
  return request.get(`/system/role/${roleId}/depts`)
}

/**
 * 根据用户ID查询角色列表
 */
export function getRolesByUserId(userId: number): Promise<ApiResponse<RoleInfo[]>> {
  return request.get(`/system/role/user/${userId}`)
}

/**
 * 根据菜单ID查询角色列表
 */
export function getRolesByMenuId(menuId: number): Promise<ApiResponse<RoleInfo[]>> {
  return request.get(`/system/role/menu/${menuId}`)
}

/**
 * 根据部门ID查询角色列表
 */
export function getRolesByDeptId(deptId: number): Promise<ApiResponse<RoleInfo[]>> {
  return request.get(`/system/role/dept/${deptId}`)
}

/**
 * 获取启用的角色列表
 */
export function getEnabledRoles(): Promise<ApiResponse<RoleInfo[]>> {
  return request.get('/system/role/enabled')
}

/**
 * 获取系统内置角色列表
 */
export function getSystemRoles(): Promise<ApiResponse<RoleInfo[]>> {
  return request.get('/system/role/system')
}

/**
 * 获取业务角色列表
 */
export function getBusinessRoles(): Promise<ApiResponse<RoleInfo[]>> {
  return request.get('/system/role/business')
}

/**
 * 检查角色编码是否唯一
 */
export function checkRoleCodeUnique(roleCode: string, excludeId?: number): Promise<ApiResponse<boolean>> {
  const params: any = { roleCode }
  if (excludeId) {
    params.excludeId = excludeId
  }
  return request.get('/system/role/check-code', params)
}

/**
 * 检查角色名称是否唯一
 */
export function checkRoleNameUnique(roleName: string, excludeId?: number): Promise<ApiResponse<boolean>> {
  const params: any = { roleName }
  if (excludeId) {
    params.excludeId = excludeId
  }
  return request.get('/system/role/check-name', params)
}

/**
 * 获取角色统计信息
 */
export function getRoleStats(): Promise<ApiResponse<{
  totalRoles: number
  enabledRoles: number
  disabledRoles: number
  systemRoles: number
  businessRoles: number
  builtinRoles: number
  customRoles: number
}>> {
  return request.get('/system/role/stats')
}

/**
 * 获取角色用户统计
 */
export function getRoleUserStats(): Promise<ApiResponse<Array<{
  roleName: string
  userCount: number
}>>> {
  return request.get('/system/role/user-stats')
}

/**
 * 复制角色
 */
export function copyRole(data: RoleCopyParams): Promise<ApiResponse<null>> {
  return request.post(`/system/role/${data.sourceRoleId}/copy`, null, {
    params: { newRoleName: data.newRoleName, newRoleCode: data.newRoleCode }
  })
}