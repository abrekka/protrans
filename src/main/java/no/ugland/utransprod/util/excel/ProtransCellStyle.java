package no.ugland.utransprod.util.excel;

import java.util.Map;

import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.hssf.usermodel.HSSFCellStyle;
//import org.apache.poi.hssf.usermodel.HSSFFont;
//import org.apache.poi.hssf.usermodel.HSSFWorkbook;
//import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Workbook;

import com.google.inject.internal.Maps;

class ProtransCellStyle {
	private CellStyle borderStyle;
	private CellStyle cellStyle12Bold;

	private CellStyle cellStyle12;

	private CellStyle cellStyle12BoldBorderBottomThick;

	private CellStyle cellStyle12BorderBottomThick;

	private CellStyle cellStyle10BorderBottomDashed;

	private CellStyle cellStyle10BorderBottomDashedBorderTopMedium;

	private CellStyle cellStyle14Bold;

	private CellStyle cellStyle18Bold;

	private CellStyle cellStyle10Bold;

	private CellStyle cellStyle10;

	private CellStyle cellStyle10BoldBorders;

	private CellStyle headingStyle;

	private CellStyle groupStyle;

	private CellStyle rowStyleBorderBottomBorderTop;

	private CellStyle rowStyleBorderBottom;

	private Workbook wb;

	private Font font12Bold;

	private Font font12;

	private Font font14Bold;

	private Font font18Bold;

	private Font font10;

	private Font font10Bold;

	private Font fontHeading;

	private CellStyle dayStyle;

	private CellStyle cellStyle10BoldTopBorderTick;

	private CellStyle cellStyle10BoldTopBorderAndLeftTick;

	private CellStyle cellStyle10BoldTopBorderAndRightTick;

	private CellStyle cellStyle10BoldBorderRightTick;

	private CellStyle cellStyle10BorderLeftTick;

	private CellStyle cellStyle10BoldBottomBorderAndLeftTick;

	private CellStyle cellStyle10BoldBottomBorderTick;

	private CellStyle cellStyle10BoldBorderLeftTick;

	private CellStyle cellStyle10BoldBottomBorderAndRigthTick;

	private static Map<String,Font> fontMap=Maps.newHashMap();

	public ProtransCellStyle(final Workbook aWb) {
		wb = aWb;
	}

	public ProtransCellStyle(final Workbook aWb, final short headingSize) {
		wb = aWb;

		fontHeading = getFontBold(headingSize);
	}

	public CellStyle getDayStyle(final boolean wrapText, final String day) {
		if (dayStyle == null) {
			dayStyle = wb.createCellStyle();
			dayStyle.setWrapText(wrapText);
			dayStyle.setBorderBottom((short) 1);
			dayStyle.setBorderLeft((short) 1);
			dayStyle.setBorderRight((short) 1);
			dayStyle.setBorderTop((short) 1);

		}
		if (day != null && day.length() != 0) {
			dayStyle.setFillPattern((short) 1);
			short dayColorIndex = DayEnum.getDayColorIndex(day);
			dayStyle.setFillBackgroundColor(dayColorIndex);
			dayStyle.setFillForegroundColor(dayColorIndex);
		}
		return dayStyle;
	}

