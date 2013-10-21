package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum for skisser ifm fraktbrev
 * 
 * @author atle.brekka
 * 
 */
public enum SketchEnum {
	/**
	 * 
	 */
	DOBBEL_18_22("18-22 dobbel.jpg"),
	/**
	 * 
	 */
	ENKEL_18_22("18-22 enkel.jpg"),
	/**
	 * 
	 */
	DOBBEL_30_38_45("30-38-45 dobbel.jpg"),
	/**
	 * 
	 */
	REKKE_5_ROMS_22_38("Rekke 5 roms 22-38.jpg");

	/**
	 * 
	 */
	private String fileName;

	/**
	 * @param aFileName
	 */
	private SketchEnum(String aFileName) {
		fileName = aFileName;
	}

	/**
	 * @return filnavn
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param aFileName
	 * @return enum basert på filnavn
	 */
	public static SketchEnum getSketchEnum(String aFileName) {
		if (aFileName != null) {
			if (aFileName.equalsIgnoreCase("18-22 dobbel.jpg")) {
				return DOBBEL_18_22;
			} else if (aFileName.equalsIgnoreCase("18-22 enkel.jpg")) {
				return ENKEL_18_22;
			} else if (aFileName.equalsIgnoreCase("30-38-45 dobbel.jpg")) {
				return DOBBEL_30_38_45;
			} else if (aFileName.equalsIgnoreCase("Rekke 5 roms 22-38.jpg")) {
				return REKKE_5_ROMS_22_38;
			}
		}
		return null;
	}

	/**
	 * @return liste med skisser
	 */
	public static List<SketchEnum> getSketchList() {
		ArrayList<SketchEnum> enumList = new ArrayList<SketchEnum>();
		enumList.add(ENKEL_18_22);
		enumList.add(DOBBEL_18_22);
		enumList.add(DOBBEL_30_38_45);
		enumList.add(REKKE_5_ROMS_22_38);
		return enumList;
	}
}
