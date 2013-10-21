package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class AssemblyManagerTest {
	private SupplierManager supplierManager;

	@Before
	public void setUp() throws Exception {
		supplierManager = (SupplierManager) ModelUtil
				.getBean("supplierManager");
	}

	@Test
	public void testFindAll() {
		List<Supplier> teams = supplierManager.findAll();
		assertEquals(true, teams.size() != 0);

	}
}
