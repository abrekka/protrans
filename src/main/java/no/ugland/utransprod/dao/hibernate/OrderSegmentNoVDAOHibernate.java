package no.ugland.utransprod.dao.hibernate;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.dao.OrderSegmentNoVDAO;
import no.ugland.utransprod.dao.TransportCostDAO;
import no.ugland.utransprod.model.OrderSegmentNoV;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.SaleReportData;
import no.ugland.utransprod.util.report.SaleReportSum;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.google.common.collect.Lists;

public class OrderSegmentNoVDAOHibernate extends BaseDAOHibernate<OrderSegmentNoV> implements OrderSegmentNoVDAO {
    private TransportCostDAO transportCostDAO;

    public final void setTransportCostDAO(final TransportCostDAO aTransportCostDAO) {
	this.transportCostDAO = aTransportCostDAO;
    }

    public OrderSegmentNoVDAOHibernate() {
	super(OrderSegmentNoV.class);
    }

    public List<SaleReportSum> sumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
	List<OrderSegmentNoV> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(), periode.getToWeek(),
		productArea.getProductArea());

	SaleReportSum saleReportSum = aggregateSaleReportSumForCountyOrders(confirmedOrders, "Alle");

	return Lists.newArrayList(saleReportSum);
    }

    public final List<OrderSegmentNoV> findByConfirmWeekProductArea(final Integer year, final Integer weekFrom, final Integer weekTo,
	    final String productAreaName) {
	return (List<OrderSegmentNoV>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		Criteria criteria = session
			.createCriteria(OrderSegmentNoV.class)
			.add(Expression.sql("datepart(year,agreement_date)=?", year, Hibernate.INTEGER))
			.add(Restrictions.not(Restrictions.ilike("constructionTypeName", "%tilleggsordre%")))
			.add(Expression.sql("dbo.GetISOWeekNumberFromDate(agreement_date) between ? and ?", new Object[] { weekFrom, weekTo },
				new Type[] { Hibernate.INTEGER, Hibernate.INTEGER })).addOrder(org.hibernate.criterion.Order.asc("salesman"))
		// .createCriteria("constructionType").add(Restrictions.not(Restrictions.ilike("name",
		// "%tilleggsordre%")))
		;
		if (productAreaName != null && !"Proff".equalsIgnoreCase(productAreaName)) {
		    criteria.add(Restrictions.eq("productAreaName", productAreaName));
		    criteria.add(Restrictions.or(Restrictions.isNull("segmentNo"), Restrictions.ne("segmentNo", "2")));
		    // criteria.createCriteria("productArea").add(Restrictions.eq("productArea",
		    // productAreaName));
		} else {
		    criteria.add(Restrictions.eq("segmentNo", "2"));
		}
		return criteria.list();
	    }

	});
    }

    private SaleReportSum aggregateSaleReportSumForCountyOrders(final List<OrderSegmentNoV> orders, final String countyName) {
	SaleReportSum saleReportSum = new SaleReportSum();
	saleReportSum.setCountyName(countyName);

	for (OrderSegmentNoV order : orders) {
	    // lazyLoad(order, new LazyLoadOrderEnum[] {
	    // LazyLoadOrderEnum.ORDER_COSTS });
	    saleReportSum.increaseCount();

	    saleReportSum.addSumAssembly(order.getAssemblyCost());
	    saleReportSum.addSumOwnProduction(order.getOwnProduction());
	    saleReportSum.addSumTransport(order.getDeliveryCost());
	    saleReportSum.addSumDb(order.getContributionMargin());
	    saleReportSum.addSumYesLines(order.getJaLinjer());

	}

	return saleReportSum;
    }

    public List<SaleReportSum> groupSumCountyByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
	List<OrderSegmentNoV> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(), periode.getToWeek(),
		productArea == null ? null : productArea.getProductArea());

	Map<String, List<OrderSegmentNoV>> orderByCountyMap = arrangeOrdersByCounty(confirmedOrders);

	List<SaleReportSum> saleReportSums = aggregateCountySaleReportSums(orderByCountyMap);

	return saleReportSums;
    }

    private Map<String, List<OrderSegmentNoV>> arrangeOrdersByCounty(final List<OrderSegmentNoV> orders) {
	Map<String, List<OrderSegmentNoV>> countyOrders = new Hashtable<String, List<OrderSegmentNoV>>();

	if (orders != null) {
	    for (OrderSegmentNoV order : orders) {
		addOrderToCountyMap(countyOrders, order);
	    }
	}
	return countyOrders;
    }

    private void addOrderToCountyMap(final Map<String, List<OrderSegmentNoV>> countyOrders, final OrderSegmentNoV order) {
	// String countyName = getCountyName(order);
	String countyName = order.getCountyName() == null ? "" : order.getCountyName();
	List<OrderSegmentNoV> countyOrderList = countyOrders.get(countyName);
	if (countyOrderList == null) {
	    countyOrderList = new ArrayList<OrderSegmentNoV>();
	}
	countyOrderList.add(order);
	countyOrders.put(countyName, countyOrderList);
    }

    // private String getCountyName(final OrderSegmentNoV order){
    //
    // String countyName =
    // transportCostDAO.findCountyNameByPostalCode(order.getPostalCode());
    // if (countyName == null) {
    // countyName = "";
    // }
    // return countyName;
    // }

    private List<SaleReportSum> aggregateCountySaleReportSums(final Map<String, List<OrderSegmentNoV>> countyOrders) {
	List<SaleReportSum> saleReportSums = new ArrayList<SaleReportSum>();
	if (countyOrders != null) {
	    Set<String> countyNames = countyOrders.keySet();
	    for (String countyName : countyNames) {
		List<OrderSegmentNoV> orders = countyOrders.get(countyName);
		saleReportSums.add(aggregateSaleReportSumForCountyOrders(orders, countyName));
	    }
	}
	return saleReportSums;
    }

    public List<SaleReportSum> groupSumSalesmanByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
	List<OrderSegmentNoV> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(), periode.getToWeek(),
		productArea == null ? null : productArea.getProductArea());

	Map<String, List<OrderSegmentNoV>> orderByCountyMap = arrangeOrdersBySalesman(confirmedOrders);

	List<SaleReportSum> saleReportSums = aggregateSalesmanSaleReportSums(orderByCountyMap);

	return saleReportSums;
    }

    private Map<String, List<OrderSegmentNoV>> arrangeOrdersBySalesman(final List<OrderSegmentNoV> orders) {
	Map<String, List<OrderSegmentNoV>> salesmanOrders = new Hashtable<String, List<OrderSegmentNoV>>();

	if (orders != null) {
	    for (OrderSegmentNoV order : orders) {
		addOrderToSalesmanMap(salesmanOrders, order);
	    }
	}
	return salesmanOrders;
    }

    private void addOrderToSalesmanMap(final Map<String, List<OrderSegmentNoV>> salesmanOrders, final OrderSegmentNoV order) {
	String salesmanName = order.getSalesman() != null ? order.getSalesman() : "";

	List<OrderSegmentNoV> salesmanOrderList = salesmanOrders.get(salesmanName);
	if (salesmanOrderList == null) {
	    salesmanOrderList = new ArrayList<OrderSegmentNoV>();
	}
	salesmanOrderList.add(order);
	salesmanOrders.put(salesmanName, salesmanOrderList);
    }

    private List<SaleReportSum> aggregateSalesmanSaleReportSums(final Map<String, List<OrderSegmentNoV>> salesmanOrders) {
	List<SaleReportSum> saleReportSums = new ArrayList<SaleReportSum>();
	if (salesmanOrders != null) {
	    Set<String> salesmanNames = salesmanOrders.keySet();
	    for (String name : salesmanNames) {
		List<OrderSegmentNoV> orders = salesmanOrders.get(name);
		saleReportSums.add(aggregateSaleReportSumForSalesmanOrders(orders, name));
	    }
	}
	return saleReportSums;
    }

    private SaleReportSum aggregateSaleReportSumForSalesmanOrders(final List<OrderSegmentNoV> orders, final String salesmanName) {
	SaleReportSum saleReportSum = new SaleReportSum();
	saleReportSum.setSalesman(salesmanName);

	for (OrderSegmentNoV order : orders) {
	    // lazyLoad(order, new LazyLoadOrderEnum[] {
	    // LazyLoadOrderEnum.ORDER_COSTS });
	    saleReportSum.increaseCount();

	    saleReportSum.addSumAssembly(order.getAssemblyCost());
	    saleReportSum.addSumOwnProduction(order.getOwnProduction());
	    saleReportSum.addSumTransport(order.getDeliveryCost());
	    saleReportSum.addSumDb(order.getContributionMargin());
	    saleReportSum.addSumYesLines(order.getJaLinjer());

	}

	return saleReportSum;
    }

    public List<SaleReportData> getSaleReportByProductAreaPeriode(ProductArea productArea, Periode periode) {
	List<OrderSegmentNoV> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(), periode.getToWeek(),
		productArea == null ? null : productArea.getProductArea());

	return createSaleReportList(confirmedOrders);
    }

    private List<SaleReportData> createSaleReportList(final List<OrderSegmentNoV> orders) {
	List<SaleReportData> saleReportDataList = new ArrayList<SaleReportData>();
	if (orders != null) {
	    for (OrderSegmentNoV order : orders) {
		// lazyLoad(order, new LazyLoadOrderEnum[] {
		// LazyLoadOrderEnum.ORDER_COSTS });
		// String countyName =
		// transportCostDAO.findCountyNameByPostalCode(order.getPostalCode());
		saleReportDataList.add(new SaleReportData("Avrop", order.getCountyName(), order.getSalesman(), String.valueOf(order.getCustomerNr()),
			order.getCustomerFullName(), order.getOrderNr(), order.getOwnProduction(), order.getDeliveryCost(), order.getAssemblyCost(),
			order.getJaLinjer(), order.getContributionMargin(), order.getContributionRate(), order.getOrderDate(), Integer.valueOf(order
				.getProductAreaNr()), order.getSegmentNo(),order.getAreal()));
	    }
	}
	return saleReportDataList;
    }

    public SaleReportSum groupSumByProductAreaConfirmPeriode(ProductArea productArea, Periode periode) {
	List<OrderSegmentNoV> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(), periode.getToWeek(),
		productArea.getProductArea());

	SaleReportSum saleReportSum = aggregateSaleReportSumForCountyOrders(confirmedOrders, "");

	return saleReportSum;
    }

    public Integer countByProductAreaPeriode(ProductArea productArea, Periode periode) {
	List<OrderSegmentNoV> confirmedOrders = findByConfirmWeekProductArea(periode.getYear(), periode.getWeek(), periode.getToWeek(),
		productArea == null ? null : productArea.getProductArea());

	if (confirmedOrders != null) {
	    return confirmedOrders.size();
	}
	return 0;
    }

}
