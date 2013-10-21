package no.ugland.utransprod.model;

import java.math.BigDecimal;

import no.ugland.utransprod.service.SalesGoal;

public class SalesStatistic {

	private String productAreaName;
	private Integer numberOfOffer;
	private Integer numberOfOrder;
	private Integer numberOfConfirmedOrder;
	private BigDecimal sumOwnProduction;
	private BigDecimal sumDG;
	private BigDecimal accumulatedSumOwnProduction;
	private Integer accumulatedNumberOfOffer;
	private Integer accumulatedNumberOfOrder;
	private BigDecimal sumDB;
	private BigDecimal accumulatedSumDG;
	private Integer accumulatedNumberOfConfirmedOrder;
	private BigDecimal saleBudget;
	private BigDecimal accumulatedSaleBudget;
	private BigDecimal orderReserve;
	

	public String getProductAreaName() {
		return productAreaName;
	}

	public void setProductAreaName(String aProductAreaName) {
		productAreaName=aProductAreaName;
		
	}

	public Integer getNumberOfOffer() {
		return numberOfOffer!=null?numberOfOffer:0;
	}

	public void setNumberOfOffer(Integer aNumberOfOffer) {
		numberOfOffer=aNumberOfOffer;
		
	}

	public Integer getNumberOfOrder() {
		return numberOfOrder!=null?numberOfOrder:0;
	}

	public void setNumberOfOrder(Integer aNumberOfOrder) {
		numberOfOrder=aNumberOfOrder;
		
	}

	public Integer getNumberOfConfirmedOrder() {
		return numberOfConfirmedOrder!=null?numberOfConfirmedOrder:0;
	}

	public void setNumberOfConfirmedOrder(Integer aNumberOfConfirmedOrder) {
		numberOfConfirmedOrder=aNumberOfConfirmedOrder;
		
	}

	public void setSumOwnProduction(BigDecimal aSumOwnProduction) {
		sumOwnProduction=aSumOwnProduction;
		
	}

	public BigDecimal getSumOwnProuction() {
		return sumOwnProduction!=null?sumOwnProduction:BigDecimal.ZERO;
	}

	public BigDecimal getSumDG() {
		return sumDG!=null?sumDG:BigDecimal.ZERO;
	}

	public void setSumDG(BigDecimal aSumDg) {
		sumDG=aSumDg;
		
	}

	public BigDecimal getSumDGProcent() {
		return sumDG!=null?sumDG.multiply(BigDecimal.valueOf(100)).setScale(2):BigDecimal.ZERO;
	}

	public BigDecimal getAccumulatedSumOwnProduction() {
		return accumulatedSumOwnProduction!=null?accumulatedSumOwnProduction:BigDecimal.ZERO;
	}

	public void setAccumulatedNumberOfOffer(Integer accNumberOf) {
		accumulatedNumberOfOffer=accNumberOf;
		
	}

	public void setAccumulatedNumberOfOrder(Integer accNumberOf) {
		accumulatedNumberOfOrder=accNumberOf;
		
	}

	public void setAccumulatedSumOwnProduction(BigDecimal accSum) {
		accumulatedSumOwnProduction=accSum;
		
	}

	public void setSumDB(BigDecimal aSumDb) {
		sumDB=aSumDb;
		
	}

	public BigDecimal getSumDB() {
		return sumDB!=null?sumDB:BigDecimal.ZERO;
	}

	public void setAccumulatedSumDG(BigDecimal accumulatedDG) {
		accumulatedSumDG=accumulatedDG;
		
	}

	public void setAccumulatedNumberOfConfirmedOrder(Integer aNumberOf) {
		accumulatedNumberOfConfirmedOrder=aNumberOf;
		
	}

	public BigDecimal getAccumulatedSumDG() {
		return accumulatedSumDG!=null?accumulatedSumDG:BigDecimal.ZERO;
	}

	public BigDecimal getAccumulatedSumDGProcent() {
		return accumulatedSumDG!=null?accumulatedSumDG.multiply(BigDecimal.valueOf(100)).setScale(2):BigDecimal.ZERO;
	}

	public Integer getAccumulatedNumberOfOffer() {
		return accumulatedNumberOfOffer!=null?accumulatedNumberOfOffer:0;
	}

	public Integer getAccumulatedNumberOfOrder() {
		return accumulatedNumberOfOrder!=null?accumulatedNumberOfOrder:0;
	}

	public Integer getAccumulatedNumberOfConfirmedOrder() {
		return accumulatedNumberOfConfirmedOrder!=null?accumulatedNumberOfConfirmedOrder:0;
	}

	public BigDecimal getSaleBudget() {
		return saleBudget!=null?saleBudget:BigDecimal.ZERO;
	}

	public void setAccumulatedLikeWeekSum() {
		accumulatedNumberOfConfirmedOrder=numberOfConfirmedOrder;
		accumulatedNumberOfOffer=numberOfOffer;
		accumulatedNumberOfOrder=numberOfOrder;
		accumulatedSumDG=sumDG;
		accumulatedSumOwnProduction=sumOwnProduction;
		
	}

	public void setSalesBudget(BigDecimal budgetValue) {
		saleBudget=budgetValue;
		
	}

	public BigDecimal getAccumulatedSaleBudget() {
		return accumulatedSaleBudget!=null?accumulatedSaleBudget:BigDecimal.ZERO;
	}

	public void setAccumulatedSaleBudget(BigDecimal budgetValue) {
		accumulatedSaleBudget=budgetValue;
		
	}

	public SalesGoal getSalesGoalWeek() {
		return SalesGoal.getSalesGoal(getSumOwnProuction(),getSaleBudget());
	}

	public SalesGoal getSalesGoalYear() {
		return SalesGoal.getSalesGoal(getAccumulatedSumOwnProduction(),getAccumulatedSaleBudget());
	}

	public BigDecimal getOrderReserve() {
		return orderReserve!=null?orderReserve:BigDecimal.ZERO;
	}

	public void setOrderReserve(BigDecimal aOrderReserve) {
		orderReserve=aOrderReserve;
		
	}


}
