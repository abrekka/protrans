package no.ugland.utransprod.util.excel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.util.Columnable;
import no.ugland.utransprod.util.Util;

/**
 * Enum for rapporttyper for excelrapporter.
 * 
 * @author atle.brekka
 */
public enum ExcelReportEnum {
    /**
     * Produktivitet - pakking.
     */
    PRODUCTIVITY_PACK("Produktivitet - pakking", "Produktivitet_pakking", "nokkelProduksjonVManager", new String[] { "År", "Uke", "Antall", "Verdi",
	    "Budsjett" }, new Integer[] { 0, 1, 2, 3, 4 }, new String[] { "NokkelProduksjonVPK.OrderReadyYear", "NokkelProduksjonVPK.OrderReadyWeek",
	    "CountOrderReady", "PackageSumWeek", "BudgetValue" }, null, 16, null, new Integer[] {}, null, null, null, false, true, 0, null, true,
	    false, null, true, true),
    /**
     * Produktivitet - pakkliste.
     */
    PRODUCTIVITY_PACKLIST("Produktivitet - pakkliste", "Produktivitet_pakkliste", "orderPacklistReadyVManager", new String[] { "År", "Uke", "Antall",
	    "Verdi" }, new Integer[] { 0, 1, 2, 3 }, new String[] { "OrderPacklistReadyVPK.PacklistYear", "OrderPacklistReadyVPK.PacklistWeek",
	    "OrderCount", "CustomerCost" }, null, 16, null, new Integer[] {}, null, null, null, false, true, 0, null, true, false, null, true, true),
    /**
     * Status alle ordre ikke sendt.
     */
    PRODUCTIVITY_ORDER_STATUS("Status alle ordre ikke sendt", "Status_ordre_ikke_sendt", "orderStatusNotSentVManager", new String[] {
	    "Antall ikke pakkliste", "Verdi ikke pakkliste", "Antall ikke pakket", "Verdi ikke pakket", "Antall ikke sent", "Verdi ikke sendt" },
	    new Integer[] { 0, 1, 2, 3, 4, 5 }, new String[] { "CountNoPacklist", "GarageValueNoPacklist", "CountNotReady", "GarageValueNotReady",
		    "CountNotSent", "GarageValueNotSent" }, new Integer[][] { { 0, 7000 }, { 1, 7000 }, { 2, 6000 }, { 3, 6000 }, { 4, 5500 },
		    { 5, 5500 } }, 16, null, new Integer[] {}, null, null, null, false, true, 0, null, true, false, null, false, false),
    /**
     * Produktivitetsdetaljer - ikke fakturert.
     */
    PRODUCTIVITY_LIST_NOT_INVOICED("Produktivitetsdetaljer - ikke fakturert", "Produktivitetsdetaljer_ikke_fakturert", "notInvoicedVManager",
	    new String[] { "Ordre", "Sent", "Verdi" }, new Integer[] { 2 }, new String[] { "OrderDetails", "SentString", "GarageValue" },
	    new Integer[][] { { 0, 15000 }, { 1, 3000 } }, 16, null, new Integer[] {}, null, null, null, false, true, 0, null, true, false, null,
	    false, false),

