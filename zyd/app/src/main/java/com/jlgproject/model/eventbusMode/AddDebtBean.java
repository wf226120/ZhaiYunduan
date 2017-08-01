package com.jlgproject.model.eventbusMode;

/**
 * @author 王锋 on 2017/7/12.
 */

public class AddDebtBean {

    private String orderId;
    private String qianshu;
    private int state;

    public AddDebtBean(String orderId, String qianshu,int state) {
        this.orderId = orderId;
        this.qianshu = qianshu;
        this.state=state;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getQianshu() {
        return qianshu;
    }

    public void setQianshu(String qianshu) {
        this.qianshu = qianshu;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
