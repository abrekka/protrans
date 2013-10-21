package no.ugland.utransprod.service;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Multimap;

import no.ugland.utransprod.model.TakstoltegnerV;
import no.ugland.utransprod.model.TakstoltegnerVSum;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.excel.ExcelManager;

public interface TakstoltegnerVManager extends ExcelManager{

	String MANAGER_NAME = "takstoltegnerVManager";

	Map<TakstoltegnerVSum,Collection<TakstoltegnerV>> findByPeriode(Periode periode);

}
