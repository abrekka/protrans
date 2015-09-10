package no.ugland.utransprod.service.impl;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.OwnProductionVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.OwnProductionV;
import no.ugland.utransprod.service.OwnProductionVManager;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingOwnProduction;

/**
 * Implementasjon av manager for view OWN_PRODUCTION_V.
 * 
 * @author atle.brekka
 */
public class OwnProductionVManagerImpl implements OwnProductionVManager {
    private OwnProductionVDAO dao;

    /**
     * @param aDao
     */
    public final void setOwnProductionVDAO(final OwnProductionVDAO aDao) {
	this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.util.excel.
     *      ExcelManager#findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final List<OwnProductionV> findByParams(final ExcelReportSetting params) {
	OwnProductionReport ownProductionReport = OwnProductionReport.valueOf(params.getExcelReportType().name());
	List<OwnProductionV> list = ownProductionReport.findByParams(dao, params);
	return list;
	/*
	 * if (params.getExcelReportType() == ExcelReportEnum.OWN_PRODUCTION) {
	 * return dao.findByParams(params); } else if
	 * (params.getExcelReportType() == ExcelReportEnum.PACKLIST_NOT_READY) {
	 * return dao.findPacklistNotReady(params.getProductAreaName()); } else
	 * if (params.getYear().equals(Util.getCurrentYear()) &&
	 * params.getWeekFrom().equals(Util.getCurrentWeek())) { return dao
	 * .findPacklistReady(((ExcelReportSettingOwnProduction) params)
	 * .getProductAreaGroupName()); } return null;
	 */
    }

    /**
     * Gjør ingenting.
     * 
     * @see no.ugland.utransprod.util.excel.
     *      ExcelManager#getInfo(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final String getInfoButtom(final ExcelReportSetting params) {
	return null;
    }

    public final String getInfoTop(final ExcelReportSetting params) {
	OwnProductionReport ownProductionReport = OwnProductionReport.valueOf(params.getExcelReportType().name());
	return ownProductionReport.getInfoTop();
    }

    /**
     * Gjør ingenting.
     * 
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     *      getReportDataMap(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final Map<Object, Object> getReportDataMap(final ExcelReportSetting params) {
	return null;
    }

    public CheckObject checkExcel(ExcelReportSetting params) {
	// TODO Auto-generated method stub
	return null;
    }

    private enum OwnProductionReport {
	OWN_PRODUCTION {
	    @Override
	    public List<OwnProductionV> findByParams(final OwnProductionVDAO dao, final ExcelReportSetting params) {
		return dao.findByParams(params);
	    }

	    @Override
	    public String getInfoTop() {
		// TODO Auto-generated method stub
		return null;
	    }
	},
	OWN_PRODUCTION_PACKLIST {
	    @Override
	    public List<OwnProductionV> findByParams(final OwnProductionVDAO dao, final ExcelReportSetting params) {
		if (params.getYear().equals(Util.getCurrentYear()) && params.getWeekFrom().equals(Util.getCurrentWeek())) {
		    return dao.findPacklistReady(((ExcelReportSettingOwnProduction) params).getProductAreaGroupName());
		}
		return null;
	    }

	    @Override
	    public String getInfoTop() {
		// TODO Auto-generated method stub
		return "Pakkliste";
	    }
	},
	OWN_PRODUCTION_PACKLIST_NOT_READY {
	    @Override
	    public List<OwnProductionV> findByParams(final OwnProductionVDAO dao, final ExcelReportSetting params) {
		if (params.getYear().equals(Util.getCurrentYear()) && params.getWeekFrom().equals(Util.getCurrentWeek())) {
		    return dao
			    .findPacklistNotReady(params.getProductAreaName(), ((ExcelReportSettingOwnProduction) params).getProductAreaGroupName());
		}
		return null;
	    }

	    @Override
	    public String getInfoTop() {
		return "Ikke pakkliste";
	    }
	},
	PACKLIST_NOT_READY {
	    @Override
	    public List<OwnProductionV> findByParams(OwnProductionVDAO dao, ExcelReportSetting params) {
		return dao.findPacklistNotReady(params.getProductAreaName(), null);
	    }

	    @Override
	    public String getInfoTop() {
		// TODO Auto-generated method stub
		return null;
	    }
	};

	public abstract List<OwnProductionV> findByParams(final OwnProductionVDAO dao, final ExcelReportSetting params);

	public abstract String getInfoTop();
    }
}
