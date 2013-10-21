package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TakstolPackageV;

/**
 * Interface for DAO mot view TAKSTOL_PACKAGEW_V
 * @author atle.brekka
 *
 */
public interface TakstolPackageVDAO extends DAO<TakstolPackageV> {
	/**
	 * Finner alle takstoler som skal pakkes
	 * 
	 * @return vegger
	 */
	List<PackableListItem> findAll();

	/**
	 * Finner takstoler som skal pakkes basert op ordrenummer
	 * 
	 * @param orderNr
	 * @return vegg
	 */
	List<PackableListItem> findByOrderNr(String orderNr);

	/**
	 * Oppdater objekt
	 * @param takstolPackageV 
	 */
	void refresh(TakstolPackageV takstolPackageV);

	/**
	 * Finn basert på kundenummer
	 * @param customerNr
	 * @return takstol
	 */
	List<PackableListItem> findByCustomerNr(Integer customerNr);

    List<PackableListItem> findApplyableByOrderNrAndArticleName(String orderNr, String mainArticleName);

	List<PackableListItem> findByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup);

	List<PackableListItem> findByCustomerNrAndProductAreaGroup(
			Integer customerNr, ProductAreaGroup productAreaGroup);
}
