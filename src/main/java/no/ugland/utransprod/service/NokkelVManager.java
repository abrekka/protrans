package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.util.YearWeek;

/**
 * Interface for serviceklasser som bruker fir n�kkeltall
 * @author atle.brekka
 * @param <T>
 */
public interface NokkelVManager<T> {
    /**
     * Finner for periode
     * @param fromYearWeek
     * @param toYearWeek
     * @param productArea
     * @return n�kkeltall
     */
    List<T> findBetweenYearWeek(YearWeek fromYearWeek, YearWeek toYearWeek,
            String productArea);

    /**
     * Aggregerer for gitt uke
     * @param currentYearWeek
     * @param productArea
     * @return aggregerte n�kkeltall
     */
    T aggreagateYearWeek(YearWeek currentYearWeek, String productArea);
}
