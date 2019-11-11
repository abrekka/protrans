package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.tree.TreePath;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.ProTransRuntimeException;
import no.ugland.utransprod.gui.ArticleTypeView;
import no.ugland.utransprod.gui.AttributeDataType;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrdlnView;
import no.ugland.utransprod.gui.OverviewView;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.UpdateAttributeView;
import no.ugland.utransprod.gui.UpdateOrderLineView;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.DeleteButton;
import no.ugland.utransprod.gui.buttons.NewButton;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.ExternalOrderModel;
import no.ugland.utransprod.gui.model.FlushListener;
import no.ugland.utransprod.gui.model.ICostableModel;
import no.ugland.utransprod.gui.model.OrderLineTreeNode;
import no.ugland.utransprod.gui.model.OrderLineTreeTableModel;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.gui.model.OrderWrapper;
import no.ugland.utransprod.gui.model.UpdateableListener;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ArticleTypeArticleType;
import no.ugland.utransprod.model.ArticleTypeAttribute;
import no.ugland.utransprod.model.Articleable;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.ConstructionTypeArticle;
import no.ugland.utransprod.model.ConstructionTypeArticleAttribute;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.IntelleV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.Udsalesmall;
import no.ugland.utransprod.service.ArticleTypeManager;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.enums.LazyLoadArticleTypeEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeArticleEnum;
import no.ugland.utransprod.service.enums.LazyLoadConstructionTypeEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTreeTable;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.value.BufferedValueModel;
import com.jgoodies.validation.util.ValidationUtils;

/**
 * Hjelpeklasse for visning av artikler for ordre.
 * 
 * @author atle.brekka
 * @param <T>
 * @param <E>
 */
public class OrderArticleViewHandler<T, E> implements FlushListener {
	private static final Integer PROBABILITY_100 = 100;

	private PresentationModel presentationModel;

	private OrderLineTreeTableModel<T, E> orderLineTreeTableModel;

	private JXTreeTable treeTable;

	private JButton buttonRemoveArticle;

	private JButton buttonAddArticle;

	private JButton buttonEditArticle;

	private OrderLineTreeNode selectedNode;

	private boolean isFlushing = false;

	private boolean searching = false;

	private List<Component> components = new ArrayList<Component>();

	private boolean enabled = true;
	private JButton buttonImportOrderLines;

	private Login login;
	private ManagerRepository managerRepository;

	private List<CostChangeListener> costChangeListeners = new ArrayList<CostChangeListener>();
	private boolean brukOrdrelinjelinjer;

	/**
	 * @param a         ()PresentationModel
	 * @param search
	 * @param aUserType
	 */
	public OrderArticleViewHandler(final PresentationModel aPresentationModel, final boolean search, final Login aLogin,
			ManagerRepository aManagerRepository, boolean brukOrdrelinjelinjer) {
		login = aLogin;
		managerRepository = aManagerRepository;
		presentationModel = aPresentationModel;

		searching = search;
		this.brukOrdrelinjelinjer = brukOrdrelinjelinjer;

	}

	/**
	 * Henter knapp for å legge til artikkel
	 * 
	 * @param window
	 * @param listener
	 * @return knapp
	 */
	public JButton getAddArticleButton(final WindowInterface window, final UpdateableListener listener) {
		if (buttonAddArticle == null) {
			buttonAddArticle = new NewButton("artikkel", new ArticleUpdate(listener), window);
			buttonAddArticle.setName("ButtonAddArticle");
			components.add(buttonAddArticle);
		}
		return buttonAddArticle;
	}

	public JButton getButtonShowOrdln(WindowInterface window) {
		JButton button = new JButton(new ShowOrdlnAction(window));
		button.setName("ButtonShowOrdln");
		components.add(button);
		return button;
	}

	/**
	 * Henter knapp for å fjerne artikkel
	 * 
	 * @param window
	 * @param listener
	 * @return knapp
	 */
	public JButton getRemoveArticleButton(WindowInterface window, UpdateableListener listener) {
		buttonRemoveArticle = new DeleteButton("artikkel", new ArticleUpdate(listener), window);
		buttonRemoveArticle.setName("ButtonRemveArticle");
		buttonRemoveArticle.setEnabled(false);
		components.add(buttonRemoveArticle);
		return buttonRemoveArticle;
	}

	/**
	 * Henter knapp for å editere artikkel
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getEditArticleButton(WindowInterface window) {
		buttonEditArticle = new JButton(new EditArticleAction(window));
		buttonEditArticle.setName("ButtonEditArticle");
		buttonEditArticle.setEnabled(false);
		components.add(buttonEditArticle);
		return buttonEditArticle;
	}

	/**
	 * Lager knapp for å editere alle artikler
	 * 
	 * @return knapp
	 */
	public JButton getEditAllButton(WindowInterface window) {
		JButton buttonEditAll = new JButton(new EditAllOrderAction(window));
		components.add(buttonEditAll);
		buttonEditAll.setName("ButtonEditAll");
		return buttonEditAll;
	}

