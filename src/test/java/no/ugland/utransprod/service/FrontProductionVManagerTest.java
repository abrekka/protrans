package no.ugland.utransprod.service;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.FrontProductionV;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class FrontProductionVManagerTest {
    private FrontProductionVManager frontProductionVManager = (FrontProductionVManager) ModelUtil.getBean("frontProductionVManager");
    private OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME);

    @Test
    public void skalHaOverstyrtTidsforbruk() {
	List<Produceable> allApplyable = frontProductionVManager.findAllApplyable();
	FrontProductionV front = (FrontProductionV) allApplyable.get(0);
	OrderLine orderLine = orderLineManager.findByOrderLineId(front.getOrderLineId());
	orderLine.setRealProductionHours(BigDecimal.valueOf(2.5));
	orderLineManager.saveOrderLine(orderLine);

	allApplyable = frontProductionVManager.findAllApplyable();
	boolean funnetFront = false;
	for (Produceable produceable : allApplyable) {
	    if (produceable.getOrderLineId().equals(orderLine.getOrderLineId())) {
		funnetFront = true;
		Assertions.assertThat(((FrontProductionV) produceable).getRealProductionHours()).isEqualByComparingTo(BigDecimal.valueOf(2.5));
	    }
	}
	Assertions.assertThat(funnetFront).isTrue();
    }

    @Test
    public void testFindAll() {
	assertNotNull(frontProductionVManager.findAllApplyable());
    }
}
