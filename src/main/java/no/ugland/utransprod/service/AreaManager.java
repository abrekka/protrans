package no.ugland.utransprod.service;

import no.ugland.utransprod.model.Area;

public interface AreaManager {
    Area load(String areaCode);
    void removeAll();
    void saveArea(Area area);
	Area findByAreaCode(String areaCode);
}
