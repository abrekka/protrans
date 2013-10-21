package no.ugland.utransprod.gui.edit;

import java.awt.BorderLayout;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.TableColumn;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.ArticleTypeViewHandler;
import no.ugland.utransprod.gui.model.ArticleTypeModel;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.validators.ArticleTypeValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;

import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;

import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndtere visning og editering av artikkeltype
 * 
 * @author atle.brekka
 */
public class EditArticleTypeView extends
		AbstractEditView<ArticleTypeModel, ArticleType> {
	private JTextField textFieldName;

	private JTextField textFieldDescription;

	private JXTable tableAttributes;

	private JButton buttonAddAttribute;

	private JButton buttonRemoveAttribute;

	private ArticleTypeViewHandler articleTypeViewHandler;

	private JXTreeTable treeTableArticleTypeRef;

	private JXCollapsiblePane collapsiblePaneAttribute;

	private JCheckBox checkBoxAttributes;

	private JCheckBox checkBoxArticles;

	private JButton buttonAddArticle;

	private JButton buttonRemoveArticle;

	private JXCollapsiblePane collapsiblePaneArticle;

	private JCheckBox checkBoxTopLevel;

	private JLabel labelFormula;
	private JComboBox comboBoxMetric;
	private JTextField textFieldProdCatNo;
	private JTextField textFieldProdCatNo2;
	private JCheckBox checkBoxForceImport;
	private JButton buttonSetAttributeInactive;
	private JButton buttonSetAttributeActive;

	/**
	 * @param searchDialog
	 * @param articleType
	 * @param aViewHandler
	 */
	public EditArticleTypeView(
			final boolean searchDialog,
			final ArticleType articleType,
			final AbstractViewHandler<ArticleType, ArticleTypeModel> aViewHandler) {
		super(searchDialog, new ArticleTypeModel(articleType), aViewHandler);
		articleTypeViewHandler = (ArticleTypeViewHandler) aViewHandler;
	}

	@Override
	protected JComponent buildEditPanel(final WindowInterface window) {
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,50dlu,3dlu,60dlu,3dlu,p,3dlu,30dlu,3dlu,70dlu,10dlu",
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,100dlu,5dlu,p,5dlu");

		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.addLabel("Navn:", cc.xy(2, 2));
		builder.add(textFieldName, cc.xyw(4, 2, 3));
		builder.add(checkBoxTopLevel, cc.xy(8, 2));
		builder.addLabel("Produktkategorinr.:", cc.xy(8, 4));
		builder.add(textFieldProdCatNo, cc.xy(10, 4));
		builder.addLabel("Produktkategorinr2.:", cc.xy(8, 6));
		builder.add(textFieldProdCatNo2, cc.xy(10, 6));
		builder.add(checkBoxForceImport, cc.xy(12, 6));

		builder.addLabel("Beskrivelse:", cc.xy(2, 4));
		builder.add(textFieldDescription, cc.xyw(4, 4, 3));
		builder.addLabel("Betegnelse:", cc.xy(2, 6));
		builder.add(comboBoxMetric, cc.xy(4, 6));

		builder.add(checkBoxAttributes, cc.xy(2, 8));
		builder.add(labelFormula, cc.xy(4, 8));
		builder.add(buildAttributePanel(), cc.xywh(2, 10, 5, 1));
		builder.add(checkBoxArticles, cc.xy(8, 8));
		builder.add(buildArticlePanel(), cc.xywh(8, 10, 5, 1));

		builder.add(
				ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel),
				cc.xyw(2, 12, 11));

		return new IconFeedbackPanel(validationResultModel, builder.getPanel());
	}

	/**
	 * Bygger panel for attributter
	 * 
	 * @return panel
	 */
	private JPanel buildAttributePanel() {
		FormLayout layout = new FormLayout("100dlu,3dlu,p", "100dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(new JScrollPane(tableAttributes), cc.xy(1, 1));
		builder.add(buildAttributeButtons(), cc.xy(3, 1));
		collapsiblePaneAttribute.add(builder.getPanel(), BorderLayout.CENTER);
		return collapsiblePaneAttribute;
	}

	/**
	 * Bygger panel for attributtknapper
	 * 
	 * @return panel
	 */
	private JPanel buildAttributeButtons() {
		ButtonStackBuilder buttonBuilder = new ButtonStackBuilder();
		buttonBuilder.addGridded(buttonSetAttributeInactive);
		buttonBuilder.addRelatedGap();
		buttonBuilder.addGridded(buttonSetAttributeActive);
		buttonBuilder.addRelatedGap();
		buttonBuilder.addGridded(buttonAddAttribute);
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
		FormLayout layout = new FormLayout("100dlu,3dlu,p", "100dlu");
		PanelBuilder builder = new PanelBuilder(layout);
		CellConstraints cc = new CellConstraints();
		builder.add(new JScrollPane(treeTableArticleTypeRef), cc.xy(1, 1));
		builder.add(buildArticleButtons(), cc.xy(3, 1));
		collapsiblePaneArticle.add(builder.getPanel(), BorderLayout.CENTER);
		return collapsiblePaneArticle;
	}

	/**
	 * Bygger panel for artikkelknapper
	 * 
	 * @return panel
	 */
	private JPanel buildArticleButtons() {
		ButtonStackBuilder buttonBuilder = new ButtonStackBuilder();
		buttonBuilder.addGridded(buttonAddArticle);
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
	protected Validator getValidator(ArticleTypeModel object, boolean search) {
		return new ArticleTypeValidator(object);
	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
	 */
	@Override
	protected void initComponentAnnotations() {
		ValidationComponentUtils.setMandatory(textFieldName, true);
		ValidationComponentUtils.setMessageKey(textFieldName,
				"Artikkeltype.navn");

	}

	/**
	 * @see no.ugland.utransprod.gui.edit.AbstractEditView#initEditComponents(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void initEditComponents(WindowInterface window1) {
		textFieldName = ((ArticleTypeViewHandler) viewHandler)
				.getTextFieldName(presentationModel);

		textFieldDescription = ((ArticleTypeViewHandler) viewHandler)
				.getTextFieldDescription(presentationModel);

		checkBoxTopLevel = ((ArticleTypeViewHandler) viewHandler)
				.getCheckBoxTopLevel(presentationModel);

		labelFormula = ((ArticleTypeViewHandler) viewHandler).getLabelFormula();

		articleTypeViewHandler.initPresentationModel(presentationModel,
				ArticleTypeModel.PROPERTY_ARTICLE_TYPE_ATTRIBUTES);

		tableAttributes = articleTypeViewHandler.getTableAttributes();

		buttonAddAttribute = articleTypeViewHandler
				.getAddAttributeButton(window1);
		buttonRemoveAttribute = articleTypeViewHandler
				.getRemoveAttributeButton(window1);
		buttonSetAttributeInactive = articleTypeViewHandler
				.getSetAttributeInactiveButton();
		buttonSetAttributeActive = articleTypeViewHandler
				.getSetAttributeActiveButton();

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

		buttonAddArticle = articleTypeViewHandler.getAddArticleButton(window1);
		buttonRemoveArticle = articleTypeViewHandler
				.getRemoveArticleButton(window1);

		treeTableArticleTypeRef = articleTypeViewHandler.getTreeTable();
		treeTableArticleTypeRef.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		treeTableArticleTypeRef.setColumnControlVisible(true);
		treeTableArticleTypeRef
				.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		comboBoxMetric = ((ArticleTypeViewHandler) viewHandler)
				.getComboBoxMetric(presentationModel);

		textFieldProdCatNo = ((ArticleTypeViewHandler) viewHandler)
				.getTextFieldProdCatNo(presentationModel);
		textFieldProdCatNo2 = ((ArticleTypeViewHandler) viewHandler)
				.getTextFieldProdCatNo2(presentationModel);

		checkBoxForceImport = ((ArticleTypeViewHandler) viewHandler)
				.getCheckBoxForceImport(presentationModel);

		initEventHandling();
		initTableColumns();
	}

	/**
	 * Initierer hendelsehåndtering
	 */
	private void initEventHandling() {

		treeTableArticleTypeRef.getSelectionModel().addListSelectionListener(
				articleTypeViewHandler.getArticleSelectionListener());
	}

	/**
	 * Setter knapper enabled/disablet
	 */
	void updateEnablement() {
		if (viewHandler.hasWriteAccess()) {
			if (articleTypeViewHandler.getAttributeSelection().hasSelection()) {
				buttonRemoveAttribute.setEnabled(true);
			} else {
				buttonRemoveAttribute.setEnabled(false);
			}
		}
	}

	/**
	 * Initierer kolonnebredder dor tabell
	 */
	private void initTableColumns() {
		TableColumn col = treeTableArticleTypeRef.getColumnModel().getColumn(0);
		col.setPreferredWidth(150);
	}

	public final String getDialogName() {
		return "EditArticleTypeView";
	}

	public final String getHeading() {
		return "Artikkel";
	}
}
