package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.AttributeChoiceDAO;
import no.ugland.utransprod.model.AttributeChoice;

public class AttributeChoiceDAOHibernate extends BaseDAOHibernate<AttributeChoice>
        implements AttributeChoiceDAO {
    /**
     * 
     */
    public AttributeChoiceDAOHibernate() {
        super(AttributeChoice.class);
    }

}
