package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/20.
 */

public class Debts_Maner_Details implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"createTime":"2017-07-13","from":{"name":"sunbeo","idCode":"410224199112143612"},"to":{"name":"孙贝贝","idCode":"410224199112143613"},"amout":180000,"natureOf":1,"genre":1,"isLawsuit":0,"remark":"","identification":["1","2"],"evidence":["6"],"electron":["4"],"proof":1,"mortgagee":0,"attorn":1,"lawsuit":1,"recordTime":"2014年7月13日","picList":[],"workflowStatus":0,"isSolution":0,"payStatus":0,"orderId":"4cc13c93c4934e4da68aa783a15061bf","qianshu":"0.01"}
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
         * createTime : 2017-07-13
         * from : {"name":"sunbeo","idCode":"410224199112143612"}
         * to : {"name":"孙贝贝","idCode":"410224199112143613"}
         * amout : 180000
         * natureOf : 1
         * genre : 1
         * isLawsuit : 0
         * remark :
         * identification : ["1","2"]
         * evidence : ["6"]
         * electron : ["4"]
         * proof : 1
         * mortgagee : 0
         * attorn : 1
         * lawsuit : 1
         * recordTime : 2014年7月13日
         * picList : []
         * workflowStatus : 0
         * isSolution : 0
         * payStatus : 0
         * orderId : 4cc13c93c4934e4da68aa783a15061bf
         * qianshu : 0.01
         */

        private String createTime;
        private FromBean from;
        private ToBean to;
        private Long amout;
        private int natureOf;
        private int genre;
        private int isLawsuit;
        private String remark;
        private int proof;
        private int mortgagee;
        private int attorn;
        private int lawsuit;
        private String recordTime;
        private int workflowStatus;
        private int isSolution;
        private int payStatus;
        private String orderId;
        private String qianshu;
        private List<String> identification;
        private List<String> evidence;
        private List<String> electron;
        private List<String> picList;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public FromBean getFrom() {
            return from;
        }

        public void setFrom(FromBean from) {
            this.from = from;
        }

        public ToBean getTo() {
            return to;
        }

        public void setTo(ToBean to) {
            this.to = to;
        }

        public Long getAmout() {
            return amout;
        }

        public void setAmout(Long amout) {
            this.amout = amout;
        }

        public int getNatureOf() {
            return natureOf;
        }

        public void setNatureOf(int natureOf) {
            this.natureOf = natureOf;
        }

        public int getGenre() {
            return genre;
        }

        public void setGenre(int genre) {
            this.genre = genre;
        }

        public int getIsLawsuit() {
            return isLawsuit;
        }

        public void setIsLawsuit(int isLawsuit) {
            this.isLawsuit = isLawsuit;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public int getProof() {
            return proof;
        }

        public void setProof(int proof) {
            this.proof = proof;
        }

        public int getMortgagee() {
            return mortgagee;
        }

        public void setMortgagee(int mortgagee) {
            this.mortgagee = mortgagee;
        }

        public int getAttorn() {
            return attorn;
        }

        public void setAttorn(int attorn) {
            this.attorn = attorn;
        }

        public int getLawsuit() {
            return lawsuit;
        }

        public void setLawsuit(int lawsuit) {
            this.lawsuit = lawsuit;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }

        public int getWorkflowStatus() {
            return workflowStatus;
        }

        public void setWorkflowStatus(int workflowStatus) {
            this.workflowStatus = workflowStatus;
        }

        public int getIsSolution() {
            return isSolution;
        }

        public void setIsSolution(int isSolution) {
            this.isSolution = isSolution;
        }

        public int getPayStatus() {
            return payStatus;
        }

        public void setPayStatus(int payStatus) {
            this.payStatus = payStatus;
        }

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getQianshu() {
            return qianshu;
        }

        public void setQianshu(String qianshu) {
            this.qianshu = qianshu;
        }

        public List<String> getIdentification() {
            return identification;
        }

        public void setIdentification(List<String> identification) {
            this.identification = identification;
        }

        public List<String> getEvidence() {
            return evidence;
        }

        public void setEvidence(List<String> evidence) {
            this.evidence = evidence;
        }

        public List<String> getElectron() {
            return electron;
        }

        public void setElectron(List<String> electron) {
            this.electron = electron;
        }

        public List<String> getPicList() {
            return picList;
        }

        public void setPicList(List<String> picList) {
            this.picList = picList;
        }

        public static class FromBean {
            /**
             * name : sunbeo
             * idCode : 410224199112143612
             */

            private String name;
            private String idCode;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIdCode() {
                return idCode;
            }

            public void setIdCode(String idCode) {
                this.idCode = idCode;
            }
        }

        public static class ToBean {
            /**
             * name : 孙贝贝
             * idCode : 410224199112143613
             */

            private String name;
            private String idCode;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getIdCode() {
                return idCode;
            }

            public void setIdCode(String idCode) {
                this.idCode = idCode;
            }
        }
    }
}
