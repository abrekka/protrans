package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.Packable;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.ReportEnum;
import no.ugland.utransprod.gui.model.TextPaneRendererOrder;
import no.ugland.utransprod.gui.model.TextPaneStringRenderer;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.binding.adapter.AbstractTableAdapter;

/**
 * Håndterer visning og editering av pakkevinduer
 * 
 * @author atle.brekka
 */
public class PackageViewHandler extends AbstractProductionPackageViewHandler<PackableListItem> {
    protected String mainArticleName;
    private PackableListItem applyable;

    /**
     * @param packageInterface
     * @param title
     * @param userType
     * @param applicationUser
     */
    @Inject
    public PackageViewHandler(Login login, ManagerRepository managerRepository, DeviationViewHandlerFactory deviationViewHandlerFactory,
	    @Assisted ApplyListInterface<PackableListItem> packageInterface, @Assisted String title, @Assisted TableEnum tableEnum,
	    @Assisted final String aMainArticleName) {
	super(login, managerRepository, deviationViewHandlerFactory, packageInterface, title, tableEnum);
	mainArticleName = aMainArticleName;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyText()
     */
    @Override
    protected String getApplyText() {
	return "Sett pakket";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getUnapplyText()
     */
    @Override
    protected String getUnapplyText() {
	return "Sett ikke pakket";
    }

    /**
     * @param packable
     * @param applied
     * @param window
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#setApplied(no.ugland.utransprod.gui.model.Applyable,
     *      boolean, no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected void setApplied(PackableListItem packable, boolean applied, WindowInterface window) {
	applyListInterface.setApplied(packable, applied, window);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getCheckBoxText()
     */
    @Override
    protected String getCheckBoxText() {
	return "Vis pakket";
    }

    /**
     * @param packable
     * @return true dersom enabled
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getButtonApplyEnabled(no.ugland.utransprod.gui.model.Applyable)
     */
    @Override
    protected boolean getButtonApplyEnabled(PackableListItem packable) {
	if (packable != null && packable.getColli() == null) {
	    return true;
	}
	return false;
    }

    /**
     * @param packable
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#checkLazyLoad(no.ugland.utransprod.gui.model.Applyable)
     */
    @Override
    protected void checkLazyLoad(PackableListItem packable) {
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected TableModel getTableModel(WindowInterface window) {
	return new PackageTableModel(getObjectSelectionList());
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyColumn()
     */
    @Override
    protected Integer getApplyColumn() {
	return 5;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#searchOrder(java.lang.String,
     *      java.lang.String, no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    protected void searchOrder(String orderNr, String customerNr, WindowInterface window) {
	try {
	    List<PackableListItem> packables = applyListInterface.doSearch(orderNr, customerNr,
		    (ProductAreaGroup) productAreaGroupModel.getValue((ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP)));
	    objectSelectionList.clearSelection();
	    PackableListItem packable = getSearchObject(window, packables);

	    if (packable != null) {
		int selectedIndex = objectList.indexOf(packable);
		// table.getSelectionModel().setSelectionInterval(table.convertRowIndexToView(selectedIndex),
		// table.convertRowIndexToView(selectedIndex));

		// table.scrollRowToVisible(table.getSelectedRow());

		// objectSelectionList.setSelection(packable);
		table.getSelectionModel().setAnchorSelectionIndex(table.convertRowIndexToView(selectedIndex));
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
	return applyListInterface.getReportEnum();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#hasWriteAccess()
     */
    @Override
    protected boolean hasWriteAccess() {
	return applyListInterface.hasWriteAccess();
    }

    /**
     * Tabellmodell for pakking
     * 
     * @author atle.brekka
     */
    final class PackageTableModel extends AbstractTableAdapter {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param listModel
	 */
	public PackageTableModel(ListModel listModel) {
	    super(listModel, PackageColumn.getColumnNames());
	}

	/**
	 * Henter verdi
	 * 
	 * @param rowIndex
	 * @param columnIndex
	 * @return verdi
	 */
	public Object getValueAt(int rowIndex, int columnIndex) {
	    PackableListItem packable = (PackableListItem) getRow(rowIndex);
	    String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_").replaceAll("\\.", "_");
	    return PackageColumn.valueOf(columnName).getValue(packable);
	}

	/**
	 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
	    String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_").replaceAll("\\.", "_");
	    return PackageColumn.valueOf(columnName).getColumnClass();
	}

    }

    public enum PackageColumn {
	TRANSPORT("Transport") {
	    @Override
	    public Object getValue(PackableListItem packable) {
		return packable.getTransportDetails();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }
	},
	ORDRE("Ordre") {
	    @Override
	    public Object getValue(PackableListItem packable) {
		return packable;
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return Packable.class;
	    }
	},
	ANTALL("Antall") {
	    @Override
	    public Class<?> getColumnClass() {
		return Integer.class;
	    }

	    @Override
	    public Object getValue(PackableListItem packable) {
		return packable.getNumberOfItems();
	    }
	},
	SPESIFIKASJON("Spesifikasjon") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PackableListItem packable) {
		if (packable.getOrdln() != null) {
		    return packable.getOrdln().getDescription();
		}
		return Util.removeNoAttributes(packable.getAttributeInfo());
	    }
	},
	OPPLASTING("Opplasting") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PackableListItem packable) {
		if (packable.getTransportDetails() != null) {
		    Date loadingDate = packable.getLoadingDate();
		    if (loadingDate != null) {
			return Util.SHORT_DATE_FORMAT.format(loadingDate);
		    }
		    return null;
		}
		return null;
	    }
	},
	PAKKET("Pakket") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PackableListItem packable) {
		if (packable.getColli() != null) {
		    return packable.getColli().toString();
		}
		return "---";
	    }
	},
	PRODUKTOMRÅDE("Produktområde") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PackableListItem packable) {
		if (packable.getProductAreaGroupName() != null) {
		    return packable.getProductAreaGroupName();
		}
		return "";
	    }
	},
	STARTET("Startet") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PackableListItem packable) {
		if (packable.getActionStarted() != null) {
		    return Util.SHORT_DATE_FORMAT.format(packable.getActionStarted());
		}
		return "---";
	    }
	},
	ARTIKKEL("Artikkel") {
	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }

	    @Override
	    public Object getValue(PackableListItem packable) {
		return packable.getArticleName();
	    }
	},
	PROD_UKE("Prod.uke") {
	    @Override
	    public Object getValue(PackableListItem packable) {
		return packable.getProductionWeek();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return Integer.class;
	    }
	},
	PAKKLISTE_KLAR("Pakkliste klar") {
	    @Override
	    public Object getValue(PackableListItem packable) {
		return packable.getPacklistReady();
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return Date.class;
	    }
	},
	MONT("Mont") {
	    @Override
	    public Object getValue(PackableListItem packable) {
		if (packable.getDoAssembly() != null && packable.getDoAssembly() == 1) {
		    return packable.getAssemblyWeek() == null ? "M" : "M" + packable.getAssemblyWeek();
		}
		return "";
	    }

	    @Override
	    public Class<?> getColumnClass() {
		return String.class;
	    }
	};
	private String columnName;

	private PackageColumn(String aColumnName) {
	    columnName = aColumnName;
	}

	public String getColumnName() {
	    return columnName;
	}

	public static String[] getColumnNames() {
	    PackageColumn[] packageColumns = PackageColumn.values();

	    List<String> columnNameList = new ArrayList<String>();
	    for (int i = 0; i < packageColumns.length; i++) {
		columnNameList.add(packageColumns[i].getColumnName());
	    }
	    String[] columnNames = new String[columnNameList.size()];
	    return columnNameList.toArray(columnNames);
	}

	public abstract Object getValue(PackableListItem packable);

	public abstract Class<?> getColumnClass();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getSpecificationCell()
     */
    @Override
    protected int getSpecificationCell() {
	return 3;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getSpecificationCellRenderer()
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
	return applyListInterface.getTableModelReport(objectSelectionList, table, getObjectSelectionList());
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#setInvisibleCells()
     */
    @Override
    protected void setInvisibleCells() {
	applyListInterface.setInvisibleColumns(table);

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyObject(no.ugland.utransprod.gui.model.Transportable)
     */
    @Override
    public PackableListItem getApplyObject(final Transportable transportable, final WindowInterface window) {
	applyable = applyable != null ? applyable : applyListInterface.getApplyObject(transportable, window);
	return applyable;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getProductAreaColumn()
     */
    @Override
    protected int getProductAreaColumn() {
	return 6;
    }

    @Override
    protected String getNotStartText() {
	return "Ikke startet pakking";
    }

    @Override
    protected String getStartText() {
	return "Startet pakking";
    }

    @Override
    protected void setStarted(PackableListItem object, boolean started) {
	applyListInterface.setStarted(object, started);

    }

    @Override
    protected boolean getButtonStartEnabled(PackableListItem packable) {
	if (packable != null && packable.getActionStarted() == null && (packable.getProbability() == null || packable.getProbability() != 90)) {
	    return true;
	}
	return false;
    }

    @Override
    protected Integer getStartColumn() {
	return 7;
    }

    @Override
    public Dimension getWindowSize() {
	return new Dimension(1000, 500);
    }

    @Override
    public String getTableWidth() {
	return "350dlu";
    }

    @Override
    public void clearApplyObject() {
	applyable = null;

    }

    @Override
    protected void setRealProductionHours(PackableListItem object, BigDecimal overstyrtTidsforbruk) {
	// TODO Auto-generated method stub

    }

}
