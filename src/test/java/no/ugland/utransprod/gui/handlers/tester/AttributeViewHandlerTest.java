package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.AttributeViewHandler;
import no.ugland.utransprod.gui.model.AttributeModel;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.service.AttributeManager;
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
 * 
 */
@Category(FastTests.class)
public class AttributeViewHandlerTest {
	private AttributeViewHandler viewHandler;

	private Attribute attribute;
	private AttributeManager attributeManager;
	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;

	@After
	public void tearDown() throws Exception {
		if (attribute != null && attribute.getAttributeId() != null) {
			attributeManager.removeObject(attribute);
		}
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		attributeManager = (AttributeManager) ModelUtil
				.getBean(AttributeManager.MANAGER_NAME);
		when(managerRepository.getAttributeManager()).thenReturn(
				attributeManager);

		viewHandler = new AttributeViewHandler(login, managerRepository);

	}

	@Test
	public void testGetOrderListAtStart() {
		assertEquals(0, viewHandler.getObjectSelectionListSize());
	}

	@Test
	public void testGetAddRemoveString() {
		assertNotNull(viewHandler.getAddRemoveString());
		assertEquals("attributt", viewHandler.getAddRemoveString());
	}

	@Test
	public void testGetNewObject() {
		assertNotNull(viewHandler.getNewObject());
		assertEquals(Attribute.class, viewHandler.getNewObject().getClass());
	}

	@Test
	public void testGetTableModel() {
		assertNotNull(viewHandler.getTableModel(null));

	}

	@Test
	public void testSaveObject() throws Exception {
		attribute = new Attribute();
		attribute.setName("testatt");
		viewHandler.saveObject(new AttributeModel(attribute), null);
		assertEquals(1, viewHandler.getObjectSelectionListSize());
	}

}
