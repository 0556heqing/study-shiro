package com.heqing.shiro.model;

import java.io.Serializable;

/**
 * @author heqing
 * @date 2021/11/22 15:33
 */
public class Account implements Serializable {

    private String userName;
    private String password;
    /**
     * 状态  0：禁用   1：正常
     */
    private Integer status;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
