package com.shanhaifangzhou.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanhaifangzhou.admin.entity.SysUser;
import com.shanhaifangzhou.admin.mapper.SysUserMapper;
import com.shanhaifangzhou.admin.service.SysUserService;
import com.shanhaifangzhou.base.common.result.ResultCode;
import com.shanhaifangzhou.base.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 系统用户服务实现类
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Override
    public SysUser getByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername, username);
        return getOne(wrapper);
    }

    @Override
    public Page<SysUser> getUserPage(Page<SysUser> page, SysUser user) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        
        // 添加查询条件
        if (user != null) {
            if (StrUtil.isNotBlank(user.getUsername())) {
                wrapper.like(SysUser::getUsername, user.getUsername());
            }
            if (StrUtil.isNotBlank(user.getNickname())) {
                wrapper.like(SysUser::getNickname, user.getNickname());
            }
            if (StrUtil.isNotBlank(user.getPhone())) {
                wrapper.like(SysUser::getPhone, user.getPhone());
            }
            if (StrUtil.isNotBlank(user.getEmail())) {
                wrapper.like(SysUser::getEmail, user.getEmail());
            }
            if (user.getStatus() != null) {
                wrapper.eq(SysUser::getStatus, user.getStatus());
            }
            if (user.getDeptId() != null) {
                wrapper.eq(SysUser::getDeptId, user.getDeptId());
            }
        }
        
        wrapper.orderByDesc(SysUser::getCreateTime);
        
        return page(page, wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(SysUser user) {
        // 检查用户名是否已存在
        SysUser existUser = getByUsername(user.getUsername());
        if (existUser != null) {
            throw new BusinessException(ResultCode.USER_ALREADY_EXISTS);
        }
        
        // 加密密码
        user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        
        // 设置默认值
        if (user.getStatus() == null) {
            user.setStatus(0);
        }
        if (user.getUserType() == null) {
            user.setUserType(0);
        }
        
        save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUser user) {
        // 检查用户是否存在
        SysUser existUser = getById(user.getId());
        if (existUser == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        // 如果修改了用户名，检查是否重复
        if (!existUser.getUsername().equals(user.getUsername())) {
            SysUser duplicateUser = getByUsername(user.getUsername());
            if (duplicateUser != null) {
                throw new BusinessException(ResultCode.USER_ALREADY_EXISTS);
            }
        }
        
        // 如果修改了密码，需要加密
        if (StrUtil.isNotBlank(user.getPassword()) && !user.getPassword().equals(existUser.getPassword())) {
            user.setPassword(BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));
        } else {
            user.setPassword(null); // 不更新密码
        }
        
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        // 检查用户是否存在
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        // 删除用户
        removeById(userId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void resetPassword(Long userId, String newPassword) {
        // 检查用户是否存在
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        // 加密新密码
        String encryptedPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());
        
        // 更新密码
        user.setPassword(encryptedPassword);
        updateById(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignRoles(Long userId, List<Long> roleIds) {
        // 检查用户是否存在
        SysUser user = getById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        
        // 这里应该调用角色分配服务，暂时简化处理
        // TODO: 实现角色分配逻辑
    }

    @Override
    public List<Long> getUserRoleIds(Long userId) {
        // TODO: 实现获取用户角色ID列表
        return null;
    }

    @Override
    public List<SysUser> getUsersByDeptId(Long deptId) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getDeptId, deptId);
        wrapper.eq(SysUser::getStatus, 0);
        return list(wrapper);
    }
}