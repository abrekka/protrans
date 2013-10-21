package no.ugland.utransprod.model;

import java.util.Date;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Domeneklasse for tabell INFO
 * 
 * @author atle.brekka
 * 
 */
public class Info extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private Integer infoId;

	/**
	 * 
	 */
	private String infoText;

	/**
	 * 
	 */
	private Date dateFrom;

	/**
	 * 
	 */
	private Date dateTo;

	/**
	 * 
	 */
	public Info() {
		super();
	}

	/**
	 * @param infoId
	 * @param infoText
	 * @param dateFrom
	 * @param dateTo
	 */
	public Info(Integer infoId, String infoText, Date dateFrom, Date dateTo) {
		super();
		this.infoId = infoId;
		this.infoText = infoText;
		this.dateFrom = dateFrom;
		this.dateTo = dateTo;
	}

	/**
	 * @return fradato
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return tildato
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return id
	 */
	public Integer getInfoId() {
		return infoId;
	}

	/**
	 * @param infoId
	 */
	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	/**
	 * @return tekst
	 */
	public String getInfoText() {
		return infoText;
	}

	/**
	 * @param infoText
	 */
	public void setInfoText(String infoText) {
		this.infoText = infoText;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof Info))
			return false;
		Info castOther = (Info) other;
		return new EqualsBuilder().append(infoId, castOther.infoId).append(
				infoText, castOther.infoText).append(dateFrom,
				castOther.dateFrom).append(dateTo, castOther.dateTo).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(infoId).append(infoText).append(
				dateFrom).append(dateTo).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"infoText", infoText).toString();
	}
}
