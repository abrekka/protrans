package no.ugland.utransprod.gui.group1;

import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ProductionBudgetViewHandler;
import no.ugland.utransprod.gui.model.ProductionBudgetModel;
import no.ugland.utransprod.model.Budget;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.BudgetManager;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.test.GUITests;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.birosoft.liquid.LiquidLookAndFeel;

/**
 * @author atle.brekka
 * 
 */
@Category(GUITests.class)
public class ProductionBudgetViewTest {
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
	@Mock
	private Login login;

	@Before
	public void setUp() throws Exception {
		MockitoAnnotations.initMocks(this);
		UserType userType = new UserType();
		userType.setIsAdmin(1);
		when(login.getUserType()).thenReturn(userType);

		BudgetManager productionBudgetManager = (BudgetManager) ModelUtil
				.getBean(BudgetManager.MANAGER_NAME);

		Set<UserTypeAccess> userTypeAccesses = new HashSet<UserTypeAccess>();
		UserTypeAccess userTypeAccess = new UserTypeAccess();
		userTypeAccess.setWindowAccess(new WindowAccess(null, "Attributter",
				null));
		userTypeAccesses.add(userTypeAccess);
		userType.setUserTypeAccesses(userTypeAccesses);

		final OverviewView<Budget, ProductionBudgetModel> view = new OverviewView<Budget, ProductionBudgetModel>(
				new ProductionBudgetViewHandler(login, true,
						productionBudgetManager));

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
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");
		ConstructionType constructionType = constructionTypeManager
				.findByName("test");
		if (constructionType != null) {
			constructionTypeManager.removeConstructionType(constructionType);
		}
	}

	@Test
	public void testNewProductionBudget() {
		dialogFixture.requireVisible();
	}

}
