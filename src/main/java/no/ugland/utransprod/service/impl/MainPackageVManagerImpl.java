package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.MainPackageVDAO;
import no.ugland.utransprod.model.MainPackageV;
import no.ugland.utransprod.service.MainPackageVManager;

/**
 * Implementasjon av serviceklasse for view MAIN_PACKAGE_V.
 * @author atle.brekka
 */
public class MainPackageVManagerImpl implements MainPackageVManager {
    private MainPackageVDAO dao;

    /**
     * @param aDao
     */
    public final void setMainPackageVDAO(final MainPackageVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.MainPackageVManager#findAll()
     */
    public final List<MainPackageV> findAll() {
        return dao.getObjects();
    }

    /**
     * @see no.ugland.utransprod.service.MainPackageVManager#refresh(no.ugland.utransprod.model.MainPackageV)
     */
    public final void refresh(final MainPackageV mainPackageV) {
        dao.refresh(mainPackageV);

    }

    /**
     * @see no.ugland.utransprod.service.MainPackageVManager#findByOrderNr(java.lang.String)
     */
    public final MainPackageV findByOrderNr(final String orderNr) {
        return dao.findByOrderNr(orderNr);
    }

    /**
     * @see no.ugland.utransprod.service.MainPackageVManager#findByCustomerNr(java.lang.Integer)
     */
    public final MainPackageV findByCustomerNr(final Integer customerNr) {
        return dao.findByCustomerNr(customerNr);
    }

}
