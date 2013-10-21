package no.ugland.utransprod.util;

import javax.swing.JLabel;

/**
 * Interface for klasser som skal kunne kj�re kall i egen tr�d i.
 * GuiUtil.runInThread
 * @author abr99
 */
public interface Threadable {
    /**
     * Enabler disabler knapper f�r og etter kall.
     * @param enable
     */
    void enableComponents(boolean enable);

    /**
     * Det er denne metoden hvor alt som skal kj�res som egen tr�d skal legges
     * i.
     * @param params
     * @param labelInfo
     * @return returverdi etter at kall er ferdig
     */
    Object doWork(Object[] params, JLabel labelInfo);

    /**
     * Metoden kj�res etter at tr�den er ferdig.
     * @param object
     */
    void doWhenFinished(Object object);
}
