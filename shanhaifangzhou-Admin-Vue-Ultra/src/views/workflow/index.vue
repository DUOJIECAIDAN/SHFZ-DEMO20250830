<template>
  <div class="workflow-management">
    <!-- 搜索表单 -->
    <el-card class="search-card" shadow="never">
      <el-form
        ref="searchFormRef"
        :model="searchForm"
        :inline="true"
        label-width="80px"
        @submit.prevent
      >
        <el-form-item label="工作流名称">
          <el-input
            v-model="searchForm.workflowName"
            placeholder="请输入工作流名称"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="工作流类型">
          <el-select
            v-model="searchForm.workflowType"
            placeholder="请选择工作流类型"
            clearable
            style="width: 150px"
          >
            <el-option label="审批流程" :value="1" />
            <el-option label="业务流程" :value="2" />
            <el-option label="其他流程" :value="3" />
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
          新增工作流
        </el-button>
        <el-button type="success" @click="handleImport">
          <el-icon><Upload /></el-icon>
          导入工作流
        </el-button>
        <el-button type="warning" @click="handleExport">
          <el-icon><Download /></el-icon>
          导出工作流
        </el-button>
        <el-button type="danger" :disabled="!selectedIds.length" @click="handleBatchDelete">
          <el-icon><Delete /></el-icon>
          批量删除
        </el-button>
      </div>
    </el-card>

    <!-- 工作流表格 -->
    <el-card class="table-card" shadow="never">
      <el-table
        ref="tableRef"
        v-loading="loading"
        :data="workflowList"
        row-key="id"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="workflowName" label="工作流名称" min-width="200">
          <template #default="{ row }">
            <div class="workflow-info">
              <el-icon v-if="row.workflowIcon" class="workflow-icon">
                <component :is="row.workflowIcon" />
              </el-icon>
              <span>{{ row.workflowName }}</span>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="workflowCode" label="工作流编码" width="150" />
        <el-table-column prop="workflowType" label="类型" width="100">
          <template #default="{ row }">
            <el-tag :type="getWorkflowTypeType(row.workflowType)">
              {{ getWorkflowTypeText(row.workflowType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="version" label="版本" width="80" />
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
        <el-table-column prop="timeoutHours" label="超时时间" width="100">
          <template #default="{ row }">
            {{ row.timeoutHours }}小时
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="createTime" label="创建时间" width="180">
          <template #default="{ row }">
            {{ formatDateTime(row.createTime) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button type="success" link @click="handleDesign(row)">
              设计
            </el-button>
            <el-button type="warning" link @click="handleCopy(row)">
              复制
            </el-button>
            <el-button type="info" link @click="handleVersion(row)">
              版本
            </el-button>
            <el-button type="danger" link @click="handleDelete(row)">
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="searchForm.current"
          v-model:page-size="searchForm.size"
          :page-sizes="[10, 20, 50, 100]"
          :total="total"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>

    <!-- 工作流表单对话框 -->
    <WorkflowFormDialog
      v-model="workflowFormVisible"
      :workflow-data="currentWorkflow"
      @success="handleSuccess"
    />

    <!-- 工作流设计器对话框 -->
    <WorkflowDesignerDialog
      v-model="workflowDesignerVisible"
      :workflow-data="currentWorkflow"
      @success="handleSuccess"
    />

    <!-- 工作流复制对话框 -->
    <WorkflowCopyDialog
      v-model="workflowCopyVisible"
      :workflow-data="currentWorkflow"
      @success="handleSuccess"
    />

    <!-- 版本管理对话框 -->
    <WorkflowVersionDialog
      v-model="workflowVersionVisible"
      :workflow-data="currentWorkflow"
      @success="handleSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Upload, Download, Delete } from '@element-plus/icons-vue'
import WorkflowFormDialog from './components/WorkflowFormDialog.vue'
import WorkflowDesignerDialog from './components/WorkflowDesignerDialog.vue'
import WorkflowCopyDialog from './components/WorkflowCopyDialog.vue'
import WorkflowVersionDialog from './components/WorkflowVersionDialog.vue'
import { formatDateTime } from '@/utils/format'

// 响应式数据
const loading = ref(false)
const workflowList = ref<any[]>([])
const selectedIds = ref<number[]>([])
const total = ref(0)
const searchFormRef = ref()
const tableRef = ref()

// 对话框控制
const workflowFormVisible = ref(false)
const workflowDesignerVisible = ref(false)
const workflowCopyVisible = ref(false)
const workflowVersionVisible = ref(false)
const currentWorkflow = ref<any>(null)

// 搜索表单
const searchForm = reactive({
  current: 1,
  size: 10,
  workflowName: '',
  workflowType: undefined,
  status: undefined
})

// 生命周期
onMounted(() => {
  loadWorkflowList()
})

// 加载工作流列表
const loadWorkflowList = async () => {
  try {
    loading.value = true
    // TODO: 调用后端API获取工作流列表
    // const response = await getWorkflowPage(searchForm)
    // if (response.code === 200) {
    //   workflowList.value = response.data.records || []
    //   total.value = response.data.total || 0
    // }
    
    // 模拟数据
    workflowList.value = [
      {
        id: 1,
        workflowName: '请假申请流程',
        workflowCode: 'LEAVE_APPROVAL',
        workflowType: 1,
        version: 1,
        status: 1,
        timeoutHours: 24,
        sort: 1,
        createTime: new Date(),
        workflowIcon: 'Calendar'
      },
      {
        id: 2,
        workflowName: '报销申请流程',
        workflowCode: 'EXPENSE_APPROVAL',
        workflowType: 1,
        version: 1,
        status: 1,
        timeoutHours: 48,
        sort: 2,
        createTime: new Date(),
        workflowIcon: 'Money'
      }
    ]
    total.value = workflowList.value.length
  } catch (error) {
    console.error('加载工作流列表失败:', error)
    ElMessage.error('加载工作流列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  searchForm.current = 1
  loadWorkflowList()
}

// 重置
const handleReset = () => {
  searchFormRef.value?.resetFields()
  searchForm.current = 1
  loadWorkflowList()
}

// 选择变化
const handleSelectionChange = (selection: any[]) => {
  selectedIds.value = selection.map(item => item.id)
}

// 新增工作流
const handleAdd = () => {
  currentWorkflow.value = null
  workflowFormVisible.value = true
}

// 编辑工作流
const handleEdit = (workflow: any) => {
  currentWorkflow.value = workflow
  workflowFormVisible.value = true
}

// 设计工作流
const handleDesign = (workflow: any) => {
  currentWorkflow.value = workflow
  workflowDesignerVisible.value = true
}

// 复制工作流
const handleCopy = (workflow: any) => {
  currentWorkflow.value = workflow
  workflowCopyVisible.value = true
}

// 版本管理
const handleVersion = (workflow: any) => {
  currentWorkflow.value = workflow
  workflowVersionVisible.value = true
}

// 删除工作流
const handleDelete = async (workflow: any) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除工作流"${workflow.workflowName}"吗？删除后不可恢复！`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    // TODO: 调用后端API删除工作流
    // const response = await deleteWorkflow(workflow.id)
    // if (response.code === 200) {
    //   ElMessage.success('删除成功')
    //   loadWorkflowList()
    // }
    
    ElMessage.success('删除成功')
    loadWorkflowList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除工作流失败:', error)
      ElMessage.error('删除工作流失败')
    }
  }
}

// 批量删除
const handleBatchDelete = async () => {
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择要删除的工作流')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedIds.value.length} 个工作流吗？删除后不可恢复！`,
      '批量删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    // TODO: 调用后端API批量删除工作流
    // const response = await batchDeleteWorkflows(selectedIds.value)
    // if (response.code === 200) {
    //   ElMessage.success('批量删除成功')
    //   selectedIds.value = []
    //   loadWorkflowList()
    // }
    
    ElMessage.success('批量删除成功')
    selectedIds.value = []
    loadWorkflowList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除工作流失败:', error)
      ElMessage.error('批量删除工作流失败')
    }
  }
}

// 导入工作流
const handleImport = () => {
  ElMessage.info('导入功能开发中...')
}

// 导出工作流
const handleExport = () => {
  ElMessage.info('导出功能开发中...')
}

// 状态变化
const handleStatusChange = async (id: number, status: number) => {
  try {
    // TODO: 调用后端API更新工作流状态
    // const response = await updateWorkflowStatus(id, status)
    // if (response.code === 200) {
    //   ElMessage.success('状态更新成功')
    // } else {
    //   // 恢复原状态
    //   const workflow = workflowList.value.find(item => item.id === id)
    //   if (workflow) {
    //     workflow.status = status === 1 ? 0 : 1
    //   }
    // }
    
    ElMessage.success('状态更新成功')
  } catch (error) {
    console.error('更新工作流状态失败:', error)
    ElMessage.error('更新工作流状态失败')
    // 恢复原状态
    const workflow = workflowList.value.find(item => item.id === id)
    if (workflow) {
      workflow.status = status === 1 ? 0 : 1
    }
  }
}

// 分页大小变化
const handleSizeChange = (size: number) => {
  searchForm.size = size
  searchForm.current = 1
  loadWorkflowList()
}

// 当前页变化
const handleCurrentChange = (current: number) => {
  searchForm.current = current
  loadWorkflowList()
}

// 操作成功回调
const handleSuccess = () => {
  loadWorkflowList()
}

// 工具方法
const getWorkflowTypeType = (type: number) => {
  const types: Record<number, string> = {
    1: 'primary',
    2: 'success',
    3: 'warning'
  }
  return types[type] || 'info'
}

const getWorkflowTypeText = (type: number) => {
  const texts: Record<number, string> = {
    1: '审批流程',
    2: '业务流程',
    3: '其他流程'
  }
  return texts[type] || '未知'
}
</script>

<style lang="scss" scoped>
.workflow-management {
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
    .workflow-info {
      display: flex;
      align-items: center;
      gap: 8px;

      .workflow-icon {
        font-size: 16px;
        color: #409EFF;
      }
    }

    .pagination-wrapper {
      margin-top: 20px;
      display: flex;
      justify-content: flex-end;
    }
  }
}
</style>