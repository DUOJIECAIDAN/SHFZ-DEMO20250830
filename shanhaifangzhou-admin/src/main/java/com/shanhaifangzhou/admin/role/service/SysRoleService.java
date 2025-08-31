package com.shanhaifangzhou.admin.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanhaifangzhou.admin.role.entity.SysRole;
import com.shanhaifangzhou.admin.role.dto.RoleQueryDTO;
import com.shanhaifangzhou.admin.role.dto.RoleCreateDTO;
import com.shanhaifangzhou.admin.role.dto.RoleUpdateDTO;

import java.util.List;
import java.util.Map;

/**
 * 系统角色服务接口
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色列表
     */
    IPage<SysRole> getRolePage(RoleQueryDTO queryDTO);

    /**
     * 根据角色编码查询角色
     */
    SysRole getRoleByRoleCode(String roleCode);

    /**
     * 根据角色名称查询角色
     */
    SysRole getRoleByRoleName(String roleName);

    /**
     * 创建角色
     */
    boolean createRole(RoleCreateDTO createDTO);

    /**
     * 更新角色
     */
    boolean updateRole(RoleUpdateDTO updateDTO);

    /**
     * 删除角色
     */
    boolean deleteRole(Long id);

    /**
     * 批量删除角色
     */
    boolean batchDeleteRoles(List<Long> ids);

    /**
     * 更新角色状态
     */
    boolean updateRoleStatus(Long id, Integer status);

    /**
     * 更新角色排序
     */
    boolean updateRoleSort(Long id, Integer sort);

    /**
     * 分配角色菜单权限
     */
    boolean assignRoleMenus(Long roleId, List<Long> menuIds);

    /**
     * 分配角色数据权限
     */
    boolean assignRoleDepts(Long roleId, List<Long> deptIds);

    /**
     * 获取角色菜单ID列表
     */
    List<Long> getRoleMenuIds(Long roleId);

    /**
     * 获取角色部门ID列表
     */
    List<Long> getRoleDeptIds(Long roleId);

    /**
     * 根据用户ID查询角色列表
     */
    List<SysRole> getRolesByUserId(Long userId);

    /**
     * 根据菜单ID查询角色列表
     */
    List<SysRole> getRolesByMenuId(Long menuId);

    /**
     * 根据部门ID查询角色列表
     */
    List<SysRole> getRolesByDeptId(Long deptId);

    /**
     * 获取启用的角色列表
     */
    List<SysRole> getEnabledRoles();

    /**
     * 获取系统内置角色列表
     */
    List<SysRole> getSystemRoles();

    /**
     * 获取业务角色列表
     */
    List<SysRole> getBusinessRoles();

    /**
     * 检查角色编码是否唯一
     */
    boolean checkRoleCodeUnique(String roleCode, Long excludeId);

    /**
     * 检查角色名称是否唯一
     */
    boolean checkRoleNameUnique(String roleName, Long excludeId);

    /**
     * 获取角色统计信息
     */
    Map<String, Object> getRoleStats();

    /**
     * 获取角色用户统计
     */
    List<Map<String, Object>> getRoleUserStats();

    /**
     * 导入角色数据
     */
    Map<String, Object> importRoles(List<Map<String, Object>> roleDataList);

    /**
     * 导出角色数据
     */
    List<SysRole> exportRoles(RoleQueryDTO queryDTO);

    /**
     * 获取角色模板数据
     */
    List<Map<String, Object>> getRoleTemplate();

    /**
     * 复制角色
     */
    boolean copyRole(Long sourceRoleId, String newRoleName, String newRoleCode);
}