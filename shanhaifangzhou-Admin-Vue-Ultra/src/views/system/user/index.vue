<template>
  <div class="user-management">
    <!-- 搜索表单 -->
    <div class="search-form">
      <el-form :model="queryParams" :inline="true" @submit.prevent="handleSearch">
        <el-form-item label="用户名">
          <el-input
            v-model="queryParams.username"
            placeholder="请输入用户名"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input
            v-model="queryParams.nickname"
            placeholder="请输入昵称"
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
        <el-form-item label="部门">
          <el-tree-select
            v-model="queryParams.deptId"
            :data="deptTreeData"
            placeholder="请选择部门"
            clearable
            style="width: 200px"
          />
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
        新增用户
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

    <!-- 用户表格 -->
    <div class="user-table">
      <el-table
        v-loading="loading"
        :data="userList"
        @selection-change="handleSelectionChange"
        stripe
        border
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="username" label="用户名" width="120" />
        <el-table-column prop="nickname" label="昵称" width="120" />
        <el-table-column prop="email" label="邮箱" width="180" />
        <el-table-column prop="phone" label="手机号" width="120" />
        <el-table-column prop="deptName" label="部门" width="120" />
        <el-table-column prop="roleNames" label="角色" width="150">
          <template #default="{ row }">
            <el-tag
              v-for="roleName in row.roleNames"
              :key="roleName"
              size="small"
              type="info"
              class="role-tag"
            >
              {{ roleName }}
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
        <el-table-column prop="onlineStatus" label="在线状态" width="80">
          <template #default="{ row }">
            <el-tag :type="row.onlineStatus === 1 ? 'success' : 'info'">
              {{ row.onlineStatus === 1 ? '在线' : '离线' }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="lastLoginTime" label="最后登录时间" width="160" />
        <el-table-column prop="createTime" label="创建时间" width="160" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">
              编辑
            </el-button>
            <el-button type="warning" size="small" @click="handleResetPwd(row)">
              重置密码
            </el-button>
            <el-dropdown @command="(command) => handleCommand(command, row)">
              <el-button type="info" size="small">
                更多<el-icon class="el-icon--right"><ArrowDown /></el-icon>
              </el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item command="status" :disabled="row.username === 'admin'">
                    {{ row.status === 1 ? '禁用' : '启用' }}
                  </el-dropdown-item>
                  <el-dropdown-item command="lock" :disabled="row.status === 0">
                    锁定
                  </el-dropdown-item>
                  <el-dropdown-item command="unlock" :disabled="row.status === 1">
                    解锁
                  </el-dropdown-item>
                  <el-dropdown-item command="forceLogout" :disabled="row.onlineStatus === 0">
                    强制下线
                  </el-dropdown-item>
                  <el-dropdown-item command="delete" :disabled="row.username === 'admin'">
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

    <!-- 用户表单对话框 -->
    <UserFormDialog
      v-model="dialogVisible"
      :user-data="currentUser"
      :dept-tree-data="deptTreeData"
      :role-list="roleList"
      @success="handleFormSuccess"
    />

    <!-- 重置密码对话框 -->
    <ResetPasswordDialog
      v-model="resetPwdDialogVisible"
      :user-data="currentUser"
      @success="handleResetPwdSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage, ElMessageBox, ElTable, ElTableColumn, ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElButton, ElIcon, ElTag, ElDropdown, ElDropdownMenu, ElDropdownItem, ElTreeSelect, ElPagination } from 'element-plus'
import { Search, Refresh, Plus, Delete, Download, ArrowDown } from '@element-plus/icons-vue'
import { getUserPage, deleteUser, batchDeleteUsers, updateUserStatus, lockUser, unlockUser, forceLogout, resetPassword } from '@/api/user'
import type { UserInfo, UserQueryParams } from '@/api/user'
import UserFormDialog from './components/UserFormDialog.vue'
import ResetPasswordDialog from './components/ResetPasswordDialog.vue'

// 响应式数据
const loading = ref(false)
const userList = ref<UserInfo[]>([])
const total = ref(0)
const selectedIds = ref<number[]>([])
const dialogVisible = ref(false)
const resetPwdDialogVisible = ref(false)
const currentUser = ref<UserInfo | null>(null)

// 查询参数
const queryParams = reactive<UserQueryParams>({
  pageNum: 1,
  pageSize: 10,
  username: '',
  nickname: '',
  status: undefined,
  deptId: undefined
})

// 部门树数据（模拟数据，实际应该从API获取）
const deptTreeData = ref([
  {
    id: 1,
    label: '总公司',
    children: [
      { id: 2, label: '技术部' },
      { id: 3, label: '市场部' },
      { id: 4, label: '人事部' }
    ]
  }
])

// 角色列表（模拟数据，实际应该从API获取）
const roleList = ref([
  { id: 1, roleName: '超级管理员' },
  { id: 2, roleName: '系统管理员' },
  { id: 3, roleName: '普通用户' }
])

// 计算属性
const hasSelection = computed(() => selectedIds.value.length > 0)

// 方法
const loadUserList = async () => {
  try {
    loading.value = true
    const response = await getUserPage(queryParams)
    if (response.code === 200) {
      userList.value = response.data.records
      total.value = response.data.total
    }
  } catch (error) {
    console.error('加载用户列表失败:', error)
    ElMessage.error('加载用户列表失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  queryParams.pageNum = 1
  loadUserList()
}

const handleReset = () => {
  Object.assign(queryParams, {
    pageNum: 1,
    pageSize: 10,
    username: '',
    nickname: '',
    status: undefined,
    deptId: undefined
  })
  loadUserList()
}

const handleSizeChange = (size: number) => {
  queryParams.pageSize = size
  loadUserList()
}

const handleCurrentChange = (page: number) => {
  queryParams.pageNum = page
  loadUserList()
}

const handleSelectionChange = (selection: UserInfo[]) => {
  selectedIds.value = selection.map(item => item.id)
}

const handleAdd = () => {
  currentUser.value = null
  dialogVisible.value = true
}

const handleEdit = (row: UserInfo) => {
  currentUser.value = { ...row }
  dialogVisible.value = true
}

const handleResetPwd = (row: UserInfo) => {
  currentUser.value = row
  resetPwdDialogVisible.value = true
}

const handleBatchDelete = async () => {
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择要删除的用户')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedIds.value.length} 个用户吗？`,
      '确认删除',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await batchDeleteUsers(selectedIds.value)
    if (response.code === 200) {
      ElMessage.success('批量删除成功')
      selectedIds.value = []
      loadUserList()
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

const handleCommand = async (command: string, row: UserInfo) => {
  try {
    switch (command) {
      case 'status':
        const newStatus = row.status === 1 ? 0 : 1
        const statusResponse = await updateUserStatus(row.id, newStatus)
        if (statusResponse.code === 200) {
          ElMessage.success(`用户${newStatus === 1 ? '启用' : '禁用'}成功`)
          loadUserList()
        }
        break
      case 'lock':
        const reason = await ElMessageBox.prompt('请输入锁定原因', '锁定用户', {
          confirmButtonText: '确定',
          cancelButtonText: '取消'
        })
        if (reason.value) {
          const lockResponse = await lockUser(row.id, reason.value)
          if (lockResponse.code === 200) {
            ElMessage.success('用户锁定成功')
            loadUserList()
          }
        }
        break
      case 'unlock':
        const unlockResponse = await unlockUser(row.id)
        if (unlockResponse.code === 200) {
          ElMessage.success('用户解锁成功')
          loadUserList()
        }
        break
      case 'forceLogout':
        const logoutResponse = await forceLogout(row.id)
        if (logoutResponse.code === 200) {
          ElMessage.success('用户强制下线成功')
          loadUserList()
        }
        break
      case 'delete':
        await ElMessageBox.confirm(
          `确定要删除用户 "${row.username}" 吗？`,
          '确认删除',
          {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }
        )
        const deleteResponse = await deleteUser(row.id)
        if (deleteResponse.code === 200) {
          ElMessage.success('用户删除成功')
          loadUserList()
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
  loadUserList()
}

const handleResetPwdSuccess = () => {
  resetPwdDialogVisible.value = false
  ElMessage.success('密码重置成功')
}

// 生命周期
onMounted(() => {
  loadUserList()
})
</script>

<style lang="scss" scoped>
.user-management {
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

.user-table {
  background: var(--bg-color);
  border-radius: 8px;
  box-shadow: var(--box-shadow-light);
  margin-bottom: 20px;
  overflow: hidden;
}

.role-tag {
  margin-right: 5px;
  margin-bottom: 5px;
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
  .user-management {
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