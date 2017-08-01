package com.jlgproject.model;

/**
 * @author 王锋 on 2017/7/11.
 */

public class UserType {


    /**
     * state : ok
     * message :
     * data : {"phoneNumber":"13552949045","userType":1}
     */

    private String state;
    private String message;
    /**
     * phoneNumber : 13552949045
     * userType : 1
     */

    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String phoneNumber;
        private int userType;

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public int getUserType() {
            return userType;
        }

        public void setUserType(int userType) {
            this.userType = userType;
        }
    }
}
