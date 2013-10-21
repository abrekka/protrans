package no.ugland.utransprod.gui.edit;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ProcentDoneViewHandler;
import no.ugland.utransprod.gui.model.ProcentDoneModel;
import no.ugland.utransprod.model.ProcentDone;
import no.ugland.utransprod.model.validators.ProcentDoneValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.toedter.calendar.JYearChooser;

public class EditProcentDoneView extends
		AbstractEditView<ProcentDoneModel, ProcentDone> {
	public EditProcentDoneView(boolean searchDialog,
			ProcentDoneModel procentDoneModel,
			ProcentDoneViewHandler aViewHandler) {
		super(searchDialog, procentDoneModel, aViewHandler);
		// TODO Auto-generated constructor stub
	}

	private JYearChooser yearChooser;

	private JComboBox comboBoxWeek;

	private JTextField textFieldProcent;

	private JTextArea textAreaComment;

	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,30dlu,3dlu,p,3dlu,p,3dlu,p,3dlu,20dlu,10dlu",
				"10dlu,p,3dlu,fill:50dlu,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("År:", cc.xy(2, 2));
		builder.add(yearChooser, cc.xy(4, 2));
		builder.addLabel("Uke:", cc.xy(6, 2));
		builder.add(comboBoxWeek, cc.xy(8, 2));
		builder.addLabel("Prosent:", cc.xy(10, 2));
		builder.add(textFieldProcent, cc.xy(12, 2));
		builder.addLabel("Kommentar:", cc.xy(2, 4));
		builder.add(new JScrollPane(textAreaComment), cc.xyw(4, 4, 9));

		builder.add(ButtonBarFactory.buildCenteredBar(buttonOk, buttonCancel),
				cc.xyw(2, 6, 11));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());

	}

	@Override
	protected Validator getValidator(ProcentDoneModel object, boolean search) {
		return new ProcentDoneValidator(object);
	}

	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldProcent, true);
		ValidationComponentUtils.setMessageKey(textFieldProcent,
				"Prosent ferdig.prosent");

	}

	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		yearChooser = ((ProcentDoneViewHandler) viewHandler)
				.getYearChooser(presentationModel);
		comboBoxWeek = ((ProcentDoneViewHandler) viewHandler)
				.getComboBoxWeek(presentationModel);
		textFieldProcent = ((ProcentDoneViewHandler) viewHandler)
				.getTextFieldProcent(presentationModel);
		textAreaComment = ((ProcentDoneViewHandler) viewHandler)
				.getTextAreaComment(presentationModel);

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
