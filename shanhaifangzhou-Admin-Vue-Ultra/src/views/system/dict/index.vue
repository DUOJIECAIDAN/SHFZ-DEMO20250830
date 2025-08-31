<template>
  <div class="dict-management">
    <el-row :gutter="20">
      <!-- 字典类型列表 -->
      <el-col :span="8">
        <el-card class="dict-type-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>字典类型</span>
              <el-button type="primary" link @click="handleAddType">
                <el-icon><Plus /></el-icon>
                新增类型
              </el-button>
            </div>
          </template>
          
          <el-table
            ref="typeTableRef"
            v-loading="typeLoading"
            :data="dictTypeList"
            row-key="id"
            highlight-current-row
            @current-change="handleTypeSelect"
          >
            <el-table-column prop="dictName" label="字典名称" min-width="120" />
            <el-table-column prop="dictType" label="字典类型" min-width="120" />
            <el-table-column prop="status" label="状态" width="80">
              <template #default="{ row }">
                <el-tag :type="row.status === 1 ? 'success' : 'danger'">
                  {{ row.status === 1 ? '正常' : '停用' }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120" fixed="right">
              <template #default="{ row }">
                <el-button type="primary" link @click="handleEditType(row)">
                  编辑
                </el-button>
                <el-button type="danger" link @click="handleDeleteType(row)">
                  删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <!-- 字典数据列表 -->
      <el-col :span="16">
        <el-card class="dict-data-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>字典数据</span>
              <div class="header-actions">
                <el-button 
                  type="primary" 
                  link 
                  :disabled="!currentDictType"
                  @click="handleAddData"
                >
                  <el-icon><Plus /></el-icon>
                  新增数据
                </el-button>
                <el-button 
                  type="success" 
                  link 
                  :disabled="!currentDictType"
                  @click="handleRefreshData"
                >
                  <el-icon><Refresh /></el-icon>
                  刷新
                </el-button>
              </div>
            </div>
          </template>

          <div v-if="!currentDictType" class="no-selection">
            <el-empty description="请选择字典类型" />
          </div>

          <div v-else>
            <el-table
              ref="dataTableRef"
              v-loading="dataLoading"
              :data="dictDataList"
              row-key="id"
              :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
            >
              <el-table-column prop="dictLabel" label="字典标签" min-width="150">
                <template #default="{ row }">
                  <div class="dict-label">
                    <el-icon v-if="row.icon" class="dict-icon">
                      <component :is="row.icon" />
                    </el-icon>
                    <span>{{ row.dictLabel }}</span>
                  </div>
                </template>
              </el-table-column>
              <el-table-column prop="dictValue" label="字典键值" min-width="120" />
              <el-table-column prop="dictSort" label="排序" width="80" />
              <el-table-column prop="status" label="状态" width="80">
                <template #default="{ row }">
                  <el-switch
                    v-model="row.status"
                    :active-value="1"
                    :inactive-value="0"
                    @change="(value) => handleDataStatusChange(row.id, value)"
                  />
                </template>
              </el-table-column>
              <el-table-column prop="isDefault" label="默认" width="80">
                <template #default="{ row }">
                  <el-tag v-if="row.isDefault === 1" type="success" size="small">
                    是
                  </el-tag>
                  <span v-else>-</span>
                </template>
              </el-table-column>
              <el-table-column prop="cssClass" label="样式" width="100">
                <template #default="{ row }">
                  <el-tag v-if="row.cssClass" :class="row.cssClass" size="small">
                    {{ row.cssClass }}
                  </el-tag>
                  <span v-else>-</span>
                </template>
              </el-table-column>
              <el-table-column label="操作" width="200" fixed="right">
                <template #default="{ row }">
                  <el-button type="primary" link @click="handleEditData(row)">
                    编辑
                  </el-button>
                  <el-button type="success" link @click="handleAddChildData(row)">
                    新增子项
                  </el-button>
                  <el-button type="danger" link @click="handleDeleteData(row)">
                    删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 字典类型表单对话框 -->
    <DictTypeFormDialog
      v-model="typeFormVisible"
      :type-data="currentType"
      @success="handleTypeSuccess"
    />

    <!-- 字典数据表单对话框 -->
    <DictDataFormDialog
      v-model="dataFormVisible"
      :data-data="currentData"
      :dict-type="currentDictType"
      @success="handleDataSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { Plus, Refresh } from '@element-plus/icons-vue'
import DictTypeFormDialog from './components/DictTypeFormDialog.vue'
import DictDataFormDialog from './components/DictDataFormDialog.vue'

// 响应式数据
const typeLoading = ref(false)
const dataLoading = ref(false)
const dictTypeList = ref<any[]>([])
const dictDataList = ref<any[]>([])
const currentDictType = ref<any>(null)
const currentType = ref<any>(null)
const currentData = ref<any>(null)

// 表格引用
const typeTableRef = ref()
const dataTableRef = ref()

// 对话框控制
const typeFormVisible = ref(false)
const dataFormVisible = ref(false)

// 生命周期
onMounted(() => {
  loadDictTypeList()
})

// 加载字典类型列表
const loadDictTypeList = async () => {
  try {
    typeLoading.value = true
    // TODO: 调用后端API获取字典类型列表
    // const response = await getDictTypeList()
    // if (response.code === 200) {
    //   dictTypeList.value = response.data || []
    // }
    
    // 模拟数据
    dictTypeList.value = [
      {
        id: 1,
        dictName: '系统状态',
        dictType: 'sys_status',
        status: 1
      },
      {
        id: 2,
        dictName: '用户性别',
        dictType: 'sys_user_sex',
        status: 1
      },
      {
        id: 3,
        dictName: '菜单类型',
        dictType: 'sys_menu_type',
        status: 1
      }
    ]
  } catch (error) {
    console.error('加载字典类型列表失败:', error)
    ElMessage.error('加载字典类型列表失败')
  } finally {
    typeLoading.value = false
  }
}

// 加载字典数据列表
const loadDictDataList = async () => {
  if (!currentDictType.value) return
  
  try {
    dataLoading.value = true
    // TODO: 调用后端API获取字典数据列表
    // const response = await getDictDataList(currentDictType.value.dictType)
    // if (response.code === 200) {
    //   dictDataList.value = response.data || []
    // }
    
    // 模拟数据
    if (currentDictType.value.dictType === 'sys_status') {
      dictDataList.value = [
        {
          id: 1,
          dictLabel: '正常',
          dictValue: '1',
          dictSort: 1,
          status: 1,
          isDefault: 1,
          cssClass: 'el-tag--success'
        },
        {
          id: 2,
          dictLabel: '停用',
          dictValue: '0',
          dictSort: 2,
          status: 1,
          isDefault: 0,
          cssClass: 'el-tag--danger'
        }
      ]
    } else if (currentDictType.value.dictType === 'sys_user_sex') {
      dictDataList.value = [
        {
          id: 3,
          dictLabel: '男',
          dictValue: '1',
          dictSort: 1,
          status: 1,
          isDefault: 1,
          cssClass: 'el-tag--primary'
        },
        {
          id: 4,
          dictLabel: '女',
          dictValue: '2',
          dictSort: 2,
          status: 1,
          isDefault: 0,
          cssClass: 'el-tag--warning'
        }
      ]
    } else {
      dictDataList.value = []
    }
  } catch (error) {
    console.error('加载字典数据列表失败:', error)
    ElMessage.error('加载字典数据列表失败')
  } finally {
    dataLoading.value = false
  }
}

// 选择字典类型
const handleTypeSelect = (row: any) => {
  currentDictType.value = row
  if (row) {
    loadDictDataList()
  } else {
    dictDataList.value = []
  }
}

// 新增字典类型
const handleAddType = () => {
  currentType.value = null
  typeFormVisible.value = true
}

// 编辑字典类型
const handleEditType = (type: any) => {
  currentType.value = type
  typeFormVisible.value = true
}

// 删除字典类型
const handleDeleteType = async (type: any) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除字典类型"${type.dictName}"吗？删除后相关的字典数据也会被删除！`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    // TODO: 调用后端API删除字典类型
    // const response = await deleteDictType(type.id)
    // if (response.code === 200) {
    //   ElMessage.success('删除成功')
    //   loadDictTypeList()
    //   if (currentDictType.value?.id === type.id) {
    //     currentDictType.value = null
    //     dictDataList.value = []
    //   }
    // }
    
    ElMessage.success('删除成功')
    loadDictTypeList()
    if (currentDictType.value?.id === type.id) {
      currentDictType.value = null
      dictDataList.value = []
    }
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除字典类型失败:', error)
      ElMessage.error('删除字典类型失败')
    }
  }
}

// 新增字典数据
const handleAddData = () => {
  currentData.value = null
  dataFormVisible.value = true
}

// 编辑字典数据
const handleEditData = (data: any) => {
  currentData.value = data
  dataFormVisible.value = true
}

// 新增子项数据
const handleAddChildData = (parentData: any) => {
  currentData.value = {
    parentId: parentData.id,
    dictType: currentDictType.value.dictType
  }
  dataFormVisible.value = true
}

// 删除字典数据
const handleDeleteData = async (data: any) => {
  try {
    await ElMessageBox.confirm(
      `确定要删除字典数据"${data.dictLabel}"吗？`,
      '删除确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )

    // TODO: 调用后端API删除字典数据
    // const response = await deleteDictData(data.id)
    // if (response.code === 200) {
    //   ElMessage.success('删除成功')
    //   loadDictDataList()
    // }
    
    ElMessage.success('删除成功')
    loadDictDataList()
  } catch (error) {
    if (error !== 'cancel') {
      console.error('删除字典数据失败:', error)
      ElMessage.error('删除字典数据失败')
    }
  }
}

// 刷新字典数据
const handleRefreshData = () => {
  loadDictDataList()
}

// 字典数据状态变化
const handleDataStatusChange = async (id: number, status: number) => {
  try {
    // TODO: 调用后端API更新字典数据状态
    // const response = await updateDictDataStatus(id, status)
    // if (response.code === 200) {
    //   ElMessage.success('状态更新成功')
    // } else {
    //   // 恢复原状态
    //   const data = dictDataList.value.find(item => item.id === id)
    //   if (data) {
    //     data.status = status === 1 ? 0 : 1
    //   }
    // }
    
    ElMessage.success('状态更新成功')
  } catch (error) {
    console.error('更新字典数据状态失败:', error)
    ElMessage.error('更新字典数据状态失败')
    // 恢复原状态
    const data = dictDataList.value.find(item => item.id === id)
    if (data) {
      data.status = status === 1 ? 0 : 1
    }
  }
}

// 字典类型操作成功回调
const handleTypeSuccess = () => {
  loadDictTypeList()
}

// 字典数据操作成功回调
const handleDataSuccess = () => {
  loadDictDataList()
}
</script>

<style lang="scss" scoped>
.dict-management {
  padding: 20px;

  .dict-type-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }

  .dict-data-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;

      .header-actions {
        display: flex;
        gap: 10px;
      }
    }

    .no-selection {
      display: flex;
      justify-content: center;
      align-items: center;
      height: 200px;
    }
  }

  .dict-label {
    display: flex;
    align-items: center;
    gap: 8px;

    .dict-icon {
      font-size: 14px;
      color: #409EFF;
    }
  }
}
</style>