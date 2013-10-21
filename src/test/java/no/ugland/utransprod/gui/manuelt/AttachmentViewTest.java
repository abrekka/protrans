package no.ugland.utransprod.gui.manuelt;

import static junit.framework.Assert.assertEquals;

import java.awt.Component;
import java.io.File;
import java.net.URL;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.AttachmentView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.MainClass;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AttachmentViewHandler;
import no.ugland.utransprod.service.ApplicationParamManager;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.io.FileUtils;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JFileChooserFinder;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JFileChooserFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;
@Category(ManuellTest.class)
public class AttachmentViewTest {
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
		initDesktopDll();

		ApplicationParamManager applicationParamManager = (ApplicationParamManager) ModelUtil
				.getBean(ApplicationParamManager.MANAGER_NAME);
		ApplicationParamUtil.setApplicationParamManger(applicationParamManager);

		AttachmentViewHandler attachmentViewHandler = new AttachmentViewHandler(
				"testdir");
		final AttachmentView viewer = new AttachmentView(attachmentViewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(viewer.buildPanel(window));
				dialog.pack();
				return dialog;
			}
		});
		dialogFixture = new DialogFixture(dialog);
		dialogFixture.show();

	}

	
	private static void initDesktopDll() {
		MainClass.class.getClassLoader();
		URL dllUrl = ClassLoader.getSystemResource("jdic.dll");
		if (dllUrl == null) {
			Util.showErrorDialog((Component) null, "Feil",
					"Finner ikke jdic.dll");
		} else {
			System.load(dllUrl.getPath());
		}
		if (dllUrl != null) {
			System.load(dllUrl.getPath());
		}
	}

	@After
	public void tearDown() throws Exception {
		dialogFixture.cleanUp();
		ApplicationParamUtil.setApplicationParamManger(null);
	}

	@Test
	public void testShowAttachmentView() {
		dialogFixture.list("ListAttachments").requireVisible();
		dialogFixture.button("ButtonAddAttachment").requireVisible();
		dialogFixture.button("ButtonAddAttachment").requireEnabled();
		dialogFixture.button("ButtonShowAttachment").requireVisible();
		dialogFixture.button("ButtonShowAttachment").requireDisabled();
		dialogFixture.button("ButtonDeleteAttachment").requireVisible();
		dialogFixture.button("ButtonDeleteAttachment").requireDisabled();
	}

	@Test
	public void testAddAttachment() throws Exception {
		File file = new File("C:\\java\\workspace\\ProTrans_google\\vedlegg\\testdir");
		FileUtils.cleanDirectory(file);
		dialogFixture.show();
		int lenght = dialogFixture.list("ListAttachments").contents().length;
		addFile("Budsjett_import_villa.xls");

		assertEquals(lenght + 1, dialogFixture.list("ListAttachments")
				.contents().length);
	}

	@Test
	public void testDeleteAttachment() {
		dialogFixture.show();
		int lenght = dialogFixture.list("ListAttachments").contents().length;

		if (lenght == 0) {
			addFile("Budsjett_import_villa.xls");
			lenght = 1;
		}
		dialogFixture.list("ListAttachments").selectItem(0);
		dialogFixture.button("ButtonDeleteAttachment").requireEnabled();
		dialogFixture.button("ButtonDeleteAttachment").click();
		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
				.buttonWithText("Ja").click();

		assertEquals(lenght - 1, dialogFixture.list("ListAttachments")
				.contents().length);
		dialogFixture.button("ButtonDeleteAttachment").requireDisabled();
	}

	@Test
	public void testShowExcelFile() {
		addFile("Budsjett_import_villa.xls");
		dialogFixture.list("ListAttachments").selectItem(
				"Budsjett_import_villa.xls");
		dialogFixture.button("ButtonShowAttachment").requireEnabled();
		dialogFixture.button("ButtonShowAttachment").click();
	}

	@Test
	public void testShowBoqFile() {

		addFile("test_cutting.boq");

		dialogFixture.list("ListAttachments").selectItem("test_cutting.boq");
		dialogFixture.button("ButtonShowAttachment").requireEnabled();
		dialogFixture.button("ButtonShowAttachment").click();

		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot)
				.buttonWithText("Ja").click();
	}

	private void addFile(String fileName) {
		Util.setFileDirectory("C:\\java\\workspace\\ProTrans\\src\\test\\resources");
		dialogFixture.button("ButtonAddAttachment").click();

		JFileChooserFixture fileChooser = JFileChooserFinder
				.findFileChooser("fileChooser").withTimeout(10000)
				.using(dialogFixture.robot);
		fileChooser.fileNameTextBox().enterText(fileName);
		fileChooser.approveButton().click();
	}

}
