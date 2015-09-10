package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.TaksteinSkarpnesVDAO;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.TaksteinSkarpnesVManager;

/**
 * Imlplementasjon av serviceklasse for view TAKSTEIN_SKARPNES_V.
 * 
 * @author atle.brekka
 */
public class TaksteinSkarpnesVManagerImpl extends AbstractApplyListManager<Produceable> implements TaksteinSkarpnesVManager {
    private TaksteinSkarpnesVDAO dao;

    public final void setTaksteinSkarpnesVDAO(final TaksteinSkarpnesVDAO aDao) {
	this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findAllApplyable()
     */
    public final List<Produceable> findAllApplyable() {
	return dao.findAll();
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    public final List<Produceable> findApplyableByCustomerNr(final Integer customerNr) {
	return dao.findByCustomerNr(customerNr);
    }

    /**
     * @see no.ugland.utransprod.service.IApplyListManager#findApplyableByOrderNr(java.lang.String)
     */
    public final List<Produceable> findApplyableByOrderNr(final String orderNr) {
	return dao.findByOrderNr(orderNr);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.IApplyListManager#refresh(java.lang.Object)
     */
    public final void refresh(final Produceable object) {
	dao.refresh(object);

    }

    public List<Produceable> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
	return dao.findByCustomerNrAndProductAreaGroup(customerNr, productAreaGroup);
    }

    public List<Produceable> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
	return dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
    }

}
