package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.ListModel;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.ColliListView;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.ProduceableProvider;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.ColliListener;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Colli;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.google.common.collect.Multimap;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.name.Named;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

public class ArticlePackageViewHandler implements Closeable, ProduceableProvider {
    private final ArrayListModel articles;
    private JButton buttonSetProductionUnit;
    private SelectionInList articleSelectionList;
    private SetProductionUnitActionFactory setProductionUnitActionFactory;
    private ArticleType articleType;
    private JXTable tableArticles;
    private JButton buttonSetProductionDate;
    private Login login;
    private ManagerRepository managerRepository;
    private Multimap<String, String> colliSetup;
    private Map<String, Colli> colliMap = new HashMap<String, Colli>();
    private ColliListViewHandler colliListViewHandler;
    private List<ColliListener> colliListeners = new ArrayList<ColliListener>();
    private String defaultColliName;
    private VismaFileCreator vismaFileCreator;

    @Inject
    public ArticlePackageViewHandler(final SetProductionUnitActionFactory aSetProductionUnitActionFactory, final Login aLogin,
	    final ManagerRepository aManagerRepository, final @Named("colli_setup") Multimap aColliSetup, final @Assisted ArticleType aArticleType,
	    final @Assisted String aDefaultColliName, VismaFileCreator vismaFileCreator) {
	this.vismaFileCreator = vismaFileCreator;
	defaultColliName = aDefaultColliName;
	colliSetup = aColliSetup;
	login = aLogin;
	managerRepository = aManagerRepository;
	articleType = aArticleType;
	setProductionUnitActionFactory = aSetProductionUnitActionFactory;
	articles = new ArrayListModel();
	articleSelectionList = new SelectionInList((ListModel) articles);
	articleSelectionList.addPropertyChangeListener(SelectionInList.PROPERTYNAME_SELECTION_EMPTY, new SelectionIndexListener());
	colliListViewHandler = new ColliListViewHandler(login, managerRepository, colliSetup, vismaFileCreator);
	addColliListener(colliListViewHandler);
    }

    public JXTable getTableArticles(List<Applyable> someArticles, PackProduction packProduction, WindowInterface window) {
	articles.clear();
	boolean success = someArticles != null ? articles.addAll(someArticles) : false;
	tableArticles = new JXTable(new ArticleTableModel<Applyable>(articleSelectionList, packProduction, window));
	tableArticles.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	tableArticles.setSelectionModel(new SingleListSelectionAdapter(articleSelectionList.getSelectionIndexHolder()));
	tableArticles.setName("TableArticles");
	ArticleColumn.initColumnWidth(tableArticles, packProduction);
	return tableArticles;
    }

    private class ArticleTableModel<E> extends AbstractTableAdapter {
	private static final long serialVersionUID = 1L;
	private WindowInterface window;

	public ArticleTableModel(ListModel listModel, PackProduction packing, WindowInterface aWindow) {
	    super(listModel, ArticleColumn.getColumnNames(packing));
	    window = aWindow;

	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    String columnName = getEnumColumnName(columnIndex);
	    return ArticleColumn.valueOf(columnName).getColumnClass();
	}

