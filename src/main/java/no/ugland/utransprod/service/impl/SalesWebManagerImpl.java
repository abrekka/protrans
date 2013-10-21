package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.OrderReserveV;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SaleStatusOrderReserveV;
import no.ugland.utransprod.model.SalesStatistic;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.OrderReserveVManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.SaleStatusOrderReserveVManager;
import no.ugland.utransprod.service.SalesVManager;
import no.ugland.utransprod.service.SalesWebManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportSum;

import org.apache.log4j.Logger;

import com.google.common.collect.Multimap;

public class SalesWebManagerImpl implements SalesWebManager {
	private SalesVManager salesVManager;
	private ProductAreaManager productAreaManager;
	private BudgetManager budgetManager;
	private OrderReserveVManager orderReserveVManager;
	private SaleStatusOrderReserveVManager saleStatusOrderReserveVManager;
	private static Logger LOGGER = Logger.getLogger(SalesWebManager.class);

	public SalesStatistic generateSalesStatistics(Integer year, Integer week,
			String productAreaName) throws ProTransException {
		ProductArea productArea = productAreaManager
				.findByName(productAreaName);
		Periode periode = new Periode(year, week, week);
		// henter for gjeldende uke
		Multimap<ProbabilityEnum, SaleReportSum> salesMap = salesVManager
				.getAllReportData(productArea, periode);
		SalesStatistic salesStatistic = new SalesStatistic();
		salesStatistic.setProductAreaName(productAreaName);
		salesStatistic = setNumberOfAndSumOrder(salesMap, salesStatistic);

		debugSalesStatistic(salesStatistic, productAreaName, week);

		// henter for hele året dersom etter uke 1
		if (week > 1) {
			periode = new Periode(year, 1, week - 1);
			LOGGER
					.debug("Periode: "
							+ periode.getFormattetYearFromWeekToWeek());
			salesMap = salesVManager.getAllReportData(productArea, periode);
			salesStatistic = setAccumulatedNumberOfAndSumOrder(salesMap,
					salesStatistic);
		} else {// setter akkumulert til uke dersmo uke 1
			salesStatistic.setAccumulatedLikeWeekSum();
		}

		salesStatistic = setSalesBudget(year, week, productArea, salesStatistic);
		salesStatistic = setOrderReserve(salesStatistic, productArea);
		return salesStatistic;
	}

	private void debugSalesStatistic(SalesStatistic salesStatistic,
			String productAreaName, Integer week) {
		LOGGER
				.debug("************************* PROTRANS *********************************");
		LOGGER.debug("Avdeling: " + productAreaName);
		LOGGER.debug("   Ukenr: " + week);
		LOGGER.debug("      DgPrUke: " + salesStatistic.getSumDGProcent());
		LOGGER.debug("      BudsjettPrUke: " + salesStatistic.getSaleBudget());
		LOGGER
				.debug("      AntallTilbud: "
						+ salesStatistic.getNumberOfOffer());
		LOGGER.debug("      AntallOrdre: " + salesStatistic.getNumberOfOrder());
		LOGGER.debug("      AntallAvrop: "
				+ salesStatistic.getNumberOfConfirmedOrder());
		LOGGER
				.debug("**********************************************************");
		/*
		 * LOGGER.debug("   Akkumulert: "+week);
		 * LOGGER.debug("      SumHittilIAar: "
		 * +salesStatistic.getAccumulatedSumOwnProduction());
		 * LOGGER.debug("      DgPrAar: "
		 * +salesStatistic.getAccumulatedSumDGProcent());
		 * LOGGER.debug("      BudsjettPrAar: "
		 * +salesStatistic.getAccumulatedSaleBudget());
		 * LOGGER.debug("      AntallTilbudHittilIAar: "
		 * +salesStatistic.getAccumulatedNumberOfOffer());
		 * LOGGER.debug("      AntallOrdreHittilIAar: "+salesStatistic
		 * .getAccumulatedNumberOfOrder());
		 * LOGGER.debug("      AntallAvropHittilIAar: "+salesStatistic
		 * .getAccumulatedNumberOfConfirmedOrder());
		 * LOGGER.debug("   Ordreseserve: "+salesStatistic.getOrderReserve());
		 */

	}

