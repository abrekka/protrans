
// Warning: No line numbers available in class file
package no.ugland.utransprod.service;

import java.io.IOException;
import java.util.List;
import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;

public interface VismaFileCreator {
   String createVismaFile(List<OrderLine> var1, int var2, boolean var3) throws ProTransException;

   boolean ignoreVismaFile(OrderLine var1, WindowInterface var2);

   String createVismaFileForTransport(Order var1) throws ProTransException;

   void createVismaFileForAssembly(Order var1, boolean var2, boolean var3, int var4);

   void createVismaFileForDelivery(Order var1, boolean var2, int var3);

   String createVismaFileForProductionWeek(Order var1);

   String writeFile(String var1, String var2, List<String> var3, int var4) throws IOException;
}
