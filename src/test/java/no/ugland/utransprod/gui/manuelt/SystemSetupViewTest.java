package no.ugland.utransprod.gui.manuelt;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.SystemSetupView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.SystemSetupViewHandler;
import no.ugland.utransprod.test.ManuellTest;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;

@Category(ManuellTest.class)
public class SystemSetupViewTest {
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
		FailOnThreadViolationRepaintManager.install();

		SystemSetupViewHandler viewHandler = new SystemSetupViewHandler();
		final SystemSetupView systemSetupView = new SystemSetupView(viewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(systemSetupView.buildPanel(window));
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
	public void testShow() {
		dialogFixture.requireVisible();

		dialogFixture.comboBox("ComboBoxWindows").requireVisible();
		dialogFixture.comboBox("ComboBoxTableNames").requireVisible();
		dialogFixture.comboBox("ComboBoxProductAreaGroup").requireVisible();
		dialogFixture.button("ButtonCancel").requireVisible();
		dialogFixture.button("ButtonSave").requireVisible();
		dialogFixture.list("ListColumns").requireVisible();
	}

}
