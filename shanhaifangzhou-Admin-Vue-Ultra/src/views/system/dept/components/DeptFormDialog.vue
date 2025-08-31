<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑部门' : '新增部门'"
    width="800px"
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
          <el-form-item label="上级部门">
            <el-tree-select
              v-model="formData.parentId"
              :data="deptTreeData"
              :props="treeProps"
              placeholder="请选择上级部门"
              clearable
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门类型" prop="deptType">
            <el-radio-group v-model="formData.deptType">
              <el-radio :label="1">公司</el-radio>
              <el-radio :label="2">部门</el-radio>
              <el-radio :label="3">小组</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="部门名称" prop="deptName">
            <el-input
              v-model="formData.deptName"
              placeholder="请输入部门名称"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门编码" prop="deptCode">
            <el-input
              v-model="formData.deptCode"
              placeholder="请输入部门编码"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="部门图标" prop="deptIcon">
            <el-input
              v-model="formData.deptIcon"
              placeholder="请输入部门图标"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门颜色" prop="deptColor">
            <el-color-picker
              v-model="formData.deptColor"
              show-alpha
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="部门状态" prop="status">
            <el-radio-group v-model="formData.status">
              <el-radio :label="1">启用</el-radio>
              <el-radio :label="0">禁用</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="显示状态" prop="visible">
            <el-radio-group v-model="formData.visible">
              <el-radio :label="1">显示</el-radio>
              <el-radio :label="0">隐藏</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="部门排序" prop="sort">
            <el-input-number
              v-model="formData.sort"
              :min="0"
              :max="9999"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="负责人ID" prop="leaderId">
            <el-input
              v-model="formData.leaderId"
              placeholder="请输入负责人ID"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="负责人姓名" prop="leaderName">
            <el-input
              v-model="formData.leaderName"
              placeholder="请输入负责人姓名"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="负责人电话" prop="leaderPhone">
            <el-input
              v-model="formData.leaderPhone"
              placeholder="请输入负责人电话"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="负责人邮箱" prop="leaderEmail">
            <el-input
              v-model="formData.leaderEmail"
              placeholder="请输入负责人邮箱"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门电话" prop="phone">
            <el-input
              v-model="formData.phone"
              placeholder="请输入部门电话"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="部门邮箱" prop="email">
            <el-input
              v-model="formData.email"
              placeholder="请输入部门邮箱"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="部门地址" prop="address">
            <el-input
              v-model="formData.address"
              placeholder="请输入部门地址"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="部门描述" prop="description">
        <el-input
          v-model="formData.description"
          type="textarea"
          :rows="3"
          placeholder="请输入部门描述"
        />
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="formData.remark"
          type="textarea"
          :rows="3"
          placeholder="请输入备注"
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
import { ElMessage } from 'element-plus'
import { createDept, updateDept, checkDeptCodeUnique, checkDeptNameUnique } from '@/api/dept'
import { getDeptTree } from '@/api/dept'
import type { DeptInfo, DeptCreateParams, DeptUpdateParams } from '@/api/dept'

// Props
interface Props {
  modelValue: boolean
  deptData?: DeptInfo | null
}

const props = withDefaults(defineProps<Props>(), {
  deptData: null
})

// Emits
const emit = defineEmits<{
  'update:modelValue': [value: boolean]
  'success': []
}>()

// 响应式数据
const formRef = ref()
const loading = ref(false)
const deptTreeData = ref<DeptInfo[]>([])

// 表单数据
const formData = reactive<DeptCreateParams & DeptUpdateParams>({
  id: 0,
  parentId: undefined,
  deptName: '',
  deptCode: '',
  deptType: 2,
  status: 1,
  visible: 1,
  sort: 0,
  leaderId: undefined,
  leaderName: '',
  leaderPhone: '',
  leaderEmail: '',
  phone: '',
  email: '',
  address: '',
  description: '',
  deptIcon: '',
  deptColor: '#409EFF',
  remark: ''
})

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const isEdit = computed(() => !!props.deptData)

// 树形组件属性配置
const treeProps = {
  children: 'children',
  label: 'deptName',
  value: 'id'
}

// 表单验证规则
const formRules = {
  deptName: [
    { required: true, message: '请输入部门名称', trigger: 'blur' },
    { max: 50, message: '部门名称长度不能超过50个字符', trigger: 'blur' }
  ],
  deptCode: [
    { required: true, message: '请输入部门编码', trigger: 'blur' },
    { pattern: /^[A-Z0-9_]+$/, message: '部门编码只能包含大写字母、数字和下划线', trigger: 'blur' },
    { max: 50, message: '部门编码长度不能超过50个字符', trigger: 'blur' }
  ],
  deptType: [
    { required: true, message: '请选择部门类型', trigger: 'change' }
  ],
  sort: [
    { required: true, message: '请输入部门排序', trigger: 'blur' }
  ],
  leaderName: [
    { max: 50, message: '负责人姓名长度不能超过50个字符', trigger: 'blur' }
  ],
  leaderPhone: [
    { pattern: /^1[3-9]\d{9}$/, message: '负责人电话格式不正确', trigger: 'blur' }
  ],
  leaderEmail: [
    { type: 'email', message: '负责人邮箱格式不正确', trigger: 'blur' },
    { max: 50, message: '负责人邮箱长度不能超过50个字符', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '部门电话格式不正确', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '部门邮箱格式不正确', trigger: 'blur' },
    { max: 50, message: '部门邮箱长度不能超过50个字符', trigger: 'blur' }
  ],
  address: [
    { max: 200, message: '部门地址长度不能超过200个字符', trigger: 'blur' }
  ],
  description: [
    { max: 500, message: '部门描述长度不能超过500个字符', trigger: 'blur' }
  ],
  deptIcon: [
    { max: 100, message: '部门图标长度不能超过100个字符', trigger: 'blur' }
  ],
  remark: [
    { max: 500, message: '备注长度不能超过500个字符', trigger: 'blur' }
  ]
}

