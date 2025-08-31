<template>
  <div class="code-generator">
    <el-row :gutter="20">
      <!-- 数据库表列表 -->
      <el-col :span="8">
        <el-card class="table-list-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>数据库表</span>
              <el-button type="primary" link @click="handleRefreshTables">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          
          <el-table
            ref="tableListRef"
            v-loading="tableLoading"
            :data="tableList"
            row-key="tableName"
            highlight-current-row
            @current-change="handleTableSelect"
          >
            <el-table-column prop="tableName" label="表名" min-width="120" />
            <el-table-column prop="tableComment" label="表注释" min-width="120" />
            <el-table-column prop="createTime" label="创建时间" width="120">
              <template #default="{ row }">
                {{ formatDateTime(row.createTime) }}
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <!-- 代码生成配置 -->
      <el-col :span="16">
        <el-card class="config-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>代码生成配置</span>
              <div class="header-actions">
                <el-button type="primary" @click="handlePreviewCode">
                  <el-icon><View /></el-icon>
                  预览代码
                </el-button>
                <el-button type="success" @click="handleGenerateCode">
                  <el-icon><Download /></el-icon>
                  生成代码
                </el-button>
              </div>
            </div>
          </template>

          <div v-if="!currentTable" class="no-selection">
            <el-empty description="请选择数据库表" />
          </div>

          <div v-else>
            <!-- 基本信息配置 -->
            <el-form
              ref="configFormRef"
              :model="configForm"
              :rules="configRules"
              label-width="120px"
            >
              <el-row :gutter="20">
                <el-col :span="12">
                  <el-form-item label="表名" prop="tableName">
                    <el-input v-model="configForm.tableName" disabled />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="表注释" prop="tableComment">
                    <el-input v-model="configForm.tableComment" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="12">
                  <el-form-item label="实体类名" prop="entityName">
                    <el-input v-model="configForm.entityName" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="包名" prop="packageName">
                    <el-input v-model="configForm.packageName" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="12">
                  <el-form-item label="作者" prop="author">
                    <el-input v-model="configForm.author" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="模块名" prop="moduleName">
                    <el-input v-model="configForm.moduleName" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="12">
                  <el-form-item label="功能名" prop="functionName">
                    <el-input v-model="configForm.functionName" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="生成路径" prop="generatePath">
                    <el-input v-model="configForm.generatePath" />
                  </el-form-item>
                </el-col>
              </el-row>

              <!-- 字段配置 -->
              <el-divider content-position="left">字段配置</el-divider>
              
              <el-table :data="fieldList" border>
                <el-table-column prop="columnName" label="字段名" width="120" />
                <el-table-column prop="columnComment" label="字段注释" width="120" />
                <el-table-column prop="dataType" label="数据类型" width="100" />
                <el-table-column prop="isRequired" label="必填" width="80">
                  <template #default="{ row }">
                    <el-checkbox v-model="row.isRequired" />
                  </template>
                </el-table-column>
                <el-table-column prop="isList" label="列表" width="80">
                  <template #default="{ row }">
                    <el-checkbox v-model="row.isList" />
                  </template>
                </el-table-column>
                <el-table-column prop="isQuery" label="查询" width="80">
                  <template #default="{ row }">
                    <el-checkbox v-model="row.isQuery" />
                  </template>
                </el-table-column>
                <el-table-column prop="isEdit" label="编辑" width="80">
                  <template #default="{ row }">
                    <el-checkbox v-model="row.isEdit" />
                  </template>
                </el-table-column>
                <el-table-column prop="queryType" label="查询类型" width="120">
                  <template #default="{ row }">
                    <el-select v-model="row.queryType" style="width: 100%">
                      <el-option label="等于" value="eq" />
                      <el-option label="不等于" value="ne" />
                      <el-option label="大于" value="gt" />
                      <el-option label="大于等于" value="ge" />
                      <el-option label="小于" value="lt" />
                      <el-option label="小于等于" value="le" />
                      <el-option label="模糊查询" value="like" />
                      <el-option label="左模糊" value="left_like" />
                      <el-option label="右模糊" value="right_like" />
                      <el-option label="范围查询" value="between" />
                      <el-option label="包含" value="in" />
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column prop="htmlType" label="HTML类型" width="120">
                  <template #default="{ row }">
                    <el-select v-model="row.htmlType" style="width: 100%">
                      <el-option label="文本框" value="input" />
                      <el-option label="文本域" value="textarea" />
                      <el-option label="下拉框" value="select" />
                      <el-option label="单选框" value="radio" />
                      <el-option label="复选框" value="checkbox" />
                      <el-option label="日期" value="date" />
                      <el-option label="时间" value="time" />
                      <el-option label="日期时间" value="datetime" />
                      <el-option label="图片上传" value="image" />
                      <el-option label="文件上传" value="file" />
                      <el-option label="富文本" value="richtext" />
                    </el-select>
                  </template>
                </el-table-column>
                <el-table-column prop="dictType" label="字典类型" width="120">
                  <template #default="{ row }">
                    <el-input v-model="row.dictType" placeholder="可选" />
                  </template>
                </el-table-column>
              </el-table>

              <!-- 生成选项 -->
              <el-divider content-position="left">生成选项</el-divider>
              
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="生成模板">
                    <el-select v-model="configForm.templateType" style="width: 100%">
                      <el-option label="单表" value="single" />
                      <el-option label="树表" value="tree" />
                      <el-option label="主子表" value="master_detail" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="生成包路径">
                    <el-input v-model="configForm.packagePath" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="生成模块名">
                    <el-input v-model="configForm.generateModuleName" />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="生成功能名">
                    <el-input v-model="configForm.generateFunctionName" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="生成作者">
                    <el-input v-model="configForm.generateAuthor" />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="生成时间">
                    <el-date-picker
                      v-model="configForm.generateTime"
                      type="datetime"
                      style="width: 100%"
                    />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="24">
                  <el-form-item label="生成文件">
                    <el-checkbox-group v-model="configForm.generateFiles">
                      <el-checkbox label="entity">实体类</el-checkbox>
                      <el-checkbox label="mapper">Mapper接口</el-checkbox>
                      <el-checkbox label="xml">Mapper XML</el-checkbox>
                      <el-checkbox label="service">Service接口</el-checkbox>
                      <el-checkbox label="serviceImpl">Service实现</el-checkbox>
                      <el-checkbox label="controller">Controller</el-checkbox>
                      <el-checkbox label="dto">DTO类</el-checkbox>
                      <el-checkbox label="vo">VO类</el-checkbox>
                      <el-checkbox label="api">前端API</el-checkbox>
                      <el-checkbox label="page">前端页面</el-checkbox>
                      <el-checkbox label="sql">SQL脚本</el-checkbox>
                    </el-checkbox-group>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 代码预览对话框 -->
    <CodePreviewDialog
      v-model="previewVisible"
      :code-map="previewCodeMap"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted, computed } from 'vue'
