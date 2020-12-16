package com.lx.sys.vo;

import com.lx.sys.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserVo extends User {
    private static final long serialVersionUID = -6491499461276457281L;
    private Integer page = 1;
    private Integer limit = 10;

    private String code;

}
