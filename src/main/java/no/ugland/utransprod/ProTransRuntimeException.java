package no.ugland.utransprod;

/**
 * Exception som ikke trenger å bli catchet
 * @author atle.brekka
 *
 */
public class ProTransRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param msg
	 */
	public ProTransRuntimeException(String msg) {
		super(msg);
	}
}
