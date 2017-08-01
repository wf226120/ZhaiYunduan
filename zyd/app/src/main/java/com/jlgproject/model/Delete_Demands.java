package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/27.
 */

public class Delete_Demands implements Serializable {

    /**
     * state : ok
     * message :
     * data : true
     */

    private String state;
    private String message;
    private boolean data;

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

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }
}
