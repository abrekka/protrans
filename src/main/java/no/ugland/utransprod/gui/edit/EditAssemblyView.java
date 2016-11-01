package no.ugland.utransprod.gui.edit;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.SupplierOrderViewHandler;
import no.ugland.utransprod.gui.model.AssemblyModel;
import no.ugland.utransprod.model.Assembly;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.toedter.calendar.JDateChooser;

/**
 * Klasse som håndterer editering av monteringsinfo
 * 
 * @author atle.brekka
 */
public class EditAssemblyView extends AbstractEditView<AssemblyModel, Assembly> {
	private JComboBox comboBoxPlanned;

	private JCheckBox checkBoxAssemblied;

	private JList listAreaComment;

	private JDateChooser dateChooser;

	private JButton buttonAddComment;
	private JButton buttonSaveNy;

	/**
	 * @param searchDialog
	 * @param assemblyModel
	 * @param aViewHandler
	 */
	public EditAssemblyView(final boolean searchDialog, final AssemblyModel assemblyModel,
			final SupplierOrderViewHandler aViewHandler) {
		super(searchDialog, assemblyModel, aViewHandler);

	}

	@Override
	protected final JComponent buildEditPanel(final WindowInterface window) {
		FormLayout layout = new FormLayout("10dlu,p,3dlu,p,3dlu,p,3dlu,55dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,50dlu,3dlu,p,5dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Planlagt", cc.xy(2, 2));
		builder.add(comboBoxPlanned, cc.xy(4, 2));
		builder.add(checkBoxAssemblied, cc.xy(6, 2));
		builder.add(dateChooser, cc.xy(8, 2));
		builder.addLabel("Kommentarer:", cc.xy(2, 4));
		builder.add(new JScrollPane(listAreaComment), cc.xywh(2, 6, 3, 3));
		builder.add(buildCommentButtons(), cc.xyw(6, 6, 3));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonSaveNy, buttonCancel), cc.xyw(2, 10, 7));
		return builder.getPanel();
	}

	private JPanel buildCommentButtons() {
		ButtonStackBuilder builder = new ButtonStackBuilder();
		builder.addGridded(buttonAddComment);
		return builder.getPanel();
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected final Validator getValidator(final AssemblyModel object, boolean search) {
		return null;
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
	}

	@Override
	protected final void initEditComponents(final WindowInterface window1) {
		comboBoxPlanned = ((SupplierOrderViewHandler) viewHandler).getComboBoxPlanned(presentationModel);
		checkBoxAssemblied = ((SupplierOrderViewHandler) viewHandler).getCheckBoxAssemblied(presentationModel);
		listAreaComment = ((SupplierOrderViewHandler) viewHandler).getListAreaComment(presentationModel);
		dateChooser = ((SupplierOrderViewHandler) viewHandler).getDateChooser(presentationModel);
		buttonAddComment = ((SupplierOrderViewHandler) viewHandler).getButtonAddComment(presentationModel);
		buttonSaveNy=((SupplierOrderViewHandler) viewHandler).getButtonSaveAssembly(presentationModel);
	}

	public final String getDialogName() {
		return "EditAssemblyView";
	}

	public final String getHeading() {
		return "Montering";
	}
}
