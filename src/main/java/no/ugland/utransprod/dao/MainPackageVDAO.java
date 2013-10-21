package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.MainPackageV;

/**
 * Interface for DAO mot view MAIN_PACKAGE_V
 * 
 * @author atle.brekka
 * 
 */
public interface MainPackageVDAO extends DAO<MainPackageV> {
	/**
	 * Oppdater objekt
	 * 
	 * @param mainPackageV
	 */
	void refresh(MainPackageV mainPackageV);

	/**
	 * Finn pakking basert på ordrenummer
	 * 
	 * @param orderNr
	 * @return pakking
	 */
	MainPackageV findByOrderNr(String orderNr);
	/**
	 * Finn basert på kundenummer
	 * @param customerNr
	 * @return pakking
	 */
	MainPackageV findByCustomerNr(Integer customerNr);
}
