package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/7.
 */

public class DebtRelation1Vo implements Serializable{

    private String startId;
    private String endId;
    private String isDebt;
    private String isPresident;
    private String amout;
    private String natureOf;
    private String nature;
    private String genre;
    private String isLawsuit;
    private String recordTime;
    private String recommend;


    public DebtRelation1Vo() {
    }

    public String getStartId() {
        return startId;
    }

    public void setStartId(String startId) {
        this.startId = startId;
    }

    public String getEndId() {
        return endId;
    }

    public void setEndId(String endId) {
        this.endId = endId;
    }

    public String getIsDebt() {
        return isDebt;
    }

    public void setIsDebt(String isDebt) {
        this.isDebt = isDebt;
    }

    public String getIsPresident() {
        return isPresident;
    }

    public void setIsPresident(String isPresident) {
        this.isPresident = isPresident;
    }

    public String getAmout() {
        return amout;
    }

    public void setAmout(String amout) {
        this.amout = amout;
    }

    public String getNatureOf() {
        return natureOf;
    }

    public void setNatureOf(String natureOf) {
        this.natureOf = natureOf;
    }

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getIsLawsuit() {
        return isLawsuit;
    }

    public void setIsLawsuit(String isLawsuit) {
        this.isLawsuit = isLawsuit;
    }

    public String getRecommend() {
        return recommend;
    }

    public void setRecommend(String recommend) {
        this.recommend = recommend;
    }
}
