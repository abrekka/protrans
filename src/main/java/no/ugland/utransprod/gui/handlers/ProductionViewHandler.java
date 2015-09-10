package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.SetProductionUnitAction;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.TextPaneRendererOrder;
import no.ugland.utransprod.gui.model.TextPaneStringRenderer;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

/**
 * Håndterer produksjonsvinduer
 * 
 * @author atle.brekka
 */
public class ProductionViewHandler extends AbstractProductionPackageViewHandler<Produceable> {
    private static final String SHOW_PRODUCED = "Vis produsert";
    private static final String APPLY_TEXT_PRODUCED = "produsert";
    private static final String SET = "Sett ";
    private static final String NOT_SET = "Sett ikke ";
    String applyText;

    String startText;
    ArticleType articleType;
    private Produceable produceable;
    protected SetProductionUnitActionFactory setProductionUnitActionFactory;

    public ProductionViewHandler(final ApplyListInterface<Produceable> productionInterface, final String title, final Login login,
	    final String aApplyText, final String aStartText, final TableEnum tableEnum, final ArticleType aArticleType,
	    ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory,
	    SetProductionUnitActionFactory aSetProductionUnitActionFactory) {
	super(login, managerRepository, deviationViewHandlerFactory, productionInterface, title, tableEnum);
	articleType = aArticleType;
	setProductionUnitActionFactory = aSetProductionUnitActionFactory;

	if (aApplyText != null) {
	    applyText = aApplyText;
	} else {
	    applyText = APPLY_TEXT_PRODUCED;
	}
	startText = aStartText;

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#initColumnWidthExt()
     */
    @Override
    void initColumnWidthExt() {
	// Transport
	table.getColumnExt(table.getModel().getColumnName(0)).setPreferredWidth(100);
	// Ordre
	table.getColumnExt(table.getModel().getColumnName(1)).setPreferredWidth(200);
	// Prod.dato
	// table.getColumnExt(table.getModel().getColumnName(2))
	// .setPreferredWidth(70);

	// Antall
	table.getColumnExt(table.getModel().getColumnName(2)).setPreferredWidth(50);

	// Spesifikasjon
	table.getColumnExt(table.getModel().getColumnName(3)).setPreferredWidth(400);

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyText()
     */
    @Override
    protected final String getApplyText() {
	return SET + applyText;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getUnapplyText()
     */
    @Override
    protected final String getUnapplyText() {
	return NOT_SET + applyText;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getCheckBoxText()
     */
    @Override
    protected final String getCheckBoxText() {
	return SHOW_PRODUCED;
    }

    /**
     * @param produceable
     * @return true dersom enabled
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#
     *      getButtonApplyEnabled(no.ugland.utransprod.gui.model.Applyable)
     */
    @Override
    protected boolean getButtonApplyEnabled(final Produceable produceable) {
	if (produceable != null && produceable.getProduced() == null) {
	    return true;
	}
	return false;
    }

    /**
     * @param produceable
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#
     *      checkLazyLoad(no.ugland.utransprod.gui.model.Applyable)
     */
    @Override
    protected void checkLazyLoad(final Produceable produceable) {
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#
     *      getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected TableModel getTableModel(final WindowInterface window) {
	if (startText != null) {
	    return new ProductionTableModel(getObjectSelectionList(), true);
	}
	return new ProductionTableModel(getObjectSelectionList());
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyColumn()
     */
    @Override
    protected Integer getApplyColumn() {
	return 6;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#
     *      searchOrder(java.lang.String, java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected void searchOrder(final String orderNr, final String customerNr, final WindowInterface window) {
	try {
	    List<Produceable> produceables = applyListInterface.doSearch(orderNr, customerNr,
		    (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
	    objectSelectionList.clearSelection();

	    Produceable produceable = getSearchObject(window, produceables);
	    if (produceable != null) {
		int selectedIndex = objectList.indexOf(produceable);
		table.getSelectionModel()
			.setSelectionInterval(table.convertRowIndexToView(selectedIndex), table.convertRowIndexToView(selectedIndex));

		table.scrollRowToVisible(table.getSelectedRow());

	    }
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	}

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getOrderCellRenderer()
     */
    @Override
    protected TableCellRenderer getOrderCellRenderer() {
	return new TextPaneRendererOrder();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getOrderInfoCell()
     */
    @Override
    protected int getOrderInfoCell() {
	return 1;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getReportEnum()
     */
    @Override
    protected ReportEnum getReportEnum() {
	return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#hasWriteAccess()
     */
    @Override
    protected boolean hasWriteAccess() {
	return applyListInterface.hasWriteAccess();
    }

    /**
     * Tabellmodell for produksjon
     * 
     * @author atle.brekka
     */
    final class ProductionTableModel extends AbstractTableAdapter {

	private static final long serialVersionUID = 1L;

	/**
	 * @param listModel
	 */
	public ProductionTableModel(final ListModel listModel) {
	    super(listModel, new String[] { "Transport", "Ordre", "Prod.dato", "Antall", "Spesifikasjon", "Opplasting",
		    StringUtils.capitalize(applyText), "Produktområde", "Prod.enhet" });

	}

	public ProductionTableModel(final ListModel listModel, boolean userStarted) {
	    super(listModel, new String[] { "Transport", "Ordre", "Prod.dato", "Antall", "Spesifikasjon", "Opplasting",
		    StringUtils.capitalize(applyText), "Produktområde", "Prod.enhet", "Startet" });

	}

	/**
	 * Henter verdi
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return verdi
	 */
	public Object getValueAt(final int rowIndex, final int columnIndex) {
	    Produceable produceable = (Produceable) getRow(rowIndex);
	    DecimalFormat decimalFormat = new DecimalFormat();
	    decimalFormat.setDecimalSeparatorAlwaysShown(false);
	    decimalFormat.setParseIntegerOnly(true);

	    switch (columnIndex) {
	    case 0:
		return produceable.getTransportDetails();
	    case 1:
		return produceable;
	    case 2:
		return Util.formatDate(produceable.getProductionDate(), Util.SHORT_DATE_FORMAT);
	    case 3:
		if (produceable.getNumberOfItems() != null) {
		    return decimalFormat.format(produceable.getNumberOfItems());
		}
		return "";
	    case 4:
		return Util.removeNoAttributes(produceable.getAttributeInfo());
	    case 5:

		Date loadingDate = produceable.getLoadingDate();
		if (loadingDate != null) {
		    return Util.SHORT_DATE_FORMAT.format(loadingDate);
		}
		return null;

	    case 6:
		if (produceable.getProduced() != null) {
		    return Util.SHORT_DATE_FORMAT.format(produceable.getProduced());
		}
		return "---";
	    case 7:
		if (produceable.getProductAreaGroupName() != null) {
		    return produceable.getProductAreaGroupName();
		}
		return "";
	    case 8:
		return produceable.getProductionUnitName();
	    case 9:
		if (produceable.getActionStarted() != null) {
		    return Util.SHORT_DATE_FORMAT.format(produceable.getActionStarted());
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
	public Class<?> getColumnClass(final int columnIndex) {
	    switch (columnIndex) {
	    case 0:
	    case 2:
	    case 3:
	    case 4:
	    case 5:
	    case 7:
	    case 8:
	    case 9:
		return String.class;
	    case 1:
		return Produceable.class;
	    case 6:
		return Object.class;
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

    }

    /**
     * @param object
     * @param applied
     * @param window
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#
     *      setApplied(no.ugland.utransprod.gui.model.Applyable, boolean,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected void setApplied(final Produceable object, final boolean applied, final WindowInterface window) {
	if (applied && object.getProduced() == null) {
	    object.setProduced(Util.getCurrentDate());
	}
	if (applied) {
	    setProductionUnit(window, object);
	}
	applyListInterface.setApplied(object, applied, window);

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getSpecificationCell()
     */
    @Override
    protected int getSpecificationCell() {
	return 4;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#
     *      getSpecificationCellRenderer()
     */
    @Override
    protected TableCellRenderer getSpecificationCellRenderer() {
	return new TextPaneStringRenderer();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getTableModelReport()
     */
    @Override
    protected TableModel getTableModelReport() {
	return applyListInterface.getTableModelReport(getObjectSelectionList(), table, objectSelectionList);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#setInvisibleCells()
     */
    @Override
    protected void setInvisibleCells() {
	applyListInterface.setInvisibleColumns(table);

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#
     *      getApplyObject(no.ugland.utransprod.gui.model.Transportable)
     */
    @Override
    public Produceable getApplyObject(final Transportable transportable, final WindowInterface window) {
	produceable = produceable != null ? produceable : applyListInterface.getApplyObject(transportable, window);
	return produceable;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getProductAreaColumn()
     */
    @Override
    protected int getProductAreaColumn() {
	return 7;
    }

    @Override
    protected String getNotStartText() {
	if (startText != null) {
	    return "Ikke startet " + startText;
	}
	return null;
    }

    @Override
    protected String getStartText() {
	if (startText != null) {
	    return "Startet " + startText;
	}
	return null;
    }

    @Override
    protected void setStarted(final Produceable object, final boolean started) {
	applyListInterface.setStarted(object, started);
	// if (object.getOrderLineId() == null) {
	// doRefresh(null);
	// }

    }

    @Override
    protected void setRealProductionHours(Produceable object, BigDecimal overstyrtTidsforbruk) {
	applyListInterface.setRealProductionHours(object, overstyrtTidsforbruk);

    }

    @Override
    protected boolean getButtonStartEnabled(Produceable produceable) {
	if (produceable != null && produceable.getActionStarted() == null
		&& (produceable.getProbability() == null || produceable.getProbability() != 90)) {
	    return true;
	}
	return false;
    }

    @Override
    protected Integer getStartColumn() {
	if (startText != null) {
	    return 9;
	}
	return null;
    }

    protected ProductionUnit setProductionUnit(WindowInterface window, Produceable produceable) {
	SetProductionUnitAction setProductionUnitAction = setProductionUnitActionFactory.create(articleType, null, window);
	return setProductionUnitAction.setProductionUnit(produceable);

    }

    @Override
    public Dimension getWindowSize() {
	return new Dimension(1070, 800);
    }

    @Override
    public String getTableWidth() {
	return "350dlu";
    }

    @Override
    public void clearApplyObject() {
	produceable = null;

    }

}
