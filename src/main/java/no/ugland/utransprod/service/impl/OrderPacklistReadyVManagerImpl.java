package no.ugland.utransprod.service.impl;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.OrderPacklistReadyVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.OrderPacklistReadyV;
import no.ugland.utransprod.service.OrderPacklistReadyVManager;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

/**
 * Implementasjon av manager for view ORDER_PACKLIST_READY_V.
 * @author atle.brekka
 */
public class OrderPacklistReadyVManagerImpl implements
        OrderPacklistReadyVManager {
    private OrderPacklistReadyVDAO dao;

    /**
     * @param aDao
     */
    public final void setOrderPacklistReadyVDAO(final OrderPacklistReadyVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     * findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final List<OrderPacklistReadyV> findByParams(final ExcelReportSetting params) {
        return dao.findByParams(params);
    }

    /**
     * Gj�r ingenting.
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
     * Gj�r ingenting.
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
