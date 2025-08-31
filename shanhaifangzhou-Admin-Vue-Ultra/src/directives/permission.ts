import type { Directive, DirectiveBinding } from 'vue'
import { useUserStore } from '@/store/user'

/**
 * 权限指令
 * 使用方式：
 * v-permission="'system:user:add'"  // 单个权限
 * v-permission="['system:user:add', 'system:user:edit']"  // 多个权限（或关系）
 * v-permission.all="['system:user:add', 'system:user:edit']"  // 多个权限（与关系）
 */
export const permission: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { value, modifiers } = binding
    
    if (!value) {
      return
    }

    const userStore = useUserStore()
    const permissions = userStore.permissions || []
    
    let hasPermission = false
    
    if (Array.isArray(value)) {
      // 数组形式
      if (modifiers.all) {
        // 与关系：所有权限都必须有
        hasPermission = value.every(permission => permissions.includes(permission))
      } else {
        // 或关系：任一权限有即可
        hasPermission = value.some(permission => permissions.includes(permission))
      }
    } else {
      // 字符串形式
      hasPermission = permissions.includes(value)
    }
    
    if (!hasPermission) {
      // 没有权限，隐藏元素
      el.style.display = 'none'
      // 或者移除元素
      // el.remove()
    }
  },
  
  updated(el: HTMLElement, binding: DirectiveBinding) {
    const { value, modifiers } = binding
    
    if (!value) {
      el.style.display = ''
      return
    }

    const userStore = useUserStore()
    const permissions = userStore.permissions || []
    
    let hasPermission = false
    
    if (Array.isArray(value)) {
      if (modifiers.all) {
        hasPermission = value.every(permission => permissions.includes(permission))
      } else {
        hasPermission = value.some(permission => permissions.includes(permission))
      }
    } else {
      hasPermission = permissions.includes(value)
    }
    
    if (hasPermission) {
      el.style.display = ''
    } else {
      el.style.display = 'none'
    }
  }
}

/**
 * 角色指令
 * 使用方式：
 * v-role="'admin'"  // 单个角色
 * v-role="['admin', 'manager']"  // 多个角色（或关系）
 * v-role.all="['admin', 'manager']"  // 多个角色（与关系）
 */
export const role: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { value, modifiers } = binding
    
    if (!value) {
      return
    }

    const userStore = useUserStore()
    const roles = userStore.roles || []
    
    let hasRole = false
    
    if (Array.isArray(value)) {
      if (modifiers.all) {
        hasRole = value.every(role => roles.includes(role))
      } else {
        hasRole = value.some(role => roles.includes(role))
      }
    } else {
      hasRole = roles.includes(value)
    }
    
    if (!hasRole) {
      el.style.display = 'none'
    }
  },
  
  updated(el: HTMLElement, binding: DirectiveBinding) {
    const { value, modifiers } = binding
    
    if (!value) {
      el.style.display = ''
      return
    }

    const userStore = useUserStore()
    const roles = userStore.roles || []
    
    let hasRole = false
    
    if (Array.isArray(value)) {
      if (modifiers.all) {
        hasRole = value.every(role => roles.includes(role))
      } else {
        hasRole = value.some(role => roles.includes(role))
      }
    } else {
      hasRole = roles.includes(value)
    }
    
    if (hasRole) {
      el.style.display = ''
    } else {
      el.style.display = 'none'
    }
  }
}

/**
 * 数据权限指令
 * 使用方式：
 * v-data-scope="'dept'"  // 部门数据权限
 * v-data-scope="'all'"   // 全部数据权限
 * v-data-scope="'self'"  // 仅本人数据权限
 */
export const dataScope: Directive = {
  mounted(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    
    if (!value) {
      return
    }

    const userStore = useUserStore()
    const dataScope = userStore.dataScope || 'self'
    
    // 如果用户的数据权限级别低于要求，则隐藏元素
    if (!checkDataScope(dataScope, value)) {
      el.style.display = 'none'
    }
  },
  
  updated(el: HTMLElement, binding: DirectiveBinding) {
    const { value } = binding
    
    if (!value) {
      el.style.display = ''
      return
    }

    const userStore = useUserStore()
    const dataScope = userStore.dataScope || 'self'
    
    if (checkDataScope(dataScope, value)) {
      el.style.display = ''
    } else {
      el.style.display = 'none'
    }
  }
}

/**
 * 检查数据权限
 * 数据权限级别：all > dept > deptAndChild > dept > self
 */
function checkDataScope(userScope: string, requiredScope: string): boolean {
  const scopeLevels = {
    'all': 5,
    'deptAndChild': 4,
    'dept': 3,
    'dept': 2,
    'self': 1
  }
  
  const userLevel = scopeLevels[userScope as keyof typeof scopeLevels] || 1
  const requiredLevel = scopeLevels[requiredScope as keyof typeof scopeLevels] || 1
  
  return userLevel >= requiredLevel
}

/**
 * 安装权限指令
 */
export function setupPermissionDirectives(app: any) {
  app.directive('permission', permission)
  app.directive('role', role)
  app.directive('data-scope', dataScope)
}