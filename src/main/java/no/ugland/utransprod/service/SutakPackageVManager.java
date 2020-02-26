package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.SutakPackageV;

public interface SutakPackageVManager extends IApplyListManager<PackableListItem> {
	String MANAGER_NAME = "sutakPackageVManager";

	List<PackableListItem> findAllApplyable();

	List<PackableListItem> findApplyableByOrderNr(String orderNr);

	void refresh(SutakPackageV sutakPackageV);

	List<PackableListItem> findApplyableByCustomerNr(Integer customerNr);
}
