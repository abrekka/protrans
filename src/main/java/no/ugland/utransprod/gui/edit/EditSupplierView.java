package no.ugland.utransprod.gui.edit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.SupplierViewHandler;
import no.ugland.utransprod.gui.model.SupplierModel;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.validators.SupplierValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse for visning og editering av leverandør
 * 
 * @author atle.brekka
 */
public class EditSupplierView extends AbstractEditView<SupplierModel, Supplier> {
	private JTextField textFieldSupplierName;

	private JTextField textFieldSupplierDescription;

	private JTextField textFieldPhone;

	private JComboBox comboBoxSupplierType;

	private JTextField textFieldAddress;

	private JTextField textFieldPostalCode;

	private JTextField textFieldPostOffice;

	private JTextField textFieldFax;

	private JButton buttonEmployee;
	private JCheckBox checkBoxInactive;

	private JList listProductAreaGroyp;

	private JButton buttonAddProductAreaGroup;

	private JButton buttonDeleteProductAreaGroup;

	/**
	 * @param searchDialog
	 * @param supplier
	 * @param aViewHandler
	 */
	public EditSupplierView(boolean searchDialog, Supplier supplier,
			AbstractViewHandler<Supplier, SupplierModel> aViewHandler) {
		super(searchDialog, new SupplierModel(supplier), aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {

		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,100dlu,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,30dlu,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldSupplierName, cc.xy(4, 2));
		builder.addLabel("Type:", cc.xy(2, 4));
		builder.add(comboBoxSupplierType, cc.xy(4, 4));
		builder.addLabel("Telefon:", cc.xy(2, 6));
		builder.add(textFieldPhone, cc.xy(4, 6));
		builder.addLabel("Fax:", cc.xy(2, 8));
		builder.add(textFieldFax, cc.xy(4, 8));
		builder.addLabel("Adresse:", cc.xy(2, 10));
		builder.add(textFieldAddress, cc.xy(4, 10));
		builder.addLabel("Postnr:", cc.xy(2, 12));
		builder.add(textFieldPostalCode, cc.xy(4, 12));
		builder.addLabel("Poststed:", cc.xy(2, 14));
		builder.add(textFieldPostOffice, cc.xy(4, 14));
		builder.addLabel("Beskrivelse:", cc.xy(2, 16));
		builder.add(textFieldSupplierDescription, cc.xy(4, 16));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonEmployee),
				cc.xy(6, 16));
		builder.add(checkBoxInactive, cc.xy(2, 18));

		builder.addLabel("Produktområder:", cc.xy(2, 20));
		builder.add(new JScrollPane(listProductAreaGroyp), cc.xywh(4, 20, 1, 5));
		builder.add(buildProductAreaGroupButtons(), cc.xywh(6, 20, 1, 5));

		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 26, 5));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());

	}

	private JPanel buildProductAreaGroupButtons() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonAddProductAreaGroup);
		builder.addRelatedGap();
		builder.addGridded(buttonDeleteProductAreaGroup);
		return builder.getPanel();

	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(SupplierModel object, boolean search) {
		return new SupplierValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldSupplierName, true);
		ValidationComponentUtils.setMessageKey(textFieldSupplierName,
				"Supplier.navn");

		ValidationComponentUtils.setMandatory(textFieldPostalCode, true);
		ValidationComponentUtils.setMessageKey(textFieldPostalCode,
				"Supplier.postnr");

		ValidationComponentUtils.setMandatory(comboBoxSupplierType, true);
		ValidationComponentUtils.setMessageKey(comboBoxSupplierType,
				"Supplier.type");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textFieldSupplierName = ((SupplierViewHandler) viewHandler)
				.getTextFieldSupplierName(presentationModel);
		textFieldSupplierDescription = ((SupplierViewHandler) viewHandler)
				.getTextFieldSupplierDescription(presentationModel);
		textFieldPhone = ((SupplierViewHandler) viewHandler)
				.getTextFieldPhone(presentationModel);
		textFieldFax = ((SupplierViewHandler) viewHandler)
				.getTextFieldFax(presentationModel);
		textFieldAddress = ((SupplierViewHandler) viewHandler)
				.getTextFieldAddress(presentationModel);
		textFieldPostalCode = ((SupplierViewHandler) viewHandler)
				.getTextFieldPostalCode(presentationModel);
		textFieldPostOffice = ((SupplierViewHandler) viewHandler)
				.getTextFieldPostOffice(presentationModel);
		comboBoxSupplierType = ((SupplierViewHandler) viewHandler)
				.getComboBoxSupplierType(presentationModel);
		buttonEmployee = ((SupplierViewHandler) viewHandler)
				.getButtonEmployee();
		checkBoxInactive = ((SupplierViewHandler) viewHandler)
				.getCheckBoxInactive(presentationModel);
		listProductAreaGroyp = ((SupplierViewHandler) viewHandler)
				.getListProductAreaGroup(presentationModel);
		buttonAddProductAreaGroup = ((SupplierViewHandler) viewHandler)
				.getButtonAddProductAreaGroup(presentationModel, aWindow);
		buttonDeleteProductAreaGroup = ((SupplierViewHandler) viewHandler)
				.getButtonDeleteProductAreaGroup(presentationModel);
	}

	public final String getDialogName() {
		return "EditSupplierView";
	}

	public final String getHeading() {
		return "Leverandør";
	}
}
