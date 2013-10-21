package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.SaleDAO;
import no.ugland.utransprod.service.SaleManager;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.report.SaleReportSum;

public class SaleManagerImpl implements SaleManager {
    private SaleDAO dao;

    /**
     * @param aDao
     */
    public final void setSaleDAO(final SaleDAO aDao) {
        this.dao = aDao;
    }

  

}
