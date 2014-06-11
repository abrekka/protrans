package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ProductionOverviewVDAO;
import no.ugland.utransprod.model.ProductionOverviewV;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.springframework.orm.hibernate3.HibernateCallback;

public class ProductionOverviewVDAOHibernate extends BaseDAOHibernate<ProductionOverviewV> implements ProductionOverviewVDAO {

    public ProductionOverviewVDAOHibernate() {
	super(ProductionOverviewV.class);
    }

    public List<ProductionOverviewV> findAll() {
	return (List<ProductionOverviewV>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(ProductionOverviewV.class).addOrder(Order.asc("transportYear")).addOrder(Order.asc("transportWeek"))
			.addOrder(Order.asc("loadingDate")).addOrder(Order.asc("transportName")).list();
	    }

	});
    }

}
