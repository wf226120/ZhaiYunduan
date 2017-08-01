package com.jlgproject.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/27.
 */

public class Share_Informations implements Serializable {


    /**
     * state : ok
     * message : 查询股权成功
     * data : {"pageNum":1,"total":1,"items":[{"@id":"1","createUser":null,"updateUser":null,"createTime":1498622129836,"updateTime":1498622129836,"isDeleted":false,"deleteReason":0,"unAuth":false,"zid":1,"userId":1192,"shareholderName":"杨洋洋","shareholderCode":"258159988494","address":"北京","amount":580884976495,"proportion":"20","registeredCapital":"50000","actualCapital":"800000","id":1715}]}
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
         * items : [{"@id":"1","createUser":null,"updateUser":null,"createTime":1498622129836,"updateTime":1498622129836,"isDeleted":false,"deleteReason":0,"unAuth":false,"zid":1,"userId":1192,"shareholderName":"杨洋洋","shareholderCode":"258159988494","address":"北京","amount":580884976495,"proportion":"20","registeredCapital":"50000","actualCapital":"800000","id":1715}]
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
            @SerializedName("@id")
            private String _$Id196; // FIXME check this code
            private Object createUser;
            private Object updateUser;
            private long createTime;
            private long updateTime;
            private boolean isDeleted;
            private int deleteReason;
            private boolean unAuth;
            private int zid;
            private int userId;
            private String shareholderName;
            private String shareholderCode;
            private String address;
            private long amount;
            private String proportion;
            private String registeredCapital;
            private String actualCapital;
            private int id;

            public String get_$Id196() {
                return _$Id196;
            }

            public void set_$Id196(String _$Id196) {
                this._$Id196 = _$Id196;
            }

            public Object getCreateUser() {
                return createUser;
            }

            public void setCreateUser(Object createUser) {
                this.createUser = createUser;
            }

            public Object getUpdateUser() {
                return updateUser;
            }

            public void setUpdateUser(Object updateUser) {
                this.updateUser = updateUser;
            }

            public long getCreateTime() {
                return createTime;
            }

            public void setCreateTime(long createTime) {
                this.createTime = createTime;
            }

            public long getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(long updateTime) {
                this.updateTime = updateTime;
            }

            public boolean isIsDeleted() {
                return isDeleted;
            }

            public void setIsDeleted(boolean isDeleted) {
                this.isDeleted = isDeleted;
            }

            public int getDeleteReason() {
                return deleteReason;
            }

            public void setDeleteReason(int deleteReason) {
                this.deleteReason = deleteReason;
            }

            public boolean isUnAuth() {
                return unAuth;
            }

            public void setUnAuth(boolean unAuth) {
                this.unAuth = unAuth;
            }

            public int getZid() {
                return zid;
            }

            public void setZid(int zid) {
                this.zid = zid;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getShareholderName() {
                return shareholderName;
            }

            public void setShareholderName(String shareholderName) {
                this.shareholderName = shareholderName;
            }

            public String getShareholderCode() {
                return shareholderCode;
            }

            public void setShareholderCode(String shareholderCode) {
                this.shareholderCode = shareholderCode;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public long getAmount() {
                return amount;
            }

            public void setAmount(long amount) {
                this.amount = amount;
            }

            public String getProportion() {
                return proportion;
            }

            public void setProportion(String proportion) {
                this.proportion = proportion;
            }

            public String getRegisteredCapital() {
                return registeredCapital;
            }

            public void setRegisteredCapital(String registeredCapital) {
                this.registeredCapital = registeredCapital;
            }

            public String getActualCapital() {
                return actualCapital;
            }

            public void setActualCapital(String actualCapital) {
                this.actualCapital = actualCapital;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