	/**
	 * Lager knapp for å bestille artikler
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonExternalOrder(WindowInterface window) {
		JButton buttonExternalOrder = new JButton(new ExternalOrderAction(window));
		components.add(buttonExternalOrder);
		return buttonExternalOrder;
	}

	/**
	 * Henter tretabell for visning av artikler
	 * 
	 * @param window
	 * @return tretabell
	 */
	@SuppressWarnings("unchecked")
	public JXTreeTable getTreeTable(WindowInterface window) {
		if (presentationModel == null) {
			throw new ProTransRuntimeException("PresentationModel er ikke initiert");
		}
		ICostableModel<T, E> costableModel = (ICostableModel) presentationModel.getBean();
		orderLineTreeTableModel = new OrderLineTreeTableModel(new OrderWrapper(costableModel), brukOrdrelinjelinjer);
		treeTable = new JXTreeTable(orderLineTreeTableModel);
		treeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		treeTable.setColumnControlVisible(true);
		treeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		treeTable.addMouseListener(getOrderLineDoubleClickHandler(window));
		components.add(treeTable);

		setConstructionTypeListener(window);

		return treeTable;
	}

	private void setConstructionTypeListener(WindowInterface window) {
		if (presentationModel.getBufferedValue(ICostableModel.PROPERTY_DEVIATION) == null) {
			presentationModel.getBufferedModel(OrderModel.PROPERTY_CONSTRUCTION_TYPE).addPropertyChangeListener("value",
					new ConstructionTypeChangeListener(window));
		}
	}

	/**
	 * Setter komponenter enablet/disablet
	 * 
	 * @param enable
	 */
	public void setComponentEnablement(boolean enable) {
		enabled = enable;
		for (Component component : components) {
			component.setEnabled(enable);
		}
		updateArticleButtons();
	}

	/**
	 * Henter klasse for håndtering av dobbeltklikk i tretabell
	 * 
	 * @param window
	 * @return muselytter
	 */
	public MouseListener getOrderLineDoubleClickHandler(WindowInterface window) {
		return new OrderLineDoubleClickHandler(window);
	}

	/**
	 * Henter klasse for håndtering av valg i tretabell
	 * 
	 * @return lytter
	 */
	public ListSelectionListener getArticleSelectionListener() {
		return new ArticleSelectionListener();
	}

	/**
	 * Enabler/disabler knapper for artikler
	 */
	void updateArticleButtons() {
		if (enabled) {
			TreePath path = treeTable.getPathForRow(treeTable.getSelectedRow());
			buttonEditArticle.setEnabled(false);
			buttonRemoveArticle.setEnabled(false);
			if (path != null) {
				selectedNode = (OrderLineTreeNode) path.getLastPathComponent();
				if (selectedNode.isLeaf()) {
					buttonEditArticle.setEnabled(true);
				} else {
					buttonRemoveArticle.setEnabled(true);
					buttonEditArticle.setEnabled(true);
				}
			}
			// if (buttonImportOrderLines != null) {
			// buttonImportOrderLines.setEnabled(isOrder90Probability());
			// }
		}
	}

	/**
	 * Editering av artikkel
	 * 
	 * @param window
	 */
	@SuppressWarnings("unchecked")
	void doEditArticle(WindowInterface window) {
		if (selectedNode != null && selectedNode.isLeaf()) {

			BufferedValueModel bufferedOrderLines = presentationModel
					.getBufferedModel(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);

			OrderLineAttribute attribute = (OrderLineAttribute) selectedNode.getObject();
			OrderLine orderLine = attribute.getOrderLine();
			orderLine.setHasArticle(null);
			orderLine.setAttributeInfo(null);
			orderLine.removeAllOrderLineAttributeInfo();
			orderLine.setIsDefault(null);

			String attributeValue;
			if (attribute.isYesNo()) {
				attributeValue = (String) JOptionPane.showInputDialog(window.getComponent(), "Gi ny verdi",
						"Endre verdi", JOptionPane.QUESTION_MESSAGE, null, new Object[] { "Ja", "Nei" },
						attribute.getAttributeValue());
			} else if (attribute.getChoices() != null && attribute.getChoices().size() != 0) {
				attributeValue = (String) JOptionPane.showInputDialog(window.getComponent(), "Gi ny verdi",
						"Endre verdi", JOptionPane.QUESTION_MESSAGE, null, attribute.getChoices().toArray(),
						attribute.getAttributeValue());
			} else {

				attributeValue = JOptionPane.showInputDialog(window.getComponent(), "Gi ny verdi",
						attribute.getAttributeValue());
			}

			if (attributeValue != null && attributeValue.length() != 0) {
				if (!StringUtils.isEmpty(attribute.getAttributeDataType()) && !AttributeDataType
						.valueOf(StringUtils.upperCase(attribute.getAttributeDataType())).isValid(attributeValue)) {
					Util.showErrorDialog(window, "Feil datatype", "Attributt har feil datatype");

				} else {
					attribute.setAttributeValue(attributeValue);

					OrderWrapper<T, E> orderWrapper = (OrderWrapper<T, E>) ((OrderLineTreeNode) orderLineTreeTableModel
							.getRoot()).getObject();
					calculateAttributes(orderWrapper.getOrderLines());
					bufferedOrderLines.setValue(new ArrayListModel(orderWrapper.getOrderLines()));
				}
			}
		} else if (selectedNode != null) {
			BufferedValueModel bufferedOrderLines = presentationModel
					.getBufferedModel(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);

			OrderLine line = (OrderLine) selectedNode.getObject();

			String metric = line.getMetric();
			if (metric != null) {
				metric = "(" + metric + ")";
			} else {
				metric = "";
			}

			String numberOfValue = JOptionPane.showInputDialog(window.getComponent(), "Gi antall" + metric,
					line.getNumberOfItems());

			if (!ValidationUtils.isNumeric(numberOfValue)) {
				Util.showErrorDialog(window, "Feil", "Antall må være et tall");
				return;
			}

			if (numberOfValue != null && numberOfValue.length() != 0) {
				line.setNumberOfItems(Integer.valueOf(numberOfValue.replace(',', '.')));

				OrderWrapper orderWrapper = (OrderWrapper) ((OrderLineTreeNode) orderLineTreeTableModel.getRoot())
						.getObject();
				bufferedOrderLines.setValue(new ArrayListModel(orderWrapper.getOrderLines()));
			}
		}
	}

