package no.ugland.utransprod.model;

import static junit.framework.Assert.assertEquals;
import no.ugland.utransprod.model.OrderLine.AttributeInfo;
import no.ugland.utransprod.test.FastTests;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class AttributeInfoTest {

	@Test
	public void skal_generere_attributt_info() {
		OrderLine orderLine = new OrderLine();
		orderLine.setAttributeInfo("Test:Ja;Test2:Nei");
		assertEquals("Test:Ja;Test2:Nei", AttributeInfo.WITH_ALL_ATTRIBUTES
				.getAttributeInfo(orderLine));
	}

	@Test
	public void skal_generere_attributt_info_uten_nei_attributter() {
		OrderLine orderLine = new OrderLine();
		orderLine.setAttributeInfo("Test:Ja;Test2:Nei");
		assertEquals("Test", AttributeInfo.WITHOUT_NO_ATTRIBUTES
				.getAttributeInfo(orderLine));
	}
}
