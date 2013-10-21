package no.ugland.utransprod.dao;

import java.util.Collection;
import java.util.Map;

import no.ugland.utransprod.model.TakstoltegnerV;
import no.ugland.utransprod.util.Periode;

public interface TakstoltegnerVDAO {

	Collection<TakstoltegnerV> findByPeriode(Periode periode);

}
