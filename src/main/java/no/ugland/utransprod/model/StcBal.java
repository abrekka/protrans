package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class StcBal extends BaseObject{
    private String prodNo;
    private Integer minBal;
    public String getProdNo() {
        return prodNo;
    }
    public void setProdNo(String prodno) {
        this.prodNo = prodno;
    }
    public Integer getMinBal() {
        return minBal;
    }
    public void setMinBal(Integer minBal) {
        this.minBal = minBal;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof StcBal))
            return false;
        StcBal castOther = (StcBal) other;
        return new EqualsBuilder().append(prodNo, castOther.prodNo).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(prodNo).toHashCode();
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prodno", prodNo).append("minBal", minBal).toString();
    }
    
}
