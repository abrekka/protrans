package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.tree.TreePath;

import no.ugland.utransprod.gui.ArticleTypeView;
import no.ugland.utransprod.gui.AttributeView;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.UpdateAttributeView;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditConstructionTypeView;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.ConstructionTreeNode;
import no.ugland.utransprod.gui.model.ConstructionTreeTableModel;
import no.ugland.utransprod.gui.model.ConstructionTypeModel;
import no.ugland.utransprod.gui.model.SketchEnum;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ArticleTypeArticleType;
import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.model.Attribute;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
import no.ugland.utransprod.model.ConstructionTypeAttribute;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.ProductAreaManager;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeAttributeEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.BufferedValueModel;

/**
 * Hjelpeklasse for visning og editering av garasjetype
 * 
 * @author atle.brekka
 */
public class ConstructionTypeViewHandler extends
		AbstractViewHandler<ConstructionType, ConstructionTypeModel> {
	private static final long serialVersionUID = 1L;

	final ArrayListModel attributeList;

	final SelectionInList attributeSelection;

	private JButton buttonAddAttribute;

	private JButton buttonRemoveAttribute;

	private JButton buttonEditAttribute;

	private JButton buttonAddArticle;

	private JButton buttonRemoveArticle;

	private JButton buttonEditArticle;

	ConstructionTreeTableModel constructionTreeTableModel;

	private JXTreeTable treeTable;

	ConstructionTreeNode selectedNode;

	private Set<ConstructionTypeAttribute> universalAttributes = new HashSet<ConstructionTypeAttribute>();

	private Set<ConstructionTypeArticle> universalArticles = new HashSet<ConstructionTypeArticle>();

	private Set<ConstructionTypeAttribute> universalChangedAttributes = new HashSet<ConstructionTypeAttribute>();

	private Set<ConstructionTypeArticleAttribute> universalChangedArticleAttributes = new HashSet<ConstructionTypeArticleAttribute>();

	private Set<ConstructionTypeArticle> universalChangedArticles = new HashSet<ConstructionTypeArticle>();

	private boolean masterDialog = false;

	private static List<ProductArea> productAreaList;

	private boolean isMasterOverview;

	private Login login;

	private ManagerRepository managerRepository;

	/**
	 * @param isMasterDialog
	 * @param userType
	 * @param masterOverview
	 */
	@Inject
	public ConstructionTypeViewHandler(Login aLogin,
			ManagerRepository aManagerRepository,
			@Assisted(value = "isMasterDialog") boolean isMasterDialog,
			@Assisted(value = "masterOverview") boolean masterOverview) {
		super("Konstruksjonstype", aManagerRepository
				.getConstructionTypeManager(), aLogin.getUserType(), true);
		managerRepository = aManagerRepository;
		login = aLogin;
		isMasterOverview = masterOverview;
		attributeList = new ArrayListModel();
		attributeSelection = new SelectionInList((ListModel) attributeList);

		masterDialog = isMasterDialog;
		productAreaList = new ArrayList<ProductArea>();
		initProductAreaList();
	}

	/**
	 * Initierer liste med produktområde
	 */
	private void initProductAreaList() {
		if (productAreaList.size() == 0) {
			productAreaList.clear();
			ProductAreaManager productAreaManager = (ProductAreaManager) ModelUtil
					.getBean("productAreaManager");
			List<ProductArea> productAreas = productAreaManager.findAll();
			if (productAreas != null) {
				productAreaList.addAll(productAreas);
			}
		}
	}

	/**
	 * Initierer presentasjonsmodell
	 * 
	 * @param aPresentationModel
	 */
	@SuppressWarnings("unchecked")
	public void initPresentationModel(PresentationModel aPresentationModel) {
		ArrayListModel bufferedAttributes = (ArrayListModel) aPresentationModel
				.getBufferedValue(ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES);

		attributeList.clear();
		if (bufferedAttributes != null) {
			attributeList.addAll(cloneAttributeList(bufferedAttributes));
		}
	}

	/**
	 * Lager komboboks for tegninger
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxSketch(PresentationModel presentationModel) {
		return new JComboBox(
				new ComboBoxAdapter(
						SketchEnum.getSketchList(),
						presentationModel
								.getBufferedModel(ConstructionTypeModel.PROPERTY_SKETCH_NAME)));
	}

	public JComboBox getComboBoxProductArea(PresentationModel presentationModel) {
		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(
						productAreaList,
						presentationModel
								.getBufferedModel(ConstructionTypeModel.PROPERTY_PRODUCT_AREA)));
		comboBox.setName("ComboBoxProductArea");
		return comboBox;

	}

	/**
	 * Lager tekstfelt for navn
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldName(PresentationModel presentationModel) {
		JTextField textFieldName = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ConstructionTypeModel.PROPERTY_NAME));
		textFieldName.setEnabled(hasWriteAccess());
		textFieldName.setName("TextFieldName");
		return textFieldName;
	}

	/**
	 * Lager tekstfelt for beskrivelse
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldDescription(
			PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ConstructionTypeModel.PROPERTY_DESCRIPTION));
		textField.setName("TextFieldDescription");
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Kloner attributtliste for å ikke jobbe på objekter som er knyttet til
	 * database
	 * 
	 * @param list
	 * @return klonet liste
	 */
	private List<ConstructionTypeAttribute> cloneAttributeList(
			List<ConstructionTypeAttribute> list) {
		List<ConstructionTypeAttribute> cloneList = new ArrayList<ConstructionTypeAttribute>();
		List<ConstructionTypeAttribute> clonedList = new ArrayList<ConstructionTypeAttribute>();
		cloneList.addAll(list);

		for (ConstructionTypeAttribute att : cloneList) {
			clonedList.add(att.clone());
		}
		return clonedList;
	}

	/**
	 * Henter seleksjonsliste for attributter
	 * 
	 * @return liste
	 */
	public SelectionInList getAttributeSelection() {
		return attributeSelection;
	}

	/**
	 * Henter attributtliste sortert
	 * 
	 * @return liste
	 */
	@SuppressWarnings("unchecked")
	public SelectionInList getAttributeSelectionWithComparator() {
		Collections.sort(attributeList, new AttributeComparator());
		return attributeSelection;
	}

	/**
	 * Henter knapp for å legge til attributt
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getAddAttributeButton(WindowInterface window,
			PresentationModel presentationModel) {
		buttonAddAttribute = new NewButton("attributt", new AttributeUpdate(
				presentationModel), window);
		buttonAddAttribute.setName("ButtonAddAttribute");
		buttonAddAttribute.setEnabled(hasWriteAccess());
		return buttonAddAttribute;
	}

	/**
	 * Henter knapp for å fjerne attributt
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getRemoveAttributeButton(WindowInterface window,
			PresentationModel presentationModel) {
		buttonRemoveAttribute = new DeleteButton("attributt",
				new AttributeUpdate(presentationModel), window);
		buttonRemoveAttribute.setName("ButtonRemveAttribute");
		buttonRemoveAttribute.setEnabled(false);
		return buttonRemoveAttribute;
	}

	/**
	 * Henter knapp for å editere attributt
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getEditAttributeButton(WindowInterface window,
			PresentationModel presentationModel) {
		buttonEditAttribute = new JButton(new EditAttributeAction(window,
				presentationModel));
		buttonEditAttribute.setName("ButtonEditAttribute");
		buttonEditAttribute.setEnabled(false);
		return buttonEditAttribute;
	}

	/**
	 * Henter knapp for å legge til artikkel
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getAddArticleButton(WindowInterface window,
			PresentationModel presentationModel) {
		buttonAddArticle = new NewButton("artikkel", new ArticleUpdate(
				presentationModel), window);
		buttonAddArticle.setName("ButtonAddArticle");
		buttonAddArticle.setEnabled(hasWriteAccess());
		return buttonAddArticle;
	}

	/**
	 * Henter knapp for å fjerne artikkel
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getRemoveArticleButton(WindowInterface window,
			PresentationModel presentationModel) {
		buttonRemoveArticle = new DeleteButton("artikkel", new ArticleUpdate(
				presentationModel), window);
		buttonRemoveArticle.setName("ButtonRemveArticle");
		buttonRemoveArticle.setEnabled(false);
		return buttonRemoveArticle;
	}

	/**
	 * Henter knapp for å editere artikkel
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getEditArticleButton(WindowInterface window,
			PresentationModel presentationModel) {
		buttonEditArticle = new JButton(new EditArticleAction(window,
				presentationModel));
		buttonEditArticle.setName("ButtonEditArticle");
		buttonEditArticle.setEnabled(false);
		return buttonEditArticle;
	}

	/**
	 * Lager knapp for å kopierer fra master
	 * 
	 * @param presentationModel
	 * @param window
	 * @return knapp
	 */
	public JButton getCopyMasterButton(PresentationModel presentationModel,
			WindowInterface window) {
		JButton copyButton = new JButton();
		copyButton.setAction(new CopyMasterAction(copyButton,
				presentationModel, window));
		return copyButton;
	}

	/**
	 * @param object
	 * @param presentationModel
	 * @param window
	 * @return feilmelding
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
	 *      com.jgoodies.binding.PresentationModel,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public CheckObject checkSaveObject(ConstructionTypeModel object,
			PresentationModel presentationModel, WindowInterface window) {
		String errorString = null;

		ConstructionType constructionType = object.getObject();
		if (constructionType.getConstructionTypeId() == null
				&& objectList.contains(constructionType)) {
			errorString = "Kan ikke lagre konstruksjonstype med et navn som finnes fra før";
		}

		if (constructionType.isMaster()) {
			ConstructionType type = ((ConstructionTypeManager) overviewManager)
					.findMaster(constructionType.getProductArea());
			if (type != null
					&& !type.getName().equalsIgnoreCase(
							constructionType.getName())) {
				errorString = "Kan ikke være to mastere for samme produktområde";
			}
		}

		return new CheckObject(errorString, false);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public ConstructionType getNewObject() {
		ConstructionType newConstructionType;
		newConstructionType = new ConstructionType();
		if (isMasterOverview) {
			newConstructionType.setIsMaster(1);
		}
		return newConstructionType;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new ConstructionTypeTableModel(objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "170dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		if (masterDialog) {
			return "Master konstruksjonstype";
		}
		return "Konstruksjonstype";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(500, 270);
	}

	void afterSaveObject(ConstructionType constructionType,
			WindowInterface window) {
		setUniversalAttributes(constructionType.getProductArea());
		setUniversalArticles(constructionType.getProductArea());
		changeUniversalAttributes(constructionType.getProductArea());
		changeUniversalArticleAttributes(constructionType.getProductArea());
		changeUniversalArticles(constructionType.getProductArea());
		addAttributesToOrders(constructionType.getProductArea());
	}

	private void addAttributesToOrders(final ProductArea productArea) {
		if (masterDialog) {
			if (universalAttributes.size() != 0) {
				OrderLineManager orderLineManager = (OrderLineManager) ModelUtil
						.getBean("orderLineManager");
				List<OrderLine> orderLines = orderLineManager
						.findAllConstructionTypeNotSent(productArea);
				for (OrderLine orderLine : orderLines) {
					addAttributesToConstructionTypeOrderLine(orderLine,
							universalAttributes);
					orderLineManager.saveOrderLine(orderLine);
				}
			}
		}
	}

	private void addAttributesToConstructionTypeOrderLine(OrderLine orderLine,
			Set<ConstructionTypeAttribute> attributes) {
		for (ConstructionTypeAttribute att : attributes) {
			orderLine.addAttribute(OrderLineAttribute.cloneAttribute(att));
		}

	}

	/**
	 * Overfører endringer gjort med master til alle garasjetyper
	 * 
	 * @param productArea
	 */
	private void changeUniversalArticles(final ProductArea productArea) {
		if (masterDialog) {
			if (universalChangedArticles.size() != 0) {
				List<ConstructionType> types = ((ConstructionTypeManager) overviewManager)
						.findByProductArea(productArea);

				Set<ConstructionTypeArticle> articles = null;

				for (ConstructionType type : types) {
					((ConstructionTypeManager) overviewManager)
							.lazyLoad(
									type,
									new LazyLoadConstructionTypeEnum[] { LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ARTICLE });
					articles = type.getConstructionTypeArticles();

					changeArticles(articles);

					((ConstructionTypeManager) overviewManager)
							.saveConstructionType(type);

				}

			}
		}
	}

	/**
	 * Brukes ved overføring av endringer gjort på master til alla garasjetype.
	 * Går gjennom alle avhengigheter mellom artikler og gjør endringer
	 * 
	 * @param articles
	 */
	private void changeArticles(Set<ConstructionTypeArticle> articles) {

		Set<ConstructionTypeArticle> articleRefs;
		if (articles != null) {
			for (ConstructionTypeArticle article : articles) {
				articleRefs = article.getConstructionTypeArticles();
				changeArticles(articleRefs);

				for (ConstructionTypeArticle art : universalChangedArticles) {
					if (article.getArticleName().equalsIgnoreCase(
							art.getArticleName())) {
						if (hasSameArticleTree(article, art)) {

							article.setNumberOfItems(art.getNumberOfItems());
							article.setDialogOrder(art.getDialogOrder());
						}
					}
				}
			}
		}

	}

	/**
	 * Legger til attributter som er lagt til master for alle garasjetype
	 * 
	 * @param productArea
	 */
	private void setUniversalAttributes(final ProductArea productArea) {

		if (masterDialog) {
			if (universalAttributes.size() != 0) {
				List<ConstructionType> types = ((ConstructionTypeManager) overviewManager)
						.findByProductArea(productArea);
				Set<ConstructionTypeAttribute> conAttributes = null;
				for (ConstructionType type : types) {
					((ConstructionTypeManager) overviewManager)
							.lazyLoad(
									type,
									new LazyLoadConstructionTypeEnum[] { LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ATTRIBUTE });
					conAttributes = type.getConstructionTypeAttributes();
					if (conAttributes == null) {
						conAttributes = new HashSet<ConstructionTypeAttribute>();
					}
					conAttributes.addAll(ConstructionTypeModel
							.copyConstructionTypeAttributes(type,
									universalAttributes));
					type.setConstructionTypeAttributes(conAttributes);
					((ConstructionTypeManager) overviewManager)
							.saveConstructionType(type);
				}

			}
		}
	}

	/**
	 * Overfører endringer gjort på atributter for master til alle garasjetyper
	 */
	private void changeUniversalAttributes(final ProductArea productArea) {

		if (masterDialog) {
			if (universalChangedAttributes.size() != 0) {
				List<ConstructionType> types = ((ConstructionTypeManager) overviewManager)
						.findByProductArea(productArea);
				Set<ConstructionTypeAttribute> conAttributes = null;
				for (ConstructionType type : types) {
					boolean changed = false;
					((ConstructionTypeManager) overviewManager)
							.lazyLoad(
									type,
									new LazyLoadConstructionTypeEnum[] { LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ATTRIBUTE });
					conAttributes = type.getConstructionTypeAttributes();
					if (conAttributes == null) {
						conAttributes = new HashSet<ConstructionTypeAttribute>();
					}

					for (ConstructionTypeAttribute att : conAttributes) {
						for (ConstructionTypeAttribute changedAtt : universalChangedAttributes) {
							if (att.getAttribute().equals(
									changedAtt.getAttribute())) {
								att.setAttributeValue(changedAtt
										.getAttributeValue());
								att.setDialogOrder(changedAtt.getDialogOrder());
								changed = true;
							}
						}

					}

					if (changed) {
						((ConstructionTypeManager) overviewManager)
								.saveConstructionType(type);
					}
				}

			}
		}
	}

	/**
	 * Overfører alle endringer gjort på attributter på artikler for master til
	 * alle grasjetyper
	 */
	private void changeUniversalArticleAttributes(final ProductArea productArea) {
		if (masterDialog) {
			if (universalChangedArticleAttributes.size() != 0) {
				List<ConstructionType> types = ((ConstructionTypeManager) overviewManager)
						.findByProductArea(productArea);

				Set<ConstructionTypeArticle> articles = null;

				for (ConstructionType type : types) {
					((ConstructionTypeManager) overviewManager)
							.lazyLoad(
									type,
									new LazyLoadConstructionTypeEnum[] { LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ARTICLE });
					articles = type.getConstructionTypeArticles();

					changeArticleAttributes(articles);

					((ConstructionTypeManager) overviewManager)
							.saveConstructionType(type);

				}

			}
		}
	}

	/**
	 * Brukes ved overføring av endringer gjort med master til alle garasjetyper
	 * 
	 * @param articles
	 */
	private void changeArticleAttributes(Set<ConstructionTypeArticle> articles) {

		Set<ConstructionTypeArticleAttribute> attributes = null;
		Set<ConstructionTypeArticle> articleRefs;
		if (articles != null) {
			for (ConstructionTypeArticle article : articles) {
				((ConstructionTypeManager) overviewManager)
						.lazyLoadArticle(
								article,
								new LazyLoadConstructionTypeArticleEnum[] {
										LazyLoadConstructionTypeArticleEnum.CONSTRUCTION_TYPE_ARTICLES,
										LazyLoadConstructionTypeArticleEnum.ATTRIBUTES });
				articleRefs = article.getConstructionTypeArticles();
				changeArticleAttributes(articleRefs);
				attributes = article.getAttributes();

				for (ConstructionTypeArticleAttribute attribute : attributes) {
					for (ConstructionTypeArticleAttribute att : universalChangedArticleAttributes) {
						if (attribute.getAttributeName().equalsIgnoreCase(
								att.getAttributeName())) {
							if (hasSameArticleTree(att
									.getConstructionTypeArticle(), attribute
									.getConstructionTypeArticle())) {
								attribute.setAttributeValue(att
										.getAttributeValue());
								attribute.setDialogOrder(att.getDialogOrder());

							}
						}

					}
				}
			}
		}

	}

	/**
	 * Sjekker om to artikler har samme path
	 * 
	 * @param article1
	 * @param article2
	 * @return true dersom path er lik
	 */
	private boolean hasSameArticleTree(ConstructionTypeArticle article1,
			ConstructionTypeArticle article2) {
		if (article1 != null
				&& article2 != null
				&& article1.getArticleName().equalsIgnoreCase(
						article2.getArticleName())) {
			if (article1.getConstructionTypeArticleRef() == null
					&& article2.getConstructionTypeArticleRef() == null) {
				return true;
			}
			return hasSameArticleTree(article1.getConstructionTypeArticleRef(),
					article2.getConstructionTypeArticleRef());
		}
		return false;
	}

	/**
	 * Overfører artikler fra master til allae garasjetyper
	 */
	private void setUniversalArticles(final ProductArea productArea) {
		if (masterDialog) {
			if (universalArticles.size() != 0) {
				List<ConstructionType> types = ((ConstructionTypeManager) overviewManager)
						.findByProductArea(productArea);
				Set<ConstructionTypeArticle> conArticles = null;
				for (ConstructionType type : types) {
					((ConstructionTypeManager) overviewManager)
							.lazyLoad(
									type,
									new LazyLoadConstructionTypeEnum[] {
											LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ARTICLE,
											LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ARTICLE_ATTRIBUTES });
					conArticles = type.getConstructionTypeArticles();
					if (conArticles == null) {
						conArticles = new HashSet<ConstructionTypeArticle>();
					}

					conArticles.addAll(ConstructionTypeModel
							.copyConstructionTypeArticles(type,
									universalArticles, null));

					addAttributes(type);

					type.setConstructionTypeArticles(conArticles);
					((ConstructionTypeManager) overviewManager)
							.saveConstructionType(type);

				}
				universalArticles.clear();
			}
		}
	}

	/**
	 * Brukes ved overføring fra master til garasjetyper, og legger til
	 * attributter
	 * 
	 * @param type
	 */
	private void addAttributes(ConstructionType type) {
		List<ConstructionTypeArticle> articles;
		ConstructionTypeArticle orgArticle;
		Set<ConstructionTypeArticleAttribute> articleAttributes;
		Set<ConstructionTypeArticleAttribute> orgArticleAttributes;
		for (ConstructionTypeArticle article : universalArticles) {
			article.setConstructionType(type);
			if (type.getConstructionTypeArticles().contains(article)) {
				articles = new ArrayList<ConstructionTypeArticle>(type
						.getConstructionTypeArticles());
				orgArticle = articles.get(articles.indexOf(article));

				articleAttributes = article.getAttributes();
				orgArticleAttributes = orgArticle.getAttributes();
				for (ConstructionTypeArticleAttribute attribute : articleAttributes) {
					if (!orgArticleAttributes.contains(attribute)) {
						attribute.setConstructionTypeArticleAttributeId(null);
						orgArticle.addAttribute(attribute);
					}
				}
			}
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		// Navn
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(50);
		// Beskrivelse
		col = table.getColumnModel().getColumn(1);
		col.setPreferredWidth(100);
		// Produktområde
		table.getColumnExt(2).setPreferredWidth(100);

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		if (!isMasterOverview) {
			return "konstruksjonstype";
		}
		return "mastertype";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "ConstructionType";
	}

	/**
	 * Tabellmodell for garasjetype
	 * 
	 * @author atle.brekka
	 */
	private static final class ConstructionTypeTableModel extends
			AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private static final String[] COLUMNS = { "Navn", "Beskrivelse",
				"Produktområde" };

		/**
		 * @param listModel
		 */
		ConstructionTypeTableModel(ListModel listModel) {
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
			ConstructionType constructionType = (ConstructionType) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return constructionType.getName();
			case 1:
				return constructionType.getDescription();
			case 2:
				return constructionType.getProductArea();
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
				return String.class;
			case 2:
				return ProductArea.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * @param object
	 * @return feilstreng dersom feil
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(ConstructionType object) {
		String errorString = null;
		((ConstructionTypeManager) overviewManager)
				.lazyLoad(
						object,
						new LazyLoadConstructionTypeEnum[] { LazyLoadConstructionTypeEnum.ORDER });
		if (object.getOrders() != null && object.getOrders().size() != 0) {
			errorString = "Kan ikke slette garasjetype som brukes av ordre";
		}
		return new CheckObject(errorString, false);
	}

	/**
	 * Åpnervindu for å editere attributte
	 * 
	 * @param window
	 * @param presentationModel
	 */
	public void openAttributeView(WindowInterface window,
			PresentationModel presentationModel) {
		ConstructionTypeModel constructionTypeModel = (ConstructionTypeModel) presentationModel
				.getBean();

		AttributeViewHandler attributeViewHandler = new AttributeViewHandler(
				login, managerRepository);
		AttributeView attributeView = new AttributeView(attributeViewHandler,
				true);
		WindowInterface dialog = new JDialogAdapter(new JDialog(
				ProTransMain.PRO_TRANS_MAIN, "Attributt", true));
		dialog.setName("AttributeView");
		dialog.add(attributeView.buildPanel(dialog));
		dialog.pack();
		Util.locateOnScreenCenter(dialog);
		dialog.setVisible(true);

		List<Attribute> newAttributes = attributeView.getSelectedObjects();
		if (newAttributes != null && newAttributes.size() == 1) {
			String useAsUniversal = "";
			if (masterDialog) {
				useAsUniversal = (String) JOptionPane.showInputDialog(window
						.getComponent(),
						"Skal attributt gjelde alle gjeldende garasjetyper",
						"Skal gjelde alle gjelende grasjetype",
						JOptionPane.PLAIN_MESSAGE, null, new Object[] { "Ja",
								"Nei" }, null);
			}

			Attribute att = newAttributes.get(0);
			Object[] selectionValues = null;

			List<String> choices = att.getChoices();
			if (choices != null && choices.size() != 0) {
				selectionValues = choices.toArray();
			} else if (att.getYesNo() != null && att.getYesNo() > 0) {
				selectionValues = new Object[] { "Ja", "Nei" };
			}
			String attributeValue = (String) JOptionPane.showInputDialog(window
					.getComponent(), "Gi attributt verdi", "Attributt",
					JOptionPane.PLAIN_MESSAGE, null, selectionValues, null);

			String orderValue = (String) JOptionPane.showInputDialog(window
					.getComponent(), "Rekkefølge", "Attributt",
					JOptionPane.PLAIN_MESSAGE, null, null, null);
			Integer dialogOrder = null;

			if (orderValue != null && orderValue.length() != 0) {
				dialogOrder = Integer.valueOf(orderValue);
			}
			if (attributeValue == null || attributeValue.length() == 0) {
				Util.showErrorDialog(window.getComponent(), "Feil",
						"Attributt må ha verdi");
			} else {
				ConstructionTypeAttribute constructionTypeAttribute = constructionTypeModel
						.getArticleAttribute(newAttributes.get(0),
								attributeValue, dialogOrder);
				if (masterDialog && useAsUniversal.equalsIgnoreCase("Ja")) {
					universalAttributes.add(constructionTypeAttribute);
				}
				ArrayListModel bufferedAttributes = (ArrayListModel) presentationModel
						.getBufferedValue(ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES);
				attributeList.add(constructionTypeAttribute);
				bufferedAttributes.add(constructionTypeAttribute);
				presentationModel
						.setBufferedValue(
								ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES,
								bufferedAttributes);
			}
		}
	}

	/**
	 * Åpner vindu for å editere artikler
	 * 
	 * @param window
	 * @param presentationModel
	 */
	@SuppressWarnings("unchecked")
	public void openArticleView(WindowInterface window,
			PresentationModel presentationModel) {
		List<ArticleType> articles = (List<ArticleType>) presentationModel
				.getValue(ConstructionTypeModel.PROPERTY_ARTICLES);

		ConstructionType currentConstructionType = (ConstructionType) presentationModel
				.getBufferedValue(AbstractModel.PROPERTY_OBJECT);

		ArticleTypeViewHandler articleTypeViewHandler = new ArticleTypeViewHandler(
				login, managerRepository, articles);
		ArticleTypeView articleTypeView = new ArticleTypeView(
				articleTypeViewHandler, true, false);
		WindowInterface dialog = new JDialogAdapter(Util.getDialog(window,
				"Artikkel", true));
		dialog.add(articleTypeView.buildPanel(dialog));
		dialog.pack();
		Util.locateOnScreenCenter(dialog);
		dialog.setVisible(true);

		List<ArticleType> newArticles = articleTypeView.getSelectedObjects();
		showArticleAttributeView(newArticles, currentConstructionType, window,
				presentationModel);

	}

	/**
	 * Viser vindu med alle attributter for valgt artikkel
	 * 
	 * @param newArticles
	 * @param currentConstructionType
	 * @param window
	 * @param presentationModel
	 */
	private void showArticleAttributeView(List<ArticleType> newArticles,
			ConstructionType currentConstructionType, WindowInterface window,
			PresentationModel presentationModel) {
		if (newArticles != null && newArticles.size() == 1) {
			String useAsUniversal = "";
			if (masterDialog) {
				useAsUniversal = (String) JOptionPane.showInputDialog(window
						.getComponent(),
						"Skal artikkel gjelde alle gjeldende garasjetyper",
						"Skal gjelde alle gjeldende garasjetyper",
						JOptionPane.PLAIN_MESSAGE, null, new Object[] { "Ja",
								"Nei" }, null);
			}

			ArticleType newArticleType = newArticles.get(0);
			managerRepository
					.getArticleTypeManager()
					.lazyLoad(
							newArticleType,
							new LazyLoadArticleTypeEnum[] { LazyLoadArticleTypeEnum.ATTRIBUTE });

			Set<ArticleTypeAttribute> attributes = newArticleType
					.getArticleTypeAttributes();

			ConstructionTypeArticle constructionTypeArticleMain = new ConstructionTypeArticle(
					null, currentConstructionType, newArticleType, null, null,
					null, null, null, null);

			Set<ConstructionTypeArticleAttribute> constructionTypeArticleAttributes = new HashSet<ConstructionTypeArticleAttribute>();

			for (ArticleTypeAttribute articleTypeAttribute : attributes) {
				constructionTypeArticleAttributes
						.add(new ConstructionTypeArticleAttribute(null,
								constructionTypeArticleMain,
								articleTypeAttribute, null, null));
			}

			if (constructionTypeArticleAttributes.size() != 0) {
				openAttributeView(constructionTypeArticleMain,
						constructionTypeArticleAttributes);

				constructionTypeArticleMain
						.setAttributes(constructionTypeArticleAttributes);
			}

			setArticleRefs(newArticleType, constructionTypeArticleMain);

			ConstructionTreeNode rootNode = (ConstructionTreeNode) constructionTreeTableModel
					.getRoot();
			ConstructionType constructionType = (ConstructionType) rootNode
					.getObject();

			if (constructionType.getConstructionTypeArticles() != null) {
				constructionType.getConstructionTypeArticles().add(
						constructionTypeArticleMain);
			} else {
				HashSet<ConstructionTypeArticle> set = new HashSet<ConstructionTypeArticle>();
				set.add(constructionTypeArticleMain);
				constructionType.setConstructionTypeArticles(set);
			}
			constructionTreeTableModel.fireChanged();

			if (masterDialog && useAsUniversal.equalsIgnoreCase("Ja")) {
				universalArticles.add(constructionTypeArticleMain);
			}

			BufferedValueModel bufferedArticles = presentationModel
					.getBufferedModel(ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ARTICLES);
			bufferedArticles.setValue(new ArrayListModel(constructionType
					.getConstructionTypeArticles()));

		}
	}

	/**
	 * Setter referanser mellom artikler
	 * 
	 * @param articleType
	 * @param constructionTypeArticleMain
	 */
	private void setArticleRefs(ArticleType articleType,
			ConstructionTypeArticle constructionTypeArticleMain) {
		managerRepository.getArticleTypeManager().lazyLoad(
				articleType,
				new LazyLoadArticleTypeEnum[] {
						LazyLoadArticleTypeEnum.ATTRIBUTE,
						LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE });
		Set<ArticleTypeArticleType> articleRefs = articleType
				.getArticleTypeArticleTypes();
		ConstructionTypeArticle constructionTypeArticle;
		Set<ConstructionTypeArticleAttribute> constructionTypeArticleAttributes;
		Set<ConstructionTypeArticle> constructionTypeRefs = new HashSet<ConstructionTypeArticle>();
		if (articleRefs != null && articleRefs.size() != 0) {
			for (ArticleTypeArticleType articleRef : articleRefs) {
				constructionTypeArticle = new ConstructionTypeArticle(null,
						null, articleRef.getArticleTypeRef(), null,
						constructionTypeArticleMain, null, null, null, null);

				ArticleType articleTypeRef = articleRef.getArticleTypeRef();
				managerRepository
						.getArticleTypeManager()
						.lazyLoad(
								articleTypeRef,
								new LazyLoadArticleTypeEnum[] { LazyLoadArticleTypeEnum.ATTRIBUTE });
				Set<ArticleTypeAttribute> attributes = articleTypeRef
						.getArticleTypeAttributes();

				if (attributes != null) {
					constructionTypeArticleAttributes = new HashSet<ConstructionTypeArticleAttribute>();

					for (ArticleTypeAttribute attribute : attributes) {
						constructionTypeArticleAttributes
								.add(new ConstructionTypeArticleAttribute(null,
										constructionTypeArticle, attribute,
										null, null));
					}

					openAttributeView(constructionTypeArticle,
							constructionTypeArticleAttributes);

					constructionTypeArticle
							.setAttributes(constructionTypeArticleAttributes);

					setArticleRefs(articleRef.getArticleTypeRef(),
							constructionTypeArticle);
				}
				constructionTypeRefs.add(constructionTypeArticle);
			}
			constructionTypeArticleMain
					.setConstructionTypeArticles(constructionTypeRefs);
		}
	}

	/**
	 * Åpner vindu for å sette attributtverdier
	 * 
	 * @param aConstructionTypeArticle
	 * @param constructionTypeArticleAttributes
	 */
	private void openAttributeView(
			ConstructionTypeArticle aConstructionTypeArticle,
			Set<ConstructionTypeArticleAttribute> constructionTypeArticleAttributes) {
		ConstructionArticleAttributeViewHandler constructionArticleAttributeViewHandler = new ConstructionArticleAttributeViewHandler();

		UpdateAttributeView constructionArticleAttributeView = new UpdateAttributeView(
				aConstructionTypeArticle, ConstructionTypeArticleAttribute
						.convertToInterface(constructionTypeArticleAttributes),
				constructionArticleAttributeViewHandler);

		JDialog dialog = Util.getDialog(window, "Attriutter", true);

		WindowInterface dialogAttributes = new JDialogAdapter(dialog);
		dialogAttributes.setName("ConstructionArticleAttributeView");
		dialogAttributes.add(constructionArticleAttributeView
				.buildPanel(dialogAttributes));
		dialogAttributes.pack();
		Util.locateOnScreenCenter(dialogAttributes);
		dialogAttributes.setVisible(true);
		dialogAttributes.dispose();
	}

	/**
	 * Klassesom håndterer oppdatering av attributter
	 * 
	 * @author atle.brekka
	 */
	private class AttributeUpdate implements Updateable {
		/**
         *
         */
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public AttributeUpdate(PresentationModel aPresentationModel) {

			presentationModel = aPresentationModel;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
		 */
		public boolean doDelete(WindowInterface window) {
			boolean deleted = true;

			ConstructionTypeAttribute attribute = (ConstructionTypeAttribute) attributeSelection
					.getSelection();

			((ConstructionTypeManager) overviewManager)
					.lazyLoadAttribute(
							attribute,
							new LazyLoadConstructionTypeAttributeEnum[] { LazyLoadConstructionTypeAttributeEnum.ORDER_LINE_ATTRIBUTE });

			if (attribute != null) {
				if (attribute.getOrderLineAttributes() != null
						&& attribute.getOrderLineAttributes().size() != 0) {
					deleted = false;
					Util
							.showErrorDialog(window, "Feil",
									"Kan ikke slette attributt som er referert til av en ordre");
				} else {
					attributeList.remove(attribute);
					attribute.setConstructionType(null);
					presentationModel
							.setBufferedValue(
									ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES,
									attributeList);
				}
			} else {
				deleted = false;
			}
			return deleted;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doNew(WindowInterface window) {
			openAttributeView(window, presentationModel);

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
	 * Klasse som håndterer oppdatering av artikkeltype
	 * 
	 * @author atle.brekka
	 */
	private class ArticleUpdate implements Updateable {
		/**
         *
         */
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public ArticleUpdate(PresentationModel aPresentationModel) {

			presentationModel = aPresentationModel;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
		 */
		public boolean doDelete(WindowInterface window) {
			boolean deleted = true;
			if (selectedNode != null && !selectedNode.isLeaf()) {
				ConstructionTypeArticle article = (ConstructionTypeArticle) selectedNode
						.getObject();
				((ConstructionTypeManager) overviewManager)
						.lazyLoadArticle(
								article,
								new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.ORDER_LINE });
				if (article.getOrderLines() != null
						&& article.getOrderLines().size() != 0) {
					deleted = false;
					Util
							.showErrorDialog(window, "Feil",
									"Kan ikke slette artikkel som er referert til av en ordre");
				} else {

					ConstructionType constructionType = (ConstructionType) ((ConstructionTreeNode) constructionTreeTableModel
							.getRoot()).getObject();
					constructionType.getConstructionTypeArticles().remove(
							article);
					article.setConstructionType(null);

					presentationModel
							.setBufferedValue(
									ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ARTICLES,
									new ArrayListModel(
											ConstructionTypeModel
													.clonedConstructionTypeArticles(constructionType
															.getConstructionTypeArticles())));

					constructionTreeTableModel.fireChanged();
				}
			} else {
				deleted = false;
			}
			return deleted;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doNew(no.ugland.utransprod.gui.WindowInterface)
		 */
		public void doNew(WindowInterface window) {
			openArticleView(window, presentationModel);

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
	 * Åpner dialog for editering av attributt
	 * 
	 * @param window
	 * @param presentationModel
	 */
	void doEditAttribute(WindowInterface window,
			PresentationModel presentationModel) {
		ConstructionTypeAttribute attribute = (ConstructionTypeAttribute) attributeSelection
				.getSelection();

		if (attribute != null) {
			String useAsUniversal = "";
			if (masterDialog) {
				useAsUniversal = (String) JOptionPane.showInputDialog(window
						.getComponent(),
						"Skal endring gjelde alle gjeldende garasjetyper",
						"Skal gjelde alle gjelende grasjetype",
						JOptionPane.PLAIN_MESSAGE, null, new Object[] { "Ja",
								"Nei" }, null);
			}
			String attributeValue;
			if (attribute.isYesNo()) {
				attributeValue = (String) JOptionPane.showInputDialog(window
						.getComponent(), "Gi ny verdi", "Endre verdi",
						JOptionPane.QUESTION_MESSAGE, null, new Object[] {
								"Ja", "Nei" }, attribute.getAttributeValue());
			} else if (attribute.getChoices() != null
					&& attribute.getChoices().size() != 0) {

				attributeValue = (String) Util.showOptionsDialogCombo(window,
						attribute.getChoices(), "Endre verdi", true, attribute
								.getAttributeValue());

			} else {

				attributeValue = JOptionPane.showInputDialog(window
						.getComponent(), "Gi ny verdi", attribute
						.getAttributeValue());
			}

			if (attributeValue != null) {
				String orderValue = JOptionPane.showInputDialog(window
						.getComponent(), "Rekkefølge", attribute
						.getDialogOrder());

				if (attributeValue.length() != 0) {
					attribute.setAttributeValue(attributeValue);

				}

				if (orderValue != null && orderValue.length() != 0) {
					attribute.setDialogOrder(Integer.valueOf(orderValue));

				} else {
					attribute.setDialogOrder(null);
				}

				if (masterDialog && useAsUniversal != null
						&& useAsUniversal.equalsIgnoreCase("Ja")) {
					universalChangedAttributes.add(attribute);
				}

				presentationModel
						.setBufferedValue(
								ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES,
								attributeList);
			}
		}
	}

	/**
	 * Åpner vindu for editering av attributt for artikkeltype
	 * 
	 * @param window
	 * @param presentationModel
	 */
	void doEditArticle(WindowInterface window,
			PresentationModel presentationModel) {
		String useAsUniversal = "";
		if (masterDialog) {
			useAsUniversal = (String) JOptionPane.showInputDialog(window
					.getComponent(),
					"Skal endring gjelde alle gjeldende garasjetyper",
					"Skal gjelde alle gjeldende garasjetyper",
					JOptionPane.PLAIN_MESSAGE, null,
					new Object[] { "Ja", "Nei" }, null);
		}
		if (selectedNode != null && selectedNode.isLeaf()) {

			BufferedValueModel bufferedArticles = presentationModel
					.getBufferedModel(ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ARTICLES);

			ConstructionTypeArticleAttribute attribute = (ConstructionTypeArticleAttribute) selectedNode
					.getObject();

			String attributeValue;
			if (attribute.isYesNo()) {
				attributeValue = (String) JOptionPane.showInputDialog(window
						.getComponent(), "Gi ny verdi", "Endre verdi",
						JOptionPane.QUESTION_MESSAGE, null, new Object[] {
								"Ja", "Nei" }, attribute
								.getConstructionTypeArticleValue());
			} else if (attribute.getChoices() != null
					&& attribute.getChoices().size() != 0) {

				attributeValue = (String) Util.showOptionsDialogCombo(window,
						attribute.getChoices(), "Endre verdi", true, attribute
								.getConstructionTypeArticleValue());

			} else {

				attributeValue = JOptionPane.showInputDialog(window
						.getComponent(), "Gi ny verdi", attribute
						.getConstructionTypeArticleValue());
			}

			if (attributeValue != null) {
				String orderValue = JOptionPane.showInputDialog(window
						.getComponent(), "Rekkefølge", attribute
						.getDialogOrder());

				if (attributeValue.length() != 0) {
					attribute.setConstructionTypeArticleValue(attributeValue);
				}

				if (orderValue != null && orderValue.length() != 0) {
					attribute.setDialogOrder(Integer.valueOf(orderValue));
				} else {
					attribute.setDialogOrder(null);
				}
				if (masterDialog && useAsUniversal.equalsIgnoreCase("Ja")) {
					universalChangedArticleAttributes.add(attribute);
				}
				ConstructionType constructiontype = (ConstructionType) ((ConstructionTreeNode) constructionTreeTableModel
						.getRoot()).getObject();
				bufferedArticles.setValue(new ArrayListModel(constructiontype
						.getConstructionTypeArticles()));
			}
		} else if (selectedNode != null) {
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalSeparatorAlwaysShown(false);
			decimalFormat.setParseIntegerOnly(true);
			BufferedValueModel bufferedArticles = presentationModel
					.getBufferedModel(ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ARTICLES);

			ConstructionTypeArticle article = (ConstructionTypeArticle) selectedNode
					.getObject();

			String numberOfValue = null;

			if (article.getNumberOfItems() != null) {
				numberOfValue = decimalFormat
						.format(article.getNumberOfItems());
			}

			numberOfValue = JOptionPane.showInputDialog(window.getComponent(),
					"Gi antall", numberOfValue);

			String orderValue = JOptionPane.showInputDialog(window
					.getComponent(), "Rekkefølge", article.getDialogOrder());

			if (numberOfValue != null && numberOfValue.length() != 0) {
				article.setNumberOfItems(Integer.valueOf(numberOfValue.replace(
						',', '.')));

			}
			if (orderValue != null) {
				if (orderValue.length() != 0) {
					article.setDialogOrder(Integer.valueOf(orderValue));
				} else {
					article.setDialogOrder(null);
				}
			}
			if (masterDialog && useAsUniversal.equalsIgnoreCase("Ja")) {
				universalChangedArticles.add(article);
			}

			ConstructionType constructiontype = (ConstructionType) ((ConstructionTreeNode) constructionTreeTableModel
					.getRoot()).getObject();
			bufferedArticles.setValue(new ArrayListModel(constructiontype
					.getConstructionTypeArticles()));
		}
	}

	/**
	 * Klasesom håndterer trykk på knapp for editering av attributt
	 * 
	 * @author atle.brekka
	 */
	private class EditAttributeAction extends AbstractAction {
		/**
         *
         */
		private static final long serialVersionUID = 1L;

		/**
         *
         */
		private WindowInterface window;

		/**
         *
         */
		private PresentationModel presentationModel;

		/**
		 * @param aWindow
		 * @param aPresentationModel
		 */
		public EditAttributeAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Editer...");
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			doEditAttribute(window, presentationModel);

		}
	}

	/**
	 * Klasse som håndterer trykk på knapp for editering av artikkel
	 * 
	 * @author atle.brekka
	 */
	private class EditArticleAction extends AbstractAction {
		/**
         *
         */
		private static final long serialVersionUID = 1L;

		/**
         *
         */
		private WindowInterface window;

		/**
         *
         */
		private PresentationModel presentationModel;

		/**
		 * @param aWindow
		 * @param aPresentationModel
		 */
		public EditArticleAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Editer...");
			window = aWindow;
			presentationModel = aPresentationModel;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			doEditArticle(window, presentationModel);

		}
	}

	/**
	 * Henter klasse for håndtering av dobbelklikk på attributt
	 * 
	 * @param window
	 * @param presentationModel
	 * @return dobbelklikkhåndterer
	 */
	public MouseListener getAttributeDoubleClickHandler(WindowInterface window,
			PresentationModel presentationModel) {
		return new AttributeDoubleClickHandler(window, presentationModel);
	}

	/**
	 * Henter klase for håndtering av dobbeltklikk på attributt for artikkel
	 * 
	 * @param window
	 * @param presentationModel
	 * @return dobbelklikkhåndterer
	 */
	public MouseListener getArticleDoubleClickHandler(WindowInterface window,
			PresentationModel presentationModel) {
		return new ArticleDoubleClickHandler(window, presentationModel);
	}

	/**
	 * Klasesom håndterer dobbelklikk på attributt
	 * 
	 * @author atle.brekka
	 */
	final class AttributeDoubleClickHandler extends MouseAdapter {
		/**
         *
         */
		private WindowInterface window;

		/**
         *
         */
		private PresentationModel presentationModel;

		/**
		 * @param aWindow
		 * @param aPresentationModel
		 */
		public AttributeDoubleClickHandler(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (hasWriteAccess()) {
				if (SwingUtilities.isLeftMouseButton(e)
						&& e.getClickCount() == 2)
					doEditAttribute(window, presentationModel);
			}
		}
	}

	/**
	 * Klasse som håndterer dobbelklikk på attributt for artikkel
	 * 
	 * @author atle.brekka
	 */
	final class ArticleDoubleClickHandler extends MouseAdapter {
		/**
         *
         */
		private WindowInterface window;

		/**
         *
         */
		private PresentationModel presentationModel;

		/**
		 * @param aWindow
		 * @param aPresentationModel
		 */
		public ArticleDoubleClickHandler(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (hasWriteAccess()) {
				if (SwingUtilities.isLeftMouseButton(e)
						&& e.getClickCount() == 2)
					doEditArticle(window, presentationModel);
			}
		}
	}

	/**
	 * Enabler/disabler knapper for attributter
	 */
	protected void updateActionEnablement() {
		if (hasWriteAccess()) {
			boolean hasSelection = attributeSelection.hasSelection();

			buttonEditAttribute.setEnabled(hasSelection);
			buttonRemoveAttribute.setEnabled(hasSelection);
		}

	}

	/**
	 * Henter klasse som håndterer valg i liste
	 * 
	 * @return PropertyChangeListener
	 */
	public PropertyChangeListener getEmptySelectionHandler() {
		return new SelectionEmptyHandler();
	}

	/**
	 * Klasse som håndtere valg i liste
	 * 
	 * @author atle.brekka
	 */
	protected class SelectionEmptyHandler implements PropertyChangeListener {

		/**
		 * @param evt
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			updateActionEnablement();
		}
	}

	/**
	 * Henter tabell som skal vise artikler for garasjetype
	 * 
	 * @param presentationModel
	 * @return tretabell
	 */
	public JXTreeTable getTreeTable(PresentationModel presentationModel) {
		ConstructionType tmpType = (ConstructionType) presentationModel
				.getBufferedValue(AbstractModel.PROPERTY_OBJECT);

		ConstructionType clonedType = ConstructionTypeModel
				.cloneConstructionType(tmpType);
		constructionTreeTableModel = new ConstructionTreeTableModel(clonedType);
		treeTable = new JXTreeTable(constructionTreeTableModel);
		return treeTable;
	}

	/**
	 * Kloner attributter for artikkel
	 * 
	 * @param original
	 * @return klonet liste
	 */

	/**
	 * Enabler/disabler knapper for artikkeltype
	 */
	void updateArticleButtons() {
		if (hasWriteAccess()) {
			TreePath path = treeTable.getPathForRow(treeTable.getSelectedRow());
			buttonEditArticle.setEnabled(false);
			buttonRemoveArticle.setEnabled(false);
			if (path != null) {
				selectedNode = (ConstructionTreeNode) path
						.getLastPathComponent();
				if (selectedNode.isLeaf()) {
					buttonEditArticle.setEnabled(true);
				} else {
					buttonRemoveArticle.setEnabled(true);
					buttonEditArticle.setEnabled(true);
				}
			}
		}
	}

	/**
	 * Henter klasse som håndterer valg i liste over artikkeltyper
	 * 
	 * @return ListSelectionListner
	 */
	public ListSelectionListener getArticleSelectionListener() {
		return new ArticleSelectionListener();
	}

	/**
	 * Klasse som håndterer valg i listen over artikkeltyper
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
	 * Kopier fra master
	 * 
	 * @param presentationModel
	 * @param window
	 */
	void doCopyMaster(PresentationModel presentationModel,
			WindowInterface window) {
		ConstructionTreeNode rootNode = (ConstructionTreeNode) constructionTreeTableModel
				.getRoot();
		ConstructionType constructionType = (ConstructionType) rootNode
				.getObject();

		if (constructionType.getProductArea() == null) {
			Util.showErrorDialog(window, "Ikke definert produktområde",
					"Det er ikke satt noe produktområde");
			return;
		}
		ConstructionType master = ((ConstructionTypeManager) overviewManager)
				.findMaster(constructionType.getProductArea());

		if (master != null) {

			ConstructionTypeModel constructionTypeModel = (ConstructionTypeModel) presentationModel
					.getBean();
			ConstructionType currentConstructionType = constructionTypeModel
					.getObject();

			((ConstructionTypeManager) overviewManager).lazyLoadTree(master);
			Set<ConstructionTypeArticle> articles = ConstructionTypeModel
					.copyConstructionTypeArticles(currentConstructionType,
							master.getConstructionTypeArticles(), null);

			if (constructionType.getConstructionTypeArticles() != null
					&& articles != null) {
				constructionType.getConstructionTypeArticles().addAll(articles);
			} else {
				if (articles != null) {
					HashSet<ConstructionTypeArticle> set = new HashSet<ConstructionTypeArticle>();
					set.addAll(articles);
					constructionType.setConstructionTypeArticles(set);
				}
			}
			constructionTreeTableModel.fireChanged();

			BufferedValueModel bufferedArticles = presentationModel
					.getBufferedModel(ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ARTICLES);
			bufferedArticles.setValue(new ArrayListModel(constructionType
					.getConstructionTypeArticles()));

			Set<ConstructionTypeAttribute> attributes = ConstructionTypeModel
					.copyConstructionTypeAttributes(currentConstructionType,
							master.getConstructionTypeAttributes());

			attributeList.addAll(attributes);
			presentationModel
					.setBufferedValue(
							ConstructionTypeModel.PROPERTY_CONSTRUCTION_TYPE_ATTRIBUTES,
							attributeList);

		}
	}

	/**
	 * Kopier fra master
	 * 
	 * @author atle.brekka
	 */
	private class CopyMasterAction extends AbstractAction {
		/**
         *
         */
		private static final long serialVersionUID = 1L;

		/**
         *
         */
		private JButton copyButton;

		/**
         *
         */
		private PresentationModel presentationModel;

		/**
         *
         */
		private WindowInterface window;

		/**
		 * @param copyMasterButton
		 * @param aPresentationModel
		 * @param aWindow
		 */
		public CopyMasterAction(JButton copyMasterButton,
				PresentationModel aPresentationModel, WindowInterface aWindow) {
			super("Kopier master");
			window = aWindow;
			this.copyButton = copyMasterButton;
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			doCopyMaster(presentationModel, window);
			copyButton.setEnabled(false);

		}
	}

	/**
	 * Sjekker om dialog er for master
	 * 
	 * @return true dersom master
	 */
	public boolean isMaster() {
		return masterDialog;
	}

	/**
	 * Klasse som sammenlikner attributter basert på navn
	 * 
	 * @author atle.brekka
	 */
	class AttributeComparator implements Comparator<ConstructionTypeAttribute> {

		/**
		 * @param o1
		 * @param o2
		 * @return -1,0 eller 1
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
		public int compare(ConstructionTypeAttribute o1,
				ConstructionTypeAttribute o2) {
			return o1.getAttributeName().compareTo(o2.getAttributeName());
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		if (masterDialog) {
			return UserUtil.hasWriteAccess(userType, "Master garasjetype");
		}
		return UserUtil.hasWriteAccess(userType, "Garasjetype");

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
	protected AbstractEditView<ConstructionTypeModel, ConstructionType> getEditView(
			AbstractViewHandler<ConstructionType, ConstructionTypeModel> handler,
			ConstructionType object, boolean searching) {
		((ConstructionTypeManager) overviewManager).lazyLoadTree(object);
		return new EditConstructionTypeView(this, object, searching);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#initObjects()
	 */
	@Override
	protected void initObjects() {
		if (!loaded) {
			setFiltered(false);
			objectSelectionList.clearSelection();
			objectList.clear();
			List<ConstructionType> allObjects;
			if (!isMasterOverview) {
				allObjects = overviewManager.findAll();
			} else {
				allObjects = ((ConstructionTypeManager) overviewManager)
						.findAllMasters();
			}
			if (allObjects != null) {
				objectList.addAll(allObjects);
			}
			noOfObjects = objectList.getSize();
			if (table != null) {
				table.scrollRowToVisible(0);
			}
		}
	}

	@Override
	String getAddString() {
		return null;
	}

}
