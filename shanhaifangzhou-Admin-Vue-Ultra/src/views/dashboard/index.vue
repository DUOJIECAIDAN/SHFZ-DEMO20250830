<template>
  <div class="dashboard-container">
    <!-- 统计卡片 -->
    <div class="stats-cards">
      <el-row :gutter="20">
        <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
          <div class="stat-card stat-card-primary">
            <div class="stat-icon">
              <el-icon><User /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ stats.userCount }}</div>
              <div class="stat-label">用户总数</div>
            </div>
          </div>
        </el-col>
        
        <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
          <div class="stat-card stat-card-success">
            <div class="stat-icon">
              <el-icon><UserFilled /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ stats.roleCount }}</div>
              <div class="stat-label">角色总数</div>
            </div>
          </div>
        </el-col>
        
        <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
          <div class="stat-card stat-card-warning">
            <div class="stat-icon">
              <el-icon><Menu /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ stats.menuCount }}</div>
              <div class="stat-label">菜单总数</div>
            </div>
          </div>
        </el-col>
        
        <el-col :xs="24" :sm="12" :md="6" :lg="6" :xl="6">
          <div class="stat-card stat-card-info">
            <div class="stat-icon">
              <el-icon><OfficeBuilding /></el-icon>
            </div>
            <div class="stat-content">
              <div class="stat-number">{{ stats.deptCount }}</div>
              <div class="stat-label">部门总数</div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    
    <!-- 图表区域 -->
    <div class="charts-section">
      <el-row :gutter="20">
        <el-col :xs="24" :lg="12">
          <div class="chart-card">
            <div class="chart-header">
              <h3 class="chart-title">系统访问统计</h3>
            </div>
            <div class="chart-content">
              <div ref="visitChartRef" class="chart-container"></div>
            </div>
          </div>
        </el-col>
        
        <el-col :xs="24" :lg="12">
          <div class="chart-card">
            <div class="chart-header">
              <h3 class="chart-title">用户分布</h3>
            </div>
            <div class="chart-content">
              <div ref="userChartRef" class="chart-container"></div>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
    
    <!-- 系统信息 -->
    <div class="system-info-section">
      <el-row :gutter="20">
        <el-col :xs="24" :lg="12">
          <div class="info-card">
            <div class="card-header">
              <h3 class="card-title">系统信息</h3>
            </div>
            <div class="card-content">
              <el-descriptions :column="1" border>
                <el-descriptions-item label="系统名称">山海方舟管理系统</el-descriptions-item>
                <el-descriptions-item label="系统版本">v1.0.0</el-descriptions-item>
                <el-descriptions-item label="服务器环境">{{ systemInfo.osName }}</el-descriptions-item>
                <el-descriptions-item label="Java版本">{{ systemInfo.javaVersion }}</el-descriptions-item>
                <el-descriptions-item label="数据库版本">{{ systemInfo.databaseVersion }}</el-descriptions-item>
                <el-descriptions-item label="启动时间">{{ systemInfo.startTime }}</el-descriptions-item>
              </el-descriptions>
            </div>
          </div>
        </el-col>
        
        <el-col :xs="24" :lg="12">
          <div class="info-card">
            <div class="card-header">
              <h3 class="card-title">最近登录记录</h3>
            </div>
            <div class="card-content">
              <el-table :data="recentLogins" stripe style="width: 100%">
                <el-table-column prop="username" label="用户名" />
                <el-table-column prop="loginTime" label="登录时间" />
                <el-table-column prop="loginIp" label="登录IP" />
                <el-table-column prop="status" label="状态">
                  <template #default="{ row }">
                    <el-tag :type="row.status === 1 ? 'success' : 'danger'">
                      {{ row.status === 1 ? '成功' : '失败' }}
                    </el-tag>
                  </template>
                </el-table-column>
              </el-table>
            </div>
          </div>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, nextTick } from 'vue'
