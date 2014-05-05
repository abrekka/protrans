package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.FakturagrunnlagV;

public interface FakturagrunnlagVDAO {

    List<FakturagrunnlagV> finnFakturagrunnlag(Integer orderId);

}
