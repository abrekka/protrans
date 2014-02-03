package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.model.TakstoltegnerV;
import no.ugland.utransprod.model.TakstoltegnerVSum;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class TakstolregnerapportVManagerTest {

    private TakstoltegnerVManager takstoltegnerVManager;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

    @Before
    public void init() {
	takstoltegnerVManager = (TakstoltegnerVManager) ModelUtil.getBean(TakstoltegnerVManager.MANAGER_NAME);
    }

    @Test
    @Ignore
    public void hentTakstoltegnerdataForUke4_5() {
	Periode periode = new Periode(2010, 4, 5);
	Map<TakstoltegnerVSum, Collection<TakstoltegnerV>> reportMap = takstoltegnerVManager.findByPeriode(periode);

	assertNotNull(reportMap);
	assertEquals(3, reportMap.size());

	Set<TakstoltegnerVSum> tegnere = reportMap.keySet();

	boolean found = false;
	for (TakstoltegnerVSum tegner : tegnere) {
	    if (tegner.getTrossDrawer().equalsIgnoreCase("Lukasz Szamocki")) {
		found = true;
		assertEquals("Takstol", tegner.getProductAreaGroupName());
		assertEquals(BigDecimal.valueOf(30013), tegner.getSumCostAmount());
		assertEquals(Integer.valueOf(2), tegner.getNumberOf());
		Collection<TakstoltegnerV> tegnerList = reportMap.get(tegner);
		assertEquals(2, tegnerList.size());
		found = false;
		for (TakstoltegnerV detalj : tegnerList) {
		    if (detalj.orderNr().equalsIgnoreCase("56434")) {
			found = true;
			assertEquals("Magne Tomren", detalj.getCustomerName());
			assertEquals(Integer.valueOf(693229), detalj.getCustomerNr());
			assertEquals("3681", detalj.getPostalCode());
			assertEquals(BigDecimal.valueOf(8656), detalj.getCostAmount());

			assertEquals("29.01.2010", sdf.format(detalj.getTrossReady()));
			assertEquals("Lukasz Szamocki", detalj.getTrossDrawer());
			assertEquals(BigDecimal.valueOf(2).setScale(0), detalj.getTakProsjektering());
			assertEquals(BigDecimal.valueOf(1843), detalj.getMaxTrossHeight());
			assertEquals(Integer.valueOf(100), detalj.getProbability());
			assertEquals("Takstol", detalj.getProductAreaGroupName());
			assertEquals("29.01.2010", sdf.format(detalj.getPacklistReady()));
			assertEquals("Atle Olsen", detalj.getSalesman());
		    }
		}
	    }
	}
	assertTrue(found);

    }
}
