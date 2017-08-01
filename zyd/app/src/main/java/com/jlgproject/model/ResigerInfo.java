package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/5.
 */

public class ResigerInfo implements Serializable{

    private String realname;
    private String mobile;
    private String validcode;
    private String password;
    private String repassword;
    private String recommendCode;


    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public ResigerInfo(String name,  String mobile, String validcode, String password, String repassword, String recommendCode) {
        this.realname = name;

        this.mobile = mobile;
        this.validcode = validcode;
        this.password = password;
        this.repassword = repassword;
        this.recommendCode=recommendCode;

    }

    public String getName() {
        return realname;
    }

    public void setName(String name) {
        this.realname = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getValidcode() {
        return validcode;
    }

    public void setValidcode(String validcode) {
        this.validcode = validcode;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }
}
