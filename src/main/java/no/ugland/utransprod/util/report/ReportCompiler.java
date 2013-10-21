package no.ugland.utransprod.util.report;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;

public final class ReportCompiler {
    private ReportCompiler() {

    }

    /**
     * Kompilerer rapport, må kjøres dersom det er gjort endringer på layout.
     * @param doCompile
     */
    @SuppressWarnings("unused")
    public static void compileReports(final boolean doCompile) {
        if (doCompile) {

            // compileGulvspon();
            // compileTakstol();
            compileMonteringsuke();
            compileMontering();
            
            // compileNokkeltallOkonomi();
            //compileNokkeltallTransport();
            //compileNokkeltall();
            // compileNokkeltallSalg();
            //compileNokkeltallProduksjon();
            // compileNokkeltallDriftProsjektering();
            // compileNokkeltallOrderReserve();
            // compileNokkeltallOkonomiSub();
            // compileNokkeltallMontering();
            // compileNokkeltallMonteringOkonomi();

            // compileOrderFax();

            // compileDeviationReportBean();

            // compileDeviationCommentsReport();
            // compileDeviationArticlesReport();
            // compileDeviationCostsReport();
            // compileDeviationReportBean();

            //compileFraktbrev();
            // compileSkisse();
            //compilePakkliste();
            // compileTransportCost();
            // compileAssembly();
             //compileAccident();

        }

    }

