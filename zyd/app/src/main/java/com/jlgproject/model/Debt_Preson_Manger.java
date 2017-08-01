package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/6.
 */

public class Debt_Preson_Manger implements Serializable {


    /**
     * state : ok
     * message :
     * data : {"pageNum":1,"total":6,"items":[{"id":4870,"name":"孙贝贝","idCode":"410224199112143614","type":"company","companyName":"腾讯"},{"id":4864,"name":"群北侧","idCode":"410224199112143619","type":"company","companyName":"美团"},{"id":4845,"name":"马云","idCode":"410224199112143618","type":"company","companyName":"百度"},{"id":4829,"name":"孙贝贝","idCode":"410224199112143615","type":"company","companyName":"中金"},{"id":4819,"name":"孙贝贝","idCode":"410224199112143613","type":"human","companyName":""},{"id":1259,"name":"sunbeo","idCode":"410224199112143612","type":"human","companyName":""}]}
     */

    private String state;
    private String message;
    /**
     * pageNum : 1
     * total : 6
     * items : [{"id":4870,"name":"孙贝贝","idCode":"410224199112143614","type":"company","companyName":"腾讯"},{"id":4864,"name":"群北侧","idCode":"410224199112143619","type":"company","companyName":"美团"},{"id":4845,"name":"马云","idCode":"410224199112143618","type":"company","companyName":"百度"},{"id":4829,"name":"孙贝贝","idCode":"410224199112143615","type":"company","companyName":"中金"},{"id":4819,"name":"孙贝贝","idCode":"410224199112143613","type":"human","companyName":""},{"id":1259,"name":"sunbeo","idCode":"410224199112143612","type":"human","companyName":""}]
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
         * id : 4870
         * name : 孙贝贝
         * idCode : 410224199112143614
         * type : company
         * companyName : 腾讯
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
            private String idCode;
            private String type;
            private String companyName;

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

            public String getIdCode() {
                return idCode;
            }

            public void setIdCode(String idCode) {
                this.idCode = idCode;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getCompanyName() {
                return companyName;
            }

            public void setCompanyName(String companyName) {
                this.companyName = companyName;
            }
        }
    }
}
