package com.lx.sys.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.sys.commen.Constast;
import com.lx.sys.commen.DataGridView;
import com.lx.sys.commen.ResultObj;
import com.lx.sys.commen.TreeNode;
import com.lx.sys.entity.Permission;
import com.lx.sys.service.IPermissionService;
import com.lx.sys.vo.PermissionVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lx
 * @since 2020-10-06
 */
@RestController
@RequestMapping("permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    /**
     * 加载权限管理左边的json
     * @return
     */
    @RequestMapping("loadPermissionManagerLeftTreeJson")
    public DataGridView loadPermissionManagerLeftTreeJson(PermissionVo permissionVo){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type", Constast.TYPE_MENU);
        List<Permission> list = this.permissionService.list(queryWrapper);
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission : list) {
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),permission.getOpen()==1));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 加载权限管理
     * @param permissionVo
     * @return
     */
    @RequestMapping("loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo){
        IPage<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit());

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constast.TYPE_PERMISSION);
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());
        queryWrapper.eq(null!=permissionVo.getId(),"pid",permissionVo.getId());

        queryWrapper.orderByAsc("ordernum");
        this.permissionService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 加载最大排序码
     * @return
     */
    @GetMapping("loadPermissionMaxOrderNum")
    public Map<String,Object> loadPermissionMaxOrderNum(){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper();
        queryWrapper.orderByDesc("ordernum");
        List<Permission> list = this.permissionService.list(queryWrapper);
        if (list.size()>0){
            map.put("value",list.get(0).getOrdernum()+1);
        }else {
            map.put("value",1);
        }
        return map;
    }

    /**
     * 添加权限
     * @param permissionVo
     * @return
     */
    @RequestMapping("addPermission")
    public ResultObj addPermission(PermissionVo permissionVo){
        try {
            permissionVo.setType(Constast.TYPE_PERMISSION);
            this.permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改权限
     * @param permissionVo
     * @return
     */
    @RequestMapping("updatePermission")
    public ResultObj updatePermission(PermissionVo permissionVo){
        try {
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除权限
     * @param permissionVo
     * @return
     */
    @RequestMapping("deletePermission")
    public ResultObj deletePermission(PermissionVo permissionVo){
        try {
            this.permissionService.removeById(permissionVo);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}
