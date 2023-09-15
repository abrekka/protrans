
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.math.BigDecimal;
import java.util.List;
import no.ugland.utransprod.model.Ord;
import no.ugland.utransprod.model.Ordln;

public interface OrdlnManager {
	String MANAGER_NAME = "ordlnManager";

	List<Ordln> findByOrderNr(String var1);

	Ordln findByOrdNoAndLnNo(Integer var1, Integer var2);

	BigDecimal getSumCstpr(String var1, Integer var2);

	Ordln findByOrderNrProdCatNo(String var1, Integer var2, Integer var3);

	BigDecimal getSumCcstpr(String var1, Integer var2);

	Integer isOrderLineInStorage(Integer var1, Integer var2);

	Ord findOrdByOrderNr(String var1);

	Ordln findByOrdnoAndPrCatNo2(Integer var1, Integer var2);

	List<Ordln> findCostLines(String var1);

	List<Ordln> findForFakturagrunnlag(String var1);

	Ord findByOrdNo(Integer var1);

	List<Ordln> findTaksteinInfo(String var1);

	List<Ordln> findOrdLnByOrdNo(Integer var1);

	List<Ord> findInvoicedOrdByOrdernr(List<String> ordernr);
}
