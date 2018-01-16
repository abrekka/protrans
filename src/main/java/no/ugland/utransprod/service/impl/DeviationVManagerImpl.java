package no.ugland.utransprod.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.dao.DeviationDAO;
import no.ugland.utransprod.dao.DeviationVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Deviation;
import no.ugland.utransprod.model.DeviationV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.service.DeviationVManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.excel.ExcelReportSetting;
import no.ugland.utransprod.util.excel.ExcelReportSettingDeviation;

public class DeviationVManagerImpl implements DeviationVManager {
    private DeviationVDAO dao;

    /**
     * @param aDao
     */
    public final void setDeviationVDAO(final DeviationVDAO aDao) {
        this.dao = aDao;
    }

    public CheckObject checkExcel(ExcelReportSetting params) {
        // TODO Auto-generated method stub
        return null;
    }

    public List<?> findByParams(ExcelReportSetting params) throws ProTransException {
        return dao.findByParams((ExcelReportSettingDeviation)params);
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

	public List<DeviationV> findAll() {
		return dao.getObjects();
	}

	public List<DeviationV> findByObject(DeviationV object) {
//		return dao.findByObject(object);
		return null;
	}

	public void removeObject(DeviationV object) {
		// TODO Auto-generated method stub
		
	}

	public void saveObject(DeviationV object) throws ProTransException {
		// TODO Auto-generated method stub
		
	}

	public void refreshObject(DeviationV object) {
		// TODO Auto-generated method stub
		
	}

	public DeviationV merge(DeviationV object) {
		// TODO Auto-generated method stub
		return null;
	}

	public void lazyLoad(DeviationV object, LazyLoadEnum[][] enums) {
		// TODO Auto-generated method stub
		
	}

	public Collection findByOrder(Order order) {
		return ((DeviationVDAO) dao).findByOrder(order);
	}

	public Collection findByManager(ApplicationUser applicationUser) {
		return ((DeviationVDAO) dao).findByManager(applicationUser);
	}

	public DeviationV findByDeviationId(Integer deviationId) {
		return ((DeviationVDAO) dao).findByDeviationId(deviationId);
	}

}