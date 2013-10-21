package no.ugland.utransprod.dao.hibernate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.dao.ApplicationUserDAO;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.util.ApplicationParamUtil;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot APPLICATION_USER for hobermate
 * @author atle.brekka
 */
public class ApplicationUserDAOHibernate extends BaseDAOHibernate<ApplicationUser> implements
        ApplicationUserDAO {
    /**
     * Konstruktør
     */
    public ApplicationUserDAOHibernate() {
        super(ApplicationUser.class);
    }

    /**
     * @see no.ugland.utransprod.dao.ApplicationUserDAO#findAllNotGroup()
     */
    @SuppressWarnings("unchecked")
    public final List<ApplicationUser> findAllNotGroup() {
        return (List<ApplicationUser>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                return session.createCriteria(ApplicationUser.class).add(Restrictions.eq("groupUser", "Nei")).addOrder(Order.asc("lastName"))
                        .list();
            }

        });
    }

    public final void refreshObject(final ApplicationUser applicationUser) {
        getHibernateTemplate().load(applicationUser, applicationUser.getUserId());

    }

    /**
     * @see no.ugland.utransprod.dao.ApplicationUserDAO#findByUserNameAndPassword(java.lang.String,
     *      java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public final List<ApplicationUser> findByUserNameAndPassword(final String userName, final String password) {
        return (List<ApplicationUser>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                Criteria crit = session.createCriteria(ApplicationUser.class).add(
                        Restrictions.ilike("userName", userName));

                if (password != null) {
                    crit.add(Restrictions.eq("password", password));
                }
                return crit.list();
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.ApplicationUserDAO#findAllPackers()
     */
    @SuppressWarnings("unchecked")
    public List<String> findAllPackers(final ProductAreaGroup productAreaGroup) {
        return (List<String>) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session) throws HibernateException {
                Criteria criteria = session.createCriteria(ApplicationUser.class).add(
                        Restrictions.eq("groupUser", "Nei"));

                ProductAreaGroupSelector selector = ProductAreaGroupSelector.valueOf(StringUtils
                        .upperCase(productAreaGroup.getProductAreaGroupName()));
                selector.setRestriction(criteria, productAreaGroup);

                criteria = criteria.createCriteria("userRoles").createCriteria("userType").add(
                        Restrictions.eq("description", ApplicationParamUtil
                                .findParamByName("user_type_package")));

                List<ApplicationUser> users = criteria.list();

                List<String> initialsList = new ArrayList<String>();
                for (ApplicationUser user : users) {
                    initialsList.add(user.getUserName());
                }

                return initialsList;
            }

        });
    }

    /**
     * @see no.ugland.utransprod.dao.ApplicationUserDAO#lazyLoad(no.ugland.utransprod.model.ApplicationUser,
     *      no.ugland.utransprod.service.enums.LazyLoadApplicationUserEnum[])
     */
    /*public void lazyLoad(final ApplicationUser applicationUser, final LazyLoadApplicationUserEnum[] enums) {
        if (applicationUser != null && applicationUser.getUserId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session) throws HibernateException {
                    if (!session.contains(applicationUser)) {
                        session.load(applicationUser, applicationUser.getUserId());
                    }

                    Set<?> set;

                    for (LazyLoadApplicationUserEnum lazyEnum : enums) {
                        switch (lazyEnum) {
                        case USER_ROLE:
                            set = applicationUser.getUserRoles();
                            if (set != null) {
                                set.iterator();
                            }
                            break;
                        case USER_PRODUCT_AREA_GROUP:
                            set = applicationUser.getUserProductAreaGroups();
                            if (set != null) {
                                set.iterator();
                            }
                            break;
                        }
                    }
                    return null;
                }

            });

        }

    }*/

    private enum ProductAreaGroupSelector {
        GARASJE {
            @Override
            public void setRestriction(Criteria criteria, ProductAreaGroup productAreaGroup) {
                setRestrictionForProductAreaGroup(criteria, productAreaGroup);

            }
        },
        BYGGELEMENT {
            @Override
            public void setRestriction(Criteria criteria, ProductAreaGroup productAreaGroup) {
                setRestrictionForProductAreaGroup(criteria, productAreaGroup);

            }
        },
        TAKSTOL {
            @Override
            public void setRestriction(Criteria criteria, ProductAreaGroup productAreaGroup) {
                setRestrictionForProductAreaGroup(criteria, productAreaGroup);

            }
        },
        ALLE {
            @Override
            public void setRestriction(Criteria criteria, ProductAreaGroup productAreaGroup) {
            }
        };

        public abstract void setRestriction(Criteria criteria, ProductAreaGroup productAreaGroup);

        private static void setRestrictionForProductAreaGroup(
                Criteria criteria, ProductAreaGroup productAreaGroup) {
            criteria.createAlias("productArea", "area").add(
                    Restrictions.eq("area.productAreaGroup", productAreaGroup));

        }
    }

	public ApplicationUser findByFullName(final String fullName) {
		return (ApplicationUser)getHibernateTemplate().execute(new HibernateCallback() {
			
			public Object doInHibernate(Session session){
				String sql = "select applicationUser " +
						"       from ApplicationUser applicationUser " +
						"       where applicationUser.firstName||' '||applicationUser.lastName=:fullName " +
						"       order by applicationUser.userName";
				List<ApplicationUser> list = session.createQuery(sql).setParameter("fullName", fullName).list();
				return list!=null&&list.size()>0?list.get(0):ApplicationUser.UNKNOWN;
			}
		});
	}

}
