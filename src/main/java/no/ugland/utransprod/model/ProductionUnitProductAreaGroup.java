package no.ugland.utransprod.model;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.lang.builder.ToStringBuilder;

public class ProductionUnitProductAreaGroup extends BaseObject{
    private Integer productionUnitProductAreaGroupId;
    private ProductionUnit productionUnit;
    private ProductAreaGroup productAreaGroup;
    public ProductionUnitProductAreaGroup() {
        super();
    }
    public ProductionUnitProductAreaGroup(Integer productionUnitProductAreaGroupId, ProductionUnit productionUnit, ProductAreaGroup productAreaGroup) {
        super();
        this.productionUnitProductAreaGroupId = productionUnitProductAreaGroupId;
        this.productionUnit = productionUnit;
        this.productAreaGroup = productAreaGroup;
    }
    public ProductAreaGroup getProductAreaGroup() {
        return productAreaGroup;
    }
    public void setProductAreaGroup(ProductAreaGroup productAreaGroup) {
        this.productAreaGroup = productAreaGroup;
    }
    public ProductionUnit getProductionUnit() {
        return productionUnit;
    }
    public void setProductionUnit(ProductionUnit productionUnit) {
        this.productionUnit = productionUnit;
    }
    public Integer getProductionUnitProductAreaGroupId() {
        return productionUnitProductAreaGroupId;
    }
    public void setProductionUnitProductAreaGroupId(
            Integer productionUnitProductAreaGroupId) {
        this.productionUnitProductAreaGroupId = productionUnitProductAreaGroupId;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ProductionUnitProductAreaGroup))
            return false;
        ProductionUnitProductAreaGroup castOther = (ProductionUnitProductAreaGroup) other;
        return new EqualsBuilder().append(productionUnit,
                castOther.productionUnit).append(productAreaGroup,
                castOther.productAreaGroup).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(productionUnit).append(
                productAreaGroup).toHashCode();
    }
    @Override
    public String toString() {
        if(productAreaGroup!=null){
        return productAreaGroup.toString();
        }
        return "";
    }
}
