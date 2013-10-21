package no.ugland.utransprod.service.impl;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.PreventiveActionDAO;
import no.ugland.utransprod.dao.SumAvvikVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.PreventiveAction;
import no.ugland.utransprod.model.SumAvvikV;
import no.ugland.utransprod.service.SumAvvikVManager;
import no.ugland.utransprod.util.MonthEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

/**
 * Implementasjon av serviceklasse for view SUM_AVVIK_V.
 * @author atle.brekka
 */
public class SumAvvikVManagerImpl implements SumAvvikVManager {
    private SumAvvikVDAO dao;

    private PreventiveActionDAO preventiveActionDAO;

    /**
     * @param aDao
     */
    public final void setSumAvvikVDAO(final SumAvvikVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @param aDao
     */
    public final void setPreventiveActionDAO(final PreventiveActionDAO aDao) {
        this.preventiveActionDAO = aDao;
    }

    /**
     * Gjør ingenting.
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     *      findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final List<SumAvvikV> findByParams(final ExcelReportSetting params) {
        return null;
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
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     *      getReportDataMap(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final Map<Object, Object> getReportDataMap(
            final ExcelReportSetting params) {
        ExcelReportSettingDeviation paramsDeviation = (ExcelReportSettingDeviation) params;
        Map<Object, Object> map = new Hashtable<Object, Object>();
        // Avvik gjeldende periode

        setDeviationForPeriode(paramsDeviation.getYear(), paramsDeviation
                .getMonthEnum().getMonth(), paramsDeviation.getProductArea()
                .getProductArea(), map,"Period");

        // Avvik totalt hittil
        setDeviationTotal(paramsDeviation, map);

        // Avvik i fjor samme periode
        setDeviationForPeriode(paramsDeviation.getYear() - 1, paramsDeviation
                .getMonthEnum().getMonth(), paramsDeviation.getProductArea()
                .getProductArea(), map,"LastYear");

        // Åpne tiltak
        setOpenPreventiveActions(map);

        // Lukkede tiltak denne måned
        setClosedPreventiveActionsThisPeriode(paramsDeviation, map);

        // Avviksoversikt
        setDeviationOverview(paramsDeviation, map);

        return map;
    }

    private void setDeviationOverview(
            final ExcelReportSettingDeviation paramsDeviation, final Map<Object, Object> map) {
        Map<MonthEnum, Map<String, SumAvvikV>> monthDeviations =
            new Hashtable<MonthEnum, Map<String, SumAvvikV>>();

        List<MonthEnum> months = MonthEnum.getMonthList();
        for (MonthEnum month : months) {
            addMonthDeviations(paramsDeviation.getYear(), paramsDeviation
                    .getProductArea().getProductArea(), month, monthDeviations);
        }

        map.put("DeviationList", monthDeviations);
    }

    private void setClosedPreventiveActionsThisPeriode(
            final ExcelReportSettingDeviation paramsDeviation, final Map<Object, Object> map) {
        List<PreventiveAction> preventiveActionList;
        preventiveActionList = preventiveActionDAO
                .findAllClosedInMonth(paramsDeviation.getMonthEnum().getMonth());

        if (preventiveActionList != null) {
            map.put("PreventiveActionClosed", preventiveActionList);
        }
    }

    private void setOpenPreventiveActions(final Map<Object, Object> map) {
        List<PreventiveAction> preventiveActionList = preventiveActionDAO
                .findAllOpen();

        if (preventiveActionList != null) {
            map.put("PreventiveActionOpen", preventiveActionList);
        }
    }

    private void setDeviationForPeriode(final Integer year, final Integer month,
            final String productArea, final Map<Object, Object> map,final String keyString) {
        List<SumAvvikV> deviations = dao.findByProductAreaYearAndMonth(year,
                month, productArea);

        if (deviations != null) {
            map.put(keyString, deviations);
        }
    }

    private void setDeviationTotal(final ExcelReportSettingDeviation paramsDeviation,
            final Map<Object, Object> map) {
        List<SumAvvikV> deviations;
        deviations = dao.findSumByProductAreaYearAndMonth(paramsDeviation
                .getYear(), paramsDeviation.getMonthEnum().getMonth(),
                paramsDeviation.getProductArea().getProductArea());

        if (deviations != null) {
            map.put("Year", deviations);
        }
    }

    /**
     * Legger til avvik for måned.
     * @param year
     * @param productArea
     * @param month
     * @param monthDeviations
     */
    private void addMonthDeviations(final Integer year, final String productArea,
            final MonthEnum month,
            final Map<MonthEnum, Map<String, SumAvvikV>> monthDeviations) {
        List<SumAvvikV> deviations = dao
                .findByProductAreaYearAndMonthWithClosed(year,
                        month.getMonth(), productArea);

        Map<String, SumAvvikV> deviationMap = new Hashtable<String, SumAvvikV>();

        SumAvvikV currentDeviationOpen = null;
        SumAvvikV currentDeviationClosed = null;
        String jobFunctionName = "";
        int count = 0;
        if (deviations != null) {
            for (SumAvvikV deviation : deviations) {
                count++;
                if (!jobFunctionName.equalsIgnoreCase(deviation
                        .getJobFunctionName())
                        && count != 1) {
                    if (currentDeviationOpen != null
                            && currentDeviationClosed != null) {
                        currentDeviationOpen
                                .setClosedCount(currentDeviationClosed
                                        .getDeviationCount());
                        deviationMap.put(currentDeviationOpen
                                .getJobFunctionName(), currentDeviationOpen);
                    } else if (currentDeviationClosed != null) {
                        currentDeviationClosed
                                .setClosedCount(currentDeviationClosed
                                        .getDeviationCount());
                        currentDeviationClosed.setDeviationCount(0);
                        deviationMap.put(currentDeviationClosed
                                .getJobFunctionName(), currentDeviationClosed);
                    } else if (currentDeviationOpen != null) {
                        deviationMap.put(currentDeviationOpen
                                .getJobFunctionName(), currentDeviationOpen);
                    }
                    currentDeviationOpen = null;
                    currentDeviationClosed = null;
                }
                if (deviation.getClosed() == 0) {
                    currentDeviationOpen = deviation;
                } else {
                    currentDeviationClosed = deviation;
                }
                jobFunctionName = deviation.getJobFunctionName();

            }
        }
        // setter siste avvik
        if (currentDeviationOpen != null && currentDeviationClosed != null) {
            currentDeviationOpen.setClosedCount(currentDeviationClosed
                    .getDeviationCount());
            deviationMap.put(currentDeviationOpen.getJobFunctionName(),
                    currentDeviationOpen);
        } else if (currentDeviationClosed != null) {
            currentDeviationClosed.setClosedCount(currentDeviationClosed
                    .getDeviationCount());
            currentDeviationClosed.setDeviationCount(0);
            deviationMap.put(currentDeviationClosed.getJobFunctionName(),
                    currentDeviationClosed);
        } else if (currentDeviationOpen != null) {
            deviationMap.put(currentDeviationOpen.getJobFunctionName(),
                    currentDeviationOpen);
        }
        monthDeviations.put(month, deviationMap);

    }

    public CheckObject checkExcel(ExcelReportSetting params) {
        // TODO Auto-generated method stub
        return null;
    }

}
