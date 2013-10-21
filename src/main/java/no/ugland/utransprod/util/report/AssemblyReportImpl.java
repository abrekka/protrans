package no.ugland.utransprod.util.report;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.service.CraningCostManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.rules.Craning;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

public final class AssemblyReportImpl implements AssemblyReport {
    private static final String FRAKT = "FRAKT";

    private static final String GARFRAKT = "GARFRAKT";

    private static final String KRANBIL = "KRANBIL";


    private Order order;

    private List<Ordln> vismaOrderLines;

    private BigDecimal craningAddition;
    private CraningCostManager craningCostManager;

    @Inject
    public AssemblyReportImpl(final CraningCostManager aCraningCostManager,@Assisted final Order order,
            @Assisted final List<Ordln> vismaOrderLines) {
        craningCostManager=aCraningCostManager;
        setOrder(order);
        setVismaOrderLines(vismaOrderLines);

        if (getCraningAddition() == null) {
            OrderManager orderManager = (OrderManager) ModelUtil
                    .getBean("orderManager");
            orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] {
                    LazyLoadOrderEnum.ORDER_LINES,
                    LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES,
                    LazyLoadOrderEnum.ORDER_COSTS,
                    LazyLoadOrderEnum.COMMENTS});
            OrderLine trossOrderLine = order.getOrderLine("Takstoler");
            OrderLine constructionTypeOrderLine = order
                    .getOrderLine("Garasjetype");
            OrderLine portOrderLine = order.getOrderLine("Port");
            Craning craning = Craning.with(craningCostManager).trossOrderLine(trossOrderLine).constructionTypeOrderLine(constructionTypeOrderLine).portOrderLine(portOrderLine).build();
            setCraningAddition(craning.getCostValue());
        }

    }

    /* (non-Javadoc)
     * @see no.ugland.utransprod.util.report.AssemblyReport#getCraningAddition()
     */
    public BigDecimal getCraningAddition() {
        if (craningAddition == null) {
            if (vismaOrderLines != null) {
                for (Ordln ordln : vismaOrderLines) {
                    if(ordln.getProd()!=null){
                    String productnr = ordln.getProd().getProdNo();
                    if (productnr != null
                            && productnr.equalsIgnoreCase(KRANBIL)) {
                        return ordln.getPrice();
                    }
                    }
                }
            }
        } else {
            return craningAddition;
        }
        return null;
    }

    /* (non-Javadoc)
     * @see no.ugland.utransprod.util.report.AssemblyReport#getDrivingAddition()
     */
    public BigDecimal getDrivingAddition() {
        if (vismaOrderLines != null) {
            for (Ordln ordln : vismaOrderLines) {
                if(ordln.getProd()!=null){
                String productnr = ordln.getProd().getProdNo();
                if (productnr != null
                        && (productnr.equalsIgnoreCase(FRAKT) || productnr
                                .equalsIgnoreCase(GARFRAKT))) {
                    return ordln.getFree1();
                }
                }
            }
        }
        return BigDecimal.valueOf(0);

    }

    /* (non-Javadoc)
     * @see no.ugland.utransprod.util.report.AssemblyReport#getOrder()
     */
    public Order getOrder() {
        return order;
    }

    /* (non-Javadoc)
     * @see no.ugland.utransprod.util.report.AssemblyReport#setOrder(no.ugland.utransprod.model.Order)
     */
    public void setOrder(final Order aOrder) {
        this.order = aOrder;
    }

    /* (non-Javadoc)
     * @see no.ugland.utransprod.util.report.AssemblyReport#getVismaOrderLines()
     */
    public List<Ordln> getVismaOrderLines() {
        return vismaOrderLines;
    }

    /* (non-Javadoc)
     * @see no.ugland.utransprod.util.report.AssemblyReport#setVismaOrderLines(java.util.List)
     */
    public void setVismaOrderLines(final List<Ordln> someVismaOrderLines) {
        this.vismaOrderLines = someVismaOrderLines;
    }

    /* (non-Javadoc)
     * @see no.ugland.utransprod.util.report.AssemblyReport#setCraningAddition(java.math.BigDecimal)
     */
    public void setCraningAddition(final BigDecimal aCraningAddition) {
        this.craningAddition = aCraningAddition;
    }

    /*public static AssemblyReport create(final Order order,
            final List<Ordln> vismaOrderLines) {
        AssemblyReport assemblyReport = new AssemblyReportImpl();
        assemblyReport.setOrder(order);
        assemblyReport.setVismaOrderLines(vismaOrderLines);

        if (assemblyReport.getCraningAddition() == null) {
            OrderManager orderManager = (OrderManager) ModelUtil
                    .getBean("orderManager");
            orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] {
                    LazyLoadOrderEnum.ORDER_LINES,
                    LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES,
                    LazyLoadOrderEnum.ORDER_COSTS});
            OrderLine trossOrderLine = order.getOrderLine("Takstoler");
            OrderLine constructionTypeOrderLine = order
                    .getOrderLine("Garasjetype");
            OrderLine portOrderLine = order.getOrderLine("Port");
            CraningRulesEngine craningRulesEngine = new CraningRulesEngine(craningCostManager);
            Craning craning = new Craning(trossOrderLine,
                    constructionTypeOrderLine, portOrderLine);
            craningRulesEngine.assignCraning(craning);
            assemblyReport.setCraningAddition(craning.getCostValue());
        }
        return assemblyReport;
    }*/
}
