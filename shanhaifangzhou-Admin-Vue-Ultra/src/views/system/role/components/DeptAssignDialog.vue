<template>
  <el-dialog
    v-model="dialogVisible"
    title="分配数据权限"
    width="600px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <div class="dept-assign">
      <div class="role-info">
        <el-descriptions :column="2" border>
          <el-descriptions-item label="角色名称">{{ roleData?.roleName }}</el-descriptions-item>
          <el-descriptions-item label="角色编码">{{ roleData?.roleCode }}</el-descriptions-item>
          <el-descriptions-item label="数据范围">
            <el-tag :type="getDataScopeType(roleData?.dataScope)">
              {{ getDataScopeText(roleData?.dataScope) }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="角色状态">
            <el-tag :type="roleData?.status === 1 ? 'success' : 'danger'">
              {{ roleData?.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="dept-tree">
        <div class="tree-header">
          <span class="title">部门权限</span>
          <div class="actions">
            <el-button size="small" @click="handleExpandAll">展开全部</el-button>
            <el-button size="small" @click="handleCollapseAll">折叠全部</el-button>
            <el-button size="small" @click="handleSelectAll">全选</el-button>
            <el-button size="small" @click="handleUnselectAll">取消全选</el-button>
          </div>
        </div>
        
        <el-tree
          ref="treeRef"
          :data="deptTreeData"
          :props="treeProps"
          :default-checked-keys="checkedDeptIds"
          :default-expanded-keys="expandedDeptIds"
          show-checkbox
          node-key="id"
          class="dept-tree-content"
        />
      </div>

      <div class="data-scope-tip">
        <el-alert
          title="数据权限说明"
          type="info"
          :closable="false"
          show-icon
        >
          <template #default>
            <div class="tip-content">
              <p><strong>全部数据权限：</strong>可以查看所有数据</p>
              <p><strong>自定义数据权限：</strong>可以查看指定部门的数据</p>
              <p><strong>本部门数据权限：</strong>只能查看本部门的数据</p>
              <p><strong>本部门及以下数据权限：</strong>可以查看本部门及下级部门的数据</p>
              <p><strong>仅本人数据权限：</strong>只能查看自己的数据</p>
            </div>
          </template>
        </el-alert>
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
import { ref, reactive, computed, watch } from 'vue'
import { ElMessage, ElDialog, ElDescriptions, ElDescriptionsItem, ElTag, ElButton, ElTree, ElAlert } from 'element-plus'
import { getRoleDepts, assignRoleDepts } from '@/api/role'
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
const checkedDeptIds = ref<number[]>([])
const expandedDeptIds = ref<number[]>([])

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const roleData = computed(() => props.roleData)

// 部门树数据（模拟数据，实际应该从API获取）
const deptTreeData = ref([
  {
    id: 1,
    label: '总公司',
    children: [
      {
        id: 2,
        label: '技术部',
        children: [
          { id: 5, label: '前端组' },
          { id: 6, label: '后端组' },
          { id: 7, label: '测试组' }
        ]
      },
      {
        id: 3,
        label: '市场部',
        children: [
          { id: 8, label: '销售组' },
          { id: 9, label: '推广组' }
        ]
      },
      {
        id: 4,
        label: '人事部',
        children: [
          { id: 10, label: '招聘组' },
          { id: 11, label: '培训组' }
        ]
      }
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
    await loadRoleDepts(newVal.id)
  } else {
    checkedDeptIds.value = []
    expandedDeptIds.value = []
  }
}, { immediate: true })

// 方法
const loadRoleDepts = async (roleId: number) => {
  try {
    const response = await getRoleDepts(roleId)
    if (response.code === 200) {
      checkedDeptIds.value = response.data || []
      // 设置展开的节点（包含所有父节点）
      expandedDeptIds.value = getParentIds(checkedDeptIds.value)
    }
  } catch (error) {
    console.error('加载角色部门权限失败:', error)
    ElMessage.error('加载角色部门权限失败')
  }
}

const getParentIds = (deptIds: number[]) => {
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
  
  findParents(deptTreeData.value, deptIds)
  return Array.from(parentIds)
}

const handleExpandAll = () => {
  const allIds = getAllDeptIds(deptTreeData.value)
  expandedDeptIds.value = allIds
}

const handleCollapseAll = () => {
  expandedDeptIds.value = []
}

const handleSelectAll = () => {
  const allIds = getAllDeptIds(deptTreeData.value)
  checkedDeptIds.value = allIds
}

const handleUnselectAll = () => {
  checkedDeptIds.value = []
}

const getAllDeptIds = (nodes: any[]): number[] => {
  const ids: number[] = []
  nodes.forEach(node => {
    ids.push(node.id)
    if (node.children && node.children.length > 0) {
      ids.push(...getAllDeptIds(node.children))
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
    
    // 获取选中的部门ID
    const checkedKeys = treeRef.value?.getCheckedKeys() || []
    const halfCheckedKeys = treeRef.value?.getHalfCheckedKeys() || []
    const allDeptIds = [...checkedKeys, ...halfCheckedKeys]

    const response = await assignRoleDepts({
      roleId: roleData.value.id,
      deptIds: allDeptIds
    })

    if (response.code === 200) {
      ElMessage.success('数据权限分配成功')
      emit('success')
    }
  } catch (error) {
    console.error('分配数据权限失败:', error)
    ElMessage.error('分配数据权限失败')
  } finally {
    loading.value = false
  }
}

// 工具方法
const getDataScopeType = (dataScope?: number) => {
  const types = ['', 'primary', 'success', 'warning', 'info', 'danger']
  return types[dataScope || 5] || 'info'
}

const getDataScopeText = (dataScope?: number) => {
  const texts = ['', '全部数据权限', '自定义数据权限', '本部门数据权限', '本部门及以下数据权限', '仅本人数据权限']
  return texts[dataScope || 5] || '未知'
}
</script>

<style lang="scss" scoped>
.dept-assign {
  .role-info {
    margin-bottom: 20px;
  }

  .dept-tree {
    margin-bottom: 20px;

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

    .dept-tree-content {
      max-height: 300px;
      overflow-y: auto;
      border: 1px solid var(--el-border-color);
      border-radius: 4px;
      padding: 10px;
    }
  }

  .data-scope-tip {
    .tip-content {
      p {
        margin: 5px 0;
        line-height: 1.5;
      }
    }
  }
}

.dialog-footer {
  text-align: right;
}
</style>