package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.GulvsponPackageV;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;

/**
 * Interface for DAO for GULVSPON_PACKAGE_V
 * @author atle.brekka
 */
public interface GulvsponPackageVDAO extends DAO<GulvsponPackageV> {
    /**
     * Finner alle vegger som skal produseres
     * @return vegger
     */
    List<PackableListItem> findAll();

    /**
     * Finner vegg som skal produseres basert op ordrenummer
     * @param orderNr
     * @return vegg
     */
    List<PackableListItem> findByOrderNr(String orderNr);

    /**
     * Oppdaterer objekt
     * @param gulvsponPackageV
     */
    void refresh(GulvsponPackageV gulvsponPackageV);

    /**
     * Finn basert på kundenummer
     * @param customerNr
     * @return gulvsponpakking
     */
    List<PackableListItem> findByCustomerNr(Integer customerNr);

	List<PackableListItem> findByCustomerNrProductAreaGroup(Integer customerNr,
			ProductAreaGroup productAreaGroup);

	List<PackableListItem> findByOrderNrAndProductAreaGroup(String orderNr,
			ProductAreaGroup productAreaGroup);

}
