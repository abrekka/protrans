package no.ugland.utransprod.dao.hibernate;

import java.util.Collection;
import java.util.List;

import no.ugland.utransprod.dao.DeviationStatusDAO;
import no.ugland.utransprod.model.DeviationStatus;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for hibernate
 * @author atle.brekka
 */
public class DeviationStatusDAOHibernate extends
        BaseDAOHibernate<DeviationStatus> implements DeviationStatusDAO {
    /**
     * Konstruktør
     */
    public DeviationStatusDAOHibernate() {
        super(DeviationStatus.class);
    }

    /**
     * @see no.ugland.utransprod.dao.DeviationStatusDAO#
     *      refreshObject(no.ugland.utransprod.model.DeviationStatus)
     */
    public final void refreshObject(final DeviationStatus deviationStatus) {
        getHibernateTemplate().load(deviationStatus,
                deviationStatus.getDeviationStatusId());

    }

    /**
     * @see no.ugland.utransprod.dao.DeviationStatusDAO#findAllNotForManager()
     */
    @SuppressWarnings("unchecked")
    public final List<DeviationStatus> findAllNotForManager() {
        return (List<DeviationStatus>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        return session.createCriteria(DeviationStatus.class)
                                .add(
                                        Restrictions.or(Restrictions
                                                .isNull("forManager"),
                                                Restrictions
                                                        .eq("forManager", 0)))
                                .list();
                    }

                });
    }

    public final Integer countUsedByDeviation(
            final DeviationStatus deviationStatus) {
        return (Integer) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        String sql = "select count(deviation.deviationId) "
                                + "       from Deviation deviation"
                                + "       where deviation.deviationStatus=:deviationStatus";
                        return session.createQuery(sql).setParameter(
                                "deviationStatus", deviationStatus)
                                .uniqueResult();
                    }

                });
    }

	public List<DeviationStatus> findAllForDeviation() {
		 return (List<DeviationStatus>) getHibernateTemplate().execute(
	                new HibernateCallback() {

	                    public Object doInHibernate(final Session session) {
	                        return session.createCriteria(DeviationStatus.class)
	                                .add(
	                                        Restrictions.eq("forDeviation", Integer.valueOf(1)))
	                                .list();
	                    }

	                });
	}

	public Collection<DeviationStatus> findAllNotForManagerForAccident() {
		return (List<DeviationStatus>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        return session.createCriteria(DeviationStatus.class)
                                .add(
                                        Restrictions.or(Restrictions
                                                .isNull("forManager"),
                                                Restrictions
                                                        .eq("forManager", 0))).add(Restrictions.eq("forAccident", Integer.valueOf(1)))
                                .list();
                    }

                });
	}

	public Collection<DeviationStatus> findAllNotForManagerForDeviation() {
		return (List<DeviationStatus>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        return session.createCriteria(DeviationStatus.class)
                                .add(
                                        Restrictions.or(Restrictions
                                                .isNull("forManager"),
                                                Restrictions
                                                        .eq("forManager", 0))).add(Restrictions.eq("forDeviation", Integer.valueOf(1)))
                                .list();
                    }

                });
	}

	public Collection<DeviationStatus> findAllForAccident() {
		 return (List<DeviationStatus>) getHibernateTemplate().execute(
	                new HibernateCallback() {

	                    public Object doInHibernate(final Session session) {
	                        return session.createCriteria(DeviationStatus.class)
	                                .add(
	                                        Restrictions.eq("forAccident", Integer.valueOf(1)))
	                                .list();
	                    }

	                });
	}

}