    OWN_PRODUCTION_SUM("Sum egenproduksjon", "Sum egenproduksjon", "nokkelProduksjonVManager",
	    new String[] { "Område", "Reelt", "Budsjett", "Avvik" }, new Integer[] { 1, 2, 3 }, new String[] { "ProductArea", "PackageSumWeek",
		    "BudgetValue", "BudgetDeviation" }, null, 10, null, new Integer[] {}, null, null, null, false, true, 1, null, false, false, null,
	    false, true), OWN_PRODUCTION_PACKLIST("Egenproduksjon pakklister", "Egenproduksjon pakklister", "ownProductionVManager", new String[] {
	    "Transportuke", "Ordrenr", "Navn", "Verdi", "Dato pakkliste", "Produktområde" }, new Integer[] { 3 }, new String[] { "TransportYearWeek",
	    "OrderNr", "CustomerName", "GarageValue", "PacklistReadyFormat", "ProductArea" }, null, 10, null, new Integer[] {}, null, null, null,
	    false, false, 1, null, false, true, new ExcelSumFormula(new Integer[] { 3 }, "Total sum", null, null, 3), false, true), OWN_PRODUCTION_PACKLIST_NOT_READY(
	    "Pakkliste ikke klar", "Pakkliste_ikke_klar", "ownProductionVManager", new String[] { "Transportuke", "Kundenr", "Navn", "Verdi",
		    "Produktområde" }, new Integer[] { 1, 3 }, new String[] { "TransportYearWeek", "CustomerNr", "CustomerName", "GarageValue",
		    "ProductArea" }, new Integer[][] { { 2, 6500 } }, 10, null, new Integer[] {}, null, null, null, false, false, 1, null, false,
	    true, new ExcelSumFormula(new Integer[] { 3 }, "Total sum", null, null, 3), false, false),
    /**
     * Egenproduksjon.
     */
    OWN_PRODUCTION("Egenproduksjon", "Egenproduksjon", "ownProductionVManager", new String[] { "Att:", "Transport", "Ordrenr", "Navn", "Verdi",
	    "Pakket", "Pr. dag", "Pakkliste", "Prod.uke", "Produktområde" }, new Integer[] { 2, 4 }, new String[] { "PackedBy", "TransportYearWeek",
	    "OrderNr", "CustomerName", "GarageValue", "OrderReadyDayString", "GarageValue", "PacklistReadyFormat", "ProductionWeek", "ProductArea" },
	    new Integer[][] { { 0, 3000 }, { 1, 2000 }, { 2, 2000 }, { 3, 5000 }, { 4, 2000 }, { 5, 2000 }, { 6, 2000 }, { 7, 4000 }, { 8, 4000 } },
	    10, 9, new Integer[] { 9 }, 5, 4, 6, false, true, 0, new ExcelReportEnum[] { OWN_PRODUCTION_SUM, OWN_PRODUCTION_PACKLIST,
		    OWN_PRODUCTION_PACKLIST_NOT_READY }, true, true, null, false, true),
    /**
     * Ettersendinger sent.
     */
    POST_SHIPMENT_SENT_COUNT("Ettersendinger sent", "Ettersendinger_sent", "postShipmentCountVManager", new String[] { "År", "Uke", "Transport",
	    "Salg", "Drift/Prosjektering", "Produksjon", "Underleverandør", "Montering", "Kunde", "Økonomi", "Annet" }, new Integer[] { 0, 1, 2, 3,
	    4, 5, 6, 7, 8, 9 }, new String[] { "År", "Uke", "Transport", "Salg", "Drift/Prosjektering", "Produksjon", "Underleverandør", "Montering",
	    "Kunde", "Økonomi", "Annet" }, new Integer[][] { { 2, 3500 } }, 12, null, new Integer[] {}, null, null, null, true, true, 0, null, true,
	    false, null, true, true),
    /**
     * Telleliste for alle ordre som er pakket men ikke sendt.
     */
    READY_COUNT("Telleliste", "Telleliste", "orderManager", new String[] { "Ordre", "Transport", "Kolli", "Mangler", "Garasjeverdi", "Fraktverdi",
	    "Monteringsverdi" }, new Integer[] { 1, 4, 5, 6 }, new String[] { "OrderString", "Transport", "Colli", "Missing", "GarageValue",
	    "TransportValue", "AssemblyValue" }, new Integer[][] { { 0, 15000 }, { 1, 3000 }, { 2, 3500 }, { 3, 3000 }, { 4, 3500 }, { 5, 4000 },
	    { 6, 3000 } }, 12, null, new Integer[] {}, null, null, null, false, true, 0, null, true, false, null, false, false),
    /**
     * Tranportert i periode.
     */
    TRANSPORT_LIST("Transportert i periode", "Transportert_i_periode", "orderManager", new String[] { "Ordre", "Transport", "Garasjeverdi",
	    "Fraktverdi", "Monteringsverdi" }, new Integer[] { 1, 2, 3, 4 }, new String[] { "OrderString", "TransportWeekString", "GarageValue",
	    "TransportValue", "AssemblyValue" }, new Integer[][] { { 0, 15000 }, { 1, 3000 }, { 2, 3500 }, { 3, 3000 }, { 4, 3500 }, { 5, 4000 },
	    { 6, 3000 } }, 12, null, new Integer[] {}, null, null, null, false, true, 0, null, true, false, null, true, true),
    /**
     * Salgsrapport grunnlag.
     */
    DEVIATION_BASIS("Avvik - grunnlag", "Avvik", "deviationVManager", new String[] { "Id", "Opprettet", "Navn", "Kundenr", "Kundenavn", "Ordrenr",
	    "Produktområde", "Behandlingsansvarlig", "Egen funksjon", "Avviksfunksjon", "Kategori", "Status", "Initiert av", "Intern kostnad",
	    "Ekstern kostnad" }, new Integer[] { 13, 14 }, new String[] { "DeviationId", "RegistrationDate", "UserName", "CustomerNr",
	    "CustomerName", "OrderNr", "ProductArea", "Responsible", "OwnFunction", "DeviationFunction", "FunctionCategoryName",
	    "DeviationStatusName", "InitiatedBy", "InternalCost", "ExternalCost" }, new Integer[][] { { 7, 4500 }, { 8, 4500 }, { 9, 4500 },
	    { 10, 4500 }, { 11, 3500 }, { 12, 4500 }, { 13, 4500 }, { 14, 4500 } }, 12, null, null, null, null, null, false, true, 0, null, true,
	    false, new ExcelSumFormula(new Integer[] { 13, 14 }, "Sum:", null, null, 0), null, true, true, true),
    /**
     * Avvik.
     */
    DEVIATION_SUM("Avvik", "Avvik", "deviationSumVManager", new String[] { "Produktområde", "Avviksfunksjon", "Kategori", "Status", "Uke", "År",
	    "Sum" }, new Integer[] { 4, 5, 6 }, new String[] { "ProductArea", "JobFunctionName", "FunctionCategoryName", "DeviationStatusName",
	    "RegistrationWeek", "RegistrationYear", "SumCost" }, new Integer[][] { { 0, 4500 }, { 1, 4500 }, { 2, 4500 }, { 3, 4500 }, { 4, 3500 },
	    { 5, 4500 }, { 6, 4500 } }, 12, null, new Integer[] {}, null, null, null, true, true, 0, new ExcelReportEnum[] { DEVIATION_BASIS }, true,
	    false, new ExcelSumFormula(new Integer[] { 4, 5, 6 }, "Sum:", null, null, 0), true, true), PACKLIST_NOT_READY("Pakkliste ikke klar",
	    "Pakkliste_ikke_klar", "ownProductionVManager", new String[] { "Transportuke", "Kundenr", "Navn", "Verdi" }, new Integer[] { 1, 3 },
	    new String[] { "TransportYearWeek", "CustomerNr", "CustomerName", "GarageValue" }, new Integer[][] { { 2, 6500 } }, 10, null,
	    new Integer[] {}, null, null, null, false, false, 0, null, false, true, new ExcelSumFormula(new Integer[] { 3 }, "Total sum", null, null,
		    2), false, false),

