package no.ugland.utransprod.gui.edit;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.Viewer;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ApplicationParamViewHandler;
import no.ugland.utransprod.gui.model.ApplicationParamModel;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.util.InternalFrameBuilder;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;

/**
 * Editering av mailoppsett
 * 
 * @author atle.brekka
 * 
 */
public class EditMailOptionsView extends
		AbstractEditView<ApplicationParamModel, ApplicationParam> implements
		Viewer {
	private JTextField textFieldDeviationSubject;
	private JTextField textFieldDeviationMessage;

	public EditMailOptionsView(boolean searchDialog, ApplicationParam object,
			ApplicationParamViewHandler aViewHandler) {
		super(searchDialog, new ApplicationParamModel(object), aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,100dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Meldingsoverskrift:", cc.xy(2, 10));
		builder.add(textFieldDeviationSubject, cc.xy(4, 10));
		builder.addLabel("Melding:", cc.xy(2, 12));
		builder.add(textFieldDeviationMessage, cc.xy(4, 12));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 16, 3));

		return builder.getPanel();
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(ApplicationParamModel object,
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
		textFieldDeviationSubject = ((ApplicationParamViewHandler) viewHandler)
				.getTextFieldDeviationSubject(presentationModel);
		textFieldDeviationMessage = ((ApplicationParamViewHandler) viewHandler)
				.getTextFieldDeviationMessage(presentationModel);
	}

	public final String getDialogName() {
		return "EditMailOptionsView";
	}

	public final String getHeading() {
		return "Mail";
	}

	public WindowInterface buildWindow() {
		WindowInterface window = InternalFrameBuilder.buildInternalFrame(
				"Mailoppsett", ((ApplicationParamViewHandler) viewHandler)
						.getMailSetupWindowSize(), false);
		window.add(buildPanel(window), BorderLayout.CENTER);
		window.pack();
		return window;
	}

	public void cleanUp() {
		// TODO Auto-generated method stub

	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public void initWindow() {
		// TODO Auto-generated method stub

	}

	public boolean useDispose() {
		// TODO Auto-generated method stub
		return true;
	}
}
