package no.ugland.utransprod.gui.model;

import java.util.Collection;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.service.IApplyListManager;
import no.ugland.utransprod.service.VismaFileCreator;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.list.SelectionInList;

/**
 * Abstrakt implementasjon av ApplyListInterface
 * 
 * @author atle.brekka
 * @param <T>
 */
public abstract class AbstractApplyList<T extends Applyable> implements ApplyListInterface<T> {

    protected Login login;

    protected IApplyListManager<T> applyListManager;

    protected VismaFileCreator vismaFileCreator;

    /**
     * @param aUserType
     * @param manager
     */
    public AbstractApplyList(Login aLogin, IApplyListManager<T> manager, VismaFileCreator aVismaFileCreator) {
	login = aLogin;
	applyListManager = manager;
	vismaFileCreator = aVismaFileCreator;

    }

    /**
     * @see no.ugland.utransprod.gui.model.ApplyListInterface#getObjectLines()
     */
    public Collection<T> getObjectLines() {
	List<T> lines = applyListManager.findAllApplyable();

	// if (lines != null) {
	// for (T obj : lines) {
	// obj.setOrdln(applyListManager.findOrdlnByOrderLine(obj.getOrderLineId()));
	// }
	// }
	sortObjectLines(lines);
	return lines;
    }

    /**
     * @see no.ugland.utransprod.gui.model.ApplyListInterface#doSearch(java.lang.String,
     *      java.lang.String)
     */
    public List<T> doSearch(String orderNr, String customerNr, ProductAreaGroup productAreaGroup) throws ProTransException {
	if (orderNr != null) {
	    return applyListManager.findApplyableByOrderNrAndProductAreaGroup(orderNr, null);
	} else if (customerNr != null) {
	    try {
		return applyListManager.findApplyableByCustomerNrAndProductAreaGroup(Integer.valueOf(customerNr), null);
	    } catch (NumberFormatException e) {
		e.printStackTrace();
		throw new ProTransException("Kundenr må være tall");
	    }
	}
	return null;
    }

    /**
     * Tom implementasjon
     * 
     * @see no.ugland.utransprod.gui.model.ApplyListInterface#getReportEnum()
     */
    public ReportEnum getReportEnum() {
	return null;
    }

    /**
     * Tom implementasjon
     * 
     * @see no.ugland.utransprod.gui.model.ApplyListInterface#getTableModelReport(javax.swing.ListModel,
     *      org.jdesktop.swingx.JXTable,
     *      com.jgoodies.binding.list.SelectionInList)
     */
    public TableModel getTableModelReport(ListModel listModel, JXTable table, SelectionInList objectSelectionList) {
	return null;
    }

    /**
     * Tom implementasjon
     * 
     * @see no.ugland.utransprod.gui.model.ApplyListInterface#setInvisibleColumns(org.jdesktop.swingx.JXTable)
     */
    public void setInvisibleColumns(JXTable table) {

    }

    /**
     * Sorterer objekter som skal vises Dette er en tom implementasjon som må
     * implementeres av de klassene som trenger dette
     * 
     * @param lines
     */
    protected void sortObjectLines(List<T> lines) {
    }

    /**
     * @param object
     * @see no.ugland.utransprod.gui.model.ApplyListInterface#refresh(no.ugland.utransprod.gui.model.Applyable)
     */
    public void refresh(T object) {
	applyListManager.refresh(object);
    }

    public void setProductionUnit(ProductionUnit productionUnit, T object, WindowInterface window) {
    }

    protected String createVismaFile(List<OrderLine> orderLines) throws ProTransException {
	return vismaFileCreator != null ? vismaFileCreator.createVismaFile(orderLines, 1) : null;

    }

    protected boolean ignoreVismaFile(OrderLine orderLine, WindowInterface window, boolean isMainArticle) {
	return !isMainArticle ? true : vismaFileCreator != null ? vismaFileCreator.ignoreVismaFile(orderLine, window) : true;
    }

}
