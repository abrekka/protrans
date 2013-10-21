package no.ugland.utransprod.model;

import java.io.Serializable;

public class CustTrPK implements Serializable{
    private Integer jno;
    private Integer entNo;
    public CustTrPK() {
    }
    public Integer getJno() {
        return jno;
    }
    public void setJno(Integer no) {
        jno = no;
    }
    public Integer getEntNo() {
        return entNo;
    }
    public void setEntNo(Integer entNo) {
        this.entNo = entNo;
    }
}
