package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class CustTr extends BaseObject {
	private static final long serialVersionUID = 1L;
	private CustTrPK custTrPK;
	private Integer ordno;

	private Integer dueDt;

	private BigDecimal invoiceAmount;

	private BigDecimal restAmount;
	public static final CustTr UNKNOWN = new CustTr() {

		private static final long serialVersionUID = 1L;
	};

	public CustTr() {
		super();
	}

	public CustTr(final Integer aOrderNo, final Integer aDueDt,
			final BigDecimal aInvoiceAmount, final BigDecimal aRestAmount// ,
	) {
		super();
		this.ordno = aOrderNo;
		this.dueDt = aDueDt;
		this.invoiceAmount = aInvoiceAmount;
		this.restAmount = aRestAmount;
	}

	public final Integer getDueDt() {
		return dueDt;
	}

	public final void setDueDt(final Integer aDueDt) {
		this.dueDt = aDueDt;
	}

	public final BigDecimal getInvoiceAmount() {
		return invoiceAmount;
	}

	public final void setInvoiceAmount(final BigDecimal aInvoiceAmount) {
		this.invoiceAmount = aInvoiceAmount;
	}

	public final Integer getOrdno() {
		return ordno;
	}

	public final void setOrdno(final Integer aOrderNo) {
		this.ordno = aOrderNo;
	}

	public final BigDecimal getRestAmount() {
		return restAmount;
	}

	public final void setRestAmount(final BigDecimal aRestAmount) {
		this.restAmount = aRestAmount;
	}

	@Override
	public final String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"orderNo", ordno).append("dueDt", dueDt).append(
				"invoiceAmount", invoiceAmount)
				.append("restAmount", restAmount).toString();
	}

	public Date getDueDate() {
		if (dueDt != null) {
			try {
				return Util.DATE_FORMAT_YYYYMMDD.parse(String.valueOf(dueDt));
			} catch (ParseException e) {
			}
		}
		return null;
	}

	public CustTrPK getCustTrPK() {
		return custTrPK;
	}

	public void setCustTrPK(CustTrPK custTrPK) {
		this.custTrPK = custTrPK;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof CustTr))
			return false;
		CustTr castOther = (CustTr) other;
		return new EqualsBuilder().append(custTrPK, castOther.custTrPK)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(custTrPK).toHashCode();
	}

}
