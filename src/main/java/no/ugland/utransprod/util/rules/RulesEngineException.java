package no.ugland.utransprod.util.rules;

public class RulesEngineException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RulesEngineException(final String message) {
        super(message);
    }

    public RulesEngineException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
