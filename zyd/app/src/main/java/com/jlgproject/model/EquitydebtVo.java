package com.jlgproject.model;

import java.io.Serializable;

/**
 * Created by sunbeibei on 2017/6/28.
 */

public class EquitydebtVo implements Serializable {
    private String debtid;

    public EquityVo getEquityVo() {
        return equityVo;
    }

    public void setEquityVo(EquityVo equityVo) {
        this.equityVo = equityVo;
    }

    public String getDebtid() {
        return debtid;
    }

    public void setDebtid(String debtid) {
        this.debtid = debtid;
    }

    private EquityVo equityVo;

}
