package no.ugland.utransprod.service;

import static junit.framework.Assert.assertNotNull;

import java.util.List;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.TakstolProductionV;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class TakstolProductionVManagerTest {
    private OrderManager orderManager;
    private OrderLine orderLine;
    private OrderLineManager orderLineManager;

    @Before
    public void settopp() {
	orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
	orderLineManager = (OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME);
    }

    @After
    public void ryddopp() {
	if (orderLine != null) {
	    orderLine.setCuttingStarted(null);
	    orderLine.setCuttingDone(null);
	    orderLineManager.saveOrderLine(orderLine);
	}
    }

    @Test
    @Ignore
    public void skalHenteFerdigKapping() {
	Order order = orderManager.findByOrderNr("65386");
	orderManager.lazyLoadTree(order);
	orderLine = order.getOrderLine("Takstoler");
	assertNotNull(orderLine);
	orderLine.setCuttingDone(Util.getCurrentDate());

	orderLineManager.saveOrderLine(orderLine);
	TakstolProductionVManager takstolProductionVManager = (TakstolProductionVManager) ModelUtil.getBean(TakstolProductionVManager.MANAGER_NAME);
	List<TakstolProductionV> takstoler = takstolProductionVManager.findByOrderNr("65386");
	assertNotNull(takstoler);

	for (TakstolProductionV takstolProductionV : takstoler) {
	    if (takstolProductionV.getNumberOfItems() == 38) {
		assertNotNull(takstolProductionV.getCuttingDone());
	    }
	}
    }

    @Test
    @Ignore
    public void skalHentStartetKapping() {

	Order order = orderManager.findByOrderNr("65386");
	orderManager.lazyLoadTree(order);
	orderLine = order.getOrderLine("Takstoler");
	assertNotNull(orderLine);
	orderLine.setCuttingStarted(Util.getCurrentDate());

	orderLineManager.saveOrderLine(orderLine);
	TakstolProductionVManager takstolProductionVManager = (TakstolProductionVManager) ModelUtil.getBean(TakstolProductionVManager.MANAGER_NAME);
	List<TakstolProductionV> takstoler = takstolProductionVManager.findByOrderNr("65386");
	assertNotNull(takstoler);

	for (TakstolProductionV takstolProductionV : takstoler) {
	    if (takstolProductionV.getNumberOfItems() == 38) {
		assertNotNull(takstolProductionV.getCuttingStarted());
	    }
	}

    }

}
