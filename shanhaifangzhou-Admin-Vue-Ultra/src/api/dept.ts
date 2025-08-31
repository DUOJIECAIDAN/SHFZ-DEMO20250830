import { request } from '@/utils/request'

// 部门信息接口
export interface DeptInfo {
  id: number
  parentId?: number
  deptName: string
  deptCode: string
  deptType: number
  status: number
  visible: number
  sort: number
  leaderId?: number
  leaderName?: string
  leaderPhone?: string
  leaderEmail?: string
  phone?: string
  email?: string
  address?: string
  description?: string
  isSystem: number
  isDeletable: number
  isEditable: number
  deptIcon?: string
  deptColor?: string
  extData?: string
  createTime: string
  updateTime: string
  createBy?: string
  updateBy?: string
  remark?: string
  children?: DeptInfo[]
  parentName?: string
  roleIds?: number[]
  roleNames?: string[]
  userCount?: number
  checked?: boolean
  indeterminate?: boolean
}

// 部门查询参数
export interface DeptQueryParams {
  current: number
  size: number
  deptName?: string
  deptCode?: string
  deptType?: number
  status?: number
  visible?: number
  parentId?: number
  leaderName?: string
  leaderPhone?: string
  leaderEmail?: string
  phone?: string
  email?: string
  isSystem?: number
  startTime?: string
  endTime?: string
}

// 部门创建参数
export interface DeptCreateParams {
  parentId?: number
  deptName: string
  deptCode: string
  deptType: number
  status?: number
  visible?: number
  sort?: number
  leaderId?: number
  leaderName?: string
  leaderPhone?: string
  leaderEmail?: string
  phone?: string
  email?: string
  address?: string
  description?: string
  deptIcon?: string
  deptColor?: string
  remark?: string
}

// 部门更新参数
export interface DeptUpdateParams {
  id: number
  parentId?: number
  deptName: string
  deptCode: string
  deptType: number
  status?: number
  visible?: number
  sort?: number
  leaderId?: number
  leaderName?: string
  leaderPhone?: string
  leaderEmail?: string
  phone?: string
  email?: string
  address?: string
  description?: string
  deptIcon?: string
  deptColor?: string
  remark?: string
}

// 部门状态参数
export interface DeptStatusParams {
  id: number
  status: number
}

// 部门显示状态参数
export interface DeptVisibleParams {
  id: number
  visible: number
}

// 部门排序参数
export interface DeptSortParams {
  id: number
  sort: number
}

// 部门复制参数
export interface DeptCopyParams {
  sourceDeptId: number
  newDeptName: string
  newParentId?: number
}

// 部门移动参数
export interface DeptMoveParams {
  deptId: number
  newParentId: number
}

// 部门展开参数
export interface DeptToggleParams {
  deptId: number
  expanded: boolean
}

// 分页查询部门列表
export function getDeptPage(params: DeptQueryParams) {
  return request<PageResult<DeptInfo>>({
    url: '/api/system/dept/page',
    method: 'get',
    params
  })
}

// 查询所有部门列表
export function getAllDepts() {
  return request<DeptInfo[]>({
    url: '/api/system/dept/list',
    method: 'get'
  })
}

// 查询部门树结构
export function getDeptTree() {
  return request<DeptInfo[]>({
    url: '/api/system/dept/tree',
    method: 'get'
  })
}

// 根据ID查询部门详情
export function getDeptById(id: number) {
  return request<DeptInfo>({
    url: `/api/system/dept/${id}`,
    method: 'get'
  })
}

// 创建部门
export function createDept(data: DeptCreateParams) {
  return request<ApiResult>({
    url: '/api/system/dept',
    method: 'post',
    data
  })
}

// 更新部门
export function updateDept(data: DeptUpdateParams) {
  return request<ApiResult>({
    url: '/api/system/dept',
    method: 'put',
    data
  })
}

// 删除部门
export function deleteDept(id: number) {
  return request<ApiResult>({
    url: `/api/system/dept/${id}`,
    method: 'delete'
  })
}

// 批量删除部门
export function batchDeleteDepts(ids: number[]) {
  return request<ApiResult>({
    url: '/api/system/dept/batch',
    method: 'delete',
    data: ids
  })
}

