package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王锋 on 2017/6/5.
 */

public class Bnner_News implements Serializable {


    /**
     * message : 查询首页信息成功
     * state : ok
     * data : {"slider":[{"openUrl":"","name":"test1","url":"http://image.tianyancha.com/1a8f835a5106401d932bfaa058abb95e.jpg"},{"openUrl":"","name":"test2","url":"http://image.tianyancha.com/3809653aa3d941c78a121a1d4c5f6596.jpg"},{"openUrl":"","name":"test3","url":"http://image.tianyancha.com/fa7fa77898b74f99bd19eed80954011a.jpg"},{"openUrl":"","name":"test4","url":"http://image.tianyancha.com/46bf198ce91141ee9fccdfed8383b6e4.jpg"},{"openUrl":"","name":"test5","url":"http://image.tianyancha.com/0eaef53c993140c78459ad18df73b4fe.jpg"}],"news":[{"title":"中金债事智慧云2.0升级上线启动仪式在东莞盛大举行","updateTime":"2017-01-12","img":"http://image.tianyancha.com/662199ca720f4b50bd5ba4c3db9bb015.jpg","url":"http://www.wdcsoc.com/resources/news/news1.html"},{"title":"罗杰斯对话中金债事 共同见证中金债事智慧云系统起航","updateTime":"2017-01-05","img":"http://image.tianyancha.com/b339f37fc0ff4bc38e47bfcfb3b671df.jpg","url":"http://www.wdcsoc.com/resources/news/news2.html"},{"title":"陈学军董事长赴广解读中金债事大系统","updateTime":"2017-01-05","img":"http://image.tianyancha.com/00276baac5d04ba79e541baa1efbced1.jpg","url":"http://www.wdcsoc.com/resources/news/news3.html"},{"title":"中金债事广东省公司盛大开业","updateTime":"2016-12-19","img":"http://image.tianyancha.com/9d4941ceab914eaeafd638bc8901de62.jpg","url":"http://www.wdcsoc.com/resources/news/news4.html"}]}
     */

    private String message;
    private String state;
    private DataBean data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * openUrl :
         * name : test1
         * url : http://image.tianyancha.com/1a8f835a5106401d932bfaa058abb95e.jpg
         */

        private List<SliderBean> slider;
        /**
         * title : 中金债事智慧云2.0升级上线启动仪式在东莞盛大举行
         * updateTime : 2017-01-12
         * img : http://image.tianyancha.com/662199ca720f4b50bd5ba4c3db9bb015.jpg
         * url : http://www.wdcsoc.com/resources/news/news1.html
         */

        private List<NewsBean> news;

        public List<SliderBean> getSlider() {
            return slider;
        }

        public void setSlider(List<SliderBean> slider) {
            this.slider = slider;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class SliderBean implements Serializable{
            private String openUrl;
            private String name;
            private String url;

            public String getOpenUrl() {
                return openUrl;
            }

            public void setOpenUrl(String openUrl) {
                this.openUrl = openUrl;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }
        }

        public static class NewsBean implements Serializable{
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
