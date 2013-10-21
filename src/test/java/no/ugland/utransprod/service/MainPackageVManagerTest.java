package no.ugland.utransprod.service;

import static org.junit.Assert.assertNotNull;
import no.ugland.utransprod.test.SlowTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 *
 */
@Category(SlowTests.class)
public class MainPackageVManagerTest {

	@Test
	public void testFindAll(){
		MainPackageVManager mainPackageVManager=(MainPackageVManager)ModelUtil.getBean("mainPackageVManager");
		assertNotNull(mainPackageVManager.findAll());
	}
}