    @SuppressWarnings("unused")
    private static void compileGulvspon() {

        try {

            JasperCompileManager.compileReportToFile("reports/Gulvspon.jrxml",
                    "src/main/resources/reports/Gulvspon.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private static void compileAssembly() {

        try {

            JasperCompileManager.compileReportToFile("reports/Assembly.jrxml",
                    "src/main/resources/reports/Assembly.jasper");

            JasperCompileManager.compileReportToFile(
                    "reports/visma_order_lines.jrxml",
                    "src/main/resources/reports/Visma_order_lines.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }
    @SuppressWarnings("unused")
    private static void compileAccident() {

        try {

            JasperCompileManager.compileReportToFile("reports/accident.jrxml",
                    "src/main/resources/reports/Accident.jasper");
            
            JasperCompileManager.compileReportToFile("reports/accident_participants.jrxml",
            "src/main/resources/reports/Accident_participants.jasper");

        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private static void compileTakstol() {

        try {

            JasperCompileManager.compileReportToFile("reports/Takstol.jrxml",
                    "src/main/resources/reports/Takstol.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere monteringsrapport.
     */
    @SuppressWarnings("unused")
    private static void compileMontering() {

        try {

            JasperCompileManager.compileReportToFile("reports/Montering_bean.jrxml",
                    "src/main/resources/reports/Montering_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere monteringsukerapport.
     */
    @SuppressWarnings("unused")
    private static void compileMonteringsuke() {

        try {

            JasperCompileManager.compileReportToFile(
                    "reports/Monteringsuke_bean.jrxml",
                    "src/main/resources/reports/Monteringsuke_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere nøkkeltall for økonomi.
     */
    @SuppressWarnings("unused")
    private static void compileNokkeltallOkonomi() {

        try {

            JasperCompileManager
                    .compileReportToFile(
                            "reports/Nokkeltall_okonomi_bean.jrxml",
                            "src/main/resources/reports/Nokkeltall_okonomi_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Kompilerere nøkkeltall for transport.
     */
    @SuppressWarnings("unused")
    private static void compileNokkeltallTransport() {
        try {

            JasperCompileManager
                    .compileReportToFile(
                            "reports/Nokkeltall_transport_bean.jrxml",
                            "src/main/resources/reports/Nokkeltall_transport_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Kompilerere nøkkeltall for salg.
     */
    @SuppressWarnings("unused")
    private static void compileNokkeltallSalg() {
        try {

            JasperCompileManager.compileReportToFile(
                    "reports/Nokkeltall_salg_bean.jrxml",
                    "src/main/resources/reports/Nokkeltall_salg_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Kompilerere nøkkeltall for produksjon.
     */
    @SuppressWarnings("unused")
    private static void compileNokkeltallProduksjon() {
        try {

            JasperCompileManager
                    .compileReportToFile(
                            "reports/Nokkeltall_produksjon_bean.jrxml",
                            "src/main/resources/reports/Nokkeltall_produksjon_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

        try {

            JasperCompileManager
                    .compileReportToFile(
                            "reports/Nokkeltall_produksjon_alt_bean.jrxml",
                            "src/main/resources/reports/Nokkeltall_produksjon_alt_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere nøkkeltall for drift og prosjektering.
     */
    @SuppressWarnings("unused")
    private static void compileNokkeltallDriftProsjektering() {
        try {

            JasperCompileManager
                    .compileReportToFile(
                            "reports/Nokkeltall_drift_prosjektering_bean.jrxml",
                            "src/main/resources/reports/Nokkeltall_drift_prosjektering_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere nøkkeltall.
     */
    @SuppressWarnings("unused")
    private static void compileNokkeltall() {
        try {
            JasperCompileManager.compileReportToFile(
                    "reports/Nokkeltall_bean.jrxml",
                    "src/main/resources/reports/Nokkeltall_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere ordrefax.
     */
    @SuppressWarnings("unused")
    private static void compileOrderFax() {

        try {

            JasperCompileManager.compileReportToFile("reports/order_fax.jrxml",
                    "src/main/resources/reports/Order_fax.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere avviksrapport.
     */
    @SuppressWarnings("unused")
    private static void compileDeviationReportBean() {

        try {

            JasperCompileManager.compileReportToFile(
                    "reports/deviation_report_bean.jrxml",
                    "src/main/resources/reports/Deviation_report.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere avvikskommentarer.
     */
    @SuppressWarnings("unused")
    private static void compileDeviationCommentsReport() {

        try {

            JasperCompileManager.compileReportToFile(
                    "reports/deviation_comments.jrxml",
                    "src/main/resources/reports/Deviation_comments.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere avviksartikler.
     */
    @SuppressWarnings("unused")
    private static void compileDeviationArticlesReport() {

        try {

            JasperCompileManager.compileReportToFile(
                    "reports/deviation_articles.jrxml",
                    "src/main/resources/reports/Deviation_articles.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere avvikskostnader.
     */
    @SuppressWarnings("unused")
    private static void compileDeviationCostsReport() {

        try {

            JasperCompileManager.compileReportToFile(
                    "reports/deviation_costs.jrxml",
                    "src/main/resources/reports/Deviation_costs.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere fraktbrev.
     */
    @SuppressWarnings("unused")
    private static void compileFraktbrev() {

        try {

            JasperCompileManager.compileReportToFile("reports/fraktbrev.jrxml",
                    "src/main/resources/reports/Fraktbrev.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    @SuppressWarnings("unused")
    private static void compileTransportCost() {

        try {

            JasperCompileManager.compileReportToFile(
                    "reports/transport_cost.jrxml",
                    "src/main/resources/reports/Transport_cost.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

        try {

            JasperCompileManager
                    .compileReportToFile(
                            "reports/transport_cost_order_cost.jrxml",
                            "src/main/resources/reports/Transport_cost_order_cost.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere skisse.
     */
    @SuppressWarnings("unused")
    private static void compileSkisse() {

        try {

            JasperCompileManager.compileReportToFile("reports/skisse.jrxml",
                    "src/main/resources/reports/Skisse.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    /**
     * Kompilerere ordrereserve.
     */
    @SuppressWarnings("unused")
    private static void compileNokkeltallOrderReserve() {
        try {

            JasperCompileManager
                    .compileReportToFile(
                            "reports/nokkeltall_order_reserve_bean.jrxml",
                            "src/main/resources/reports/Nokkeltall_order_reserve_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Kompilerere nøkkeltall.
     */
    @SuppressWarnings("unused")
    private static void compileNokkeltallOkonomiSub() {

        try {

            JasperCompileManager.compileReportToFile(
                    "reports/nokkeltall_okonomi_sub.jrxml",
                    "src/main/resources/reports/Nokkeltall_okonomi_sub.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

    @SuppressWarnings("unused")
    private static void compileNokkeltallMontering() {
        try {

            JasperCompileManager
                    .compileReportToFile(
                            "reports/Nokkeltall_montering_bean.jrxml",
                            "src/main/resources/reports/Nokkeltall_montering_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    @SuppressWarnings("unused")
    private static void compileNokkeltallMonteringOkonomi() {
        try {

            JasperCompileManager
                    .compileReportToFile(
                            "reports/Nokkeltall_mont_ok_bean.jrxml",
                            "src/main/resources/reports/Nokkeltall_mont_ok_bean.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * Kompilerere pakkliste.
     */
    @SuppressWarnings("unused")
    private static void compilePakkliste() {

        try {

            JasperCompileManager.compileReportToFile("reports/pakkliste.jrxml",
                    "src/main/resources/reports/Pakkliste.jasper");
        } catch (JRException e1) {
            e1.printStackTrace();
        }

    }

}
