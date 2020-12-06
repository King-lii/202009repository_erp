package com.lx.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.sys.commen.ActiverUser;
import com.lx.sys.entity.User;
import com.lx.sys.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private IUserService userService;

    public String getName(){
        return this.getClass().getSimpleName();
    }
    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        QueryWrapper<User> querryWrapper = new QueryWrapper<User>();
        querryWrapper.eq("loginname",authenticationToken.getPrincipal().toString());
        User user = userService.getOne(querryWrapper);
        if (null != user){
            ActiverUser activerUser = new ActiverUser();
            activerUser.setUser(user);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser,user.getPwd(),ByteSource.Util.bytes(user.getSalt()),this.getName());
            return info;
        }
        return null;
    }
}