import { ElRow, ElCol, ElDescriptions, ElDescriptionsItem, ElTable, ElTableColumn, ElTag } from 'element-plus'
import { User, UserFilled, Menu, OfficeBuilding } from '@element-plus/icons-vue'
import * as echarts from 'echarts'

// 统计数据
const stats = ref({
  userCount: 0,
  roleCount: 0,
  menuCount: 0,
  deptCount: 0
})

// 系统信息
const systemInfo = ref({
  osName: 'Linux',
  javaVersion: 'OpenJDK 17',
  databaseVersion: 'MySQL 8.0',
  startTime: '2024-01-01 00:00:00'
})

// 最近登录记录
const recentLogins = ref([
  {
    username: 'admin',
    loginTime: '2024-01-01 10:00:00',
    loginIp: '192.168.1.100',
    status: 1
  },
  {
    username: 'user1',
    loginTime: '2024-01-01 09:30:00',
    loginIp: '192.168.1.101',
    status: 1
  },
  {
    username: 'user2',
    loginTime: '2024-01-01 09:00:00',
    loginIp: '192.168.1.102',
    status: 0
  }
])

// 图表引用
const visitChartRef = ref<HTMLElement>()
const userChartRef = ref<HTMLElement>()

// 初始化访问统计图表
const initVisitChart = () => {
  if (!visitChartRef.value) return
  
  const chart = echarts.init(visitChartRef.value)
  
  const option = {
    tooltip: {
      trigger: 'axis'
    },
    legend: {
      data: ['访问量', '用户数']
    },
    xAxis: {
      type: 'category',
      data: ['周一', '周二', '周三', '周四', '周五', '周六', '周日']
    },
    yAxis: {
      type: 'value'
    },
    series: [
      {
        name: '访问量',
        type: 'line',
        data: [120, 132, 101, 134, 90, 230, 210],
        smooth: true,
        lineStyle: {
          color: '#409eff'
        },
        areaStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              { offset: 0, color: 'rgba(64, 158, 255, 0.3)' },
              { offset: 1, color: 'rgba(64, 158, 255, 0.1)' }
            ]
          }
        }
      },
      {
        name: '用户数',
        type: 'line',
        data: [220, 182, 191, 234, 290, 330, 310],
        smooth: true,
        lineStyle: {
          color: '#67c23a'
        },
        areaStyle: {
          color: {
            type: 'linear',
            x: 0,
            y: 0,
            x2: 0,
            y2: 1,
            colorStops: [
              { offset: 0, color: 'rgba(103, 194, 58, 0.3)' },
              { offset: 1, color: 'rgba(103, 194, 58, 0.1)' }
            ]
          }
        }
      }
    ]
  }
  
  chart.setOption(option)
  
  // 响应式处理
  window.addEventListener('resize', () => {
    chart.resize()
  })
}

// 初始化用户分布图表
const initUserChart = () => {
  if (!userChartRef.value) return
  
  const chart = echarts.init(userChartRef.value)
  
  const option = {
    tooltip: {
      trigger: 'item',
      formatter: '{a} <br/>{b}: {c} ({d}%)'
    },
    legend: {
      orient: 'vertical',
      left: 'left'
    },
    series: [
      {
        name: '用户分布',
        type: 'pie',
        radius: ['40%', '70%'],
        avoidLabelOverlap: false,
        label: {
          show: false,
          position: 'center'
        },
        emphasis: {
          label: {
            show: true,
            fontSize: '18',
            fontWeight: 'bold'
          }
        },
        labelLine: {
          show: false
        },
        data: [
          { value: 1048, name: '管理员' },
          { value: 735, name: '普通用户' },
          { value: 580, name: 'VIP用户' },
          { value: 484, name: '访客' }
        ]
      }
    ]
  }
  
  chart.setOption(option)
  
  // 响应式处理
  window.addEventListener('resize', () => {
    chart.resize()
  })
}

// 加载统计数据
const loadStats = async () => {
  // 模拟API调用
  stats.value = {
    userCount: 1250,
    roleCount: 15,
    menuCount: 85,
    deptCount: 12
  }
}

