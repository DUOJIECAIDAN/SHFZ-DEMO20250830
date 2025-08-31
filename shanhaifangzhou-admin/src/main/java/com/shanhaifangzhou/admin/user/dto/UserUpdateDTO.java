package com.shanhaifangzhou.admin.user.dto;

import lombok.Data;

import jakarta.validation.constraints.*;
import java.util.List;

/**
 * 用户更新DTO
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
public class UserUpdateDTO {

    /**
     * 用户ID
     */
    @NotNull(message = "用户ID不能为空")
    private Long id;

    /**
     * 昵称
     */
    @NotBlank(message = "昵称不能为空")
    @Size(max = 30, message = "昵称长度不能超过30个字符")
    private String nickname;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
    @Size(max = 50, message = "邮箱长度不能超过50个字符")
    private String email;

    /**
     * 手机号
     */
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别（0：未知，1：男，2：女）
     */
    @Min(value = 0, message = "性别值不正确")
    @Max(value = 2, message = "性别值不正确")
    private Integer gender;

    /**
     * 状态（0：禁用，1：启用）
     */
    @Min(value = 0, message = "状态值不正确")
    @Max(value = 1, message = "状态值不正确")
    private Integer status;

    /**
     * 部门ID
     */
    @NotNull(message = "部门不能为空")
    private Long deptId;

    /**
     * 岗位ID
     */
    private Long postId;

    /**
     * 角色ID列表
     */
    @NotEmpty(message = "角色不能为空")
    private List<Long> roleIds;

    /**
     * 用户类型（0：系统用户，1：业务用户）
     */
    @Min(value = 0, message = "用户类型值不正确")
    @Max(value = 1, message = "用户类型值不正确")
    private Integer userType;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过500个字符")
    private String remark;
}