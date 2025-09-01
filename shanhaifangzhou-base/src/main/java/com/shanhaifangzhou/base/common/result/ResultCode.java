package com.shanhaifangzhou.base.common.result;

import lombok.Getter;

/**
 * 返回码枚举
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Getter
public enum ResultCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    ERROR(500, "操作失败"),

    /**
     * 参数错误
     */
    PARAM_ERROR(400, "参数错误"),

    /**
     * 未授权
     */
    UNAUTHORIZED(401, "未授权"),

    /**
     * 禁止访问
     */
    FORBIDDEN(403, "禁止访问"),

    /**
     * 资源不存在
     */
    NOT_FOUND(404, "资源不存在"),

    /**
     * 方法不允许
     */
    METHOD_NOT_ALLOWED(405, "方法不允许"),

    /**
     * 请求超时
     */
    REQUEST_TIMEOUT(408, "请求超时"),

    /**
     * 冲突
     */
    CONFLICT(409, "资源冲突"),

    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),

    /**
     * 服务不可用
     */
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    /**
     * 业务异常
     */
    BUSINESS_ERROR(1000, "业务异常"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(1001, "用户不存在"),

    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(1002, "用户名或密码错误"),

    /**
     * 用户已被禁用
     */
    USER_DISABLED(1003, "用户已被禁用"),

    /**
     * 用户已存在
     */
    USER_ALREADY_EXISTS(1004, "用户已存在"),

    /**
     * 角色不存在
     */
    ROLE_NOT_FOUND(1005, "角色不存在"),

    /**
     * 权限不足
     */
    INSUFFICIENT_PERMISSIONS(1006, "权限不足"),

    /**
     * 验证码错误
     */
    CAPTCHA_ERROR(1007, "验证码错误"),

    /**
     * 验证码已过期
     */
    CAPTCHA_EXPIRED(1008, "验证码已过期"),

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_ERROR(1009, "文件上传失败"),

    /**
     * 文件不存在
     */
    FILE_NOT_FOUND(1010, "文件不存在"),

    /**
     * 数据导出失败
     */
    EXPORT_ERROR(1011, "数据导出失败"),

    /**
     * 数据导入失败
     */
    IMPORT_ERROR(1012, "数据导入失败");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 消息
     */
    private final String message;

    /**
     * 构造函数
     */
    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
