package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.text.DecimalFormat;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListModel;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.model.OrderCostModel;
import no.ugland.utransprod.model.CostType;
import no.ugland.utransprod.model.CostUnit;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.CostTypeManager;
import no.ugland.utransprod.service.CostUnitManager;
import no.ugland.utransprod.service.EmployeeManager;
import no.ugland.utransprod.service.EmployeeTypeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YesNoInteger;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;

/**
 * Hjelpeklasse for visning av kostnad
 * 
 * @author atle.brekka
 * 
 */
public class OrderCostViewHandler implements Closeable {
	/**
	 * 
	 */
	private ArrayListModel costUnitList;

	/**
	 * 
	 */
	private ArrayListModel costTypeList;

	/**
	 * 
	 */
	private ArrayListModel supplierList;


	/**
	 * 
	 */
	private final ValidationResultModel validationResultModel = new DefaultValidationResultModel();

	/**
	 * 
	 */
	private boolean canceled = false;

	/**
	 * 
	 */
	private Login login;
	private ManagerRepository managerRepository;

	/**
	 * @param aUserType
	 */
	public OrderCostViewHandler(Login aLogin,ManagerRepository aManagerRepository) {
		login=aLogin;
		managerRepository=aManagerRepository;
	
		costTypeList = new ArrayListModel();
		costUnitList = new ArrayListModel();
		supplierList = new ArrayListModel();
		CostTypeManager costTypeManager = (CostTypeManager) ModelUtil
				.getBean("costTypeManager");
		CostUnitManager costUnitManager = (CostUnitManager) ModelUtil
				.getBean("costUnitManager");

		List<CostType> list = costTypeManager.findAll();

		if (list != null) {
			costTypeList.addAll(list);
		}

		List<CostUnit> listUnit = costUnitManager.findAll();

		if (listUnit != null) {
			costUnitList.addAll(listUnit);
		}

		List<Supplier> listSupplier = managerRepository.getSupplierManager().findAll();

		if (listSupplier != null) {
			supplierList.addAll(listSupplier);
		}

	}

	

	/**
	 * Lager comboboks for kostnadstype
	 * 
	 * @param presentationModel
	 * @return comboboks
	 */
	public JComboBox getComboBoxCostType(PresentationModel presentationModel) {
		JComboBox combo = new JComboBox(new ComboBoxAdapter(
				(ListModel) costTypeList, presentationModel
						.getModel(OrderCostModel.PROPERTY_COST_TYPE)));
		combo.setName("ComboBoxCostType");
		return combo;
	}

	/**
	 * Lager comboboks for kostnadsenhet
	 * 
	 * @param presentationModel
	 * @return comboboks
	 */
	public JComboBox getComboBoxCostUnit(PresentationModel presentationModel) {
		JComboBox combo = new JComboBox(new ComboBoxAdapter(
				(ListModel) costUnitList, presentationModel
						.getModel(OrderCostModel.PROPERTY_COST_UNIT)));
		combo.setName("ComboBoxCostUnit");
		return combo;
	}

	/**
	 * Lager comboboks for moms
	 * 
	 * @param presentationModel
	 * @return comboboks
	 */
	public JComboBox getComboBoxVat(PresentationModel presentationModel) {
		return new JComboBox(new ComboBoxAdapter(new Object[] {
				new YesNoInteger(0), new YesNoInteger(1) }, presentationModel
				.getModel(OrderCostModel.PROPERTY_IS_INCL_VAT)));
	}

	/**
	 * Lager comboboks for leverandører
	 * 
	 * @param presentationModel
	 * @return comboboks
	 */
	public JComboBox getComboBoxSupplier(PresentationModel presentationModel) {
		return new JComboBox(new ComboBoxAdapter((ListModel) supplierList,
				presentationModel.getModel(OrderCostModel.PROPERTY_SUPPLIER)));
	}

	/**
	 * Lager tekstfelt for beløp
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldAmount(PresentationModel presentationModel) {
		DecimalFormat decimalFormat = new DecimalFormat("#,##0.0#");
		decimalFormat.setDecimalSeparatorAlwaysShown(true);
		decimalFormat.setParseBigDecimal(true);
		JTextField textField = BasicComponentFactory
				.createFormattedTextField(presentationModel
						.getModel(OrderCostModel.PROPERTY_COST_AMOUNT),
						decimalFormat);
		textField.setName("TextFieldAmount");
		return textField;
	}

	/**
	 * Lager tekstfelt for fakturanummer
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldInvoiceNr(PresentationModel presentationModel) {

		return BasicComponentFactory.createTextField(presentationModel
				.getModel(OrderCostModel.PROPERTY_INVOICE_NR));
	}

	/**
	 * Henter ok-knapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getOkButton(WindowInterface window) {
		JButton buttonOk = new CancelButton(window, this, true, "Ok",
				IconEnum.ICON_OK, null, true);
		buttonOk.setName("OkOrderCost");
		return buttonOk;
	}

	/**
	 * Lager knapp for å legge til leverandør
	 * 
	 * @param presentationModel
	 * @param window
	 * @return knapp
	 */
	public JButton getAddSupplierButton(PresentationModel presentationModel,
			WindowInterface window) {
		return new JButton(new AddSupplierAction(presentationModel, window));
	}

	/**
	 * Henter avbrytknapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getCancelButton(WindowInterface window) {
		JButton buttonCancel = new CancelButton(window, this, true);
		buttonCancel.setName("CancelOrderCost");
		return buttonCancel;
	}

	/**
	 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	public boolean canClose(String actionString, WindowInterface window) {
		if (actionString.equalsIgnoreCase("Ok")) {
			canceled = false;
			if (validationResultModel.hasErrors()) {
				Util.showErrorDialog(window, "Rett feil", "Rett alle feil!");
				return false;
			}
		} else {
			canceled = true;
		}
		return true;
	}

	/**
	 * Henter feilrapportering
	 * 
	 * @return feilrapportering
	 */
	public ValidationResultModel getValidationResultModel() {
		return validationResultModel;
	}

	/**
	 * Henter kostnad
	 * 
	 * @param presentationModel
	 * @return kostnad
	 */
	public OrderCostModel getOrderCostModel(PresentationModel presentationModel) {
		if (canceled) {
			return null;
		}
		return (OrderCostModel) presentationModel.getBean();
	}

	/**
	 * Håndterer å legge til leverandør
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class AddSupplierAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private PresentationModel presentationModel;

		/**
		 * 
		 */
		private WindowInterface window;

		/**
		 * @param aPresentationModel
		 * @param aWindow
		 */
		public AddSupplierAction(PresentationModel aPresentationModel,
				WindowInterface aWindow) {
			super("...");
			presentationModel = aPresentationModel;
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			SupplierViewHandler supplierViewHandler = new SupplierViewHandler(
					login, managerRepository);
			Supplier supplier = supplierViewHandler.insertNewSupplier(window);
			presentationModel.setValue(OrderCostModel.PROPERTY_SUPPLIER,
					supplier);
		}
	}
}
