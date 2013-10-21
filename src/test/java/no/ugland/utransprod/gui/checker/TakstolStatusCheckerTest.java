package no.ugland.utransprod.gui.checker;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.TakstolPackageVManager;
import no.ugland.utransprod.service.TakstolProductionVManager;
import no.ugland.utransprod.test.FastTests;

import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class TakstolStatusCheckerTest {
    public TakstolStatusCheckerTest(){
        super();
    }
    @Test
    public void testCreate() {
        new TakstolStatusChecker(null, null, null);
    }
    @Test
    public void testGetArticleName(){
        ArticleType articleType = new ArticleType();
        articleType.setArticleTypeName("Takstoler");
        TakstolStatusChecker takstolStatusChecker = new TakstolStatusChecker(articleType,
                null, null);
        assertEquals("Takstoler", takstolStatusChecker.getArticleName());
        
    }

    @Test
    public void testGetArticleStatus() {
        ArticleType articleType = new ArticleType();
        articleType.setArticleTypeName("Takstoler");
        final TakstolPackageVManager takstolPackageVManager = mock(TakstolPackageVManager.class);
        final TakstolProductionVManager takstolProductionVManager = mock(TakstolProductionVManager.class);
        TakstolStatusChecker takstolStatusChecker = new TakstolStatusChecker(articleType,takstolPackageVManager, takstolProductionVManager);

        final List<OrderLine> orderLineList = new ArrayList<OrderLine>();
        OrderLine orderLine = new OrderLine();
        Order order = new Order();
        order.setOrderNr("1");
        order.addOrderLine(orderLine);
        orderLine.setOrder(order);
        orderLine.setArticlePath("Takstoler");
        orderLine.setOrderLineId(1);
        orderLineList.add(orderLine);

        String status = takstolStatusChecker.getArticleStatus(order);
        assertEquals("e0", status);
    }
}
