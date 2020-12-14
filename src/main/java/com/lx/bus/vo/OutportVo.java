package com.lx.bus.vo;

import com.lx.bus.entity.Outport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false) //生成equals(Object other) 和 hashCode()方法
public class OutportVo extends Outport {
    /**
     * 序列化方便数据传递，相当于加密解密
     */
    private static final long serialVersionUID = 1L;
    /**
     * 设置分页的初始值和页数
     */
    private Integer page=1;
    private Integer limit=10;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}
