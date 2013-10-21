package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.CraningCostDAO;
import no.ugland.utransprod.model.CraningCost;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class CraningCostDAOHibernate extends BaseDAOHibernate<CraningCost>
        implements CraningCostDAO {
    /**
     * Konstruktør
     */
    public CraningCostDAOHibernate() {
        super(CraningCost.class);
    }

    public final CraningCost findByRuleId(final String ruleId) {
        return (CraningCost) getHibernateTemplate().execute(
                new HibernateCallback() {

                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(final Session session) {
                        List<CraningCost> list = session.createCriteria(
                                CraningCost.class).add(
                                Restrictions.eq("ruleId", ruleId)).list();
                        if (list != null && list.size() == 1) {
                            return list.get(0);
                        }
                        return null;
                    }

                });
    }

}
