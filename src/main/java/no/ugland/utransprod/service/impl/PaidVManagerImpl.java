package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.PaidVDAO;
import no.ugland.utransprod.model.PacklistV;
import no.ugland.utransprod.model.PaidV;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.PaidVManager;

/**
 * Implementasjon av serviceklasse for view PAID_V.
 * @author atle.brekka
 */
public class PaidVManagerImpl extends AbstractApplyListManager<PaidV> implements PaidVManager {
    private PaidVDAO dao;

    /**
     * @param aDao
     */
    public final void setPaidVDAO(final PaidVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findAllApplyable()
     */
    public final List<PaidV> findAllApplyable() {
        return dao.findAll();
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    public final List<PaidV> findApplyableByCustomerNr(final Integer customerNr) {
        return dao.findByCustomerNr(customerNr);
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByOrderNr(java.lang.String)
     */
    public final List<PaidV> findApplyableByOrderNr(final String orderNr) {
        return dao.findByOrderNr(orderNr);
    }

    /**
     * @param paidV
     * @see no.ugland.utransprod.service.IApplyListManager#refresh(java.lang.Object)
     */
    public final void refresh(final PaidV paidV) {
        dao.refresh(paidV);

    }

	public List<PaidV> findApplyableByCustomerNrAndProductAreaGroup(
			Integer customerNr, ProductAreaGroup productAreaGroup) {
		return dao.findByCustomerNrAndProductAreaGroup(customerNr,productAreaGroup);
	}

	public List<PaidV> findApplyableByOrderNrAndProductAreaGroup(
			String orderNr, ProductAreaGroup productAreaGroup) {
		return dao.findByOrderNrAndProductAreaGroup(orderNr,productAreaGroup);
	}

}
