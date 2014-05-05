package no.ugland.utransprod.model;

import java.math.BigDecimal;

public class FakturagrunnlagV {
    private FakturagrunnlagVPK fakturagrunnlagVPK;
    private String prodno;
    private BigDecimal price;
    private String orderNr;
    private BigDecimal free2;
    private String descr;
    private BigDecimal noof;
    private BigDecimal priceMont;
    private BigDecimal sumLine;
    private Integer purcno;

    public FakturagrunnlagVPK getFakturagrunnlagVPK() {
	return fakturagrunnlagVPK;
    }

    public void setFakturagrunnlagVPK(FakturagrunnlagVPK fakturagrunnlagVPK) {
	this.fakturagrunnlagVPK = fakturagrunnlagVPK;
    }

    public BigDecimal getSumLine() {
	return sumLine;
    }

    public void setSumLine(BigDecimal sumLine) {
	this.sumLine = sumLine;
    }

    public BigDecimal getNoof() {
	return noof;
    }

    public void setNoof(BigDecimal noof) {
	this.noof = noof;
    }

    public BigDecimal getPriceMont() {
	return priceMont;
    }

    public void setPriceMont(BigDecimal priceMont) {
	this.priceMont = priceMont;
    }

    public String getDescr() {
	return descr;
    }

    public void setDescr(String descr) {
	this.descr = descr;
    }

    public String getProdno() {
	return prodno;
    }

    public void setProdno(String prodno) {
	this.prodno = prodno;
    }

    public BigDecimal getPrice() {
	return price;
    }

    public void setPrice(BigDecimal price) {
	this.price = price;
    }

    public String getOrderNr() {
	return orderNr;
    }

    public void setOrderNr(String orderNr) {
	this.orderNr = orderNr;
    }

    public BigDecimal getFree2() {
	return free2;
    }

    public void setFree2(BigDecimal free2) {
	this.free2 = free2;
    }

    public Integer getPurcno() {
	return purcno;
    }

    public void setPurcno(Integer purcno) {
	this.purcno = purcno;
    }
}
