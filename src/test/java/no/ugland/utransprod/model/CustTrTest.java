package no.ugland.utransprod.model;

import static junit.framework.Assert.assertNull;

import no.ugland.utransprod.test.FastTests;

import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class CustTrTest {

	@Test
	public void skal_haandtere_dato_med_verdi_0() {
		CustTr custTr = new CustTr();
		custTr.setDueDt(0);
		assertNull(custTr.getDueDate());
	}
}
