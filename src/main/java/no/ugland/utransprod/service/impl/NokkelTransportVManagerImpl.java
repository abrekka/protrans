package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.NokkelTransportVDAO;
import no.ugland.utransprod.model.NokkelTransportV;
import no.ugland.utransprod.service.NokkelTransportVManager;
import no.ugland.utransprod.util.YearWeek;

/**
 * Implementasjon av serviceklasse for view NOKKEL_TRANSPORT_V.
 * @author atle.brekka
 */
public class NokkelTransportVManagerImpl implements NokkelTransportVManager {
    private NokkelTransportVDAO dao;

    /**
     * @param aDao
     */
    public final void setNokkelTransportVDAO(final NokkelTransportVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     * findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
     *      no.ugland.utransprod.util.YearWeek, java.lang.String)
     */
    public final List<NokkelTransportV> findBetweenYearWeek(final YearWeek fromYearWeek,
            final YearWeek toYearWeek, final String productArea) {
        return dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     * aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
     *      java.lang.String)
     */
    public final NokkelTransportV aggreagateYearWeek(final YearWeek currentYearWeek,
            final String productArea) {
        return dao.aggreagateYearWeek(currentYearWeek, productArea);
    }

}
