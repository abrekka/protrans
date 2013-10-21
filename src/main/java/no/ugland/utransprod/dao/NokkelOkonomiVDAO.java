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
	 * Finner n�kkeltall for �konomi for gitt periode
	 * 
	 * @param fromYearWeek
	 * @param toYearWeek
	 * @param productArea
	 * @return n�kkeltall for �konomi
	 */
	List<NokkelOkonomiV> findBetweenYearWeek(YearWeek fromYearWeek,
			YearWeek toYearWeek, String productArea);

	/**
	 * Finner aggregerte tall for �komomi
	 * 
	 * @param currentYearWeek
	 * @param productArea
	 * @return aggregerte tall for �komomi
	 */
	NokkelOkonomiV aggreagateYearWeek(YearWeek currentYearWeek,
			String productArea);
}
