package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/6.
 */

public class Debt_Query implements Serializable{


    /**
     * message : 查找成功
     * state : ok
     * data : {"id":1255,"name":"了解了"}
     */

    private String message;
    private String state;
    /**
     * id : 1255
     * name : 了解了
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

    public static class DataBean {
        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
