package no.ugland.utransprod.gui;

import no.ugland.utransprod.ProTransException;

/**
 * Enum for look and feel
 * 
 * @author atle.brekka
 * 
 */
public enum LFEnum {
	/**
	 * 
	 */
	LNF_QUAQUA("Look and feel - QUAQUA",
			"ch.randelshofer.quaqua.QuaquaLookAndFeel"),
	/**
	 * 
	 */
	LNF_OFFICE("Look and feel - OFFICE",
			"org.fife.plaf.VisualStudio2005.VisualStudio2005LookAndFeel"),
	/**
	 * 
	 */
	LNF_NIMROD("Look and feel - NIMROD",
			"com.nilo.plaf.nimrod.NimRODLookAndFeel"),
	/**
	 * 
	 */
	LNF_LIQUID("Look and feel - LIQUID",
			"com.birosoft.liquid.LiquidLookAndFeel"),
	/**
	 * 
	 */
	LNF_INFONODE("Look and feel - INFONODE",
			"net.infonode.gui.laf.InfoNodeLookAndFeel"),

	/**
	 * 
	 */
	LNF_SYNTHETICA("Look and feel - SYNTHETICA",
			"de.javasoft.plaf.synthetica.SyntheticaStandardLookAndFeel"), 
	/**
	 * 
	 */
	LNF_JGOODIES(
			"Look and feel - JGOODIES",
			"com.jgoodies.plaf.plastic.PlasticLookAndFeel");
	/**
	 * 
	 */
	private String actionString;

	/**
	 * 
	 */
	private String className;

	/**
	 * @param action
	 * @param classString 
	 */
	private LFEnum(String action, String classString) {
		actionString = action;
		className = classString;
	}

	/**
	 * @return actionstring
	 */
	public String getActionString() {
		return actionString;
	}

	/**
	 * @return klassenavn
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @param actionCommand
	 * @return enum
	 * @throws ProTransException
	 */
	public static LFEnum getLFEnum(String actionCommand)
			throws ProTransException {
		if (actionCommand.equalsIgnoreCase(LNF_INFONODE.getActionString())) {
			return LNF_INFONODE;
		} else if (actionCommand.equalsIgnoreCase(LNF_JGOODIES
				.getActionString())) {
			return LNF_JGOODIES;
		} else if (actionCommand.equalsIgnoreCase(LNF_LIQUID.getActionString())) {
			return LNF_LIQUID;
		} else if (actionCommand.equalsIgnoreCase(LNF_SYNTHETICA
				.getActionString())) {
			return LNF_SYNTHETICA;
		} else if (actionCommand.equalsIgnoreCase(LNF_NIMROD.getActionString())) {
			return LNF_NIMROD;
		} else if (actionCommand.equalsIgnoreCase(LNF_OFFICE.getActionString())) {
			return LNF_OFFICE;
		} else if (actionCommand.equalsIgnoreCase(LNF_QUAQUA.getActionString())) {
			return LNF_QUAQUA;
		} else {
			throw new ProTransException("LF ble ikke funnet");
		}
	}
}
