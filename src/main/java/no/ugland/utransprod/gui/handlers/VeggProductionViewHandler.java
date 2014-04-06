package no.ugland.utransprod.gui.handlers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.VeggProductionV;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

/**
 * Hjlepeklasse for veggproduksjon
 * 
 * @author atle.brekka
 * 
 */
public class VeggProductionViewHandler extends ProductionViewHandler {

    /**
     * @param productionInterface
     * @param title
     * @param deviationViewHandlerFactory
     * @param userType
     * @param applicationUser
     */
    public VeggProductionViewHandler(ApplyListInterface<Produceable> productionInterface, String title, Login login, ArticleType articleType,
	    ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory,
	    SetProductionUnitActionFactory aSetProductionUnitActionFactory) {
	super(productionInterface, title, login, null, "produksjon", TableEnum.TABLEPRODUCTIONVEGG, articleType, managerRepository,
		deviationViewHandlerFactory, aSetProductionUnitActionFactory);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.ProductionViewHandler#getApplyColumn()
     */
    @Override
    protected Integer getApplyColumn() {
	return 7;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.ProductionViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected TableModel getTableModel(WindowInterface window) {
	return new VeggProductionTableModel(getObjectSelectionList(), window);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#initColumnWidth()
     */
    @Override
    protected void initColumnWidthExt() {
	// Transportdato
	table.getColumnExt(table.getModel().getColumnName(0)).setPreferredWidth(100);
	// Ordre
	table.getColumnExt(table.getModel().getColumnName(1)).setPreferredWidth(200);
	// Prod.dato
	DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
	tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	table.getColumnExt(table.getModel().getColumnName(2)).setCellRenderer(tableCellRenderer);
	table.getColumnExt(table.getModel().getColumnName(2)).setPreferredWidth(70);
	// Antall
	table.getColumnExt(table.getModel().getColumnName(3)).setPreferredWidth(50);
	// spesifikasjon
	table.getColumnExt(table.getModel().getColumnName(4)).setPreferredWidth(200);
	// front
	table.getColumnExt(table.getModel().getColumnName(5)).setPreferredWidth(50);
	// opplasting
	table.getColumnExt(table.getModel().getColumnName(6)).setPreferredWidth(70);
	// produsert
	table.getColumnExt(table.getModel().getColumnName(7)).setPreferredWidth(100);
	// startet
	table.getColumnExt(table.getModel().getColumnName(10)).setPreferredWidth(100);
	// reell tidsforbruk
	// DefaultTableCellRenderer tableCellRenderer = new
	// DefaultTableCellRenderer();
	tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
	table.getColumnExt(table.getModel().getColumnName(11)).setCellRenderer(tableCellRenderer);
	table.getColumnExt(table.getModel().getColumnName(11)).setPreferredWidth(120);
    }

    /**
     * Tabellmodell for veggproduksjon
     * 
     * @author atle.brekka
     * 
     */
    final class VeggProductionTableModel extends AbstractTableAdapter {
	private static final long serialVersionUID = 1L;

	private StatusCheckerInterface<Transportable> frontChecker;

	private WindowInterface window;

	public VeggProductionTableModel(ListModel listModel, WindowInterface aWindow) {
	    super(listModel, new String[] { "Transport", "Ordre", "Prod. uke", "Antall", "Spesifikasjon", "Front", "Opplasting", "Produsert",
		    "Produktområde", "Prod.enhet", "Startet", "Reell tidsforbruk", "Gjort av" });
	    frontChecker = Util.getFrontChecker();
	    window = aWindow;
	    initStatus(listModel);
	}

	private void initStatus(ListModel list) {
	    if (list != null) {
		Map<String, String> statusMap;
		String status;
		int rowCount = getRowCount();

		VeggProductionV prod;
		for (int i = 0; i < rowCount; i++) {
		    prod = (VeggProductionV) getRow(i);
		    statusMap = Util.createStatusMap(prod.getOrderStatus());
		    status = statusMap.get(frontChecker.getArticleName());

		    if (status == null) {
			Order order = managerRepository.getOrderManager().findByOrderNr(prod.getOrderNr());
			if (order != null) {
			    managerRepository.getOrderManager().lazyLoad(order,
				    new LazyLoadEnum[][] { { LazyLoadEnum.ORDER_LINES, LazyLoadEnum.ORDER_LINE_ATTRIBUTES } });
			    status = frontChecker.getArticleStatus(order);
			    statusMap.put(frontChecker.getArticleName(), status);
			    order.setStatus(Util.statusMapToString(statusMap));
			    try {
				managerRepository.getOrderManager().saveOrder(order);
			    } catch (ProTransException e) {
				Util.showErrorDialog(window, "Feil", e.getMessage());
				e.printStackTrace();
			    }
			    applyListInterface.refresh(prod);
			}
		    }
		}
	    }

	}

	public Object getValueAt(int rowIndex, int columnIndex) {
	    VeggProductionV veggProductionV = (VeggProductionV) getRow(rowIndex);
	    DecimalFormat decimalFormat = new DecimalFormat();
	    decimalFormat.setDecimalSeparatorAlwaysShown(false);
	    decimalFormat.setParseIntegerOnly(true);
	    Map<String, String> statusMap = Util.createStatusMap(veggProductionV.getOrderStatus());

	    switch (columnIndex) {
	    case 0:
		return veggProductionV.getTransportDetails();
	    case 1:
		return veggProductionV;
	    case 2:

		return veggProductionV.getProductionWeek();
	    case 3:
		if (veggProductionV.getNumberOfItems() != null) {
		    return decimalFormat.format(veggProductionV.getNumberOfItems());
		}
		return "";

	    case 4:
		if (veggProductionV.getOrdln() != null) {
		    return veggProductionV.getOrdln().getDescription();
		}
		return Util.removeNoAttributes(veggProductionV.getAttributeInfo());
	    case 5:
		return statusMap.get(frontChecker.getArticleName());
	    case 6:
		Date loadingDate = veggProductionV.getLoadingDate();
		if (loadingDate != null) {
		    return Util.SHORT_DATE_FORMAT.format(loadingDate);
		}
		return null;

	    case 7:
		if (veggProductionV.getProduced() != null) {
		    return Util.SHORT_DATE_TIME_FORMAT.format(veggProductionV.getProduced());
		}
		return "---";
	    case 8:
		if (veggProductionV.getProductAreaGroupName() != null) {
		    return veggProductionV.getProductAreaGroupName();
		}
		return "";
	    case 9:
		return veggProductionV.getProductionUnitName();
	    case 10:
		if (veggProductionV.getActionStarted() != null) {
		    return Util.SHORT_DATE_TIME_FORMAT.format(veggProductionV.getActionStarted());
		}
		return "---";
	    case 11:
		return veggProductionV.getRealProductionHours() == null ? Tidsforbruk.beregnTidsforbruk(veggProductionV.getActionStarted(),
			veggProductionV.getProduced()) : veggProductionV.getRealProductionHours();
	    case 12:
		return veggProductionV.getDoneBy();
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

	    case 3:
	    case 4:
	    case 5:
	    case 6:
	    case 7:
	    case 8:
	    case 9:
	    case 10:
	    case 12:
		return String.class;
	    case 2:
		return Integer.class;
	    case 1:
		return VeggProductionV.class;
	    case 11:
		return BigDecimal.class;
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.ProductionViewHandler#getProductAreaColumn()
     */
    @Override
    protected int getProductAreaColumn() {
	return 8;
    }

    @Override
    protected Integer getStartColumn() {
	return 10;
    }

}
