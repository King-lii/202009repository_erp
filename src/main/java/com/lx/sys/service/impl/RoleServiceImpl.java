package com.lx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.sys.entity.Role;
import com.lx.sys.mapper.RoleMapper;
import com.lx.sys.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-11-11
 */
@Service
@Transactional
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    @Override
    public boolean removeById(Serializable id) {
        //根据角色id删除sys_permission_role
        this.getBaseMapper().deleteRolePermissionByRid(id);
        //根据角色ID删除sys_permission_user
        this.getBaseMapper().deleteRoleUserByRid(id);
        return super.removeById(id);
    }

    @Override
    public List<Integer> queryRolePermissionIdsByRid(Integer roleId) {
        return this.getBaseMapper().queryRolePermissionIdsByRid(roleId);
    }

    @Override
    public void saveRolePermission(Integer roleId, Integer[] ids) {
        RoleMapper roleMapper = this.getBaseMapper();
        roleMapper.deleteRolePermissionByRid(roleId);
        if (null!=ids && ids.length>0){
            for (Integer pid : ids){
                roleMapper.saveRolePermission(roleId,pid);
            }
        }
    }

    @Override
    public List<Integer> queryUserRoleIdsByUid(Integer id) {

        return this.getBaseMapper().queryUserRoleIdsByUid(id);
    }
}
