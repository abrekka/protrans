package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.UdsalesmallDAO;
import no.ugland.utransprod.model.Udsalesmall;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class UdsalesmallDAOHibernate extends BaseDAOHibernate<Udsalesmall>
        implements UdsalesmallDAO {

    /**
     * Konstruktør.
     */
    public UdsalesmallDAOHibernate() {
        super(Udsalesmall.class);
    }

	public Udsalesmall findByOrderNr(final String orderNr) {
		return (Udsalesmall)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(final Session session){
				String sql="select udsalesmall " +
						"     from Udsalesmall udsalesmall,Sale sale" +
						"     where udsalesmall.udsalesmallId=sale.userdefId and " +
						"           sale.number1=:orderNr";
				List<Udsalesmall> list = session.createQuery(sql).setParameter("orderNr", orderNr).list();
				return list!=null&&list.size()==1?list.get(0):null;
			}
		});
	}

}
