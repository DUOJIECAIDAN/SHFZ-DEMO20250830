<template>
  <el-dialog
    v-model="dialogVisible"
    title="重置密码"
    width="400px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="100px"
      @submit.prevent
    >
      <el-form-item label="用户名">
        <el-input
          v-model="userData?.username"
          disabled
        />
      </el-form-item>
      
      <el-form-item label="新密码" prop="newPassword">
        <el-input
          v-model="formData.newPassword"
          type="password"
          placeholder="请输入新密码"
          show-password
        />
      </el-form-item>
      
      <el-form-item label="确认密码" prop="confirmPassword">
        <el-input
          v-model="formData.confirmPassword"
          type="password"
          placeholder="请再次输入新密码"
          show-password
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
import { ref, reactive, computed } from 'vue'
import { ElMessage, ElDialog, ElForm, ElFormItem, ElInput, ElButton } from 'element-plus'
import { resetPassword } from '@/api/user'
import type { UserInfo } from '@/api/user'

// Props
interface Props {
  modelValue: boolean
  userData?: UserInfo | null
}

const props = withDefaults(defineProps<Props>(), {
  userData: null
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
const formData = reactive({
  newPassword: '',
  confirmPassword: ''
})

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

// 表单验证规则
const formRules = {
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入新密码', trigger: 'blur' },
    {
      validator: (rule: any, value: string, callback: Function) => {
        if (value !== formData.newPassword) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

// 方法
const resetForm = () => {
  formData.newPassword = ''
  formData.confirmPassword = ''
  
  // 清除表单验证
  formRef.value?.clearValidate()
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

const handleSubmit = async () => {
  try {
    // 表单验证
    const isValid = await validateForm()
    if (!isValid) return

    if (!props.userData?.id) {
      ElMessage.error('用户信息不完整')
      return
    }

    loading.value = true

    const response = await resetPassword(props.userData.id, formData.newPassword)
    if (response.code === 200) {
      ElMessage.success('密码重置成功')
      emit('success')
    }
  } catch (error) {
    console.error('重置密码失败:', error)
    ElMessage.error('重置密码失败')
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