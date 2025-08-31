package com.shanhaifangzhou.base.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 响应码枚举
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Getter
@AllArgsConstructor
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
     * 请求方法不允许
     */
    METHOD_NOT_ALLOWED(405, "请求方法不允许"),

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
    BUSINESS_ERROR(1001, "业务异常"),

    /**
     * 数据验证失败
     */
    VALIDATION_ERROR(1002, "数据验证失败"),

    /**
     * 文件上传失败
     */
    FILE_UPLOAD_ERROR(1003, "文件上传失败"),

    /**
     * 文件下载失败
     */
    FILE_DOWNLOAD_ERROR(1004, "文件下载失败"),

    /**
     * 数据库操作失败
     */
    DATABASE_ERROR(1005, "数据库操作失败"),

    /**
     * 缓存操作失败
     */
    CACHE_ERROR(1006, "缓存操作失败"),

    /**
     * 网络请求失败
     */
    NETWORK_ERROR(1007, "网络请求失败"),

    /**
     * 用户不存在
     */
    USER_NOT_FOUND(2001, "用户不存在"),

    /**
     * 用户名或密码错误
     */
    USERNAME_OR_PASSWORD_ERROR(2002, "用户名或密码错误"),

    /**
     * 用户已被禁用
     */
    USER_DISABLED(2003, "用户已被禁用"),

    /**
     * 用户已过期
     */
    USER_EXPIRED(2004, "用户已过期"),

    /**
     * 用户已锁定
     */
    USER_LOCKED(2005, "用户已锁定"),

    /**
     * 角色不存在
     */
    ROLE_NOT_FOUND(3001, "角色不存在"),

    /**
     * 权限不足
     */
    INSUFFICIENT_PERMISSIONS(3002, "权限不足"),

    /**
     * 菜单不存在
     */
    MENU_NOT_FOUND(4001, "菜单不存在"),

    /**
     * 部门不存在
     */
    DEPT_NOT_FOUND(5001, "部门不存在"),

    /**
     * 字典类型不存在
     */
    DICT_TYPE_NOT_FOUND(6001, "字典类型不存在"),

    /**
     * 字典数据不存在
     */
    DICT_DATA_NOT_FOUND(6002, "字典数据不存在");

    /**
     * 响应码
     */
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;
}