package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.AttachmentView;
import no.ugland.utransprod.gui.DeviationOverviewView;
import no.ugland.utransprod.gui.DeviationOverviewView2;
import no.ugland.utransprod.gui.DeviationOverviewViewFactory;
import no.ugland.utransprod.gui.FileView;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.OrderOverviewView;
import no.ugland.utransprod.gui.OrderPanelTypeEnum;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.ProductAreaGroupProvider;
import no.ugland.utransprod.gui.SearchAttributeView;
import no.ugland.utransprod.gui.UBColumnControlPopup;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditCommentView;
import no.ugland.utransprod.gui.edit.EditOrderView;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.Delelisteinfo;
import no.ugland.utransprod.gui.model.ListMultilineRenderer;
import no.ugland.utransprod.gui.model.OrderCommentModel;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.gui.model.OrderTableModel;
import no.ugland.utransprod.gui.model.Ordreinfo;
import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.gui.model.ProductionReportData;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.TextPaneRendererCustomer;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.importing.CuttingImport;
import no.ugland.utransprod.model.Assembly;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.ConstructionType;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Cutting;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.OfferAddress;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.OrderCost;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.model.PostShipment;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Project;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.service.AssemblyManager;
import no.ugland.utransprod.service.ConstructionTypeManager;
import no.ugland.utransprod.service.CustomerManager;
import no.ugland.utransprod.service.CuttingManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.OrdlnManager;
import no.ugland.utransprod.service.ProjectManager;
import no.ugland.utransprod.service.TransportManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.service.enums.LazyLoadPostShipmentEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.CommentTypeUtil;
import no.ugland.utransprod.util.FileExtensionFilter;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.PdfUtil;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ReportViewer;

import org.hibernate.Hibernate;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;
import org.springframework.orm.hibernate3.HibernateOptimisticLockingFailureException;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.toedter.calendar.JDateChooser;

import net.sf.jasperreports.engine.JRException;

/**
 * Klassesom håndtrer vindusvariable for visning av ordre
 * 
 * @author atle.brekka
 */
