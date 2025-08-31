package com.shanhaifangzhou.admin.role.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanhaifangzhou.admin.role.entity.SysRole;
import com.shanhaifangzhou.admin.role.dto.RoleQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统角色Mapper接口
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole> {

    /**
     * 分页查询角色列表
     */
    IPage<SysRole> selectRolePage(Page<SysRole> page, @Param("query") RoleQueryDTO query);

    /**
     * 根据角色编码查询角色
     */
    SysRole selectByRoleCode(@Param("roleCode") String roleCode);

    /**
     * 根据角色名称查询角色
     */
    SysRole selectByRoleName(@Param("roleName") String roleName);

    /**
     * 根据用户ID查询角色列表
     */
    List<SysRole> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据菜单ID查询角色列表
     */
    List<SysRole> selectByMenuId(@Param("menuId") Long menuId);

    /**
     * 根据部门ID查询角色列表
     */
    List<SysRole> selectByDeptId(@Param("deptId") Long deptId);

    /**
     * 查询启用的角色列表
     */
    List<SysRole> selectEnabledRoles();

    /**
     * 查询系统内置角色列表
     */
    List<SysRole> selectSystemRoles();

    /**
     * 查询业务角色列表
     */
    List<SysRole> selectBusinessRoles();

    /**
     * 根据条件查询角色数量
     */
    long selectRoleCount(@Param("query") RoleQueryDTO query);

    /**
     * 查询角色统计信息
     */
    List<Object> selectRoleStats();

    /**
     * 查询角色用户统计
     */
    List<Object> selectRoleUserStats();

    /**
     * 更新角色状态
     */
    int updateRoleStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新角色排序
     */
    int updateRoleSort(@Param("id") Long id, @Param("sort") Integer sort);

    /**
     * 检查角色编码是否唯一
     */
    int checkRoleCodeUnique(@Param("roleCode") String roleCode, @Param("excludeId") Long excludeId);

    /**
     * 检查角色名称是否唯一
     */
    int checkRoleNameUnique(@Param("roleName") String roleName, @Param("excludeId") Long excludeId);
}