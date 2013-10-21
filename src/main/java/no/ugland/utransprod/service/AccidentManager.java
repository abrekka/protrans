package no.ugland.utransprod.service;

import no.ugland.utransprod.model.Accident;


public interface AccidentManager extends OverviewManager<Accident> {

	String MANAGER_NAME = "accidentManager";
    //void lazyLoad(Accident accident, LazyLoadAccidentEnum[] enums);
}
