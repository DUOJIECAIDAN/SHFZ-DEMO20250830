package com.shanhaifangzhou.admin.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.shanhaifangzhou.admin.dto.LoginRequest;
import com.shanhaifangzhou.admin.dto.LoginResponse;
import com.shanhaifangzhou.admin.service.AuthService;
import com.shanhaifangzhou.base.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Tag(name = "认证管理", description = "认证相关接口")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @Operation(summary = "用户登录", description = "用户登录接口")
    @PostMapping("/login")
    public Result<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        LoginResponse response = authService.login(loginRequest);
        return Result.success(response);
    }

    /**
     * 用户登出
     */
    @Operation(summary = "用户登出", description = "用户登出接口")
    @PostMapping("/logout")
    public Result<Void> logout() {
        StpUtil.logout();
        return Result.success();
    }

    /**
     * 获取当前用户信息
     */
    @Operation(summary = "获取用户信息", description = "获取当前登录用户信息")
    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo() {
        Map<String, Object> userInfo = authService.getUserInfo();
        return Result.success(userInfo);
    }

    /**
     * 获取验证码
     */
    @Operation(summary = "获取验证码", description = "获取登录验证码")
    @GetMapping("/captcha")
    public Result<Map<String, Object>> getCaptcha() {
        Map<String, Object> captcha = authService.generateCaptcha();
        return Result.success(captcha);
    }

    /**
     * 刷新Token
     */
    @Operation(summary = "刷新Token", description = "刷新用户Token")
    @PostMapping("/refresh")
    public Result<Map<String, Object>> refreshToken() {
        String newToken = StpUtil.getTokenValue();
        Map<String, Object> result = new HashMap<>();
        result.put("token", newToken);
        return Result.success(result);
    }

    /**
     * 检查登录状态
     */
    @Operation(summary = "检查登录状态", description = "检查当前用户是否已登录")
    @GetMapping("/check")
    public Result<Map<String, Object>> checkLogin() {
        Map<String, Object> result = new HashMap<>();
        result.put("isLogin", StpUtil.isLogin());
        if (StpUtil.isLogin()) {
            result.put("loginId", StpUtil.getLoginId());
        }
        return Result.success(result);
    }
}