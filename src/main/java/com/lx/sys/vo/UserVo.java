package com.lx.sys.vo;

import com.lx.sys.entity.Loginfo;
import com.lx.sys.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class UserVo extends User {
    private static final long serialVersionUID = 1L;
    /**
     * 设置分页的初始值和页数
     */
    private Integer page=1;
    private Integer limit=10;

    private String code;
}
