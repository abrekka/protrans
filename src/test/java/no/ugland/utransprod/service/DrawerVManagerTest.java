package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.DrawerV;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.DrawerGroup;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class DrawerVManagerTest {
    private DrawerVManager drawerVManager;
    private ProductAreaManager productAreaManager;

    @Before
    public void setUp() throws Exception {
	drawerVManager = (DrawerVManager) ModelUtil.getBean("drawerVManager");
	productAreaManager = (ProductAreaManager) ModelUtil.getBean("productAreaManager");
    }

    @Test
    public void testDrawerGroupReportGarageVilla() throws Exception {
	Periode periode = new Periode(2009, 42, 42);
	ProductArea productArea = productAreaManager.findByName("Villa Element");
	List<DrawerGroup> drawerGroups = drawerVManager.groupByProductAreaPeriode(productArea, periode);
	assertNotNull(drawerGroups);
	assertEquals(true, drawerGroups.size() == 1);

	DrawerGroup drawerGroup = drawerGroups.get(0);

	assertEquals("test1", drawerGroup.getCategoryName());
	assertEquals(Integer.valueOf(1), drawerGroup.getCount());
	assertEquals("odd", drawerGroup.getDrawerName());
	assertEquals(BigDecimal.valueOf(200000), drawerGroup.getSumOwnProduction());
	assertEquals("Villa Element", drawerGroup.getProductArea().getProductArea());

    }

    @Test
    public void testGetDrawerBasisGarageVilla() {
	Periode periode = new Periode(2009, 42, 42);
	List<DrawerV> drawers = drawerVManager
		.findByProductAreaPeriode(productAreaManager.getProductAreaNrListFromAreaName("Villa Element"), periode);
	assertNotNull(drawers);
	assertEquals(1, drawers.size());

    }
}
