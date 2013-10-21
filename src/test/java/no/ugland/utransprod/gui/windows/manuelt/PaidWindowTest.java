package no.ugland.utransprod.gui.windows.manuelt;

import static org.mockito.Mockito.when;

import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.PaidView;
import no.ugland.utransprod.gui.PaidViewFactory;
import no.ugland.utransprod.gui.PaidWindow;
import no.ugland.utransprod.gui.SystemReadyListener;
import no.ugland.utransprod.gui.handlers.PaidViewHandler;
import no.ugland.utransprod.gui.handlers.TableEnum;
import no.ugland.utransprod.gui.model.PaidApplyList;
import no.ugland.utransprod.service.CustTrManager;
import no.ugland.utransprod.service.PaidVManager;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.FrameFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 * 
 */
public class PaidWindowTest extends WindowTest {
	static {
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private FrameFixture frameFixture;

	@Before
	protected void setUp() throws Exception {
		super.setUp();
		CustTrManager custTrManager = (CustTrManager) ModelUtil
				.getBean(CustTrManager.MANAGER_NAME);
		when(managerRepository.getCustTrManager()).thenReturn(custTrManager);
		PaidVManager paidVManager = (PaidVManager) ModelUtil
				.getBean(PaidVManager.MANAGER_NAME);
		PaidApplyList paidApplyList = new PaidApplyList(login, paidVManager);
		PaidViewHandler paidViewHandler = new PaidViewHandler(paidApplyList,
				login, managerRepository, deviationViewHandlerFactory);
		final PaidView paidView = new PaidView(paidViewHandler, false);

		PaidViewFactory paidViewFactory = new PaidViewFactory() {

			public PaidView create(boolean printButton) {
				return paidView;
			}
		};

		final PaidWindow paidWindow = new PaidWindow(paidViewFactory);
		paidWindow.setLogin(login);

		JFrame frame = GuiActionRunner.execute(new GuiQuery<JFrame>() {
			protected JFrame executeInEDT() {
				return (JFrame) paidWindow.buildMainWindow(
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
	public void testShowPaidWindow() throws Exception {
		frameFixture.requireVisible();

		frameFixture.table(TableEnum.TABLEPAID.getTableName());
		assertEquals("Forhåndsbetaling", frameFixture.target.getTitle());
	}

}
