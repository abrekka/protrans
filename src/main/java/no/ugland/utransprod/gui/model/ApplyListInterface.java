package no.ugland.utransprod.gui.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.ProductionUnit;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.list.SelectionInList;

/**
 * Interface for klasser som skal vise lister av ting som skal settes
 * 
 * @author atle.brekka
 * 
 * @param <T>
 */
public interface ApplyListInterface<T extends Applyable> {

    /**
     * Henter objekter som er satt eller skal settes
     * 
     * @return objekter
     */
    Collection<T> getObjectLines();

    /**
     * Søker etter objekt med ordrenummer eller kundenummer
     * 
     * @param orderNr
     * @param customerNr
     * @return objekter
     * @throws ProTransException
     */
    // List<T> doSearch(String orderNr, String customerNr)throws
    // ProTransException;

    /**
     * Setter tilordnet
     * 
     * @param object
     * @param applied
     * @param window
     */
    void setApplied(T object, boolean applied, WindowInterface window);

    // void setApplied(T object, boolean applied, WindowInterface window,List<T>
    // relatedArticles);

    /**
     * Sjekker om bruker har skriveakksess
     * 
     * @return true dersom skriveakksess
     */
    boolean hasWriteAccess();

    /**
     * Henter rapportdefinisjon
     * 
     * @return rapportdefinisjon
     */
    ReportEnum getReportEnum();

    /**
     * Henter tabelmodell for rapport
     * 
     * @param listModel
     * @param table
     * @param objectSelectionList
     * @return tabellmodell
     */
    TableModel getTableModelReport(ListModel listModel, JXTable table, SelectionInList objectSelectionList);

    /**
     * Setter kolonner som ikke skal vises
     * 
     * @param table
     */
    void setInvisibleColumns(JXTable table);

    /**
     * Oppdaterer objekt
     * 
     * @param object
     */
    void refresh(T object);

    /**
     * Henter objekt som skal settes
     * 
     * @param transportable
     * @return objekt som skal settes
     */
    T getApplyObject(Transportable transportable, WindowInterface window);

    void setStarted(T object, boolean started);

    void setRealProductionHours(T object, BigDecimal overstyrtTidsforbruk);

    void setProductionUnit(ProductionUnit productionUnit, T object, WindowInterface window);

    List<T> doSearch(String orderNr, String customerNr, ProductAreaGroup productAreaGroup) throws ProTransException;

}
