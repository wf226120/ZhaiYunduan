package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/21.
 */

public class Demand_Infromation_Detail implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"name":"写字间","assetNum":2,"totalAmout":2080000,"assetDetails":"沈阳市皇姑区北陵大街23号沈阳天地t-3","assetCredential":"房产证","isLawsuit":0,"currentAsset":"","mortgage":"","location":"","belongTo":"","phoneNumber":"","tradeableAssets":2080000,"mortgageTarget":"","restrictReasion":"","restrictNum":"","restrictWorth":"","picList":["http://image.tianyancha.com/4ac5d0050c1544f699eeae463fab4c83.jpg"],"tangible":1}
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
         * name : 写字间
         * assetNum : 2
         * totalAmout : 2080000
         * assetDetails : 沈阳市皇姑区北陵大街23号沈阳天地t-3
         * assetCredential : 房产证
         * isLawsuit : 0
         * currentAsset :
         * mortgage :
         * location :
         * belongTo :
         * phoneNumber :
         * tradeableAssets : 2080000
         * mortgageTarget :
         * restrictReasion :
         * restrictNum :
         * restrictWorth :
         * picList : ["http://image.tianyancha.com/4ac5d0050c1544f699eeae463fab4c83.jpg"]
         * tangible : 1
         */

        private String name;
        private int assetNum;
        private int totalAmout;
        private String assetDetails;
        private String assetCredential;
        private int isLawsuit;
        private String currentAsset;
        private String mortgage;
        private String location;
        private String belongTo;
        private String phoneNumber;
        private Long tradeableAssets;
        private String mortgageTarget;
        private String restrictReasion;
        private String restrictNum;
        private String restrictWorth;
        private int tangible;
        private List<String> picList;

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

        public String getAssetDetails() {
            return assetDetails;
        }

        public void setAssetDetails(String assetDetails) {
            this.assetDetails = assetDetails;
        }

        public String getAssetCredential() {
            return assetCredential;
        }

        public void setAssetCredential(String assetCredential) {
            this.assetCredential = assetCredential;
        }

        public int getIsLawsuit() {
            return isLawsuit;
        }

        public void setIsLawsuit(int isLawsuit) {
            this.isLawsuit = isLawsuit;
        }

        public String getCurrentAsset() {
            return currentAsset;
        }

        public void setCurrentAsset(String currentAsset) {
            this.currentAsset = currentAsset;
        }

        public String getMortgage() {
            return mortgage;
        }

        public void setMortgage(String mortgage) {
            this.mortgage = mortgage;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getBelongTo() {
            return belongTo;
        }

        public void setBelongTo(String belongTo) {
            this.belongTo = belongTo;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public Long getTradeableAssets() {
            return tradeableAssets;
        }

        public void setTradeableAssets(Long tradeableAssets) {
            this.tradeableAssets = tradeableAssets;
        }

        public String getMortgageTarget() {
            return mortgageTarget;
        }

        public void setMortgageTarget(String mortgageTarget) {
            this.mortgageTarget = mortgageTarget;
        }

        public String getRestrictReasion() {
            return restrictReasion;
        }

        public void setRestrictReasion(String restrictReasion) {
            this.restrictReasion = restrictReasion;
        }

        public String getRestrictNum() {
            return restrictNum;
        }

        public void setRestrictNum(String restrictNum) {
            this.restrictNum = restrictNum;
        }

        public String getRestrictWorth() {
            return restrictWorth;
        }

        public void setRestrictWorth(String restrictWorth) {
            this.restrictWorth = restrictWorth;
        }

        public int getTangible() {
            return tangible;
        }

        public void setTangible(int tangible) {
            this.tangible = tangible;
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }
    }
}
