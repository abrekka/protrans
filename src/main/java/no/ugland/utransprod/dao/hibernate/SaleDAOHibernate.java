package no.ugland.utransprod.dao.hibernate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import no.ugland.utransprod.dao.SaleDAO;
import no.ugland.utransprod.model.Sale;
import no.ugland.utransprod.util.report.ProbabilityEnum;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class SaleDAOHibernate extends BaseDAOHibernate<Sale> implements SaleDAO {

    public SaleDAOHibernate() {
        super(Sale.class);
    }

    @SuppressWarnings("unchecked")
    public List<Sale> findByProbability(final ProbabilityEnum probabilityEnum) {
        return (List<Sale>) getHibernateTemplate().execute(
                new HibernateCallback() {

                    public Object doInHibernate(final Session session) {
                        try {
							return session.createCriteria(Sale.class).add(
							        Restrictions.eq("probability", probabilityEnum
							                .getProbability())).add(
							        Restrictions.ge("saledate", new SimpleDateFormat("yyyy-MM-dd").parseObject("2009-01-01"))).list();
						} catch (HibernateException e) {
							e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}
                        return null;
                    }

                });
    }

}
