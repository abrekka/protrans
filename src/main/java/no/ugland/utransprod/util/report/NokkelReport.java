package no.ugland.utransprod.util.report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import no.ugland.utransprod.model.NokkelDriftProsjekteringV;
import no.ugland.utransprod.model.NokkelMonteringV;
import no.ugland.utransprod.model.NokkelOkonomiV;
import no.ugland.utransprod.model.NokkelProduksjonV;
import no.ugland.utransprod.model.NokkelSalgV;
import no.ugland.utransprod.model.NokkelTransportV;
import no.ugland.utransprod.model.OrderReserveV;
import no.ugland.utransprod.service.NokkelDriftProsjekteringVManager;
import no.ugland.utransprod.service.NokkelMonteringVManager;
import no.ugland.utransprod.service.NokkelOkonomiVManager;
import no.ugland.utransprod.service.NokkelProduksjonVManager;
import no.ugland.utransprod.service.NokkelSalgVManager;
import no.ugland.utransprod.service.NokkelTransportVManager;
import no.ugland.utransprod.service.OrderReserveVManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.YearWeek;

/**
 * Håndterer data for nøkkeltallsrapport.
 * @author atle.brekka
 */
public class NokkelReport {
    /**
     * År og uke valgt for rapport.
     */
    private YearWeek currentYearWeek;

    /**
     * Rapport fra år og uke.
     */
    private YearWeek fromYearWeek;

    /**
     * År uke minus 2.
     */
    private YearWeek yearWeekN2;

    /**
     * År uke minus 1.
     */
    private YearWeek yearWeekN1;

    /**
     * År uke pluss en.
     */
    private YearWeek toYearWeek;

    /**
     * Valgt produktområde.
     */
    private String productArea;

    /**
     * Data for salgsrapport.
     */
    private Map<YearWeek, NokkelSalgV> nokkelSalgMap;

    /**
     * Akkumulerte tall for salg.
     */
    private NokkelSalgV nokkelSalgVAllYear;

    /**
     * Data for drift og prosjektering.
     */
    private Map<YearWeek, NokkelDriftProsjekteringV> nokkelDriftMap;

    /**
     * Akkumulerte tall for drift og prosjektering.
     */
    private NokkelDriftProsjekteringV nokkelDriftVAllYear;

    /**
     * Data for transport.
     */
    private Map<YearWeek, NokkelTransportV> nokkelTransportMap;

    /**
     * Akkumulerte tall for transport.
     */
    private NokkelTransportV nokkelTransportVAllYear;

    /**
     * Data for montering.
     */
    private Map<YearWeek, NokkelMonteringV> nokkelMonteringMap;

    /**
     * Akkumulerte tall for montering.
     */
    private NokkelMonteringV nokkelMonteringVAllYear;

    /**
     * Data for økonomi.
     */
    private Map<YearWeek, NokkelOkonomiV> nokkelOkonomiMap;

    /**
     * Akkumulerte tall for økonomi.
     */
    private NokkelOkonomiV nokkelOkonomiVAllYear;

    /**
     * Data for produksjon.
     */
    private Map<YearWeek, NokkelProduksjonV> nokkelProduksjonMap;

    /**
     * Akkumulerte tall for produksjon.
     */
    private NokkelProduksjonV nokkelProduksjonVAllYear;

    private List<OrderReserveV> orderReserveList;

    /**
     * @param year
     * @param week
     * @param aProductArea
     */
    public NokkelReport(final Integer year, final Integer week,
            final String aProductArea) {
        productArea = aProductArea;
        currentYearWeek = new YearWeek(year, week);

        fromYearWeek = Util.addWeek(currentYearWeek, -3);
        yearWeekN2 = Util.addWeek(currentYearWeek, -2);
        yearWeekN1 = Util.addWeek(currentYearWeek, -1);
        toYearWeek = Util.addWeek(currentYearWeek, 1);

    }

    /**
     * @return rapportår
     */
    public final Integer getReportYear() {
        return currentYearWeek.getYear();
    }

    /**
     * @return rapportuke
     */
    public final Integer getReportWeek() {
        return currentYearWeek.getWeek();
    }

    /**
     * @return produktområde
     */
    public final String getProductArea() {
        return productArea;
    }

    /**
     * @return uke minus 3
     */
    public final Integer getWeekN3() {
        return fromYearWeek.getWeek();
    }

    /**
     * @return uke minus 2
     */
    public final Integer getWeekN2() {
        return yearWeekN2.getWeek();
    }

