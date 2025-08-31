<template>
  <div class="app-layout">
    <!-- 侧边栏 -->
    <div class="sidebar" :class="{ collapsed: isCollapsed }">
      <div class="sidebar-header">
        <div class="logo">
          <img v-if="!isCollapsed" src="@/assets/logo.png" alt="Logo" />
          <img v-else src="@/assets/logo-mini.png" alt="Logo" />
        </div>
      </div>
      
      <div class="sidebar-menu">
        <el-menu
          :default-active="activeMenu"
          :collapse="isCollapsed"
          :unique-opened="true"
          router
        >
          <sidebar-menu-item
            v-for="menu in menuList"
            :key="menu.path"
            :menu="menu"
          />
        </el-menu>
      </div>
    </div>

    <!-- 主内容区 -->
    <div class="main-content" :class="{ 'sidebar-collapsed': isCollapsed }">
      <!-- 顶部导航栏 -->
      <div class="top-navbar">
        <div class="navbar-left">
          <el-button
            type="text"
            @click="toggleSidebar"
            class="collapse-btn"
          >
            <el-icon>
              <component :is="isCollapsed ? 'Expand' : 'Fold'" />
            </el-icon>
          </el-button>
          
          <el-breadcrumb separator="/">
            <el-breadcrumb-item
              v-for="item in breadcrumbList"
              :key="item.path"
              :to="item.path"
            >
              {{ item.title }}
            </el-breadcrumb-item>
          </el-breadcrumb>
        </div>
        
        <div class="navbar-right">
          <!-- 全屏按钮 -->
          <el-button
            type="text"
            @click="toggleFullscreen"
            class="fullscreen-btn"
          >
            <el-icon>
              <component :is="isFullscreen ? 'Aim' : 'FullScreen'" />
            </el-icon>
          </el-button>
          
          <!-- 通知按钮 -->
          <el-badge :value="unreadCount" :hidden="unreadCount === 0">
            <el-button
              type="text"
              @click="showNotifications"
              class="notification-btn"
            >
              <el-icon><Bell /></el-icon>
            </el-button>
          </el-badge>
          
          <!-- 用户信息 -->
          <el-dropdown @command="handleUserCommand">
            <div class="user-info">
              <el-avatar :size="32" :src="userInfo.avatar" />
              <span class="username">{{ userInfo.nickname || userInfo.username }}</span>
              <el-icon><ArrowDown /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="profile">
                  <el-icon><User /></el-icon>
                  个人中心
                </el-dropdown-item>
                <el-dropdown-item command="settings">
                  <el-icon><Setting /></el-icon>
                  系统设置
                </el-dropdown-item>
                <el-dropdown-item divided command="logout">
                  <el-icon><SwitchButton /></el-icon>
                  退出登录
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </div>
      
      <!-- 标签页导航 -->
      <div class="tabs-nav" v-if="showTabs">
        <el-tabs
          v-model="activeTab"
          type="card"
          closable
          @tab-remove="removeTab"
          @tab-click="handleTabClick"
        >
          <el-tab-pane
            v-for="tab in tabList"
            :key="tab.path"
            :label="tab.title"
            :name="tab.path"
          />
        </el-tabs>
      </div>
      
      <!-- 主内容区域 -->
      <div class="main-container">
        <router-view v-slot="{ Component, route }">
          <transition name="fade" mode="out-in">
            <keep-alive :include="cachedViews">
              <component :is="Component" :key="route.path" />
            </keep-alive>
          </transition>
        </router-view>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, watch, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { useUserStore } from '@/store/user'
import { useAppStore } from '@/store/app'
import SidebarMenuItem from './components/SidebarMenuItem.vue'
import {
  Expand,
  Fold,
  FullScreen,
  Aim,
  Bell,
  ArrowDown,
  User,
  Setting,
  SwitchButton
} from '@element-plus/icons-vue'

// 响应式数据
const isCollapsed = ref(false)
const isFullscreen = ref(false)
const unreadCount = ref(0)
const activeTab = ref('')
const showTabs = ref(true)

// Store
const userStore = useUserStore()
const appStore = useAppStore()

// Router
const route = useRoute()
const router = useRouter()

// 计算属性
const userInfo = computed(() => userStore.userInfo)
const menuList = computed(() => userStore.menuList)
const activeMenu = computed(() => route.path)
const breadcrumbList = computed(() => {
  const matched = route.matched.filter(item => item.meta && item.meta.title)
  return matched.map(item => ({
    title: item.meta?.title,
    path: item.path
  }))
})
const tabList = computed(() => appStore.tabList)
const cachedViews = computed(() => appStore.cachedViews)

// 生命周期
onMounted(() => {
  initLayout()
})

// 监听路由变化
watch(
  () => route.path,
  (newPath) => {
    addTab(newPath)
    activeTab.value = newPath
  },
  { immediate: true }
)

