package no.ugland.utransprod.service.impl;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.OrderStatusNotSentVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.StatusOrdersNotSent;
import no.ugland.utransprod.service.OrderStatusNotSentVManager;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

/**
 * Implementasons av swerviceklasse for view ORDER_STATUS_NOT_SENT_V.
 * @author atle.brekka
 */
public class OrderStatusNotSentVManagerImpl implements
        OrderStatusNotSentVManager {
    private OrderStatusNotSentVDAO dao;

    /**
     * @param aDao
     */
    public final void setOrderStatusNotSentVDAO(final OrderStatusNotSentVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     * findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final List<StatusOrdersNotSent> findByParams(final ExcelReportSetting params) {
        return dao.findByParams(params);
    }

    /**
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
