
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.util.List;
import no.ugland.utransprod.model.TransportCostBasis;

public interface TransportCostBasisManager extends OverviewManager<TransportCostBasis> {
   String MANAGER_NAME = "transportCostBasisManager";

   void saveTransportCostBasisList(List<TransportCostBasis> var1);

   void removeTransportCostBasis(TransportCostBasis var1);

   List<TransportCostBasis> findById(Integer var1);

   void saveTransportCostBasis(TransportCostBasis var1);

   void setInvoiceNr(TransportCostBasis var1, String var2);
}
