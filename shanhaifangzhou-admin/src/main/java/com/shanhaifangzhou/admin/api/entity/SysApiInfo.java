package com.shanhaifangzhou.admin.api.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * API管理实体
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("sys_api_info")
public class SysApiInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * API ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * API名称
     */
    @TableField("api_name")
    private String apiName;

    /**
     * API路径
     */
    @TableField("api_path")
    private String apiPath;

    /**
     * HTTP方法（GET、POST、PUT、DELETE等）
     */
    @TableField("http_method")
    private String httpMethod;

    /**
     * API描述
     */
    @TableField("api_description")
    private String apiDescription;

    /**
     * API版本
     */
    @TableField("api_version")
    private String apiVersion;

    /**
     * API状态（1：开发中，2：测试中，3：已发布，4：已废弃）
     */
    @TableField("api_status")
    private Integer apiStatus;

    /**
     * 所属模块
     */
    @TableField("module_name")
    private String moduleName;

    /**
     * 所属分类
     */
    @TableField("category_name")
    private String categoryName;

    /**
     * 请求参数（JSON格式）
     */
    @TableField("request_params")
    private String requestParams;

    /**
     * 响应参数（JSON格式）
     */
    @TableField("response_params")
    private String responseParams;

    /**
     * 请求示例
     */
    @TableField("request_example")
    private String requestExample;

    /**
     * 响应示例
     */
    @TableField("response_example")
    private String responseExample;

    /**
     * 错误码说明（JSON格式）
     */
    @TableField("error_codes")
    private String errorCodes;

    /**
     * 接口文档
     */
    @TableField("api_documentation")
    private String apiDocumentation;

    /**
     * 接口标签
     */
    @TableField("api_tags")
    private String apiTags;

    /**
     * 是否需要认证（0：否，1：是）
     */
    @TableField("need_auth")
    private Integer needAuth;

    /**
     * 认证类型（1：JWT Token，2：API Key，3：OAuth2，4：无）
     */
    @TableField("auth_type")
    private Integer authType;

    /**
     * 权限标识
     */
    @TableField("permission")
    private String permission;

    /**
     * 请求频率限制（次/分钟）
     */
    @TableField("rate_limit")
    private Integer rateLimit;

    /**
     * 超时时间（秒）
     */
    @TableField("timeout")
    private Integer timeout;

    /**
     * 是否启用（0：禁用，1：启用）
     */
    @TableField("is_enabled")
    private Integer isEnabled;

    /**
     * 是否公开（0：私有，1：公开）
     */
    @TableField("is_public")
    private Integer isPublic;

    /**
     * 创建者
     */
    @TableField("creator")
    private String creator;

    /**
     * 维护者
     */
    @TableField("maintainer")
    private String maintainer;

    /**
     * 最后修改时间
     */
    @TableField("last_modified_time")
    private LocalDateTime lastModifiedTime;

    /**
     * 最后测试时间
     */
    @TableField("last_test_time")
    private LocalDateTime lastTestTime;

    /**
     * 测试状态（1：未测试，2：测试通过，3：测试失败）
     */
    @TableField("test_status")
    private Integer testStatus;

    /**
     * 测试结果
     */
    @TableField("test_result")
    private String testResult;

    /**
     * 调用次数
     */
    @TableField("call_count")
    private Long callCount;

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
     * 平均响应时间（毫秒）
     */
    @TableField("avg_response_time")
    private Long avgResponseTime;

    /**
     * 最长响应时间（毫秒）
     */
    @TableField("max_response_time")
    private Long maxResponseTime;

    /**
     * 最短响应时间（毫秒）
     */
    @TableField("min_response_time")
    private Long minResponseTime;

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