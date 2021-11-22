package com.heqing.shiro.config;

import com.alibaba.fastjson.JSONObject;
import com.heqing.shiro.model.Account;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * shiro 核心处理类
 * @author heqing
 * @date 2021/11/22 13:35
 */
@Component
public class CustomizeShiroRealm extends AuthorizingRealm {

    /**
     * 角色权限和对应权限添加
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals.getPrimaryPrincipal() == null) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        Account account = (Account)principals.getPrimaryPrincipal();
        System.out.println("权限 --> " + JSONObject.toJSONString(account));

        //用户权限列表
        Set<String> permsSet = getPermissions(account);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.setStringPermissions(permsSet);
        return info;
    }

    /**
     * 认证(登录时调用)
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        // 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (token.getPrincipal() == null) {
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }
        System.out.println("登录 --> " + JSONObject.toJSONString(token));

        String userName = token.getPrincipal().toString();
        String password = new String((char[]) token.getCredentials());

        //查询用户信息
        Account user = geUserByName(userName);
        //账号不存在
        if(user == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        //密码错误
        if(!password.equals(user.getPassword())) {
            throw new IncorrectCredentialsException("账号或密码不正确");
        }
        //账号锁定
        if(user.getStatus() == 0) {
            throw new LockedAccountException("账号已被锁定,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, password, getName());
        return info;
    }

    /**
     * 模拟一个用户账号。正常使用中，这里会从数据库获取
     * @param userName
     * @return
     */
    private Account geUserByName(String userName) {
        Account account = new Account();
        account.setUserName("admin");
        account.setPassword("admin");
        account.setStatus(1);
        return account;
    }

    /**
     * 模拟获取一个账号对应的账号权限。正常使用中，这里会从数据库获取
     * @param account
     * @return
     */
    private Set<String> getPermissions(Account account) {
        Set<String> permsSet = new HashSet<>();
        permsSet.add("sys:demo:study");
        return permsSet;
    }
}
