package no.ugland.utransprod.dao;

import java.util.List;

import no.ugland.utransprod.model.OrdchgrLineV;

public interface OrdchgrLineVDAO extends DAO<OrdchgrLineV> {
    List<OrdchgrLineV> getLines(Integer ordNo, List<Integer> lnNos);
}
