package no.ugland.utransprod.gui.windows.manuelt;

import javax.swing.JFrame;

import no.ugland.utransprod.gui.SystemReadyListener;
import no.ugland.utransprod.gui.VeggProductionWindow;
import no.ugland.utransprod.gui.handlers.TableEnum;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author atle.brekka
 * 
 */
public class VeggProductionWindowTest extends WindowTest {
	/**
     *
     */
	private FrameFixture frameFixture;

	@Before
	protected void setUp() throws Exception {
		super.setUp();

		final VeggProductionWindow veggProductionWindow = new VeggProductionWindow(
				login, managerRepository, deviationViewHandlerFactory, null,null);
		veggProductionWindow.setLogin(login);

		JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
			protected JFrame executeInEDT() {
				return (JFrame) veggProductionWindow.buildMainWindow(
						new SystemReadyListener() {

							public void systemReady() {

							}

						}, managerRepository);

			}
		});

		frameFixture = new FrameFixture(frame);
		frameFixture.show();

	}

	@After
	protected void tearDown() throws Exception {
		frameFixture.cleanUp();
		super.tearDown();
	}

	@Test
	public void testShow() throws Exception {
		frameFixture.requireVisible();

		frameFixture.table(TableEnum.TABLEPRODUCTIONVEGG.getTableName());
		assertEquals("Produksjon av vegg", frameFixture.target.getTitle());

	}
}
