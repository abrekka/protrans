package no.ugland.utransprod.model;

import java.util.Set;

import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Domeneklasse for tabell PRODUCT_AREA_GROUP
 * @author atle.brekka
 */
public class ProductAreaGroup extends BaseObject {
    private static final long serialVersionUID = 1L;

    private Integer productAreaGroupId;

    private String productAreaGroupName;

    private Set<ProductArea> productAreas;
    private Integer usePrepayment;
    public static final ProductAreaGroup UNKNOWN = new ProductAreaGroup() {
        public final String getProductAreaGroupName() {
            return "";
        }
        
    };

    public ProductAreaGroup() {
        super();
    }

    public ProductAreaGroup(final Integer aProductAreaGroupId, final String aProductAreaGroupName,
            final Set<ProductArea> someProductAreas, final Integer shouldUsePrepayment) {
        super();
        this.productAreaGroupId = aProductAreaGroupId;
        this.productAreaGroupName = aProductAreaGroupName;
        this.productAreas = someProductAreas;
        this.usePrepayment = shouldUsePrepayment;
    }

    /**
     * @return id
     */
    public final Integer getProductAreaGroupId() {
        return productAreaGroupId;
    }

    /**
     * @param aProductAreaGroupId
     */
    public final void setProductAreaGroupId(final Integer aProductAreaGroupId) {
        this.productAreaGroupId = aProductAreaGroupId;
    }

    /**
     * @return navn
     */
    public String getProductAreaGroupName() {
        return productAreaGroupName;
    }

    /**
     * @param aProductAreaGroupName
     */
    public final void setProductAreaGroupName(final String aProductAreaGroupName) {
        this.productAreaGroupName = aProductAreaGroupName;
    }

    /**
     * @return produktområder
     */
    public final Set<ProductArea> getProductAreas() {
        return productAreas;
    }

    /**
     * @param someProductAreas
     */
    public final void setProductAreas(final Set<ProductArea> someProductAreas) {
        this.productAreas = someProductAreas;
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#equals(java.lang.Object)
     */
    @Override
    public final boolean equals(final Object other) {
        if (!(other instanceof ProductAreaGroup)) {
            return false;
        }
        ProductAreaGroup castOther = (ProductAreaGroup) other;
        return new EqualsBuilder().append(productAreaGroupId, castOther.productAreaGroupId).append(
                productAreaGroupName, castOther.productAreaGroupName).isEquals();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#hashCode()
     */
    @Override
    public final int hashCode() {
        return new HashCodeBuilder().append(productAreaGroupId).append(productAreaGroupName).toHashCode();
    }

    /**
     * @see no.ugland.utransprod.model.BaseObject#toString()
     */
    @Override
    public final String toString() {
        return productAreaGroupName;
    }


    public final Integer getUsePrepayment() {
        return usePrepayment;
    }

    public final void setUsePrepayment(final Integer shouldUsePrepayment) {
        this.usePrepayment = shouldUsePrepayment;
    }
    public final boolean usePrepayment(){
        return Util.convertNumberToBoolean(usePrepayment);
    }
}
