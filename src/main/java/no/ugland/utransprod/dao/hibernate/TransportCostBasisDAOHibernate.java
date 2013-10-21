package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.TransportCostBasisDAO;
import no.ugland.utransprod.model.TransportCostBasis;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class TransportCostBasisDAOHibernate extends
        BaseDAOHibernate<TransportCostBasis> implements TransportCostBasisDAO {
    public TransportCostBasisDAOHibernate() {
        super(TransportCostBasis.class);
    }

    @SuppressWarnings("unchecked")
    public final List<TransportCostBasis> findById(
            final Integer transportCostBasisId) {
        return (List<TransportCostBasis>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        return session.createCriteria(TransportCostBasis.class)
                                .add(
                                        Restrictions.eq("transportCostBasisId",
                                                transportCostBasisId)).list();
                    }

                });
    }

   /* public final void lazyLoad(final TransportCostBasis transportCostBasis,
            final LazyLoadTransportCostBasisEnum[] enums) {
        if (transportCostBasis != null
                && transportCostBasis.getTransportCostBasisId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) {
                    if (!session.contains(transportCostBasis)) {
                        session.load(transportCostBasis, transportCostBasis
                                .getTransportCostBasisId());
                    }
                    Set<?> set;

                    for (LazyLoadTransportCostBasisEnum lazyEnum : enums) {
                        switch (lazyEnum) {
                        case ORDER_COST:
                            set = transportCostBasis.getOrderCosts();
                            set.iterator();
                            break;
                        default:
                            break;
                        }
                    }
                    return null;
                }

            });
        }

    }*/

    public final void setInvoiceNr(final TransportCostBasis transportCostBasis,
            final String invoiceNr) {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                String sql = "update OrderCost orderCost set invoiceNr=:invoiceNr "
                        + "          where orderCost.transportCostBasis = :transportCostBasis";
                session.createQuery(sql).setParameter("invoiceNr", invoiceNr)
                        .setParameter("transportCostBasis", transportCostBasis)
                        .executeUpdate();

                return null;
            }

        });

    }

}
