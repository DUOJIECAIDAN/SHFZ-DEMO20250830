# ShanHaiFangZhou-Admin-Ultra (山海方舟)

## 项目简介

山海方舟是一款专注于设计与代码细节的高质量企业级中后台管理系统框架，采用前后端分离架构，旨在让大家能"开箱即用"。

## 技术栈

### 后端技术
- Spring Boot 3.3.x + Java 17
- 认证 & 权限：JWT + Spring Security
- 数据层：MyBatis-Plus + MySQL 8
- 缓存：JetCache + Redisson（两级缓存）
- 工具集：Hutool
- 接口文档：Swagger 3

### 前端技术
- Vue 3 + TypeScript + Vite
- UI框架：Element Plus
- HTTP客户端：Axios
- 状态管理：Pinia
- 路由：Vue Router 4

## 功能模块

1. 用户管理
2. 角色管理
3. 菜单管理
4. 部门管理
5. 数据字典
6. 文件管理
7. 云存储
8. 通知公告
9. 操作日志
10. 支付管理
11. 系统监控
12. 代码生成
13. 系统接口（Swagger）

## 项目结构

```
shanhaifangzhou-Admin-Ultra/
├── shanhaifangzhou-admin/          # 后端主模块
├── shanhaifangzhou-base/           # 后端基础模块
└── shanhaifangzhou-Admin-Vue-Ultra/ # 前端项目
```

## 快速开始

### 后端启动
1. 配置数据库连接
2. 运行 `shanhaifangzhou-admin` 模块
3. 访问 Swagger 文档：http://localhost:8080/swagger-ui.html

### 前端启动
1. 进入前端目录
2. 安装依赖：`npm install`
3. 启动开发服务器：`npm run dev`
4. 访问：http://localhost:3000

## 默认账号
- 用户名：admin
- 密码：123456

## 特性

- 🎨 现代化UI设计，支持浅色/深色主题切换
- 🔐 完整的RBAC权限控制，支持按钮级权限
- 📱 响应式设计，支持多端适配
- 🚀 高性能，采用两级缓存架构
- 📊 完善的系统监控和日志记录
- 🔧 代码生成器，一键生成CRUD代码
- �� 详细的API文档和代码注释

## 开发规范

- 遵循阿里Java开发手册
- 统一的代码风格和命名规范
- 完整的单元测试覆盖
- 详细的代码注释和文档

## 许可证

MIT License
