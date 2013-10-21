package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.TransportCostAdditionDAO;
import no.ugland.utransprod.model.TransportCostAddition;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class TransportCostAdditionDAOHibernate extends
        BaseDAOHibernate<TransportCostAddition> implements
        TransportCostAdditionDAO {
    public TransportCostAdditionDAOHibernate() {
        super(TransportCostAddition.class);
    }

    public final TransportCostAddition findByDescription(final String desc) {
        return (TransportCostAddition) getHibernateTemplate().execute(
                new HibernateCallback() {

                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(final Session session) {
                        List<TransportCostAddition> list = session
                                .createCriteria(TransportCostAddition.class)
                                .add(Restrictions.ilike("description", desc))
                                .list();
                        if (list != null && list.size() == 1) {
                            return list.get(0);
                        }
                        return null;
                    }

                });
    }

    @SuppressWarnings("unchecked")
    public final List<TransportCostAddition> findTransportBasisAdditions(
            final Integer isMemberOfMaxAddition) {
        return (List<TransportCostAddition>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        return session.createCriteria(
                                TransportCostAddition.class).add(
                                Restrictions.eq("transportBasis", 1)).add(
                                Restrictions.eq("memberOfMaxAdditions",
                                        isMemberOfMaxAddition)).list();
                    }

                });
    }

}
