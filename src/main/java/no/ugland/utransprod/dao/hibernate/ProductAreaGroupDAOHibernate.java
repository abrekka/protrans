package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ProductAreaGroupDAO;
import no.ugland.utransprod.model.ProductAreaGroup;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Interface for DAO mot tabell PRODUCT_AREA_GROUP.
 * @author atle.brekka
 */
public class ProductAreaGroupDAOHibernate extends
        BaseDAOHibernate<ProductAreaGroup> implements ProductAreaGroupDAO {
    public ProductAreaGroupDAOHibernate() {
        super(ProductAreaGroup.class);
    }

    public final ProductAreaGroup findByName(final String name) {
        return (ProductAreaGroup) getHibernateTemplate().execute(
                new HibernateCallback() {

                    @SuppressWarnings("unchecked")
                    public Object doInHibernate(final Session session) {
                        List<ProductAreaGroup> list = session.createCriteria(
                                ProductAreaGroup.class).add(
                                Restrictions
                                        .ilike("productAreaGroupName", name))
                                .list();
                        if (list != null && list.size() == 1) {
                            return list.get(0);
                        }
                        return null;
                    }

                });
    }

}
