package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.SutakPackageV;

public interface SutakPackageVDAO extends DAO<SutakPackageV> {
	List<PackableListItem> findAll();

	List<PackableListItem> findByOrderNr(String orderNr);

	void refresh(SutakPackageV sutakPackageV);

	List<PackableListItem> findByCustomerNr(Integer customerNr);

	List<PackableListItem> findByCustomerNrProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup);

	List<PackableListItem> findByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup);

}
