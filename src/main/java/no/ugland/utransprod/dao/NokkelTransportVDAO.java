package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.NokkelTransportV;
import no.ugland.utransprod.util.YearWeek;

/**
 * Interface forDAO mot view NOKKEL_TRANSPORT_V
 * 
 * @author atle.brekka
 * 
 */
public interface NokkelTransportVDAO extends DAO<NokkelTransportV> {
	/**
	 * Finner nøkkeltall for transport for gitt periode
	 * 
	 * @param fromYearWeek
	 * @param toYearWeek
	 * @param productArea
	 * @return nøkkeltall for transport for gitt periode
	 */
	List<NokkelTransportV> findBetweenYearWeek(YearWeek fromYearWeek,
			YearWeek toYearWeek, String productArea);

	/**
	 * Finner aggregerte tall for transport
	 * 
	 * @param currentYearWeek
	 * @param productArea
	 * @return aggregerte tall for transport
	 */
	NokkelTransportV aggreagateYearWeek(YearWeek currentYearWeek,
			String productArea);
}
