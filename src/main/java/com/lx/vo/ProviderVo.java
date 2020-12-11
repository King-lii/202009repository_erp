package com.lx.vo;

import com.lx.bus.entity.Customer;
import com.lx.bus.entity.Provider;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false) //生成equals(Object other) 和 hashCode()方法
public class ProviderVo extends Provider {
    /**
     * 序列化方便数据传递，相当于加密解密
     */
    private static final long serialVersionUID = 1L;
    /**
     * 设置分页的初始值和页数
     */
    private Integer page=1;
    private Integer limit=10;
    /**
     *
     */
    private Integer[] ids;

}
