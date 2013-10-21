package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.UdsalesmallDAO;
import no.ugland.utransprod.model.Udsalesmall;
import no.ugland.utransprod.service.UdsalesmallManager;

public class UdsalesmallManagerImpl implements UdsalesmallManager{
	private UdsalesmallDAO dao;

    /**
     * @param aDao
     */
    public final void setUdsalesmallDAO(final UdsalesmallDAO aDao) {
        this.dao = aDao;
    }

	public Udsalesmall findByOrderNr(String orderNr) {
		return dao.findByOrderNr(orderNr);
	}
}
