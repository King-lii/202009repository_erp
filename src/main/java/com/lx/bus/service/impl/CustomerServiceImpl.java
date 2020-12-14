package com.lx.bus.service.impl;

import com.lx.bus.entity.Customer;
import com.lx.bus.mapper.CustomerMapper;
import com.lx.bus.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lidada
 * @since 2020-12-07
 */
@Service
@Transactional//是声明式事务管理 编程中使用的注解
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {
    //重写增删改查完成缓存
    @Override
    public boolean save(Customer entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(Customer entity) {
        return super.updateById(entity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> idList) {
        return super.removeByIds(idList);
    }

    @Override
    public Customer getById(Serializable id) {
        return super.getById(id);
    }
}
