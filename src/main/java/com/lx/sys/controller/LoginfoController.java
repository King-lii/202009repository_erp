package com.lx.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.sys.common.DataGridView;
import com.lx.sys.common.ResultObj;
import com.lx.sys.entity.Loginfo;
import com.lx.sys.service.ILoginfoService;
import com.lx.vo.LoginfoVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lidada
 * @since 2020-11-11
 */
@RestController
@RequestMapping("/loginfo")
public class LoginfoController {

    @Autowired
    private ILoginfoService iLoginfoService;

    /**
     * 全查询
     * @param loginfoVo
     * @return
     */
    @RequestMapping("loadAllLoginfo")
    public DataGridView loadAllLoginfo(LoginfoVo loginfoVo){
        IPage<Loginfo> page = new Page<>(loginfoVo.getPage(),loginfoVo.getLimit());
        /**
         * 实例化QueryWrapper对象进行数据库操作
         */
        QueryWrapper<Loginfo> queryWrapper=new QueryWrapper<>();
        /**
         * 对数据库进行操作，通过
         */
        /** 1.
         * StringUtils.isNotBlank();
         * 判断参数是否不为空.
         * 1.如果不为空返回true。
         * 2.如果为空返回false。
         * StringUtils.isNotEmpty(null)  -> false
         * StringUtils.isNotEmpty("")  -> false
         * StringUtils.isNotEmpty("a") -> true
         * StringUtils.isNotEmpty(" ") -> true
         */
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginname()),"loginname",loginfoVo.getLoginname());
        queryWrapper.like(StringUtils.isNotBlank(loginfoVo.getLoginip()),"loginip",loginfoVo.getLoginip());
        queryWrapper.ge(loginfoVo.getStartTime()!=null,"logintime",loginfoVo.getStartTime());
        queryWrapper.le(loginfoVo.getEndTime()!=null,"logintime",loginfoVo.getEndTime());
        /**
         * 对查询数据按照logintime字段降序
         */
        queryWrapper.orderByDesc("logintime");
        /**
         * 将分页信息放入page
         */
        this.iLoginfoService.page(page,queryWrapper);
        /**
         * 实例化一个分页总行数和分耶记录表的自定义数据类型
         */
        return new DataGridView(page.getTotal(),page.getRecords());

    }

    /**
     * 单个通过id删除变量
     * @param id
     * @return
     */
    @RequestMapping("deleteLoginfo")
    public ResultObj deleteLoginfo(Integer id){
        try {
            this.iLoginfoService.removeById(id);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    @RequestMapping("batchDeleteLoginfo")
    public ResultObj deleteLoginfo(LoginfoVo LoginfoVo){
        try {
            /**
             * 将数据转换为list列表，用于查询
             */
            Collection<Serializable> idList = new ArrayList<Serializable>();
            for (Integer id : LoginfoVo.getIds()) {
                idList.add(id);
            }
            this.iLoginfoService.removeByIds(idList);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
