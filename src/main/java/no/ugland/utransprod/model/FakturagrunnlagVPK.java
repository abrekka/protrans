package no.ugland.utransprod.model;

import java.io.Serializable;

public class FakturagrunnlagVPK implements Serializable {
    private Integer lnno;

    private Integer orderId;

    public FakturagrunnlagVPK() {
	super();
    }

    public FakturagrunnlagVPK(final Integer aLnno, final Integer aOrderId) {
	super();
	this.lnno = aLnno;
	this.orderId = aOrderId;
    }

    public final Integer getLnno() {
	return lnno;
    }

    public final void setLnno(final Integer aLnno) {
	this.lnno = aLnno;
    }

    public final Integer getOrderId() {
	return orderId;
    }

    public final void setOrderId(final Integer aOrderId) {
	this.orderId = aOrderId;
    }

}
