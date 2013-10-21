package no.ugland.utransprod.gui.model;

/**
 * Enum for dialog
 * 
 * @author atle.brekka
 * 
 */
public enum WindowEnum {
	/**
	 * 
	 */
	CUSTOMER("Kunder"),
	/**
	 * 
	 */
	ORDER("Ordre");
	/**
	 * 
	 */
	private String title;

	/**
	 * @param aTitle
	 */
	private WindowEnum(String aTitle) {
		title = aTitle;
	}

	/**
	 * @return tittel
	 */
	public String getTitle() {
		return title;
	}
}