// 初始化布局
const initLayout = () => {
  // 从localStorage恢复侧边栏状态
  const collapsed = localStorage.getItem('sidebar-collapsed')
  if (collapsed) {
    isCollapsed.value = JSON.parse(collapsed)
  }
  
  // 从localStorage恢复标签页状态
  const tabs = localStorage.getItem('tab-list')
  if (tabs) {
    appStore.setTabList(JSON.parse(tabs))
  }
}

// 切换侧边栏
const toggleSidebar = () => {
  isCollapsed.value = !isCollapsed.value
  localStorage.setItem('sidebar-collapsed', JSON.stringify(isCollapsed.value))
}

// 切换全屏
const toggleFullscreen = () => {
  if (!document.fullscreenElement) {
    document.documentElement.requestFullscreen()
    isFullscreen.value = true
  } else {
    document.exitFullscreen()
    isFullscreen.value = false
  }
}

// 显示通知
const showNotifications = () => {
  // TODO: 实现通知面板
  ElMessage.info('通知功能开发中...')
}

// 处理用户命令
const handleUserCommand = async (command: string) => {
  switch (command) {
    case 'profile':
      router.push('/profile')
      break
    case 'settings':
      router.push('/settings')
      break
    case 'logout':
      await handleLogout()
      break
  }
}

// 处理退出登录
const handleLogout = async () => {
  try {
    await ElMessageBox.confirm(
      '确定要退出登录吗？',
      '退出确认',
      {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }
    )
    
    await userStore.logout()
    router.push('/login')
    ElMessage.success('退出登录成功')
  } catch (error) {
    if (error !== 'cancel') {
      console.error('退出登录失败:', error)
      ElMessage.error('退出登录失败')
    }
  }
}

// 添加标签页
const addTab = (path: string) => {
  const route = router.resolve(path)
  const title = route.meta?.title || '未知页面'
  
  appStore.addTab({
    path,
    title,
    name: route.name as string
  })
}

// 移除标签页
const removeTab = (targetPath: string) => {
  appStore.removeTab(targetPath)
  
  // 如果关闭的是当前标签页，跳转到其他标签页
  if (activeTab.value === targetPath) {
    const tabs = appStore.tabList
    if (tabs.length > 0) {
      const lastTab = tabs[tabs.length - 1]
      router.push(lastTab.path)
    }
  }
}

// 处理标签页点击
const handleTabClick = (tab: any) => {
  router.push(tab.name)
}
</script>

<style lang="scss" scoped>
.app-layout {
  display: flex;
  height: 100vh;
  overflow: hidden;

  .sidebar {
    width: 240px;
    background: #001529;
    transition: width 0.3s;
    display: flex;
    flex-direction: column;

    &.collapsed {
      width: 64px;
    }

    .sidebar-header {
      height: 60px;
      display: flex;
      align-items: center;
      justify-content: center;
      border-bottom: 1px solid #1f2937;

      .logo {
        img {
          height: 32px;
        }
      }
    }

    .sidebar-menu {
      flex: 1;
      overflow-y: auto;

      :deep(.el-menu) {
        border: none;
        background: transparent;

        .el-menu-item,
        .el-sub-menu__title {
          color: #a6adb4;
          
          &:hover {
            background: #1890ff;
            color: white;
          }
        }

        .el-menu-item.is-active {
          background: #1890ff;
          color: white;
        }
      }
    }
  }

  .main-content {
    flex: 1;
    display: flex;
    flex-direction: column;
    transition: margin-left 0.3s;

    &.sidebar-collapsed {
      margin-left: 0;
    }

    .top-navbar {
      height: 60px;
      background: white;
      border-bottom: 1px solid #e8e8e8;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 20px;

      .navbar-left {
        display: flex;
        align-items: center;
        gap: 20px;

        .collapse-btn {
          font-size: 18px;
          color: #666;
        }
      }

      .navbar-right {
        display: flex;
        align-items: center;
        gap: 16px;

        .fullscreen-btn,
        .notification-btn {
          font-size: 18px;
          color: #666;
        }

        .user-info {
          display: flex;
          align-items: center;
          gap: 8px;
          cursor: pointer;
          padding: 4px 8px;
          border-radius: 4px;
          transition: background 0.3s;

          &:hover {
            background: #f5f5f5;
          }

          .username {
            font-size: 14px;
            color: #333;
          }
        }
      }
    }

    .tabs-nav {
      background: white;
      border-bottom: 1px solid #e8e8e8;
      padding: 0 20px;

      :deep(.el-tabs__header) {
        margin: 0;
      }
    }

    .main-container {
      flex: 1;
      padding: 20px;
      background: #f0f2f5;
      overflow-y: auto;
    }
  }
}

// 过渡动画
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.3s;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
