package com.shanhaifangzhou.admin.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanhaifangzhou.admin.entity.SysUser;

import java.util.List;

/**
 * 系统用户服务接口
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 根据用户名查询用户
     */
    SysUser getByUsername(String username);

    /**
     * 分页查询用户
     */
    Page<SysUser> getUserPage(Page<SysUser> page, SysUser user);

    /**
     * 创建用户
     */
    void createUser(SysUser user);

    /**
     * 更新用户
     */
    void updateUser(SysUser user);

    /**
     * 删除用户
     */
    void deleteUser(Long userId);

    /**
     * 重置密码
     */
    void resetPassword(Long userId, String newPassword);

    /**
     * 分配角色
     */
    void assignRoles(Long userId, List<Long> roleIds);

    /**
     * 获取用户角色ID列表
     */
    List<Long> getUserRoleIds(Long userId);

    /**
     * 根据部门ID查询用户列表
     */
    List<SysUser> getUsersByDeptId(Long deptId);
}