package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/28.
 */

public class Buness_Informations_Info implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"pageNum":1,"total":1,"items":[{"id":1734,"legalPersonName":"孙贝贝","phoneNumber":"18813158028","taxNumber":"123488498","address":" 北京","year":"2000","lastSales":80000000000,"lastElectricityBills":"500000","profitMargin":"20","gross":2888888888,"totalInvestment":"588888888888"}]}
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
         * items : [{"id":1734,"legalPersonName":"孙贝贝","phoneNumber":"18813158028","taxNumber":"123488498","address":" 北京","year":"2000","lastSales":80000000000,"lastElectricityBills":"500000","profitMargin":"20","gross":2888888888,"totalInvestment":"588888888888"}]
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
             * id : 1734
             * legalPersonName : 孙贝贝
             * phoneNumber : 18813158028
             * taxNumber : 123488498
             * address :  北京
             * year : 2000
             * lastSales : 80000000000
             * lastElectricityBills : 500000
             * profitMargin : 20
             * gross : 2888888888
             * totalInvestment : 588888888888
             */

            private int id;
            private String legalPersonName;
            private String phoneNumber;
            private String taxNumber;
            private String address;
            private String year;
            private long lastSales;
            private String lastElectricityBills;
            private String profitMargin;
            private long gross;
            private String totalInvestment;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getLegalPersonName() {
                return legalPersonName;
            }

            public void setLegalPersonName(String legalPersonName) {
                this.legalPersonName = legalPersonName;
            }

            public String getPhoneNumber() {
                return phoneNumber;
            }

            public void setPhoneNumber(String phoneNumber) {
                this.phoneNumber = phoneNumber;
            }

            public String getTaxNumber() {
                return taxNumber;
            }

            public void setTaxNumber(String taxNumber) {
                this.taxNumber = taxNumber;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getYear() {
                return year;
            }

            public void setYear(String year) {
                this.year = year;
            }

            public long getLastSales() {
                return lastSales;
            }

            public void setLastSales(long lastSales) {
                this.lastSales = lastSales;
            }

            public String getLastElectricityBills() {
                return lastElectricityBills;
            }

            public void setLastElectricityBills(String lastElectricityBills) {
                this.lastElectricityBills = lastElectricityBills;
            }

            public String getProfitMargin() {
                return profitMargin;
            }

            public void setProfitMargin(String profitMargin) {
                this.profitMargin = profitMargin;
            }

            public long getGross() {
                return gross;
            }

            public void setGross(long gross) {
                this.gross = gross;
            }

            public String getTotalInvestment() {
                return totalInvestment;
            }

            public void setTotalInvestment(String totalInvestment) {
                this.totalInvestment = totalInvestment;
            }
        }
    }
}
