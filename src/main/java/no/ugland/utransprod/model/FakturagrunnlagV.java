package no.ugland.utransprod.model;

import java.math.BigDecimal;

public class FakturagrunnlagV {
    private static final String FRAKT = "FRAKT";
    private static final String GARFRAKT = "GARFRAKT";
    private static final String MONTERING_VILLA = "MONTERING VILLA";
    private static final String KRANBIL = "KRANBIL";
    private static final String AVFALLSFJERNING = "AVFALLSFJERNING MONTASJE";
    private static final String MONTERINGSSPIKER = "MONTSPIKER";
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
    private Integer lnPurcno;
    private Integer alloc;

    public FakturagrunnlagVPK getFakturagrunnlagVPK() {
	return fakturagrunnlagVPK;
    }

    public void setFakturagrunnlagVPK(FakturagrunnlagVPK fakturagrunnlagVPK) {
	this.fakturagrunnlagVPK = fakturagrunnlagVPK;
    }

    public BigDecimal getSumLine() {
	return sumLine;
    }

    public BigDecimal getSumLineMedVerdi() {
	return sumLine.intValue() == 0 ? null : sumLine;
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

    public BigDecimal getPriceMontMedVerdi() {
	return priceMont.intValue() == 0 ? null : priceMont;
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

    public Integer getLnPurcno() {
	return lnPurcno;
    }

    public void setLnPurcno(Integer lnPurcno) {
	this.lnPurcno = lnPurcno;
    }

    public Integer getAlloc() {
	return alloc;
    }

    public void setAlloc(Integer alloc) {
	this.alloc = alloc;
    }

    public FakturagrunnlagV medPurcno(Integer purcno) {
	this.purcno = purcno;
	return this;
    }

    public FakturagrunnlagV medLnPurcno(Integer lnPurcno) {
	this.lnPurcno = lnPurcno;
	return this;
    }

    public FakturagrunnlagV medAlloc(Integer alloc) {
	this.alloc = alloc;
	return this;
    }

    public boolean erFrakt() {
	return FRAKT.equalsIgnoreCase(getProdno()) || GARFRAKT.equalsIgnoreCase(getProdno());
    }

    public boolean erMontering() {
	return MONTERING_VILLA.equalsIgnoreCase(getProdno());
    }

    public boolean erKranbil() {
	return KRANBIL.equalsIgnoreCase(getProdno());
    }

    public boolean erAvfallsfjerning() {
	return AVFALLSFJERNING.equalsIgnoreCase(getProdno());
    }

    public boolean erMonteringsspiker() {
	return MONTERINGSSPIKER.equalsIgnoreCase(getProdno());
    }

    public boolean harBestillingsnr() {
	return getPurcno() != null && getPurcno() != 0;
    }

    public FakturagrunnlagV medProdno(String prodno) {
	this.prodno = prodno;
	return this;
    }
}
