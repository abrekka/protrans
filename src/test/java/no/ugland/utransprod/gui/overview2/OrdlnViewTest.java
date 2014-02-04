package no.ugland.utransprod.gui.overview2;

import static junit.framework.Assert.assertEquals;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.OrdlnView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.OrdlnViewHandler;
import no.ugland.utransprod.test.GUITests;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.jdesktop.swingx.JXTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;

@Category(GUITests.class)
public class OrdlnViewTest {
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

	OrdlnViewHandler ordlnViewHandler = new OrdlnViewHandler("11");
	final OrdlnView ordlnView = new OrdlnView(ordlnViewHandler);

	JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
	    protected JDialog executeInEDT() {
		JDialog dialog = new JDialog();
		WindowInterface window = new JDialogAdapter(dialog);
		dialog.add(ordlnView.buildPanel(window));
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

	assertEquals(13, ((JXTable) dialogFixture.table("TableOrdln").target).getRowCount());

	dialogFixture.button("ButtonCancelOrdlnView");

    }

}