// 更新部门状态
export function updateDeptStatus(id: number, status: number) {
  return request<ApiResult>({
    url: `/api/system/dept/${id}/status`,
    method: 'put',
    params: { status }
  })
}

// 更新部门显示状态
export function updateDeptVisible(id: number, visible: number) {
  return request<ApiResult>({
    url: `/api/system/dept/${id}/visible`,
    method: 'put',
    params: { visible }
  })
}

// 更新部门排序
export function updateDeptSort(id: number, sort: number) {
  return request<ApiResult>({
    url: `/api/system/dept/${id}/sort`,
    method: 'put',
    params: { sort }
  })
}

// 根据父部门ID查询子部门列表
export function getDeptsByParentId(parentId: number) {
  return request<DeptInfo[]>({
    url: `/api/system/dept/parent/${parentId}`,
    method: 'get'
  })
}

// 根据角色ID查询部门列表
export function getDeptsByRoleId(roleId: number) {
  return request<DeptInfo[]>({
    url: `/api/system/dept/role/${roleId}`,
    method: 'get'
  })
}

// 根据用户ID查询部门列表
export function getDeptsByUserId(userId: number) {
  return request<DeptInfo[]>({
    url: `/api/system/dept/user/${userId}`,
    method: 'get'
  })
}

// 根据部门编码查询部门
export function getDeptByCode(deptCode: string) {
  return request<DeptInfo>({
    url: `/api/system/dept/code/${deptCode}`,
    method: 'get'
  })
}

// 根据部门名称查询部门
export function getDeptByName(deptName: string) {
  return request<DeptInfo>({
    url: `/api/system/dept/name/${deptName}`,
    method: 'get'
  })
}

// 获取启用的部门列表
export function getEnabledDepts() {
  return request<DeptInfo[]>({
    url: '/api/system/dept/enabled',
    method: 'get'
  })
}

// 获取显示的部门列表
export function getVisibleDepts() {
  return request<DeptInfo[]>({
    url: '/api/system/dept/visible',
    method: 'get'
  })
}

// 根据部门类型查询部门列表
export function getDeptsByType(deptType: number) {
  return request<DeptInfo[]>({
    url: `/api/system/dept/type/${deptType}`,
    method: 'get'
  })
}

// 检查部门编码是否唯一
export function checkDeptCodeUnique(deptCode: string, excludeId?: number) {
  return request<boolean>({
    url: '/api/system/dept/check-code',
    method: 'get',
    params: { deptCode, excludeId }
  })
}

// 检查部门名称是否唯一
export function checkDeptNameUnique(deptName: string, parentId?: number, excludeId?: number) {
  return request<boolean>({
    url: '/api/system/dept/check-name',
    method: 'get',
    params: { deptName, parentId, excludeId }
  })
}

// 获取部门统计信息
export function getDeptStats() {
  return request<Record<string, any>>({
    url: '/api/system/dept/stats',
    method: 'get'
  })
}

// 复制部门
export function copyDept(data: DeptCopyParams) {
  return request<ApiResult>({
    url: `/api/system/dept/${data.sourceDeptId}/copy`,
    method: 'post',
    params: {
      newDeptName: data.newDeptName,
      newParentId: data.newParentId
    }
  })
}

// 移动部门
export function moveDept(data: DeptMoveParams) {
  return request<ApiResult>({
    url: `/api/system/dept/${data.deptId}/move`,
    method: 'put',
    params: { newParentId: data.newParentId }
  })
}

// 展开/折叠部门
export function toggleDeptExpand(data: DeptToggleParams) {
  return request<ApiResult>({
    url: `/api/system/dept/${data.deptId}/toggle`,
    method: 'put',
    params: { expanded: data.expanded }
  })
}

// 获取部门用户数量
export function getDeptUserCount(id: number) {
  return request<number>({
    url: `/api/system/dept/${id}/user-count`,
    method: 'get'
  })
}

// 获取部门角色数量
export function getDeptRoleCount(id: number) {
  return request<number>({
    url: `/api/system/dept/${id}/role-count`,
    method: 'get'
  })
}