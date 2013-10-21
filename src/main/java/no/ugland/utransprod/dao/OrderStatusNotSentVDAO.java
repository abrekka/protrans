package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.OrderStatusNotSentV;
import no.ugland.utransprod.model.StatusOrdersNotSent;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

/**
 * Interface for DAO mot view ORDER_STATUS_NOT_SENT_V
 * 
 * @author atle.brekka
 * 
 */
public interface OrderStatusNotSentVDAO extends DAO<OrderStatusNotSentV> {
	/**
	 * Finner status for ordre som ikke er sent basert på parametre
	 * 
	 * @param params
	 * @return status for ordre
	 */
	List<StatusOrdersNotSent> findByParams(ExcelReportSetting params);
}
