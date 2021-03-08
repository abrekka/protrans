
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.handlers;

import com.google.common.collect.Multimap;
import java.util.Map;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.model.OrderLine;

public interface MainPackageViewHandlerFactory {
   MainPackageViewHandler create(Multimap<String, String> var1, Map<String, StatusCheckerInterface<OrderLine>> var2);
}
