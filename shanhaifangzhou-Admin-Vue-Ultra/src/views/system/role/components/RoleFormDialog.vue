<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑角色' : '新增角色'"
    width="700px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="120px"
      @submit.prevent
    >
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="角色名称" prop="roleName">
            <el-input
              v-model="formData.roleName"
              placeholder="请输入角色名称"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="角色编码" prop="roleCode">
            <el-input
              v-model="formData.roleCode"
              placeholder="请输入角色编码"
              :disabled="isEdit && currentRole?.isSystem === 1"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="角色类型" prop="roleType">
            <el-select v-model="formData.roleType" placeholder="请选择角色类型" style="width: 100%">
              <el-option label="系统角色" :value="0" />
              <el-option label="业务角色" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="角色级别" prop="roleLevel">
            <el-select v-model="formData.roleLevel" placeholder="请选择角色级别" style="width: 100%">
              <el-option label="超级管理员" :value="1" />
              <el-option label="系统管理员" :value="2" />
              <el-option label="普通管理员" :value="3" />
              <el-option label="普通用户" :value="4" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="数据范围" prop="dataScope">
            <el-select v-model="formData.dataScope" placeholder="请选择数据范围" style="width: 100%">
              <el-option label="全部数据权限" :value="1" />
              <el-option label="自定义数据权限" :value="2" />
              <el-option label="本部门数据权限" :value="3" />
              <el-option label="本部门及以下数据权限" :value="4" />
              <el-option label="仅本人数据权限" :value="5" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="角色状态" prop="status">
            <el-radio-group v-model="formData.status">
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="0">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="角色排序" prop="sort">
            <el-input-number
              v-model="formData.sort"
              :min="0"
              :max="9999"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="角色图标" prop="roleIcon">
            <el-input
              v-model="formData.roleIcon"
              placeholder="请输入角色图标"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="角色颜色" prop="roleColor">
            <el-color-picker
              v-model="formData.roleColor"
              show-alpha
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否系统内置">
            <el-tag :type="currentRole?.isSystem === 1 ? 'danger' : 'info'">
              {{ currentRole?.isSystem === 1 ? '是' : '否' }}
            </el-tag>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="角色描述" prop="roleDesc">
        <el-input
          v-model="formData.roleDesc"
          type="textarea"
          :rows="3"
          placeholder="请输入角色描述"
        />
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="formData.remark"
          type="textarea"
          :rows="2"
          placeholder="请输入备注信息"
        />
      </el-form-item>
    </el-form>

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
import { ElMessage, ElDialog, ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElButton, ElRow, ElCol, ElInputNumber, ElColorPicker, ElRadioGroup, ElRadio, ElTag } from 'element-plus'
import { createRole, updateRole, checkRoleCodeUnique, checkRoleNameUnique } from '@/api/role'
import type { RoleInfo, RoleCreateParams, RoleUpdateParams } from '@/api/role'

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
const formRef = ref()
const loading = ref(false)

// 表单数据
const formData = reactive<RoleCreateParams & RoleUpdateParams>({
  id: 0,
  roleName: '',
  roleCode: '',
  roleDesc: '',
  roleType: 1,
  status: 1,
  sort: 0,
  dataScope: 5,
  roleLevel: 4,
  roleIcon: '',
  roleColor: '#409EFF',
  menuIds: [],
  deptIds: [],
  remark: ''
})

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const isEdit = computed(() => !!props.roleData)

const currentRole = computed(() => props.roleData)

