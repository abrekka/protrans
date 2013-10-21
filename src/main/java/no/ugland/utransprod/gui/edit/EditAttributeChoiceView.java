package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AttributeChoiceViewHandler;
import no.ugland.utransprod.gui.model.AttributeChoiceModel;
import no.ugland.utransprod.model.AttributeChoice;
import no.ugland.utransprod.model.validators.AttributeChoiceValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

public class EditAttributeChoiceView extends
		AbstractEditView<AttributeChoiceModel, AttributeChoice> {
	private JTextField textFieldChoice;
	private JTextField textFieldProdCatNo;
	private JTextField textFieldProdCatNo2;
	private JTextArea textAreaComment;

	public EditAttributeChoiceView(boolean searchDialog,
			AttributeChoice attributeChoice,
			AttributeChoiceViewHandler viewHandler) {
		super(searchDialog, new AttributeChoiceModel(attributeChoice),
				viewHandler);
	}

	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,70dlu,30dlu",
				"10,p,3dlu,p,3dlu,p,3dlu,fill:50dlu,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Valg:", cc.xy(2, 2));
		builder.add(textFieldChoice, cc.xy(4, 2));
		builder.addLabel("Produktkategori:", cc.xy(2, 4));
		builder.add(textFieldProdCatNo, cc.xy(4, 4));
		builder.addLabel("Produktkategori2:", cc.xy(2, 6));
		builder.add(textFieldProdCatNo2, cc.xy(4, 6));
		builder.addLabel("Kommentar:", cc.xy(2, 8));
		builder.add(new JScrollPane(textAreaComment), cc.xy(4, 8));

		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk, buttonCancel),
				cc.xyw(2, 10, 4));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	@Override
	protected Validator getValidator(AttributeChoiceModel object, boolean search) {
		return new AttributeChoiceValidator(object);
	}

	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldChoice, true);
		ValidationComponentUtils.setMessageKey(textFieldChoice, "Valg.valg");

	}

	@Override
	protected void initEditComponents(WindowInterface window) {
		textFieldChoice = ((AttributeChoiceViewHandler) viewHandler)
				.getTextFieldChoice(presentationModel);
		textFieldProdCatNo = ((AttributeChoiceViewHandler) viewHandler)
				.getTextFieldProdCatNo(presentationModel);
		textFieldProdCatNo2 = ((AttributeChoiceViewHandler) viewHandler)
				.getTextFieldProdCatNo2(presentationModel);
		textAreaComment = ((AttributeChoiceViewHandler) viewHandler)
				.getTextAreaComment(presentationModel);
		buttonOk = ((AttributeChoiceViewHandler) viewHandler).getButtonOk(
				validationResultModel, presentationModel, window);
	}

	public String getDialogName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getHeading() {
		// TODO Auto-generated method stub
		return null;
	}

}
