package no.ugland.utransprod;

/**
 * Dette er systemets hoved exception, alle andre exception som blir kastet i
 * systemet vil bli konvertert om til denne
 * 
 * @author atb
 * 
 */
public class ProTransException extends RuntimeException {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * KonstruktÝr
     * 
     * @param msg
     *            feilmelding
     */
    public ProTransException(String msg) {
        super(msg);
    }

    /**
     * KonstruktÝr
     * 
     * @param exception
     *            opprinnelig exception
     */
    public ProTransException(Throwable exception) {
        super(exception);
    }
}
