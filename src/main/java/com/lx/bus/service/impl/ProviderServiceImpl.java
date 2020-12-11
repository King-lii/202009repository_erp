package com.lx.bus.service.impl;

import com.lx.bus.entity.Provider;
import com.lx.bus.mapper.ProviderMapper;
import com.lx.bus.service.IProviderService;
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
 * @since 2020-12-10
 */
@Service
@Transactional//是声明式事务管理 编程中使用的注解
public class ProviderServiceImpl extends ServiceImpl<ProviderMapper, Provider> implements IProviderService {
    @Override
    public boolean save(Provider entity) {
        return super.save(entity);
    }

    @Override
    public boolean updateById(Provider entity) {
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
    public Provider getById(Serializable id) {
        return super.getById(id);
    }

}
