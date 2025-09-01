package com.shanhaifangzhou.admin.menu.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanhaifangzhou.admin.menu.entity.SysMenu;
import com.shanhaifangzhou.admin.menu.dto.MenuQueryDTO;
import com.shanhaifangzhou.admin.menu.dto.MenuCreateDTO;
import com.shanhaifangzhou.admin.menu.dto.MenuUpdateDTO;
import com.shanhaifangzhou.admin.menu.mapper.SysMenuMapper;
import com.shanhaifangzhou.admin.menu.service.SysMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统菜单服务实现类
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public IPage<SysMenu> getMenuPage(MenuQueryDTO queryDTO) {
        Page<SysMenu> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        return baseMapper.selectMenuPage(page, queryDTO);
    }

    @Override
    public List<SysMenu> getAllMenus() {
        return baseMapper.selectAllMenus();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createMenu(MenuCreateDTO createDTO) {
        // 检查菜单名称唯一性
        if (!checkMenuNameUnique(createDTO.getMenuName(), createDTO.getParentId(), null)) {
            throw new RuntimeException("同级菜单下菜单名称已存在");
        }

        // 检查权限标识唯一性
        if (StrUtil.isNotBlank(createDTO.getPerms()) && !checkPermsUnique(createDTO.getPerms(), null)) {
            throw new RuntimeException("权限标识已存在");
        }

        // 检查路径唯一性
        if (StrUtil.isNotBlank(createDTO.getPath()) && !checkPathUnique(createDTO.getPath(), null)) {
            throw new RuntimeException("路由地址已存在");
        }

        // 检查组件路径唯一性
        if (StrUtil.isNotBlank(createDTO.getComponent()) && !checkComponentUnique(createDTO.getComponent(), null)) {
            throw new RuntimeException("组件路径已存在");
        }

        // 创建菜单实体
        SysMenu menu = new SysMenu();
        BeanUtil.copyProperties(createDTO, menu);
        
        // 设置默认值
        menu.setStatus(createDTO.getStatus() != null ? createDTO.getStatus() : 1);
        menu.setVisible(createDTO.getVisible() != null ? createDTO.getVisible() : 1);
        menu.setIsFrame(createDTO.getIsFrame() != null ? createDTO.getIsFrame() : 0);
        menu.setIsCache(createDTO.getIsCache() != null ? createDTO.getIsCache() : 0);
        menu.setSort(createDTO.getSort() != null ? createDTO.getSort() : getMaxSort(createDTO.getParentId()) + 1);

        // 设置其他默认值
        setDefaultValues(menu);

        return save(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateMenu(MenuUpdateDTO updateDTO) {
        // 检查菜单是否存在
        SysMenu existingMenu = getById(updateDTO.getId());
        if (existingMenu == null) {
            throw new RuntimeException("菜单不存在");
        }

        // 检查菜单名称唯一性
        if (!checkMenuNameUnique(updateDTO.getMenuName(), updateDTO.getParentId(), updateDTO.getId())) {
            throw new RuntimeException("同级菜单下菜单名称已存在");
        }

        // 检查权限标识唯一性
        if (StrUtil.isNotBlank(updateDTO.getPerms()) && !checkPermsUnique(updateDTO.getPerms(), updateDTO.getId())) {
            throw new RuntimeException("权限标识已存在");
        }

        // 检查路径唯一性
        if (StrUtil.isNotBlank(updateDTO.getPath()) && !checkPathUnique(updateDTO.getPath(), updateDTO.getId())) {
            throw new RuntimeException("路由地址已存在");
        }

        // 检查组件路径唯一性
        if (StrUtil.isNotBlank(updateDTO.getComponent()) && !checkComponentUnique(updateDTO.getComponent(), updateDTO.getId())) {
            throw new RuntimeException("组件路径已存在");
        }

        // 检查是否将菜单设置为自己的子菜单
        if (updateDTO.getParentId() != null && updateDTO.getParentId().equals(updateDTO.getId())) {
            throw new RuntimeException("父菜单不能是自己");
        }

        // 检查是否将菜单设置为自己的子菜单的子菜单
        List<Long> childrenIds = getChildrenIds(updateDTO.getId());
        if (childrenIds.contains(updateDTO.getParentId())) {
            throw new RuntimeException("父菜单不能是子菜单");
        }

        // 更新菜单信息
        SysMenu menu = new SysMenu();
        BeanUtil.copyProperties(updateDTO, menu);
        menu.setUpdateTime(LocalDateTime.now());

        return updateById(menu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteMenu(Long id) {
        if (id == null) {
            return false;
        }
        
        // 检查菜单是否存在
        SysMenu menu = getById(id);
        if (menu == null) {
            return false;
        }
        
        // 检查是否有子菜单
        List<SysMenu> children = getMenusByParentId(id);
        if (!children.isEmpty()) {
            throw new RuntimeException("存在子菜单，不能删除");
        }
        
        // 检查是否有关联角色
        List<SysMenu> roleMenus = getMenusByRoleId(id);
        if (!roleMenus.isEmpty()) {
            throw new RuntimeException("菜单已分配给角色，不能删除");
        }
        
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteMenus(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        // 检查是否有子菜单
        for (Long id : ids) {
            List<SysMenu> children = getMenusByParentId(id);
            if (!children.isEmpty()) {
                throw new RuntimeException("存在子菜单，不能删除");
            }
        }
        
        return removeByIds(ids);
    }

    @Override
    public boolean updateMenuStatus(Long id, Integer status) {
        if (id == null || status == null) {
            return false;
        }
        
        return baseMapper.updateMenuStatus(id, status) > 0;
    }

    @Override
    public boolean updateMenuVisible(Long id, Integer visible) {
        if (id == null || visible == null) {
            return false;
        }
        
        return baseMapper.updateMenuVisible(id, visible) > 0;
    }

    @Override
    public boolean updateMenuSort(Long id, Integer sort) {
        if (id == null || sort == null) {
            return false;
        }
        
        return baseMapper.updateMenuSort(id, sort) > 0;
    }

    @Override
    public List<SysMenu> getMenusByParentId(Long parentId) {
        if (parentId == null) {
            return null;
        }
        return baseMapper.selectByParentId(parentId);
    }

    @Override
    public List<SysMenu> getMenusByRoleId(Long roleId) {
        if (roleId == null) {
            return null;
        }
        return baseMapper.selectByRoleId(roleId);
    }

    @Override
    public List<SysMenu> getMenusByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        return baseMapper.selectByUserId(userId);
    }

    @Override
    public SysMenu getMenuByPerms(String perms) {
        if (StrUtil.isBlank(perms)) {
            return null;
        }
        return baseMapper.selectByPerms(perms);
    }

    @Override
    public SysMenu getMenuByPath(String path) {
        if (StrUtil.isBlank(path)) {
            return null;
        }
        return baseMapper.selectByPath(path);
    }

    @Override
    public SysMenu getMenuByComponent(String component) {
        if (StrUtil.isBlank(component)) {
            return null;
        }
        return baseMapper.selectByComponent(component);
    }

    @Override
    public List<SysMenu> getEnabledMenus() {
        return baseMapper.selectEnabledMenus();
    }

    @Override
    public List<SysMenu> getVisibleMenus() {
        return baseMapper.selectVisibleMenus();
    }

    @Override
    public List<SysMenu> getMenuTree() {
        List<SysMenu> menus = baseMapper.selectMenuTree();
        return buildMenuTree(menus);
    }

    @Override
    public List<SysMenu> buildMenuTree(List<SysMenu> menus) {
        if (menus == null || menus.isEmpty()) {
            return new ArrayList<>();
        }

        // 构建菜单树
        Map<Long, List<SysMenu>> menuMap = menus.stream()
                .collect(Collectors.groupingBy(menu -> 
                    menu.getParentId() != null ? menu.getParentId() : 0L));

        // 递归构建树结构
        return buildMenuTreeRecursive(menuMap, 0L);
    }

    private List<SysMenu> buildMenuTreeRecursive(Map<Long, List<SysMenu>> menuMap, Long parentId) {
        List<SysMenu> children = menuMap.get(parentId);
        if (children == null) {
            return new ArrayList<>();
        }

        for (SysMenu menu : children) {
            List<SysMenu> childList = buildMenuTreeRecursive(menuMap, menu.getId());
            if (!childList.isEmpty()) {
                menu.setChildren(childList);
            }
        }

        return children;
    }

    @Override
    public boolean checkMenuNameUnique(String menuName, Long parentId, Long excludeId) {
        if (StrUtil.isBlank(menuName)) {
            return false;
        }
        
        return baseMapper.checkMenuNameUnique(menuName, parentId, excludeId) == 0;
    }

    @Override
    public boolean checkPermsUnique(String perms, Long excludeId) {
        if (StrUtil.isBlank(perms)) {
            return false;
        }
        
        return baseMapper.checkPermsUnique(perms, excludeId) == 0;
    }

    @Override
    public boolean checkPathUnique(String path, Long excludeId) {
        if (StrUtil.isBlank(path)) {
            return false;
        }
        
        return baseMapper.checkPathUnique(path, excludeId) == 0;
    }

    @Override
    public boolean checkComponentUnique(String component, Long excludeId) {
        if (StrUtil.isBlank(component)) {
            return false;
        }
        
        return baseMapper.checkComponentUnique(component, excludeId) == 0;
    }

    @Override
    public List<Long> getChildrenIds(Long menuId) {
        if (menuId == null) {
            return null;
        }
        return baseMapper.selectChildrenIds(menuId);
    }

    @Override
    public List<Long> getParentIds(Long menuId) {
        if (menuId == null) {
            return null;
        }
        return baseMapper.selectParentIds(menuId);
    }

    @Override
    public List<SysMenu> getMenusByType(String menuType) {
        if (StrUtil.isBlank(menuType)) {
            return null;
        }
        return baseMapper.selectByMenuType(menuType);
    }

    @Override
    public Integer getMaxSort(Long parentId) {
        Integer maxSort = baseMapper.selectMaxSort(parentId);
        return maxSort != null ? maxSort : 0;
    }

    @Override
    public Map<String, Object> getMenuStats() {
        List<Object> stats = baseMapper.selectMenuStats();
        if (stats != null && !stats.isEmpty()) {
            return (Map<String, Object>) stats.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> importMenus(List<Map<String, Object>> menuDataList) {
        // TODO: 实现菜单数据导入逻辑
        Map<String, Object> result = new HashMap<>();
        result.put("success", 0);
        result.put("failure", 0);
        result.put("message", "导入功能待实现");
        return result;
    }

    @Override
    public List<SysMenu> exportMenus(MenuQueryDTO queryDTO) {
        // TODO: 实现菜单数据导出逻辑
        return null;
    }

    @Override
    public List<Map<String, Object>> getMenuTemplate() {
        // TODO: 实现菜单模板数据逻辑
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean copyMenu(Long sourceMenuId, String newMenuName, Long newParentId) {
        if (sourceMenuId == null || StrUtil.isBlank(newMenuName)) {
            return false;
        }
        
        // 检查新菜单名称是否唯一
        if (!checkMenuNameUnique(newMenuName, newParentId, null)) {
            throw new RuntimeException("同级菜单下菜单名称已存在");
        }
        
        // 获取源菜单信息
        SysMenu sourceMenu = getById(sourceMenuId);
        if (sourceMenu == null) {
            throw new RuntimeException("源菜单不存在");
        }
        
        // 创建新菜单
        SysMenu newMenu = new SysMenu();
        BeanUtil.copyProperties(sourceMenu, newMenu);
        newMenu.setId(null);
        newMenu.setMenuName(newMenuName);
        newMenu.setParentId(newParentId);
        newMenu.setSort(getMaxSort(newParentId) + 1);
        newMenu.setCreateTime(LocalDateTime.now());
        newMenu.setUpdateTime(LocalDateTime.now());
        
        return save(newMenu);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean moveMenu(Long menuId, Long newParentId) {
        if (menuId == null || newParentId == null) {
            return false;
        }
        
        // 检查是否将菜单移动到自己的子菜单下
        if (newParentId.equals(menuId)) {
            throw new RuntimeException("不能将菜单移动到自己的子菜单下");
        }
        
        // 检查是否将菜单移动到自己的子菜单的子菜单下
        List<Long> childrenIds = getChildrenIds(menuId);
        if (childrenIds.contains(newParentId)) {
            throw new RuntimeException("不能将菜单移动到子菜单的子菜单下");
        }
        
        SysMenu menu = new SysMenu();
        menu.setId(menuId);
        menu.setParentId(newParentId);
        menu.setUpdateTime(LocalDateTime.now());
        
        return updateById(menu);
    }

    @Override
    public boolean toggleMenuExpand(Long menuId, Boolean expanded) {
        // TODO: 实现菜单展开/折叠逻辑
        return true;
    }

    /**
     * 设置菜单默认值
     */
    private void setDefaultValues(SysMenu menu) {
        if (menu.getExternal() == null) {
            menu.setExternal(0);
        }
        if (menu.getNewWindow() == null) {
            menu.setNewWindow(0);
        }
        if (menu.getAffix() == null) {
            menu.setAffix(0);
        }
        if (menu.getHideBreadcrumb() == null) {
            menu.setHideBreadcrumb(0);
        }
        if (menu.getHideTab() == null) {
            menu.setHideTab(0);
        }
        if (menu.getHideMenu() == null) {
            menu.setHideMenu(0);
        }
        if (menu.getHideChildren() == null) {
            menu.setHideChildren(0);
        }
        if (menu.getHideSearch() == null) {
            menu.setHideSearch(0);
        }
        if (menu.getHideFooter() == null) {
            menu.setHideFooter(0);
        }
        if (menu.getHideSetting() == null) {
            menu.setHideSetting(0);
        }
        if (menu.getHideI18n() == null) {
            menu.setHideI18n(0);
        }
        if (menu.getHideFullscreen() == null) {
            menu.setHideFullscreen(0);
        }
        if (menu.getHideRefresh() == null) {
            menu.setHideRefresh(0);
        }
        if (menu.getHideHelp() == null) {
            menu.setHideHelp(0);
        }
        if (menu.getHideNotice() == null) {
            menu.setHideNotice(0);
        }
        if (menu.getHideUserInfo() == null) {
            menu.setHideUserInfo(0);
        }
        if (menu.getHideLogout() == null) {
            menu.setHideLogout(0);
        }
    }
}