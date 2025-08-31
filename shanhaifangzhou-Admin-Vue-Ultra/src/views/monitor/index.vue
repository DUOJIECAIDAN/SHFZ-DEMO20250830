<template>
  <div class="monitor-dashboard">
    <!-- 系统概览卡片 -->
    <el-row :gutter="20" class="overview-cards">
      <el-col :span="6">
        <el-card class="overview-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon user-icon">
              <el-icon><User /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">在线用户</div>
              <div class="card-value">{{ systemStats.onlineUserCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="overview-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon system-icon">
              <el-icon><Monitor /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">系统状态</div>
              <div class="card-value" :class="getStatusClass(systemHealth.status)">
                {{ getStatusText(systemHealth.status) }}
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="overview-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon memory-icon">
              <el-icon><Cpu /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">内存使用</div>
              <div class="card-value">{{ formatMemoryUsage() }}%</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="overview-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon thread-icon">
              <el-icon><Connection /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">活跃线程</div>
              <div class="card-value">{{ systemInfo.threadCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 系统信息和监控 -->
    <el-row :gutter="20" class="monitor-content">
      <!-- 系统信息 -->
      <el-col :span="12">
        <el-card class="monitor-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>系统信息</span>
              <el-button type="primary" link @click="refreshSystemInfo">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <div class="system-info">
            <div class="info-item">
              <span class="info-label">操作系统：</span>
              <span class="info-value">{{ systemInfo.osName }} {{ systemInfo.osVersion }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">系统架构：</span>
              <span class="info-value">{{ systemInfo.osArch }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">处理器：</span>
              <span class="info-value">{{ systemInfo.availableProcessors }} 核</span>
            </div>
            <div class="info-item">
              <span class="info-label">Java版本：</span>
              <span class="info-value">{{ systemInfo.javaVersion }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">JVM厂商：</span>
              <span class="info-value">{{ systemInfo.javaVendor }}</span>
            </div>
            <div class="info-item">
              <span class="info-label">系统负载：</span>
              <span class="info-value">{{ formatLoadAverage() }}</span>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 系统健康状态 -->
      <el-col :span="12">
        <el-card class="monitor-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>系统健康状态</span>
              <el-button type="primary" link @click="refreshHealth">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <div class="health-status">
            <div class="health-item">
              <span class="health-label">数据库：</span>
              <el-tag :type="getHealthTagType(systemHealth.database)">
                {{ systemHealth.database }}
              </el-tag>
            </div>
            <div class="health-item">
              <span class="health-label">Redis：</span>
              <el-tag :type="getHealthTagType(systemHealth.redis)">
                {{ systemHealth.redis }}
              </el-tag>
            </div>
            <div class="health-item">
              <span class="health-label">磁盘：</span>
              <el-tag :type="getHealthTagType(systemHealth.disk)">
                {{ systemHealth.disk }}
              </el-tag>
            </div>
            <div class="health-item">
              <span class="health-label">内存：</span>
              <el-tag :type="getHealthTagType(systemHealth.memory)">
                {{ systemHealth.memory }}
              </el-tag>
            </div>
            <div class="health-item">
              <span class="health-label">整体状态：</span>
              <el-tag :type="getHealthTagType(systemHealth.status)" size="large">
                {{ getStatusText(systemHealth.status) }}
              </el-tag>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 内存和线程监控 -->
    <el-row :gutter="20" class="monitor-content">
      <!-- 内存使用情况 -->
      <el-col :span="12">
        <el-card class="monitor-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>内存使用情况</span>
            </div>
          </template>
          <div class="memory-usage">
            <el-progress
              :percentage="getMemoryUsagePercentage()"
              :color="getMemoryColor()"
              :stroke-width="20"
              :show-text="false"
            />
            <div class="memory-details">
              <div class="memory-item">
                <span>已用内存：</span>
                <span>{{ formatBytes(systemInfo.usedMemory) }}</span>
              </div>
              <div class="memory-item">
                <span>总内存：</span>
                <span>{{ formatBytes(systemInfo.totalMemory) }}</span>
              </div>
              <div class="memory-item">
                <span>最大内存：</span>
                <span>{{ formatBytes(systemInfo.maxMemory) }}</span>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>

      <!-- 线程信息 -->
      <el-col :span="12">
        <el-card class="monitor-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>线程信息</span>
            </div>
          </template>
          <div class="thread-info">
            <div class="thread-item">
              <span class="thread-label">当前线程数：</span>
              <span class="thread-value">{{ systemInfo.threadCount || 0 }}</span>
            </div>
            <div class="thread-item">
              <span class="thread-label">峰值线程数：</span>
              <span class="thread-value">{{ systemInfo.peakThreadCount || 0 }}</span>
            </div>
            <div class="thread-item">
              <span class="thread-label">守护线程数：</span>
              <span class="thread-value">{{ systemInfo.daemonThreadCount || 0 }}</span>
            </div>
            <div class="thread-item">
              <span class="thread-label">用户线程数：</span>
              <span class="thread-value">{{ (systemInfo.threadCount || 0) - (systemInfo.daemonThreadCount || 0) }}</span>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 系统统计 -->
    <el-row :gutter="20" class="monitor-content">
      <el-col :span="24">
        <el-card class="monitor-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>系统统计</span>
              <el-button type="primary" link @click="refreshStats">
                <el-icon><Refresh /></el-icon>
                刷新
              </el-button>
            </div>
          </template>
          <div class="system-stats">
            <el-row :gutter="20">
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-number">{{ systemStats.userCount || 0 }}</div>
                  <div class="stat-label">用户总数</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-number">{{ systemStats.roleCount || 0 }}</div>
                  <div class="stat-label">角色总数</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-number">{{ systemStats.menuCount || 0 }}</div>
                  <div class="stat-label">菜单总数</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-number">{{ systemStats.deptCount || 0 }}</div>
                  <div class="stat-label">部门总数</div>
                </div>
              </el-col>
            </el-row>
            <el-row :gutter="20" style="margin-top: 20px;">
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-number">{{ systemStats.todayLoginCount || 0 }}</div>
                  <div class="stat-label">今日登录</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-number">{{ systemStats.todayOperCount || 0 }}</div>
                  <div class="stat-label">今日操作</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-number">{{ systemStats.onlineUserCount || 0 }}</div>
                  <div class="stat-label">在线用户</div>
                </div>
              </el-col>
              <el-col :span="6">
                <div class="stat-item">
                  <div class="stat-number">{{ getUptime() }}</div>
                  <div class="stat-label">运行时间</div>
                </div>
              </el-col>
            </el-row>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import { ElMessage } from 'element-plus'
import { User, Monitor, Cpu, Connection, Refresh } from '@element-plus/icons-vue'

// 响应式数据
const systemInfo = ref<any>({})
const systemHealth = ref<any>({})
const systemStats = ref<any>({})
const refreshTimer = ref<NodeJS.Timeout | null>(null)

// 生命周期
onMounted(() => {
  loadAllData()
  // 每30秒自动刷新一次
  refreshTimer.value = setInterval(() => {
    loadAllData()
  }, 30000)
})

onUnmounted(() => {
  if (refreshTimer.value) {
    clearInterval(refreshTimer.value)
  }
})

// 加载所有数据
const loadAllData = async () => {
  await Promise.all([
    loadSystemInfo(),
    loadSystemHealth(),
    loadSystemStats()
  ])
}

// 加载系统信息
const loadSystemInfo = async () => {
  try {
    // TODO: 调用后端API获取系统信息
    // const response = await getSystemInfo()
    // if (response.code === 200) {
    //   systemInfo.value = response.data
    // }
    
    // 模拟数据
    systemInfo.value = {
      osName: 'Linux',
      osVersion: '6.12.8+',
      osArch: 'amd64',
      availableProcessors: 8,
      javaVersion: '17.0.9',
      javaVendor: 'Oracle Corporation',
      threadCount: 45,
      peakThreadCount: 52,
      daemonThreadCount: 12,
      totalMemory: 1073741824,
      maxMemory: 2147483648,
      usedMemory: 536870912
    }
  } catch (error) {
    console.error('加载系统信息失败:', error)
  }
}

// 加载系统健康状态
const loadSystemHealth = async () => {
  try {
    // TODO: 调用后端API获取系统健康状态
    // const response = await getSystemHealth()
    // if (response.code === 200) {
    //   systemHealth.value = response.data
    // }
    
    // 模拟数据
    systemHealth.value = {
      database: 'UP',
      redis: 'UP',
      disk: 'UP',
      memory: 'UP',
      status: 'UP',
      timestamp: Date.now()
    }
  } catch (error) {
    console.error('加载系统健康状态失败:', error)
  }
}

// 加载系统统计
const loadSystemStats = async () => {
  try {
    // TODO: 调用后端API获取系统统计
    // const response = await getSystemStats()
    // if (response.code === 200) {
    //   systemStats.value = response.data
    // }
    
    // 模拟数据
    systemStats.value = {
      userCount: 156,
      roleCount: 24,
      menuCount: 89,
      deptCount: 18,
      onlineUserCount: 23,
      todayLoginCount: 45,
      todayOperCount: 128
    }
  } catch (error) {
    console.error('加载系统统计失败:', error)
  }
}

// 刷新系统信息
const refreshSystemInfo = () => {
  loadSystemInfo()
  ElMessage.success('系统信息已刷新')
}

// 刷新健康状态
const refreshHealth = () => {
  loadSystemHealth()
  ElMessage.success('健康状态已刷新')
}

// 刷新统计信息
const refreshStats = () => {
  loadSystemStats()
  ElMessage.success('统计信息已刷新')
}

// 工具方法
const getStatusClass = (status: string) => {
  return status === 'UP' ? 'status-up' : 'status-down'
}

const getStatusText = (status: string) => {
  return status === 'UP' ? '正常' : '异常'
}

const getHealthTagType = (status: string) => {
  if (status === 'UP') return 'success'
  if (status === 'WARNING') return 'warning'
  return 'danger'
}

const formatMemoryUsage = () => {
  if (!systemInfo.value.usedMemory || !systemInfo.value.maxMemory) return 0
  return Math.round((systemInfo.value.usedMemory / systemInfo.value.maxMemory) * 100)
}

const getMemoryUsagePercentage = () => {
  return formatMemoryUsage()
}

const getMemoryColor = () => {
  const usage = getMemoryUsagePercentage()
  if (usage < 70) return '#67C23A'
  if (usage < 90) return '#E6A23C'
  return '#F56C6C'
}

const formatBytes = (bytes: number) => {
  if (!bytes) return '0 B'
  const k = 1024
  const sizes = ['B', 'KB', 'MB', 'GB']
  const i = Math.floor(Math.log(bytes) / Math.log(k))
  return parseFloat((bytes / Math.pow(k, i)).toFixed(2)) + ' ' + sizes[i]
}

const formatLoadAverage = () => {
  const load = systemInfo.value.systemLoadAverage
  return load ? load.toFixed(2) : 'N/A'
}

const getUptime = () => {
  // TODO: 实现获取系统运行时间的逻辑
  return '3天 12小时'
}
</script>

<style lang="scss" scoped>
.monitor-dashboard {
  padding: 20px;

  .overview-cards {
    margin-bottom: 20px;

    .overview-card {
      .card-content {
        display: flex;
        align-items: center;
        padding: 10px;

        .card-icon {
          width: 60px;
          height: 60px;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          margin-right: 15px;
          font-size: 24px;
          color: white;

          &.user-icon {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          }

          &.system-icon {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
          }

          &.memory-icon {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
          }

          &.thread-icon {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
          }
        }

        .card-info {
          flex: 1;

          .card-title {
            font-size: 14px;
            color: #666;
            margin-bottom: 5px;
          }

          .card-value {
            font-size: 24px;
            font-weight: bold;
            color: #333;

            &.status-up {
              color: #67C23A;
            }

            &.status-down {
              color: #F56C6C;
            }
          }
        }
      }
    }
  }

  .monitor-content {
    margin-bottom: 20px;

    .monitor-card {
      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
      }

      .system-info {
        .info-item {
          display: flex;
          justify-content: space-between;
          padding: 8px 0;
          border-bottom: 1px solid #f0f0f0;

          &:last-child {
            border-bottom: none;
          }

          .info-label {
            color: #666;
            font-weight: 500;
          }

          .info-value {
            color: #333;
          }
        }
      }

      .health-status {
        .health-item {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 8px 0;
          border-bottom: 1px solid #f0f0f0;

          &:last-child {
            border-bottom: none;
          }

          .health-label {
            color: #666;
            font-weight: 500;
          }
        }
      }

      .memory-usage {
        .memory-details {
          margin-top: 20px;

          .memory-item {
            display: flex;
            justify-content: space-between;
            padding: 5px 0;
            color: #666;
          }
        }
      }

      .thread-info {
        .thread-item {
          display: flex;
          justify-content: space-between;
          padding: 8px 0;
          border-bottom: 1px solid #f0f0f0;

          &:last-child {
            border-bottom: none;
          }

          .thread-label {
            color: #666;
            font-weight: 500;
          }

          .thread-value {
            color: #333;
            font-weight: bold;
          }
        }
      }

      .system-stats {
        .stat-item {
          text-align: center;
          padding: 20px;

          .stat-number {
            font-size: 32px;
            font-weight: bold;
            color: #409EFF;
            margin-bottom: 10px;
          }

          .stat-label {
            color: #666;
            font-size: 14px;
          }
        }
      }
    }
  }
}
</style>