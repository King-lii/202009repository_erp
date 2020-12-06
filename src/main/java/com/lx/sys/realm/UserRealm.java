package com.lx.sys.realm;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lx.sys.commen.ActiverUser;
import com.lx.sys.commen.Constast;
import com.lx.sys.entity.Permission;
import com.lx.sys.entity.User;
import com.lx.sys.mapper.RoleMapper;
import com.lx.sys.service.IPermissionService;
import com.lx.sys.service.IRoleService;
import com.lx.sys.service.IUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    private IUserService userService;
    @Autowired
    @Lazy
    private IPermissionService permissionService;
    @Autowired
    @Lazy
    private IRoleService roleService;
    public String getName(){
        return this.getClass().getSimpleName();
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

            //查询所有权限
            QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
            //设置只能查询菜单
            queryWrapper.eq("type", Constast.TYPE_PERMISSION);
            queryWrapper.eq("available",Constast.AVAILABLE_TRUE);
            /**
             * 根据用户ID+角色+权限去查询
             */
            Integer userId = user.getId();

            List<Integer> currentUserRoleIds = roleService.queryUserRoleIdsByUid(userId);
            Set<Integer> pids = new HashSet<>();
            for (Integer rid:currentUserRoleIds
            ) {
                List<Integer> permissionIds = roleService.queryRolePermissionIdsByRid(rid);
                pids.addAll(permissionIds);
            }
            List<Permission> list = new ArrayList<>();
            if (pids.size()>0){
                queryWrapper.in("id",pids);
                list = permissionService.list(queryWrapper);
            }
            List<String> percodes = new ArrayList<>();
            for (Permission permission : list){
                percodes.add(permission.getPercode());
            }
            activerUser.setPermissions(percodes);
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activerUser,user.getPwd(),ByteSource.Util.bytes(user.getSalt()),this.getName());
            return info;
        }
        return null;
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiverUser activerUser = (ActiverUser) principalCollection.getPrimaryPrincipal();
        User user = activerUser.getUser();
        List<String> permission = activerUser.getPermissions();
        if (user.getType() == Constast.USER_TYPE_SUPER){
            authorizationInfo.addStringPermission("*.*");
        }else {
            if (null!=permission && permission.size()>0){
                authorizationInfo.addStringPermissions(permission);
            }
        }
        return authorizationInfo;
    }
}
