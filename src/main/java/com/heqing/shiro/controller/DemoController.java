package com.heqing.shiro.controller;

import com.alibaba.fastjson.JSONObject;
import com.heqing.shiro.utils.ShiroUtil;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author heqing
 * @date 2021/11/22 11:30
 */
@RestController
@RequestMapping("/study")
public class DemoController {

    @GetMapping("/shiro")
    @RequiresPermissions("sys:demo:study")
    public String shiro(){
        return "hello shiro";
    }

    @GetMapping("/heqing")
    @RequiresPermissions("sys:demo:heqing")
    public String heqing(){
        return "hello heqing";
    }

    @GetMapping("/login")
    public String login(@RequestParam(value="type", defaultValue="2") int type){
        if(type == 1) {
            UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
            ShiroUtil.login(token);
            return "开始请求登陆";
        } else {
            return "对不起，您还没有登陆，请先登陆";
        }
    }

    @GetMapping("/index")
    public String index(){
        return "恭喜，您已登陆成功了! --> " + JSONObject.toJSONString(ShiroUtil.getUserEntity());
    }

    @GetMapping("/logout")
    public String logout(){
        ShiroUtil.logout();
        return "退出登陆了";
    }

    @GetMapping("/unauthorized")
    public String unauthorized(){
        return "对不起，您没有访问权限";
    }
}
