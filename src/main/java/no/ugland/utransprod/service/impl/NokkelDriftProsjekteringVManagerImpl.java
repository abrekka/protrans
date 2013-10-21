package no.ugland.utransprod.service.impl;

import java.util.List;

import no.ugland.utransprod.dao.NokkelDriftProsjekteringVDAO;
import no.ugland.utransprod.model.NokkelDriftProsjekteringV;
import no.ugland.utransprod.service.NokkelDriftProsjekteringVManager;
import no.ugland.utransprod.util.YearWeek;

/**
 * Implementasjon av serviceklasse for view NOKKEL_DRIFT_PROSJEKTERING_V.
 * @author atle.brekka
 */
public class NokkelDriftProsjekteringVManagerImpl implements
        NokkelDriftProsjekteringVManager {
    private NokkelDriftProsjekteringVDAO dao;

    /**
     * @param aDao
     */
    public final void setNokkelDriftProsjekteringVDAO(final NokkelDriftProsjekteringVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     * findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
     *      no.ugland.utransprod.util.YearWeek, java.lang.String)
     */
    public final List<NokkelDriftProsjekteringV> findBetweenYearWeek(
            final YearWeek fromYearWeek, final YearWeek toYearWeek, final String productArea) {
        return dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
     *      java.lang.String)
     */
    public final NokkelDriftProsjekteringV aggreagateYearWeek(
            final YearWeek currentYearWeek, final String productArea) {
        return dao.aggreagateYearWeek(currentYearWeek, productArea);
    }

}
