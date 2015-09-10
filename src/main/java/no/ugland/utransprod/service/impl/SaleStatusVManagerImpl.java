package no.ugland.utransprod.service.impl;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.SaleStatusVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.service.SaleStatusVManager;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingSaleStatus;

public class SaleStatusVManagerImpl implements SaleStatusVManager {
    private SaleStatusVDAO dao;

    /**
     * @param aDao
     */
    public final void setSaleStatusVDAO(final SaleStatusVDAO aDao) {
	this.dao = aDao;
    }

    public CheckObject checkExcel(ExcelReportSetting params) {
	// TODO Auto-generated method stub
	return null;
    }

    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
	return dao.findByProbabilitesAndProductArea(((ExcelReportSettingSaleStatus) params).getProbabilities(),
		((ExcelReportSettingSaleStatus) params).getProductArea());
    }

    public String getInfoButtom(ExcelReportSetting params) throws ProTransException {
	// TODO Auto-generated method stub
	return null;
    }

    public String getInfoTop(ExcelReportSetting params) {
	// TODO Auto-generated method stub
	return null;
    }

    public Map<Object, Object> getReportDataMap(ExcelReportSetting params) {
	// TODO Auto-generated method stub
	return null;
    }
}
