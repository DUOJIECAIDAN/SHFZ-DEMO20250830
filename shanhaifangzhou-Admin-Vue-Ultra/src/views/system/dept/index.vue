<template>
  <div class="dept-management">
    <!-- 搜索表单 -->
    <el-card class="search-card" shadow="never">
      <el-form
        ref="searchFormRef"
        :model="searchForm"
        :inline="true"
        label-width="80px"
        @submit.prevent
      >
        <el-form-item label="部门名称">
          <el-input
            v-model="searchForm.deptName"
            placeholder="请输入部门名称"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="部门编码">
          <el-input
            v-model="searchForm.deptCode"
            placeholder="请输入部门编码"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="部门类型">
          <el-select
            v-model="searchForm.deptType"
            placeholder="请选择部门类型"
            clearable
            style="width: 150px"
          >
            <el-option label="公司" :value="1" />
            <el-option label="部门" :value="2" />
            <el-option label="小组" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            v-model="searchForm.status"
            placeholder="请选择状态"
            clearable
            style="width: 120px"
          >
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <!-- 操作按钮 -->
    <el-card class="action-card" shadow="never">
      <div class="action-buttons">
        <el-button type="primary" @click="handleAdd">
          <el-icon><Plus /></el-icon>
          新增部门
        </el-button>
        <el-button type="danger" :disabled="!selectedIds.length" @click="handleBatchDelete">
          <el-icon><Delete /></el-icon>
          批量删除
        </el-button>
        <el-button @click="handleExpandAll">
          <el-icon><Expand /></el-icon>
          展开全部
        </el-button>
        <el-button @click="handleCollapseAll">
          <el-icon><Fold /></el-icon>
          折叠全部
        </el-button>
      </div>
    </el-card>

    <!-- 部门表格 -->
    <el-card class="table-card" shadow="never">
      <el-table
        ref="tableRef"
        v-loading="loading"
        :data="deptList"
        row-key="id"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="deptName" label="部门名称" min-width="200">
          <template #default="{ row }">
            <el-icon v-if="row.deptIcon" class="dept-icon">
              <component :is="row.deptIcon" />
            </el-icon>
            <span>{{ row.deptName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="deptCode" label="部门编码" width="120" />
        <el-table-column prop="deptType" label="类型" width="80">
          <template #default="{ row }">
            <el-tag :type="getDeptTypeType(row.deptType)">
              {{ getDeptTypeText(row.deptType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="leaderName" label="负责人" width="100" />
        <el-table-column prop="phone" label="联系电话" width="120" />
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-switch
              v-model="row.status"
              :active-value="1"
              :inactive-value="0"
              @change="(value) => handleStatusChange(row.id, value)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="visible" label="显示" width="80">
          <template #default="{ row }">
            <el-switch
              v-model="row.visible"
              :active-value="1"
              :inactive-value="0"
              @change="(value) => handleVisibleChange(row.id, value)"
            />
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatDateTime(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button
              type="primary"
              link
              @click="handleAdd(row)"
            >
              新增
            </el-button>
            <el-button type="primary" link @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button type="success" link @click="handleCopy(row)">
              复制
            </el-button>
            <el-button type="warning" link @click="handleMove(row)">
              移动
            </el-button>
            <el-button
              type="danger"
              link
              :disabled="row.isSystem === 1"
              @click="handleDelete(row)"
            >
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>

    <!-- 部门表单对话框 -->
    <DeptFormDialog
      v-model="deptFormVisible"
      :dept-data="currentDept"
      @success="handleSuccess"
    />

    <!-- 部门复制对话框 -->
    <DeptCopyDialog
      v-model="deptCopyVisible"
      :dept-data="currentDept"
      @success="handleSuccess"
    />

    <!-- 部门移动对话框 -->
    <DeptMoveDialog
      v-model="deptMoveVisible"
      :dept-data="currentDept"
      @success="handleSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Delete, Expand, Fold } from '@element-plus/icons-vue'
import { getDeptPage, deleteDept, batchDeleteDepts, updateDeptStatus, updateDeptVisible } from '@/api/dept'
import type { DeptInfo, DeptQueryParams } from '@/api/dept'
import DeptFormDialog from './components/DeptFormDialog.vue'
import DeptCopyDialog from './components/DeptCopyDialog.vue'
import DeptMoveDialog from './components/DeptMoveDialog.vue'
import { formatDateTime } from '@/utils/format'

// 响应式数据
const loading = ref(false)
const deptList = ref<DeptInfo[]>([])
const selectedIds = ref<number[]>([])
const searchFormRef = ref()
const tableRef = ref()

// 对话框控制
const deptFormVisible = ref(false)
const deptCopyVisible = ref(false)
const deptMoveVisible = ref(false)
const currentDept = ref<DeptInfo | null>(null)

// 搜索表单
const searchForm = reactive<DeptQueryParams>({
  current: 1,
  size: 1000,
  deptName: '',
  deptCode: '',
  deptType: undefined,
  status: undefined
})

// 生命周期
onMounted(() => {
  loadDeptList()
})

// 加载部门列表
const loadDeptList = async () => {
  try {
    loading.value = true
    const response = await getDeptPage(searchForm)
    if (response.code === 200) {
      deptList.value = response.data.records || []
    }
  } catch (error) {
    console.error('加载部门列表失败:', error)
    ElMessage.error('加载部门列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  searchForm.current = 1
  loadDeptList()
}

// 重置
const handleReset = () => {
  searchFormRef.value?.resetFields()
  searchForm.current = 1
  loadDeptList()
}

// 选择变化
const handleSelectionChange = (selection: DeptInfo[]) => {
  selectedIds.value = selection.map(item => item.id)
}

// 新增部门
const handleAdd = (parentDept?: DeptInfo) => {
  currentDept.value = parentDept || null
  deptFormVisible.value = true
}

// 编辑部门
const handleEdit = (dept: DeptInfo) => {
  currentDept.value = dept
  deptFormVisible.value = true
}

// 复制部门
const handleCopy = (dept: DeptInfo) => {
  currentDept.value = dept
  deptCopyVisible.value = true
}

// 移动部门
const handleMove = (dept: DeptInfo) => {
  currentDept.value = dept
  deptMoveVisible.value = true
}

// 删除部门
const handleDelete = async (dept: DeptInfo) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除部门"${dept.deptName}"吗？删除后不可恢复！`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await deleteDept(dept.id)
    if (response.code === 200) {
      ElMessage.success('删除成功')
      loadDeptList()
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除部门失败:', error)
      ElMessage.error('删除部门失败')
    }
  }
}

// 批量删除
const handleBatchDelete = async () => {
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择要删除的部门')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedIds.value.length} 个部门吗？删除后不可恢复！`,
      '批量删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await batchDeleteDepts(selectedIds.value)
    if (response.code === 200) {
      ElMessage.success('批量删除成功')
      selectedIds.value = []
      loadDeptList()
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除部门失败:', error)
      ElMessage.error('批量删除部门失败')
    }
  }
}

// 状态变化
const handleStatusChange = async (id: number, status: number) => {
  try {
    const response = await updateDeptStatus(id, status)
    if (response.code === 200) {
      ElMessage.success('状态更新成功')
    } else {
      // 恢复原状态
      const dept = deptList.value.find(item => item.id === id)
      if (dept) {
        dept.status = status === 1 ? 0 : 1
      }
    }
  } catch (error) {
    console.error('更新部门状态失败:', error)
    ElMessage.error('更新部门状态失败')
    // 恢复原状态
    const dept = deptList.value.find(item => item.id === id)
    if (dept) {
      dept.status = status === 1 ? 0 : 1
    }
  }
}

// 显示状态变化
const handleVisibleChange = async (id: number, visible: number) => {
  try {
    const response = await updateDeptVisible(id, visible)
    if (response.code === 200) {
      ElMessage.success('显示状态更新成功')
    } else {
      // 恢复原状态
      const dept = deptList.value.find(item => item.id === id)
      if (dept) {
        dept.visible = visible === 1 ? 0 : 1
      }
    }
  } catch (error) {
    console.error('更新部门显示状态失败:', error)
    ElMessage.error('更新部门显示状态失败')
    // 恢复原状态
    const dept = deptList.value.find(item => item.id === id)
    if (dept) {
      dept.visible = visible === 1 ? 0 : 1
    }
  }
}

// 展开全部
const handleExpandAll = () => {
  nextTick(() => {
    const allIds = getAllDeptIds(deptList.value)
    allIds.forEach(id => {
      tableRef.value?.toggleRowExpansion(
        deptList.value.find(item => item.id === id),
        true
      )
    })
  })
}

// 折叠全部
const handleCollapseAll = () => {
  nextTick(() => {
    const allIds = getAllDeptIds(deptList.value)
    allIds.forEach(id => {
      tableRef.value?.toggleRowExpansion(
        deptList.value.find(item => item.id === id),
        false
      )
    })
  })
}

// 获取所有部门ID
const getAllDeptIds = (depts: DeptInfo[]): number[] => {
  const ids: number[] = []
  depts.forEach(dept => {
    ids.push(dept.id)
    if (dept.children && dept.children.length > 0) {
      ids.push(...getAllDeptIds(dept.children))
    }
  })
  return ids
}

// 操作成功回调
const handleSuccess = () => {
  loadDeptList()
}

// 工具方法
const getDeptTypeType = (type: number) => {
  const types: Record<number, string> = {
    1: 'primary',
    2: 'success',
    3: 'warning'
  }
  return types[type] || 'info'
}

const getDeptTypeText = (type: number) => {
  const texts: Record<number, string> = {
    1: '公司',
    2: '部门',
    3: '小组'
  }
  return texts[type] || '未知'
}
</script>

<style lang="scss" scoped>
.dept-management {
  padding: 20px;

  .search-card {
    margin-bottom: 20px;
  }

  .action-card {
    margin-bottom: 20px;

    .action-buttons {
      display: flex;
      gap: 10px;
    }
  }

  .table-card {
    .dept-icon {
      margin-right: 5px;
      vertical-align: middle;
    }
  }
}
</style>