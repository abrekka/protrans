package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.OrdchgrLineVDAO;
import no.ugland.utransprod.model.OrdchgrLineV;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class OrdchgrLineVDAOHibernate extends BaseDAOHibernate<OrdchgrLineV> implements OrdchgrLineVDAO {
    /**
     * Konstruktør
     */
    public OrdchgrLineVDAOHibernate() {
        super(OrdchgrLineV.class);
    }

    @SuppressWarnings("unchecked")
	public List<OrdchgrLineV> getLines(final Integer ordNo, final List<Integer> lnNos) {
        return (List<OrdchgrLineV>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {

                return session.createCriteria(OrdchgrLineV.class).add(Restrictions.eq("ordchgrLineVPK.ordNo", ordNo)).add(
                        Restrictions.in("ordchgrLineVPK.lnNo", lnNos)).list();
            }
        });
    }

}
