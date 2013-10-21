package no.ugland.utransprod.gui.edit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.IconFeedbackPanel;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.CommentViewHandler;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.ICommentModel;
import no.ugland.utransprod.gui.model.OrderCommentModel;
import no.ugland.utransprod.model.IComment;
import no.ugland.utransprod.model.validators.CommentValidator;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Dialog for å legge til kommentar
 * 
 * @author atle.brekka
 */
public class EditCommentView extends AbstractEditView<ICommentModel, IComment> {

	public EditCommentView(final AbstractModel<IComment, ICommentModel> object,
			final AbstractViewHandler<IComment, ICommentModel> aViewHandler) {
		super(false, object, aViewHandler);
	}

	private JTextField textFieldUserName;

	private JTextArea textAreaComment;

	private JButton buttonCommentOk;

	private JButton buttonCommentCancel;

	private JCheckBox checkBoxTransport;

	private JCheckBox checkBoxPackage;

	public final String getDialogName() {
		return "EditCommentView";
	}

	public final String getHeading() {
		return "Kommentar";
	}

	@Override
	protected final JComponent buildEditPanel(final WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,150dlu:grow,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,fill:100dlu:grow,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.addLabel("Bruker:", cc.xy(2, 2));
		builder.add(textFieldUserName, cc.xy(4, 2));
		if (checkBoxTransport != null) {
			builder.add(checkBoxTransport, cc.xy(2, 6));
		}
		if (checkBoxPackage != null) {
			builder.add(checkBoxPackage, cc.xy(4, 6));
		}
		builder.addLabel("Kommentar:", cc.xy(2, 8));
		builder.add(new JScrollPane(textAreaComment), cc.xyw(2, 10, 3));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonCommentOk,
				buttonCommentCancel), cc.xyw(2, 12, 3));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	@Override
	protected final Validator getValidator(final ICommentModel object,
			boolean search) {
		return new CommentValidator(object);
	}

	@Override
	protected final void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textAreaComment, true);
		ValidationComponentUtils.setMessageKey(textAreaComment,
				"Kommentar.kommentar");

	}

	@Override
	protected final void initEditComponents(final WindowInterface aWindow) {
		textFieldUserName = ((CommentViewHandler) viewHandler)
				.getTextFieldUserName(presentationModel);
		textAreaComment = ((CommentViewHandler) viewHandler)
				.getTextAreaComment(presentationModel);
		buttonCommentCancel = ((CommentViewHandler) viewHandler)
				.getButtonCancel(aWindow);
		buttonCommentOk = ((CommentViewHandler) viewHandler).getButtonOk(
				aWindow, validationResultModel);
		if (currentObject instanceof OrderCommentModel) {
			checkBoxTransport = ((CommentViewHandler) viewHandler)
					.getCheckBoxTransport(presentationModel);
			checkBoxPackage = ((CommentViewHandler) viewHandler)
					.getCheckBoxPackage(presentationModel);
		}

	}
}
