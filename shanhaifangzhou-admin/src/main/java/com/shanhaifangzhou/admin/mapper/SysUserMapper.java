package com.shanhaifangzhou.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shanhaifangzhou.admin.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户Mapper接口
 * 
 * @author shanhaifangzhou
 * @since 2024-01-01
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}