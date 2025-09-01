package com.shanhaifangzhou.admin.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanhaifangzhou.admin.dto.LoginResponse;
import com.shanhaifangzhou.admin.entity.SysMenu;
import com.shanhaifangzhou.admin.mapper.SysMenuMapper;
import com.shanhaifangzhou.admin.service.SysMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统菜单服务实现类
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<LoginResponse.MenuInfo> getUserMenus(Long userId) {
        // TODO: 根据用户ID获取有权限的菜单列表
        // 这里简化处理，返回所有菜单
        List<SysMenu> menus = getAllMenus();
        return convertToMenuInfo(menus);
    }

    @Override
    public List<SysMenu> getMenuTree() {
        List<SysMenu> allMenus = getAllMenus();
        return buildMenuTree(allMenus, 0L);
    }

    @Override
    public List<SysMenu> getMenusByRoleId(Long roleId) {
        // TODO: 根据角色ID获取菜单列表
        return null;
    }

    @Override
    public List<SysMenu> getAllMenus() {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getStatus, "0");
        wrapper.orderByAsc(SysMenu::getOrderNum);
        return list(wrapper);
    }

    @Override
    public boolean checkMenuNameUnique(SysMenu menu) {
        Long menuId = menu.getId() == null ? -1L : menu.getId();
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getMenuName, menu.getMenuName())
                .eq(SysMenu::getParentId, menu.getParentId())
                .ne(SysMenu::getId, menuId);
        return count(wrapper) == 0;
    }

    @Override
    public boolean checkMenuPermsUnique(SysMenu menu) {
        Long menuId = menu.getId() == null ? -1L : menu.getId();
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysMenu::getPerms, menu.getPerms())
                .ne(SysMenu::getId, menuId);
        return count(wrapper) == 0;
    }

    /**
     * 构建菜单树
     */
    private List<SysMenu> buildMenuTree(List<SysMenu> menus, Long parentId) {
        return menus.stream()
                .filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> {
                    menu.setChildren(buildMenuTree(menus, menu.getId()));
                    return menu;
                })
                .collect(Collectors.toList());
    }

    /**
     * 转换为MenuInfo
     */
    private List<LoginResponse.MenuInfo> convertToMenuInfo(List<SysMenu> menus) {
        return menus.stream().map(this::convertToMenuInfo).collect(Collectors.toList());
    }

    /**
     * 转换为MenuInfo
     */
    private LoginResponse.MenuInfo convertToMenuInfo(SysMenu menu) {
        LoginResponse.MenuInfo menuInfo = new LoginResponse.MenuInfo();
        menuInfo.setId(menu.getId());
        menuInfo.setMenuName(menu.getMenuName());
        menuInfo.setParentId(menu.getParentId());
        menuInfo.setOrderNum(menu.getOrderNum());
        menuInfo.setPath(menu.getPath());
        menuInfo.setComponent(menu.getComponent());
        menuInfo.setQuery(menu.getQuery());
        menuInfo.setIsFrame(menu.getIsFrame());
        menuInfo.setIsCache(menu.getIsCache());
        menuInfo.setMenuType(menu.getMenuType());
        menuInfo.setVisible(menu.getVisible());
        menuInfo.setPerms(menu.getPerms());
        menuInfo.setIcon(menu.getIcon());
        
        if (menu.getChildren() != null && !menu.getChildren().isEmpty()) {
            menuInfo.setChildren(convertToMenuInfo(menu.getChildren()));
        }
        
        return menuInfo;
    }
}