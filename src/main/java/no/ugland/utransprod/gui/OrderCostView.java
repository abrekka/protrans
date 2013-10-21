package no.ugland.utransprod.gui;

import java.awt.Component;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.handlers.OrderCostViewHandler;
import no.ugland.utransprod.gui.model.OrderCostModel;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.validators.OrderCostValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer visning og editering av kostnad for ordre
 * 
 * @author atle.brekka
 * 
 */
public class OrderCostView {
	/**
	 * 
	 */
	private JButton buttonOk;

	/**
	 * 
	 */
	private JButton buttonCancel;

	/**
	 * 
	 */
	private OrderCostViewHandler viewHandler;

	/**
	 * 
	 */
	private JComboBox comboBoxCostType;

	/**
	 * 
	 */
	private JComboBox comboBoxCostUnit;

	/**
	 * 
	 */
	private JTextField textFieldAmount;

	/**
	 * 
	 */
	private JComboBox comboBoxSupplier;

	/**
	 * 
	 */
	private JTextField textFieldInvoiceNr;

	/**
	 * 
	 */
	private JComboBox comboBoxVat;

	/**
	 * 
	 */
	private PresentationModel presentationModel;

	/**
	 * 
	 */
	private ValidationResultModel validationResultModel;

	/**
	 * 
	 */
	private JButton buttonAddSupplier;

	/**
	 * @param handler
	 * @param aOrderCost
	 */
	public OrderCostView(OrderCostViewHandler handler, OrderCost aOrderCost) {
		viewHandler = handler;
		if (aOrderCost != null) {
			presentationModel = new PresentationModel(new OrderCostModel(
					aOrderCost));
		} else {
			presentationModel = new PresentationModel(new OrderCostModel(
					new OrderCost()));
		}
	}

	/**
	 * Initierer komponenter
	 * 
	 * @param window
	 */
	private void initComponents(WindowInterface window) {
		window.setName("OrderCostView");
		comboBoxCostType = viewHandler.getComboBoxCostType(presentationModel);
		comboBoxCostUnit = viewHandler.getComboBoxCostUnit(presentationModel);
		textFieldAmount = viewHandler.getTextFieldAmount(presentationModel);
		comboBoxVat = viewHandler.getComboBoxVat(presentationModel);
		buttonOk = viewHandler.getOkButton(window);
		buttonCancel = viewHandler.getCancelButton(window);

		comboBoxSupplier = viewHandler.getComboBoxSupplier(presentationModel);

		textFieldInvoiceNr = viewHandler
				.getTextFieldInvoiceNr(presentationModel);

		buttonAddSupplier = viewHandler.getAddSupplierButton(presentationModel,window);

		validationResultModel = viewHandler.getValidationResultModel();

	}

	/**
	 * Bygger panel
	 * 
	 * @param window
	 * @return panel
	 */
	public Component buildPanel(WindowInterface window) {
		window.setName("OrderCostView");
		initComponents(window);
		initComponentAnnotations();
		initEventHandling();
		FormLayout layout = new FormLayout("10dlu,p,3dlu,100dlu,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,,3dlu,p,3dlu,p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Kostnad:", cc.xy(2, 2));
		builder.add(comboBoxCostType, cc.xy(4, 2));
		builder.addLabel("Kostnadsenhet:", cc.xy(2, 4));
		builder.add(comboBoxCostUnit, cc.xy(4, 4));
		builder.addLabel("Beløp:", cc.xy(2, 6));
		builder.add(textFieldAmount, cc.xy(4, 6));
		builder.addLabel("Inkl. mva:", cc.xy(2, 8));
		builder.add(comboBoxVat, cc.xy(4, 8));
		builder.addLabel("Leverandør:", cc.xy(2, 10));
		builder.add(comboBoxSupplier, cc.xy(4, 10));
		builder.add(buttonAddSupplier, cc.xy(6, 10));
		builder.addLabel("Fakturanr:", cc.xy(2, 12));
		builder.add(textFieldInvoiceNr, cc.xy(4, 12));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk, buttonCancel),
				cc.xyw(2, 14, 4));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * Initierer feilrapportering for komponenter
	 */
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldAmount, true);
		ValidationComponentUtils
				.setMessageKey(textFieldAmount, "Kostnad.beløp");
        
        ValidationComponentUtils.setMandatory(comboBoxCostUnit, true);
        ValidationComponentUtils
                .setMessageKey(comboBoxCostUnit, "Kostnad.enhet");

		ValidationComponentUtils.setMandatory(comboBoxCostType, true);
		ValidationComponentUtils
				.setMessageKey(comboBoxCostType, "Kostnad.type");

	}

	/**
	 * Oppdaterer feilrapportering i vindu
	 */
	void updateValidationResult() {
		ValidationResult result = new OrderCostValidator(
				(OrderCostModel) presentationModel.getBean()).validate();
		validationResultModel.setResult(result);

	}

	/**
	 * Initierer hendelsehåndtering
	 */
	private void initEventHandling() {
		PropertyChangeListener handler = new ValidationUpdateHandler();
		presentationModel.addBeanPropertyChangeListener(handler);
		updateValidationResult();
	}

	/**
	 * Klasse som håndterer oppdatering av feilrapportering
	 * 
	 * @author atle.brekka
	 * 
	 */
	final class ValidationUpdateHandler implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			updateValidationResult();
		}

	}

	/**
	 * Henter kostnad som vises i vindu
	 * 
	 * @return kostnad
	 */
	public OrderCost getOrderCost() {
		OrderCostModel orderCostModel = viewHandler
				.getOrderCostModel(presentationModel);
		if (orderCostModel != null) {
			return orderCostModel.getObject();
		}
		return null;
	}
}
