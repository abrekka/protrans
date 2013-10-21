package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.FrontProductionVDAO;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.FrontProductionVManager;

/**
 * Implementasjon av serviceklasse for view FRONT_PRODUCTION_V.
 * @author atle.brekka
 */
public class FrontProductionVManagerImpl extends AbstractApplyListManager<Produceable> implements FrontProductionVManager {
    private FrontProductionVDAO dao;

    public final void setFrontProductionVDAO(final FrontProductionVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.FrontProductionVManager#findAllApplyable()
     */
    public final List<Produceable> findAllApplyable() {
        return dao.findAll();
    }

    /**
     * @see no.ugland.utransprod.service.FrontProductionVManager#findApplyableByOrderNr(java.lang.String)
     */
    public final List<Produceable> findApplyableByOrderNr(final String orderNr) {
        return dao.findByOrderNr(orderNr);
    }

    /**
     * @see no.ugland.utransprod.service.FrontProductionVManager#
     * refresh(no.ugland.utransprod.model.Produceable)
     */
    public final void refresh(final Produceable frontProductionV) {
        dao.refresh(frontProductionV);

    }

    /**
     * @see no.ugland.utransprod.service.FrontProductionVManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    public final List<Produceable> findApplyableByCustomerNr(final Integer customerNr) {
        return dao.findByCustomerNr(customerNr);
    }

	public List<Produceable> findApplyableByCustomerNrAndProductAreaGroup(
			Integer customerNr, ProductAreaGroup productAreaGroup) {
		return dao.findByCustomerNrProductAreaGroup(customerNr,productAreaGroup);
	}

	public List<Produceable> findApplyableByOrderNrAndProductAreaGroup(
			String orderNr, ProductAreaGroup productAreaGroup) {
		return dao.findByOrderNrAndProductAreaGroup(orderNr,productAreaGroup);
	}

}
