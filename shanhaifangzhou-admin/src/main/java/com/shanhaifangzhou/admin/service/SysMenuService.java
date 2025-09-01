package com.shanhaifangzhou.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.shanhaifangzhou.admin.dto.LoginResponse;
import com.shanhaifangzhou.admin.entity.SysMenu;

import java.util.List;

/**
 * 系统菜单服务接口
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 获取用户菜单列表
     */
    List<LoginResponse.MenuInfo> getUserMenus(Long userId);

    /**
     * 获取菜单树
     */
    List<SysMenu> getMenuTree();

    /**
     * 根据角色ID获取菜单列表
     */
    List<SysMenu> getMenusByRoleId(Long roleId);

    /**
     * 获取所有菜单（用于角色分配）
     */
    List<SysMenu> getAllMenus();

    /**
     * 检查菜单名称是否唯一
     */
    boolean checkMenuNameUnique(SysMenu menu);

    /**
     * 检查菜单权限标识是否唯一
     */
    boolean checkMenuPermsUnique(SysMenu menu);
}