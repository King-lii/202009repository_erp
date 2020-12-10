package com.lx.bus.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  业务管理的路由器
 * </p>
 *
 * @author lidada
 * @since 2020-12-07
 */
@Controller
@RequestMapping("/bus")
public class BusinessController {
/**
 * 跳转到客户管理
 */
@RequestMapping("toCustomerManager")
public String toCustomerManager(){
    return "/business/customer/customerManager.html";
}
}
