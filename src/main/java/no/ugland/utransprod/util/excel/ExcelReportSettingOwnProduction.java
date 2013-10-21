package no.ugland.utransprod.util.excel;

import no.ugland.utransprod.model.ProductAreaGroup;

public class ExcelReportSettingOwnProduction extends ExcelReportSetting {
    public static final String PROPERTY_PRODUCT_AREA_GROUP = "productAreaGroup";

    private ProductAreaGroup productAreaGroup;

    public ExcelReportSettingOwnProduction(final ExcelReportEnum aExcelReportEnum) {
        super(aExcelReportEnum);
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

    public final String getProductAreaGroupName() {
        if (productAreaGroup != null) {
            return productAreaGroup.getProductAreaGroupName();
        }
        return null;
    }
}
