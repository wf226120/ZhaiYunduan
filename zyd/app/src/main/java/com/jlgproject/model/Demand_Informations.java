package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/21.
 */

public class Demand_Informations implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"pageNum":1,"total":3,"items":[{"id":1706,"name":"lkjhgfd","assetNum":0,"totalAmout":0,"tangible":0},{"id":1707,"name":"asdfghj","assetNum":0,"totalAmout":0,"tangible":0},{"id":1709,"name":"jjjjjjjjjjjj","assetNum":11111111,"totalAmout":11111111111,"tangible":0}]}
     */

    private String state;
    private String message;
    /**
     * pageNum : 1
     * total : 3
     * items : [{"id":1706,"name":"lkjhgfd","assetNum":0,"totalAmout":0,"tangible":0},{"id":1707,"name":"asdfghj","assetNum":0,"totalAmout":0,"tangible":0},{"id":1709,"name":"jjjjjjjjjjjj","assetNum":11111111,"totalAmout":11111111111,"tangible":0}]
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
         * id : 1706
         * name : lkjhgfd
         * assetNum : 0
         * totalAmout : 0
         * tangible : 0
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
            private String name;
            private Long assetNum;
            private Long totalAmout;
            private int tangible;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
                this.id = id;
            }

            public Long getAssetNum() {
                return assetNum;
            }

            public void setAssetNum(Long assetNum) {
                this.assetNum = assetNum;
            }

            public Long getTotalAmout() {
                return totalAmout;
            }

            public void setTotalAmout(Long totalAmout) {
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
