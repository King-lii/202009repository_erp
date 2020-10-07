package com.lx.sys.controller;


import com.lx.sys.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public St
}
