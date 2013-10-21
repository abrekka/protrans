package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.SaleStatusVDAO;
import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SaleStatusV;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class SaleStatusVDAOHibernate extends BaseDAOHibernate<SaleStatusV> implements SaleStatusVDAO {

    public SaleStatusVDAOHibernate() {
        super(SaleStatusV.class);
    }

    public List<SaleStatusV> findByProbabilitesAndProductArea(
            final List<Integer> probabilities, final ProductArea productArea) {
        return (List<SaleStatusV>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                Criteria criteria = session.createCriteria(SaleStatusV.class).add(
                        Restrictions.in("probability", probabilities));

                
                criteria = productArea != null ? criteria.add(Restrictions.eq("deptno", Integer.valueOf(productArea
                        .getProductAreaNr()))) : criteria;
                return criteria.list();
            }
        });
    }

}
