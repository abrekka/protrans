package no.ugland.utransprod.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import no.ugland.utransprod.dao.SumOrderReadyVDAO;
import no.ugland.utransprod.model.SumOrderReadyV;
import no.ugland.utransprod.service.SumOrderReadyVManager;

/**
 * Implementasjon av serviceklasse for view SUM_ORDER_READY_V.
 * 
 * @author atle.brekka
 */
public class SumOrderReadyVManagerImpl implements SumOrderReadyVManager {
    private SumOrderReadyVDAO dao;

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy.MM.dd");

    public final void setSumOrderReadyVDAO(final SumOrderReadyVDAO aDao) {
	this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.SumOrderReadyVManager#findByDate(java.util.Date)
     */
    public final SumOrderReadyV findByDate(final Date date) {
	return dao.findByDate(DATE_FORMAT.format(date));
    }

    /**
     * @see no.ugland.utransprod.service.SumOrderReadyVManager#findSumByWeek(java.lang.Integer,
     *      java.lang.Integer)
     */
    public final SumOrderReadyV findSumByWeek(final Integer year, final Integer week) {
	return dao.findSumByWeek(year, week);
    }

    /**
     * @see no.ugland.utransprod.service.SumOrderReadyVManager#
     *      findByDateAndProductAreaGroupName(java.util.Date, java.lang.String)
     */
    public final SumOrderReadyV findByDateAndProductAreaGroupName(final Date date, final String productAreaGroupName) {
	if (productAreaGroupName == null) {
	    return findByDate(date);
	}
	return dao.findByDateAndProductAreaGroupName(DATE_FORMAT.format(date), productAreaGroupName);
    }

    /**
     * @see no.ugland.utransprod.service.SumOrderReadyVManager#
     *      findSumByWeekAndProductAreaGroupName(java.lang.Integer,
     *      java.lang.Integer, java.lang.String)
     */
    public final SumOrderReadyV findSumByWeekAndProductAreaGroupName(final Integer year, final Integer week, final String productAreaGroupName) {
	if (productAreaGroupName == null) {
	    return findSumByWeek(year, week);
	}
	return dao.findSumByWeekAndProductAreaGroupName(year, week, productAreaGroupName);
    }

}