	public CellStyle createDayStyle(final boolean wrapText, final String day) {
		CellStyle style;
		if (day != null && day.length() != 0) {
			style = wb.createCellStyle();
			style.setWrapText(wrapText);
			style.setBorderBottom(BorderStyle.MEDIUM);
			style.setBorderLeft(BorderStyle.MEDIUM);
			style.setBorderRight(BorderStyle.MEDIUM);
			style.setBorderTop(BorderStyle.MEDIUM);
			style.setFillPattern(FillPatternType.BRICKS);
			short dayColorIndex = DayEnum.getDayColorIndex(day);
			style.setFillBackgroundColor(dayColorIndex);
			style.setFillForegroundColor(dayColorIndex);
		}else{
			if(borderStyle==null){
				borderStyle = wb.createCellStyle();
				borderStyle.setWrapText(wrapText);
				borderStyle.setBorderBottom(BorderStyle.MEDIUM);
				borderStyle.setBorderLeft(BorderStyle.MEDIUM);
				borderStyle.setBorderRight(BorderStyle.MEDIUM);
				borderStyle.setBorderTop(BorderStyle.MEDIUM);
			}
			style=borderStyle;
		}
		
		
//		HSSFCellStyle style = wb.createCellStyle();
//		style.setWrapText(wrapText);
//		style.setBorderBottom((short) 1);
//		style.setBorderLeft((short) 1);
//		style.setBorderRight((short) 1);
//		style.setBorderTop((short) 1);
//
//		if (day != null && day.length() != 0) {
//			style.setFillPattern((short) 1);
//			short dayColorIndex = DayEnum.getDayColorIndex(day);
//			style.setFillBackgroundColor(dayColorIndex);
//			style.setFillForegroundColor(dayColorIndex);
//		}
		return style;
	}

	public CellStyle createStyle(final short headFontSize,
			final short boldStyle, final short backgroundColor, short alignment) {
		Font font = wb.createFont();
		font.setFontHeightInPoints(headFontSize);
		font.setBoldweight(boldStyle);

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(alignment);
		cellStyle.setFillBackgroundColor(backgroundColor);
		cellStyle.setFillForegroundColor(backgroundColor);
		cellStyle.setFillPattern((short) 1);
		return cellStyle;
	}

	public CellStyle getStyle12() {
		if (cellStyle12 == null) {
			cellStyle12 = wb.createCellStyle();
			cellStyle12.setFont(getFont12());
		}
		return cellStyle12;

	}

	public CellStyle getStyle12Bold() {
		if (cellStyle12Bold == null) {
			cellStyle12Bold = wb.createCellStyle();
			cellStyle12Bold.setFont(getFont12Bold());
		}
		return cellStyle12Bold;

	}

	public CellStyle getStyle12BoldBorderBottomThick() {
		if (cellStyle12BoldBorderBottomThick == null) {
			cellStyle12BoldBorderBottomThick = wb.createCellStyle();
			cellStyle12BoldBorderBottomThick.setFont(getFont12Bold());
			cellStyle12BoldBorderBottomThick
					.setBorderBottom(CellStyle.BORDER_THICK);
		}
		return cellStyle12BoldBorderBottomThick;

	}

	public CellStyle getStyle12BorderBottomThick() {
		if (cellStyle12BorderBottomThick == null) {
			cellStyle12BorderBottomThick = wb.createCellStyle();
			cellStyle12BorderBottomThick.setFont(getFont12());
			cellStyle12BorderBottomThick
					.setBorderBottom(CellStyle.BORDER_THICK);
		}
		return cellStyle12BorderBottomThick;

	}

	public CellStyle getStyle10BorderBottomDashed(final boolean wrapText) {
		if (cellStyle10BorderBottomDashed == null) {
			cellStyle10BorderBottomDashed = wb.createCellStyle();
			cellStyle10BorderBottomDashed
					.setBorderBottom(CellStyle.BORDER_DASHED);
		}
		cellStyle10BorderBottomDashed.setWrapText(wrapText);
		return cellStyle10BorderBottomDashed;

	}

	public CellStyle getStyle10BorderBottomDashedBorderTopMedium(
			final boolean wrapText) {
		if (cellStyle10BorderBottomDashedBorderTopMedium == null) {
			cellStyle10BorderBottomDashedBorderTopMedium = wb.createCellStyle();
			cellStyle10BorderBottomDashedBorderTopMedium.setFont(getFont10());
			cellStyle10BorderBottomDashedBorderTopMedium
					.setBorderBottom(CellStyle.BORDER_DASHED);
			cellStyle10BorderBottomDashedBorderTopMedium
					.setBorderTop(CellStyle.BORDER_MEDIUM_DASH_DOT);
		}
		cellStyle10BorderBottomDashedBorderTopMedium.setWrapText(wrapText);
		return cellStyle10BorderBottomDashedBorderTopMedium;

	}

