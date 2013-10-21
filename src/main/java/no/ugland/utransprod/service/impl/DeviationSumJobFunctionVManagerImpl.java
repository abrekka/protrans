package no.ugland.utransprod.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.DeviationSumJobFunctionVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.DeviationSumJobFunctionV;
import no.ugland.utransprod.service.DeviationSumJobFunctionVManager;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

public class DeviationSumJobFunctionVManagerImpl implements
		DeviationSumJobFunctionVManager {
	private DeviationSumJobFunctionVDAO dao;

	public final void setDeviationSumJobFunctionVDAO(
			final DeviationSumJobFunctionVDAO aDao) {
		this.dao = aDao;
	}

	public CheckObject checkExcel(ExcelReportSetting params) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<?> findByParams(ExcelReportSetting params)
			throws ProTransException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInfoButtom(ExcelReportSetting params)
			throws ProTransException {
		// TODO Auto-generated method stub
		return null;
	}

	public String getInfoTop(ExcelReportSetting params) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
		List<DeviationSumJobFunctionV> deviations = dao
				.findByYearAndDeviationFunctionAndProductAreaGroup(params
						.getYear(), ((ExcelReportSettingDeviation) params)
						.getDeviationFunction(),((ExcelReportSettingDeviation) params).getProductAreaGroup());

		Set<String> functionCategories = new HashSet<String>();
		Map<String, DeviationSumJobFunctionV> reportData = new Hashtable<String, DeviationSumJobFunctionV>();
		for (DeviationSumJobFunctionV deviation : deviations) {
			functionCategories.add(deviation.getFunctionCategoryName());
			reportData.put("" + deviation.getRegistrationYear() + "_"
					+ deviation.getRegistrationWeek() + "_"
					+ deviation.getFunctionCategoryName(), deviation);
		}
		Map<Object, Object> reportMap = new HashMap<Object, Object>();
		reportMap.put("FunctionCategory", functionCategories);
		reportMap.put("ReportData", reportData);
		return reportMap;
	}

}
