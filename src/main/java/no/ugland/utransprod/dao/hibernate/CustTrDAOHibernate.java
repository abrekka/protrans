package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.CustTrDAO;
import no.ugland.utransprod.model.CustTr;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

public class CustTrDAOHibernate extends BaseDAOHibernate<CustTr> implements
        CustTrDAO {
    /**
     * Konstruktør
     */
    public CustTrDAOHibernate() {
        super(CustTr.class);
    }

    @SuppressWarnings("unchecked")
	public final List<CustTr> findByOrderNr(final String orderNr) {
        return (List<CustTr>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                String sql = "select custTr from CustTr custTr,Ord ord "
                        + "       where custTr.ordno=ord.ordno and "
                        + "           ord.inf6=:orderNr";
                return session.createQuery(sql).setParameter(
                        "orderNr", orderNr).list();
            }

        });
    }

}
