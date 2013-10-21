package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.PreventiveAction;

/**
 * Interface for serviceklasse mot tabell PREVENTIVE_ACTION
 * @author atle.brekka
 */
public interface PreventiveActionManager extends
        OverviewManager<PreventiveAction> {
    /**
     * Lazy laster prevantiv handling
     * @param preventiveAction
     * @param enums
     */
    //void lazyLoad(PreventiveAction preventiveAction,LazyLoadPreventiveActionEnum[] enums);

    String MANAGER_NAME = "preventiveActionManager";

	/**
     * Finner alle basert på funksjon og kategori
     * @param jobFunction
     * @param functionCategory
     * @return prevantive handlinger
     */
    List<PreventiveAction> findAllOpenByFunctionAndCategory(
            JobFunction jobFunction, FunctionCategory functionCategory);
}
