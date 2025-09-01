package com.shanhaifangzhou.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 登录请求DTO
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Data
@Schema(description = "登录请求")
public class LoginRequest {

    @Schema(description = "用户名", required = true)
    @NotBlank(message = "用户名不能为空")
    private String username;

    @Schema(description = "密码", required = true)
    @NotBlank(message = "密码不能为空")
    private String password;

    @Schema(description = "验证码")
    private String captcha;

    @Schema(description = "验证码标识")
    private String captchaKey;

    @Schema(description = "记住我")
    private Boolean rememberMe = false;
}