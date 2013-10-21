package no.ugland.utransprod.service;

import static org.junit.Assert.assertNotNull;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 *
 */
@Category(FastTests.class)
public class GavlProductionVManagerTest {

	@Test
	public void testFindAll(){
		GavlProductionVManager gavlProductionVManager=(GavlProductionVManager)ModelUtil.getBean("gavlProductionVManager");
		assertNotNull(gavlProductionVManager.findAllApplyable());
	}
}
