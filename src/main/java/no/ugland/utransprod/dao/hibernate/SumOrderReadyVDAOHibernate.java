package no.ugland.utransprod.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.dao.SumOrderReadyVDAO;
import no.ugland.utransprod.model.SumOrderReadyV;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot view SUM_ORDER_READY_V
 * @author atle.brekka
 */
public class SumOrderReadyVDAOHibernate extends BaseDAOHibernate<SumOrderReadyV> implements SumOrderReadyVDAO {

    /**
     * Konstruktør
     */
    public SumOrderReadyVDAOHibernate() {
        super(SumOrderReadyV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.SumOrderReadyVDAO#findByDate(java.lang.String)
     */
    public SumOrderReadyV findByDate(final String dateString) {
        return (SumOrderReadyV) getHibernateTemplate().execute(new HibernateCallback() {

            @SuppressWarnings("unchecked")
            public Object doInHibernate(Session session) throws HibernateException {
                List<SumOrderReadyV> list = session.createCriteria(SumOrderReadyV.class).add(
                        Restrictions.eq("sumOrderReadyVPK.orderReady", dateString)).list();
                SumOrderReadyV sumOrderReadyV = null;
                if (list != null) {
                    sumOrderReadyV = new SumOrderReadyV(null, BigDecimal.valueOf(0), null, null, null);
                    for (SumOrderReadyV sum : list) {
                        sumOrderReadyV.setPackageSum(sumOrderReadyV.getPackageSum().add(sum.getPackageSum()));
                    }
                }

                if (list != null && list.size() == 1) {
                    return list.get(0);
                }
                return sumOrderReadyV;
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.SumOrderReadyVDAO#findSumByWeek(java.lang.Integer,
     *      java.lang.Integer)
     */
    public SumOrderReadyV findSumByWeek(final Integer year, final Integer week) {
        return (SumOrderReadyV) getHibernateTemplate().execute(new HibernateCallback() {

            @SuppressWarnings("unchecked")
            public Object doInHibernate(Session session) throws HibernateException {
                List<SumOrderReadyV> list = session.createCriteria(SumOrderReadyV.class).add(
                        Restrictions.eq("orderReadyYear", year)).add(Restrictions.eq("orderReadyWeek", week))
                        .list();
                BigDecimal sum = new BigDecimal(0);
                if (list != null) {
                    for (SumOrderReadyV sumOrderReadyV : list) {
                        sum = sum.add(sumOrderReadyV.getPackageSum());
                    }
                }
                return new SumOrderReadyV(null, sum, week, year, null);
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.SumOrderReadyVDAO#findByDateAndProductAreaGroupName(java.lang.String,
     *      java.lang.String)
     */
    public SumOrderReadyV findByDateAndProductAreaGroupName(final String dateString,
            final String productAreaGroupName) {
        return (SumOrderReadyV) getHibernateTemplate().execute(new HibernateCallback() {

            @SuppressWarnings("unchecked")
            public Object doInHibernate(Session session) throws HibernateException {
                List<SumOrderReadyV> list = session.createCriteria(SumOrderReadyV.class).add(
                        Restrictions.eq("sumOrderReadyVPK.orderReady", dateString)).add(
                        Restrictions.eq("productAreaGroupName", productAreaGroupName)).list();
                SumOrderReadyV sumOrderReadyV = null;
                if (list != null) {
                    sumOrderReadyV = new SumOrderReadyV(null, BigDecimal.valueOf(0), null, null, null);
                    for (SumOrderReadyV sum : list) {
                        sumOrderReadyV.setPackageSum(sumOrderReadyV.getPackageSum().add(sum.getPackageSum()));
                    }
                }

                if (list != null && list.size() == 1) {
                    return list.get(0);
                }
                return sumOrderReadyV;
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.SumOrderReadyVDAO#findSumByWeekAndProductAreaGroupName(java.lang.Integer,
     *      java.lang.Integer, java.lang.String)
     */
    public SumOrderReadyV findSumByWeekAndProductAreaGroupName(final Integer year, final Integer week,
            final String productAreaGroupName) {
        return (SumOrderReadyV) getHibernateTemplate().execute(new HibernateCallback() {

            @SuppressWarnings("unchecked")
            public Object doInHibernate(Session session) throws HibernateException {
                List<SumOrderReadyV> list = session.createCriteria(SumOrderReadyV.class).add(
                        Restrictions.eq("orderReadyYear", year)).add(Restrictions.eq("orderReadyWeek", week))
                        .add(Restrictions.eq("productAreaGroupName", productAreaGroupName)).list();
                BigDecimal sum = new BigDecimal(0);
                if (list != null) {
                    for (SumOrderReadyV sumOrderReadyV : list) {
                        sum = sum.add(sumOrderReadyV.getPackageSum());
                    }
                }
                return new SumOrderReadyV(null, sum, week, year, null);
            }

        });
    }

}
