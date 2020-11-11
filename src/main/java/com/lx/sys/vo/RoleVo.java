package com.lx.sys.vo;

import com.lx.sys.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleVo extends Role {
    private static final long serialVersionUID = 1L;
    private Integer page = 1;
    private Integer limit = 10;
}
