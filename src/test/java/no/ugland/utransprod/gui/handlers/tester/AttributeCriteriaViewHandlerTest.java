package no.ugland.utransprod.gui.handlers.tester;

import static junit.framework.Assert.assertNotNull;
import no.ugland.utransprod.gui.handlers.AttributeCriteriaViewHandler;
import no.ugland.utransprod.test.FastTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

/**
 * @author atle.brekka
 * 
 */
@Category(FastTests.class)
public class AttributeCriteriaViewHandlerTest {
	/**
	 *
	 */
	private AttributeCriteriaViewHandler viewHandler;

	@Before
	public void setUp() throws Exception {
		viewHandler = new AttributeCriteriaViewHandler();

	}

	@Test
	public void testGetCheckBoxYesNo() {

		assertNotNull(viewHandler.getCheckBoxYesNo(null));
	}

}
