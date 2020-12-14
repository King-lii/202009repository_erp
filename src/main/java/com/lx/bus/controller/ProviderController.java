package com.lx.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.bus.entity.Provider;
import com.lx.bus.service.IGoodsService;
import com.lx.bus.service.IProviderService;
import com.lx.sys.common.Constast;
import com.lx.sys.common.DataGridView;
import com.lx.sys.common.ResultObj;
import com.lx.bus.vo.ProviderVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lidada
 * @since 2020-12-10
 */
@RestController
@RequestMapping("/provider")
public class ProviderController {
    @Autowired
    private IGoodsService iGoodsService;
    @Autowired
    private IProviderService iProviderService;
    /**
     * 查询
     */
    @RequestMapping("loadAllProvider")
    public DataGridView loadAllProvider(ProviderVo ProviderVo){
        /**
         * 获取分页数据
         */
        IPage<Provider> page = new Page<>(ProviderVo.getPage(),ProviderVo.getLimit());
        /**
         * 实例Provider类型的QueryWrapper对象
         */
        QueryWrapper<Provider> queryWrapper = new QueryWrapper<>();
        /**
         * 模糊查询中获取顾客用户名，电话，对接客人
         */
        queryWrapper.like(StringUtils.isNotBlank(ProviderVo.getProvidername()),"Providername",ProviderVo.getProvidername());
        queryWrapper.like(StringUtils.isNotBlank(ProviderVo.getPhone()),"phone",ProviderVo.getPhone());
        queryWrapper.like(StringUtils.isNotBlank(ProviderVo.getConnectionperson()),"connectionperson",ProviderVo.getConnectionperson());

        this.iProviderService.page(page,queryWrapper);
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 添加数据
     */
    @RequestMapping("addProvider")
    public ResultObj addProvider(ProviderVo ProviderVo){
        try {
            this.iProviderService.save(ProviderVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改数据
     */
    @RequestMapping("updateProvider")
    public ResultObj updateProvider(ProviderVo ProviderVo){
        try {
            this.iProviderService.updateById(ProviderVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除数据
     */
    @RequestMapping("deleteProvider")
    public ResultObj deleteProvider(Integer id){
        try {
            this.iProviderService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 批量删除数据
     */
    @RequestMapping("batchDeleteProvider")
    public ResultObj batchDeleteProvider(ProviderVo ProviderVo){
        try {
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : ProviderVo.getIds()) {
                idList.add(id);
            }
            this.iProviderService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 加载所有可用的供应商
     */
    @RequestMapping("loadAllProviderForSelect")
    public  DataGridView loadAllProviderForSelect(){
        QueryWrapper<Provider> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Provider> list = this.iProviderService.list(queryWrapper);
        return new DataGridView(list);
    }

}
