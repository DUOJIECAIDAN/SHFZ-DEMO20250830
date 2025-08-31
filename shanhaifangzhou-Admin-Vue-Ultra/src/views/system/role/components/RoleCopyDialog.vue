<template>
  <el-dialog
    v-model="dialogVisible"
    title="复制角色"
    width="500px"
    :close-on-click-modal="false"
    @close="handleClose"
  >
    <div class="role-copy">
      <div class="source-role">
        <el-descriptions title="源角色信息" :column="2" border>
          <el-descriptions-item label="角色名称">{{ roleData?.roleName }}</el-descriptions-item>
          <el-descriptions-item label="角色编码">{{ roleData?.roleCode }}</el-descriptions-item>
          <el-descriptions-item label="角色类型">
            <el-tag :type="roleData?.roleType === 0 ? 'primary' : 'success'">
              {{ roleData?.roleType === 0 ? '系统角色' : '业务角色' }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="角色级别">
            <el-tag :type="getRoleLevelType(roleData?.roleLevel)">
              {{ getRoleLevelText(roleData?.roleLevel) }}
            </el-tag>
          </el-descriptions-item>
        </el-descriptions>
      </div>

      <div class="copy-form">
        <el-form
          ref="formRef"
          :model="formData"
          :rules="formRules"
          label-width="100px"
        >
          <el-form-item label="新角色名称" prop="newRoleName">
            <el-input
              v-model="formData.newRoleName"
              placeholder="请输入新角色名称"
            />
          </el-form-item>
          
          <el-form-item label="新角色编码" prop="newRoleCode">
            <el-input
              v-model="formData.newRoleCode"
              placeholder="请输入新角色编码"
            />
          </el-form-item>
          
          <el-form-item label="复制内容">
            <el-checkbox-group v-model="formData.copyItems">
              <el-checkbox label="menu">菜单权限</el-checkbox>
              <el-checkbox label="dept">数据权限</el-checkbox>
              <el-checkbox label="config">角色配置</el-checkbox>
            </el-checkbox-group>
          </el-form-item>
        </el-form>
      </div>

      <div class="copy-tip">
        <el-alert
          title="复制说明"
          type="warning"
          :closable="false"
          show-icon
        >
          <template #default>
            <div class="tip-content">
              <p>• 复制后的角色将继承源角色的权限配置</p>
              <p>• 新角色默认为业务角色，状态为启用</p>
              <p>• 角色编码必须唯一，不能与现有角色重复</p>
              <p>• 复制完成后可以对新角色进行个性化调整</p>
            </div>
          </template>
        </el-alert>
      </div>
    </div>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" :loading="loading" @click="handleSubmit">
          {{ loading ? '复制中...' : '确定复制' }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch, nextTick } from 'vue'
import { ElMessage, ElDialog, ElDescriptions, ElDescriptionsItem, ElTag, ElForm, ElFormItem, ElInput, ElCheckboxGroup, ElCheckbox, ElButton, ElAlert } from 'element-plus'
import { copyRole, checkRoleCodeUnique, checkRoleNameUnique } from '@/api/role'
import type { RoleInfo } from '@/api/role'

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
const formData = reactive({
  newRoleName: '',
  newRoleCode: '',
  copyItems: ['menu', 'dept', 'config']
})

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const roleData = computed(() => props.roleData)

// 表单验证规则
const formRules = {
  newRoleName: [
    { required: true, message: '请输入新角色名称', trigger: 'blur' },
    { max: 50, message: '角色名称长度不能超过50个字符', trigger: 'blur' }
  ],
  newRoleCode: [
    { required: true, message: '请输入新角色编码', trigger: 'blur' },
    { pattern: /^[A-Z_]+$/, message: '角色编码只能包含大写字母和下划线', trigger: 'blur' },
    { max: 50, message: '角色编码长度不能超过50个字符', trigger: 'blur' }
  ]
}

// 监听角色数据变化
watch(() => props.roleData, (newVal) => {
  if (newVal) {
    // 设置默认的新角色名称和编码
    formData.newRoleName = `${newVal.roleName}_副本`
    formData.newRoleCode = `${newVal.roleCode}_COPY`
  } else {
    resetForm()
  }
}, { immediate: true })

// 方法
const resetForm = () => {
  formData.newRoleName = ''
  formData.newRoleCode = ''
  formData.copyItems = ['menu', 'dept', 'config']
  
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
    const codeResponse = await checkRoleCodeUnique(formData.newRoleCode)
    if (!codeResponse.data) {
      ElMessage.error('角色编码已存在')
      return false
    }

    // 检查角色名称唯一性
    const nameResponse = await checkRoleNameUnique(formData.newRoleName)
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

    if (!roleData.value?.id) {
      ElMessage.error('源角色信息不完整')
      return
    }

    loading.value = true

    const response = await copyRole({
      sourceRoleId: roleData.value.id,
      newRoleName: formData.newRoleName,
      newRoleCode: formData.newRoleCode
    })

    if (response.code === 200) {
      ElMessage.success('角色复制成功')
      emit('success')
    }
  } catch (error) {
    console.error('复制角色失败:', error)
    ElMessage.error('复制角色失败')
  } finally {
    loading.value = false
  }
}

// 工具方法
const getRoleLevelType = (level?: number) => {
  const types = ['', 'danger', 'warning', 'info', 'success']
  return types[level || 4] || 'success'
}

const getRoleLevelText = (level?: number) => {
  const texts = ['', '超级管理员', '系统管理员', '普通管理员', '普通用户']
  return texts[level || 4] || '未知'
}
</script>

<style lang="scss" scoped>
.role-copy {
  .source-role {
    margin-bottom: 20px;
  }

  .copy-form {
    margin-bottom: 20px;
  }

  .copy-tip {
    .tip-content {
      p {
        margin: 5px 0;
        line-height: 1.5;
      }
    }
  }
}

.dialog-footer {
  text-align: right;
}
</style>