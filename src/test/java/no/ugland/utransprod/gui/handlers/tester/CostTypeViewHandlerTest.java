package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.CostTypeViewHandler;
import no.ugland.utransprod.gui.model.CostTypeModel;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.CostTypeManager;
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
public class CostTypeViewHandlerTest {
	private CostTypeViewHandler viewHandler;

	private CostType costType;

	private CostTypeManager costTypeManager;

	@Mock
	private Login login;

	@After
	public void tearDown() throws Exception {
		if (costType != null) {
			costTypeManager.removeObject(costType);
		}
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		costTypeManager = (CostTypeManager) ModelUtil
				.getBean("costTypeManager");
		final UserType userType = new UserType();
		userType.setIsAdmin(1);
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWindowAccess(new WindowAccess(null, "Attributter",
				null));
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);

		viewHandler = new CostTypeViewHandler(login, costTypeManager);

	}

	@Test
	public void testGetOrderListAtStart() {
		assertEquals(0, viewHandler.getObjectSelectionListSize());
	}

	@Test
	public void testGetAddRemoveString() {
		assertNotNull(viewHandler.getAddRemoveString());
		assertEquals("kostnadstype", viewHandler.getAddRemoveString());
	}

	@Test
	public void testGetNewObject() {
		assertNotNull(viewHandler.getNewObject());
		assertEquals(CostType.class, viewHandler.getNewObject().getClass());
	}

	@Test
	public void testGetTableModel() {
		assertNotNull(viewHandler.getTableModel(null));

	}

	@Test
	public void testSaveObject() throws Exception {
		costType = new CostType();
		costType.setCostTypeName("test");
		viewHandler.saveObject(new CostTypeModel(costType), null);
		assertEquals(1, viewHandler.getObjectSelectionListSize());
	}

}
