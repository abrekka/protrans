package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.model.SaleStatusV;

public interface SaleStatusVDAO extends DAO<SaleStatusV> {
    List<SaleStatusV> findByProbabilitesAndProductArea(List<Integer> probabilities, ProductArea productArea);
}
