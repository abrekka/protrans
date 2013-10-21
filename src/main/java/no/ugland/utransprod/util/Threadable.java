package no.ugland.utransprod.util;

import javax.swing.JLabel;

/**
 * Interface for klasser som skal kunne kjøre kall i egen tråd i.
 * GuiUtil.runInThread
 * @author abr99
 */
public interface Threadable {
    /**
     * Enabler disabler knapper før og etter kall.
     * @param enable
     */
    void enableComponents(boolean enable);

    /**
     * Det er denne metoden hvor alt som skal kjøres som egen tråd skal legges
     * i.
     * @param params
     * @param labelInfo
     * @return returverdi etter at kall er ferdig
     */
    Object doWork(Object[] params, JLabel labelInfo);

    /**
     * Metoden kjøres etter at tråden er ferdig.
     * @param object
     */
    void doWhenFinished(Object object);
}