    DEVIATION_SUMMARY("Avvik - Gjennomgåelse", "Avvik_gjennomgåelse", "sumAvvikVManager", "generateDeviationSummary", false, true, true), DEVIATION_JOB_FUNCTION_SUM(
	    "Avvik pr aviksfunksjon", "Avvik_pr_aviksfunksjon", "deviationSumJobFunctionVManager", "generateDeviationSumJobFunction", false, true,
	    true),

    /**
     * Salgsrapport ordre.
     */
    SALES_REPORT_ORDER("Salgsrapport - ordre", "Salgsrapport", "salesVManager", new String[] { "Fylke", "Antall", "Egenproduksjon", "Frakt",
	    "Montering", "Jalinjer", "DB", "DG" }, new Integer[] { 1, 2, 3, 4, 5, 6 }, new String[] { "CountyName", "OrderCount", "SumOwnProduction",
	    "SumTransport", "SumAssembly", "SumYesLines", "SumDb", "SumDg" }, null, 10, null, null, null, null, null, false, true, 0, null, true,
	    false, new ExcelSumFormula(new Integer[] { 1, 2, 3, 4, 5, 6 }, "Sum:", "G$ROW/C$ROW", 7, 0), null, true, true, true),
    /**
     * Salgsrapport ordre pr selge.
     */
    SALES_REPORT_ORDER_SALESMAN("Salgsrapport - ordre", "Salgsrapport", "salesVManager", new String[] { "Selger", "Antall", "Egenproduksjon",
	    "Frakt", "Montering", "Jalinjer", "DB", "DG" }, new Integer[] { 1, 2, 3, 4, 5, 6 }, new String[] { "Salesman", "OrderCount",
	    "SumOwnProduction", "SumTransport", "SumAssembly", "SumYesLines", "SumDb", "SumDg" }, null, 10, null, null, null, null, null, false,
	    true, 0, null, true, false, new ExcelSumFormula(new Integer[] { 1, 2, 3, 4, 5, 6 }, "Sum:", "G$ROW/C$ROW", 7, 0), null, true, true, true),
    /**
     * Salgsrapport avrop.
     */
    SALES_REPORT_CONFIRMED_ORDER("Salgsrapport - avrop", "Salgsrapport", "salesVManager", new String[] { "Fylke", "Antall", "Egenproduksjon",
	    "Frakt", "Montering", "Jalinjer", "DB", "DG" }, new Integer[] { 1, 2, 3, 4, 5, 6 }, new String[] { "CountyName", "OrderCount",
	    "SumOwnProduction", "SumTransport", "SumAssembly", "SumYesLines", "SumDb", "SumDg" }, null, 10, null, null, null, null, null, false,
	    true, 0, null, true, false, new ExcelSumFormula(new Integer[] { 1, 2, 3, 4, 5, 6 }, "Sum:", "G$ROW/C$ROW", 7, 0), null, true, true, true),
    /**
     * Salgsrapport avrop pr selger.
     */
    SALES_REPORT_CONFIRMED_ORDER_SALESMAN("Salgsrapport - avrop", "Salgsrapport", "salesVManager", new String[] { "Selger", "Antall",
	    "Egenproduksjon", "Frakt", "Montering", "Jalinjer", "DB", "DG" }, new Integer[] { 1, 2, 3, 4, 5, 6 }, new String[] { "Salesman",
	    "OrderCount", "SumOwnProduction", "SumTransport", "SumAssembly", "SumYesLines", "SumDb", "SumDg" }, null, 10, null, null, null, null,
	    null, false, true, 0, null, true, false, new ExcelSumFormula(new Integer[] { 1, 2, 3, 4, 5, 6 }, "Sum:", "G$ROW/C$ROW", 7, 0), null,
	    true, true, true),
    /**
     * Salgsrapport grunnlag.
     */
    SALES_REPORT_BASIS("Salgsrapport - grunnlag", "Salgsrapport", "salesVManager", new String[] { "Type", "Fylke", "Selger", "Salgsdato", "Salgsuke",
	    "Kundenr", "Navn", "Ordrenr", "Egenproduksjon", "Frakt", "Montering", "Jalinjer", "DB", "DG" }, new Integer[] { 4, 8, 9, 10, 11, 12 },
	    new String[] { "Type", "CountyName", "Salesman", "SalesDateString", "SalesWeek", "CustomerNr", "CustomerName", "OrderNr",
		    "OwnProductionCost", "TransportCost", "AssemblyCost", "YesLines", "Db", "Dg" }, null, 10, null, null, null, null, null, false,
	    true, 0, null, true, false, new ExcelSumFormula(new Integer[] { 8, 9, 10, 11, 12 }, "Sum:", "M$ROW/I$ROW", 13, 7), null, true, true, true),
    /**
     * Salgsrapport grunnlag r selger.
     */
    SALES_REPORT_BASIS_SALESMAN("Salgsrapport - grunnlag", "Salgsrapport", "salesVManager", new String[] { "Type", "Fylke", "Selger", "Salgsdato",
	    "Salgsuke", "Kundenr", "Navn", "Ordrenr", "Egenproduksjon", "Frakt", "Montering", "Jalinjer", "DB", "DG" }, new Integer[] { 4, 8, 9, 10,
	    11, 12 }, new String[] { "Type", "CountyName", "Salesman", "SalesDateString", "SalesWeek", "CustomerNr", "CustomerName", "OrderNr",
	    "OwnProductionCost", "TransportCost", "AssemblyCost", "YesLines", "Db", "Dg" }, null, 10, null, null, null, null, null, false, true, 0,
	    null, true, false, new ExcelSumFormula(new Integer[] { 8, 9, 10, 11, 12 }, "Sum:", "M$ROW/I$ROW", 13, 7), null, true, true, true),

