package no.ugland.utransprod.gui.edit;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.Viewer;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ApplicationParamViewHandler;
import no.ugland.utransprod.gui.model.ApplicationParamModel;
import no.ugland.utransprod.model.ApplicationParam;
import no.ugland.utransprod.util.InternalFrameBuilder;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;

public class EditColliSetupView extends
		AbstractEditView<ApplicationParamModel, ApplicationParam> implements
		Viewer {
	private JList listCollies;
	private JButton buttonAddColli;
	private JButton buttonRemoveColli;

	public EditColliSetupView(ApplicationParamViewHandler viewHandler) {
		super(false, new ApplicationParamModel(new ApplicationParam()),
				viewHandler);
	}

	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,p,10dlu",
				"10dlu,p,3dlu,fill:p:grow,3dlu,top:p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Kollier:", cc.xy(2, 2));
		builder.add(new JScrollPane(listCollies), cc.xy(2, 4));
		builder.add(buildButtonPanel(), cc.xy(4, 4));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 6, 3));

		return builder.getPanel();
	}

	private JPanel buildButtonPanel() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonAddColli);
		builder.addRelatedGap();
		builder.addGridded(buttonRemoveColli);
		return builder.getPanel();
	}

	@Override
	protected Validator getValidator(ApplicationParamModel object,
			boolean search) {
		return null;
	}

	@Override
	protected void initComponentAnnotations() {

	}

	@Override
	protected void initEditComponents(WindowInterface window) {
		listCollies = ((ApplicationParamViewHandler) viewHandler)
				.getListCollies(presentationModel);
		buttonAddColli = ((ApplicationParamViewHandler) viewHandler)
				.getButtonAddColli(window, presentationModel);
		buttonRemoveColli = ((ApplicationParamViewHandler) viewHandler)
				.getButtonRemoveColli(window, presentationModel);
	}

	public String getDialogName() {
		return "EditColliSetupView";
	}

	public String getHeading() {
		return "Kollier";
	}

	public WindowInterface buildWindow() {
		WindowInterface window = InternalFrameBuilder.buildInternalFrame(
				getTitle(), viewHandler.getWindowSize(), false);
		window.add(buildPanel(window), BorderLayout.CENTER);

		return window;
	}

	public void cleanUp() {
	}

	public String getTitle() {
		return "Kollioppsett";
	}

	public void initWindow() {

	}

	public boolean useDispose() {
		// TODO Auto-generated method stub
		return false;
	}

}
