package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.Produceable;

/**
 * Interface for serviceklasse mot view GAVL_PRODUCTION_V
 * @author atle.brekka
 */
public interface GavlProductionVManager extends IApplyListManager<Produceable> {

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findAllApplyable()
     */
    List<Produceable> findAllApplyable();

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByOrderNr(java.lang.String)
     */
    List<Produceable> findApplyableByOrderNr(String orderNr);

    /**
     * @param productionV
     * @see no.ugland.utransprod.service.IApplyListManager#refresh(java.lang.Object)
     */
    void refresh(Produceable productionV);

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    List<Produceable> findApplyableByCustomerNr(Integer customerNr);

}
