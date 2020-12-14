package com.lx.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.bus.entity.Goods;
import com.lx.bus.entity.Inport;
import com.lx.bus.entity.Outport;
import com.lx.bus.entity.Provider;
import com.lx.bus.service.IGoodsService;
import com.lx.bus.service.IInportService;
import com.lx.bus.service.IOutportService;
import com.lx.bus.service.IProviderService;
import com.lx.bus.vo.OutportVo;
import com.lx.bus.vo.OutportVo;
import com.lx.sys.common.DataGridView;
import com.lx.sys.common.ResultObj;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lidada
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/outport")
public class OutportController {
    @Autowired
    private IOutportService iOutportService;
    @Autowired
    private IProviderService iProviderService;
    @Autowired
    private IGoodsService iGoodsService;
    /**
     * 查询
     */
    @RequestMapping("loadAllOutport")
    public DataGridView loadAllOutport(OutportVo outportVo) {
        IPage<Outport> page = new Page<>(outportVo.getPage(), outportVo.getLimit());
        QueryWrapper<Outport> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(outportVo.getProviderid()!=null&&outportVo.getProviderid()!=0,"providerid",outportVo.getProviderid());
        queryWrapper.eq(outportVo.getGoodsid()!=null&&outportVo.getGoodsid()!=0,"goodsid",outportVo.getGoodsid());
        queryWrapper.ge(outportVo.getStartTime()!=null, "outputtime", outportVo.getStartTime());
        queryWrapper.le(outportVo.getEndTime()!=null, "outputtime", outportVo.getEndTime());
        queryWrapper.like(StringUtils.isNotBlank(outportVo.getOperateperson()), "operateperson", outportVo.getOperateperson());
        queryWrapper.like(StringUtils.isNotBlank(outportVo.getRemark()), "remark", outportVo.getRemark());
        queryWrapper.orderByDesc("outputtime");
        this.iOutportService.page(page, queryWrapper);
        List<Outport> records = page.getRecords();

        for (Outport outport : records) {
            Provider provider = this.iProviderService.getById(outport.getProviderid());
            if(null!=provider) {
                outport.setProvidername(provider.getProvidername());
            }
            Goods goods = this.iGoodsService.getById(outport.getGoodsid());
            if(null!=goods) {
                outport.setGoodsname(goods.getGoodsname());
                outport.setSize(goods.getSize());
            }
        }

        return new DataGridView(page.getTotal(), records);
    }


    /**
     * 添加退货信息
     */
    @RequestMapping("addOutport")
    public ResultObj addOutport(Integer id,Integer number,String remark){
        try {
            this.iOutportService.addOutport(id,number,remark);
            return ResultObj.OPERATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.OPERATE_ERROR;
        }
    }
}
