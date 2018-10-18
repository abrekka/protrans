package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertNull;

import java.util.Date;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.OrderArticleViewHandler;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.test.FastTests;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.jgoodies.binding.PresentationModel;

@Category(FastTests.class)
public class OrderArticleViewHandlerTest {

	@Test
	public void skal_sette_komplett_til_nei_dersom_det_legges_til_artikkel() {
		PresentationModel presentationModel = null;
		Login login = null;
		ManagerRepository managerRepository = null;
		OrderArticleViewHandler<Order, OrderModel> orderArticleViewHandler = new OrderArticleViewHandler<Order, OrderModel>(
				presentationModel, false, login, managerRepository, true);
		Order order = new Order();
		order.setOrderComplete(new Date());
		ArticleType newArticleType = new ArticleType();
		orderArticleViewHandler.getNewOrderLineFromArticle(order, newArticleType);
		assertNull(order.getOrderComplete());
	}
}