// 生命周期
watch(() => props.modelValue, async (newVal) => {
  if (newVal) {
    await loadDeptTree()
    if (props.deptData) {
      // 编辑模式，填充表单数据
      Object.assign(formData, {
        id: props.deptData.id,
        parentId: props.deptData.parentId,
        deptName: props.deptData.deptName,
        deptCode: props.deptData.deptCode,
        deptType: props.deptData.deptType,
        status: props.deptData.status,
        visible: props.deptData.visible,
        sort: props.deptData.sort,
        leaderId: props.deptData.leaderId,
        leaderName: props.deptData.leaderName || '',
        leaderPhone: props.deptData.leaderPhone || '',
        leaderEmail: props.deptData.leaderEmail || '',
        phone: props.deptData.phone || '',
        email: props.deptData.email || '',
        address: props.deptData.address || '',
        description: props.deptData.description || '',
        deptIcon: props.deptData.deptIcon || '',
        deptColor: props.deptData.deptColor || '#409EFF',
        remark: props.deptData.remark || ''
      })
    } else {
      // 新增模式，重置表单数据
      resetForm()
    }
  }
}, { immediate: true })

// 加载部门树
const loadDeptTree = async () => {
  try {
    const response = await getDeptTree()
    if (response.code === 200) {
      deptTreeData.value = response.data || []
    }
  } catch (error) {
    console.error('加载部门树失败:', error)
    ElMessage.error('加载部门树失败')
  }
}

// 重置表单
const resetForm = () => {
  Object.assign(formData, {
    id: 0,
    parentId: undefined,
    deptName: '',
    deptCode: '',
    deptType: 2,
    status: 1,
    visible: 1,
    sort: 0,
    leaderId: undefined,
    leaderName: '',
    leaderPhone: '',
    leaderEmail: '',
    phone: '',
    email: '',
    address: '',
    description: '',
    deptIcon: '',
    deptColor: '#409EFF',
    remark: ''
  })
  
  // 清除表单验证
  nextTick(() => {
    formRef.value?.clearValidate()
  })
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  resetForm()
}

// 表单验证
const validateForm = async () => {
  if (!formRef.value) return false
  
  try {
    await formRef.value.validate()
    return true
  } catch (error) {
    return false
  }
}

// 唯一性验证
const validateUnique = async () => {
  try {
    // 检查部门编码唯一性
    if (formData.deptCode) {
      const codeResponse = await checkDeptCodeUnique(
        formData.deptCode,
        isEdit.value ? formData.id : undefined
      )
      if (!codeResponse.data) {
        ElMessage.error('部门编码已存在')
        return false
      }
    }

    // 检查部门名称唯一性
    if (formData.deptName) {
      const nameResponse = await checkDeptNameUnique(
        formData.deptName,
        formData.parentId,
        isEdit.value ? formData.id : undefined
      )
      if (!nameResponse.data) {
        ElMessage.error('同级部门下部门名称已存在')
        return false
      }
    }

    return true
  } catch (error) {
    console.error('验证唯一性失败:', error)
    return false
  }
}

// 提交表单
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
      // 编辑部门
      const updateData: DeptUpdateParams = {
        id: formData.id,
        parentId: formData.parentId,
        deptName: formData.deptName,
        deptCode: formData.deptCode,
        deptType: formData.deptType,
        status: formData.status,
        visible: formData.visible,
        sort: formData.sort,
        leaderId: formData.leaderId,
        leaderName: formData.leaderName,
        leaderPhone: formData.leaderPhone,
        leaderEmail: formData.leaderEmail,
        phone: formData.phone,
        email: formData.email,
        address: formData.address,
        description: formData.description,
        deptIcon: formData.deptIcon,
        deptColor: formData.deptColor,
        remark: formData.remark
      }

      const response = await updateDept(updateData)
      if (response.code === 200) {
        ElMessage.success('部门更新成功')
        emit('success')
      }
    } else {
      // 新增部门
      const createData: DeptCreateParams = {
        parentId: formData.parentId,
        deptName: formData.deptName,
        deptCode: formData.deptCode,
        deptType: formData.deptType,
        status: formData.status,
        visible: formData.visible,
        sort: formData.sort,
        leaderId: formData.leaderId,
        leaderName: formData.leaderName,
        leaderPhone: formData.leaderPhone,
        leaderEmail: formData.leaderEmail,
        phone: formData.phone,
        email: formData.email,
        address: formData.address,
        description: formData.description,
        deptIcon: formData.deptIcon,
        deptColor: formData.deptColor,
        remark: formData.remark
      }

      const response = await createDept(createData)
      if (response.code === 200) {
        ElMessage.success('部门创建成功')
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