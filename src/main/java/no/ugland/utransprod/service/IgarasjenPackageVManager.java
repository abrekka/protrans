package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.PackableListItem;

public interface IgarasjenPackageVManager extends IApplyListManager<PackableListItem> {
	String MANAGER_NAME = "igarasjenPackageVManager";

	List<PackableListItem> findAllApplyable();

	List<PackableListItem> findApplyableByOrderNr(String orderNr);

	void refresh(IgarasjenPackageV igrasjenPackageV);

	List<PackableListItem> findApplyableByCustomerNr(Integer customerNr);
}
