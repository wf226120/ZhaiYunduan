package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/7/21.
 */

public class Video_List_Bean implements Serializable {

    /**
     * state : ok
     * message : 查询视频信息成功
     * data : {"pageNum":0,"total":0,"items":[{"subTitle":"中金债事智慧云2.0升级上线启动仪式在东莞盛大举行","title":"中金债事智慧云2.0升级上线启动仪式在东莞盛大举行","updateTime":"2017-01-12","img":"http://image.tianyancha.com/662199ca720f4b50bd5ba4c3db9bb015.jpg","url":"http://www.wdcsoc.com/resources/news/news1.html"},{"subTitle":"陈学军董事长赴广解读中金债事大系统","title":"陈学军董事长赴广解读中金债事大系统","updateTime":"2017-01-05","img":"http://image.tianyancha.com/00276baac5d04ba79e541baa1efbced1.jpg","url":"http://www.wdcsoc.com/resources/news/news3.html"},{"subTitle":"2016年中央经济工作会议深入推进\u201c三去一降一补\u201d","title":"2016年中央经济工作会议深入推进\u201c三去一降一补\u201d","updateTime":"2016-12-19","img":"http://image.tianyancha.com/f28dcbaee1954342ab01cab8b17dc407.jpg","url":"http://www.wdcsoc.com/resources/news/news3.html"}]}
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
         * pageNum : 0
         * total : 0
         * items : [{"subTitle":"中金债事智慧云2.0升级上线启动仪式在东莞盛大举行","title":"中金债事智慧云2.0升级上线启动仪式在东莞盛大举行","updateTime":"2017-01-12","img":"http://image.tianyancha.com/662199ca720f4b50bd5ba4c3db9bb015.jpg","url":"http://www.wdcsoc.com/resources/news/news1.html"},{"subTitle":"陈学军董事长赴广解读中金债事大系统","title":"陈学军董事长赴广解读中金债事大系统","updateTime":"2017-01-05","img":"http://image.tianyancha.com/00276baac5d04ba79e541baa1efbced1.jpg","url":"http://www.wdcsoc.com/resources/news/news3.html"},{"subTitle":"2016年中央经济工作会议深入推进\u201c三去一降一补\u201d","title":"2016年中央经济工作会议深入推进\u201c三去一降一补\u201d","updateTime":"2016-12-19","img":"http://image.tianyancha.com/f28dcbaee1954342ab01cab8b17dc407.jpg","url":"http://www.wdcsoc.com/resources/news/news3.html"}]
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
             * subTitle : 中金债事智慧云2.0升级上线启动仪式在东莞盛大举行
             * title : 中金债事智慧云2.0升级上线启动仪式在东莞盛大举行
             * updateTime : 2017-01-12
             * img : http://image.tianyancha.com/662199ca720f4b50bd5ba4c3db9bb015.jpg
             * url : http://www.wdcsoc.com/resources/news/news1.html
             */

            private String subTitle;
            private String title;
            private String updateTime;
            private String img;
            private String url;

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }
    }
}
