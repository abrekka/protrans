package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.TransportCostAddition;

public interface TransportCostAdditionDAO extends DAO<TransportCostAddition>{
    TransportCostAddition findByDescription(String desc);
    List<TransportCostAddition> findTransportBasisAdditions(Integer isMemberOfMaxAdditions);
    
}
