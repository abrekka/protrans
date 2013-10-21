package no.ugland.utransprod.dao;

import no.ugland.utransprod.model.Area;

public interface AreaDAO extends DAO<Area>{

	Area findByAreaCode(String areaCode);

}
