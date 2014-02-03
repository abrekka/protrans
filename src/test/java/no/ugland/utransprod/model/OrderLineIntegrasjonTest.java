package no.ugland.utransprod.model;

import static junit.framework.Assert.assertNotNull;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class OrderLineIntegrasjonTest {

    @Test
    @Ignore
    public void skalSetteFerdigKappet() {
	OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	Order order = orderManager.findByOrderNr("65386");
	orderManager.lazyLoadTree(order);
	OrderLine orderLine = order.getOrderLine("Takstoler");
	assertNotNull(orderLine);
	orderLine.setCuttingDone(Util.getCurrentDate());
	OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME);
	orderLineManager.saveOrderLine(orderLine);

	order = orderManager.findByOrderNr("65386");

	orderManager.lazyLoadTree(order);
	orderLine = order.getOrderLine("Takstoler");
	assertNotNull(orderLine);
	assertNotNull(orderLine.getCuttingDone());

	orderLine.setCuttingDone(null);
	orderLineManager.saveOrderLine(orderLine);
    }

    @Test
    @Ignore
    public void skalSetteStartetKapping() {
	OrderManager orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	Order order = orderManager.findByOrderNr("65386");
	orderManager.lazyLoadTree(order);
	OrderLine orderLine = order.getOrderLine("Takstoler");
	assertNotNull(orderLine);
	orderLine.setCuttingStarted(Util.getCurrentDate());
	OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME);
	orderLineManager.saveOrderLine(orderLine);

	order = orderManager.findByOrderNr("65386");

	orderManager.lazyLoadTree(order);
	orderLine = order.getOrderLine("Takstoler");
	assertNotNull(orderLine);
	assertNotNull(orderLine.getCuttingStarted());

	orderLine.setCuttingStarted(null);
	orderLineManager.saveOrderLine(orderLine);
    }

}
