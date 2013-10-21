package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.ArticleTypeView;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.JFrameAdapter;
import no.ugland.utransprod.gui.JInternalFrameAdapter;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditExternalOrderLineView;
import no.ugland.utransprod.gui.edit.EditExternalOrderView;
import no.ugland.utransprod.gui.model.ExternalOrderModel;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ExternalOrder;
import no.ugland.utransprod.model.ExternalOrderLine;
import no.ugland.utransprod.model.ExternalOrderLineAttribute;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.OrderLineAttribute;
import no.ugland.utransprod.model.Supplier;
import no.ugland.utransprod.service.ExternalOrderManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.SupplierManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.service.enums.LazyLoadOrderLineEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.ReportViewer;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.HighlighterFactory;
import org.jdesktop.swingx.decorator.PatternFilter;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.internal.Nullable;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.toedter.calendar.JDateChooser;

/**
 * Håndterer eksterne ordre
 * 
 * @author atle.brekka
 * 
 */
public class ExternalOrderViewHandler extends
		DefaultAbstractViewHandler<ExternalOrder, ExternalOrderModel> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	JXTable tableArticleOrders;

	/**
	 * 
	 */
	JXTable tableExternalOrders;

	/**
	 * 
	 */
	ArrayListModel orderArticleList;

	/**
	 * 
	 */
	ArrayListModel externalArticleList;

	/**
	 * 
	 */
	private List<Supplier> supplierList;

	/**
	 * 
	 */
	SelectionInList orderArticleSelectionList;

	/**
	 * 
	 */
	SelectionInList externalArticleSelectionList;

	/**
	 * 
	 */
	Order order;

	/**
	 * 
	 */
	OrderArticleTableModel orderArticleTableModel;

	/**
	 * 
	 */
	private SupplierManager supplierManager;

	/**
	 * 
	 */
	JButton buttonEditExternalOrderLine;

	/**
	 * 
	 */
	JButton buttonRemoveExternalOrderLine;

	/**
	 * 
	 */
	OrderManager orderManager;

	/**
	 * 
	 */
	OrderLineManager orderLineManager;

	

	
	/*
	 * private ArticleTypeManager articleTypeManager; private AttributeManager
	 * attributeManager; private AttributeChoiceManager attributeChoiceManager;
	 * private ExternalOrderLineManager externalOrderLineManager;
	 */

	//private AttributeChoiceManager attributeChoiceManager;

	//private ArticleTypeManager articleTypeManager;
	private ManagerRepository managerRepository;
	private Login login;
	/**
	 * @param aOrder
	 * @param userType
	 */
	@Inject
	public ExternalOrderViewHandler(Login aLogin,ManagerRepository aManagerRepository,@Assisted @Nullable Order aOrder) {
		super("Bestilling", aManagerRepository.getExternalOrderManager(), true, aLogin.getUserType(), true);
		managerRepository=aManagerRepository;
		login=aLogin;
		/*
		 * externalOrderLineManager=aExternalOrderLineManager;
		 * articleTypeManager =aArticleTypeManager; attributeManager
		 * =aAttributeManager; attributeChoiceManager =aAttributeChoiceManager;
		 */
		initManagers();

		supplierList = new ArrayList<Supplier>();
		List<Supplier> suppliers = supplierManager.findAll();
		if (suppliers != null) {
			supplierList.addAll(suppliers);
		}

		order = aOrder;

		initObjects();
	}

	/**
	 * Initierer managere
	 */
	private void initManagers() {
		supplierManager = (SupplierManager) ModelUtil
				.getBean("supplierManager");
		orderManager = (OrderManager) ModelUtil.getBean("orderManager");
		orderLineManager = (OrderLineManager) ModelUtil
				.getBean("orderLineManager");
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#initObjects()
	 */
	@Override
	protected void initObjects() {
		setFiltered(false);
		objectSelectionList.clearSelection();
		objectList.clear();

		if (order != null) {
			initOrderLines(order);
			orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] {
					LazyLoadOrderEnum.EXTERNAL_ORDER,
					LazyLoadOrderEnum.ORDER_LINES,
					LazyLoadOrderEnum.ORDER_COSTS });
			if (order.getExternalOrders() != null) {
				objectList.addAll(order.getExternalOrders());
			}
		} else {
			objectList.addAll(overviewManager.findAll());
		}
		noOfObjects = objectList.getSize();
		if (table != null) {
			table.scrollRowToVisible(0);
		}
	}

	/**
	 * Initierer ordrelinjer
	 * 
	 * @param order1
	 */
	protected void initOrderLines(Order order1) {
		if (order1 != null) {
			orderArticleList = new ArrayListModel();
			orderManager.lazyLoadOrder(order1, new LazyLoadOrderEnum[] {
					LazyLoadOrderEnum.EXTERNAL_ORDER,
					LazyLoadOrderEnum.ORDER_LINES,
					LazyLoadOrderEnum.ORDER_COSTS });
			if (order1.getOrderLines() != null) {
				orderArticleList.addAll(order1.getOrderLines());
			}
			orderArticleSelectionList = new SelectionInList(
					(ListModel) orderArticleList);
		}

	}

	/**
	 * Lager tabell for ordreartikler
	 * 
	 * @param presentationModel
	 * @return tabell
	 */
	public JXTable getTableOrderArticles(PresentationModel presentationModel) {
		// tar ikke med order som allerede er bestilt
		Filter[] filters = new Filter[] { new PatternFilter("---",
				Pattern.CASE_INSENSITIVE, 3) };
		FilterPipeline filterPipeline = new FilterPipeline(filters);

		ExternalOrder externalOrder = ((ExternalOrderModel) presentationModel
				.getBean()).getObject();

		if (order == null) {
			initOrderLines(externalOrder.getOrder());
		}

		orderArticleSelectionList.clearSelection();

		orderArticleTableModel = new OrderArticleTableModel(orderArticleList);
		tableArticleOrders = new JXTable();
		tableArticleOrders.setModel(orderArticleTableModel);
		tableArticleOrders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableArticleOrders.setSelectionModel(new SingleListSelectionAdapter(
				orderArticleSelectionList.getSelectionIndexHolder()));
		tableArticleOrders.setColumnControlVisible(true);
		tableArticleOrders.setFilters(filterPipeline);

		tableArticleOrders.getColumnExt(3).setVisible(false);

		/*
		 * tableArticleOrders.setHighlighters(new HighlighterPipeline( new
		 * Highlighter[] { new AlternateRowHighlighter() }));
		 */
		tableArticleOrders.addHighlighter(HighlighterFactory
				.createAlternateStriping());

		tableArticleOrders.getColumnExt(0).setPreferredWidth(120);
		tableArticleOrders.getColumnExt(1).setPreferredWidth(50);
		tableArticleOrders.getColumnExt(2).setPreferredWidth(180);

		return tableArticleOrders;
	}

	/**
	 * Lager tabell for artikler i ekstern ordre
	 * 
	 * @param presentationModel
	 * @return tabell
	 */
	@SuppressWarnings("unchecked")
	public JXTable getTableExternalArticles(PresentationModel presentationModel) {
		Set<ExternalOrderLine> externalOrderLines = (Set<ExternalOrderLine>) presentationModel
				.getBufferedValue(ExternalOrderModel.PROPERTY_EXTERNAL_ORDER_LINES);
		externalArticleList = new ArrayListModel();
		if (externalOrderLines != null) {
			externalArticleList.addAll(externalOrderLines);
		}
		externalArticleSelectionList = new SelectionInList(
				(ListModel) externalArticleList);

		externalArticleSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				new ExternalArticleSelectionListener());

		tableExternalOrders = new JXTable();
		tableExternalOrders.setModel(new ExternalArticleTableModel(
				externalArticleList));
		tableExternalOrders.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableExternalOrders.setSelectionModel(new SingleListSelectionAdapter(
				externalArticleSelectionList.getSelectionIndexHolder()));
		tableExternalOrders.setColumnControlVisible(true);

		/*
		 * tableExternalOrders.setHighlighters(new HighlighterPipeline( new
		 * Highlighter[] { new AlternateRowHighlighter() }));
		 */
		tableExternalOrders.addHighlighter(HighlighterFactory
				.createAlternateStriping());

		tableExternalOrders.getColumnExt(0).setPreferredWidth(120);
		tableExternalOrders.getColumnExt(1).setPreferredWidth(50);
		tableExternalOrders.getColumnExt(2).setPreferredWidth(180);

		return tableExternalOrders;
	}

	/**
	 * Lager knapp for å bestille artikkel
	 * 
	 * @param presentationModel
	 * @param window
	 * @return knapp
	 */
	public JButton getButtonOrderArticle(PresentationModel presentationModel,
			WindowInterface window) {
		JButton button = new JButton(new OrderArticleAction(presentationModel,
				window));
		button.setEnabled(false);
		orderArticleSelectionList.addPropertyChangeListener(
				SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
				new OrderArticleEmptySelection(button));
		return button;
	}

	/**
	 * Lager komboboks for leverandører
	 * 
	 * @param presentationModel
	 * @return komboboks
	 */
	public JComboBox getComboBoxSupplier(PresentationModel presentationModel) {
		JComboBox comboBox = new JComboBox(
				new ComboBoxAdapter(supplierList, presentationModel
						.getBufferedModel(ExternalOrderModel.PROPERTY_SUPPLIER)));
		comboBox.addItemListener(new SupplierSelectionListener(
				presentationModel));
		comboBox.setEnabled(hasWriteAccess());
		return comboBox;
	}

	/**
	 * Lager tekstfelt for ordrenummer
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldExternalOrderNr(
			PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ExternalOrderModel.PROPERTY_EXTERNAL_ORDER_NR));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager tekstfelt for attestert
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldAtt(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ExternalOrderModel.PROPERTY_ATT));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager tekstfelt for egen ref
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldOurRef(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ExternalOrderModel.PROPERTY_OUR_REF));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager tekstfelt for merket med
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldMarkedWith(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ExternalOrderModel.PROPERTY_MARKED_WITH));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager tekstfelt for telefonnummer
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldPhoneNr(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ExternalOrderModel.PROPERTY_PHONE_NR));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager tekstfelt for faxnummer
	 * 
	 * @param presentationModel
	 * @return tekstfelt
	 */
	public JTextField getTextFieldFaxNr(PresentationModel presentationModel) {
		JTextField textField = BasicComponentFactory
				.createTextField(presentationModel
						.getBufferedModel(ExternalOrderModel.PROPERTY_FAX_NR));
		textField.setEnabled(hasWriteAccess());
		return textField;
	}

	/**
	 * Lager datovelger for leveringsdato
	 * 
	 * @param presentationModel
	 * @return datovelger
	 */
	public JDateChooser getDateChooserDeliveryDate(
			PresentationModel presentationModel) {
		JDateChooser dateChooser = new JDateChooser();
		PropertyConnector conn = new PropertyConnector(
				dateChooser,
				"date",
				presentationModel
						.getBufferedModel(ExternalOrderModel.PROPERTY_DELIVERY_DATE),
				"value");
		conn.updateProperty1();
		dateChooser.setEnabled(hasWriteAccess());
		return dateChooser;
	}

	/**
	 * Lager knapp for å legge til artikkel
	 * 
	 * @param window
	 * 
	 * @return knapp
	 */
	public JButton getButtonAddArticle(WindowInterface window) {
		JButton button = new JButton(new AddArticleAction(window));
		button.setEnabled(hasWriteAccess());
		return button;
	}

	/**
	 * Lager knapp for å editere ekstern orderlinje
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonEditExternalOrderLine(WindowInterface window,
			PresentationModel presentationModel) {
		buttonEditExternalOrderLine = new JButton(
				new EditExternalOrderLineAction(window, presentationModel));
		buttonEditExternalOrderLine.setEnabled(false);
		return buttonEditExternalOrderLine;
	}

	/**
	 * Lager knapp for å fjerne orderlinje
	 * 
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonRemoveExternalOrderLine(
			PresentationModel presentationModel) {
		buttonRemoveExternalOrderLine = new JButton(
				new RemoveExternalOrderLineAction(presentationModel));
		buttonRemoveExternalOrderLine.setEnabled(false);
		return buttonRemoveExternalOrderLine;
	}

	/**
	 * Lager knapp for generere fax
	 * 
	 * @param window
	 * @param presentationModel
	 * @return knapp
	 */
	public JButton getButtonFax(WindowInterface window,
			PresentationModel presentationModel) {
		JButton button = new JButton(new FaxAction(window, presentationModel));
		button.setIcon(IconEnum.ICON_PRINT.getIcon());
		return button;
	}

	/**
	 * Tabellmodell for ordreartikler
	 * 
	 * @author atle.brekka
	 * 
	 */
	public static final class OrderArticleTableModel extends
			AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "Navn", "Antall",
				"Attributter", "ExternalOrder" };

		/**
		 * @param listModel
		 */
		public OrderArticleTableModel(ListModel listModel) {
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
			OrderLine orderLine = (OrderLine) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return orderLine.getArticlePath();
			case 1:
				return orderLine.getNumberOfItems();
			case 2:
				return orderLine.getAttributeInfo();
			case 3:
				if (orderLine.getExternalOrderLine() != null) {
					return "Ja";
				}
				return "---";
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
			case 2:
			case 3:
				return String.class;
			case 1:
				return Integer.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

		/**
		 * Forteller at rad har endret seg
		 */
		public void fireRowChanged() {
			fireTableDataChanged();
		}

	}

	/**
	 * Tabellmodell for extern artikkel
	 * 
	 * @author atle.brekka
	 * 
	 */
	public static final class ExternalArticleTableModel extends
			AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "Navn", "Antall",
				"Attributter" };

		/**
		 * @param listModel
		 */
		public ExternalArticleTableModel(ListModel listModel) {
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
			ExternalOrderLine externalOrderLine = (ExternalOrderLine) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return externalOrderLine.getOrderLine().getArticleName();
			case 1:
				return externalOrderLine.getNumberOfItems();
			case 2:
				return externalOrderLine.getAttributeInfo();
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
			case 2:
				return String.class;
			case 1:
				return Integer.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
	 */
	@Override
	public String getAddRemoveString() {
		return "bestiling";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandlerShort#getNewObject()
	 */
	@Override
	public ExternalOrder getNewObject() {
		return new ExternalOrder();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandlerShort#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	public TableModel getTableModel(WindowInterface window) {
		return new ExternalOrderTableModel(objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandlerShort#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "200dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandlerShort#getTitle()
	 */
	@Override
	public String getTitle() {
		return "Bestillinger";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandlerShort#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(500, 260);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandlerShort#setColumnWidth(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setColumnWidth(JXTable table) {
		// Leverandør
		table.getColumnExt(0).setPreferredWidth(150);

		// Ordernr
		table.getColumnExt(1).setPreferredWidth(50);
		// Leveringsdato
		table.getColumnExt(2).setPreferredWidth(100);
	}

	/**
	 * Tabellmodell for eksterne ordre
	 * 
	 * @author atle.brekka
	 * 
	 */
	private static final class ExternalOrderTableModel extends
			AbstractTableAdapter {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private static final String[] COLUMNS = { "Leverandør", "Ordernr",
				"Leveringsdato" };

		/**
		 * @param listModel
		 */
		ExternalOrderTableModel(ListModel listModel) {
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
			ExternalOrder externalOrder = (ExternalOrder) getRow(rowIndex);
			switch (columnIndex) {
			case 0:
				return externalOrder.getSupplier();
			case 1:
				return externalOrder.getExternalOrderNr();
			case 2:
				if (externalOrder.getDeliveryDate() != null) {
					return Util.SHORT_DATE_FORMAT.format(externalOrder
							.getDeliveryDate());
				}
				return null;
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			switch (columnIndex) {
			case 0:
				return Supplier.class;
			case 1:
			case 2:
				return String.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * Bestille artikkel
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class OrderArticleAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private PresentationModel presentationModel;

		/**
		 * 
		 */
		private WindowInterface window;

		/**
		 * @param aPresentationModel
		 * @param aWindow
		 */
		public OrderArticleAction(PresentationModel aPresentationModel,
				WindowInterface aWindow) {
			super("Bestill...");

			window = aWindow;
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			OrderLine orderLine = (OrderLine) orderArticleSelectionList
					.getElementAt(tableArticleOrders
							.convertRowIndexToModel(orderArticleSelectionList
									.getSelectionIndex()));
			ExternalOrderLine externalOrderLine = new ExternalOrderLine(null,
					orderLine.getArticleName(), orderLine,
					((ExternalOrderModel) presentationModel.getBean())
							.getObject(), orderLine.getAttributeInfo(), null,
					orderLine.getNumberOfItems());
			orderLineManager
					.lazyLoad(
							orderLine,
							new LazyLoadOrderLineEnum[] { LazyLoadOrderLineEnum.ORDER_LINE_ATTRIBUTE });
			Set<OrderLineAttribute> orderLineAttributes = orderLine
					.getOrderLineAttributes();
			Set<ExternalOrderLineAttribute> externalOrderLineAttributes = new HashSet<ExternalOrderLineAttribute>();
			ExternalOrderLineAttribute externalOrderLineAttribute;
			if (orderLineAttributes != null) {
				for (OrderLineAttribute orderLineAttribute : orderLineAttributes) {
					externalOrderLineAttribute = new ExternalOrderLineAttribute(
							null, externalOrderLine, orderLineAttribute
									.getAttributeValue(), orderLineAttribute
									.getAttributeName());
					externalOrderLineAttributes.add(externalOrderLineAttribute);
				}
			}
			externalOrderLine
					.setExternalOrderLineAttributes(externalOrderLineAttributes);

			boolean isCanceled = openEditExternalOrderLine(externalOrderLine,
					window);

			if (!isCanceled) {
				externalOrderLine.setAttributeInfo(externalOrderLine
						.getExternalOrderLineAttributesAsString());
				Set<ExternalOrderLine> orderLines = (Set<ExternalOrderLine>) presentationModel
						.getBufferedValue(ExternalOrderModel.PROPERTY_EXTERNAL_ORDER_LINES);
				if (orderLines == null) {
					orderLines = new HashSet<ExternalOrderLine>();
				}
				orderLines.add(externalOrderLine);
				externalArticleList.add(externalOrderLine);
				orderLine.setExternalOrderLine(externalOrderLine);

				presentationModel.setBufferedValue(
						ExternalOrderModel.PROPERTY_EXTERNAL_ORDER_LINES,
						orderLines);
				orderArticleTableModel.fireRowChanged();
			}

		}
	}

	/**
	 * Legg til artikkel
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class AddArticleAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * 
		 */
		private WindowInterface window;
		
		/**
		 * @param aWindow
		 *            OF
		 * 
		 */
		public AddArticleAction(WindowInterface aWindow) {
			super("Ny artikkel...");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			ArticleTypeViewHandler articleTypeViewHandler = new ArticleTypeViewHandler(login,managerRepository,
					null);
			ArticleTypeView articleTypeView = new ArticleTypeView(
					articleTypeViewHandler, true, true);
			WindowInterface dialog = new JDialogAdapter(new JDialog(
					ProTransMain.PRO_TRANS_MAIN, "Artikkel", true));
			dialog.setName("ArticleView");
			dialog.add(articleTypeView.buildPanel(dialog));
			dialog.pack();
			Util.locateOnScreenCenter(dialog);
			dialog.setVisible(true);

			orderManager.refreshObject(order);

			List<ArticleType> newArticles = articleTypeView
					.getSelectedObjects();
			if (newArticles != null && newArticles.size() > 0) {
				ArticleType newArticle = newArticles.get(0);
				OrderLine newOrderLine = new OrderLine(null, order, null,
						newArticle, null, null, null, null, null, null,
						newArticle.getArticleTypeName(), null, 1, null, null,
						null, null, null, null, null, null, null);
				order.setColliesDone(0);
				newOrderLine.setAttributeInfo(newOrderLine
						.getAttributesAsString());
				orderLineManager.saveOrderLine(newOrderLine);
				try {
					orderLineManager.saveOrder(order);

					order.addOrderLine(newOrderLine);
					orderArticleList.add(newOrderLine);
				} catch (ProTransException e) {
					Util.showErrorDialog(window, "Feil", e.getMessage());
					e.printStackTrace();
				}
			}

		}
	}

	/**
	 * Åpner dialog for editering av ordrelinje
	 * 
	 * @param externalOrderLine
	 * @param window
	 * @return true dersom dialog er avbrutt
	 */
	boolean openEditExternalOrderLine(ExternalOrderLine externalOrderLine,
			WindowInterface window) {

		((ExternalOrderManager) overviewManager).lazyLoadExternalOrderLine(
				externalOrderLine, new LazyLoadEnum[][] { {
						LazyLoadEnum.EXTERNAL_ORDER_LINE_ATTRIBUTES,
						LazyLoadEnum.NONE } });
		ExternalOrderLineViewHandler externalOrderLineViewHandler = new ExternalOrderLineViewHandler(login,managerRepository);
		EditExternalOrderLineView externalOrderLineView = new EditExternalOrderLineView(
				externalOrderLine, externalOrderLineViewHandler);
		JDialog dialog = Util.getDialog(window, "Artikkel", true);
		WindowInterface dialogWindow = new JDialogAdapter(dialog);
		dialogWindow.add(externalOrderLineView.buildPanel(dialogWindow));
		dialogWindow.pack();
		Util.locateOnScreenCenter(dialogWindow);
		dialogWindow.setVisible(true);
		return externalOrderLineView.isCanceled();
	}

	/**
	 * Editering av ordrelinje
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class EditExternalOrderLineAction extends AbstractAction {
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
		public EditExternalOrderLineAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Editer...");

			presentationModel = aPresentationModel;
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent arg0) {
			Set<ExternalOrderLine> orderLines = (Set<ExternalOrderLine>) presentationModel
					.getBufferedValue(ExternalOrderModel.PROPERTY_EXTERNAL_ORDER_LINES);
			ExternalOrderLine externalOrderLine = (ExternalOrderLine) externalArticleSelectionList
					.getElementAt(tableExternalOrders
							.convertRowIndexToModel(externalArticleSelectionList
									.getSelectionIndex()));
			openEditExternalOrderLine(externalOrderLine, window);
			externalOrderLine.setAttributeInfo(externalOrderLine
					.getExternalOrderLineAttributesAsString());

			presentationModel.setBufferedValue(
					ExternalOrderModel.PROPERTY_EXTERNAL_ORDER_LINES,
					orderLines);

		}
	}

	/**
	 * Fjerner ekstern ordrelinje
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class RemoveExternalOrderLineAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 
		 */
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public RemoveExternalOrderLineAction(
				PresentationModel aPresentationModel) {
			super("Fjern");
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@SuppressWarnings("unchecked")
		public void actionPerformed(ActionEvent e) {
			ExternalOrderLine externalOrderLine = (ExternalOrderLine) externalArticleSelectionList
					.getElementAt(tableExternalOrders
							.convertRowIndexToModel(externalArticleSelectionList
									.getSelectionIndex()));

			Set<ExternalOrderLine> orderLines = (Set<ExternalOrderLine>) presentationModel
					.getBufferedValue(ExternalOrderModel.PROPERTY_EXTERNAL_ORDER_LINES);
			if (orderLines != null) {

				orderLines.remove(externalOrderLine);
				externalArticleList.remove(externalOrderLine);
				OrderLine orderLine = externalOrderLine.getOrderLine();
				if (orderLine != null) {
					orderLine.setExternalOrderLine(null);

				}
				presentationModel.setBufferedValue(
						ExternalOrderModel.PROPERTY_EXTERNAL_ORDER_LINES,
						orderLines);
				orderArticleTableModel.fireRowChanged();
			}

		}
	}

	/**
	 * Håndterer selektering av ordrelinjer
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class OrderArticleEmptySelection implements PropertyChangeListener {
		/**
		 * 
		 */
		private JButton button;

		/**
		 * @param aButton
		 */
		public OrderArticleEmptySelection(JButton aButton) {
			button = aButton;
		}

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent arg0) {
			if (hasWriteAccess()) {
				button.setEnabled(orderArticleSelectionList.hasSelection());
			}

		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setFlushing(boolean)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setFlushingExt(boolean flushing) {
		List<OrderLine> orderLines = orderArticleList;
		for (OrderLine orderLine : orderLines) {
			if (orderLine.getExternalOrderLine() != null
					&& orderLine.getExternalOrderLine()
							.getExternalOrderLineId() == null) {
				orderLine.setExternalOrderLine(null);
			}
		}
	}

	/**
	 * Håndterer selektering av eksterne artikler
	 * 
	 * @author atle.brekka
	 * 
	 */
	class ExternalArticleSelectionListener implements PropertyChangeListener {

		/**
		 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
		 */
		public void propertyChange(PropertyChangeEvent arg0) {
			if (hasWriteAccess()) {
				boolean hasSelection = externalArticleSelectionList
						.hasSelection();
				buttonEditExternalOrderLine.setEnabled(hasSelection);
				buttonRemoveExternalOrderLine.setEnabled(hasSelection);
			}

		}

	}

	/**
	 * Genererer fax
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class FaxAction extends AbstractAction {
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
		public FaxAction(WindowInterface aWindow,
				PresentationModel aPresentationModel) {
			super("Lag fax");
			presentationModel = aPresentationModel;
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			Util.runInThreadWheel(window.getRootPane(), new Printer(window,
					presentationModel), null);

		}
	}

	/**
	 * Håndterer selektering av leverandør
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class SupplierSelectionListener implements ItemListener {
		/**
		 * 
		 */
		private PresentationModel presentationModel;

		/**
		 * @param aPresentationModel
		 */
		public SupplierSelectionListener(PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
		}

		/**
		 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
		 */
		public void itemStateChanged(ItemEvent itemEvent) {
			if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
				Supplier supplier = (Supplier) itemEvent.getItem();
				presentationModel.setBufferedValue(
						ExternalOrderModel.PROPERTY_PHONE_NR, supplier
								.getPhone());
				presentationModel.setBufferedValue(
						ExternalOrderModel.PROPERTY_FAX_NR, supplier.getFax());
			}

		}

	}

	/**
	 * Genererer fax
	 * 
	 * @author atle.brekka
	 * 
	 */
	private class Printer implements Threadable {
		/**
		 * 
		 */
		private WindowInterface owner;

		/**
		 * 
		 */
		private PresentationModel presentationModel;

		/**
		 * @param aOwner
		 * @param aPresentationModel
		 */
		public Printer(WindowInterface aOwner,
				PresentationModel aPresentationModel) {
			presentationModel = aPresentationModel;
			owner = aOwner;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWhenFinished(java.lang.Object)
		 */
		public void doWhenFinished(Object object) {

		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#doWork(java.lang.Object[],
		 *      javax.swing.JLabel)
		 */
		public Object doWork(Object[] params, JLabel labelInfo) {
			ExternalOrder externalOrder = ((ExternalOrderModel) presentationModel
					.getBean()).getObject();
			if (externalOrder != null
					&& externalOrder.getExternalOrderLines() != null) {
				labelInfo.setText("Genererer fax...");

				ReportViewer reportViewer = new ReportViewer("Fax");

				WindowInterface window;

				if (owner instanceof JDialogAdapter) {
					window = new JDialogAdapter(new JDialog((JDialog) owner
							.getComponent(), "Fax"));
				} else if (owner instanceof JFrameAdapter) {
					window = new JDialogAdapter(new JDialog((JFrame) owner
							.getComponent(), "Fax"));
				} else {
					window = new JInternalFrameAdapter(new JInternalFrame(
							"Fax", true, true, true, true));
					ProTransMain.PRO_TRANS_MAIN.addInternalFrame(window);
				}

				window.add(reportViewer.buildPanel(window));

				try {
					String uglandAddress = ApplicationParamUtil
							.findParamByName("ugland_adresse");
					String uglandFax = ApplicationParamUtil
							.findParamByName("ugland_fax");
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("ugland_adresse", uglandAddress);
					parameters.put("ugland_fax", uglandFax);

					ArrayListModel list = new ArrayListModel();
					list.addAll(externalOrder.getExternalOrderLines());
					reportViewer.generateProtransReport(
							new FaxTableModel(list), "Fax", ReportEnum.FAX,
							parameters);
					window.pack();
					window.setSize(new Dimension(850, 700));
					Util.locateOnScreenCenter(window);
					window.setVisible(true);
				} catch (ProTransException e) {
					e.printStackTrace();
				}
			}

			return null;
		}

		/**
		 * @see no.ugland.utransprod.util.Threadable#enableComponents(boolean)
		 */
		public void enableComponents(boolean enable) {
		}

	}

	/**
	 * Tabellmodell for fax
	 * 
	 * @author atle.brekka
	 * 
	 */
	private final class FaxTableModel extends AbstractTableAdapter {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param listModel
		 */
		public FaxTableModel(ListModel listModel) {
			super(listModel, new String[] { "SUPPLIER_NAME", "ATT", "OUR_REF",
					"PHONE_NR", "FAX_NR", "MARKED_WITH", "DELIVERY_DATE",
					"ORDER_LINE", "NUMBER_OF" });
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int row, int column) {
			ExternalOrderLine externalOrderLine = (ExternalOrderLine) getRow(row);
			switch (column) {
			case 0:
				return externalOrderLine.getExternalOrder().getSupplier()
						.getSupplierName();
			case 1:
				return externalOrderLine.getExternalOrder().getAtt();
			case 2:
				return externalOrderLine.getExternalOrder().getOurRef();
			case 3:
				return externalOrderLine.getExternalOrder().getPhoneNr();
			case 4:
				return externalOrderLine.getExternalOrder().getFaxNr();
			case 5:
				return externalOrderLine.getExternalOrder().getMarkedWith();
			case 6:
				return externalOrderLine.getExternalOrder().getDeliveryDate();
			case 7:
				return externalOrderLine.getArticleName() + " "
						+ externalOrderLine.getAttributeInfo();
			case 8:
				return externalOrderLine.getNumberOfItems();
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int column) {
			switch (column) {
			case 0:
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 7:
				return String.class;
			case 6:
				return Date.class;
			case 8:
				return Integer.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
	 */
	@Override
	public Boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(userType, "Bestillinger");
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
	protected AbstractEditView<ExternalOrderModel, ExternalOrder> getEditView(
			AbstractViewHandler<ExternalOrder, ExternalOrderModel> handler,
			ExternalOrder object, boolean searching) {
		object.setOrder(order);
		overviewManager.lazyLoad(object, new LazyLoadEnum[][] { {
				LazyLoadEnum.EXTERNAL_ORDER_LINES, LazyLoadEnum.NONE } });

		return new EditExternalOrderView(object, this, searching);
	}

	@Override
	public CheckObject checkDeleteObject(ExternalOrder object) {
		return null;
	}

	@Override
	public CheckObject checkSaveObject(ExternalOrderModel object,
			PresentationModel presentationModel, WindowInterface window) {
		return null;
	}

	@Override
	public String getClassName() {
		return null;
	}

}
