package com.shanhaifangzhou.admin.schedule.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 定时任务实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_schedule_job")
public class SysScheduleJob implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    @TableField("job_name")
    private String jobName;

    /**
     * 任务组名
     */
    @TableField("job_group")
    private String jobGroup;

    /**
     * 任务类名
     */
    @TableField("job_class")
    private String jobClass;

    /**
     * 任务方法名
     */
    @TableField("job_method")
    private String jobMethod;

    /**
     * 任务参数（JSON格式）
     */
    @TableField("job_params")
    private String jobParams;

    /**
     * Cron表达式
     */
    @TableField("cron_expression")
    private String cronExpression;

    /**
     * 任务描述
     */
    @TableField("job_description")
    private String jobDescription;

    /**
     * 任务状态（1：正常，2：暂停，3：删除）
     */
    @TableField("job_status")
    private Integer jobStatus;

    /**
     * 是否并发执行（0：禁止，1：允许）
     */
    @TableField("is_concurrent")
    private Integer isConcurrent;

    /**
     * 是否持久化（0：否，1：是）
     */
    @TableField("is_persistent")
    private Integer isPersistent;

    /**
     * 是否恢复执行（0：否，1：是）
     */
    @TableField("is_recovery")
    private Integer isRecovery;

    /**
     * 任务优先级
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 任务超时时间（秒）
     */
    @TableField("timeout")
    private Integer timeout;

    /**
     * 失败重试次数
     */
    @TableField("retry_count")
    private Integer retryCount;

    /**
     * 重试间隔（秒）
     */
    @TableField("retry_interval")
    private Integer retryInterval;

    /**
     * 任务类型（1：Java类，2：Spring Bean，3：Shell脚本，4：HTTP请求）
     */
    @TableField("job_type")
    private Integer jobType;

    /**
     * 任务配置（JSON格式）
     */
    @TableField("job_config")
    private String jobConfig;

    /**
     * 任务标签
     */
    @TableField("job_tags")
    private String jobTags;

    /**
     * 任务分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 任务分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 上次执行时间
     */
    @TableField("last_execute_time")
    private LocalDateTime lastExecuteTime;

    /**
     * 下次执行时间
     */
    @TableField("next_execute_time")
    private LocalDateTime nextExecuteTime;

    /**
     * 执行次数
     */
    @TableField("execute_count")
    private Long executeCount;

    /**
     * 成功次数
     */
    @TableField("success_count")
    private Long successCount;

    /**
     * 失败次数
     */
    @TableField("failure_count")
    private Long failureCount;

    /**
     * 平均执行时间（毫秒）
     */
    @TableField("avg_execute_time")
    private Long avgExecuteTime;

    /**
     * 最长执行时间（毫秒）
     */
    @TableField("max_execute_time")
    private Long maxExecuteTime;

    /**
     * 最短执行时间（毫秒）
     */
    @TableField("min_execute_time")
    private Long minExecuteTime;

    /**
     * 最后执行结果
     */
    @TableField("last_execute_result")
    private String lastExecuteResult;

    /**
     * 最后执行错误信息
     */
    @TableField("last_error_message")
    private String lastErrorMessage;

    /**
     * 是否启用（0：禁用，1：启用）
     */
    @TableField("is_enabled")
    private Integer isEnabled;

    /**
     * 排序
     */
    @TableField("sort")
    private Integer sort;

    /**
     * 备注
     */
    @TableField("remark")
    private String remark;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建者
     */
    @TableField(value = "create_by", fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新者
     */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
}