    /**
     * Salgsrapport.
     */
    SALES_REPORT("Salgsrapport", "Salgsrapport", "salesVManager", new String[] { "Fylke", "Antall", "Egenproduksjon", "Frakt", "Montering",
	    "Jalinjer", "DB", "DG" }, new Integer[] { 1, 2, 3, 4, 5, 6 }, new String[] { "CountyName", "OrderCount", "SumOwnProduction",
	    "SumTransport", "SumAssembly", "SumYesLines", "SumDb", "SumDg" }, new Integer[][] { { 0, 3000 }, { 1, 3000 }, { 2, 3000 }, { 3, 3000 },
	    { 4, 3000 }, { 5, 4000 }, { 6, 3000 }, { 6, 3000 } }, 10, null, null, null, null, null, false, true, 0, new ExcelReportEnum[] {
	    SALES_REPORT_ORDER, SALES_REPORT_CONFIRMED_ORDER, SALES_REPORT_BASIS }, true, false, new ExcelSumFormula(
	    new Integer[] { 1, 2, 3, 4, 5, 6 }, "Sum:", "G$ROW/C$ROW", 7, 0), null, true, true, true),
    /**
     * Salgsrapport pr selger.
     */
    SALES_REPORT_SALESMAN("Salgsrapport", "Salgsrapport", "salesVManager", new String[] { "Selger", "Antall", "Egenproduksjon", "Frakt", "Montering",
	    "Jalinjer", "DB", "DG" }, new Integer[] { 1, 2, 3, 4, 5, 6 }, new String[] { "Salesman", "OrderCount", "SumOwnProduction",
	    "SumTransport", "SumAssembly", "SumYesLines", "SumDb", "SumDg" }, new Integer[][] { { 0, 3000 }, { 1, 3000 }, { 2, 3000 }, { 3, 3000 },
	    { 4, 3000 }, { 5, 4000 }, { 6, 3000 }, { 6, 3000 } }, 10, null, null, null, null, null, false, true, 0, new ExcelReportEnum[] {
	    SALES_REPORT_ORDER_SALESMAN, SALES_REPORT_CONFIRMED_ORDER_SALESMAN, SALES_REPORT_BASIS_SALESMAN }, true, false, new ExcelSumFormula(
	    new Integer[] { 1, 2, 3, 4, 5, 6 }, "Sum:", "G$ROW/C$ROW", 7, 0), null, true, true, true),
    /**
     * Tegningsrapport grunnlag.
     */
    DRAWER_REPORT_BASIS("Tegningsrapport - grunnlag", "Tegningsrapport", "drawerVManager", new String[] { "Kundenr", "Ordrenr", "Produktområde",
	    "Kundenavn", "Kategori", "Registert", "Egenproduksjon", "Tegner" }, new Integer[] { 0, 6 }, new String[] { "CustomerNr", "OrderNr",
	    "ProductArea", "CustomerName", "CategoryName", "RegisteredDate", "OwnProduction", "DrawerName" }, null, 10, null, null, null, null, null,
	    false, true, 0, null, true, false, null, null, true, true, true),
    /**
     * Tegnerrapport
     */
    DRAWER_REPORT("Tegningsrapport", "Tegningsrapport", "drawerVManager", new String[] { "Kategori", "Produktområde", "Tegner", "Antall",
	    "Sum egenproduksjon" }, new Integer[] { 4, 5 },
	    new String[] { "CategoryName", "ProductArea", "DrawerName", "Count", "SumOwnProduction" }, new Integer[][] { { 0, 3000 }, { 1, 3000 },
		    { 2, 3000 }, { 3, 3000 }, { 4, 3000 } }, 10, null, null, null, null, null, false, true, 0,
	    new ExcelReportEnum[] { DRAWER_REPORT_BASIS }, true, false, null, null, true, true, true),
    /**
     * Grunnlag for apport egenordre takstol.
     */
    TAKSTOL_OWN_ORDER_BASIS("Grunnlag Egenordre takstol", "Grunnlag Egenordre takstol", "orderLineManager", new String[] { "Produktområde",
	    "Ordrenr", "Kunde", "Egenproduksjon", "Kost takstol" }, new Integer[] { 3, 4 }, new String[] { "ProductArea", "OrderNr", "Customer",
	    "OwnProductionTakstol", "InternalCostTakstol" }, new Integer[][] { { 0, 3000 }, { 1, 3000 }, { 2, 3000 } }, 10, null, null, null, null,
	    null, false, true, 0, null, true, false, null, null, true, true, false),
    /**
     * Rapport egenordre takstol.
     */
    TAKSTOL_OWN_ORDER_REPORT("Egenordre takstol", "Egenordre takstol", "orderLineManager", new String[] { "Produktområde", "Antall",
	    "Egenproduksjon", "Kost takstol" }, new Integer[] { 1, 2, 3 }, new String[] { "ProductArea", "NumberOfOrders", "SumOwnProduction",
	    "SumInternalCost" }, new Integer[][] { { 0, 3000 }, { 1, 3000 }, { 2, 3000 }, { 3, 3000 } }, 10, null, null, null, null, null, false,
	    true, 0, new ExcelReportEnum[] { TAKSTOL_OWN_ORDER_BASIS }, true, false, new ExcelSumFormula(new Integer[] { 1, 2, 3 }, "Sum:", null,
		    null, 0), null, true, true, false),
    /**
     * Rapport salgsstatus order(tilbud).
     */
    SALE_STATUS_REPORT("Salgsstatus", "Salgsstatus", "saleStatusVManager", new String[] { "Kundenr", "Navn", "Ordrenr", "Beløp", "Sannsynlig",
	    "Dato", "År", "Uke", "Byggestart", "Salgsstatus", "Selger", "Produktområde" }, new Integer[] { 0, 3 }, new String[] { "CustomerNr",
	    "CustomerName", "OrderNr", "Amount", "Probability", "SaleDateAsDate", "SaleYear", "SaleWeek", "BuildDateAsDate", "SaleStatus",
	    "Salesman", "ProductArea" }, new Integer[][] { { 0, 2000 }, { 1, 5000 }, { 2, 2000 }, { 4, 3000 }, { 5, 3000 }, { 8, 3000 }, { 9, 3000 },
	    { 10, 3000 }, { 11, 4000 } }, 10, null, null, null, null, null, false, true, 0, null, true, false, new ExcelSumFormula(
	    new Integer[] { 3 }, "Sum:", null, null, 0), null, true, true, true), JIGG_REPORT("Jiggrapport", "Jiggrapport", "takstolAllVManager",
	    "generateJiggReport", true, true, false), ORDRERESERVE_TAKSTOL("Ordrereserve takstol", "Ordrereserve takstol",
	    "takstolProbability90VManager", "generateOrdrereserveTakstolReport", false, false, false)
    /*
     * ORDRERESERVE_TAKSTOL_BASIS( "Ordrereserve takstol",
     * "Ordrereserve takstol", "importOrderProb90VManager", new String[] {
     * "Type", "Avdeling", "Kundenr", "Navn", "Ordrenr","Egenproduksjon","Frakt"
     * }, new Integer[] { 5, 6 }, new String[] { "Type", "ProductAreaGroup",
     * "CustomerNr", "CustomerName", "OrderNr","OwnProduction","DeliveryCost" },
     * new Integer[][] { { 0, 3000 }, { 1, 3000 }, { 2, 3000 } }, 10, null,
     * null, null, null, null, false, true, 0, null, true, false, null, null,
     * false, false, false)
     */, SALES_GOAL("Salgsmål", "Salgsmål", "salesVManager", "generateSalesGoalReport", false, true, false), TAKSTOLTEGNER("Takstoltegner",
	    "Takstoltegner", "takstoltegnerVManager", "generateTakstoltegnerReport", true, true, false);

