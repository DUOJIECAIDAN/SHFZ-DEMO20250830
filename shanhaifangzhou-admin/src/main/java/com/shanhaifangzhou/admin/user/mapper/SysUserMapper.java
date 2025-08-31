package com.shanhaifangzhou.admin.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanhaifangzhou.admin.user.entity.SysUser;
import com.shanhaifangzhou.admin.user.dto.UserQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户Mapper接口
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * 分页查询用户列表
     */
    IPage<SysUser> selectUserPage(Page<SysUser> page, @Param("query") UserQueryDTO query);

    /**
     * 根据用户名查询用户
     */
    SysUser selectByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     */
    SysUser selectByEmail(@Param("email") String email);

    /**
     * 根据手机号查询用户
     */
    SysUser selectByPhone(@Param("phone") String phone);

    /**
     * 根据部门ID查询用户列表
     */
    List<SysUser> selectByDeptId(@Param("deptId") Long deptId);

    /**
     * 根据角色ID查询用户列表
     */
    List<SysUser> selectByRoleId(@Param("roleId") Long roleId);

    /**
     * 查询在线用户列表
     */
    List<SysUser> selectOnlineUsers();

    /**
     * 更新用户最后登录信息
     */
    int updateLastLoginInfo(@Param("id") Long id, @Param("lastLoginTime") String lastLoginTime, @Param("lastLoginIp") String lastLoginIp);

    /**
     * 更新用户在线状态
     */
    int updateOnlineStatus(@Param("id") Long id, @Param("onlineStatus") Integer onlineStatus);

    /**
     * 更新用户最后活跃时间
     */
    int updateLastActiveTime(@Param("id") Long id);

    /**
     * 更新用户密码错误信息
     */
    int updatePasswordErrorInfo(@Param("id") Long id, @Param("passwordErrorCount") Integer passwordErrorCount, @Param("passwordErrorTime") String passwordErrorTime);

    /**
     * 锁定用户账号
     */
    int lockUser(@Param("id") Long id, @Param("lockTime") String lockTime, @Param("lockReason") String lockReason);

    /**
     * 解锁用户账号
     */
    int unlockUser(@Param("id") Long id);

    /**
     * 重置用户密码错误信息
     */
    int resetPasswordErrorInfo(@Param("id") Long id);

    /**
     * 根据条件查询用户数量
     */
    long selectUserCount(@Param("query") UserQueryDTO query);

    /**
     * 查询用户统计信息
     */
    List<Object> selectUserStats();

    /**
     * 查询用户登录统计
     */
    List<Object> selectUserLoginStats(@Param("days") Integer days);
}