package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/16.
 */

public class AddDebtResponse implements Serializable {


    /**
     * state : ok
     * <p>
     * message : 债事备案成功,未支付
     * data : {"relation":{"qianshu":"0.01","orderId":"49b941fca7304171a0c5ed538a373d1f"}}
     */


    private String state;
    private String message;


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
        private RelationBean relation;

        public RelationBean getRelation() {
            return relation;
        }

        public void setRelation(RelationBean relation) {
            this.relation = relation;
        }

        public static class RelationBean {


            private String qianshu;


            private String orderId;

            public String getQianshu() {
                return qianshu;
            }

            public void setQianshu(String qianshu) {
                this.qianshu = qianshu;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }
        }
    }
}