    /**
     * @return uke minus 1
     */
    public final Integer getWeekN1() {
        return yearWeekN1.getWeek();
    }

    /**
     * @return uke pluss en
     */
    public final Integer getWeek1N() {
        return toYearWeek.getWeek();
    }

    public final List<NokkelSalgV> getNokkelTallSalgWeek(final YearWeek yearWeek) {
        initReportSalgDriftTransport();
        NokkelSalgV sale = nokkelSalgMap.get(yearWeek);
        if (sale != null) {
            return Arrays.asList(new NokkelSalgV[] {sale});
        }
        return null;
    }

    /**
     * @return data for salg uke minus 3
     */
    public final List<NokkelSalgV> getNokkelTallSalgWeekN3() {
        return getNokkelTallSalgWeek(fromYearWeek);
    }

    /**
     * @return data for salg uke minus 2
     */
    public final List<NokkelSalgV> getNokkelTallSalgWeekN2() {
        return getNokkelTallSalgWeek(yearWeekN2);
    }

    /**
     * @return data for salg uke minus 1
     */
    public final List<NokkelSalgV> getNokkelTallSalgWeekN1() {
        return getNokkelTallSalgWeek(yearWeekN1);
    }

    /**
     * @return data for salg gjeldende uke
     */
    public final List<NokkelSalgV> getNokkelTallSalgWeekN() {
        return getNokkelTallSalgWeek(currentYearWeek);
    }

    /**
     * @return akkumulerte data for salg
     */
    public final List<NokkelSalgV> getNokkelTallSalgAggregate() {
        initReportSalgDriftTransport();

        if (nokkelSalgVAllYear != null) {
            return Arrays.asList(new NokkelSalgV[] {nokkelSalgVAllYear});
        }
        return null;
    }

    /**
     * @return data for salg uke pluss en
     */
    public final List<NokkelSalgV> getNokkelTallSalgWeek1N() {
        return getNokkelTallSalgWeek(toYearWeek);
    }

    public final List<NokkelDriftProsjekteringV> getNokkelTallDriftProsjektering(
            final YearWeek yearWeek) {
        initReportSalgDriftTransport();
        NokkelDriftProsjekteringV drift = nokkelDriftMap.get(yearWeek);
        if (drift != null) {
            return Arrays.asList(new NokkelDriftProsjekteringV[] {drift});
        }
        return null;
    }

    /**
     * @return data for drift og prosjektering uke minus 3
     */
    public final List<NokkelDriftProsjekteringV> getNokkelTallDriftProsjekteringWeekN3() {
        return getNokkelTallDriftProsjektering(fromYearWeek);
    }

    /**
     * @return data for drift og prosjektering uke minus 2
     */
    public final List<NokkelDriftProsjekteringV> getNokkelTallDriftProsjekteringWeekN2() {
        return getNokkelTallDriftProsjektering(yearWeekN2);
    }

    /**
     * @return data for drift og prosjektering uke minus 1
     */
    public final List<NokkelDriftProsjekteringV> getNokkelTallDriftProsjekteringWeekN1() {
        return getNokkelTallDriftProsjektering(yearWeekN1);
    }

    /**
     * @return data for drift og prosjektering for gjeldende uke
     */
    public final List<NokkelDriftProsjekteringV> getNokkelTallDriftProsjekteringWeekN() {
        return getNokkelTallDriftProsjektering(currentYearWeek);
    }

    /**
     * @return akkumulerte data for drift og prosjektering
     */
    public final List<NokkelDriftProsjekteringV> getNokkelTallDriftProsjekteringAggregate() {
        initReportSalgDriftTransport();

        if (nokkelDriftVAllYear != null) {
            return Arrays
                    .asList(new NokkelDriftProsjekteringV[] {nokkelDriftVAllYear});
        }
        return null;
    }

    /**
     * @return data for drift og prosjektering uke pluss 1
     */
    public final List<NokkelDriftProsjekteringV> getNokkelTallDriftProsjekteringWeek1N() {
        return getNokkelTallDriftProsjektering(toYearWeek);
    }

    public final List<NokkelTransportV> getNokkelTallTransportWeek(
            final YearWeek yearWeek) {
        initReportSalgDriftTransport();
        NokkelTransportV transport = nokkelTransportMap.get(yearWeek);
        if (transport != null) {
            return Arrays.asList(new NokkelTransportV[] {transport});
        }
        return null;
    }

