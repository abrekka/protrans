package no.ugland.utransprod.gui;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum for de forskjelige oppstartsvinduene som kan brukes
 * 
 * @author atle.brekka
 * 
 */
public enum StartWindowEnum {
	/**
	 * Hovedvindu
	 */
	PROTRANS_MAIN("no.ugland.utransprod.gui.ProTransMain", "ProtransMain"),
	/**
	 * Takstolproduksjon
	 */
	TAKSTOL_PRODUCTION("no.ugland.utransprod.gui.TakstolProductionWindow",
			"Takstolproduksjon"),
	/**
	 * Takstolpakking
	 */
	TAKSTOL_PACKAGE("no.ugland.utransprod.gui.TakstolPackageWindow",
			"Takstolpakking"),
	/**
	 * Garasjepakking
	 */
	GARAGE_PACKAGE("no.ugland.utransprod.gui.MainPackageWindow",
			"Garasjepakking"),
	/**
	 * Veggproduksjon
	 */
	VEGG_PRODUCTION("no.ugland.utransprod.gui.VeggProductionWindow",
			"Veggproduksjon"),
	/**
	 * Frontproduksjon
	 */
	FRONT_PRODUCTION("no.ugland.utransprod.gui.FrontProductionWindow",
			"Frontproduksjon"),
	/**
	 * Fakturering
	 */
	INVOICE("no.ugland.utransprod.gui.InvoiceWindow", "Fakturering"),
	/**
	 * Pakklister
	 */
	PACKLIST("no.ugland.utransprod.gui.PacklistWindow", "Pakklister"),
	/**
	 * Transport
	 */
	TRANSPORT("no.ugland.utransprod.gui.TransportWindow", "Transport"),
	/**
	 * Avvik
	 */
	DEVIATION("no.ugland.utransprod.gui.DeviationWindow", "Avvik"),
	/**
	 * Gavlproduksjon
	 */
	GAVL_PRODUCTION("no.ugland.utransprod.gui.GavlProductionWindow",
			"Gavlproduksjon"),
	/**
	 * Betaling
	 */
	PAID("no.ugland.utransprod.gui.PaidWindow", "Betaling"),
	/**
	 * Produksjonsoversikt
	 */
	PRODUCTION("no.ugland.utransprod.gui.ProductionOverviewWindow",
			"Produksjonsoversikt");
	/**
	 * 
	 */
	private String className;

	/**
	 * 
	 */
	private String displayName;

	/**
	 * @param aClassName
	 * @param aDisplayName
	 */
	private StartWindowEnum(String aClassName, String aDisplayName) {
		className = aClassName;
		displayName = aDisplayName;
	}

	/**
	 * @return klassenavn
	 */
	public String getClassName() {
		return className;
	}

	/**
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return displayName;
	}

	/**
	 * Finner enum basert på klassenavn
	 * 
	 * @param aClassName
	 * @return oppstartsenum
	 */
	public static StartWindowEnum getStartWindowEnum(String aClassName) {
		if (aClassName != null) {
			if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.ProTransMain")) {
				return PROTRANS_MAIN;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.GavlProductionWindow")) {
				return GAVL_PRODUCTION;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.TakstolProductionWindow")) {
				return TAKSTOL_PRODUCTION;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.TakstolPackageWindow")) {
				return TAKSTOL_PACKAGE;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.MainPackageWindow")) {
				return GARAGE_PACKAGE;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.VeggProductionWindow")) {
				return VEGG_PRODUCTION;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.FrontProductionWindow")) {
				return FRONT_PRODUCTION;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.InvoiceWindow")) {
				return INVOICE;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.PacklistWindow")) {
				return PACKLIST;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.TransportWindow")) {
				return TRANSPORT;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.DeviationWindow")) {
				return DEVIATION;
			} else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.PaidWindow")) {
				return PAID;
			}else if (aClassName
					.equalsIgnoreCase("no.ugland.utransprod.gui.ProductionOverviewWindow")) {
				return PRODUCTION;
			}
		}

		return null;
	}

	/**
	 * Henter alle enum
	 * 
	 * @return alle enum
	 */
	public static List<StartWindowEnum> getAll() {
		List<StartWindowEnum> list = new ArrayList<StartWindowEnum>();
		list.add(PROTRANS_MAIN);
		list.add(GAVL_PRODUCTION);
		list.add(TAKSTOL_PRODUCTION);
		list.add(TAKSTOL_PACKAGE);
		list.add(GARAGE_PACKAGE);
		list.add(VEGG_PRODUCTION);
		list.add(FRONT_PRODUCTION);
		list.add(INVOICE);
		list.add(PACKLIST);
		list.add(TRANSPORT);
		list.add(DEVIATION);
		list.add(PAID);
		list.add(PRODUCTION);
		return list;
	}
}
