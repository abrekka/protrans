package no.ugland.utransprod.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class TransportManagerTest {
    private TransportManager transportManager;

    private Transport transport;

    @Before
    public void setUp() throws Exception {
	transportManager = (TransportManager) ModelUtil.getBean("transportManager");
    }

    @After
    public void tearDown() throws Exception {
	if (transport != null && transport.getTransportId() != null) {
	    transportManager.removeTransport(transport);
	}
    }

    @Test
    public void testGetAll() {
	List<Transport> transportList = transportManager.findAll();
	assertEquals(true, transportList.size() != 0);
    }

    @Test
    public void testInsert() {
	transport = new Transport();
	transport.setTransportWeek(1);
	transport.setTransportYear(2006);
	transport.setTransportName("TestTur");
	transportManager.saveTransport(transport);
    }

    @Test
    public void testGetAllTransportForGarasje() {
	ProductAreaGroupManager productAreaGroupManager = (ProductAreaGroupManager) ModelUtil.getBean("productAreaGroupManager");
	ProductAreaGroup productAreaGroup = productAreaGroupManager.findByName("Garasje");
	List<Transport> list = transportManager.findByYearAndWeekAndProductAreaGroup(2014, 3, productAreaGroup);
	assertNotNull(list);
	assertEquals(true, list.size() > 0);
    }
}
