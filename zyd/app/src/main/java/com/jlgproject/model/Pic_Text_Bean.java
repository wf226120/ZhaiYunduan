package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/7/21.
 */

public class Pic_Text_Bean implements Serializable {

    /**
     * state : ok
     * message : 查询图文信息成功
     * data : {"pageNum":0,"total":0,"items":[{"title":"罗杰斯对话中金债事 共同见证中金债事智慧云系统起航","updateTime":"2017-01-05","img":"http://image.tianyancha.com/b339f37fc0ff4bc38e47bfcfb3b671df.jpg","url":"http://www.wdcsoc.com/resources/news/news2.html"}]}
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
         * items : [{"title":"罗杰斯对话中金债事 共同见证中金债事智慧云系统起航","updateTime":"2017-01-05","img":"http://image.tianyancha.com/b339f37fc0ff4bc38e47bfcfb3b671df.jpg","url":"http://www.wdcsoc.com/resources/news/news2.html"}]
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
             * title : 罗杰斯对话中金债事 共同见证中金债事智慧云系统起航
             * updateTime : 2017-01-05
             * img : http://image.tianyancha.com/b339f37fc0ff4bc38e47bfcfb3b671df.jpg
             * url : http://www.wdcsoc.com/resources/news/news2.html
             */

            private String title;
            private String updateTime;
            private String img;
            private String url;

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
