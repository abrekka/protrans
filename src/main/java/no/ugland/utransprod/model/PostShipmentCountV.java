package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * Klasse for view POST_SHIPMENT_COUNT_V
 * 
 * @author atle.brekka
 * 
 */
public class PostShipmentCountV extends BaseObject {
	/**
	 * 
	 */
	private static final long serialVersionUID = -363506928849900129L;

	/**
	 * 
	 */
	private PostShipmentCountVPK postShipmentCountVPK;

	/**
	 * 
	 */
	private Integer postShipmentCount;

	/**
	 * 
	 */
	public PostShipmentCountV() {
		super();
	}

	/**
	 * @param postShipmentCountVPK
	 * @param postShipmentCount
	 */
	public PostShipmentCountV(PostShipmentCountVPK postShipmentCountVPK,
			Integer postShipmentCount) {
		super();
		this.postShipmentCountVPK = postShipmentCountVPK;
		this.postShipmentCount = postShipmentCount;
	}

	/**
	 * @return antall ettersendinger
	 */
	public Integer getPostShipmentCount() {
		return postShipmentCount;
	}

	/**
	 * @param postShipmentCount
	 */
	public void setPostShipmentCount(Integer postShipmentCount) {
		this.postShipmentCount = postShipmentCount;
	}

	/**
	 * @return nøkkel
	 */
	public PostShipmentCountVPK getPostShipmentCountVPK() {
		return postShipmentCountVPK;
	}

	/**
	 * @param postShipmentCountVPK
	 */
	public void setPostShipmentCountVPK(
			PostShipmentCountVPK postShipmentCountVPK) {
		this.postShipmentCountVPK = postShipmentCountVPK;
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof PostShipmentCountV))
			return false;
		PostShipmentCountV castOther = (PostShipmentCountV) other;
		return new EqualsBuilder().append(postShipmentCountVPK,
				castOther.postShipmentCountVPK).isEquals();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#hashCode()
	 */
	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(postShipmentCountVPK).toHashCode();
	}

	/**
	 * @see no.ugland.utransprod.model.BaseObject#toString()
	 */
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
				"postShipmentCountVPK", postShipmentCountVPK).append(
				"postShipmentCount", postShipmentCount).toString();
	}

	/**
	 * @return sendt uke
	 */
	public Integer getSentWeek() {
		if (postShipmentCountVPK != null) {
			return postShipmentCountVPK.getSentWeek();
		}
		return null;
	}

	/**
	 * @return sendt år
	 */
	public Integer getSentYear() {
		if (postShipmentCountVPK != null) {
			return postShipmentCountVPK.getSentYear();
		}
		return null;
	}

	/**
	 * @return funksjonsnavn
	 */
	public String getJobFunctionName() {
		if (postShipmentCountVPK != null) {
			return postShipmentCountVPK.getJobFunctionName();
		}
		return null;
	}
}
