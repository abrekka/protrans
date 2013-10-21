package no.ugland.utransprod.gui.edit;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.TransportViewHandler;
import no.ugland.utransprod.gui.model.TransportModel;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.model.validators.TransportValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

/**
 * Klasse som håndterer visning og editering av transport
 * 
 * @author atle.brekka
 * 
 */
public class EditTransportView extends
		AbstractEditView<TransportModel, Transport> {
	/**
	 * 
	 */
	private JYearChooser yearChooser;

	/**
	 * 
	 */
	private JComboBox comboBoxWeek;

	/**
	 * 
	 */
	private JDateChooser dateChooser;

	/**
	 * 
	 */
	private JTextField textFieldName;
	private JTextArea textAreaTransportComment;

	/**
	 * @param handler
	 * @param transport
	 * @param searchDialog
	 */
	public EditTransportView(TransportViewHandler handler, Transport transport,
			boolean searchDialog) {
		super(searchDialog, new TransportModel(transport), handler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		initEditEventHandling();
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,30dlu,3dlu,50dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,fill:30dlu,3dlu,p");

		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldName, cc.xyw(4, 2, 3));
		builder.addLabel("År:", cc.xy(2, 4));
		builder.add(yearChooser, cc.xy(4, 4));
		builder.addLabel("Uke:", cc.xy(2, 6));
		builder.add(comboBoxWeek, cc.xy(4, 6));
		builder.addLabel("Opplasting:", cc.xy(2, 8));
		builder.add(dateChooser, cc.xyw(4, 8, 3));

		builder.addLabel("Kommentar:", cc.xy(2, 10));
		builder.add(new JScrollPane(textAreaTransportComment),
				cc.xywh(4, 10, 3, 3));

		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 14, 5));

		builder.appendRow(new RowSpec("5dlu"));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(TransportModel object, boolean search) {
		return new TransportValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(yearChooser, true);
		ValidationComponentUtils.setMessageKey(yearChooser, "Transport.år");

		ValidationComponentUtils.setMandatory(comboBoxWeek, true);
		ValidationComponentUtils.setMessageKey(comboBoxWeek, "Transport.uke");

		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName, "Transport.navn");

	}

	/**
	 * Initierer hendelsehåndtering
	 */
	private void initEditEventHandling() {
		PropertyConnector connYear = new PropertyConnector(
				yearChooser,
				"year",
				presentationModel
						.getBufferedModel(TransportModel.PROPERTY_TRANSPORT_YEAR),
				"value");

		if (presentationModel
				.getBufferedValue(TransportModel.PROPERTY_TRANSPORT_WEEK) == null) {
			connYear.updateProperty2();
		} else {
			connYear.updateProperty1();
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface window1) {
		yearChooser = new JYearChooser();

		comboBoxWeek = new JComboBox(
				new ComboBoxAdapter(
						Util.getWeeks(),
						presentationModel
								.getBufferedModel(TransportModel.PROPERTY_TRANSPORT_WEEK)));

		dateChooser = new JDateChooser();
		PropertyConnector connDate = new PropertyConnector(
				dateChooser,
				"date",
				presentationModel
						.getBufferedModel(TransportModel.PROPERTY_LOADING_DATE),
				"value");

		if (presentationModel
				.getBufferedValue(TransportModel.PROPERTY_LOADING_DATE) != null) {
			connDate.updateProperty1();
		}

		textFieldName = BasicComponentFactory.createTextField(presentationModel
				.getBufferedModel(TransportModel.PROPERTY_TRANSPORT_NAME));

		textAreaTransportComment = BasicComponentFactory
				.createTextArea(presentationModel
						.getBufferedModel(TransportModel.PROPERTY_TRANSPORT_COMMENT));
		textAreaTransportComment.setName("TextAreaComment");

	}

	public final String getDialogName() {
		return "EditTransportView";
	}

	public final String getHeading() {
		return "Transport";
	}
}