public class OrderViewHandler extends DefaultAbstractViewHandler<Order, OrderModel>
		implements ProductAreaGroupProvider, ViewHandlerExt<Order, OrderModel> {
	private NewOrderDoubleClickHandler newOrderDoubleClickHandler;
	private static final long serialVersionUID = 1L;

	private final ArrayListModel customerList;

	private final ArrayListModel orderPanelList;

	final SelectionInList orderPanelSelectionList;

	private List<ListDataListener> listDataListeners = new ArrayList<ListDataListener>();

	private Boolean searching = false;

	private JXTable tableOrders;

	private JCheckBox checkBoxLock;

	private OrderCostsViewHandler orderCostsViewHandler;

	private OrderArticleViewHandler<Order, OrderModel> orderArticleViewHandler;

	private boolean editEnabled = true;

	private OrderTableModel orderTableModel;

	private OrderTableModel orderPanelTableModel;

	private NumberOf numberOf;

	private SearchAttributeViewHandler viewHandler = null;

	private WindowInterface statisticsDialog;

	private JLabel labelFilterInfo;

	private DeviationOverviewView2 deviationOverviewView;

	protected List<Component> editComponents = new ArrayList<Component>();

	final ArrayListModel orderComments;

	private static List<ProductArea> productAreaList;

	private ArrayListModel constructionTypeList;

	private final PresentationModel presentationModelProductAreaGroup;
	// private ProductAreaGroup currentProductAreaGroup;

	private AttachmentViewHandler attachmentViewHandler;
	private Login login;

	private ManagerRepository managerRepository;
	private DeviationOverviewViewFactory deviationOverviewViewFactory;
	private DeviationViewHandlerFactory deviationViewHandlerFactory;
	private VismaFileCreator vismaFileCreator;
	private Multimap<String, String> colliSetup;

	/**
	 * @param notInitData
	 * @param aSupplierViewHandler
	 * @param userType
	 * @param aApplicationUser
	 */
	@Inject
	public OrderViewHandler(Login aLogin, ManagerRepository aManagerRepository,
			DeviationOverviewViewFactory aDeviationOverviewViewFactory,
			DeviationViewHandlerFactory aDeviationViewHandlerFactory, @Assisted boolean notInitData,
			VismaFileCreator vismaFileCreator) {
		super("Ordre", aManagerRepository.getOrderManager(), notInitData, aLogin.getUserType(), false);

		this.vismaFileCreator = vismaFileCreator;
		deviationViewHandlerFactory = aDeviationViewHandlerFactory;
		deviationOverviewViewFactory = aDeviationOverviewViewFactory;
		managerRepository = aManagerRepository;

		viewHandlerExt = this;
		login = aLogin;

		orderComments = new ArrayListModel();
		customerList = new ArrayListModel();

		orderPanelList = new ArrayListModel();

		orderPanelSelectionList = new SelectionInList((ListModel) orderPanelList);

		numberOf = new NumberOf(objectList.getSize(), objectList.getSize());

		productAreaList = new ArrayList<ProductArea>();
		constructionTypeList = new ArrayListModel();
		initProductAreaList();

		presentationModelProductAreaGroup = new PresentationModel(new ProductAreaGroupModel());
		presentationModelProductAreaGroup.addBeanPropertyChangeListener(new ProductAreaGroupChangeListener());
		this.colliSetup = ApplicationParamUtil.getColliSetup();
	}

	public ManagerRepository getManagerRepository() {
		return managerRepository;
	}

	/**
	 * Initierer liste med produktområder
	 */
	private void initProductAreaList() {
		if (productAreaList.size() == 0) {
			productAreaList.clear();

			List<ProductArea> productAreas = managerRepository.getProductAreaManager().findAll();
			if (productAreas != null) {
				productAreaList.addAll(productAreas);
			}
		}
	}

	/**
	 * Lazy laster ordre
	 * 
	 * @param order
	 * @param enums
	 */
	public void lazyLoadOrder(Order order, LazyLoadOrderEnum[] enums) {
		((OrderManager) overviewManager).lazyLoadOrder(order, enums);
	}

	/**
	 * Lazy laster ordertre
	 * 
	 * @param order
	 */
	public void lazyLoadOrderTree(Order order) {
		((OrderManager) overviewManager).lazyLoadTree(order);
	}

	/**
	 * Legger til lytter for data
	 * 
	 * @param listener
	 */
	public void addListDataListener(ListDataListener listener) {
		listDataListeners.add(listener);
	}

	/**
	 * Gir beskjed om at data har endret seg
	 */
	private void fireContentsChanged() {
		for (ListDataListener listener : listDataListeners) {
			listener.contentsChanged(new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, -1, -1));
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
	 */
	@Override
	public Order getNewObject() {
		return new Order();
	}

	/**
	 * Initierer liste med konstruksjonstyper
	 * 
	 * @param presentationModel
	 */
	void initConstructionTypeList(PresentationModel presentationModel) {
		constructionTypeList.clear();
		if (presentationModel.getBufferedValue(OrderModel.PROPERTY_PRODUCT_AREA) != null) {
			ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
					.getBean("constructionTypeManager");
			List<ConstructionType> types = constructionTypeManager.findByProductArea(
					(ProductArea) presentationModel.getBufferedValue(OrderModel.PROPERTY_PRODUCT_AREA));
			if (types != null) {
				constructionTypeList.addAll(types);
			}
		}
	}

	/**
	 * Lager komboboks med konstruksjonstyper
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxConstructionType(PresentationModel presentationModel) {
		initConstructionTypeList(presentationModel);
		JComboBox comboBox = new JComboBox(new ComboBoxAdapter((ListModel) constructionTypeList,
				presentationModel.getBufferedModel(OrderModel.PROPERTY_CONSTRUCTION_TYPE)));
		comboBox.setName("ComboBoxConstructionType");
		addEditComponent(comboBox);
		return comboBox;
	}

	/**
	 * Lager tekstfelt for pakkedato
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldWallPackageDate(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_ORDER_READY_WALL_STRING));
		textField.setName("TextFieldWallPackageDate");
		textField.setEnabled(false);
		return textField;
	}

	public JTextField getTextFieldTrossPackageDate(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_ORDER_READY_TROSS_STRING));
		textField.setName("TextFieldTrossPackageDate");
		textField.setEnabled(false);
		return textField;
	}

	public JTextField getTextFieldPackPackageDate(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_ORDER_READY_PACK_STRING));
		textField.setName("TextFieldPackPackageDate");
		textField.setEnabled(false);
		return textField;
	}

	/**
	 * Lager tekstfelt for pakket av
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldWallPackedBy(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_PACKED_BY));
		textField.setName("TextFieldPackedBy");
		textField.setEnabled(false);
		return textField;
	}

	public JTextField getTextFieldPackedByTross(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_PACKED_BY_TROSS));
		textField.setName("TextFieldPackedByTROSS");
		textField.setEnabled(false);
		return textField;
	}

	public JTextField getTextFieldPackedByPack(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_PACKED_BY_PACK));
		textField.setName("TextFieldPackedByPack");
		textField.setEnabled(false);
		return textField;
	}

	/**
	 * Lager tekstfelt for når gavl er ferdig
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldGavlDone(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_GAVL_DONE));
		textField.setName("TextFieldGavlDone");
		textField.setEnabled(false);
		return textField;
	}

	/**
	 * Lager tekstfelt for npr takstol er ferdig
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldTakstolDone(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_TAKSTOL_DONE));
		textField.setName("TextFieldTakstolDone");
		textField.setEnabled(false);
		return textField;
	}

	/**
	 * Lager tekstfelt for npr front er ferdig
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldFrontDone(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_FRONT_DONE));
		textField.setName("TextFieldFrontDone");
		textField.setEnabled(false);
		return textField;
	}

	/**
	 * Lager tekstfelt for når vegg er ferdig
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldVeggDone(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_VEGG_DONE));
		textField.setName("TextFieldVeggDone");
		textField.setEnabled(false);
		return textField;
	}

	/**
	 * Lager tekstfelt for monteringsdato
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldAssemblyDone(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_ASSEMBLY_DONE_STRING));
		textField.setName("TextFieldAssemblyDone");
		textField.setEnabled(false);
		return textField;
	}

	/**
	 * Lager tekstfelt for når takstoler er pakket
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldTakstolPackaged(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_TAKSTOL_PACKAGED));
		textField.setName("TextFieldTakstolPackaged");
		textField.setEnabled(false);
		return textField;
	}

	public JTextField getTextFieldRegistered(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_REGISTRATION_DATE_STRING));
		textField.setName("TextFieldRegistered");
		textField.setEnabled(false);
		return textField;
	}

	public JTextField getTextFieldLoadingDate(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_LOADING_DATE_STRING));
		textField.setName("TextFieldLoadingDate");
		textField.setEnabled(false);
		return textField;
	}

	public JTextField getTextFieldProjectNr(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_PROJECT_NR));
		textField.setName("TextFieldProjectNr");
		textField.setEnabled(false);
		return textField;
	}

	public JTextField getTextFieldProjectName(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_PROJECT_NAME));
		textField.setName("TextFieldProjectName");
		textField.setEnabled(false);
		return textField;
	}

	public final JDateChooser getProductionDateChooser(PresentationModel presentationModel) {
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setName("DateChooserProduction");
		addEditComponent(dateChooser);

		PropertyConnector connPaidDate = new PropertyConnector(dateChooser, "date",
				presentationModel.getBufferedModel(OrderModel.PROPERTY_PRODUCTION_DATE), "value");

		connPaidDate.updateProperty1();
		return dateChooser;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		if (orderTableModel == null) {
			orderTableModel = new OrderTableModel(objectSelectionList, OrderPanelTypeEnum.ORDER);
		}
		return orderTableModel;
	}

	/**
	 * Lager komboboks med produktområder
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxProductArea(PresentationModel presentationModel) {
		JComboBox comboBox;
		if (searching) {
			comboBox = Util.createComboBoxProductArea(
					presentationModel.getBufferedModel(OrderModel.PROPERTY_PRODUCT_AREA), true);

		} else {
			comboBox = Util.createComboBoxProductArea(
					presentationModel.getBufferedModel(OrderModel.PROPERTY_PRODUCT_AREA), false);
		}
		comboBox.setName("ComboBoxProductArea");
		addEditComponent(comboBox);
		presentationModel.getBufferedModel(OrderModel.PROPERTY_PRODUCT_AREA)
				.addPropertyChangeListener(new ProductAreaChangeListener(presentationModel));
		return comboBox;

	}

	public boolean openOrderView(Order order, boolean isSearching, WindowInterface window, boolean lettvekt) {
		return openOrderView(order, isSearching, window, false, lettvekt);
	}

	public boolean openOrderView(Order order, boolean isSearching, WindowInterface window, boolean forIncomingOrder,
			boolean lettvekt) {
		this.searching = isSearching;

		Project project = new Project();
		if (orderIsSaved(order)) {
			setEditEnabled(false);
			project = getProject(order, lettvekt);
		} else if (order.getProductArea() == null) {
			setEditEnabled(true);
			ProductArea productArea = login.getApplicationUser().getProductArea();
			if (productArea != null && !searching) {
				order.setProductArea(productArea);
			}
		}

		EditOrderView editOrderView = new EditOrderView(this, order, searching, project, vismaFileCreator, lettvekt);

		WindowInterface dialog = new JDialogAdapter(Util.getDialog(window, "Ordre", true));

		dialog.setName("EditOrderView");
		dialog.add(editOrderView.buildPanel(dialog));
		dialog.pack();

		Util.locateOnScreenCenter(dialog);
		if (!forIncomingOrder) {
			editOrderView.resetBuffering();
		}
		dialog.setVisible(true);

		if (editOrderViewIsSearching(editOrderView)) {
			OrderModel orderModel = editOrderView.getOrderModel();
			orderModel.viewToModel();

			try {
				setCustomerSearchData(order, dialog, orderModel);
			} catch (ProTransException e) {
				Util.showErrorDialog(dialog, "Feil", e.getMessage());
				return false;
			}

		}
		return editOrderView.isCanceled();
	}

	private void setCustomerSearchData(Order order, WindowInterface dialog, OrderModel orderModel)
			throws ProTransException {
		if (orderModel.getCustomerNr() != null || orderModel.getCustomerFirstName() != null
				|| orderModel.getCustomerLastName() != null) {
			Customer customer = new Customer();
			try {
				if (orderModel.getCustomerNr() != null && orderModel.getCustomerNr().length() != 0) {
					customer.setCustomerNr(Integer.valueOf(orderModel.getCustomerNr()));
				}
			} catch (NumberFormatException e) {
				throw new ProTransException("Kundenr må være tall");

			}
			customer.setFirstName(orderModel.getCustomerFirstName());
			customer.setLastName(orderModel.getCustomerLastName());
			order.setCustomer(customer);
		}
	}

	private boolean editOrderViewIsSearching(EditOrderView editOrderView) {
		return editOrderView.isSearch() && !editOrderView.isCanceled();
	}

	private Project getProject(Order order, boolean lettvekt) {
		Project project;
		if (!lettvekt) {
			lazyLoadOrder(order,
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS, LazyLoadOrderEnum.ORDER_LINES,
							LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.COMMENTS,
							LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.PROCENT_DONE });
		} else {
			lazyLoadOrder(order,
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS, // LazyLoadOrderEnum.ORDER_LINES,
							// LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,
							LazyLoadOrderEnum.COMMENTS, // LazyLoadOrderEnum.COLLIES,
							LazyLoadOrderEnum.PROCENT_DONE });
		}

		ProjectManager projectManager = (ProjectManager) ModelUtil.getBean("projectManager");
		project = projectManager.findByOrderNr(order.getOrderNr());
		if (project == null) {
			project = new Project();
		}
		return project;
	}

	private boolean orderIsSaved(Order order) {
		return order.getOrderId() != null;
	}

	public boolean openEditViewExt(Order object, boolean isSearching, WindowInterface parentWindow, boolean lettvekt) {
		boolean isCanceled = openOrderView(object, isSearching, parentWindow, lettvekt);

		if (this.searching && !isCanceled) {
			labelFilterInfo.setText("");
			updateViewList(object, parentWindow);
			numberOf.setNumbers(objectList.getSize(), noOfObjects);
		}
		return true;
	}

	public boolean saveObjectExt(AbstractModel<Order, OrderModel> objectModel, WindowInterface window) {
		OrderModel object = (OrderModel) objectModel;
		Order order = object.getObject();
		int index = objectList.indexOf(order);

		handleCustomer(object, order);

		try {
			// Skal bare settes dersom attributter lengde og bredde kan ha
			// forandret seg
			updateOrder(object, order);

			try {

				((OrderManager) overviewManager).saveOrder(order);
				if (!object.erLettvekt()) {
					vismaFileCreator.createVismaFileForProductionWeek(order);
				}
//				checkCollies(order, window);
			} catch (ProTransException e) {
				Util.showErrorDialog(window, "Feil", e.getMessage());
				e.printStackTrace();
				return true;
			}

			handleAssembly(object);
		} catch (HibernateOptimisticLockingFailureException e) {
			Util.showErrorDialog(window, "Feil", "Ordre er oppdatert av noen andre, oppdater før lagring");
			e.printStackTrace();
		}

		if (index < 0) {
			objectList.add(order);
			noOfObjects++;
		} else {
			objectSelectionList.fireContentsChanged(index, index);
		}

		fireContentsChanged();
		return true;
	}

	public void checkCollies(Order order, WindowInterface window) throws ProTransException {
		if (!Util.convertNumberToBoolean(order.getDefaultColliesGenerated())) {

			// packable = (Packable) overviewManager.merge(packable);
			List<Colli> collies = order.getColliList();
			List<OrderLine> orderLines = order.getOrderLineList();
			Colli tmpColli;

			tmpColli = new Colli(null, order, null, null, null, null, null, null, null,"Ordrevindu");
			if (collies == null) {
				collies = new ArrayList<Colli>();

			}
			// sjekk om kollier Takstol,Gavl,Gulvspon,Garasjepakke er med
			// for
			// ordre,
			// sjekk mot artikler

			Set<String> colliNames = colliSetup.keySet();
			if (colliNames != null) {
				for (String colliName : colliNames) {
					tmpColli.setColliName(colliName);
					if (!collies.contains(tmpColli)) {
						if (!Hibernate.isInitialized(order.getCollies())) {
							initializePackable(order);
						}
						if (shouldHaveColli(orderLines, colliSetup.get(colliName), order.getTransportable())) {
							Colli newColli = new Colli(null, tmpColli.getOrder(), tmpColli.getColliName(), null, null,
									null, tmpColli.getPostShipment(), null, null,"Ordrevindu");
							order.addColli(newColli);

							if (colliName.equalsIgnoreCase("Takstein")) {
								checkTakstein(order, orderLines, newColli, window);
							}
							managerRepository.getColliManager().saveColli(newColli);
						}
					}
				}
			}
			order.setDefaultColliesGenerated(1);
			overviewManager.saveObject(order);
			// setPackable(packable, null);
		}
	}

	private void checkTakstein(Order order, List<OrderLine> orderLines, Colli colli, WindowInterface window) {

		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				managerRepository.getOrderLineManager().lazyLoad(orderLine,
						new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
				if (orderLine.getArticleName().equalsIgnoreCase("Takstein")) {
					Set<OrderLineAttribute> attributes = orderLine.getOrderLineAttributes();
					if (attributes != null) {
						for (OrderLineAttribute attribute : attributes) {
							if (attribute.getAttributeName().equalsIgnoreCase("Sendes fra GG")
									&& (attribute.getAttributeValue() == null
											|| attribute.getAttributeValue().equalsIgnoreCase("Nei"))) {
								// ColliViewHandler colliViewHandler =
								// colliViewHandlers.get(colli);
								// ColliViewHandler colliViewHandler =
								// colliListViewHandler.getColliViewHandler(colli);
								try {
									// if (colliViewHandler != null) {
									// colliViewHandler.addOrderLine(orderLine,
									// 0);
									// } else {
									ColliViewHandler colliViewHandler = new ColliViewHandler("Kolli", colli,
											// (Packable)
											// presentationModelPackable.getBean(),
											order, login, managerRepository, window, vismaFileCreator);
									colliViewHandler.addOrderLine(orderLine, 0);
									// colliListViewHandler.putColliViewHandler(colli,
									// colliViewHandler);
									// colliViewHandlers.put(colli,
									// colliViewHandler);
									// colliViewHandler.addColliSelectionListener(this);
									// colliListViewHandler.addColliListener(colliViewHandler);
									// }
									// fireOrderLineRemoved(null);
									// colliListViewHandler.fireListChanged();
								} catch (ProTransException e) {
									Util.showErrorDialog(window, "Feil", e.getMessage());
									e.printStackTrace();
								}
							}
						}
					}
				}
			}
		}
	}

	private void initializePackable(Packable packable) {
		if (Order.class.isInstance(packable)) {
			managerRepository.getOrderManager().lazyLoadOrder((Order) packable,
					new LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES, LazyLoadOrderEnum.ORDER_LINES,
							LazyLoadOrderEnum.ORDER_COSTS, LazyLoadOrderEnum.COMMENTS });
		} else {
			managerRepository.getPostShipmentManager().lazyLoad((PostShipment) packable,
					new LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES });
		}

		// Manager manager = (Manager)
		// ModelUtil.getBean(packable.getManagerName());
		// manager.lazyLoad(packable, new LazyLoadEnum[][] { {
		// LazyLoadEnum.COLLIES, LazyLoadEnum.NONE },
		// { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE }, {
		// LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE } });

	}

	private boolean shouldHaveColli(List<OrderLine> orderLines, Collection<String> articlenames,
			Transportable transportable) {
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				if (orderLine.getHasArticle() == null) {
					orderLineManager.lazyLoad(orderLine,
							new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
				}
				if (articlenames.contains(orderLine.getArticleName()) && orderLine.hasArticle()
						&& orderLine.belongTo(transportable)) {
					return true;
				}
			}
		}
		return false;
	}

	private void handleAssembly(final OrderModel object) {
		if (object.getAssembly() != null && object.getAssembly().getAssemblyWeek() != null) {
			AssemblyManager assemblyManager = (AssemblyManager) ModelUtil.getBean("assemblyManager");
			assemblyManager.saveAssembly(object.getAssembly());
		}
	}

	private void updateOrder(final OrderModel object, final Order order) {
		if (object.getInfo() == null || object.canChangeInfo()) {
			if (!Hibernate.isInitialized(order.getOrderLines())) {
				managerRepository.getOrderManager().lazyLoadOrder(order,
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES,
								LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES, LazyLoadOrderEnum.COMMENTS,
								LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.ORDER_COSTS,
								LazyLoadOrderEnum.COLLIES });
			}
			order.setInfo(order.orderLinesToString());
			order.setSpecialConcern(order.getOrderLineSpecialConcerns());
			setOrderLineDefault(order);
		}
		if (object.canChangeStatus()) {
			setOrderstatus(order);
			// order.setStatus(null);

		}

		if (order.getOrderId() == null) {
			order.setAgreementDate(Util.getCurrentDate());
		}
		if (order.getRegistrationDate() == null) {
			order.setRegistrationDate(Util.getCurrentDate());
		}
		order.cacheComments();
	}

	private void setOrderstatus(Order order) {
		// PostShipmentManager postShipmentManager = (PostShipmentManager)
		// ModelUtil.getBean(PostShipmentManager.MANAGER_NAME);
		// CustTrManager custTrManager = (CustTrManager)
		// ModelUtil.getBean(CustTrManager.MANAGER_NAME);
		// OrdlnManager ordlnManager = (OrdlnManager)
		// ModelUtil.getBean(OrdlnManager.MANAGER_NAME);
		Map<String, StatusCheckerInterface<Transportable>> statusCheckers = Util
				.getStatusCheckersTransport(managerRepository);
		Map<String, String> statusMap;
		//
		String status;
		StatusCheckerInterface<Transportable> checker;
		// boolean orderLoaded = false;
		// boolean needToSave = false;
		//
		// transportable.setCustTrs(custTrManager.findByOrderNr(transportable.getOrder().getOrderNr()));
		//
		statusMap = Util.createStatusMap(order.getStatus());
		for (String checkerName : statusCheckers.keySet()) {
			checker = statusCheckers.get(checkerName);
			// status = statusMap.get(checker.getArticleName());
			//
			// if (status == null) {
			// needToSave = true;
			// if (!orderLoaded && transportable instanceof Order) {
			// managerRepository.getOrderManager().lazyLoadOrder(order, new
			// LazyLoadOrderEnum[] {
			// LazyLoadOrderEnum.COLLIES,
			// LazyLoadOrderEnum.ORDER_LINES,
			// LazyLoadOrderEnum.ORDER_LINE_ATTRIBUTES,
			// LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,
			// LazyLoadOrderEnum.COMMENTS,
			// LazyLoadOrderEnum.PROCENT_DONE
			// });
			// orderLoaded = true;
			// } else if (!orderLoaded && transportable instanceof PostShipment)
			// {
			// postShipmentManager.lazyLoad((PostShipment) transportable, new
			// LazyLoadPostShipmentEnum[] { LazyLoadPostShipmentEnum.COLLIES,
			// LazyLoadPostShipmentEnum.ORDER_LINES,
			// LazyLoadPostShipmentEnum.ORDER_LINE_ORDER_LINES,
			// LazyLoadPostShipmentEnum.ORDER_COMMENTS });
			// orderLoaded = true;
			// }
			status = checker.getArticleStatus(order);
			//
			// }
			statusMap.put(checker.getArticleName(), status);
			//
			// }
			// if (!Hibernate.isInitialized(transportable.getOrderLines())) {
			// ((OrderManager) overviewManager).lazyLoadOrder((Order)
			// transportable, new
			// LazyLoadOrderEnum[] { LazyLoadOrderEnum.COLLIES,
			// LazyLoadOrderEnum.ORDER_LINES,
			// LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES,
			// LazyLoadOrderEnum.COMMENTS,
			// LazyLoadOrderEnum.PROCENT_DONE });
			// }
			// OrderLine takstol = transportable.getOrderLine("Takstoler");
			// if (takstol != null) {
			// Ordln ordln = ordlnManager.findByOrdNoAndLnNo(takstol.getOrdNo(),
			// takstol.getLnNo());
			// if (ordln != null && ordln.getPurcno() != null) {
			// Ord ord = ordlnManager.findByOrdNo(ordln.getPurcno());
			// if (ord != null) {
			// transportable.setTakstolKjopOrd(ord);
			// }
			// }
		}
		//
		order.setStatus(Util.statusMapToString(statusMap));
		//
		// if (transportable.getComment() == null) {
		// needToSave = true;
		// cacheComment(transportable, window, !orderLoaded);
		// orderLoaded = true;
		// }
		//
		// if (needToSave) {
		// if (transportable instanceof Order) {
		// try {
		// ((OrderManager) overviewManager).saveOrder((Order) transportable);
		// } catch (ProTransException e) {
		// Util.showErrorDialog(window, "Feil", e.getMessage());
		// e.printStackTrace();
		// }
		// } else {
		// postShipmentManager.savePostShipment((PostShipment) transportable);
		// }
		// }
		// if (transportable instanceof Order &&
		// !Hibernate.isInitialized(((Order)
		// transportable).getProcentDones())) {
		// ((OrderManager) overviewManager).lazyLoadOrder(((Order)
		// transportable),
		// new LazyLoadOrderEnum[] { LazyLoadOrderEnum.PROCENT_DONE });
		// }

	}

	private void handleCustomer(final OrderModel object, final Order order) {
		if (object.getCustomer() == null || object.getCustomer().getCustomerId() == null) {
			CustomerManager customerManager = (CustomerManager) ModelUtil.getBean("customerManager");
			Customer customer = new Customer(null,
					object.getCustomerNr() != null ? Integer.valueOf(object.getCustomerNr()) : null,
					object.getCustomerFirstName(), object.getCustomerLastName(), null);

			customerManager.saveCustomer(customer);
			order.setCustomer(customer);
			setFlushing(true);
			setFlushing(false);
		}
	}

	/**
	 * Setter om ordre er default eller ikke
	 * 
	 * @param order
	 */
	private void setOrderLineDefault(Order order) {

		Set<OrderLine> orderLines = order.getOrderLines();
		if (orderLines != null) {
			for (OrderLine orderLine : orderLines) {
				checkVismaStorage(orderLine);
				orderLine.isDefault();

				if (orderLine.getAttributeInfo() == null) {
					orderLine.setAttributeInfo(orderLine.getAttributesAsString());
				}
			}
		}
	}

	private void checkVismaStorage(OrderLine orderLine) {
		OrdlnManager ordlnManager = (OrdlnManager) ModelUtil.getBean(OrdlnManager.MANAGER_NAME);
		orderLine.setIsDefault(ordlnManager.isOrderLineInStorage(orderLine.getOrdNo(), orderLine.getLnNo()));

	}

	/**
	 * Henter kundeliste
	 * 
	 * @return kundeliste
	 */
	@SuppressWarnings("unchecked")
	public List<Customer> getCustomerList() {
		CustomerManager customerManager = (CustomerManager) ModelUtil.getBean("customerManager");
		customerList.clear();
		customerList.add(null);
		customerList.addAll(customerManager.findAll());
		return customerList;
	}

	/**
	 * Henter garasjetyper
	 * 
	 * @return garasjetyper
	 */
	public List<ConstructionType> getConstructionTypeList() {
		ConstructionTypeManager constructionTypeManager = (ConstructionTypeManager) ModelUtil
				.getBean("constructionTypeManager");
		return constructionTypeManager.findAll();
	}

	/**
	 * Henter transportliste
	 * 
	 * @param onlyNew
	 * @return transportliste
	 */
	public List<Transport> getTransportList(boolean onlyNew) {
		TransportManager transportManager = (TransportManager) ModelUtil.getBean("transportManager");
		ArrayList<Transport> transportList = new ArrayList<Transport>();
		transportList.add(null);
		List<Transport> transports;
		if (onlyNew) {
			transports = transportManager.findNewTransports();
		} else {
			transports = transportManager.findAll();
		}
		if (transports != null) {
			transportList.addAll(transports);
		}

		return transportList;
	}

	/**
	 * Henter alle ordre med montering
	 * 
	 * @return liste med monteringsordre
	 */
	public List<Assembly> getAssemblyList() {
		AssemblyManager assemblyManager = (AssemblyManager) ModelUtil.getBean("assemblyManager");
		ArrayList<Assembly> assemblyList = new ArrayList<Assembly>();
		assemblyList.add(null);
		assemblyList.addAll(assemblyManager.findAll());
		return assemblyList;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Ordre";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "250dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(1000, 520);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		// Kunde
		TableColumn col = table.getColumnModel().getColumn(0);
		col.setPreferredWidth(150);

		// ordernr
		col = table.getColumnModel().getColumn(1);
		col.setPreferredWidth(50);

		// adresse
		col = table.getColumnModel().getColumn(2);
		col.setPreferredWidth(100);

		// postnummer
		col = table.getColumnModel().getColumn(3);
		col.setPreferredWidth(80);

		// Poststed
		col = table.getColumnModel().getColumn(4);
		col.setPreferredWidth(100);

		// Garasjetype
		col = table.getColumnModel().getColumn(5);
		col.setPreferredWidth(80);

		// Transport
		col = table.getColumnModel().getColumn(6);
		col.setPreferredWidth(100);

		// Produktområde
		col = table.getColumnModel().getColumn(7);
		col.setPreferredWidth(100);

	}

	/**
	 * @param object
	 * @param presentationModel1
	 * @param window
	 * @return feilstreng
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
	 *      com.jgoodies.binding.PresentationModel,
	 *      no.ugland.utransprod.gui.WindowInterface)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public CheckObject checkSaveObject(OrderModel object, PresentationModel presentationModel1,
			WindowInterface window) {
		String errorString = null;
		Customer customer = null;

		Order order = object.getObject();
		if (order.getOrderId() == null && objectList.contains(order)) {
			errorString = "Kan ikke lagre ordre med et ordrenummer som finnes fra før";
		}

		if (object.getCustomer() == null) {
			CustomerManager customerManager = (CustomerManager) ModelUtil.getBean("customerManager");
			customer = customerManager.findByCustomerNr(Integer.valueOf(object.getCustomerNr()));
			if (customer != null) {
				if (JOptionPane.showConfirmDialog(window.getComponent(),
						"Kunde med dette kundenr finnes fra før med navn " + customer.getFirstName() + " "
								+ customer.getLastName() + " ønskes denne kunden å benyttes?",
						"Kunde finnes fra før", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
					presentationModel1.setBufferedValue(OrderModel.PROPERTY_CUSTOMER, customer);
				} else {
					errorString = "Kan ikke lagre kunde med et kundenr som finnes fra før";
				}
			}
		}

		List<OrderCost> costs = object.getCostList();

		if (costs == null || costs.size() == 0) {
			errorString = "Ordre mangler kostnader for egenproduksjonsverdi og frakt";
		} else {
			String garasjeError = "Ordre mangler kostnad for egenproduksjon";
			String fraktError = "Ordre mangler kostnad for frakt";
			String monteringError = "Ordre mangler kostnad for montering";
			for (OrderCost cost : costs) {
				if (cost.getCostType().getCostTypeName().equalsIgnoreCase("Egenproduksjon")) {
					garasjeError = "";
				} else if (cost.getCostType().getCostTypeName().equalsIgnoreCase("Frakt")) {
					fraktError = "";
				} else if (cost.getCostType().getCostTypeName().equalsIgnoreCase("Montering")) {
					monteringError = "";
				}
			}
			errorString = "";
			if (garasjeError.length() != 0) {
				errorString += garasjeError;
			} else if (fraktError.length() != 0) {
				errorString += fraktError;
			} else if (monteringError.length() != 0 && object.isDoAssembly()) {
				errorString += monteringError;
			}
		}

		return new CheckObject(errorString, false);
	}

	/**
	 * Henter liste over ordre som ikke har transport satt
	 * 
	 * @param orderPanelType
	 * @return nye ordre
	 */
	public SelectionInList initAndGetOrderPanelSelectionList(OrderPanelTypeEnum orderPanelType) {
		orderPanelList.clear();
		orderPanelList.addAll(getOrderList(orderPanelType));
		return orderPanelSelectionList;
	}

	public final void setOrderPanelList(final List<Order> orders) {
		orderPanelList.clear();
		orderPanelList.addAll(orders);
	}

	/**
	 * Henter indeks for valgt ordre
	 * 
	 * @return indeks
	 */
	public int getOrderPanelSelectedOrderIndex() {
		return tableOrders.convertRowIndexToModel(orderPanelSelectionList.getSelectionIndex());
	}

	/**
	 * Henter ordreliste
	 * 
	 * @return liste
	 */
	public SelectionInList getOrderPanelSelectionList() {
		return orderPanelSelectionList;
	}

	public ArrayListModel getOrderPanelList() {
		return orderPanelList;
	}

	/**
	 * Henter ordreliste basert på paneltype
	 * 
	 * @param orderPanelType
	 * @return ordreliste
	 */
	@SuppressWarnings("incomplete-switch")
	private List<Order> getOrderList(OrderPanelTypeEnum orderPanelType) {
		switch (orderPanelType) {
		case NEW_ORDERS:
			List<Order> newOrders = ((OrderManager) overviewManager).getAllNewOrders();
			return newOrders;
		case ASSEMBLY_ORDERS:
			List<Order> assemblyOrders = ((OrderManager) overviewManager).getAllAssemblyOrders();
			return assemblyOrders;
		default:
			return new ArrayList<Order>();

		}

	}

	/**
	 * Lager ordreliste
	 * 
	 * @return liste
	 */
	public JList getOrderList() {
		JList listOrders = BasicComponentFactory.createList(orderPanelSelectionList);
		listOrders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOrders.setName("ListOrders");
		return listOrders;
	}

	/**
	 * Initiererer status for ordre
	 * 
	 * @param orders
	 * @param steinChecker
	 * @param window
	 */
	private void initStatus(List<Order> orders, StatusCheckerInterface<Transportable> steinChecker,
			WindowInterface window) {
		for (Order order : orders) {
			Map<String, String> statusMap = Util.createStatusMap(order.getStatus());

			String status = statusMap.get(steinChecker.getArticleName());

			if (status == null) {
				lazyLoadOrderTree(order);
				status = steinChecker.getArticleStatus(order);
				order.setStatus("$Takstein;" + status);
				try {
					((OrderManager) overviewManager).saveOrder(order);
				} catch (ProTransException e) {
					Util.showErrorDialog(window, "Feil", e.getMessage());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Henter tabell for ordre
	 * 
	 * @param orderPanelTypeEnum
	 * @param window
	 * @return tabell
	 */
	@SuppressWarnings({ "unchecked", "incomplete-switch" })
	public JXTable getPanelTableOrders(OrderPanelTypeEnum orderPanelTypeEnum, WindowInterface window) {
		if (tableOrders != null) {
			return tableOrders;
		}
		tableOrders = new JXTable();
		StatusCheckerInterface<Transportable> steinChecker = Util.getSteinChecker();

		if (orderPanelTypeEnum == OrderPanelTypeEnum.NEW_ORDERS) {
			initStatus(orderPanelList, steinChecker, window);
		}

		orderPanelTableModel = new OrderTableModel(orderPanelSelectionList, orderPanelList, orderPanelTypeEnum,
				steinChecker);
		tableOrders.setModel(orderPanelTableModel);
		tableOrders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableOrders
				.setSelectionModel(new SingleListSelectionAdapter(orderPanelSelectionList.getSelectionIndexHolder()));
		tableOrders.setColumnControlVisible(true);
		tableOrders.setColumnControl(new UBColumnControlPopup(tableOrders, this));
		tableOrders.setSearchable(null);
		DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
		tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

		switch (orderPanelTypeEnum) {
		case NEW_ORDERS:
			tableOrders.setRowHeight(25);
			tableOrders.getColumnModel().getColumn(6).setCellRenderer(

					new TextPaneRendererCustomer());

			ColorHighlighter greyPattern = new ColorHighlighter(new PatternPredicate("90", 10),
					ColorEnum.GREY.getColor(), null);
			tableOrders.addHighlighter(greyPattern);

			tableOrders.getColumnExt(12).setCellRenderer(tableCellRenderer);

			break;
		case ASSEMBLY_ORDERS:
			tableOrders.setRowHeight(25);
			tableOrders.getColumnModel().getColumn(3).setCellRenderer(

					new TextPaneRendererCustomer());
			tableOrders.getColumnExt(8).setCellRenderer(tableCellRenderer);

			break;
		}

		setColumnWidth(orderPanelTypeEnum);

		tableOrders.setName(TableEnum.TABLEORDERS.getTableName());
		return tableOrders;
	}

	/**
	 * Setter kolonnebredder
	 * 
	 * @param orderPanelTypeEnum
	 */
	@SuppressWarnings("incomplete-switch")
	private void setColumnWidth(OrderPanelTypeEnum orderPanelTypeEnum) {
		switch (orderPanelTypeEnum) {
		case NEW_ORDERS:
			// ØUke
			tableOrders.getColumnExt(0).setPreferredWidth(40);
			// Type
			tableOrders.getColumnExt(1).setPreferredWidth(50);
			// Mont.
			tableOrders.getColumnExt(2).setPreferredWidth(50);
			// Pnr.
			tableOrders.getColumnExt(3).setPreferredWidth(50);
			// Poststed.
			tableOrders.getColumnExt(4).setPreferredWidth(60);
			// Stein
			tableOrders.getColumnExt(5).setPreferredWidth(40);
			// Kunde
			tableOrders.getColumnExt(6).setPreferredWidth(150);
			// Ordrenr
			tableOrders.getColumnExt(7).setPreferredWidth(50);
			break;
		case ASSEMBLY_ORDERS:
			// Type
			tableOrders.getColumnExt(0).setPreferredWidth(50);
			// Pnr.
			tableOrders.getColumnExt(1).setPreferredWidth(50);
			// Poststed.
			tableOrders.getColumnExt(2).setPreferredWidth(60);
			// Kunde
			tableOrders.getColumnExt(3).setPreferredWidth(150);
			// Ordrenr
			tableOrders.getColumnExt(4).setPreferredWidth(50);
			break;
		case CONFIRM_REPORT:
			// kunde
			tableOrders.getColumnExt(0).setPreferredWidth(200);
			// ordrenr
			tableOrders.getColumnExt(1).setPreferredWidth(60);
			// adresse
			tableOrders.getColumnExt(2).setPreferredWidth(120);
			// Postnummer
			tableOrders.getColumnExt(3).setPreferredWidth(90);
			// poststed
			tableOrders.getColumnExt(4).setPreferredWidth(100);
			// Type
			tableOrders.getColumnExt(5).setPreferredWidth(100);
			// transport
			tableOrders.getColumnExt(6).setPreferredWidth(100);
			// egenproduksjon
			tableOrders.getColumnExt(7).setPreferredWidth(110);
			// frakt
			tableOrders.getColumnExt(8).setPreferredWidth(50);
			// montering
			tableOrders.getColumnExt(9).setPreferredWidth(100);
			// egenproduksjon intern
			tableOrders.getColumnExt(10).setPreferredWidth(50);
			// dekningsgrad
			tableOrders.getColumnExt(11).setPreferredWidth(100);
			break;
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "ordre";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
	 */
	@Override
	public String getClassName() {
		return "Order";
	}

	/**
	 * @param object
	 * @return null
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
	 */
	@Override
	public CheckObject checkDeleteObject(Order object) {
		((OrderManager) overviewManager).lazyLoadOrder(object,
				new LazyLoadOrderEnum[] { LazyLoadOrderEnum.DEVIATIONS });
		if (object.getDeviations() != null && object.getDeviations().size() != 0) {
			return new CheckObject("Ordre har avvik!", false);
		}
		if (object.getAssembly() != null) {
			return new CheckObject("Ordre har montering!", true);
		}
		return null;
	}

	/**
	 * @param object
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#refreshObject(java.lang.Object)
	 */
	@Override
	public void refreshObject(OrderModel object) {
		setFlushing(true);
		((OrderManager) overviewManager).refreshObject(object.getObject());
		((OrderManager) overviewManager).lazyLoadOrder(object.getObject(),
				new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_COSTS, LazyLoadOrderEnum.ORDER_LINES,
						LazyLoadOrderEnum.ORDER_LINE_ORDER_LINES, LazyLoadOrderEnum.COLLIES });
		object.firePropertiesChanged();
		setFlushing(false);
	}

	/**
	 * Henter hjelpeklasse for visning av artikler
	 * 
	 * @param aPresentationModel
	 * @return hjelpeklasse
	 */
	public OrderArticleViewHandler<Order, OrderModel> getOrderArticleViewHandler(PresentationModel aPresentationModel) {
		orderArticleViewHandler = new OrderArticleViewHandler<Order, OrderModel>(aPresentationModel, searching, login,
				managerRepository, true);
		return orderArticleViewHandler;
	}

	/**
	 * Henter hjelpeklasse for visning av kostnader
	 * 
	 * @param aPresentationModel
	 * @return hjelpeklasse
	 */
	public OrderCostsViewHandler getOrderCostsViewHandler(PresentationModel aPresentationModel, boolean lettvekt) {
		orderCostsViewHandler = new OrderCostsViewHandler(aPresentationModel, login, false, false, false,
				managerRepository);
		if (!lettvekt) {
			orderArticleViewHandler.addCostChangeListener(orderCostsViewHandler);
		}
		return orderCostsViewHandler;
	}

	/**
	 * Henter knapp for å editere ordre
	 * 
	 * @param aWindow
	 * @return knapp
	 */
	public JButton getButtonEditOrder(WindowInterface aWindow) {

		JButton button = new JButton(new EditOrderAction(aWindow));
		button.setName("EditOrder");
		button.setEnabled(false);
		return button;
	}

	/**
	 * Klassesom håndterer dobbelklikk i vindu for nye ordre
	 * 
	 * @author atle.brekka
	 */
	final class NewOrderDoubleClickHandler extends MouseAdapter {
		private WindowInterface window;
		private boolean lettvekt;

		/**
		 * @param aWindow
		 */
		public NewOrderDoubleClickHandler(WindowInterface aWindow, boolean lettvekt) {
			window = aWindow;
			this.lettvekt = lettvekt;

		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			Util.setWaitCursor(window.getComponent());
			if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {
				if (orderPanelSelectionList.getSelection() != null) {
					int index = tableOrders.convertRowIndexToModel(orderPanelSelectionList.getSelectionIndex());
					openEditView((Order) orderPanelSelectionList.getElementAt(index), false, window, lettvekt);
				}
			}
			Util.setDefaultCursor(window.getComponent());
		}
	}

	/**
	 * Klasse som håndterer editering av nye ordre
	 * 
	 * @author atle.brekka
	 */
	private final class EditOrderAction extends AbstractAction {
		private WindowInterface window;

		private static final long serialVersionUID = 1L;

		/**
		 * @param aWindow
		 */
		public EditOrderAction(WindowInterface aWindow) {
			super("Editer...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			Util.setWaitCursor(window.getComponent());
			int index = tableOrders.convertRowIndexToModel(orderPanelSelectionList.getSelectionIndex());
			openEditView((Order) orderPanelSelectionList.getElementAt(index), false, window, false);
			Util.setDefaultCursor(window.getComponent());
		}
	}

	/**
	 * Henter lytter for dobbeltklikk i tabell med nye ordre
	 * 
	 * @param window
	 * @return lytter
	 */
	public MouseListener getNewOrderDoubleClickHandler(WindowInterface window, JXTable table, boolean lettvekt) {
		if (newOrderDoubleClickHandler == null) {

			newOrderDoubleClickHandler = new NewOrderDoubleClickHandler(window, lettvekt);
			table.addMouseListener(newOrderDoubleClickHandler);
		}
		return newOrderDoubleClickHandler;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableHeight()
	 */
	@Override
	public String getTableHeight() {
		return "200dlu";
	}

	/**
	 * Lager sjekkboks for låsing av vindu
	 * 
	 * @return sjekkboks
	 */
	public JCheckBox getCheckBoxLock() {
		checkBoxLock = new JCheckBox(new LockAction());
		checkBoxLock.setSelected(!editEnabled);
		checkBoxLock.setEnabled(hasWriteAccess());
		checkBoxLock.setName("CheckBoxLock");
		return checkBoxLock;
	}

	/**
	 * Håndterer låsing av vindu
	 * 
	 * @author atle.brekka
	 */
	private class LockAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public LockAction() {
			super("Låst");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			if (checkBoxLock.isSelected()) {
				setComponentEnablement(false);
			} else {
				setComponentEnablement(true);

			}
		}
	}

	/**
	 * Legger til komponent som skal vise i dialog
	 * 
	 * @param comp
	 */
	public void addEditComponent(Component comp) {
		editComponents.add(comp);

	}

	/**
	 * Enabler/disabler komponenter
	 */
	public void setComponentEnablement() {
		setComponentEnablement(editEnabled);
	}

	/**
	 * Enabler/disabler komponenter
	 * 
	 * @param enable
	 */
	void setComponentEnablement(boolean enable) {
		for (Component component : editComponents) {
			component.setEnabled(enable);
		}

		if (orderArticleViewHandler != null) {
			orderArticleViewHandler.setComponentEnablement(enable);
		}
		orderCostsViewHandler.setComponentEnablement(enable);
		attachmentViewHandler.setComponentEnablement(enable);

		if (deviationOverviewView != null) {
			deviationOverviewView.setComponentEnablement(enable);
		}

	}

	/**
	 * Viser statistikk
	 */
	void doStatistics(WindowInterface window) {
		objectSelectionList.clearSelection();
		if (statisticsDialog == null) {
			viewHandler = new SearchAttributeViewHandler();
			SearchAttributeView view = new SearchAttributeView(viewHandler);

			JDialog dialog = Util.getDialog(window, "Statistikk", true);

			statisticsDialog = new JDialogAdapter(dialog);

			statisticsDialog.add(view.buildPanel(statisticsDialog));
			statisticsDialog.pack();
		}

		Util.locateOnScreenCenter(statisticsDialog);
		statisticsDialog.setVisible(true);

		if (!viewHandler.isCanceled()) {
			List<Order> foundOrders = viewHandler.getOrderLines();
			Integer numberOfOrders = viewHandler.getNumberOfOrders();
			Integer numberOfFoundOrders;

			if (foundOrders != null) {
				objectList.clear();
				objectList.addAll(foundOrders);
				numberOfFoundOrders = foundOrders.size();
			} else {
				numberOfFoundOrders = 0;
			}
			numberOf.setNumbers(numberOfFoundOrders, numberOfOrders);
			labelFilterInfo.setText(viewHandler.getFilterString());
		}
		if (objectList.size() != noOfObjects) {
			setFiltered(true);
		} else {
			setFiltered(false);
		}
	}

	/**
	 * Lager knapp for å vise statistikk
	 * 
	 * @param aWindow
	 * @return knapp
	 */
	public JButton getButtonStatistics(WindowInterface aWindow) {
		JButton button = new JButton(new StatisticsAction(aWindow));
		button.setName("ArticleStatistics");
		return button;
	}

	/**
	 * Lager knapp for å genererer excel
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonExcel(WindowInterface window) {
		JButton buttonExcel = new JButton(new ExcelAction(window));
		buttonExcel.setIcon(IconEnum.ICON_EXCEL.getIcon());
		return buttonExcel;
	}

	/**
	 * Vis statistikk
	 * 
	 * @author atle.brekka
	 */
	private class StatisticsAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public StatisticsAction(WindowInterface aWindow) {
			super("Artikkelstatistikk...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			Util.setWaitCursor(window.getComponent());
			doStatistics(window);
			Util.setDefaultCursor(window.getComponent());

		}
	}

	/**
	 * Håndterer genererering av excel
	 * 
	 * @author atle.brekka
	 */
	private class ExcelAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ExcelAction(WindowInterface aWindow) {
			super("Excel");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			Util.setWaitCursor(window.getComponent());
			try {
				exportToExcel(window);
				// Util.showMsgFrame(window.getComponent(), "Excel generert",
				// "Dersom excelfil ikke kom opp ligger den i katalog definert
				// for excel");
			} catch (ProTransException e) {
				e.printStackTrace();
				Util.showErrorDialog(window, "Feil", e.getMessage());
			}
			Util.setDefaultCursor(window.getComponent());

		}
	}

	/**
	 * Lager label for å vise antall
	 * 
	 * @return label
	 */
	public JLabel getLabelNumberOf() {
		PresentationModel pres = new PresentationModel(numberOf);
		return BasicComponentFactory.createLabel(pres.getModel(NumberOf.PROPERTY_LABEL_STRING));
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#initObjects()
	 */
	@Override
	protected void initObjects() {
		super.initObjects();
		numberOf.setNumbers(objectList.getSize(), noOfObjects);
	}

	/**
	 * Lager label for å vise filterinfo
	 * 
	 * @return label
	 */
	public JLabel getLabelFilterInfo() {
		labelFilterInfo = new JLabel();
		return labelFilterInfo;
	}

	public JComboBox getComboBoxProductAreaGroup() {
		JComboBox comboBox = Util.createComboBoxProductAreaGroup(
				presentationModelProductAreaGroup.getModel(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
		comboBox.setSelectedIndex(0);
		return comboBox;
	}

	/**
	 * Klasse som holder på antallinfo
	 * 
	 * @author atle.brekka
	 */
	public class NumberOf extends Model {
		private static final long serialVersionUID = 1L;

		public static final String PROPERTY_LABEL_STRING = "labelString";

		private String numberOfString;

		private String totalString;

		/**
		 * @param aNumberOf
		 * @param aTotal
		 */
		public NumberOf(Integer aNumberOf, Integer aTotal) {
			numberOfString = String.valueOf(aNumberOf);
			totalString = String.valueOf(aTotal);
		}

		/**
		 * Henter streng som skal vise
		 * 
		 * @return streng
		 */
		public String getLabelString() {
			return numberOfString + " av " + totalString;
		}

		/**
		 * Setter streng som skal vises
		 * 
		 * @param aString
		 */
		public void setLabelString(String aString) {
			firePropertyChange(PROPERTY_LABEL_STRING, null, getLabelString());
		}

		/**
		 * Setter tall
		 * 
		 * @param numOf
		 * @param total
		 */
		public void setNumbers(Integer numOf, Integer total) {
			numberOfString = String.valueOf(numOf);
			totalString = String.valueOf(total);
			setLabelString("");
		}
	}

	/**
	 * Setter valgt ordre
	 * 
	 * @param order
	 */
	public void setSelectedNewTransportable(Transportable transportable) {
		orderPanelSelectionList.setSelection(transportable);
	}

	/**
	 * Setter ordre
	 * 
	 * @param orders
	 */
	public void setTransportables(List<Transportable> transportables) {
		loaded = true;
		objectSelectionList.clearSelection();
		objectList.clear();
		objectList.addAll(transportables);
	}

	/**
	 * Lager ok-knapp
	 * 
	 * @param window
	 * @return knapp
	 */
	public JButton getOkButton(WindowInterface window) {
		return new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK, null, false);
	}

	/**
	 * Henter monteringslag liste
	 * 
	 * @return liste
	 */
	public List<Supplier> getSupplierList() {
		return managerRepository.getSupplierManager().findActiveByTypeName("Montering", "postalCode");
	}

	/**
	 * Henter muselytter
	 * 
	 * @param aWindow
	 * @return muselytter
	 */
	public MouseListener getMouseListenerSelect(WindowInterface aWindow) {
		return new MouseListenerSelect(aWindow);
	}

	/**
	 * Muselytter som benyttes når det skal velges en order med dobbeltklikk
	 * 
	 * @author atle.brekka
	 */
	private class MouseListenerSelect extends MouseAdapter {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public MouseListenerSelect(WindowInterface aWindow) {
			super();
			window = aWindow;
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@Override
		public void mouseClicked(MouseEvent e) {
			if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2)
				if (objectSelectionList.getSelection() != null) {
					window.dispose();
				}
		}

	}

	/**
	 * Lagrer montering
	 * 
	 * @param assembly
	 */
	public void saveAssembly(Assembly assembly) {
		AssemblyManager assemblyManager = (AssemblyManager) ModelUtil.getBean("assemblyManager");
		assemblyManager.saveAssembly(assembly);
	}

	/**
	 * Fjerner montering
	 * 
	 * @param assembly
	 */
	public void removeAssembly(Assembly assembly) {
		AssemblyManager assemblyManager = (AssemblyManager) ModelUtil.getBean("assemblyManager");
		assemblyManager.removeObject(assembly);
	}

	/**
	 * Setter montering til inaktiv
	 * 
	 * @param assembly
	 */
	public void setAssemblyInactive(Assembly assembly) {
		AssemblyManager assemblyManager = (AssemblyManager) ModelUtil.getBean("assemblyManager");
		assembly.setInactive(1);
		assemblyManager.saveAssembly(assembly);
	}

	/**
	 * Henter ordremanager
	 * 
	 * @return ordremanager
	 */
	public OrderManager getOrderManager() {
		return (OrderManager) overviewManager;
	}

	/**
	 * Henter lytter for ordrenummer forandring
	 * 
	 * @param window
	 * @return ordrenummerlytter
	 */
	public PropertyChangeListener getOrderNrChangeListener(WindowInterface window) {
		return new OrderNrChangeListener(window);
	}

	private class OrderNrChangeListener implements PropertyChangeListener {
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public OrderNrChangeListener(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent event) {
			String orderNr = (String) event.getNewValue();
			if (orderNr != null) {
				Order order = ((OrderManager) overviewManager).findByOrderNr(orderNr);

				if (order != null) {
					Util.showMsgDialog(window.getComponent(), "Ordre finnes fra før", "Ordre finnes fra før");
				}
			}

		}

	}

	/**
	 * Henter tabellmodell for ordrepanel
	 * 
	 * @return tabellmodell
	 */
	public OrderTableModel getOrderPanelTableModel() {
		return orderPanelTableModel;
	}

	/**
	 * Oppdaterer ordre
	 * 
	 * @param order
	 */
	public void refreshOrder(Order order) {
		((OrderManager) overviewManager).refreshObject(order);
	}

	/**
	 * Enabler/disabler editering i vindu
	 * 
	 * @param enableEdit
	 */
	public void setEditEnabled(boolean enableEdit) {
		editEnabled = enableEdit;
	}

	/**
	 * Sjekker om ordre har flere adresse
	 * 
	 * @param presentationModel1
	 * @param window
	 */
	public void checkAddresses(PresentationModel presentationModel1, WindowInterface window) {
		Order order = ((OrderModel) presentationModel1.getBean()).getObject();

		if (order.getOfferAddresses() != null) {
			OfferAddress offerAddress = (OfferAddress) JOptionPane.showInputDialog(window.getComponent(),
					"Ordre har flere adresser", "Velg adresse", JOptionPane.INFORMATION_MESSAGE, null,
					order.getOfferAddresses().toArray(), null);

			if (offerAddress != null) {
				order.setDeliveryAddress(offerAddress.getAddress1());
				order.setPostalCode(offerAddress.getZipcode());
				order.setPostOffice(offerAddress.getCity());
			}
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Ordre");
	}

	/**
	 * Lager avvikspanel
	 * 
	 * @param window
	 * @param presentationModel
	 * @return panel
	 */
	public JPanel getDeviationPane(WindowInterface window, PresentationModel presentationModel) {
		Order order = ((OrderModel) presentationModel.getBean()).getObject();
		DeviationViewHandler2 deviationViewHandler = deviationViewHandlerFactory.create(order, true, true, false, null,
				false, true);

		deviationOverviewView = deviationOverviewViewFactory.create(deviationViewHandler, false, order, true, true,
				false, null, false);
		return deviationOverviewView.buildDeviationPanel(window, true);
	}

	/**
	 * Lager kommentarliste
	 * 
	 * @param presentationModel
	 * @return liste
	 */
	@SuppressWarnings("unchecked")
	public JList getListComments(PresentationModel presentationModel) {
		orderComments.clear();
		orderComments.addAll((List<OrderComment>) presentationModel.getBufferedValue(OrderModel.PROPERTY_COMMENTS));
		JList list = new JList(orderComments);
		list.setName("ListOrderComments");
		list.setCellRenderer(new ListMultilineRenderer(65));
		return list;
	}

	/**
	 * Lager knapp for å legge til kommentar
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonAddComment(WindowInterface window, PresentationModel presentationModel) {
		JButton button = new JButton(new AddComment(window, presentationModel));
		button.setName("ButtonAddComment");
		return button;
	}

	/**
	 * Legger til kommentar
	 * 
	 * @author atle.brekka
	 */
	private class AddComment extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		private PresentationModel presentationModel;

		/**
		 * @param aWindow
		 * @param aPresentationModel
		 */
		public AddComment(WindowInterface aWindow, PresentationModel aPresentationModel) {
			super("Legg til kommentar...");
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			OrderComment orderComment = new OrderComment();
			orderComment.setUserName(login.getApplicationUser().getUserName());
			orderComment.setCommentDate(Util.getCurrentDate());
			orderComment.addCommentType(CommentTypeUtil.getCommentType("Ordre"));

			CommentViewHandler orderCommentViewHandler = new CommentViewHandler(login, (OrderManager) overviewManager);
			EditCommentView editDeviationCommentView = new EditCommentView(new OrderCommentModel(orderComment),
					orderCommentViewHandler);

			JDialog dialog = Util.getDialog(window, "Legg til kommentar", true);
			dialog.setName("EditDeviationCommentView");
			WindowInterface dialogWindow = new JDialogAdapter(dialog);
			dialogWindow.add(editDeviationCommentView.buildPanel(dialogWindow));
			dialog.pack();
			Util.locateOnScreenCenter(dialog);
			dialogWindow.setVisible(true);

			if (!orderCommentViewHandler.isCanceled()) {
				orderComment.setOrder(((OrderModel) presentationModel.getBean()).getObject());
				orderComments.add(0, orderComment);
				presentationModel.setBufferedValue(OrderModel.PROPERTY_COMMENTS, orderComments);
				presentationModel.setBufferedValue(OrderModel.PROPERTY_CACHED_COMMENT, null);
			}

		}

	}

	/**
	 * @param handler
	 * @param object
	 * @param searching1
	 * @return view
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
	 *      java.lang.Object, boolean)
	 */
	@Override
	protected AbstractEditView<OrderModel, Order> getEditView(AbstractViewHandler<Order, OrderModel> handler,
			Order object, boolean searching1) {
		return null;
	}

	/**
	 * Håndterer endring av produktområde
	 * 
	 * @author atle.brekka
	 */
	private class ProductAreaChangeListener implements PropertyChangeListener {
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public ProductAreaChangeListener(PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent event) {
			if (event.getPropertyName().equalsIgnoreCase("value")) {
				ProductArea oldValue = (ProductArea) event.getOldValue();
				ProductArea newValue = (ProductArea) event.getNewValue();
				boolean initTypes = false;
				if (oldValue != null && newValue != null
						&& !oldValue.getProductArea().equalsIgnoreCase(newValue.getProductArea())) {
					initTypes = true;

				} else if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null)) {
					initTypes = true;
				}
				if (initTypes && !flushing) {
					presentationModel.setBufferedValue(OrderModel.PROPERTY_CONSTRUCTION_TYPE, null);
					initConstructionTypeList(presentationModel);
				}
			}

		}

	}

	/**
	 * Søker ordre
	 * 
	 * @param window
	 * @return ordre
	 */
	public Transportable searchOrder(WindowInterface window, boolean includePostShipment, boolean includeDeviation) {
		Order searchOrder = new Order();
		boolean isCanceled = openOrderView(searchOrder, true, window, false);
		Transportable transportable = null;
		if (!isCanceled) {

			OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
			List<Order> orderList = orderManager.findByOrder(searchOrder);

			List<Transportable> transportables = null;
			if (orderList != null && orderList.size() != 0) {
				transportables = new ArrayList<Transportable>(orderList);

				List<PostShipment> postShipments = null;
				if (includePostShipment) {
					postShipments = findPostShipments(orderList);

					if (postShipments != null) {
						transportables.addAll(postShipments);
					}
				}

				if (includeDeviation) {
					List<Deviation> deviations = findDeviations(orderList);

					if (deviations != null) {
						if (postShipments != null) {
							final List<Integer> postShipmentIder = Lists.newArrayList(
									Iterables.transform(postShipments, new Function<PostShipment, Integer>() {

										public Integer apply(PostShipment postShipment) {
											return postShipment.getPostShipmentId();
										}
									}));
							List<Deviation> avvik = Lists
									.newArrayList(Iterables.filter(deviations, new Predicate<Deviation>() {

										public boolean apply(Deviation deviation) {
											return deviation.getPostShipment() != null && !postShipmentIder
													.contains(deviation.getPostShipment().getPostShipmentId());
										}
									}));
							transportables.addAll(avvik);
						}

					}
				}
			}

			if (transportables != null && transportables.size() > 1) {
				WindowInterface orderDialog = new JDialogAdapter(
						new JDialog(ProTransMain.PRO_TRANS_MAIN, "Velg ordre", true));

				OrderOverviewView orderOverviewView = new OrderOverviewView(this, true);
				setTransportables(transportables);

				orderDialog.add(orderOverviewView.buildPanel(orderDialog));
				orderDialog.pack();
				Util.locateOnScreenCenter(orderDialog);
				orderDialog.setVisible(true);

				transportable = (Transportable) getTableSelection();

				if (transportable == null) {
					Util.showErrorDialog(window, "Ordre ikke valgt", "Det ble ikke valgt noen ordre");
				}
			} else if (transportables != null && transportables.size() == 1) {
				transportable = transportables.get(0);
			} else {
				Util.showErrorDialog(window, "Ordre ikke funnet", "Ordre ble ikke funnet");
			}

		}
		return transportable;
	}

	private List<PostShipment> findPostShipments(List<Order> orders) {
		List<PostShipment> postShipments = null;
		if (orders != null) {
			postShipments = new ArrayList<PostShipment>();
			for (Order order : orders) {
				((OrderManager) overviewManager).lazyLoadOrder(order,
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.POST_SHIPMENTS });
				postShipments.addAll(order.getPostShipments());
			}
		}
		return postShipments;
	}

	private List<Deviation> findDeviations(List<Order> orders) {
		List<Deviation> deviations = null;
		if (orders != null) {
			deviations = new ArrayList<Deviation>();
			for (Order order : orders) {
				((OrderManager) overviewManager).lazyLoadOrder(order,
						new LazyLoadOrderEnum[] { LazyLoadOrderEnum.DEVIATIONS });
				deviations.addAll(order.getDeviations());
			}
		}
		return deviations;
	}

	/**
	 * Håndterer filtrering
	 * 
	 * @param productAreaGroup
	 */
	public void handleFilter(OrderPanelTypeEnum orderPanelType) {
		if (tableOrders != null) {
			// currentProductAreaGroup = productAreaGroup;
			PrefsUtil.setInvisibleColumns(ProductAreaGroup.UNKNOWN.getProductAreaGroupName(), tableOrders.getName(),
					tableOrders);
			// if (productAreaGroup != null &&
			// !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle"))
			// {
			//
			// Filter[] filters = new Filter[] { new
			// PatternFilter(productAreaGroup.getProductAreaGroupName(),
			// Pattern.CASE_INSENSITIVE,
			// orderPanelType.getProductAreaGroupColumn()) };
			// FilterPipeline filterPipeline = new FilterPipeline(filters);
			// tableOrders.setFilters(filterPipeline);
			//
			// } else {
			tableOrders.setFilters(null);
			// }

			tableOrders.repaint();
		}

	}

	private class ProductAreaGroupChangeListener implements PropertyChangeListener {

		public void propertyChange(PropertyChangeEvent evt) {
			ProductAreaGroup group = (ProductAreaGroup) presentationModelProductAreaGroup
					.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
			if (group != null && !group.getProductAreaGroupName().equalsIgnoreCase("Alle")) {

				Filter filter = new PatternFilter(group.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE,
						table.getColumnExt("Produktområde").getModelIndex());
				FilterPipeline filterPipeline = new FilterPipeline(new Filter[] { filter });
				table.setFilters(filterPipeline);
				numberOf.setNumbers(table.getRowCount(), noOfObjects);

			} else {
				table.setFilters(null);
				numberOf.setNumbers(table.getRowCount(), noOfObjects);
			}

		}

	}

	public String getProductAreaGroupName() {
		return ProductAreaGroup.UNKNOWN.getProductAreaGroupName();
	}

	public JTextField getTextFieldCuttingFile(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_CUTTING_FILE_NAME));
		textField.setName("TextFieldCuttingFile");
		textField.setEnabled(false);
		return textField;
	}

	public JButton getButtonImportCuttingFile(WindowInterface aWindow, PresentationModel presentationModel) {
		JButton button = new JButton(new ImportCuttingFileAction(aWindow, presentationModel));
		button.setName("ButtonImportCuttingFile");
		button.setEnabled(false);
		editComponents.add(button);
		return button;
	}

	public JButton getButtonOpenCuttingFile(PresentationModel presentationModel, WindowInterface aWindow) {
		JButton button = new JButton(new OpenCuttingFileAction(presentationModel, aWindow));
		button.setName("ButtonOpenCuttingFile");
		button.setEnabled(false);
		editComponents.add(button);
		return button;
	}

	public JButton getButtonMontering(PresentationModel presentationModel, WindowInterface aWindow) {
		JButton buttonMontering = new JButton(new MonteringReportAction(presentationModel, window));
		// buttonProductionReport.setEnabled(false);
		return buttonMontering;
	}

	private class MonteringReportAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;
		private PresentationModel presentationModel;

		public MonteringReportAction(PresentationModel presentationModel, WindowInterface aWindow) {
			super("Monteringsanvisning...");
			window = aWindow;
			this.presentationModel = presentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			Util.runInThreadWheel(window.getRootPane(), new Threadable() {

				public void enableComponents(boolean enable) {
				}

				public Object doWork(Object[] params, JLabel labelInfo) {
					labelInfo.setText("Genererer monteringsanvisning...");
					String errorMsg = null;
					try {
						opprettOgVisMonteringsanvisning(presentationModel, window);
					} catch (ProTransException e) {
						errorMsg = e.getMessage();
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (URISyntaxException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (JRException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					return errorMsg;
				}

				public void doWhenFinished(Object object) {
					if (object != null) {
						Util.showErrorDialog(window, "Feil", object.toString());
					}
				}

			}, null);

		}

	}

	private void opprettOgVisMonteringsanvisning(PresentationModel presentationModel, WindowInterface window)
			throws IOException, URISyntaxException, JRException {
		Order order = ((OrderModel) presentationModel.getBean()).getObject();
		List<String> monteringsanvisninger = managerRepository.getOrderManager()
				.finnMonteringsanvisninger(order.getOrderNr());

		if (monteringsanvisninger != null && !monteringsanvisninger.isEmpty()) {
			String monteringsanvisning = PdfUtil.slaaSammenFiler(order.getCustomerString(), order.getOrderNr(),
					monteringsanvisninger);
			if (Desktop.isDesktopSupported()) {
				Desktop.getDesktop().open(new File(monteringsanvisning));
			}
		}
	}

	private class ImportCuttingFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private WindowInterface window;
		private PresentationModel presentationModel;
		private boolean overwrite = false;

		public ImportCuttingFileAction(WindowInterface aWindow, PresentationModel aPresentationModel) {
			super("Importer kappfil...");
			window = aWindow;
			presentationModel = aPresentationModel;
		}

		public void actionPerformed(ActionEvent e) {
			if (continueImport()) {

				Util.runInThreadWheel(window.getRootPane(), new CuttingImporter(window, overwrite, presentationModel),
						null);
			}

		}

		private boolean continueImport() {
			boolean doImport = true;

			if (presentationModel.getBufferedValue(OrderModel.PROPERTY_CUTTING_FILE_NAME) != null) {
				doImport = Util.showConfirmDialog(window, "Ordre har kappfil",
						"Order har kappfil, vil du overskrive denne?");
				overwrite = true;
			}
			return doImport;
		}

	}

	private class OpenCuttingFileAction extends AbstractAction {
		private static final long serialVersionUID = 1L;
		private PresentationModel presentationModel;
		private WindowInterface window;

		public OpenCuttingFileAction(PresentationModel aPresentationModel, WindowInterface aWindow) {
			super("Se kappfil...");
			presentationModel = aPresentationModel;
			window = aWindow;
		}

		public void actionPerformed(ActionEvent e) {
			if (presentationModel.getBufferedValue(OrderModel.PROPERTY_CUTTING_FILE_NAME) == null) {
				Util.showMsgDialog(window != null ? window.getComponent() : null, "Har ikke fil",
						"Order har ikke kappfil");
			} else {
				showCuttingFile(((OrderModel) presentationModel.getBean()).getObject(), window);
			}

		}

	}

	private void showCuttingFile(Order order, WindowInterface aWindow) {
		overviewManager.refreshObject(order);
		overviewManager.lazyLoad(order,
				new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_COSTS, LazyLoadEnum.NONE },
						{ LazyLoadEnum.ORDER_COMMENTS, LazyLoadEnum.NONE },
						{ LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE }, { LazyLoadEnum.COLLIES, LazyLoadEnum.NONE },
						{ LazyLoadEnum.PROCENT_DONES, LazyLoadEnum.NONE } });
		Cutting cutting = order.getCutting();
		CuttingManager cuttingManager = (CuttingManager) ModelUtil.getBean(CuttingManager.MANAGER_NAME);
		cuttingManager.lazyLoad(cutting, new LazyLoadEnum[][] { { LazyLoadEnum.CUTTING_LINES, LazyLoadEnum.NONE } });
		FileViewHandler fileViewHandler = new FileViewHandler(cutting.toFileContent());
		FileView fileView = new FileView(fileViewHandler);
		JDialog dialog = Util.getDialog(aWindow, "Kappfil", true);
		WindowInterface dialogWindow = new JDialogAdapter(dialog);
		dialog.add(fileView.buildPanel(dialogWindow));
		dialog.pack();
		Util.locateOnScreenCenter(dialogWindow);
		dialogWindow.setVisible(true);
	}

	private class CuttingImporter implements Threadable {
		private WindowInterface window;
		private boolean overwriteExistingCutting;
		private PresentationModel presentationModel;
		private boolean imported = false;

		public CuttingImporter(WindowInterface aWindow, boolean doOverwrite,
				final PresentationModel aPresentationModel) {
			window = aWindow;
			overwriteExistingCutting = doOverwrite;
			presentationModel = aPresentationModel;
		}

		public void doWhenFinished(Object object) {
			if (object != null) {
				Util.showErrorDialog(window, "Feil", object.toString());
			} else {
				if (imported) {
					Util.showMsgDialog(window != null ? window.getComponent() : null, "Importert",
							"Kappfil er importert");
				}
			}

		}

		public Object doWork(Object[] params, JLabel labelInfo) {
			String errorMsg = null;
			try {
				labelInfo.setText("Importerer kappfil...");
				String proId = importAndSaveCutting(((OrderModel) presentationModel.getBean()).getObject(),
						overwriteExistingCutting);
				imported = proId != null ? true : false;
				if (imported) {
					presentationModel.setBufferedValue(OrderModel.PROPERTY_CUTTING_FILE_NAME, proId);
				}
			} catch (ProTransException e) {
				e.printStackTrace();
				errorMsg = e.getMessage();
			}
			return errorMsg;
		}

		public void enableComponents(boolean enable) {

		}

	}

	private String importAndSaveCutting(Order order, final boolean overwriteExistingCutting) throws ProTransException {

		Cutting cutting = getAndImportCuttingFile(window, order);
		if (cutting != Cutting.UNKNOWN) {
			saveImportedCutting(cutting, overwriteExistingCutting);
			overviewManager.saveObject(order);
		}
		return cutting.getProId();
	}

	private Cutting getAndImportCuttingFile(WindowInterface window, final Order order) throws ProTransException {
		String cuttingFileName = getCuttingFileName(window);
		return cuttingFileName != null ? importCuttingFile(cuttingFileName, order) : Cutting.UNKNOWN;

	}

	private Cutting importCuttingFile(String cuttingFileName, Order order) throws ProTransException {
		CuttingImport cuttingImport = new CuttingImport();
		Cutting cutting = cuttingImport.importCuttingFile(cuttingFileName);
		cutting.setOrder(order);

		return cutting;
	}

	private String getCuttingFileName(WindowInterface aWindow) {
		return Util.getFileName(aWindow != null ? aWindow.getComponent() : null,
				new FileExtensionFilter("boq", "BOQ-fil"), null);
	}

	private void saveImportedCutting(Cutting cutting, boolean overwriteExistingCutting) throws ProTransException {
		if (cutting != Cutting.UNKNOWN) {
			CuttingManager cuttingManager = (CuttingManager) ModelUtil.getBean(CuttingManager.MANAGER_NAME);
			cuttingManager.saveCutting(cutting, overwriteExistingCutting);
		}

	}

	public AttachmentView getAttachmentView(PresentationModel presentationModel) throws ProTransException {
		String orderNr = (String) presentationModel.getBufferedValue(OrderModel.PROPERTY_ORDER_NR);
		attachmentViewHandler = new AttachmentViewHandler(orderNr);
		return new AttachmentView(attachmentViewHandler);

	}

	public JTextField getTextFieldTelephonenrSite(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_TELEPHONE_NR_SITE));
		textField.setName("TextFieldTelephonenrSite");
		textField.setEnabled(false);
		editComponents.add(textField);
		return textField;
	}

	public JTextField getTextFieldMaxTrossHeight(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_MAX_TROSS_HEIGHT));
		textField.setName("TextFieldMaxTrossHeight");
		textField.setEnabled(false);
		editComponents.add(textField);
		return textField;
	}

}
