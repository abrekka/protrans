package no.ugland.utransprod.util.excel;

import java.util.ArrayList;
import java.util.List;

import no.ugland.utransprod.util.report.ProbabilityEnum;

public class ExcelReportSettingSaleStatus extends ExcelReportSetting {
    public static final String PROPERTY_USE_OFFER = "useOffer";
    public static final String PROPERTY_USE_ORDER = "useOrder";
    private boolean useOrder;
    private boolean useOffer;
    private List<ProbabilityEnum> probabilites;

    public ExcelReportSettingSaleStatus() {
        super(ExcelReportEnum.SALE_STATUS_REPORT);
        probabilites = new ArrayList<ProbabilityEnum>();
    }

    public boolean getUseOrder() {

        return useOrder;
    }

    public void setUseOrder(boolean shouldUseOrder) {
        boolean added = shouldUseOrder ? probabilites.add(ProbabilityEnum.PROBABILITY_ORDER) : probabilites
                .remove(ProbabilityEnum.PROBABILITY_ORDER);
        this.useOrder = shouldUseOrder;
    }

    public boolean getUseOffer() {
        return useOffer;
    }

    public void setUseOffer(boolean shouldUseOffer) {
        boolean added = shouldUseOffer ? probabilites.add(ProbabilityEnum.PROBABILITY_OFFER) : probabilites
                .remove(ProbabilityEnum.PROBABILITY_OFFER);
        this.useOffer = shouldUseOffer;
    }

    public List<Integer> getProbabilities() {
        List<Integer> probabilityProcents = new ArrayList<Integer>();
        for (ProbabilityEnum prob : probabilites) {
            probabilityProcents.add(prob.getProbability());
        }
        return probabilityProcents;
    }

}
