package no.ugland.utransprod.service;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class OrderLineManagerTest {
    private OrderLineManager orderLineManager;

    
    @Before
    public void setUp() throws Exception {
        orderLineManager = (OrderLineManager) ModelUtil
                .getBean("orderLineManager");
    }

    @Test
    public void testFindAllConstructionTypeNotSent(){
        ProductAreaManager productAreaManager=(ProductAreaManager)ModelUtil.getBean("productAreaManager");
        ProductArea productArea=productAreaManager.findByName("Garasje villa");
        List<OrderLine> orderLines = orderLineManager.findAllConstructionTypeNotSent(productArea);
        assertNotNull(orderLines);
    }

}
