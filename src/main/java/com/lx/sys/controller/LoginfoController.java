package com.lx.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.sys.commen.DataGridView;
import com.lx.sys.commen.ResultObj;
import com.lx.sys.entity.Loginfo;
import com.lx.sys.service.ILoginfoService;
import com.lx.sys.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2020-10-07
 */
@RestController
@RequestMapping("loginfo")
public class LoginfoController {

    @Autowired
    ILoginfoService loginfoService;

    /**
     * 全查询
     */
    @RequestMapping("loadAllLoginfo")
    public DataGridView loadAllLoginfo(LoginfoVo loginfoVo){

        IPage<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());

        QueryWrapper<Loginfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        queryWrapper.ge(null != loginfoVo.getStartTime() , "logintime",loginfoVo.getStartTime());
        queryWrapper.le(null != loginfoVo.getEndTime(),"logintime",loginfoVo.getEndTime());
        queryWrapper.orderByDesc("logintime");

        this.loginfoService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 删除
     */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(Integer id){
        try {
            this.loginfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteLoginfo")
    public ResultObj batchDeleteLoginfo(LoginfoVo loginfoVo){
        try {
            Collection<Serializable> idList = new ArrayList<>(Arrays.asList(loginfoVo.getIds()));
            this.loginfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
