package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.DeviationSumV;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

/**
 * Interface for DAO mot view DEVIATION_SUM_V
 * 
 * @author atle.brekka
 * 
 */
public interface DeviationSumVDAO extends DAO<DeviationSumV> {
	/**
	 * Finner avviksummering basert på kriterier satt
	 * 
	 * @param params
	 * @return avvikssummering
	 */
	List<DeviationSumV> findByParams(ExcelReportSettingDeviation params);
}
