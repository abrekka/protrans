/*    */ package no.ugland.utransprod.dao.hibernate;

import java.sql.SQLException;
/*    */
/*    */ import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/*    */ import no.ugland.utransprod.dao.FrontProductionVDAO;
/*    */ import no.ugland.utransprod.model.FrontProductionV;
/*    */ import no.ugland.utransprod.model.Produceable;
/*    */ import no.ugland.utransprod.model.ProductAreaGroup;

/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */
/*    */ public class FrontProductionVDAOHibernate extends BaseDAOHibernate<FrontProductionV>
		implements FrontProductionVDAO {
	/*    */ public FrontProductionVDAOHibernate() {
		/* 28 */ super(FrontProductionV.class);
		/* 29 */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public List<Produceable> findAll() {
		/* 36 */ return this.getHibernateTemplate().find(
				"from FrontProductionV order by transportYear,transportWeek,loadingDate,transportDetails,loadTime");
		/*    */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public List<Produceable> findByOrderNr(final String orderNr) {
		return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session.createCriteria(FrontProductionV.class).add(Restrictions.eq("orderNr", orderNr)).list();
			}

		});
		/*    */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public void refresh(Produceable frontProductionV) {
		/* 57 */ this.getHibernateTemplate().flush();
		/* 58 */ this.getHibernateTemplate().load(frontProductionV, frontProductionV.getOrderNr());
		/*    */
		/* 60 */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public List<Produceable> findByCustomerNr(final Integer customerNr) {
		/* 67 */ return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session.createCriteria(FrontProductionV.class).add(Restrictions.eq("customerNr", customerNr))
						.list();
			}

		});
		/*    */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public List<Produceable> findByCustomerNrProductAreaGroup(final Integer customerNr,
			final ProductAreaGroup productAreaGroup) {
		return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(FrontProductionV.class)
						.add(Restrictions.eq("customerNr", customerNr));
				if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
					criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
				}
				return criteria.list();
			}

		});
		/*    */ }

	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */
	/*    */ public List<Produceable> findByOrderNrAndProductAreaGroup(final String orderNr,
			final ProductAreaGroup productAreaGroup) {
		return (List<Produceable>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(FrontProductionV.class)
						.add(Restrictions.eq("orderNr", orderNr));
				if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
					criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
				}
				return criteria.list();
			}

		});
		/*    */ }
	/*    */ }
