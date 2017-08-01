package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王锋 on 2017/6/15.
 */
public class DebtCompany implements Serializable {

    private String organCode;//组织机构代码
    private String debtCompanyName;//债事企业名称
    private String name;//法人姓名
    private String idCode;//身份证号
    private String provinceCode;//所属省
    private String cityCode;//市
    private String prCode;//区
    private String category;//所属行业
    private String phoneNumber;//联系电话
    private String registeredCapital;//注册资本
    private String email;//电子邮箱
    private String qq;//qq
    private String weChat;
    private List<String> picList;



    public DebtCompany() {
    }

    public String getOrganCode() {
        return organCode;
    }

    public void setOrganCode(String organCode) {
        this.organCode = organCode;
    }

    public String getDebtCompanyName() {
        return debtCompanyName;
    }

    public void setDebtCompanyName(String debtCompanyName) {
        this.debtCompanyName = debtCompanyName;
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

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getPrCode() {
        return prCode;
    }

    public void setPrCode(String prCode) {
        this.prCode = prCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRegisteredCapital() {
        return registeredCapital;
    }

    public void setRegisteredCapital(String registeredCapital) {
        this.registeredCapital = registeredCapital;
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

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}
