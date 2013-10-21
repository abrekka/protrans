package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.PostShipmentCountSum;
import no.ugland.utransprod.model.PostShipmentCountV;
import no.ugland.utransprod.util.excel.ExcelReportSetting;

/**
 * Interface for DAO mot view POST_SHIPMENT_COUNT_V
 * @author atle.brekka
 *
 */
public interface PostShipmentCountVDAO extends DAO<PostShipmentCountV> {
	/**
	 * Finner alle ettersendinger som er sent innenfor gitt periode
	 * @param params
	 * @return sendte ettersendringer
	 */
	List<PostShipmentCountSum> findByParams(ExcelReportSetting params);
}
