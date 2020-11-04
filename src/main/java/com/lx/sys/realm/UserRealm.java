package com.lx.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.sys.common.ActiverUser;
import com.lx.sys.entity.User;
import com.lx.sys.service.IUserService;
import lombok.val;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class UserRealm extends AuthorizingRealm {
    /**
     * 注入userservice接口
     */
    @Autowired
    private IUserService iUserService;
    /**
     * 认证重写getname方法
     * @return
     */
    @Override
    public String getName() {
        return this.getClass().getSimpleName();
    }

    /**
     * 权限授予
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    /**
     * 认证方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        /**
         * 通过getPrincipal获取认证
         */
        queryWrapper.eq("loginname",token.getPrincipal().toString());
        /**
         * 返回User对象
         */
        User user = iUserService.getOne(queryWrapper);
        if (user!=null){
            //实例组件类
            ActiverUser activerUser = new ActiverUser();
            //注入组件类
            activerUser.setUser(user);
            //从User对象获取角色的盐
            ByteSource creadenticalsSalt = ByteSource.Util.bytes(user.getSalt());
            SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(activerUser,user.getPwd(),creadenticalsSalt,this.getName());
            return info;
        }
        return null;
    }
}
