package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sunbeibei on 2017/6/23.
 */

public class AssetRequest implements Serializable {

    private Long debtId;
    //资产名称
    private String name;
    //资产数量
    private Long assetNum;
    //总价值
    private Long totalAmout;
    //资产详情
    private String assetDetails;
    //资产凭证
    private String assetCredential;
    //是否已诉讼
    private int isLawsuit;
    //流通资产
    private String currentAsset;
    //抵押等数量和价值
    private String mortgage;
    //所在位置
    private String location;
    //归属人
    private String belongTo;
    //联系电话
    private String phoneNumber;
    //可流通资产价值
    private Long tradeableAssets;
    //质押对象
    private String mortgageTarget;
    //限制流通原因
    private String restrictReasion;
    //限制流通负债量
    private String restrictNum;
    //限制流通价值
    private String restrictWorth;
    //有形资产1 无形资产0
    private int tangible;

    private List<String> picUrl;


    public Long getDebtId() {
        return debtId;
    }

    public void setDebtId(Long debtId) {
        this.debtId = debtId;
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

    public Long getAssetNum() {
        return assetNum;
    }

    public void setAssetNum(Long assetNum) {
        this.assetNum = assetNum;
    }

    public Long	 getTradeableAssets() {
        return tradeableAssets;
    }

    public void setTradeableAssets(Long tradeableAssets) {
        this.tradeableAssets = tradeableAssets;
    }

    public String getAssetCredential() {
        return assetCredential;
    }

    public void setAssetCredential(String assetCredential) {
        this.assetCredential = assetCredential;
    }

    public String getAssetDetails() {
        return assetDetails;
    }

    public void setAssetDetails(String assetDetails) {
        this.assetDetails = assetDetails;
    }

    public String getMortgage() {
        return mortgage;
    }

    public void setMortgage(String mortgage) {
        this.mortgage = mortgage;
    }

    public int getIsLawsuit() {
        return isLawsuit;
    }

    public void setIsLawsuit(int isLawsuit) {
        this.isLawsuit = isLawsuit;
    }

    public String getCurrentAsset() {
        return currentAsset;
    }

    public void setCurrentAsset(String currentAsset) {
        this.currentAsset = currentAsset;
    }

    public String getBelongTo() {
        return belongTo;
    }

    public void setBelongTo(String belongTo) {
        this.belongTo = belongTo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getRestrictReasion() {
        return restrictReasion;
    }

    public void setRestrictReasion(String restrictReasion) {
        this.restrictReasion = restrictReasion;
    }

    public String getMortgageTarget() {
        return mortgageTarget;
    }

    public void setMortgageTarget(String mortgageTarget) {
        this.mortgageTarget = mortgageTarget;
    }

    public String getRestrictWorth() {
        return restrictWorth;
    }

    public void setRestrictWorth(String restrictWorth) {
        this.restrictWorth = restrictWorth;
    }

    public String getRestrictNum() {
        return restrictNum;
    }

    public void setRestrictNum(String restrictNum) {
        this.restrictNum = restrictNum;
    }

    public List<String> getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(List<String> picUrl) {
        this.picUrl = picUrl;
    }
}
