package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.IgarasjenPackageVDAO;
import no.ugland.utransprod.dao.SutakPackageVDAO;
import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.SutakPackageV;
import no.ugland.utransprod.service.SutakPackageVManager;

public class SutakPackageVManagerImpl extends AbstractApplyListManager<PackableListItem>
		implements SutakPackageVManager {
	private SutakPackageVDAO dao;

	/**
	 * @param aDao
	 */
	public final void setSutakPackageVDAO(final SutakPackageVDAO aDao) {
		this.dao = aDao;
	}

	public final List<PackableListItem> findAllApplyable() {
		return dao.findAll();
	}

	public final List<PackableListItem> findApplyableByOrderNr(final String orderNr) {
		return dao.findByOrderNr(orderNr);
	}

	public final void refresh(final SutakPackageV sutakPackageV) {
		dao.refresh(sutakPackageV);

	}

	public final List<PackableListItem> findApplyableByCustomerNr(final Integer customerNr) {
		return dao.findByCustomerNr(customerNr);
	}

	public final void refresh(final PackableListItem object) {
		refresh((SutakPackageV) object);

	}

	public List<PackableListItem> findApplyableByCustomerNrAndProductAreaGroup(Integer customerNr,
			ProductAreaGroup productAreaGroup) {
		return dao.findByCustomerNrProductAreaGroup(customerNr, productAreaGroup);
	}

	public List<PackableListItem> findApplyableByOrderNrAndProductAreaGroup(String orderNr,
			ProductAreaGroup productAreaGroup) {
		return dao.findByOrderNrAndProductAreaGroup(orderNr, productAreaGroup);
	}

}
