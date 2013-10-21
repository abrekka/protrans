package no.ugland.utransprod.service;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProcentDone;

public interface ProcentDoneManager extends OverviewManager<ProcentDone> {
    String MANAGER_NAME = "procentDoneManager";

	ProcentDone findByYearWeekOrder(Integer year, Integer week, Order order);
}
