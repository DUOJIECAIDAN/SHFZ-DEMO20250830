package com.shanhaifangzhou.admin.dept.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shanhaifangzhou.admin.dept.entity.SysDept;
import com.shanhaifangzhou.admin.dept.dto.DeptQueryDTO;
import com.shanhaifangzhou.admin.dept.dto.DeptCreateDTO;
import com.shanhaifangzhou.admin.dept.dto.DeptUpdateDTO;
import com.shanhaifangzhou.admin.dept.mapper.SysDeptMapper;
import com.shanhaifangzhou.admin.dept.service.SysDeptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 系统部门服务实现类
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Slf4j
@Service
public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {

    @Override
    public IPage<SysDept> getDeptPage(DeptQueryDTO queryDTO) {
        Page<SysDept> page = new Page<>(queryDTO.getCurrent(), queryDTO.getSize());
        return baseMapper.selectDeptPage(page, queryDTO);
    }

    @Override
    public List<SysDept> getAllDepts() {
        return baseMapper.selectAllDepts();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean createDept(DeptCreateDTO createDTO) {
        // 检查部门编码唯一性
        if (!checkDeptCodeUnique(createDTO.getDeptCode(), null)) {
            throw new RuntimeException("部门编码已存在");
        }

        // 检查部门名称唯一性
        if (!checkDeptNameUnique(createDTO.getDeptName(), createDTO.getParentId(), null)) {
            throw new RuntimeException("同级部门下部门名称已存在");
        }

        // 创建部门实体
        SysDept dept = new SysDept();
        BeanUtil.copyProperties(createDTO, dept);
        
        // 设置默认值
        dept.setStatus(createDTO.getStatus() != null ? createDTO.getStatus() : 1);
        dept.setVisible(createDTO.getVisible() != null ? createDTO.getVisible() : 1);
        dept.setSort(createDTO.getSort() != null ? createDTO.getSort() : getMaxSort(createDTO.getParentId()) + 1);
        dept.setIsSystem(0);
        dept.setIsDeletable(1);
        dept.setIsEditable(1);

        return save(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateDept(DeptUpdateDTO updateDTO) {
        // 检查部门是否存在
        SysDept existingDept = getById(updateDTO.getId());
        if (existingDept == null) {
            throw new RuntimeException("部门不存在");
        }

        // 检查部门编码唯一性
        if (!checkDeptCodeUnique(updateDTO.getDeptCode(), updateDTO.getId())) {
            throw new RuntimeException("部门编码已存在");
        }

        // 检查部门名称唯一性
        if (!checkDeptNameUnique(updateDTO.getDeptName(), updateDTO.getParentId(), updateDTO.getId())) {
            throw new RuntimeException("同级部门下部门名称已存在");
        }

        // 检查是否将部门设置为自己的子部门
        if (updateDTO.getParentId() != null && updateDTO.getParentId().equals(updateDTO.getId())) {
            throw new RuntimeException("父部门不能是自己");
        }

        // 检查是否将部门设置为自己的子部门的子部门
        List<Long> childrenIds = getChildrenIds(updateDTO.getId());
        if (childrenIds.contains(updateDTO.getParentId())) {
            throw new RuntimeException("父部门不能是子部门");
        }

        // 更新部门信息
        SysDept dept = new SysDept();
        BeanUtil.copyProperties(updateDTO, dept);
        dept.setUpdateTime(LocalDateTime.now());

        return updateById(dept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteDept(Long id) {
        if (id == null) {
            return false;
        }
        
        // 检查部门是否存在
        SysDept dept = getById(id);
        if (dept == null) {
            return false;
        }
        
        // 检查是否有子部门
        List<SysDept> children = getDeptsByParentId(id);
        if (!children.isEmpty()) {
            throw new RuntimeException("存在子部门，不能删除");
        }
        
        // 检查是否有关联用户
        Integer userCount = getDeptUserCount(id);
        if (userCount > 0) {
            throw new RuntimeException("部门下存在用户，不能删除");
        }
        
        // 检查是否有关联角色
        Integer roleCount = getDeptRoleCount(id);
        if (roleCount > 0) {
            throw new RuntimeException("部门已分配给角色，不能删除");
        }
        
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteDepts(List<Long> ids) {
        if (ids == null || ids.isEmpty()) {
            return false;
        }
        
        // 检查是否有子部门
        for (Long id : ids) {
            List<SysDept> children = getDeptsByParentId(id);
            if (!children.isEmpty()) {
                throw new RuntimeException("存在子部门，不能删除");
            }
        }
        
        // 检查是否有关联用户
        for (Long id : ids) {
            Integer userCount = getDeptUserCount(id);
            if (userCount > 0) {
                throw new RuntimeException("部门下存在用户，不能删除");
            }
        }
        
        return removeByIds(ids);
    }

    @Override
    public boolean updateDeptStatus(Long id, Integer status) {
        if (id == null || status == null) {
            return false;
        }
        
        return baseMapper.updateDeptStatus(id, status) > 0;
    }

    @Override
    public boolean updateDeptVisible(Long id, Integer visible) {
        if (id == null || visible == null) {
            return false;
        }
        
        return baseMapper.updateDeptVisible(id, visible) > 0;
    }

    @Override
    public boolean updateDeptSort(Long id, Integer sort) {
        if (id == null || sort == null) {
            return false;
        }
        
        return baseMapper.updateDeptSort(id, sort) > 0;
    }

    @Override
    public List<SysDept> getDeptsByParentId(Long parentId) {
        if (parentId == null) {
            return null;
        }
        return baseMapper.selectByParentId(parentId);
    }

    @Override
    public List<SysDept> getDeptsByRoleId(Long roleId) {
        if (roleId == null) {
            return null;
        }
        return baseMapper.selectByRoleId(roleId);
    }

    @Override
    public List<SysDept> getDeptsByUserId(Long userId) {
        if (userId == null) {
            return null;
        }
        return baseMapper.selectByUserId(userId);
    }

    @Override
    public SysDept getDeptByCode(String deptCode) {
        if (StrUtil.isBlank(deptCode)) {
            return null;
        }
        return baseMapper.selectByDeptCode(deptCode);
    }

    @Override
    public SysDept getDeptByName(String deptName) {
        if (StrUtil.isBlank(deptName)) {
            return null;
        }
        return baseMapper.selectByDeptName(deptName);
    }

    @Override
    public List<SysDept> getEnabledDepts() {
        return baseMapper.selectEnabledDepts();
    }

    @Override
    public List<SysDept> getVisibleDepts() {
        return baseMapper.selectVisibleDepts();
    }

    @Override
    public List<SysDept> getDeptTree() {
        List<SysDept> depts = baseMapper.selectDeptTree();
        return buildDeptTree(depts);
    }

    @Override
    public List<SysDept> buildDeptTree(List<SysDept> depts) {
        if (depts == null || depts.isEmpty()) {
            return new ArrayList<>();
        }

        // 构建部门树
        Map<Long, List<SysDept>> deptMap = depts.stream()
                .collect(Collectors.groupingBy(dept -> 
                    dept.getParentId() != null ? dept.getParentId() : 0L));

        // 递归构建树结构
        return buildDeptTreeRecursive(deptMap, 0L);
    }

    private List<SysDept> buildDeptTreeRecursive(Map<Long, List<SysDept>> deptMap, Long parentId) {
        List<SysDept> children = deptMap.get(parentId);
        if (children == null) {
            return new ArrayList<>();
        }

        for (SysDept dept : children) {
            List<SysDept> childList = buildDeptTreeRecursive(deptMap, dept.getId());
            if (!childList.isEmpty()) {
                dept.setChildren(childList);
            }
        }

        return children;
    }

    @Override
    public boolean checkDeptCodeUnique(String deptCode, Long excludeId) {
        if (StrUtil.isBlank(deptCode)) {
            return false;
        }
        
        return baseMapper.checkDeptCodeUnique(deptCode, excludeId) == 0;
    }

    @Override
    public boolean checkDeptNameUnique(String deptName, Long parentId, Long excludeId) {
        if (StrUtil.isBlank(deptName)) {
            return false;
        }
        
        return baseMapper.checkDeptNameUnique(deptName, parentId, excludeId) == 0;
    }

    @Override
    public List<Long> getChildrenIds(Long deptId) {
        if (deptId == null) {
            return null;
        }
        return baseMapper.selectChildrenIds(deptId);
    }

    @Override
    public List<Long> getParentIds(Long deptId) {
        if (deptId == null) {
            return null;
        }
        return baseMapper.selectParentIds(deptId);
    }

    @Override
    public List<SysDept> getDeptsByType(Integer deptType) {
        if (deptType == null) {
            return null;
        }
        return baseMapper.selectByDeptType(deptType);
    }

    @Override
    public Integer getMaxSort(Long parentId) {
        Integer maxSort = baseMapper.selectMaxSort(parentId);
        return maxSort != null ? maxSort : 0;
    }

    @Override
    public Map<String, Object> getDeptStats() {
        List<Object> stats = baseMapper.selectDeptStats();
        if (stats != null && !stats.isEmpty()) {
            return (Map<String, Object>) stats.get(0);
        }
        return null;
    }

    @Override
    public Map<String, Object> importDepts(List<Map<String, Object>> deptDataList) {
        // TODO: 实现部门数据导入逻辑
        Map<String, Object> result = new HashMap<>();
        result.put("success", 0);
        result.put("failure", 0);
        result.put("message", "导入功能待实现");
        return result;
    }

    @Override
    public List<SysDept> exportDepts(DeptQueryDTO queryDTO) {
        // TODO: 实现部门数据导出逻辑
        return null;
    }

    @Override
    public List<Map<String, Object>> getDeptTemplate() {
        // TODO: 实现部门模板数据逻辑
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean copyDept(Long sourceDeptId, String newDeptName, Long newParentId) {
        if (sourceDeptId == null || StrUtil.isBlank(newDeptName)) {
            return false;
        }
        
        // 检查新部门名称是否唯一
        if (!checkDeptNameUnique(newDeptName, newParentId, null)) {
            throw new RuntimeException("同级部门下部门名称已存在");
        }
        
        // 获取源部门信息
        SysDept sourceDept = getById(sourceDeptId);
        if (sourceDept == null) {
            throw new RuntimeException("源部门不存在");
        }
        
        // 创建新部门
        SysDept newDept = new SysDept();
        BeanUtil.copyProperties(sourceDept, newDept);
        newDept.setId(null);
        newDept.setDeptName(newDeptName);
        newDept.setParentId(newParentId);
        newDept.setSort(getMaxSort(newParentId) + 1);
        newDept.setCreateTime(LocalDateTime.now());
        newDept.setUpdateTime(LocalDateTime.now());
        
        return save(newDept);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean moveDept(Long deptId, Long newParentId) {
        if (deptId == null || newParentId == null) {
            return false;
        }
        
        // 检查是否将部门移动到自己的子部门下
        if (newParentId.equals(deptId)) {
            throw new RuntimeException("不能将部门移动到自己的子部门下");
        }
        
        // 检查是否将部门移动到自己的子部门的子部门下
        List<Long> childrenIds = getChildrenIds(deptId);
        if (childrenIds.contains(newParentId)) {
            throw new RuntimeException("不能将部门移动到子部门的子部门下");
        }
        
        SysDept dept = new SysDept();
        dept.setId(deptId);
        dept.setParentId(newParentId);
        dept.setUpdateTime(LocalDateTime.now());
        
        return updateById(dept);
    }

    @Override
    public boolean toggleDeptExpand(Long deptId, Boolean expanded) {
        // TODO: 实现部门展开/折叠逻辑
        return true;
    }

    @Override
    public Integer getDeptUserCount(Long deptId) {
        if (deptId == null) {
            return 0;
        }
        Integer count = baseMapper.selectUserCount(deptId);
        return count != null ? count : 0;
    }

    @Override
    public Integer getDeptRoleCount(Long deptId) {
        if (deptId == null) {
            return 0;
        }
        Integer count = baseMapper.selectRoleCount(deptId);
        return count != null ? count : 0;
    }
}