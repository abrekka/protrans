package no.ugland.utransprod.service;

import java.util.List;

import no.ugland.utransprod.model.FakturagrunnlagV;

public interface FakturagrunnlagVManager {

    String MANAGER_NAME = "fakturagrunnlagVManager";

    List<FakturagrunnlagV> findFakturagrunnlag(Integer orderId);

    Integer finnBestillingsnrFrakt(Integer orderId);

}
