package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.FakturagrunnlagVDAO;
import no.ugland.utransprod.model.FakturagrunnlagV;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class FakturagrunnlagVDAOHibernate extends HibernateDaoSupport implements FakturagrunnlagVDAO {

    public List<FakturagrunnlagV> finnFakturagrunnlag(final Integer orderId) {
	return (List<FakturagrunnlagV>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(Session session) throws HibernateException {
		return session.createCriteria(FakturagrunnlagV.class).add(Restrictions.eq("fakturagrunnlagVPK.orderId", orderId))
			.addOrder(Order.asc("free2")).addOrder(Order.asc("fakturagrunnlagVPK.lnno")).list();
	    }

	});
    }

}
