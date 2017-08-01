package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/16.
 */

public class Preson_Basic_Informations implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"idCode":"330724198206010514","area":"浙江省","phoneNumber":"15215869490","contactAddress":"浙江省东阳市江北街道月亮湾社区桑园","email":"31645@qq.com","qq":"3165791","weChat":"67984213","legalPersonName":"","organCode":"","legalPersonId":"","registeredCapital":0,"category":"","debtCompanyName":""}
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
         * idCode : 330724198206010514
         * area : 浙江省
         * phoneNumber : 15215869490
         * contactAddress : 浙江省东阳市江北街道月亮湾社区桑园
         * email : 31645@qq.com
         * qq : 3165791
         * weChat : 67984213
         * legalPersonName :
         * organCode :
         * legalPersonId :
         * registeredCapital : 0
         * category :
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
