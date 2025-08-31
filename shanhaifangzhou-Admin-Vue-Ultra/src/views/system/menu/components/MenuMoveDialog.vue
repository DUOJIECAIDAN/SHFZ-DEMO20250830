<template>
  <el-dialog
    v-model="dialogVisible"
    title="移动菜单"
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

      <el-form-item label="当前父菜单">
        <el-input
          :value="currentParentName"
          readonly
          disabled
        />
      </el-form-item>

      <el-form-item label="目标父菜单" prop="newParentId">
        <el-tree-select
          v-model="formData.newParentId"
          :data="menuTreeData"
          :props="treeProps"
          placeholder="请选择目标父菜单"
          clearable
          style="width: 100%"
        />
      </el-form-item>

      <el-form-item label="移动说明">
        <el-alert
          title="移动菜单将改变菜单的层级关系，请谨慎操作！"
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
import { moveMenu } from '@/api/menu'
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
  newParentId: undefined as number | undefined
})

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const sourceMenuName = computed(() => {
  return props.menuData?.menuName || ''
})

const currentParentName = computed(() => {
  if (!props.menuData?.parentId) return '顶级菜单'
  const parentMenu = findMenuById(menuTreeData.value, props.menuData.parentId)
  return parentMenu?.menuName || '未知菜单'
})

// 树形组件属性配置
const treeProps = {
  children: 'children',
  label: 'menuName',
  value: 'id'
}

// 表单验证规则
const formRules = {
  newParentId: [
    { required: true, message: '请选择目标父菜单', trigger: 'change' }
  ]
}

// 生命周期
watch(() => props.modelValue, async (newVal) => {
  if (newVal && props.menuData) {
    await loadMenuTree()
    // 设置默认目标父菜单为当前父菜单
    formData.newParentId = props.menuData.parentId
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

// 根据ID查找菜单
const findMenuById = (menus: MenuInfo[], id: number): MenuInfo | null => {
  for (const menu of menus) {
    if (menu.id === id) {
      return menu
    }
    if (menu.children && menu.children.length > 0) {
      const found = findMenuById(menu.children, id)
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
  if (!props.menuData) {
    ElMessage.error('源菜单数据不存在')
    return false
  }

  if (!formData.newParentId) {
    ElMessage.error('请选择目标父菜单')
    return false
  }

  // 不能移动到自己的子菜单下
  if (formData.newParentId === props.menuData.id) {
    ElMessage.error('不能将菜单移动到自己的子菜单下')
    return false
  }

  // 检查是否移动到自己的子菜单的子菜单下
  if (isDescendant(props.menuData.id, formData.newParentId)) {
    ElMessage.error('不能将菜单移动到子菜单的子菜单下')
    return false
  }

  return true
}

// 检查是否为后代菜单
const isDescendant = (menuId: number, targetId: number): boolean => {
  const menu = findMenuById(menuTreeData.value, menuId)
  if (!menu || !menu.children) return false

  for (const child of menu.children) {
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

    const response = await moveMenu({
      menuId: props.menuData!.id,
      newParentId: formData.newParentId!
    })

    if (response.code === 200) {
      ElMessage.success('菜单移动成功')
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