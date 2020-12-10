package com.lx.bus.service.impl;

import com.lx.bus.entity.Customer;
import com.lx.bus.mapper.CustomerMapper;
import com.lx.bus.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lidada
 * @since 2020-12-07
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
