# 山海方舟企业级中后台管理系统

## 项目概述

山海方舟是基于RBAC权限模型的企业级中后台管理系统，采用前后端分离架构，Spring Boot 3 + Vue 3技术栈。

## 技术栈

### 后端技术
- Spring Boot 3.3.x + Java 17
- Spring Security + JWT (认证与权限)
- MyBatis-Plus + MySQL 8 (数据层)
- JetCache + Redisson (两级缓存)
- Hutool (工具集)
- Knife4j (API文档)

### 前端技术
- Vue 3 + TypeScript + Vite
- Element Plus (UI组件库)
- Axios (HTTP客户端)
- Pinia (状态管理)
- Vue Router (路由管理)

## 功能模块

1. **用户管理** - 用户CRUD、导入导出、状态管理、角色分配
2. **角色管理** - 角色CRUD、权限分配
3. **菜单管理** - 菜单树形结构、权限配置
4. **部门管理** - 部门树形结构、层级管理
5. **数据字典** - 字典类型、字典数据管理
6. **文件管理** - 文件上传下载、分类管理
7. **云存储** - 多存储源配置、文件同步
8. **通知公告** - 公告发布、消息推送
9. **操作日志** - 操作记录、审计追踪
10. **支付管理** - 支付配置、交易记录
11. **系统监控** - 性能监控、资源监控
12. **代码生成** - 一键生成前后端代码
13. **系统接口** - Swagger API文档

## 项目结构

```
shanhaifangzhou-Admin-Utral/
├── shanhaifangzhou-admin/          # 主应用模块
├── shanhaifangzhou-base/           # 基础模块(单向依赖)
└── shanhaifangzhou-Admin-Vue-Ultra/ # 前端项目
```

## 快速开始

### 环境要求

- JDK 17+
- Maven 3.8+
- MySQL 8.0+
- Redis 6.0+
- Node.js 18+

### 后端启动

1. 创建数据库并执行初始化脚本
```sql
-- 执行 src/main/resources/sql/init.sql
```

2. 修改配置文件
```yaml
# application-dev.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/shanhaifangzhou_dev
    username: your_username
    password: your_password
```

3. 启动应用
```bash
cd shanhaifangzhou-admin
mvn spring-boot:run
```

### 前端启动

1. 安装依赖
```bash
cd shanhaifangzhou-Admin-Vue-Ultra
npm install
```

2. 启动开发服务器
```bash
npm run dev
```

3. 构建生产版本
```bash
npm run build
```

## 默认账号

- 用户名：admin
- 密码：123456

## 开发规范

- 遵循阿里Java开发规范，扫描0警告
- 使用jakarta包替代javax
- 按业务域垂直分包，拒绝大杂烩
- 每个模块完整分层：Controller、Service、ServiceImpl、Entity、Mapper、Mapper XML
- 支持浅色/深色主题切换
- 实现按钮级别权限控制

## 特性

- 🚀 **现代化技术栈** - Spring Boot 3 + Vue 3 + TypeScript
- 🔐 **安全认证** - JWT + Spring Security + RBAC权限模型
- 📱 **响应式设计** - 支持PC端和移动端
- 🎨 **主题切换** - 支持浅色/深色主题
- 📊 **数据可视化** - 集成ECharts图表库
- 🔧 **代码生成** - 一键生成CRUD代码
- 📝 **操作日志** - 完整的操作审计追踪
- 🌐 **国际化** - 支持多语言切换

## 贡献指南

1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 许可证

本项目采用 MIT 许可证 - 查看 [LICENSE](LICENSE) 文件了解详情

## 联系方式

- 项目地址：[https://github.com/your-username/shanhaifangzhou-admin-utral](https://github.com/your-username/shanhaifangzhou-admin-utral)
- 问题反馈：[Issues](https://github.com/your-username/shanhaifangzhou-admin-utral/issues)
- 邮箱：admin@shanhaifangzhou.com

## 更新日志

### v1.0.0 (2024-01-01)
- 🎉 项目初始化
- ✨ 基础框架搭建
- 🔐 用户认证与权限管理
- 🎨 基础UI组件
- 📊 系统管理模块