	/**
	 * Åpner vindu for artikkel
	 * 
	 * @param window
	 */
	@SuppressWarnings("unchecked")
	public void openArticleView(WindowInterface window) {
		List<ArticleType> articles = (List<ArticleType>) presentationModel.getValue(ICostableModel.PROPERTY_ARTICLES);

		Articleable costable = (Articleable) presentationModel.getBufferedValue(AbstractModel.PROPERTY_OBJECT);

		ArticleTypeViewHandler articleTypeViewHandler = new ArticleTypeViewHandler(login, managerRepository, articles);
		ArticleTypeView articleTypeView = new ArticleTypeView(articleTypeViewHandler, true, false);
		WindowInterface dialog = new JDialogAdapter(new JDialog(ProTransMain.PRO_TRANS_MAIN, "Artikkel", true));
		dialog.setName("ArticleView");
		dialog.add(articleTypeView.buildPanel(dialog));
		dialog.pack();
		Util.locateOnScreenCenter(dialog);
		dialog.setVisible(true);

		List<ArticleType> newArticles = articleTypeView.getSelectedObjects();
		showArticleAttributeView(newArticles, costable, window);

	}

	/**
	 * Viser vindu for å sette verdier for attributter
	 * 
	 * @param newArticles
	 * @param costable
	 * @param window
	 */
	private void showArticleAttributeView(List<ArticleType> newArticles, Articleable costable, WindowInterface window) {
		if (newArticles == null || newArticles.size() == 0) {
			return;
		}
		ArticleType newArticleType = getNewArticle(newArticles);
		Set<ArticleTypeAttribute> attributes = newArticleType.getArticleTypeAttributes();

		OrderLine orderLineMain = getNewOrderLineFromArticle(costable, newArticleType);

		if (attributes != null && attributes.size() != 0) {
			getAttributeValues(attributes, orderLineMain);
		} else {
			getNumberOfArticle(window, orderLineMain);
		}

		setOrderLineRefs(newArticleType, orderLineMain);

		orderLineMain.setArticlePath(orderLineMain.getGeneratedArticlePath());

		updateOrderLineTreeNode(orderLineMain);

	}

	public OrderLine getNewOrderLineFromArticle(Articleable costable, ArticleType newArticleType) {
		OrderLine orderLineMain = OrderLine.getInstance(costable.getOrder(), newArticleType, costable.getDeviation(),
				costable.getPostShipment());
		costable.getOrder().setOrderComplete(null);
		return orderLineMain;
	}

	private void getNumberOfArticle(WindowInterface window, OrderLine orderLineMain) {
		String metric = orderLineMain.getMetric();
		if (metric != null) {
			metric = "Gi antall(" + metric + ")";
		} else {
			metric = "Gi antall";
		}
		String numberOfValue = JOptionPane.showInputDialog(window.getComponent(), metric,
				orderLineMain.getNumberOfItems());

		if (numberOfValue != null && numberOfValue.length() != 0) {
			orderLineMain.setNumberOfItems(Integer.valueOf(numberOfValue.replace(',', '.')));

		}
	}

	private void getAttributeValues(Set<ArticleTypeAttribute> attributes, OrderLine orderLineMain) {
		Set<OrderLineAttribute> orderLineAttributes = new HashSet<OrderLineAttribute>();
		for (ArticleTypeAttribute articleTypeAttribute : attributes) {
			createNewOrderLineAttribute(orderLineMain, orderLineAttributes, articleTypeAttribute);
		}

		orderLineMain.setOrderLineAttributes(orderLineAttributes);

		openAttributeView(orderLineMain, orderLineAttributes);
	}