    /**
     * @return data for transport uke minus 3
     */
    public final List<NokkelTransportV> getNokkelTallTransportWeekN3() {
        return getNokkelTallTransportWeek(fromYearWeek);
    }

    /**
     * @return data for transport uke minus 2
     */
    public final List<NokkelTransportV> getNokkelTallTransportWeekN2() {
        return getNokkelTallTransportWeek(yearWeekN2);
    }

    /**
     * @return data for transport uke minus 1
     */
    public final List<NokkelTransportV> getNokkelTallTransportWeekN1() {
        return getNokkelTallTransportWeek(yearWeekN1);
    }

    /**
     * @return data for transport gjeldende uke
     */
    public final List<NokkelTransportV> getNokkelTallTransportWeekN() {
        return getNokkelTallTransportWeek(currentYearWeek);
    }

    /**
     * @return akkumulerte data for transport
     */
    public final List<NokkelTransportV> getNokkelTallTransportAggregate() {
        initReportSalgDriftTransport();

        if (nokkelTransportVAllYear != null) {
            return Arrays
                    .asList(new NokkelTransportV[] {nokkelTransportVAllYear});
        }
        return null;
    }

    /**
     * @return data for transport uke pluss 1
     */
    public final List<NokkelTransportV> getNokkelTallTransportWeek1N() {
        return getNokkelTallTransportWeek(toYearWeek);
    }

    public final List<NokkelMonteringV> getNokkelTallMonteringWeek(
            final YearWeek yearWeek) {
        initReportMonteringOkonomi();
        NokkelMonteringV montering = nokkelMonteringMap.get(yearWeek);
        if (montering != null) {
            return Arrays.asList(new NokkelMonteringV[] {montering});
        }
        return null;
    }

    public final List<NokkelMonteringV> getNokkelTallMonteringWeekN3() {
        return getNokkelTallMonteringWeek(fromYearWeek);
    }

    public final List<NokkelMonteringV> getNokkelTallMonteringWeekN2() {
        return getNokkelTallMonteringWeek(yearWeekN2);
    }

    public final List<NokkelMonteringV> getNokkelTallMonteringWeekN1() {
        return getNokkelTallMonteringWeek(yearWeekN1);
    }

    public final List<NokkelMonteringV> getNokkelTallMonteringWeekN() {
        return getNokkelTallMonteringWeek(currentYearWeek);
    }

    public final List<NokkelMonteringV> getNokkelTallMonteringWeek1N() {
        return getNokkelTallMonteringWeek(toYearWeek);
    }

    public final List<NokkelMonteringV> getNokkelTallMonteringAggregate() {
        initReportMonteringOkonomi();

        if (nokkelMonteringVAllYear != null) {
            return Arrays
                    .asList(new NokkelMonteringV[] {nokkelMonteringVAllYear});
        }
        return null;
    }

    public final List<NokkelOkonomiV> getNokkelTallOkonomiWeek(
            final YearWeek yearWeek) {
        initReportMonteringOkonomi();
        NokkelOkonomiV okonomi = nokkelOkonomiMap.get(yearWeek);
        if (okonomi != null) {
            return Arrays.asList(new NokkelOkonomiV[] {okonomi});
        }
        return null;
    }

    public final List<NokkelOkonomiV> getNokkelTallOkonomiWeekN3() {
        return getNokkelTallOkonomiWeek(fromYearWeek);
    }

    public final List<NokkelOkonomiV> getNokkelTallOkonomiWeekN2() {
        return getNokkelTallOkonomiWeek(yearWeekN2);
    }

    public final List<NokkelOkonomiV> getNokkelTallOkonomiWeekN1() {
        return getNokkelTallOkonomiWeek(yearWeekN1);
    }

    public final List<NokkelOkonomiV> getNokkelTallOkonomiWeekN() {
        return getNokkelTallOkonomiWeek(currentYearWeek);
    }

    public final List<NokkelOkonomiV> getNokkelTallOkonomiWeek1N() {
        return getNokkelTallOkonomiWeek(toYearWeek);
    }

    public final List<NokkelOkonomiV> getNokkelTallOkonomiAggregate() {
        initReportMonteringOkonomi();

        if (nokkelOkonomiVAllYear != null) {
            return Arrays.asList(new NokkelOkonomiV[] {nokkelOkonomiVAllYear});
        }
        return null;
    }

