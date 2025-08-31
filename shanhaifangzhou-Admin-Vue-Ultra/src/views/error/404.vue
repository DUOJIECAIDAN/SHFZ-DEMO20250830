<template>
  <div class="error-page">
    <div class="error-container">
      <div class="error-content">
        <div class="error-image">
          <img src="@/assets/images/404.svg" alt="404" />
        </div>
        <div class="error-info">
          <h1 class="error-code">404</h1>
          <h2 class="error-title">页面不存在</h2>
          <p class="error-description">
            抱歉，您访问的页面不存在或已被删除。
          </p>
          <div class="error-actions">
            <el-button type="primary" @click="goHome">
              <el-icon><House /></el-icon>
              返回首页
            </el-button>
            <el-button @click="goBack">
              <el-icon><Back /></el-icon>
              返回上页
            </el-button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router'
import { ElButton, ElIcon } from 'element-plus'
import { House, Back } from '@element-plus/icons-vue'

const router = useRouter()

// 返回首页
const goHome = () => {
  router.push('/')
}

// 返回上页
const goBack = () => {
  router.go(-1)
}
</script>

<style lang="scss" scoped>
.error-page {
  width: 100vw;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-content: center;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
}

.error-container {
  text-align: center;
  max-width: 600px;
  padding: 2rem;
}

.error-content {
  background: rgba(255, 255, 255, 0.95);
  backdrop-filter: blur(10px);
  border-radius: 16px;
  padding: 3rem 2rem;
  box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
  border: 1px solid rgba(255, 255, 255, 0.2);
}

.error-image {
  margin-bottom: 2rem;
  
  img {
    width: 200px;
    height: 200px;
    opacity: 0.8;
  }
}

.error-info {
  .error-code {
    font-size: 6rem;
    font-weight: 700;
    color: var(--primary-color);
    margin: 0 0 1rem 0;
    text-shadow: 0 4px 8px rgba(64, 158, 255, 0.3);
  }
  
  .error-title {
    font-size: 2rem;
    font-weight: 600;
    color: var(--text-color-primary);
    margin: 0 0 1rem 0;
  }
  
  .error-description {
    font-size: 1rem;
    color: var(--text-color-secondary);
    margin: 0 0 2rem 0;
    line-height: 1.6;
  }
}

.error-actions {
  display: flex;
  gap: 1rem;
  justify-content: center;
  flex-wrap: wrap;
  
  .el-button {
    padding: 0.75rem 1.5rem;
    font-size: 1rem;
    border-radius: 8px;
    
    .el-icon {
      margin-right: 0.5rem;
    }
  }
}

// 响应式设计
@media (max-width: 768px) {
  .error-container {
    padding: 1rem;
  }
  
  .error-content {
    padding: 2rem 1.5rem;
  }
  
  .error-code {
    font-size: 4rem !important;
  }
  
  .error-title {
    font-size: 1.5rem !important;
  }
  
  .error-actions {
    flex-direction: column;
    align-items: center;
    
    .el-button {
      width: 100%;
      max-width: 200px;
    }
  }
}

// 深色主题适配
[data-theme='dark'] {
  .error-content {
    background: rgba(31, 31, 31, 0.95);
    border-color: rgba(255, 255, 255, 0.1);
  }
  
  .error-title {
    color: var(--text-color-primary);
  }
  
  .error-description {
    color: var(--text-color-secondary);
  }
}
</style>