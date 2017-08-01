package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/28.
 * 新增经营实体类
 */

public class ManageStateRequest implements Serializable {
    private Long debtId;//	债事人ID

    private String legalPersonName;        //法人名称
    private String phoneNumber;//		联系电话
    private String taxNumber;    //税号
    private String address;    //	联系地址
    private String year;//所属年度
    private String lastSales;//	上季度销售额
    private String lastElectricityBills;//年度电费
    private String perCapita;//	年度人均总值
    private String employeeNum;//	现有人员总数
    private String profitMargin;//	利润率

    public Long getDebtId() {
        return debtId;
    }

    public void setDebtId(Long debtId) {
        this.debtId = debtId;
    }



    public String getLegalPersonName() {
        return legalPersonName;
    }

    public void setLegalPersonName(String legalPersonName) {
        this.legalPersonName = legalPersonName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getTaxNumber() {
        return taxNumber;
    }

    public void setTaxNumber(String taxNumber) {
        this.taxNumber = taxNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getLastSales() {
        return lastSales;
    }

    public void setLastSales(String lastSales) {
        this.lastSales = lastSales;
    }

    public String getLastElectricityBills() {
        return lastElectricityBills;
    }

    public void setLastElectricityBills(String lastElectricityBills) {
        this.lastElectricityBills = lastElectricityBills;
    }

    public String getPerCapita() {
        return perCapita;
    }

    public void setPerCapita(String perCapita) {
        this.perCapita = perCapita;
    }

    public String getEmployeeNum() {
        return employeeNum;
    }

    public void setEmployeeNum(String employeeNum) {
        this.employeeNum = employeeNum;
    }

    public String getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(String profitMargin) {
        this.profitMargin = profitMargin;
    }

    public String getGross() {
        return gross;
    }

    public void setGross(String gross) {
        this.gross = gross;
    }

    public String getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(String totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    private String gross;//	总收入
    private String totalInvestment;//	总投资
}
