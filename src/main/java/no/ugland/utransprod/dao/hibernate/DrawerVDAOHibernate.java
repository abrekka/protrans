package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.DrawerVDAO;
import no.ugland.utransprod.model.DrawerV;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.DrawerGroup;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class DrawerVDAOHibernate extends BaseDAOHibernate<DrawerV> implements
        DrawerVDAO {
    public DrawerVDAOHibernate() {
        super(DrawerV.class);
    }

    @SuppressWarnings("unchecked")
    public final List<DrawerGroup> groupByProductAreaPeriode(
            final List<Integer> productAreaNrList, final Periode periode) {
        return (List<DrawerGroup>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        Integer startDate = Util.convertDateToInt(periode
                                .getStartDate());
                        Integer endDate = Util.convertDateToInt(periode
                                .getEndDate());
                        String sql = "select new no.ugland.utransprod.util.report.DrawerGroup("
                                + "                                   count(drawerV.orderNr),"
                                + "            drawerV.categoryName,"
                                + "            drawerV.drawerName,"
                                + "            sum(drawerV.ownProduction))"
                                + "       from DrawerV drawerV"
                                + "       where drawerV.productAreaNr in(:productAreaNrList) and"
                                + "               drawerV.registered between :startDate and :endDate "
                                + "       group by drawerV.categoryName,drawerV.drawerName";
                        return session.createQuery(sql).setParameterList(
                                "productAreaNrList", productAreaNrList).setParameter(
                                "startDate", startDate).setParameter("endDate",
                                endDate).list();
                    }

                });
    }

    @SuppressWarnings("unchecked")
    public final List<DrawerV> findByProductAreaPeriode(
            final List<Integer> productAreaNrList, final Periode periode) {
        return (List<DrawerV>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        Integer startDate = Util.convertDateToInt(periode
                                .getStartDate());
                        Integer endDate = Util.convertDateToInt(periode
                                .getEndDate());
                        return session.createCriteria(DrawerV.class).add(
                                Restrictions.in("productAreaNr", productAreaNrList)).add(
                                Restrictions.between("registered", startDate,
                                        endDate)).list();
                    }

                });
    }

    @SuppressWarnings("unchecked")
    public final List<Integer> getDocumentIdByPeriode(final Periode periode) {
        return (List<Integer>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        Integer startDate = Util.convertDateToInt(periode
                                .getStartDate());
                        Integer endDate = Util.convertDateToInt(periode
                                .getEndDate());
                        String sql = "select distinct drawerV.documentId"
                                + "       from DrawerV drawerV"
                                + "       where   drawerV.registered between :startDate and :endDate";
                        return session.createQuery(sql).setParameter(
                                "startDate", startDate).setParameter("endDate",
                                endDate).list();
                    }

                });
    }

}
