package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ColliViewHandler;
import no.ugland.utransprod.gui.model.ColliModel;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.validators.ColliValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer editering av kolli
 * 
 * @author atle.brekka
 * 
 */
public class EditColliView extends AbstractEditView<ColliModel, Colli> {
    private JTextField textFieldColliName;
    private JTextField textFieldNumberOfCollies;
    private JTextField textFieldHeight;
    private JTextField textFieldLenght;
    private JTextField textFieldWidht;

    public EditColliView(boolean searchDialog, ColliModel colliModel, ColliViewHandler colliViewHandler) {
	super(searchDialog, colliModel, colliViewHandler);
    }

    @Override
    protected JComponent buildEditPanel(WindowInterface window) {
	FormLayout layout = new FormLayout("10dlu,p,3dlu,100dlu,10dlu", "10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p");
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Navn:", cc.xy(2, 2));
	builder.add(textFieldColliName, cc.xy(4, 2));
	builder.addLabel("Antall:", cc.xy(2, 4));
	builder.add(textFieldNumberOfCollies, cc.xy(4, 4));
	builder.addLabel("Lengde:", cc.xy(2, 6));
	builder.add(textFieldLenght, cc.xy(4, 6));
	builder.addLabel("Bredde:", cc.xy(2, 8));
	builder.add(textFieldWidht, cc.xy(4, 8));
	builder.addLabel("Høyde:", cc.xy(2, 10));
	builder.add(textFieldHeight, cc.xy(4, 10));
	builder.add(ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel), cc.xyw(2, 12, 4));

	return new IconFeedbackPanel(validationResultModel, builder.getPanel());
    }

    @Override
    protected Validator getValidator(ColliModel object, boolean search) {
	return new ColliValidator(object);
    }

    @Override
    protected void initComponentAnnotations() {
	ValidationComponentUtils.setMandatory(textFieldNumberOfCollies, true);
	ValidationComponentUtils.setMessageKey(textFieldNumberOfCollies, "Kolli.antall");
    }

    @Override
    protected void initEditComponents(WindowInterface window1) {
	textFieldColliName = ((ColliViewHandler) viewHandler).getTextFieldColliName(presentationModel);
	textFieldNumberOfCollies = ((ColliViewHandler) viewHandler).getTextFieldNumberOfCollies(presentationModel);
	textFieldHeight = ((ColliViewHandler) viewHandler).getTextFieldHeight(presentationModel);
	textFieldLenght = ((ColliViewHandler) viewHandler).getTextFieldLenght(presentationModel);
	textFieldWidht = ((ColliViewHandler) viewHandler).getTextFieldWidht(presentationModel);

    }

    public final String getDialogName() {
	return "EditColliView";
    }

    public final String getHeading() {
	return "Kolli";
    }
}
