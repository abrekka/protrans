package no.ugland.utransprod.util.report;

import java.math.BigDecimal;

import no.ugland.utransprod.model.ProductArea;

public class DrawerGroup {
    private Integer count;

    private String categoryName;

    private String drawerName;

    private BigDecimal sumOwnProduction;
    private ProductArea productArea;

    public DrawerGroup(final Integer aCount, final String aCategoryName,
            final String aDrawerName, final BigDecimal aSumOwnProduction) {
        super();
        this.count = aCount;
        this.categoryName = aCategoryName;
        this.drawerName = aDrawerName;
        this.sumOwnProduction = aSumOwnProduction;
        
    }

    public final String getCategoryName() {
        return categoryName;
    }

    public final void setCategoryName(final String aCategoryName) {
        this.categoryName = aCategoryName;
    }

    public final Integer getCount() {
        return count;
    }

    public final void setCount(final Integer aCount) {
        this.count = aCount;
    }

    public final String getDrawerName() {
        return drawerName;
    }

    public final void setDrawerName(final String aDrawerName) {
        this.drawerName = aDrawerName;
    }

    public final BigDecimal getSumOwnProduction() {
        return sumOwnProduction;
    }

    public final void setSumOwnProduction(final BigDecimal aSumOwnProduction) {
        this.sumOwnProduction = aSumOwnProduction;
    }

    public ProductArea getProductArea() {
        return productArea;
    }
    public void setProductArea(final ProductArea aProductArea) {
        this.productArea=aProductArea;
    }

    
}
