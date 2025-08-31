# 山海方舟企业级中后台管理系统

## 🚀 项目简介

山海方舟企业级中后台管理系统是一个基于 Spring Boot + Vue 3 的现代化企业级权限管理系统，采用前后端分离架构，提供完整的用户、角色、菜单、部门管理功能，支持RBAC权限模型和数据权限控制。

## ✨ 核心特性

### 🔐 权限管理
- **RBAC权限模型** - 基于角色的访问控制
- **多级权限控制** - 菜单权限、按钮权限、数据权限
- **数据权限范围** - 全部数据、本部门及下级、本部门、仅本人
- **动态权限验证** - 前端权限指令，后端权限拦截

### 👥 用户管理
- **用户生命周期管理** - 创建、编辑、删除、状态管理
- **角色分配** - 支持多角色分配
- **部门管理** - 用户所属部门管理
- **密码安全** - BCrypt加密，支持密码重置

### 🎭 角色管理
- **角色体系** - 4级角色级别管理
- **权限分配** - 菜单权限、数据权限分配
- **角色复制** - 快速创建相似角色
- **系统保护** - 内置角色保护机制

### 📋 菜单管理
- **三级菜单结构** - 目录、菜单、按钮
- **权限标识** - 唯一权限标识管理
- **树形展示** - 支持展开/折叠、拖拽排序
- **菜单复制** - 支持菜单复制和移动

### 🏢 部门管理
- **组织架构** - 三级组织架构（公司-部门-小组）
- **树形结构** - 支持部门层级关系管理
- **部门权限** - 部门数据权限控制
- **部门操作** - 支持复制、移动、排序

### 📊 系统监控
- **实时监控** - 系统资源使用情况
- **健康检查** - 数据库、Redis、磁盘等状态
- **性能统计** - 内存、线程、系统负载
- **在线用户** - 在线用户管理和统计

## 🏗️ 技术架构

### 后端技术栈
- **核心框架** - Spring Boot 3.x
- **ORM框架** - MyBatis-Plus 3.5.x
- **数据库** - MySQL 8.0+
- **安全框架** - Spring Security 6.x
- **权限控制** - 自定义RBAC权限模型
- **API文档** - Swagger 3.x (OpenAPI)
- **日志框架** - Logback + SLF4J
- **工具库** - Hutool、Lombok

### 前端技术栈
- **核心框架** - Vue 3.x + TypeScript
- **UI组件库** - Element Plus
- **状态管理** - Pinia
- **路由管理** - Vue Router 4.x
- **HTTP客户端** - Axios
- **构建工具** - Vite
- **样式预处理** - SCSS
- **代码规范** - ESLint + Prettier

### 数据库设计
- **用户表** - sys_user
- **角色表** - sys_role
- **菜单表** - sys_menu
- **部门表** - sys_dept
- **用户角色关联表** - sys_user_role
- **角色菜单关联表** - sys_role_menu
- **角色部门关联表** - sys_role_dept
- **在线用户表** - sys_online_user
- **操作日志表** - sys_oper_log
- **登录日志表** - sys_login_log

## 📁 项目结构

```
shanhaifangzhou-Admin-Utral/
├── shanhaifangzhou-admin/          # 主应用模块
│   └── src/main/java/com/shanhaifangzhou/admin/
│       ├── user/                    # 用户管理模块
│       │   ├── controller/         # 用户控制器
│       │   ├── service/            # 用户服务
│       │   ├── mapper/             # 用户数据访问
│       │   ├── entity/             # 用户实体
│       │   └── dto/                # 用户DTO
│       ├── role/                    # 角色管理模块
│       │   ├── controller/         # 角色控制器
│       │   ├── service/            # 角色服务
│       │   ├── mapper/             # 角色数据访问
│       │   ├── entity/             # 角色实体
│       │   └── dto/                # 角色DTO
│       ├── menu/                    # 菜单管理模块
│       │   ├── controller/         # 菜单控制器
│       │   ├── service/            # 菜单服务
│       │   ├── mapper/             # 菜单数据访问
│       │   ├── entity/             # 菜单实体
│       │   └── dto/                # 菜单DTO
│       ├── dept/                    # 部门管理模块
│       │   ├── controller/         # 部门控制器
│       │   ├── service/            # 部门服务
│       │   ├── mapper/             # 部门数据访问
│       │   ├── entity/             # 部门实体
│       │   └── dto/                # 部门DTO
│       └── monitor/                 # 系统监控模块
│           ├── controller/         # 监控控制器
│           └── entity/             # 监控实体
├── shanhaifangzhou-base/           # 基础模块
│   └── src/main/java/com/shanhaifangzhou/base/
│       ├── common/                  # 公共组件
│       │   ├── result/             # 统一返回结果
│       │   ├── exception/          # 异常处理
│       │   └── utils/              # 工具类
│       └── config/                  # 配置类
└── shanhaifangzhou-Admin-Vue-Ultra/ # 前端项目
    ├── src/
    │   ├── api/                    # API接口
    │   │   ├── user.ts            # 用户API
    │   │   ├── role.ts            # 角色API
    │   │   ├── menu.ts            # 菜单API
    │   │   └── dept.ts            # 部门API
    │   ├── views/                  # 页面组件
    │   │   ├── system/            # 系统管理页面
    │   │   │   ├── user/          # 用户管理页面
    │   │   │   ├── role/          # 角色管理页面
    │   │   │   ├── menu/          # 菜单管理页面
    │   │   │   └── dept/          # 部门管理页面
    │   │   └── monitor/           # 系统监控页面
    │   ├── layout/                 # 布局组件
    │   ├── components/             # 公共组件
    │   ├── directives/             # 自定义指令
    │   │   └── permission.ts      # 权限指令
    │   ├── store/                  # 状态管理
    │   ├── router/                 # 路由配置
    │   ├── styles/                 # 样式文件
    │   ├── types/                  # 类型定义
    │   └── utils/                  # 工具函数
    ├── public/                     # 静态资源
    └── package.json                # 依赖配置
```

