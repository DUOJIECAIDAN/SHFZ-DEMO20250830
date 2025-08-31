package com.shanhaifangzhou.admin.dept.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.shanhaifangzhou.admin.dept.entity.SysDept;
import com.shanhaifangzhou.admin.dept.dto.DeptQueryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统部门Mapper接口
 *
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Mapper
public interface SysDeptMapper extends BaseMapper<SysDept> {

    /**
     * 分页查询部门列表
     */
    IPage<SysDept> selectDeptPage(Page<SysDept> page, @Param("query") DeptQueryDTO query);

    /**
     * 查询所有部门列表
     */
    List<SysDept> selectAllDepts();

    /**
     * 根据父部门ID查询子部门列表
     */
    List<SysDept> selectByParentId(@Param("parentId") Long parentId);

    /**
     * 根据角色ID查询部门列表
     */
    List<SysDept> selectByRoleId(@Param("roleId") Long roleId);

    /**
     * 根据用户ID查询部门列表
     */
    List<SysDept> selectByUserId(@Param("userId") Long userId);

    /**
     * 根据部门编码查询部门
     */
    SysDept selectByDeptCode(@Param("deptCode") String deptCode);

    /**
     * 根据部门名称查询部门
     */
    SysDept selectByDeptName(@Param("deptName") String deptName);

    /**
     * 查询启用的部门列表
     */
    List<SysDept> selectEnabledDepts();

    /**
     * 查询显示的部门列表
     */
    List<SysDept> selectVisibleDepts();

    /**
     * 查询部门树结构
     */
    List<SysDept> selectDeptTree();

    /**
     * 根据条件查询部门数量
     */
    long selectDeptCount(@Param("query") DeptQueryDTO query);

    /**
     * 查询部门统计信息
     */
    List<Object> selectDeptStats();

    /**
     * 更新部门状态
     */
    int updateDeptStatus(@Param("id") Long id, @Param("status") Integer status);

    /**
     * 更新部门显示状态
     */
    int updateDeptVisible(@Param("id") Long id, @Param("visible") Integer visible);

    /**
     * 更新部门排序
     */
    int updateDeptSort(@Param("id") Long id, @Param("sort") Integer sort);

    /**
     * 检查部门编码是否唯一
     */
    int checkDeptCodeUnique(@Param("deptCode") String deptCode, @Param("excludeId") Long excludeId);

    /**
     * 检查部门名称是否唯一
     */
    int checkDeptNameUnique(@Param("deptName") String deptName, @Param("parentId") Long parentId, @Param("excludeId") Long excludeId);

    /**
     * 查询部门的所有子部门ID
     */
    List<Long> selectChildrenIds(@Param("deptId") Long deptId);

    /**
     * 查询部门的所有父部门ID
     */
    List<Long> selectParentIds(@Param("deptId") Long deptId);

    /**
     * 根据部门类型查询部门列表
     */
    List<SysDept> selectByDeptType(@Param("deptType") Integer deptType);

    /**
     * 查询部门的最大排序值
     */
    Integer selectMaxSort(@Param("parentId") Long parentId);

    /**
     * 查询部门用户数量
     */
    Integer selectUserCount(@Param("deptId") Long deptId);

    /**
     * 查询部门角色数量
     */
    Integer selectRoleCount(@Param("deptId") Long deptId);
}