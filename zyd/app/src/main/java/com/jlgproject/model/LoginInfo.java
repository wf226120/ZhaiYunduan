package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/5/23.
 */

public class LoginInfo implements Serializable {

    private String username;
    private String password;
    private String validCode;

    public LoginInfo(String username, String password, String validCode) {
        this.username = username;
        this.password = password;
        this.validCode = validCode;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getValidCode() {
        return validCode;
    }

    public void setValidCode(String validCode) {
        this.validCode = validCode;
    }
}
