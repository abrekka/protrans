package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.WindowAccessDAO;
import no.ugland.utransprod.model.WindowAccess;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell WINDOW_ACCESS
 * @author atle.brekka
 */
public class WindowAccessDAOHibernate extends BaseDAOHibernate<WindowAccess>
        implements WindowAccessDAO {

    /**
     * Konstruktør
     */
    public WindowAccessDAOHibernate() {
        super(WindowAccess.class);
    }

    @SuppressWarnings("unchecked")
    public final List<WindowAccess> findAllWithTableNames() {
        return (List<WindowAccess>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        return session.createCriteria(WindowAccess.class).add(
                                Restrictions.isNotNull("tableNames")).list();
                    }

                });
    }

}
