package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.OrdlnPK;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
@Category(FastTests.class)
public class KledningConverterTest {

	@Mock
	private ManagerRepository managerRepository;
	
	@Before
	public void settopp(){
		MockitoAnnotations.initMocks(this);
		ConstructionTypeManager constructionTypeManager=(ConstructionTypeManager)ModelUtil.getBean(ConstructionTypeManager.MANAGER_NAME);
		when(managerRepository.getConstructionTypeManager()).thenReturn(constructionTypeManager);
	}

	@Test
	public void convertGavlKledning(){
		OrdlnManager ordlnManager=(OrdlnManager)ModelUtil.getBean(OrdlnManager.MANAGER_NAME);
		when(managerRepository.getOrdlnManager()).thenReturn(ordlnManager);
		
		ArticleType articleType=new ArticleType();
		articleType.setArticleTypeName("Kledning");
		Ordln ordln=new Ordln();
		ordln.setOrdlnPK(new OrdlnPK(1, 1));
		ordln.setFree4(BigDecimal.valueOf(2));
		Order order=new Order();
		OrderLine gavl=new OrderLine();
		gavl.setArticlePath("Gavl");
		OrderLine kledning = new OrderLine();
		kledning.setArticlePath("Gavl$Kledning");
		gavl.addOrderLine(kledning);
		order.addOrderLine(gavl);
		kledning = gavl.getOrderLines().iterator().next();
		assertNotNull(kledning);
		assertEquals("Gavl$Kledning", kledning.getArticlePath());
		assertNotNull(kledning.getOrderLineRef());
	}
	
	@Test
	public void convertVeggKledning(){
		ArticleType articleType=new ArticleType();
		articleType.setArticleTypeName("Kledning");
		Ordln ordln=new Ordln();
		ordln.setOrdlnPK(new OrdlnPK(1, 1));
		ordln.setFree4(BigDecimal.ONE);
		Order order=new Order();
		OrderLine vegg=new OrderLine();
		vegg.setArticlePath("Vegg");
		OrderLine kledning = new OrderLine();
		kledning.setArticlePath("Vegg$Kledning");
		vegg.addOrderLine(kledning);
		order.addOrderLine(vegg);
		kledning = vegg.getOrderLines().iterator().next();
		assertNotNull(kledning);
		assertEquals("Vegg$Kledning", kledning.getArticlePath());
		assertNotNull(kledning.getOrderLineRef());
	}
	
	@Test
	public void convertKledning(){
		KledningConverter kledningConverter=new KledningConverter(managerRepository);
		ArticleType articleType=new ArticleType();
		articleType.setArticleTypeName("Kledning");
		Ordln ordln=new Ordln();
		ordln.setOrdlnPK(new OrdlnPK(1, 1));
		ordln.setFree4(BigDecimal.ZERO);
		Order order=new Order();
		OrderLine vegg=new OrderLine();
		vegg.setArticlePath("Vegg");
		order.addOrderLine(vegg);
		Ord ord=null;
		OrderLine kledning =kledningConverter.convert(articleType, ordln, order, ord);
		assertNotNull(kledning);
		assertEquals("Kledning", kledning.getArticlePath());
		assertNull(kledning.getOrderLineRef());
	}
}
