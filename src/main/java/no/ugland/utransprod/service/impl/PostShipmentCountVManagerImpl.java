package no.ugland.utransprod.service.impl;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.PostShipmentCountVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.PostShipmentCountSum;
import no.ugland.utransprod.service.PostShipmentCountVManager;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

/**
 * Implementasjon av manager for view POST_SHIPMENT_COUNT_V.
 * @author atle.brekka
 */
public class PostShipmentCountVManagerImpl implements PostShipmentCountVManager {
    private PostShipmentCountVDAO dao;

    /**
     * @param aDao
     */
    public final void setPostShipmentCountVDAO(final PostShipmentCountVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     * findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final List<PostShipmentCountSum> findByParams(final ExcelReportSetting params) {
        return dao.findByParams(params);
    }

    /**
     * Gjør ingenting.
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     * getInfo(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final String getInfoButtom(final ExcelReportSetting params) {
        return null;
    }
    public final String getInfoTop(final ExcelReportSetting params) {
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

    public CheckObject checkExcel(ExcelReportSetting params) {
        // TODO Auto-generated method stub
        return null;
    }

}
