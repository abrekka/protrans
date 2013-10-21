package no.ugland.utransprod.dao;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.TransportCost;

public interface TransportCostDAO extends DAO<TransportCost>{
    List<TransportCost> findAll();

    TransportCost findByPostalCode(String postalCode);
    void setAllPostalCodesInvalid();
    void updatePriceForPostalCodes(String postalCodeFrom,String postalCodeTo,BigDecimal price,Integer maxAddition,BigDecimal addition);
    String findCountyNameByPostalCode(String postalCode);
}
