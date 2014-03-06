package no.ugland.utransprod.gui.manuelt;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.fest.swing.data.TableCell.row;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.tree.TreePath;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.SplitOrderView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.SplitOrderViewHandler;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.gui.model.TransportableTreeNode;
import no.ugland.utransprod.gui.util.JXTreeTableFinder;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.core.ComponentFinder;
import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JTableFixture;
import org.jdesktop.swingx.JXTreeTable;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category(ManuellTest.class)
public class SplitOrderViewTest {
    static {
	try {

	    UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
	    JFrame.setDefaultLookAndFeelDecorated(true);
	    // LiquidLookAndFeel.setLiquidDecorations(true, "mac");

	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

    private DialogFixture dialogFixture;

    private SplitOrderViewHandler viewHandler;

    @Before
    public void setUp() throws Exception {
	FailOnThreadViolationRepaintManager.install();

	OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
	Transportable transportable = orderManager.findByOrderNr("45088");

	viewHandler = new SplitOrderViewHandler(transportable);
	final SplitOrderView splitOrderView = new SplitOrderView(viewHandler);

	JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
	    protected JDialog executeInEDT() {
		JDialog dialog = new JDialog();
		WindowInterface window = new JDialogAdapter(dialog);
		dialog.add(splitOrderView.buildPanel(window));
		dialog.pack();
		return dialog;
	    }
	});
	dialogFixture = new DialogFixture(dialog);
	dialogFixture.show();

    }

    @After
    public void tearDown() throws Exception {
	dialogFixture.robot.cleanUp();
	dialogFixture.cleanUp();
	dialogFixture = null;
    }

    @Test
    public void testRemoveOrderLine() {

	ComponentFinder finder = dialogFixture.robot.finder();
	JXTreeTable treeTable = finder.find(new JXTreeTableFinder("TreeTableTransportable"));

	int rowCount = treeTable.getRowCount();
	int currentRow;
	boolean orderLineFound = false;
	for (currentRow = 0; currentRow < rowCount; currentRow++) {
	    TreePath treePath = treeTable.getPathForRow(currentRow);
	    TransportableTreeNode treeNode = (TransportableTreeNode) treePath.getLastPathComponent();

	    if (treeNode.getObject() instanceof OrderLine) {
		orderLineFound = true;
		break;
	    }
	}

	assertEquals(true, orderLineFound);

	JTableFixture tableFixture = new JTableFixture(dialogFixture.robot, treeTable);
	tableFixture.cell(row(currentRow).column(1)).click();

	List<Object> splitted = viewHandler.getSplitted();
	assertNotNull(splitted);
	assertEquals(1, splitted.size());

	assertEquals(true, splitted.get(0) instanceof OrderLine);
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testShow() {
	dialogFixture.requireVisible();
	assertEquals("Artikler", dialogFixture.target.getTitle());

	dialogFixture.button("ButtonOk").requireVisible();
	dialogFixture.button("ButtonCancel").requireVisible();
	dialogFixture.button("ButtonCancel").click();
    }

    @Test
    public void testRemoveColli() {
	ComponentFinder finder = dialogFixture.robot.finder();
	JXTreeTable treeTable = finder.find(new JXTreeTableFinder("TreeTableTransportable"));

	int rowCount = treeTable.getRowCount();
	int currentRow;
	boolean colliFound = false;
	for (currentRow = 0; currentRow < rowCount; currentRow++) {
	    TreePath treePath = treeTable.getPathForRow(currentRow);
	    TransportableTreeNode treeNode = (TransportableTreeNode) treePath.getLastPathComponent();

	    if (treeNode.getObject() instanceof Colli) {
		colliFound = true;
		break;
	    }
	}

	assertEquals(true, colliFound);

	JTableFixture tableFixture = new JTableFixture(dialogFixture.robot, treeTable);
	tableFixture.cell(row(currentRow).column(1)).click();

	List<Object> splitted = viewHandler.getSplitted();
	assertNotNull(splitted);
	assertEquals(1, splitted.size());

	assertEquals(true, splitted.get(0) instanceof Colli);
	dialogFixture.button("ButtonCancel").click();
    }

}
