package no.ugland.utransprod.util;

import org.fest.assertions.Assertions;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;

public class CollicreatorTest {

	@Test
	public void skalOppretteKollier() {
		ManagerRepository managerRepository = Mockito.mock(ManagerRepository.class);
		OrderLineManager orderLineManager=Mockito.mock(OrderLineManager.class);
		Mockito.when(managerRepository.getOrderLineManager()).thenReturn(orderLineManager);
		ColliManager colliManager=Mockito.mock(ColliManager.class);
		Mockito.when(managerRepository.getColliManager()).thenReturn(colliManager);
		OrderManager orderManager=Mockito.mock(OrderManager.class);
		Mockito.when(managerRepository.getOrderManager()).thenReturn(orderManager);
		Multimap<String, String> colliSetup = ArrayListMultimap.create();
		colliSetup.put("Vegg", "Vegg");
		Collicreator collicreator = new Collicreator(colliSetup, managerRepository);
		Order ordre = new Order();
		OrderLine ordrelinje=new OrderLine();
		ordrelinje.setArticlePath("Vegg");
		ArticleType articleType=new ArticleType();
		articleType.setArticleTypeName("Vegg");
		ordrelinje.setArticleType(articleType);
		ordre.addOrderLine(ordrelinje);
		collicreator.opprettKollier(ordre,"test");

		Assertions.assertThat(ordre.getColliList()).isNotEmpty();
	}
}
