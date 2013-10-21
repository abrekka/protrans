package no.ugland.utransprod.gui.packageproduction.manuelt;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.PackageProductionView;
import no.ugland.utransprod.gui.handlers.PackageProductionViewHandler;
import no.ugland.utransprod.test.ManuellTest;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 * 
 */
@Category(ManuellTest.class)
public class PackageProductionViewTest {
	static {
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private DialogFixture dialogFixture;

	@Before
	public void setUp() throws Exception {

		PackageProductionViewHandler packageProductionViewHandler = new PackageProductionViewHandler();
		final PackageProductionView packageProductionView = new PackageProductionView(
				packageProductionViewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();

				dialog.add(packageProductionView.buildPanel());
				dialog.pack();
				return dialog;
			}
		});
		dialogFixture = new DialogFixture(dialog);
		dialogFixture.show();

	}

	@After
	public void tearDown() throws Exception {
		dialogFixture.cleanUp();
	}

	@Test
	public void testOpenWindow() throws Exception {

		dialogFixture.requireVisible();
	}

}
