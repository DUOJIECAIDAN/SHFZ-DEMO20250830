<template>
  <el-dialog
    v-model="dialogVisible"
    title="复制部门"
    width="600px"
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
      <el-form-item label="源部门">
        <el-input
          :value="sourceDeptName"
          readonly
          disabled
        />
      </el-form-item>

      <el-form-item label="上级部门">
        <el-tree-select
          v-model="formData.newParentId"
          :data="deptTreeData"
          :props="treeProps"
          placeholder="请选择上级部门"
          clearable
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="新部门名称" prop="newDeptName">
        <el-input
          v-model="formData.newDeptName"
          placeholder="请输入新部门名称"
        />
      </el-form-item>

      <el-form-item label="复制选项">
        <el-checkbox-group v-model="formData.copyOptions">
          <el-checkbox label="config">部门配置</el-checkbox>
          <el-checkbox label="leader">负责人信息</el-checkbox>
          <el-checkbox label="contact">联系方式</el-checkbox>
          <el-checkbox label="ui">UI设置</el-checkbox>
        </el-checkbox-group>
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" :loading="loading" @click="handleSubmit">
          {{ loading ? '复制中...' : '确定' }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { copyDept, checkDeptNameUnique } from '@/api/dept'
import { getDeptTree } from '@/api/dept'
import type { DeptInfo } from '@/api/dept'

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
const formData = reactive({
  newParentId: undefined as number | undefined,
  newDeptName: '',
  copyOptions: ['config', 'leader', 'contact', 'ui'] as string[]
})

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const sourceDeptName = computed(() => {
  return props.deptData?.deptName || ''
})

// 树形组件属性配置
const treeProps = {
  children: 'children',
  label: 'deptName',
  value: 'id'
}

// 表单验证规则
const formRules = {
  newDeptName: [
    { required: true, message: '请输入新部门名称', trigger: 'blur' },
    { max: 50, message: '部门名称长度不能超过50个字符', trigger: 'blur' }
  ]
}

// 生命周期
watch(() => props.modelValue, async (newVal) => {
  if (newVal && props.deptData) {
    await loadDeptTree()
    // 设置默认父部门为源部门的父部门
    formData.newParentId = props.deptData.parentId
    // 设置默认新部门名称
    formData.newDeptName = `${props.deptData.deptName}_copy`
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

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  // 重置表单
  formData.newParentId = undefined
  formData.newDeptName = ''
  formData.copyOptions = ['config', 'leader', 'contact', 'ui']
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
    if (formData.newDeptName) {
      const response = await checkDeptNameUnique(
        formData.newDeptName,
        formData.newParentId,
        undefined
      )
      if (!response.data) {
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

    if (!props.deptData) {
      ElMessage.error('源部门数据不存在')
      return
    }

    loading.value = true

    const response = await copyDept({
      sourceDeptId: props.deptData.id,
      newDeptName: formData.newDeptName,
      newParentId: formData.newParentId
    })

    if (response.code === 200) {
      ElMessage.success('部门复制成功')
      emit('success')
    }
  } catch (error) {
    console.error('复制失败:', error)
    ElMessage.error('复制失败')
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