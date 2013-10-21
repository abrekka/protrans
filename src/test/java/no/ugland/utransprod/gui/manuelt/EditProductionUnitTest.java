package no.ugland.utransprod.gui.manuelt;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.LoginImpl;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditProductionUnitView;
import no.ugland.utransprod.gui.handlers.ProductionUnitViewHandler;
import no.ugland.utransprod.gui.model.ProductionUnitModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ProductionUnitManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
import org.fest.swing.edt.GuiActionRunner;
import org.fest.swing.edt.GuiQuery;
import org.fest.swing.finder.JOptionPaneFinder;
import org.fest.swing.finder.WindowFinder;
import org.fest.swing.fixture.DialogFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.birosoft.liquid.LiquidLookAndFeel;
@Category(ManuellTest.class)
public class EditProductionUnitTest {
	static {
		try {

			UIManager.setLookAndFeel(LFEnum.LNF_LIQUID.getClassName());
			JFrame.setDefaultLookAndFeelDecorated(true);
			LiquidLookAndFeel.setLiquidDecorations(true, "mac");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * test
	 */
	private DialogFixture dialogFixture;

	/**
     *
     */

	private ProductionUnitManager productionUnitManager;

	/**
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		FailOnThreadViolationRepaintManager.install();
		productionUnitManager = (ProductionUnitManager) ModelUtil
				.getBean("productionUnitManager");

		ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean("applicationUserManager");
		ApplicationUser user;
		user = applicationUserManager.login("admin", "admin");
		applicationUserManager.lazyLoad(user, new LazyLoadEnum[][] { {
				LazyLoadEnum.USER_ROLES, LazyLoadEnum.NONE } });
		UserType userType = user.getUserRoles().iterator().next().getUserType();

		Login login = new LoginImpl(user, userType);

		ProductionUnitViewHandler productionUnitViewHandler = new ProductionUnitViewHandler(
				login, productionUnitManager);

		ProductionUnitModel productionUnitModel = new ProductionUnitModel(
				new ProductionUnit());
		final EditProductionUnitView editProductionUnitView = new EditProductionUnitView(
				false, productionUnitModel, productionUnitViewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(editProductionUnitView.buildPanel(window));
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

		ProductionUnit productionUnit = new ProductionUnit();
		productionUnit.setProductionUnitName("test");
		List<ProductionUnit> list = productionUnitManager
				.findByObject(productionUnit);
		if (list != null) {
			for (ProductionUnit prodUnit : list) {
				productionUnitManager.removeObject(prodUnit);
			}
		}
	}

	@Test
	public void testShow() {
		dialogFixture.requireVisible();

		dialogFixture.textBox("TextFieldProductionUnitName").requireVisible();
		dialogFixture.comboBox("ComboBoxArticleType").requireVisible();
		dialogFixture.list("ListProductAreaGroup").requireVisible();
		dialogFixture.button("ButtonAddProductAreaGroup").requireVisible();
		dialogFixture.button("ButtonRemoveProductAreaGroup").requireVisible();
		dialogFixture.button("ButtonRemoveProductAreaGroup").requireDisabled();
		dialogFixture.button("SaveProductionUnit").requireVisible();
		dialogFixture.button("EditCancelProductionUnit").requireVisible();
	}

	@Test
	public void testInsertProductionUnitWithErrors() {
		dialogFixture.comboBox("ComboBoxArticleType").selectItem("Takstoler");
		dialogFixture.textBox("TextFieldProductionUnitName").enterText("test");

		dialogFixture.button("SaveProductionUnit").click();

		JOptionPaneFinder.findOptionPane().using(dialogFixture.robot);
	}

	@Test
	public void testInsertProductionUnit() {
		dialogFixture.show();
		dialogFixture.comboBox("ComboBoxArticleType").selectItem("Takstoler");
		dialogFixture.textBox("TextFieldProductionUnitName").enterText("test");

		dialogFixture.button("ButtonAddProductAreaGroup").click();

		DialogFixture optionDialog = WindowFinder.findDialog(
				"Velg produktområde").using(dialogFixture.robot);
		optionDialog.comboBox().selectItem("Takstol");
		optionDialog.button("ButtonOk").click();

		dialogFixture.button("SaveProductionUnit").click();

		ProductionUnit productionUnit = new ProductionUnit();
		productionUnit.setProductionUnitName("test");
		List<ProductionUnit> list = productionUnitManager
				.findByObject(productionUnit);
		assertNotNull(list);
		assertEquals(1, list.size());

		productionUnit = list.get(0);
		productionUnitManager.lazyLoad(productionUnit, new LazyLoadEnum[][] { {
				LazyLoadEnum.PRODUCTION_UNIT_PRODUCT_AREA_GROUPS,
				LazyLoadEnum.NONE } });
		assertEquals("Takstoler", productionUnit.getArticleType()
				.getArticleTypeName());
		assertEquals("test", productionUnit.getProductionUnitName());

		assertEquals(1, productionUnit.getProductionUnitProductAreaGroups()
				.size());
		assertEquals("Takstol", productionUnit
				.getProductionUnitProductAreaGroups().iterator().next()
				.getProductAreaGroup().getProductAreaGroupName());

	}

	@Test
	public void testRemoveProductAreaGroup() {
		dialogFixture.comboBox("ComboBoxArticleType").selectItem("Takstoler");
		dialogFixture.textBox("TextFieldProductionUnitName").enterText("test");

		dialogFixture.button("ButtonAddProductAreaGroup").click();

		DialogFixture optionDialog = WindowFinder.findDialog(
				"Velg produktområde").using(dialogFixture.robot);
		optionDialog.comboBox().selectItem("Takstol");
		optionDialog.button("ButtonOk").click();

		dialogFixture.button("ButtonAddProductAreaGroup").click();

		optionDialog = WindowFinder.findDialog("Velg produktområde").using(
				dialogFixture.robot);
		optionDialog.comboBox().selectItem("Garasje");
		optionDialog.button("ButtonOk").click();

		dialogFixture.list("ListProductAreaGroup").selectItem(1);
		dialogFixture.button("ButtonRemoveProductAreaGroup").requireEnabled();
		dialogFixture.button("ButtonRemoveProductAreaGroup").click();

		dialogFixture.button("SaveProductionUnit").click();

		ProductionUnit productionUnit = new ProductionUnit();
		productionUnit.setProductionUnitName("test");
		List<ProductionUnit> list = productionUnitManager
				.findByObject(productionUnit);
		assertNotNull(list);
		assertEquals(1, list.size());

		productionUnit = list.get(0);
		productionUnitManager.lazyLoad(productionUnit, new LazyLoadEnum[][] { {
				LazyLoadEnum.PRODUCTION_UNIT_PRODUCT_AREA_GROUPS,
				LazyLoadEnum.NONE } });
		assertEquals("Takstoler", productionUnit.getArticleType()
				.getArticleTypeName());
		assertEquals("test", productionUnit.getProductionUnitName());

		assertEquals(1, productionUnit.getProductionUnitProductAreaGroups()
				.size());
		assertEquals("Takstol", productionUnit
				.getProductionUnitProductAreaGroups().iterator().next()
				.getProductAreaGroup().getProductAreaGroupName());

	}
}