    public final List<NokkelProduksjonV> getNokkelTallProduksjonWeek(
            final YearWeek yearWeek) {
        initReportProduksjon();
        NokkelProduksjonV produksjon = nokkelProduksjonMap.get(yearWeek);
        if (produksjon != null) {
            return Arrays.asList(new NokkelProduksjonV[] {produksjon});
        }
        return null;
    }

    public final List<NokkelProduksjonV> getNokkelTallProduksjonWeekN3() {
        return getNokkelTallProduksjonWeek(fromYearWeek);
    }

    public final List<NokkelProduksjonV> getNokkelTallProduksjonWeekN2() {
        return getNokkelTallProduksjonWeek(yearWeekN2);
    }

    public final List<NokkelProduksjonV> getNokkelTallProduksjonWeekN1() {
        return getNokkelTallProduksjonWeek(yearWeekN1);
    }

    public final List<NokkelProduksjonV> getNokkelTallProduksjonWeekN() {
        return getNokkelTallProduksjonWeek(currentYearWeek);
    }

    public final List<NokkelProduksjonV> getNokkelTallProduksjonWeek1N() {
        return getNokkelTallProduksjonWeek(toYearWeek);
    }

    public final List<NokkelProduksjonV> getNokkelTallProduksjonAggregate() {
        initReportProduksjon();

        if (nokkelProduksjonVAllYear != null) {
            return Arrays
                    .asList(new NokkelProduksjonV[] {nokkelProduksjonVAllYear});
        }
        return null;
    }

    public final List<OrderReserveV> getOrderReserve() {
        initReportProduksjon();

        return orderReserveList;
    }

    private void initReportProduksjon() {
        NokkelProduksjonVManager nokkelProduksjonVManager = (NokkelProduksjonVManager) ModelUtil
                .getBean("nokkelProduksjonVManager");
        OrderReserveVManager orderReserveVManager = (OrderReserveVManager) ModelUtil
                .getBean("orderReserveVManager");
        // produksjon
        if (nokkelProduksjonMap == null) {
            setNokkelProduksjonMap(nokkelProduksjonVManager);
        }

        if (nokkelProduksjonVAllYear == null) {
            nokkelProduksjonVAllYear = nokkelProduksjonVManager
                    .aggreagateYearWeek(currentYearWeek, productArea);
        }

        if (orderReserveList == null) {
            setOrderReserveList(orderReserveVManager);
        }
    }

    private void setOrderReserveList(
            final OrderReserveVManager orderReserveVManager) {
        orderReserveList = new ArrayList<OrderReserveV>();
        List<OrderReserveV> reserve = orderReserveVManager
                .findByProductArea(productArea);
        if (reserve != null) {
            orderReserveList.addAll(reserve);
        }
    }

    private void setNokkelProduksjonMap(
            final NokkelProduksjonVManager nokkelProduksjonVManager) {
        List<NokkelProduksjonV> produksjonList = nokkelProduksjonVManager
                .findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);

