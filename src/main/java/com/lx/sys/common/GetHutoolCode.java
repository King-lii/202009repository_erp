package com.lx.sys.common;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.lang.Console;

public class GetHutoolCode {
    public static void main(String[] args) {
        //定义图形验证码的长和宽
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36,4,5);
        //图形验证码写出，可以写出到文件，也可以写出到流
        System.out.println(lineCaptcha.getCode());
    }
}
