package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.ApplicationUserViewHandler;
import no.ugland.utransprod.gui.model.ApplicationUserModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.ApplicationUserManager;
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
public class ApplicationUserViewHandlerTest {
	private ApplicationUserViewHandler viewHandler;

	private ApplicationUserManager applicationUserManager;
	@Mock
	private Login login;

	@After
	public void tearDown() throws Exception {
		ApplicationUser bruker = new ApplicationUser();
		bruker.setUserName("test");
		List<ApplicationUser> brukere = applicationUserManager
				.findByObject(bruker);
		for (ApplicationUser user : brukere) {
			applicationUserManager.removeObject(user);
		}
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean("applicationUserManager");

		final UserType userType = new UserType();
		userType.setIsAdmin(1);
		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWindowAccess(new WindowAccess(null, "Attributter",
				null));
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);

		viewHandler = new ApplicationUserViewHandler(login,
				applicationUserManager);
	}

	@Test
	public void testGetOrderListAtStart() {
		assertEquals(0, viewHandler.getObjectSelectionListSize());
	}

	@Test
	public void testGetAddRemoveString() {
		assertNotNull(viewHandler.getAddRemoveString());
		assertEquals("bruker", viewHandler.getAddRemoveString());
	}

	@Test
	public void testGetNewObject() {
		assertNotNull(viewHandler.getNewObject());
		assertEquals(ApplicationUser.class, viewHandler.getNewObject()
				.getClass());
	}

	@Test
	public void testGetTableModel() {
		assertNotNull(viewHandler.getTableModel(null));

	}

	@Test
	public void testSaveObject() throws Exception {
		ApplicationUser user = new ApplicationUser();
		user.setUserName("test");
		user.setFirstName("test");
		user.setLastName("test");
		user.setPassword("test");
		viewHandler.saveObject(new ApplicationUserModel(user), null);
		assertEquals(1, viewHandler.getObjectSelectionListSize());
	}

}
