package com.lx.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 路由
 */
@Controller
@RequestMapping("sys")
public class SystemController {
    /**
     * 跳转到登录页面
     */
    @RequestMapping("toLogin")
    public String toLogin(){
        return "system/index/login";
    }

    /**
     * 跳转到首页
     */
    @RequestMapping("index")
    public String  index(){
        return "system/index/index";
    }
    /**
     * 跳转到工作台
     */
    @RequestMapping("toDeskManager")
    public String  toDeskManager(){
        return "system/index/deskManager";
    }
    /**
     * 跳转到日志管理
     */
    @RequestMapping("toLoginfoManager")
    public String  toLoginfoManager(){
        return "system/loginfo/loginfoManager";
    }
    /**
     * 跳转到公告
     */
    @RequestMapping("toNoticeManager")
    public String  toNoticeManager(){
        return "system/notice/noticeManager";
    }
    /**
     * 跳转到部门管理
     */
    @RequestMapping("toDeptManager")
    public String  toDeptManager(){
        return "system/dept/deptManager";
    }
    /**
     * 跳转到部门管理左边
     * @return
     */
    @RequestMapping("toDeptLeft")
    public String toDeptLeft(){
        return "system/dept/deptLeft";
    }
    /**
     * 跳转到部门管理右边
     * @return
     */
    @RequestMapping("toDeptRight")
    public String toDeptRight(){
        return "system/dept/deptRight";
    }
}
