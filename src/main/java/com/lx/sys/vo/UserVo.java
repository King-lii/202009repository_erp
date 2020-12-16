package com.lx.sys.vo;

<<<<<<< HEAD
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
=======
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
>>>>>>> origin/dev-lidada
}
