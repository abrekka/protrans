package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.GulvsponPackageVDAO;
import no.ugland.utransprod.model.GulvsponPackageV;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.GulvsponPackageVManager;

/**
 * Implementasjon av serviceklasse for view GULVSPON_PACKAGE_V.
 * 
 * @author atle.brekka
 */
public class GulvsponPackageVManagerImpl extends AbstractApplyListManager<PackableListItem> implements GulvsponPackageVManager {
    private GulvsponPackageVDAO dao;

    /**
     * @param aDao
     */
    public final void setGulvsponPackageVDAO(final GulvsponPackageVDAO aDao) {
	this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.GulvsponPackageVManager#findAllApplyable()
     */
    public final List<PackableListItem> findAllApplyable() {
	return dao.findAll();
    }

    /**
     * @see no.ugland.utransprod.service.GulvsponPackageVManager#findApplyableByOrderNr(java.lang.String)
     */
    public final List<PackableListItem> findApplyableByOrderNr(final String orderNr) {
	return dao.findByOrderNr(orderNr);
    }

    /**
     * @see no.ugland.utransprod.service.GulvsponPackageVManager#
     *      refresh(no.ugland.utransprod.model.GulvsponPackageV)
     */
    public final void refresh(final GulvsponPackageV gulvsponPackageV) {
	dao.refresh(gulvsponPackageV);

    }

    /**
     * @see no.ugland.utransprod.service.GulvsponPackageVManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    public final List<PackableListItem> findApplyableByCustomerNr(final Integer customerNr) {
	return dao.findByCustomerNr(customerNr);
    }

    /**
     * @param object
     * @see no.ugland.utransprod.service.IApplyListManager#refresh(java.lang.Object)
     */
    public final void refresh(final PackableListItem object) {
	refresh((GulvsponPackageV) object);

    }

    public List<PackableListItem> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup) {
	return dao.findByCustomerNrProductAreaGroup(customerNr, productAreaGroup);
    }

    public List<PackableListItem> findApplyableByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup) {
	return dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
    }

}
