package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.model.SalesStatistic;

public interface SalesWebManager {

    SalesStatistic generateSalesStatistics(Integer year, Integer week, String productAreaName) throws ProTransException;

    List<String> getProductAreaNames();

}
