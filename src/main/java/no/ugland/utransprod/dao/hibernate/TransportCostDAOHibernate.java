package no.ugland.utransprod.dao.hibernate;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.dao.TransportCostDAO;
import no.ugland.utransprod.model.TransportCost;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class TransportCostDAOHibernate extends BaseDAOHibernate<TransportCost>
        implements TransportCostDAO {
    public TransportCostDAOHibernate() {
        super(TransportCost.class);
    }

    public final List<TransportCost> findAll() {

        return getObjects();
    }

    public final TransportCost findByPostalCode(final String postalCode) {
        return (TransportCost) getHibernateTemplate().execute(
                new HibernateCallback() {

                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(final Session session) {
                        List<TransportCost> list = session.createCriteria(
                                TransportCost.class).add(
                                Restrictions.eq("valid", 1)).add(
                                Restrictions.eq("postalCode", postalCode))
                                .list();
                        if (list != null && list.size() > 0) {
                            return list.get(0);
                        }
                        return null;
                    }

                });
    }

    public final void setAllPostalCodesInvalid() {
        getHibernateTemplate().bulkUpdate("update TransportCost set valid=0");
    }

    public final void updatePriceForPostalCodes(final String postalCodeFrom,
            final String postalCodeTo, final BigDecimal price,
            final Integer maxAddition, final BigDecimal addition) {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                String sql = "update TransportCost set cost=:price,valid=1,maxAddition=:maxAddition,addition="
                        + ":addition "
                        + "          where postalCode between :postalCodeFrom and :postalCodeTo";
                session.createQuery(sql).setParameter("price", price)
                        .setParameter("postalCodeFrom", postalCodeFrom)
                        .setParameter("postalCodeTo", postalCodeTo)
                        .setParameter("maxAddition", maxAddition).setParameter(
                                "addition", addition).executeUpdate();
                return null;
            }

        });

    }

    public final String findCountyNameByPostalCode(final String postalCode) {
        return (String) getHibernateTemplate().execute(new HibernateCallback() {

            @SuppressWarnings("unchecked")
            public Object doInHibernate(final Session session) {
                List<TransportCost> costs = session.createCriteria(
                        TransportCost.class).add(
                        Restrictions.eq("postalCode", postalCode)).list();
                if (costs != null && costs.size() != 0) {
                    return costs.get(0).getArea().getCounty().getCountyName();
                }
                return null;
            }

        });
    }

}
