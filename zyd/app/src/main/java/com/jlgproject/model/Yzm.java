package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/5.
 */

public class Yzm implements Serializable {


    /**
     * state : warn
     * message : 该手机号已注册，请登录
     * data : null
     */

    private String state;
    private String message;
    private Object data;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
