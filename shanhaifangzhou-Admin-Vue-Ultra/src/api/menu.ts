// 菜单相关API接口
import { request } from '@/utils/request'
import type { Menu, ApiResponse, PageResponse } from '@/types/auth'

// 菜单信息接口
export interface MenuInfo {
  id: number
  parentId?: number
  menuName: string
  menuType: string
  path?: string
  component?: string
  query?: string
  isFrame: number
  isCache: number
  status: number
  visible: number
  perms?: string
  icon?: string
  sort: number
  title?: string
  alias?: string
  description?: string
  external: number
  newWindow: number
  affix: number
  hideBreadcrumb: number
  hideTab: number
  hideMenu: number
  hideChildren: number
  hideSearch: number
  hideFooter: number
  hideSetting: number
  hideI18n: number
  hideFullscreen: number
  hideRefresh: number
  hideHelp: number
  hideNotice: number
  hideUserInfo: number
  hideLogout: number
  extData?: string
  createTime: string
  updateTime: string
  createBy?: string
  updateBy?: string
  remark?: string
  children?: MenuInfo[]
  parentName?: string
  roleIds?: number[]
  roleNames?: string[]
  checked?: boolean
  indeterminate?: boolean
}

// 菜单查询参数
export interface MenuQueryParams {
  current: number
  size: number
  menuName?: string
  menuType?: string
  status?: number
  visible?: number
  perms?: string
  parentId?: number
  path?: string
  component?: string
  startTime?: string
  endTime?: string
}

// 菜单创建参数
export interface MenuCreateParams {
  parentId?: number
  menuName: string
  menuType: string
  path?: string
  component?: string
  query?: string
  isFrame?: number
  isCache?: number
  status?: number
  visible?: number
  perms?: string
  icon?: string
  sort?: number
  title?: string
  alias?: string
  description?: string
  external?: number
  newWindow?: number
  affix?: number
  hideBreadcrumb?: number
  hideTab?: number
  hideMenu?: number
  hideChildren?: number
  hideSearch?: number
  hideFooter?: number
  hideSetting?: number
  hideI18n?: number
  hideFullscreen?: number
  hideRefresh?: number
  hideHelp?: number
  hideNotice?: number
  hideUserInfo?: number
  hideLogout?: number
  remark?: string
}

// 菜单更新参数
export interface MenuUpdateParams {
  id: number
  parentId?: number
  menuName: string
  menuType: string
  path?: string
  component?: string
  query?: string
  isFrame?: number
  isCache?: number
  status?: number
  visible?: number
  perms?: string
  icon?: string
  sort?: number
  title?: string
  alias?: string
  description?: string
  external?: number
  newWindow?: number
  affix?: number
  hideBreadcrumb?: number
  hideTab?: number
  hideMenu?: number
  hideChildren?: number
  hideSearch?: number
  hideFooter?: number
  hideSetting?: number
  hideI18n?: number
  hideFullscreen?: number
  hideRefresh?: number
  hideHelp?: number
  hideNotice?: number
  hideUserInfo?: number
  hideLogout?: number
  remark?: string
}

// 菜单状态参数
export interface MenuStatusParams {
  id: number
  status: number
}

// 菜单显示状态参数
export interface MenuVisibleParams {
  id: number
  visible: number
}

// 菜单排序参数
export interface MenuSortParams {
  id: number
  sort: number
}

// 菜单复制参数
export interface MenuCopyParams {
  sourceMenuId: number
  newMenuName: string
  newParentId?: number
}

// 菜单移动参数
export interface MenuMoveParams {
  menuId: number
  newParentId: number
}

// 菜单展开参数
export interface MenuToggleParams {
  menuId: number
  expanded: boolean
}

// 分页查询菜单列表
export function getMenuPage(params: MenuQueryParams) {
  return request<PageResult<MenuInfo>>({
    url: '/api/system/menu/page',
    method: 'get',
    params
  })
}

// 查询所有菜单列表
export function getAllMenus() {
  return request<MenuInfo[]>({
    url: '/api/system/menu/list',
    method: 'get'
  })
}

// 查询菜单树结构
export function getMenuTree() {
  return request<MenuInfo[]>({
    url: '/api/system/menu/tree',
    method: 'get'
  })
}

// 根据ID查询菜单详情
export function getMenuById(id: number) {
  return request<MenuInfo>({
    url: `/api/system/menu/${id}`,
    method: 'get'
  })
}

// 创建菜单
export function createMenu(data: MenuCreateParams) {
  return request<ApiResult>({
    url: '/api/system/menu',
    method: 'post',
    data
  })
}

// 更新菜单
export function updateMenu(data: MenuUpdateParams) {
  return request<ApiResult>({
    url: '/api/system/menu',
    method: 'put',
    data
  })
}

