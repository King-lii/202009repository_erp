package com.lx.sys.common;

import com.lx.sys.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data//getter和setter方法
@AllArgsConstructor//有参构造方法
@NoArgsConstructor//无参构造方法
public class ActiverUser {
    private User user;
    private List<String> roles;
    private List<String> permissions;
}
