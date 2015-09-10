package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.PaidV;
import no.ugland.utransprod.model.ProductAreaGroup;

/**
 * Interface for DAO mot view PAID_V
 * 
 * @author atle.brekka
 * 
 */
public interface PaidVDAO extends DAO<PaidV> {
    /**
     * Finner alle til betaling
     * 
     * @return ordre
     */
    List<PaidV> findAll();

    /**
     * Finner basert på ordre nummer
     * 
     * @param orderNr
     * @return ordre
     */
    List<PaidV> findByOrderNr(String orderNr);

    /**
     * Finner basert på kundenummer
     * 
     * @param customerNr
     * @return ordre
     */
    List<PaidV> findByCustomerNr(Integer customerNr);

    /**
     * Oppdaterer
     * 
     * @param paidV
     */
    void refresh(PaidV paidV);

    List<PaidV> findByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup);

    List<PaidV> findByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup);
}
