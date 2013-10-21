package no.ugland.utransprod.model;

import java.util.Set;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ProductionUnit extends BaseObject{
    private Integer productionUnitId;
    private String productionUnitName;
    private ArticleType articleType;
    private Set<ProductionUnitProductAreaGroup> productionUnitProductAreaGroups;
    public ProductionUnit() {
        super();
    }
    public ProductionUnit(Integer productionUnitId, String productionUnitName, ArticleType articleType,Set<ProductionUnitProductAreaGroup> groups) {
        super();
        this.productionUnitId = productionUnitId;
        this.productionUnitName = productionUnitName;
        this.articleType = articleType;
        this.productionUnitProductAreaGroups=groups;
    }
    public ArticleType getArticleType() {
        return articleType;
    }
    public void setArticleType(ArticleType articleType) {
        this.articleType = articleType;
    }
    public Integer getProductionUnitId() {
        return productionUnitId;
    }
    public void setProductionUnitId(Integer productionUnitId) {
        this.productionUnitId = productionUnitId;
    }
    public String getProductionUnitName() {
        return productionUnitName;
    }
    public void setProductionUnitName(String productionUnitName) {
        this.productionUnitName = productionUnitName;
    }
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ProductionUnit))
            return false;
        ProductionUnit castOther = (ProductionUnit) other;
        return new EqualsBuilder().append(productionUnitName,
                castOther.productionUnitName).append(articleType,
                castOther.articleType).isEquals();
    }
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(productionUnitName).append(
                articleType).toHashCode();
    }
    @Override
    public String toString() {
        return productionUnitName;
    }
    public Set<ProductionUnitProductAreaGroup> getProductionUnitProductAreaGroups() {
        return productionUnitProductAreaGroups;
    }
    public void setProductionUnitProductAreaGroups(
            Set<ProductionUnitProductAreaGroup> productionUnitProductAreaGroups) {
        this.productionUnitProductAreaGroups = productionUnitProductAreaGroups;
    }
}
