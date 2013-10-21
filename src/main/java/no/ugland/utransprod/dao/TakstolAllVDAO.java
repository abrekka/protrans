package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.TakstolAllV;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.TakstolInterface;

public interface TakstolAllVDAO {

	List<TakstolInterface> findProductionByPeriode(Periode periode);

	List<TakstolAllV> findAllNotProduced();

}
