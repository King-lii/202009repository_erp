package com.lx.sys.controller;

import com.lx.sys.common.ActiverUser;
import com.lx.sys.common.ResultObj;
import com.lx.sys.common.WebUtils;
import com.lx.sys.entity.Loginfo;
import com.lx.sys.service.ILoginfoService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private ILoginfoService iLoginfoService;

    @RequestMapping("login")
    public ResultObj login(String loginname,String pwd){
        Subject subject = SecurityUtils.getSubject();
        AuthenticationToken token = new UsernamePasswordToken(loginname,pwd);
        try {
            subject.login(token);
            ActiverUser activerUser = (ActiverUser) subject.getPrincipal();
            /**
             * 将取出来的登陆数据放入session
             */
            WebUtils.getSession().setAttribute("user",activerUser.getUser());
            /**
             * 记录登录日志,用户名，IP地址，登陆时间
             */
            Loginfo entity = new Loginfo();
            entity.setLoginname(activerUser.getUser().getName()+"_"+activerUser.getUser().getLoginname());
            entity.setLoginip(WebUtils.getHttpServletRequest().getRemoteAddr());
            entity.setLogintime(new Date());
            iLoginfoService.save(entity);
            /**
             * 记录登录日志结束
             */
            return ResultObj.LOGIN_SUCCESS;
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return ResultObj.LOGIN_SUCCESS_PASS;
        }
    }
}
