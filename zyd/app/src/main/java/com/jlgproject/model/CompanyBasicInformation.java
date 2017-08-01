package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/26.
 */

public class CompanyBasicInformation implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"idCode":"410224199112143615","area":"北京市北京东城","phoneNumber":"123456","contactAddress":"","email":"你好","qq":"","weChat":"","legalPersonName":"","name":"孙贝贝","organCode":"123456","legalPersonId":"","registeredCapital":89999999,"category":"金融","debtCompanyName":"中金"}
     */

    private String state;
    private String message;
    /**
     * idCode : 410224199112143615
     * area : 北京市北京东城
     * phoneNumber : 123456
     * contactAddress :
     * email : 你好
     * qq :
     * weChat :
     * legalPersonName :
     * name : 孙贝贝
     * organCode : 123456
     * legalPersonId :
     * registeredCapital : 89999999
     * category : 金融
     * debtCompanyName : 中金
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

    public static class DataBean {
        /**
         * idCode : 802100433
         * area : 北京市
         * phoneNumber : 13800000000
         * contactAddress : 西二旗百度大厦
         * email :
         * qq :
         * weChat :
         * legalPersonName : 梁志祥
         * organCode :
         * legalPersonId : 41112319871204601X
         * registeredCapital : 10000000
         * category : 21
         * debtCompanyName :
         */



        private String idCode;
        private String area;
        private String phoneNumber;
        private String contactAddress;
        private String email;
        private String qq;
        private String weChat;
        private String legalPersonName;
        private String name;
        private String organCode;
        private String legalPersonId;
        private int registeredCapital;
        private String category;
        private String debtCompanyName;

        public String getIdCode() {
            return idCode;
        }

        public void setIdCode(String idCode) {
            this.idCode = idCode;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getContactAddress() {
            return contactAddress;
        }

        public void setContactAddress(String contactAddress) {
            this.contactAddress = contactAddress;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getQq() {
            return qq;
        }

        public void setQq(String qq) {
            this.qq = qq;
        }

        public String getWeChat() {
            return weChat;
        }

        public void setWeChat(String weChat) {
            this.weChat = weChat;
        }

        public String getLegalPersonName() {
            return legalPersonName;
        }

        public void setLegalPersonName(String legalPersonName) {
            this.legalPersonName = legalPersonName;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getOrganCode() {
            return organCode;
        }

        public void setOrganCode(String organCode) {
            this.organCode = organCode;
        }

        public String getLegalPersonId() {
            return legalPersonId;
        }

        public void setLegalPersonId(String legalPersonId) {
            this.legalPersonId = legalPersonId;
        }

        public int getRegisteredCapital() {
            return registeredCapital;
        }

        public void setRegisteredCapital(int registeredCapital) {
            this.registeredCapital = registeredCapital;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getDebtCompanyName() {
            return debtCompanyName;
        }

        public void setDebtCompanyName(String debtCompanyName) {
            this.debtCompanyName = debtCompanyName;
        }
    }
}
