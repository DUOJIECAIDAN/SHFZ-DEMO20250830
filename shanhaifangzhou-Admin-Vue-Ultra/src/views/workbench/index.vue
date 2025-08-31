<template>
  <div class="workbench">
    <!-- 工作台概览 -->
    <el-row :gutter="20" class="overview-section">
      <el-col :span="6">
        <el-card class="overview-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon todo-icon">
              <el-icon><Clock /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">待办任务</div>
              <div class="card-value">{{ overview.todoCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="overview-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon processing-icon">
              <el-icon><Loading /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">进行中任务</div>
              <div class="card-value">{{ overview.processingCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="overview-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon completed-icon">
              <el-icon><Check /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">已完成任务</div>
              <div class="card-value">{{ overview.completedCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card class="overview-card" shadow="hover">
          <div class="card-content">
            <div class="card-icon urgent-icon">
              <el-icon><Warning /></el-icon>
            </div>
            <div class="card-info">
              <div class="card-title">紧急任务</div>
              <div class="card-value">{{ overview.urgentCount || 0 }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 主要内容区域 -->
    <el-row :gutter="20" class="main-content">
      <!-- 左侧任务列表 -->
      <el-col :span="16">
        <el-card class="task-list-card" shadow="never">
          <template #header>
            <div class="card-header">
              <span>我的任务</span>
              <div class="header-actions">
                <el-button type="primary" @click="handleAddTask">
                  <el-icon><Plus /></el-icon>
                  新建任务
                </el-button>
                <el-button @click="handleRefreshTasks">
                  <el-icon><Refresh /></el-icon>
                  刷新
                </el-button>
              </div>
            </div>
          </template>

          <!-- 任务筛选 -->
          <div class="task-filter">
            <el-form :inline="true" :model="taskFilter" label-width="80px">
              <el-form-item label="任务状态">
                <el-select v-model="taskFilter.status" placeholder="全部状态" clearable>
                  <el-option label="待处理" :value="1" />
                  <el-option label="处理中" :value="2" />
                  <el-option label="已完成" :value="3" />
                  <el-option label="已取消" :value="4" />
                  <el-option label="已暂停" :value="5" />
                </el-select>
              </el-form-item>
              <el-form-item label="优先级">
                <el-select v-model="taskFilter.priority" placeholder="全部优先级" clearable>
                  <el-option label="低" :value="1" />
                  <el-option label="中" :value="2" />
                  <el-option label="高" :value="3" />
                  <el-option label="紧急" :value="4" />
                </el-select>
              </el-form-item>
              <el-form-item label="任务类型">
                <el-select v-model="taskFilter.type" placeholder="全部类型" clearable>
                  <el-option label="待办任务" :value="1" />
                  <el-option label="进行中任务" :value="2" />
                  <el-option label="已完成任务" :value="3" />
                  <el-option label="已取消任务" :value="4" />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" @click="handleFilterTasks">筛选</el-button>
                <el-button @click="handleResetFilter">重置</el-button>
              </el-form-item>
            </el-form>
          </div>

          <!-- 任务列表 -->
          <div class="task-list">
            <div
              v-for="task in taskList"
              :key="task.id"
              class="task-item"
              :class="{ 'task-urgent': task.priority === 4, 'task-top': task.isTop }"
            >
              <div class="task-header">
                <div class="task-title">
                  <el-icon v-if="task.isTop" class="top-icon"><Star /></el-icon>
                  <span>{{ task.taskTitle }}</span>
                </div>
                <div class="task-actions">
                  <el-button type="primary" link @click="handleEditTask(task)">
                    编辑
                  </el-button>
                  <el-button type="success" link @click="handleStartTask(task)">
                    开始
                  </el-button>
                  <el-button type="warning" link @click="handlePauseTask(task)">
                    暂停
                  </el-button>
                  <el-button type="info" link @click="handleCompleteTask(task)">
                    完成
                  </el-button>
                </div>
              </div>
              
              <div class="task-content">
                <p class="task-description">{{ task.taskDescription }}</p>
                <div class="task-meta">
                  <span class="task-priority">
                    <el-tag :type="getPriorityType(task.priority)" size="small">
                      {{ getPriorityText(task.priority) }}
                    </el-tag>
                  </span>
                  <span class="task-status">
                    <el-tag :type="getStatusType(task.taskStatus)" size="small">
                      {{ getStatusText(task.taskStatus) }}
                    </el-tag>
                  </span>
                  <span class="task-progress">
                    进度: {{ task.progress || 0 }}%
                  </span>
                </div>
              </div>

              <div class="task-footer">
                <div class="task-info">
                  <span class="task-creator">创建人: {{ task.creatorName }}</span>
                  <span class="task-assignee">负责人: {{ task.assigneeName }}</span>
                  <span class="task-dept">部门: {{ task.deptName }}</span>
                </div>
                <div class="task-time">
                  <span class="plan-time">
                    计划: {{ formatDateTime(task.planStartTime) }} - {{ formatDateTime(task.planEndTime) }}
                  </span>
                  <span class="create-time">
                    创建: {{ formatDateTime(task.createTime) }}
                  </span>
                </div>
              </div>
            </div>

            <!-- 空状态 -->
            <div v-if="taskList.length === 0" class="empty-state">
              <el-empty description="暂无任务" />
            </div>
          </div>

          <!-- 分页 -->
          <div class="pagination-wrapper">
            <el-pagination
              v-model:current-page="taskFilter.current"
              v-model:page-size="taskFilter.size"
              :page-sizes="[10, 20, 50, 100]"
              :total="total"
              layout="total, sizes, prev, pager, next, jumper"
              @size-change="handleSizeChange"
              @current-change="handleCurrentChange"
            />
          </div>
        </el-card>
      </el-col>

      <!-- 右侧快捷操作和统计 -->
      <el-col :span="8">
        <!-- 快捷操作 -->
        <el-card class="quick-actions-card" shadow="never">
          <template #header>
            <span>快捷操作</span>
          </template>
          <div class="quick-actions">
            <el-button type="primary" @click="handleQuickTask">
              <el-icon><Plus /></el-icon>
              快速创建任务
            </el-button>
            <el-button type="success" @click="handleBatchComplete">
              <el-icon><Check /></el-icon>
              批量完成任务
            </el-button>
            <el-button type="warning" @click="handleExportTasks">
              <el-icon><Download /></el-icon>
              导出任务
            </el-button>
            <el-button type="info" @click="handleTaskTemplate">
              <el-icon><Document /></el-icon>
              任务模板
            </el-button>
          </div>
        </el-card>

        <!-- 任务统计 -->
        <el-card class="task-stats-card" shadow="never">
          <template #header>
            <span>任务统计</span>
          </template>
          <div class="task-stats">
            <div class="stat-item">
              <div class="stat-label">本周完成任务</div>
              <div class="stat-value">{{ stats.weekCompleted || 0 }}</div>
            </div>
            <div class="stat-item">
              <div class="stat-label">本月完成任务</div>
              <div class="stat-value">{{ stats.monthCompleted || 0 }}</div>
            </div>
            <div class="stat-item">
              <div class="stat-label">任务完成率</div>
              <div class="stat-value">{{ stats.completionRate || 0 }}%</div>
            </div>
            <div class="stat-item">
              <div class="stat-label">平均处理时长</div>
              <div class="stat-value">{{ stats.avgDuration || 0 }}小时</div>
            </div>
          </div>
        </el-card>

        <!-- 最近活动 -->
        <el-card class="recent-activities-card" shadow="never">
          <template #header>
            <span>最近活动</span>
          </template>
          <div class="recent-activities">
            <div
              v-for="activity in recentActivities"
              :key="activity.id"
              class="activity-item"
            >
              <div class="activity-icon">
                <el-icon><component :is="activity.icon" /></el-icon>
              </div>
              <div class="activity-content">
                <div class="activity-title">{{ activity.title }}</div>
                <div class="activity-time">{{ formatDateTime(activity.time) }}</div>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 任务表单对话框 -->
    <TaskFormDialog
      v-model="taskFormVisible"
      :task-data="currentTask"
      @success="handleTaskSuccess"
    />
  </div>
</template>

<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import {
  Clock,
  Loading,
  Check,
  Warning,
  Plus,
  Refresh,
  Star,
  Document,
  Download
} from '@element-plus/icons-vue'
import TaskFormDialog from './components/TaskFormDialog.vue'
import { formatDateTime } from '@/utils/format'

// 响应式数据
const overview = ref<any>({})
const taskList = ref<any[]>([])
const total = ref(0)
const stats = ref<any>({})
const recentActivities = ref<any[]>([])
const taskFormVisible = ref(false)
const currentTask = ref<any>(null)

// 任务筛选
const taskFilter = reactive({
  current: 1,
  size: 10,
  status: undefined,
  priority: undefined,
  type: undefined
})

// 生命周期
onMounted(() => {
  loadOverview()
  loadTaskList()
  loadStats()
  loadRecentActivities()
})

// 加载概览数据
const loadOverview = async () => {
  try {
    // TODO: 调用后端API获取概览数据
    // const response = await getWorkbenchOverview()
    // if (response.code === 200) {
    //   overview.value = response.data
    // }
    
    // 模拟数据
    overview.value = {
      todoCount: 12,
      processingCount: 8,
      completedCount: 45,
      urgentCount: 3
    }
  } catch (error) {
    console.error('加载概览数据失败:', error)
  }
}

// 加载任务列表
const loadTaskList = async () => {
  try {
    // TODO: 调用后端API获取任务列表
    // const response = await getTaskPage(taskFilter)
    // if (response.code === 200) {
    //   taskList.value = response.data.records || []
    //   total.value = response.data.total || 0
    // }
    
    // 模拟数据
    taskList.value = [
      {
        id: 1,
        taskTitle: '完成用户管理模块开发',
        taskDescription: '实现用户的增删改查功能，包括用户信息管理、角色分配等',
        priority: 3,
        taskStatus: 2,
        progress: 75,
        creatorName: '张三',
        assigneeName: '李四',
        deptName: '技术部',
        planStartTime: new Date('2024-01-15'),
        planEndTime: new Date('2024-01-20'),
        createTime: new Date('2024-01-10'),
        isTop: 1
      },
      {
        id: 2,
        taskTitle: '系统性能优化',
        taskDescription: '对系统进行性能分析，优化数据库查询和接口响应速度',
        priority: 4,
        taskStatus: 1,
        progress: 0,
        creatorName: '王五',
        assigneeName: '赵六',
        deptName: '技术部',
        planStartTime: new Date('2024-01-18'),
        planEndTime: new Date('2024-01-25'),
        createTime: new Date('2024-01-12'),
        isTop: 0
      }
    ]
    total.value = taskList.value.length
  } catch (error) {
    console.error('加载任务列表失败:', error)
    ElMessage.error('加载任务列表失败')
  }
}

// 加载统计数据
const loadStats = async () => {
  try {
    // TODO: 调用后端API获取统计数据
    // const response = await getTaskStats()
    // if (response.code === 200) {
    //   stats.value = response.data
    // }
    
    // 模拟数据
    stats.value = {
      weekCompleted: 15,
      monthCompleted: 67,
      completionRate: 85,
      avgDuration: 2.5
    }
  } catch (error) {
    console.error('加载统计数据失败:', error)
  }
}

// 加载最近活动
const loadRecentActivities = async () => {
  try {
    // TODO: 调用后端API获取最近活动
    // const response = await getRecentActivities()
    // if (response.code === 200) {
    //   recentActivities.value = response.data
    // }
    
    // 模拟数据
    recentActivities.value = [
      {
        id: 1,
        title: '创建了新任务：系统性能优化',
        time: new Date('2024-01-12 10:30:00'),
        icon: 'Plus'
      },
      {
        id: 2,
        title: '完成了任务：用户管理模块开发',
        time: new Date('2024-01-11 16:45:00'),
        icon: 'Check'
      },
      {
        id: 3,
        title: '更新了任务：数据库设计优化',
        time: new Date('2024-01-10 14:20:00'),
        icon: 'Edit'
      }
    ]
  } catch (error) {
    console.error('加载最近活动失败:', error)
  }
}

// 筛选任务
const handleFilterTasks = () => {
  taskFilter.current = 1
  loadTaskList()
}

// 重置筛选
const handleResetFilter = () => {
  Object.assign(taskFilter, {
    current: 1,
    size: 10,
    status: undefined,
    priority: undefined,
    type: undefined
  })
  loadTaskList()
}

// 新建任务
const handleAddTask = () => {
  currentTask.value = null
  taskFormVisible.value = true
}

// 编辑任务
const handleEditTask = (task: any) => {
  currentTask.value = task
  taskFormVisible.value = true
}

// 开始任务
const handleStartTask = async (task: any) => {
  try {
    // TODO: 调用后端API开始任务
    // const response = await startTask(task.id)
    // if (response.code === 200) {
    //   ElMessage.success('任务已开始')
    //   loadTaskList()
    // }
    
    ElMessage.success('任务已开始')
    loadTaskList()
  } catch (error) {
    console.error('开始任务失败:', error)
    ElMessage.error('开始任务失败')
  }
}

// 暂停任务
const handlePauseTask = async (task: any) => {
  try {
    // TODO: 调用后端API暂停任务
    // const response = await pauseTask(task.id)
    // if (response.code === 200) {
    //   ElMessage.success('任务已暂停')
    //   loadTaskList()
    // }
    
    ElMessage.success('任务已暂停')
    loadTaskList()
  } catch (error) {
    console.error('暂停任务失败:', error)
    ElMessage.error('暂停任务失败')
  }
}

// 完成任务
const handleCompleteTask = async (task: any) => {
  try {
    // TODO: 调用后端API完成任务
    // const response = await completeTask(task.id)
    // if (response.code === 200) {
    //   ElMessage.success('任务已完成')
    //   loadTaskList()
    //   loadOverview()
    //   loadStats()
    // }
    
    ElMessage.success('任务已完成')
    loadTaskList()
    loadOverview()
    loadStats()
  } catch (error) {
    console.error('完成任务失败:', error)
    ElMessage.error('完成任务失败')
  }
}

// 刷新任务
const handleRefreshTasks = () => {
  loadTaskList()
}

// 快速创建任务
const handleQuickTask = () => {
  handleAddTask()
}

// 批量完成任务
const handleBatchComplete = () => {
  ElMessage.info('批量完成功能开发中...')
}

// 导出任务
const handleExportTasks = () => {
  ElMessage.info('导出功能开发中...')
}

// 任务模板
const handleTaskTemplate = () => {
  ElMessage.info('任务模板功能开发中...')
}

// 分页大小变化
const handleSizeChange = (size: number) => {
  taskFilter.size = size
  taskFilter.current = 1
  loadTaskList()
}

// 当前页变化
const handleCurrentChange = (current: number) => {
  taskFilter.current = current
  loadTaskList()
}

// 任务操作成功回调
const handleTaskSuccess = () => {
  loadTaskList()
  loadOverview()
  loadStats()
}

// 工具方法
const getPriorityType = (priority: number) => {
  const types: Record<number, string> = {
    1: 'info',
    2: 'warning',
    3: 'danger',
    4: 'danger'
  }
  return types[priority] || 'info'
}

const getPriorityText = (priority: number) => {
  const texts: Record<number, string> = {
    1: '低',
    2: '中',
    3: '高',
    4: '紧急'
  }
  return texts[priority] || '未知'
}

const getStatusType = (status: number) => {
  const types: Record<number, string> = {
    1: 'info',
    2: 'warning',
    3: 'success',
    4: 'danger',
    5: 'info'
  }
  return types[status] || 'info'
}

const getStatusText = (status: number) => {
  const texts: Record<number, string> = {
    1: '待处理',
    2: '处理中',
    3: '已完成',
    4: '已取消',
    5: '已暂停'
  }
  return texts[status] || '未知'
}
</script>

<style lang="scss" scoped>
.workbench {
  padding: 20px;

  .overview-section {
    margin-bottom: 20px;

    .overview-card {
      .card-content {
        display: flex;
        align-items: center;
        padding: 20px;

        .card-icon {
          width: 60px;
          height: 60px;
          border-radius: 50%;
          display: flex;
          align-items: center;
          justify-content: center;
          margin-right: 20px;
          font-size: 24px;
          color: white;

          &.todo-icon {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
          }

          &.processing-icon {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
          }

          &.completed-icon {
            background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
          }

          &.urgent-icon {
            background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
          }
        }

        .card-info {
          flex: 1;

          .card-title {
            font-size: 14px;
            color: #666;
            margin-bottom: 8px;
          }

          .card-value {
            font-size: 28px;
            font-weight: bold;
            color: #333;
          }
        }
      }
    }
  }

  .main-content {
    .task-list-card {
      .card-header {
        display: flex;
        justify-content: space-between;
        align-items: center;

        .header-actions {
          display: flex;
          gap: 10px;
        }
      }

      .task-filter {
        margin-bottom: 20px;
        padding: 20px;
        background: #f8f9fa;
        border-radius: 8px;
      }

      .task-list {
        .task-item {
          border: 1px solid #e8e8e8;
          border-radius: 8px;
          margin-bottom: 16px;
          padding: 20px;
          transition: all 0.3s;

          &:hover {
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
          }

          &.task-urgent {
            border-color: #f56c6c;
            background: #fef0f0;
          }

          &.task-top {
            border-color: #e6a23c;
            background: #fdf6ec;
          }

          .task-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 12px;

            .task-title {
              display: flex;
              align-items: center;
              font-size: 16px;
              font-weight: bold;
              color: #333;

              .top-icon {
                color: #e6a23c;
                margin-right: 8px;
              }
            }

            .task-actions {
              display: flex;
              gap: 8px;
            }
          }

          .task-content {
            margin-bottom: 16px;

            .task-description {
              color: #666;
              margin-bottom: 12px;
              line-height: 1.5;
            }

            .task-meta {
              display: flex;
              gap: 16px;
              align-items: center;

              .task-progress {
                color: #666;
                font-size: 14px;
              }
            }
          }

          .task-footer {
            display: flex;
            justify-content: space-between;
            align-items: center;
            padding-top: 16px;
            border-top: 1px solid #f0f0f0;

            .task-info {
              display: flex;
              gap: 16px;
              color: #666;
              font-size: 14px;
            }

            .task-time {
              display: flex;
              flex-direction: column;
              gap: 4px;
              color: #999;
              font-size: 12px;
              text-align: right;
            }
          }
        }

        .empty-state {
          padding: 60px 0;
          text-align: center;
        }
      }

      .pagination-wrapper {
        margin-top: 20px;
        display: flex;
        justify-content: flex-end;
      }
    }

    .quick-actions-card {
      margin-bottom: 20px;

      .quick-actions {
        display: flex;
        flex-direction: column;
        gap: 12px;

        .el-button {
          justify-content: flex-start;
        }
      }
    }

    .task-stats-card {
      margin-bottom: 20px;

      .task-stats {
        .stat-item {
          display: flex;
          justify-content: space-between;
          align-items: center;
          padding: 12px 0;
          border-bottom: 1px solid #f0f0f0;

          &:last-child {
            border-bottom: none;
          }

          .stat-label {
            color: #666;
            font-size: 14px;
          }

          .stat-value {
            color: #409EFF;
            font-size: 18px;
            font-weight: bold;
          }
        }
      }
    }

    .recent-activities-card {
      .recent-activities {
        .activity-item {
          display: flex;
          align-items: center;
          padding: 12px 0;
          border-bottom: 1px solid #f0f0f0;

          &:last-child {
            border-bottom: none;
          }

          .activity-icon {
            width: 32px;
            height: 32px;
            border-radius: 50%;
            background: #f0f9ff;
            display: flex;
            align-items: center;
            justify-content: center;
            margin-right: 12px;
            color: #409EFF;
          }

          .activity-content {
            flex: 1;

            .activity-title {
              color: #333;
              font-size: 14px;
              margin-bottom: 4px;
            }

            .activity-time {
              color: #999;
              font-size: 12px;
            }
          }
        }
      }
    }
  }
}
</style>