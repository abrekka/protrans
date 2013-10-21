package no.ugland.utransprod.util.report;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * Brukes til salgsrapportoversikt gruppert på fylker
 * 
 * @author atle.brekka
 */
public class SaleReportSum implements Serializable {
	public static final SaleReportSum UNKNOWN = new SaleReportSum();

	private Integer orderCount;

	private BigDecimal sumOwnProduction;

	private BigDecimal sumTransport;

	private BigDecimal sumAssembly;

	private BigDecimal sumYesLines;

	private BigDecimal sumDb;

	private String countyName;
	private String salesman;

	public SaleReportSum() {
		super();
		this.orderCount = 0;
		this.sumOwnProduction = BigDecimal.ZERO;
		this.sumTransport = BigDecimal.ZERO;
		this.sumAssembly = BigDecimal.ZERO;
		this.sumYesLines = BigDecimal.ZERO;
		this.sumDb = BigDecimal.ZERO;
	}

	public SaleReportSum(final Integer aOrderCount,
			final BigDecimal aSumOwnProduction, final BigDecimal aSumTransport,
			final BigDecimal aSumAssembly, final BigDecimal aSumYesLines,
			final BigDecimal aSumDb, final String aCountyName) {
		super();
		this.orderCount = aOrderCount;
		this.sumOwnProduction = aSumOwnProduction;
		this.sumTransport = aSumTransport;
		this.sumAssembly = aSumAssembly;
		this.sumYesLines = aSumYesLines;
		this.sumDb = aSumDb;
		this.countyName = aCountyName;
	}

	public SaleReportSum(final Integer aOrderCount,
			final BigDecimal aSumOwnProduction, final BigDecimal aSumTransport,
			final BigDecimal aSumAssembly, final BigDecimal aSumYesLines,
			final BigDecimal aSumDb, final String aCountyName,
			final String aSalesman) {
		super();
		this.orderCount = aOrderCount;
		this.sumOwnProduction = aSumOwnProduction;
		this.sumTransport = aSumTransport;
		this.sumAssembly = aSumAssembly;
		this.sumYesLines = aSumYesLines;
		this.sumDb = aSumDb;
		this.countyName = aCountyName;
		this.salesman = aSalesman;
	}

	public SaleReportSum(final Integer aOrderCount,
			final BigDecimal aSumOwnProduction, final BigDecimal aSumTransport,
			final BigDecimal aSumAssembly, final BigDecimal aSumYesLines,
			final BigDecimal aSumDb) {
		super();
		this.orderCount = aOrderCount;
		this.sumOwnProduction = aSumOwnProduction;
		this.sumTransport = aSumTransport;
		this.sumAssembly = aSumAssembly;
		this.sumYesLines = aSumYesLines;
		this.sumDb = aSumDb;
	}

	public SaleReportSum(final Long aOrderCount,
			final BigDecimal aSumOwnProduction, final BigDecimal aSumTransport,
			final BigDecimal aSumAssembly, final BigDecimal aSumYesLines,
			final BigDecimal aSumDb) {
		super();
		this.orderCount = aOrderCount != null ? aOrderCount.intValue() : null;
		this.sumOwnProduction = aSumOwnProduction;
		this.sumTransport = aSumTransport;
		this.sumAssembly = aSumAssembly;
		this.sumYesLines = aSumYesLines;
		this.sumDb = aSumDb;
	}

	public final Integer getOrderCount() {
		return orderCount != null ? orderCount : 0;
	}

	public final void setOrderCount(final Integer aOrderCount) {
		this.orderCount = aOrderCount;
	}

	public final BigDecimal getSumAssembly() {
		return sumAssembly;
	}

	public final void setSumAssembly(final BigDecimal aSumAssembly) {
		this.sumAssembly = aSumAssembly;
	}

	public final BigDecimal getSumDb() {
		return sumDb != null ? sumDb : BigDecimal.ZERO;
	}

	public final void setSumDb(final BigDecimal aSumDb) {
		this.sumDb = aSumDb;
	}

	public final BigDecimal getSumDg() {
		if (sumDb != null && sumOwnProduction != null
				&& sumOwnProduction.intValue() != 0) {
			return // BigDecimal.valueOf(1).subtract(
			sumDb.divide(sumOwnProduction, 4, RoundingMode.HALF_UP);
			// );
		}
		return null;
	}

	public final BigDecimal getSumOwnProduction() {
		return sumOwnProduction != null ? sumOwnProduction : BigDecimal.ZERO;
	}

	public final void setSumOwnProduction(final BigDecimal aSumOwnProduction) {
		this.sumOwnProduction = aSumOwnProduction;
	}

	public final BigDecimal getSumTransport() {
		return sumTransport;
	}

	public final void setSumTransport(final BigDecimal aSumTransport) {
		this.sumTransport = aSumTransport;
	}

	public final BigDecimal getSumYesLines() {
		return sumYesLines;
	}

	public final void setSumYesLines(final BigDecimal aSumYesLines) {
		this.sumYesLines = aSumYesLines;
	}

	public String getCountyName() {
		return countyName;
	}

	public void setCountyName(String countyName) {
		this.countyName = countyName;
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof SaleReportSum))
			return false;
		SaleReportSum castOther = (SaleReportSum) other;
		return new EqualsBuilder().append(countyName, castOther.countyName)
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(countyName).toHashCode();
	}

	public void increaseCount() {
		orderCount++;
	}

	public void addSumAssembly(BigDecimal assemblySum) {
		sumAssembly = sumAssembly.add(assemblySum);
	}

	public void addSumOwnProduction(BigDecimal ownProductionSum) {
		sumOwnProduction = sumOwnProduction.add(ownProductionSum);
	}

	public void addSumTransport(BigDecimal transportSum) {
		sumTransport = sumTransport.add(transportSum);
	}

	public void addSumDb(BigDecimal dbSum) {
		sumDb = sumDb.add(dbSum);
	}

	public void addSumYesLines(BigDecimal yesLinesSum) {
		sumYesLines = sumYesLines.add(yesLinesSum);
	}

	public String getSalesman() {
		return salesman;
	}

	public void setSalesman(String salesman) {
		this.salesman = salesman;
	}

	public void addOrderCount() {
		orderCount = orderCount == null ? 1 : orderCount + 1;

	}

}
