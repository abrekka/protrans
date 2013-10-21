package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ProcentDoneDAO;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProcentDone;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class ProcentDoneDAOHibernate extends BaseDAOHibernate<ProcentDone>
        implements ProcentDoneDAO {
    /**
     * Konstruktør
     */
    public ProcentDoneDAOHibernate() {
        super(ProcentDone.class);
    }

    @SuppressWarnings("unchecked")
    public final ProcentDone findByYearWeekOrder(final Integer year,
            final Integer week, final Order order) {
        return (ProcentDone) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        List<ProcentDone> list = session.createCriteria(
                                ProcentDone.class).add(
                                Restrictions.eq("procentDoneYear", year)).add(
                                Restrictions.eq("procentDoneWeek", week)).add(
                                Restrictions.eq("order", order)).list();
                        if (list != null && list.size() == 1) {
                            return list.get(0);
                        }
                        return null;
                    }

                });
    }

}
