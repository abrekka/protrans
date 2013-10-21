package no.ugland.utransprod.model;

import java.math.BigDecimal;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class TransportCost extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer transportCostId;

    private String postalCode;

    private String place;

    private Area area;

    private BigDecimal cost;

    private BigDecimal addition;

    private Integer valid;

    private Integer maxAddition;

    public TransportCost() {
        super();
    }

    public TransportCost(final Integer aTransportCostId, final String aPostalCode,
            final String aPlace, final Area aArea, final BigDecimal aCost, final BigDecimal aAddition,
            final Integer aValid, final Integer aMaxAddition) {
        super();
        this.transportCostId = aTransportCostId;
        this.postalCode = aPostalCode;
        this.place = aPlace;
        this.area = aArea;
        this.cost = aCost;
        this.addition = aAddition;
        this.valid = aValid;
        this.maxAddition = aMaxAddition;
    }

    public final BigDecimal getCost() {
        return cost;
    }

    public final void setCost(final BigDecimal aCost) {
        this.cost = aCost;
    }

    public final String getPlace() {
        return place;
    }

    public final void setPlace(final String aPlace) {
        this.place = aPlace;
    }

    public final String getPostalCode() {
        return postalCode;
    }

    public final void setPostalCode(final String aPostalCode) {
        this.postalCode = aPostalCode;
    }

    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof TransportCost)) {
            return false;
        }
        TransportCost castOther = (TransportCost) other;
        return new EqualsBuilder().append(postalCode, castOther.postalCode)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(postalCode).toHashCode();
    }

    public final Integer getTransportCostId() {
        return transportCostId;
    }

    public final void setTransportCostId(final Integer aTransportCostId) {
        this.transportCostId = aTransportCostId;
    }

    public final Integer getValid() {
        return valid;
    }

    public final void setValid(final Integer isValid) {
        this.valid = isValid;
    }

    public final Integer getMaxAddition() {
        return maxAddition;
    }

    public final void setMaxAddition(final Integer aMaxAddition) {
        this.maxAddition = aMaxAddition;
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "transportCostId", transportCostId).append("postalCode",
                postalCode).append("place", place).append("area", area).append(
                "cost", cost).append("addition", addition).append("valid",
                valid).append("maxAddition", maxAddition).toString();
    }

    public final BigDecimal getAddition() {
        return addition;
    }

    public final void setAddition(final BigDecimal aAddition) {
        this.addition = aAddition;
    }

    public final Area getArea() {
        return area;
    }

    public final void setArea(final Area aArea) {
        this.area = aArea;
    }

}
