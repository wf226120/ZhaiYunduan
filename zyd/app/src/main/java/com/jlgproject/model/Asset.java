package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王锋 on 2017/6/26.
 */

public class Asset implements Serializable {

    private Long id;//资产ID
    private String name;//资产名称
    private int tangible;//资产性质分类：有形资产1 无形资产0
    private Long totalAmout;//总价值
    private Long assetNum;//资产数量
    private Long tradeableAssets;//可流通资产价值
    private String assetCredential;//资产凭证
    private String assetDetails;//资产详情
    private String mortgage;//抵押等数量和价值
    private int isLawsuit;//是否已诉讼 ；0代表未诉讼，1代表已诉讼
    private String currentAsset;//流通资产
    private String belongTo;//归属人
    private String location;//所在位置
    private String phoneNumber;//联系电话
    private String restrictReasion;//限制流通原因
    private String mortgageTarget;//质押对象
    private String restrictWorth;//限制流通价值
    private String restrictNum;//限制流通负债量
    private List<String> picUrl;//上传资产图片

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

    public Long getAssetNum() {
        return assetNum;
    }

    public void setAssetNum(Long assetNum) {
        this.assetNum = assetNum;
    }

    public Long getTradeableAssets() {
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
