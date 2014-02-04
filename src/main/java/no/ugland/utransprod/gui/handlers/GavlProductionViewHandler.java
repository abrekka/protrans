package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.swing.JCheckBox;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.GavlProductionV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.common.collect.Lists;
import com.jgoodies.binding.adapter.AbstractTableAdapter;

/**
 * Håndterer gavlproduksjon. Måtte lage en egen for gavl siden denne skal vise
 * info om takstol
 * 
 * @author atle.brekka
 */
public class GavlProductionViewHandler extends ProductionViewHandler {
    private JCheckBox checkBoxFilterStandard;
    private JCheckBox checkBoxFilterOwn;

    /**
     * @param productionInterface
     * @param title
     * @param deviationViewHandlerFactory
     * @param userType
     * @param applicationUser
     */
    public GavlProductionViewHandler(ApplyListInterface<Produceable> productionInterface, String title, Login login, ArticleType articleType,
	    ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory,
	    SetProductionUnitActionFactory aSetProductionUnitActionFactory) {
	super(productionInterface, title, login, null, "produksjon", TableEnum.TABLEPRODUCTIONGAVL, articleType, managerRepository,
		deviationViewHandlerFactory, aSetProductionUnitActionFactory);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.ProductionViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected TableModel getTableModel(WindowInterface window) {
	return new GavlProductionTableModel(getObjectSelectionList(), window);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.ProductionViewHandler#getApplyColumn()
     */
    @Override
    protected Integer getApplyColumn() {
	return 6;
    }

    public static enum GavlColumn {
	TRANSPORT("Transport") {
	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return gavlProductionV.getTransportDetails();
	    }

	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }
	},
	ORDRE("Ordre") {
	    @Override
	    public Class getColumnClass() {
		return GavlProductionV.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return gavlProductionV;
	    }
	},
	PROD_DATO("Prod.dato") {
	    @SuppressWarnings("unchecked")
	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return Util.formatDate(gavlProductionV.getProductionDate(), Util.SHORT_DATE_TIME_FORMAT);
	    }
	},
	ANTALL("Antall") {
	    @Override
	    public Class getColumnClass() {
		return Integer.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return gavlProductionV.getNumberOfItems() != null ? gavlProductionV.getNumberOfItems().intValue() : null;
	    }
	},
	SPESIFIKASJON("Spesifikasjon") {
	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		if (gavlProductionV.getOrdln() != null) {
		    return gavlProductionV.getOrdln().getDescription();
		}
		return Util.removeNoAttributes(gavlProductionV.getAttributeInfo());
	    }
	},
	TAKSTOL("Takstol") {
	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return getStatus(takstolChecker, statusMap, gavlProductionV, window, managerRepository, applyListInterface);
	    }
	},
	OPPLASTING("Opplasting") {
	    @SuppressWarnings("unchecked")
	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return gavlProductionV.getLoadingDate() != null ? Util.SHORT_DATE_FORMAT.format(gavlProductionV.getLoadingDate()) : null;
	    }
	},
	PRODUSERT("Produsert") {
	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return gavlProductionV.getProduced() != null ? Util.SHORT_DATE_TIME_FORMAT.format(gavlProductionV.getProduced()) : "---";
	    }
	},
	PRODUKTOMRÅDE("Produktområde") {
	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return gavlProductionV.getProductAreaGroupName() != null ? gavlProductionV.getProductAreaGroupName() : "";
	    }
	},
	PROD_ENHET("Prod.enhet") {
	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return gavlProductionV.getProductionUnitName();
	    }
	},
	STARTET("Startet") {
	    @SuppressWarnings("unchecked")
	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return gavlProductionV.getActionStarted() != null ? Util.SHORT_DATE_TIME_FORMAT.format(gavlProductionV.getActionStarted()) : "---";
	    }
	},
	STANDARD("Standard") {
	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		String takstolStatus = getStatus(takstolChecker, statusMap, gavlProductionV, window, managerRepository, applyListInterface);
		return takstolStatus.contains("e") ? "nei" : "ja";
	    }

	    @Override
	    public Class getColumnClass() {
		return String.class;
	    }
	},
	REELL_TIDSFORBRUK("Reell tidsforbruk") {
	    @Override
	    public Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		    Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		    ApplyListInterface<Produceable> applyListInterface) {
		return gavlProductionV.getRealProductionHours() == null ? Tidsforbruk.beregnTidsforbruk(gavlProductionV.getActionStarted(),
			gavlProductionV.getProduced()) : gavlProductionV.getRealProductionHours();
	    }

	    @Override
	    public Class getColumnClass() {
		return BigDecimal.class;
	    }
	};

	private String columnName;
	private static final List<String> columNames = Lists.newArrayList();
	static {
	    for (GavlColumn col : GavlColumn.values()) {
		columNames.add(col.getColumnName());
	    }
	}

	private GavlColumn(String aColumnName) {
	    columnName = aColumnName;
	}

	public String getColumnName() {
	    return columnName;
	}

	public static String[] getColumnNames() {
	    String[] columnArray = new String[columNames.size()];
	    return columNames.toArray(columnArray);
	}

	public abstract Object getValue(GavlProductionV gavlProductionV, StatusCheckerInterface<Transportable> takstolChecker,
		Map<String, String> statusMap, WindowInterface window, ManagerRepository managerRepository,
		ApplyListInterface<Produceable> applyListInterface);

	public abstract Class getColumnClass();

	static String getStatus(StatusCheckerInterface<Transportable> checker, Map<String, String> statusMap, GavlProductionV gavlProductionV,
		WindowInterface window, ManagerRepository managerRepository, ApplyListInterface<Produceable> applyListInterface) {
	    String status = statusMap.get(checker.getArticleName());
	    if (status != null) {
		return status;
	    }

	    Order order = managerRepository.getOrderManager().findByOrderNr(gavlProductionV.getOrderNr());
	    if (order != null) {
		managerRepository.getOrderManager().lazyLoad(order,
			new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.ORDER_LINE_ATTRIBUTES } });
		status = checker.getArticleStatus(order);
		statusMap.put(checker.getArticleName(), status);
		order.setStatus(Util.statusMapToString(statusMap));
		try {
		    managerRepository.getOrderManager().saveOrder(order);
		} catch (ProTransException e) {
		    Util.showErrorDialog(window, "Feil", e.getMessage());
		    e.printStackTrace();
		}
		applyListInterface.refresh(gavlProductionV);

	    }
	    return status;
	}
    }

    /**
     * Tabellmodell for gavlproduksjon
     * 
     * @author atle.brekka
     */
    final class GavlProductionTableModel extends AbstractTableAdapter {

	private static final long serialVersionUID = 1L;

	private StatusCheckerInterface<Transportable> takstolChecker;

	private WindowInterface window;

	/**
	 * @param listModel
	 * @param aWindow
	 */
	public GavlProductionTableModel(ListModel listModel, WindowInterface aWindow) {
	    super(listModel, GavlColumn.getColumnNames());
	    window = aWindow;
	    takstolChecker = Util.getTakstolChecker(managerRepository);

	}

	/**
	 * Henter verdi
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return verdi
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
	    GavlProductionV gavlProductionV = (GavlProductionV) getRow(rowIndex);
	    DecimalFormat decimalFormat = new DecimalFormat();
	    decimalFormat.setDecimalSeparatorAlwaysShown(false);
	    decimalFormat.setParseIntegerOnly(true);
	    Map<String, String> statusMap = Util.createStatusMap(gavlProductionV.getOrderStatus());

	    String columnName = StringUtils.replace(StringUtils.upperCase(getColumnName(columnIndex)), ".", "_");
	    columnName = StringUtils.replace(columnName, " ", "_");
	    return GavlColumn.valueOf(columnName).getValue(gavlProductionV, takstolChecker, statusMap, window, managerRepository, applyListInterface);

	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    return GavlColumn
		    .valueOf(StringUtils.replace(StringUtils.replace(StringUtils.upperCase(getColumnName(columnIndex)), ".", "_"), " ", "_"))
		    .getColumnClass();
	}
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.ProductionViewHandler#getProductAreaColumn()
     */
    @Override
    protected int getProductAreaColumn() {
	return 8;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.ProductionViewHandler#getStartColumn()
     */
    @Override
    protected Integer getStartColumn() {
	return 10;
    }

    @Override
    void setAdditionHighlighters() {
	ColorHighlighter redPattern = new ColorHighlighter(new PatternPredicate("nei", GavlColumn.STANDARD.ordinal()), null, ColorEnum.RED.getColor());
	redPattern.setSelectedForeground(ColorEnum.RED.getColor());
	table.addHighlighter(redPattern);
    }

    public JCheckBox getCheckBoxFilterStandard(WindowInterface window) {
	checkBoxFilterStandard = new JCheckBox("Vis standard");
	checkBoxFilterStandard.setSelected(true);
	checkBoxFilterStandard.setName("CheckBoxFilterStandard");
	checkBoxFilterStandard.addActionListener(new FilterStandardActionListener());
	return checkBoxFilterStandard;
    }

    class FilterStandardActionListener implements ActionListener {
	public void actionPerformed(final ActionEvent arg0) {
	    handleFilter();
	}

    }

    protected void handleFilter() {
	table.clearSelection();
	objectSelectionList.clearSelection();

	ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);
	PrefsUtil.setInvisibleColumns(group.getProductAreaGroupName(), table.getName(), table);
	if (group.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
	    group = null;
	}
	List<Filter> filterList = new ArrayList<Filter>();

	if (!checkBoxFilter.isSelected()) {
	    Filter filterApplied = new PatternFilter("---", Pattern.CASE_INSENSITIVE, getApplyColumn());
	    filterList.add(filterApplied);
	}
	if (!checkBoxFilterStandard.isSelected()) {
	    Filter filterStandard = new PatternFilter("nei", Pattern.CASE_INSENSITIVE, 11);
	    filterList.add(filterStandard);
	}
	if (!checkBoxFilterOwn.isSelected()) {
	    Filter filterStandard = new PatternFilter("ja", Pattern.CASE_INSENSITIVE, 11);
	    filterList.add(filterStandard);
	}
	if (group != null) {
	    PatternFilter filterProductAreaGroup = new PatternFilter(group.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE,
		    getProductAreaColumn());
	    filterList.add(filterProductAreaGroup);
	}
	setAdditionFilters(filterList);
	if (filterList.size() != 0) {
	    Filter[] filterArray = new Filter[filterList.size()];
	    FilterPipeline filterPipeline = new FilterPipeline(filterList.toArray(filterArray));
	    table.setFilters(filterPipeline);
	} else {
	    table.setFilters(null);
	}
	table.repaint();
	handleFilterExt();
    }

    public JCheckBox getCheckBoxFilterOwn(WindowInterface window) {
	checkBoxFilterOwn = new JCheckBox("Vis egenordre");
	checkBoxFilterOwn.setSelected(true);
	checkBoxFilterOwn.setName("CheckBoxFilterOwn");
	checkBoxFilterOwn.addActionListener(new FilterStandardActionListener());
	return checkBoxFilterOwn;
    }

    @Override
    public String getTableWidth() {
	return "260dlu";
    }

    void initColumnWidthExt() {
	// Transport
	table.getColumnExt(table.getModel().getColumnName(0)).setPreferredWidth(100);
	// Ordre
	table.getColumnExt(table.getModel().getColumnName(1)).setPreferredWidth(200);
	// Prod.dato
	table.getColumnExt(table.getModel().getColumnName(2)).setPreferredWidth(70);

	// Antall
	table.getColumnExt(table.getModel().getColumnName(3)).setPreferredWidth(50);

	// Spesifikasjon
	table.getColumnExt(table.getModel().getColumnName(4)).setPreferredWidth(400);

	// Produsert
	table.getColumnExt(table.getModel().getColumnName(7)).setPreferredWidth(110);

	// Startet
	table.getColumnExt(table.getModel().getColumnName(10)).setPreferredWidth(110);
	// kalkulert tidsforbruk
	table.getColumnExt(table.getModel().getColumnName(12)).setPreferredWidth(120);

    }

}
