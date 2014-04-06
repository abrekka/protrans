package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Ord extends BaseObject {
    private Integer ordno;

    private String inf6;

    private String inf;

    private String free1;

    private String free2;

    private String yrRef;
    private Integer deldt;
    private Integer cfdeldt;

    public Ord() {
	super();
    }

    public Ord(final Integer aOrdno, final String aInf6) {
	super();
	this.ordno = aOrdno;
	this.inf6 = aInf6;
    }

    public final String getInf6() {
	return inf6;
    }

    public final void setInf6(final String aInf6) {
	this.inf6 = aInf6;
    }

    public final Integer getOrdno() {
	return ordno;
    }

    public final void setOrdno(final Integer aOrdno) {
	this.ordno = aOrdno;
    }

    @Override
    public final boolean equals(final Object other) {
	if (!(other instanceof Ord)) {
	    return false;
	}
	Ord castOther = (Ord) other;
	return new EqualsBuilder().append(ordno, castOther.ordno).isEquals();
    }

    @Override
    public final int hashCode() {
	return new HashCodeBuilder().append(ordno).toHashCode();
    }

    @Override
    public final String toString() {
	return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append("ordno", ordno).append("inf6", inf6).toString();
    }

    public String getInf() {
	return inf;
    }

    public void setInf(String aInf) {
	inf = aInf;

    }

    public String getFree1() {
	return free1;
    }

    public void setFree1(String string) {
	free1 = string;

    }

    public String getFree2() {
	return free2;
    }

    public void setFree2(String string) {
	free2 = string;

    }

    public String getYrRef() {
	return yrRef;
    }

    public void setYrRef(String yrRef) {
	this.yrRef = yrRef;
    }

    public Integer getDeldt() {
	return deldt;
    }

    public void setDeldt(Integer deldt) {
	this.deldt = deldt;
    }

    public Integer getCfdeldt() {
	return cfdeldt;
    }

    public void setCfdeldt(Integer cfdeldt) {
	this.cfdeldt = cfdeldt;
    }
}
