package com.shanhaifangzhou.admin.service;

import com.shanhaifangzhou.admin.dto.LoginRequest;
import com.shanhaifangzhou.admin.dto.LoginResponse;

import java.util.Map;

/**
 * 认证服务接口
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
public interface AuthService {

    /**
     * 用户登录
     */
    LoginResponse login(LoginRequest loginRequest);

    /**
     * 获取用户信息
     */
    Map<String, Object> getUserInfo();

    /**
     * 生成验证码
     */
    Map<String, Object> generateCaptcha();

    /**
     * 验证验证码
     */
    boolean validateCaptcha(String captchaKey, String captcha);
}