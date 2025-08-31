package com.shanhaifangzhou.admin.user.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanhaifangzhou.admin.user.entity.SysUser;
import com.shanhaifangzhou.admin.user.dto.UserQueryDTO;
import com.shanhaifangzhou.admin.user.dto.UserCreateDTO;
import com.shanhaifangzhou.admin.user.dto.UserUpdateDTO;
import com.shanhaifangzhou.admin.user.mapper.SysUserMapper;
import com.shanhaifangzhou.admin.user.service.SysUserService;
import com.shanhaifangzhou.base.common.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * 系统用户服务实现类
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public IPage<SysUser> getUserPage(UserQueryDTO queryDTO) {
        Page<SysUser> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        return baseMapper.selectUserPage(page, queryDTO);
    }

    @Override
    public SysUser getUserByUsername(String username) {
        if (StrUtil.isBlank(username)) {
            return null;
        }
        return baseMapper.selectByUsername(username);
    }

    @Override
    public SysUser getUserByEmail(String email) {
        if (StrUtil.isBlank(email)) {
            return null;
        }
        return baseMapper.selectByEmail(email);
    }

    @Override
    public SysUser getUserByPhone(String phone) {
        if (StrUtil.isBlank(phone)) {
            return null;
        }
        return baseMapper.selectByPhone(phone);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createUser(UserCreateDTO createDTO) {
        // 验证密码
        if (!createDTO.getPassword().equals(createDTO.getConfirmPassword())) {
            throw new RuntimeException("两次输入的密码不一致");
        }

        // 检查用户名唯一性
        if (!checkUsernameUnique(createDTO.getUsername(), null)) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱唯一性
        if (StrUtil.isNotBlank(createDTO.getEmail()) && !checkEmailUnique(createDTO.getEmail(), null)) {
            throw new RuntimeException("邮箱已存在");
        }

        // 检查手机号唯一性
        if (StrUtil.isNotBlank(createDTO.getPhone()) && !checkPhoneUnique(createDTO.getPhone(), null)) {
            throw new RuntimeException("手机号已存在");
        }

        // 创建用户实体
        SysUser user = new SysUser();
        BeanUtil.copyProperties(createDTO, user);
        
        // 加密密码
        user.setPassword(BCrypt.hashpw(createDTO.getPassword(), BCrypt.gensalt()));
        
        // 设置默认值
        user.setStatus(1);
        user.setUserType(createDTO.getUserType() != null ? createDTO.getUserType() : 0);
        user.setLoginType(0);
        user.setIsFirstLogin(1);
        user.setPasswordErrorCount(0);
        user.setOnlineStatus(0);
        user.setUserSource(0);

        // 保存用户
        boolean success = save(user);
        if (success && createDTO.getRoleIds() != null && !createDTO.getRoleIds().isEmpty()) {
            // 分配角色
            assignUserRoles(user.getId(), createDTO.getRoleIds());
        }
        
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateUser(UserUpdateDTO updateDTO) {
        // 检查用户是否存在
        SysUser existingUser = getById(updateDTO.getId());
        if (existingUser == null) {
            throw new RuntimeException("用户不存在");
        }

        // 检查用户名唯一性
        if (!checkUsernameUnique(updateDTO.getNickname(), updateDTO.getId())) {
            throw new RuntimeException("用户名已存在");
        }

        // 检查邮箱唯一性
        if (StrUtil.isNotBlank(updateDTO.getEmail()) && !checkEmailUnique(updateDTO.getEmail(), updateDTO.getId())) {
            throw new RuntimeException("邮箱已存在");
        }

        // 检查手机号唯一性
        if (StrUtil.isNotBlank(updateDTO.getPhone()) && !checkPhoneUnique(updateDTO.getPhone(), updateDTO.getId())) {
            throw new RuntimeException("手机号已存在");
        }

        // 更新用户信息
        SysUser user = new SysUser();
        BeanUtil.copyProperties(updateDTO, user);
        user.setUpdateTime(LocalDateTime.now());

        // 更新用户
        boolean success = updateById(user);
        if (success && updateDTO.getRoleIds() != null && !updateDTO.getRoleIds().isEmpty()) {
            // 重新分配角色
            assignUserRoles(updateDTO.getId(), updateDTO.getRoleIds());
        }
        
        return success;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteUser(Long id) {
        if (id == null) {
            return false;
        }
        
        // 检查是否为超级管理员
        SysUser user = getById(id);
        if (user != null && "admin".equals(user.getUsername())) {
            throw new RuntimeException("不能删除超级管理员");
        }
        
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteUsers(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        // 检查是否包含超级管理员
        List<SysUser> users = listByIds(ids);
        for (SysUser user : users) {
            if ("admin".equals(user.getUsername())) {
                throw new RuntimeException("不能删除超级管理员");
            }
        }
        
        return removeByIds(ids);
    }

    @Override
    public boolean resetPassword(Long id, String newPassword) {
        if (id == null || StrUtil.isBlank(newPassword)) {
            return false;
        }
        
        SysUser user = new SysUser();
        user.setId(id);
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        user.setUpdateTime(LocalDateTime.now());
        user.setPasswordErrorCount(0);
        user.setPasswordErrorTime(null);
        user.setLockTime(null);
        user.setLockReason(null);
        
        return updateById(user);
    }

    @Override
    public boolean changePassword(Long id, String oldPassword, String newPassword) {
        if (id == null || StrUtil.isBlank(oldPassword) || StrUtil.isBlank(newPassword)) {
            return false;
        }
        
        SysUser user = getById(id);
        if (user == null) {
            return false;
        }
        
        // 验证旧密码
        if (!BCrypt.checkpw(oldPassword, user.getPassword())) {
            throw new RuntimeException("原密码不正确");
        }
        
        // 更新密码
        user.setPassword(BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        user.setUpdateTime(LocalDateTime.now());
        user.setPasswordErrorCount(0);
        user.setPasswordErrorTime(null);
        
        return updateById(user);
    }

    @Override
    public boolean updateUserStatus(Long id, Integer status) {
        if (id == null || status == null) {
            return false;
        }
        
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(status);
        user.setUpdateTime(LocalDateTime.now());
        
        return updateById(user);
    }

    @Override
    public boolean lockUser(Long id, String reason) {
        if (id == null) {
            return false;
        }
        
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(0);
        user.setLockTime(LocalDateTime.now());
        user.setLockReason(reason);
        user.setUpdateTime(LocalDateTime.now());
        
        return updateById(user);
    }

    @Override
    public boolean unlockUser(Long id) {
        if (id == null) {
            return false;
        }
        
        SysUser user = new SysUser();
        user.setId(id);
        user.setStatus(1);
        user.setLockTime(null);
        user.setLockReason(null);
        user.setUpdateTime(LocalDateTime.now());
        
        return updateById(user);
    }

    @Override
    public boolean forceLogout(Long id) {
        if (id == null) {
            return false;
        }
        
        SysUser user = new SysUser();
        user.setId(id);
        user.setOnlineStatus(0);
        user.setUpdateTime(LocalDateTime.now());
        
        return updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean assignUserRoles(Long userId, List<Long> roleIds) {
        if (userId == null || roleIds == null) {
            return false;
        }
        
        // TODO: 实现角色分配逻辑，需要调用用户角色关联表的Mapper
        // 这里先返回true，后续完善
        return true;
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        if (userId == null) {
            return null;
        }
        
        // TODO: 实现获取用户角色ID列表逻辑
        return null;
    }

    @Override
    public List<SysUser> getUsersByDeptId(Long deptId) {
        if (deptId == null) {
            return null;
        }
        return baseMapper.selectByDeptId(deptId);
    }

    @Override
    public List<SysUser> getUsersByRoleId(Long roleId) {
        if (roleId == null) {
            return null;
        }
        return baseMapper.selectByRoleId(roleId);
    }

    @Override
    public List<SysUser> getOnlineUsers() {
        return baseMapper.selectOnlineUsers();
    }

    @Override
    public boolean updateLastLoginInfo(Long id, String lastLoginTime, String lastLoginIp) {
        if (id == null) {
            return false;
        }
        
        String time = lastLoginTime;
        if (time == null) {
            time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }
        
        return baseMapper.updateLastLoginInfo(id, time, lastLoginIp) > 0;
    }

    @Override
    public boolean updateOnlineStatus(Long id, Integer onlineStatus) {
        if (id == null || onlineStatus == null) {
            return false;
        }
        return baseMapper.updateOnlineStatus(id, onlineStatus) > 0;
    }

    @Override
    public boolean updateLastActiveTime(Long id) {
        if (id == null) {
            return false;
        }
        return baseMapper.updateLastActiveTime(id) > 0;
    }

    @Override
    public boolean checkUsernameUnique(String username, Long excludeId) {
        if (StrUtil.isBlank(username)) {
            return false;
        }
        
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        if (excludeId != null) {
            wrapper.ne(SysUser::getId, excludeId);
        }
        
        return count(wrapper) == 0;
    }

    @Override
    public boolean checkEmailUnique(String email, Long excludeId) {
        if (StrUtil.isBlank(email)) {
            return true;
        }
        
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getEmail, email);
        if (excludeId != null) {
            wrapper.ne(SysUser::getId, excludeId);
        }
        
        return count(wrapper) == 0;
    }

    @Override
    public boolean checkPhoneUnique(String phone, Long excludeId) {
        if (StrUtil.isBlank(phone)) {
            return true;
        }
        
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getPhone, phone);
        if (excludeId != null) {
            wrapper.ne(SysUser::getId, excludeId);
        }
        
        return count(wrapper) == 0;
    }

    @Override
    public Map<String, Object> getUserStats() {
        List<Object> stats = baseMapper.selectUserStats();
        if (stats != null && !stats.isEmpty()) {
            return (Map<String, Object>) stats.get(0);
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> getUserLoginStats(Integer days) {
        if (days == null) {
            days = 7;
        }
        return baseMapper.selectUserLoginStats(days);
    }

    @Override
    public Map<String, Object> importUsers(List<Map<String, Object>> userDataList) {
        // TODO: 实现用户数据导入逻辑
        Map<String, Object> result = new java.util.HashMap<>();
        result.put("success", 0);
        result.put("failure", 0);
        result.put("message", "导入功能待实现");
        return result;
    }

    @Override
    public List<SysUser> exportUsers(UserQueryDTO queryDTO) {
        // TODO: 实现用户数据导出逻辑
        return null;
    }

    @Override
    public List<Map<String, Object>> getUserTemplate() {
        // TODO: 实现用户模板数据逻辑
        return null;
    }
}