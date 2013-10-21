package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ProductionUnitDAO;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.ProductionUnit;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class ProductionUnitDAOHibernate extends BaseDAOHibernate<ProductionUnit> implements ProductionUnitDAO {
    /**
     * Konstruktør
     */
    public ProductionUnitDAOHibernate() {
        super(ProductionUnit.class);
    }

    /*public final void lazyLoad(final ProductionUnit productionUnit, final LazyLoadProductionUnitEnum[] enums) {
        if (productionUnit != null && productionUnit.getProductionUnitId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) {
                    session.load(productionUnit, productionUnit.getProductionUnitId());
                    Set<?> set;

                    for (LazyLoadProductionUnitEnum lazyEnum : enums) {
                        switch (lazyEnum) {
                        case PRODUCTION_UNIT_PRODUCT_AREA_GROUP:
                            set = productionUnit.getProductionUnitProductAreaGroups();
                            set.iterator();
                            break;
                        default:
                        }
                    }
                    return null;
                }

            });
        }

    }*/

    @SuppressWarnings("unchecked")
    public final List<ProductionUnit> findByArticleTypeProductAreaGroup(final ArticleType articleType,
            final String productAreaGroupName) {
        return (List<ProductionUnit>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                String sql = "select productionUnit from ProductionUnit productionUnit "
                        + "     where productionUnit.articleType=:articleType and " + "     exists(select 1 "
                        + "from ProductionUnitProductAreaGroup productionUnitProductAreaGroup "
                        + "      where productionUnitProductAreaGroup.productionUnit="
                        + "      productionUnit and "
                        + "  (productionUnitProductAreaGroup.productAreaGroup.productAreaGroupName="
                        + ":productAreaGroupName or "
                        + "  productionUnitProductAreaGroup.productAreaGroup.productAreaGroupName="
                        + ":groupNameAll))";
                return session.createQuery(sql).setParameter("articleType", articleType).setParameter(
                        "productAreaGroupName", productAreaGroupName).setParameter("groupNameAll", "Alle")
                        .list();
            }

        });
    }

    public ProductionUnit findByName(final String name) {
        return (ProductionUnit) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                List<ProductionUnit> list = session.createCriteria(ProductionUnit.class).add(
                        Restrictions.eq("productionUnitName", name)).list();
                if (list != null) {
                    return list.get(0);
                }
                return null;
            }
        });
    }

}
