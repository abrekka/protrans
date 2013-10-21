package no.ugland.utransprod.model;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

import java.util.Set;

import no.ugland.utransprod.test.FastTests;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.google.common.collect.Sets;
@Category(FastTests.class)
public class OrderLineTest {
	
	
	
	@Test
	public void skal_ta_med_baade_beskrivelse_fra_visma_og_protrans(){
		OrderLine orderLine = new OrderLine();
		Ordln ordln=new Ordln();
		ordln.setDescription("VISMA");
		orderLine.setOrdln(ordln);
		OrderLineAttribute orderLineAttribute=new OrderLineAttribute();
		orderLineAttribute.setOrderLineAttributeName("ProTransAttributtnavn");
		orderLineAttribute.setAttributeValue("ProTransVerdi");
		Set<OrderLineAttribute> orderLineAttributes=Sets.newHashSet(orderLineAttribute);
		orderLine.setOrderLineAttributes(orderLineAttributes);
		assertEquals("VISMA;ProTransAttributtnavn:ProTransVerdi;",orderLine.getAttributesAsString());
	}

	

	

	@Test
	public void skal_ikka_ta_med_ordrelinje_for_takstein_som_ikke_skal_sendes_fra_gg() {
		OrderLine orderLine = new OrderLine();
		ArticleType articleType = new ArticleType();
		articleType.setArticleTypeName("Takstein");
		orderLine.setArticleType(articleType);
		orderLine.setNumberOfItems(1);
		Set<OrderLineAttribute> orderLineAttributes = Sets.newHashSet();
		OrderLineAttribute orderLineAttribute = new OrderLineAttribute();
		orderLineAttribute.setOrderLineAttributeName("Endemøne");
		orderLineAttribute.setOrderLineAttributeValue("0");
		orderLineAttributes.add(orderLineAttribute);
		orderLineAttribute = new OrderLineAttribute();
		orderLineAttribute.setOrderLineAttributeName("Taksteintype");
		orderLineAttribute.setOrderLineAttributeValue("type");
		orderLineAttributes.add(orderLineAttribute);
		orderLineAttribute = new OrderLineAttribute();
		orderLineAttribute.setOrderLineAttributeName("Sendes fra GG");
		orderLineAttribute.setOrderLineAttributeValue("Nei");
		orderLineAttributes.add(orderLineAttribute);
		orderLine.setOrderLineAttributes(orderLineAttributes);
		String details = orderLine.getDetailsWithoutNoAttributes();
		System.out.println(details);
		assertTrue(StringUtils.isBlank(details));
	}
}
