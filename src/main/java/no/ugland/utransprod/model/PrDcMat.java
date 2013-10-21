package no.ugland.utransprod.model;

import java.math.BigDecimal;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class PrDcMat extends BaseObject{
    private String prodno;
    private BigDecimal cstpr;
    public PrDcMat() {
        super();
    }
    public PrDcMat(String prodno, BigDecimal cstpr) {
        super();
        this.prodno = prodno;
        this.cstpr = cstpr;
    }
    public String getProdno() {
        return prodno;
    }
    public void setProdno(String prodno) {
        this.prodno = prodno;
    }
    public BigDecimal getCstpr() {
        return cstpr;
    }
    public void setCstpr(BigDecimal cstpr) {
        this.cstpr = cstpr;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof PrDcMat))
            return false;
        PrDcMat castOther = (PrDcMat) other;
        return new EqualsBuilder().append(prodno, castOther.prodno).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(prodno).toHashCode();
    }
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prodno", prodno).append("cstpr", cstpr).toString();
    }
}
