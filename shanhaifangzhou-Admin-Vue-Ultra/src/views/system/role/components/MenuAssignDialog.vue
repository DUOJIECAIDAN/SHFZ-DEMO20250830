<template>
  <el-dialog
    v-model="dialogVisible"
    title="分配菜单权限"
    width="600px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <div class="menu-assign">
      <div class="role-info">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="角色名称">{{ roleData?.roleName }}</el-descriptions-item>
          <el-descriptions-item label="角色编码">{{ roleData?.roleCode }}</el-descriptions-item>
          <el-descriptions-item label="角色类型">
            <el-tag :type="roleData?.roleType === 0 ? 'primary' : 'success'">
              {{ roleData?.roleType === 0 ? '系统角色' : '业务角色' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="角色状态">
            <el-tag :type="roleData?.status === 1 ? 'success' : 'danger'">
              {{ roleData?.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="menu-tree">
        <div class="tree-header">
          <span class="title">菜单权限</span>
          <div class="actions">
            <el-button size="small" @click="handleExpandAll">展开全部</el-button>
            <el-button size="small" @click="handleCollapseAll">折叠全部</el-button>
            <el-button size="small" @click="handleSelectAll">全选</el-button>
            <el-button size="small" @click="handleUnselectAll">取消全选</el-button>
          </div>
        </div>
        
        <el-tree
          ref="treeRef"
          :data="menuTreeData"
          :props="treeProps"
          :default-checked-keys="checkedMenuIds"
          :default-expanded-keys="expandedMenuIds"
          show-checkbox
          node-key="id"
          class="menu-tree-content"
        />
      </div>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" :loading="loading" @click="handleSubmit">
          {{ loading ? '提交中...' : '确定' }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch, nextTick } from 'vue'
import { ElMessage, ElDialog, ElDescriptions, ElDescriptionsItem, ElTag, ElButton, ElTree } from 'element-plus'
import { getRoleMenus, assignRoleMenus } from '@/api/role'
import type { RoleInfo } from '@/api/role'

// Props
interface Props {
  modelValue: boolean
  roleData?: RoleInfo | null
}

const props = withDefaults(defineProps<Props>(), {
  roleData: null
})

// Emits
const emit = defineEmits<{
  'update:modelValue': [value: boolean]
  'success': []
}>()

// 响应式数据
const treeRef = ref()
const loading = ref(false)
const checkedMenuIds = ref<number[]>([])
const expandedMenuIds = ref<number[]>([])

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const roleData = computed(() => props.roleData)

// 菜单树数据（模拟数据，实际应该从API获取）
const menuTreeData = ref([
  {
    id: 1,
    label: '系统管理',
    children: [
      {
        id: 2,
        label: '用户管理',
        children: [
          { id: 3, label: '用户列表' },
          { id: 4, label: '新增用户' },
          { id: 5, label: '编辑用户' },
          { id: 6, label: '删除用户' }
        ]
      },
      {
        id: 7,
        label: '角色管理',
        children: [
          { id: 8, label: '角色列表' },
          { id: 9, label: '新增角色' },
          { id: 10, label: '编辑角色' },
          { id: 11, label: '删除角色' }
        ]
      },
      {
        id: 12,
        label: '菜单管理',
        children: [
          { id: 13, label: '菜单列表' },
          { id: 14, label: '新增菜单' },
          { id: 15, label: '编辑菜单' },
          { id: 16, label: '删除菜单' }
        ]
      }
    ]
  },
  {
    id: 17,
    label: '系统监控',
    children: [
      { id: 18, label: '在线用户' },
      { id: 19, label: '系统日志' },
      { id: 20, label: '服务监控' }
    ]
  }
])

// 树形组件属性配置
const treeProps = {
  children: 'children',
  label: 'label'
}

// 监听角色数据变化
watch(() => props.roleData, async (newVal) => {
  if (newVal) {
    await loadRoleMenus(newVal.id)
  } else {
    checkedMenuIds.value = []
    expandedMenuIds.value = []
  }
}, { immediate: true })

// 方法
const loadRoleMenus = async (roleId: number) => {
  try {
    const response = await getRoleMenus(roleId)
    if (response.code === 200) {
      checkedMenuIds.value = response.data || []
      // 设置展开的节点（包含所有父节点）
      expandedMenuIds.value = getParentIds(checkedMenuIds.value)
    }
  } catch (error) {
    console.error('加载角色菜单权限失败:', error)
    ElMessage.error('加载角色菜单权限失败')
  }
}

const getParentIds = (menuIds: number[]) => {
  const parentIds = new Set<number>()
  
  const findParents = (nodes: any[], targetIds: number[]) => {
    nodes.forEach(node => {
      if (node.children && node.children.length > 0) {
        if (node.children.some((child: any) => targetIds.includes(child.id) || parentIds.has(child.id))) {
          parentIds.add(node.id)
        }
        findParents(node.children, targetIds)
      }
    })
  }
  
  findParents(menuTreeData.value, menuIds)
  return Array.from(parentIds)
}

const handleExpandAll = () => {
  const allIds = getAllMenuIds(menuTreeData.value)
  expandedMenuIds.value = allIds
}

const handleCollapseAll = () => {
  expandedMenuIds.value = []
}

const handleSelectAll = () => {
  const allIds = getAllMenuIds(menuTreeData.value)
  checkedMenuIds.value = allIds
}

const handleUnselectAll = () => {
  checkedMenuIds.value = []
}

const getAllMenuIds = (nodes: any[]): number[] => {
  const ids: number[] = []
  nodes.forEach(node => {
    ids.push(node.id)
    if (node.children && node.children.length > 0) {
      ids.push(...getAllMenuIds(node.children))
    }
  })
  return ids
}

const handleClose = () => {
  dialogVisible.value = false
}

const handleSubmit = async () => {
  if (!roleData.value?.id) {
    ElMessage.error('角色信息不完整')
    return
  }

  try {
    loading.value = true
    
    // 获取选中的菜单ID
    const checkedKeys = treeRef.value?.getCheckedKeys() || []
    const halfCheckedKeys = treeRef.value?.getHalfCheckedKeys() || []
    const allMenuIds = [...checkedKeys, ...halfCheckedKeys]

    const response = await assignRoleMenus({
      roleId: roleData.value.id,
      menuIds: allMenuIds
    })

    if (response.code === 200) {
      ElMessage.success('菜单权限分配成功')
      emit('success')
    }
  } catch (error) {
    console.error('分配菜单权限失败:', error)
    ElMessage.error('分配菜单权限失败')
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.menu-assign {
  .role-info {
    margin-bottom: 20px;
  }

  .menu-tree {
    .tree-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
      margin-bottom: 15px;
      padding: 10px;
      background-color: var(--el-fill-color-light);
      border-radius: 4px;

      .title {
        font-weight: 600;
        color: var(--el-text-color-primary);
      }

      .actions {
        .el-button {
          margin-left: 8px;
        }
      }
    }

    .menu-tree-content {
      max-height: 400px;
      overflow-y: auto;
      border: 1px solid var(--el-border-color);
      border-radius: 4px;
      padding: 10px;
    }
  }
}

.dialog-footer {
  text-align: right;
}
</style>