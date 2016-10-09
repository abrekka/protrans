package no.ugland.utransprod.gui.handlers;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.AbstractModel;

public interface ViewHandlerExt<T,E> {
    boolean openEditViewExt(T object, boolean searching,
            WindowInterface parentWindow,boolean lettvekt);
    boolean saveObjectExt(AbstractModel<T, E> objectModel,
            WindowInterface window);
}
