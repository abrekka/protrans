package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Domeneklasse for tabell PRODUCT_AREA
 * @author atle.brekka
 */
public class ProductArea extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer productAreaId;

    private String productArea;

    private Integer sortNr;

    private ProductAreaGroup productAreaGroup;

    private BigDecimal ownProductionCostLimit;
    private String productAreaNr;
    private Integer packlistReady;

    /**
     * 
     */
    public ProductArea() {
        super();
    }

    public ProductArea(Integer productAreaId, String productArea,
            Integer sortNr, ProductAreaGroup productAreaGroup,
           final String aProductAreaNr,
            final BigDecimal aOwnProductionCostLimit,final Integer setPacklistReady) {
        super();
        this.productAreaId = productAreaId;
        this.productArea = productArea;
        this.sortNr = sortNr;
        this.productAreaGroup = productAreaGroup;
        this.ownProductionCostLimit=aOwnProductionCostLimit;
        this.productAreaNr=aProductAreaNr;
        this.packlistReady=setPacklistReady;
    }

    /**
     * @return produktområde
     */
    public String getProductArea() {
        return productArea;
    }

    /**
     * @param productArea
     */
    public void setProductArea(String productArea) {
        this.productArea = productArea;
    }

    /**
     * @return id
     */
    public Integer getProductAreaId() {
        return productAreaId;
    }

    /**
     * @param productAreaId
     */
    public void setProductAreaId(Integer productAreaId) {
        this.productAreaId = productAreaId;
    }

    /**
     * @return rekkefølge
     */
    public Integer getSortNr() {
        return sortNr;
    }

    /**
     * @param sortNr
     */
    public void setSortNr(Integer sortNr) {
        this.sortNr = sortNr;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ProductArea))
            return false;
        ProductArea castOther = (ProductArea) other;
        return new EqualsBuilder().append(productArea, castOther.productArea)
                .isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(productArea).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public String toString() {
        return productArea;
    }

    /**
     * @return produktområdegruppe
     */
    public ProductAreaGroup getProductAreaGroup() {
        return productAreaGroup;
    }

    /**
     * @param productAreaGroup
     */
    public void setProductAreaGroup(ProductAreaGroup productAreaGroup) {
        this.productAreaGroup = productAreaGroup;
    }

   /* public String getGroupIdx() {
        return groupIdx;
    }

    public void setGroupIdx(String groupIdx) {
        this.groupIdx = groupIdx;
    }*/
    
    public List<Integer> getProductAreaNrList(){
        List<Integer> productAreaNrList=new ArrayList<Integer>();
        if(productAreaNr!=null){
            String[] stringArray=productAreaNr.split(",");
            for(int i=0;i<stringArray.length;i++){
                productAreaNrList.add(Integer.valueOf(stringArray[i]));
            }
        }
        return productAreaNrList;
    }

    public BigDecimal getOwnProductionCostLimit() {
        return ownProductionCostLimit;
    }

    public void setOwnProductionCostLimit(BigDecimal ownProductionCostLimit) {
        this.ownProductionCostLimit = ownProductionCostLimit;
    }

    public String getProductAreaNr() {
        return productAreaNr;
    }

    public void setProductAreaNr(String productAreaNr) {
        this.productAreaNr = productAreaNr;
    }

    public Integer getPacklistReady() {
        return packlistReady;
    }

    public void setPacklistReady(Integer packlistReady) {
        this.packlistReady = packlistReady;
    }
}
