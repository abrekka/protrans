package no.ugland.utransprod.service;

import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.GavlProductionV;
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
public class GavlProductionVManagerTest {
    private GavlProductionVManager gavlProductionVManager = (GavlProductionVManager) ModelUtil.getBean("gavlProductionVManager");
    private OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME);

    @Test
    public void skalHaOverstyrtTidsforbruk() {
	List<Produceable> allApplyable = gavlProductionVManager.findAllApplyable();
	GavlProductionV gavl = (GavlProductionV) allApplyable.get(0);
	OrderLine orderLine = orderLineManager.findByOrderLineId(gavl.getOrderLineId());
	orderLine.setRealProductionHours(BigDecimal.valueOf(2.5));
	orderLineManager.saveOrderLine(orderLine);

	allApplyable = gavlProductionVManager.findAllApplyable();
	boolean funnetGavl = false;
	for (Produceable produceable : allApplyable) {
	    if (produceable.getOrderLineId().equals(orderLine.getOrderLineId())) {
		funnetGavl = true;
		Assertions.assertThat(((GavlProductionV) produceable).getRealProductionHours()).isEqualByComparingTo(BigDecimal.valueOf(2.5));
	    }
	}
	Assertions.assertThat(funnetGavl).isTrue();
    }

    @Test
    public void testFindAll() {
	assertNotNull(gavlProductionVManager.findAllApplyable());
    }
}
