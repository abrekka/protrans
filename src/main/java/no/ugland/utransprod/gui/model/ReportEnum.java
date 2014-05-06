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
    GULVSPON("Gulvspon.jasper", IconEnum.ICON_UGLAND_BIG, "Gulvspon"),
    /**
	 * 
	 */
    TAKSTOL("Takstol.jasper", IconEnum.ICON_UGLAND_BIG, "Takstol"),
    /**
	 * 
	 */
    MONTERING("Montering_bean.jasper", IconEnum.ICON_UGLAND_BIG, "Montering"),
    /**
	 * 
	 */
    NOKKEl("Nokkeltall_bean.jasper", IconEnum.ICON_UGLAND_BIG, "Nøkkeltall - Salg/Drift/Transport"),
    /**
	 * 
	 */
    NOKKEl_PRODUCTION("Nokkeltall_produksjon_alt_bean.jasper", IconEnum.ICON_UGLAND_BIG, "Nøkkeltall - Produksjon"),
    /**
	 * 
	 */
    NOKKEl_ASSEMBLY("Nokkeltall_mont_ok_bean.jasper", IconEnum.ICON_UGLAND_BIG, "Nøkkeltall - Montering/Økonomi"),
    /**
	 * 
	 */
    FAX("Order_fax.jasper", IconEnum.ICON_GRIMSTAD_INDUSTRIER, "Fax"),
    /**
	 * 
	 */
    DEVIATION("Deviation_report.jasper", IconEnum.ICON_UGLAND_BIG, "Avvik"),
    /**
	 * 
	 */
    TRANSPORT_LETTER("Fraktbrev.jasper", IconEnum.ICON_GRIMSTAD_INDUSTRIER, "Fraktbrev"),
    /**
	 * 
	 */
    PACKLIST("Pakkliste.jasper", IconEnum.ICON_GRIMSTAD_INDUSTRIER, "Pakkliste"), TRANSPORT_COST("Transport_cost.jasper",
	    IconEnum.ICON_GRIMSTAD_INDUSTRIER, "Transportkostnad"), ASSEMBLY("Assembly.jasper", IconEnum.ICON_GRIMSTAD_INDUSTRIER, "Montering"), ASSEMBLY_NY(
	    "assembly_ny.jasper", IconEnum.ICON_GRIMSTAD_INDUSTRIER, "Montering"), ACCIDENT("Accident.jasper", IconEnum.ICON_GRIMSTAD_INDUSTRIER,
	    "Hendelse/ulykke"), TAKSTOL_INFO("Takstolinfo.jasper", IconEnum.ICON_JATAK, "Takstolinfo");
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
