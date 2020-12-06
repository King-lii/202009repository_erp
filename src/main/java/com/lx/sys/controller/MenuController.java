package com.lx.sys.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lx.sys.commen.*;
import com.lx.sys.entity.Permission;
import com.lx.sys.entity.User;
import com.lx.sys.service.IPermissionService;
import com.lx.sys.service.IRoleService;
import com.lx.sys.service.IUserService;
import com.lx.sys.vo.PermissionVo;
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
 * @since 2020-10-06
 */
@RestController
@RequestMapping("menu")
public class MenuController {

    @Autowired
    private IPermissionService permissionService;
    @Autowired
    private IRoleService roleService;

    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(){

        //查询所有菜单
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        //设置只能查询菜单
        queryWrapper.eq("type", Constast.TYPE_MENU);
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);

        User user = (User) WebUtils.getSession().getAttribute("user");
        List<Permission> list = null;
        if (user.getType().equals(Constast.USER_TYPE_SUPER)){
            list = permissionService.list(queryWrapper);
        }else {
            /**
             * 根据用户ID+角色+权限去查询
             */
            Integer userId = user.getId();
            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);
            Set<Integer> pids = new HashSet<>();
            for (Integer rid:currentUserRoleIds
                 ) {
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            if (pids.size()>0){
                queryWrapper.in("id",pids);
                list = permissionService.list(queryWrapper);
            }else {
                list = new ArrayList<>();
            }
        }
        List<TreeNode> treeNodes = new ArrayList<>();
        assert list != null;
        for (Permission permission : list) {
            treeNodes.add(new TreeNode(
                    permission.getId()
                    ,permission.getPid()
                    ,permission.getTitle()
                    ,permission.getIcon()
                    ,permission.getHref()
                    , permission.getOpen().equals(Constast.OPEN_TRUE)));
        }
        List<TreeNode> list2 = TreeNodeBuilder.build(treeNodes , 1);
        return new DataGridView(list2);
    }


    /**
     * 加载菜单管理左边的json
     * @return
     */
    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(PermissionVo permissionVo){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("type",Constast.TYPE_MENU);
        List<Permission> list = this.permissionService.list(queryWrapper);
        List<TreeNode> treeNodes = new ArrayList<>();
        for (Permission permission : list) {
            treeNodes.add(new TreeNode(permission.getId(),permission.getPid(),permission.getTitle(),permission.getOpen()==1));
        }
        return new DataGridView(treeNodes);
    }

    /**
     * 加载菜单管理
     * @param permissionVo
     * @return
     */
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(PermissionVo permissionVo){
        IPage<Permission> page = new Page<>(permissionVo.getPage(),permissionVo.getLimit());

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(null!=permissionVo.getId(),"id",permissionVo.getId()).or().eq(null!=permissionVo.getId(),"pid",permissionVo.getId());
        queryWrapper.eq("type", Constast.TYPE_MENU);
        queryWrapper.like(StringUtils.isNotBlank(permissionVo.getTitle()),"title",permissionVo.getTitle());

        queryWrapper.orderByAsc("ordernum");
        this.permissionService.page(page,queryWrapper);

        return new DataGridView(page.getTotal(),page.getRecords());
    }

    /**
     * 加载最大排序码
     * @return
     */
    @GetMapping("loadMenuMaxOrderNum")
    public Map<String,Object> loadMenuMaxOrderNum(){
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Permission> queryWrapper = new QueryWrapper();
//        queryWrapper.eq("type", Constast.TYPE_MENU);
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
     * 添加菜单
     * @param permissionVo
     * @return
     */
    @RequestMapping("addMenu")
    public ResultObj addMenu(PermissionVo permissionVo){
        try {
            permissionVo.setType(Constast.TYPE_MENU);
            this.permissionService.save(permissionVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }
    /**
     * 修改菜单
     * @param permissionVo
     * @return
     */
    @RequestMapping("updateMenu")
    public ResultObj updateMenu(PermissionVo permissionVo){
        try {
            this.permissionService.updateById(permissionVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }
    /**
     * 删除菜单
     * @param permissionVo
     * @return
     */
    @RequestMapping("deleteMenu")
    public ResultObj deleteMenu(PermissionVo permissionVo){
        try {
            this.permissionService.removeById(permissionVo);
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
    /**
     * 检查是否包含子节点
     * @param permissionVo
     * @return
     */
    @RequestMapping("checkMenuHasChildrenNode")
    public Map checkMenuHasChildrenNode(PermissionVo permissionVo){
        Map<String, Object> map = new HashMap<>();

        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("type", Constast.TYPE_MENU);
        queryWrapper.eq(null != permissionVo,"pid",permissionVo.getId());
        List<Permission> list = this.permissionService.list(queryWrapper);
        if (list.size()>0){
            map.put("value",true);
        }else {
            map.put("value",false);
        }
        return map;
    }
}
