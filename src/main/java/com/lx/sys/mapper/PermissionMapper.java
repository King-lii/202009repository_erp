package com.lx.sys.mapper;

import com.lx.sys.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2020-10-06
 */
public interface PermissionMapper extends BaseMapper<Permission> {
    @Delete("delete from sys_role_permission where pid = #{id}")
    void deleteRolePermissionByPid(@Param("id") Serializable id);
}
