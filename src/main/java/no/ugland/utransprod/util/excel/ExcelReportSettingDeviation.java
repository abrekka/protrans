package no.ugland.utransprod.util.excel;

import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.model.FunctionCategory;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.util.MonthEnum;

public class ExcelReportSettingDeviation extends ExcelReportSetting {
    public static final String PROPERTY_DEVIATION_FUNCTION = "deviationFunction";

    public static final String PROPERTY_FUNCTION_CATEGORY = "functionCategory";

    public static final String PROPERTY_DEVIATION_STATUS = "deviationStatus";

    public static final String PROPERTY_MONTH_ENUM = "monthEnum";

	public static final String PROPERTY_PRODUCT_AREA_GROUP = "productAreaGroup";

    private JobFunction deviationFunction;

    private FunctionCategory functionCategory;

    private DeviationStatus deviationStatus;

    private MonthEnum monthEnum;
    private ProductAreaGroup productAreaGroup;

    public ExcelReportSettingDeviation(final ExcelReportEnum aExcelReportEnum) {
        super(aExcelReportEnum);
    }

    public final JobFunction getDeviationFunction() {
        return deviationFunction;
    }

    public final void setDeviationFunction(final JobFunction aDeviationFunction) {
        JobFunction oldFunction = getDeviationFunction();
        this.deviationFunction = aDeviationFunction;
        firePropertyChange(PROPERTY_DEVIATION_FUNCTION, oldFunction,
                aDeviationFunction);
    }

    public final FunctionCategory getFunctionCategory() {
        return functionCategory;
    }

    public final void setFunctionCategory(final FunctionCategory aFunctionCategory) {
        FunctionCategory oldCat = getFunctionCategory();
        this.functionCategory = aFunctionCategory;
        firePropertyChange(PROPERTY_FUNCTION_CATEGORY, oldCat, aFunctionCategory);
    }

    public final DeviationStatus getDeviationStatus() {
        return deviationStatus;
    }

    public final void setDeviationStatus(final DeviationStatus aDeviationStatus) {
        DeviationStatus oldStatus = getDeviationStatus();
        this.deviationStatus = aDeviationStatus;
        firePropertyChange(PROPERTY_DEVIATION_STATUS, oldStatus,
                aDeviationStatus);
    }

    public final MonthEnum getMonthEnum() {
        return monthEnum;
    }

    public final void setMonthEnum(final MonthEnum aMonthEnum) {
        MonthEnum oldMonth = getMonthEnum();
        this.monthEnum = aMonthEnum;
        firePropertyChange(PROPERTY_MONTH_ENUM, oldMonth, aMonthEnum);
    }
    public final ProductAreaGroup getProductAreaGroup() {
        return productAreaGroup;
    }

    public final void setProductAreaGroup(final ProductAreaGroup aProductAreaGroup) {
        ProductAreaGroup oldGroup = getProductAreaGroup();
        this.productAreaGroup = aProductAreaGroup;
        firePropertyChange(PROPERTY_PRODUCT_AREA_GROUP, oldGroup,
                aProductAreaGroup);
    }

}