// 删除菜单
export function deleteMenu(id: number) {
  return request<ApiResult>({
    url: `/api/system/menu/${id}`,
    method: 'delete'
  })
}

// 批量删除菜单
export function batchDeleteMenus(ids: number[]) {
  return request<ApiResult>({
    url: '/api/system/menu/batch',
    method: 'delete',
    data: ids
  })
}

// 更新菜单状态
export function updateMenuStatus(id: number, status: number) {
  return request<ApiResult>({
    url: `/api/system/menu/${id}/status`,
    method: 'put',
    params: { status }
  })
}

// 更新菜单显示状态
export function updateMenuVisible(id: number, visible: number) {
  return request<ApiResult>({
    url: `/api/system/menu/${id}/visible`,
    method: 'put',
    params: { visible }
  })
}

// 更新菜单排序
export function updateMenuSort(id: number, sort: number) {
  return request<ApiResult>({
    url: `/api/system/menu/${id}/sort`,
    method: 'put',
    params: { sort }
  })
}

// 根据父菜单ID查询子菜单列表
export function getMenusByParentId(parentId: number) {
  return request<MenuInfo[]>({
    url: `/api/system/menu/parent/${parentId}`,
    method: 'get'
  })
}

// 根据角色ID查询菜单列表
export function getMenusByRoleId(roleId: number) {
  return request<MenuInfo[]>({
    url: `/api/system/menu/role/${roleId}`,
    method: 'get'
  })
}

// 根据用户ID查询菜单列表
export function getMenusByUserId(userId: number) {
  return request<MenuInfo[]>({
    url: `/api/system/menu/user/${userId}`,
    method: 'get'
  })
}

// 根据权限标识查询菜单
export function getMenuByPerms(perms: string) {
  return request<MenuInfo>({
    url: `/api/system/menu/perms/${perms}`,
    method: 'get'
  })
}

// 根据路径查询菜单
export function getMenuByPath(path: string) {
  return request<MenuInfo>({
    url: `/api/system/menu/path/${path}`,
    method: 'get'
  })
}

// 根据组件路径查询菜单
export function getMenuByComponent(component: string) {
  return request<MenuInfo>({
    url: `/api/system/menu/component/${component}`,
    method: 'get'
  })
}

// 获取启用的菜单列表
export function getEnabledMenus() {
  return request<MenuInfo[]>({
    url: '/api/system/menu/enabled',
    method: 'get'
  })
}

// 获取显示的菜单列表
export function getVisibleMenus() {
  return request<MenuInfo[]>({
    url: '/api/system/menu/visible',
    method: 'get'
  })
}

// 根据菜单类型查询菜单列表
export function getMenusByType(menuType: string) {
  return request<MenuInfo[]>({
    url: `/api/system/menu/type/${menuType}`,
    method: 'get'
  })
}

// 检查菜单名称是否唯一
export function checkMenuNameUnique(menuName: string, parentId?: number, excludeId?: number) {
  return request<boolean>({
    url: '/api/system/menu/check-name',
    method: 'get',
    params: { menuName, parentId, excludeId }
  })
}

// 检查权限标识是否唯一
export function checkPermsUnique(perms: string, excludeId?: number) {
  return request<boolean>({
    url: '/api/system/menu/check-perms',
    method: 'get',
    params: { perms, excludeId }
  })
}

// 检查路径是否唯一
export function checkPathUnique(path: string, excludeId?: number) {
  return request<boolean>({
    url: '/api/system/menu/check-path',
    method: 'get',
    params: { path, excludeId }
  })
}

// 检查组件路径是否唯一
export function checkComponentUnique(component: string, excludeId?: number) {
  return request<boolean>({
    url: '/api/system/menu/check-component',
    method: 'get',
    params: { component, excludeId }
  })
}

// 获取菜单统计信息
export function getMenuStats() {
  return request<Record<string, any>>({
    url: '/api/system/menu/stats',
    method: 'get'
  })
}

// 复制菜单
export function copyMenu(data: MenuCopyParams) {
  return request<ApiResult>({
    url: `/api/system/menu/${data.sourceMenuId}/copy`,
    method: 'post',
    params: {
      newMenuName: data.newMenuName,
      newParentId: data.newParentId
    }
  })
}

// 移动菜单
export function moveMenu(data: MenuMoveParams) {
  return request<ApiResult>({
    url: `/api/system/menu/${data.menuId}/move`,
    method: 'put',
    params: { newParentId: data.newParentId }
  })
}

// 展开/折叠菜单
export function toggleMenuExpand(data: MenuToggleParams) {
  return request<ApiResult>({
    url: `/api/system/menu/${data.menuId}/toggle`,
    method: 'put',
    params: { expanded: data.expanded }
  })
}