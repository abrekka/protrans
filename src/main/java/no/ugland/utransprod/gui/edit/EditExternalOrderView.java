package no.ugland.utransprod.gui.edit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.ExternalOrderViewHandler;
import no.ugland.utransprod.gui.model.ExternalOrderModel;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.validators.ExternalOrderValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.toedter.calendar.JDateChooser;

/**
 * Klasse for visning og editering av ekstern ordre
 * 
 * @author atle.brekka
 * 
 */
public class EditExternalOrderView extends
		AbstractEditView<ExternalOrderModel, ExternalOrder> {
	/**
	 * 
	 */
	private JXTable tableOrderArticles;

	/**
	 * 
	 */
	private JXTable tableExternalArticles;

	/**
	 * 
	 */
	private JButton buttonOrderArticle;

	/**
	 * 
	 */
	private JComboBox comboBoxSupplier;

	/**
	 * 
	 */
	private JTextField textFieldExternalOrderNr;

	/**
	 * 
	 */
	private JDateChooser dateChooserDeliveryDate;

	/**
	 * 
	 */
	private JButton buttonAddArticle;

	/**
	 * 
	 */
	private JButton buttonRemoveExternalOrderLine;

	/**
	 * 
	 */
	private JButton buttonEditExternalOrderLine;

	/**
	 * 
	 */
	private JButton buttonFax;

	/**
	 * 
	 */
	private JTextField textFieldAtt;

	/**
	 * 
	 */
	private JTextField textFieldOurRef;

	/**
	 * 
	 */
	private JTextField textFieldMarkedWith;

	/**
	 * 
	 */
	private JTextField textFieldPhoneNr;

	/**
	 * 
	 */
	private JTextField textFieldFaxNr;

	/**
	 * @param externalOrder
	 * @param aViewHandler
	 * @param searching
	 */
	public EditExternalOrderView(
			ExternalOrder externalOrder,
			AbstractViewHandler<ExternalOrder, ExternalOrderModel> aViewHandler,
			boolean searching) {
		super(searching, new ExternalOrderModel(externalOrder), aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,60dlu,3dlu,p,3dlu,45dlu,3dlu,20dlu,3dlu,p,3dlu,90dlu,3dlu,120dlu,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,15dlu,3dlu,p,3dlu,15dlu,3dlu,100dlu,3dlu,p,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Leverandør:", cc.xy(2, 2));
		builder.add(comboBoxSupplier, cc.xyw(4, 2, 5));
		builder.addLabel("Eksternt ordrenr.:", cc.xy(2, 4));
		builder.add(textFieldExternalOrderNr, cc.xy(4, 4));
		builder.addLabel("Att.:", cc.xy(6, 4));
		builder.add(textFieldAtt, cc.xy(8, 4));
		builder.addLabel("Tlf:", cc.xy(12, 4));
		builder.add(textFieldPhoneNr, cc.xy(14, 4));
		builder.addLabel("Merkes med:", cc.xy(12, 2));
		builder.add(textFieldMarkedWith, cc.xy(14, 2));
		builder.addLabel("Leveringsdato:", cc.xy(2, 6));
		builder.add(dateChooserDeliveryDate, cc.xy(4, 6));
		builder.addLabel("Vår ref.:", cc.xy(6, 6));
		builder.add(textFieldOurRef, cc.xy(8, 6));
		builder.addLabel("Fax:", cc.xy(12, 6));
		builder.add(textFieldFaxNr, cc.xy(14, 6));
		builder.addLabel("Artikler", cc.xy(2, 8));
		builder.add(new JScrollPane(tableOrderArticles), cc.xywh(2, 10, 9, 7));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonAddArticle),
				cc.xyw(2, 18, 7));
		builder.add(buttonOrderArticle, cc.xy(12, 12));
		builder.addLabel("Artikler i bestilling", cc.xy(14, 8));
		builder.add(new JScrollPane(tableExternalArticles),
				cc.xywh(14, 10, 3, 7));
		builder.add(buildButtonPanel(), cc.xywh(18, 12, 1, 3));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonSave, buttonFax,
				buttonCancel), cc.xyw(2, 20, 15));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());

	}

	/**
	 * Bygger knappepanel
	 * 
	 * @return panel
	 */
	private JPanel buildButtonPanel() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonEditExternalOrderLine);
		builder.addGlue();
		builder.addGridded(buttonRemoveExternalOrderLine);
		return builder.getPanel();
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(ExternalOrderModel object, boolean search) {
		return new ExternalOrderValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(comboBoxSupplier, true);
		ValidationComponentUtils.setMessageKey(comboBoxSupplier,
				"Bestilling.leverandør");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		tableOrderArticles = ((ExternalOrderViewHandler) viewHandler)
				.getTableOrderArticles(presentationModel);
		tableExternalArticles = ((ExternalOrderViewHandler) viewHandler)
				.getTableExternalArticles(presentationModel);
		buttonOrderArticle = ((ExternalOrderViewHandler) viewHandler)
				.getButtonOrderArticle(presentationModel, aWindow);
		comboBoxSupplier = ((ExternalOrderViewHandler) viewHandler)
				.getComboBoxSupplier(presentationModel);
		textFieldExternalOrderNr = ((ExternalOrderViewHandler) viewHandler)
				.getTextFieldExternalOrderNr(presentationModel);
		dateChooserDeliveryDate = ((ExternalOrderViewHandler) viewHandler)
				.getDateChooserDeliveryDate(presentationModel);
		buttonAddArticle = ((ExternalOrderViewHandler) viewHandler)
				.getButtonAddArticle(aWindow);
		buttonEditExternalOrderLine = ((ExternalOrderViewHandler) viewHandler)
				.getButtonEditExternalOrderLine(aWindow, presentationModel);
		buttonRemoveExternalOrderLine = ((ExternalOrderViewHandler) viewHandler)
				.getButtonRemoveExternalOrderLine(presentationModel);
		buttonFax = ((ExternalOrderViewHandler) viewHandler).getButtonFax(
				aWindow, presentationModel);
		textFieldAtt = ((ExternalOrderViewHandler) viewHandler)
				.getTextFieldAtt(presentationModel);
		textFieldOurRef = ((ExternalOrderViewHandler) viewHandler)
				.getTextFieldOurRef(presentationModel);
		textFieldMarkedWith = ((ExternalOrderViewHandler) viewHandler)
				.getTextFieldMarkedWith(presentationModel);
		textFieldPhoneNr = ((ExternalOrderViewHandler) viewHandler)
				.getTextFieldPhoneNr(presentationModel);
		textFieldFaxNr = ((ExternalOrderViewHandler) viewHandler)
				.getTextFieldFaxNr(presentationModel);
	}

	public final String getDialogName() {
		return "EditExternalOrderView";
	}

	public final String getHeading() {
		return "Ordre";
	}
}
