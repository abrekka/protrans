package no.ugland.utransprod.dao.hibernate;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;

import no.ugland.utransprod.dao.DAO;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * This class serves as the Base class for all other DAOs - namely to hold
 * common methods that they might all use. Can be used for standard CRUD
 * operations.
 * </p>
 * <p>
 * <a href="BaseDAOHibernate.java.html"><i>View Source</i></a>
 * </p>
 * @author <a href="mailto:matt@raibledesigns.com">Matt Raible</a>
 * @param <E>
 */
public class BaseDAOHibernate<E> extends HibernateDaoSupport implements DAO<E> {
    Class<?> clazz;
    private static final String UNKNOWN="UNKNOWN";

    /**
     * @param aClass
     */
    public BaseDAOHibernate(final Class<?> aClass) {
        clazz = aClass;
    }

    /**
     * @param object
     * @see no.ugland.utransprod.dao.DAO#saveObject(java.lang.Object)
     */
    public final void saveObject(final E object) {
        getHibernateTemplate().saveOrUpdate(object);
        getHibernateTemplate().flush();
    }

    /**
     * @see no.ugland.utransprod.dao.DAO#getObject(java.io.Serializable)
     */
    @SuppressWarnings("unchecked")
    public final E getObject(final Serializable classId) {

        return (E) getHibernateTemplate().get(clazz, classId);

    }

    /**
     * @see no.ugland.utransprod.dao.DAO#getObjects()
     */
    @SuppressWarnings("unchecked")
    public final List<E> getObjects() {
        return getHibernateTemplate().loadAll(clazz);
    }

    /**
     * @see no.ugland.utransprod.dao.DAO#removeObject(java.io.Serializable)
     */
    public final void removeObject(final Serializable classId) {
        getHibernateTemplate().delete(getObject(classId));
        getHibernateTemplate().flush();
    }

    /**
     * Kanselerere oppdateringer
     * @param objects
     */
    public final void cancelObjectUpdates(final List<E> objects) {
        if (objects == null) {
            return;
        }

        for (E object : objects) {
            getHibernateTemplate().evict(object);
        }
    }

    /**
     * Finner objekter basert på eksempel
     * @param example
     * @return objekter
     */
    @SuppressWarnings("unchecked")
    public final List<E> find(E example) {
        return getHibernateTemplate().findByExample(example);
    }

    /**
     * @param example
     * @return objekter
     * @see no.ugland.utransprod.dao.DAO#findByExample(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public final List<E> findByExample(final E example) {
        return getHibernateTemplate().findByExample(example);
    }

    /**
     * @param example
     * @return objekter
     * @see no.ugland.utransprod.dao.DAO#findByExampleLike(java.lang.Object)
     */
    @SuppressWarnings("unchecked")
    public final List<E> findByExampleLike(final E example) {
        Example exam = Example.create(example);
        exam.enableLike(MatchMode.ANYWHERE); // this is it.
        exam.ignoreCase();
        DetachedCriteria detachedCriteria = DetachedCriteria.forClass(clazz).add(exam);
        
        detachedCriteria = getCriteria(example, detachedCriteria);

        // DetachedCriteria c = DetachedCriteria.forClass(clazz).add(exam);

        return getHibernateTemplate().findByCriteria(detachedCriteria);
    }

    private DetachedCriteria getCriteria(final Object example, DetachedCriteria detachedCriteria) {
        Example exam = Example.create(example);
        exam.enableLike(MatchMode.ANYWHERE); // this is it.
        exam.ignoreCase();
        
        


        try {
            Field[] fields = example.getClass().getDeclaredFields();

            for (int i = 0; i < fields.length; i++) {
                Field field = fields[i];
                if(field.getType().getCanonicalName().indexOf(
                "java.util.Date") >= 0){
                    Method method = example.getClass().getMethod(
                            "get" + StringUtils.capitalize(field.getName()),
                            (Class[]) null);
                    if (method.invoke(example, (Object[]) null) != null) {
                        String dateSql = "convert(datetime,convert(varchar,"+Util.getDatabaseField(field.getName())+",101)) = convert(datetime,?,101)";
                        detachedCriteria.add(Restrictions.sqlRestriction(dateSql, method.invoke(example, (Object[]) null), Hibernate.DATE));
                        
                        Method setMethod = example.getClass().getMethod(
                                "set" + StringUtils.capitalize(field.getName()),
                                new Class[]{Date.class});
                        setMethod.invoke(example, new Object[]{null});
                    }
                }
                if (field.getType().getCanonicalName().indexOf(
                        "no.ugland.utransprod.model") >= 0) {
                    if(!field.getName().equalsIgnoreCase(UNKNOWN)){
                    Method method = example.getClass().getMethod(
                            "get" + StringUtils.capitalize(field.getName()),
                            (Class[]) null);
                    if (method.invoke(example, (Object[]) null) != null) {
                        Object object = method.invoke(example,
                                (Object[]) null);
                        DetachedCriteria childCriteria = detachedCriteria.createCriteria(field.getName()).add(
                                Example.create(object));
                        getCriteria(object, childCriteria);
                    }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return detachedCriteria;
    }

    
    
   

    /**
     * @see no.ugland.utransprod.dao.DAO#getObjects(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<E> getObjects(final String orderBy) {
        return (List<E>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        return session.createCriteria(clazz).addOrder(
                                Order.asc(orderBy)).list();
                    }

                });
    }

    public void removeAll() {
        getHibernateTemplate().bulkUpdate("delete from " + clazz.getName());
    }

    public void refreshObject(Object object, Serializable id) {
        getHibernateTemplate().load(object, id);
        getHibernateTemplate().flush();

    }
    
    public void lazyLoad(final Object object,final Serializable id,final LazyLoadEnum[][] enums){
        if (object != null && id != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(Session session)
                        throws HibernateException {
                    if(!session.contains(object)){
                        session.load(object, id);
                    }

                    for (LazyLoadEnum[] lazyEnum : enums) {
                        lazyEnum[0].lazyLoad(object, lazyEnum[1]);
                    }
                    return null;
                }

            });

        }

    }
    
    
}
