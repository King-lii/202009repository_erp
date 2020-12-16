package com.lx.sys.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.lx.sys.commen.ActiverUser;
import com.lx.sys.common.ResultObj;
import com.lx.sys.common.WebUtils;
import com.lx.sys.entity.Loginfo;
import com.lx.sys.service.ILoginfoService;
import com.lx.sys.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;


@RestController
@RequestMapping("login")
public class LoginController {
    @Autowired
    private ILoginfoService iLoginfoService;

    @RequestMapping("login")
    public ResultObj login(UserVo userVo,  String code,HttpSession session){
        String servicecode = (String) session.getAttribute("code");

        if (code.equals(servicecode)){

            Subject subject = SecurityUtils.getSubject();
            AuthenticationToken token = new UsernamePasswordToken(userVo.getLoginname(),userVo.getPwd());
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

        }else{
            return ResultObj.LOGIN_ERROR_CODE;
        }


    }


    /**
     * 得到验证码并返回给登录页面
     */
    @RequestMapping("getCode")
    public  void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,5);
        session.setAttribute("code",lineCaptcha.getCode());


        ServletOutputStream outputStream = response.getOutputStream();
        //输出图片流
        ImageIO.write(lineCaptcha.getImage(),"JPEG",outputStream);
        //关闭流
        outputStream.close();
    }
}