	private SalesStatistic setOrderReserve(SalesStatistic salesStatistic,
			ProductArea productArea) {
		BigDecimal orderReserve = BigDecimal.ZERO;
		for (OrderReserveV reserve : orderReserveVManager
				.findByProductArea(productArea.getProductArea())) {
			orderReserve = orderReserve.add(reserve.getOwnProduction());
		}
		SaleStatusOrderReserveV salesReserve = saleStatusOrderReserveVManager
				.findByProductArea(productArea);
		orderReserve = orderReserve.add(salesReserve.getOrderReserve());
		salesStatistic.setOrderReserve(orderReserve);
		return salesStatistic;
	}

	private SalesStatistic setSalesBudget(Integer year, Integer week,
			ProductArea productArea, SalesStatistic salesStatistic) {
		Budget salesBudget = budgetManager.findByYearAndWeek(year, week,
				productArea, BudgetType.SALE);
		salesStatistic.setSalesBudget(salesBudget.getBudgetValue());
		Periode periode = new Periode(year, 1, week);
		salesBudget = budgetManager.findSumPrProductAreaAndPeriode(periode,
				productArea, BudgetType.SALE);
		salesStatistic.setAccumulatedSaleBudget(salesBudget.getBudgetValue());
		return salesStatistic;
	}

	private SalesStatistic setAccumulatedNumberOfAndSumOrder(
			Multimap<ProbabilityEnum, SaleReportSum> salesMap,
			SalesStatistic salesStatistic) {
		List<SaleReportSum> salesList = (List<SaleReportSum>) salesMap
				.get(ProbabilityEnum.PROBABILITY_OFFER);
		if (salesList.size() != 0) {
			salesStatistic.setAccumulatedNumberOfOffer(salesList.get(0)
					.getOrderCount()
					+ salesStatistic.getNumberOfOffer());
			// salesStatistic.setAccumulatedNumberOfOffer(salesList.get(0).getOrderCount());
		}

		salesList = (List<SaleReportSum>) salesMap
				.get(ProbabilityEnum.PROBABILITY_ORDER);
		if (salesList.size() != 0) {
			salesStatistic.setAccumulatedNumberOfOrder(salesList.get(0)
					.getOrderCount()
					+ salesStatistic.getNumberOfOrder());
			// salesStatistic.setAccumulatedNumberOfOrder(salesList.get(0).getOrderCount());
			salesStatistic.setAccumulatedSumOwnProduction(salesList.get(0)
					.getSumOwnProduction().add(
							salesStatistic.getSumOwnProuction()));

			salesStatistic.setAccumulatedSumDG(getAccumulatedSumDG(salesList
					.get(0), salesStatistic));
		}

		salesList = (List<SaleReportSum>) salesMap
				.get(ProbabilityEnum.PROBABILITY_CONFRIM_ORDER);
		if (salesList.size() != 0) {
			salesStatistic.setAccumulatedNumberOfConfirmedOrder(salesList
					.get(0).getOrderCount()
					+ salesStatistic.getNumberOfConfirmedOrder());
			// salesStatistic.setAccumulatedNumberOfConfirmedOrder(salesList.get(0).getOrderCount());
		}

		return salesStatistic;
	}

	private BigDecimal getAccumulatedSumDG(SaleReportSum yearSum,
			SalesStatistic weekStatistic) {
		BigDecimal sumDb = yearSum.getSumDb().add(weekStatistic.getSumDB());
		BigDecimal sumOwnProduction = yearSum.getSumOwnProduction().add(
				weekStatistic.getSumOwnProuction());
		if (sumDb != null && sumOwnProduction != null
				&& sumOwnProduction.intValue() != 0) {
			return sumDb.divide(sumOwnProduction, 4, RoundingMode.HALF_EVEN);
		}
		return BigDecimal.ZERO;
	}

