package no.ugland.utransprod.util.report;

import java.math.BigDecimal;

import no.ugland.utransprod.model.ProductArea;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

public class SumTakstolOwnOrder {
    private ProductArea productArea;
    private Integer numberOfOrders;
    private BigDecimal sumOwnProduction;
	private BigDecimal sumInternalCost;

    public SumTakstolOwnOrder(ProductArea productArea, Integer numberOfOrders, BigDecimal sumOwnProduction,BigDecimal sumInternalCost) {
        super();
        this.productArea = productArea;
        this.numberOfOrders = numberOfOrders;
        this.sumOwnProduction = sumOwnProduction;
        this.sumInternalCost=sumInternalCost;
    }

    public ProductArea getProductArea() {
        return productArea;
    }

    public void setProductArea(ProductArea productArea) {
        this.productArea = productArea;
    }

    public Integer getNumberOfOrders() {
        return numberOfOrders;
    }

    public void setNumberOfOrders(Integer numberOfOrders) {
        this.numberOfOrders = numberOfOrders;
    }

    public BigDecimal getSumOwnProduction() {
        return sumOwnProduction;
    }

    public void setSumOwnProduction(BigDecimal sumOwnProduction) {
        this.sumOwnProduction = sumOwnProduction;
    }

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof SumTakstolOwnOrder))
            return false;
        SumTakstolOwnOrder castOther = (SumTakstolOwnOrder) other;
        return new EqualsBuilder().append(productArea, castOther.productArea).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(productArea).toHashCode();
    }

    public final void addNumberOfOrders(Integer addNumber) {
        numberOfOrders += addNumber;
    }

    public final void addSumOwnProduction(BigDecimal addSum) {
        sumOwnProduction = sumOwnProduction.add(addSum);
    }

	public void addSumInternalOrderCost(BigDecimal addCost) {
		
		sumInternalCost=addCost!=null?sumInternalCost.add(addCost):sumInternalCost;
		
	}

	public BigDecimal getSumInternalCost() {
		return sumInternalCost;
	}
}
