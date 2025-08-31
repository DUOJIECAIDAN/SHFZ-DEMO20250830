package com.shanhaifangzhou.admin.log.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 日志分析实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_log_analysis")
public class SysLogAnalysis implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 分析ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 分析名称
     */
    @TableField("analysis_name")
    private String analysisName;

    /**
     * 分析类型（1：操作日志分析，2：登录日志分析，3：系统日志分析，4：性能日志分析）
     */
    @TableField("analysis_type")
    private Integer analysisType;

    /**
     * 分析维度（1：按用户，2：按模块，3：按时间，4：按IP地址，5：按操作类型）
     */
    @TableField("analysis_dimension")
    private Integer analysisDimension;

    /**
     * 时间范围类型（1：今天，2：昨天，3：最近7天，4：最近30天，5：自定义）
     */
    @TableField("time_range_type")
    private Integer timeRangeType;

    /**
     * 开始时间
     */
    @TableField("start_time")
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    @TableField("end_time")
    private LocalDateTime endTime;

    /**
     * 查询条件（JSON格式）
     */
    @TableField("query_conditions")
    private String queryConditions;

    /**
     * 分析结果（JSON格式）
     */
    @TableField("analysis_result")
    private String analysisResult;

    /**
     * 图表配置（JSON格式）
     */
    @TableField("chart_config")
    private String chartConfig;

    /**
     * 统计指标（JSON格式）
     */
    @TableField("statistics_metrics")
    private String statisticsMetrics;

    /**
     * 异常统计（JSON格式）
     */
    @TableField("exception_statistics")
    private String exceptionStatistics;

    /**
     * 性能统计（JSON格式）
     */
    @TableField("performance_statistics")
    private String performanceStatistics;

    /**
     * 用户行为分析（JSON格式）
     */
    @TableField("user_behavior_analysis")
    private String userBehaviorAnalysis;

    /**
     * 系统健康度评分
     */
    @TableField("health_score")
    private Double healthScore;

    /**
     * 风险等级（1：低风险，2：中风险，3：高风险）
     */
    @TableField("risk_level")
    private Integer riskLevel;

    /**
     * 风险描述
     */
    @TableField("risk_description")
    private String riskDescription;

    /**
     * 建议措施
     */
    @TableField("suggestions")
    private String suggestions;

    /**
     * 是否定时分析（0：否，1：是）
     */
    @TableField("is_scheduled")
    private Integer isScheduled;

    /**
     * 定时表达式
     */
    @TableField("cron_expression")
    private String cronExpression;

    /**
     * 上次分析时间
     */
    @TableField("last_analysis_time")
    private LocalDateTime lastAnalysisTime;

    /**
     * 下次分析时间
     */
    @TableField("next_analysis_time")
    private LocalDateTime nextAnalysisTime;

    /**
     * 分析状态（1：待分析，2：分析中，3：已完成，4：分析失败）
     */
    @TableField("analysis_status")
    private Integer analysisStatus;

    /**
     * 分析耗时（毫秒）
     */
    @TableField("analysis_duration")
    private Long analysisDuration;

    /**
     * 错误信息
     */
    @TableField("error_message")
    private String errorMessage;

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