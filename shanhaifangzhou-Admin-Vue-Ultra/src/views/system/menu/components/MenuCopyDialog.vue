<template>
  <el-dialog
    v-model="dialogVisible"
    title="复制菜单"
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
      <el-form-item label="源菜单">
        <el-input
          :value="sourceMenuName"
          readonly
          disabled
        />
      </el-form-item>

      <el-form-item label="上级菜单">
        <el-tree-select
          v-model="formData.newParentId"
          :data="menuTreeData"
          :props="treeProps"
          placeholder="请选择上级菜单"
          clearable
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="新菜单名称" prop="newMenuName">
        <el-input
          v-model="formData.newMenuName"
          placeholder="请输入新菜单名称"
        />
      </el-form-item>

      <el-form-item label="复制选项">
        <el-checkbox-group v-model="formData.copyOptions">
          <el-checkbox label="menu">菜单配置</el-checkbox>
          <el-checkbox label="perms">权限标识</el-checkbox>
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
import { copyMenu, checkMenuNameUnique } from '@/api/menu'
import { getMenuTree } from '@/api/menu'
import type { MenuInfo } from '@/api/menu'

// Props
interface Props {
  modelValue: boolean
  menuData?: MenuInfo | null
}

const props = withDefaults(defineProps<Props>(), {
  menuData: null
})

// Emits
const emit = defineEmits<{
  'update:modelValue': [value: boolean]
  'success': []
}>()

// 响应式数据
const formRef = ref()
const loading = ref(false)
const menuTreeData = ref<MenuInfo[]>([])

// 表单数据
const formData = reactive({
  newParentId: undefined as number | undefined,
  newMenuName: '',
  copyOptions: ['menu', 'perms', 'ui'] as string[]
})

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const sourceMenuName = computed(() => {
  return props.menuData?.menuName || ''
})

// 树形组件属性配置
const treeProps = {
  children: 'children',
  label: 'menuName',
  value: 'id'
}

// 表单验证规则
const formRules = {
  newMenuName: [
    { required: true, message: '请输入新菜单名称', trigger: 'blur' },
    { max: 50, message: '菜单名称长度不能超过50个字符', trigger: 'blur' }
  ]
}

// 生命周期
watch(() => props.modelValue, async (newVal) => {
  if (newVal && props.menuData) {
    await loadMenuTree()
    // 设置默认父菜单为源菜单的父菜单
    formData.newParentId = props.menuData.parentId
    // 设置默认新菜单名称
    formData.newMenuName = `${props.menuData.menuName}_copy`
  }
}, { immediate: true })

// 加载菜单树
const loadMenuTree = async () => {
  try {
    const response = await getMenuTree()
    if (response.code === 200) {
      menuTreeData.value = response.data || []
    }
  } catch (error) {
    console.error('加载菜单树失败:', error)
    ElMessage.error('加载菜单树失败')
  }
}

// 关闭对话框
const handleClose = () => {
  dialogVisible.value = false
  // 重置表单
  formData.newParentId = undefined
  formData.newMenuName = ''
  formData.copyOptions = ['menu', 'perms', 'ui']
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
    if (formData.newMenuName) {
      const response = await checkMenuNameUnique(
        formData.newMenuName,
        formData.newParentId,
        undefined
      )
      if (!response.data) {
        ElMessage.error('同级菜单下菜单名称已存在')
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

    if (!props.menuData) {
      ElMessage.error('源菜单数据不存在')
      return
    }

    loading.value = true

    const response = await copyMenu({
      sourceMenuId: props.menuData.id,
      newMenuName: formData.newMenuName,
      newParentId: formData.newParentId
    })

    if (response.code === 200) {
      ElMessage.success('菜单复制成功')
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