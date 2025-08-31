<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑用户' : '新增用户'"
    width="600px"
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
      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="用户名" prop="username" v-if="!isEdit">
            <el-input
              v-model="formData.username"
              placeholder="请输入用户名"
              :disabled="isEdit"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="昵称" prop="nickname">
            <el-input
              v-model="formData.nickname"
              placeholder="请输入昵称"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20" v-if="!isEdit">
        <el-col :span="12">
          <el-form-item label="密码" prop="password">
            <el-input
              v-model="formData.password"
              type="password"
              placeholder="请输入密码"
              show-password
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="确认密码" prop="confirmPassword">
            <el-input
              v-model="formData.confirmPassword"
              type="password"
              placeholder="请再次输入密码"
              show-password
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="邮箱" prop="email">
            <el-input
              v-model="formData.email"
              placeholder="请输入邮箱"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="手机号" prop="phone">
            <el-input
              v-model="formData.phone"
              placeholder="请输入手机号"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="性别" prop="gender">
            <el-select v-model="formData.gender" placeholder="请选择性别" style="width: 100%">
              <el-option label="未知" :value="0" />
              <el-option label="男" :value="1" />
              <el-option label="女" :value="2" />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="用户类型" prop="userType">
            <el-select v-model="formData.userType" placeholder="请选择用户类型" style="width: 100%">
              <el-option label="系统用户" :value="0" />
              <el-option label="业务用户" :value="1" />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="部门" prop="deptId">
            <el-tree-select
              v-model="formData.deptId"
              :data="deptTreeData"
              placeholder="请选择部门"
              style="width: 100%"
              check-strictly
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="岗位" prop="postId">
            <el-select v-model="formData.postId" placeholder="请选择岗位" clearable style="width: 100%">
              <el-option
                v-for="post in postList"
                :key="post.id"
                :label="post.postName"
                :value="post.id"
              />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="角色" prop="roleIds">
        <el-checkbox-group v-model="formData.roleIds">
          <el-checkbox
            v-for="role in roleList"
            :key="role.id"
            :label="role.id"
          >
            {{ role.roleName }}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item label="状态" prop="status" v-if="isEdit">
        <el-radio-group v-model="formData.status">
          <el-radio :label="1">启用</el-radio>
          <el-radio :label="0">禁用</el-radio>
        </el-radio-group>
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input
          v-model="formData.remark"
          type="textarea"
          :rows="3"
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
import { ElMessage, ElDialog, ElForm, ElFormItem, ElInput, ElSelect, ElOption, ElButton, ElRow, ElCol, ElTreeSelect, ElCheckboxGroup, ElCheckbox, ElRadioGroup, ElRadio } from 'element-plus'
import { createUser, updateUser, checkUsernameUnique, checkEmailUnique, checkPhoneUnique } from '@/api/user'
import type { UserInfo, UserCreateParams, UserUpdateParams } from '@/api/user'

// Props
interface Props {
  modelValue: boolean
  userData?: UserInfo | null
  deptTreeData: any[]
  roleList: any[]
}

const props = withDefaults(defineProps<Props>(), {
  userData: null,
  deptTreeData: () => [],
  roleList: () => []
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
const formData = reactive<UserCreateParams & UserUpdateParams>({
  id: 0,
  username: '',
  password: '',
  confirmPassword: '',
  nickname: '',
  email: '',
  phone: '',
  gender: 0,
  deptId: 0,
  postId: undefined,
  roleIds: [],
  userType: 0,
  status: 1,
  remark: ''
})

// 岗位列表（模拟数据，实际应该从API获取）
const postList = ref([
  { id: 1, postName: '总经理' },
  { id: 2, postName: '部门经理' },
  { id: 3, postName: '技术员' },
  { id: 4, postName: '销售员' }
])

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const isEdit = computed(() => !!props.userData)

// 表单验证规则
const formRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' },
    { pattern: /^[a-zA-Z0-9_]+$/, message: '用户名只能包含字母、数字和下划线', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请再次输入密码', trigger: 'blur' },
    {
      validator: (rule: any, value: string, callback: Function) => {
        if (value !== formData.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { max: 30, message: '昵称长度不能超过30个字符', trigger: 'blur' }
  ],
  email: [
    { type: 'email', message: '请输入正确的邮箱地址', trigger: 'blur' }
  ],
  phone: [
    { pattern: /^1[3-9]\d{9}$/, message: '请输入正确的手机号', trigger: 'blur' }
  ],
  deptId: [
    { required: true, message: '请选择部门', trigger: 'change' }
  ],
  roleIds: [
    { required: true, message: '请选择角色', trigger: 'change' }
  ]
}

// 监听用户数据变化
watch(() => props.userData, (newVal) => {
  if (newVal) {
    // 编辑模式，填充表单数据
    Object.assign(formData, {
      id: newVal.id,
      username: newVal.username,
      nickname: newVal.nickname,
      email: newVal.email,
      phone: newVal.phone,
      gender: newVal.gender,
      deptId: newVal.deptId,
      postId: newVal.postId,
      roleIds: newVal.roleIds || [],
      userType: newVal.userType,
      status: newVal.status,
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
    username: '',
    password: '',
    confirmPassword: '',
    nickname: '',
    email: '',
    phone: '',
    gender: 0,
    deptId: 0,
    postId: undefined,
    roleIds: [],
    userType: 0,
    status: 1,
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
    // 检查用户名唯一性（仅新增时）
    if (!isEdit.value) {
      const usernameResponse = await checkUsernameUnique(formData.username)
      if (!usernameResponse.data) {
        ElMessage.error('用户名已存在')
        return false
      }
    }

    // 检查邮箱唯一性
    if (formData.email) {
      const emailResponse = await checkEmailUnique(formData.email, isEdit.value ? formData.id : undefined)
      if (!emailResponse.data) {
        ElMessage.error('邮箱已存在')
        return false
      }
    }

    // 检查手机号唯一性
    if (formData.phone) {
      const phoneResponse = await checkPhoneUnique(formData.phone, isEdit.value ? formData.id : undefined)
      if (!phoneResponse.data) {
        ElMessage.error('手机号已存在')
        return false
      }
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
      // 编辑用户
      const updateData: UserUpdateParams = {
        id: formData.id,
        nickname: formData.nickname,
        email: formData.email,
        phone: formData.phone,
        gender: formData.gender,
        status: formData.status,
        deptId: formData.deptId,
        postId: formData.postId,
        roleIds: formData.roleIds,
        userType: formData.userType,
        remark: formData.remark
      }

      const response = await updateUser(updateData)
      if (response.code === 200) {
        ElMessage.success('用户更新成功')
        emit('success')
      }
    } else {
      // 新增用户
      const createData: UserCreateParams = {
        username: formData.username,
        password: formData.password,
        confirmPassword: formData.confirmPassword,
        nickname: formData.nickname,
        email: formData.email,
        phone: formData.phone,
        gender: formData.gender,
        deptId: formData.deptId,
        postId: formData.postId,
        roleIds: formData.roleIds,
        userType: formData.userType,
        remark: formData.remark
      }

      const response = await createUser(createData)
      if (response.code === 200) {
        ElMessage.success('用户创建成功')
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

:deep(.el-checkbox-group) {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
}

:deep(.el-checkbox) {
  margin-right: 0;
}
</style>