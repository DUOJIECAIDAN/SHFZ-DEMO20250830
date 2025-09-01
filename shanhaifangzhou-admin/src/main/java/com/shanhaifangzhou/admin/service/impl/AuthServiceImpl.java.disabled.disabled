package com.shanhaifangzhou.admin.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.shanhaifangzhou.admin.dto.LoginRequest;
import com.shanhaifangzhou.admin.dto.LoginResponse;
import com.shanhaifangzhou.admin.entity.SysUser;
import com.shanhaifangzhou.admin.service.AuthService;
import com.shanhaifangzhou.admin.service.SysMenuService;
import com.shanhaifangzhou.admin.service.SysUserService;
import com.shanhaifangzhou.base.common.result.ResultCode;
import com.shanhaifangzhou.base.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 认证服务实现类
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserService sysUserService;
    private final SysMenuService sysMenuService;
    private final StringRedisTemplate stringRedisTemplate;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        // 验证验证码
        if (StrUtil.isNotBlank(loginRequest.getCaptcha())) {
            if (!validateCaptcha(loginRequest.getCaptchaKey(), loginRequest.getCaptcha())) {
                throw new BusinessException(ResultCode.CAPTCHA_ERROR);
            }
        }

        // 根据用户名查询用户
        SysUser user = sysUserService.getByUsername(loginRequest.getUsername());
        if (user == null) {
            throw new BusinessException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }

        // 验证密码
        if (!BCrypt.checkpw(loginRequest.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.USERNAME_OR_PASSWORD_ERROR);
        }

        // 检查用户状态
        if (user.getStatus() != 0) {
            throw new BusinessException(ResultCode.USER_DISABLED);
        }

        // 执行登录
        StpUtil.login(user.getId());
        
        // 设置用户额外信息
        StpUtil.getSession().set("username", user.getUsername());
        StpUtil.getSession().set("nickname", user.getNickname());
        StpUtil.getSession().set("email", user.getEmail());
        StpUtil.getSession().set("phone", user.getPhone());
        StpUtil.getSession().set("avatar", user.getAvatar());
        StpUtil.getSession().set("deptId", user.getDeptId());
        StpUtil.getSession().set("tenantId", user.getTenantId());

        // 构建响应
        LoginResponse response = new LoginResponse();
        response.setToken(StpUtil.getTokenValue());

        // 用户信息
        LoginResponse.UserInfo userInfo = new LoginResponse.UserInfo();
        userInfo.setId(user.getId());
        userInfo.setUsername(user.getUsername());
        userInfo.setNickname(user.getNickname());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhone(user.getPhone());
        userInfo.setAvatar(user.getAvatar());
        userInfo.setSex(user.getSex());
        userInfo.setDeptId(user.getDeptId());
        response.setUserInfo(userInfo);

        // 菜单信息
        List<LoginResponse.MenuInfo> menus = sysMenuService.getUserMenus(user.getId());
        response.setMenus(menus);

        // 权限信息
        List<String> permissions = StpUtil.getPermissionList();
        response.setPermissions(permissions);

        return response;
    }

    @Override
    public Map<String, Object> getUserInfo() {
        if (!StpUtil.isLogin()) {
            throw new BusinessException(ResultCode.UNAUTHORIZED);
        }

        Long userId = StpUtil.getLoginIdAsLong();
        SysUser user = sysUserService.getById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("id", user.getId());
        userInfo.put("username", user.getUsername());
        userInfo.put("nickname", user.getNickname());
        userInfo.put("email", user.getEmail());
        userInfo.put("phone", user.getPhone());
        userInfo.put("avatar", user.getAvatar());
        userInfo.put("sex", user.getSex());
        userInfo.put("deptId", user.getDeptId());
        userInfo.put("roles", StpUtil.getRoleList());
        userInfo.put("permissions", StpUtil.getPermissionList());

        return userInfo;
    }

    @Override
    public Map<String, Object> generateCaptcha() {
        // 生成验证码标识
        String captchaKey = IdUtil.fastSimpleUUID();
        
        // 生成验证码（这里简化处理，实际可以使用验证码生成库）
        String captcha = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
        
        // 存储到Redis，5分钟过期
        stringRedisTemplate.opsForValue().set("captcha:" + captchaKey, captcha, 5, TimeUnit.MINUTES);
        
        Map<String, Object> result = new HashMap<>();
        result.put("captchaKey", captchaKey);
        result.put("captcha", captcha); // 实际项目中应该返回图片，这里简化处理
        
        return result;
    }

    @Override
    public boolean validateCaptcha(String captchaKey, String captcha) {
        if (StrUtil.isBlank(captchaKey) || StrUtil.isBlank(captcha)) {
            return false;
        }
        
        String key = "captcha:" + captchaKey;
        String storedCaptcha = stringRedisTemplate.opsForValue().get(key);
        
        if (StrUtil.isBlank(storedCaptcha)) {
            return false;
        }
        
        // 验证成功后删除验证码
        stringRedisTemplate.delete(key);
        
        return captcha.equalsIgnoreCase(storedCaptcha);
    }
}