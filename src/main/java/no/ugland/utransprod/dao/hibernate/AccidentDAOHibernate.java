package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.AccidentDAO;
import no.ugland.utransprod.model.Accident;

public class AccidentDAOHibernate extends BaseDAOHibernate<Accident> implements
        AccidentDAO {
    /**
     * Konstruktør
     */
    public AccidentDAOHibernate() {
        super(Accident.class);
    }

    /*public final void lazyLoad(final Accident accident,
            final LazyLoadAccidentEnum[] enums) {
        if (accident != null && accident.getAccidentId() != null) {
            getHibernateTemplate().execute(new HibernateCallback() {

                public Object doInHibernate(final Session session) {
                    session.load(accident, accident.getAccidentId());
                    Set<?> set;

                    for (LazyLoadAccidentEnum lazyEnum : enums) {
                        switch (lazyEnum) {
                        case ACCIDENT_PARTICIPANTS:
                            set = accident.getAccidentParticipants();
                            set.iterator();
                            break;
                        default:
                        }
                    }
                    return null;
                }

            });
        }

    }*/

}
