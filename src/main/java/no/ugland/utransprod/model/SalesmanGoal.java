package no.ugland.utransprod.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import no.ugland.utransprod.util.Periode;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;

public class SalesmanGoal implements Comparable<SalesmanGoal> {

    private String salesman;
    private ProductArea productArea;
    private BigDecimal offerSumOwnProduction;
    private BigDecimal orderSumOwnProduction;
    private BigDecimal confirmedOrderSumOwnProduction;
    private BigDecimal db;
    private BigDecimal offerSumOwnProductionLastYear;
    private BigDecimal dbAccumulated;
    private BigDecimal offerSumOwnProductionAccumulated;
    private BigDecimal offerSumOwnProductionAccumulatedLastYear;
    private BigDecimal orderSumOwnProductionLastYear;
    private BigDecimal orderSumOwnProductionAccumulated;
    private BigDecimal orderSumOwnProductionAccumulatedLastYear;
    private BigDecimal confirmedOrderSumOwnProductionAccumulated;
    private BigDecimal budgetValue;
    private BigDecimal budgetValueOffer;

    public void setSalesman(String aSalesman) {
	salesman = aSalesman;

    }

    public void setProductArea(ProductArea aProductArea) {
	productArea = aProductArea;

    }

    public void setValue(String propabilityType, BigDecimal value, PeriodeEnum periodeEnum) {
	ProbabilityType.valueOf(StringUtils.upperCase(propabilityType)).setValue(this, value, periodeEnum);

    }

    public void setOfferSumOwnProduction(BigDecimal value) {
	offerSumOwnProduction = value;

    }

    public void setOrderSumOwnProduction(BigDecimal value) {
	orderSumOwnProduction = value;

    }

    public void setConfirmedOrderSumOwnProduction(BigDecimal value) {
	confirmedOrderSumOwnProduction = value;

    }

    public ProductArea getProductArea() {
	return productArea;
    }

    public String getSalesman() {
	return salesman;
    }

    public BigDecimal getOfferSumOwnProduction() {
	return offerSumOwnProduction != null ? offerSumOwnProduction : BigDecimal.ZERO;
    }

    public BigDecimal getOrderSumOwnProduction() {
	return orderSumOwnProduction != null ? orderSumOwnProduction : BigDecimal.ZERO;
    }

    public BigDecimal getConfirmedOrderSumOwnProduction() {
	return confirmedOrderSumOwnProduction != null ? confirmedOrderSumOwnProduction : BigDecimal.ZERO;
    }

    public BigDecimal getProcentOrder() {
	return getOfferSumOwnProduction().intValue() > 0 ? getOrderSumOwnProduction().divide(getOfferSumOwnProduction(), 2, RoundingMode.HALF_EVEN)
		.multiply(BigDecimal.valueOf(100)).setScale(0) : BigDecimal.ZERO;
    }

