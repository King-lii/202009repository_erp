package com.lx.bus.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.bus.entity.Goods;
import com.lx.bus.entity.Provider;
import com.lx.bus.service.IGoodsService;
import com.lx.bus.service.IProviderService;
import com.lx.bus.vo.GoodsVo;
import com.lx.sys.common.AppFileUtils;
import com.lx.sys.common.Constast;
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
 * @since 2020-12-11
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private IGoodsService iGoodsService;

    @Autowired
    private IProviderService iProviderService;
    /**
     * 查询
     */
    @RequestMapping("loadAllGoods")
    public DataGridView loadAllGoods(GoodsVo goodsVo){
        /**
         * 获取分页数据
         */
        IPage<Goods> page = new Page<>(goodsVo.getPage(),goodsVo.getLimit());
        /**
         * 实例Goods类型的QueryWrapper对象
         */
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        /**
         * 模糊查询中获取顾客货物id，电话，对接客人
         */
        queryWrapper.eq(goodsVo.getProviderid()!=null&&goodsVo.getProviderid()!=0, "providerid",goodsVo.getProviderid());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getGoodsname()),"goodsname",goodsVo.getGoodsname());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getProductcode()),"productcode",goodsVo.getProductcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getPromitcode()),"promitcode",goodsVo.getPromitcode());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getDescription()),"description",goodsVo.getDescription());
        queryWrapper.like(StringUtils.isNotBlank(goodsVo.getSize()),"size",goodsVo.getSize());
        /**
         * 获取provider中供应商名称
         */
        this.iGoodsService.page(page,queryWrapper);
        List<Goods> records = page.getRecords();
        for (Goods goods : records) {
            Provider provider = this.iProviderService.getById(goods.getProviderid());
            if (null != provider){
                goods.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(page.getTotal(),page.getRecords());
    }
    /**
     * 添加数据
     */
    @RequestMapping("addGoods")
    public ResultObj addGoods(GoodsVo GoodsVo){
        try {
            if (GoodsVo.getGoodsimg()!=null&&GoodsVo.getGoodsimg().endsWith("_temp")){
                //修改名字，返回新名字
                String newName = AppFileUtils.renameFile(GoodsVo.getGoodsimg());
                GoodsVo.setGoodsimg(newName);
            }
            this.iGoodsService.save(GoodsVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改数据
     */
    @RequestMapping("updateGoods")
    public ResultObj updateGoods(GoodsVo GoodsVo){
        try {
            //说明图片不是默认图片
            if (!(GoodsVo.getGoodsimg()!=null&&GoodsVo.getGoodsimg().equals(Constast.IMAGES_DEFAULTGOODSIMG_PNG))){
                if (GoodsVo.getGoodsimg().endsWith("_temp")){
                    String newName = AppFileUtils.renameFile(GoodsVo.getGoodsimg());
                    GoodsVo.setGoodsimg(newName);
                    //删除原先的图片
                    String oldPath = this.iGoodsService.getById(GoodsVo.getId()).getGoodsimg();
                    //如果不是默认地址那么删除
                    if(!oldPath.equals(Constast.IMAGES_DEFAULTGOODSIMG_PNG)){
                        AppFileUtils.removeFileByPath(oldPath);
                    }
                }
            }

            this.iGoodsService.updateById(GoodsVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除数据
     */
    @RequestMapping("deleteGoods")
    public ResultObj deleteGoods(Integer id,String goodsimg){
        try {
            //删除原文件
            AppFileUtils.removeFileByPath(goodsimg);
            //删除数据库数据
            this.iGoodsService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 加载  查询栏   所有可用的商品
     */
    @RequestMapping("loadAllGoodsForSelect")
    public  DataGridView loadAllProviderForSelect(){
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        List<Goods> list = this.iGoodsService.list(queryWrapper);
        //封装供应商名字
        for (Goods goods : list) {
            Provider provider = this.iProviderService.getById(goods.getProviderid());
            if (null != provider){
                goods.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(list);
    }

    /**
     * 弹出层    根据供应商ID查询商品信息
     */
    @RequestMapping("loadGoodsByProviderId")
    public  DataGridView loadGoodsByProviderId(Integer providerid){
        QueryWrapper<Goods> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("available", Constast.AVAILABLE_TRUE);
        queryWrapper.eq(providerid!=null, "providerid",providerid);
        List<Goods> list = this.iGoodsService.list(queryWrapper);
        //封装供应商名字
        for (Goods goods : list) {
            Provider provider = this.iProviderService.getById(goods.getProviderid());
            if (null != provider){
                goods.setProvidername(provider.getProvidername());
            }
        }
        return new DataGridView(list);
    }
}
