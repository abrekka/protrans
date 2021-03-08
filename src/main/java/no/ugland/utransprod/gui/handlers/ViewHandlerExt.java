
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.AbstractModel;

public interface ViewHandlerExt<T, E> {
   boolean openEditViewExt(T var1, boolean var2, WindowInterface var3, boolean var4);

   boolean saveObjectExt(AbstractModel<T, E> var1, WindowInterface var2);
}
