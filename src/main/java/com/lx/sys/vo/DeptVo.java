package com.lx.sys.vo;

import com.lx.sys.entity.Dept;
import com.lx.sys.entity.Loginfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class DeptVo extends Dept {

    private static final long serialVersionUID = 4055405137735274773L;

    private Integer page = 1;
    private Integer limit = 10;
}
