package com.shanhaifangzhou.admin.menu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanhaifangzhou.admin.menu.entity.SysMenu;
import com.shanhaifangzhou.admin.menu.dto.MenuQueryDTO;
import com.shanhaifangzhou.admin.menu.dto.MenuCreateDTO;
import com.shanhaifangzhou.admin.menu.dto.MenuUpdateDTO;

import java.util.List;
import java.util.Map;

/**
 * 系统菜单服务接口
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 分页查询菜单列表
     */
    IPage<SysMenu> getMenuPage(MenuQueryDTO queryDTO);

    /**
     * 查询所有菜单列表
     */
    List<SysMenu> getAllMenus();

    /**
     * 创建菜单
     */
    boolean createMenu(MenuCreateDTO createDTO);

    /**
     * 更新菜单
     */
    boolean updateMenu(MenuUpdateDTO updateDTO);

    /**
     * 删除菜单
     */
    boolean deleteMenu(Long id);

    /**
     * 批量删除菜单
     */
    boolean batchDeleteMenus(List<Long> ids);

    /**
     * 更新菜单状态
     */
    boolean updateMenuStatus(Long id, Integer status);

    /**
     * 更新菜单显示状态
     */
    boolean updateMenuVisible(Long id, Integer visible);

    /**
     * 更新菜单排序
     */
    boolean updateMenuSort(Long id, Integer sort);

    /**
     * 根据父菜单ID查询子菜单列表
     */
    List<SysMenu> getMenusByParentId(Long parentId);

    /**
     * 根据角色ID查询菜单列表
     */
    List<SysMenu> getMenusByRoleId(Long roleId);

    /**
     * 根据用户ID查询菜单列表
     */
    List<SysMenu> getMenusByUserId(Long userId);

    /**
     * 根据权限标识查询菜单
     */
    SysMenu getMenuByPerms(String perms);

    /**
     * 根据路径查询菜单
     */
    SysMenu getMenuByPath(String path);

    /**
     * 根据组件路径查询菜单
     */
    SysMenu getMenuByComponent(String component);

    /**
     * 获取启用的菜单列表
     */
    List<SysMenu> getEnabledMenus();

    /**
     * 获取显示的菜单列表
     */
    List<SysMenu> getVisibleMenus();

    /**
     * 获取菜单树结构
     */
    List<SysMenu> getMenuTree();

    /**
     * 构建菜单树
     */
    List<SysMenu> buildMenuTree(List<SysMenu> menus);

    /**
     * 检查菜单名称是否唯一
     */
    boolean checkMenuNameUnique(String menuName, Long parentId, Long excludeId);

    /**
     * 检查权限标识是否唯一
     */
    boolean checkPermsUnique(String perms, Long excludeId);

    /**
     * 检查路径是否唯一
     */
    boolean checkPathUnique(String path, Long excludeId);

    /**
     * 检查组件路径是否唯一
     */
    boolean checkComponentUnique(String component, Long excludeId);

    /**
     * 获取菜单的所有子菜单ID
     */
    List<Long> getChildrenIds(Long menuId);

    /**
     * 获取菜单的所有父菜单ID
     */
    List<Long> getParentIds(Long menuId);

    /**
     * 根据菜单类型查询菜单列表
     */
    List<SysMenu> getMenusByType(String menuType);

    /**
     * 获取菜单的最大排序值
     */
    Integer getMaxSort(Long parentId);

    /**
     * 获取菜单统计信息
     */
    Map<String, Object> getMenuStats();

    /**
     * 导入菜单数据
     */
    Map<String, Object> importMenus(List<Map<String, Object>> menuDataList);

    /**
     * 导出菜单数据
     */
    List<SysMenu> exportMenus(MenuQueryDTO queryDTO);

    /**
     * 获取菜单模板数据
     */
    List<Map<String, Object>> getMenuTemplate();

    /**
     * 复制菜单
     */
    boolean copyMenu(Long sourceMenuId, String newMenuName, Long newParentId);

    /**
     * 移动菜单
     */
    boolean moveMenu(Long menuId, Long newParentId);

    /**
     * 展开/折叠菜单
     */
    boolean toggleMenuExpand(Long menuId, Boolean expanded);
}