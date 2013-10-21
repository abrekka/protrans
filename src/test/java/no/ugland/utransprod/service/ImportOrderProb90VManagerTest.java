package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;

import no.ugland.utransprod.model.TakstolProbability90V;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class ImportOrderProb90VManagerTest {

	@Test
	public void findAllTakstoler(){
		ImportOrderProb90VManager importOrderProb90VManager=(ImportOrderProb90VManager)ModelUtil.getBean(ImportOrderProb90VManager.MANAGER_NAME);
		List<TakstolProbability90V> takstoler = importOrderProb90VManager.findAllTakstoler();
		assertNotNull(takstoler);
		assertEquals(1, takstoler.size());
	}
	
}