	private ArticleType getNewArticle(List<ArticleType> newArticles) {
		ArticleType newArticleType = newArticles.get(0);
		managerRepository.getArticleTypeManager().lazyLoad(newArticleType, new LazyLoadArticleTypeEnum[] {
				LazyLoadArticleTypeEnum.ATTRIBUTE, LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE });
		return newArticleType;
	}

	private void createNewOrderLineAttribute(OrderLine orderLineMain, Set<OrderLineAttribute> orderLineAttributes,
			ArticleTypeAttribute articleTypeAttribute) {
		if (!articleTypeAttribute.getIsInactive()) {
			orderLineAttributes.add(new OrderLineAttribute(null, orderLineMain, null, null, articleTypeAttribute, null,
					null, articleTypeAttribute.getAttribute().getName()));
		}
	}

	@SuppressWarnings("unchecked")
	private void updateOrderLineTreeNode(OrderLine orderLineMain) {
		OrderLineTreeNode rootNode = (OrderLineTreeNode) orderLineTreeTableModel.getRoot();
		OrderWrapper<T, E> orderWrapper = (OrderWrapper<T, E>) rootNode.getObject();

		if (orderWrapper.getOrderLines() != null) {
			orderWrapper.getOrderLines().add(orderLineMain);
		}
		orderLineTreeTableModel.fireChanged(brukOrdrelinjelinjer);

		BufferedValueModel bufferedArticles = presentationModel
				.getBufferedModel(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);
		bufferedArticles.setValue(new ArrayListModel(orderWrapper.getOrderLines()));
	}

	/**
	 * Setter referanser til ordre
	 * 
	 * @param articleType
	 * @param orderLineMain
	 */
	private void setOrderLineRefs(ArticleType articleType, OrderLine orderLineMain) {
		ArticleTypeManager articleTypeManager = (ArticleTypeManager) ModelUtil.getBean("articleTypeManager");
		articleTypeManager.lazyLoad(articleType,
				new LazyLoadArticleTypeEnum[] { LazyLoadArticleTypeEnum.ARTICLE_TYPE_ARTICLE_TYPE });
		Set<ArticleTypeArticleType> articleRefs = articleType.getArticleTypeArticleTypes();
		OrderLine orderLine;
		Set<OrderLineAttribute> orderLineAttributes;
		Set<OrderLine> orderLineRefs = new HashSet<OrderLine>();
		if (articleRefs != null) {
			for (ArticleTypeArticleType articleRef : articleRefs) {
				orderLine = OrderLine.getInstance(
						(Order) presentationModel.getBufferedValue(ICostableModel.PROPERTY_ORDER),
						articleRef.getArticleTypeRef(), orderLineMain,
						(Deviation) presentationModel.getBufferedValue(ICostableModel.PROPERTY_DEVIATION));

				ArticleType articleTypeRef = articleRef.getArticleTypeRef();
				articleTypeManager.lazyLoad(articleTypeRef,
						new LazyLoadArticleTypeEnum[] { LazyLoadArticleTypeEnum.ATTRIBUTE });
				Set<ArticleTypeAttribute> attributes = articleTypeRef.getArticleTypeAttributes();

				if (attributes != null) {
					orderLineAttributes = new HashSet<OrderLineAttribute>();

					for (ArticleTypeAttribute attribute : attributes) {
						orderLineAttributes.add(new OrderLineAttribute(null, orderLine, null, null, attribute, null,
								null, attribute.getAttribute().getName()));
					}

					openAttributeView(orderLine, orderLineAttributes);

					orderLine.setOrderLineAttributes(orderLineAttributes);

					setOrderLineRefs(articleRef.getArticleTypeRef(), orderLine);
				}
				orderLineRefs.add(orderLine);
				orderLine.setArticlePath(orderLine.getGeneratedArticlePath());
			}
			orderLineMain.setOrderLines(orderLineRefs);
		}
	}

	/**
	 * Åpner vindu for å sette verdier på attributter
	 * 
	 * @param aOrderLine
	 * @param orderLineAttributes
	 */
	private void openAttributeView(OrderLine aOrderLine, Set<OrderLineAttribute> orderLineAttributes) {
		ConstructionArticleAttributeViewHandler constructionArticleAttributeViewHandler = new ConstructionArticleAttributeViewHandler();

		UpdateAttributeView constructionArticleAttributeView = new UpdateAttributeView(aOrderLine,
				OrderLineAttribute.convertToInterface(orderLineAttributes), constructionArticleAttributeViewHandler);
		WindowInterface dialogAttributes = new JDialogAdapter(
				new JDialog(ProTransMain.PRO_TRANS_MAIN, "Attributter", true));
		dialogAttributes.setName("OrderLineAttributeView");
		dialogAttributes.add(constructionArticleAttributeView.buildPanel(dialogAttributes));
		dialogAttributes.pack();
		Util.locateOnScreenCenter(dialogAttributes);
		dialogAttributes.setVisible(true);
		dialogAttributes.dispose();
	}

