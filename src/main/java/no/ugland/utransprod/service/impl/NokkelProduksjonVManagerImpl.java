package no.ugland.utransprod.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.NokkelProduksjonVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.NokkelProduksjonV;
import no.ugland.utransprod.service.NokkelProduksjonVManager;
import no.ugland.utransprod.util.YearWeek;
import no.ugland.utransprod.util.excel.ExcelReportEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingOwnProduction;

/**
 * Implementasjon av serviceklasse for view NOKKEL_PRODUKSJON_V.
 * @author atle.brekka
 */
public class NokkelProduksjonVManagerImpl implements NokkelProduksjonVManager {
    private NokkelProduksjonVDAO dao;

    /**
     * @param aDao
     */
    public final void setNokkelProduksjonVDAO(final NokkelProduksjonVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.NokkelProduksjonVManager#findByWeek(java.lang.Integer,
     *      java.lang.Integer)
     */
    public final NokkelProduksjonV findByWeek(final Integer year,
            final Integer week) {
        return dao.findByWeek(year, week);
    }

    /**
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     *      findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final List<NokkelProduksjonV> findByParams(
            final ExcelReportSetting params) {
        if (params.getExcelReportType() == ExcelReportEnum.PRODUCTIVITY_PACK) {
            return dao.findByYearWeekProductArea(params.getYear(), params
                    .getWeekFrom(), params.getWeekTo(), params
                    .getProductAreaName());
        }
        return dao.findByYearWeekProductAreaGroup(params.getYear(), params
                .getWeekFrom(), params.getWeekTo(),
                ((ExcelReportSettingOwnProduction) params)
                        .getProductAreaGroupName());

    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#aggreagateYearWeek(no.ugland.utransprod.util.YearWeek,
     *      java.lang.String)
     */
    public final NokkelProduksjonV aggreagateYearWeek(
            final YearWeek currentYearWeek, final String productArea) {
        return dao.aggreagateYearWeek(currentYearWeek, productArea);
    }

    /**
     * @see no.ugland.utransprod.service.NokkelVManager#
     *      findBetweenYearWeek(no.ugland.utransprod.util.YearWeek,
     *      no.ugland.utransprod.util.YearWeek, java.lang.String)
     */
    public final List<NokkelProduksjonV> findBetweenYearWeek(
            final YearWeek fromYearWeek, final YearWeek toYearWeek,
            final String productArea) {
        return dao.findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);
    }

    /**
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     *      getInfo(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final String getInfoButtom(final ExcelReportSetting params) {
        List<NokkelProduksjonV> prodList = findByParams(params);
        BigDecimal budget = BigDecimal.valueOf(0);
        if (prodList != null) {
            for (NokkelProduksjonV prod : prodList) {
                BigDecimal currentBudget = prod.getBudgetValue();
                if (currentBudget != null) {
                    budget = budget.add(currentBudget);
                }
            }
        }
        budget = budget.divide(BigDecimal.valueOf(5));
        return String.format(
                "For å klare budsjett må det pakkes for %1$.0f hver dag",
                budget);
    }
    public final String getInfoTop(final ExcelReportSetting params) {
        return null;
    }

    /**
     * Gjør ingenting.
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     *      getReportDataMap(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final Map<Object, Object> getReportDataMap(
            final ExcelReportSetting params) {
        return null;
    }

    public CheckObject checkExcel(ExcelReportSetting params) {
        // TODO Auto-generated method stub
        return null;
    }

}
