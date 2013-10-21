package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.CustTr;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
@Category(FastTests.class)
public class CustTrManagerTest {
    private CustTrManager custTrManager;

    @Before
    public void setUp() throws Exception {
        custTrManager = (CustTrManager) ModelUtil.getBean("custTrManager");
    }

    @Test
    public void testFindByOrderNr() {
        List<CustTr> custTrs = custTrManager.findByOrderNr("19");
        assertNotNull(custTrs);
        assertEquals(2, custTrs.size());
        CustTr custTr = custTrs.get(0);

        assertEquals("2008.12.10", Util.SHORT_DATE_FORMAT.format(custTr
                .getDueDate()));
        assertEquals(BigDecimal.valueOf(1000.5).setScale(2), custTr
                .getInvoiceAmount().setScale(2));
        assertEquals(BigDecimal.valueOf(50).setScale(2), custTr.getRestAmount()
                .setScale(2));

    }
    

}
