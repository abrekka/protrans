package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Prod extends BaseObject {
    private static final long serialVersionUID = 1L;
    private String prodNo;
    private Integer prCatNo;
    private Integer prCatNo2;
    private Integer prCatNo3;
    private String inf;

    public Prod() {
	super();
    }

    public Prod(final String aProdNo, final Integer aPrCatNo, final Integer aPrCatNo2, final Integer aPrCatNo3) {
	super();
	this.prodNo = aProdNo;
	this.prCatNo = aPrCatNo;
	this.prCatNo2 = aPrCatNo2;
	this.prCatNo3 = aPrCatNo3;
    }

    public Integer getPrCatNo3() {
	return prCatNo3;
    }

    public void setPrCatNo3(Integer prCatNo3) {
	this.prCatNo3 = prCatNo3;
    }

    public final String getProdNo() {
	return prodNo;
    }

    public final void setProdNo(final String aProdNo) {
	this.prodNo = aProdNo;
    }

    public final Integer getPrCatNo() {
	return prCatNo;
    }

    public final void setPrCatNo(final Integer aPrCatNo) {
	this.prCatNo = aPrCatNo;
    }

    public final Integer getPrCatNo2() {
	return prCatNo2;
    }

    public final void setPrCatNo2(final Integer aPrCatNo2) {
	this.prCatNo2 = aPrCatNo2;
    }

    @Override
    public final boolean equals(final Object other) {
	if (!(other instanceof Prod)) {
	    return false;
	}
	Prod castOther = (Prod) other;
	return new EqualsBuilder().append(prodNo, castOther.prodNo).isEquals();
    }

    @Override
    public final int hashCode() {
	return new HashCodeBuilder().append(prodNo).toHashCode();
    }

    @Override
    public final String toString() {
	return new ToStringBuilder(this).append("prodNo", prodNo).append("prCatNo", prCatNo).append("prCatNo2", prCatNo2).toString();
    }

    public String getInf() {
	return inf;
    }

    public void setInf(String aInf) {
	inf = aInf;
    }

}
