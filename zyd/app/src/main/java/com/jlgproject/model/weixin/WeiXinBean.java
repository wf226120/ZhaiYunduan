package com.jlgproject.model.weixin;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/7/18.
 */

public class WeiXinBean implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"orderId":"A2017072852092","callWx":{"appid":"wxbc0753acdfa4e7c3","noncestr":"1314141412","package":"Sign=WXPay","partnerid":"1483366602","prepayid":"wx20170728131414c2e4da63d50387936722","sign":"0949CD1FC9E9413835E80CFA91D625CF","timestamp":"1501218854"}}
     */

    private String state;
    private String message;
    /**
     * orderId : A2017072852092
     * callWx : {"appid":"wxbc0753acdfa4e7c3","noncestr":"1314141412","package":"Sign=WXPay","partnerid":"1483366602","prepayid":"wx20170728131414c2e4da63d50387936722","sign":"0949CD1FC9E9413835E80CFA91D625CF","timestamp":"1501218854"}
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

    public static class DataBean implements Serializable{
        private String orderId;
        /**
         * appid : wxbc0753acdfa4e7c3
         * noncestr : 1314141412
         * package : Sign=WXPay
         * partnerid : 1483366602
         * prepayid : wx20170728131414c2e4da63d50387936722
         * sign : 0949CD1FC9E9413835E80CFA91D625CF
         * timestamp : 1501218854
         */

        private CallWxBean callWx;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public CallWxBean getCallWx() {
            return callWx;
        }

        public void setCallWx(CallWxBean callWx) {
            this.callWx = callWx;
        }

        public static class CallWxBean implements Serializable{
            private String appid;
            private String noncestr;
            @SerializedName("package")
            private String packageX;
            private String partnerid;
            private String prepayid;
            private String sign;
            private String timestamp;

            public String getAppid() {
                return appid;
            }

            public void setAppid(String appid) {
                this.appid = appid;
            }

            public String getNoncestr() {
                return noncestr;
            }

            public void setNoncestr(String noncestr) {
                this.noncestr = noncestr;
            }

            public String getPackageX() {
                return packageX;
            }

            public void setPackageX(String packageX) {
                this.packageX = packageX;
            }

            public String getPartnerid() {
                return partnerid;
            }

            public void setPartnerid(String partnerid) {
                this.partnerid = partnerid;
            }

            public String getPrepayid() {
                return prepayid;
            }

            public void setPrepayid(String prepayid) {
                this.prepayid = prepayid;
            }

            public String getSign() {
                return sign;
            }

            public void setSign(String sign) {
                this.sign = sign;
            }

            public String getTimestamp() {
                return timestamp;
            }

            public void setTimestamp(String timestamp) {
                this.timestamp = timestamp;
            }
        }
    }
}
