package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.CountyDAO;
import no.ugland.utransprod.model.County;

public class CountyDAOHibernate extends
        BaseDAOHibernate<County> implements CountyDAO {
    /**
     * Konstruktør
     */
    public CountyDAOHibernate() {
        super(County.class);
    }


}
