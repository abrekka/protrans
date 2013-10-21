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
public class NokkelProduksjonVManagerTest {

	@Test
	public void testFindByWeek(){
		NokkelProduksjonVManager nokkelProduksjonVManager=(NokkelProduksjonVManager)ModelUtil.getBean("nokkelProduksjonVManager");
		assertNotNull(nokkelProduksjonVManager.findByWeek(2008, 10));
	}
}
