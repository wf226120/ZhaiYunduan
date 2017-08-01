package com.jlgproject.model;

import java.io.Serializable;

/**
 * @author 王锋 on 2017/6/27.
 */

public class Demand implements Serializable {

    private Long id;
    private String name;
    private int tangible;
    private Long totalAmout;
    private int assetNum;


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

    public int getTangible() {
        return tangible;
    }

    public void setTangible(int tangible) {
        this.tangible = tangible;
    }

    public Long getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(Long totalAmout) {
        this.totalAmout = totalAmout;
    }

    public int getAssetNum() {
        return assetNum;
    }

    public void setAssetNum(int assetNum) {
        this.assetNum = assetNum;
    }
}
