package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/30.
 */

public class UpdataUserImage implements Serializable {

    /**
     * message : 头像上传成功
     * state : ok
     * data : http://zjzs.oss-cn-beijing.aliyuncs.com/2eda5910c40c412089dbde77c29daedc.jpg
     */

    private String message;
    private String state;
    private String data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
