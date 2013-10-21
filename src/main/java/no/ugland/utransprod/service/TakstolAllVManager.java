package no.ugland.utransprod.service;

import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.model.TakstolAllV;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.excel.ExcelManager;
import no.ugland.utransprod.util.report.JiggReportData;

public interface TakstolAllVManager extends ExcelManager {

	String MANAGER_NAME = "takstolAllVManager";

	Map<String,Map<String,Set<JiggReportData>>> findJiggReportDataByPeriode(Periode periode);

	List<TakstolAllV> findAllNotProduced();

}
