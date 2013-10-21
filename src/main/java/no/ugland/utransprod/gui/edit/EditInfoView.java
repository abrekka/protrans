package no.ugland.utransprod.gui.edit;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.InfoViewHandler;
import no.ugland.utransprod.gui.model.InfoModel;
import no.ugland.utransprod.model.Info;
import no.ugland.utransprod.model.validators.InfoValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.toedter.calendar.JDateChooser;

/**
 * Visning av info
 * 
 * @author atle.brekka
 * 
 */
public class EditInfoView extends AbstractEditView<InfoModel, Info> {
	/**
	 * 
	 */
	private JTextArea textAreaInfo;

	/**
	 * 
	 */
	private JDateChooser dateChooserFrom;

	/**
	 * 
	 */
	private JDateChooser dateChooserTo;

	/**
	 * @param searchDialog
	 * @param info
	 * @param aViewHandler
	 */
	public EditInfoView(boolean searchDialog, Info info,
			AbstractViewHandler<Info, InfoModel> aViewHandler) {
		super(searchDialog, new InfoModel(info), aViewHandler);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,fill:100dlu,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Fra dato:", cc.xy(2, 2));
		builder.add(dateChooserFrom, cc.xy(4, 2));
		builder.addLabel("Til dato:", cc.xy(6, 2));
		builder.add(dateChooserTo, cc.xy(8, 2));
		builder.addLabel("Informasjon:", cc.xy(2, 4));
		builder.add(new JScrollPane(textAreaInfo), cc.xyw(2, 6, 7));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 8, 7));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(InfoModel object, boolean search) {
		return new InfoValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(dateChooserFrom, true);
		ValidationComponentUtils.setMessageKey(dateChooserFrom,
				"Informasjon.fra dato");

		ValidationComponentUtils.setMandatory(dateChooserTo, true);
		ValidationComponentUtils.setMessageKey(dateChooserTo,
				"Informasjon.til dato");

		ValidationComponentUtils.setMandatory(textAreaInfo, true);
		ValidationComponentUtils.setMessageKey(textAreaInfo,
				"Informasjon.informasjonstekst");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface aWindow) {
		textAreaInfo = ((InfoViewHandler) viewHandler)
				.getTextAreaInfo(presentationModel);
		dateChooserFrom = ((InfoViewHandler) viewHandler)
				.getDateChooserFrom(presentationModel);
		dateChooserTo = ((InfoViewHandler) viewHandler)
				.getDateChooserTo(presentationModel);

	}

	public final String getDialogName() {
		return "EditInfoView";
	}

	public final String getHeading() {
		return "Info";
	}
}
