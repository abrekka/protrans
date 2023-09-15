package no.ugland.utransprod.dao.hibernate;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import com.google.common.collect.Lists;

import no.ugland.utransprod.dao.ProductionTimeDAO;
import no.ugland.utransprod.gui.model.Delelisteinfo;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProductionTime;
import no.ugland.utransprod.model.ProductionUnit;

public class ProductionTimeDAOHibernate extends BaseDAOHibernate<ProductionTime> implements ProductionTimeDAO {

	public ProductionTimeDAOHibernate() {
		super(ProductionTime.class);
	}

	public List<ProductionTime> findByOrderNrAndProductionname(final String orderNr, final String productionname) {
		return (List<ProductionTime>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				return session.createCriteria(ProductionTime.class).add(Restrictions.eq("orderNr", orderNr))
						.add(Restrictions.eq("productionname", productionname)).list();
			}
		});
	}

	public void deleteAllForUser(final String orderNr, final String productionname, final String userName) {
		getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				return session.createQuery("delete from ProductionTime where orderNr='" + orderNr
						+ "' and productionname='" + productionname + "' and username='" + userName + "'")
						.executeUpdate();
			}
		});

	}

	public List<ProductionTime> findAll() {
		return (List<ProductionTime>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(final Session session) {
				String sql = "select Production_time_id,p.Order_nr,Productionname,Username, u.first_name + ' ' + u.last_name as name,Created,Updated,Started,Stopped,Overtime,o.production_week"
						+ "  FROM Production_time p inner join"
						+ "  Application_user u on u.user_name = p.username inner join"
						+ " Customer_order o on o.order_nr = p.Order_nr order by p.order_nr, p.started ";

				List<ProductionTime> produksjonstider = Lists.newArrayList();
				List<Object[]> resultater = session.createSQLQuery(sql).list();
				for (Object[] linje : resultater) {
					produksjonstider.add(new ProductionTime((Integer) linje[0], (String) linje[1], (String) linje[2],
							(String) linje[3], (String) linje[4], (Timestamp) linje[5], (Timestamp) linje[6],
							(Timestamp) linje[7], (Timestamp) linje[8], (Integer) linje[9], (Integer) linje[10]));

				}
				return produksjonstider;

			}
		});
	}

}
