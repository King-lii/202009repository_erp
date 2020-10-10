package com.lx.sys.vo;

import com.lx.sys.entity.Notice;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class NoticeVo extends Notice {

    private static final long serialVersionUID = 4055405137735274773L;

    private Integer page = 1;
    private Integer limit = 10;

    private Integer[] ids ;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}
