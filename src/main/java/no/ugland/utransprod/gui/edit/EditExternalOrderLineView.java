package no.ugland.utransprod.gui.edit;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.ExternalOrderLineViewHandler;
import no.ugland.utransprod.gui.model.ExternalOrderLineModel;
import no.ugland.utransprod.model.ExternalOrderLine;
import no.ugland.utransprod.util.IconFeedbackPanel;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;

/**
 * Klasse for visning og editering av ordrelinjer for ekstern ordre
 * 
 * @author atle.brekka
 * 
 */
public class EditExternalOrderLineView extends
		AbstractEditView<ExternalOrderLineModel, ExternalOrderLine> {
	/**
	 * 
	 */
	private JTextField textFieldName;

	/**
	 * 
	 */
	private JTextField textFieldNumberOfItems;

	/**
	 * 
	 */
	private JXTable tableExternalAttributes;

	/**
	 * 
	 */
	private JButton buttonEditAttribute;

	/**
	 * 
	 */
	private JButton buttonRemoveAttribute;

	/**
	 * 
	 */
	private JButton buttonAddAttribute;

	/**
	 * @param externalOrderLine
	 * @param aViewHandler
	 */
	public EditExternalOrderLineView(
			ExternalOrderLine externalOrderLine,
			AbstractViewHandler<ExternalOrderLine, ExternalOrderLineModel> aViewHandler) {
		super(false, new ExternalOrderLineModel(externalOrderLine),
				aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,80dlu,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,100dlu,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldName, cc.xy(4, 2));
		builder.addLabel("Antall:", cc.xy(2, 4));
		builder.add(textFieldNumberOfItems, cc.xy(4, 4));
		builder.addLabel("Attributter:", cc.xy(2, 6));
		builder.add(new JScrollPane(tableExternalAttributes), cc.xyw(2, 8, 3));
		builder.add(buildButtonPanel(), cc.xy(6, 8));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonCancel),
				cc.xyw(2, 10, 5));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * Bygger knappepanel
	 * 
	 * @return panel
	 */
	private JPanel buildButtonPanel() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonAddAttribute);
		builder.addGlue();
		builder.addGridded(buttonEditAttribute);
		builder.addGlue();
		builder.addGridded(buttonRemoveAttribute);
		return builder.getPanel();
	}

	/**
	 * @param object
	 * @return validato
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(ExternalOrderLineModel object,
			boolean search) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textFieldName = ((ExternalOrderLineViewHandler) viewHandler)
				.getTextFieldName(presentationModel);
		textFieldNumberOfItems = ((ExternalOrderLineViewHandler) viewHandler)
				.getTextFieldNumberOfItems(presentationModel);
		buttonCancel = ((ExternalOrderLineViewHandler) viewHandler)
				.getButtonCancel(aWindow);
		tableExternalAttributes = ((ExternalOrderLineViewHandler) viewHandler)
				.getTableExternalAttributes(presentationModel);
		buttonAddAttribute = ((ExternalOrderLineViewHandler) viewHandler)
				.getButtonAddAttribute(presentationModel, aWindow);
		buttonEditAttribute = ((ExternalOrderLineViewHandler) viewHandler)
				.getButtonEditAttribute(aWindow);
		buttonRemoveAttribute = ((ExternalOrderLineViewHandler) viewHandler)
				.getButtonRemoveAttribute();
	}

	public final String getDialogName() {
		return "EditExternalOrderLineView";
	}

	public final String getHeading() {
		return "Ordrelinje";
	}
}
