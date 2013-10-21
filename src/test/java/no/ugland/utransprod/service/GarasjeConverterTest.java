package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.test.FastTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
@Category(FastTests.class)
public class GarasjeConverterTest {

	@Mock
	private OrdlnManager ordlnManager;
	
	@Before
	public void settopp(){
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testConvertConstructionType(){
		ConstructionTypeAttributesConverter garasjeConverter = new GarasjeConverter(ordlnManager);
		ProductAreaGroup productAreaGroup=new ProductAreaGroup();
		productAreaGroup.setProductAreaGroupName("Garasje");
		ProductArea productArea = new ProductArea();
		productArea.setProductAreaGroup(productAreaGroup);
		Order order = new Order();
		order.setProductArea(productArea);
		
		Ord ord = new Ord();
		ord.setOrdno(111);
		ord.setInf("A3");
		ord.setFree1("150");
		ord.setFree2("20");
		
		
		
		OrderLine orderLine = new OrderLine();
		orderLine.setArticlePath("Garasjetype");
		
		ConstructionTypeArticleAttribute constructionTypeArticleAttribute=new ConstructionTypeArticleAttribute();
		ArticleTypeAttribute articleTypeAttribute=new ArticleTypeAttribute();
		Attribute attribute=new Attribute();
		attribute.setName("Vegghøyde");
		articleTypeAttribute.setAttribute(attribute);
		
		constructionTypeArticleAttribute.setArticleTypeAttribute(articleTypeAttribute);
		
		OrderLineAttribute orderLineAttribute = new OrderLineAttribute();
		orderLineAttribute.setConstructionTypeArticleAttribute(constructionTypeArticleAttribute);
		orderLine.addAttribute(orderLineAttribute);
		
		constructionTypeArticleAttribute=new ConstructionTypeArticleAttribute();
		articleTypeAttribute=new ArticleTypeAttribute();
		attribute=new Attribute();
		attribute.setName("Murhøyde");
		articleTypeAttribute.setAttribute(attribute);
		
		orderLineAttribute = new OrderLineAttribute();
		orderLineAttribute.setConstructionTypeArticleAttribute(constructionTypeArticleAttribute);
		orderLine.addAttribute(orderLineAttribute);
		
		constructionTypeArticleAttribute.setArticleTypeAttribute(articleTypeAttribute);
		
		constructionTypeArticleAttribute=new ConstructionTypeArticleAttribute();
		articleTypeAttribute=new ArticleTypeAttribute();
		attribute=new Attribute();
		attribute.setName("Bredde");
		articleTypeAttribute.setAttribute(attribute);
		
		orderLineAttribute = new OrderLineAttribute();
		orderLineAttribute.setConstructionTypeArticleAttribute(constructionTypeArticleAttribute);
		orderLine.addAttribute(orderLineAttribute);
		
		constructionTypeArticleAttribute.setArticleTypeAttribute(articleTypeAttribute);
		
		constructionTypeArticleAttribute=new ConstructionTypeArticleAttribute();
		articleTypeAttribute=new ArticleTypeAttribute();
		attribute=new Attribute();
		attribute.setName("Lengde");
		articleTypeAttribute.setAttribute(attribute);
		
		orderLineAttribute = new OrderLineAttribute();
		orderLineAttribute.setConstructionTypeArticleAttribute(constructionTypeArticleAttribute);
		orderLine.addAttribute(orderLineAttribute);
		
		constructionTypeArticleAttribute.setArticleTypeAttribute(articleTypeAttribute);
		
		order.addOrderLine(orderLine);
		
		final Ordln ordln=new Ordln();
		ordln.setWdtu(BigDecimal.valueOf(400));
		ordln.setLgtU(BigDecimal.valueOf(800));
		
		when(ordlnManager.findByOrdnoAndPrCatNo2(111, 1)).thenReturn(ordln);
		
		
		
		
		garasjeConverter.setConstructionTypeAttributes(ord, order);
		assertNotNull(order);
		OrderLine garageOrderLine = order.getOrderLine("Garasjetype");
		assertNotNull(garageOrderLine);
		assertEquals(true, garageOrderLine!=OrderLine.UNKNOWN);
		OrderLineAttribute testOrderLineAttribute=garageOrderLine.getAttributeByName("Vegghøyde");
		assertNotNull(testOrderLineAttribute);
		assertEquals(true, testOrderLineAttribute!=OrderLineAttribute.UNKNOWN);
		assertEquals("150", testOrderLineAttribute.getOrderLineAttributeValue());
		
		testOrderLineAttribute=garageOrderLine.getAttributeByName("Murhøyde");
		assertEquals(true, testOrderLineAttribute!=OrderLineAttribute.UNKNOWN);
		assertEquals("20", testOrderLineAttribute.getOrderLineAttributeValue());
		
		testOrderLineAttribute=garageOrderLine.getAttributeByName("Bredde");
		assertEquals(true, testOrderLineAttribute!=OrderLineAttribute.UNKNOWN);
		assertEquals("400", testOrderLineAttribute.getOrderLineAttributeValue());
		
		testOrderLineAttribute=garageOrderLine.getAttributeByName("Lengde");
		assertEquals(true, testOrderLineAttribute!=OrderLineAttribute.UNKNOWN);
		assertEquals("800", testOrderLineAttribute.getOrderLineAttributeValue());
	}
}
