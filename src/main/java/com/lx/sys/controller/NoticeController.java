package com.lx.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.sys.commen.DataGridView;
import com.lx.sys.commen.ResultObj;
import com.lx.sys.commen.WebUtils;
import com.lx.sys.entity.Notice;
import com.lx.sys.entity.User;
import com.lx.sys.service.INoticeService;
import com.lx.sys.vo.NoticeVo;
import com.sun.deploy.nativesandbox.comm.Request;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2020-10-09
 */
@RestController
@RequestMapping("notice")
public class NoticeController {


    @Autowired
    INoticeService noticeService;

    /**
     * 查询
     */
    @RequestMapping("loadAllNotice")
    public DataGridView loadAllNotice(NoticeVo noticeVo){

        IPage<Notice> page = new Page<>(noticeVo.getPage(),noticeVo.getLimit());
        QueryWrapper<Notice> queryWrapper = new QueryWrapper<>();


        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getTitle()),"title",noticeVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(noticeVo.getOpername()),"opername",noticeVo.getOpername());
        queryWrapper.ge(null != noticeVo.getStartTime(),"createtime",noticeVo.getStartTime());
        queryWrapper.le(null != noticeVo.getEndTime(),"createtime",noticeVo.getEndTime());
        queryWrapper.orderByDesc("createtime");
        this.noticeService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 添加
     */
    @RequestMapping("addNotice")
    public ResultObj addNotice(NoticeVo noticeVo){
        try {
            noticeVo.setCreatetime(new Date());
            User user = (User) WebUtils.getSession().getAttribute("user");
            noticeVo.setOpername(user.getLoginname());
            this.noticeService.save(noticeVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改
     */
    @RequestMapping("updateNotice")
    public ResultObj updateNotice(NoticeVo noticeVo){
        try {
            this.noticeService.updateById(noticeVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除
     */
    @RequestMapping("deleteNotice")
    public ResultObj deleteNotice(Integer id){
        try {
            this.noticeService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除
     */
    @RequestMapping("batchDeleteNotice")
    public ResultObj batchDeleteLoginfo(NoticeVo noticeVo){
        try {
            Collection<Serializable> idList = new ArrayList<>(Arrays.asList(noticeVo.getIds()));
            this.noticeService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
