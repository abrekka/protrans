package no.ugland.utransprod.dao.hibernate;

import no.ugland.utransprod.dao.OrdchgrHeadVDAO;
import no.ugland.utransprod.model.OrdchgrHeadV;

public class OrdchgrHeadVDAOHibernate extends BaseDAOHibernate<OrdchgrHeadV>
        implements OrdchgrHeadVDAO {
    /**
     * KonstruktÝr
     */
    public OrdchgrHeadVDAOHibernate() {
        super(OrdchgrHeadV.class);
    }

}
