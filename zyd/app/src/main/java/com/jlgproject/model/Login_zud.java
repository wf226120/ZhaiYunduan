package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/5/23.
 */

public class Login_zud implements Serializable {


    /**
     * message : 登陆成功
     * state : ok
     * data : {"token":"xxx","type":1}
     */

    private String message;
    private String state;
    /**
     * token : xxx
     * type : 1
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        private String token;
        private int type;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
