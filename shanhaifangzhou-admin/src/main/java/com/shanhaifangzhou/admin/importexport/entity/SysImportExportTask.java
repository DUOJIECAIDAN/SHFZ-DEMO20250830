package com.shanhaifangzhou.admin.importexport.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 数据导入导出任务实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_import_export_task")
public class SysImportExportTask implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 任务ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 任务名称
     */
    @TableField("task_name")
    private String taskName;

    /**
     * 任务类型（1：数据导入，2：数据导出）
     */
    @TableField("task_type")
    private Integer taskType;

    /**
     * 业务模块
     */
    @TableField("business_module")
    private String businessModule;

    /**
     * 业务表名
     */
    @TableField("business_table")
    private String businessTable;

    /**
     * 文件类型（1：Excel，2：CSV，3：JSON，4：XML，5：SQL）
     */
    @TableField("file_type")
    private Integer fileType;

    /**
     * 文件路径
     */
    @TableField("file_path")
    private String filePath;

    /**
     * 文件大小（字节）
     */
    @TableField("file_size")
    private Long fileSize;

    /**
     * 文件MD5值
     */
    @TableField("file_md5")
    private String fileMd5;

    /**
     * 任务状态（1：等待中，2：处理中，3：已完成，4：已失败，5：已取消）
     */
    @TableField("task_status")
    private Integer taskStatus;

    /**
     * 进度百分比（0-100）
     */
    @TableField("progress")
    private Integer progress;

    /**
     * 总记录数
     */
    @TableField("total_count")
    private Long totalCount;

    /**
     * 成功记录数
     */
    @TableField("success_count")
    private Long successCount;

    /**
     * 失败记录数
     */
    @TableField("failure_count")
    private Long failureCount;

    /**
     * 跳过记录数
     */
    @TableField("skip_count")
    private Long skipCount;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 完成时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 耗时（毫秒）
     */
    @TableField("duration")
    private Long duration;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

    /**
     * 错误详情（JSON格式）
     */
    @TableField("error_details")
    private String errorDetails;

    /**
     * 导入/导出配置（JSON格式）
     */
    @TableField("task_config")
    private String taskConfig;

    /**
     * 字段映射（JSON格式）
     */
    @TableField("field_mapping")
    private String fieldMapping;

    /**
     * 数据验证规则（JSON格式）
     */
    @TableField("validation_rules")
    private String validationRules;

    /**
     * 数据转换规则（JSON格式）
     */
    @TableField("transformation_rules")
    private String transformationRules;

    /**
     * 是否覆盖现有数据（0：否，1：是）
     */
    @TableField("is_overwrite")
    private Integer isOverwrite;

    /**
     * 是否跳过重复数据（0：否，1：是）
     */
    @TableField("is_skip_duplicate")
    private Integer isSkipDuplicate;

    /**
     * 是否验证数据（0：否，1：是）
     */
    @TableField("is_validate")
    private Integer isValidate;

    /**
     * 是否转换数据（0：否，1：是）
     */
    @TableField("is_transform")
    private Integer isTransform;

    /**
     * 批次大小
     */
    @TableField("batch_size")
    private Integer batchSize;

    /**
     * 最大错误数
     */
    @TableField("max_error_count")
    private Integer maxErrorCount;

    /**
     * 任务优先级（1：低，2：中，3：高，4：紧急）
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 是否定时执行（0：否，1：是）
     */
    @TableField("is_scheduled")
    private Integer isScheduled;

    /**
     * Cron表达式
     */
    @TableField("cron_expression")
    private String cronExpression;

    /**
     * 下次执行时间
     */
    @TableField("next_execute_time")
    private LocalDateTime nextExecuteTime;

    /**
     * 执行次数
     */
    @TableField("execute_count")
    private Integer executeCount;

    /**
     * 最后执行时间
     */
    @TableField("last_execute_time")
    private LocalDateTime lastExecuteTime;

    /**
     * 是否启用（0：禁用，1：启用）
     */
    @TableField("is_enabled")
    private Integer isEnabled;

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