package no.ugland.utransprod.service;

import no.ugland.utransprod.model.County;

public interface CountyManager {
    County load(String countyNr);
    void removeAll();
    void saveCounty(County county);
    
}
