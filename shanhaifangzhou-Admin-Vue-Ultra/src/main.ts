import { createApp } from 'vue'
import { createPinia } from 'pinia'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/dark/css-vars.css'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import zhCn from 'element-plus/dist/locale/zh-cn.mjs'
import en from 'element-plus/dist/locale/en.mjs'

import App from './App.vue'
import router from './router'
import './styles/index.scss'

// 创建应用实例
const app = createApp(App)

// 注册Element Plus图标
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

// 使用插件
app.use(createPinia())
app.use(router)
app.use(ElementPlus, {
  locale: zhCn, // 默认使用中文
  size: 'default',
})

// 挂载应用
app.mount('#app')
