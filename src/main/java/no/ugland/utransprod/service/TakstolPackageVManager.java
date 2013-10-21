package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.TakstolPackageV;

/**
 * Interface for serviceklasse mot view TAKSTOL_PACKAGE_V
 * @author atle.brekka
 */
public interface TakstolPackageVManager extends
        IApplyListManager<PackableListItem>,TakstolVManager {
    String MANAGER_NAME = "takstolPackageVManager";

    /**
     * Oppdaterer objekt fra view
     * @param packageV
     */
    void refresh(TakstolPackageV packageV);

    //TakstolPackageV findByOrderNrAndOrderLineId(String orderNr,Integer orderLineId);

    List<PackableListItem> findApplyableByOrderNrAndArticleName(String orderNr, String mainArticleName);
}