	/**
	 * Åpner vindu for
	 * 
	 * @param orderLines
	 */
	@SuppressWarnings("unchecked")
	private void openOrderLineView(Collection<OrderLine> orderLines, WindowInterface window) {
		ConstructionArticleAttributeViewHandler constructionArticleAttributeViewHandler = new ConstructionArticleAttributeViewHandler();

		ICostableModel<Order, OrderModel> costableModel = (ICostableModel<Order, OrderModel>) presentationModel
				.getBean();
		costableModel = costableModel.getBufferedObjectModel(presentationModel);

		if (hasAttributes(orderLines)) {

			UpdateOrderLineView orderLineView = new UpdateOrderLineView(orderLines,
					constructionArticleAttributeViewHandler,
					costableModel.getCustomerFirstName() + " " + costableModel.getCustomerLastName() + " "
							+ costableModel.getDeliveryAddress() + " " + costableModel.getPostalCode() + " "
							+ costableModel.getPostOffice());
			JDialog dialog = Util.getDialog(window, "Attributter", true);
			WindowInterface dialogAttributes = new JDialogAdapter(dialog);
			dialogAttributes.setName("UpdateOrderLineView");
			dialogAttributes.add(orderLineView.buildPanel(dialogAttributes));
			dialogAttributes.pack();
			Util.locateOnScreenCenter(dialogAttributes);
			dialogAttributes.setVisible(true);
			dialogAttributes.dispose();
		}
	}

