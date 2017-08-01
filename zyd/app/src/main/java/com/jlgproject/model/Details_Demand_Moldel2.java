package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/7/1.
 */

public class Details_Demand_Moldel2 implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"item":{"id":1776,"name":"车","assetNum":2,"totalAmout":8888888888888,"tangible":1}}
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
         * item : {"id":1776,"name":"车","assetNum":2,"totalAmout":8888888888888,"tangible":1}
         */

        private ItemBean item;

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public static class ItemBean {
            /**
             * id : 1776
             * name : 车
             * assetNum : 2
             * totalAmout : 8888888888888
             * tangible : 1
             */

            private int id;
            private String name;
            private int assetNum;
            private long totalAmout;
            private int tangible;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getAssetNum() {
                return assetNum;
            }

            public void setAssetNum(int assetNum) {
                this.assetNum = assetNum;
            }

            public long getTotalAmout() {
                return totalAmout;
            }

            public void setTotalAmout(long totalAmout) {
                this.totalAmout = totalAmout;
            }

            public int getTangible() {
                return tangible;
            }

            public void setTangible(int tangible) {
                this.tangible = tangible;
            }
        }
    }
}
