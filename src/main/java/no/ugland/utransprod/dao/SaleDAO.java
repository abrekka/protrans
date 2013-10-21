package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.Sale;
import no.ugland.utransprod.util.report.ProbabilityEnum;

public interface SaleDAO extends DAO<Sale> {
    List<Sale> findByProbability(ProbabilityEnum probabilityEnum);
}
