package no.ugland.utransprod.model;

import java.math.BigDecimal;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class Udsalesmall extends BaseObject {
    private Integer udsalesmallId;

    private BigDecimal egenprodVerdiKunde;

    private BigDecimal fraktKunde;

    private BigDecimal monteringKunde;

    private BigDecimal kostEgenProd;
    private BigDecimal jaLinjer;
    private Integer orderDate;
    private Integer wantedDeliveryDate;

    public Udsalesmall() {
        super();
    }

    public Udsalesmall(final Integer aUdsalesmallId,
            final BigDecimal aEgenprodVerdiKunde, final BigDecimal aFraktKunde,
            final BigDecimal aMonteringKunde, final BigDecimal aKostEgenProd,
            final BigDecimal someJaLinjer,final Integer aOrderDate,final Integer aWantedDeliveryDate
            ) {
        super();
        this.udsalesmallId = aUdsalesmallId;
        this.egenprodVerdiKunde = aEgenprodVerdiKunde;
        this.fraktKunde = aFraktKunde;
        this.monteringKunde = aMonteringKunde;
        this.kostEgenProd=aKostEgenProd;
        this.jaLinjer=someJaLinjer;
        this.orderDate=aOrderDate;
        this.wantedDeliveryDate=aWantedDeliveryDate;
    }

    public final BigDecimal getEgenprodVerdiKunde() {
        return egenprodVerdiKunde;
    }

    public final void setEgenprodVerdiKunde(final BigDecimal aEgenprodVerdiKunde) {
        this.egenprodVerdiKunde = aEgenprodVerdiKunde;
    }

    public final BigDecimal getFraktKunde() {
        return fraktKunde;
    }

    public final void setFraktKunde(final BigDecimal aFraktKunde) {
        this.fraktKunde = aFraktKunde;
    }


    public final BigDecimal getMonteringKunde() {
        return monteringKunde;
    }

    public final void setMonteringKunde(final BigDecimal aMonteringKunde) {
        this.monteringKunde = aMonteringKunde;
    }

    /*public final Sale getSale() {
        return sale;
    }

    public final void setSale(final Sale aSale) {
        this.sale = aSale;
    }*/

    public final Integer getUdsalesmallId() {
        return udsalesmallId;
    }

    public final void setUdsalesmallId(final Integer aUdsalesmallId) {
        this.udsalesmallId = aUdsalesmallId;
    }

    

    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof Udsalesmall)) {
            return false;
        }
        Udsalesmall castOther = (Udsalesmall) other;
        return new EqualsBuilder().append(udsalesmallId,
                castOther.udsalesmallId).isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(udsalesmallId).toHashCode();
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "udsalesmallId", udsalesmallId).append("egenprodVerdiKunde",
                egenprodVerdiKunde).append("fraktKunde", fraktKunde).append(
                "monteringKunde", monteringKunde).toString();
    }

    public final BigDecimal getKostEgenProd() {
        return kostEgenProd;
    }

    public final void setKostEgenProd(final BigDecimal aKostEgenProd) {
        this.kostEgenProd = aKostEgenProd;
    }

    public BigDecimal getJaLinjer() {
        return jaLinjer;
    }

    public void setJaLinjer(BigDecimal jaLinjer) {
        this.jaLinjer = jaLinjer;
    }

    public Integer getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Integer orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getWantedDeliveryDate() {
        return wantedDeliveryDate;
    }

    public void setWantedDeliveryDate(Integer wantedDeliveryDate) {
        this.wantedDeliveryDate = wantedDeliveryDate;
    }
}
