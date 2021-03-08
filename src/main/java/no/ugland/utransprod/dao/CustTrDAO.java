
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.CustTr;

public interface CustTrDAO extends DAO<CustTr> {
   List<CustTr> findByOrderNr(String var1);
}
