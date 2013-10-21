package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.PreventiveAction;

/**
 * Interface for DAO mot tabell PREVENTIVE_ACTION
 * 
 * @author atle.brekka
 * 
 */
public interface PreventiveActionDAO extends DAO<PreventiveAction> {
	/**
	 * Finner alle prevantive tiltak
	 * 
	 * @return prevantive tiltak
	 */
	List<PreventiveAction> findAll();

	/**
	 * Oppdaterer objekt
	 * 
	 * @param preventiveAction
	 */
	void refreshPreventiveAction(PreventiveAction preventiveAction);

	/**
	 * Lazy laster prevantivt tiltak
	 * 
	 * @param preventiveAction
	 * @param enums
	 */
	//void lazyLoad(PreventiveAction preventiveAction,LazyLoadPreventiveActionEnum[] enums);

	/**
	 * Finner alle �pne tiltak med gjeldende funksjon og kategori
	 * 
	 * @param jobFunction
	 * @param functionCategory
	 * @return �pne tiltak
	 */
	List<PreventiveAction> findAllOpenByFunctionAndCategory(
			JobFunction jobFunction, FunctionCategory functionCategory);

	/**
	 * Finner alle �pne
	 * 
	 * @return prevantive tiltak
	 */
	List<PreventiveAction> findAllOpen();

	/**
	 * Finner alle lukkede for gjeldende m�ned
	 * 
	 * @param month
	 * @return prevantive tiltak
	 */
	List<PreventiveAction> findAllClosedInMonth(Integer month);
}
