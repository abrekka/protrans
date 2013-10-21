package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.handlers.ArticleTypeViewHandler;
import no.ugland.utransprod.gui.model.ArticleTypeModel;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.test.FastTests;
import no.ugland.utransprod.util.ApplicationParamUtil;

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
public class ArticleTypeViewHandlerTest {
	private ArticleTypeViewHandler viewHandler;
	@Mock
	private ArticleTypeManager articleTypeManager;
	@Mock
	private Login login;
	@Mock
	private ManagerRepository managerRepository;
	@Mock
	private ApplicationParamManager applicationParamManager;

	@After
	public void tearDown() throws Exception {
		ArticleType articleType = articleTypeManager.findByName("test");
		if (articleType != null) {
			articleTypeManager.removeArticleType(articleType);
		}
	}

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		ApplicationParamUtil.setApplicationParamManger(applicationParamManager);
		when(managerRepository.getArticleTypeManager()).thenReturn(
				articleTypeManager);

		viewHandler = new ArticleTypeViewHandler(login, managerRepository, null);
	}

	@Test
	public void testGetOrderListAtStart() {
		// skal være 0 fordi liste er ikke initiert ennå
		assertEquals(0, viewHandler.getObjectSelectionListSize());
	}

	@Test
	public void testGetAddRemoveString() {
		assertNotNull(viewHandler.getAddRemoveString());
		assertEquals("artikkeltype", viewHandler.getAddRemoveString());
	}

	@Test
	public void testGetNewObject() {
		assertNotNull(viewHandler.getNewObject());
		assertEquals(ArticleType.class, viewHandler.getNewObject().getClass());
	}

	@Test
	public void testGetTableModel() {
		assertNotNull(viewHandler.getTableModel(null));

	}

	@Test
	public void testSaveObject() throws Exception {
		ArticleType articleType = new ArticleType();
		articleType.setArticleTypeName("test");
		viewHandler.saveObject(new ArticleTypeModel(articleType), null);
		assertEquals(1, viewHandler.getObjectSelectionListSize());
	}

}
