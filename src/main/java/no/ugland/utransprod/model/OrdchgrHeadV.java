package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OrdchgrHeadV extends BaseObject {
    public static final String HEAD_LINE_TMP = "H;;%s;;;;;;;;;;;;;;;;;;;;;;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4";
    public static final String HEAD_LINE_PRODUCTION_WEEK_TMP = "H;;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4";
    public static final String HEAD_LINE_WITH_SUPPLIER_TMP = "H;;%s;;;%s;;;;;;;;;;;;;;;;;;;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4";
    // ord levnr trdato tur sj�f�r
    public static final String HEAD_LINE_DELIVERY_TMP = "H;;%s;;;%s;;;;;;;;;;;;;;;;;;;%s;;;;;%s;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4";
    private static final String HEAD_START = "H;;$OrdNo";
    private static final String HEAD_END = "4";
    private static final int NUMBER_OF_SEMICOLON = 77;
    private static final String ORD_NO_STRING = "$OrdNo";
    private Integer ordNo;

    public Integer getOrdNo() {
	return ordNo;
    }

    public void setOrdNo(Integer ordNo) {
	this.ordNo = ordNo;
    }

    public String getHeadLine(String transportDate) {
	return String.format(HEAD_LINE_TMP, ordNo != null ? ordNo.toString() : "", transportDate != null ? transportDate : "0");
    }

    public String getHeadLineForProductionWeek(Integer productionWeek) {
	return String.format(HEAD_LINE_PRODUCTION_WEEK_TMP, ordNo != null ? ordNo.toString() : "", productionWeek != null ? productionWeek : "");
    }

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof OrdchgrHeadV))
	    return false;
	OrdchgrHeadV castOther = (OrdchgrHeadV) other;
	return new EqualsBuilder().append(ordNo, castOther.ordNo).isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(ordNo).toHashCode();
    }

    @Override
    public String toString() {
	return new ToStringBuilder(this).append("ordNo", ordNo).toString();
    }
}
