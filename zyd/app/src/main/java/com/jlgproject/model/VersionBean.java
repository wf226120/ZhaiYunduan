package com.jlgproject.model;

/**
 * @author 王锋 on 2017/7/17.
 */

public class VersionBean {


    /**
     * state : ok
     * message :
     * data : {"id":1,"versionNum":"2.1","isForce":1,"updateItems":"备案","type":"anroid","downUrl":"http://api.zhongjin.com"}
     */

    private String state;
    private String message;
    /**
     * id : 1
     * versionNum : 2.1
     * isForce : 1
     * updateItems : 备案
     * type : anroid
     * downUrl : http://api.zhongjin.com
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
        private int id;
        private String versionNum;
        private int isForce;
        private String updateItems;
        private String type;
        private String downUrl;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVersionNum() {
            return versionNum;
        }

        public void setVersionNum(String versionNum) {
            this.versionNum = versionNum;
        }

        public int getIsForce() {
            return isForce;
        }

        public void setIsForce(int isForce) {
            this.isForce = isForce;
        }

        public String getUpdateItems() {
            return updateItems;
        }

        public void setUpdateItems(String updateItems) {
            this.updateItems = updateItems;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getDownUrl() {
            return downUrl;
        }

        public void setDownUrl(String downUrl) {
            this.downUrl = downUrl;
        }
    }
}
