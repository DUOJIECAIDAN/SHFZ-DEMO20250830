package com.shanhaifangzhou.admin.report.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 报表管理实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_report")
public class SysReport implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 报表ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 报表名称
     */
    @TableField("report_name")
    private String reportName;

    /**
     * 报表编码
     */
    @TableField("report_code")
    private String reportCode;

    /**
     * 报表类型（1：统计报表，2：图表报表，3：明细报表，4：仪表盘）
     */
    @TableField("report_type")
    private Integer reportType;

    /**
     * 报表分类ID
     */
    @TableField("category_id")
    private Long categoryId;

    /**
     * 报表分类名称
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 数据源类型（1：数据库，2：API接口，3：文件，4：自定义SQL）
     */
    @TableField("data_source_type")
    private Integer dataSourceType;

    /**
     * 数据源配置（JSON格式）
     */
    @TableField("data_source_config")
    private String dataSourceConfig;

    /**
     * 查询SQL
     */
    @TableField("query_sql")
    private String querySql;

    /**
     * 报表参数（JSON格式）
     */
    @TableField("report_params")
    private String reportParams;

    /**
     * 图表配置（JSON格式）
     */
    @TableField("chart_config")
    private String chartConfig;

    /**
     * 报表模板（JSON格式）
     */
    @TableField("report_template")
    private String reportTemplate;

    /**
     * 刷新频率（分钟）
     */
    @TableField("refresh_interval")
    private Integer refreshInterval;

    /**
     * 是否自动刷新（0：否，1：是）
     */
    @TableField("auto_refresh")
    private Integer autoRefresh;

    /**
     * 是否缓存（0：否，1：是）
     */
    @TableField("is_cache")
    private Integer isCache;

    /**
     * 缓存时间（分钟）
     */
    @TableField("cache_time")
    private Integer cacheTime;

    /**
     * 报表状态（0：禁用，1：启用）
     */
    @TableField("status")
    private Integer status;

    /**
     * 是否公开（0：私有，1：公开）
     */
    @TableField("is_public")
    private Integer isPublic;

    /**
     * 访问权限（1：全部用户，2：指定角色，3：指定部门，4：指定用户）
     */
    @TableField("access_permission")
    private Integer accessPermission;

    /**
     * 访问范围（JSON格式）
     */
    @TableField("access_scope")
    private String accessScope;

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