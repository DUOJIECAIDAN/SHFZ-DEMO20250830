// 认证相关类型定义

// 登录表单
export interface LoginForm {
  username: string
  password: string
  captcha?: string
  rememberMe?: boolean
}

// 登录响应
export interface LoginResponse {
  accessToken: string
  refreshToken: string
  expiresIn: number
  tokenType: string
}

// 用户信息
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
  roles: string[]
  permissions: string[]
  lastLoginTime: string
  lastLoginIp: string
  createTime: string
  updateTime: string
}

// 角色信息
export interface Role {
  id: number
  roleName: string
  roleCode: string
  roleDesc: string
  roleType: number
  status: number
  sort: number
  createTime: string
  updateTime: string
}

// 菜单信息
export interface Menu {
  id: number
  menuName: string
  parentId: number
  sort: number
  path: string
  component: string
  query: string
  isFrame: number
  isCache: number
  menuType: string
  visible: number
  status: number
  perms: string
  icon: string
  children?: Menu[]
}

// 部门信息
export interface Department {
  id: number
  deptName: string
  parentId: number
  ancestors: string
  sort: number
  leader: string
  phone: string
  email: string
  status: number
  createTime: string
  updateTime: string
  children?: Department[]
}

// 权限信息
export interface Permission {
  id: number
  permissionName: string
  permissionCode: string
  permissionType: string
  parentId: number
  path: string
  component: string
  icon: string
  sort: number
  status: number
  children?: Permission[]
}

// 用户状态
export interface UserState {
  token: string
  userInfo: UserInfo | null
  roles: string[]
  permissions: string[]
}

// 权限状态
export interface PermissionState {
  routes: any[]
  addRoutes: any[]
  menuList: Menu[]
}

// 登录请求参数
export interface LoginParams {
  username: string
  password: string
  captcha?: string
  rememberMe?: boolean
}

// 刷新令牌请求参数
export interface RefreshTokenParams {
  refreshToken: string
}

// 修改密码请求参数
export interface ChangePasswordParams {
  oldPassword: string
  newPassword: string
  confirmPassword: string
}

// 更新用户信息请求参数
export interface UpdateUserInfoParams {
  nickname?: string
  email?: string
  phone?: string
  avatar?: string
  gender?: number
}

// 用户查询参数
export interface UserQueryParams {
  username?: string
  nickname?: string
  email?: string
  phone?: string
  status?: number
  deptId?: number
  roleId?: number
  pageNum: number
  pageSize: number
}

// 角色查询参数
export interface RoleQueryParams {
  roleName?: string
  roleCode?: string
  status?: number
  pageNum: number
  pageSize: number
}

// 菜单查询参数
export interface MenuQueryParams {
  menuName?: string
  status?: number
  pageNum: number
  pageSize: number
}

// 部门查询参数
export interface DepartmentQueryParams {
  deptName?: string
  status?: number
  pageNum: number
  pageSize: number
}

// 分页响应
export interface PageResponse<T> {
  records: T[]
  total: number
  current: number
  size: number
  pages: number
}

// 通用响应
export interface ApiResponse<T = any> {
  code: number
  message: string
  data: T
  timestamp: number
}

// 错误响应
export interface ErrorResponse {
  code: number
  message: string
  details?: string
  timestamp: number
}