package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ProductAreaDAO;
import no.ugland.utransprod.model.ProductArea;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell PRODUCT_AREA
 * @author atle.brekka
 *
 */
public class ProductAreaDAOHibernate extends BaseDAOHibernate<ProductArea>
		implements ProductAreaDAO {
	/**
	 * Konstruktør
	 */
	public ProductAreaDAOHibernate() {
		super(ProductArea.class);
	}

	public ProductArea findByProductAreaNr(final Integer productAreaNr) {
		return (ProductArea)getHibernateTemplate().execute(new HibernateCallback() {
			
			@SuppressWarnings("unchecked")
			public Object doInHibernate(final Session session){
				List<ProductArea> list = session.createCriteria(ProductArea.class).add(Restrictions.eq("productAreaNr", String.valueOf(productAreaNr))).list();
				return list!=null&&list.size()==1?list.get(0):null;
			}
		});
	}

	@SuppressWarnings("unchecked")
	public List<String> findAllNames() {
		return (List<String>)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session){
				String sql="select productArea.productArea from ProductArea productArea";
				return session.createQuery(sql).list();
			}
		});
	}

	

}
