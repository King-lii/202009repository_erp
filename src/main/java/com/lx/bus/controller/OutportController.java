package com.lx.bus.controller;


import com.lx.bus.service.IOutportService;
import com.lx.sys.common.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lidada
 * @since 2020-12-13
 */
@RestController
@RequestMapping("/outport")
public class OutportController {
    @Autowired
    private IOutportService iOutportService;
    /**
     * 添加退货信息
     */
    @RequestMapping("addOutport")
    public ResultObj addOutport(Integer id,Integer number,String remark){
        try {
            this.iOutportService.addOutport(id,number,remark);
            return ResultObj.OPERATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.OPERATE_ERROR;
        }
    }
}
