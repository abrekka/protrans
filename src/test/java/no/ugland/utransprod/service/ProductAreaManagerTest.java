package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class ProductAreaManagerTest {
    private ProductAreaManager productAreaManager;

    @Before
    public void setUp() throws Exception {
	productAreaManager = (ProductAreaManager) ModelUtil.getBean("productAreaManager");
    }

    @Test
    public void testGetGarasjeVillaFor200() {
	ProductArea productArea = productAreaManager.getProductAreaForProductAreaNr(200, true);
	assertNotNull(productArea);
	assertEquals("Villa Element", productArea.getProductArea());
    }

    @Test
    public void testGetTakstolFor300() {
	ProductArea productArea = productAreaManager.getProductAreaForProductAreaNr(300, true);
	assertNotNull(productArea);
	assertEquals("Service", productArea.getProductArea());
    }

    @Test
    public void testGetGarasjeRekkeFor100() {
	ProductArea productArea = productAreaManager.getProductAreaForProductAreaNr(100, true);
	assertNotNull(productArea);
	assertEquals("Rekke", productArea.getProductArea());
    }

}
