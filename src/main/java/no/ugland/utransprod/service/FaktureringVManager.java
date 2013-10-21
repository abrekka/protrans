package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.util.excel.ExcelManager;

/**
 * Interface for derviceklasse mot view FAKTURERING_V
 * @author atle.brekka
 */
public interface FaktureringVManager extends ExcelManager,
        IApplyListManager<FaktureringV> {
    String MANAGER_NAME = "faktureringVManager";

	/**
     * @see no.ugland.utransprod.service.IApplyListManager#findAllApplyable()
     */
    List<FaktureringV> findAllApplyable();

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByOrderNr(java.lang.String)
     */
    List<FaktureringV> findApplyableByOrderNr(String orderNr);

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    List<FaktureringV> findApplyableByCustomerNr(Integer customerNr);

    /**
     * @param faktureringV
     * @see no.ugland.utransprod.service.IApplyListManager#refresh(java.lang.Object)
     */
    void refresh(FaktureringV faktureringV);

	FaktureringV findByOrderNr(String orderNr);
}
