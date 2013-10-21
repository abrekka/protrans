package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.FaktureringVDAO;
import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implemntasjon av DAO mot view FAKTURERING_V
 * @author atle.brekka
 */
public class FaktureringVDAOHibernate extends BaseDAOHibernate<FaktureringV>
        implements FaktureringVDAO {

    /**
     * Konstruktør
     */
    public FaktureringVDAOHibernate() {
        super(FaktureringV.class);
    }

    /**
     * @see no.ugland.utransprod.dao.FaktureringVDAO#findAll()
     */
    @SuppressWarnings("unchecked")
    public final List<FaktureringV> findAll() {
        return getHibernateTemplate().find("from FaktureringV order by sent");
    }

    /**
     * @see no.ugland.utransprod.dao.FaktureringVDAO#findByCustomerNr(java.lang.Integer)
     */
    @SuppressWarnings("unchecked")
    public final List<FaktureringV> findByCustomerNr(final Integer customerNr) {
        return (List<FaktureringV>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        return session.createCriteria(FaktureringV.class).add(
                                Restrictions.eq("customerNr", customerNr))
                                .list();
                    }

                });
    }

    /**
     * @see no.ugland.utransprod.dao.FaktureringVDAO#findByOrderNr(java.lang.String)
     */
    @SuppressWarnings("unchecked")
    public List<FaktureringV> findByOrderNr(final String orderNr) {
        return (List<FaktureringV>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(Session session)
                            throws HibernateException {
                        return session.createCriteria(FaktureringV.class).add(
                                Restrictions.eq("orderNr", orderNr)).list();
                    }

                });
    }

    /**
     * @see no.ugland.utransprod.dao.FaktureringVDAO#refresh(no.ugland.utransprod.model.FaktureringV)
     */
    public void refresh(FaktureringV faktureringV) {
        getHibernateTemplate().flush();
        getHibernateTemplate().load(faktureringV, faktureringV.getOrderId());

    }

    /**
     * @see no.ugland.utransprod.dao.FaktureringVDAO#findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    @SuppressWarnings("unchecked")
    public List<FaktureringV> findByParams(final ExcelReportSetting params) {
        return (List) getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(Session session)
                    throws HibernateException {

                return session.createCriteria(FaktureringV.class).add(
                        Restrictions.isNotNull("sent")).add(
                        Restrictions.isNull("invoiceDate")).list();
            }

        });
    }

	@SuppressWarnings("unchecked")
	public List<FaktureringV> findByCustomerNrAndProductAreaGroup(
			final Integer customerNr, final ProductAreaGroup productAreaGroup) {
		return (List<FaktureringV>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        Criteria criteria= session.createCriteria(FaktureringV.class).add(
                                Restrictions.eq("customerNr", customerNr));
                        
                        if(productAreaGroup!=null&&!productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")){
                        	criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
                        }
                         return criteria.list();
                    }

                });
	}

	@SuppressWarnings("unchecked")
	public List<FaktureringV> findByOrderNrAndProductAreaGroup(final String orderNr,
			final ProductAreaGroup productAreaGroup) {
		 return (List<FaktureringV>) getHibernateTemplate().execute(
	                new HibernateCallback() {

	                    public Object doInHibernate(Session session)
	                            throws HibernateException {
	                    	Criteria criteria= session.createCriteria(FaktureringV.class).add(
	                                Restrictions.eq("orderNr", orderNr));
	                    	
	                    	 if(productAreaGroup!=null&&!productAreaGroup.getProductAreaGroupName().equalsIgnoreCase("Alle")){
	                         	criteria.add(Restrictions.eq("productAreaGroupName", productAreaGroup.getProductAreaGroupName()));
	                         }
	                          return criteria.list();
	                    }

	                });
	}

}
