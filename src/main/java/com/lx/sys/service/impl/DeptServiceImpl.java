package com.lx.sys.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.lx.sys.entity.Dept;
import com.lx.sys.mapper.DeptMapper;
import com.lx.sys.service.IDeptService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lx
 * @since 2020-10-17
 */
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements IDeptService {
    @Override
    public Dept getOne(Wrapper<Dept> queryWrapper, boolean throwEx) {
        return super.getOne(queryWrapper, throwEx);
    }

    @Override
    public boolean update(Wrapper<Dept> updateWrapper) {
        return false;
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }
}
