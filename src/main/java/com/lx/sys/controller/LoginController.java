package com.lx.sys.controller;

import com.lx.sys.commen.ActiverUser;
import com.lx.sys.commen.ResultObj;
import com.lx.sys.commen.WebUtils;
import com.lx.sys.entity.Loginfo;
import com.lx.sys.service.ILoginfoService;
import io.swagger.annotations.Api;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    ILoginfoService loginfoService;

    @RequestMapping("login")
    public ResultObj login(String loginname , String pwd){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(loginname,pwd);
        try {
            subject.login(token);
            ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
            WebUtils.getSession().setAttribute("user",activerUser.getUser());

            //登陆日志记录
            /**
             * 遇到了request.getRemoteAddr()获取的值为0:0:0:0:0:0:0:1
             * 这种情况只有在服务器和客户端都在同一台电脑上才会出现
             * host文件
             * 注释掉文件中的 # ::1 localhost 这一行即可解决问题
             * 如果还不能解决，本机访问的时候用127.0.0.1或本机ip代替localhost即可解决
             *
             *
             * 时差问题
             * yml文件
             * time-zone: GMT+8
             */
            Loginfo entity = new Loginfo();
            entity.setLoginname(activerUser.getUser().getName()+"-"+activerUser.getUser().getLoginname());
            entity.setLoginip(WebUtils.getRequest().getRemoteAddr());
            entity.setLogintime(new Date());
            this.loginfoService.save(entity);

            return ResultObj.LOGIN_SUCCESS;
        }catch (AuthenticationException e){
            e.printStackTrace();
            return ResultObj.LOGIN_ERROR_PASS;
        }

    }
}
