package no.ugland.utransprod.dao;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Ordln;

public interface OrdlnDAO extends DAO<Ordln> {
    List<Ordln> findByOrderNr(String orderNr);

    Ordln findByOrdNoAndLnNo(Integer ordNo, Integer lnNo);

    BigDecimal getSumCstpr(final String orderNr, final Integer prCatNo);

    Ordln findByOrderNrProdCatNo(String orderNr, Integer prodCatNo, Integer prodCatNo2);

    BigDecimal getSumCcstpr(String orderNr, Integer prodCatNo);

    Integer isOrderLineInStorage(Integer ordNo, Integer lnNo);

    Ord findOrdByOrderNr(String orderNr);

    Ordln findByOrdnoAndPrCatNo2(Integer ordno, Integer garageTypeLine);

    List<Ordln> findCostLines(String orderNr);

    List<Ordln> findForFakturagrunnlag(String orderNr);

    Ord findByOrdNo(Integer ordNo);

    List<Ordln> findTaksteinInfo(String orderNr);

	List<Ordln> findOrdLnByOrdNo(Integer ordno);
}