import { ElMessage } from 'element-plus'
import { Refresh, View, Download } from '@element-plus/icons-vue'
import CodePreviewDialog from './components/CodePreviewDialog.vue'
import { formatDateTime } from '@/utils/format'

// 响应式数据
const tableLoading = ref(false)
const tableList = ref<any[]>([])
const currentTable = ref<any>(null)
const fieldList = ref<any[]>([])
const previewVisible = ref(false)
const previewCodeMap = ref<Record<string, string>>({})

// 表单引用
const configFormRef = ref()

// 配置表单
const configForm = reactive({
  tableName: '',
  tableComment: '',
  entityName: '',
  packageName: 'com.shanhaifangzhou.admin',
  author: 'shanhaifangzhou',
  moduleName: '',
  functionName: '',
  generatePath: '',
  templateType: 'single',
  packagePath: '',
  generateModuleName: '',
  generateFunctionName: '',
  generateAuthor: 'shanhaifangzhou',
  generateTime: new Date(),
  generateFiles: ['entity', 'mapper', 'xml', 'service', 'serviceImpl', 'controller', 'dto', 'vo', 'api', 'page']
})

// 表单验证规则
const configRules = {
  entityName: [
    { required: true, message: '请输入实体类名', trigger: 'blur' }
  ],
  packageName: [
    { required: true, message: '请输入包名', trigger: 'blur' }
  ],
  author: [
    { required: true, message: '请输入作者', trigger: 'blur' }
  ],
  moduleName: [
    { required: true, message: '请输入模块名', trigger: 'blur' }
  ],
  functionName: [
    { required: true, message: '请输入功能名', trigger: 'blur' }
  ]
}

// 生命周期
onMounted(() => {
  loadTableList()
})

// 加载数据库表列表
const loadTableList = async () => {
  try {
    tableLoading.value = true
    // TODO: 调用后端API获取数据库表列表
    // const response = await getTableList()
    // if (response.code === 200) {
    //   tableList.value = response.data || []
    // }
    
    // 模拟数据
    tableList.value = [
      {
        tableName: 'sys_user',
        tableComment: '用户表',
        createTime: new Date()
      },
      {
        tableName: 'sys_role',
        tableComment: '角色表',
        createTime: new Date()
      },
      {
        tableName: 'sys_menu',
        tableComment: '菜单表',
        createTime: new Date()
      }
    ]
  } catch (error) {
    console.error('加载数据库表列表失败:', error)
    ElMessage.error('加载数据库表列表失败')
  } finally {
    tableLoading.value = false
  }
}

// 刷新表列表
const handleRefreshTables = () => {
  loadTableList()
}

// 选择表
const handleTableSelect = (row: any) => {
  currentTable.value = row
  if (row) {
    loadFieldList(row.tableName)
    initConfigForm(row)
  } else {
    fieldList.value = []
    resetConfigForm()
  }
}

