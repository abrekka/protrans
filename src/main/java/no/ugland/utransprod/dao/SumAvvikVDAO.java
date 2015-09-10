package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.SumAvvikV;

/**
 * Interface for DAO mot view SUM_AVVIK_V
 * 
 * @author atle.brekka
 * 
 */
public interface SumAvvikVDAO extends DAO<SumAvvikV> {
    /**
     * Finner alle for gitt produktomr�de for gitt �r og m�ned
     * 
     * @param year
     * @param month
     * @param productArea
     * @return avvik
     */
    List<SumAvvikV> findByProductAreaYearAndMonth(Integer year, Integer month, String productArea);

    /**
     * Summerer avvik for gitt produktomr�de, �r og m�ned
     * 
     * @param year
     * @param month
     * @param productArea
     * @return avvik
     */
    List<SumAvvikV> findSumByProductAreaYearAndMonth(Integer year, Integer month, String productArea);

    /**
     * Finner alle for gitt produktomr�de, �r og m�ned og skiller p� lukkede og
     * �pne
     * 
     * @param year
     * @param month
     * @param productArea
     * @return avvik
     */
    List<SumAvvikV> findByProductAreaYearAndMonthWithClosed(Integer year, Integer month, String productArea);
}
