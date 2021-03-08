
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;

public interface WindowFactory {
   WindowInterface getWindow(WindowEnum var1, Login var2);
}
