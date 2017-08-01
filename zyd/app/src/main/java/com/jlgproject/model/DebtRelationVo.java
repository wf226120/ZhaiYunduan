package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王锋 on 2017/6/6.
 */

public class DebtRelationVo implements Serializable {

    private DebtRelation1Vo debtRelation1Vo;
    private DebtRelation2Vo debtRelation2Vo;
    private List<String> picList;


    public DebtRelationVo() {
    }

    public DebtRelationVo(DebtRelation1Vo debtRelation1Vo, DebtRelation2Vo debtRelation2Vo, List<String> picList) {
        this.debtRelation1Vo = debtRelation1Vo;
        this.debtRelation2Vo = debtRelation2Vo;
        this.picList = picList;
    }



    public void setDebtRelation1Vo(DebtRelation1Vo debtRelation1Vo) {
        this.debtRelation1Vo = debtRelation1Vo;
    }

    public DebtRelation2Vo getDebtRelation2Vo() {
        return debtRelation2Vo;
    }

    public void setDebtRelation2Vo(DebtRelation2Vo debtRelation2Vo) {
        this.debtRelation2Vo = debtRelation2Vo;
    }

    public List<String> getPicList() {
        return picList;
    }

    public void setPicList(List<String> picList) {
        this.picList = picList;
    }
}
