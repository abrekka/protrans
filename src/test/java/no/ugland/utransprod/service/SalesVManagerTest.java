package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.gui.model.BudgetType;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SalesmanGoal;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.ProbabilityEnum;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class SalesVManagerTest {
	private SalesVManager salesVManager;

	private OrderManager orderManager;
	private ProductAreaManager productAreaManager;
	private BudgetManager budgetManager;

	@Before
	public void setUp() throws Exception {
		salesVManager = (SalesVManager) ModelUtil.getBean("salesVManager");
		orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		productAreaManager = (ProductAreaManager) ModelUtil
				.getBean("productAreaManager");
		budgetManager = (BudgetManager) ModelUtil
		.getBean(BudgetManager.MANAGER_NAME);
	}
	
	@After
	public void tearDown(){
		ProductArea productAreaVilla = productAreaManager.findByName("Garasje villa");
		budgetManager.removeForYearProductArea(2010,productAreaVilla,BudgetType.SALESMAN);
	}

	@Test
	public void testGetSalesReportDataForOfferVilla() throws Exception {
		Periode periode = new Periode(2010, 3, 3);
		ProductArea productArea = productAreaManager.findByName("Garasje villa");
		List<SaleReportSum> salesReportOffer = salesVManager
				.groupSumCountyByProbabilityProductAreaPeriode(
						ProbabilityEnum.PROBABILITY_OFFER, productArea,
						// ProductAreaEnum.getGroupIdxFromAreaName("Garasje villa"),
						periode);
		assertNotNull(salesReportOffer);
		assertEquals(15, salesReportOffer.size());

		for (SaleReportSum sum : salesReportOffer) {
			if (sum.getCountyName().equalsIgnoreCase("Rogaland")) {
				assertEquals(Integer.valueOf(4), sum.getOrderCount());
				assertEquals(BigDecimal.valueOf(63648), sum.getSumAssembly());
				assertEquals(BigDecimal.valueOf(323312), sum
						.getSumOwnProduction());
				assertEquals(BigDecimal.valueOf(21840), sum.getSumTransport());
				assertEquals(BigDecimal.valueOf(0), sum.getSumYesLines());
			}
		}
	}

	@Test
	public void testGetSalesReportDataForOrderVilla() throws Exception {
		Periode periode = new Periode(2009, 2, 2);
		ProductArea productArea = productAreaManager
				.findByName("Garasje villa");
		List<SaleReportSum> salesReportOrders = salesVManager
				.groupSumCountyByProbabilityProductAreaPeriode(
						ProbabilityEnum.PROBABILITY_ORDER, productArea,
						// ProductAreaEnum.getGroupIdxFromAreaName("Garasje villa"),
						periode);
		assertNotNull(salesReportOrders);
		assertEquals(5, salesReportOrders.size());

		SaleReportSum sum = salesReportOrders.get(0);
		// assertEquals("Aust-Agder", sum.getCountyName());
		assertEquals(Integer.valueOf(1), sum.getOrderCount());
		assertEquals(BigDecimal.valueOf(0), sum.getSumAssembly());
		assertEquals(BigDecimal.valueOf(124200), sum.getSumOwnProduction());
		assertEquals(BigDecimal.valueOf(0), sum.getSumTransport());
		assertEquals(BigDecimal.valueOf(0), sum.getSumYesLines());
	}

	@Test
	public void testGetSalesReportDataForConfirmOrderVilla() {
		Periode periode = new Periode(2008, 50, 50);
		List<SaleReportSum> salesReportConfirm = orderManager
				.groupSumCountyByProductAreaConfirmPeriode(productAreaManager
						.findByName("Garasje villa"),
				// ProductAreaEnum.GARAGE_VILLA,
						periode);
		assertNotNull(salesReportConfirm);
		assertEquals(1, salesReportConfirm.size());

//		SaleReportSum sum = salesReportConfirm.get(0);
//		assertEquals("", sum.getCountyName());
//		assertEquals(Integer.valueOf(9), sum.getOrderCount());
//		assertEquals(BigDecimal.valueOf(92620), sum.getSumAssembly());
//		assertEquals(BigDecimal.valueOf(693316), sum.getSumOwnProduction());
//		assertEquals(BigDecimal.valueOf(42320), sum.getSumTransport());

	}

	@Test
	public void getSalesMap() throws Exception{
		ProductArea productArea = productAreaManager
		.findByName("Garasje villa");
		Periode periode = new Periode(2010, 3, 3);
		Map<ProbabilityEnum, Set<SaleReportData>> salesMap=salesVManager.getSalesMap(productArea, periode);
		assertNotNull(salesMap);
	}
	
	@Test
	public void getSalesMapWeek5_2010_Villa() throws Exception{
		URL url = getClass().getClassLoader().getResource("Budsjett_salesman_import_villa.xls");
        budgetManager.importBudget(url.getFile(),BudgetType.SALESMAN);
		
		Periode periode = new Periode(2010, 5, 5);
		Collection<SalesmanGoal> salesGoalList = salesVManager.getSalesGoalList(periode);
		
		assertNotNull(salesGoalList);
		assertEquals(38, salesGoalList.size());
		int count=0;
		for(SalesmanGoal goal:salesGoalList){
			if(goal.getProductArea().getProductArea().equalsIgnoreCase("Garasje villa")){
				if(goal.getSalesman().equalsIgnoreCase("Bjørn Mathisen Solberg")){
				count+=1;
				assertEquals(BigDecimal.valueOf(716995), goal.getOfferSumOwnProduction());
				assertEquals(BigDecimal.valueOf(91576), goal.getOrderSumOwnProduction());
				assertEquals(BigDecimal.valueOf(13), goal.getProcentOrder());
				assertEquals(BigDecimal.valueOf(0.36), goal.getDG());
				
				assertEquals(BigDecimal.valueOf(5711105), goal.getOfferSumOwnProductionAccumulated());
				assertEquals(BigDecimal.valueOf(924196), goal.getOrderSumOwnProductionAccumulated());
				assertEquals(BigDecimal.valueOf(0), goal.getConfirmedOrderSumOwnProductionAccumulated());
				assertEquals(BigDecimal.valueOf(0.35), goal.getDGAccumulated());
				assertEquals(BigDecimal.valueOf(16), goal.getProcentOrderAccumulated());
				
				assertEquals(BigDecimal.valueOf(1000), goal.getBudgetValue());
				
				}
				if(goal.getSalesman().equalsIgnoreCase("Morten Løvheim")){
					assertEquals(BigDecimal.valueOf(1099840), goal.getOfferSumOwnProduction());
					assertEquals(BigDecimal.valueOf(126208), goal.getConfirmedOrderSumOwnProduction());
					
					assertEquals(BigDecimal.valueOf(945680), goal.getOfferSumOwnProductionLastYear());
					assertEquals(BigDecimal.valueOf(227840), goal.getOrderSumOwnProductionLastYear());
					
					assertEquals(BigDecimal.valueOf(24), goal.getProcentOrderLastYear());
					
					assertEquals(BigDecimal.valueOf(4992476), goal.getOfferSumOwnProductionAccumulatedLastYear());
					assertEquals(BigDecimal.valueOf(565120), goal.getOrderSumOwnProductionAccumulatedLastYear());
					assertEquals(BigDecimal.valueOf(11), goal.getProcentOrderAccumulatedLastYear());
				}
				
			}else if(goal.getProductArea().getProductArea().equalsIgnoreCase("Takstol")){
				if(goal.getSalesman().equalsIgnoreCase("Marco Johansson")){
					assertEquals(BigDecimal.valueOf(27), goal.getProcentOrderLastYearDiff());
					assertEquals(BigDecimal.valueOf(-614049), goal.getOfferSumOwnProductionLastYearDiff());
					assertEquals(BigDecimal.valueOf(-13744), goal.getOrderSumOwnProductionLastYearDiff());
					
					assertEquals(BigDecimal.valueOf(86), goal.getProcentOrderAccumulatedLastYearDiff());
					assertEquals(BigDecimal.valueOf(-1108856), goal.getOfferSumOwnProductionAccumulatedLastYearDiff());
					assertEquals(BigDecimal.valueOf(569523), goal.getOrderSumOwnProductionAccumulatedLastYearDiff());
				}
			}
		}
		
	}
	
	@Test
	public void getSalesMapWeek1_2010_Villa() throws Exception{
		URL url = getClass().getClassLoader().getResource("Budsjett_salesman_import_villa.xls");
        budgetManager.importBudget(url.getFile(),BudgetType.SALESMAN);
		
		Periode periode = new Periode(2010, 1, 1);
		Collection<SalesmanGoal> salesGoalList = salesVManager.getSalesGoalList(periode);
		
		assertNotNull(salesGoalList);
		assertEquals(14, salesGoalList.size());
		int count=0;
		for(SalesmanGoal goal:salesGoalList){
			if(goal.getProductArea().getProductArea().equalsIgnoreCase("Garasje villa")){
				if(goal.getSalesman().equalsIgnoreCase("Morten Løvheim")){
					assertEquals(BigDecimal.valueOf(862640), goal.getOfferSumOwnProduction());
					assertEquals(BigDecimal.valueOf(181120), goal.getConfirmedOrderSumOwnProduction());
					
					assertEquals(BigDecimal.valueOf(862640), goal.getOfferSumOwnProductionAccumulated());
					assertEquals(BigDecimal.valueOf(181120), goal.getConfirmedOrderSumOwnProductionAccumulated());
				}
				
			}
		}
		
	}
	
	@Test
	public void getGroupSumByProbabilityProductAreaPeriod() throws Exception{
		ProbabilityEnum probability=ProbabilityEnum.PROBABILITY_OFFER;
		ProductArea productArea=productAreaManager.findByName("Garasje villa");
		Periode periode=new Periode(2010,1,15);
		SaleReportSum saleReportSum=salesVManager.getGroupSumByProbabilityProductAreaPeriod(probability, productArea, periode);
		assertNotNull(saleReportSum);
	}
	
	@Test
	public void getGroupSumByProbabilityProductAreaPeriodFromSnapshot() throws Exception{
		ProbabilityEnum probability=ProbabilityEnum.PROBABILITY_ORDER;
		ProductArea productArea=productAreaManager.findByName("Garasje villa");
		Periode periode=new Periode(2010,2,2);
		SaleReportSum saleReportSum=salesVManager.getGroupSumByProbabilityProductAreaPeriod(probability, productArea, periode);
		assertNotNull(saleReportSum);
	}
	
	
}
