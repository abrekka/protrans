package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.CostUnitViewHandler;
import no.ugland.utransprod.gui.model.CostUnitModel;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.service.CostUnitManager;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ModelUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class CostUnitViewHandlerTest {
	private CostUnitViewHandler viewHandler;

	private CostUnit costUnit;
	private CostUnitManager costUnitManager;

	@Mock
	private Login login;

	@After
	public void tearDown() throws Exception {
		if (costUnit != null) {
			costUnitManager.removeObject(costUnit);
		}
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		costUnitManager = (CostUnitManager) ModelUtil
				.getBean("costUnitManager");
		viewHandler = new CostUnitViewHandler(login, costUnitManager);

	}

	@Test
	public void testGetOrderListAtStart() {
		assertEquals(0, viewHandler.getObjectSelectionListSize());
	}

	@Test
	public void testGetAddRemoveString() {
		assertNotNull(viewHandler.getAddRemoveString());
		assertEquals("kostnadsenhet", viewHandler.getAddRemoveString());
	}

	@Test
	public void testGetNewObject() {
		assertNotNull(viewHandler.getNewObject());
		assertEquals(CostUnit.class, viewHandler.getNewObject().getClass());
	}

	@Test
	public void testGetTableModel() {
		assertNotNull(viewHandler.getTableModel(null));

	}

	@Test
	public void testSaveObject() throws Exception {
		costUnit = new CostUnit();
		costUnit.setCostUnitName("test");

		viewHandler.saveObject(new CostUnitModel(costUnit), null);
		assertEquals(1, viewHandler.getObjectSelectionListSize());
	}

}
