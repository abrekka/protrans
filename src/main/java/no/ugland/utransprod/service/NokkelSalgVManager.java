package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.NokkelSalgV;

/**
 * Interface for serviceklasse mot view NOKKEL_SALG_V
 * @author atle.brekka
 */
public interface NokkelSalgVManager extends NokkelVManager<NokkelSalgV> {
    /**
     * Finner for gitt uke og produktområde
     * @param year
     * @param week
     * @param productArea
     * @return nøkkeltall salg
     */
    List<NokkelSalgV> findByYearWeek(Integer year, Integer week,
            String productArea);

}
