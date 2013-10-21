package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.PreventiveActionDAO;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.PreventiveAction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO for tabell PREVENTIVE_ACTION for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class PreventiveActionDAOHibernate extends
		BaseDAOHibernate<PreventiveAction> implements PreventiveActionDAO {

	/**
	 * 
	 */
	public PreventiveActionDAOHibernate() {
		super(PreventiveAction.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.PreventiveActionDAO#findAll()
	 */
	@SuppressWarnings("unchecked")
	public List<PreventiveAction> findAll() {
		return getHibernateTemplate().find("from PreventiveAction");
	}

	/**
	 * @see no.ugland.utransprod.dao.PreventiveActionDAO#refreshPreventiveAction(no.ugland.utransprod.model.PreventiveAction)
	 */
	public void refreshPreventiveAction(PreventiveAction preventiveAction) {
		getHibernateTemplate().load(preventiveAction,
				preventiveAction.getPreventiveActionId());
		getHibernateTemplate().flush();

	}

	/**
	 * @see no.ugland.utransprod.dao.PreventiveActionDAO#lazyLoad(no.ugland.utransprod.model.PreventiveAction,
	 *      no.ugland.utransprod.service.enums.LazyLoadPreventiveActionEnum[])
	 */
	/*public void lazyLoad(final PreventiveAction preventiveAction,
			final LazyLoadPreventiveActionEnum[] enums) {
		if (preventiveAction != null
				&& preventiveAction.getPreventiveActionId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException {
					if (!session.contains(preventiveAction)) {
						session.load(preventiveAction, preventiveAction
								.getPreventiveActionId());
					}
					

					for (LazyLoadPreventiveActionEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case COMMENTS:
							Set<PreventiveActionComment> set = preventiveAction.getPreventiveActionComments();
                            if(set!=null){
                                for(PreventiveActionComment comment:set){
                                    Set<PreventiveActionCommentCommentType> types=comment.getPreventiveActionCommentCommentTypes();
                                    types.iterator();
                                }
                            }
							
							break;
						}
					}
					return null;
				}

			});
		}

	}*/

	/**
	 * @see no.ugland.utransprod.dao.PreventiveActionDAO#findAllOpenByFunctionAndCategory(no.ugland.utransprod.model.JobFunction,
	 *      no.ugland.utransprod.model.FunctionCategory)
	 */
	@SuppressWarnings("unchecked")
	public List<PreventiveAction> findAllOpenByFunctionAndCategory(
			final JobFunction jobFunction,
			final FunctionCategory functionCategory) {
		return (List<PreventiveAction>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(PreventiveAction.class)
								.add(
										Restrictions.isNull("closedDate"))
								.add(
										Restrictions.eq("jobFunction",
												jobFunction)).add(
										Restrictions.eq("functionCategory",
												functionCategory)).list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.PreventiveActionDAO#findAllOpen()
	 */
	@SuppressWarnings("unchecked")
	public List<PreventiveAction> findAllOpen() {
		return (List<PreventiveAction>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						return session.createCriteria(PreventiveAction.class)
								.add(Restrictions.isNull("closedDate")).list();
					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.PreventiveActionDAO#findAllClosedInMonth(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public List<PreventiveAction> findAllClosedInMonth(final Integer month) {
		return (List<PreventiveAction>) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						String sql = "select * "
								+ "		from Preventive_Action preventiveAction "
								+ "		where preventiveAction.closed_Date is not null and "
								+ "				DATEPART(month, preventiveAction.closed_date)=:month";
						return session.createSQLQuery(sql).addEntity(
								PreventiveAction.class).setParameter("month",
								month).list();
					}

				});
	}

}