	private String getEnumColumnName(int columnIndex) {
	    String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_").replaceAll("\\.", "_");
	    return columnName;
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
	    Applyable applyable = (Applyable) getRow(rowIndex);
	    String columnName = getEnumColumnName(columnIndex);
	    return ArticleColumn.valueOf(columnName).getValue(applyable);
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
	 *      int, int)
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	    Applyable applyable = (Applyable) getRow(rowIndex);
	    String columnName = getEnumColumnName(columnIndex);
	    OrderLine orderLine = managerRepository.getOrderLineManager().findByOrderLineId(applyable.getOrderLineId());
	    Colli colli = getColli(applyable, orderLine);
	    ArticleColumn.valueOf(columnName).setValue(applyable, aValue, colli, orderLine);
	    managerRepository.getOrderLineManager().saveOrderLine(orderLine);
	    managerRepository.getColliManager().saveColli(colli);
	    fireRefreshCollies();
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
	    String columnName = getEnumColumnName(columnIndex);
	    return ArticleColumn.valueOf(columnName).isCellEditable();
	}

    }

    private Colli getColli(Applyable applyable, OrderLine orderLine) {
	Colli colli = colliMap.get(applyable.getArticleName() + applyable.getOrderNr());
	if (colli == null) {
	    String colliName = Util.getColliName(applyable.getArticleName());
	    colliName = colliName != null ? colliName : defaultColliName;

	    if (applyable.isForPostShipment()) {
		colli = managerRepository.getColliManager().findByNameAndPostShipment(colliName, orderLine.getPostShipment());
	    } else {
		colli = managerRepository.getColliManager().findByNameAndOrder(colliName, orderLine.getOrder());
	    }
	    if (colli == null) {
		if (orderLine.getPostShipment() != null) {
		    colli = new Colli(null, null, colliName, null, null, null, orderLine.getPostShipment(), null, Util.getCurrentDate());
		} else {
		    colli = new Colli(null, orderLine.getOrder(), colliName, null, null, null, null, null, Util.getCurrentDate());
		}
	    } else {
		managerRepository.getColliManager().lazyLoad(colli, new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.NONE } });
	    }
	    colliMap.put(applyable.getArticleName() + applyable.getOrderNr(), colli);
	}
	return colli;
    }

    private enum ArticleColumn {
	ARTIKKEL("Artikkel", PackProduction.BOTH) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(Applyable object) {
		return object.getArticleName() + " " + (object.getNumberOfItems() != null ? object.getNumberOfItems() : "");
	    }

	    @Override
	    public boolean isCellEditable() {
		return false;
	    }

	    @Override
	    public void setValue(Applyable applyable, Object value, Colli colli, OrderLine orderLine) {
	    }

	    @Override
	    public int getColumnWidth() {
		return 100;
	    }
	},
	PAKKET("Pakket", PackProduction.PACK) {
	    @Override
	    public Class<?> getColumnClass() {
		return Boolean.class;
	    }

	    @Override
	    public Object getValue(Applyable packable) {
		return packable.getColli() != null ? true : false;
	    }

	    @Override
	    public boolean isCellEditable() {
		return true;
	    }

	    @Override
	    public void setValue(Applyable applyable, Object value, Colli colli, OrderLine orderLine) {
		if ((Boolean) value) {
		    applyable.setColli(colli);
		    colli.addOrderLine(orderLine);
		} else {
		    applyable.setColli(null);
		    colli.removeOrderLine(orderLine);
		}

	    }

	    @Override
	    public int getColumnWidth() {
		return 50;
	    }
	},
	PRODUSERT("Produsert", PackProduction.PRODUCTION) {
	    @Override
	    public Class<?> getColumnClass() {
		return Boolean.class;
	    }

	    @Override
	    public Object getValue(Applyable applyable) {
		return applyable.getProduced() != null ? Boolean.TRUE : Boolean.FALSE;
	    }

	    @Override
	    public boolean isCellEditable() {
		return true;
	    }

	    @Override
	    public void setValue(Applyable applyable, Object value, Colli colli, OrderLine orderLine) {
		if ((Boolean) value) {
		    applyable.setProduced(Util.getCurrentDate());
		    applyable.setColli(colli);
		    colli.addOrderLine(orderLine);
		} else {
		    applyable.setProduced(null);
		    colli.removeOrderLine(orderLine);
		}

	    }

	    @Override
	    public int getColumnWidth() {
		return 80;
	    }
	},
	PROD_ENHET("Prod.enhet", PackProduction.PRODUCTION) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int getColumnWidth() {
		return 80;
	    }

	    @Override
	    public Object getValue(Applyable applyable) {
		return applyable.getProductionUnitName();
	    }

	    @Override
	    public boolean isCellEditable() {
		return false;
	    }

	    @Override
	    public void setValue(Applyable applyable, Object aValue, Colli colli, OrderLine orderLine) {
	    }
	},
	DATO("Dato", PackProduction.PRODUCTION) {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public int getColumnWidth() {
		return 80;
	    }

	    @Override
	    public Object getValue(Applyable applyable) {
		return applyable.getProduced() != null ? Util.SHORT_DATE_FORMAT.format(applyable.getProduced()) : "";
	    }

	    @Override
	    public boolean isCellEditable() {
		return false;
	    }

	    @Override
	    public void setValue(Applyable applyable, Object aValue, Colli colli, OrderLine orderLine) {
		// TODO Auto-generated method stub

	    }
	}/*
	  * , KOLLI("Kolli", PackProduction.BOTH) {
	  * 
	  * @Override public Class<?> getColumnClass() { return Colli.class; }
	  * 
	  * @Override public int getColumnWidth() { return 80; }
	  * 
	  * @Override public Object getValue(Applyable applyable) { return
	  * applyable.getColli(); }
	  * 
	  * @Override public boolean isCellEditable() { return false; }
	  * 
	  * @Override public void setValue(Applyable applyable, Object aValue) {
	  * 
	  * } }
	  */;
	private String columnName;
	private PackProduction packProduction;

	private ArticleColumn(final String aColumnName, final PackProduction aPackingProduction) {
	    columnName = aColumnName;
	    packProduction = aPackingProduction;

	}

	public String getColumnName() {
	    return columnName;
	}

	public static String[] getColumnNames(PackProduction packProduction) {
	    ArticleColumn[] articleColumns = ArticleColumn.values();

	    List<String> columnNameList = new ArrayList<String>();
	    for (int i = 0; i < articleColumns.length; i++) {
		if (articleColumns[i].getPackProduction().isEqual(packProduction)) {
		    columnNameList.add(articleColumns[i].getColumnName());
		}
	    }
	    String[] columnNames = new String[columnNameList.size()];
	    return columnNameList.toArray(columnNames);
	}

	private PackProduction getPackProduction() {
	    return packProduction;
	}

	public static void initColumnWidth(JXTable table, PackProduction packProduction) {
	    ArticleColumn[] columns = ArticleColumn.values();
	    for (ArticleColumn column : columns) {
		if (column.getPackProduction().isEqual(packProduction)) {
		    table.getColumnExt(column.getColumnName()).setPreferredWidth(column.getColumnWidth());
		}
	    }
	}

	public abstract Object getValue(Applyable applyable);

	public abstract Class<?> getColumnClass();

	public abstract boolean isCellEditable();

	public abstract void setValue(Applyable applyable, Object aValue, Colli colli, OrderLine orderLine);

	public abstract int getColumnWidth();

    }

    public enum PackProduction {
	PACK, PRODUCTION, BOTH;
	public boolean isEqual(PackProduction packProduction) {
	    return this == packProduction || this == BOTH;
	}
    }

    public JButton getButtonOk(WindowInterface window) {
	JButton button = new CancelButton(window, this, false, "Ok", IconEnum.ICON_OK, null, true);
	button.setName("ButtonOk");
	return button;
    }

    public boolean canClose(String actionString, WindowInterface window) {
	return true;
    }

    public JButton getButtonSetProductionUnit(WindowInterface window) {
	buttonSetProductionUnit = new JButton(setProductionUnitActionFactory.create(articleType, this, window));
	buttonSetProductionUnit.setName("ButtonSetProductionUnit");
	buttonSetProductionUnit.setEnabled(false);
	return buttonSetProductionUnit;
    }

    public JButton getButtonSetProductionDate(WindowInterface window) {
	buttonSetProductionDate = new JButton(new SetProductionDateAction(window));
	buttonSetProductionDate.setName("ButtonSetProductionDate");
	buttonSetProductionDate.setEnabled(false);
	return buttonSetProductionDate;
    }

    private class SelectionIndexListener implements PropertyChangeListener {

	public void propertyChange(PropertyChangeEvent evt) {
	    enableButtons();

	}

	private void enableButtons() {
	    boolean hasSelection = articleSelectionList.hasSelection();
	    if (buttonSetProductionUnit != null) {
		buttonSetProductionUnit.setEnabled(hasSelection);
		buttonSetProductionDate.setEnabled(hasSelection);
	    }
	}

    }

    public Produceable getSelectedProduceable() {
	Produceable produceable = null;
	if (articleSelectionList.hasSelection()) {
	    produceable = (Produceable) articleSelectionList.getElementAt(tableArticles.convertRowIndexToModel(articleSelectionList
		    .getSelectionIndex()));
	}
	return produceable;
    }

    private class SetProductionDateAction extends AbstractAction {
	private WindowInterface window;

	public SetProductionDateAction(WindowInterface aWindow) {
	    super("Sett dato..");
	    window = aWindow;
	}

	public void actionPerformed(ActionEvent e) {
	    Produceable produceable = getSelectedProduceable();
	    if (produceable != null) {
		produceable.setProduced(Util.getDate(window));
	    }

	}
    }

    public void addColliListener(ColliListener aColliListener) {
	colliListeners.add(aColliListener);
    }

    private void fireRefreshCollies() {
	for (ColliListener listener : colliListeners) {
	    listener.refreshCollies();
	}
    }

    public ColliListView getColliListView() {

	List<Colli> collies = getCollies();
	Order order = getOrder();
	colliListViewHandler.setPackable(order, collies);

	ColliListView colliListView = new ColliListView(colliListViewHandler);
	return colliListView;
    }

    private Order getOrder() {
	return managerRepository.getOrderManager().findByOrderNr(((Applyable) articles.get(0)).getOrderNr());
    }

    private List<Colli> getCollies() {
	Iterator<Applyable> it = articles.iterator();
	Set<Colli> collies = new HashSet<Colli>();
	while (it.hasNext()) {
	    Colli colli = it.next().getColli();
	    boolean added = colli != null ? collies.add(colli) : false;
	}
	return new ArrayList<Colli>(collies);
    }
}
