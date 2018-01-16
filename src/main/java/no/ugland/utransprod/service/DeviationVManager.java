package no.ugland.utransprod.service;

import java.util.Collection;

import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.DeviationV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.util.excel.ExcelManager;

public interface DeviationVManager extends ExcelManager, OverviewManager<DeviationV> {

	public static final String MANAGER_NAME = "deviationVManager";

	Collection findByOrder(Order order);

	Collection findByManager(ApplicationUser applicationUser);

	DeviationV findByDeviationId(Integer deviationId);

}