    private final String reportName;

    private final String excelFileName;

    private final String excelManagerName;

    private final String[] columnNames;

    private final List<Integer> numCols = new ArrayList<Integer>();

    private final transient ExcelReportGenerator excelReportGenerator;

    private final String[] getters;

    private final Map<Integer, Integer> colSize = new Hashtable<Integer, Integer>();

    private Integer headFontSize;

    private Integer groupColumn;

    private final List<Integer> notVisibleColumns = new ArrayList<Integer>();

    private Integer groupSumValueColumn;

    private Integer groupSumColumn;

    private Integer groupResultColumn;

    private boolean useDynamicColumns = false;

    private boolean wrapText;

    private int startCell;

    private ExcelReportEnum[] subReports;

    private boolean allwaysShowHeading;

    private ExcelSumFormula sumFormula;

    /**
     * True dersom det skal skrives ut radnummer for hver rad.
     */
    private boolean writeRowNumber = false;

    private String generateMethodName = null;

    private boolean useTo;

    private boolean useFrom;

    private boolean useProductArea;

    private ExcelReportEnum(final String aName, final String aFileName, final String aManager, final String aMethodName, final boolean doUseTo,
	    final boolean doUseFrom, final boolean shouldUseProductArea) {
	this(aName, aFileName, aManager, null, null, null, null, null, null, null, null, null, null, false, false, 0, null, false, false, null,
		aMethodName, doUseTo, doUseFrom, shouldUseProductArea);
    }

