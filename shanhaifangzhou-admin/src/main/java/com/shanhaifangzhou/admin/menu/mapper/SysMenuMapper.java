package com.shanhaifangzhou.admin.menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanhaifangzhou.admin.menu.entity.SysMenu;
import com.shanhaifangzhou.admin.menu.dto.MenuQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统菜单Mapper接口
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu> {

    /**
     * 分页查询菜单列表
     */
    IPage<SysMenu> selectMenuPage(Page<SysMenu> page, @Param("query") MenuQueryDTO query);

    /**
     * 查询所有菜单列表
     */
    List<SysMenu> selectAllMenus();

    /**
     * 根据父菜单ID查询子菜单列表
     */
    List<SysMenu> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 根据角色ID查询菜单列表
     */
    List<SysMenu> selectByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户ID查询菜单列表
     */
    List<SysMenu> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据权限标识查询菜单
     */
    SysMenu selectByPerms(@Param("perms") String perms);

    /**
     * 根据路径查询菜单
     */
    SysMenu selectByPath(@Param("path") String path);

    /**
     * 根据组件路径查询菜单
     */
    SysMenu selectByComponent(@Param("component") String component);

    /**
     * 查询启用的菜单列表
     */
    List<SysMenu> selectEnabledMenus();

    /**
     * 查询显示的菜单列表
     */
    List<SysMenu> selectVisibleMenus();

    /**
     * 查询菜单树结构
     */
    List<SysMenu> selectMenuTree();

    /**
     * 根据条件查询菜单数量
     */
    long selectMenuCount(@Param("query") MenuQueryDTO query);

    /**
     * 查询菜单统计信息
     */
    List<Object> selectMenuStats();

    /**
     * 更新菜单状态
     */
    int updateMenuStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新菜单显示状态
     */
    int updateMenuVisible(@Param("id") Long id, @Param("visible") Integer visible);

    /**
     * 更新菜单排序
     */
    int updateMenuSort(@Param("id") Long id, @Param("sort") Integer sort);

    /**
     * 检查菜单名称是否唯一
     */
    int checkMenuNameUnique(@Param("menuName") String menuName, @Param("parentId") Long parentId, @Param("excludeId") Long excludeId);

    /**
     * 检查权限标识是否唯一
     */
    int checkPermsUnique(@Param("perms") String perms, @Param("excludeId") Long excludeId);

    /**
     * 检查路径是否唯一
     */
    int checkPathUnique(@Param("path") String path, @Param("excludeId") Long excludeId);

    /**
     * 检查组件路径是否唯一
     */
    int checkComponentUnique(@Param("component") String component, @Param("excludeId") Long excludeId);

    /**
     * 查询菜单的所有子菜单ID
     */
    List<Long> selectChildrenIds(@Param("menuId") Long menuId);

    /**
     * 查询菜单的所有父菜单ID
     */
    List<Long> selectParentIds(@Param("menuId") Long menuId);

    /**
     * 根据菜单类型查询菜单列表
     */
    List<SysMenu> selectByMenuType(@Param("menuType") String menuType);

    /**
     * 查询菜单的最大排序值
     */
    Integer selectMaxSort(@Param("parentId") Long parentId);
}