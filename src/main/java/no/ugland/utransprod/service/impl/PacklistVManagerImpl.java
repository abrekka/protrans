package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.PacklistVDAO;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.PacklistVManager;

/**
 * Implementasjon av serviceklasse for view PACKLIST_V.
 * @author atle.brekka
 */
public class PacklistVManagerImpl extends AbstractApplyListManager<PacklistV> implements PacklistVManager {
    private PacklistVDAO dao;

    /**
     * @param aDao
     */
    public final void setPacklistVDAO(final PacklistVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findAllApplyable()
     */
    public final List<PacklistV> findAllApplyable() {
        return dao.findAll();
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByOrderNr(java.lang.String)
     */
    public final List<PacklistV> findApplyableByOrderNr(final String orderNr) {
        return dao.findByOrderNr(orderNr);
    }

    /**
     * @param productionV
     * @see no.ugland.utransprod.service.IApplyListManager#refresh(java.lang.Object)
     */
    public final void refresh(final PacklistV productionV) {
        dao.refresh(productionV);

    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    public final List<PacklistV> findApplyableByCustomerNr(final Integer customerNr) {
        return dao.findByCustomerNr(customerNr);
    }

	public List<PacklistV> findApplyableByCustomerNrAndProductAreaGroup(
			Integer customerNr, ProductAreaGroup productAreaGroup) {
		return dao.findByCustomerNrAndProductAreaGroup(customerNr,productAreaGroup);
	}

	public List<PacklistV> findApplyableByOrderNrAndProductAreaGroup(
			String orderNr, ProductAreaGroup productAreaGroup) {
		return dao.findByOrderNrAndProductAreaGroup(orderNr,productAreaGroup);
	}

}
