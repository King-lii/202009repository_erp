package com.lx.bus.service;

import com.lx.bus.entity.Outport;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lidada
 * @since 2020-12-13
 */
public interface IOutportService extends IService<Outport> {
    /**
     * 退货
     * @param id 进货单id
     * @param number 进货数量
     * @param remark  进货备注
     */
    void addOutport(Integer id, Integer number, String remark);
}