    public BigDecimal getDG() {
	return getOrderSumOwnProduction().intValue() != 0 ? getDB().divide(getOrderSumOwnProduction(), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }

    public BigDecimal getDGAccumulated() {
	return getOrderSumOwnProductionAccumulated().intValue() != 0 ? getDBAccumulated().divide(getOrderSumOwnProductionAccumulated(), 2,
		RoundingMode.HALF_UP) : BigDecimal.ZERO;
    }

    private BigDecimal getDBAccumulated() {
	return dbAccumulated != null ? dbAccumulated : BigDecimal.ZERO;
    }

    private BigDecimal getDB() {
	return db != null ? db : BigDecimal.ZERO;
    }

    public void setDbValue(String propabilityType, PeriodeEnum periodeEnum, BigDecimal dbValue) {
	ProbabilityType.valueOf(StringUtils.upperCase(propabilityType)).setDbValue(this, dbValue, periodeEnum);

    }

    public void setDb(BigDecimal dbValue) {
	db = dbValue;

    }

    public BigDecimal getOfferSumOwnProductionLastYear() {
	return offerSumOwnProductionLastYear != null ? offerSumOwnProductionLastYear : BigDecimal.ZERO;
    }

    public void setDbAccumulated(BigDecimal dbValue) {
	dbAccumulated = dbValue;

    }

    public BigDecimal getDbAccumulated() {
	return dbAccumulated != null ? dbAccumulated : BigDecimal.ZERO;

    }

    public void setOfferSumOwnProductionLastYear(BigDecimal value) {
	offerSumOwnProductionLastYear = value;

    }

    public void setOfferSumOwnProductionAccumulated(BigDecimal value) {
	offerSumOwnProductionAccumulated = value;

    }

    public void setOfferSumOwnProductionAccumulatedLastYear(BigDecimal value) {
	offerSumOwnProductionAccumulatedLastYear = value;

    }

    public void setOrderSumOwnProductionLastYear(BigDecimal value) {
	orderSumOwnProductionLastYear = value;

    }

    public void setOrderSumOwnProductionAccumulated(BigDecimal value) {
	orderSumOwnProductionAccumulated = value;

    }

    public void setOrderSumOwnProductionAccumulatedLastYear(BigDecimal value) {
	orderSumOwnProductionAccumulatedLastYear = value;

    }

    public void setConfirmedOrderSumOwnProductionAccumulated(BigDecimal value) {
	confirmedOrderSumOwnProductionAccumulated = value;

    }

    public BigDecimal getOrderSumOwnProductionLastYear() {
	return orderSumOwnProductionLastYear != null ? orderSumOwnProductionLastYear : BigDecimal.ZERO;
    }

    public BigDecimal getProcentOrderLastYear() {
	return getOfferSumOwnProductionLastYear().intValue() > 0 ? getOrderSumOwnProductionLastYear()
		.divide(getOfferSumOwnProductionLastYear(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100)).setScale(0)
		: BigDecimal.ZERO;
    }

    public BigDecimal getOfferSumOwnProductionAccumulated() {
	return offerSumOwnProductionAccumulated != null ? offerSumOwnProductionAccumulated : BigDecimal.ZERO;
    }

    public BigDecimal getOrderSumOwnProductionAccumulated() {
	return orderSumOwnProductionAccumulated != null ? orderSumOwnProductionAccumulated : BigDecimal.ZERO;
    }

    public BigDecimal getConfirmedOrderSumOwnProductionAccumulated() {
	return confirmedOrderSumOwnProductionAccumulated != null ? confirmedOrderSumOwnProductionAccumulated : BigDecimal.ZERO;
    }

    public BigDecimal getProcentOrderAccumulated() {
	return getOfferSumOwnProductionAccumulated().intValue() > 0 ? getOrderSumOwnProductionAccumulated()
		.divide(getOfferSumOwnProductionAccumulated(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100)).setScale(0)
		: BigDecimal.ZERO;
    }

    public BigDecimal getOfferSumOwnProductionAccumulatedLastYear() {
	return offerSumOwnProductionAccumulatedLastYear != null ? offerSumOwnProductionAccumulatedLastYear : BigDecimal.ZERO;
    }

    public BigDecimal getOrderSumOwnProductionAccumulatedLastYear() {
	return orderSumOwnProductionAccumulatedLastYear != null ? orderSumOwnProductionAccumulatedLastYear : BigDecimal.ZERO;
    }

    public BigDecimal getProcentOrderAccumulatedLastYear() {
	return getOfferSumOwnProductionAccumulatedLastYear().intValue() > 0 ? getOrderSumOwnProductionAccumulatedLastYear()
		.divide(getOfferSumOwnProductionAccumulatedLastYear(), 2, RoundingMode.HALF_EVEN).multiply(BigDecimal.valueOf(100)).setScale(0)
		: BigDecimal.ZERO;
    }

    public BigDecimal getBudgetValue() {
	return budgetValue != null ? budgetValue : BigDecimal.ZERO;
    }

    public void setBudgetValue(BigDecimal value) {
	budgetValue = value;

    }

    public void setBudgetValueOffer(BigDecimal value) {
	budgetValueOffer = value;

    }

    private enum ProbabilityType {
	TILBUD {
	    @Override
	    public void setValue(SalesmanGoal salesmanGoal, BigDecimal value, PeriodeEnum periodeEnum) {
		periodeEnum.setOfferSumValue(salesmanGoal, value);

	    }

	    @Override
	    public void setDbValue(SalesmanGoal salesmanGoal, BigDecimal value, PeriodeEnum periodeEnum) {

	    }
	},
	ORDRE {
	    @Override
	    public void setValue(SalesmanGoal salesmanGoal, BigDecimal value, PeriodeEnum periodeEnum) {
		periodeEnum.setOrderSumValue(salesmanGoal, value);

	    }

	    @Override
	    public void setDbValue(SalesmanGoal salesmanGoal, BigDecimal value, PeriodeEnum periodeEnum) {
		periodeEnum.setDbValue(salesmanGoal, value);

	    }
	},
	AVROP {
	    @Override
	    public void setValue(SalesmanGoal salesmanGoal, BigDecimal value, PeriodeEnum periodeEnum) {
		periodeEnum.setConfirmedOrderSumValue(salesmanGoal, value);

	    }

	    @Override
	    public void setDbValue(SalesmanGoal salesmanGoal, BigDecimal value, PeriodeEnum periodeEnum) {

	    }
	};

	public abstract void setValue(SalesmanGoal salesmanGoal, BigDecimal value, PeriodeEnum periodeEnum);

	public abstract void setDbValue(SalesmanGoal salesmanGoal, BigDecimal value, PeriodeEnum periodeEnum);
    }

    public enum PeriodeEnum {
	UKE_I_AAR(false) {
	    @Override
	    public void setDbValue(SalesmanGoal salesmanGoal, BigDecimal dbValue) {
		salesmanGoal.setDb(dbValue);
		salesmanGoal.setDbAccumulated(dbValue);
	    }

	    @Override
	    public void setOfferSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setOfferSumOwnProduction(value);
		salesmanGoal.setOfferSumOwnProductionAccumulated(value);
	    }

	    @Override
	    public void setOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setOrderSumOwnProduction(value);
		salesmanGoal.setOrderSumOwnProductionAccumulated(value);
	    }

	    @Override
	    public void setConfirmedOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setConfirmedOrderSumOwnProduction(value);
		salesmanGoal.setConfirmedOrderSumOwnProductionAccumulated(value);
	    }

