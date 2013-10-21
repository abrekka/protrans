package no.ugland.utransprod.service.impl;

import java.util.List;
import java.util.Map;

import no.ugland.utransprod.dao.FaktureringVDAO;
import no.ugland.utransprod.gui.handlers.CheckObject;
import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.model.Ordln;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.FaktureringVManager;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

/**
 * Implementasjon av serviceklasse for view FAKTURERING_V.
 * @author atle.brekka
 */
public class FaktureringVManagerImpl extends AbstractApplyListManager<FaktureringV> implements FaktureringVManager {
    private FaktureringVDAO dao;

    /**
     * Setter dao for kunder.
     * @param aDao
     */
    public final void setFaktureringVDAO(final FaktureringVDAO aDao) {
        this.dao = aDao;
    }

    /**
     * @see no.ugland.utransprod.service.FaktureringVManager#findAllApplyable()
     */
    public final List<FaktureringV> findAllApplyable() {
        return dao.findAll();
    }

    /**
     * @see no.ugland.utransprod.service.FaktureringVManager#findApplyableByCustomerNr(java.lang.Integer)
     */
    public final List<FaktureringV> findApplyableByCustomerNr(final Integer customerNr) {
        return dao.findByCustomerNr(customerNr);
    }

    /**
     * @see no.ugland.utransprod.service.FaktureringVManager#findApplyableByOrderNr(java.lang.String)
     */
    public final List<FaktureringV> findApplyableByOrderNr(final String orderNr) {
        return dao.findByOrderNr(orderNr);
    }

    /**
     * @see no.ugland.utransprod.service.FaktureringVManager#refresh(no.ugland.utransprod.model.FaktureringV)
     */
    public final void refresh(final FaktureringV faktureringV) {
        dao.refresh(faktureringV);

    }

    /**
     * @see no.ugland.utransprod.util.excel.ExcelManager#
     * findByParams(no.ugland.utransprod.util.excel.ExcelReportSetting)
     */
    public final List<FaktureringV> findByParams(final ExcelReportSetting params) {

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

	public List<FaktureringV> findApplyableByCustomerNrAndProductAreaGroup(
			Integer customerNr, ProductAreaGroup productAreaGroup) {
		return dao.findByCustomerNrAndProductAreaGroup(customerNr,productAreaGroup);
	}

	public List<FaktureringV> findApplyableByOrderNrAndProductAreaGroup(
			String orderNr, ProductAreaGroup productAreaGroup) {
		return dao.findByOrderNrAndProductAreaGroup(orderNr,productAreaGroup);
	}

	public FaktureringV findByOrderNr(String orderNr) {
		List<FaktureringV> list = dao.findByOrderNr(orderNr);
		return list!=null&&list.size()==1?list.get(0):null;
	}

   

}