	public CellStyle getStyle18Bold() {
		if (cellStyle18Bold == null) {
			cellStyle18Bold = wb.createCellStyle();
			cellStyle18Bold.setFont(getFont18Bold());
		}
		return cellStyle18Bold;

	}

	public CellStyle getStyle10() {
		if (cellStyle10 == null) {
			cellStyle10 = wb.createCellStyle();
			cellStyle10.setFont(getFont10());
		}
		return cellStyle10;

	}

	public CellStyle getStyle10Bold() {
		if (cellStyle10Bold == null) {
			cellStyle10Bold = wb.createCellStyle();
			cellStyle10Bold.setFont(getFont10Bold());
		}
		return cellStyle10Bold;

	}

	public CellStyle getStyle10BoldWithBorders() {
		if (cellStyle10BoldBorders == null) {
			cellStyle10BoldBorders = wb.createCellStyle();
			cellStyle10BoldBorders.setFont(getFont10Bold());

			cellStyle10BoldBorders.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle10BoldBorders.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle10BoldBorders.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle10BoldBorders.setBorderRight(CellStyle.BORDER_THIN);
		}
		return cellStyle10BoldBorders;
	}

	public CellStyle getStyle10BoldWithTopBorderTick() {
		if (cellStyle10BoldTopBorderTick == null) {
			cellStyle10BoldTopBorderTick = wb.createCellStyle();
			cellStyle10BoldTopBorderTick.setFont(getFont10Bold());

			cellStyle10BoldTopBorderTick
					.setBorderTop(CellStyle.BORDER_THICK);

		}
		return cellStyle10BoldTopBorderTick;
	}

	public CellStyle getStyle10BoldWithTopBorderAndLeftTick() {
		if (cellStyle10BoldTopBorderAndLeftTick == null) {
			cellStyle10BoldTopBorderAndLeftTick = wb.createCellStyle();
			cellStyle10BoldTopBorderAndLeftTick.setFont(getFont10Bold());

			cellStyle10BoldTopBorderAndLeftTick
					.setBorderTop(CellStyle.BORDER_THICK);
			cellStyle10BoldTopBorderAndLeftTick
					.setBorderLeft(CellStyle.BORDER_THICK);

		}
		return cellStyle10BoldTopBorderAndLeftTick;
	}

