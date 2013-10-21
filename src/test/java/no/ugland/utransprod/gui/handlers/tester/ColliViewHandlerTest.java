package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.ColliViewHandler;
import no.ugland.utransprod.gui.model.ColliModel;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ManagerRepository;
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
 */
@Category(FastTests.class)
public class ColliViewHandlerTest {
	private ColliViewHandler viewHandler;

	private Colli colli;

	private ColliManager colliManager;
	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;

	@After
	public void tearDown() throws Exception {
		if (colli != null) {
			colliManager.removeObject(colli);
		}
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		colliManager = (ColliManager) ModelUtil.getBean("colliManager");
		when(managerRepository.getColliManager()).thenReturn(colliManager);
		UserType userType = new UserType();
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);

		viewHandler = new ColliViewHandler("Kolli", new Colli(), null, login,
				managerRepository, null);

	}

	@Test
	public void testGetOrderListAtStart() {
		assertEquals(1, viewHandler.getObjectSelectionListSize());
	}

	@Test
	public void testSaveObject() throws Exception {
		colli = new Colli();
		colli.setColliName("test");
		viewHandler.saveObject(new ColliModel(colli), null);
		assertEquals(2, viewHandler.getObjectSelectionListSize());
	}

}
