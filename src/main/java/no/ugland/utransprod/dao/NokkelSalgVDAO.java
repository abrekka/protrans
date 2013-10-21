package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.NokkelSalgV;
import no.ugland.utransprod.util.YearWeek;

/**
 * Interface for DAO mot view NOKKEL_SALG_V
 * 
 * @author atle.brekka
 * 
 */
public interface NokkelSalgVDAO extends DAO<NokkelSalgV> {
	/**
	 * Finner nøkkeltall for salg for gitt periode
	 * 
	 * @param year
	 * @param week
	 * @param productArea
	 * @return nøkkeltall for salg for gitt periode
	 */
	List<NokkelSalgV> findByYearWeek(Integer year, Integer week,
			String productArea);

	/**
	 * Finner nøkkeltall for salg for gitt periode
	 * 
	 * @param fromYearWeek
	 * @param toYearWeek
	 * @param productArea
	 * @return nøkkeltall for salg for gitt periode
	 */
	List<NokkelSalgV> findBetweenYearWeek(YearWeek fromYearWeek,
			YearWeek toYearWeek, String productArea);

	/**
	 * Finner aggregerte tall for salg
	 * 
	 * @param currentYearWeek
	 * @param productArea
	 * @return aggregerte tall for salg
	 */
	NokkelSalgV aggreagateYearWeek(YearWeek currentYearWeek, String productArea);
}
