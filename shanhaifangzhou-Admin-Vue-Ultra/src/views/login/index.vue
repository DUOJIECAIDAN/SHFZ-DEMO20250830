<template>
  <div class="login-container">
    <div class="login-background">
      <div class="login-background-overlay"></div>
    </div>
    
    <div class="login-content">
      <div class="login-header">
        <div class="login-logo">
          <img src="@/assets/images/logo.png" alt="Logo" class="logo-image" />
          <h1 class="logo-title">山海方舟</h1>
        </div>
        <p class="logo-subtitle">企业级中后台管理系统</p>
      </div>
      
      <div class="login-form-container">
        <div class="login-form-header">
          <h2 class="form-title">用户登录</h2>
          <p class="form-subtitle">请输入您的账号和密码</p>
        </div>
        
        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
          @keyup.enter="handleLogin"
        >
          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="请输入用户名"
              size="large"
              prefix-icon="User"
              clearable
            />
          </el-form-item>
          
          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              type="password"
              placeholder="请输入密码"
              size="large"
              prefix-icon="Lock"
              show-password
              clearable
            />
          </el-form-item>
          
          <el-form-item prop="captcha" v-if="showCaptcha">
            <div class="captcha-container">
              <el-input
                v-model="loginForm.captcha"
                placeholder="请输入验证码"
                size="large"
                prefix-icon="Key"
                clearable
                class="captcha-input"
              />
              <div class="captcha-image" @click="refreshCaptcha">
                <img :src="captchaImage" alt="验证码" />
                <div class="captcha-refresh">
                  <el-icon><Refresh /></el-icon>
                </div>
              </div>
            </div>
          </el-form-item>
          
          <el-form-item>
            <el-checkbox v-model="loginForm.rememberMe">记住我</el-checkbox>
            <el-link type="primary" class="forgot-password">忘记密码？</el-link>
          </el-form-item>
          
          <el-form-item>
            <el-button
              type="primary"
              size="large"
              class="login-button"
              :loading="loading"
              @click="handleLogin"
            >
              {{ loading ? '登录中...' : '登录' }}
            </el-button>
          </el-form-item>
        </el-form>
        
        <div class="login-footer">
          <p class="footer-text">
            © 2024 山海方舟管理系统. All rights reserved.
          </p>
        </div>
      </div>
    </div>
    
    <!-- 主题切换按钮 -->
    <div class="theme-switch-container">
      <div
        class="theme-switch"
        :class="{ dark: isDarkTheme }"
        @click="toggleTheme"
      >
        <el-icon class="icon sun"><Sunny /></el-icon>
        <el-icon class="icon moon"><Moon /></el-icon>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage, ElForm, ElFormItem, ElInput, ElButton, ElCheckbox, ElLink, ElIcon } from 'element-plus'
import { User, Lock, Key, Refresh, Sunny, Moon } from '@element-plus/icons-vue'
import { useUserStore } from '@/store/modules/user'
import { getCaptcha } from '@/api/auth'
import type { LoginForm } from '@/types/auth'

// 路由相关
const router = useRouter()
const route = useRoute()

// 用户状态管理
const userStore = useUserStore()

// 表单引用
const loginFormRef = ref<InstanceType<typeof ElForm>>()

// 登录表单数据
const loginForm = reactive<LoginForm>({
  username: '',
  password: '',
  captcha: '',
  rememberMe: false
})

// 表单验证规则
const loginRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度在 3 到 20 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度在 6 到 20 个字符', trigger: 'blur' }
  ],
  captcha: [
    { required: true, message: '请输入验证码', trigger: 'blur' },
    { len: 4, message: '验证码长度为 4 位', trigger: 'blur' }
  ]
}

// 状态变量
const loading = ref(false)
const showCaptcha = ref(false)
const captchaImage = ref('')
const captchaId = ref('')
const isDarkTheme = ref(false)

// 获取验证码
const fetchCaptcha = async () => {
  try {
    const response = await getCaptcha()
    if (response.code === 200) {
      captchaImage.value = response.data.captchaImage
      captchaId.value = response.data.captchaId
      showCaptcha.value = true
    }
  } catch (error) {
    console.error('获取验证码失败:', error)
  }
}

// 刷新验证码
const refreshCaptcha = () => {
  fetchCaptcha()
  loginForm.captcha = ''
}

// 处理登录
const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  try {
    await loginFormRef.value.validate()
    
    loading.value = true
    
    // 如果有验证码，添加到登录数据中
    const loginData = { ...loginForm }
    if (showCaptcha.value) {
      loginData.captcha = loginForm.captcha
    }
    
    // 调用登录接口
    const result = await userStore.loginAction(loginData)
    
    if (result) {
      ElMessage.success('登录成功')
      
      // 跳转到目标页面或首页
      const redirect = route.query.redirect as string
      router.push(redirect || '/')
    }
  } catch (error: any) {
    console.error('登录失败:', error)
    
    // 登录失败时显示验证码
    if (!showCaptcha.value) {
      showCaptcha.value = true
      await fetchCaptcha()
    }
    
    // 清空验证码
    loginForm.captcha = ''
    
    ElMessage.error(error.message || '登录失败，请重试')
  } finally {
    loading.value = false
  }
}

// 切换主题
const toggleTheme = () => {
  isDarkTheme.value = !isDarkTheme.value
  const theme = isDarkTheme.value ? 'dark' : 'light'
  document.documentElement.setAttribute('data-theme', theme)
  localStorage.setItem('theme', theme)
}

// 初始化主题
const initTheme = () => {
  const savedTheme = localStorage.getItem('theme') || 'light'
  isDarkTheme.value = savedTheme === 'dark'
  document.documentElement.setAttribute('data-theme', savedTheme)
}

