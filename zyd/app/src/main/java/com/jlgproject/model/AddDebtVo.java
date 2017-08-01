package com.jlgproject.model;

/**
 * @author 王锋 on 2017/6/15.
 */

public class AddDebtVo {

    private String type;

    private String isOwer;

    private DebtPerson debtPerson;

    private DebtCompany debtCompany;

    public AddDebtVo() {
    }

    public AddDebtVo(String type, String isOwer, DebtPerson debtPerson, DebtCompany debtCompany) {
        this.type = type;
        this.isOwer = isOwer;
        this.debtPerson = debtPerson;
        this.debtCompany = debtCompany;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIsOwer() {
        return isOwer;
    }

    public void setIsOwer(String isOwer) {
        this.isOwer = isOwer;
    }

    public DebtPerson getDebtPerson() {
        return debtPerson;
    }

    public void setDebtPerson(DebtPerson debtPerson) {
        this.debtPerson = debtPerson;
    }

    public DebtCompany getDebtCompany() {
        return debtCompany;
    }

    public void setDebtCompany(DebtCompany debtCompany) {
        this.debtCompany = debtCompany;
    }
}
