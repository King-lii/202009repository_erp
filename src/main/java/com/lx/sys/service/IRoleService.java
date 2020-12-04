package com.lx.sys.service;

import com.lx.sys.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lx
 * @since 2020-11-11
 */
public interface IRoleService extends IService<Role> {

    List<Integer> queryRolePermissionIdsByRid(Integer roleId);

    void saveRolePermission(Integer roleId, Integer[] ids);
}