	private SalesStatistic setNumberOfAndSumOrder(
			Multimap<ProbabilityEnum, SaleReportSum> salesMap,
			SalesStatistic salesStatistic) {
		List<SaleReportSum> salesList = (List<SaleReportSum>) salesMap
				.get(ProbabilityEnum.PROBABILITY_OFFER);
		if (salesList.size() != 0) {
			salesStatistic.setNumberOfOffer(salesList.get(0).getOrderCount());
		}

		salesList = (List<SaleReportSum>) salesMap
				.get(ProbabilityEnum.PROBABILITY_ORDER);
		if (salesList.size() != 0) {
			salesStatistic.setNumberOfOrder(salesList.get(0).getOrderCount());
			salesStatistic.setSumOwnProduction(salesList.get(0)
					.getSumOwnProduction());
			salesStatistic.setSumDG(salesList.get(0).getSumDg());
			salesStatistic.setSumDB(salesList.get(0).getSumDb());
		}

		salesList = (List<SaleReportSum>) salesMap
				.get(ProbabilityEnum.PROBABILITY_CONFRIM_ORDER);
		if (salesList.size() != 0) {
			salesStatistic.setNumberOfConfirmedOrder(salesList.get(0)
					.getOrderCount());
		}

		return salesStatistic;
	}

	public SalesVManager getSalesVManager() {
		return salesVManager;
	}

	public void setSalesVManager(SalesVManager salesVManager) {
		this.salesVManager = salesVManager;
	}

	public ProductAreaManager getProductAreaManager() {
		return productAreaManager;
	}

	public void setProductAreaManager(ProductAreaManager productAreaManager) {
		this.productAreaManager = productAreaManager;
	}

	public BudgetManager getBudgetManager() {
		return budgetManager;
	}

	public void setBudgetManager(BudgetManager productionBudgetManager) {
		this.budgetManager = productionBudgetManager;
	}

	public List<String> getProductAreaNames() {
		return productAreaManager.getAllNames();
	}

	public OrderReserveVManager getOrderReserveVManager() {
		return orderReserveVManager;
	}

	public void setOrderReserveVManager(
			OrderReserveVManager orderReserveVManager) {
		this.orderReserveVManager = orderReserveVManager;
	}

	public SaleStatusOrderReserveVManager getSaleStatusOrderReserveVManager() {
		return saleStatusOrderReserveVManager;
	}

	public void setSaleStatusOrderReserveVManager(
			SaleStatusOrderReserveVManager saleStatusOrderReserveVManager) {
		this.saleStatusOrderReserveVManager = saleStatusOrderReserveVManager;
	}

	public static Builder med() {
		return new Builder();
	}

	public static class Builder {
		private SalesWebManagerImpl salesWebManagerImpl = new SalesWebManagerImpl();

		private Builder() {

		}

		public Builder productAreaManager(ProductAreaManager productAreaManager) {
			salesWebManagerImpl.productAreaManager = productAreaManager;
			return this;
		}

		public SalesWebManager build() {
			return salesWebManagerImpl;
		}

		public Builder salesVManager(SalesVManager salesVManager) {
			salesWebManagerImpl.salesVManager = salesVManager;
			return this;
		}

		public Builder budgetManager(BudgetManager budgetManager) {
			salesWebManagerImpl.budgetManager = budgetManager;
			return this;
		}

		public Builder orderReserveVManager(
				OrderReserveVManager orderReserveVManager) {
			salesWebManagerImpl.orderReserveVManager = orderReserveVManager;
			return this;
		}

		public Builder saleStatusOrderReserveVManager(
				SaleStatusOrderReserveVManager saleStatusOrderReserveVManager) {
			salesWebManagerImpl.saleStatusOrderReserveVManager = saleStatusOrderReserveVManager;
			return this;
		}
	}

}