	public CellStyle getStyle10BoldWithBottomBorderAndLeftTick() {
		if (cellStyle10BoldBottomBorderAndLeftTick == null) {
			cellStyle10BoldBottomBorderAndLeftTick = wb.createCellStyle();
			cellStyle10BoldBottomBorderAndLeftTick.setFont(getFont10Bold());

			cellStyle10BoldBottomBorderAndLeftTick
					.setBorderBottom(CellStyle.BORDER_THICK);
			cellStyle10BoldBottomBorderAndLeftTick
					.setBorderLeft(CellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBottomBorderAndLeftTick;
	}

	public CellStyle getStyle10BoldWithBottomBorderTick() {
		if (cellStyle10BoldBottomBorderTick == null) {
			cellStyle10BoldBottomBorderTick = wb.createCellStyle();
			cellStyle10BoldBottomBorderTick.setFont(getFont10Bold());

			cellStyle10BoldBottomBorderTick
					.setBorderBottom(CellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBottomBorderTick;
	}

	public CellStyle getStyle10BoldWithBottomBorderAndRigthTick() {
		if (cellStyle10BoldBottomBorderAndRigthTick == null) {
			cellStyle10BoldBottomBorderAndRigthTick = wb.createCellStyle();
			cellStyle10BoldBottomBorderAndRigthTick.setFont(getFont10Bold());

			cellStyle10BoldBottomBorderAndRigthTick
					.setBorderBottom(CellStyle.BORDER_THICK);
			cellStyle10BoldBottomBorderAndRigthTick
					.setBorderRight(CellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBottomBorderAndRigthTick;
	}

	public CellStyle getStyle10BoldWithTopBorderAndRightTick() {
		if (cellStyle10BoldTopBorderAndRightTick == null) {
			cellStyle10BoldTopBorderAndRightTick = wb.createCellStyle();
			cellStyle10BoldTopBorderAndRightTick.setFont(getFont10Bold());

			cellStyle10BoldTopBorderAndRightTick
					.setBorderTop(CellStyle.BORDER_THICK);
			cellStyle10BoldTopBorderAndRightTick
					.setBorderRight(CellStyle.BORDER_THICK);

		}
		return cellStyle10BoldTopBorderAndRightTick;
	}

	public CellStyle getStyle10BoldWithBorderRightTick() {
		if (cellStyle10BoldBorderRightTick == null) {
			cellStyle10BoldBorderRightTick = wb.createCellStyle();
			cellStyle10BoldBorderRightTick.setFont(getFont10Bold());

			cellStyle10BoldBorderRightTick
					.setBorderRight(CellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBorderRightTick;
	}

	public CellStyle getStyle10WithBorderLeftTick() {
		if (cellStyle10BorderLeftTick == null) {
			cellStyle10BorderLeftTick = wb.createCellStyle();
			cellStyle10BorderLeftTick.setFont(getFont10());

			cellStyle10BorderLeftTick.setBorderLeft(CellStyle.BORDER_THICK);

		}
		return cellStyle10BorderLeftTick;
	}

	public CellStyle getStyle10BoldWithBorderLeftTick() {
		if (cellStyle10BoldBorderLeftTick == null) {
			cellStyle10BoldBorderLeftTick = wb.createCellStyle();
			cellStyle10BoldBorderLeftTick.setFont(getFont10Bold());

			cellStyle10BoldBorderLeftTick
					.setBorderLeft(CellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBorderLeftTick;
	}

	public CellStyle getStyle(short fontSize, short boldStyle,
			short alignment, short backgroundColor, Short borderLeft,
			Short borderTop, Short borderRight, Short borderBottom,String dataFormat) {
		Font font = fontMap.get(String.valueOf(fontSize)
				+ String.valueOf(boldStyle));
		if(font==null){
		font = wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setBoldweight(boldStyle);
		fontMap.put(String.valueOf(fontSize)
				+ String.valueOf(boldStyle),font);
		}

		CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(alignment);
		if(backgroundColor!=-1){
		cellStyle.setFillBackgroundColor(backgroundColor);
		cellStyle.setFillForegroundColor(backgroundColor);
		cellStyle.setFillPattern((short) 1);
		}

		if (borderLeft != null) {
			cellStyle.setBorderLeft(borderLeft);
		}
		if (borderTop != null) {
			cellStyle.setBorderTop(borderTop);
		}
		if (borderRight != null) {
			cellStyle.setBorderRight(borderRight);
		}
		if (borderBottom != null) {
			cellStyle.setBorderBottom(borderBottom);
		}
		
		if(dataFormat!=null){
			DataFormat format = wb.createDataFormat();
			cellStyle.setDataFormat(format.getFormat(dataFormat));
		}
		return cellStyle;

	}

	public CellStyle getStyle(Font font, Short borderLeft,
			Short borderTop, Short borderRight, Short borderBottom) {
		CellStyle style = wb.createCellStyle();
		style.setFont(font);
		if (borderLeft != null) {
			style.setBorderLeft(borderLeft);
		}
		if (borderTop != null) {
			style.setBorderTop(borderTop);
		}
		if (borderRight != null) {
			style.setBorderRight(borderRight);
		}
		if (borderBottom != null) {
			style.setBorderBottom(borderBottom);
		}
		return style;
	}

	public CellStyle getStyle14Bold() {
		if (cellStyle14Bold == null) {
			cellStyle14Bold = wb.createCellStyle();
			cellStyle14Bold.setFont(getFont14Bold());
		}
		return cellStyle14Bold;

	}

	public CellStyle getGroupStyle() {
		if (groupStyle == null) {
			groupStyle = wb.createCellStyle();
			if (fontHeading != null) {
				groupStyle.setFont(fontHeading);
			} else {
				groupStyle.setFont(getFont12Bold());
			}
			groupStyle.setAlignment(CellStyle.ALIGN_CENTER);
			groupStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
			groupStyle.setFillForegroundColor(HSSFColor.BLUE_GREY.index);
			groupStyle.setBorderBottom((short) 1);
			groupStyle.setBorderLeft((short) 1);
			groupStyle.setBorderRight((short) 1);
			groupStyle.setBorderTop((short) 1);
			groupStyle.setFillPattern((short) 1);
		}
		return groupStyle;
	}

	public CellStyle getHeadingStyle() {
		if (headingStyle == null) {
			headingStyle = wb.createCellStyle();
			if (fontHeading != null) {
				headingStyle.setFont(fontHeading);
			} else {
				headingStyle.setFont(getFont12Bold());
			}
			headingStyle.setBorderBottom(CellStyle.BORDER_THICK);
			headingStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
		}
		return headingStyle;
	}

	public CellStyle getRowStyleBorderBottomBorderTop() {
		if (rowStyleBorderBottomBorderTop == null) {
			rowStyleBorderBottomBorderTop = wb.createCellStyle();
			rowStyleBorderBottomBorderTop
					.setBorderBottom(CellStyle.BORDER_DOTTED);
			rowStyleBorderBottomBorderTop
					.setBorderTop(CellStyle.BORDER_MEDIUM_DASHED);
			rowStyleBorderBottomBorderTop.setWrapText(true);
		}
		return rowStyleBorderBottomBorderTop;
	}

	public CellStyle getRowStyleBorderBottom() {
		if (rowStyleBorderBottom == null) {
			rowStyleBorderBottom = wb.createCellStyle();
			rowStyleBorderBottom.setBorderBottom((short) 3);
			rowStyleBorderBottom.setWrapText(true);
		}
		return rowStyleBorderBottom;
	}

	private Font getFontBold(final short fontSize) {
		if (font12Bold == null) {
			font12Bold = wb.createFont();
			font12Bold.setFontHeightInPoints(fontSize);
			font12Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		}
		return font12Bold;
	}

	public Font getFont12Bold() {
		if (font12Bold == null) {
			font12Bold = wb.createFont();
			font12Bold.setFontHeightInPoints((short) 12);
			font12Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		}
		return font12Bold;
	}

	private Font getFont12() {
		if (font12 == null) {
			font12 = wb.createFont();
			font12.setFontHeightInPoints((short) 12);

		}
		return font12;
	}

	private Font getFont18Bold() {
		if (font18Bold == null) {
			font18Bold = wb.createFont();
			font18Bold.setFontHeightInPoints((short) 18);
			font18Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		}
		return font18Bold;
	}

	public Font getFont10() {
		if (font10 == null) {
			font10 = wb.createFont();
			font10.setFontHeightInPoints((short) 10);

		}
		return font10;
	}

	public Font getFont10Bold() {
		if (font10Bold == null) {
			font10Bold = wb.createFont();
			font10Bold.setFontHeightInPoints((short) 10);
			font10Bold.setBoldweight(Font.BOLDWEIGHT_BOLD);
		}
		return font10Bold;
	}

	public Font getFont14Bold() {
		if (font14Bold == null) {
			font14Bold = wb.createFont();
			font14Bold.setFontHeightInPoints((short) 14);
			font14Bold.setBoldweight((short) 20);
		}
		return font14Bold;
	}

}