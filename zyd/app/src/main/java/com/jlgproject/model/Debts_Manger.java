package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/9.
 */

public class Debts_Manger implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"pageNum":1,"total":512,"items":[{"id":14436,"createTime":"2017-07-31 ","from":"难看","to":"sunbeo","amout":666,"orderId":"423929b92325419c83449a9db5fee2a7","payStatus":0,"isSolution":0,"otherPerson":null,"qianshu":"0.01","recommend":"13552949045"},{"id":14429,"createTime":"2017-07-31 ","from":"sunbeo","to":"测试","amout":100000,"orderId":"cac7fabc940c432892415c068d953f47","payStatus":1,"isSolution":0,"otherPerson":null,"qianshu":"0.01","recommend":"13552949045"},{"id":14403,"createTime":"2017-07-31 ","from":"看看咯","to":"sunbeo","amout":6699,"orderId":"dd153c2caa0143f396045e1e6d3f84d6","payStatus":1,"isSolution":0,"otherPerson":null,"qianshu":"0.01","recommend":"13552949045"},{"id":14402,"createTime":"2017-07-31 ","from":"看看咯","to":"sunbeo","amout":6655,"orderId":"cdacb3ea38a34b5c99d46593e06316fb","payStatus":1,"isSolution":0,"otherPerson":null,"qianshu":"0.01","recommend":"13552949045"}]}
     */

    private String state;
    private String message;
    /**
     * pageNum : 1
     * total : 512
     * items : [{"id":14436,"createTime":"2017-07-31 ","from":"难看","to":"sunbeo","amout":666,"orderId":"423929b92325419c83449a9db5fee2a7","payStatus":0,"isSolution":0,"otherPerson":null,"qianshu":"0.01","recommend":"13552949045"},{"id":14429,"createTime":"2017-07-31 ","from":"sunbeo","to":"测试","amout":100000,"orderId":"cac7fabc940c432892415c068d953f47","payStatus":1,"isSolution":0,"otherPerson":null,"qianshu":"0.01","recommend":"13552949045"},{"id":14403,"createTime":"2017-07-31 ","from":"看看咯","to":"sunbeo","amout":6699,"orderId":"dd153c2caa0143f396045e1e6d3f84d6","payStatus":1,"isSolution":0,"otherPerson":null,"qianshu":"0.01","recommend":"13552949045"},{"id":14402,"createTime":"2017-07-31 ","from":"看看咯","to":"sunbeo","amout":6655,"orderId":"cdacb3ea38a34b5c99d46593e06316fb","payStatus":1,"isSolution":0,"otherPerson":null,"qianshu":"0.01","recommend":"13552949045"}]
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
        private int pageNum;
        private int total;
        /**
         * id : 14436
         * createTime : 2017-07-31
         * from : 难看
         * to : sunbeo
         * amout : 666
         * orderId : 423929b92325419c83449a9db5fee2a7
         * payStatus : 0
         * isSolution : 0
         * otherPerson : null
         * qianshu : 0.01
         * recommend : 13552949045
         */

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

        public static class ItemsBean implements Serializable{
            private Long id;
            private String createTime;
            private String from;
            private String to;
            private Long amout;
            private String orderId;
            private int payStatus;
            private int isSolution;
            private Object otherPerson;
            private String qianshu;
            private String recommend;

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

            public Object getOtherPerson() {
                return otherPerson;
            }

            public void setOtherPerson(Object otherPerson) {
                this.otherPerson = otherPerson;
            }

            public String getQianshu() {
                return qianshu;
            }

            public void setQianshu(String qianshu) {
                this.qianshu = qianshu;
            }

            public String getRecommend() {
                return recommend;
            }

            public void setRecommend(String recommend) {
                this.recommend = recommend;
            }
        }
    }
}
