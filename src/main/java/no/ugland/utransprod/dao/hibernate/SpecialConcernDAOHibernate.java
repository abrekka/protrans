package no.ugland.utransprod.dao.hibernate;

import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.SpecialConcernDAO;
import no.ugland.utransprod.model.SpecialConcern;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;

/**
 * Implementasjon av DAO mot SPECIAL_CONCERN for hibernate
 * @author atle.brekka
 */
public class SpecialConcernDAOHibernate extends
        BaseDAOHibernate<SpecialConcern> implements SpecialConcernDAO {
    public SpecialConcernDAOHibernate() {
        super(SpecialConcern.class);
    }

    /**
     * @see no.ugland.utransprod.dao.SpecialConcernDAO#findByDescription(java.lang.String)
     */
    public final SpecialConcern findByDescription(final String description)
            throws ProTransException {
        SpecialConcern specialConcern = new SpecialConcern();
        specialConcern.setDescription(description);
        List<SpecialConcern> list = find(specialConcern);
        if (list == null || list.size() != 1) {
            throw new ProTransException("Kunne ikke finne spesielt hensyn for "
                    + description);
        }
        return list.get(0);
    }

    @Override
    public final void removeAll() {
        getHibernateTemplate().execute(new HibernateCallback() {

            public Object doInHibernate(final Session session) {
                session.createQuery("delete from SpecialConcern")
                        .executeUpdate();

                return null;
            }

        });

    }
}
