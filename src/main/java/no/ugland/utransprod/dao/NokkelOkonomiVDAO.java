package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.NokkelOkonomiV;
import no.ugland.utransprod.util.YearWeek;

/**
 * Interface for DAO mot view NOKKEL_OKONOMI
 * 
 * @author atle.brekka
 * 
 */
public interface NokkelOkonomiVDAO extends DAO<NokkelOkonomiV> {
	/**
	 * Finner nøkkeltall for økonomi for gitt periode
	 * 
	 * @param fromYearWeek
	 * @param toYearWeek
	 * @param productArea
	 * @return nøkkeltall for økonomi
	 */
	List<NokkelOkonomiV> findBetweenYearWeek(YearWeek fromYearWeek,
			YearWeek toYearWeek, String productArea);

	/**
	 * Finner aggregerte tall for økomomi
	 * 
	 * @param currentYearWeek
	 * @param productArea
	 * @return aggregerte tall for økomomi
	 */
	NokkelOkonomiV aggreagateYearWeek(YearWeek currentYearWeek,
			String productArea);
}
