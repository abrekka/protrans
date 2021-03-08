
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.math.BigDecimal;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.util.Periode;

public interface ITransportCostAddition {
   BigDecimal calculateAddition(BigDecimal var1, Transportable var2, Periode var3, boolean var4);

   String getArticlePath();

   String getInfo();
}
