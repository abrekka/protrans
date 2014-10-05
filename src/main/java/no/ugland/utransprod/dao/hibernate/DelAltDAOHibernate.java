package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.DelAltDAO;
import no.ugland.utransprod.model.DelAlt;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class DelAltDAOHibernate extends HibernateDaoSupport implements DelAltDAO {

    public List<DelAlt> finnForProdno(final String prodno) {
	return (List<DelAlt>) getHibernateTemplate().execute(new HibernateCallback() {

	    public Object doInHibernate(final Session session) {
		return session.createCriteria(DelAlt.class).add(Restrictions.eq("prodNo", prodno)).list();
	    }
	});
    }

}