        if (produksjonList != null) {
            nokkelProduksjonMap = new Hashtable<YearWeek, NokkelProduksjonV>();
            for (NokkelProduksjonV produksjon : produksjonList) {
                nokkelProduksjonMap.put(new YearWeek(produksjon
                        .getOrderReadyYear(), produksjon.getOrderReadyWeek()),
                        produksjon);
            }
        }
    }

    private void initReportMonteringOkonomi() {
        NokkelMonteringVManager nokkelMonteringVManager = (NokkelMonteringVManager) ModelUtil
                .getBean("nokkelMonteringVManager");
        NokkelOkonomiVManager nokkelOkonomiVManager = (NokkelOkonomiVManager) ModelUtil
                .getBean("nokkelOkonomiVManager");
        // montering
        if (nokkelMonteringMap == null) {

            setNokkelMonteringMap(nokkelMonteringVManager);
        }

        if (nokkelMonteringVAllYear == null) {
            nokkelMonteringVAllYear = nokkelMonteringVManager
                    .aggreagateYearWeek(currentYearWeek, productArea);
        }
        // økonomi
        if (nokkelOkonomiMap == null) {

            setNokkelOkonomiMap(nokkelOkonomiVManager);
        }

        if (nokkelOkonomiVAllYear == null) {
            nokkelOkonomiVAllYear = nokkelOkonomiVManager.aggreagateYearWeek(
                    currentYearWeek, productArea);
        }
    }

    private void setNokkelOkonomiMap(
            final NokkelOkonomiVManager nokkelOkonomiVManager) {
        List<NokkelOkonomiV> okonomiList = nokkelOkonomiVManager
                .findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);

        if (okonomiList != null) {
            nokkelOkonomiMap = new Hashtable<YearWeek, NokkelOkonomiV>();
            for (NokkelOkonomiV okonomi : okonomiList) {
                nokkelOkonomiMap.put(new YearWeek(okonomi.getInvoiceYear(),
                        okonomi.getInvoiceWeek()), okonomi);
            }
        }
    }

    private void setNokkelMonteringMap(
            final NokkelMonteringVManager nokkelMonteringVManager) {
        List<NokkelMonteringV> monteringList = nokkelMonteringVManager
                .findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);

        if (monteringList != null) {
            nokkelMonteringMap = new Hashtable<YearWeek, NokkelMonteringV>();
            for (NokkelMonteringV montering : monteringList) {
                nokkelMonteringMap.put(new YearWeek(montering
                        .getAssembliedYear(), montering.getAssembliedWeek()),
                        montering);
            }
        }
    }

    /**
     * Initierere alle data for rapport for salg/drift/transport.
     */
    private void initReportSalgDriftTransport() {
        NokkelSalgVManager nokkelSalgVManager = (NokkelSalgVManager) ModelUtil
                .getBean("nokkelSalgVManager");
        NokkelDriftProsjekteringVManager nokkelDriftProsjekteringVManager =
            (NokkelDriftProsjekteringVManager) ModelUtil
                .getBean("nokkelDriftProsjekteringVManager");
        NokkelTransportVManager nokkelTransportVManager = (NokkelTransportVManager) ModelUtil
                .getBean("nokkelTransportVManager");
        // salg
        if (nokkelSalgMap == null) {

            setNokkelSalgMap(nokkelSalgVManager);
        }

        if (nokkelSalgVAllYear == null) {
            nokkelSalgVAllYear = nokkelSalgVManager.aggreagateYearWeek(
                    currentYearWeek, productArea);
        }
        // drift og prosjektering
        if (nokkelDriftMap == null) {

            setNokkelDriftMap(nokkelDriftProsjekteringVManager);
        }

        if (nokkelDriftVAllYear == null) {
            nokkelDriftVAllYear = nokkelDriftProsjekteringVManager
                    .aggreagateYearWeek(currentYearWeek, productArea);
        }
        // transport
        if (nokkelTransportMap == null) {

            setNokkelTransportMap(nokkelTransportVManager);
        }

        if (nokkelTransportVAllYear == null) {
            nokkelTransportVAllYear = nokkelTransportVManager
                    .aggreagateYearWeek(currentYearWeek, productArea);
        }
    }

    private void setNokkelTransportMap(
            final NokkelTransportVManager nokkelTransportVManager) {
        List<NokkelTransportV> transportList = nokkelTransportVManager
                .findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);

        if (transportList != null) {
            nokkelTransportMap = new Hashtable<YearWeek, NokkelTransportV>();
            for (NokkelTransportV transport : transportList) {
                nokkelTransportMap.put(new YearWeek(transport
                        .getOrderSentYear(), transport.getOrderSentWeek()),
                        transport);
            }
        }
    }

    private void setNokkelDriftMap(
            final NokkelDriftProsjekteringVManager nokkelDriftProsjekteringVManager) {
        List<NokkelDriftProsjekteringV> driftList = nokkelDriftProsjekteringVManager
                .findBetweenYearWeek(fromYearWeek, toYearWeek, productArea);

        if (driftList != null) {
            nokkelDriftMap = new Hashtable<YearWeek, NokkelDriftProsjekteringV>();
            for (NokkelDriftProsjekteringV drift : driftList) {
                nokkelDriftMap.put(new YearWeek(drift.getPacklistYear(), drift
                        .getPacklistWeek()), drift);
            }
        }
    }

    private void setNokkelSalgMap(final NokkelSalgVManager nokkelSalgVManager) {
        List<NokkelSalgV> saleList = nokkelSalgVManager.findBetweenYearWeek(
                fromYearWeek, toYearWeek, productArea);

        if (saleList != null) {
            nokkelSalgMap = new Hashtable<YearWeek, NokkelSalgV>();
            for (NokkelSalgV sale : saleList) {
                nokkelSalgMap.put(new YearWeek(sale.getAgreementYear(), sale
                        .getAgreementWeek()), sale);
            }
        }
    }

}
