package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.NokkelProduksjonV;
import no.ugland.utransprod.util.YearWeek;

/**
 * Interface for DAO mot view NOKKEL_PRODUKSJON_V.
 * 
 * @author atle.brekka
 */
public interface NokkelProduksjonVDAO extends DAO<NokkelProduksjonV> {
    /**
     * Finner for gitt uke.
     * 
     * @param year
     * @param week
     * @return n�kkeltall for produksjon
     */
    NokkelProduksjonV findByWeek(Integer year, Integer week);

    /**
     * Finner n�kkeltall for gitte excelparametre.
     * 
     * @param year
     * @param weekFrom
     * @param weekTo
     * @param productAreaName
     * @return n�kkeltall
     */
    List<NokkelProduksjonV> findByYearWeekProductArea(Integer year, Integer weekFrom, Integer weekTo, String productAreaName);

    /**
     * Finner n�kkeltall for produksjon for gitt periode.
     * 
     * @param fromYearWeek
     * @param toYearWeek
     * @param productArea
     * @return n�kkeltall for produksjon
     */
    List<NokkelProduksjonV> findBetweenYearWeek(YearWeek fromYearWeek, YearWeek toYearWeek, String productArea);

    /**
     * Finner aggregerte tall for produksjon.
     * 
     * @param currentYearWeek
     * @param productArea
     * @return aggregerte tall for produksjon
     */
    NokkelProduksjonV aggreagateYearWeek(YearWeek currentYearWeek, String productArea);

    List<NokkelProduksjonV> findByYearWeekProductAreaGroup(Integer year, Integer weekFrom, Integer weekTo, String productAreaGroupName);
}
