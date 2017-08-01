package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/20.
 */

public class Asserts_Infromations implements Serializable {

    /**
     * state : ok
     * message :
     * data : {"pageNum":1,"total":1,"items":[{"id":851,"name":"车","assetNum":1,"totalAmout":500000,"tradeableAssets":500000}]}
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
         * items : [{"id":851,"name":"车","assetNum":1,"totalAmout":500000,"tradeableAssets":500000}]
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
             * id : 851
             * name : 车
             * assetNum : 1
             * totalAmout : 500000
             * tradeableAssets : 500000
             */

            private Long id;
            private String name;
            private int assetNum;
            private int totalAmout;
            private Long tradeableAssets;

            public Long getId() {
                return id;
            }

            public void setId(Long id) {
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

            public int getTotalAmout() {
                return totalAmout;
            }

            public void setTotalAmout(int totalAmout) {
                this.totalAmout = totalAmout;
            }

            public Long getTradeableAssets() {
                return tradeableAssets;
            }

            public void setTradeableAssets(Long tradeableAssets) {
                this.tradeableAssets = tradeableAssets;
            }
        }
    }
}
