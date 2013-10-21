package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.NokkelMonteringVDAO;
import no.ugland.utransprod.model.NokkelMonteringV;
import no.ugland.utransprod.service.NokkelMonteringVManager;
import no.ugland.utransprod.util.YearWeek;

/**
 * Implementasjon av serviceklasse for view NOKKEL_MONTERING_V.
 * @author atle.brekka
 */
public class NokkelMonteringVManagerImpl implements NokkelMonteringVManager {
    private NokkelMonteringVDAO dao;

    /**
     * @param aDao
     */
    public final void setNokkelMonteringVDAO(final NokkelMonteringVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     * findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
     *      no.ugland.utransprod.util.YearWeek, java.lang.String)
     */
    public final List<NokkelMonteringV> findBetweenYearWeek(final YearWeek fromYearWeek,
            final YearWeek toYearWeek, final String productArea) {
        return dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     * aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
     *      java.lang.String)
     */
    public final NokkelMonteringV aggreagateYearWeek(final YearWeek currentYearWeek,
            final String productArea) {
        return dao.aggreagateYearWeek(currentYearWeek, productArea);
    }

}
