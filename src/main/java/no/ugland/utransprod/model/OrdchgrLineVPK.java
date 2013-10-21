package no.ugland.utransprod.model;

import java.io.Serializable;

public class OrdchgrLineVPK implements Serializable{
    private Integer ordNo;
    private Integer lnNo;
    public OrdchgrLineVPK(){
        
    }
    public OrdchgrLineVPK(Integer aOrdNo,Integer aLnNo){
        ordNo=aOrdNo;
        lnNo=aLnNo;
    }
    public Integer getOrdNo() {
        return ordNo;
    }
    public void setOrdNo(Integer ordNo) {
        this.ordNo = ordNo;
    }
    public Integer getLnNo() {
        return lnNo;
    }
    public void setLnNo(Integer lnNo) {
        this.lnNo = lnNo;
    }
    
}
