<template>
  <div class="menu-management">
    <!-- 搜索表单 -->
    <el-card class="search-card" shadow="never">
      <el-form
        ref="searchFormRef"
        :model="searchForm"
        :inline="true"
        label-width="80px"
        @submit.prevent
      >
        <el-form-item label="菜单名称">
          <el-input
            v-model="searchForm.menuName"
            placeholder="请输入菜单名称"
            clearable
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item label="菜单类型">
          <el-select
            v-model="searchForm.menuType"
            placeholder="请选择菜单类型"
            clearable
            style="width: 150px"
          >
            <el-option label="目录" value="M" />
            <el-option label="菜单" value="C" />
            <el-option label="按钮" value="F" />
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
        <el-form-item label="显示状态">
          <el-select
            v-model="searchForm.visible"
            placeholder="请选择显示状态"
            clearable
            style="width: 120px"
          >
            <el-option label="显示" :value="1" />
            <el-option label="隐藏" :value="0" />
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
          新增菜单
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

    <!-- 菜单表格 -->
    <el-card class="table-card" shadow="never">
      <el-table
        ref="tableRef"
        v-loading="loading"
        :data="menuList"
        row-key="id"
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" />
        <el-table-column prop="menuName" label="菜单名称" min-width="200">
          <template #default="{ row }">
            <el-icon v-if="row.icon" class="menu-icon">
              <component :is="row.icon" />
            </el-icon>
            <span>{{ row.menuName }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="menuType" label="类型" width="80">
          <template #default="{ row }">
            <el-tag :type="getMenuTypeType(row.menuType)">
              {{ getMenuTypeText(row.menuType) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="path" label="路由地址" min-width="150" />
        <el-table-column prop="component" label="组件路径" min-width="150" />
        <el-table-column prop="perms" label="权限标识" min-width="150" />
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
              v-if="row.menuType !== 'F'"
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

    <!-- 菜单表单对话框 -->
    <MenuFormDialog
      v-model="menuFormVisible"
      :menu-data="currentMenu"
      @success="handleSuccess"
    />

    <!-- 菜单复制对话框 -->
    <MenuCopyDialog
      v-model="menuCopyVisible"
      :menu-data="currentMenu"
      @success="handleSuccess"
    />

    <!-- 菜单移动对话框 -->
    <MenuMoveDialog
      v-model="menuMoveVisible"
      :menu-data="currentMenu"
      @success="handleSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, nextTick } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Search, Refresh, Plus, Delete, Expand, Fold } from '@element-plus/icons-vue'
import { getMenuPage, deleteMenu, batchDeleteMenus, updateMenuStatus, updateMenuVisible } from '@/api/menu'
import type { MenuInfo, MenuQueryParams } from '@/api/menu'
import MenuFormDialog from './components/MenuFormDialog.vue'
import MenuCopyDialog from './components/MenuCopyDialog.vue'
import MenuMoveDialog from './components/MenuMoveDialog.vue'
import { formatDateTime } from '@/utils/format'

// 响应式数据
const loading = ref(false)
const menuList = ref<MenuInfo[]>([])
const selectedIds = ref<number[]>([])
const searchFormRef = ref()
const tableRef = ref()

// 对话框控制
const menuFormVisible = ref(false)
const menuCopyVisible = ref(false)
const menuMoveVisible = ref(false)
const currentMenu = ref<MenuInfo | null>(null)

// 搜索表单
const searchForm = reactive<MenuQueryParams>({
  current: 1,
  size: 1000,
  menuName: '',
  menuType: '',
  status: undefined,
  visible: undefined
})

// 生命周期
onMounted(() => {
  loadMenuList()
})

// 加载菜单列表
const loadMenuList = async () => {
  try {
    loading.value = true
    const response = await getMenuPage(searchForm)
    if (response.code === 200) {
      menuList.value = response.data.records || []
    }
  } catch (error) {
    console.error('加载菜单列表失败:', error)
    ElMessage.error('加载菜单列表失败')
  } finally {
    loading.value = false
  }
}

// 搜索
const handleSearch = () => {
  searchForm.current = 1
  loadMenuList()
}

// 重置
const handleReset = () => {
  searchFormRef.value?.resetFields()
  searchForm.current = 1
  loadMenuList()
}

// 选择变化
const handleSelectionChange = (selection: MenuInfo[]) => {
  selectedIds.value = selection.map(item => item.id)
}

// 新增菜单
const handleAdd = (parentMenu?: MenuInfo) => {
  currentMenu.value = parentMenu || null
  menuFormVisible.value = true
}

// 编辑菜单
const handleEdit = (menu: MenuInfo) => {
  currentMenu.value = menu
  menuFormVisible.value = true
}

// 复制菜单
const handleCopy = (menu: MenuInfo) => {
  currentMenu.value = menu
  menuCopyVisible.value = true
}

// 移动菜单
const handleMove = (menu: MenuInfo) => {
  currentMenu.value = menu
  menuMoveVisible.value = true
}

// 删除菜单
const handleDelete = async (menu: MenuInfo) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除菜单"${menu.menuName}"吗？删除后不可恢复！`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await deleteMenu(menu.id)
    if (response.code === 200) {
      ElMessage.success('删除成功')
      loadMenuList()
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除菜单失败:', error)
      ElMessage.error('删除菜单失败')
    }
  }
}

// 批量删除
const handleBatchDelete = async () => {
  if (!selectedIds.value.length) {
    ElMessage.warning('请选择要删除的菜单')
    return
  }

  try {
    await ElMessageBox.confirm(
      `确定要删除选中的 ${selectedIds.value.length} 个菜单吗？删除后不可恢复！`,
      '批量删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    const response = await batchDeleteMenus(selectedIds.value)
    if (response.code === 200) {
      ElMessage.success('批量删除成功')
      selectedIds.value = []
      loadMenuList()
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('批量删除菜单失败:', error)
      ElMessage.error('批量删除菜单失败')
    }
  }
}

// 状态变化
const handleStatusChange = async (id: number, status: number) => {
  try {
    const response = await updateMenuStatus(id, status)
    if (response.code === 200) {
      ElMessage.success('状态更新成功')
    } else {
      // 恢复原状态
      const menu = menuList.value.find(item => item.id === id)
      if (menu) {
        menu.status = status === 1 ? 0 : 1
      }
    }
  } catch (error) {
    console.error('更新菜单状态失败:', error)
    ElMessage.error('更新菜单状态失败')
    // 恢复原状态
    const menu = menuList.value.find(item => item.id === id)
    if (menu) {
      menu.status = status === 1 ? 0 : 1
    }
  }
}

// 显示状态变化
const handleVisibleChange = async (id: number, visible: number) => {
  try {
    const response = await updateMenuVisible(id, visible)
    if (response.code === 200) {
      ElMessage.success('显示状态更新成功')
    } else {
      // 恢复原状态
      const menu = menuList.value.find(item => item.id === id)
      if (menu) {
        menu.visible = visible === 1 ? 0 : 1
      }
    }
  } catch (error) {
    console.error('更新菜单显示状态失败:', error)
    ElMessage.error('更新菜单显示状态失败')
    // 恢复原状态
    const menu = menuList.value.find(item => item.id === id)
    if (menu) {
      menu.visible = visible === 1 ? 0 : 1
    }
  }
}

// 展开全部
const handleExpandAll = () => {
  nextTick(() => {
    const allIds = getAllMenuIds(menuList.value)
    allIds.forEach(id => {
      tableRef.value?.toggleRowExpansion(
        menuList.value.find(item => item.id === id),
        true
      )
    })
  })
}

// 折叠全部
const handleCollapseAll = () => {
  nextTick(() => {
    const allIds = getAllMenuIds(menuList.value)
    allIds.forEach(id => {
      tableRef.value?.toggleRowExpansion(
        menuList.value.find(item => item.id === id),
        false
      )
    })
  })
}

// 获取所有菜单ID
const getAllMenuIds = (menus: MenuInfo[]): number[] => {
  const ids: number[] = []
  menus.forEach(menu => {
    ids.push(menu.id)
    if (menu.children && menu.children.length > 0) {
      ids.push(...getAllMenuIds(menu.children))
    }
  })
  return ids
}

// 操作成功回调
const handleSuccess = () => {
  loadMenuList()
}

// 工具方法
const getMenuTypeType = (type: string) => {
  const types: Record<string, string> = {
    M: 'primary',
    C: 'success',
    F: 'warning'
  }
  return types[type] || 'info'
}

const getMenuTypeText = (type: string) => {
  const texts: Record<string, string> = {
    M: '目录',
    C: '菜单',
    F: '按钮'
  }
  return texts[type] || '未知'
}
</script>

<style lang="scss" scoped>
.menu-management {
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
    .menu-icon {
      margin-right: 5px;
      vertical-align: middle;
    }
  }
}
</style>