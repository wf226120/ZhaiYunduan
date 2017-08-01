package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/7/11.
 */

public class TuJianHuiYan implements Serializable {

    /**
     * message : 推荐会员列表查询成功
     * state : ok
     * data : {"items":[{"phonenumber":"18612111522","name":"赵亮"},{"phonenumber":"17600223674","name":"段鹏飞"},{"phonenumber":"15194604124","name":"孙贝贝"},{"phonenumber":"13552949045","name":"王锋"},{"phonenumber":"18911132089","name":"于安琪"}]}
     */

    private String message;
    private String state;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ItemsBean> items;

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * phonenumber : 18612111522
             * name : 赵亮
             */

            private String phonenumber;
            private String name;

            public String getPhonenumber() {
                return phonenumber;
            }

            public void setPhonenumber(String phonenumber) {
                this.phonenumber = phonenumber;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
