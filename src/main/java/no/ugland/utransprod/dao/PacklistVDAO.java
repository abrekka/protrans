package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.model.ProductAreaGroup;

/**
 * Interface for DAO mot view PACKLIST_V
 * 
 * @author atle.brekka
 * 
 */
public interface PacklistVDAO extends DAO<PacklistV> {
    String DAO_NAME = "packlistVDAO";

    /**
     * Finner alle
     * 
     * @return vegger
     */
    List<PacklistV> findAll();

    /**
     * Finner basert på ordrenummer
     * 
     * @param orderNr
     * @return ordre
     */
    List<PacklistV> findByOrderNr(String orderNr);

    /**
     * Oppdater objekt
     * 
     * @param productionV
     */
    void refresh(PacklistV productionV);

    /**
     * Finner basert på kundenummer
     * 
     * @param customerNr
     * @return ordre
     */
    List<PacklistV> findByCustomerNr(Integer customerNr);

    List<PacklistV> findByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup);

    List<PacklistV> findByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup);
}
