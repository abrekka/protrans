package no.ugland.utransprod.model;

import java.math.BigDecimal;

import no.ugland.utransprod.util.YesNoInteger;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.CompareToBuilder;

/**
 * Klasse som representerer tabell ORDER_COST
 * @author atle.brekka
 */
public class OrderCost extends BaseObject   {
    private static final long serialVersionUID = 1L;

    private Integer orderCostId;

    private Order order;

    private CostType costType;

    private CostUnit costUnit;

    private BigDecimal costAmount;

    private Integer inclVat;

    private Supplier supplier;

    private String invoiceNr;

    private Deviation deviation;

    private TransportCostBasis transportCostBasis;
    private PostShipment postShipment;
    private String comment;

    public OrderCost() {
        super();
    }

    /**
     * @param aOrderCostId 
     * @param aOrder 
     * @param aCostType 
     * @param aCostUnit 
     * @param aCostAmount 
     * @param aInclVat 
     * @param aSupplier 
     * @param aInvoiceNr 
     * @param aDeviation 
     * @param aTransportCostBasis 
     */
    public OrderCost(final Integer aOrderCostId, final Order aOrder,
            final CostType aCostType, final CostUnit aCostUnit,
            final BigDecimal aCostAmount, final Integer aInclVat,
            final Supplier aSupplier, final String aInvoiceNr,
            final Deviation aDeviation,
            final TransportCostBasis aTransportCostBasis,final PostShipment aPostShipment,final String aComment) {
        super();
        this.orderCostId = aOrderCostId;
        this.order = aOrder;
        this.costType = aCostType;
        this.costUnit = aCostUnit;
        this.costAmount = aCostAmount;

        this.inclVat = aInclVat;
        this.supplier = aSupplier;
        this.invoiceNr = aInvoiceNr;
        this.deviation = aDeviation;
        this.transportCostBasis = aTransportCostBasis;
        this.postShipment=aPostShipment;
        this.comment=aComment;
    }

    /**
     * @return beløp
     */
    public BigDecimal getCostAmount() {
        return costAmount;
    }

    /**
     * @param costAmount
     */
    public void setCostAmount(BigDecimal costAmount) {
        this.costAmount = costAmount;
    }

    /**
     * @return kostnadstype
     */
    public CostType getCostType() {
        return costType;
    }

    /**
     * @param costType
     */
    public void setCostType(CostType costType) {
        this.costType = costType;
    }

    /**
     * @return kostnadsenhet
     */
    public CostUnit getCostUnit() {
        return costUnit;
    }

    /**
     * @param costUnit
     */
    public void setCostUnit(CostUnit costUnit) {
        this.costUnit = costUnit;
    }

    /**
     * @return moms
     */
    public Integer getInclVat() {
        return inclVat;
    }

    /**
     * @param inclVat
     */
    public void setInclVat(Integer inclVat) {
        this.inclVat = inclVat;
    }

    /**
     * @return ordre
     */
    public Order getOrder() {
        return order;
    }

    /**
     * @param order
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * @return id
     */
    public Integer getOrderCostId() {
        return orderCostId;
    }

    /**
     * @param orderCostId
     */
    public void setOrderCostId(Integer orderCostId) {
        this.orderCostId = orderCostId;
    }

    /**
     * @return moms
     */
    public YesNoInteger isInclVat() {
        return new YesNoInteger(inclVat);
    }

    /**
     * @param isInclvat
     */
    public void setIsInclVat(YesNoInteger isInclvat) {

        inclVat = isInclvat.getIntegerValue();

    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("orderCostId", orderCostId)
                .append("order", order).append("costType", costType).append(
                        "costUnit", costUnit).append("costAmount", costAmount)
                .append("inclVat", inclVat).toString();
    }

    /**
     * @return fakturanummer
     */
    public String getInvoiceNr() {
        return invoiceNr;
    }

    /**
     * @param invoiceNr
     */
    public void setInvoiceNr(String invoiceNr) {
        this.invoiceNr = invoiceNr;
    }

    /**
     * @return leverandør
     */
    public Supplier getSupplier() {
        return supplier;
    }

    /**
     * @param supplier
     */
    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    /**
     * @return avvik
     */
    public Deviation getDeviation() {
        return deviation;
    }

    /**
     * @param deviation
     */
    public void setDeviation(Deviation deviation) {
        this.deviation = deviation;
    }

    public TransportCostBasis getTransportCostBasis() {
        return transportCostBasis;
    }

    public void setTransportCostBasis(TransportCostBasis transportCostBasis) {
        this.transportCostBasis = transportCostBasis;
    }

    public PostShipment getPostShipment() {
        return postShipment;
    }

    public void setPostShipment(PostShipment postShipment) {
        this.postShipment = postShipment;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

   
    public final Transport getTransport(){
        if(postShipment!=null){
            return postShipment.getTransport();
        }
        return order.getTransport();
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof OrderCost))
            return false;
        OrderCost castOther = (OrderCost) other;
        return new EqualsBuilder().append(orderCostId, castOther.orderCostId)
                .append(costType, castOther.costType).append(costUnit,
                        castOther.costUnit).append(costAmount,
                        castOther.costAmount).append(supplier,
                        castOther.supplier).append(invoiceNr,
                        castOther.invoiceNr).append(comment, castOther.comment)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(orderCostId).append(costType)
                .append(costUnit).append(costAmount).append(supplier).append(
                        invoiceNr).append(comment).toHashCode();
    }
   
}
