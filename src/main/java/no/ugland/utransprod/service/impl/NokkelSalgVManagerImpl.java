package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.NokkelSalgVDAO;
import no.ugland.utransprod.model.NokkelSalgV;
import no.ugland.utransprod.service.NokkelSalgVManager;
import no.ugland.utransprod.util.YearWeek;

/**
 * Implementasjon av serviceklasse for view NOKKEL_SALG_V.
 * @author atle.brekka
 */
public class NokkelSalgVManagerImpl implements NokkelSalgVManager {
    private NokkelSalgVDAO dao;

    /**
     * @param aDao
     */
    public final void setNokkelSalgVDAO(final NokkelSalgVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.NokkelSalgVManager#findByYearWeek(java.lang.Integer,
     *      java.lang.Integer, java.lang.String)
     */
    public final List<NokkelSalgV> findByYearWeek(final Integer year, final Integer week,
            final String productArea) {
        return dao.findByYearWeek(year, week, productArea);
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     * findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
     *      no.ugland.utransprod.util.YearWeek, java.lang.String)
     */
    public final List<NokkelSalgV> findBetweenYearWeek(final YearWeek fromYearWeek,
            final YearWeek toYearWeek, final String productArea) {
        return dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     * aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
     *      java.lang.String)
     */
    public final NokkelSalgV aggreagateYearWeek(final YearWeek currentYearWeek,
            final String productArea) {
        return dao.aggreagateYearWeek(currentYearWeek, productArea);
    }

}
