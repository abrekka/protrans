package no.ugland.utransprod.gui.manuelt;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditColliView;
import no.ugland.utransprod.gui.handlers.ColliViewHandler;
import no.ugland.utransprod.gui.model.ColliModel;
import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.service.ColliManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.util.UserUtil;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class EditColliViewTest {
    private DialogFixture dialogFixture;
    @Mock
    private UserTypeManager userTypeManager;
    @Mock
    private Login login;
    @Mock
    private ManagerRepository managerRepository;
    @Mock
    private ColliManager colliManager;

    @Before
    public void setup() {
	FailOnThreadViolationRepaintManager.install();
	MockitoAnnotations.initMocks(this);
	final Colli colli = new Colli("test");
	final ColliModel colliModel = new ColliModel(colli);

	final Packable packable = null;
	final UserType userType = new UserType();
	Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
	userType.setUserTypeAccesses(userTypeAccesses);
	UserUtil.setUserTypeManagerForTest(userTypeManager);
	when(managerRepository.getColliManager()).thenReturn(colliManager);
	when(login.getUserType()).thenReturn(userType);

	JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
	    protected JDialog executeInEDT() {
		ColliViewHandler colliViewHandler = new ColliViewHandler("Kolli", colli, packable, login, managerRepository, null, null);
		final EditColliView editColliView = new EditColliView(false, colliModel, colliViewHandler);
		JDialog dialog = new JDialog();
		WindowInterface window = new JDialogAdapter(dialog);
		dialog.add(editColliView.buildPanel(window));
		dialog.pack();
		return dialog;
	    }
	});
	dialogFixture = new DialogFixture(dialog);
	dialogFixture.show();

    }

    @After
    public void tear() {
	dialogFixture.cleanUp();
    }

    @Test
    public void show() {
	dialogFixture.requireVisible();
    }

    @Test
    public void setName() {

	dialogFixture.show();
	dialogFixture.textBox("TextFieldColliName").enterText("test");
	dialogFixture.button("EditCancelColli").requireEnabled();
	dialogFixture.button("EditCancelColli").click();
    }

    @Test
    public void setNumberOfCollies() {
	dialogFixture.show();
	dialogFixture.textBox("TextFieldNumberOfCollies").enterText("test");
    }
}
