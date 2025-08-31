<template>
  <div class="role-management">
    <!-- 搜索表单 -->
    <div class="search-form">
      <el-form :model="queryParams" :inline="true" @submit.prevent="handleSearch">
        <el-form-item label="角色名称">
          <el-input
            v-model="queryParams.roleName"
            placeholder="请输入角色名称"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="角色编码">
          <el-input
            v-model="queryParams.roleCode"
            placeholder="请输入角色编码"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="状态">
          <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 120px">
            <el-option label="启用" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item label="角色类型">
          <el-select v-model="queryParams.roleType" placeholder="请选择类型" clearable style="width: 120px">
            <el-option label="系统角色" :value="0" />
            <el-option label="业务角色" :value="1" />
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
    </div>

    <!-- 操作按钮 -->
    <div class="action-bar">
      <el-button type="primary" @click="handleAdd">
        <el-icon><Plus /></el-icon>
        新增角色
      </el-button>
      <el-button type="danger" :disabled="!selectedIds.length" @click="handleBatchDelete">
        <el-icon><Delete /></el-icon>
        批量删除
      </el-button>
      <el-button @click="handleExport">
        <el-icon><Download /></el-icon>
        导出
      </el-button>
    </div>

    <!-- 角色表格 -->
    <div class="role-table">
      <el-table
        v-loading="loading"
        :data="roleList"
        @selection-change="handleSelectionChange"
        stripe
        border
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="roleName" label="角色名称" width="150" />
        <el-table-column prop="roleCode" label="角色编码" width="150" />
        <el-table-column prop="roleDesc" label="角色描述" width="200" show-overflow-tooltip />
        <el-table-column prop="roleType" label="角色类型" width="100">
          <template #default="{ row }">
            <el-tag :type="row.roleType === 0 ? 'primary' : 'success'">
              {{ row.roleType === 0 ? '系统角色' : '业务角色' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.status === 1 ? 'success' : 'danger'">
              {{ row.status === 1 ? '启用' : '禁用' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="dataScope" label="数据范围" width="120">
          <template #default="{ row }">
            <el-tag :type="getDataScopeType(row.dataScope)">
              {{ getDataScopeText(row.dataScope) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="sort" label="排序" width="80" />
        <el-table-column prop="userCount" label="用户数量" width="100" />
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="250" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button type="success" size="small" @click="handleAssignMenu(row)">
              分配菜单
            </el-button>
            <el-button type="warning" size="small" @click="handleAssignDept(row)">
              分配权限
            </el-button>
            <el-dropdown @command="(command) => handleCommand(command, row)">
              <el-button type="info" size="small">
                更多<el-icon class="el-icon--right"><ArrowDown /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="status" :disabled="row.isSystem === 1">
                    {{ row.status === 1 ? '禁用' : '启用' }}
                  </el-dropdown-item>
                  <el-dropdown-item command="copy">复制角色</el-dropdown-item>
                  <el-dropdown-item command="delete" :disabled="row.isSystem === 1">
                    删除
                  </el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- 分页 -->
    <div class="pagination">
      <el-pagination
        v-model:current-page="queryParams.pageNum"
        v-model:page-size="queryParams.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>

    <!-- 角色表单对话框 -->
    <RoleFormDialog
      v-model="dialogVisible"
      :role-data="currentRole"
      @success="handleFormSuccess"
    />

    <!-- 菜单权限分配对话框 -->
    <MenuAssignDialog
      v-model="menuAssignDialogVisible"
      :role-data="currentRole"
      @success="handleMenuAssignSuccess"
    />

    <!-- 数据权限分配对话框 -->
    <DeptAssignDialog
      v-model="deptAssignDialogVisible"
      :role-data="currentRole"
      @success="handleDeptAssignSuccess"
    />

    <!-- 角色复制对话框 -->
    <RoleCopyDialog
      v-model="copyDialogVisible"
      :role-data="currentRole"
      @success="handleCopySuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, ElTable, ElTableColumn, ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElButton, ElIcon, ElTag, ElDropdown, ElDropdownMenu, ElDropdownItem, ElPagination } from 'element-plus'
import { Search, Refresh, Plus, Delete, Download, ArrowDown } from '@element-plus/icons-vue'
import { getRolePage, deleteRole, batchDeleteRoles, updateRoleStatus, copyRole } from '@/api/role'
import type { RoleInfo, RoleQueryParams } from '@/api/role'
import RoleFormDialog from './components/RoleFormDialog.vue'
import MenuAssignDialog from './components/MenuAssignDialog.vue'
import DeptAssignDialog from './components/DeptAssignDialog.vue'
import RoleCopyDialog from './components/RoleCopyDialog.vue'

// 响应式数据
const loading = ref(false)
const roleList = ref<RoleInfo[]>([])
const total = ref(0)
const selectedIds = ref<number[]>([])
const dialogVisible = ref(false)
const menuAssignDialogVisible = ref(false)
const deptAssignDialogVisible = ref(false)
const copyDialogVisible = ref(false)
const currentRole = ref<RoleInfo | null>(null)

// 查询参数
const queryParams = reactive<RoleQueryParams>({
  pageNum: 1,
  pageSize: 10,
  roleName: '',
  roleCode: '',
  status: undefined,
  roleType: undefined
})

// 方法
const loadRoleList = async () => {
  try {
    loading.value = true
    const response = await getRolePage(queryParams)
    if (response.code === 200) {
      roleList.value = response.data.records
      total.value = response.data.total
    }
  } catch (error) {
    console.error('加载角色列表失败:', error)
    ElMessage.error('加载角色列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  queryParams.pageNum = 1
  loadRoleList()
}

const handleReset = () => {
  Object.assign(queryParams, {
    pageNum: 1,
    pageSize: 10,
    roleName: '',
    roleCode: '',
    status: undefined,
    roleType: undefined
  })
  loadRoleList()
}

const handleSizeChange = (size: number) => {
  queryParams.pageSize = size
  loadRoleList()
}

const handleCurrentChange = (page: number) => {
  queryParams.pageNum = page
  loadRoleList()
}

const handleSelectionChange = (selection: RoleInfo[]) => {
  selectedIds.value = selection.map(item => item.id)
}

const handleAdd = () => {
  currentRole.value = null
  dialogVisible.value = true
}

const handleEdit = (row: RoleInfo) => {
  currentRole.value = { ...row }
  dialogVisible.value = true
}

const handleAssignMenu = (row: RoleInfo) => {
  currentRole.value = row
  menuAssignDialogVisible.value = true
}

const handleAssignDept = (row: RoleInfo) => {
  currentRole.value = row
  deptAssignDialogVisible.value = true
}

const handleBatchDelete = async () => {
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择要删除的角色')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedIds.value.length} 个角色吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await batchDeleteRoles(selectedIds.value)
    if (response.code === 200) {
      ElMessage.success('批量删除成功')
      selectedIds.value = []
      loadRoleList()
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除失败:', error)
      ElMessage.error('批量删除失败')
    }
  }
}

const handleExport = () => {
  ElMessage.info('导出功能待实现')
}

const handleCommand = async (command: string, row: RoleInfo) => {
  try {
    switch (command) {
      case 'status':
        const newStatus = row.status === 1 ? 0 : 1
        const statusResponse = await updateRoleStatus(row.id, newStatus)
        if (statusResponse.code === 200) {
          ElMessage.success(`角色${newStatus === 1 ? '启用' : '禁用'}成功`)
          loadRoleList()
        }
        break
      case 'copy':
        currentRole.value = row
        copyDialogVisible.value = true
        break
      case 'delete':
        await ElMessageBox.confirm(
          `确定要删除角色 "${row.roleName}" 吗？`,
          '确认删除',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
        )
        const deleteResponse = await deleteRole(row.id)
        if (deleteResponse.code === 200) {
          ElMessage.success('角色删除成功')
          loadRoleList()
        }
        break
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('操作失败:', error)
      ElMessage.error('操作失败')
    }
  }
}

const handleFormSuccess = () => {
  dialogVisible.value = false
  loadRoleList()
}

const handleMenuAssignSuccess = () => {
  menuAssignDialogVisible.value = false
  ElMessage.success('菜单权限分配成功')
}

const handleDeptAssignSuccess = () => {
  deptAssignDialogVisible.value = false
  ElMessage.success('数据权限分配成功')
}

const handleCopySuccess = () => {
  copyDialogVisible.value = false
  loadRoleList()
}

// 工具方法
const getDataScopeType = (dataScope: number) => {
  const types = ['', 'primary', 'success', 'warning', 'info', 'danger']
  return types[dataScope] || 'info'
}

const getDataScopeText = (dataScope: number) => {
  const texts = ['', '全部数据权限', '自定义数据权限', '本部门数据权限', '本部门及以下数据权限', '仅本人数据权限']
  return texts[dataScope] || '未知'
}

// 生命周期
onMounted(() => {
  loadRoleList()
})
</script>

<style lang="scss" scoped>
.role-management {
  padding: 20px;
  background-color: var(--bg-color-page);
  min-height: calc(100vh - 84px);
}

.search-form {
  background: var(--bg-color);
  padding: 20px;
  border-radius: 8px;
  box-shadow: var(--box-shadow-light);
  margin-bottom: 20px;
}

.action-bar {
  margin-bottom: 20px;
  
  .el-button {
    margin-right: 10px;
  }
}

.role-table {
  background: var(--bg-color);
  border-radius: 8px;
  box-shadow: var(--box-shadow-light);
  margin-bottom: 20px;
  overflow: hidden;
}

.pagination {
  display: flex;
  justify-content: center;
  background: var(--bg-color);
  padding: 20px;
  border-radius: 8px;
  box-shadow: var(--box-shadow-light);
}

// 响应式设计
@media (max-width: 768px) {
  .role-management {
    padding: 10px;
  }
  
  .search-form {
    padding: 15px;
  }
  
  .action-bar {
    .el-button {
      margin-bottom: 10px;
      margin-right: 0;
      width: 100%;
    }
  }
}
</style>