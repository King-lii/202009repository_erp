package com.lx.sys.vo;

import com.lx.sys.entity.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {

    private static final long serialVersionUID = -2208928697951562147L;

    private Integer page = 1;
    private Integer limit = 10;
}
