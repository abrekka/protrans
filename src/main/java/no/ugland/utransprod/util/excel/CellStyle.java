package no.ugland.utransprod.util.excel;

import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.DataFormat;

import com.google.inject.internal.Maps;

class CellStyle {
	private HSSFCellStyle cellStyle12Bold;

	private HSSFCellStyle cellStyle12;

	private HSSFCellStyle cellStyle12BoldBorderBottomThick;

	private HSSFCellStyle cellStyle12BorderBottomThick;

	private HSSFCellStyle cellStyle10BorderBottomDashed;

	private HSSFCellStyle cellStyle10BorderBottomDashedBorderTopMedium;

	private HSSFCellStyle cellStyle14Bold;

	private HSSFCellStyle cellStyle18Bold;

	private HSSFCellStyle cellStyle10Bold;

	private HSSFCellStyle cellStyle10;

	private HSSFCellStyle cellStyle10BoldBorders;

	private HSSFCellStyle headingStyle;

	private HSSFCellStyle groupStyle;

	private HSSFCellStyle rowStyleBorderBottomBorderTop;

	private HSSFCellStyle rowStyleBorderBottom;

	private HSSFWorkbook wb;

	private HSSFFont font12Bold;

	private HSSFFont font12;

	private HSSFFont font14Bold;

	private HSSFFont font18Bold;

	private HSSFFont font10;

	private HSSFFont font10Bold;

	private HSSFFont fontHeading;

	private HSSFCellStyle dayStyle;

	private HSSFCellStyle cellStyle10BoldTopBorderTick;

	private HSSFCellStyle cellStyle10BoldTopBorderAndLeftTick;

	private HSSFCellStyle cellStyle10BoldTopBorderAndRightTick;

	private HSSFCellStyle cellStyle10BoldBorderRightTick;

	private HSSFCellStyle cellStyle10BorderLeftTick;

	private HSSFCellStyle cellStyle10BoldBottomBorderAndLeftTick;

	private HSSFCellStyle cellStyle10BoldBottomBorderTick;

	private HSSFCellStyle cellStyle10BoldBorderLeftTick;

	private HSSFCellStyle cellStyle10BoldBottomBorderAndRigthTick;

	private static Map<String,HSSFFont> fontMap=Maps.newHashMap();

	public CellStyle(final HSSFWorkbook aWb) {
		wb = aWb;
	}

	public CellStyle(final HSSFWorkbook aWb, final short headingSize) {
		wb = aWb;

		fontHeading = getFontBold(headingSize);
	}

