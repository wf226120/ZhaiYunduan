package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/5/24.
 */

public class Alipay_orderId implements Serializable {

    private String orderId;

    public Alipay_orderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
