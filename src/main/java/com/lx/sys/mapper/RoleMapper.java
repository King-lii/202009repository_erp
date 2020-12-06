package com.lx.sys.mapper;

import com.lx.sys.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;


import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author lx
 * @since 2020-11-11
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     *
     * @param id
     */
    void deleteRolePermissionByRid(Serializable id);

    /**
     *
     * @param id
     */
    void deleteRoleUserByRid(Serializable id);

    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    void saveRolePermission(@Param("rid") Integer roleId, @Param("pid") Integer pid);

    void deleteRoleUserByUid(Serializable id);

    List<Integer> queryUserRoleIdsByUid(Integer id);

    void saveUserRole(@Param("uid") Integer uid, @Param("rids") Integer[] rid);
}