// 组件挂载时执行
onMounted(async () => {
  await loadStats()
  
  // 等待DOM更新后初始化图表
  await nextTick()
  initVisitChart()
  initUserChart()
})
</script>

<style lang="scss" scoped>
.dashboard-container {
  padding: 20px;
  background-color: var(--bg-color-page);
  min-height: calc(100vh - 84px);
}

.stats-cards {
  margin-bottom: 20px;
}

.stat-card {
  background: var(--bg-color);
  border-radius: 12px;
  padding: 24px;
  box-shadow: var(--box-shadow-light);
  border: 1px solid var(--border-color-lighter);
  display: flex;
  align-items: center;
  transition: all 0.3s ease;
  
  &:hover {
    transform: translateY(-4px);
    box-shadow: var(--box-shadow-hover);
  }
  
  &.stat-card-primary {
    border-left: 4px solid var(--primary-color);
  }
  
  &.stat-card-success {
    border-left: 4px solid var(--success-color);
  }
  
  &.stat-card-warning {
    border-left: 4px solid var(--warning-color);
  }
  
  &.stat-card-info {
    border-left: 4px solid var(--info-color);
  }
}

.stat-icon {
  width: 60px;
  height: 60px;
  border-radius: 12px;
  display: flex;
  align-items: center;
  justify-content: center;
  margin-right: 16px;
  
  .el-icon {
    font-size: 28px;
    color: white;
  }
  
  .stat-card-primary & {
    background: linear-gradient(135deg, var(--primary-color), #66b1ff);
  }
  
  .stat-card-success & {
    background: linear-gradient(135deg, var(--success-color), #85ce61);
  }
  
  .stat-card-warning & {
    background: linear-gradient(135deg, var(--warning-color), #ebb563);
  }
  
  .stat-card-info & {
    background: linear-gradient(135deg, var(--info-color), #a6a9ad);
  }
}

.stat-content {
  flex: 1;
}

.stat-number {
  font-size: 2rem;
  font-weight: 700;
  color: var(--text-color-primary);
  margin-bottom: 4px;
}

.stat-label {
  font-size: 0.875rem;
  color: var(--text-color-secondary);
}

.charts-section {
  margin-bottom: 20px;
}

.chart-card {
  background: var(--bg-color);
  border-radius: 12px;
  box-shadow: var(--box-shadow-light);
  border: 1px solid var(--border-color-lighter);
  overflow: hidden;
}

.chart-header {
  padding: 20px 24px 0;
  
  .chart-title {
    font-size: 1.125rem;
    font-weight: 600;
    color: var(--text-color-primary);
    margin: 0;
  }
}

.chart-content {
  padding: 20px 24px 24px;
}

.chart-container {
  width: 100%;
  height: 300px;
}

.system-info-section {
  .info-card {
    background: var(--bg-color);
    border-radius: 12px;
    box-shadow: var(--box-shadow-light);
    border: 1px solid var(--border-color-lighter);
    overflow: hidden;
  }
  
  .card-header {
    padding: 20px 24px 0;
    
    .card-title {
      font-size: 1.125rem;
      font-weight: 600;
      color: var(--text-color-primary);
      margin: 0;
    }
  }
  
  .card-content {
    padding: 20px 24px 24px;
  }
}

// 响应式设计
@media (max-width: 768px) {
  .dashboard-container {
    padding: 10px;
  }
  
  .stat-card {
    padding: 16px;
    margin-bottom: 16px;
  }
  
  .stat-icon {
    width: 48px;
    height: 48px;
    
    .el-icon {
      font-size: 24px;
    }
  }
  
  .stat-number {
    font-size: 1.5rem;
  }
  
  .chart-container {
    height: 250px;
  }
}

// 深色主题适配
[data-theme='dark'] {
  .stat-card,
  .chart-card,
  .info-card {
    background: var(--bg-color);
    border-color: var(--border-color-base);
  }
  
  .stat-number {
    color: var(--text-color-primary);
  }
  
  .stat-label {
    color: var(--text-color-secondary);
  }
}
</style>