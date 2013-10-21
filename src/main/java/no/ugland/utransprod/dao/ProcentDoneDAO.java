package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProcentDone;

public interface ProcentDoneDAO extends DAO<ProcentDone> {
    ProcentDone findByYearWeekOrder(Integer year, Integer week, Order order);
}
