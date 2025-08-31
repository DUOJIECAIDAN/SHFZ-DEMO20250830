// 菜单相关API接口
import { request } from '@/utils/request'
import type { Menu, ApiResponse, PageResponse } from '@/types/auth'

/**
 * 获取菜单列表
 */
export function getMenuList(): Promise<ApiResponse<Menu[]>> {
  return request.get('/system/menu/list')
}

/**
 * 获取菜单树形结构
 */
export function getMenuTree(): Promise<ApiResponse<Menu[]>> {
  return request.get('/system/menu/tree')
}

/**
 * 获取菜单分页列表
 */
export function getMenuPage(params: {
  pageNum: number
  pageSize: number
  menuName?: string
  status?: number
}): Promise<ApiResponse<PageResponse<Menu>>> {
  return request.get('/system/menu/page', params)
}

/**
 * 获取菜单详情
 */
export function getMenuById(id: number): Promise<ApiResponse<Menu>> {
  return request.get(`/system/menu/${id}`)
}

/**
 * 新增菜单
 */
export function createMenu(data: Partial<Menu>): Promise<ApiResponse<null>> {
  return request.post('/system/menu', data)
}

/**
 * 更新菜单
 */
export function updateMenu(id: number, data: Partial<Menu>): Promise<ApiResponse<null>> {
  return request.put(`/system/menu/${id}`, data)
}

/**
 * 删除菜单
 */
export function deleteMenu(id: number): Promise<ApiResponse<null>> {
  return request.delete(`/system/menu/${id}`)
}

/**
 * 批量删除菜单
 */
export function batchDeleteMenu(ids: number[]): Promise<ApiResponse<null>> {
  return request.delete('/system/menu/batch', { ids })
}

/**
 * 获取用户菜单权限
 */
export function getUserMenus(): Promise<ApiResponse<Menu[]>> {
  return request.get('/system/menu/user')
}

/**
 * 获取角色菜单权限
 */
export function getRoleMenus(roleId: number): Promise<ApiResponse<number[]>> {
  return request.get(`/system/menu/role/${roleId}`)
}

/**
 * 分配角色菜单权限
 */
export function assignRoleMenus(roleId: number, menuIds: number[]): Promise<ApiResponse<null>> {
  return request.post(`/system/menu/role/${roleId}`, { menuIds })
}

/**
 * 获取菜单权限树
 */
export function getMenuPermissionTree(): Promise<ApiResponse<Menu[]>> {
  return request.get('/system/menu/permission-tree')
}

/**
 * 检查菜单名称是否唯一
 */
export function checkMenuNameUnique(data: {
  id?: number
  menuName: string
  parentId: number
}): Promise<ApiResponse<boolean>> {
  return request.post('/system/menu/check-name', data)
}

/**
 * 获取菜单下拉树列表
 */
export function getMenuSelectTree(): Promise<ApiResponse<Menu[]>> {
  return request.get('/system/menu/select-tree')
}

/**
 * 获取菜单路由
 */
export function getMenuRoutes(): Promise<ApiResponse<Menu[]>> {
  return request.get('/system/menu/routes')
}

/**
 * 更新菜单状态
 */
export function updateMenuStatus(id: number, status: number): Promise<ApiResponse<null>> {
  return request.put(`/system/menu/${id}/status`, { status })
}

/**
 * 更新菜单显示状态
 */
export function updateMenuVisible(id: number, visible: number): Promise<ApiResponse<null>> {
  return request.put(`/system/menu/${id}/visible`, { visible })
}

/**
 * 更新菜单缓存状态
 */
export function updateMenuCache(id: number, isCache: number): Promise<ApiResponse<null>> {
  return request.put(`/system/menu/${id}/cache`, { isCache })
}

/**
 * 获取菜单图标列表
 */
export function getMenuIcons(): Promise<ApiResponse<string[]>> {
  return request.get('/system/menu/icons')
}

/**
 * 导出菜单数据
 */
export function exportMenu(params: {
  menuName?: string
  status?: number
}): Promise<void> {
  return request.download('/system/menu/export', 'menu.xlsx', params)
}

/**
 * 导入菜单数据
 */
export function importMenu(file: File): Promise<ApiResponse<{
  success: number
  failure: number
  message: string
}>> {
  return request.upload('/system/menu/import', file)
}

/**
 * 获取菜单模板
 */
export function getMenuTemplate(): Promise<void> {
  return request.download('/system/menu/template', 'menu-template.xlsx')
}

/**
 * 获取菜单统计信息
 */
export function getMenuStats(): Promise<ApiResponse<{
  totalMenus: number
  enabledMenus: number
  disabledMenus: number
  directoryMenus: number
  menuMenus: number
  buttonMenus: number
}>> {
  return request.get('/system/menu/stats')
}

/**
 * 获取菜单操作日志
 */
export function getMenuLogs(params: {
  pageNum: number
  pageSize: number
  menuName?: string
  operationType?: string
  startTime?: string
  endTime?: string
}): Promise<ApiResponse<PageResponse<{
  id: number
  menuName: string
  operationType: string
  operationUser: string
  operationTime: string
  operationIp: string
  operationResult: string
}>>> {
  return request.get('/system/menu/logs', params)
}