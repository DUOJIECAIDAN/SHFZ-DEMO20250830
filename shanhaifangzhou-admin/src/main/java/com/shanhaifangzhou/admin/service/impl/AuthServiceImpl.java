package com.shanhaifangzhou.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.shanhaifangzhou.admin.dto.LoginRequest;
import com.shanhaifangzhou.admin.dto.LoginResponse;
import com.shanhaifangzhou.admin.service.AuthService;
import com.shanhaifangzhou.base.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证服务实现类
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 简化的登录逻辑，实际应该查询数据库
        if ("admin".equals(loginRequest.getUsername()) && "123456".equals(loginRequest.getPassword())) {
            // 登录成功
            StpUtil.login(1L);
            
            LoginResponse response = new LoginResponse();
            response.setToken(StpUtil.getTokenValue());
            
            // 设置用户信息
            LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
            userInfo.setId(1L);
            userInfo.setUsername("admin");
            userInfo.setNickname("系统管理员");
            userInfo.setEmail("admin@shanhaifangzhou.com");
            userInfo.setAvatar("https://example.com/avatar.jpg");
            userInfo.setRoles(java.util.Arrays.asList("admin", "user"));
            response.setUserInfo(userInfo);
            
            // 设置权限列表
            response.setPermissions(java.util.Arrays.asList("system:*", "user:*", "role:*"));
            
            return response;
        } else {
            throw new BusinessException("用户名或密码错误");
        }
    }

    @Override
    public Map<String, Object> getUserInfo() {
        // 简化的用户信息，实际应该从数据库查询
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", 1L);
        userInfo.put("username", "admin");
        userInfo.put("nickname", "系统管理员");
        userInfo.put("email", "admin@shanhaifangzhou.com");
        userInfo.put("avatar", "https://example.com/avatar.jpg");
        userInfo.put("roles", new String[]{"admin", "user"});
        userInfo.put("permissions", new String[]{"system:*", "user:*", "role:*"});
        return userInfo;
    }

    @Override
    public Map<String, Object> generateCaptcha() {
        // 简化的验证码生成
        Map<String, Object> captcha = new HashMap<>();
        captcha.put("captchaId", "test-captcha-id");
        captcha.put("captchaImage", "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg==");
        return captcha;
    }

    @Override
    public boolean validateCaptcha(String captchaId, String captchaCode) {
        // 简化的验证码验证
        return "test-captcha-id".equals(captchaId) && "1234".equals(captchaCode);
    }
}