    private ExcelReportEnum(final String aName, final String aFileName, final String aManager, final String[] colNames, final Integer[] numArray,
	    final String[] columnGetters, final Integer[][] columnSizeArray, final Integer fontSizeHead, final Integer aGroupColumn,
	    final Integer[] notVisibleColumnArray, final Integer aGroupSumValueColumn, final Integer aGroupSumColumn,
	    final Integer aGroupResultColumn, final boolean dynamicColumns, final boolean useWrapText, final int aStartCell,
	    final ExcelReportEnum[] someSubReports, final boolean shouldAllwaysShowHeading, final boolean shouldWriteRowNumber,
	    final ExcelSumFormula aFormula, final boolean doUseTo, final boolean doUseFrom) {
	this(aName, aFileName, aManager, colNames, numArray, columnGetters, columnSizeArray, fontSizeHead, aGroupColumn, notVisibleColumnArray,
		aGroupSumValueColumn, aGroupSumColumn, aGroupResultColumn, dynamicColumns, useWrapText, aStartCell, someSubReports,
		shouldAllwaysShowHeading, shouldWriteRowNumber, aFormula, null, doUseTo, doUseFrom, true);
    }

    private ExcelReportEnum(final String aName, final String aFileName, final String aManager, final String[] colNames, final Integer[] numArray,
	    final String[] columnGetters, final Integer[][] columnSizeArray, final Integer fontSizeHead, final Integer aGroupColumn,
	    final Integer[] notVisibleColumnArray, final Integer aGroupSumValueColumn, final Integer aGroupSumColumn,
	    final Integer aGroupResultColumn, final boolean dynamicColumns, final boolean useWrapText, final int aStartCell,
	    final ExcelReportEnum[] someSubReports, final boolean shouldAllwaysShowHeading, final boolean shouldWriteRowNumber,
	    final ExcelSumFormula aFormula, final String methodName, final boolean doUseTo, final boolean doUseFrom,
	    final boolean shouldUseProductArea) {
	generateMethodName = methodName;
	sumFormula = aFormula;
	writeRowNumber = shouldWriteRowNumber;
	reportName = aName;
	excelFileName = aFileName;
	excelManagerName = aManager;

	columnNames = colNames != null ? colNames.clone() : null;

	if (numArray != null) {
	    Collections.addAll(numCols, numArray);
	}
	excelReportGenerator = new ExcelReportGenerator(this);
	getters = columnGetters != null ? columnGetters.clone() : null;
	Util.fillMap(colSize, columnSizeArray);
	headFontSize = fontSizeHead;
	groupColumn = aGroupColumn;
	if (notVisibleColumnArray != null) {
	    Collections.addAll(notVisibleColumns, notVisibleColumnArray);
	}
	groupSumValueColumn = aGroupSumValueColumn;
	groupSumColumn = aGroupSumColumn;
	groupResultColumn = aGroupResultColumn;
	useDynamicColumns = dynamicColumns;
	wrapText = useWrapText;
	startCell = aStartCell;
	subReports = someSubReports != null ? someSubReports : null;
	allwaysShowHeading = shouldAllwaysShowHeading;
	this.useTo = doUseTo;
	this.useFrom = doUseFrom;
	this.useProductArea = shouldUseProductArea;
    }

