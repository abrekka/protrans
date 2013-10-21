package no.ugland.utransprod.service;

import java.math.BigDecimal;
import java.util.List;

import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Ordln;

public interface OrdlnManager {
    public static final String MANAGER_NAME="ordlnManager";
    List<Ordln> findByOrderNr(String orderNr);
    Ordln findByOrdNoAndLnNo(Integer ordNo,Integer lnNo);
    BigDecimal getSumCstpr(final String orderNr,final Integer prCatNo);
    Ordln findByOrderNrProdCatNo(String orderNr,Integer prodCatNo,Integer prodCatNo2);
    BigDecimal getSumCcstpr(String orderNr, Integer prodCatNo);
    Integer isOrderLineInStorage(Integer ordNo, Integer lnNo);
	Ord findOrdByOrderNr(String orderNr);
	Ordln findByOrdnoAndPrCatNo2(Integer ordno, Integer garageTypeLine);
	List<Ordln> findCostLines(String string);
}
