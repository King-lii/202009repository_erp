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
    /**
     * 跳转到供应商管理
     */
    @RequestMapping("toProviderManager")
    public String toProviderManager(){
        return "/business/provider/providerManager.html";
    }
    /**
     * 跳转到商品管理
     */
    @RequestMapping("toGoodsManager")
    public String toGoodsManager(){
        return "/business/goods/goodsManager.html";
    }

    /**
     * 跳转到商品进货管理
     */
    @RequestMapping("toInportManager")
    public String toInportManager(){
        return "/business/inport/inportManager.html";
    }

    /**
     * 跳转到商品退货管理
     */
    @RequestMapping("toOutportManager")
    public String toOutportManager(){
        return "/business/outport/outportManager.html";
    }
    /**
     * 跳转到商品销售管理
     */
    @RequestMapping("toSalesManager")
    public String toSalesManager(){
        return "/business/sales/salesManager.html";
    }

    /**
     * 跳转到商品销售退货管理
     */
    @RequestMapping("toSalesbackManager")
    public String toSalesbackManager(){
        return "/business/salesback/salesbackManager.html";
    }

}
