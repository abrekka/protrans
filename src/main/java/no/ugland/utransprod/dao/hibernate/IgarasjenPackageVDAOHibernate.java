package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

import no.ugland.utransprod.dao.IgarasjenPackageVDAO;
import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.ProductAreaGroup;

public class IgarasjenPackageVDAOHibernate extends BaseDAOHibernate<IgarasjenPackageV> implements IgarasjenPackageVDAO {

	/**
	 * Konstruktør
	 */
	public IgarasjenPackageVDAOHibernate() {
		super(IgarasjenPackageV.class);
	}

	@SuppressWarnings("unchecked")
	public List<PackableListItem> findAll() {
		return getHibernateTemplate().find(
				"from IgarasjenPackageV order by transportYear,transportWeek,loadingDate,transportDetails,loadTime");
	}

	@SuppressWarnings("unchecked")
	public List<PackableListItem> findByOrderNr(final String orderNr) {
		return (List<PackableListItem>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session.createCriteria(IgarasjenPackageV.class).add(Restrictions.eq("orderNr", orderNr)).list();
			}

		});
	}

	public void refresh(IgarasjenPackageV igarasjenPackageV) {
		getHibernateTemplate().flush();
		getHibernateTemplate().load(igarasjenPackageV, igarasjenPackageV.getOrderLineId());

	}

	@SuppressWarnings("unchecked")
	public List<PackableListItem> findByCustomerNr(final Integer customerNr) {
		return (List<PackableListItem>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				return session.createCriteria(IgarasjenPackageV.class).add(Restrictions.eq("customerNr", customerNr))
						.list();
			}

		});
	}

	@SuppressWarnings("unchecked")
	public List<PackableListItem> findByCustomerNrProductAreaGroup(final Integer customerNr,
			final ProductAreaGroup productAreaGroup) {
		return (List<PackableListItem>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(IgarasjenPackageV.class)
						.add(Restrictions.eq("customerNr", customerNr));

				if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
					criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
				}
				return criteria.list();
			}

		});
	}

	@SuppressWarnings("unchecked")
	public List<PackableListItem> findByOrderNrAndProductAreaGroup(final String orderNr,
			final ProductAreaGroup productAreaGroup) {
		return (List<PackableListItem>) getHibernateTemplate().execute(new HibernateCallback() {

			public Object doInHibernate(Session session) throws HibernateException {
				Criteria criteria = session.createCriteria(IgarasjenPackageV.class)
						.add(Restrictions.eq("orderNr", orderNr));

				if (productAreaGroup != null && !productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")) {
					criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
				}
				return criteria.list();
			}

		});
	}

}
