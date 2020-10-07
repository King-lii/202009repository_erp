package com.lx.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.sys.commen.DataGridView;
import com.lx.sys.entity.Loginfo;
import com.lx.sys.service.ILoginfoService;
import com.lx.sys.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.management.Query;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2020-10-07
 */
@RestController
@RequestMapping("/loginfo")
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
}
