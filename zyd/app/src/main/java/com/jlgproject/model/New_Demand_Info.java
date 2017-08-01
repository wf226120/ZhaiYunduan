package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/24.
 */

public class New_Demand_Info implements Serializable {
    private Long debtId;
    private String name;
    private int tangible;

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

    public String getTotalAmout() {
        return totalAmout;
    }

    public void setTotalAmout(String totalAmout) {
        this.totalAmout = totalAmout;
    }

    public String getDemandNum() {
        return demandNum;
    }

    public void setDemandNum(String demandNum) {
        this.demandNum = demandNum;
    }

    private String totalAmout;
    private String demandNum;
}
