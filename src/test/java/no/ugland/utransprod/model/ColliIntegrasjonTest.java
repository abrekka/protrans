package no.ugland.utransprod.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class ColliIntegrasjonTest {

	@Test
	public void skal_ikke_ta_med_takstein_som_ikke_sendes_fra_gg() {
		OrderManager orderManager = (OrderManager) ModelUtil
				.getBean(OrderManager.MANAGER_NAME);
		Order order = orderManager.findByOrderNr("63484");
		assertNotNull(order);
		orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] {
				LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.ORDER_LINES,
				LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES,
				LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES });

		boolean fantTakstein = false;
		for (Colli colli : order.getCollies()) {
			if ("Takstein".equalsIgnoreCase(colli.getColliName())) {
				fantTakstein = true;
				assertEquals("",colli.getDetails());
			}
		}
		assertTrue(fantTakstein);
	}

	
}
