package no.ugland.utransprod.util;

import javax.swing.SwingUtilities;

/**
 * Dette er en abstrakt klasse som subklases for � utf�re.
 * brukergrensesnitt-relatert arbeid i en dedikert tr�d. Dette er for � hindre
 * at brukergrensesnittet henger n�r det gj�res en jobb som tar litt tid For mer
 * info: http://java.sun.com/docs/books/tutorial/uiswing/misc/threads.html
 */
public abstract class SwingWorker {

    private Object value;

    /**
     * Klasse som h�ndterer referanse til gjeldende tr�d under sunkroniserings.
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
         * @return tr�d
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
     * Henter verdien laget av tr�den, eller null dersom den ikke har blitt
     * laget enn�.
     * @return objekt
     */
    protected final synchronized Object getValue() {
        return value;
    }

    /**
     * Setter verdien laget av tr�den.
     * @param x
     */
    final synchronized void setValue(final Object x) {
        value = x;
    }

    /**
     * Kreerer verdien som skal returneres av <code>get</code> metoden.
     * @return returverdi fra tr�dkall
     */
    public abstract Object construct();

    /**
     * Blir kalt i event dispatching tr�d (ikke tr�d som gj�r arbeid) etter
     * metoden <code>construct</code> har returnert.
     */
    public void finished() {
    }

    /**
     * Kj�rer interrupt p� tr�den. Kall denne metoden for � tvinge tr�den til �
     * stoppe med det den holder p� med.
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
     * null dersom enten tr�den som gj�r arbeid eller gjeldende tr�d ble
     * interruptet f�r verdien ble laget.
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
     * Starter en tr�d som vil kalle metoden <code>construct</code> og
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
     * Starter tr�den.
     */
    public final void start() {
        Thread t = threadVar.get();
        if (t != null) {
            t.start();
        }
    }
}
