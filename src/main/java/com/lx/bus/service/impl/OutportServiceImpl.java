package com.lx.bus.service.impl;

import com.lx.bus.entity.Goods;
import com.lx.bus.entity.Inport;
import com.lx.bus.entity.Outport;
import com.lx.bus.mapper.GoodsMapper;
import com.lx.bus.mapper.InportMapper;
import com.lx.bus.mapper.OutportMapper;
import com.lx.bus.service.IOutportService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lx.sys.common.WebUtils;
import com.lx.sys.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lidada
 * @since 2020-12-13
 */
@Service
@Transactional
public class OutportServiceImpl extends ServiceImpl<OutportMapper, Outport> implements IOutportService {

    @Autowired(required = false)
    private InportMapper inportMapper;
    @Autowired(required = false)
    private GoodsMapper goodsMapper;


    @Override
    public void addOutport(Integer id, Integer number, String remark) {
            //1.根据进货单ID查询进货单信息
        Inport inport = this.inportMapper.selectById(id);
        //2.根据商品ID修改商品信息
        Goods goods = this.goodsMapper.selectById(inport.getGoodsid());
        goods.setNumber(goods.getNumber()-number);
        this.goodsMapper.updateById(goods);
        //3.添加退货单信息
        Outport entity = new Outport();
        entity.setGoodsid(inport.getGoodsid());
        entity.setNumber(number);
        User user = (User) WebUtils.getSession().getAttribute("user");
        entity.setOperateperson(user.getName());
        entity.setOutportprice(inport.getInportprice());
        entity.setOutputtime(new Date());
        entity.setPaytype(inport.getPaytype());
        entity.setProviderid(inport.getProviderid());
        entity.setRemark(remark);
        this.getBaseMapper().insert(entity);

    }
}
