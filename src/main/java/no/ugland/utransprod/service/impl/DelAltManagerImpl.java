package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.DelAltDAO;
import no.ugland.utransprod.model.DelAlt;
import no.ugland.utransprod.service.DelAltManager;

public class DelAltManagerImpl implements DelAltManager {
    private DelAltDAO dao;

    /**
     * @param aDao
     */
    public final void setDelAltDAO(final DelAltDAO aDao) {
	this.dao = aDao;
    }

    public List<DelAlt> finnForProdno(String prodno) {
	return dao.finnForProdno(prodno);
    }

}
