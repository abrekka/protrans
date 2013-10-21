package no.ugland.utransprod.gui.edit;

import java.awt.BorderLayout;

import javax.swing.JComboBox;
import javax.swing.JComponent;

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
 * Editering av avviksansvarlig
 * 
 * @author atle.brekka
 * 
 */
public class EditManagerView extends
		AbstractEditView<ApplicationParamModel, ApplicationParam> implements
		Viewer {
	private JComboBox comboBoxManager;
	private String managerText;
	private String property;
	private String heading;

	/**
	 * @param searchDialog
	 * @param object
	 * @param aViewHandler
	 */
	public EditManagerView(boolean searchDialog, ApplicationParam object,
			ApplicationParamViewHandler aViewHandler, String aManagerText,
			String aProperty, String aHeading) {
		super(searchDialog, new ApplicationParamModel(object), aViewHandler);
		heading = aHeading;
		property = aProperty;
		managerText = aManagerText;
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,100dlu,10dlu",
				"10dlu,p,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel(managerText, cc.xy(2, 2));
		builder.add(comboBoxManager, cc.xy(4, 2));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 4, 3));

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
		comboBoxManager = ((ApplicationParamViewHandler) viewHandler)
				.getComboBoxManager(presentationModel, property);

	}

	public final String getDialogName() {
		return "EditManagerView";
	}

	public final String getHeading() {
		return heading;
	}

	public WindowInterface buildWindow() {

		WindowInterface window = InternalFrameBuilder.buildInternalFrame(
				heading, ((ApplicationParamViewHandler) viewHandler)
						.getDeviationManagerWindowSize(), false);
		window.add(buildPanel(window), BorderLayout.CENTER);

		window.pack();
		return window;
	}

	public void cleanUp() {
	}

	public String getTitle() {
		return heading;
	}

	public void initWindow() {
	}

	public boolean useDispose() {
		return false;
	}
}
