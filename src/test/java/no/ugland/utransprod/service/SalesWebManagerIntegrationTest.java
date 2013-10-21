package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.SalesStatistic;
import no.ugland.utransprod.service.impl.SalesWebManagerImpl;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class SalesWebManagerIntegrationTest {
	private SalesWebManager salesWebManager;
	private ProductAreaManager productAreaManager;
	private BudgetManager budgetManager;

	@Before
	public void init() throws ProTransException {
		salesWebManager = new SalesWebManagerImpl();
		productAreaManager = (ProductAreaManager) ModelUtil
				.getBean(ProductAreaManager.MANAGER_NAME);
		SalesVManager salesVManager = (SalesVManager) ModelUtil
				.getBean(SalesVManager.MANAGER_NAME);
		budgetManager = (BudgetManager) ModelUtil
				.getBean(BudgetManager.MANAGER_NAME);
		OrderReserveVManager orderReserveVManager = (OrderReserveVManager) ModelUtil
				.getBean(OrderReserveVManager.MANAGER_NAME);
		SaleStatusOrderReserveVManager saleStatusOrderReserveVManager = (SaleStatusOrderReserveVManager) ModelUtil
				.getBean(SaleStatusOrderReserveVManager.MANAGER_NAME);
		((SalesWebManagerImpl) salesWebManager)
				.setOrderReserveVManager(orderReserveVManager);
		((SalesWebManagerImpl) salesWebManager)
				.setProductAreaManager(productAreaManager);
		((SalesWebManagerImpl) salesWebManager).setSalesVManager(salesVManager);
		((SalesWebManagerImpl) salesWebManager).setBudgetManager(budgetManager);
		((SalesWebManagerImpl) salesWebManager)
				.setSaleStatusOrderReserveVManager(saleStatusOrderReserveVManager);
		// URL url = getClass().getClassLoader().getResource(
		// "Budsjett_sales_2010_import_villa.xls");
		// budgetManager.importBudget(url.getFile(), BudgetType.SALE);
	}


	@Test
	public void skal() throws Exception {
		Integer year = 2011;
		Integer week = 6;
		String productAreaName = "Garasje villa";
		SalesStatistic statistic = salesWebManager.generateSalesStatistics(
				year, week, productAreaName);
		assertNotNull(statistic);
	}

	@Test
	public void getSalesdataForGarasjeVilla2010_08() throws ProTransException {

		Integer year = 2010;
		Integer week = 16;
		String productAreaName = "Garasje villa";
		SalesStatistic statistic = salesWebManager.generateSalesStatistics(
				year, week, productAreaName);
		assertNotNull(statistic);
		assertEquals("Garasje villa", statistic.getProductAreaName());
		assertEquals(Integer.valueOf(107), statistic.getNumberOfOffer());
		assertEquals(Integer.valueOf(37), statistic.getNumberOfOrder());
		assertEquals(Integer.valueOf(20), statistic.getNumberOfConfirmedOrder());
		assertEquals(BigDecimal.valueOf(3119424), statistic.getSumOwnProuction());
		assertEquals(BigDecimal.valueOf(0.3231), statistic.getSumDG());
		// assertEquals(BigDecimal.valueOf(0).setScale(2),
		// statistic.getSumDGProcent());
		assertEquals(BigDecimal.valueOf(32.31), statistic.getSumDGProcent());
		assertEquals(BigDecimal.valueOf(0), statistic.getSaleBudget());
		assertEquals(SalesGoal.GOAL_0, statistic.getSalesGoalWeek());
		assertEquals(BigDecimal.valueOf(12465248), statistic.getOrderReserve());

		assertEquals(BigDecimal.valueOf(22369809), statistic
				.getAccumulatedSumOwnProduction());
		assertEquals(BigDecimal.valueOf(0.3329), statistic
				.getAccumulatedSumDG());
		assertEquals(BigDecimal.valueOf(33.29).setScale(2), statistic
				.getAccumulatedSumDGProcent());
		assertEquals(Integer.valueOf(1303), statistic
				.getAccumulatedNumberOfOffer());
		assertEquals(Integer.valueOf(257), statistic
				.getAccumulatedNumberOfOrder());
		assertEquals(Integer.valueOf(97), statistic
				.getAccumulatedNumberOfConfirmedOrder());
		assertEquals(BigDecimal.valueOf(0), statistic
				.getAccumulatedSaleBudget());
		assertEquals(SalesGoal.GOAL_0, statistic.getSalesGoalYear());
	}

	@Test
	public void getSalesdataForGarasjeVilla2010_01() throws ProTransException {

		Integer year = 2010;
		Integer week = 1;
		String productAreaName = "Garasje villa";
		SalesStatistic statistic = salesWebManager.generateSalesStatistics(
				year, week, productAreaName);
		assertNotNull(statistic);
		assertEquals("Garasje villa", statistic.getProductAreaName());
		assertEquals(Integer.valueOf(50), statistic.getNumberOfOffer());
		assertEquals(Integer.valueOf(32), statistic.getNumberOfOrder());
		assertEquals(Integer.valueOf(5), statistic.getNumberOfConfirmedOrder());
		assertEquals(BigDecimal.valueOf(3011288), statistic
				.getSumOwnProuction());
		assertEquals(BigDecimal.valueOf(0.3290).setScale(4), statistic.getSumDG());
		assertEquals(BigDecimal.valueOf(32.90).setScale(2), statistic
				.getSumDGProcent());

		assertEquals(BigDecimal.valueOf(3011288), statistic
				.getAccumulatedSumOwnProduction());
		assertEquals(BigDecimal.valueOf(0.3290).setScale(4), statistic
				.getAccumulatedSumDG());
		assertEquals(BigDecimal.valueOf(32.90).setScale(2), statistic
				.getAccumulatedSumDGProcent());
		assertEquals(Integer.valueOf(50), statistic
				.getAccumulatedNumberOfOffer());
		assertEquals(Integer.valueOf(32), statistic
				.getAccumulatedNumberOfOrder());
		assertEquals(Integer.valueOf(5), statistic
				.getAccumulatedNumberOfConfirmedOrder());
	}

	@Test
	public void getProductAreaNames() {
		List<String> names = salesWebManager.getProductAreaNames();
		assertNotNull(names);
		assertEquals(6, names.size());
	}

	@Test
	public void getSalesdataForGarasjeVilla2010_11() throws ProTransException {

		Integer year = 2010;
		;
		Integer week = 8;
		String productAreaName = "Garasje villa";
		SalesStatistic statistic = salesWebManager.generateSalesStatistics(
				year, week, productAreaName);
		assertNotNull(statistic);
		assertEquals("Garasje villa", statistic.getProductAreaName());
		assertEquals(Integer.valueOf(56), statistic.getNumberOfOffer());
		assertEquals(Integer.valueOf(6), statistic.getNumberOfOrder());
		assertEquals(Integer.valueOf(3), statistic.getNumberOfConfirmedOrder());
		assertEquals(BigDecimal.valueOf(484730), statistic.getSumOwnProuction());
		assertEquals(BigDecimal.valueOf(0.3178), statistic.getSumDG());
		// assertEquals(BigDecimal.valueOf(0).setScale(2),
		// statistic.getSumDGProcent());
		assertEquals(BigDecimal.valueOf(31.78), statistic.getSumDGProcent());
		assertEquals(BigDecimal.valueOf(0), statistic.getSaleBudget());
		assertEquals(SalesGoal.GOAL_0, statistic.getSalesGoalWeek());
		assertEquals(BigDecimal.valueOf(12465248), statistic.getOrderReserve());

		assertEquals(BigDecimal.valueOf(7479218), statistic
				.getAccumulatedSumOwnProduction());
		assertEquals(BigDecimal.valueOf(0.3364), statistic
				.getAccumulatedSumDG());
		assertEquals(BigDecimal.valueOf(33.64).setScale(2), statistic
				.getAccumulatedSumDGProcent());
		assertEquals(Integer.valueOf(505), statistic
				.getAccumulatedNumberOfOffer());
		assertEquals(Integer.valueOf(85), statistic
				.getAccumulatedNumberOfOrder());
		assertEquals(Integer.valueOf(26), statistic
				.getAccumulatedNumberOfConfirmedOrder());
		assertEquals(BigDecimal.valueOf(0), statistic
				.getAccumulatedSaleBudget());
		assertEquals(SalesGoal.GOAL_0, statistic.getSalesGoalYear());
	}
}
