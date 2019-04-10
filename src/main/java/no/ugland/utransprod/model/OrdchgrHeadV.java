package no.ugland.utransprod.model;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OrdchgrHeadV extends BaseObject {
	// purcno hvem type timer
	public static final String HEAD_LINE = "H;;%s;;;;;%s;;;;;;;;;;;;;;;;;;;;;;;%s;;;;;;;;;;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4";

	public static final String HEAD_LINE_TMP = "H;;%s;;;;;;;;;;;;;;;;;;;;;;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4";

	public static final String HEAD_LINE_PRODUCTION_WEEK_TMP = "H;;%s;;;;;;;;;;;;;;;;;;;;;;%s;;;;;;%02d;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4";
	public static final String HEAD_LINE_WITH_SUPPLIER_TMP = "H;;%s;;;%s;;;;;;;;;;;;;;;;;;;%s;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;4";
	// ord levnr trdato tur sjåfør
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
		return String.format(HEAD_LINE_TMP, ordNo != null ? ordNo.toString() : "",
				transportDate != null ? transportDate : "0");
	}

	public String getHeadLineForProductionWeek(Integer productionWeek) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance();
		if (productionWeek != null) {
			cal.set(Calendar.WEEK_OF_YEAR, productionWeek - 1);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		}
		return String.format(HEAD_LINE_PRODUCTION_WEEK_TMP, ordNo != null ? ordNo.toString() : "",
				productionWeek != null ? dateFormat.format(cal.getTime()) : "",
				productionWeek != null ? productionWeek : "");
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
