package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.ProductionUnitDAO;
import no.ugland.utransprod.dao.ProduksjonskapasitetDAO;
import no.ugland.utransprod.model.ProductionUnit;
import no.ugland.utransprod.model.Produksjonskapasitet;

public class ProduksjonskapasitetDAOHibernate extends BaseDAOHibernate<Produksjonskapasitet> implements ProduksjonskapasitetDAO {

	public ProduksjonskapasitetDAOHibernate() {
		super(Produksjonskapasitet.class);
	}

}
