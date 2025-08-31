package com.shanhaifangzhou.admin.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanhaifangzhou.admin.user.entity.SysUser;
import com.shanhaifangzhou.admin.user.dto.UserQueryDTO;
import com.shanhaifangzhou.admin.user.dto.UserCreateDTO;
import com.shanhaifangzhou.admin.user.dto.UserUpdateDTO;

import java.util.List;
import java.util.Map;

/**
 * 系统用户服务接口
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户列表
     */
    IPage<SysUser> getUserPage(UserQueryDTO queryDTO);

    /**
     * 根据用户名查询用户
     */
    SysUser getUserByUsername(String username);

    /**
     * 根据邮箱查询用户
     */
    SysUser getUserByEmail(String email);

    /**
     * 根据手机号查询用户
     */
    SysUser getUserByPhone(String phone);

    /**
     * 创建用户
     */
    boolean createUser(UserCreateDTO createDTO);

    /**
     * 更新用户
     */
    boolean updateUser(UserUpdateDTO updateDTO);

    /**
     * 删除用户
     */
    boolean deleteUser(Long id);

    /**
     * 批量删除用户
     */
    boolean batchDeleteUsers(List<Long> ids);

    /**
     * 重置用户密码
     */
    boolean resetPassword(Long id, String newPassword);

    /**
     * 修改用户密码
     */
    boolean changePassword(Long id, String oldPassword, String newPassword);

    /**
     * 更新用户状态
     */
    boolean updateUserStatus(Long id, Integer status);

    /**
     * 锁定用户账号
     */
    boolean lockUser(Long id, String reason);

    /**
     * 解锁用户账号
     */
    boolean unlockUser(Long id);

    /**
     * 强制下线用户
     */
    boolean forceLogout(Long id);

    /**
     * 分配用户角色
     */
    boolean assignUserRoles(Long userId, List<Long> roleIds);

    /**
     * 获取用户角色ID列表
     */
    List<Long> getUserRoleIds(Long userId);

    /**
     * 根据部门ID查询用户列表
     */
    List<SysUser> getUsersByDeptId(Long deptId);

    /**
     * 根据角色ID查询用户列表
     */
    List<SysUser> getUsersByRoleId(Long roleId);

    /**
     * 获取在线用户列表
     */
    List<SysUser> getOnlineUsers();

    /**
     * 更新用户最后登录信息
     */
    boolean updateLastLoginInfo(Long id, String lastLoginTime, String lastLoginIp);

    /**
     * 更新用户在线状态
     */
    boolean updateOnlineStatus(Long id, Integer onlineStatus);

    /**
     * 更新用户最后活跃时间
     */
    boolean updateLastActiveTime(Long id);

    /**
     * 检查用户名是否唯一
     */
    boolean checkUsernameUnique(String username, Long excludeId);

    /**
     * 检查邮箱是否唯一
     */
    boolean checkEmailUnique(String email, Long excludeId);

    /**
     * 检查手机号是否唯一
     */
    boolean checkPhoneUnique(String phone, Long excludeId);

    /**
     * 获取用户统计信息
     */
    Map<String, Object> getUserStats();

    /**
     * 获取用户登录统计
     */
    List<Map<String, Object>> getUserLoginStats(Integer days);

    /**
     * 导入用户数据
     */
    Map<String, Object> importUsers(List<Map<String, Object>> userDataList);

    /**
     * 导出用户数据
     */
    List<SysUser> exportUsers(UserQueryDTO queryDTO);

    /**
     * 获取用户模板数据
     */
    List<Map<String, Object>> getUserTemplate();
}