<template>
  <div class="login-container">
    <div class="login-box">
      <div class="login-form">
        <div class="title-container">
          <h3 class="title">山海方舟管理系统</h3>
        </div>
        
        <el-form
          ref="loginFormRef"
          :model="loginForm"
          :rules="loginRules"
          class="login-form"
          auto-complete="on"
          label-position="left"
        >
          <div class="tips-container">
            <span class="tips">用户名: admin</span>
            <span class="tips">密码: 123456</span>
          </div>

          <el-form-item prop="username">
            <el-input
              v-model="loginForm.username"
              placeholder="用户名"
              type="text"
              tabindex="1"
              auto-complete="on"
            >
              <template #prefix>
                <el-icon><User /></el-icon>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="password">
            <el-input
              v-model="loginForm.password"
              placeholder="密码"
              :type="passwordVisible ? 'text' : 'password'"
              tabindex="2"
              auto-complete="on"
              @keyup.enter="handleLogin"
            >
              <template #prefix>
                <el-icon><Lock /></el-icon>
              </template>
              <template #suffix>
                <el-icon 
                  class="cursor-pointer"
                  @click="passwordVisible = !passwordVisible"
                >
                  <View v-if="passwordVisible" />
                  <Hide v-else />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>

          <el-form-item prop="captcha">
            <div class="captcha-container">
              <el-input
                v-model="loginForm.captcha"
                placeholder="验证码"
                tabindex="3"
                auto-complete="on"
                @keyup.enter="handleLogin"
              >
                <template #prefix>
                  <el-icon><Key /></el-icon>
                </template>
              </el-input>
              <div class="captcha-img" @click="refreshCaptcha">
                <img :src="captchaUrl" alt="验证码" />
              </div>
            </div>
          </el-form-item>

          <el-button
            :loading="loading"
            type="primary"
            style="width: 100%; margin-bottom: 30px"
            @click="handleLogin"
          >
            登录
          </el-button>
        </el-form>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { ElMessage } from 'element-plus'
import { User, Lock, View, Hide, Key } from '@element-plus/icons-vue'
import { useUserStore } from '@/store/user'
import type { FormInstance, FormRules } from 'element-plus'

const router = useRouter()
const route = useRoute()
const userStore = useUserStore()

const loginFormRef = ref<FormInstance>()
const loading = ref(false)
const passwordVisible = ref(false)
const captchaUrl = ref('')

const loginForm = reactive({
  username: 'admin',
  password: '123456',
  captcha: ''
})

const loginRules: FormRules = {
  username: [
    { required: true, trigger: 'blur', message: '请输入用户名' }
  ],
  password: [
    { required: true, trigger: 'blur', message: '请输入密码' }
  ],
  captcha: [
    { required: true, trigger: 'blur', message: '请输入验证码' }
  ]
}

const handleLogin = async () => {
  if (!loginFormRef.value) return
  
  try {
    await loginFormRef.value.validate()
    loading.value = true
    
    await userStore.login(loginForm)
    ElMessage.success('登录成功')
    
    const redirect = route.query.redirect as string
    router.push(redirect || '/')
  } catch (error: any) {
    ElMessage.error(error.message || '登录失败')
    refreshCaptcha()
  } finally {
    loading.value = false
  }
}

const refreshCaptcha = () => {
  captchaUrl.value = `/api/captcha?t=${Date.now()}`
  loginForm.captcha = ''
}

onMounted(() => {
  refreshCaptcha()
})
</script>

<style lang="scss" scoped>
.login-container {
  min-height: 100vh;
  width: 100%;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  overflow: hidden;
  display: flex;
  align-items: center;
  justify-content: center;
}

.login-box {
  position: relative;
  width: 520px;
  max-width: 100%;
  padding: 160px 35px 0;
  margin: 0 auto;
  overflow: hidden;
}

.login-form {
  position: relative;
  width: 520px;
  max-width: 100%;
  padding: 160px 35px 0;
  margin: 0 auto;
  overflow: hidden;
}

.title-container {
  position: relative;
  .title {
    font-size: 26px;
    color: #eee;
    margin: 0 auto 40px auto;
    text-align: center;
    font-weight: bold;
  }
}

.tips-container {
  font-size: 14px;
  color: #fff;
  margin-bottom: 10px;
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
  }
}

.captcha-container {
  display: flex;
  align-items: center;
  gap: 10px;
  
  .captcha-img {
    width: 120px;
    height: 40px;
    cursor: pointer;
    
    img {
      width: 100%;
      height: 100%;
      border-radius: 4px;
    }
  }
}

.cursor-pointer {
  cursor: pointer;
}
</style>
