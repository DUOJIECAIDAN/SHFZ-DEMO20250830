<template>
  <el-dialog
    v-model="dialogVisible"
    :title="isEdit ? '编辑菜单' : '新增菜单'"
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
          <el-form-item label="上级菜单">
            <el-tree-select
              v-model="formData.parentId"
              :data="menuTreeData"
              :props="treeProps"
              placeholder="请选择上级菜单"
              clearable
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单类型" prop="menuType">
            <el-radio-group v-model="formData.menuType">
              <el-radio label="M">目录</el-radio>
              <el-radio label="C">菜单</el-radio>
              <el-radio label="F">按钮</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="菜单名称" prop="menuName">
            <el-input
              v-model="formData.menuName"
              placeholder="请输入菜单名称"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单图标" prop="icon">
            <el-input
              v-model="formData.icon"
              placeholder="请输入菜单图标"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20" v-if="formData.menuType !== 'F'">
        <el-col :span="12">
          <el-form-item label="路由地址" prop="path">
            <el-input
              v-model="formData.path"
              placeholder="请输入路由地址"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="组件路径" prop="component">
            <el-input
              v-model="formData.component"
              placeholder="请输入组件路径"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="权限标识" prop="perms">
            <el-input
              v-model="formData.perms"
              placeholder="请输入权限标识"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单排序" prop="sort">
            <el-input-number
              v-model="formData.sort"
              :min="0"
              :max="9999"
              style="width: 100%"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="菜单状态" prop="status">
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

      <el-row :gutter="20" v-if="formData.menuType === 'C'">
        <el-col :span="12">
          <el-form-item label="是否外链" prop="isFrame">
            <el-radio-group v-model="formData.isFrame">
              <el-radio :label="0">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="是否缓存" prop="isCache">
            <el-radio-group v-model="formData.isCache">
              <el-radio :label="0">否</el-radio>
              <el-radio :label="1">是</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row :gutter="20">
        <el-col :span="12">
          <el-form-item label="菜单标题" prop="title">
            <el-input
              v-model="formData.title"
              placeholder="请输入菜单标题"
            />
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="菜单别名" prop="alias">
            <el-input
              v-model="formData.alias"
              placeholder="请输入菜单别名"
            />
          </el-form-item>
        </el-col>
      </el-row>

      <el-form-item label="菜单描述" prop="description">
        <el-input
          v-model="formData.description"
          type="textarea"
          :rows="3"
          placeholder="请输入菜单描述"
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
import { createMenu, updateMenu, checkMenuNameUnique, checkPermsUnique, checkPathUnique, checkComponentUnique } from '@/api/menu'
import { getMenuTree } from '@/api/menu'
import type { MenuInfo, MenuCreateParams, MenuUpdateParams } from '@/api/menu'

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
const formData = reactive<MenuCreateParams & MenuUpdateParams>({
  id: 0,
  parentId: undefined,
  menuName: '',
  menuType: 'M',
  path: '',
  component: '',
  query: '',
  isFrame: 0,
  isCache: 0,
  status: 1,
  visible: 1,
  perms: '',
  icon: '',
  sort: 0,
  title: '',
  alias: '',
  description: '',
  external: 0,
  newWindow: 0,
  affix: 0,
  hideBreadcrumb: 0,
  hideTab: 0,
  hideMenu: 0,
  hideChildren: 0,
  hideSearch: 0,
  hideFooter: 0,
  hideSetting: 0,
  hideI18n: 0,
  hideFullscreen: 0,
  hideRefresh: 0,
  hideHelp: 0,
  hideNotice: 0,
  hideUserInfo: 0,
  hideLogout: 0,
  remark: ''
})

// 计算属性
const dialogVisible = computed({
  get: () => props.modelValue,
  set: (value) => emit('update:modelValue', value)
})

const isEdit = computed(() => !!props.menuData)

// 树形组件属性配置
const treeProps = {
  children: 'children',
  label: 'menuName',
  value: 'id'
}

// 表单验证规则
const formRules = {
  menuName: [
    { required: true, message: '请输入菜单名称', trigger: 'blur' },
    { max: 50, message: '菜单名称长度不能超过50个字符', trigger: 'blur' }
  ],
  menuType: [
    { required: true, message: '请选择菜单类型', trigger: 'change' }
  ],
  path: [
    { max: 200, message: '路由地址长度不能超过200个字符', trigger: 'blur' }
  ],
  component: [
    { max: 255, message: '组件路径长度不能超过255个字符', trigger: 'blur' }
  ],
  perms: [
    { max: 100, message: '权限标识长度不能超过100个字符', trigger: 'blur' }
  ],
  icon: [
    { max: 100, message: '菜单图标长度不能超过100个字符', trigger: 'blur' }
  ],
  sort: [
    { required: true, message: '请输入菜单排序', trigger: 'blur' }
  ],
  title: [
    { max: 50, message: '菜单标题长度不能超过50个字符', trigger: 'blur' }
  ],
  alias: [
    { max: 50, message: '菜单别名长度不能超过50个字符', trigger: 'blur' }
  ],
  description: [
    { max: 200, message: '菜单描述长度不能超过200个字符', trigger: 'blur' }
  ],
  remark: [
    { max: 500, message: '备注长度不能超过500个字符', trigger: 'blur' }
  ]
}

