package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JRadioButton;
import javax.swing.ListModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.PaidApplyList;
import no.ugland.utransprod.gui.model.ProductAreaGroupModel;
import no.ugland.utransprod.gui.model.TextPaneRendererOrder;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.PaidV;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.PaidVManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.decorator.Filter;
import org.jdesktop.swingx.decorator.FilterPipeline;
import org.jdesktop.swingx.decorator.PatternFilter;

import com.google.inject.Inject;
import com.jgoodies.binding.adapter.AbstractTableAdapter;

/**
 * Håndterer forhåndsbetaling
 * 
 * @author atle.brekka
 */
public class PaidViewHandler extends AbstractProductionPackageViewHandlerShort<PaidV> {

    private JRadioButton radioButtonAll;

    private JRadioButton radioButtonAssembly;

    private JRadioButton radioButtonNotAssembly;

    private ButtonGroup buttonGroup = new ButtonGroup();

    FilterListener filterListener;

    /**
     * @param productionInterface
     * @param deviationViewHandlerFactory
     * @param userType
     * @param applicationUser
     */
    @Inject
    public PaidViewHandler(final PaidApplyList productionInterface, final Login login, final ManagerRepository managerRepository,
	    DeviationViewHandlerFactory deviationViewHandlerFactory) {
	super(login, managerRepository, deviationViewHandlerFactory, productionInterface, "Forhåndsbetaling", TableEnum.TABLEPAID);

	filterListener = new FilterListener();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyText()
     */
    @Override
    protected final String getApplyText() {
	return "Sett betalt";
    }

    /**
     * @param paidV
     * @return true dersom enabled
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#
     *      getButtonApplyEnabled(no.ugland.utransprod.gui.model.Applyable)
     */
    @Override
    protected final boolean getButtonApplyEnabled(final PaidV paidV) {
	if (paidV.getPaidDate() == null && (paidV.getCustTrs() == null || paidV.getCustTrs().size() == 0)) {
	    return true;
	}
	return false;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getCheckBoxText()
     */
    @Override
    protected final String getCheckBoxText() {
	return "Vis betalt";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getUnapplyText()
     */
    @Override
    protected final String getUnapplyText() {
	return "Sett ikke betalt";
    }

    @Override
    protected final void setApplied(final PaidV paidV, final boolean applied, final WindowInterface window) {
	PaidVManager paidVManager = (PaidVManager) ModelUtil.getBean("paidVManager");
	OrderManager orderManager = (OrderManager) ModelUtil.getBean("orderManager");
	Order order = orderManager.findByOrderNr(paidV.getOrderNr());
	if (applied) {
	    Date paidDate = Util.getDate(window);
	    order.setPaidDate(paidDate);
	} else {
	    order.setPaidDate(null);
	}
	try {
	    orderManager.saveOrder(order);
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	    e.printStackTrace();
	}
	paidVManager.refresh(paidV);
    }

    @Override
    protected final TableModel getTableModel(final WindowInterface window) {
	return new PaidTableModel(getObjectSelectionList());
    }

    @Override
    void initColumnWidthExt() {
	table.getColumnExt(0).setPreferredWidth(200);
	table.getColumnExt(4).setPreferredWidth(80);
	// restbeløp
	table.getColumnExt(5).setPreferredWidth(80);
	// forfalsdato
	table.getColumnExt(6).setPreferredWidth(80);
	// visma
	table.getColumnExt(7).setPreferredWidth(50);
    }

    /**
     * Tabellmodell for tabell med order til fakturering
     * 
     * @author atle.brekka
     */
    private final class PaidTableModel extends AbstractTableAdapter {

	private static final long serialVersionUID = 1L;

	/**
	 * @param listModel
	 */
	public PaidTableModel(final ListModel listModel) {
	    super(listModel, new String[] { "Ordre", "Beløp", "Betalt", "Montering", "Produktområde", "Fakturabeløp", "Restbeløp", "Forfallsdato",
		    "Visma" });
	}

	/**
	 * @see javax.swing.table.TableModel#getValueAt(int, int)
	 */
	public Object getValueAt(final int rowIndex, final int columnIndex) {
	    PaidV paidV = (PaidV) getRow(rowIndex);

	    switch (columnIndex) {
	    case 0:
		return paidV;
	    case 1:
		return paidV.getCustomerCost();
	    case 2:
		if (paidV.getPaidDate() != null) {
		    return Util.SHORT_DATE_FORMAT.format(paidV.getPaidDate());
		}
		return "---";
	    case 3:
		if (Util.convertNumberToBoolean(paidV.getDoAssembly())) {
		    return "Ja";
		}
		return "Nei";
	    case 4:
		if (paidV.getProductAreaGroupName() != null) {
		    return paidV.getProductAreaGroupName();
		}
		return "";
	    case 5:
		return paidV.getInvoiceAmount();
		/*
		 * if (paidV.getCustTr() != null) { return
		 * String.format("%1$.0f", paidV.getCustTr()
		 * .getInvoiceAmount().setScale(0, RoundingMode.HALF_EVEN)); }
		 * return null;
		 */
	    case 6:
		return paidV.getRestAmount();
		/*
		 * if (paidV.getCustTr() != null) { return
		 * String.format("%1$.0f", paidV.getCustTr() .getRestAmount()
		 * .setScale(0, RoundingMode.HALF_EVEN)); } return null;
		 */
	    case 7:
		return paidV.getDueDate();
		/*
		 * if (paidV.getCustTr() != null &&
		 * paidV.getCustTr().getDueDate() != null) { return
		 * Util.SHORT_DATE_FORMAT.format(paidV.getCustTr()
		 * .getDueDate()); } return null;
		 */
	    case 8:
		if (paidV.getCustTrs() != null && paidV.getCustTrs().size() > 0) {
		    return Boolean.TRUE;
		}
		return Boolean.FALSE;

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
		return PaidV.class;
	    case 2:
	    case 3:
	    case 4:
	    case 7:
		return String.class;
	    case 1:
	    case 5:
	    case 6:
		return BigDecimal.class;
	    case 8:
		return Boolean.class;
	    default:
		throw new IllegalStateException("Unknown column");
	    }
	}

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyColumn()
     */
    @Override
    protected final Integer getApplyColumn() {
	return 2;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getWindowSize()
     */
    @Override
    public final Dimension getWindowSize() {
	return new Dimension(900, 600);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getTableWidth()
     */
    @Override
    public final String getTableWidth() {
	return "200dlu";
    }

    @Override
    protected final void searchOrder(final String orderNr, final String customerNr, final WindowInterface window) {
	try {
	    List<PaidV> list = applyListInterface.doSearch(orderNr, customerNr,
		    (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));
	    PaidV paidV = getSearchObject(window, list);
	    if (paidV != null) {
		objectSelectionList.setSelection(paidV);
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
    protected final TableCellRenderer getOrderCellRenderer() {
	return new TextPaneRendererOrder();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getOrderInfoCell()
     */
    @Override
    protected final int getOrderInfoCell() {
	return 0;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#hasWriteAccess()
     */
    @Override
    protected final boolean hasWriteAccess() {
	return UserUtil.hasWriteAccess(login.getUserType(), "Betaling");
    }

    /**
     * Lager radioknapp for å se alle
     * 
     * @return radioknapp
     */
    public final JRadioButton getRadioButtonAll() {
	radioButtonAll = new JRadioButton("Vis alle");
	radioButtonAll.setName("RadioButtonAll");
	radioButtonAll.addActionListener(filterListener);
	radioButtonAll.setSelected(true);
	buttonGroup.add(radioButtonAll);
	return radioButtonAll;
    }

    /**
     * Lager radioknapp for å se de som skal ha montering
     * 
     * @return radioknapp
     */
    public final JRadioButton getRadioButtonAssembly() {
	radioButtonAssembly = new JRadioButton("Vis montering");
	radioButtonAssembly.setName("RadioButtonAssembly");
	radioButtonAssembly.addActionListener(filterListener);
	buttonGroup.add(radioButtonAssembly);
	return radioButtonAssembly;
    }

    /**
     * Lager radioknapp for å se byggesett
     * 
     * @return radioknapp
     */
    public final JRadioButton getRadioButtonNotAssembly() {
	radioButtonNotAssembly = new JRadioButton("Vis byggesett");
	radioButtonNotAssembly.setName("RadioButtonNotAssembly");
	radioButtonNotAssembly.addActionListener(filterListener);
	buttonGroup.add(radioButtonNotAssembly);
	return radioButtonNotAssembly;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#handleFilter()
     */
    @Override
    final void handleFilterExt() {
	table.clearSelection();
	objectSelectionList.clearSelection();

	List<Filter> filterList = new ArrayList<Filter>();

	ProductAreaGroup group = (ProductAreaGroup) productAreaGroupModel.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP);

	PrefsUtil.setInvisibleColumns(group.getProductAreaGroupName(), table.getName(), table);
	if (group.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
	    group = null;
	}

	if (group != null) {
	    PatternFilter filterProductAreaGroup = new PatternFilter(group.getProductAreaGroupName(), Pattern.CASE_INSENSITIVE,
		    getProductAreaColumn());
	    filterList.add(filterProductAreaGroup);
	}
	if (radioButtonAssembly != null && radioButtonAssembly.isSelected()) {// montering
	    Filter filterAssembly = new PatternFilter("Ja", Pattern.CASE_INSENSITIVE, 3);// montering
	    filterList.add(filterAssembly);

	} else if (radioButtonNotAssembly != null && radioButtonNotAssembly.isSelected()) {// byggesett
	    Filter filterNotAssembly = new PatternFilter("Nei", Pattern.CASE_INSENSITIVE, 3);// ikke
											     // montering
	    filterList.add(filterNotAssembly);
	}

	if (!checkBoxFilter.isSelected()) {// vis alle
	    Filter filterApplied = new PatternFilter("---", Pattern.CASE_INSENSITIVE, getApplyColumn());// ikke
													// betalt
	    filterList.add(filterApplied);

	}
	if (filterList.size() != 0) {
	    Filter[] filters = new Filter[filterList.size()];
	    FilterPipeline filterPipeline = new FilterPipeline(filterList.toArray(filters));
	    table.setFilters(filterPipeline);
	} else {
	    table.setFilters(null);
	}

	table.repaint();
    }

    /**
     * Håndterer setting av filtrering
     * 
     * @author atle.brekka
     */
    class FilterListener implements ActionListener {

	/**
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
	    handleFilter();

	}

    }

    @Override
    public final PaidV getApplyObject(final Transportable transportable, WindowInterface window) {
	return applyListInterface.getApplyObject(transportable, window);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getProductAreaColumn()
     */
    @Override
    protected final int getProductAreaColumn() {
	return 4;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected final void addObjectInfo() {
	if (objectList != null) {
	    // CustTrManager custTrManager = (CustTrManager) ModelUtil
	    // .getBean("custTrManager");
	    Iterator<PaidV> paidVIt = objectList.iterator();
	    while (paidVIt.hasNext()) {
		PaidV paidV = paidVIt.next();
		paidV.setCustTrs(managerRepository.getCustTrManager().findByOrderNr(paidV.getOrderNr()));
	    }
	}

    }

    @Override
    protected final void buttonsEnabled(final PaidV paidV, boolean hasSelection) {
	if (paidV != null && paidV.getCustTrs() != null && paidV.getCustTrs().size() > 0) {
	    buttonUnapply.setEnabled(false);
	}
    }

    @Override
    public void clearApplyObject() {
	// TODO Auto-generated method stub

    }

    @Override
    protected void setRealProductionHours(PaidV object, BigDecimal overstyrtTidsforbruk) {
	// TODO Auto-generated method stub

    }

	@Override
	public boolean isProductionWindow() {
		return false;
	}

}
