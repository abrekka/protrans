package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.NokkelOkonomiVDAO;
import no.ugland.utransprod.model.NokkelOkonomiV;
import no.ugland.utransprod.service.NokkelOkonomiVManager;
import no.ugland.utransprod.util.YearWeek;

/**
 * Implementasjon av serviceklasse for view NOKKEL_OKONOMI_V.
 * @author atle.brekka
 */
public class NokkelOkonomiVManagerImpl implements NokkelOkonomiVManager {
    private NokkelOkonomiVDAO dao;

    /**
     * @param aDao
     */
    public final void setNokkelOkonomiVDAO(final NokkelOkonomiVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     * findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
     *      no.ugland.utransprod.util.YearWeek, java.lang.String)
     */
    public final List<NokkelOkonomiV> findBetweenYearWeek(final YearWeek fromYearWeek,
            final YearWeek toYearWeek, final String productArea) {
        return dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     * aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
     *      java.lang.String)
     */
    public final NokkelOkonomiV aggreagateYearWeek(final YearWeek currentYearWeek,
            final String productArea) {
        return dao.aggreagateYearWeek(currentYearWeek, productArea);
    }

}