    public int getStartCell() {
	return startCell;
    }

    public boolean isWrapText() {
	return wrapText;
    }

    /**
     * @return resultatkolonne for grupperingssummering
     */
    public Integer getGroupResultColumn() {
	return groupResultColumn;
    }

    /**
     * @return kolonne som skal summeres ved gruppering
     */
    public Integer getGroupSumColumn() {
	return groupSumColumn;
    }

    /**
     * @return kolonne som skal grupperes og summeres
     */
    public Integer getGroupSumValueColumn() {
	return groupSumValueColumn;
    }

    /**
     * @return kolonner som ikke skal vises
     */
    public List<Integer> getNotVisibleColumns() {
	return notVisibleColumns;
    }

    /**
     * @return grupperingskolonne
     */
    public Integer getGroupColumn() {
	return groupColumn;
    }

    /**
     * @return fontstørrelse på overskrift
     */
    public int getHeadFontSize() {
	return headFontSize;
    }

    /**
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
	return reportName;
    }

    /**
     * @return rapportnavn
     */
    public String getReportName() {
	return reportName;
    }

    /**
     * @return excelfilnavn
     */
    public String getExcelFileName() {
	return excelFileName;
    }

    /**
     * @return navn på manager som skal hente data
     */
    public String getExcelManagerName() {
	return excelManagerName;
    }

