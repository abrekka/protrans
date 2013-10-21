package no.ugland.utransprod.util.excel;

import java.math.BigDecimal;

public class AntallSum {
	private Integer numberOf;
	private BigDecimal sum;
	public AntallSum(Integer numberOf, BigDecimal sum) {
		super();
		this.numberOf = numberOf;
		this.sum = sum;
	}
	public AntallSum() {
		this(0,BigDecimal.ZERO);
	}
	public Integer getNumberOf() {
		return numberOf;
	}
	public void setNumberOf(Integer numberOf) {
		this.numberOf = numberOf;
	}
	public BigDecimal getSum() {
		return sum;
	}
	public void setSum(BigDecimal sum) {
		this.sum = sum;
	}
	public void incrementNumberOf() {
		numberOf++;
		
	}
	public void add(BigDecimal aSum) {
		sum=sum.add(aSum);
		
	}
}
