package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.OwnProductionV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

/**
 * Interface for DAO mot view OWN_PRODUCTION_V
 * 
 * @author atle.brekka
 * 
 */
public interface OwnProductionVDAO extends DAO<OwnProductionV> {
    /**
     * Finner egenproduksjon basert på parametre
     * 
     * @param params
     * @return info om egenproduksjon
     */
    List<OwnProductionV> findByParams(ExcelReportSetting params);

    List<OwnProductionV> findPacklistReady(String productAreaGroupName);

    List<OwnProductionV> findPacklistNotReady(String productAreaName, String productAreaGroupName);
}
