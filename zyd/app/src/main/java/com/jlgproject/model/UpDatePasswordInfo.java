package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/5.
 */

public class UpDatePasswordInfo implements Serializable {

    private String mobile;
    private String code;
    private String newPwd;

    public UpDatePasswordInfo(String mobile, String code, String newPwd) {
        this.mobile = mobile;
        this.code = code;
        this.newPwd = newPwd;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }
}
