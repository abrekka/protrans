package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class DelAlt extends BaseObject {
    private String prodNo;
    private Integer free1;
    private Integer free2;

    public String getProdNo() {
	return prodNo;
    }

    public Integer getFree1() {
	return free1;
    }

    public Integer getFree2() {
	return free2;
    }

    public void setFree1(Integer free1) {
	this.free1 = free1;
    }

    public void setFree2(Integer free2) {
	this.free2 = free2;
    }

    public void setProdNo(String prodNo) {
	this.prodNo = prodNo;
    }

    @Override
    public String toString() {
	return prodNo;
    }

    @Override
    public boolean equals(Object other) {
	if (!(other instanceof DelAlt)) {
	    return false;
	}
	DelAlt castOther = (DelAlt) other;
	return new EqualsBuilder().append(prodNo, castOther.prodNo).isEquals();
    }

    @Override
    public final int hashCode() {
	return new HashCodeBuilder().append(prodNo).toHashCode();
    }

}
