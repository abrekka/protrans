package no.ugland.utransprod.gui;

import javax.swing.ImageIcon;

/**
 * Enumer for ikoner
 * 
 * @author abr99
 * 
 */
public enum IconEnum {
    /**
     * Clear ikon
     */
    ICON_CLEAR("images/clear.png"),
    /**
     * OK ikon
     */
    ICON_OK("images/ok.png"),

    /**
     * Avslutt
     */
    ICON_EXIT("images/exit.png"),

    /**
     * Skriv ut
     */
    ICON_PRINT("images/print.png"),

    /**
     * Lagre
     */
    ICON_SAVE("images/save.png"),

    /**
     * Avbryt
     */
    ICON_CANCEL("images/cancel.png"),

    /**
     * Oppdater
     */
    ICON_REFRESH("images/refresh.png"),

    /**
     * Slett
     */
    ICON_DELETE("images/delete.png"),

    /**
     * Excel
     */
    ICON_EXCEL("images/excel.jpeg"),

    /**
     * Søk
     */
    ICON_SEARCH("images/search.png"),

    /**
     * Stort Ugland
     */
    ICON_UGLAND_BIG("images/ugland.jpg"),
    /**
     * Logo med grimstad garasjene tekst
     */
    // ICON_UGLAND_GRIMSTAD_GARASJENE("images/GRIMSTAD_GARASJENE_logo.jpg"),
//    ICON_GRIMSTAD_INDUSTRIER("images/GRIMSTAD_INDUSTRIER_logo.jpg"),
    /**
     * Ikon som brukes på dialoger
     */
    // ICON_UGLAND("images/ugland.png"),
    ICON_UGLAND("images/G.png"),
    /**
	 * 
	 */
    // ICON_UGLAND2("images/ugland2.jpg"),
    ICON_UGLAND2("images/G_stor.png"),

    /**
     * Ikon systemlogg
     */
    ICON_COMMENT("images/comment.png"),
    /**
	 * 
	 */
    ICON_FILTER("images/filter.png"),
    /**
     * Ikon advarsel
     */
    ICON_WARNING("images/warning.png"),
    /**
     * Ikon etterlevering
     */
    ICON_POST_SHIPMENT("images/post_shipment.png"),
    /**
     * Ikon for epost
     */
    ICON_MAIL("images/mail.png"), ICON_CHECKED("images/checked.jpeg"), ICON_UGLAND_TAKSTOL_BYGGELEMENT("images/takstol_byggelement.jpg"), ICON_UTSTIKKTYPE_1(
	    "images/Utstikktype1.jpg"), ICON_UTSTIKKTYPE_2("images/Utstikktype2.jpg"), ICON_UTSTIKKTYPE_3("images/Utstikktype3.jpg"), ICON_UTSTIKKTYPE_4(
	    "images/Utstikktype4.jpg"), ICON_JATAK("images/Jatak.JPG"),ICON_IGLAND("images/igland_garasjen.png"), ;

    /**
     * Ikon
     */
    String iconPath;

    /**
     * Kosntruktør
     * 
     * @param aIconPath
     */
    IconEnum(String aIconPath) {
	iconPath = aIconPath;
    }

    /**
     * Henter ikon
     * 
     * @return ikon
     */
    public ImageIcon getIcon() {
	return new ImageIcon(getClass().getClassLoader().getResource(iconPath));
    }

    /**
     * Henter sti til bildefil
     * 
     * @return sti
     */
    public String getIconPath() {
	return iconPath;
    }
}
