package com.lx.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.bus.entity.Goods;
import com.lx.bus.entity.Inport;
import com.lx.bus.entity.Provider;
import com.lx.bus.service.IGoodsService;
import com.lx.bus.service.IInportService;
import com.lx.bus.service.IProviderService;
import com.lx.bus.vo.GoodsVo;
import com.lx.bus.vo.InportVo;
import com.lx.sys.common.*;
import com.lx.sys.entity.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
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
@RequestMapping("/inport")
public class InportController {
    @Autowired
    private IInportService iInportService;

    @Autowired
    private IProviderService iProviderService;

    @Autowired
    private IGoodsService iGoodsService;
    /**
     * 查询
     */
    @RequestMapping("loadAllInport")
    public DataGridView loadAllInport(InportVo inportVo){
        /**
         * 获取分页数据
         */
        IPage<Inport> page = new Page<>(inportVo.getPage(),inportVo.getLimit());
        /**
         * 实例Inport类型的QueryWrapper对象
         */
        QueryWrapper<Inport> queryWrapper = new QueryWrapper<>();

        queryWrapper.eq(inportVo.getProviderid()!=null&&inportVo.getProviderid()!=0, "providerid",inportVo.getProviderid());
        queryWrapper.eq(inportVo.getGoodsid()!=null&&inportVo.getGoodsid()!=0, "goodsid",inportVo.getGoodsid());
        queryWrapper.ge(inportVo.getStartTime()!=null,"inporttime", inportVo.getStartTime());
        queryWrapper.le(inportVo.getEndTime()!=null,"inporttime", inportVo.getEndTime());
        //操作员模糊查询
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getOperateperson()),"operateperson",inportVo.getOperateperson());
        //备注模糊查询
        queryWrapper.like(StringUtils.isNotBlank(inportVo.getRemark()),"remark",inportVo.getRemark());
        queryWrapper.orderByDesc("inporttime");
        /**
         * 获取provider中供应商名称
         */
        this.iInportService.page(page,queryWrapper);
        List<Inport> records = page.getRecords();
        //模糊查询供应商名字
        for (Inport inport : records) {
            Provider provider = this.iProviderService.getById(inport.getProviderid());
            if (null != provider){
                inport.setProvidername(provider.getProvidername());
            }
            //模糊查询商品名
            Goods goods = this.iGoodsService.getById(inport.getGoodsid());
            if (null!=goods){
                //获取商品名和商品规格
                inport.setGoodsname(goods.getGoodsname());
                inport.setSize(goods.getSize());
            }

        }
        return new DataGridView(page.getTotal(),records);
    }


    /**
     * 添加数据
     */
    @RequestMapping("addInport")
    public ResultObj addInport(InportVo InportVo){
        try {
           InportVo.setInporttime(new Date());
           //通过session获取用户名
           User user = (User) WebUtils.getSession().getAttribute("user");
           InportVo.setOperateperson(user.getName());
            this.iInportService.save(InportVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改数据
     */
    @RequestMapping("updateInport")
    public ResultObj updateInport(InportVo InportVo){
        try {
            this.iInportService.updateById(InportVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除数据
     */
    @RequestMapping("deleteInport")
    public ResultObj deleteInport(Integer id){
        try {
            this.iInportService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