// 组件挂载时执行
onMounted(() => {
  initTheme()
  
  // 如果用户已登录，直接跳转
  if (userStore.token) {
    router.push('/')
    return
  }
  
  // 获取验证码
  fetchCaptcha()
})
</script>

<style lang="scss" scoped>
.login-container {
  position: relative;
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  overflow: hidden;
}

.login-background {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background-image: url('@/assets/images/login-bg.jpg');
  background-size: cover;
  background-position: center;
  z-index: 1;
}

.login-background-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.4);
  z-index: 2;
}

.login-content {
  position: relative;
  z-index: 3;
  display: flex;
  flex-direction: column;
  align-items: center;
  max-width: 400px;
  width: 90%;
}

.login-header {
  text-align: center;
  margin-bottom: 2rem;
}

.login-logo {
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 1rem;
}

.logo-image {
  width: 48px;
  height: 48px;
  margin-right: 1rem;
}

.logo-title {
  font-size: 2rem;
  font-weight: 700;
  color: white;
  margin: 0;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.logo-subtitle {
  font-size: 1rem;
  color: rgba(255, 255, 255, 0.8);
  margin: 0;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.3);
}

.login-form-container {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 2rem;
  width: 100%;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.login-form-header {
  text-align: center;
  margin-bottom: 2rem;
}

.form-title {
  font-size: 1.5rem;
  font-weight: 600;
  color: var(--text-color-primary);
  margin: 0 0 0.5rem 0;
}

.form-subtitle {
  font-size: 0.875rem;
  color: var(--text-color-secondary);
  margin: 0;
}

.login-form {
  .el-form-item {
    margin-bottom: 1.5rem;
  }
  
  .el-input {
    :deep(.el-input__wrapper) {
      border-radius: 8px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
      border: 1px solid var(--border-color-light);
      transition: all 0.3s ease;
      
      &:hover {
        border-color: var(--primary-color);
        box-shadow: 0 4px 8px rgba(64, 158, 255, 0.2);
      }
      
      &.is-focus {
        border-color: var(--primary-color);
        box-shadow: 0 0 0 2px rgba(64, 158, 255, 0.2);
      }
    }
  }
}

.captcha-container {
  display: flex;
  gap: 1rem;
  align-items: center;
}

.captcha-input {
  flex: 1;
}

.captcha-image {
  position: relative;
  width: 120px;
  height: 40px;
  border-radius: 8px;
  overflow: hidden;
  cursor: pointer;
  border: 1px solid var(--border-color-light);
  
  img {
    width: 100%;
    height: 100%;
    object-fit: cover;
  }
  
  .captcha-refresh {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background: rgba(0, 0, 0, 0.5);
    display: flex;
    align-items: center;
    justify-content: center;
    opacity: 0;
    transition: opacity 0.3s ease;
    
    .el-icon {
      color: white;
      font-size: 1.2rem;
    }
  }
  
  &:hover .captcha-refresh {
    opacity: 1;
  }
}

.login-button {
  width: 100%;
  height: 48px;
  border-radius: 8px;
  font-size: 1rem;
  font-weight: 600;
  background: linear-gradient(135deg, var(--primary-color) 0%, #66b1ff 100%);
  border: none;
  box-shadow: 0 4px 12px rgba(64, 158, 255, 0.4);
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-2px);
    box-shadow: 0 6px 20px rgba(64, 158, 255, 0.6);
  }
  
  &:active {
    transform: translateY(0);
  }
}

.forgot-password {
  float: right;
  font-size: 0.875rem;
}

.login-footer {
  text-align: center;
  margin-top: 2rem;
  padding-top: 1rem;
  border-top: 1px solid var(--border-color-lighter);
}

.footer-text {
  font-size: 0.75rem;
  color: var(--text-color-secondary);
  margin: 0;
}

.theme-switch-container {
  position: absolute;
  top: 2rem;
  right: 2rem;
  z-index: 4;
}

.theme-switch {
  width: 48px;
  height: 24px;
  background: rgba(255, 255, 255, 0.2);
  border-radius: 12px;
  cursor: pointer;
  position: relative;
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.3);
  transition: all 0.3s ease;
  
  &::before {
    content: '';
    position: absolute;
    top: 2px;
    left: 2px;
    width: 20px;
    height: 20px;
    background: white;
    border-radius: 50%;
    transition: transform 0.3s ease;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
  }
  
  &.dark::before {
    transform: translateX(24px);
  }
  
  .icon {
    position: absolute;
    top: 50%;
    transform: translateY(-50%);
    font-size: 14px;
    color: white;
    
    &.sun {
      left: 6px;
    }
    
    &.moon {
      right: 6px;
    }
  }
  
  &:hover {
    background: rgba(255, 255, 255, 0.3);
    transform: scale(1.05);
  }
}

// 响应式设计
@media (max-width: 768px) {
  .login-content {
    width: 95%;
    padding: 0 1rem;
  }
  
  .login-form-container {
    padding: 1.5rem;
  }
  
  .logo-title {
    font-size: 1.5rem;
  }
  
  .form-title {
    font-size: 1.25rem;
  }
  
  .theme-switch-container {
    top: 1rem;
    right: 1rem;
  }
}

// 深色主题适配
[data-theme='dark'] {
  .login-form-container {
    background: rgba(31, 31, 31, 0.95);
    border-color: rgba(255, 255, 255, 0.1);
  }
  
  .form-title {
    color: var(--text-color-primary);
  }
  
  .form-subtitle {
    color: var(--text-color-secondary);
  }
}
</style>
