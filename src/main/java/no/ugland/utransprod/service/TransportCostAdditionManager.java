
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.math.BigDecimal;
import java.util.Map;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.TransportCost;
import no.ugland.utransprod.model.TransportCostAddition;
import no.ugland.utransprod.util.Periode;

public interface TransportCostAdditionManager {
   TransportCostAddition findByDescription(String var1);

   void deleteAll();

   void saveTransportCostAddition(TransportCostAddition var1);

   Map<ITransportCostAddition, BigDecimal> calculateCostAddition(Transportable var1, TransportCost var2, Periode var3) throws ProTransException;
}