    /**
     * @param objectList
     * @return kolonnenavn
     */
    public String[] getColumnNames(final List<?> objectList) {
	if (useDynamicColumns) {
	    if (objectList != null && objectList.size() > 0) {
		return ((Columnable) objectList.get(0)).getColumnNames();
	    }
	}
	return columnNames;
    }

    /**
     * @return tallkolonner
     */
    public List<Integer> getNumCols() {
	return numCols;
    }

    /**
     * @return rapportgenrator
     */
    public ExcelReportGenerator getExcelReportGenerator() {
	return excelReportGenerator;
    }

    /**
     * @return kolonnestørrelser
     */
    public Map<Integer, Integer> getColumnSizes() {
	return colSize;
    }

    /**
     * @param object
     * @param column
     * @return henter verdit for dynamisk kolonne
     */
    private Object getValueForDynamicColumn(final Object object, final Integer column) {
	return ((Columnable) object).getValueForColumn(column);
    }

    /**
     * @param object
     * @param column
     * @return verdi for kolonne med gitt objekt
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public Object getValueForColumn(final Object object, final Integer column) throws NoSuchMethodException, IllegalAccessException,
	    InvocationTargetException {
	if (useDynamicColumns) {
	    return getValueForDynamicColumn(object, column);
	}
	Object returnObject = null;
	String methodName = getters[column];
	String[] methods = methodName.split("\\.");
	for (String method : methods) {
	    returnObject = getResult(object, method);

	}
	return returnObject;
    }

    /**
     * @param object
     * @param methodName
     * @return resultat fra metodekall
     * @throws SecurityException
     * @throws NoSuchMethodException
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private Object getResult(final Object object, final String methodName) throws NoSuchMethodException, IllegalAccessException,
	    InvocationTargetException {
	Method callMethod = object.getClass().getMethod("get" + methodName, (Class[]) null);
	return callMethod.invoke(object, (Object[]) null);
    }

    /**
     * @return produktivitetsrapporter
     */
    public static List<ExcelReportEnum> getProductivityReports() {
	return Arrays.asList(new ExcelReportEnum[] { PRODUCTIVITY_PACK, PRODUCTIVITY_PACKLIST });
    }

    public List<ExcelReportEnum> getSubReports() {
	if (subReports != null) {
	    return Arrays.asList(subReports);
	}
	return null;
    }

    public boolean isAllwaysShowHeading() {
	return allwaysShowHeading;
    }

    public boolean getWriteRowNumber() {
	return writeRowNumber;
    }

    public ExcelSumFormula getSumFormula() {
	return sumFormula;
    }

    public String getGenerateMethodName() {
	return generateMethodName;
    }

    public boolean useTo() {
	return useTo;
    }

    public boolean useFrom() {
	return useFrom;
    }

    public boolean useProductArea() {
	return useProductArea;
    }
}
