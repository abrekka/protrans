
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

import no.ugland.utransprod.gui.WindowInterface;

public interface ColliListener {
   void colliSelectionChange(boolean var1, ColliModel var2);

   void orderLineRemoved(WindowInterface var1);

   void refreshCollies();
}
