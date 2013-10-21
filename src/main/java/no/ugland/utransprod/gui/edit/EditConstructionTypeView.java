package no.ugland.utransprod.gui.edit;

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.ConstructionTypeViewHandler;
import no.ugland.utransprod.gui.model.ConstructionTypeModel;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.validators.ConstructionTypeValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXTreeTable;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klase som håndterer visning og editering av garasjetype
 * 
 * @author atle.brekka
 * 
 */
public class EditConstructionTypeView extends
		AbstractEditView<ConstructionTypeModel, ConstructionType> {
	private JTextField textFieldName;
	private JTextField textFieldDescription;
	private JList listAttributes;
	private JCheckBox checkBoxAttributes;
	private JCheckBox checkBoxArticles;
	private JXCollapsiblePane collapsiblePaneAttribute;
	private JXCollapsiblePane collapsiblePaneArticle;
	private JButton buttonAddAttribute;
	private JButton buttonRemoveAttribute;
	private JButton buttonEditAttribute;
	private JButton buttonAddArticle;
	private JButton buttonRemoveArticle;
	private JButton buttonEditArticle;
	private JXTreeTable treeTableArticles;
	private JButton buttonCopyMaster;
	private boolean newObject = false;
	private JComboBox comboBoxSketch;
	private JComboBox comboBoxProductArea;

	public EditConstructionTypeView(ConstructionTypeViewHandler handler,
			ConstructionType constructionType, boolean searchDialog) {
		super(searchDialog, new ConstructionTypeModel(constructionType),
				handler);
		if (constructionType.getConstructionTypeId() == null) {
			newObject = true;
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#buildEditPanel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected JComponent buildEditPanel(WindowInterface window) {
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,80dlu,3dlu,170dlu,3dlu,p,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,top:100dlu:grow,3dlu,p");

		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldName, cc.xy(4, 2));
		builder.addLabel("Beskrivelse:", cc.xy(2, 4));
		builder.add(textFieldDescription, cc.xyw(4, 4, 1));
		builder.addLabel("Skisse:", cc.xy(2, 6));
		builder.add(comboBoxSketch, cc.xyw(4, 6, 1));
		// builder.add(checkBoxVilla, cc.xyw(4, 8, 1));
		builder.addLabel("Produktområde:", cc.xy(2, 8));
		builder.add(comboBoxProductArea, cc.xyw(4, 8, 1));
		if (!((ConstructionTypeViewHandler) viewHandler).isMaster()) {
			builder.add(buttonCopyMaster, cc.xyw(4, 10, 1));
		}

		builder.add(checkBoxAttributes, cc.xy(6, 2));
		builder.add(buildAttributePanel(), cc.xywh(6, 4, 1, 5));
		builder.add(checkBoxArticles, cc.xy(8, 2));
		builder.add(buildArticlePanel(), cc.xywh(8, 4, 1, 5));
		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 10, 8));
		builder.appendRow(new RowSpec("5dlu"));
		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * Bygger panel for ttributter
	 * 
	 * @return panel
	 */
	private JPanel buildAttributePanel() {
		FormLayout layout = new FormLayout("100dlu,3dlu,p", "120dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();

		builder.add(new JScrollPane(listAttributes), cc.xy(1, 1));
		builder.add(buildAttributeButtons(), cc.xy(3, 1));
		collapsiblePaneAttribute.add(builder.getPanel(), BorderLayout.CENTER);
		return collapsiblePaneAttribute;
	}

	/**
	 * Bygger panel for knapper tilhørende attributtpanel
	 * 
	 * @return panel
	 */
	private JPanel buildAttributeButtons() {
		ButtonStackBuilder buttonBuilder = new ButtonStackBuilder();
		buttonBuilder.addGridded(buttonAddAttribute);
		buttonBuilder.addRelatedGap();
		buttonBuilder.addGridded(buttonEditAttribute);
		buttonBuilder.addRelatedGap();
		buttonBuilder.addGridded(buttonRemoveAttribute);

		return buttonBuilder.getPanel();
	}

	/**
	 * Bygger panel for artikler
	 * 
	 * @return panel
	 */
	private JPanel buildArticlePanel() {

		FormLayout layout = new FormLayout("170dlu,3dlu,p", "120dlu:grow");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		CellConstraints cc = new CellConstraints();
		builder.add(new JScrollPane(treeTableArticles), cc.xy(1, 1));
		builder.add(buildArticleButtons(), cc.xy(3, 1));
		collapsiblePaneArticle.add(builder.getPanel(), BorderLayout.CENTER);
		return collapsiblePaneArticle;
	}

	/**
	 * Bygger panel for knapper tilhørende artikkelpanel
	 * 
	 * @return pnel
	 */
	private JPanel buildArticleButtons() {
		ButtonStackBuilder buttonBuilder = new ButtonStackBuilder();
		buttonBuilder.addGridded(buttonAddArticle);
		buttonBuilder.addRelatedGap();
		buttonBuilder.addGridded(buttonEditArticle);
		buttonBuilder.addRelatedGap();
		buttonBuilder.addGridded(buttonRemoveArticle);

		return buttonBuilder.getPanel();
	}

	/**
	 * @param object
	 * @return validator
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
	 */
	@Override
	protected Validator getValidator(ConstructionTypeModel object,
			boolean search) {
		return new ConstructionTypeValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName,
				"Garasjetype.navn");

		ValidationComponentUtils.setMandatory(comboBoxProductArea, true);
		ValidationComponentUtils.setMessageKey(comboBoxProductArea,
				"Garasjetype.produktområde");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface window1) {
		textFieldName = ((ConstructionTypeViewHandler) viewHandler)
				.getTextFieldName(presentationModel);
		textFieldDescription = ((ConstructionTypeViewHandler) viewHandler)
				.getTextFieldDescription(presentationModel);

		((ConstructionTypeViewHandler) viewHandler)
				.initPresentationModel(presentationModel);
		listAttributes = BasicComponentFactory
				.createList(((ConstructionTypeViewHandler) viewHandler)
						.getAttributeSelectionWithComparator());
		listAttributes.setName("ListAttributes");
		listAttributes
				.addMouseListener(((ConstructionTypeViewHandler) viewHandler)
						.getAttributeDoubleClickHandler(window1,
								presentationModel));

		collapsiblePaneAttribute = new JXCollapsiblePane(new BorderLayout());
		Action toggleAttributeAction = collapsiblePaneAttribute.getActionMap()
				.get(JXCollapsiblePane.TOGGLE_ACTION);

		collapsiblePaneArticle = new JXCollapsiblePane(new BorderLayout());
		Action toggleArticleAction = collapsiblePaneArticle.getActionMap().get(
				JXCollapsiblePane.TOGGLE_ACTION);

		checkBoxAttributes = new JCheckBox(toggleAttributeAction);
		checkBoxAttributes.setText("Attributter");
		checkBoxAttributes.setSelected(true);

		checkBoxArticles = new JCheckBox(toggleArticleAction);
		checkBoxArticles.setText("Artikler");
		checkBoxArticles.setSelected(true);

		buttonAddAttribute = ((ConstructionTypeViewHandler) viewHandler)
				.getAddAttributeButton(window1, presentationModel);
		buttonRemoveAttribute = ((ConstructionTypeViewHandler) viewHandler)
				.getRemoveAttributeButton(window1, presentationModel);
		buttonEditAttribute = ((ConstructionTypeViewHandler) viewHandler)
				.getEditAttributeButton(window1, presentationModel);

		buttonAddArticle = ((ConstructionTypeViewHandler) viewHandler)
				.getAddArticleButton(window1, presentationModel);
		buttonRemoveArticle = ((ConstructionTypeViewHandler) viewHandler)
				.getRemoveArticleButton(window1, presentationModel);
		buttonEditArticle = ((ConstructionTypeViewHandler) viewHandler)
				.getEditArticleButton(window1, presentationModel);

		buttonCopyMaster = ((ConstructionTypeViewHandler) viewHandler)
				.getCopyMasterButton(presentationModel, window1);
		if (!newObject) {
			buttonCopyMaster.setEnabled(false);
		}

		treeTableArticles = ((ConstructionTypeViewHandler) viewHandler)
				.getTreeTable(presentationModel);
		treeTableArticles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		treeTableArticles.setColumnControlVisible(true);
		treeTableArticles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		treeTableArticles
				.addMouseListener(((ConstructionTypeViewHandler) viewHandler)
						.getArticleDoubleClickHandler(window1,
								presentationModel));

		comboBoxSketch = ((ConstructionTypeViewHandler) viewHandler)
				.getComboBoxSketch(presentationModel);
		// checkBoxVilla = ((ConstructionTypeViewHandler)
		// viewHandler).getCheckBoxVilla(presentationModel);
		comboBoxProductArea = ((ConstructionTypeViewHandler) viewHandler)
				.getComboBoxProductArea(presentationModel);

		initColumnWidth();
		initEventHandling();
	}

	/**
	 * Initierer kolonnebredder for artikkeltabell
	 */
	private void initColumnWidth() {
		// Artikkel
		TableColumn col = treeTableArticles.getColumnModel().getColumn(0);
		col.setPreferredWidth(150);

		// Verdi
		col = treeTableArticles.getColumnModel().getColumn(1);
		col.setPreferredWidth(50);

		// Rekkefølge
		col = treeTableArticles.getColumnModel().getColumn(2);
		col.setPreferredWidth(80);
	}

	/**
	 * Initierer hendelsehåndtering
	 */
	private void initEventHandling() {
		((ConstructionTypeViewHandler) viewHandler).getAttributeSelection()
				.addPropertyChangeListener(
						SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
						((ConstructionTypeViewHandler) viewHandler)
								.getEmptySelectionHandler());

		treeTableArticles.getSelectionModel().addListSelectionListener(
				((ConstructionTypeViewHandler) viewHandler)
						.getArticleSelectionListener());
	}

	public final String getDialogName() {
		return "EditConstructionTypeView";
	}

	public final String getHeading() {
		return "Konstruksjonstype";
	}
}
