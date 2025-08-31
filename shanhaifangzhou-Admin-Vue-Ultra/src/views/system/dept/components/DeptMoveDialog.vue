<template>
  <el-dialog
    v-model="dialogVisible"
    title="移动部门"
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

      <el-form-item label="当前父部门">
        <el-input
          :value="currentParentName"
          readonly
          disabled
        />
      </el-form-item>

      <el-form-item label="目标父部门" prop="newParentId">
        <el-tree-select
          v-model="formData.newParentId"
          :data="deptTreeData"
          :props="treeProps"
          placeholder="请选择目标父部门"
          clearable
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="移动说明">
        <el-alert
          title="移动部门将改变部门的层级关系，请谨慎操作！"
          type="warning"
          :closable="false"
          show-icon
        />
      </el-form-item>
    </el-form>

    <template #footer>
      <div class="dialog-footer">
        <el-button @click="handleClose">取消</el-button>
        <el-button type="primary" :loading="loading" @click="handleSubmit">
          {{ loading ? '移动中...' : '确定' }}
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>

<script setup lang="ts">
import { ref, reactive, computed, watch } from 'vue'
import { ElMessage } from 'element-plus'
import { moveDept } from '@/api/dept'
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
  newParentId: undefined as number | undefined
})

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const sourceDeptName = computed(() => {
  return props.deptData?.deptName || ''
})

const currentParentName = computed(() => {
  if (!props.deptData?.parentId) return '顶级部门'
  const parentDept = findDeptById(deptTreeData.value, props.deptData.parentId)
  return parentDept?.deptName || '未知部门'
})

// 树形组件属性配置
const treeProps = {
  children: 'children',
  label: 'deptName',
  value: 'id'
}

// 表单验证规则
const formRules = {
  newParentId: [
    { required: true, message: '请选择目标父部门', trigger: 'change' }
  ]
}

// 生命周期
watch(() => props.modelValue, async (newVal) => {
  if (newVal && props.deptData) {
    await loadDeptTree()
    // 设置默认目标父部门为当前父部门
    formData.newParentId = props.deptData.parentId
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

// 根据ID查找部门
const findDeptById = (depts: DeptInfo[], id: number): DeptInfo | null => {
  for (const dept of depts) {
    if (dept.id === id) {
      return dept
    }
    if (dept.children && dept.children.length > 0) {
      const found = findDeptById(dept.children, id)
      if (found) return found
    }
  }
  return null
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  // 重置表单
  formData.newParentId = undefined
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

// 验证移动操作
const validateMove = () => {
  if (!props.deptData) {
    ElMessage.error('源部门数据不存在')
    return false
  }

  if (!formData.newParentId) {
    ElMessage.error('请选择目标父部门')
    return false
  }

  // 不能移动到自己的子部门下
  if (formData.newParentId === props.deptData.id) {
    ElMessage.error('不能将部门移动到自己的子部门下')
    return false
  }

  // 检查是否移动到自己的子部门的子部门下
  if (isDescendant(props.deptData.id, formData.newParentId)) {
    ElMessage.error('不能将部门移动到子部门的子部门下')
    return false
  }

  return true
}

// 检查是否为后代部门
const isDescendant = (deptId: number, targetId: number): boolean => {
  const dept = findDeptById(deptTreeData.value, deptId)
  if (!dept || !dept.children) return false

  for (const child of dept.children) {
    if (child.id === targetId) return true
    if (isDescendant(child.id, targetId)) return true
  }
  return false
}

// 提交表单
const handleSubmit = async () => {
  try {
    // 表单验证
    const isValid = await validateForm()
    if (!isValid) return

    // 验证移动操作
    if (!validateMove()) return

    loading.value = true

    const response = await moveDept({
      deptId: props.deptData!.id,
      newParentId: formData.newParentId!
    })

    if (response.code === 200) {
      ElMessage.success('部门移动成功')
      emit('success')
    }
  } catch (error) {
    console.error('移动失败:', error)
    ElMessage.error('移动失败')
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