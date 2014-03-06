package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandler;
import no.ugland.utransprod.gui.model.ConstructionTypeModel;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.ProductAreaManager;
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
public class ConstructionTypeViewHandlerTest {
    private ConstructionTypeViewHandler viewHandler;

    private ConstructionType constructionType;

    private ConstructionTypeManager constructionTypeManager;
    @Mock
    private Login login;
    @Mock
    private ManagerRepository managerRepository;

    @After
    public void tearDown() throws Exception {
	if (constructionType != null && constructionType.getConstructionTypeId() != null) {
	    constructionTypeManager.removeObject(constructionType);
	}
    }

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
	constructionTypeManager = (ConstructionTypeManager) ModelUtil.getBean("constructionTypeManager");
	when(managerRepository.getConstructionTypeManager()).thenReturn(constructionTypeManager);

	viewHandler = new ConstructionTypeViewHandler(login, managerRepository, false, false);

    }

    @Test
    public void testGetOrderListAtStart() {
	assertEquals(0, viewHandler.getObjectSelectionListSize());
    }

    @Test
    public void testGetAddRemoveString() {
	assertNotNull(viewHandler.getAddRemoveString());
	assertEquals("konstruksjonstype", viewHandler.getAddRemoveString());
    }

    @Test
    public void testGetNewObject() {
	assertNotNull(viewHandler.getNewObject());
	assertEquals(ConstructionType.class, viewHandler.getNewObject().getClass());
    }

    @Test
    public void testGetTableModel() {
	assertNotNull(viewHandler.getTableModel(null));

    }

    @Test
    public void testSaveObject() throws Exception {
	ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil.getBean("productAreaManager");
	ProductArea productArea = productAreaManager.findByName("Villa Element");
	constructionType = new ConstructionType();
	constructionType.setName("test");
	constructionType.setProductArea(productArea);
	viewHandler.saveObject(new ConstructionTypeModel(constructionType), null);
	assertEquals(1, viewHandler.getObjectSelectionListSize());
    }

}
