package no.ugland.utransprod.model;

import java.math.BigDecimal;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TransportCostAddition extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer transportCostAdditionId;

    private String description;

    private String basis;

    private BigDecimal addition;

    private String metric;
    private Integer transportBasis;
    private Integer memberOfMaxAdditions;

	private String basis2;

    public TransportCostAddition() {
        super();
    }

    public TransportCostAddition(final Integer aTransportCostAdditionId,
            final String aDescription, final String aBasis,
            final BigDecimal aAddition, final String aMetric,final Integer aTransportBasis,final Integer isMemberOfMaxAdditions) {
        super();
        this.transportCostAdditionId = aTransportCostAdditionId;
        this.description = aDescription;
        this.basis = aBasis;
        this.addition = aAddition;
        this.metric = aMetric;
        this.transportBasis=aTransportBasis;
        this.memberOfMaxAdditions=isMemberOfMaxAdditions;
    }

    public final BigDecimal getAddition() {
        return addition;
    }

    public final void setAddition(final BigDecimal aAddition) {
        this.addition = aAddition;
    }

    public final String getBasis() {
        return basis;
    }

    public final void setBasis(final String aBasis) {
        this.basis = aBasis;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String aDescription) {
        this.description = aDescription;
    }

    public final String getMetric() {
        return metric;
    }

    public final void setMetric(final String aMetric) {
        this.metric = aMetric;
    }

    public final Integer getTransportCostAdditionId() {
        return transportCostAdditionId;
    }

    public final void setTransportCostAdditionId(final Integer aTransportCostAdditionId) {
        this.transportCostAdditionId = aTransportCostAdditionId;
    }

    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof TransportCostAddition)){
            return false;
        }
        TransportCostAddition castOther = (TransportCostAddition) other;
        return new EqualsBuilder().append(description, castOther.description)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(description).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "description", description).toString();
    }

    public Integer getTransportBasis() {
        return transportBasis;
    }

    public void setTransportBasis(Integer transportBasis) {
        this.transportBasis = transportBasis;
    }

    public final boolean isMemberOfMaxAddition(){
        return Util.convertNumberToBoolean(memberOfMaxAdditions);
    }

    public Integer getMemberOfMaxAdditions() {
        return memberOfMaxAdditions;
    }

    public void setMemberOfMaxAdditions(Integer memberOfMaxAdditions) {
        this.memberOfMaxAdditions = memberOfMaxAdditions;
    }

	public void setBasis2(String basis2) {
		this.basis2=basis2;
	}
	public String getBasis2() {
		return basis2;
	}
}
