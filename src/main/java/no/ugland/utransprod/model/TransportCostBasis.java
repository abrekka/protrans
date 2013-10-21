package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class TransportCostBasis extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer transportCostBasisId;

    private Supplier supplier;

    private Set<OrderCost> orderCosts;

    private String periode;

    private String invoiceNr;

    public TransportCostBasis() {

    }

    public TransportCostBasis(final Periode aPeriod) {
        periode = aPeriod.getFormattetYearFromWeekToWeek();
    }

    public final Integer getTransportCostBasisId() {
        return transportCostBasisId;
    }

    public final void setTransportCostBasisId(
            final Integer aTransportCostBasisId) {
        this.transportCostBasisId = aTransportCostBasisId;
    }

    public final Supplier getSupplier() {
        return supplier;
    }

    public final void setSupplier(final Supplier aSupplier) {
        this.supplier = aSupplier;
    }

    public final void addOrderCost(final OrderCost orderCost) {
        if (orderCosts == null) {
            orderCosts = new HashSet<OrderCost>();
        }
        orderCost.setTransportCostBasis(this);
        orderCosts.add(orderCost);
    }

    public final Set<OrderCost> getOrderCosts() {
        return orderCosts;
    }

    public final List<OrderCost> getOrderCostsSorted() {
        List<OrderCost> orderCostList = null;
        if (orderCosts != null) {
            orderCostList = new ArrayList<OrderCost>(orderCosts);
            Collections.sort(orderCostList, new OrderCostTransportComparator());
        }
        return orderCostList;
    }

    public final void setOrderCosts(final Set<OrderCost> someOrderCosts) {
        this.orderCosts = someOrderCosts;
    }

    public final String getPeriode() {
        return periode;
    }

    public final void setPeriode(final String aPeriode) {
        this.periode = aPeriode;
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "supplier", supplier).append("periode", periode).toString();
    }

    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof TransportCostBasis)){
            return false;
        }
        TransportCostBasis castOther = (TransportCostBasis) other;
        return new EqualsBuilder().append(transportCostBasisId,
                castOther.transportCostBasisId).append(supplier,
                castOther.supplier).append(periode, castOther.periode)
                .isEquals();
    }

    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(transportCostBasisId).append(
                supplier).append(periode).toHashCode();
    }

    public final String getInvoiceNr() {
        return invoiceNr;
    }

    public final void setInvoiceNr(final String aInvoiceNr) {
        this.invoiceNr = aInvoiceNr;
    }

    public final BigDecimal getTotalCost() {
        BigDecimal totalCost = BigDecimal.valueOf(0);
        if (orderCosts != null) {
            for (OrderCost cost : orderCosts) {
                totalCost = totalCost.add(Util.convertNullToBigDecimal(cost
                        .getCostAmount()));
            }
        }
        return totalCost;
    }

    public final Integer getTotalCount() {
        if (orderCosts != null) {
            return orderCosts.size();
        }
        return 0;
    }
}
