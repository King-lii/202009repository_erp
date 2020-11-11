package com.lx.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.sys.commen.DataGridView;
import com.lx.sys.commen.ResultObj;
import com.lx.sys.commen.TreeNode;
import com.lx.sys.entity.Dept;
import com.lx.sys.service.IDeptService;
import com.lx.sys.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2020-10-17
 */
@RestController
@RequestMapping("dept")
public class DeptController {

    @Autowired
    private IDeptService deptService;

    /**
     * 加载部门管理左边的json
     * @return
     */
    @RequestMapping("loadDeptManagerLeftTreeJson")
    public DataGridView loadDeptManagerLeftTreeJson(DeptVo deptVo){
        List<Dept> list = this.deptService.list();

        List<TreeNode> treeNodes = new ArrayList<>();
        for (Dept dept : list) {
            treeNodes.add(new TreeNode(dept.getId(),dept.getPid(),dept.getTitle(),dept.getOpen()==1));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 加载部门管理
     * @param deptVo
     * @return
     */
    @RequestMapping("loadAllDept")
    public DataGridView loadAllDept(DeptVo deptVo){
        IPage<Dept> page = new Page<>(deptVo.getPage(),deptVo.getLimit());

        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();

        queryWrapper.like(StringUtils.isNotBlank(deptVo.getTitle()),"title",deptVo.getTitle());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getAddress()),"address",deptVo.getAddress());
        queryWrapper.like(StringUtils.isNotBlank(deptVo.getRemark()),"remark",deptVo.getRemark());
        queryWrapper.eq(null!=deptVo.getId(),"id",deptVo.getId()).or().eq(null!=deptVo.getId(),"pid",deptVo.getId());

        queryWrapper.orderByAsc("ordernum");
        this.deptService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 加载最大排序码
     * @return
     */
    @GetMapping("loadDeptMaxOrderNum")
    public Map<String,Object> loadDeptMaxOrderNum(){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Dept> wrapper = new QueryWrapper();
        wrapper.orderByDesc("ordernum");
        List<Dept> list = this.deptService.list(wrapper);
        if (list.size()>0){
            map.put("value",list.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;
    }

    /**
     * 添加部门
     * @param deptVo
     * @return
     */
    @RequestMapping("addDept")
    public ResultObj addDept(DeptVo deptVo){
        try {
            deptVo.setCreatetime(new Date());
            this.deptService.save(deptVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改部门
     * @param deptVo
     * @return
     */
    @RequestMapping("updateDept")
    public ResultObj updateDept(DeptVo deptVo){
        try {
            this.deptService.updateById(deptVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除部门
     * @param deptVo
     * @return
     */
    @RequestMapping("deleteDept")
    public ResultObj deleteDept(DeptVo deptVo){
        try {
            this.deptService.removeById(deptVo);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 检查是否包含子节点
     * @param deptVo
     * @return
     */
    @RequestMapping("checkDeptHasChildrenNode")
    public Map checkDeptHasChildrenNode(DeptVo deptVo){
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Dept> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(null != deptVo,"pid",deptVo.getId());
        List<Dept> list = this.deptService.list(queryWrapper);
        if (list.size()>0){
            map.put("value",true);
        }else {
            map.put("value",false);
        }
        return map;
    }
}
