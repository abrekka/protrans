package no.ugland.utransprod.dao.hibernate;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.List;

import no.ugland.utransprod.dao.NokkelProduksjonVDAO;
import no.ugland.utransprod.model.NokkelProduksjonV;
import no.ugland.utransprod.model.NokkelProduksjonVPK;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for view NOKKEL_PRODUKSJON_V
 * @author atle.brekka
 */
public class NokkelProduksjonVDAOHibernate extends
        BaseDAOHibernate<NokkelProduksjonV> implements NokkelProduksjonVDAO {
    /**
     * Konstruktør
     */
    public NokkelProduksjonVDAOHibernate() {
        super(NokkelProduksjonV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.NokkelProduksjonVDAO#findByWeek(java.lang.Integer,
     *      java.lang.Integer)
     */
    public final NokkelProduksjonV findByWeek(final Integer year,
            final Integer week) {
        return (NokkelProduksjonV) getHibernateTemplate().execute(
                new HibernateCallback() {

                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(final Session session) {
                        List<NokkelProduksjonV> list = session.createCriteria(
                                NokkelProduksjonV.class).add(
                                Restrictions.eq(
                                        "nokkelProduksjonVPK.orderReadyYear",
                                        year)).add(
                                Restrictions.eq(
                                        "nokkelProduksjonVPK.orderReadyWeek",
                                        week)).list();

                        NokkelProduksjonV nokkelProduksjonV = null;
                        if (list != null) {
                            nokkelProduksjonV = new NokkelProduksjonV(null, 0,
                                    BigDecimal.valueOf(0), 0, BigDecimal
                                            .valueOf(0), BigDecimal.valueOf(0),
                                    BigDecimal.valueOf(0), BigDecimal
                                            .valueOf(0));
                            for (NokkelProduksjonV nokkel : list) {
                                if (nokkel != null) {
                                    nokkelProduksjonV
                                            .setBudgetDeviation(nokkelProduksjonV
                                                    .getBudgetDeviation()
                                                    .add(
                                                            nokkel
                                                                    .getBudgetDeviation()));
                                    nokkelProduksjonV.setBudgetValue(nokkel
                                            .getBudgetValue());
                                    nokkelProduksjonV
                                            .setCountOrderReady(nokkelProduksjonV
                                                    .getCountOrderReady()
                                                    + nokkel
                                                            .getCountOrderReady());
                                    nokkelProduksjonV
                                            .setPackageSumWeek(nokkelProduksjonV
                                                    .getPackageSumWeek()
                                                    .add(
                                                            Util
                                                                    .convertNullToBigDecimal(nokkel
                                                                            .getPackageSumWeek())));
                                    nokkelProduksjonV.calculateDeviationProc();
                                }
                            }
                        }

                        return nokkelProduksjonV;
                    }

                });
    }

    /**
     * @see no.ugland.utransprod.dao.NokkelProduksjonVDAO#
     *      aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
     *      java.lang.String)
     */
    public final NokkelProduksjonV aggreagateYearWeek(
            final YearWeek currentYearWeek, final String productArea) {
        return (NokkelProduksjonV) getHibernateTemplate().execute(
                new HibernateCallback() {

                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(final Session session) {
                        NokkelProduksjonV nokkelProduksjonV = null;
                        List<Object> list = session
                                .createQuery(
                                        "select sum(nokkelProduksjonV.packageSumWeek),"
                                                + "sum(nokkelProduksjonV.budgetValue),"
                                                + "sum(nokkelProduksjonV.budgetDeviation),"
                                                + "sum(nokkelProduksjonV.countOrderReady),"
                                                + "sum(nokkelProduksjonV.deviationCount),"
                                                + "sum(nokkelProduksjonV.internalCost)"
                                                + "from NokkelProduksjonV nokkelProduksjonV "
                                                + "where nokkelProduksjonV.nokkelProduksjonVPK.orderReadyYear"
                                                + "=:year and "
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyWeek "
                                                + "<=:week and "
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.productArea="
                                                + ":productArea")
                                .setParameter("year", currentYearWeek.getYear())
                                .setParameter("week", currentYearWeek.getWeek())
                                .setParameter("productArea", productArea)
                                .list();
                        if (list != null && list.size() == 1) {
                            Object[] result = (Object[]) list.get(0);
                            nokkelProduksjonV = new NokkelProduksjonV();
                            nokkelProduksjonV
                                    .setNokkelProduksjonVPK(new NokkelProduksjonVPK(
                                            currentYearWeek.getYear(),
                                            currentYearWeek.getWeek(),
                                            productArea, null));
                            nokkelProduksjonV
                                    .setCountOrderReady((Integer) result[3]);
                            nokkelProduksjonV
                                    .setDeviationCount((Integer) result[4]);
                            nokkelProduksjonV
                                    .setInternalCost((BigDecimal) result[5]);
                            nokkelProduksjonV
                                    .setPackageSumWeek((BigDecimal) result[0]);
                            nokkelProduksjonV
                                    .setBudgetValue((BigDecimal) result[1]);
                            nokkelProduksjonV
                                    .setBudgetDeviation((BigDecimal) result[2]);

                            if (nokkelProduksjonV.getBudgetValue() != null
                                    && nokkelProduksjonV.getBudgetValue()
                                            .intValue() != 0) {
                                nokkelProduksjonV
                                        .setBudgetDeviationProc(nokkelProduksjonV
                                                .getBudgetDeviation()
                                                .divide(
                                                        nokkelProduksjonV
                                                                .getBudgetValue(),
                                                        2, RoundingMode.HALF_UP)
                                                .multiply(
                                                        BigDecimal.valueOf(100),
                                                        new MathContext(
                                                                100,
                                                                RoundingMode.HALF_UP)));
                            } else {
                                nokkelProduksjonV
                                        .setBudgetDeviationProc(BigDecimal
                                                .valueOf(100));
                            }

                        }
                        return nokkelProduksjonV;
                    }

                });
    }

    @SuppressWarnings("unchecked")
    public final List<NokkelProduksjonV> findByYearWeekProductArea(
            final Integer year, final Integer weekFrom, final Integer weekTo,
            final String productAreaName) {
        return (List<NokkelProduksjonV>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {

                        return session
                                .createQuery(
                                        "select "
                                                + "new NokkelProduksjonV("
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyYear,"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyWeek,"
                                                + "sum(nokkelProduksjonV.countOrderReady),"
                                                + "sum(nokkelProduksjonV.budgetValue),"
                                                + "sum(nokkelProduksjonV.packageSumWeek),"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.productArea, "
                                                + "sum(nokkelProduksjonV.budgetDeviation))"
                                                + "from NokkelProduksjonV nokkelProduksjonV "
                                                + "where nokkelProduksjonV.nokkelProduksjonVPK.productArea="
                                                + ":productArea and "
                                                + " nokkelProduksjonV.nokkelProduksjonVPK.orderReadyYear="
                                                + ":year and "
                                                + " nokkelProduksjonV.nokkelProduksjonVPK.orderReadyWeek"
                                                + " between :weekFrom and :weekTo "
                                                + "group by "
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyYear,"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyWeek,"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.productArea")
                                .setParameter("year", year).setParameter(
                                        "weekFrom", weekFrom).setParameter(
                                        "weekTo", weekTo).setParameter(
                                        "productArea", productAreaName).list();
                    }

                });
    }

    /**
     * @see no.ugland.utransprod.dao.NokkelProduksjonVDAO#
     *      findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
     *      no.ugland.utransprod.util.YearWeek, java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public final List<NokkelProduksjonV> findBetweenYearWeek(
            final YearWeek fromYearWeek, final YearWeek toYearWeek,
            final String productArea) {
        return (List<NokkelProduksjonV>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        String query = "select nokkelProduksjonV "
                                + "from NokkelProduksjonV nokkelProduksjonV "
                                + "where "
                                + "cast(nokkelProduksjonV.nokkelProduksjonVPK.orderReadyYear as string)"
                                + "||"
                                + "cast(nokkelProduksjonV.nokkelProduksjonVPK.orderReadyWeek+10 as string) "
                                + "between  :fromString and :toString and "
                                + "nokkelProduksjonV.nokkelProduksjonVPK.productArea=:productArea";

                        return session.createQuery(query).setParameter(
                                "fromString",
                                fromYearWeek.getFormattetYearWeekAdd10())
                                .setParameter("toString",
                                        toYearWeek.getFormattetYearWeekAdd10())
                                .setParameter("productArea", productArea)
                                .list();
                    }

                });
    }

    @SuppressWarnings("unchecked")
    public final List<NokkelProduksjonV> findByYearWeekProductAreaGroup(
            final Integer year, final Integer weekFrom, final Integer weekTo,
            final String productAreaGroupName) {
        return (List<NokkelProduksjonV>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {

                        return session
                                .createQuery(
                                        "select "
                                                + "new NokkelProduksjonV("
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyYear,"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyWeek,"
                                                + "sum(nokkelProduksjonV.countOrderReady),"
                                                + "sum(nokkelProduksjonV.budgetValue),"
                                                + "sum(nokkelProduksjonV.packageSumWeek),"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.productArea, "
                                                + "sum(nokkelProduksjonV.budgetDeviation),"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK."
                                                + "productAreaGroupName)"
                                                + "from NokkelProduksjonV nokkelProduksjonV "
                                                + "where "
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.productAreaGroupName"
                                                + "=:productAreaGroupName and "
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyYear="
                                                + ":year and "
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyWeek "
                                                + "between :weekFrom and :weekTo "
                                                + "group by "
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyYear,"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.orderReadyWeek,"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK.productArea,"
                                                + "nokkelProduksjonV.nokkelProduksjonVPK."
                                                + "productAreaGroupName")
                                .setParameter("year", year).setParameter(
                                        "weekFrom", weekFrom).setParameter(
                                        "weekTo", weekTo).setParameter(
                                        "productAreaGroupName",
                                        productAreaGroupName).list();
                    }

                });
    }

}
