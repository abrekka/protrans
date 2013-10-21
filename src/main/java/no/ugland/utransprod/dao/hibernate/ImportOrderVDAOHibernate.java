package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.dao.ImportOrderVDAO;
import no.ugland.utransprod.model.ImportOrderV;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateCallback;

public class ImportOrderVDAOHibernate extends
        BaseDAOHibernate<ImportOrderV> implements ImportOrderVDAO {
    /**
     * Konstruktør
     */
    public ImportOrderVDAOHibernate() {
        super(ImportOrderV.class);
    }

    public ImportOrderV findByNumber1(final String number1) {
        return (ImportOrderV)getHibernateTemplate().execute(new HibernateCallback() {
        
            @SuppressWarnings("unchecked")
			public Object doInHibernate(final Session session) {
                List<ImportOrderV> list = session.createCriteria(ImportOrderV.class).add(Restrictions.eq("number1", number1)).list();
                if(list!=null&&list.size()==1){
                    return list.get(0);
                }
                return null;
            }
        });
    }

}
