package no.ugland.utransprod.service;

import no.ugland.utransprod.model.CostUnit;

/**
 * Interface for manager for kostnadsenhet
 * @author atle.brekka
 */
public interface CostUnitManager extends OverviewManager<CostUnit> {
    String MANAGER_NAME = "costUnitManager";

	/**
     * Finner basert på navn
     * @param name
     * @return kostnadsenhet
     */
    CostUnit findByName(String name);

    /**
     * Lazy laster kostnadsenhet
     * @param costUnit
     * @param enums
     */
    //void lazyLoad(CostUnit costUnit, LazyLoadCostUnitEnum[] enums);

}
