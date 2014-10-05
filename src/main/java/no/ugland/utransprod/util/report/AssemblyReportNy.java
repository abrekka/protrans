package no.ugland.utransprod.util.report;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.FakturagrunnlagV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.CraningCostManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.rules.Craning;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Lists;

public class AssemblyReportNy {

    private Order order;

    private List<FakturagrunnlagV> fakturagrunnlag;

    private BigDecimal craningCost;
    private CraningCostManager craningCostManager;

    public AssemblyReportNy(final CraningCostManager aCraningCostManager, final Order order, final List<FakturagrunnlagV> fakturagrunnlag) {
	craningCostManager = aCraningCostManager;
	setOrder(order);
	setFakturagrunnlag(fakturagrunnlag);

	if (getCraningCost() == null) {
	    OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
	    orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES,
		    LazyLoadOrderEnum.ORDER_COSTS, LazyLoadOrderEnum.COMMENTS });
	    OrderLine trossOrderLine = order.getOrderLine("Takstoler");
	    OrderLine constructionTypeOrderLine = order.getOrderLine("Garasjetype");
	    OrderLine portOrderLine = order.getOrderLine("Port");
	    Craning craning = Craning.with(craningCostManager).trossOrderLine(trossOrderLine).constructionTypeOrderLine(constructionTypeOrderLine)
		    .portOrderLine(portOrderLine).build();
	    setCraningCost(craning.getCostValue());
	}

    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.util.report.AssemblyReport#getCraningAddition()
     */
    public BigDecimal getCraningCost() {
	if (craningCost == null) {
	    if (fakturagrunnlag != null) {
		for (FakturagrunnlagV linje : fakturagrunnlag) {
		    if (linje.erKranbil()) {
			return linje.getPrice();
		    }
		}
	    }
	} else {
	    return craningCost;
	}
	return BigDecimal.ZERO;
    }

    public Integer getBestillingsnrMontering() {
	if (fakturagrunnlag != null) {
	    for (FakturagrunnlagV linje : fakturagrunnlag) {
		if (linje.erMontering()) {
		    return linje.getPurcno();
		}
	    }
	}
	return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.util.report.AssemblyReport#getDrivingAddition()
     */
    public BigDecimal getDrivingAddition() {
	if (fakturagrunnlag != null) {
	    for (FakturagrunnlagV linje : fakturagrunnlag) {
		if (linje.erFrakt()) {
		    return linje.getPrice();
		}
	    }
	}
	return BigDecimal.valueOf(0);

    }

    public Order getOrder() {
	return order;
    }

    public void setOrder(final Order aOrder) {
	this.order = aOrder;
    }

    /*
     * (non-Javadoc)
     * 
     * @see no.ugland.utransprod.util.report.AssemblyReport#getVismaOrderLines()
     */
    public List<FakturagrunnlagV> getFakturagrunnlag() {
	return Lists.newArrayList(Iterables.filter(fakturagrunnlag, utenMonteringVilla()));
    }

    private Predicate<FakturagrunnlagV> utenMonteringVilla() {
	return new Predicate<FakturagrunnlagV>() {

	    public boolean apply(FakturagrunnlagV fakturagrunnlagV) {
		return !fakturagrunnlagV.erMontering();
	    }
	};
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.util.report.AssemblyReport#setVismaOrderLines(java
     * .util.List)
     */
    public void setFakturagrunnlag(final List<FakturagrunnlagV> fakturagrunnlag) {
	this.fakturagrunnlag = fakturagrunnlag;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * no.ugland.utransprod.util.report.AssemblyReport#setCraningAddition(java
     * .math.BigDecimal)
     */
    public void setCraningCost(final BigDecimal aCraningCost) {
	this.craningCost = aCraningCost;
    }

    public BigDecimal getTotalSum() {
	BigDecimal totalSum = BigDecimal.ZERO;

	for (FakturagrunnlagV linje : getFakturagrunnlag()) {
	    totalSum = totalSum.add(linje.getSumLineMedVerdi() == null ? BigDecimal.ZERO : linje.getSumLineMedVerdi());
	}
	return totalSum;
    }

    public BigDecimal getTotalSumInklTillegg() {
	return getTotalSum().add(getCraningCost());
    }

    /*
     * public static AssemblyReport create(final Order order, final List<Ordln>
     * vismaOrderLines) { AssemblyReport assemblyReport = new
     * AssemblyReportImpl(); assemblyReport.setOrder(order);
     * assemblyReport.setVismaOrderLines(vismaOrderLines);
     * 
     * if (assemblyReport.getCraningAddition() == null) { OrderManager
     * orderManager = (OrderManager) ModelUtil .getBean("orderManager");
     * orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] {
     * LazyLoadOrderEnum.ORDER_LINES, LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES,
     * LazyLoadOrderEnum.ORDER_COSTS}); OrderLine trossOrderLine =
     * order.getOrderLine("Takstoler"); OrderLine constructionTypeOrderLine =
     * order .getOrderLine("Garasjetype"); OrderLine portOrderLine =
     * order.getOrderLine("Port"); CraningRulesEngine craningRulesEngine = new
     * CraningRulesEngine(craningCostManager); Craning craning = new
     * Craning(trossOrderLine, constructionTypeOrderLine, portOrderLine);
     * craningRulesEngine.assignCraning(craning);
     * assemblyReport.setCraningAddition(craning.getCostValue()); } return
     * assemblyReport; }
     */
}
