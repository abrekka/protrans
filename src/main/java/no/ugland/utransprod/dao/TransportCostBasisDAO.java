
// Warning: No line numbers available in class file
package no.ugland.utransprod.dao;

import java.util.List;
import no.ugland.utransprod.model.TransportCostBasis;

public interface TransportCostBasisDAO extends DAO<TransportCostBasis> {
   List<TransportCostBasis> findById(Integer var1);

   void setInvoiceNr(TransportCostBasis var1, String var2);
}
