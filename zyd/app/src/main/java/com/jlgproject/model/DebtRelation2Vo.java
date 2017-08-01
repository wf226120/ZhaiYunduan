package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王锋 on 2017/6/7.
 */

public class DebtRelation2Vo implements Serializable {

    private List<String> identification;
    private List<String> evidence;
    private List<String> electron;
    private String proof;
    private String mortgagee;
    private String attorn;
    private String lawsuit;


    public DebtRelation2Vo() {
    }

    public List<String> getIdentification() {
        return identification;
    }

    public void setIdentification(List<String> identification) {
        this.identification = identification;
    }

    public List<String> getEvidence() {
        return evidence;
    }

    public void setEvidence(List<String> evidence) {
        this.evidence = evidence;
    }

    public List<String> getElectron() {
        return electron;
    }

    public void setElectron(List<String> electron) {
        this.electron = electron;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }

    public String getMortgagee() {
        return mortgagee;
    }

    public void setMortgagee(String mortgagee) {
        this.mortgagee = mortgagee;
    }

    public String getAttorn() {
        return attorn;
    }

    public void setAttorn(String attorn) {
        this.attorn = attorn;
    }

    public String getLawsuit() {
        return lawsuit;
    }

    public void setLawsuit(String lawsuit) {
        this.lawsuit = lawsuit;
    }
}
