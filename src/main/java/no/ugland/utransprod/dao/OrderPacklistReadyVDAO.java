package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.OrderPacklistReadyV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

/**
 * Interface for DAO mot view ORDER_PACKLIST_READY_V
 * 
 * @author atle.brekka
 * 
 */
public interface OrderPacklistReadyVDAO extends DAO<OrderPacklistReadyV> {
	/**
	 * Finner pakklisteinfo basert på parametre
	 * 
	 * @param params
	 * @return pakklisteinfo
	 */
	List<OrderPacklistReadyV> findByParams(ExcelReportSetting params);
}