// 加载字段列表
const loadFieldList = async (tableName: string) => {
  try {
    // TODO: 调用后端API获取表字段信息
    // const response = await getTableColumns(tableName)
    // if (response.code === 200) {
    //   fieldList.value = response.data || []
    // }
    
    // 模拟数据
    fieldList.value = [
      {
        columnName: 'id',
        columnComment: '主键ID',
        dataType: 'bigint',
        isRequired: true,
        isList: false,
        isQuery: false,
        isEdit: false,
        queryType: 'eq',
        htmlType: 'input'
      },
      {
        columnName: 'username',
        columnComment: '用户名',
        dataType: 'varchar',
        isRequired: true,
        isList: true,
        isQuery: true,
        isEdit: true,
        queryType: 'like',
        htmlType: 'input'
      },
      {
        columnName: 'nickname',
        columnComment: '昵称',
        dataType: 'varchar',
        isRequired: false,
        isList: true,
        isQuery: true,
        isEdit: true,
        queryType: 'like',
        htmlType: 'input'
      },
      {
        columnName: 'email',
        columnComment: '邮箱',
        dataType: 'varchar',
        isRequired: false,
        isList: true,
        isQuery: true,
        isEdit: true,
        queryType: 'eq',
        htmlType: 'input'
      },
      {
        columnName: 'status',
        columnComment: '状态',
        dataType: 'tinyint',
        isRequired: true,
        isList: true,
        isQuery: true,
        isEdit: true,
        queryType: 'eq',
        htmlType: 'select',
        dictType: 'sys_status'
      }
    ]
  } catch (error) {
    console.error('加载字段列表失败:', error)
    ElMessage.error('加载字段列表失败')
  }
}

// 初始化配置表单
const initConfigForm = (table: any) => {
  configForm.tableName = table.tableName
  configForm.tableComment = table.tableComment
  configForm.entityName = tableToEntityName(table.tableName)
  configForm.moduleName = tableToModuleName(table.tableName)
  configForm.functionName = table.tableComment
  configForm.generatePath = `shanhaifangzhou-admin/src/main/java/com/shanhaifangzhou/admin/${configForm.moduleName}`
  configForm.packagePath = `com.shanhaifangzhou.admin.${configForm.moduleName}`
  configForm.generateModuleName = configForm.moduleName
  configForm.generateFunctionName = configForm.functionName
}

// 重置配置表单
const resetConfigForm = () => {
  Object.assign(configForm, {
    tableName: '',
    tableComment: '',
    entityName: '',
    packageName: 'com.shanhaifangzhou.admin',
    author: 'shanhaifangzhou',
    moduleName: '',
    functionName: '',
    generatePath: '',
    templateType: 'single',
    packagePath: '',
    generateModuleName: '',
    generateFunctionName: '',
    generateAuthor: 'shanhaifangzhou',
    generateTime: new Date(),
    generateFiles: ['entity', 'mapper', 'xml', 'service', 'serviceImpl', 'controller', 'dto', 'vo', 'api', 'page']
  })
}

// 表名转实体类名
const tableToEntityName = (tableName: string) => {
  return tableName
    .replace(/^sys_/, '')
    .split('_')
    .map(word => word.charAt(0).toUpperCase() + word.slice(1))
    .join('')
}

// 表名转模块名
const tableToModuleName = (tableName: string) => {
  return tableName
    .replace(/^sys_/, '')
    .split('_')
    .map(word => word.charAt(0).toLowerCase() + word.slice(1))
    .join('')
}

// 预览代码
const handlePreviewCode = async () => {
  try {
    // 表单验证
    const isValid = await configFormRef.value?.validate()
    if (!isValid) return

    // TODO: 调用后端API预览代码
    // const response = await previewCode({
    //   ...configForm,
    //   fields: fieldList.value
    // })
    // if (response.code === 200) {
    //   previewCodeMap.value = response.data
    //   previewVisible.value = true
    // }
    
    // 模拟预览代码
    previewCodeMap.value = {
      'Entity.java': `package ${configForm.packagePath};\n\npublic class ${configForm.entityName} {\n    // TODO: 生成实体类代码\n}`,
      'Controller.java': `package ${configForm.packagePath};\n\npublic class ${configForm.entityName}Controller {\n    // TODO: 生成控制器代码\n}`,
      'Service.java': `package ${configForm.packagePath};\n\npublic interface ${configForm.entityName}Service {\n    // TODO: 生成服务接口代码\n}`,
      'index.vue': `<template>\n  <div class="${configForm.moduleName}-management">\n    <!-- TODO: 生成前端页面代码 -->\n  </div>\n</template>`
    }
    previewVisible.value = true
  } catch (error) {
    console.error('预览代码失败:', error)
    ElMessage.error('预览代码失败')
  }
}

// 生成代码
const handleGenerateCode = async () => {
  try {
    // 表单验证
    const isValid = await configFormRef.value?.validate()
    if (!isValid) return

    // TODO: 调用后端API生成代码
    // const response = await generateCode({
    //   ...configForm,
    //   fields: fieldList.value
    // })
    // if (response.code === 200) {
    //   ElMessage.success('代码生成成功')
    // }
    
    ElMessage.success('代码生成成功')
  } catch (error) {
    console.error('生成代码失败:', error)
    ElMessage.error('生成代码失败')
  }
}
</script>

<style lang="scss" scoped>
.code-generator {
  padding: 20px;

  .table-list-card {
    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;
    }
  }

  .config-card {
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

    .el-divider {
      margin: 20px 0;
    }
  }
}
</style>