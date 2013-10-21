package no.ugland.utransprod.service.impl;

import no.ugland.utransprod.dao.TransportSumVDAO;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.TransportSumV;
import no.ugland.utransprod.service.TransportSumVManager;

/**
 * Implementasjon av serviceklasse for view TRANSPORT_SUM_V.
 * @author atle.brekka
 */
public class TransportSumVManagerImpl implements TransportSumVManager {
    private TransportSumVDAO dao;

    /**
     * @param aDao
     */
    public final void setTransportSumVDAO(final TransportSumVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.TransportSumVManager#findYearAndWeek(java.lang.Integer,
     *      java.lang.Integer)
     */
    public final TransportSumV findYearAndWeek(final Integer currentYear, final Integer currentWeek) {
        return dao.findYearAndWeek(currentYear, currentWeek);
    }

    /**
     * @see no.ugland.utransprod.service.TransportSumVManager#
     *      findYearAndWeekByProductAreaGroup(java.lang.Integer,
     *      java.lang.Integer, no.ugland.utransprod.model.ProductAreaGroup)
     */
    public final TransportSumV findYearAndWeekByProductAreaGroup(final Integer currentYear,
            final Integer currentWeek, final ProductAreaGroup productAreaGroup) {
        return productAreaGroup.getProductAreaGroupName().length() == 0 ? findYearAndWeek(currentYear,
                currentWeek) : dao.findYearAndWeekByProductAreaGroup(currentYear, currentWeek,
                productAreaGroup);
    }

}
