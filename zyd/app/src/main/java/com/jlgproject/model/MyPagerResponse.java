package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/19.
 */

public class MyPagerResponse implements Serializable {


    /**
     * message : 返回信息成功
     * state : ok
     * data : {"username":"wangfeng","recommendCode":"888888","phone":"13552949045","jiezhai":0,"kaihang":0,"zhaishi":0,"image":"http://zjzs.oss-cn-beijing.aliyuncs.com/84333420ed064aa999927fed8160576d.jpg","huiyuan":0,"type":3}
     */

    private String message;
    private String state;
    /**
     * username : wangfeng
     * recommendCode : 888888
     * phone : 13552949045
     * jiezhai : 0
     * kaihang : 0
     * zhaishi : 0
     * image : http://zjzs.oss-cn-beijing.aliyuncs.com/84333420ed064aa999927fed8160576d.jpg
     * huiyuan : 0
     * type : 3
     */

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

    public static class DataBean {
        private String username;//name
        private String recommendCode;//推荐码
        private String phone;//手机号
        private int jiezhai;//借债数
        private int kaihang;//推荐开行数
        private int zhaishi;//推荐备案数
        private String image;//用户头像
        private int huiyuan;//推荐会员数
        private int type;//用户类型 1 从业地址  2会员 3普通用户
        private String hangzhang;//云债行行长，服务行（区县），市行行长，省行行长，拓展
        private String cardNumber;//身份证号

        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getHangzhang() {
            return hangzhang;
        }

        public void setHangzhang(String hangzhang) {
            this.hangzhang = hangzhang;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRecommendCode() {
            return recommendCode;
        }

        public void setRecommendCode(String recommendCode) {
            this.recommendCode = recommendCode;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public int getJiezhai() {
            return jiezhai;
        }

        public void setJiezhai(int jiezhai) {
            this.jiezhai = jiezhai;
        }

        public int getKaihang() {
            return kaihang;
        }

        public void setKaihang(int kaihang) {
            this.kaihang = kaihang;
        }

        public int getZhaishi() {
            return zhaishi;
        }

        public void setZhaishi(int zhaishi) {
            this.zhaishi = zhaishi;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getHuiyuan() {
            return huiyuan;
        }

        public void setHuiyuan(int huiyuan) {
            this.huiyuan = huiyuan;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }


    }
}
