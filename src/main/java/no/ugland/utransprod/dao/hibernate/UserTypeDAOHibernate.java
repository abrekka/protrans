package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.UserTypeDAO;
import no.ugland.utransprod.model.UserType;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot tabell USER_TYPE for hibernate
 * 
 * @author atle.brekka
 * 
 */
public class UserTypeDAOHibernate extends BaseDAOHibernate<UserType> implements
		UserTypeDAO {

	/**
	 * Konstruktør
	 */
	public UserTypeDAOHibernate() {
		super(UserType.class);
	}

	/**
	 * @see no.ugland.utransprod.dao.UserTypeDAO#lazyLoad(no.ugland.utransprod.model.UserType,
	 *      no.ugland.utransprod.service.enums.LazyLoadUserTypeEnum[])
	 */
	/*public void lazyLoad(final UserType userType,
			final LazyLoadUserTypeEnum[] enums) {
		if (userType != null && userType.getUserTypeId() != null) {
			getHibernateTemplate().execute(new HibernateCallback() {

				public Object doInHibernate(Session session)
						throws HibernateException {
					session.load(userType, userType.getUserTypeId());

					Set<?> set;

					for (LazyLoadUserTypeEnum lazyEnum : enums) {
						switch (lazyEnum) {
						case USER_TYPE_ACCESS:
							set = userType.getUserTypeAccesses();
							set.iterator();
							break;
						}
					}
					return null;
				}

			});
		}

	}*/

	/**
	 * @see no.ugland.utransprod.dao.UserTypeDAO#getNumberOfUsers(no.ugland.utransprod.model.UserType)
	 */
	public int getNumberOfUsers(final UserType userType) {

		return (Integer) getHibernateTemplate().execute(
				new HibernateCallback() {

					public Object doInHibernate(Session session)
							throws HibernateException {
						Query query = session
								.createQuery("select count(*) from ApplicationUser applicationUser where exists(select 1 from UserRole userRole  where userRole.applicationUser=applicationUser and userRole.userType=:userType)");
						query.setParameter("userType", userType);
						return ((Integer) query.iterate().next()).intValue();

					}

				});
	}

	/**
	 * @see no.ugland.utransprod.dao.UserTypeDAO#refresh(no.ugland.utransprod.model.UserType)
	 */
	public void refresh(UserType userType) {
		getHibernateTemplate().load(userType, userType.getUserTypeId());
		getHibernateTemplate().flush();

	}

}
