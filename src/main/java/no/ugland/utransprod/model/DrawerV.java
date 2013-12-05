package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.Date;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class DrawerV extends BaseObject {
    private Integer customerNr;

    private String orderNr;

    //private Integer groupIdx;
    private Integer productAreaNr;

    private String customerName;

    private String categoryName;

    private Date registered;

    private BigDecimal ownProduction;

    private String drawerName;
    private Integer documentId;

    public DrawerV() {
        super();
    }

    public DrawerV(final Integer aCustomerNr, final String aOrderNr, 
            //final Integer aGroupIdx,
            final Integer aProductAreaNr,
            final String aCustomerName, final String aCategoryName, final Date isRegistered,
            final BigDecimal aOwnProduction, final String aDrawerName,final Integer aDocumentId) {
        super();
        this.customerNr = aCustomerNr;
        this.orderNr = aOrderNr;
        //this.groupIdx = aGroupIdx;
        this.productAreaNr=aProductAreaNr;
        this.customerName = aCustomerName;
        this.categoryName = aCategoryName;
        this.registered = isRegistered;
        this.ownProduction = aOwnProduction;
        this.drawerName = aDrawerName;
        this.documentId=aDocumentId;
    }

    public final String getCategoryName() {
        return categoryName;
    }

    public final void setCategoryName(final String aCategoryName) {
        this.categoryName = aCategoryName;
    }

    public final String getCustomerName() {
        return customerName;
    }

    public final void setCustomerName(final String aCustomerName) {
        this.customerName = aCustomerName;
    }

    public final Integer getCustomerNr() {
        return customerNr;
    }

    public final void setCustomerNr(final Integer aCustomerNr) {
        this.customerNr = aCustomerNr;
    }

    public final String getDrawerName() {
        return drawerName;
    }

    public final void setDrawerName(final String aDrawerName) {
        this.drawerName = aDrawerName;
    }

    /*public final Integer getGroupIdx() {
        return groupIdx;
    }

    public final void setGroupIdx(final Integer aGroupIdx) {
        this.groupIdx = aGroupIdx;
    }*/
    public final Integer getProductAreaNr() {
        return productAreaNr;
    }

    public final void setProductAreaNr(final Integer aProductAreaNr) {
        this.productAreaNr = aProductAreaNr;
    }

    public final String getOrderNr() {
        return orderNr;
    }

    public final void setOrderNr(final String aOrderNr) {
        this.orderNr = aOrderNr;
    }

    public final BigDecimal getOwnProduction() {
        return ownProduction;
    }

    public final void setOwnProduction(final BigDecimal aOwnProduction) {
        this.ownProduction = aOwnProduction;
    }

    public final Date getRegistered() {
        return registered;
    }

    public final void setRegistered(final Date isRegistered) {
        this.registered = isRegistered;
    }

    @Override
    public final String toString() {
        return new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE).append(
                "customerNr", customerNr).append("orderNr", orderNr).append(
                "productAreaNr", productAreaNr).append("customerName", customerName)
                .append("categoryName", categoryName).append("registered",
                        registered).append("ownProduction", ownProduction)
                .append("drawerName", drawerName).toString();
    }
    
    public final ProductArea getProductArea(){
        if(productAreaNr!=null){
            return Util.getProductAreaFromProductAreaNr(productAreaNr);
        }
        return null;
    }
    
    public String getRegisteredDate(){
        if(registered!=null){
            return Util.formatDate(registered,Util.SHORT_DATE_FORMAT);
        }
        return null;
    }

    public Integer getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Integer documentId) {
        this.documentId = documentId;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof DrawerV))
            return false;
        DrawerV castOther = (DrawerV) other;
        return new EqualsBuilder().append(customerNr, castOther.customerNr)
                .append(orderNr, castOther.orderNr).append(productAreaNr,
                        castOther.productAreaNr).append(documentId,
                        castOther.documentId).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(customerNr).append(orderNr).append(
                productAreaNr).append(documentId).toHashCode();
    }
}