	    @Override
	    public Periode convertPeriode(Periode periode) {
		return periode;
	    }
	},
	UKE_I_FJOR(true) {
	    @Override
	    public void setDbValue(SalesmanGoal salesmanGoal, BigDecimal dbValue) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void setOfferSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setOfferSumOwnProductionLastYear(value);
		salesmanGoal.setOfferSumOwnProductionAccumulatedLastYear(value);
	    }

	    @Override
	    public void setOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setOrderSumOwnProductionLastYear(value);
		salesmanGoal.setOrderSumOwnProductionAccumulatedLastYear(value);
	    }

	    @Override
	    public void setConfirmedOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public Periode convertPeriode(Periode periode) {
		return new Periode(periode.getYear() - 1, periode.getWeek(), periode.getToWeek());
	    }
	},
	AKKUMULERT_I_AAR(true) {
	    @Override
	    public void setDbValue(SalesmanGoal salesmanGoal, BigDecimal dbValue) {
		salesmanGoal.setDbAccumulated(dbValue != null ? dbValue.add(salesmanGoal.getDbAccumulated()) : salesmanGoal.getDbAccumulated());

	    }

	    @Override
	    public void setOfferSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setOfferSumOwnProductionAccumulated(value != null ? value.add(salesmanGoal.getOfferSumOwnProductionAccumulated())
			: salesmanGoal.getOfferSumOwnProductionAccumulated());

	    }

	    @Override
	    public void setOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setOrderSumOwnProductionAccumulated(value != null ? value.add(salesmanGoal.getOrderSumOwnProductionAccumulated())
			: salesmanGoal.getOrderSumOwnProductionAccumulated());

	    }

	    @Override
	    public void setConfirmedOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setConfirmedOrderSumOwnProductionAccumulated(value != null ? value.add(salesmanGoal
			.getConfirmedOrderSumOwnProductionAccumulated()) : salesmanGoal.getConfirmedOrderSumOwnProductionAccumulated());

	    }

	    @Override
	    public Periode convertPeriode(Periode periode) {
		return new Periode(periode.getYear(), 1, periode.getToWeek() != 1 ? periode.getToWeek() - 1 : 0);
	    }
	},
	AKKUMULERT_I_FJOR(true) {
	    @Override
	    public void setDbValue(SalesmanGoal salesmanGoal, BigDecimal dbValue) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public void setOfferSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setOfferSumOwnProductionAccumulatedLastYear(value != null ? value.add(salesmanGoal
			.getOfferSumOwnProductionAccumulatedLastYear()) : salesmanGoal.getOfferSumOwnProductionAccumulatedLastYear());

	    }

	    @Override
	    public void setOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		salesmanGoal.setOrderSumOwnProductionAccumulatedLastYear(value != null ? value.add(salesmanGoal
			.getOrderSumOwnProductionAccumulatedLastYear()) : salesmanGoal.getOrderSumOwnProductionAccumulatedLastYear());

	    }

	    @Override
	    public void setConfirmedOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value) {
		// TODO Auto-generated method stub

	    }

	    @Override
	    public Periode convertPeriode(Periode periode) {
		return new Periode(periode.getYear() - 1, 1, periode.getToWeek() != 1 ? periode.getToWeek() - 1 : 0);
	    }
	};

	private boolean salesmanShouldExist = false;

	private PeriodeEnum(boolean shouldSalesmanExist) {
	    salesmanShouldExist = shouldSalesmanExist;
	}

	public abstract void setDbValue(SalesmanGoal salesmanGoal, BigDecimal dbValue);

	public abstract void setConfirmedOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value);

	public abstract void setOrderSumValue(SalesmanGoal salesmanGoal, BigDecimal value);

	public abstract void setOfferSumValue(SalesmanGoal salesmanGoal, BigDecimal value);

	public boolean salesmanShouldExist() {
	    return false;
	}

	public abstract Periode convertPeriode(Periode periode);
    }

    public int compareTo(final SalesmanGoal other) {
	return new CompareToBuilder().append(productArea.getSortNr(), other.productArea.getSortNr()).append(salesman, other.salesman).toComparison();
    }

    public BigDecimal getBudgetValueOffer() {
	return budgetValueOffer != null ? budgetValueOffer : BigDecimal.ZERO;
    }

    public BigDecimal getBudgetOrderProcent() {
	return getBudgetValue().intValue() > 0 ? getBudgetValue().divide(getBudgetValueOffer(), 2, RoundingMode.HALF_EVEN)
		.multiply(BigDecimal.valueOf(100)).setScale(0) : BigDecimal.ZERO;
    }

    public BigDecimal getDb() {
	return db != null ? db : BigDecimal.ZERO;
    }

    public BigDecimal getProcentOrderLastYearDiff() {
	return getProcentOrder().subtract(getProcentOrderLastYear());
    }

    public BigDecimal getOfferSumOwnProductionLastYearDiff() {
	return getOfferSumOwnProduction().subtract(getOfferSumOwnProductionLastYear());
    }

    public BigDecimal getOrderSumOwnProductionLastYearDiff() {
	return getOrderSumOwnProduction().subtract(getOrderSumOwnProductionLastYear());
    }

    public BigDecimal getProcentOrderAccumulatedLastYearDiff() {
	return getProcentOrderAccumulated().subtract(getProcentOrderAccumulatedLastYear());
    }

    public BigDecimal getOfferSumOwnProductionAccumulatedLastYearDiff() {
	return getOfferSumOwnProductionAccumulated().subtract(getOfferSumOwnProductionAccumulatedLastYear());
    }

    public BigDecimal getOrderSumOwnProductionAccumulatedLastYearDiff() {
	return getOrderSumOwnProductionAccumulated().subtract(getOrderSumOwnProductionAccumulatedLastYear());
    }

}
