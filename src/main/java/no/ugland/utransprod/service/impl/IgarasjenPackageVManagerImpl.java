package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.IgarasjenPackageVDAO;
import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.IgarasjenPackageVManager;

public class IgarasjenPackageVManagerImpl extends AbstractApplyListManager<PackableListItem>
		implements IgarasjenPackageVManager {
	private IgarasjenPackageVDAO dao;

	/**
	 * @param aDao
	 */
	public final void setIgarasjenPackageVDAO(final IgarasjenPackageVDAO aDao) {
		this.dao = aDao;
	}

	public final List<PackableListItem> findAllApplyable() {
		return dao.findAll();
	}

	public final List<PackableListItem> findApplyableByOrderNr(final String orderNr) {
		return dao.findByOrderNr(orderNr);
	}

	public final void refresh(final IgarasjenPackageV igarasjenPackageV) {
		dao.refresh(igarasjenPackageV);

	}

	public final List<PackableListItem> findApplyableByCustomerNr(final Integer customerNr) {
		return dao.findByCustomerNr(customerNr);
	}

	public final void refresh(final PackableListItem object) {
		refresh((IgarasjenPackageV) object);

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