	private boolean hasAttributes(Collection<OrderLine> orderLines) {
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getOrderLineAttributes().size() != 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Endrer garasjetype
	 * 
	 * @param newConstructionType
	 */
	@SuppressWarnings("unchecked")
	void changeConstructionType(ConstructionType newConstructionType, WindowInterface window) {
		if (newConstructionType != null) {
			ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
					.getBean(ConstructionTypeManager.MANAGER_NAME);
			OrderWrapper<T, E> rootOrderWrapper = (OrderWrapper<T, E>) ((OrderLineTreeNode) orderLineTreeTableModel
					.getRoot()).getObject();
			List<OrderLine> rootOrderOrderLines = new ArrayList<OrderLine>(rootOrderWrapper.getOrderLines());
			rootOrderWrapper.getOrderLines().clear();
			Set<OrderLine> orderLines = constructionTypeManager.getOrderLinesForNewConstructionType(rootOrderOrderLines,
					newConstructionType, (Order) presentationModel.getBufferedValue(ICostableModel.PROPERTY_ORDER),
					(Deviation) presentationModel.getBufferedValue(ICostableModel.PROPERTY_DEVIATION));

			if (orderLines != null) {
				openOrderLineView(orderLines, window);
			}
			BufferedValueModel bufferedOrderLines = presentationModel
					.getBufferedModel(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);

			if (orderLines != null && orderLines.size() != 0) {

				rootOrderWrapper.getOrderLines().addAll(orderLines);
				calculateAttributes(orderLines);
				bufferedOrderLines.setValue(new ArrayListModel(orderLines));
			} else {
				bufferedOrderLines.setValue(new ArrayListModel());
			}
			if (presentationModel.getBufferedValue(ICostableModel.PROPERTY_DEVIATION) == null) {
				presentationModel.setBufferedValue(OrderModel.PROPERTY_PRODUCT_AREA,
						newConstructionType.getProductArea());
			}

			orderLineTreeTableModel.fireChanged(brukOrdrelinjelinjer);
		}
	}

	/**
	 * Kalkulerer attributter dersom det er noen som har formel knyttet opp til seg
	 * 
	 * @param orderLines
	 */
	private void calculateAttributes(Collection<OrderLine> orderLines) {
		for (OrderLine orderLine : orderLines) {
			orderLine.calculateAttributes();
		}
	}

	/**
	 * Setter referanser mellom artikler for garasjetype
	 * 
	 * @param constructionArticle
	 * @param orderLineMain
	 */
	private void setOrderLineConstructionRefs(ConstructionTypeArticle constructionArticle, OrderLine orderLineMain) {
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");
		constructionTypeManager.lazyLoadArticle(constructionArticle, new LazyLoadConstructionTypeArticleEnum[] {
				LazyLoadConstructionTypeArticleEnum.CONSTRUCTION_TYPE_ARTICLES });
		Set<ConstructionTypeArticle> articleRefs = constructionArticle.getConstructionTypeArticles();
		OrderLine orderLine;
		Set<OrderLineAttribute> orderLineAttributes;
		Set<OrderLine> orderLineRefs = new LinkedHashSet<OrderLine>();
		if (articleRefs != null) {
			for (ConstructionTypeArticle articleRef : articleRefs) {
				orderLine = OrderLine.getInstance(
						(Order) presentationModel.getBufferedValue(ICostableModel.PROPERTY_ORDER), articleRef,
						orderLineMain, articleRef.getNumberOfItems(), articleRef.getDialogOrder(),
						(Deviation) presentationModel.getBufferedValue(ICostableModel.PROPERTY_DEVIATION));

				constructionTypeManager.lazyLoadArticle(articleRef,
						new LazyLoadConstructionTypeArticleEnum[] { LazyLoadConstructionTypeArticleEnum.ATTRIBUTES });
				Set<ConstructionTypeArticleAttribute> attributes = articleRef.getAttributes();

				if (attributes != null) {
					orderLineAttributes = new LinkedHashSet<OrderLineAttribute>();

					for (ConstructionTypeArticleAttribute attribute : attributes) {
						orderLineAttributes.add(new OrderLineAttribute(null, orderLine, attribute, null, null,
								attribute.getAttributeValue(), attribute.getDialogOrder(),
								attribute.getAttributeName()));
					}

					orderLine.setOrderLineAttributes(orderLineAttributes);

					setOrderLineConstructionRefs(articleRef, orderLine);
				}
				orderLineRefs.add(orderLine);
				orderLine.setArticlePath(orderLine.getGeneratedArticlePath());
			}
			orderLineMain.setOrderLines(orderLineRefs);
		}
	}

	/**
	 * Klasse som håndterer valg i artikkelliste
	 * 
	 * @author atle.brekka
	 */
	class ArticleSelectionListener implements ListSelectionListener {

		/**
		 * @see javax.swing.event.ListSelectionListener#valueChanged(javax.swing.event.ListSelectionEvent)
		 */
		public void valueChanged(ListSelectionEvent e) {
			if (enabled) {
				updateArticleButtons();
			}

		}

	}

	/**
	 * Klasse som håndterer dobbeltklikk i tretabell
	 * 
	 * @author atle.brekka
	 */
	final class OrderLineDoubleClickHandler extends MouseAdapter {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public OrderLineDoubleClickHandler(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {

			if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2 && enabled)
				doEditArticle(window);
		}
	}

	/**
	 * Klasse som håndterer oppdatering av artikkel
	 * 
	 * @author atle.brekka
	 */
	class ArticleUpdate implements Updateable {
		private UpdateableListener updateableListener;

		/**
		 * @param listener
		 */
		public ArticleUpdate(UpdateableListener listener) {

			updateableListener = listener;
		}

		/**
		 * @see no.ugland.utransprod.gui.Updateable#doDelete(no.ugland.utransprod.gui.WindowInterface)
		 */
		@SuppressWarnings("unchecked")
		public boolean doDelete(WindowInterface window) {
			boolean deleted = true;
			if (selectedNode != null && !selectedNode.isLeaf() && enabled) {
				OrderLine orderLine = (OrderLine) selectedNode.getObject();
				if (orderLine.getArticleType() == null && orderLine.getConstructionTypeArticle() == null) {
					deleted = false;
					Util.showErrorDialog(window.getComponent(), "Feil", "Kan ikke slette garasjetype");
				} else {
					OrderWrapper<T, E> orderWrapper = (OrderWrapper<T, E>) ((OrderLineTreeNode) orderLineTreeTableModel
							.getRoot()).getObject();
					orderWrapper.removeOrderLine(orderLine);

					BufferedValueModel bufferedOrderLines = presentationModel
							.getBufferedModel(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);
					bufferedOrderLines.setValue(new ArrayListModel(orderWrapper.getOrderLines()));
					orderLineTreeTableModel.fireChanged(brukOrdrelinjelinjer);
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
			if (enabled) {
				boolean doAdd = true;
				if (updateableListener != null) {
					doAdd = updateableListener.beforeAdded();
				}
				if (doAdd) {
					openArticleView(window);
				}
				if (updateableListener != null) {
					updateableListener.afterAdded();
				}
			}

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
	 * Klassesom håndterer editering av artikkel
	 * 
	 * @author atle.brekka
	 */
	private class EditArticleAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public EditArticleAction(WindowInterface aWindow) {
			super("Editer...");
			window = aWindow;

		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (enabled) {
				doEditArticle(window);
			}

		}
	}

	/**
	 * Klasse som håndterer endring av garasjetype.
	 * 
	 * @author atle.brekka
	 */
	class ConstructionTypeChangeListener implements PropertyChangeListener {
		private WindowInterface window;

		public ConstructionTypeChangeListener(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(final PropertyChangeEvent evt) {
			if (evt.getNewValue() == null && evt.getOldValue() == null) {
				return;
			}
			if (!isFlushing && !searching && enabled) {
				ConstructionType constructionType = lazyLoadConstructionType(evt);
				changeConstructionType(constructionType, window);
			}

		}

	}

	final ConstructionType lazyLoadConstructionType(final PropertyChangeEvent evt) {
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");
		ConstructionType constructionType = (ConstructionType) evt.getNewValue();
		constructionTypeManager.lazyLoad(constructionType,
				new LazyLoadConstructionTypeEnum[] { LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ARTICLE,
						LazyLoadConstructionTypeEnum.CONSTRUCTION_TYPE_ATTRIBUTE });
		return constructionType;
	}

	/**
	 * @see no.ugland.utransprod.gui.model.FlushListener#flushChanged(boolean)
	 */
	public void flushChanged(boolean flushing) {
		isFlushing = flushing;

	}

	/**
	 * Editer alle ordrelinjer
	 */
	@SuppressWarnings("unchecked")
	void doEditAllOrderLines(WindowInterface window) {
		Collection<OrderLine> bufferedOrderLines = (Collection<OrderLine>) presentationModel
				.getBufferedValue(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);

		OrderWrapper rootOrder = (OrderWrapper) ((OrderLineTreeNode) orderLineTreeTableModel.getRoot()).getObject();

		if (bufferedOrderLines != null && bufferedOrderLines.size() != 0) {
			List<OrderLine> lines = new ArrayList<OrderLine>();
			for (OrderLine line : bufferedOrderLines) {
				if (line.getOrderLineRef() == null) {
					lines.add(line);
				}
			}
			openOrderLineView(lines, window);

			BufferedValueModel bufferedOrderLinesModel = presentationModel
					.getBufferedModel(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);

			bufferedOrderLinesModel.setValue(new ArrayListModel(bufferedOrderLines));

			calculateAttributes(bufferedOrderLines);
			rootOrder.setOrderLines(new ArrayList<OrderLine>(bufferedOrderLines));

			orderLineTreeTableModel.fireChanged(brukOrdrelinjelinjer);
		}
	}

	/**
	 * Editer alle ordrelinjer
	 * 
	 * @author atle.brekka
	 */
	private class EditAllOrderAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public EditAllOrderAction(WindowInterface aWindow) {
			super("Editer alt...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (enabled) {
				doEditAllOrderLines(window);
			}

		}
	}

	/**
	 * Betilling
	 * 
	 * @author atle.brekka
	 */
	private class ExternalOrderAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ExternalOrderAction(WindowInterface aWindow) {
			super("Bestillinger...");

			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			ExternalOrderViewHandler externalOrderViewHandler = new ExternalOrderViewHandler(login, managerRepository,
					(Order) presentationModel.getBufferedValue(ICostableModel.PROPERTY_ORDER));
			OverviewView<ExternalOrder, ExternalOrderModel> externalOverviewView = new OverviewView<ExternalOrder, ExternalOrderModel>(
					externalOrderViewHandler);

			JDialog dialog = Util.getDialog(window, "Bestillinger", true);

			WindowInterface windowDialog = new JDialogAdapter(dialog);

			windowDialog.add(externalOverviewView.buildPanel(windowDialog));
			windowDialog.setSize(externalOrderViewHandler.getWindowSize());
			Util.locateOnScreenCenter(windowDialog);
			dialog.setVisible(true);

		}

	}

	private class ShowOrdlnAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public ShowOrdlnAction(WindowInterface aWindow) {
			super("Visma...");
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			showOrderLinesFromVisma(window);

		}
	}

	private void showOrderLinesFromVisma(WindowInterface window) {
		OrdlnViewHandler ordlnViewHandler = new OrdlnViewHandler(
				(String) presentationModel.getBufferedValue(OrderModel.PROPERTY_ORDER_NR));
		OrdlnView ordlnView = new OrdlnView(ordlnViewHandler);
		WindowInterface dialogWindow = new JDialogAdapter(Util.getDialog(window, "Ordrelinjer fra Visma", false));
		dialogWindow.add(ordlnView.buildPanel(dialogWindow));
		dialogWindow.pack();
		Util.locateOnScreenCenter(dialogWindow);
		dialogWindow.setVisible(true);
	}

	public JButton getButtonImportOrderLines(WindowInterface window) {
		buttonImportOrderLines = new JButton(new ImportOrderLinesAction(window));
		buttonImportOrderLines.setName("ButtonImportOrderLines");
		buttonImportOrderLines.setEnabled(true);
		// buttonImportOrderLines.setEnabled(isOrder90Probability());
		components.add(buttonImportOrderLines);
		return buttonImportOrderLines;
	}

	private boolean isOrder90Probability() {
		Integer probability = (Integer) presentationModel.getBufferedValue(OrderModel.PROPERTY_PROBABILITY);
		return probability != null && probability == 90 ? true : false;
	}

	private class ImportOrderLinesAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;

		public ImportOrderLinesAction(WindowInterface aWindow) {
			super("Importer artikler...");
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			Order order = ((OrderModel) presentationModel.getBean()).getObject();
			if (order.getPacklistReady() != null) {
				Util.showErrorDialog(window, "Pakkliste er ferdig",
						"Kan ikke importere artikler fordi pakkliste er ferdig!");
				return;
			}
			try {
				if (Util.showConfirmDialog(window, "Importere artikler på nytt?",
						"Dette vil slette alle artikler og importere dem på nytt, vil du fortsette?")) {
					importOrderLines();
				}
			} catch (ProTransException e1) {
				e1.printStackTrace();
				Util.showErrorDialog(window, "Feil", e1.getMessage());
			}

		}

	}

	private void importOrderLines() throws ProTransException {
		Order incomingOrder = ((OrderModel) presentationModel.getBean()).getObject();
		removeOrderLines(incomingOrder);
		managerRepository.getIncomingOrderManager().setOrderLines(incomingOrder, managerRepository);
		incomingOrder.setProbability(PROBABILITY_100);
		if (incomingOrder.getProductAreaGroup().getProductAreaGroupName().equalsIgnoreCase("Takstol")) {
			incomingOrder.setPacklistReady(Util.getCurrentDate());
		}

		Udsalesmall udsalesmall = managerRepository.getUdsalesmallManager().findByOrderNr(incomingOrder.getOrderNr());
		managerRepository.getIncomingOrderManager().setCustomerCost(incomingOrder, udsalesmall);

		// presentationModel.setBufferedValue(OrderModel.PROPERTY_PACKLIST_READY,
		// Util.getCurrentDate());
		if (incomingOrder.getOrderLines() != null) {
			resetOrderLineTreeNode(new ArrayList<OrderLine>(incomingOrder.getOrderLines()));
		}
		setOrderMaxTrossHeight(incomingOrder);
		presentationModel.setBufferedValue(OrderModel.PROPERTY_MAX_TROSS_HEIGHT,
				incomingOrder.getMaxTrossHeight() != null ? String.valueOf(incomingOrder.getMaxTrossHeight()) : null);
		fireCostChange(incomingOrder);
		updateArticleButtons();

		sjekkOmLinjerErDobbelt(incomingOrder);
	}

	private void sjekkOmLinjerErDobbelt(Order incomingOrder) {
		List<String> ordnoLnnoListe = Lists.newArrayList();

		for (OrderLine orderLine : incomingOrder.getOrderLines()) {
			if (orderLine.getOrdNo() != null && orderLine.getLnNo() != null) {
				String ordnoLnno = orderLine.getOrdNo() + ";" + orderLine.getLnNo();
				if (ordnoLnnoListe.contains(ordnoLnno)) {
					throw new ProTransException(
							String.format("Ordre %s inneholder duplikate ordrelinjer", incomingOrder.getOrderNr()));
				}
				ordnoLnnoListe.add(ordnoLnno);
			}
		}

	}

	private void setOrderMaxTrossHeight(Order order) {
		IntelleV intelleV = managerRepository.getIntelleVManager().findByOrdreNr(order.getOrderNr());
		if (intelleV != null && intelleV.getMaxHoyde() != null) {
			order.setMaxTrossHeight(intelleV.getMaxHoyde());
		}

	}

	private void fireCostChange(Order order) {
		for (CostChangeListener listener : costChangeListeners) {
			listener.costChanged(order);
		}

	}

	private void removeOrderLines(Order incomingOrder) throws ProTransException {
		Set<OrderLine> orderLines = incomingOrder.getOrderLines();
		List<OrderLine> ordrelinjerSomKanFjernes = Lists.newArrayList();
		boolean inneholderOrdrelinjeSomErPakket = false;
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getColli() == null) {
					ordrelinjerSomKanFjernes.add(orderLine);
				} else {
					inneholderOrdrelinjeSomErPakket = true;
				}
			}
			// orderLines.clear();
		}
		for (OrderLine orderLine : ordrelinjerSomKanFjernes) {
			orderLines.remove(orderLine);
		}

		managerRepository.getOrderManager().saveOrder(incomingOrder, true);
		if (inneholderOrdrelinjeSomErPakket) {
			Util.showMsgDialog(null, "Allerede pakket",
					"Noen ordrelinjer er allerede pakket og vil ikke bli importert på nytt");
		}
	}

	@SuppressWarnings("unchecked")
	private void resetOrderLineTreeNode(List<OrderLine> orderLines) {
		OrderLineTreeNode rootNode = (OrderLineTreeNode) orderLineTreeTableModel.getRoot();
		OrderWrapper<T, E> orderWrapper = (OrderWrapper<T, E>) rootNode.getObject();

		orderWrapper.setOrderLines(orderLines);

		orderLineTreeTableModel.fireChanged(brukOrdrelinjelinjer);

		BufferedValueModel bufferedArticles = presentationModel
				.getBufferedModel(ICostableModel.PROPERTY_ORDER_LINE_ARRAY_LIST_MODEL);
		bufferedArticles.setValue(new ArrayListModel(orderWrapper.getOrderLines()));
	}

	public void addCostChangeListener(CostChangeListener listener) {
		costChangeListeners.add(listener);

	}

}
