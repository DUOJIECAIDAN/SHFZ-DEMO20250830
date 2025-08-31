package com.shanhaifangzhou.admin.dept.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shanhaifangzhou.admin.dept.entity.SysDept;
import com.shanhaifangzhou.admin.dept.dto.DeptQueryDTO;
import com.shanhaifangzhou.admin.dept.dto.DeptCreateDTO;
import com.shanhaifangzhou.admin.dept.dto.DeptUpdateDTO;

import java.util.List;
import java.util.Map;

/**
 * 系统部门服务接口
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
public interface SysDeptService extends IService<SysDept> {

    /**
     * 分页查询部门列表
     */
    IPage<SysDept> getDeptPage(DeptQueryDTO queryDTO);

    /**
     * 查询所有部门列表
     */
    List<SysDept> getAllDepts();

    /**
     * 创建部门
     */
    boolean createDept(DeptCreateDTO createDTO);

    /**
     * 更新部门
     */
    boolean updateDept(DeptUpdateDTO updateDTO);

    /**
     * 删除部门
     */
    boolean deleteDept(Long id);

    /**
     * 批量删除部门
     */
    boolean batchDeleteDepts(List<Long> ids);

    /**
     * 更新部门状态
     */
    boolean updateDeptStatus(Long id, Integer status);

    /**
     * 更新部门显示状态
     */
    boolean updateDeptVisible(Long id, Integer visible);

    /**
     * 更新部门排序
     */
    boolean updateDeptSort(Long id, Integer sort);

    /**
     * 根据父部门ID查询子部门列表
     */
    List<SysDept> getDeptsByParentId(Long parentId);

    /**
     * 根据角色ID查询部门列表
     */
    List<SysDept> getDeptsByRoleId(Long roleId);

    /**
     * 根据用户ID查询部门列表
     */
    List<SysDept> getDeptsByUserId(Long userId);

    /**
     * 根据部门编码查询部门
     */
    SysDept getDeptByCode(String deptCode);

    /**
     * 根据部门名称查询部门
     */
    SysDept getDeptByName(String deptName);

    /**
     * 获取启用的部门列表
     */
    List<SysDept> getEnabledDepts();

    /**
     * 获取显示的部门列表
     */
    List<SysDept> getVisibleDepts();

    /**
     * 获取部门树结构
     */
    List<SysDept> getDeptTree();

    /**
     * 构建部门树
     */
    List<SysDept> buildDeptTree(List<SysDept> depts);

    /**
     * 检查部门编码是否唯一
     */
    boolean checkDeptCodeUnique(String deptCode, Long excludeId);

    /**
     * 检查部门名称是否唯一
     */
    boolean checkDeptNameUnique(String deptName, Long parentId, Long excludeId);

    /**
     * 获取部门的所有子部门ID
     */
    List<Long> getChildrenIds(Long deptId);

    /**
     * 获取部门的所有父部门ID
     */
    List<Long> getParentIds(Long deptId);

    /**
     * 根据部门类型查询部门列表
     */
    List<SysDept> getDeptsByType(Integer deptType);

    /**
     * 获取部门的最大排序值
     */
    Integer getMaxSort(Long parentId);

    /**
     * 获取部门统计信息
     */
    Map<String, Object> getDeptStats();

    /**
     * 导入部门数据
     */
    Map<String, Object> importDepts(List<Map<String, Object>> deptDataList);

    /**
     * 导出部门数据
     */
    List<SysDept> exportDepts(DeptQueryDTO queryDTO);

    /**
     * 获取部门模板数据
     */
    List<Map<String, Object>> getDeptTemplate();

    /**
     * 复制部门
     */
    boolean copyDept(Long sourceDeptId, String newDeptName, Long newParentId);

    /**
     * 移动部门
     */
    boolean moveDept(Long deptId, Long newParentId);

    /**
     * 展开/折叠部门
     */
    boolean toggleDeptExpand(Long deptId, Boolean expanded);

    /**
     * 获取部门用户数量
     */
    Integer getDeptUserCount(Long deptId);

    /**
     * 获取部门角色数量
     */
    Integer getDeptRoleCount(Long deptId);
}