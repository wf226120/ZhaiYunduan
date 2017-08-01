package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/28.
 */

public class Add_Buness_Informaton implements Serializable {

    /**
     * state : ok
     * message :
     * data : 1735
     */

    private String state;
    private String message;
    private int data;

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

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
