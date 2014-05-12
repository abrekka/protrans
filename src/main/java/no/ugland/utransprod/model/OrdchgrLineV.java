package no.ugland.utransprod.model;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class OrdchgrLineV extends BaseObject {

    private OrdchgrLineVPK ordchgrLineVPK;
    private Integer lineStatus;

    // "L,$lnNo,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,,1, 1, 1, 3";
    private static final String LN_NO_STRING = "$lnNo";
    private static final String LINE_STATUS_STRING = "$lineStatus";
    private static final String HEAD_START = "L;" + LN_NO_STRING;
    private static final int NUMBER_OF_SEMICOLONS = 71;
    private static final String HEAD_END = "1;1;" + LINE_STATUS_STRING + ";3";

    public OrdchgrLineVPK getOrdchgrLineVPK() {
	return ordchgrLineVPK;
    }

    public void setOrdchgrLineVPK(OrdchgrLineVPK ordchgrLinePK) {
	this.ordchgrLineVPK = ordchgrLinePK;
    }

    public Integer getLineStatus() {
	return lineStatus;
    }

    public void setLineStatus(Integer aLineStatus) {
	lineStatus = aLineStatus;
    }

    public String getLineLine() {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(HEAD_START).append(StringUtils.leftPad("", NUMBER_OF_SEMICOLONS, ";")).append(HEAD_END);
	String lineString = StringUtils.replaceOnce(stringBuilder.toString(), LN_NO_STRING, ordchgrLineVPK.getLnNo() != null ? ordchgrLineVPK
		.getLnNo().toString() : "");
	return StringUtils.replaceOnce(lineString, LINE_STATUS_STRING, lineStatus.toString());
    }

    public static String getLinje(Integer lnno, Integer numberOf) {
	StringBuilder stringBuilder = new StringBuilder();
	stringBuilder.append(HEAD_START).append(StringUtils.leftPad("", NUMBER_OF_SEMICOLONS, ";")).append(HEAD_END);
	String lineString = StringUtils.replaceOnce(stringBuilder.toString(), LN_NO_STRING, String.valueOf(lnno));
	return StringUtils.replaceOnce(lineString, LINE_STATUS_STRING, String.valueOf(numberOf));
    }

    @Override
    public String toString() {
	return new ToStringBuilder(this).append("ordchgrLinePK", ordchgrLineVPK).toString();
    }

    @Override
    public boolean equals(final Object other) {
	if (!(other instanceof OrdchgrLineV))
	    return false;
	OrdchgrLineV castOther = (OrdchgrLineV) other;
	return new EqualsBuilder().append(ordchgrLineVPK, castOther.ordchgrLineVPK).isEquals();
    }

    @Override
    public int hashCode() {
	return new HashCodeBuilder().append(ordchgrLineVPK).toHashCode();
    }

}
