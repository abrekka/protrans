package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.CommentTypeDAO;
import no.ugland.utransprod.model.CommentType;

public class CommentTypeDAOHibernate extends
        BaseDAOHibernate<CommentType> implements CommentTypeDAO {
    /**
     * Konstruktør
     */
    public CommentTypeDAOHibernate() {
        super(CommentType.class);
    }

}
