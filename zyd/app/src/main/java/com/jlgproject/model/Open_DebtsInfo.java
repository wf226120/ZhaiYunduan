package com.jlgproject.model;

import com.jlgproject.util.L;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/14.
 */

public class Open_DebtsInfo implements Serializable {



    private String realname;
    private String idNumber;
    private String mobile;
    private String provinceId;
    private String cityId;
    private String countyId;
    private String recommendCode;


    public Open_DebtsInfo(String realname, String idNumber, String mobile, String provinceId, String cityId, String countyId, String recommendCode) {
        this.realname = realname;
        this.idNumber = idNumber;
        this.mobile = mobile;
        this.provinceId = provinceId;
        this.cityId = cityId;
        this.countyId = countyId;
        this.recommendCode = recommendCode;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountyId() {
        return countyId;
    }

    public void setCountyId(String countyId) {
        this.countyId = countyId;
    }

    public String getRecommendCode() {
        return recommendCode;
    }

    public void setRecommendCode(String recommendCode) {
        this.recommendCode = recommendCode;
    }
}
