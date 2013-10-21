package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.CuttingDAO;
import no.ugland.utransprod.model.Cutting;
import no.ugland.utransprod.model.Order;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class CuttingDAOHibernate extends
        BaseDAOHibernate<Cutting> implements CuttingDAO {
    /**
     * Konstruktør
     */
    public CuttingDAOHibernate() {
        super(Cutting.class);
    }

    public Cutting findByOrder(final Order order) {
        return (Cutting)getHibernateTemplate().execute(new HibernateCallback() {
        
            @SuppressWarnings("unchecked")
			public Object doInHibernate(final Session session){
                List<Cutting> list = session.createCriteria(Cutting.class).add(Restrictions.eq("order", order)).list();
                if(list!=null&&list.size()==1){
                    return list.get(0);
                }
                return null;
            }
        });
    }

}
