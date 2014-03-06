package no.ugland.utransprod.gui.group1;

import static org.mockito.Mockito.when;

import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.TransportCostView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.TransportCostViewHandler;
import no.ugland.utransprod.gui.util.JYearChooserFinder;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.TransportCostBasisManager;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.core.BasicComponentFinder;
import org.fest.swing.core.ComponentFinder;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.fest.swing.fixture.JComboBoxFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.toedter.calendar.JYearChooser;

@Category(GUITests.class)
public class TransportCostViewTest {
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
    @Mock
    private Login login;

    @Before
    public void setUp() throws Exception {
	MockitoAnnotations.initMocks(this);
	UserType userType = new UserType();
	userType.setIsAdmin(1);
	when(login.getUserType()).thenReturn(userType);

	TransportCostBasisManager transportCostBasisManager = (TransportCostBasisManager) ModelUtil.getBean(TransportCostBasisManager.MANAGER_NAME);

	TransportCostViewHandler viewHandler = new TransportCostViewHandler(login, transportCostBasisManager);
	final TransportCostView view = new TransportCostView(viewHandler);

	JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
	    protected JDialog executeInEDT() {
		JDialog dialog = new JDialog();
		WindowInterface window = new JDialogAdapter(dialog);
		dialog.add(view.buildPanel(window));
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
    public void testGenerateTransportCost() {
	dialogFixture.show(new Dimension(500, 500));

	ComponentFinder finder = BasicComponentFinder.finderWithCurrentAwtHierarchy();
	JYearChooser yearChooser = finder.find(new JYearChooserFinder("YearChooserTransportCost"));
	yearChooser.setYear(2008);

	JComboBoxFixture comboBoxWeekFrom = dialogFixture.comboBox("ComboBoxWeekFrom");
	comboBoxWeekFrom.selectItem("50");

	JComboBoxFixture comboBoxWeekTo = dialogFixture.comboBox("ComboBoxWeekTo");
	comboBoxWeekTo.selectItem("50");

	dialogFixture.table("TableTransportCostBasis").requireVisible();

    }

}
