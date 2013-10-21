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

public class EditTakstolSetupView extends
		AbstractEditView<ApplicationParamModel, ApplicationParam> implements
		Viewer {
	private JList listArticles;
	private JButton buttonAddArticle;
	private JButton buttonRemoveArticle;

	public EditTakstolSetupView(ApplicationParamViewHandler viewHandler) {
		super(false, new ApplicationParamModel(new ApplicationParam()),
				viewHandler);
	}

	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,100dlu,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,top:p,3dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Artikler:", cc.xy(2, 2));
		builder.add(new JScrollPane(listArticles), cc.xy(2, 4));
		builder.add(buildButtonPanel(), cc.xy(4, 4));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 6, 3));

		return builder.getPanel();
	}

	private JPanel buildButtonPanel() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonAddArticle);
		builder.addRelatedGap();
		builder.addGridded(buttonRemoveArticle);
		return builder.getPanel();
	}

	public String getHeading() {
		return "Takstolartikler";
	}

	public String getTitle() {
		return "Takstoloppsett";
	}

	@Override
	protected void initEditComponents(WindowInterface window) {
		listArticles = ((ApplicationParamViewHandler) viewHandler)
				.getListTakstolArticles(presentationModel);
		buttonAddArticle = ((ApplicationParamViewHandler) viewHandler)
				.getButtonAddTakstolArticle(window, presentationModel);
		buttonRemoveArticle = ((ApplicationParamViewHandler) viewHandler)
				.getButtonRemoveTakstolArticle(window, presentationModel);
	}

	public String getDialogName() {
		return "EditTaktolSetupView";
	}

	public boolean useDispose() {
		return true;
	}

	public WindowInterface buildWindow() {
		WindowInterface window = InternalFrameBuilder.buildInternalFrame(
				getTitle(), viewHandler.getWindowSize(), false);
		window.add(buildPanel(window), BorderLayout.CENTER);

		return window;
	}

	@Override
	protected void initComponentAnnotations() {
	}

	public void cleanUp() {
	}

	@Override
	protected Validator getValidator(ApplicationParamModel object,
			boolean search) {
		return null;
	}

	public void initWindow() {

	}
}
