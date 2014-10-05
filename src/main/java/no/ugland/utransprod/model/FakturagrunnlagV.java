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
    private BigDecimal orgPriceMont;

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
	return orgPriceMont.compareTo(BigDecimal.valueOf(0.01)) == 0 ? null : sumLine;
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
	return orgPriceMont.compareTo(BigDecimal.valueOf(0.01)) == 0 ? null : priceMont;
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

    public BigDecimal getOrgPriceMont() {
	return orgPriceMont;
    }

    public void setOrgPriceMont(BigDecimal orgPriceMont) {
	this.orgPriceMont = orgPriceMont;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((alloc == null) ? 0 : alloc.hashCode());
	result = prime * result + ((descr == null) ? 0 : descr.hashCode());
	result = prime * result + ((fakturagrunnlagVPK == null) ? 0 : fakturagrunnlagVPK.hashCode());
	result = prime * result + ((free2 == null) ? 0 : free2.hashCode());
	result = prime * result + ((lnPurcno == null) ? 0 : lnPurcno.hashCode());
	result = prime * result + ((noof == null) ? 0 : noof.hashCode());
	result = prime * result + ((orderNr == null) ? 0 : orderNr.hashCode());
	result = prime * result + ((orgPriceMont == null) ? 0 : orgPriceMont.hashCode());
	result = prime * result + ((price == null) ? 0 : price.hashCode());
	result = prime * result + ((priceMont == null) ? 0 : priceMont.hashCode());
	result = prime * result + ((prodno == null) ? 0 : prodno.hashCode());
	result = prime * result + ((purcno == null) ? 0 : purcno.hashCode());
	result = prime * result + ((sumLine == null) ? 0 : sumLine.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FakturagrunnlagV other = (FakturagrunnlagV) obj;
	if (alloc == null) {
	    if (other.alloc != null)
		return false;
	} else if (!alloc.equals(other.alloc))
	    return false;
	if (descr == null) {
	    if (other.descr != null)
		return false;
	} else if (!descr.equals(other.descr))
	    return false;
	if (fakturagrunnlagVPK == null) {
	    if (other.fakturagrunnlagVPK != null)
		return false;
	} else if (!fakturagrunnlagVPK.equals(other.fakturagrunnlagVPK))
	    return false;
	if (free2 == null) {
	    if (other.free2 != null)
		return false;
	} else if (!free2.equals(other.free2))
	    return false;
	if (lnPurcno == null) {
	    if (other.lnPurcno != null)
		return false;
	} else if (!lnPurcno.equals(other.lnPurcno))
	    return false;
	if (noof == null) {
	    if (other.noof != null)
		return false;
	} else if (!noof.equals(other.noof))
	    return false;
	if (orderNr == null) {
	    if (other.orderNr != null)
		return false;
	} else if (!orderNr.equals(other.orderNr))
	    return false;
	if (orgPriceMont == null) {
	    if (other.orgPriceMont != null)
		return false;
	} else if (!orgPriceMont.equals(other.orgPriceMont))
	    return false;
	if (price == null) {
	    if (other.price != null)
		return false;
	} else if (!price.equals(other.price))
	    return false;
	if (priceMont == null) {
	    if (other.priceMont != null)
		return false;
	} else if (!priceMont.equals(other.priceMont))
	    return false;
	if (prodno == null) {
	    if (other.prodno != null)
		return false;
	} else if (!prodno.equals(other.prodno))
	    return false;
	if (purcno == null) {
	    if (other.purcno != null)
		return false;
	} else if (!purcno.equals(other.purcno))
	    return false;
	if (sumLine == null) {
	    if (other.sumLine != null)
		return false;
	} else if (!sumLine.equals(other.sumLine))
	    return false;
	return true;
    }

}
