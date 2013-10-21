package no.ugland.utransprod.gui.model;

import java.awt.Color;

/**
 * Brukes til å sette farger i tabellrader
 * 
 * @author atle.brekka
 * 
 */
public enum ColorEnum {
	/**
	 *
	 */
	RED(new Color(241, 51, 3)),
	/**
	 *
	 */
	GREEN(new Color(113, 189, 94)),
	/**
	 *
	 */
	YELLOW(new Color(240, 245, 37)), BLUE(new Color(30, 43, 193)), GREY(
			new Color(129, 129, 129));
	/**
	 *
	 */
	private Color color;

	/**
	 * @param color
	 */
	private ColorEnum(Color color) {
		this.color = color;
	}

	/**
	 * @return farge
	 */
	public Color getColor() {
		return color;
	}
}
