package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.tree.TreePath;

import no.ugland.utransprod.gui.ArticleTypeView;
import no.ugland.utransprod.gui.AttributeView;
import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditArticleTypeView;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.ArticleTypeModel;
import no.ugland.utransprod.gui.model.ArticleTypeTreeNode;
import no.ugland.utransprod.gui.model.ArticleTypeTreeTableModel;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ArticleTypeArticleType;
import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.AttributeManager;
import no.ugland.utransprod.service.ConstructionTypeArticleManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXList;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.BufferedValueModel;

/**
 * Hjelpklasse for visning og redigering av artikkeltyper
 * 
 * @author atle.brekka
 */
public class ArticleTypeViewHandler extends
		DefaultAbstractViewHandler<ArticleType, ArticleTypeModel> {
	private static final long serialVersionUID = 1L;

	PresentationModel presentationModel;
	final ArrayListModel attributeList;

	final SelectionInList attributeSelection;

	BufferedValueModel bufferedAttributes;

	private JButton buttonAddArticle;

	private JButton buttonRemoveArticle;

	private JButton buttonRemoveAttribute;

	ArticleTypeTreeTableModel articleTypeTreeTableModel;

	private JXTreeTable treeTableArticleType;

	ArticleTypeTreeNode selectedNode;

	private JLabel labelFormula;

	private boolean updateConstructionType = true;

	private List<String> metricList;

	private AttributeUpdate attributeUpdate;

	private ManagerRepository managerRepository;
	private Login login;

	private JButton buttonSetAttributeInactive;

	private JButton buttonSetAttributeActive;
	private JXTable tableAttributes;

	/**
	 * Brukes av test for å slå av oppdatering av garasjetype
	 * 
	 * @param usedArticles
	 * @param userType
	 * @param doUpdateConstructionType
	 */
	@Inject
	public ArticleTypeViewHandler(Login aLogin,
			ManagerRepository managerRepository,
			@Assisted List<ArticleType> usedArticles,
			@Assisted boolean doUpdateConstructionType) {
		this(aLogin, managerRepository, usedArticles);
		updateConstructionType = doUpdateConstructionType;

	}

	/**
	 * @param usedArticles
	 * @param userType
	 */
	@SuppressWarnings("unchecked")
	@Inject
	public ArticleTypeViewHandler(Login aLogin,
			ManagerRepository aManagerRepository,
			@Assisted List<ArticleType> usedArticles) {
		super("Artikkelttype", aManagerRepository.getArticleTypeManager(),
				aLogin.getUserType(), true);

		login = aLogin;
		managerRepository = aManagerRepository;
		attributeUpdate = new AttributeUpdate(managerRepository
				.getAttributeManager());
		if (usedArticles != null) {
			List<ArticleType> tmpList = Util.getDiff(objectList, usedArticles);
			objectList.clear();
			objectList.addAll(tmpList);
		}
		// liste blir initiert i initPresentationModel
		attributeList = new ArrayListModel();
		attributeSelection = new SelectionInList((ListModel) attributeList);
		attributeSelection.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_INDEX,
				new SelectionListener());

		metricList = ApplicationParamUtil.findParamListByType("Betegnelse");
	}

	public JXList getListArticles(boolean useSingleSelection) {
		JXList listArticles = new JXList(
				getObjectSelectionList(new ArticleTypeComparator()));
		listArticles.setName("ListArticles");
		if (useSingleSelection) {
			listArticles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			listArticles.setSelectionModel(new SingleListSelectionAdapter(
					objectSelectionList.getSelectionIndexHolder()));
		}
		listArticles.addMouseListener(getListDoubleClickHandler(window));
		return listArticles;
	}

	/**
	 * Henter ok-knapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getOkButton(WindowInterface window) {
		JButton buttonOk = new CancelButton(window, this, false, "Ok",
				IconEnum.ICON_OK, null, true);
		buttonOk.setName("ButtonOkArticle");
		return buttonOk;
	}

	/**
	 * Lager knapp for å legge til artikkel
	 * 
	 * @return knapp
	 */
	public JButton getButtonAddArticle() {
		JButton button = new JButton(new AddArticleAction());
		button.setName("ButtonAddArticle");
		return button;
	}

	/**
	 * Lager lytter for dobbelklikk
	 * 
	 * @param window
	 * @return muselytter
	 */
	public MouseListener getListDoubleClickHandler(WindowInterface window) {
		return new ListDoubleClickHandler(window);
	}

	/**
	 * Lager tekstfelt for navn
	 * 
	 * @param presentationModel1
	 * @return tekstfelt
	 */
	public JTextField getTextFieldName(PresentationModel presentationModel1) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel1
						.getBufferedModel(ArticleTypeModel.PROPERTY_ARTICLE_TYPE_NAME));
		textField.setName("TextFieldName");
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	public JTextField getTextFieldProdCatNo(PresentationModel presentationModel1) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel1
						.getBufferedModel(ArticleTypeModel.PROPERTY_PROD_CAT_NO));
		textField.setName("TextFieldProdCatNo");
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	public JTextField getTextFieldProdCatNo2(
			PresentationModel presentationModel1) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel1
						.getBufferedModel(ArticleTypeModel.PROPERTY_PROD_CAT_NO_2));
		textField.setName("TextFieldProdCatNo2");
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager komboboks for betegnelse
	 * 
	 * @param presentationModel1
	 * @return komboboks
	 */
	public JComboBox getComboBoxMetric(PresentationModel presentationModel1) {
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter(metricList,
				presentationModel1
						.getBufferedModel(ArticleTypeModel.PROPERTY_METRIC)));
		comboBox.setName("ComboBoxMetric");
		return comboBox;
	}

	/**
	 * Lager tekstfelt for beskrivelse
	 * 
	 * @param presentationModel1
	 * @return tekstfelt
	 */
	public JTextField getTextFieldDescription(
			PresentationModel presentationModel1) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel1
						.getBufferedModel(ArticleTypeModel.PROPERTY_DESCRIPTION));
		textField.setName("TextFieldDescription");
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager checkboks for toppnivå
	 * 
	 * @param presentationModel1
	 * @return checkboks
	 */
	public JCheckBox getCheckBoxTopLevel(PresentationModel presentationModel1) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel1
								.getBufferedModel(ArticleTypeModel.PROPERTY_TOP_LEVEL_BOOL),
						"Toppnivå");
		checkBox.setEnabled(hasWriteAccess());
		return checkBox;
	}

	public JCheckBox getCheckBoxForceImport(PresentationModel presentationModel1) {
		JCheckBox checkBox = BasicComponentFactory
				.createCheckBox(
						presentationModel1
								.getBufferedModel(ArticleTypeModel.PROPERTY_FORCE_IMPORT_BOOL),
						"Må importeres");
		checkBox.setEnabled(hasWriteAccess());
		return checkBox;
	}

	/**
	 * Lager label for formel
	 * 
	 * @return label
	 */
	public JLabel getLabelFormula() {
		labelFormula = new JLabel("Formel");
		return labelFormula;
	}

	/**
	 * Initierer presentasjonsmodell
	 * 
	 * @param aPresentationModel
	 * @param property
	 */
	public void initPresentationModel(PresentationModel aPresentationModel,
			String property) {
		presentationModel = aPresentationModel;
		bufferedAttributes = presentationModel.getBufferedModel(property);
		attributeList.clear();
		attributeList.addAll((ArrayListModel) bufferedAttributes.getValue());
	}

	/**
	 * Henter selsksjonsliste for attributter
	 * 
	 * @return liste
	 */
	public SelectionInList getAttributeSelection() {
		return attributeSelection;
	}

	/**
	 * Henter knapp for å legge til attributt
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getAddAttributeButton(WindowInterface window) {
		JButton buttonAddAttribute = new NewButton("attributt",
				attributeUpdate, window);
		buttonAddAttribute.setName("ButtonAddAttribute");
		buttonAddAttribute.setEnabled(hasWriteAccess());
		return buttonAddAttribute;
	}

	/**
	 * Henter knapp for å fjerne attributt
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getRemoveAttributeButton(WindowInterface window) {
		buttonRemoveAttribute = new DeleteButton("attributt", attributeUpdate,
				window);
		buttonRemoveAttribute.setName("ButtonRemoveAttribute");
		buttonRemoveAttribute.setEnabled(false);
		return buttonRemoveAttribute;
	}

	public JButton getSetAttributeInactiveButton() {
		buttonSetAttributeInactive = new JButton(
				new SetAttributeInactiveAction());
		buttonSetAttributeInactive.setName("ButtonSetAttributeInactive");
		buttonSetAttributeInactive.setEnabled(false);
		return buttonSetAttributeInactive;
	}

	public JButton getSetAttributeActiveButton() {
		buttonSetAttributeActive = new JButton(new SetAttributeActiveAction());
		buttonSetAttributeActive.setName("ButtonSetAttributeInactive");
		buttonSetAttributeActive.setEnabled(false);
		return buttonSetAttributeActive;
	}

	/**
	 * @param object
	 * @param aPresentationModel
	 * @param window
	 * @return feilmedling
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
	 *      com.jgoodies.binding.PresentationModel,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CheckObject checkSaveObject(ArticleTypeModel object,
			PresentationModel aPresentationModel, WindowInterface window) {
		String errorString = null;
		ArticleType articleType = (object).getObject();
		List<ArticleTypeArticleType> list = (List) aPresentationModel
				.getBufferedValue(ArticleTypeModel.PROPERTY_ARTICLE_TYPE_ARTICLE_TYPES);
		if (articleType.getArticleTypeId() == null
				&& objectList.contains(articleType)) {
			errorString = "Kan ikke lagre artikkeltype som finnes fra før";
		} else if (list != null) {
			for (ArticleTypeArticleType ref : list) {
				if (ref.getArticleTypeRef().equals(articleType)) {
					errorString = "Artikkel kan ikke ha referanse til seg selv!";
				}
			}

		}

		return new CheckObject(errorString, false);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "artikkeltype";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "ArticleType";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public ArticleType getNewObject() {
		return new ArticleType();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new ArticleTypeTableModel(objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "150dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Artikkeltype";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(450, 260);
	}

	final void afterSaveObject(ArticleType articleType, WindowInterface window) {
		if (window != null && updateConstructionType) {
			if (Util
					.showConfirmDialog(window.getComponent(),
							"Oppdaterer garsjetyper?",
							"Ønsker du å oppdatere garsjetyper som bruker denne artikkelen?")) {
				updateConstructionTypes(articleType);
			}
		}
	}

	/**
	 * Oppdater garasjetyper i henhold til endringer som er gjort på artikkel
	 * 
	 * @param articleType
	 */
	private void updateConstructionTypes(ArticleType articleType) {
		((ArticleTypeManager) overviewManager).lazyLoad(articleType,
				new LazyLoadArticleTypeEnum[] {
						LazyLoadArticleTypeEnum.CONSTRUCTION_TYPE_ARTICLE,
						LazyLoadArticleTypeEnum.ATTRIBUTE,
						LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE });
		Set<ConstructionTypeArticle> articles = articleType
				.getConstructionTypeArticles();
		Set<ArticleTypeAttribute> articleAttributes = articleType
				.getArticleTypeAttributes();
		Set<ConstructionTypeArticleAttribute> attributes;
		ConstructionTypeArticleAttribute newAttribute;

		ConstructionTypeArticleManager constructionTypeArticleManager = (ConstructionTypeArticleManager) ModelUtil
				.getBean("constructionTypeArticleManager");

		for (ConstructionTypeArticle consArticle : articles) {
			constructionTypeArticleManager.lazyLoad(consArticle,
					new LazyLoadEnum[][] { { LazyLoadEnum.ATTRIBUTES,
							LazyLoadEnum.NONE } });
			attributes = consArticle.getAttributes();
			for (ArticleTypeAttribute articleTypeAttribute : articleAttributes) {
				if (!constructionTypeHasAttribute(attributes,
						articleTypeAttribute)) {
					newAttribute = new ConstructionTypeArticleAttribute(null,
							consArticle, articleTypeAttribute,
							Util.convertNumberToBoolean(articleTypeAttribute
									.getAttribute().getYesNo()) ? "Nei" : "",
							null);
					attributes.add(newAttribute);
				}
			}
			constructionTypeArticleManager
					.saveConstructionTypeArticle(consArticle);
		}
	}

	/**
	 * Sjekker om garasjetype har attributt som skal endres
	 * 
	 * @param attributes
	 * @param articleTypeAttribute
	 * @return true dersom garasjetype har atributt som skal endres
	 */
	private boolean constructionTypeHasAttribute(
			Set<ConstructionTypeArticleAttribute> attributes,
			ArticleTypeAttribute articleTypeAttribute) {
		boolean hasAttribute = false;
		for (ConstructionTypeArticleAttribute attribute : attributes) {
			if (attribute.getArticleTypeAttribute().getAttribute().equals(
					articleTypeAttribute.getAttribute())) {
				hasAttribute = true;
				break;
			}
		}
		return hasAttribute;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		// Navn
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(80);

		// Beskrivlse
		col = table.getColumnModel().getColumn(1);
		col.setPreferredWidth(80);

	}

	/**
	 * Tabellmodell for artikkeltype
	 * 
	 * @author atle.brekka
	 */
	public static final class ArticleTypeTableModel extends
			AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMNS = { "Navn", "Betegnesle",
				"Beskrivelse" };

		/**
		 * @param listModel
		 */
		public ArticleTypeTableModel(ListModel listModel) {
			super(listModel, COLUMNS);
		}

		/**
		 * Henter verdi
		 * 
		 * @param rowIndex
		 * @param columnIndex
		 * @return verdi
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			ArticleType articleType = (ArticleType) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return articleType.getArticleTypeName();
			case 1:
				return articleType.getMetric();
			case 2:
				return articleType.getDescription();
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		/**
		 * Henter kolonneklasse
		 * 
		 * @param columnIndex
		 * @return kolonneklasse
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
			case 1:
			case 2:
				return String.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * Åpner vindu for å velge attributter
	 */
	public void openAttributeView(AttributeManager attributeManager) {

		ArticleTypeModel attributeable = (ArticleTypeModel) presentationModel
				.getBean();

		AttributeViewHandler attributeViewHandler = new AttributeViewHandler(
				login, managerRepository);
		AttributeView attributeView = new AttributeView(attributeViewHandler,
				false);
		WindowInterface dialog = new JDialogAdapter(new JDialog(
				ProTransMain.PRO_TRANS_MAIN, "Attributt", true));
		dialog.setName("AttributeView");
		dialog.add(attributeView.buildPanel(dialog));
		dialog.pack();
		Util.locateOnScreenCenter(dialog);
		dialog.setVisible(true);

		if (!attributeView.isCanceled()) {

			List<Attribute> newAttributes = attributeView.getSelectedObjects();
			if (newAttributes != null && newAttributes.size() != 0) {
				attributeList.addAll(attributeable
						.getArticleAttributes(newAttributes));
				bufferedAttributes.setValue(attributeList);
			}
		}
	}

	/**
	 * Klasse som håndtrer oppdatering av attributter
	 * 
	 * @author atle.brekka
	 */
	class AttributeUpdate implements Updateable {
		private AttributeManager attributeManager;

		public AttributeUpdate(AttributeManager aAttributeManager) {
			attributeManager = aAttributeManager;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
		 */
		public boolean doDelete(WindowInterface window) {
			boolean deleted = true;
			final ArticleTypeAttribute attribute = getSelectedAttribute();
			managerRepository.getArticleTypeAttributeManager().lazyLoad(
					attribute,
					new LazyLoadEnum[][] { {
							LazyLoadEnum.CONSTRUCTION_TYPE_ARTICLE_ATTRIBUTES,
							LazyLoadEnum.NONE } });

			if (attribute.getConstructionTypeArticleAttributes() != null
					&& attribute.getConstructionTypeArticleAttributes().size() != 0) {
				deleted = false;
				Util
						.showErrorDialog(window, "Feil",
								"Kan ikke slette attributt som er referert til av an garasjetype eller master");
			} else {

				attributeList.remove(attribute);
				bufferedAttributes.setValue(attributeList);
			}
			return deleted;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doNew(WindowInterface window) {
			openAttributeView(attributeManager);

		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doSave(WindowInterface window1) {
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doRefresh(WindowInterface window) {

		}

	}

	private ArticleTypeAttribute getSelectedAttribute() {
		final ArticleTypeAttribute attribute = (ArticleTypeAttribute) attributeSelection
				.getSelection();
		return attribute;
	}

	/**
	 * @param articleType
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(ArticleType articleType) {
		String errorString = null;
		((ArticleTypeManager) overviewManager).lazyLoad(articleType,
				new LazyLoadArticleTypeEnum[] {
						LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE_REF,
						LazyLoadArticleTypeEnum.CONSTRUCTION_TYPE_ARTICLE });

		if (articleType.getArticleTypeArticleTypeRefs() != null
				&& articleType.getArticleTypeArticleTypeRefs().size() != 0) {
			errorString = "Kan ikke slette artikkel som er referert til av andre artikler";
		} else if (articleType.getConstructionTypeArticles() != null
				&& articleType.getConstructionTypeArticles().size() != 0) {
			errorString = "Kan ikke slette artikkel som er referert til av garasjetyper";
		}

		return new CheckObject(errorString, false);
	}

	/**
	 * Henter knapp for å legge til artikkel
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getAddArticleButton(WindowInterface window) {
		buttonAddArticle = new NewButton("artikkel", new ArticleUpdate(),
				window);
		buttonAddArticle.setName("ButtonAddArticle");
		buttonAddArticle.setEnabled(hasWriteAccess());
		return buttonAddArticle;
	}

	/**
	 * Henter tratabell for artikkel
	 * 
	 * @return tretabell
	 */
	public JXTreeTable getTreeTable() {
		ArticleType clonedType = ArticleTypeModel
				.cloneArticleType((ArticleType) presentationModel
						.getBufferedValue(AbstractModel.PROPERTY_OBJECT));

		((ArticleTypeManager) overviewManager).lazyLoad(clonedType,
				new LazyLoadArticleTypeEnum[] {
						LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE,
						LazyLoadArticleTypeEnum.ATTRIBUTE });

		articleTypeTreeTableModel = new ArticleTypeTreeTableModel(clonedType);
		treeTableArticleType = new JXTreeTable(articleTypeTreeTableModel);
		return treeTableArticleType;
	}

	/**
	 * Henter knapp for å fjerne artikkel
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getRemoveArticleButton(WindowInterface window) {
		buttonRemoveArticle = new DeleteButton("artikkel", new ArticleUpdate(),
				window);
		buttonRemoveArticle.setName("ButtonRemveArticle");
		buttonRemoveArticle.setEnabled(false);
		return buttonRemoveArticle;
	}

	/**
	 * Klase som håndterer oppdatering av artikkel
	 * 
	 * @author atle.brekka
	 */
	class ArticleUpdate implements Updateable {
		/**
		 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
		 */
		public boolean doDelete(WindowInterface window) {
			boolean deleted = true;
			if (selectedNode != null) {
				ArticleTypeArticleType article;

				if (selectedNode.isLeaf()) {
					article = (ArticleTypeArticleType) selectedNode.getParent()
							.getObject();
				} else {
					article = (ArticleTypeArticleType) selectedNode.getObject();
				}
				ArticleType articleType = (ArticleType) ((ArticleTypeTreeNode) articleTypeTreeTableModel
						.getRoot()).getObject();
				articleType.getArticleTypeArticleTypes().remove(article);

				BufferedValueModel bufferedArticles = presentationModel
						.getBufferedModel(ArticleTypeModel.PROPERTY_ARTICLE_TYPE_ARTICLE_TYPES);
				bufferedArticles.setValue(new ArrayListModel(articleType
						.getClonedArticleTypeArticleTypes()));
				articleTypeTreeTableModel.fireChanged();
			} else {
				deleted = false;
			}
			return deleted;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doNew(WindowInterface window) {
			openArticleView(window);

		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doSave(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doSave(WindowInterface window1) {
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doRefresh(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doRefresh(WindowInterface window) {

		}

	}

	/**
	 * Åpner vindu for å legge til artikkel
	 * 
	 * @param window
	 */
	@SuppressWarnings("unchecked")
	public void openArticleView(WindowInterface window) {
		List<ArticleType> articles = (List<ArticleType>) presentationModel
				.getValue(ArticleTypeModel.PROPERTY_ARTICLES);

		ArticleType currentArticleType = (ArticleType) presentationModel
				.getBufferedValue(AbstractModel.PROPERTY_OBJECT);

		ArticleTypeViewHandler articleTypeViewHandler = new ArticleTypeViewHandler(
				login, managerRepository, articles);
		ArticleTypeView articleTypeView = new ArticleTypeView(
				articleTypeViewHandler, false, false);
		WindowInterface dialog = new JDialogAdapter(new JDialog(
				ProTransMain.PRO_TRANS_MAIN, "Artikkel", true));
		dialog.setName("ArticleView");
		dialog.add(articleTypeView.buildPanel(dialog));
		dialog.pack();
		Util.locateOnScreenCenter(dialog);
		dialog.setVisible(true);

		List<ArticleType> newArticles = articleTypeView.getSelectedObjects();
		showArticleAttributeView(newArticles, currentArticleType);

	}

	/**
	 * Viser vindu for å legg til artikkel
	 * 
	 * @param newArticles
	 * @param currentArticleType
	 */
	private void showArticleAttributeView(List<ArticleType> newArticles,
			ArticleType currentArticleType) {
		if (newArticles != null && newArticles.size() != 0) {
			Set<ArticleTypeArticleType> articles = new HashSet<ArticleTypeArticleType>();
			for (ArticleType article : newArticles) {
				((ArticleTypeManager) overviewManager)
						.lazyLoad(
								article,
								new LazyLoadArticleTypeEnum[] {
										LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE,
										LazyLoadArticleTypeEnum.ATTRIBUTE });
				articles.add(new ArticleTypeArticleType(null,
						currentArticleType, article));
			}

			ArticleTypeTreeNode rootNode = (ArticleTypeTreeNode) articleTypeTreeTableModel
					.getRoot();
			ArticleType articleType = (ArticleType) rootNode.getObject();

			if (articleType.getArticleTypeArticleTypes() != null) {
				articleType.getArticleTypeArticleTypes().addAll(articles);
			} else {
				HashSet<ArticleTypeArticleType> set = new HashSet<ArticleTypeArticleType>();
				set.addAll(articles);
				articleType.setArticleTypeArticleTypes(set);
			}
			articleTypeTreeTableModel.fireChanged();

			BufferedValueModel bufferedArticles = presentationModel
					.getBufferedModel(ArticleTypeModel.PROPERTY_ARTICLE_TYPE_ARTICLE_TYPES);
			bufferedArticles.setValue(new ArrayListModel(articleType
					.getArticleTypeArticleTypes()));

		}
	}

	/**
	 * Henter lytter for endring av valg av artikkel
	 * 
	 * @return lytter
	 */
	public ListSelectionListener getArticleSelectionListener() {
		return new ArticleSelectionListener();
	}

	/**
	 * Klasse som håndterer endring av valg av artikkel
	 * 
	 * @author atle.brekka
	 */
	class ArticleSelectionListener implements ListSelectionListener {

		/**
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		public void valueChanged(ListSelectionEvent e) {

			updateArticleButtons();

		}

	}

	/**
	 * Enabler/disabler knapper for artikkel
	 */
	void updateArticleButtons() {
		if (hasWriteAccess()) {
			TreePath path = treeTableArticleType
					.getPathForRow(treeTableArticleType.getSelectedRow());

			buttonRemoveArticle.setEnabled(false);

			if (path != null) {
				selectedNode = (ArticleTypeTreeNode) path
						.getLastPathComponent();
				buttonRemoveArticle.setEnabled(true);
			}

		}
	}

	/**
	 * Håndterer dobbelklikk i liste
	 * 
	 * @author atle.brekka
	 */
	final class ListDoubleClickHandler extends MouseAdapter {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ListDoubleClickHandler(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)

				window.dispose();

		}
	}

	/**
	 * Legger til artikkel
	 * 
	 * @author atle.brekka
	 */
	private class AddArticleAction extends AbstractAction {
		/**
         *
         */
		private static final long serialVersionUID = 1L;

		/**
         *
         */
		public AddArticleAction() {
			super("Ny artikkel...");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			String articleName = JOptionPane
					.showInputDialog("Navn på artikkel");
			if (articleName != null && articleName.length() != 0) {
				ArticleType articleType = new ArticleType();
				articleType.setArticleTypeName(articleName);
				articleType.setTopLevel(1);
				articleType.setIsExtra(1);
				((ArticleTypeManager) overviewManager)
						.saveArticleType(articleType);
				objectList.add(0, articleType);

				objectSelectionList.setSelection(articleType);

			}

		}
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Artikkel");
	}

	/**
	 * Håndterer avbryt
	 * 
	 * @author atle.brekka
	 */
	class CancelDialog implements Closeable {

		/**
		 * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
		 *      no.ugland.utransprod.gui.WindowInterface)
		 */
		public boolean canClose(String actionString, WindowInterface window) {
			objectSelectionList.clearSelection();
			return true;
		}

	}

	/**
	 * Oppdater knappestatus
	 */
	void updateEnablement() {
		buttonRemoveAttribute.setEnabled(false);

		if (hasWriteAccess()) {
			if (attributeSelection.hasSelection()) {
				ArticleTypeAttribute attribute = (ArticleTypeAttribute) attributeSelection
						.getSelection();
				if (attribute.getAttributeFormula() != null) {
					labelFormula.setText(attribute.getAttributeFormula());
				} else {
					labelFormula.setText("");
				}

				buttonRemoveAttribute.setEnabled(true);
				if (attribute.getIsInactive()) {
					buttonSetAttributeActive.setEnabled(true);
					buttonSetAttributeInactive.setEnabled(false);
				} else {
					buttonSetAttributeActive.setEnabled(false);
					buttonSetAttributeInactive.setEnabled(true);
				}
			}

		}
	}

	/**
	 * Hpndterer selektering i liste
	 * 
	 * @author atle.brekka
	 */
	class SelectionListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			updateEnablement();

		}

	}

	/**
	 * @param handler
	 * @param object
	 * @param searching
	 * @return view
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
	 *      java.lang.Object, boolean)
	 */
	@Override
	protected AbstractEditView<ArticleTypeModel, ArticleType> getEditView(
			AbstractViewHandler<ArticleType, ArticleTypeModel> handler,
			ArticleType object, boolean searching) {
		((ArticleTypeManager) overviewManager).lazyLoad(object,
				new LazyLoadArticleTypeEnum[] {
						LazyLoadArticleTypeEnum.ATTRIBUTE,
						LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE });
		return new EditArticleTypeView(searching, object, this);
	}

	/**
	 * Klasse som sammenlikner to artikkeltyper basert på navn
	 * 
	 * @author atle.brekka
	 */
	static class ArticleTypeComparator implements Comparator<ArticleType>,
			Serializable {

		/**
         *
         */
		private static final long serialVersionUID = 1L;

		/**
		 * @param o1
		 * @param o2
		 * @return -1,0 eller 1
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(ArticleType o1, ArticleType o2) {
			return o1.getArticleTypeName().compareTo(o2.getArticleTypeName());
		}

	}

	public JXTable getTableAttributes() {
		tableAttributes = new JXTable(new AttributeTableModel(
				attributeSelection));
		tableAttributes.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableAttributes.setSelectionModel(new SingleListSelectionAdapter(
				attributeSelection.getSelectionIndexHolder()));
		tableAttributes.getColumnExt(0).setPreferredWidth(120);
		tableAttributes.getColumnExt(1).setPreferredWidth(50);
		tableAttributes.setName("TableAttributes");
		return tableAttributes;
	}

	private static class AttributeTableModel extends AbstractTableAdapter {

		private static final long serialVersionUID = 1L;
		private static final String[] columnNames = new String[] { "Navn",
				"Inaktiv" };

		public AttributeTableModel(final ListModel listModel) {
			super(listModel, columnNames);
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			ArticleTypeAttribute attribute = (ArticleTypeAttribute) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return attribute.getAttribute().getName();
			case 1:
				return attribute.getIsInactive();
			}
			return null;
		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return String.class;
			case 1:
				return Boolean.class;
			}
			return String.class;
		}

	}

	private class SetAttributeInactiveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SetAttributeInactiveAction() {
			super("Sett inaktiv");
		}

		public void actionPerformed(ActionEvent e) {
			final ArticleTypeAttribute attribute = getSelectedAttribute();
			attribute.setInactive(1);
			bufferedAttributes.setValue(attributeList);
			tableAttributes.repaint();
		}
	}

	private class SetAttributeActiveAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SetAttributeActiveAction() {
			super("Sett aktiv");
		}

		public void actionPerformed(ActionEvent e) {
			final ArticleTypeAttribute attribute = getSelectedAttribute();
			attribute.setInactive(0);
			bufferedAttributes.setValue(attributeList);
			tableAttributes.repaint();
		}
	}
}
