package com.lx.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.sys.commen.DataGridView;
import com.lx.sys.commen.TreeNode;
import com.lx.sys.entity.Dept;
import com.lx.sys.service.IDeptService;
import com.lx.sys.vo.DeptVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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
        queryWrapper.eq(null!=deptVo.getId(),"id",deptVo.getId())
                .or().eq(null!=deptVo.getId(),"pid",deptVo.getId());

        queryWrapper.orderByAsc("ordernum");
        this.deptService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());
    }
}
