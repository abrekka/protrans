package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.CustTrDAO;
import no.ugland.utransprod.model.CustTr;
import no.ugland.utransprod.service.CustTrManager;

public class CustTrManagerImpl implements
        CustTrManager {
    private CustTrDAO dao;

    /**
     * @param aDao
     */
    public final void setCustTrDAO(final CustTrDAO aDao) {
        this.dao = aDao;
    }

    public List<CustTr> findByOrderNr(String orderNr) {
        return dao.findByOrderNr(orderNr);
    }

}