// 生命周期
watch(() => props.modelValue, async (newVal) => {
  if (newVal) {
    await loadMenuTree()
    if (props.menuData) {
      // 编辑模式，填充表单数据
      Object.assign(formData, {
        id: props.menuData.id,
        parentId: props.menuData.parentId,
        menuName: props.menuData.menuName,
        menuType: props.menuData.menuType,
        path: props.menuData.path || '',
        component: props.menuData.component || '',
        query: props.menuData.query || '',
        isFrame: props.menuData.isFrame,
        isCache: props.menuData.isCache,
        status: props.menuData.status,
        visible: props.menuData.visible,
        perms: props.menuData.perms || '',
        icon: props.menuData.icon || '',
        sort: props.menuData.sort,
        title: props.menuData.title || '',
        alias: props.menuData.alias || '',
        description: props.menuData.description || '',
        remark: props.menuData.remark || ''
      })
    } else {
      // 新增模式，重置表单数据
      resetForm()
    }
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

// 重置表单
const resetForm = () => {
  Object.assign(formData, {
    id: 0,
    parentId: undefined,
    menuName: '',
    menuType: 'M',
    path: '',
    component: '',
    query: '',
    isFrame: 0,
    isCache: 0,
    status: 1,
    visible: 1,
    perms: '',
    icon: '',
    sort: 0,
    title: '',
    alias: '',
    description: '',
    external: 0,
    newWindow: 0,
    affix: 0,
    hideBreadcrumb: 0,
    hideTab: 0,
    hideMenu: 0,
    hideChildren: 0,
    hideSearch: 0,
    hideFooter: 0,
    hideSetting: 0,
    hideI18n: 0,
    hideFullscreen: 0,
    hideRefresh: 0,
    hideHelp: 0,
    hideNotice: 0,
    hideUserInfo: 0,
    hideLogout: 0,
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
    // 检查菜单名称唯一性
    if (formData.menuName) {
      const nameResponse = await checkMenuNameUnique(
        formData.menuName,
        formData.parentId,
        isEdit.value ? formData.id : undefined
      )
      if (!nameResponse.data) {
        ElMessage.error('同级菜单下菜单名称已存在')
        return false
      }
    }

    // 检查权限标识唯一性
    if (formData.perms) {
      const permsResponse = await checkPermsUnique(
        formData.perms,
        isEdit.value ? formData.id : undefined
      )
      if (!permsResponse.data) {
        ElMessage.error('权限标识已存在')
        return false
      }
    }

    // 检查路径唯一性
    if (formData.path) {
      const pathResponse = await checkPathUnique(
        formData.path,
        isEdit.value ? formData.id : undefined
      )
      if (!pathResponse.data) {
        ElMessage.error('路由地址已存在')
        return false
      }
    }

    // 检查组件路径唯一性
    if (formData.component) {
      const componentResponse = await checkComponentUnique(
        formData.component,
        isEdit.value ? formData.id : undefined
      )
      if (!componentResponse.data) {
        ElMessage.error('组件路径已存在')
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
      // 编辑菜单
      const updateData: MenuUpdateParams = {
        id: formData.id,
        parentId: formData.parentId,
        menuName: formData.menuName,
        menuType: formData.menuType,
        path: formData.path,
        component: formData.component,
        query: formData.query,
        isFrame: formData.isFrame,
        isCache: formData.isCache,
        status: formData.status,
        visible: formData.visible,
        perms: formData.perms,
        icon: formData.icon,
        sort: formData.sort,
        title: formData.title,
        alias: formData.alias,
        description: formData.description,
        remark: formData.remark
      }

      const response = await updateMenu(updateData)
      if (response.code === 200) {
        ElMessage.success('菜单更新成功')
        emit('success')
      }
    } else {
      // 新增菜单
      const createData: MenuCreateParams = {
        parentId: formData.parentId,
        menuName: formData.menuName,
        menuType: formData.menuType,
        path: formData.path,
        component: formData.component,
        query: formData.query,
        isFrame: formData.isFrame,
        isCache: formData.isCache,
        status: formData.status,
        visible: formData.visible,
        perms: formData.perms,
        icon: formData.icon,
        sort: formData.sort,
        title: formData.title,
        alias: formData.alias,
        description: formData.description,
        remark: formData.remark
      }

      const response = await createMenu(createData)
      if (response.code === 200) {
        ElMessage.success('菜单创建成功')
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