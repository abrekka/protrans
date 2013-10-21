package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.model.TakstolAllV;
import no.ugland.utransprod.test.SlowTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.JiggReportData;

import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(SlowTests.class)
public class TakstolAllVManagerTest {

	@Test
	public void skalHenteUtAlleSomIkkeErProdusert() {
		TakstolAllVManager takstolAllVManager = (TakstolAllVManager) ModelUtil
				.getBean(TakstolAllVManager.MANAGER_NAME);
		List<TakstolAllV> uproduserteTakstoler = takstolAllVManager
				.findAllNotProduced();
		assertFalse(uproduserteTakstoler.isEmpty());
	}

	@Test
	public void findAllProductionByPeriode() {
		Periode periode = new Periode(2009, 45, 45);
		TakstolAllVManager takstolAllVManager = (TakstolAllVManager) ModelUtil
				.getBean(TakstolAllVManager.MANAGER_NAME);
		Map<String, Map<String, Set<JiggReportData>>> list = takstolAllVManager
				.findJiggReportDataByPeriode(periode);
		assertNotNull(list);
		assertEquals(4, list.size());
	}
}
