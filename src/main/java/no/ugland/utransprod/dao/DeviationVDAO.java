package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.DeviationV;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

public interface DeviationVDAO extends DAO<DeviationV> {
    /**
     * Finner avviksummering basert på kriterier satt
     * 
     * @param params
     * @return avvikssummering
     */
    List<DeviationV> findByParams(ExcelReportSettingDeviation params);

}
