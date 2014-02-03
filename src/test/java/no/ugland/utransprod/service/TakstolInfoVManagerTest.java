package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import no.ugland.utransprod.gui.handlers.ReportConstraintViewHandler.TransportConstraintEnum;
import no.ugland.utransprod.model.TakstolInfoV;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class TakstolInfoVManagerTest {
    private static final String ORDER_NR = "70722";

    @Test
    public void skalHenteAlleOrdreMedInfoOgBergnetTid() {
	TakstolInfoVManager takstolInfoVManager = (TakstolInfoVManager) ModelUtil.getBean(TakstolInfoVManager.MANAGER_NAME);
	ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean(ProductAreaManager.MANAGER_NAME);
	List<Object[]> arbeidsinnsats = takstolInfoVManager.getBeregnetTidForOrdre("201211", "201230", TransportConstraintEnum.TRANSPORT_PLANNED,
		"Takstol");
	assertNotNull(arbeidsinnsats);
    }

    @Test
    public void skalHenteUtSummertArbeidsinnsats() {
	TakstolInfoVManager takstolInfoVManager = (TakstolInfoVManager) ModelUtil.getBean(TakstolInfoVManager.MANAGER_NAME);
	ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean(ProductAreaManager.MANAGER_NAME);
	List<Object[]> arbeidsinnsats = takstolInfoVManager.summerArbeidsinnsats("201211", "201230", TransportConstraintEnum.TRANSPORT_PLANNED,
		"Takstol");
	assertNotNull(arbeidsinnsats);
    }

    @Test
    @Ignore
    public void testGetTakstolInfo() {
	TakstolInfoVManager takstolInfoVManager = (TakstolInfoVManager) ModelUtil.getBean(TakstolInfoVManager.MANAGER_NAME);
	List<TakstolInfoV> list = takstolInfoVManager.findByOrderNr(ORDER_NR);
	assertNotNull(list);
	assertEquals(1, list.size());
    }
}
