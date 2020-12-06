package com.lx.sys.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.sys.entity.User;
import com.lx.sys.mapper.RoleMapper;
import com.lx.sys.mapper.UserMapper;
import com.lx.sys.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-10-06
 */
@Service
@Transactional
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    RoleMapper roleMapper;

    @Override
    public boolean save(User entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(User entity) {
        return super.updateById(entity);
    }

    @Override
    public User getById(Serializable id) {
        return super.getById(id);
    }

    @Override
    public boolean removeById(Serializable id) {

        roleMapper.deleteRoleUserByUid(id);
        // TODO: 2020/12/6 删除头像
        return super.removeById(id);
    }

    @Override
    public void saveUserRole(Integer uid, Integer[] rids) {
        roleMapper.deleteRoleUserByUid(uid);
        if (null != rids && rids.length>0){

            this.roleMapper.saveUserRole(uid,rids);
        }
    }
}
