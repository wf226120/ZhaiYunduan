package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/19.
 */

public class Debts_Informations implements  Serializable {


    /**
     * state : ok
     * message :
     * data : {"pageNum":1,"total":1,"items":[{"id":9974,"createTime":"2017-07-13 ","from":"孙贝贝","to":"孙贝贝","amout":1000000,"orderId":"b8463a8c7ea34644b59c7887438283c3","payStatus":0,"isSolution":0,"otherPerson":1,"qianshu":null}]}
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
        /**
         * pageNum : 1
         * total : 1
         * items : [{"id":9974,"createTime":"2017-07-13 ","from":"孙贝贝","to":"孙贝贝","amout":1000000,"orderId":"b8463a8c7ea34644b59c7887438283c3","payStatus":0,"isSolution":0,"otherPerson":1,"qianshu":null}]
         */

        private int pageNum;
        private int total;
        private List<ItemsBean> items;

        public int getPageNum() {
            return pageNum;
        }

        public void setPageNum(int pageNum) {
            this.pageNum = pageNum;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 9974
             * createTime : 2017-07-13
             * from : 孙贝贝
             * to : 孙贝贝
             * amout : 1000000
             * orderId : b8463a8c7ea34644b59c7887438283c3
             * payStatus : 0
             * isSolution : 0
             * otherPerson : 1
             * qianshu : null
             */

            private Long id;
            private String createTime;
            private String from;
            private String to;
            private Long amout;
            private String orderId;
            private int payStatus;
            private int isSolution;
            private int otherPerson;
            private Object qianshu;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public String getCreateTime() {
                return createTime;
            }

            public void setCreateTime(String createTime) {
                this.createTime = createTime;
            }

            public String getFrom() {
                return from;
            }

            public void setFrom(String from) {
                this.from = from;
            }

            public String getTo() {
                return to;
            }

            public void setTo(String to) {
                this.to = to;
            }

            public Long getAmout() {
                return amout;
            }

            public void setAmout(Long amout) {
                this.amout = amout;
            }

            public String getOrderId() {
                return orderId;
            }

            public void setOrderId(String orderId) {
                this.orderId = orderId;
            }

            public int getPayStatus() {
                return payStatus;
            }

            public void setPayStatus(int payStatus) {
                this.payStatus = payStatus;
            }

            public int getIsSolution() {
                return isSolution;
            }

            public void setIsSolution(int isSolution) {
                this.isSolution = isSolution;
            }

            public int getOtherPerson() {
                return otherPerson;
            }

            public void setOtherPerson(int otherPerson) {
                this.otherPerson = otherPerson;
            }

            public Object getQianshu() {
                return qianshu;
            }

            public void setQianshu(Object qianshu) {
                this.qianshu = qianshu;
            }
        }
    }
}