	public HSSFCellStyle getDayStyle(final boolean wrapText, final String day) {
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

	public HSSFCellStyle createDayStyle(final boolean wrapText, final String day) {
		HSSFCellStyle style = wb.createCellStyle();
		style.setWrapText(wrapText);
		style.setBorderBottom((short) 1);
		style.setBorderLeft((short) 1);
		style.setBorderRight((short) 1);
		style.setBorderTop((short) 1);

		if (day != null && day.length() != 0) {
			style.setFillPattern((short) 1);
			short dayColorIndex = DayEnum.getDayColorIndex(day);
			style.setFillBackgroundColor(dayColorIndex);
			style.setFillForegroundColor(dayColorIndex);
		}
		return style;
	}

	public HSSFCellStyle createStyle(final short headFontSize,
			final short boldStyle, final short backgroundColor, short alignment) {
		HSSFFont font = wb.createFont();
		font.setFontHeightInPoints(headFontSize);
		font.setBoldweight(boldStyle);

		HSSFCellStyle cellStyle = wb.createCellStyle();
		cellStyle.setFont(font);
		cellStyle.setAlignment(alignment);
		cellStyle.setFillBackgroundColor(backgroundColor);
		cellStyle.setFillForegroundColor(backgroundColor);
		cellStyle.setFillPattern((short) 1);
		return cellStyle;
	}

	public HSSFCellStyle getStyle12() {
		if (cellStyle12 == null) {
			cellStyle12 = wb.createCellStyle();
			cellStyle12.setFont(getFont12());
		}
		return cellStyle12;

	}

	public HSSFCellStyle getStyle12Bold() {
		if (cellStyle12Bold == null) {
			cellStyle12Bold = wb.createCellStyle();
			cellStyle12Bold.setFont(getFont12Bold());
		}
		return cellStyle12Bold;

	}

	public HSSFCellStyle getStyle12BoldBorderBottomThick() {
		if (cellStyle12BoldBorderBottomThick == null) {
			cellStyle12BoldBorderBottomThick = wb.createCellStyle();
			cellStyle12BoldBorderBottomThick.setFont(getFont12Bold());
			cellStyle12BoldBorderBottomThick
					.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		}
		return cellStyle12BoldBorderBottomThick;

	}

	public HSSFCellStyle getStyle12BorderBottomThick() {
		if (cellStyle12BorderBottomThick == null) {
			cellStyle12BorderBottomThick = wb.createCellStyle();
			cellStyle12BorderBottomThick.setFont(getFont12());
			cellStyle12BorderBottomThick
					.setBorderBottom(HSSFCellStyle.BORDER_THICK);
		}
		return cellStyle12BorderBottomThick;

	}

	public HSSFCellStyle getStyle10BorderBottomDashed(final boolean wrapText) {
		if (cellStyle10BorderBottomDashed == null) {
			cellStyle10BorderBottomDashed = wb.createCellStyle();
			cellStyle10BorderBottomDashed
					.setBorderBottom(HSSFCellStyle.BORDER_DASHED);
		}
		cellStyle10BorderBottomDashed.setWrapText(wrapText);
		return cellStyle10BorderBottomDashed;

	}

	public HSSFCellStyle getStyle10BorderBottomDashedBorderTopMedium(
			final boolean wrapText) {
		if (cellStyle10BorderBottomDashedBorderTopMedium == null) {
			cellStyle10BorderBottomDashedBorderTopMedium = wb.createCellStyle();
			cellStyle10BorderBottomDashedBorderTopMedium.setFont(getFont10());
			cellStyle10BorderBottomDashedBorderTopMedium
					.setBorderBottom(HSSFCellStyle.BORDER_DASHED);
			cellStyle10BorderBottomDashedBorderTopMedium
					.setBorderTop(HSSFCellStyle.BORDER_MEDIUM_DASH_DOT);
		}
		cellStyle10BorderBottomDashedBorderTopMedium.setWrapText(wrapText);
		return cellStyle10BorderBottomDashedBorderTopMedium;

	}

	public HSSFCellStyle getStyle18Bold() {
		if (cellStyle18Bold == null) {
			cellStyle18Bold = wb.createCellStyle();
			cellStyle18Bold.setFont(getFont18Bold());
		}
		return cellStyle18Bold;

	}

	public HSSFCellStyle getStyle10() {
		if (cellStyle10 == null) {
			cellStyle10 = wb.createCellStyle();
			cellStyle10.setFont(getFont10());
		}
		return cellStyle10;

	}

	public HSSFCellStyle getStyle10Bold() {
		if (cellStyle10Bold == null) {
			cellStyle10Bold = wb.createCellStyle();
			cellStyle10Bold.setFont(getFont10Bold());
		}
		return cellStyle10Bold;

	}

	public HSSFCellStyle getStyle10BoldWithBorders() {
		if (cellStyle10BoldBorders == null) {
			cellStyle10BoldBorders = wb.createCellStyle();
			cellStyle10BoldBorders.setFont(getFont10Bold());

			cellStyle10BoldBorders.setBorderTop(HSSFCellStyle.BORDER_THIN);
			cellStyle10BoldBorders.setBorderBottom(HSSFCellStyle.BORDER_THIN);
			cellStyle10BoldBorders.setBorderLeft(HSSFCellStyle.BORDER_THIN);
			cellStyle10BoldBorders.setBorderRight(HSSFCellStyle.BORDER_THIN);
		}
		return cellStyle10BoldBorders;
	}

	public HSSFCellStyle getStyle10BoldWithTopBorderTick() {
		if (cellStyle10BoldTopBorderTick == null) {
			cellStyle10BoldTopBorderTick = wb.createCellStyle();
			cellStyle10BoldTopBorderTick.setFont(getFont10Bold());

			cellStyle10BoldTopBorderTick
					.setBorderTop(HSSFCellStyle.BORDER_THICK);

		}
		return cellStyle10BoldTopBorderTick;
	}

	public HSSFCellStyle getStyle10BoldWithTopBorderAndLeftTick() {
		if (cellStyle10BoldTopBorderAndLeftTick == null) {
			cellStyle10BoldTopBorderAndLeftTick = wb.createCellStyle();
			cellStyle10BoldTopBorderAndLeftTick.setFont(getFont10Bold());

			cellStyle10BoldTopBorderAndLeftTick
					.setBorderTop(HSSFCellStyle.BORDER_THICK);
			cellStyle10BoldTopBorderAndLeftTick
					.setBorderLeft(HSSFCellStyle.BORDER_THICK);

		}
		return cellStyle10BoldTopBorderAndLeftTick;
	}

	public HSSFCellStyle getStyle10BoldWithBottomBorderAndLeftTick() {
		if (cellStyle10BoldBottomBorderAndLeftTick == null) {
			cellStyle10BoldBottomBorderAndLeftTick = wb.createCellStyle();
			cellStyle10BoldBottomBorderAndLeftTick.setFont(getFont10Bold());

			cellStyle10BoldBottomBorderAndLeftTick
					.setBorderBottom(HSSFCellStyle.BORDER_THICK);
			cellStyle10BoldBottomBorderAndLeftTick
					.setBorderLeft(HSSFCellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBottomBorderAndLeftTick;
	}

	public HSSFCellStyle getStyle10BoldWithBottomBorderTick() {
		if (cellStyle10BoldBottomBorderTick == null) {
			cellStyle10BoldBottomBorderTick = wb.createCellStyle();
			cellStyle10BoldBottomBorderTick.setFont(getFont10Bold());

			cellStyle10BoldBottomBorderTick
					.setBorderBottom(HSSFCellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBottomBorderTick;
	}

	public HSSFCellStyle getStyle10BoldWithBottomBorderAndRigthTick() {
		if (cellStyle10BoldBottomBorderAndRigthTick == null) {
			cellStyle10BoldBottomBorderAndRigthTick = wb.createCellStyle();
			cellStyle10BoldBottomBorderAndRigthTick.setFont(getFont10Bold());

			cellStyle10BoldBottomBorderAndRigthTick
					.setBorderBottom(HSSFCellStyle.BORDER_THICK);
			cellStyle10BoldBottomBorderAndRigthTick
					.setBorderRight(HSSFCellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBottomBorderAndRigthTick;
	}

	public HSSFCellStyle getStyle10BoldWithTopBorderAndRightTick() {
		if (cellStyle10BoldTopBorderAndRightTick == null) {
			cellStyle10BoldTopBorderAndRightTick = wb.createCellStyle();
			cellStyle10BoldTopBorderAndRightTick.setFont(getFont10Bold());

			cellStyle10BoldTopBorderAndRightTick
					.setBorderTop(HSSFCellStyle.BORDER_THICK);
			cellStyle10BoldTopBorderAndRightTick
					.setBorderRight(HSSFCellStyle.BORDER_THICK);

		}
		return cellStyle10BoldTopBorderAndRightTick;
	}

	public HSSFCellStyle getStyle10BoldWithBorderRightTick() {
		if (cellStyle10BoldBorderRightTick == null) {
			cellStyle10BoldBorderRightTick = wb.createCellStyle();
			cellStyle10BoldBorderRightTick.setFont(getFont10Bold());

			cellStyle10BoldBorderRightTick
					.setBorderRight(HSSFCellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBorderRightTick;
	}

	public HSSFCellStyle getStyle10WithBorderLeftTick() {
		if (cellStyle10BorderLeftTick == null) {
			cellStyle10BorderLeftTick = wb.createCellStyle();
			cellStyle10BorderLeftTick.setFont(getFont10());

			cellStyle10BorderLeftTick.setBorderLeft(HSSFCellStyle.BORDER_THICK);

		}
		return cellStyle10BorderLeftTick;
	}

	public HSSFCellStyle getStyle10BoldWithBorderLeftTick() {
		if (cellStyle10BoldBorderLeftTick == null) {
			cellStyle10BoldBorderLeftTick = wb.createCellStyle();
			cellStyle10BoldBorderLeftTick.setFont(getFont10Bold());

			cellStyle10BoldBorderLeftTick
					.setBorderLeft(HSSFCellStyle.BORDER_THICK);

		}
		return cellStyle10BoldBorderLeftTick;
	}

	public HSSFCellStyle getStyle(short fontSize, short boldStyle,
			short alignment, short backgroundColor, Short borderLeft,
			Short borderTop, Short borderRight, Short borderBottom,String dataFormat) {
		HSSFFont font = fontMap.get(String.valueOf(fontSize)
				+ String.valueOf(boldStyle));
		if(font==null){
		font = wb.createFont();
		font.setFontHeightInPoints(fontSize);
		font.setBoldweight(boldStyle);
		fontMap.put(String.valueOf(fontSize)
				+ String.valueOf(boldStyle),font);
		}

		HSSFCellStyle cellStyle = wb.createCellStyle();
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

	public HSSFCellStyle getStyle(HSSFFont font, Short borderLeft,
			Short borderTop, Short borderRight, Short borderBottom) {
		HSSFCellStyle style = wb.createCellStyle();
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

	public HSSFCellStyle getStyle14Bold() {
		if (cellStyle14Bold == null) {
			cellStyle14Bold = wb.createCellStyle();
			cellStyle14Bold.setFont(getFont14Bold());
		}
		return cellStyle14Bold;

	}

	public HSSFCellStyle getGroupStyle() {
		if (groupStyle == null) {
			groupStyle = wb.createCellStyle();
			if (fontHeading != null) {
				groupStyle.setFont(fontHeading);
			} else {
				groupStyle.setFont(getFont12Bold());
			}
			groupStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
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

	public HSSFCellStyle getHeadingStyle() {
		if (headingStyle == null) {
			headingStyle = wb.createCellStyle();
			if (fontHeading != null) {
				headingStyle.setFont(fontHeading);
			} else {
				headingStyle.setFont(getFont12Bold());
			}
			headingStyle.setBorderBottom(HSSFCellStyle.BORDER_THICK);
			headingStyle.setFillBackgroundColor(HSSFColor.BLUE_GREY.index);
		}
		return headingStyle;
	}

	public HSSFCellStyle getRowStyleBorderBottomBorderTop() {
		if (rowStyleBorderBottomBorderTop == null) {
			rowStyleBorderBottomBorderTop = wb.createCellStyle();
			rowStyleBorderBottomBorderTop
					.setBorderBottom(HSSFCellStyle.BORDER_DOTTED);
			rowStyleBorderBottomBorderTop
					.setBorderTop(HSSFCellStyle.BORDER_MEDIUM_DASHED);
			rowStyleBorderBottomBorderTop.setWrapText(true);
		}
		return rowStyleBorderBottomBorderTop;
	}

	public HSSFCellStyle getRowStyleBorderBottom() {
		if (rowStyleBorderBottom == null) {
			rowStyleBorderBottom = wb.createCellStyle();
			rowStyleBorderBottom.setBorderBottom((short) 3);
			rowStyleBorderBottom.setWrapText(true);
		}
		return rowStyleBorderBottom;
	}

	private HSSFFont getFontBold(final short fontSize) {
		if (font12Bold == null) {
			font12Bold = wb.createFont();
			font12Bold.setFontHeightInPoints(fontSize);
			font12Bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		return font12Bold;
	}

	public HSSFFont getFont12Bold() {
		if (font12Bold == null) {
			font12Bold = wb.createFont();
			font12Bold.setFontHeightInPoints((short) 12);
			font12Bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		return font12Bold;
	}

	private HSSFFont getFont12() {
		if (font12 == null) {
			font12 = wb.createFont();
			font12.setFontHeightInPoints((short) 12);

		}
		return font12;
	}

	private HSSFFont getFont18Bold() {
		if (font18Bold == null) {
			font18Bold = wb.createFont();
			font18Bold.setFontHeightInPoints((short) 18);
			font18Bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		return font18Bold;
	}

	public HSSFFont getFont10() {
		if (font10 == null) {
			font10 = wb.createFont();
			font10.setFontHeightInPoints((short) 10);

		}
		return font10;
	}

	public HSSFFont getFont10Bold() {
		if (font10Bold == null) {
			font10Bold = wb.createFont();
			font10Bold.setFontHeightInPoints((short) 10);
			font10Bold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		}
		return font10Bold;
	}

	public HSSFFont getFont14Bold() {
		if (font14Bold == null) {
			font14Bold = wb.createFont();
			font14Bold.setFontHeightInPoints((short) 14);
			font14Bold.setBoldweight((short) 20);
		}
		return font14Bold;
	}

}