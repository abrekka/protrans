package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.NokkelDriftProsjekteringV;
import no.ugland.utransprod.util.YearWeek;

/**
 * Interface for DAO mot view NOKKEL_DRIFT_PRODJEKTERING
 * 
 * @author atle.brekka
 * 
 */
public interface NokkelDriftProsjekteringVDAO extends
		DAO<NokkelDriftProsjekteringV> {
	/**
	 * Finner tall for nøkkelrapport for drift og prosjektering for en periode
	 * 
	 * @param fromYearWeek
	 * @param toYearWeek
	 * @param productArea
	 * @return nøkkelrapport for drift og prosjektering for en periode
	 */
	List<NokkelDriftProsjekteringV> findBetweenYearWeek(YearWeek fromYearWeek,
			YearWeek toYearWeek, String productArea);

	/**
	 * Aggregerer tall for drift og prosjektering
	 * 
	 * @param currentYearWeek
	 * @param productArea
	 * @return aggregerte tall
	 */
	NokkelDriftProsjekteringV aggreagateYearWeek(YearWeek currentYearWeek,
			String productArea);
}
