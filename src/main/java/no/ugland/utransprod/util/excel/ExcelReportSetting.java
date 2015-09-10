package no.ugland.utransprod.util.excel;

import no.ugland.utransprod.model.ProductArea;
import no.ugland.utransprod.util.Periode;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.beans.Model;

/**
 * Klasse som holder rapportsettinger.
 * 
 * @author atle.brekka
 */
public class ExcelReportSetting extends Model {
    private static final long serialVersionUID = 8748599411872146981L;

    public static final String PROPERTY_YEAR = "year";

    public static final String PROPERTY_WEEK_FROM = "weekFrom";

    public static final String PROPERTY_WEEK_TO = "weekTo";

    public static final String PROPERTY_EXCEL_REPORT_TYPE = "excelReportType";

    public static final String PROPERTY_PRODUCT_AREA = "productArea";

    private Integer year;

    private Integer weekFrom;

    private Integer weekTo;

    private ProductArea productArea;

    protected ExcelReportEnum excelReportEnum;

    /**
     * @param aExcelReportEnum
     */
    public ExcelReportSetting(final ExcelReportEnum aExcelReportEnum) {
	excelReportEnum = aExcelReportEnum;
    }

    /**
     * Henter rapporttype.
     * 
     * @return rapporttype
     */
    public final ExcelReportEnum getExcelReportType() {
	return excelReportEnum;
    }

    public final void setExcelReportType(final ExcelReportEnum aExcelReportType) {
	ExcelReportEnum oldType = getExcelReportType();
	this.excelReportEnum = aExcelReportType;
	firePropertyChange(PROPERTY_EXCEL_REPORT_TYPE, oldType, aExcelReportType);
    }

    /**
     * Henter fra uke.
     * 
     * @return fra uke
     */
    public final Integer getWeekFrom() {
	return weekFrom;
    }

    public final void setWeekFrom(final Integer aWeekFrom) {
	Integer oldWeek = getWeekFrom();
	this.weekFrom = aWeekFrom;
	firePropertyChange(PROPERTY_WEEK_FROM, oldWeek, aWeekFrom);
    }

    public final Integer getWeekTo() {
	return weekTo;
    }

    public final void setWeekTo(final Integer aWeekTo) {
	Integer oldWeek = getWeekTo();
	this.weekTo = aWeekTo;
	firePropertyChange(PROPERTY_WEEK_TO, oldWeek, aWeekTo);
    }

    public final Integer getYear() {
	return year;
    }

    public final void setYear(final Integer aYear) {
	Integer oldYear = getYear();
	this.year = aYear;
	firePropertyChange(PROPERTY_YEAR, oldYear, aYear);
    }

    /**
     * @return periodestreng
     */
    public final String getPeriodString() {
	StringBuilder stringBuilder = new StringBuilder();
	if (year != null) {
	    stringBuilder.append(year);
	}
	if (weekFrom != null) {
	    stringBuilder.append(String.format("%02d", weekFrom));
	}
	if (weekTo != null) {
	    stringBuilder.append(String.format("%02d", weekTo));
	}
	return stringBuilder.toString();

    }

    /**
     * @return rapportnavn
     */
    public final String getReportName() {
	StringBuilder stringBuilder = new StringBuilder();
	if (excelReportEnum != null) {
	    stringBuilder.append(excelReportEnum.getReportName());
	}
	if (weekFrom != null) {
	    stringBuilder.append(" Uke ").append(weekFrom);
	}
	if (weekTo != null) {
	    stringBuilder.append(" - ").append(weekTo);
	}
	if (year != null) {
	    stringBuilder.append(" ").append(year);
	}
	if (productArea != null) {
	    stringBuilder.append(" ").append(productArea);
	}
	if (stringBuilder.length() == 0) {
	    stringBuilder.append(" pr. ").append(Util.getCurrentDateAsDateString());
	}
	return stringBuilder.toString();
    }

    public final ProductArea getProductArea() {
	return productArea;
    }

    public final void setProductArea(final ProductArea aProductArea) {
	ProductArea oldArea = getProductArea();
	this.productArea = aProductArea;
	firePropertyChange(PROPERTY_PRODUCT_AREA, oldArea, aProductArea);
    }

    public final String getProductAreaName() {
	if (productArea != null) {
	    return productArea.getProductArea();
	}
	return null;
    }

    public final Periode getPeriode() {
	return new Periode(year, weekFrom, weekTo);
    }
}