// 表单验证规则
const formRules = {
  roleName: [
    { required: true, message: '请输入角色名称', trigger: 'blur' },
    { max: 50, message: '角色名称长度不能超过50个字符', trigger: 'blur' }
  ],
  roleCode: [
    { required: true, message: '请输入角色编码', trigger: 'blur' },
    { pattern: /^[A-Z_]+$/, message: '角色编码只能包含大写字母和下划线', trigger: 'blur' },
    { max: 50, message: '角色编码长度不能超过50个字符', trigger: 'blur' }
  ],
  roleType: [
    { required: true, message: '请选择角色类型', trigger: 'change' }
  ],
  roleLevel: [
    { required: true, message: '请选择角色级别', trigger: 'change' }
  ],
  dataScope: [
    { required: true, message: '请选择数据范围', trigger: 'change' }
  ],
  status: [
    { required: true, message: '请选择角色状态', trigger: 'change' }
  ],
  sort: [
    { required: true, message: '请输入角色排序', trigger: 'blur' }
  ],
  roleColor: [
    { pattern: /^#[0-9A-Fa-f]{6}$/, message: '角色颜色格式不正确', trigger: 'blur' }
  ]
}

// 监听角色数据变化
watch(() => props.roleData, (newVal) => {
  if (newVal) {
    // 编辑模式，填充表单数据
    Object.assign(formData, {
      id: newVal.id,
      roleName: newVal.roleName,
      roleCode: newVal.roleCode,
      roleDesc: newVal.roleDesc,
      roleType: newVal.roleType,
      status: newVal.status,
      sort: newVal.sort,
      dataScope: newVal.dataScope,
      roleLevel: newVal.roleLevel,
      roleIcon: newVal.roleIcon,
      roleColor: newVal.roleColor,
      menuIds: newVal.menuIds || [],
      deptIds: newVal.deptIds || [],
      remark: newVal.remark
    })
  } else {
    // 新增模式，重置表单数据
    resetForm()
  }
}, { immediate: true })

// 方法
const resetForm = () => {
  Object.assign(formData, {
    id: 0,
    roleName: '',
    roleCode: '',
    roleDesc: '',
    roleType: 1,
    status: 1,
    sort: 0,
    dataScope: 5,
    roleLevel: 4,
    roleIcon: '',
    roleColor: '#409EFF',
    menuIds: [],
    deptIds: [],
    remark: ''
  })
  
  // 清除表单验证
  nextTick(() => {
    formRef.value?.clearValidate()
  })
}

const handleClose = () => {
  dialogVisible.value = false
  resetForm()
}

const validateForm = async () => {
  if (!formRef.value) return false
  
  try {
    await formRef.value.validate()
    return true
  } catch (error) {
    return false
  }
}

const validateUnique = async () => {
  try {
    // 检查角色编码唯一性
    const codeResponse = await checkRoleCodeUnique(formData.roleCode, isEdit.value ? formData.id : undefined)
    if (!codeResponse.data) {
      ElMessage.error('角色编码已存在')
      return false
    }

    // 检查角色名称唯一性
    const nameResponse = await checkRoleNameUnique(formData.roleName, isEdit.value ? formData.id : undefined)
    if (!nameResponse.data) {
      ElMessage.error('角色名称已存在')
      return false
    }

    return true
  } catch (error) {
    console.error('验证唯一性失败:', error)
    return false
  }
}

const handleSubmit = async () => {
  try {
    // 表单验证
    const isValid = await validateForm()
    if (!isValid) return

    // 唯一性验证
    const isUnique = await validateUnique()
    if (!isUnique) return

    loading.value = true

    if (isEdit.value) {
      // 编辑角色
      const updateData: RoleUpdateParams = {
        id: formData.id,
        roleName: formData.roleName,
        roleCode: formData.roleCode,
        roleDesc: formData.roleDesc,
        roleType: formData.roleType,
        status: formData.status,
        sort: formData.sort,
        dataScope: formData.dataScope,
        roleLevel: formData.roleLevel,
        roleIcon: formData.roleIcon,
        roleColor: formData.roleColor,
        menuIds: formData.menuIds,
        deptIds: formData.deptIds,
        remark: formData.remark
      }

      const response = await updateRole(updateData)
      if (response.code === 200) {
        ElMessage.success('角色更新成功')
        emit('success')
      }
    } else {
      // 新增角色
      const createData: RoleCreateParams = {
        roleName: formData.roleName,
        roleCode: formData.roleCode,
        roleDesc: formData.roleDesc,
        roleType: formData.roleType,
        status: formData.status,
        sort: formData.sort,
        dataScope: formData.dataScope,
        roleLevel: formData.roleLevel,
        roleIcon: formData.roleIcon,
        roleColor: formData.roleColor,
        menuIds: formData.menuIds,
        deptIds: formData.deptIds,
        remark: formData.remark
      }

      const response = await createRole(createData)
      if (response.code === 200) {
        ElMessage.success('角色创建成功')
        emit('success')
      }
    }
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error('操作失败')
  } finally {
    loading.value = false
  }
}
</script>

<style lang="scss" scoped>
.dialog-footer {
  text-align: right;
}
</style>