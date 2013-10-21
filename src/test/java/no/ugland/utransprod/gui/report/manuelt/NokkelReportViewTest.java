package no.ugland.utransprod.gui.report.manuelt;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.KeyReportView;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.KeyReportViewHandler;
import no.ugland.utransprod.test.ManuellTest;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JComboBoxFixture;
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
public class NokkelReportViewTest {
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

		KeyReportViewHandler keyReportViewHandler = new KeyReportViewHandler();

		final KeyReportView keyReportView = new KeyReportView(
				keyReportViewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(keyReportView.buildPanel(window));
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
	public void testShowReportSalgDriftTransport() {
		JComboBoxFixture comboBox = dialogFixture
				.comboBox("ComboBoxReportType");
		comboBox.selectItem(0);
		String reportName = comboBox.valueAt(0);
		dialogFixture.comboBox("ComboBoxProductArea").selectItem(
				"Garasje villa");
		dialogFixture.comboBox("ComboBoxWeek").selectItem(3);
		dialogFixture.button("ButtonShowReport").click();

		WindowFinder.findDialog(reportName).withTimeout(160000).using(
				dialogFixture.robot);
	}

	@Test
	public void testShowReportMonteringOkonomi() {
		dialogFixture.comboBox("ComboBoxProductArea").selectItem(
				"Garasje villa");
		JComboBoxFixture comboBox = dialogFixture
				.comboBox("ComboBoxReportType");
		comboBox.selectItem(1);
		String reportName = comboBox.valueAt(1);
		
		dialogFixture.comboBox("ComboBoxWeek").selectItem(3);
		dialogFixture.button("ButtonShowReport").click();

		WindowFinder.findDialog(reportName).withTimeout(180000).using(
				dialogFixture.robot);
	}

	@Test
	public void testShowReportProduksjon() {
		JComboBoxFixture comboBox = dialogFixture
				.comboBox("ComboBoxReportType");
		comboBox.selectItem(2);
		String reportName = comboBox.valueAt(2);
		dialogFixture.comboBox("ComboBoxProductArea").selectItem(
				"Garasje villa");
		dialogFixture.comboBox("ComboBoxWeek").selectItem(3);
		dialogFixture.button("ButtonShowReport").click();

		WindowFinder.findDialog(reportName).withTimeout(60000).using(
				dialogFixture.robot);
	}

}
