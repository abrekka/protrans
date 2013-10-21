package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.MainPackageV;

/**
 * Interface for serviceklasse mot view MAIN_PACKAGE_V
 * @author atle.brekka
 */
public interface MainPackageVManager {
    String MANAGER_NAME = "mainPackageVManager";

	/**
     * Finner alle
     * @return ordre til pakking
     */
    List<MainPackageV> findAll();

    /**
     * Oppdaterer view
     * @param mainPackageV
     */
    void refresh(MainPackageV mainPackageV);

    /**
     * Finner basert på ordrenummer
     * @param orderNr
     * @return ordre til pakking
     */
    MainPackageV findByOrderNr(String orderNr);

    /**
     * Finner basert på kundenummer
     * @param customerNr
     * @return ordre til pakking
     */
    MainPackageV findByCustomerNr(Integer customerNr);
}
