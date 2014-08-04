package no.ugland.utransprod.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(FastTests.class)
public class OrdlnManagerTest {
    private OrdlnManager ordlnManager;

    @Before
    public void settOpp() throws Exception {
	ordlnManager = (OrdlnManager) ModelUtil.getBean("ordlnManager");
    }

    @Test
    public void skalHenteTaksteinInfo() {
	List<Ordln> taksteinInfo = ordlnManager.findTaksteinInfo("84892");
	Assertions.assertThat(taksteinInfo).hasSize(4);
    }

    @Test
    public void skalHenteOrdlnForFakturagrunnlag() {
	List<Ordln> fakturagrunnlag = ordlnManager.findForFakturagrunnlag("88833");
	Assertions.assertThat(fakturagrunnlag).isNotEmpty();
    }

    @Test
    public void skal_hente_ut_kostnadslinjer_for_order_i_visma() {
	List<Ordln> orderLines = ordlnManager.findCostLines("11");
	assertNotNull(orderLines);
	assertEquals(3, orderLines.size());

	for (Ordln ordln : orderLines) {
	    if (ordln.getProd().getProdNo().equalsIgnoreCase("Montering villa")) {
		assertEquals(BigDecimal.valueOf(20000), ordln.getAm().setScale(0));
	    }

	    if (ordln.getProd().getProdNo().equalsIgnoreCase("KRANBIL")) {
		assertEquals(BigDecimal.valueOf(2000), ordln.getAm().setScale(0));
	    }

	    if (ordln.getProd().getProdNo().equalsIgnoreCase("AVFALLSFJERNING MONTERING")) {
		assertEquals(BigDecimal.valueOf(500), ordln.getAm().setScale(0));
	    }
	}
    }

    @Test
    @Ignore
    public void testGetOrderLines() {
	List<Ordln> orderLines = ordlnManager.findByOrderNr("11");
	assertNotNull(orderLines);
	assertEquals(12, orderLines.size());
	int counter = 0;
	for (Ordln ln : orderLines) {
	    counter++;
	    if (counter == 1) {
		assertEquals(Integer.valueOf(1), ln.getLnno());
		assertEquals("MONTERING VILLA", ln.getProd().getProdNo());
		assertEquals("test1", ln.getDescription());
		assertEquals(BigDecimal.valueOf(1).setScale(2), ln.getNoOrg().setScale(2));
		assertEquals(BigDecimal.valueOf(0).setScale(2), ln.getDiscount().setScale(2));
		assertEquals(BigDecimal.valueOf(100).setScale(2), ln.getFree1().setScale(2));
		assertEquals(BigDecimal.valueOf(100).setScale(2), ln.getCostPrice().setScale(2));
		assertEquals(BigDecimal.valueOf(200).setScale(2), ln.getPrice().setScale(2));
	    } else if (counter == 2) {
		assertEquals(Integer.valueOf(2), ln.getLnno());
		assertEquals("TAKSTOLER", ln.getProd().getProdNo());
		assertEquals("test2", ln.getDescription());
		assertEquals(BigDecimal.valueOf(2).setScale(2), ln.getNoOrg().setScale(2));
		assertEquals(BigDecimal.valueOf(10).setScale(2), ln.getDiscount().setScale(2));
		assertEquals(BigDecimal.valueOf(0).setScale(2), ln.getFree1().setScale(2));
		assertEquals(BigDecimal.valueOf(200).setScale(2), ln.getCostPrice().setScale(2));
		assertEquals(BigDecimal.valueOf(300).setScale(2), ln.getPrice().setScale(2));
	    }
	}
	assertEquals(12, counter);
    }

}
