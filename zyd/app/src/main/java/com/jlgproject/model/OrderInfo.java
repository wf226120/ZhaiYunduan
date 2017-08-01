package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/1.
 */

public class OrderInfo implements Serializable {


    /**
     * data : alipay_sdk=alipay-sdk-java-dynamicVersionNo&app_id=2017051707261792&biz_content=%7B%22body%22%3A%22%E5%BC%80%E8%A1%8C%E4%BF%A1%E6%81%AF%22%2C%22out_trade_no%22%3A%22B2017071154268%22%2C%22product_code%22%3A%22QUICK_MSECURITY_PAY%22%2C%22subject%22%3A%22%E4%BA%91%E5%80%BA%E8%A1%8C%E5%BC%80%E8%A1%8C%E8%B4%B9%22%2C%22timeout_express%22%3A%2230m%22%2C%22total_amount%22%3A%220.02%22%7D&charset=utf-8&format=json&method=alipay.trade.app.pay&notify_url=http%3A%2F%2Ftest.api.zhongjinzhaishi.com%2Fapi%2Falipay%2Fpay%2Fnotify&sign=ZM7a3okXC1mMjhFB%2F%2FUSHBae1yoBt8flJ%2BL%2B3dtqQzEKnSbdkWahS27DNhVhGuJ%2B4IPBeD9MYVmHO6BB1VO80L7TphHcOkIdsb8EEXaJawCAOsiJpB1H3ET1MEqynLZI8K0qvmlNyjDDoLmkGKiXrRGepCpovrhSOWTPy5m6AbD5%2FAr49Z1wapg%2BL0Sv4nGpekLKkJCdINfgBjCdCQygzlRuZznrrtJPo6O0tSGam0zodPLHB9YQq29fSU5mirUC%2BRYN%2FvAb0YgPiRs%2FoZ8vF7BWsANKmfY%2B%2FQt%2FlTYRQ9w4FUQD7CCJ79N1ff6d45Tr9etLBugu6ShboQ3E1ZS%2FSg%3D%3D&sign_type=RSA2&timestamp=2017-07-11+13%3A38%3A59&version=1.0
     * code : ok
     * msg : 支付成功
     */

    private String data;
    private String code;
    private String msg;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
