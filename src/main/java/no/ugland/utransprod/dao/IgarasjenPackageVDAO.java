package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;

public interface IgarasjenPackageVDAO extends DAO<IgarasjenPackageV> {
    List<PackableListItem> findAll();

    List<PackableListItem> findByOrderNr(String orderNr);

    void refresh(IgarasjenPackageV igarasjenPackageV);
    List<PackableListItem> findByCustomerNr(Integer customerNr);

    List<PackableListItem> findByCustomerNrProductAreaGroup(Integer customerNr, ProductAreaGroup productAreaGroup);

    List<PackableListItem> findByOrderNrAndProductAreaGroup(String orderNr, ProductAreaGroup productAreaGroup);

}
