package com.lx.sys.controller;



import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.sys.commen.*;
import com.lx.sys.entity.Permission;
import com.lx.sys.entity.User;
import com.lx.sys.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import static com.lx.sys.commen.Constast.TYPE_MENU;

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

    @RequestMapping("loadIndexLeftMenuJson")
    public DataGridView loadIndexLeftMenuJson(){

        //查询所有菜单
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        //设置只能查询菜单
        queryWrapper.eq("type", TYPE_MENU);
        queryWrapper.eq("available",Constast.AVAILABLE_TRUE);

        User user = (User) WebUtils.getSession().getAttribute("user");
        List<Permission> list = null;
        if (user.getType().equals(Constast.USER_TYPE_SUPER)){
            list = permissionService.list(queryWrapper);
        }else if (user.getType().equals(Constast.USER_TYPE_NORMAL)){
            /**
             * 根据用户ID+角色+权限去查询
             */
            list = permissionService.list(queryWrapper);
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
}
