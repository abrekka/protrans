package no.ugland.utransprod.gui.model;

import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.gui.IconEnum;

/**
 * Enum for rapporter
 * 
 * @author atle.brekka
 * 
 */
public enum ReportEnum {
	/**
	 * 
	 */
	GULVSPON("Gulvspon.jasper", IconEnum.ICON_IGLAND, "Gulvspon"), IGARASJEN("Gulvspon.jasper",
			IconEnum.ICON_IGLAND, "Igrasjen"),
	/**
	 * 
	 */
	TAKSTOL("Takstol.jasper", IconEnum.ICON_IGLAND, "Takstol"),
	/**
	 * 
	 */
	MONTERING("montering_bean.jasper", IconEnum.ICON_IGLAND, "Montering"),
	/**
	 * 
	 */
	NOKKEl("Nokkeltall_bean.jasper", IconEnum.ICON_IGLAND, "Nøkkeltall - Salg/Drift/Transport"),
	/**
	 * 
	 */
	NOKKEl_PRODUCTION("Nokkeltall_produksjon_alt_bean.jasper", IconEnum.ICON_IGLAND,
			"Nøkkeltall - Produksjon"),
	/**
	 * 
	 */
	NOKKEl_ASSEMBLY("Nokkeltall_mont_ok_bean.jasper", IconEnum.ICON_IGLAND,
			"Nøkkeltall - Montering/Økonomi"),
	/**
	 * 
	 */
	FAX("Order_fax.jasper", IconEnum.ICON_IGLAND, "Fax"),
	/**
	 * 
	 */
	DEVIATION("deviation_report.jasper", IconEnum.ICON_IGLAND, "Avvik"),
	/**
	 * 
	 */
	TRANSPORT_LETTER("fraktbrev.jasper", IconEnum.ICON_IGLAND, "Fraktbrev"),
	/**
	 * 
	 */
	PACKLIST("Pakkliste.jasper", IconEnum.ICON_IGLAND, "Pakkliste"),
	/**
	 * 
	 */
	TRANSPORT_COST("Transport_cost.jasper", IconEnum.ICON_IGLAND, "Transportkostnad"),
	/**
	 * 
	 */
	ASSEMBLY("Assembly.jasper", IconEnum.ICON_IGLAND, "Montering"),
	/**
	 * 
	 */
	ASSEMBLY_NY("assembly_ny.jasper", IconEnum.ICON_IGLAND, "Montering"),
	/**
	 * 
	 */
	ACCIDENT("accident.jasper", IconEnum.ICON_IGLAND, "Hendelse/ulykke"),
	/**
	 * 
	 */
	TAKSTOL_INFO("Takstolinfo.jasper", IconEnum.ICON_JATAK, "Takstolinfo"),
	/**
	 * 
	 */
	KORRIGERENDE_TILTAK("korrigerende_tiltak.jasper", IconEnum.ICON_IGLAND, "Korrigerende tiltak"),
	/**
	 * 
	 */
	PRODUCTION_REPORT("produksjon.jasper", IconEnum.ICON_IGLAND, "Produksjon"),
	DELELISTE_KUNDE_REPORT("deleliste_kunde.jasper", IconEnum.ICON_IGLAND, "Deleliste kunde");
	/**
	 * 
	 */
	private String reportFileName;

	/**
	 * 
	 */
	private String reportName;

	/**
	 * 
	 */
	private IconEnum icon;

	/**
	 * @param aReportFileName
	 * @param iconEnum
	 * @param aReportName
	 */
	private ReportEnum(String aReportFileName, IconEnum iconEnum, String aReportName) {
		reportFileName = aReportFileName;
		icon = iconEnum;
		reportName = aReportName;
	}

	/**
	 * @return filnavn
	 */
	public String getReportFileName() {
		return reportFileName;
	}

	/**
	 * @return rapportnavn
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @return katalog for bilde
	 */
	public String getImagePath() {
		return icon.getIconPath();
	}

	/**
	 * @return nøkkelrapporter
	 */
	public static List<ReportEnum> getKeyReports() {
		ArrayList<ReportEnum> reportList = new ArrayList<ReportEnum>();
		reportList.add(NOKKEl);
		reportList.add(NOKKEl_ASSEMBLY);
		reportList.add(NOKKEl_PRODUCTION);
		return reportList;
	}

	/**
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return reportName;
	}
}
