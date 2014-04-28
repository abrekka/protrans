package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ProductionOverviewVDAO;
import no.ugland.utransprod.model.ProductionOverviewV;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ProductionOverviewVDAOHibernate extends HibernateDaoSupport implements ProductionOverviewVDAO {

    public List<ProductionOverviewV> findAll() {
	return getHibernateTemplate().loadAll(ProductionOverviewV.class);
    }

}
