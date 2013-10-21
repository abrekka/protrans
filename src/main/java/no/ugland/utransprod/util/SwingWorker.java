package no.ugland.utransprod.util;

import javax.swing.SwingUtilities;

/**
 * Dette er en abstrakt klasse som subklases for å utføre.
 * brukergrensesnitt-relatert arbeid i en dedikert tråd. Dette er for å hindre
 * at brukergrensesnittet henger når det gjøres en jobb som tar litt tid For mer
 * info: http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html
 */
public abstract class SwingWorker {

    private Object value;

    /**
     * Klasse som håndterer referanse til gjeldende tråd under sunkroniserings.
     * kontroll.
     */
    private static class ThreadVar {
        private Thread thread;

        /**
         * @param t
         */
        ThreadVar(final Thread t) {
            thread = t;
        }

        /**
         * @return tråd
         */
        synchronized Thread get() {
            return thread;
        }

        synchronized void clear() {
            thread = null;
        }
    }

    ThreadVar threadVar;

    /**
     * Henter verdien laget av tråden, eller null dersom den ikke har blitt
     * laget ennå.
     * @return objekt
     */
    protected final synchronized Object getValue() {
        return value;
    }

    /**
     * Setter verdien laget av tråden.
     * @param x
     */
    final synchronized void setValue(final Object x) {
        value = x;
    }

    /**
     * Kreerer verdien som skal returneres av <code>get</code> metoden.
     * @return returverdi fra trådkall
     */
    public abstract Object construct();

    /**
     * Blir kalt i event dispatching tråd (ikke tråd som gjør arbeid) etter
     * metoden <code>construct</code> har returnert.
     */
    public void finished() {
    }

    /**
     * Kjører interrupt på tråden. Kall denne metoden for å tvinge tråden til å
     * stoppe med det den holder på med.
     */
    public final void interrupt() {
        Thread t = threadVar.get();
        if (t != null) {
            t.interrupt();
        }
        threadVar.clear();
    }

    /**
     * Henter verdien laget av metoden <code>construct</code>. Returnerer
     * null dersom enten tråden som gjør arbeid eller gjeldende tråd ble
     * interruptet før verdien ble laget.
     * @return verdien laget av metoden <code>construct</code>
     */
    public final Object get() {
        while (true) {
            Thread t = threadVar.get();
            if (t == null) {
                return getValue();
            }
            try {
                t.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // propagate
                return null;
            }
        }
    }

    /**
     * Starter en tråd som vil kalle metoden <code>construct</code> og
     * avslutte.
     */
    public SwingWorker() {
        final Runnable doFinished = new Runnable() {
            public void run() {
                finished();
            }
        };

        Runnable doConstruct = new Runnable() {
            public void run() {
                try {
                    setValue(construct());
                } catch (Exception ex) {
                    ex.printStackTrace();
                    setValue(construct());
                } finally {
                    threadVar.clear();
                }

                SwingUtilities.invokeLater(doFinished);
            }
        };

        Thread t = new Thread(doConstruct);
        threadVar = new ThreadVar(t);
    }

    /**
     * Starter tråden.
     */
    public final void start() {
        Thread t = threadVar.get();
        if (t != null) {
            t.start();
        }
    }
}
