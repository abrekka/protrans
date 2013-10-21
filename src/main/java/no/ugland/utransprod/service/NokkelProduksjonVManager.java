package no.ugland.utransprod.service;

import no.ugland.utransprod.model.NokkelProduksjonV;
import no.ugland.utransprod.util.excel.ExcelManager;

/**
 * Interface for serviceklasse mot view NOKKEL_PRODUKSJON_V
 * @author atle.brekka
 */
public interface NokkelProduksjonVManager extends ExcelManager,
        NokkelVManager<NokkelProduksjonV> {
    /**
     * Finner fir gitt uke
     * @param year
     * @param week
     * @return nøkkeltall produksjon
     */
    NokkelProduksjonV findByWeek(Integer year, Integer week);
}
