package no.ugland.utransprod.service.impl;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.DeviationSumVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.DeviationSumV;
import no.ugland.utransprod.service.DeviationSumVManager;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

/**
 * Implementasjon av serviceklasse for view DEVIATION_SUM_V.
 * @author atle.brekka
 */
public class DeviationSumVManagerImpl implements DeviationSumVManager {
    private DeviationSumVDAO dao;

    /**
     * @param aDao
     */
    public final void setDeviationSumVDAO(final DeviationSumVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     * findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final List<DeviationSumV> findByParams(final ExcelReportSetting params) {
        return dao.findByParams((ExcelReportSettingDeviation) params);
    }

    /**
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     * getInfo(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final String getInfoButtom(final ExcelReportSetting params) {
        return null;
    }

    /**
     * Gjør ingenting.
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     * getReportDataMap(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final Map<Object, Object> getReportDataMap(final ExcelReportSetting params) {
        return null;
    }

    public String getInfoTop(ExcelReportSetting params) {
        // TODO Auto-generated method stub
        return null;
    }

    public CheckObject checkExcel(ExcelReportSetting params) {
        // TODO Auto-generated method stub
        return null;
    }

}
