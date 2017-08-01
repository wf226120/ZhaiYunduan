package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王锋 on 2017/5/22.
 */

public class News implements Serializable {


    private DataBean data;
    /**
     * data : {"news":[{"title":"中金债事智慧云2.0升级上线启动仪式在东莞盛大举行","subTitle":"","img":"http://image.tianyancha.com/662199ca720f4b50bd5ba4c3db9bb015.jpg","url":"http://www.wdcsoc.com/resources/news/news1.html","updateTime":"2017-01-12"},{"title":"罗杰斯对话中金债事 共同见证中金债事智慧云系统起航","subTitle":"","img":"http://image.tianyancha.com/b339f37fc0ff4bc38e47bfcfb3b671df.jpg","url":"http://www.wdcsoc.com/resources/news/news2.html","updateTime":"2017-01-05"},{"title":"陈学军董事长赴广解读中金债事大系统","subTitle":"","img":"http://image.tianyancha.com/00276baac5d04ba79e541baa1efbced1.jpg","url":"http://www.wdcsoc.com/resources/news/news3.html","updateTime":"2017-01-05"},{"title":"中金债事广东省公司盛大开业","subTitle":"","img":"http://image.tianyancha.com/9d4941ceab914eaeafd638bc8901de62.jpg","url":"http://www.wdcsoc.com/resources/news/news4.html","updateTime":"2016-12-19"},{"title":"中金债事大系统2.0正式上线","subTitle":"","img":"http://image.tianyancha.com/e5b60acca31941cb8e3ba72cb9831300.jpg","url":"http://www.wdcsoc.com/resources/news/news5.html","updateTime":"2016-12-19"},{"title":"2016年中央经济工作会议深入推进\u201c三去一降一补\u201d","subTitle":"","img":"http://image.tianyancha.com/bacd9f6c2a9541b6a6bd42d0d617248e.jpg","url":"http://www.wdcsoc.com/resources/news/news6.html","updateTime":"2016-12-19"},{"title":"中金债事大系统说明会在京成功召开","subTitle":"","img":"http://image.tianyancha.com/f28dcbaee1954342ab01cab8b17dc407.jpg","url":"http://www.wdcsoc.com/resources/news/news7.html","updateTime":"2016-09-06"},{"title":"中金债事大系统京津冀分公司在北京成立","subTitle":"","img":"http://image.tianyancha.com/cdc8cae4ceb14274a3056cc0b63013b8.jpg","url":"http://www.wdcsoc.com/resources/news/news8.html","updateTime":"2016-08-30"},{"title":"广东东莞团队加盟签约仪式（组图）","subTitle":"","img":"http://image.tianyancha.com/ee1d37ee8a0f41e88a492b165f794f85.jpg","url":"http://www.wdcsoc.com/resources/news/news9.html","updateTime":"2016-08-26"},{"title":"中金债事浙江金华团队及东阳团队加盟会（组图）","subTitle":"","img":"http://image.tianyancha.com/a18afab1e77b4930a597636c92a86015.jpg","url":"http://www.wdcsoc.com/resources/news/news10.html","updateTime":"2016-08-25"}]}
     * message : 成功
     * state : ok
     */

    private String message;
    private String state;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

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

    public static class DataBean {
        /**
         * title : 中金债事智慧云2.0升级上线启动仪式在东莞盛大举行
         * subTitle :
         * img : http://image.tianyancha.com/662199ca720f4b50bd5ba4c3db9bb015.jpg
         * url : http://www.wdcsoc.com/resources/news/news1.html
         * updateTime : 2017-01-12
         */

        private List<NewsBean> news;

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class NewsBean {
            private String title;
            private String subTitle;
            private String img;
            private String url;
            private String updateTime;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getSubTitle() {
                return subTitle;
            }

            public void setSubTitle(String subTitle) {
                this.subTitle = subTitle;
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

            public String getUpdateTime() {
                return updateTime;
            }

            public void setUpdateTime(String updateTime) {
                this.updateTime = updateTime;
            }
        }
    }
}
