package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.NokkelMonteringV;
import no.ugland.utransprod.util.YearWeek;

/**
 * Interface for DAO mot view NOKKEL_MONTERING
 * 
 * @author atle.brekka
 * 
 */
public interface NokkelMonteringVDAO extends DAO<NokkelMonteringV> {
	/**
	 * Finner nøkkeltall for montering i git periode
	 * 
	 * @param fromYearWeek
	 * @param toYearWeek
	 * @param productArea
	 * @return nøkkeltall for montering
	 */
	List<NokkelMonteringV> findBetweenYearWeek(YearWeek fromYearWeek,
			YearWeek toYearWeek, String productArea);

	/**
	 * Aggregerte tall for montering
	 * 
	 * @param currentYearWeek
	 * @param productArea
	 * @return aggregerte tall for montering
	 */
	NokkelMonteringV aggreagateYearWeek(YearWeek currentYearWeek,
			String productArea);
}
