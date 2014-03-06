package no.ugland.utransprod.gui.manuelt;

import static org.mockito.Mockito.when;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.LoginImpl;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditConstructionTypeView;
import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandler;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * @author atle.brekka
 * 
 */
@Category(ManuellTest.class)
public class EditConstructionTypeViewTest {
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
    private ManagerRepository managerRepository;

    /**
     * @see junit.framework.TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
	FailOnThreadViolationRepaintManager.install();
	MockitoAnnotations.initMocks(this);

	ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil.getBean("applicationUserManager");
	final ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil.getBean(ConstructionTypeManager.MANAGER_NAME);
	when(managerRepository.getConstructionTypeManager()).thenReturn(constructionTypeManager);

	ApplicationUser user;

	user = applicationUserManager.login("admin", "admin");
	applicationUserManager.lazyLoad(user, new LazyLoadEnum[][] { { LazyLoadEnum.USER_ROLES, LazyLoadEnum.NONE } });
	UserType userType = user.getUserRoles().iterator().next().getUserType();

	Login login = new LoginImpl(user, userType);

	ConstructionTypeViewHandler constructionTypeViewHandler = new ConstructionTypeViewHandler(login, managerRepository, false, false);

	final EditConstructionTypeView view = new EditConstructionTypeView(constructionTypeViewHandler, new ConstructionType(), false);

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

    /**
     * @see junit.framework.TestCase#tearDown()
     */
    @After
    public void tearDown() throws Exception {
	dialogFixture.cleanUp();
	ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil.getBean("constructionTypeManager");
	ConstructionType constructionType = constructionTypeManager.findByName("test");
	if (constructionType != null) {
	    constructionTypeManager.removeConstructionType(constructionType);
	}
    }

    @Test
    public void testShow() {
	dialogFixture.requireVisible();
    }

    @Test
    public void testShowProductionArea() {
	dialogFixture.comboBox("ComboBoxProductArea").requireVisible();

    }

    @Test
    public void testInsertNew() {
	dialogFixture.textBox("TextFieldName").enterText("test");
	dialogFixture.textBox("TextFieldDescription").enterText("Test");
	dialogFixture.comboBox("ComboBoxProductArea").selectItem("Garasje villa");
	dialogFixture.button("SaveConstructionType").click();
    }

    @Test
    public void testInsertNewWithoutProductArea() {
	dialogFixture.textBox("TextFieldName").enterText("test");
	dialogFixture.textBox("TextFieldDescription").enterText("Test");

	dialogFixture.button("SaveConstructionType").click();

	JOptionPaneFinder.findOptionPane();
    }

}
