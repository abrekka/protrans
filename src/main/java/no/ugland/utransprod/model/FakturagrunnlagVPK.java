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

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((lnno == null) ? 0 : lnno.hashCode());
	result = prime * result + ((orderId == null) ? 0 : orderId.hashCode());
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
	FakturagrunnlagVPK other = (FakturagrunnlagVPK) obj;
	if (lnno == null) {
	    if (other.lnno != null)
		return false;
	} else if (!lnno.equals(other.lnno))
	    return false;
	if (orderId == null) {
	    if (other.orderId != null)
		return false;
	} else if (!orderId.equals(other.orderId))
	    return false;
	return true;
    }

}
