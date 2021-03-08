
// Warning: No line numbers available in class file
package no.ugland.utransprod.gui.model;

import com.jgoodies.binding.PresentationModel;
import java.beans.PropertyChangeListener;

public interface ModelInterface<E> {
   E getBufferedObjectModel(PresentationModel var1);

   void addBufferChangeListener(PropertyChangeListener var1, PresentationModel var2);
}
