package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/14.
 */

public class Open_Debts implements Serializable {


    /**
     * state : ok
     * message : 已录入信息，请去支付
     * data : {"payAmount":"0.02","openOrderId":"20170710c4728f45dddb465b810d5cba3ec86b99"}
     */

    private String state;
    private String message;
    /**
     * payAmount : 0.02
     * openOrderId : 20170710c4728f45dddb465b810d5cba3ec86b99
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
        private String payAmount;
        private String openOrderId;

        public String getPayAmount() {
            return payAmount;
        }

        public void setPayAmount(String payAmount) {
            this.payAmount = payAmount;
        }

        public String getOpenOrderId() {
            return openOrderId;
        }

        public void setOpenOrderId(String openOrderId) {
            this.openOrderId = openOrderId;
        }
    }
}
