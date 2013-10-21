package no.ugland.utransprod.gui.manuelt;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.UIManager;

import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.LFEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.LoginImpl;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.EditSupplierView;
import no.ugland.utransprod.gui.handlers.SupplierViewHandler;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.test.ManuellTest;
import no.ugland.utransprod.util.ModelUtil;

import org.fest.swing.edt.FailOnThreadViolationRepaintManager;
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
@Category(ManuellTest.class)
public class EditSupplierTest {
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

	private SupplierManager supplierManager;

	@Mock
	private ManagerRepository managerRepository;

	@Before
	public void setUp() throws Exception {
		FailOnThreadViolationRepaintManager.install();
		MockitoAnnotations.initMocks(this);
		supplierManager = (SupplierManager) ModelUtil
				.getBean("supplierManager");

		ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
				.getBean("applicationUserManager");
		ApplicationUser user;
		user = applicationUserManager.login("admin", "admin");
		applicationUserManager.lazyLoad(user, new LazyLoadEnum[][] { {
				LazyLoadEnum.USER_ROLES, LazyLoadEnum.NONE } });
		UserType userType = user.getUserRoles().iterator().next().getUserType();

		Login login = new LoginImpl(user, userType);

		when(managerRepository.getSupplierManager())
				.thenReturn(supplierManager);

		SupplierViewHandler supplierViewHandler = new SupplierViewHandler(
				login, managerRepository);

		final EditSupplierView editSupplierView = new EditSupplierView(false,
				new Supplier(), supplierViewHandler);

		JDialog dialog = GuiActionRunner.execute(new GuiQuery<JDialog>() {
			protected JDialog executeInEDT() {
				JDialog dialog = new JDialog();
				WindowInterface window = new JDialogAdapter(dialog);
				dialog.add(editSupplierView.buildPanel(window));
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

		Supplier supplier = new Supplier();
		supplier.setSupplierName("test");
		List<Supplier> list = supplierManager.findByObject(supplier);
		if (list != null) {
			for (Supplier sup : list) {
				supplierManager.removeObject(sup);
			}
		}
	}

	@Test
	public void testShow() {
		dialogFixture.requireVisible();
	}

	@Test
	public void testInsertSupplier() {

		dialogFixture.textBox("TextFieldSupplierName").enterText("test");
		dialogFixture.comboBox("ComboBoxSupplierType").selectItem(3);
		dialogFixture.textBox("TextFieldPhone").enterText("1111111");
		dialogFixture.textBox("TextFieldFax").enterText("2222222");
		dialogFixture.textBox("TextFieldAddress").enterText("adresse");
		dialogFixture.textBox("TextFieldPostalCode").enterText("1000");
		dialogFixture.textBox("TextFieldPostOffice").enterText("poststed");
		dialogFixture.textBox("TextFieldSupplierDescription").enterText(
				"beskrivelse");
		dialogFixture.checkBox("CheckBoxInactive").check();
		dialogFixture.button("SaveSupplier").click();

		Supplier supplier = new Supplier();
		supplier.setSupplierName("test");
		List<Supplier> list = supplierManager.findByObject(supplier);

		assertNotNull(list);
		assertEquals(1, list.size());

		supplier = list.get(0);

		assertEquals("test", supplier.getSupplierName());
		assertEquals("Montering", supplier.getSupplierType()
				.getSupplierTypeName());
		assertEquals("1111111", supplier.getPhone());
		assertEquals("2222222", supplier.getFax());
		assertEquals("adresse", supplier.getAddress());
		assertEquals("1000", supplier.getPostalCode());
		assertEquals("poststed", supplier.getPostOffice());
		assertEquals("beskrivelse", supplier.getSupplierDescription());
		assertEquals(Integer.valueOf(1), supplier.getInactive());
	}

}
