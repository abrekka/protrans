package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.SaleDAO;
import no.ugland.utransprod.service.SaleManager;

public class SaleManagerImpl implements SaleManager {
    private SaleDAO dao;

    /**
     * @param aDao
     */
    public final void setSaleDAO(final SaleDAO aDao) {
        this.dao = aDao;
    }

  

}
