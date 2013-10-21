package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.IntelleVDAO;
import no.ugland.utransprod.model.IntelleV;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class IntelleVDAOHibernate extends BaseDAOHibernate<IntelleV>
implements IntelleVDAO {

	public IntelleVDAOHibernate() {
		super(IntelleV.class);
	}

	public IntelleV findByOrderNr(final String orderNr) {
		return (IntelleV)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session){
				List<IntelleV> list = session.createCriteria(IntelleV.class).add(Restrictions.eq("orderNr", orderNr)).list();
				return list!=null&&list.size()!=0?list.get(0):null;
			}
		});
	}

}
