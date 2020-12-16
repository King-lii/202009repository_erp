package com.lx.sys.vo;

import com.lx.sys.entity.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Data
@EqualsAndHashCode(callSuper = false)
public class LoginfoVo extends Loginfo {
    private static final long serialVersionUID = 1L;
    /**
     * 设置分页的初始值和页数
     */
    private Integer page=1;
    private Integer limit=10;
    /**
     *设置多查询参数
     */
    private Integer[] ids;
    /**
     * 加入验证码变量
     */
    private String code;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
