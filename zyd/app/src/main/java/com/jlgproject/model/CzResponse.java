package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/20.
 */

public class CzResponse implements Serializable {


    /**
     * message : 充值成功
     * state : ok
     */

    private String message;
    private String state;

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
}
