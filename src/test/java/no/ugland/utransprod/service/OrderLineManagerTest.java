package no.ugland.utransprod.service;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class OrderLineManagerTest {
    private OrderLineManager orderLineManager;

    @Before
    public void setUp() throws Exception {
	orderLineManager = (OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME);
    }

    @Test
    @Ignore
    public void skalLagreOverstyringAvTidsforbruk() {
	OrderLine orderLine = orderLineManager.findByOrderLineId(26);
	orderLine.setRealProductionHours(BigDecimal.valueOf(20));
	orderLineManager.saveOrderLine(orderLine);
	orderLine = orderLineManager.findByOrderLineId(26);
	Assertions.assertThat(orderLine.getRealProductionHours()).isEqualByComparingTo(BigDecimal.valueOf(20));
    }

    @Test
    public void testFindAllConstructionTypeNotSent() {
	ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean("productAreaManager");
	ProductArea productArea = productAreaManager.findByName("Garasje villa");
	List<OrderLine> orderLines = orderLineManager.findAllConstructionTypeNotSent(productArea);
	assertNotNull(orderLines);
    }

}