## 🚀 快速开始

### 环境要求
- **JDK** - 17+
- **Node.js** - 16+
- **MySQL** - 8.0+
- **Redis** - 6.0+ (可选)

### 后端启动
```bash
# 1. 克隆项目
git clone https://github.com/your-username/shanhaifangzhou-Admin-Utral.git

# 2. 进入后端目录
cd shanhaifangzhou-admin

# 3. 配置数据库
# 修改 application.yml 中的数据库连接信息

# 4. 启动应用
mvn spring-boot:run

# 或者打包后运行
mvn clean package
java -jar target/shanhaifangzhou-admin.jar
```

### 前端启动
```bash
# 1. 进入前端目录
cd shanhaifangzhou-Admin-Vue-Ultra

# 2. 安装依赖
npm install

# 3. 启动开发服务器
npm run dev

# 4. 构建生产版本
npm run build
```

### 数据库初始化
```sql
-- 创建数据库
CREATE DATABASE shanhaifangzhou_admin DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 执行SQL脚本
-- 项目根目录下的 sql/init.sql
```

## 🔧 配置说明

### 后端配置
```yaml
# application.yml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/shanhaifangzhou_admin
    username: root
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
  
  redis:
    host: localhost
    port: 6379
    password: your_redis_password
    
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

# 自定义配置
shanhaifangzhou:
  jwt:
    secret: your_jwt_secret
    expiration: 86400000  # 24小时
  security:
    password:
      min-length: 6
      max-length: 20
```

### 前端配置
```typescript
// .env.development
VITE_BASE_API = '/api'
VITE_APP_TITLE = '山海方舟管理系统'

// .env.production
VITE_BASE_API = 'https://your-domain.com/api'
VITE_APP_TITLE = '山海方舟管理系统'
```

## 📖 使用说明

### 权限指令使用
```vue
<template>
  <!-- 单个权限 -->
  <el-button v-permission="'system:user:add'">新增用户</el-button>
  
  <!-- 多个权限（或关系） -->
  <el-button v-permission="['system:user:add', 'system:user:edit']">操作</el-button>
  
  <!-- 多个权限（与关系） -->
  <el-button v-permission.all="['system:user:add', 'system:user:edit']">操作</el-button>
  
  <!-- 角色控制 -->
  <el-button v-role="'admin'">管理员操作</el-button>
  
  <!-- 数据权限 -->
  <el-button v-data-scope="'dept'">部门数据</el-button>
</template>
```

### API接口使用
```typescript
import { getUserPage, createUser, updateUser, deleteUser } from '@/api/user'

// 分页查询用户
const loadUsers = async () => {
  const response = await getUserPage({
    current: 1,
    size: 10,
    username: 'admin'
  })
  if (response.code === 200) {
    userList.value = response.data.records
  }
}

// 创建用户
const handleCreate = async (userData: UserCreateParams) => {
  const response = await createUser(userData)
  if (response.code === 200) {
    ElMessage.success('用户创建成功')
    loadUsers()
  }
}
```

## 🔒 安全特性

### 认证授权
- **JWT Token认证** - 无状态认证机制
- **Session管理** - 支持Session和Token双重认证
- **权限拦截** - 方法级权限控制
- **数据权限** - 行级数据权限控制

### 安全防护
- **SQL注入防护** - MyBatis参数化查询
- **XSS防护** - 输入输出过滤
- **CSRF防护** - 跨站请求伪造防护
- **密码加密** - BCrypt强哈希算法

## 📊 性能优化

### 后端优化
- **数据库索引** - 关键字段索引优化
- **分页查询** - MyBatis-Plus分页插件
- **缓存策略** - Redis缓存热点数据
- **连接池** - HikariCP高性能连接池

### 前端优化
- **组件懒加载** - 路由懒加载
- **图片懒加载** - 虚拟滚动优化
- **代码分割** - 按需加载组件
- **缓存策略** - 浏览器缓存优化

## 🧪 测试

### 单元测试
```bash
# 后端测试
mvn test

# 前端测试
npm run test:unit
```

### 集成测试
```bash
# 后端集成测试
mvn verify

# 前端E2E测试
npm run test:e2e
```

## 📦 部署

### Docker部署
```dockerfile
# Dockerfile
FROM openjdk:17-jre-slim
COPY target/shanhaifangzhou-admin.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

```bash
# 构建镜像
docker build -t shanhaifangzhou-admin .

# 运行容器
docker run -d -p 8080:8080 --name shanhaifangzhou-admin shanhaifangzhou-admin
```

### 传统部署
```bash
# 后端部署
java -Xms512m -Xmx1024m -jar shanhaifangzhou-admin.jar

# 前端部署
npm run build
# 将dist目录部署到Web服务器
```

## 🤝 贡献指南

1. Fork 项目
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request

## 📄 许可证

本项目采用 [MIT License](LICENSE) 许可证。

## 📞 联系方式

- **项目地址**: https://github.com/your-username/shanhaifangzhou-Admin-Utral
- **问题反馈**: https://github.com/your-username/shanhaifangzhou-Admin-Utral/issues
- **邮箱**: your-email@example.com

## 🙏 致谢

感谢以下开源项目的支持：
- [Spring Boot](https://spring.io/projects/spring-boot)
- [Vue.js](https://vuejs.org/)
- [Element Plus](https://element-plus.org/)
- [MyBatis-Plus](https://baomidou.com/)

---

⭐ 如果这个项目对您有帮助，请给我们一个星标！
