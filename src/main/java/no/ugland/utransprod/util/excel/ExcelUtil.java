package no.ugland.utransprod.util.excel;

import java.awt.Dimension;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.JDialogAdapter;
import no.ugland.utransprod.gui.ProTransMain;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.TransportOrderTableModel;
import no.ugland.utransprod.gui.handlers.TransportOverviewReportHandler.TransportOverviewTableModel;
import no.ugland.utransprod.model.DeviationSumJobFunctionV;
import no.ugland.utransprod.model.PreventiveAction;
import no.ugland.utransprod.model.SalesmanGoal;
import no.ugland.utransprod.model.SumAvvikV;
import no.ugland.utransprod.model.SumAvvikVPK;
import no.ugland.utransprod.model.TakstoltegnerV;
import no.ugland.utransprod.model.TakstoltegnerVSum;
import no.ugland.utransprod.model.Transport;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.MonthEnum;
import no.ugland.utransprod.util.Threadable;
import no.ugland.utransprod.util.TransportComparator;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.report.JiggReportData;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.jdesktop.jdic.desktop.Desktop;
import org.jdesktop.jdic.desktop.DesktopException;
import org.jdesktop.swingx.JXTable;

import com.google.common.collect.ImmutableMap;
import com.google.inject.internal.Maps;

/**
 * Klasse som brukes til generering av excelfiler.
 * 
 * @author abr99
 */
public class ExcelUtil {
	private static final Logger LOGGER = Logger.getLogger(ExcelUtil.class);

	private static SimpleDateFormat dateFormat = new SimpleDateFormat(
			"yyyy.MM.dd");

	private HSSFSheet readSheet;
	private static boolean useUniqueFileName = true;

	public ExcelUtil() {
	}

	public static void setUseUniqueFileName(boolean unique) {
		useUniqueFileName = unique;
	}

	public final void openExcelFileForRead(final String excelFileName)
			throws ProTransException {
		try {
			File excelFile = getFile(excelFileName);
			POIFSFileSystem fileSystem = new POIFSFileSystem(
					new FileInputStream(excelFile));
			HSSFWorkbook workbook = new HSSFWorkbook(fileSystem);
			readSheet = workbook.getSheetAt(0);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new ProTransException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new ProTransException(e);
		}
	}

	public final String readCell(final int row, final int col,
			final String stringFormat) {
		if (readSheet == null) {
			return null;
		}
		HSSFRow excelRow = readSheet.getRow(row);
		if (excelRow != null) {
			return getCellValue(excelRow, col, stringFormat);
		}
		return null;
	}

	public final BigDecimal readCellAsBigDecimal(final int row, final int col) {
		if (readSheet == null) {
			return null;
		}
		String value = readCell(row, col, null);
		if (value != null) {
			return BigDecimal.valueOf(Double.valueOf(value));
		}
		return null;

	}

	public final Integer readCellAsInteger(final int row, final int col) {
		if (readSheet == null) {
			return null;
		}

		String value = readCell(row, col, "%1$.0f");
		if (value != null) {
			return Integer.valueOf(value);
		}
		return null;

	}

	public final void showDataInExcelTransport(final String directory,
			final String fileName, final JLabel labelInfo,
			final Map<Transport, TransportOrderTableModel> tableModels)
			throws ProTransException {
		if (directory == null || directory.length() == 0) {
			throw new ProTransException("Katalog ikke satt");
		}
		String infoString = "Genererer excel-fil...rad ";
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet;

		sheet = getSheet(wb, 10, new int[] { 7000, 1400, 1400, 1200, 1500,
				1500, 1900, 1300, 1000, 3000 });

		int currentRow = 0;
		HSSFRow row;

		CellStyle cellStyle = new CellStyle(wb);

		Set<Transport> transports = tableModels.keySet();
		List<Transport> sortedTransport = new ArrayList<Transport>(transports);
		Collections.sort(sortedTransport, new TransportComparator());
		TableModel tableModel;
		String dateString = "";

		for (Transport transport : sortedTransport) {
			tableModel = tableModels.get(transport);

			String transportFirm = getTransportFirmInfo(transport);
			dateString = getDateString(dateString, transport);

			// Overskrift
			row = sheet.createRow((short) currentRow++);

			createCell(row, cellStyle.getStyle12(), (short) 0, transport
					.getTransportName()
					+ " - lasting: " + dateString);

			row = sheet.createRow((short) currentRow++);
			createCell(row, cellStyle.getStyle12(), (short) 0, "firma:"
					+ transportFirm);

			String comment = transport.getTransportComment();
			if (comment != null && comment.length() > 0) {
				row = sheet.createRow((short) currentRow++);
				createCell(row, cellStyle.getStyle12(), (short) 0, "kommentar:"
						+ comment);
			}

			row = sheet.createRow((short) currentRow++);

			int columnCount = tableModel.getColumnCount();
			int rowCount = tableModel.getRowCount();

			createColumnHeadings(row, cellStyle.getStyle12BorderBottomThick(),
					tableModel, (short) 0, columnCount - 1, 0, null);

			// Data
			int j;
			int k;
			for (j = currentRow; j < rowCount + currentRow; j++) {
				setLabelInfo(labelInfo, infoString, j);
				row = sheet.createRow((short) j);

				for (k = 0; k < columnCount; k++) {
					// kommentar
					if (k == 11) {
						// har kommentar
						if (tableModel.getValueAt(j - currentRow, k) != null) {
							// lager egen linje for kommentar
							row = sheet.createRow((short) j + 1);
							createCell(row, null, (short) 0, String
									.valueOf(tableModel.getValueAt(j
											- currentRow++, k)));

							j++;
						}
					} else {
						if (tableModel.getValueAt(j - currentRow, k) != null) {
							Class<?> clazz = tableModel.getColumnClass(k);

							if (clazz.equals(Integer.class)
									|| clazz.equals(BigDecimal.class)) {
								createCell(
										row,
										cellStyle
												.getStyle10BorderBottomDashedBorderTopMedium(true),
										(short) k,
										String
												.valueOf(Double
														.valueOf(
																String
																		.valueOf(tableModel
																				.getValueAt(
																						j
																								- currentRow,
																						k)))
														.doubleValue()));
							} else {
								createCell(
										row,
										cellStyle
												.getStyle10BorderBottomDashedBorderTopMedium(true),
										(short) k, String.valueOf(tableModel
												.getValueAt(j - currentRow, k)));
							}
						} else {
							createCell(
									row,
									cellStyle
											.getStyle10BorderBottomDashedBorderTopMedium(true),
									(short) k, "");
						}
					}
				}
			}
			currentRow = j + 1;

		}
		openExcelFile(fileName, directory, wb, true);
	}

	public final void showDataInExcelTransportOverview(
			final String directory,
			final String fileName,
			final JLabel labelInfo,
			final Map<Integer, Map<Transport, TransportOverviewTableModel>> tableModels)
			throws ProTransException {
		if (directory == null || directory.length() == 0) {
			throw new ProTransException("Katalog ikke satt");
		}
		String infoString = "Genererer excel-fil...rad ";
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet;

		sheet = getSheet(wb, 9, new int[] { 7000, 1400, 1400, 1200, 1500, 1500,
				1900, 1300, 1000 });

		int currentRow = 0;
		HSSFRow row;
		Map<Integer, HSSFRow> rader = new HashMap<Integer, HSSFRow>();

		CellStyle cellStyle = new CellStyle(wb);

		short addColumn = -1;
		short weekAdd = -3;

		Set<Integer> weeks = tableModels.keySet();
		List<Integer> sortedWeeks = new ArrayList<Integer>(weeks);
		Collections.sort(sortedWeeks);
		for (Integer week : sortedWeeks) {
			addColumn++;
			weekAdd += 3;
			currentRow = 0;
			if (weekAdd > 0) {
				sheet.setColumnWidth((short) (weekAdd - 1), (short) 500);
			}
			sheet.setColumnWidth(weekAdd, (short) 7000);

			Map<Transport, TransportOverviewTableModel> models = tableModels
					.get(week);
			Set<Transport> transports = models.keySet();

			List<Transport> sortedTransport = new ArrayList<Transport>(
					transports);
			Collections.sort(sortedTransport, new TransportComparator());

			row = rader.get(currentRow);
			if (row == null) {
				row = sheet.createRow((short) currentRow);
				rader.put(currentRow, row);
			}
			currentRow++;
			createCell(row, cellStyle.getStyle14Bold(), weekAdd, "Uke: " + week);

			for (Transport transport : sortedTransport) {

				currentRow = createRowsForTransport(labelInfo, infoString,
						sheet, currentRow, cellStyle, weekAdd, models,
						transport,rader);

			}
		}
		openExcelFile(fileName, directory, wb, true);
	}

	public static void showDataInExcelInThread(final WindowInterface window,
			final String fileName, final String heading, final JXTable table,
			final List<Integer> numberCols,
			final Map<Integer, Integer> colSize, final int headFontSize,
			final boolean wrapText) {
		Util.runInThreadWheel(window.getRootPane(), new Threadable() {

			public void enableComponents(final boolean enable) {
			}

			public Object doWork(final Object[] params, final JLabel labelInfo) {
				String errorMsg = null;
				try {
					String directory = ApplicationParamUtil
							.findParamByName("excel_path");
					showDataInExcel(directory, fileName, labelInfo, heading,
							table, numberCols, colSize, headFontSize, wrapText);
				} catch (ProTransException e) {
					e.printStackTrace();
					errorMsg = e.getMessage();
				}
				return errorMsg;
			}

			public void doWhenFinished(final Object object) {
				if (object != null) {
					Util.showErrorDialog(window, "Feil", object.toString());
				}

			}

		}, null);
	}

	public static void showDataInExcel(final String directory,
			final String fileName, final JLabel labelInfo,
			final String heading, final JXTable table,
			final List<Integer> numberCols,
			final Map<Integer, Integer> colSize, final int headFontSize,
			final boolean wrapText) throws ProTransException {
		showDataInExcel(directory, fileName, labelInfo, heading, table,
				numberCols, colSize, headFontSize, null, null, null, null,
				null, wrapText);
	}

	/**
	 * @param directory
	 *            - katalog hvor excelfil skal skrives
	 * @param fileName
	 *            - filnavn
	 * @param labelInfo
	 *            - label som det skrives info til under generering
	 * @param heading
	 *            - overskrift
	 * @param table
	 *            tabell med data som skal vises
	 * @param numberCols
	 *            - kolonner som skal være tall
	 * @param colSize
	 *            - kolonnestørrelser
	 * @param headFontSize
	 *            - fontstørrelse på overskrift
	 * @param groupColumn
	 *            - kolonne det skal grupperes på, denne kolonnen vil da bli
	 *            brukt som overskrift for hver gruppering
	 * @param notVisibleColumns
	 *            - kolonner som ikke skal være synlige
	 * @param groupSumValueColumn
	 *            - kolonne det skal grupperes og summeres etter
	 * @param groupSumColumn
	 *            - kolonne som skal summeres når det er en ny gruppering ut fra
	 *            groupSumValueColumn
	 * @param groupResultColumn
	 *            - kolonne summeringen av grupperingen skal skrives til
	 * @param wrapText
	 *            true dersom cellene skal ha satt wraptext
	 * @throws ProTransException
	 */
	public static void showDataInExcel(final String directory,
			final String fileName, final JLabel labelInfo,
			final String heading, final JXTable table,
			final List<Integer> numberCols,
			final Map<Integer, Integer> colSize, final int headFontSize,
			final Integer groupColumn, final List<Integer> notVisibleColumns,
			final Integer groupSumValueColumn, final Integer groupSumColumn,
			final Integer groupResultColumn, final boolean wrapText)
			throws ProTransException {
		if (directory == null || directory.length() == 0) {
			throw new ProTransException("Katalog ikke satt");
		}
		int notVisibleColumnsSize = 0;
		// dersom kolonner skal være usynlige må disse trekkes fra ved laging av
		// celler
		if (notVisibleColumns != null) {
			notVisibleColumnsSize = notVisibleColumns.size();
		}
		String infoString = "Genererer excel-fil...rad ";
		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet;

		sheet = wb.createSheet("sheet");
		int currentRow = 0;
		HSSFRow row;
		HSSFCell cell;

		CellStyle cellStyle = new CellStyle(wb, (short) headFontSize);

		// Overskrift
		if (heading != null && heading.length() != 0) {
			row = sheet.createRow((short) currentRow++);
			createCell(row, cellStyle.getHeadingStyle(), (short) 0, heading);
		}

		// Kolonneoverskrift
		row = sheet.createRow((short) currentRow++);
		int columnCount = table.getColumnCount();
		int rowCount = table.getRowCount();

		// Skriver ut kolonneoverskrift
		createColumnHeadings(row, cellStyle.getHeadingStyle(),
				table.getModel(), 0, columnCount, 0, notVisibleColumns);

		String groupValue = "";
		List<ExcelGroupSum> formulaCells = new ArrayList<ExcelGroupSum>();
		String groupSumValue = "";
		ExcelGroupSum currentExcelGroupSum = null;

		// Data
		int j;
		int k;
		int l = currentRow;
		// Går gjennom alle rader og kolonner
		for (j = currentRow; j < rowCount + currentRow; j++) {
			// dersom data skal grupperes
			if (groupColumn != null
					&& !table.getValueAt(j - currentRow, groupColumn).equals(
							groupValue)) {
				// setter forrige grupperingssum
				if (currentExcelGroupSum != null) {
					currentExcelGroupSum.setToRow((short) (l));
					formulaCells.add(currentExcelGroupSum);
					groupSumValue = "";
					currentExcelGroupSum = null;
				}
				// henter grupperingsverdi og setter ny overskrift for
				// gruppering
				groupValue = (String) table.getValueAt(j - currentRow,
						groupColumn);
				row = sheet.createRow((short) l);
				createCell(row, cellStyle.getGroupStyle(), (short) 0,
						groupValue);
				sheet.addMergedRegion(new Region((short) l, (short) 0,
						(short) l,
						(short) (columnCount - notVisibleColumnsSize - 1)));
				l++;

			}
			setLabelInfo(labelInfo, infoString, j);

			row = sheet.createRow((short) l);
			l++;

			// går gjennom alle kolonner for rad
			for (k = 0; k < columnCount; k++) {
				// dersom kolonne skal være synlig
				if (notVisibleColumns == null || !notVisibleColumns.contains(k)) {
					// dersom kolonnebredde er satt
					if (colSize != null) {
						Integer columnSize = colSize.get(k);
						if (columnSize != null) {
							sheet.setColumnWidth((short) k, columnSize
									.shortValue());
						}
					}
					cell = row.createCell((short) k);
					// dersom celle har verdi
					if (table.getValueAt(j - currentRow, k) != null) {

						// dersom det er grupperingssum satt og den er ulik
						// forrige
						if (groupSumValueColumn != null
								&& !table.getValueAt(j - currentRow,
										groupSumValueColumn).equals(
										groupSumValue)) {
							groupSumValue = (String) table.getValueAt(j
									- currentRow, groupSumValueColumn);

							short dayColorIndex = DayEnum
									.getDayColorIndex(groupSumValue);

							if (currentExcelGroupSum != null) {
								currentExcelGroupSum.setToRow((short) (l - 1));
								formulaCells.add(currentExcelGroupSum);
							}
							if (groupResultColumn != null) {
								currentExcelGroupSum = new ExcelGroupSum(
										(short) l, row
												.createCell(groupResultColumn
														.shortValue()),
										groupSumColumn.shortValue(),
										dayColorIndex, wb.createCellStyle());
							}

						}
						cell.setCellStyle(cellStyle.createDayStyle(wrapText,
								groupSumValue));

						// dersom kolonne ikke er summeringskolonne
						if (groupResultColumn == null || k != groupResultColumn) {
							setCellValue(table, numberCols, currentRow, 0,
									cell, j, k);
						}
						// dersom celle ikke har verdi settes den til tomstreng
						// for å få med eventuell formatering
					} else {
						cell
								.setCellStyle(cellStyle.createDayStyle(false,
										null));
						cell.setCellValue(new HSSFRichTextString(""));
					}
				}
			}
		}
		// setter siste grupperingssum dersom det finnes
		if (currentExcelGroupSum != null) {
			currentExcelGroupSum.setToRow((short) l);
			formulaCells.add(currentExcelGroupSum);
		}

		// går gjennom grupperingssummer og skriver inn formler
		setFormulas(groupResultColumn, sheet, 0, formulaCells);
		openExcelFile(fileName, directory, wb, true);
	}

	public final void generateExcel(final ExcelReportSetting setting,
			final WindowInterface window) throws ProTransException {
		WindowInterface dialogWindow = null;
		boolean disposeWindow = true;
		if (window != null) {
			dialogWindow = window;
			disposeWindow = false;
		} else {
			JDialog dialog = new JDialog(ProTransMain.PRO_TRANS_MAIN, false);
			dialog.setSize(new Dimension(250, 250));

			dialogWindow = new JDialogAdapter(dialog);
			Util.locateOnScreenCenter(dialogWindow);
			dialog.setVisible(true);
		}
		Dimension dim = dialogWindow.getSize();

		dialogWindow.setSize(new Dimension(dim.width, 250));

		ExcelGenerator excelGenerator = new ExcelGenerator(dialogWindow,
				setting, disposeWindow, dim);
		Util.runInThreadWheel(dialogWindow.getRootPane(), excelGenerator, null);
		// new ExcelGenerator(dialogWindow, setting,disposeWindow,
		// dim).doWork(null, null);
	}

	public final void generateDeviationSummary(
			final ExcelReportSetting reportSetting,
			final Map<Object, Object> data) throws ProTransException {
		String excelPath = getExcelPath();
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = getSheet(wb, 1, new int[] { 4000 });

		int currentRow = 0;
		HSSFRow row;

		CellStyle cellStyle = new CellStyle(wb);

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle18Bold(), (short) 2, "Avviksrapport");

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle10Bold(), (short) 0, "Avvik måned");
		createCell(row, cellStyle.getStyle10Bold(), (short) 1,
				((ExcelReportSettingDeviation) reportSetting).getMonthEnum()
						.getMonthString());
		createCell(row, cellStyle.getStyle10Bold(), (short) 3, "År");
		createCell(row, cellStyle.getStyle10Bold(), (short) 4, String
				.valueOf(reportSetting.getYear()));

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 0, "Produktgruppe");
		createCell(row, cellStyle.getStyle10Bold(), (short) 1,
				((ExcelReportSettingDeviation) reportSetting).getProductArea()
						.getProductArea());

		currentRow = createEmptyRows(sheet, currentRow, 2);

		// **
		// **********************************PERIODE***************************************
		// */
		currentRow = generateDeviationSummarySub(wb, sheet, currentRow, data,
				"Period", "Antall Avvik", "Sum", cellStyle);
		// **************************HITTIL I
		// ÅR***************************************
		currentRow = generateDeviationSummarySub(wb, sheet, currentRow, data,
				"Year", "Avvik hittil i år", "Sum hittil i år", cellStyle);

		// **************************SAMMENLIKNING MED I
		// FJOR***************************************
		currentRow = createEmptyRows(sheet, currentRow, 2);
		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 0,
				"(sammenligning med samme periode i fjor)");

		currentRow = generateDeviationSummarySub(wb, sheet, currentRow, data,
				"LastYear", "Antall Avvik", "Sum", cellStyle);

		currentRow = createEmptyRows(sheet, currentRow, 1);
		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 0, "Eventuelt");

		currentRow = createEmptyRows(sheet, currentRow, 13);

		// *************************** AKTIVE TILTAK
		// *****************************
		currentRow = generateDeviationSummaryPreventiveAction(wb, sheet,
				currentRow, data, "PreventiveActionOpen", "Aktive tiltak",
				cellStyle);

		// *************************** LUKKEDE TILTAK
		// *****************************
		currentRow = generateDeviationSummaryPreventiveAction(wb, sheet,
				currentRow, data, "PreventiveActionClosed", "Lukkede tiltak",
				cellStyle);

		currentRow = createEmptyRows(sheet, currentRow, 2);

		row = sheet.createRow((short) currentRow++);

		currentRow = generateDeviationOverview(wb, sheet, currentRow, data,
				cellStyle);

		openExcelFile(reportSetting.getExcelReportType().getExcelFileName()
				+ ".xls", excelPath, wb, true);
	}

	public final String getHeaderLine() {
		StringBuilder header = new StringBuilder();
		int col = 0;
		String columnValue = readCell(0, col, null);
		while (columnValue != null) {
			header.append(columnValue).append(";");
			col++;
			columnValue = readCell(0, col, null);
		}
		return header.toString();
	}

	private String getCellValue(final HSSFRow excelRow, final int column,
			final String stringFormat) {
		HSSFCell cell = excelRow.getCell((short) column);
		String value = null;
		if (cell != null) {
			if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
				if (stringFormat != null) {
					value = String.format(stringFormat, cell
							.getNumericCellValue());
				} else {
					value = String.valueOf(cell.getNumericCellValue());
				}
			} else {
				value = cell.getRichStringCellValue().getString();
			}
		}
		if (value != null && value.length() == 0) {
			value = null;
		}
		return value;

	}

	private File getFile(final String fileName) throws ProTransException {
		File file = new File(fileName);
		if (!file.exists()) {
			throw new ProTransException("Fil " + fileName + " finnes ikke!");
		}
		return file;
	}

	private static void openExcelFile(final String fileName,
			final String directory, final HSSFWorkbook wb,
			final boolean useUniqueFileName) throws ProTransException {
		try {
			checkExcelDir(directory);

			String fullfileName = createFullFileName(directory, fileName,
					useUniqueFileName);

			// String cmd = createCmd(fullfileName);

			writeExcelFile(fullfileName, wb);
			File excelFile = new File(fullfileName);
			Desktop.open(excelFile);
			// Runtime.getRuntime().exec(cmd);
		} catch (DesktopException de) {
			LOGGER.error("Feil ved visning av exceldata", de);
			de.printStackTrace();
			throw new ProTransException(de);
		}/*
		 * catch (IOException e) { LOGGER.error("Feil ved visning av exceldata",
		 * e); e.printStackTrace(); throw new ProTransException(e); }
		 */
	}

	private static void writeExcelFile(final String fullFileName,
			final HSSFWorkbook wb) throws ProTransException {
		try {
			FileOutputStream fileOut = new FileOutputStream(fullFileName
					.toString());
			wb.write(fileOut);
			fileOut.close();

		} catch (FileNotFoundException e) {
			LOGGER.error("Feil ved visning av exceldata", e);
			e.printStackTrace();
			throw new ProTransException(e);
		} catch (IOException e) {
			LOGGER.error("Feil ved visning av exceldata", e);
			e.printStackTrace();
			throw new ProTransException(e);
		}
	}

	private static void checkExcelDir(final String directory) {
		File dir = new File(directory);

		if (!dir.exists()) {
			dir.mkdir();
		}

	}

	private static String createFullFileName(final String directory,
			final String fileName, final boolean useUniqueFileName) {
		StringBuilder fullFileName = new StringBuilder(directory);
		if (useUniqueFileName) {
			fullFileName.append(createUniqueFileName(fileName));
		} else {
			fullFileName.append(fileName);
		}
		return fullFileName.toString();

	}

	private static String createUniqueFileName(final String fileName) {
		StringBuilder uniqueFileName = new StringBuilder();
		int indexOfExtension = fileName.indexOf("xls");
		if (indexOfExtension >= 0) {
			uniqueFileName.append(fileName.substring(0, indexOfExtension - 1))
					.append(Util.getCurrentDateAsDateTimeStringWithSeconds())
					.append(".xls");
		} else {
			uniqueFileName.append(fileName);
		}
		return uniqueFileName.toString();
	}

	private static String createCmd(final String fullFileName) {
		StringBuilder cmd = getAndCheckExcelStartPath(fullFileName);

		if (cmd == null) {
			cmd = new StringBuilder("cmd /C excel.exe \"").append(fullFileName)
					.append("\"");
		}
		return cmd.toString();
	}

	private static StringBuilder getAndCheckExcelStartPath(
			final String fullFileName) {
		StringBuilder cmd = null;
		String excelFilePaths = ApplicationParamUtil
				.findParamByName("excel_start_path");
		String[] paths = excelFilePaths.split(";");
		if (paths != null) {
			String excelPath = checkExcelStartPath(paths);
			if (excelPath != null) {
				cmd = new StringBuilder("cmd /C ").append("\"").append(
						excelPath).append(" \" ").append(fullFileName);
			}

		}
		return cmd;
	}

	private static String checkExcelStartPath(final String[] paths) {
		List<String> pathStrings = Arrays.asList(paths);
		File excelFile;
		for (String path : pathStrings) {

			excelFile = new File(path);

			if (excelFile.exists()) {
				return excelFile.getAbsolutePath();
			}
		}
		return null;
	}

	private String getDateString(final String stringDate,
			final Transport transport) {
		String dateString = stringDate;
		if (transport.getLoadingDate() != null) {
			dateString = dateFormat.format(transport.getLoadingDate());
		}
		if (transport.getLoadTime() != null) {
			dateString += " tid:" + transport.getLoadTime();
		}
		return dateString;
	}

	private String getTransportFirmInfo(final Transport transport) {
		String transportFirm = "";
		if (transport.getSupplier() != null) {
			transportFirm = transport.getSupplier().getSupplierName();
		}

		if (transport.getEmployee() != null) {
			transportFirm += " sjåfør:"
					+ Util.nullToString(transport.getEmployee().getFirstName())
					+ " "
					+ Util.nullToString(transport.getEmployee().getLastName());

			if (transport.getEmployee().getPhone() != null) {
				transportFirm += " tlf. " + transport.getEmployee().getPhone();
			}
		}
		return transportFirm;
	}

	private int createRowsForTransport(final JLabel labelInfo,
			final String infoString, final HSSFSheet sheet,
			final int rowNumber, final CellStyle cellStyle,
			final short weekAdd,
			final Map<Transport, TransportOverviewTableModel> models,
			final Transport transport, final Map<Integer, HSSFRow> rader) {
		HSSFRow row;
		int currentRow = rowNumber;

		TableModel tableModel = models.get(transport);

		row = rader.get(currentRow);
		if (row == null) {
			row = sheet.createRow((short) currentRow);
			rader.put(currentRow, row);
		}
		currentRow++;
		createCell(row, cellStyle.getStyle12Bold(), weekAdd, transport
				.getTransportName());

		row = rader.get(currentRow);
		if (row == null) {
			row = sheet.createRow((short) currentRow);
			rader.put(currentRow, row);
		}
		currentRow++;
		int columnCount = tableModel.getColumnCount();
		int rowCount = tableModel.getRowCount();

		createColumnHeadings(row, cellStyle.getStyle12BoldBorderBottomThick(),
				tableModel, weekAdd, columnCount, 0, null);

		int j;

		for (j = currentRow; j < rowCount + currentRow; j++) {
			setCellData(labelInfo, infoString, sheet, currentRow, cellStyle,
					tableModel, weekAdd, columnCount, j,rader);
		}
		currentRow = j + 1;
		return currentRow;
	}

	private static void createColumnHeadings(final HSSFRow row,
			final HSSFCellStyle cellStyle, final TableModel tableModel,
			final int addColumn, final int columnCount, final int startCell,
			final List<Integer> notVisibleColumns) {

		for (int i = startCell; i < columnCount + startCell; i++) {
			if (notVisibleColumns == null
					|| !notVisibleColumns.contains(i - startCell)) {
				String columnHeading = tableModel.getColumnName(i - startCell);
				createCell(row, cellStyle, (short) (i + addColumn),
						columnHeading);
			}
		}
	}

	private int setCellData(final JLabel labelInfo, final String infoString,
			final HSSFSheet sheet, final int currentRow,
			final CellStyle cellStyle, final TableModel tableModel,
			final short weekAdd, final int columnCount, final int j,
			Map<Integer, HSSFRow> rader) {
		HSSFRow row;
		int k;
		setLabelInfo(labelInfo, infoString, j);
		row = rader.get(j);
		if (row == null) {
			row = sheet.createRow((short) j);
			rader.put(j, row);
		}

		for (k = 0; k < columnCount; k++) {
			if (tableModel.getValueAt(j - currentRow, k) != null) {
				createCell(row, cellStyle.getStyle10BorderBottomDashed(true),
						(short) (k + weekAdd), String.valueOf(tableModel
								.getValueAt(j - currentRow, k)));
			} else {
				createCell(row, cellStyle.getStyle10BorderBottomDashed(true),
						(short) (k + weekAdd), "");
			}
		}
		return k;
	}

	private static void createCell(final HSSFRow row,
			final HSSFCellStyle cellStyle, final short column,
			final String value) {
		HSSFCell cell;
		cell = row.createCell(column);
		cell.setCellValue(new HSSFRichTextString(value));
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
	}

	private static void createCell(final HSSFRow row,
			final HSSFCellStyle cellStyle, final short column,
			final String value, final Double doubleValue) {
		HSSFCell cell;
		cell = row.createCell(column);
		if (doubleValue != null) {
			cell.setCellValue(doubleValue);
		} else {
			cell.setCellValue(new HSSFRichTextString(value));
		}
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
	}

	private HSSFSheet getSheet(final HSSFWorkbook wb,
			final int numberOfColumns, final int[] sizes) {
		HSSFSheet sheet;
		sheet = wb.createSheet("sheet");
		for (int i = 0; i < numberOfColumns; i++) {
			sheet.setColumnWidth((short) i, (short) sizes[i]);
		}

		return sheet;
	}

	static void showDataInExcelSub(final String directory,
			final String fileName, final JLabel labelInfo,
			final String heading,
			final Map<ExcelReportEnum, ExcelReportData> excelReportData,
			final int headFontSize, final boolean useUniqueFileName)
			throws ProTransException {
		if (directory == null || directory.length() == 0) {
			throw new ProTransException("Katalog ikke satt");
		}

		HSSFWorkbook wb = new HSSFWorkbook();
		HSSFSheet sheet = wb.createSheet("sheet");

		int currentRow = 0;
		int startCell = 0;

		HSSFRow row;

		CellStyle cellStyle = new CellStyle(wb, (short) headFontSize);

		// Overskrift
		if (heading != null && heading.length() != 0) {
			row = sheet.createRow((short) currentRow++);
			createCell(row, cellStyle.createStyle((short) headFontSize,
					HSSFFont.BOLDWEIGHT_BOLD, HSSFColor.BLUE_GREY.index,
					HSSFCellStyle.ALIGN_LEFT), (short) startCell, heading);
		}

		Set<ExcelReportEnum> reports = excelReportData.keySet();
		int reportCount = 0;

		for (ExcelReportEnum report : reports) {
			reportCount++;
			if (reportCount > 1) {
				currentRow += 2;
			}
			currentRow = generateSheet(labelInfo, excelReportData.get(report)
					.getTable(), report.getNumCols(), report.getColumnSizes(),
					report.getHeadFontSize(), report.getGroupColumn(), report
							.getNotVisibleColumns(), report
							.getGroupSumValueColumn(), wb, report
							.getGroupSumColumn(),
					report.getGroupResultColumn(), report.isWrapText(), sheet,
					report.getStartCell(), currentRow, report
							.isAllwaysShowHeading(),
					report.getWriteRowNumber(), excelReportData.get(report)
							.getInfoTop(), excelReportData.get(report)
							.getInfoButtom(), report.getSumFormula(), cellStyle);
		}
		openExcelFile(fileName, directory, wb, useUniqueFileName);

	}

	private static int generateSheet(final JLabel labelInfo,
			final JXTable table, final List<Integer> numberCols,
			final Map<Integer, Integer> colSize, final int headFontSize,
			final Integer groupColumn, final List<Integer> notVisibleColumns,
			final Integer groupSumValueColumn, final HSSFWorkbook wb,
			final Integer sumGroupColumn, final Integer groupResultColumn,
			final boolean wrapText, final HSSFSheet excelSheet,
			final int cellStart, final int currentRowNumber,
			final boolean allwaysShowHeading, final boolean writeRowNumber,
			final String infoTextTop, final String infoTextButtom,
			final ExcelSumFormula sumFormula, final CellStyle cellStyle)
			throws ProTransException {
		int currentRow = currentRowNumber;
		int startCell = cellStart;
		Integer groupSumColumn = sumGroupColumn;
		if (excelSheet == null) {
			throw new ProTransException("Sheet er ikke laget");
		}
		if (!allwaysShowHeading && table.getRowCount() == 0) {
			return currentRow;
		}
		String infoString = "Genererer excel-fil...rad ";
		int notVisibleColumnsSize = 0;
		if (notVisibleColumns != null) {
			notVisibleColumnsSize = notVisibleColumns.size();
		}

		currentRow = setInfoText(excelSheet, infoTextTop, startCell,
				currentRow, cellStyle.getStyle10Bold());

		HSSFRow row = excelSheet.createRow((short) currentRow++);
		int columnCount = table.getColumnCount();
		int rowCount = table.getRowCount();
		// kolonne som har blitt lagt til pga utskrift av radnummer
		int addedColumn = 0;
		// dersom radnummer skal skrives ut
		if (writeRowNumber) {
			excelSheet.setColumnWidth((short) 0, (short) 900);
			startCell += 1;
			if (groupSumColumn != null) {
				groupSumColumn += 1;
			}
			addedColumn = 1;
		}
		// Skriver ut kolonneoverskrift
		createColumnHeadings(row, cellStyle.getHeadingStyle(),
				table.getModel(), 0, columnCount, startCell, notVisibleColumns);
		// Data
		int j;
		int k;
		int l = currentRow;
		int rowNumber = 0;

		// det skal legges til en summeringsformel på sluttet
		if (sumFormula != null) {
			sumFormula.setFromRow(l + 1);
		}
		HSSFCell cell;
		String groupValue = "";
		List<ExcelGroupSum> formulaCells = new ArrayList<ExcelGroupSum>();
		String groupSumValue = "";
		ExcelGroupSum currentExcelGroupSum = null;
		// Går gjennom alle rader og kolonner
		for (j = currentRow; j < rowCount + currentRow; j++) {
			rowNumber++;
			// dersom data skal grupperes
			if (groupColumn != null
					&& !table.getValueAt(j - currentRow, groupColumn).equals(
							groupValue)) {
				// setter forrige grupperingssum
				if (currentExcelGroupSum != null) {
					currentExcelGroupSum.setToRow((short) (l));
					formulaCells.add(currentExcelGroupSum);
					groupSumValue = "";
					currentExcelGroupSum = null;
					rowNumber = 1;
				}
				// henter grupperingsverdi og setter ny overskrift for
				// gruppering
				groupValue = (String) table.getValueAt(j - currentRow,
						groupColumn);
				row = excelSheet.createRow((short) l);

				createCell(row, cellStyle.getGroupStyle(),
						(short) (startCell - addedColumn), groupValue);
				excelSheet.addMergedRegion(new Region((short) l, (short) 0,
						(short) l, (short) (columnCount + addedColumn
								- notVisibleColumnsSize - 1)));
				l++;

			}
			setLabelInfo(labelInfo, infoString, j);
			row = excelSheet.createRow((short) l);
			l++;
			// dersom det skal skrives ut rad nummer
			if (writeRowNumber) {
				cell = row.createCell((short) (startCell - 1));
				cell.setCellValue(rowNumber);
			}
			// går gjennom alle kolonner for rad
			for (k = startCell; k < columnCount + startCell; k++) {
				// dersom kolonne skal være synlig
				if (notVisibleColumns == null
						|| !notVisibleColumns.contains(k - startCell)) {
					setColumnSize(colSize, excelSheet, startCell, addedColumn,
							k);
					cell = row.createCell((short) k);
					// dersom celle har verdi
					if (table.getValueAt(j - currentRow, k - startCell) != null) {
						// dersom det er grupperingssum satt og den er ulik
						// forrige
						if (groupSumValueColumn != null
								&& !table.getValueAt(j - currentRow,
										groupSumValueColumn).equals(
										groupSumValue)) {

							groupSumValue = (String) table.getValueAt(j
									- currentRow, groupSumValueColumn);
							if (currentExcelGroupSum != null) {
								currentExcelGroupSum.setToRow((short) (l - 1));
								formulaCells.add(currentExcelGroupSum);
							}
							if (groupResultColumn != null
									&& groupSumColumn != null) {
								currentExcelGroupSum = new ExcelGroupSum(
										(short) l,
										row
												.createCell((short) (groupResultColumn
														.shortValue() + addedColumn)),
										groupSumColumn.shortValue(),
										DayEnum.getDayColorIndex(groupSumValue),
										wb.createCellStyle());
							}
						}
						cell.setCellStyle(cellStyle.createDayStyle(false,
								groupSumValue));
						setCellValueForNotSumColumn(table, numberCols,
								groupResultColumn, currentRow, startCell, cell,
								j, k);
						// dersom celle ikke har verdi settes den til tomstreng
						// for å få med eventuell formatering
					} else {
						cell
								.setCellStyle(cellStyle.getDayStyle(wrapText,
										null));
						cell.setCellValue(new HSSFRichTextString(""));
					}
				}
			}
		}
		// det skal legges til en summeringsformel på sluttet
		if (sumFormula != null) {
			sumFormula.setToRow(l);
		}
		// setter siste grupperingssum dersom det finnes
		if (currentExcelGroupSum != null) {
			currentExcelGroupSum.setToRow((short) l);
			formulaCells.add(currentExcelGroupSum);
		}
		setFormulas(groupResultColumn, excelSheet, addedColumn, formulaCells);
		l = setSumFormula(excelSheet, sumFormula, cellStyle, addedColumn, l,
				columnCount - 1, startCell);
		l = setInfoText(excelSheet, infoTextButtom, startCell, l, null);
		return l;
	}

	private static void setLabelInfo(final JLabel labelInfo,
			final String infoString, final int j) {
		// setter genereringsinfo dersom label ikke er NULL
		if (labelInfo != null) {
			labelInfo.setText(infoString + (j + 1));
		}
	}

	private static void setCellValueForNotSumColumn(final JXTable table,
			final List<Integer> numberCols, final Integer groupResultColumn,
			final int currentRow, final int startCell, final HSSFCell cell,
			final int j, final int k) {
		// dersom kolonne ikke er summeringskolonne
		if (groupResultColumn == null || k - startCell != groupResultColumn) {

			setCellValue(table, numberCols, currentRow, startCell, cell, j, k);
		}
	}

	private static int setInfoText(final HSSFSheet excelSheet,
			final String infoText, final int startCell,
			final int currentRowNumber, final HSSFCellStyle cellStyle) {
		HSSFRow row;
		int l = currentRowNumber;
		if (infoText != null) {
			row = excelSheet.createRow((short) l);
			l++;

			createCell(row, cellStyle, (short) startCell, infoText);
		}
		return l;
	}

	private static int setSumFormula(final HSSFSheet excelSheet,
			final ExcelSumFormula sumFormula, final CellStyle cellStyle,
			final int addedColumn, final int currentRowNumber,
			final int maxColumn, final int startCell) {
		HSSFRow row;
		HSSFCell cell;
		int l = currentRowNumber;
		if (sumFormula != null
				&& sumFormula.getToRow() > sumFormula.getFromRow()) {
			row = excelSheet.createRow((short) l);

			l++;

			List<Integer> sumColumns = sumFormula.getSumColumns();
			// int counter = 0;
			int tmpMaxColumn = maxColumn + startCell;
			int ignoreCell = 0;
			for (int i = startCell; i <= tmpMaxColumn; i++) {

				// for (Integer column : sumColumns) {
				/*
				 * counter++; if (counter == 1) { if (sumFormula.getInfoText()
				 * != null) { createCell(row, cellStyle.getHeadingStyle(),
				 * (short) (sumFormula.getInfoColumn()),
				 * sumFormula.getInfoText()); } }
				 */

				if (sumColumns.contains(Integer.valueOf(i))) {
					createCell(row, cellStyle.getHeadingStyle(), (short) i, "");
					cell = row.createCell((short) (i + startCell));
					String columnLetter = getColLetter(i + startCell);
					ignoreCell = i + startCell;

					cell.setCellFormula("sum(" + columnLetter
							+ sumFormula.getFromRow() + ":" + columnLetter
							+ sumFormula.getToRow() + ")");
					cell.setCellStyle(cellStyle.getHeadingStyle());
				} else {
					String text = i == startCell
							&& sumFormula.getInfoText() != null ? sumFormula
							.getInfoText() : "";
					if (i != ignoreCell) {
						createCell(row, cellStyle.getHeadingStyle(), (short) i,
								text);
					}
				}
			}

			if (sumFormula.getSumLineFormulaColumn() != null) {
				cell = row.createCell(sumFormula.getSumLineFormulaColumn()
						.shortValue());
				cell.setCellFormula(sumFormula.getSumLineFormula());
				cell.setCellStyle(cellStyle.getHeadingStyle());
			}
		}
		return l;
	}

	private static void setFormulas(final Integer groupResultColumn,
			final HSSFSheet excelSheet, final int addedColumn,
			final List<ExcelGroupSum> formulaCells) {
		// går gjennom grupperingssummer og skriver inn formler
		for (ExcelGroupSum excelGroupSum : formulaCells) {
			excelGroupSum.setFormula();

			if (groupResultColumn != null) {
				excelSheet.addMergedRegion(new Region((short) (excelGroupSum
						.getFromRow() - 1), (short) (groupResultColumn
						.shortValue() + addedColumn), (short) (excelGroupSum
						.getToRow() - 1), (short) (groupResultColumn
						.shortValue() + addedColumn)));
			}
		}
	}

	private static void setColumnSize(final Map<Integer, Integer> colSize,
			final HSSFSheet excelSheet, final int startCell,
			final int addedColumn, final int k) {
		// dersom kolonnebredde er satt
		if (colSize != null) {
			Integer columnSize = colSize.get(k - startCell - addedColumn);
			if (columnSize != null) {
				excelSheet.setColumnWidth((short) (k - startCell), columnSize
						.shortValue());
			}
		}
	}

	private static void setCellValue(final JXTable table,
			final List<Integer> numberCols, final int currentRow,
			final int startCell, final HSSFCell cell, final int j, final int k) {
		if (numberCols != null
				&& numberCols.contains(new Integer(k - startCell))) {
			cell.setCellValue(Double.valueOf(
					String.valueOf(table.getValueAt(j - currentRow, k
							- startCell))).doubleValue());
		} else {
			setCellValueBasedOnClass(table, currentRow, startCell, cell, j, k);
		}
	}

	private static void setCellValueBasedOnClass(final JXTable table,
			final int currentRow, final int startCell, final HSSFCell cell,
			final int j, final int k) {
		Class<?> clazz = table.getColumnClass(k - startCell);
		if (clazz.equals(Integer.class) || clazz.equals(BigDecimal.class)) {
			cell.setCellValue(Double.valueOf(
					String.valueOf(table.getValueAt(j - currentRow, k
							- startCell))).doubleValue());
		} else if (clazz.equals(Boolean.class)) {
			cell.setCellValue(new HSSFRichTextString(String.valueOf(Util
					.convertBooleanToString((Boolean) table.getValueAt(j
							- currentRow, k - startCell)))));
		} else {
			cell.setCellValue(new HSSFRichTextString(String.valueOf(table
					.getValueAt(j - currentRow, k - startCell))));
		}
	}

	/**
	 * Finner kolonnenummer basert på kolonnebokstav.
	 * 
	 * @param colLetter
	 * @return kolonnenummer basert på kolonnebokstav
	 * @throws ProTransException
	 */
	@SuppressWarnings("unused")
	private short getColNumber(final String colLetter) throws ProTransException {
		if (colLetter.equalsIgnoreCase("A")) {
			return 0;
		} else if (colLetter.equalsIgnoreCase("B")) {
			return 1;
		} else if (colLetter.equalsIgnoreCase("C")) {
			return 2;
		} else if (colLetter.equalsIgnoreCase("D")) {
			return 3;
		} else if (colLetter.equalsIgnoreCase("E")) {
			return 4;
		} else if (colLetter.equalsIgnoreCase("F")) {
			return 5;
		} else if (colLetter.equalsIgnoreCase("G")) {
			return 6;
		} else if (colLetter.equalsIgnoreCase("H")) {
			return 7;
		} else if (colLetter.equalsIgnoreCase("I")) {
			return 8;
		} else if (colLetter.equalsIgnoreCase("J")) {
			return 9;
		} else if (colLetter.equalsIgnoreCase("K")) {
			return 10;
		} else if (colLetter.equalsIgnoreCase("L")) {
			return 11;
		} else if (colLetter.equalsIgnoreCase("M")) {
			return 12;
		} else if (colLetter.equalsIgnoreCase("N")) {
			return 13;
		} else if (colLetter.equalsIgnoreCase("O")) {
			return 14;
		} else if (colLetter.equalsIgnoreCase("P")) {
			return 15;
		} else if (colLetter.equalsIgnoreCase("Q")) {
			return 16;
		} else if (colLetter.equalsIgnoreCase("R")) {
			return 17;
		} else if (colLetter.equalsIgnoreCase("S")) {
			return 18;
		} else if (colLetter.equalsIgnoreCase("T")) {
			return 19;
		} else if (colLetter.equalsIgnoreCase("U")) {
			return 20;
		} else {
			throw new ProTransException("Kolonne ikke definert");
		}

	}

	/**
	 * Finner kolonnebokstav basert på kolonnenummer.
	 * 
	 * @param col
	 * @return kolonnebokstav basert på kolonnenummer
	 */
	@SuppressWarnings("unused")
	static String getColLetter(final int col) {
		switch (col) {
		case 0:
			return "A";
		case 1:
			return "B";
		case 2:
			return "C";
		case 3:
			return "D";
		case 4:
			return "E";
		case 5:
			return "F";
		case 6:
			return "G";
		case 7:
			return "H";
		case 8:
			return "I";
		case 9:
			return "J";
		case 10:
			return "K";
		case 11:
			return "L";
		case 12:
			return "M";
		case 13:
			return "N";
		case 14:
			return "O";
		case 15:
			return "P";
		default:
			throw new IllegalStateException("Kolonne ikke definert");
		}
	}

	private class ExcelGenerator implements Threadable {
		private WindowInterface window;

		private ExcelReportSetting excelReportSetting;

		private String excelPath;

		private boolean disposeWindow = true;

		private Dimension orgDimension;

		public ExcelGenerator(final WindowInterface aWindow,
				final ExcelReportSetting setting, final boolean windowDispose,
				final Dimension windowDimension) {
			window = aWindow;
			excelReportSetting = setting;
			excelPath = ApplicationParamUtil.findParamByName("excel_path");
			disposeWindow = windowDispose;
			orgDimension = windowDimension;
		}

		public final void doWhenFinished(final Object object) {
			if (object != null) {
				Util.showErrorDialog(window, "Feil", object.toString());
			} else {
				Util.showMsgDialog(window.getComponent(), "Excelfil generert",
						"Dersom excel ikke starter automatisk ligger excelfil i katalog "
								+ excelPath);
			}
			if (disposeWindow) {
				window.dispose();
			} else {
				window.setSize(orgDimension);
			}

		}

		public final Object doWork(final Object[] params, final JLabel labelInfo) {
			String errorMsg = null;
			if (labelInfo != null) {
				labelInfo.setText("Genererer excelfil...");
			}
			try {
				generateExcel();
			} catch (Exception e) {
				e.printStackTrace();
				errorMsg = e.getMessage();
			}
			return errorMsg;
		}

		public void generateExcel() throws ProTransException {
			ExcelReportEnum excelReportEnum = excelReportSetting
					.getExcelReportType();
			ExcelReportGenerator excelDataGenerator = excelReportEnum
					.getExcelReportGenerator();

			// dersom rapport generers med egen metode
			if (excelReportEnum.getGenerateMethodName() != null) {
				runGenerateMethod(excelReportSetting, excelDataGenerator
						.getReportDataMap(excelReportSetting));

			} else {
				generateExcelForReport(excelReportEnum, excelDataGenerator);
			}
		}

		private void generateExcelForReport(
				final ExcelReportEnum excelReportEnum,
				final ExcelReportGenerator excelDataGenerator)
				throws ProTransException {
			Map<ExcelReportEnum, ExcelReportData> reports = new LinkedHashMap<ExcelReportEnum, ExcelReportData>();
			JXTable table = excelDataGenerator
					.getReportData(excelReportSetting);
			String infoTop = excelDataGenerator.getInfoTop(excelReportSetting);
			String infoButtom = excelDataGenerator
					.getInfoButtom(excelReportSetting);
			reports.put(excelReportEnum, new ExcelReportData(table, infoTop,
					infoButtom));

			List<ExcelReportEnum> subReports = excelReportEnum.getSubReports();

			if (subReports != null) {
				generateExcelForeachReport(reports, subReports);
			}
			excelReportSetting.setExcelReportType(excelReportEnum);

			showDataInExcelSub(excelPath, excelReportEnum.getExcelFileName()
					+ excelReportSetting.getPeriodString() + ".xls", null,
					excelReportSetting.getReportName(), reports,
					excelReportEnum.getHeadFontSize(), useUniqueFileName);
		}

		private void generateExcelForeachReport(
				final Map<ExcelReportEnum, ExcelReportData> reports,
				final List<ExcelReportEnum> subReports)
				throws ProTransException {
			ExcelReportGenerator excelDataGenerator;
			JXTable table;
			String infoTop;
			String infoButtom;
			for (ExcelReportEnum report : subReports) {
				excelDataGenerator = report.getExcelReportGenerator();
				excelReportSetting.setExcelReportType(report);
				table = excelDataGenerator.getReportData(excelReportSetting);
				infoTop = excelDataGenerator.getInfoTop(excelReportSetting);
				infoButtom = excelDataGenerator
						.getInfoButtom(excelReportSetting);
				reports.put(report, new ExcelReportData(table, infoTop,
						infoButtom));
			}
		}

		public final void enableComponents(final boolean enable) {
		}

	}

	final void runGenerateMethod(final ExcelReportSetting excelReportSetting,
			final Map<Object, Object> data) throws ProTransException {
		Method method;
		try {
			method = this.getClass().getMethod(
					excelReportSetting.getExcelReportType()
							.getGenerateMethodName(),
					new Class[] { ExcelReportSetting.class, Map.class });
			method.invoke(this, new Object[] { excelReportSetting, data });
		} catch (Exception e) {
			throw new ProTransException(e);
		}
	}

	private static class ExcelGroupSum {
		private short sumColumn;

		private HSSFCell resultCell;

		private short fromRow;

		private short toRow;

		ExcelGroupSum(final short aFromRow, final HSSFCell aResultCell,
				final short aSumColumn, final short colorIndex,
				final HSSFCellStyle style) {
			fromRow = aFromRow;
			resultCell = aResultCell;

			style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);

			style.setFillBackgroundColor(colorIndex);
			style.setFillForegroundColor(colorIndex);
			style.setFillPattern((short) 1);

			style.setBorderBottom((short) 1);
			style.setBorderLeft((short) 1);
			style.setBorderRight((short) 1);
			style.setBorderTop((short) 1);

			resultCell.setCellStyle(style);

			sumColumn = aSumColumn;
		}

		public final void setToRow(final short aToRow) {
			toRow = aToRow;
		}

		public short getFromRow() {
			return fromRow;
		}

		public short getToRow() {
			return toRow;
		}

		public void setFormula() {
			StringBuilder stringBuilder = new StringBuilder("SUM(").append(
					getColLetter(sumColumn)).append(fromRow).append(":")
					.append(getColLetter(sumColumn)).append(toRow).append(")");

			resultCell.setCellFormula(stringBuilder.toString());

		}
	}

	private class ExcelReportData {
		private JXTable table;

		private String infoTop;

		private String infoButtom;

		public ExcelReportData(final JXTable aTable, final String aInfoTop,
				final String aInfoButtom) {
			table = aTable;
			infoTop = aInfoTop;
			infoButtom = aInfoButtom;
		}

		public JXTable getTable() {
			return table;
		}

		public String getInfoButtom() {
			return infoButtom;
		}

		public String getInfoTop() {
			return infoTop;
		}
	}

	private int createEmptyRows(final HSSFSheet sheet, final int currentRow,
			final int numberOfRows) {
		for (int i = 0; i < numberOfRows; i++) {
			sheet.createRow((short) currentRow + i);
		}
		return currentRow + numberOfRows;
	}

	@SuppressWarnings("unchecked")
	private int generateDeviationSummarySub(final HSSFWorkbook wb,
			final HSSFSheet sheet, final int currentRowNumber,
			final Map<Object, Object> data, final String dataKey,
			final String countHeading, final String sumHeading,
			final CellStyle cellStyle) {
		int currentRow = currentRowNumber;
		/**
		 * **********************************PERIODE****************************
		 * ***********
		 */
		// henter ut data for periode
		List<SumAvvikV> deviationList = (List<SumAvvikV>) data.get(dataKey);

		// Skriver ut overskrifter for periode
		Integer totalCount = Integer.valueOf(0);
		BigDecimal totalCost = BigDecimal.valueOf(0);
		Map<Integer, SumAvvikV> columnMap = new Hashtable<Integer, SumAvvikV>();

		HSSFRow row;

		currentRow = createEmptyRows(sheet, currentRow, 2);

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 1, "Totalt");

		int column = 1;

		if (deviationList != null) {
			for (SumAvvikV deviation : deviationList) {
				column++;
				createCell(row, cellStyle.getStyle10Bold(), (short) column,
						deviation.getJobFunctionName());

				columnMap.put(column, deviation);

				sheet.setColumnWidth((short) column, (short) 3000);

				totalCount = totalCount + deviation.getDeviationCount();
				totalCost = totalCost.add(deviation.getInternalCost());
			}
		}

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 0, countHeading);
		createCell(row, cellStyle.getStyle10(), (short) 1, String
				.valueOf(totalCount));

		SumAvvikV avvik;
		for (int i = 2; i <= column; i++) {
			avvik = columnMap.get(i);
			createCell(row, cellStyle.getStyle10(), (short) i, String
					.valueOf(avvik.getDeviationCount()));
		}

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 0, sumHeading);
		createCell(row, cellStyle.getStyle10(), (short) 1, String
				.valueOf(totalCost.doubleValue()));

		for (int i = 2; i <= column; i++) {
			avvik = columnMap.get(i);
			createCell(row, cellStyle.getStyle10(), (short) i, String
					.valueOf(avvik.getInternalCost().doubleValue()));
		}
		return currentRow;

	}

	@SuppressWarnings("unchecked")
	private int generateDeviationSummaryPreventiveAction(final HSSFWorkbook wb,
			final HSSFSheet sheet, final int currentRowNumber,
			final Map<Object, Object> data, final String dataKey,
			final String heading, final CellStyle cellStyle) {
		int currentRow = currentRowNumber;

		HSSFRow row;

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 0, heading);

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 0, "Navn");
		createCell(row, cellStyle.getStyle10Bold(), (short) 1, "Funksjon");
		createCell(row, cellStyle.getStyle10Bold(), (short) 2,
				"Knyttet til avvik");
		createCell(row, cellStyle.getStyle10Bold(), (short) 4, "Prosjektleder");

		List<PreventiveAction> preventiveActionList = (List<PreventiveAction>) data
				.get(dataKey);

		row = sheet.createRow((short) currentRow++);

		for (PreventiveAction preventiveAction : preventiveActionList) {
			createCell(row, cellStyle.getStyle10Bold(), (short) 0,
					preventiveAction.getPreventiveActionName());
			if (preventiveAction.getJobFunction() != null) {
				createCell(row, cellStyle.getStyle10Bold(), (short) 1,
						preventiveAction.getJobFunction().getJobFunctionName());
			}
			if (preventiveAction.getFunctionCategory() != null) {
				createCell(row, cellStyle.getStyle10Bold(), (short) 2,
						preventiveAction.getFunctionCategory()
								.getFunctionCategoryName());
			}
			createCell(row, cellStyle.getStyle10Bold(), (short) 4,
					preventiveAction.getManager());

			row = sheet.createRow((short) currentRow++);
		}
		return currentRow;
	}

	@SuppressWarnings("unchecked")
	private int generateDeviationOverview(final HSSFWorkbook wb,
			final HSSFSheet sheet, final int currentRowNumber,
			final Map<Object, Object> data, final CellStyle cellStyle) {
		HSSFRow row;

		int currentRow = currentRowNumber;

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 3, "Avviksoversikt");

		currentRow = createEmptyRows(sheet, currentRow, 1);
		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10Bold(), (short) 3,
				"Totalt per måned antall åpne (lukkede) fordelt per funksjon");

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10BoldWithBorders(), (short) 1, "Nye");
		createCell(row, cellStyle.getStyle10BoldWithBorders(), (short) 2,
				"Totalt");

		// Henter ut alle avvik for alle måneder
		Map<MonthEnum, Map<String, SumAvvikV>> deviationList = (Map<MonthEnum, Map<String, SumAvvikV>>) data
				.get("DeviationList");

		// Henter alle avvik for alle funksjoner
		Collection<Map<String, SumAvvikV>> allMonths = deviationList.values();

		Map<Integer, String> jobFunctionNameColumns = new Hashtable<Integer, String>();
		Set<String> jobFunctionNames = new TreeSet<String>();

		// Legger til alle funksjoner som har avvik totalt for å få alle
		// overskrifter
		for (Map<String, SumAvvikV> monthList : allMonths) {
			jobFunctionNames.addAll(monthList.keySet());
		}
		int column = 2;
		createJobFunctionNameCells(cellStyle, row, jobFunctionNameColumns,
				jobFunctionNames, column);

		List<MonthEnum> months = MonthEnum.getMonthList();
		Map<String, SumAvvikV> monthDeviations;
		Set<Integer> columns;
		// Antall nye avvik for gjeldende måned
		SumAvvikV newDeviations = null;
		SumAvvikV totalDeviations = new SumAvvikV();

		// Total for alle fiunksjoner
		Map<String, SumAvvikV> totalDeviationMap = new Hashtable<String, SumAvvikV>();

		// Går gjennom alle måneder og skriver ut antall avvik, åpne og lukkede
		for (MonthEnum month : months) {
			row = sheet.createRow((short) currentRow++);
			createCell(row, cellStyle.getStyle10BoldWithBorders(), (short) 0,
					month.getMonthString());

			// Henter ut avvik for gjeldende måned
			monthDeviations = deviationList.get(month);
			if (monthDeviations != null) {
				columns = jobFunctionNameColumns.keySet();
				newDeviations = new SumAvvikV();

				createDeviationCellForFunction(cellStyle, row,
						jobFunctionNameColumns, monthDeviations, columns,
						newDeviations, totalDeviations, totalDeviationMap);
				createCell(row, cellStyle.getStyle10BoldWithBorders(),
						(short) 1, newDeviations.getDeviationCount() + " ("
								+ newDeviations.getClosedCount() + ")");
				newDeviations = null;
				if (totalDeviations != null) {
					createCell(row, cellStyle.getStyle10BoldWithBorders(),
							(short) 2, totalDeviations.getDeviationCount()
									+ " (" + totalDeviations.getClosedCount()
									+ ")");
				}
			}

		}
		return currentRow;
	}

	private void createDeviationCellForFunction(final CellStyle cellStyle,
			final HSSFRow row,
			final Map<Integer, String> jobFunctionNameColumns,
			final Map<String, SumAvvikV> monthDeviations,
			final Set<Integer> columns, final SumAvvikV newDeviations,
			final SumAvvikV totalDeviations,
			final Map<String, SumAvvikV> totalDeviationMap) {
		// Går gjennom alle funksjoner
		for (Integer nameColumn : columns) {
			createDeviationCellForFunction(cellStyle, row,
					jobFunctionNameColumns, monthDeviations, newDeviations,
					totalDeviations, totalDeviationMap, nameColumn);
		}
	}

	private void createDeviationCellForFunction(final CellStyle cellStyle,
			final HSSFRow row,
			final Map<Integer, String> jobFunctionNameColumns,
			final Map<String, SumAvvikV> monthDeviations,
			final SumAvvikV newDeviations, final SumAvvikV totalDeviations,
			final Map<String, SumAvvikV> totalDeviationMap,
			final Integer nameColumn) {
		SumAvvikV deviation;
		deviation = monthDeviations.get(jobFunctionNameColumns.get(nameColumn));

		// Dersom måned har avvik for gjeldende funskjon skrives
		// denne ut
		if (deviation != null) {

			// Legger til nye avvik for måned
			newDeviations.addDeviationCount(deviation.getDeviationCount());
			newDeviations.addClosedCount(deviation.getClosedCount());

			createCellForDeviation(cellStyle, row, jobFunctionNameColumns,
					totalDeviations, totalDeviationMap, nameColumn, deviation);

		}
	}

	private void createCellForDeviation(final CellStyle cellStyle,
			final HSSFRow row,
			final Map<Integer, String> jobFunctionNameColumns,
			final SumAvvikV totalDeviations,
			final Map<String, SumAvvikV> totalDeviationMap,
			final Integer nameColumn, final SumAvvikV deviation) {
		SumAvvikV totalDeviation;
		if (totalDeviations != null) {
			// Legger til total
			totalDeviations.addDeviationCount(deviation.getDeviationCount());
			totalDeviations.addClosedCount(deviation.getClosedCount());

			totalDeviation = totalDeviationMap.get(jobFunctionNameColumns
					.get(nameColumn));
			totalDeviation = getTotalDeviation(jobFunctionNameColumns,
					totalDeviationMap, nameColumn, deviation, totalDeviation);
			createCell(row, cellStyle.getStyle10BoldWithBorders(), nameColumn
					.shortValue(), totalDeviation.getDeviationCount() + " ("
					+ totalDeviation.getClosedCount() + ")");
		}
	}

	private SumAvvikV getTotalDeviation(
			final Map<Integer, String> jobFunctionNameColumns,
			final Map<String, SumAvvikV> totalDeviationMap,
			final Integer nameColumn, final SumAvvikV deviation,
			final SumAvvikV currentTotalDeviation) {
		SumAvvikV totalDeviation = currentTotalDeviation;
		if (totalDeviation != null) {
			totalDeviation.addDeviationCount(deviation.getDeviationCount());
			totalDeviation.addClosedCount(deviation.getClosedCount());
		} else {
			totalDeviation = new SumAvvikV();
			totalDeviation.setSumAvvikVPK(new SumAvvikVPK(deviation
					.getProductArea(), deviation.getJobFunctionName(), null,
					null, null));
			totalDeviation.setDeviationCount(deviation.getDeviationCount());
			totalDeviation.setClosedCount(deviation.getClosedCount());
			totalDeviationMap.put(jobFunctionNameColumns.get(nameColumn),
					totalDeviation);
		}
		return totalDeviation;
	}

	private void createJobFunctionNameCells(final CellStyle cellStyle,
			final HSSFRow row,
			final Map<Integer, String> jobFunctionNameColumns,
			final Set<String> jobFunctionNames, final int currentColumn) {
		int column = currentColumn;
		// Går gjennom alle funskjoner og skriver ut overskrifter
		for (String jobFunctionName : jobFunctionNames) {
			column++;
			createCell(row, cellStyle.getStyle10BoldWithBorders(),
					(short) column, jobFunctionName);
			jobFunctionNameColumns.put(column, jobFunctionName);
		}
	}

	public static void checkFileFormat(final ExcelUtil excelUtil,
			final String fileFormat) throws ProTransException {
		String header = excelUtil.getHeaderLine();
		if (!header.equalsIgnoreCase(fileFormat)) {
			throw new ProTransException("Filformatet " + header
					+ " stemmer ikke med \n" + fileFormat);
		}
	}

	public void generateJiggReport(final ExcelReportSetting reportSetting,
			final Map<Object, Object> data) throws ProTransException {
		String excelPath = getExcelPath();
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = getSheet(wb, 1, new int[] { 4000 });

		int currentRow = 0;
		HSSFRow row;

		CellStyle cellStyle = new CellStyle(wb);

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle18Bold(), (short) 2, "Jiggrapport");

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle10Bold(), (short) 0, "Periode");
		createCell(row, cellStyle.getStyle10Bold(), (short) 1, (reportSetting)
				.getPeriodString());

		Map<String, Map<String, Set<JiggReportData>>> reportData = (Map<String, Map<String, Set<JiggReportData>>>) data
				.get("Rapport");

		Set<String> jigger = reportData.keySet();
		List<String> jiggListe = jigger != null ? new ArrayList<String>(jigger)
				: new ArrayList<String>();
		Collections.sort(jiggListe);

		row = sheet.createRow((short) currentRow++);
		BigDecimal sumOrdreTotal = BigDecimal.ZERO;
		BigDecimal sumInternTotal = BigDecimal.ZERO;
		for (String jigg : jiggListe) {
			row = sheet.createRow((short) currentRow++);
			row = sheet.createRow((short) currentRow++);
			Map<String, Set<JiggReportData>> jiggData = reportData.get(jigg);
			Set<JiggReportData> ordre = jiggData.get("Ordre");
			ordre = ordre != null ? ordre : new HashSet<JiggReportData>();

			Set<JiggReportData> internOrdre = jiggData.get("Intern");
			internOrdre = internOrdre != null ? internOrdre
					: new HashSet<JiggReportData>();
			createCell(row, cellStyle.getStyle10BoldWithTopBorderAndLeftTick(),
					(short) 0, jigg);
			createCell(row, cellStyle.getStyle10BoldWithTopBorderTick(),
					(short) 1, "");
			createCell(row, cellStyle.getStyle10BoldWithTopBorderTick(),
					(short) 2, "");
			createCell(row, cellStyle.getStyle10BoldWithTopBorderTick(),
					(short) 3, "");
			createCell(row, cellStyle.getStyle10BoldWithTopBorderTick(),
					(short) 4, "");
			createCell(row,
					cellStyle.getStyle10BoldWithTopBorderAndRightTick(),
					(short) 5, "");
			row = sheet.createRow((short) currentRow++);
			createCell(row, cellStyle.getStyle10BoldWithBorderLeftTick(),
					(short) 0, "Ordre");
			createCell(row, cellStyle.getStyle10Bold(), (short) 1, "Artikkel");
			createCell(row, cellStyle.getStyle10Bold(), (short) 2, "Start");
			createCell(row, cellStyle.getStyle10Bold(), (short) 3, "Ferdig");
			createCell(row, cellStyle.getStyle10Bold(), (short) 4, "Verdi");
			createCell(row, cellStyle.getStyle10BoldWithBorderRightTick(),
					(short) 5, "Pris");
			row = sheet.createRow((short) currentRow++);
			BigDecimal sumOrdre = BigDecimal.ZERO;
			for (JiggReportData jiggReportData : ordre) {
				createCell(row, cellStyle.getStyle10WithBorderLeftTick(),
						(short) 0, jiggReportData.getOrderInfo());
				createCell(row, cellStyle.getStyle10(), (short) 1,
						jiggReportData.getArticleName());
				createCell(row, cellStyle.getStyle10(), (short) 2, Util
						.formatDate(jiggReportData.getStartDate(), dateFormat));
				createCell(row, cellStyle.getStyle10(), (short) 3, Util
						.formatDate(jiggReportData.getProduced(), dateFormat));
				createCell(row, cellStyle.getStyle10(), (short) 4,
						jiggReportData.getOwnProductionString());
				createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
						null, HSSFCellStyle.BORDER_THICK, null), (short) 5,
						Util.convertBigDecimalToString(jiggReportData
								.getPrice()));
				row = sheet.createRow((short) currentRow++);
				sumOrdre = sumOrdre
						.add(jiggReportData.getOwnProduction() != null ? jiggReportData
								.getOwnProduction()
								: BigDecimal.ZERO);
			}
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(),
					HSSFCellStyle.BORDER_THICK, null, null,
					HSSFCellStyle.BORDER_THIN), (short) 0, "Sum ordre " + jigg);
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					null, null, HSSFCellStyle.BORDER_THIN), (short) 1, "");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					null, null, HSSFCellStyle.BORDER_THIN), (short) 2, "");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					null, null, HSSFCellStyle.BORDER_THIN), (short) 3, "");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, HSSFCellStyle.BORDER_THIN), (short) 4, Util
					.convertBigDecimalToString(sumOrdre));
			createCell(row, cellStyle
					.getStyle(cellStyle.getFont10(), null, null,
							HSSFCellStyle.BORDER_THICK,
							HSSFCellStyle.BORDER_THIN), (short) 5, "");
			BigDecimal sumIntern = BigDecimal.ZERO;

			row = sheet.createRow((short) currentRow++);
			createCell(row, cellStyle.getStyle10WithBorderLeftTick(),
					(short) 0, "");
			createCell(row, cellStyle.getStyle10BoldWithBorderRightTick(),
					(short) 5, "");
			row = sheet.createRow((short) currentRow++);

			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
					HSSFCellStyle.BORDER_THICK, HSSFCellStyle.BORDER_THIN,
					null, null), (short) 0, "Internordre");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					HSSFCellStyle.BORDER_THIN, null, null), (short) 1,
					"Artikkel");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					HSSFCellStyle.BORDER_THIN, null, null), (short) 2, "Start");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					HSSFCellStyle.BORDER_THIN, null, null), (short) 3, "Ferdig");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					HSSFCellStyle.BORDER_THIN, null, null), (short) 4, "Verdi");
			createCell(row, cellStyle
					.getStyle(cellStyle.getFont10Bold(), null,
							HSSFCellStyle.BORDER_THIN,
							HSSFCellStyle.BORDER_THICK, null), (short) 5,
					"Pris");
			row = sheet.createRow((short) currentRow++);

			for (JiggReportData jiggReportData : internOrdre) {
				createCell(row, cellStyle.getStyle10WithBorderLeftTick(),
						(short) 0, jiggReportData.getOrderInfo());
				createCell(row, cellStyle.getStyle10(), (short) 1,
						jiggReportData.getArticleName());
				createCell(row, cellStyle.getStyle10(), (short) 2, Util
						.formatDate(jiggReportData.getStartDate(), dateFormat));
				createCell(row, cellStyle.getStyle10(), (short) 3, Util
						.formatDate(jiggReportData.getProduced(), dateFormat));
				createCell(row, cellStyle.getStyle10(), (short) 4,
						jiggReportData.getOwnInternalProductionString());
				createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
						null, HSSFCellStyle.BORDER_THICK, null), (short) 5,
						Util.convertBigDecimalToString(jiggReportData
								.getPrice()));
				row = sheet.createRow((short) currentRow++);
				sumIntern = sumIntern.add(jiggReportData
						.getOwnInternalProduction() != null ? jiggReportData
						.getOwnInternalProduction() : BigDecimal.ZERO);
			}
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(),
					HSSFCellStyle.BORDER_THICK, null, null,
					HSSFCellStyle.BORDER_THIN), (short) 0, "Sum intern " + jigg);
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					null, null, HSSFCellStyle.BORDER_THIN), (short) 1, "");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					null, null, HSSFCellStyle.BORDER_THIN), (short) 2, "");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					null, null, HSSFCellStyle.BORDER_THIN), (short) 3, "");

			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, HSSFCellStyle.BORDER_THIN), (short) 4, Util
					.convertBigDecimalToString(sumIntern));
			createCell(row, cellStyle
					.getStyle(cellStyle.getFont10(), null, null,
							HSSFCellStyle.BORDER_THICK,
							HSSFCellStyle.BORDER_THIN), (short) 5, "");
			row = sheet.createRow((short) currentRow++);
			createCell(row, cellStyle.getStyle10WithBorderLeftTick(),
					(short) 0, "");
			createCell(row, cellStyle.getStyle10BoldWithBorderRightTick(),
					(short) 5, "");
			row = sheet.createRow((short) currentRow++);
			createCell(row, cellStyle
					.getStyle10BoldWithBottomBorderAndLeftTick(), (short) 0,
					"Sum " + jigg);
			createCell(row, cellStyle.getStyle10BoldWithBottomBorderTick(),
					(short) 1, "");
			createCell(row, cellStyle.getStyle10BoldWithBottomBorderTick(),
					(short) 2, "");
			createCell(row, cellStyle.getStyle10BoldWithBottomBorderTick(),
					(short) 3, "");
			createCell(row, cellStyle.getStyle10BoldWithBottomBorderTick(),
					(short) 4, Util.convertBigDecimalToString(sumOrdre
							.add(sumIntern)));
			createCell(row, cellStyle
					.getStyle10BoldWithBottomBorderAndRigthTick(), (short) 5,
					"");

			sumOrdreTotal = sumOrdreTotal.add(sumOrdre);
			sumInternTotal = sumInternTotal.add(sumIntern);
		}

		row = sheet.createRow((short) currentRow++);
		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle10BoldWithTopBorderAndLeftTick(),
				(short) 0, "Sum ordre");
		createCell(row, cellStyle.getStyle10BoldWithTopBorderTick(), (short) 1,
				Util.convertBigDecimalToString(sumOrdreTotal));
		createCell(row, cellStyle.getStyle10BoldWithTopBorderTick(), (short) 2,
				"");
		createCell(row, cellStyle.getStyle10BoldWithTopBorderTick(), (short) 3,
				"");
		createCell(row, cellStyle.getStyle10BoldWithTopBorderTick(), (short) 4,
				"");
		createCell(row, cellStyle.getStyle10BoldWithTopBorderAndRightTick(),
				(short) 5, "");
		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle10BoldWithBorderLeftTick(),
				(short) 0, "Sum intern");
		createCell(row, cellStyle.getStyle10Bold(), (short) 1, Util
				.convertBigDecimalToString(sumInternTotal));
		createCell(row, cellStyle.getStyle10BoldWithBorderRightTick(),
				(short) 5, "");
		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
				HSSFCellStyle.BORDER_THICK, HSSFCellStyle.BORDER_THIN, null,
				HSSFCellStyle.BORDER_THICK), (short) 0, "Sum total");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, null, HSSFCellStyle.BORDER_THICK),
				(short) 1, Util.convertBigDecimalToString(sumOrdreTotal
						.add(sumInternTotal)));
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, null, HSSFCellStyle.BORDER_THICK),
				(short) 2, "");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, null, HSSFCellStyle.BORDER_THICK),
				(short) 3, "");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, null, HSSFCellStyle.BORDER_THICK),
				(short) 4, "");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THICK,
				HSSFCellStyle.BORDER_THICK), (short) 5, "");

		openExcelFile(reportSetting.getExcelReportType().getExcelFileName()
				+ ".xls", excelPath, wb, true);

	}

	public void generateOrdrereserveTakstolReport(
			final ExcelReportSetting reportSetting,
			final Map<Object, Object> data) throws ProTransException {
		String excelPath = getExcelPath();
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = getSheet(wb, 1, new int[] { 4000 });

		int currentRow = 0;
		HSSFRow row;

		CellStyle cellStyle = new CellStyle(wb);

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle18Bold(), (short) 2,
				"Ordrereserve takstol");

		row = sheet.createRow((short) currentRow++);
		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
				HSSFCellStyle.BORDER_THIN, null, null,
				HSSFCellStyle.BORDER_THIN), (short) 1, "Ekstern");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
				HSSFCellStyle.BORDER_THIN, null, null,
				HSSFCellStyle.BORDER_THIN), (short) 3, "Intern");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
				HSSFCellStyle.BORDER_THIN, null, null,
				HSSFCellStyle.BORDER_THIN), (short) 5, "");

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THICK), (short) 0, "Ordrereserve");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
				HSSFCellStyle.BORDER_THIN, null, null,
				HSSFCellStyle.BORDER_THICK), (short) 1, "Antall");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, null, HSSFCellStyle.BORDER_THICK),
				(short) 2, "Sum");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
				HSSFCellStyle.BORDER_THIN, null, null,
				HSSFCellStyle.BORDER_THICK), (short) 3, "Antall");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, null, HSSFCellStyle.BORDER_THICK),
				(short) 4, "Sum");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
				HSSFCellStyle.BORDER_THIN, null, null,
				HSSFCellStyle.BORDER_THICK), (short) 5, "Totalt");

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, HSSFCellStyle.BORDER_THIN, null), (short) 0,
				"Ikke prosjektert");

		Map<String, AntallSum> reportSum = (Map<String, AntallSum>) data
				.get("Sum");
		AntallSum antallSum = reportSum.get("IkkeProsjektertEkstern");

		createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null, null,
				null, null), (short) 1, null, antallSum != null ? Double
				.valueOf(antallSum.getNumberOf()) : 0);
		createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null, null,
				HSSFCellStyle.BORDER_THIN, null), (short) 2, null,
				antallSum != null ? Double.valueOf(antallSum.getSum()
						.doubleValue()) : 0);

		antallSum = reportSum.get("IkkeProsjektertIntern");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null, null,
				null, null), (short) 3, null, antallSum != null ? Double
				.valueOf(antallSum.getNumberOf()) : 0);
		createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null, null,
				HSSFCellStyle.BORDER_THIN, null), (short) 4, null,
				antallSum != null ? Double.valueOf(antallSum.getSum()
						.doubleValue()) : 0);

		createFormulaCell(row, (short) (5), "C5+E5", null);

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, HSSFCellStyle.BORDER_THIN, null), (short) 0,
				"Prosjektert");

		antallSum = reportSum.get("ProsjektertEkstern");

		createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null, null,
				null, null), (short) 1, null, antallSum != null ? Double
				.valueOf(antallSum.getNumberOf()) : 0);
		createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null, null,
				HSSFCellStyle.BORDER_THIN, null), (short) 2, null,
				antallSum != null ? Double.valueOf(antallSum.getSum()
						.doubleValue()) : 0);

		antallSum = reportSum.get("ProsjektertIntern");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null, null,
				null, null), (short) 3, null, antallSum != null ? Double
				.valueOf(antallSum.getNumberOf()) : 0);
		createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null, null,
				HSSFCellStyle.BORDER_THIN, null), (short) 4, null,
				antallSum != null ? Double.valueOf(antallSum.getSum()
						.doubleValue()) : 0);

		createFormulaCell(row, (short) (5), "C6+E6", null);

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THICK), (short) 0, "Totalt");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, null, HSSFCellStyle.BORDER_THICK),
				(short) 1, "");
		createFormulaCell(row, (short) (2), "C5+C6", cellStyle.getStyle(
				cellStyle.getFont10(), null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THICK));
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				HSSFCellStyle.BORDER_THIN, null, HSSFCellStyle.BORDER_THICK),
				(short) 3, "");
		createFormulaCell(row, (short) (4), "E5+E6", cellStyle.getStyle(
				cellStyle.getFont10(), null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THICK));
		createFormulaCell(row, (short) (5), "F5+F6", cellStyle.getStyle(
				cellStyle.getFont10(), null, HSSFCellStyle.BORDER_THIN, null,
				HSSFCellStyle.BORDER_THICK));

		row = sheet.createRow((short) currentRow++);
		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, null), (short) 0, "Grunnlag");

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, HSSFCellStyle.BORDER_THICK), (short) 0, "Type");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, HSSFCellStyle.BORDER_THICK), (short) 1, "Avdeling");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, HSSFCellStyle.BORDER_THICK), (short) 2, "Kundenr");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, HSSFCellStyle.BORDER_THICK), (short) 3, "Navn");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, HSSFCellStyle.BORDER_THICK), (short) 4, "Ordrenr");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, HSSFCellStyle.BORDER_THICK), (short) 5,
				"Egenproduksjon");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, HSSFCellStyle.BORDER_THICK), (short) 6, "Frakt");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, HSSFCellStyle.BORDER_THICK), (short) 7, "Prod.dato");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, HSSFCellStyle.BORDER_THICK), (short) 8, "Transport");

		List<OrdreReserveTakstol> reportBasis = (List<OrdreReserveTakstol>) data
				.get("Basis");

		row = sheet.createRow((short) currentRow++);

		for (OrdreReserveTakstol ordre : reportBasis) {
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, null), (short) 0, ordre.getType());
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, null), (short) 1, ordre.getProductAreaGroup());
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, null), (short) 2, ordre.getCustomerNr());
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, null), (short) 3, ordre.getCustomerName());
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, null), (short) 4, ordre.getOrderNr());
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, null), (short) 5, null, ordre
					.getOwnProduction() != null ? ordre.getOwnProduction()
					.doubleValue() : 0);
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, null), (short) 6, null,
					ordre.getDeliveryCost() != null ? ordre.getDeliveryCost()
							.doubleValue() : 0);
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, null), (short) 7,
					ordre.getProductionDate() != null ? Util.formatDate(ordre
							.getProductionDate(), Util.SHORT_DATE_FORMAT) : "");
			createCell(row, cellStyle.getStyle(cellStyle.getFont10(), null,
					null, null, null), (short) 8,
					ordre.getTransportWeek() != null ? ""
							+ ordre.getTransportYear() + "/"
							+ ordre.getTransportWeek() : "");
			row = sheet.createRow((short) currentRow++);
		}

		openExcelFile(reportSetting.getExcelReportType().getExcelFileName()
				+ ".xls", excelPath, wb, true);
	}

	private void createFormulaCell(HSSFRow row, short col, String formula,
			final HSSFCellStyle cellStyle) {
		HSSFCell cell = row.createCell(col);
		cell.setCellFormula(formula);
		if (cellStyle != null) {
			cell.setCellStyle(cellStyle);
		}
	}

	public void generateDeviationSumJobFunction(
			final ExcelReportSetting reportSetting,
			final Map<Object, Object> data) throws ProTransException {
		String excelPath = getExcelPath();
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = getSheet(wb, 10, new int[] { 5000, 1000, 5000, 5000,
				5000, 5000, 5000, 5000, 5000, 5000 });

		int currentRow = 0;
		HSSFRow row;

		CellStyle cellStyle = new CellStyle(wb);

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle18Bold(), (short) 2,
				"Rapport pr funksjonsområde - "
						+ ((ExcelReportSettingDeviation) reportSetting)
								.getProductAreaGroup()
								.getProductAreaGroupName());

		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle(cellStyle.getFont12Bold(), null,
				null, null, null), (short) 0, "År:");
		createCell(row, cellStyle.getStyle(cellStyle.getFont12Bold(), null,
				null, null, null), (short) 1, String
				.valueOf(((ExcelReportSettingDeviation) reportSetting)
						.getYear()));
		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle(cellStyle.getFont12Bold(), null,
				null, null, null), (short) 0, "Aviksfunksjon:");
		createCell(row, cellStyle.getStyle(cellStyle.getFont12Bold(), null,
				null, null, null), (short) 1,
				((ExcelReportSettingDeviation) reportSetting)
						.getDeviationFunction().getJobFunctionName());

		row = sheet.createRow((short) currentRow++);
		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
				HSSFCellStyle.BORDER_THIN, null, null, null), (short) 2,
				"Kategori");
		row = sheet.createRow((short) currentRow++);
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, null), (short) 0, "Måned");
		createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
				null, null, null), (short) 1, "Uke");
		Set<String> functionCategories = (Set<String>) data
				.get("FunctionCategory");
		int column = 2;
		for (String functionCategory : functionCategories) {
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
					HSSFCellStyle.BORDER_THIN, null, null,
					HSSFCellStyle.BORDER_THIN), (short) column,
					functionCategory);
			column++;
		}
		row = sheet.createRow((short) currentRow++);

		Map<String, DeviationSumJobFunctionV> reportData = (Map<String, DeviationSumJobFunctionV>) data
				.get("ReportData");
		String lastMonth = "";
		boolean writeMonth = false;
		for (int i = 1; i < 54; i++) {// går gjennom alle uker i et år
			String month = Util.getMonthByWeek(i);
			writeMonth = false;
			if (!month.equalsIgnoreCase(lastMonth)) {
				lastMonth = month;
				writeMonth = true;
				createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(),
						null, HSSFCellStyle.BORDER_THIN, null, null),
						(short) 0, month);
			}
			createCell(row, cellStyle.getStyle(cellStyle.getFont10Bold(), null,
					writeMonth ? HSSFCellStyle.BORDER_THIN : null, null, null),
					(short) 1, String.valueOf(i));

			int countColumn = 2;
			for (String functionCategory : functionCategories) {
				DeviationSumJobFunctionV deviationSum = reportData.get(""
						+ ((ExcelReportSettingDeviation) reportSetting)
								.getYear() + "_" + i + "_" + functionCategory);
				if (deviationSum != null) {
					createCell(row, cellStyle.getStyle(cellStyle.getFont10(),
							HSSFCellStyle.BORDER_THIN,
							writeMonth ? HSSFCellStyle.BORDER_THIN : null,
							null, null), (short) countColumn, null, Double
							.valueOf(deviationSum.getCountDeviations()));
				} else {
					createCell(row, cellStyle.getStyle(cellStyle.getFont10(),
							HSSFCellStyle.BORDER_THIN,
							writeMonth ? HSSFCellStyle.BORDER_THIN : null,
							null, null), (short) countColumn, null, Double
							.valueOf(0));
				}
				countColumn++;
			}

			row = sheet.createRow((short) currentRow++);
		}

		openExcelFile(reportSetting.getExcelReportType().getExcelFileName()
				+ ".xls", excelPath, wb, true);
	}

	private String getExcelPath() throws ProTransException {
		String excelPath = ApplicationParamUtil.findParamByName("excel_path");
		if (excelPath == null || excelPath.length() == 0) {
			throw new ProTransException("Katalog ikke satt");
		}
		return excelPath;
	}

	public void generateSalesGoalReport(ExcelReportSetting reportSetting,
			Map<Object, Object> data) throws ProTransException {
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = getSheet(wb, 21, new int[] { 5000, 6000, 5000, 5000,
				5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
				5000, 5000, 5000, 5000, 5000, 5000, 5000 }

		);

		int currentRow = 0;
		HSSFRow row;

		CellStyle cellStyle = new CellStyle(wb);

		currentRow = createEmptyRows(sheet, currentRow, 2);
		row = sheet.createRow((short) currentRow++);

		currentRow = createHeadings(reportSetting, sheet, currentRow, row,
				cellStyle);

		Collection<SalesmanGoal> dataList = (Collection<SalesmanGoal>) data
				.get("Reportdata");
		String currentProductAreaName = "";
		for (SalesmanGoal goal : dataList) {
			row = sheet.createRow((short) currentRow++);
			String productAreaName = goal.getProductArea().getProductArea();
			if (!currentProductAreaName.equalsIgnoreCase(productAreaName)) {
				currentProductAreaName = productAreaName;
			} else {
				productAreaName = "";
			}
			createDataRow(row, cellStyle, goal, productAreaName);
		}

		openExcelFile(reportSetting.getExcelReportType().getExcelFileName()
				+ ".xls", getExcelPath(), wb, useUniqueFileName);

	}

	private void createDataRow(HSSFRow row, CellStyle cellStyle,
			SalesmanGoal goal, String productAreaName) {
		String numberFormat = "# ##0;[Red]-# ##0";
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 0, productAreaName);
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 1, goal.getSalesman());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 2, null, goal
				.getBudgetValue().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 3, null, goal
				.getBudgetValueOffer().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 4, null, goal
				.getBudgetOrderProcent().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 5, null, goal
				.getProcentOrder().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 6, null, goal
				.getDG().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 7, null, goal
				.getOfferSumOwnProduction().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 8, null, goal
				.getOrderSumOwnProduction().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 9, null, goal
				.getConfirmedOrderSumOwnProduction().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 10, null,
				goal.getProcentOrderLastYearDiff().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 11, null,
				goal.getOfferSumOwnProductionLastYearDiff().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 12, null,
				goal.getOrderSumOwnProductionLastYearDiff().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 13, null,
				goal.getProcentOrderAccumulated().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 14, null,
				goal.getDGAccumulated().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 15, null,
				goal.getOfferSumOwnProductionAccumulated().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 16, null,
				goal.getOrderSumOwnProductionAccumulated().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 17, null,
				goal.getConfirmedOrderSumOwnProductionAccumulated()
						.doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 18, null,
				goal.getProcentOrderAccumulatedLastYearDiff().doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 19, null,
				goal.getOfferSumOwnProductionAccumulatedLastYearDiff()
						.doubleValue());
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_RIGHT,
				HSSFColor.WHITE.index, null, null, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, numberFormat), (short) 20, null,
				goal.getOrderSumOwnProductionAccumulatedLastYearDiff()
						.doubleValue());
	}

	private int createHeadings(ExcelReportSetting reportSetting,
			HSSFSheet sheet, int currentRow, HSSFRow row, CellStyle cellStyle) {
		for (int i = 0; i <= 20; i++) {
			createCell(row, cellStyle.getStyle((short) 16,
					HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
					HSSFColor.SEA_GREEN.index, HSSFCellStyle.BORDER_MEDIUM,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_MEDIUM,
					HSSFCellStyle.BORDER_MEDIUM, null), (short) i, "");
		}

		createCell(row, cellStyle.getStyle((short) 16,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				HSSFColor.SEA_GREEN.index, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_MEDIUM, null), (short) 0, "SALGSMÅL");

		sheet.addMergedRegion(new Region((short) currentRow - 1, (short) 0,
				(short) currentRow - 1, (short) 20));

		row = sheet.createRow((short) currentRow++);

		for (int i = 0; i <= 20; i++) {
			createCell(row, cellStyle.getStyle((short) 10,
					HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
					HSSFColor.GREY_25_PERCENT.index,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_MEDIUM,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_THIN,
					null), (short) i, "");
		}

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				HSSFColor.GREY_25_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 2, "Salgsmål "
				+ reportSetting.getYear());

		sheet.addMergedRegion(new Region((short) currentRow - 1, (short) 2,
				(short) currentRow - 1, (short) 4));

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				HSSFColor.GREY_25_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 5, "Uke "
				+ reportSetting.getWeekFrom());

		sheet.addMergedRegion(new Region((short) currentRow - 1, (short) 5,
				(short) currentRow - 1, (short) 12));

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				HSSFColor.GREY_25_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 13,
				"Akkumulert pr uke " + reportSetting.getWeekFrom());

		sheet.addMergedRegion(new Region((short) currentRow - 1, (short) 13,
				(short) currentRow - 1, (short) 20));

		row = sheet.createRow((short) currentRow++);

		for (int i = 0; i <= 1; i++) {
			createCell(row, cellStyle.getStyle((short) 10,
					HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
					HSSFColor.GREY_25_PERCENT.index,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_MEDIUM, HSSFCellStyle.BORDER_THIN,
					null), (short) i, "");
		}

		for (int i = 2; i <= 4; i++) {
			createCell(row, cellStyle.getStyle((short) 10,
					HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
					HSSFColor.GREY_25_PERCENT.index, null,
					HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_THIN,
					HSSFCellStyle.BORDER_THIN, null), (short) i, "");
		}

		for (int i = 5; i <= 20; i++) {
			createCell(row, cellStyle.getStyle((short) 10,
					HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
					HSSFColor.GREY_25_PERCENT.index, null,
					HSSFCellStyle.BORDER_THIN, null, HSSFCellStyle.BORDER_THIN,
					null), (short) i, "");
		}

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				HSSFColor.GREY_25_PERCENT.index, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 20, "");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				HSSFColor.GREY_25_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 5, "pr uke");

		sheet.addMergedRegion(new Region((short) currentRow - 1, (short) 5,
				(short) currentRow - 1, (short) 9));

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				HSSFColor.GREY_25_PERCENT.index, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 10,
				"Endring fra i fjor(diff):");

		sheet.addMergedRegion(new Region((short) currentRow - 1, (short) 10,
				(short) currentRow - 1, (short) 12));

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				HSSFColor.GREY_25_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 13, "pr uke");

		sheet.addMergedRegion(new Region((short) currentRow - 1, (short) 13,
				(short) currentRow - 1, (short) 17));

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_CENTER,
				HSSFColor.GREY_25_PERCENT.index, HSSFCellStyle.BORDER_THIN,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM,
				HSSFCellStyle.BORDER_THIN, null), (short) 18,
				"Endring fra i fjor(diff):");

		sheet.addMergedRegion(new Region((short) currentRow - 1, (short) 18,
				(short) currentRow - 1, (short) 20));

		row = sheet.createRow((short) currentRow++);

		createCell(row,
				cellStyle.getStyle((short) 10, HSSFFont.BOLDWEIGHT_NORMAL,
						HSSFCellStyle.ALIGN_LEFT,
						HSSFColor.GREY_25_PERCENT.index, null, null,
						HSSFCellStyle.BORDER_MEDIUM,
						HSSFCellStyle.BORDER_MEDIUM, null), (short) 0,
				"Produktområde");

		createCell(row,
				cellStyle.getStyle((short) 10, HSSFFont.BOLDWEIGHT_NORMAL,
						HSSFCellStyle.ALIGN_LEFT,
						HSSFColor.GREY_25_PERCENT.index, null, null,
						HSSFCellStyle.BORDER_MEDIUM,
						HSSFCellStyle.BORDER_MEDIUM, null), (short) 1, "Selger");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 2, "Ordre");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 3, "Tilbudsmengde");

		createCell(row,
				cellStyle.getStyle((short) 10, HSSFFont.BOLDWEIGHT_NORMAL,
						HSSFCellStyle.ALIGN_LEFT,
						HSSFColor.GREY_25_PERCENT.index, null, null,
						HSSFCellStyle.BORDER_MEDIUM,
						HSSFCellStyle.BORDER_MEDIUM, null), (short) 4,
				"Tilslagsprosent");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 5, "Tilslagsprosent");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 6, "DG");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 7, "Tilbudsmengde");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 8, "Ordremengde");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 9, "Avrop");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 10, "Tilslagsprosent");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 11, "Tilbudsmengde");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 12, "Ordremengde");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, HSSFCellStyle.BORDER_MEDIUM,
				null, HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM,
				null), (short) 13, "Tilslagsprosent");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 14, "DG");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 15, "Tilbudsmengde");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 16, "Ordremengde");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 17, "Avrop");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 18, "Tilslagsprosent");
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
				HSSFColor.GREY_25_PERCENT.index, null, null,
				HSSFCellStyle.BORDER_THIN, HSSFCellStyle.BORDER_MEDIUM, null),
				(short) 19, "Tilbudsmengde");
		createCell(row,
				cellStyle.getStyle((short) 10, HSSFFont.BOLDWEIGHT_NORMAL,
						HSSFCellStyle.ALIGN_LEFT,
						HSSFColor.GREY_25_PERCENT.index, null, null,
						HSSFCellStyle.BORDER_MEDIUM,
						HSSFCellStyle.BORDER_MEDIUM, null), (short) 20,
				"Ordremengde");
		return currentRow;
	}

	public void generateTakstoltegnerReport(ExcelReportSetting reportSetting,
			Map<Object, Object> data) throws ProTransException {

		ImmutableMap<String, Integer> productAreaColumns = new ImmutableMap.Builder<String, Integer>()
				.put("Takstol", 1).put("Garasje", 3).put("Byggelement", 5)
				.build();
		HSSFWorkbook wb = new HSSFWorkbook();

		HSSFSheet sheet = getSheet(wb, 21, new int[] { 5000, 6000, 5000, 5000,
				5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000, 5000,
				5000, 5000, 5000, 5000, 5000, 5000, 5000 }

		);

		int currentRow = 0;
		HSSFRow row;

		CellStyle cellStyle = new CellStyle(wb);

		// currentRow = createEmptyRows(sheet, currentRow, 2);
		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle((short) 12,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, null, null), (short) 0, "Takstoltegning uke "
				+ reportSetting.getWeekFrom() + " - "
				+ reportSetting.getWeekTo() + " " + reportSetting.getYear());

		currentRow = createEmptyRows(sheet, currentRow, 2);

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, null, null), (short) 1, "Takstol");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, null, null), (short) 3, "Garasje");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, null, null), (short) 5, "Byggelement");

		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null), (short) 0,
				"Tegner");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null), (short) 1,
				"Antall");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null), (short) 2,
				"Sum");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null), (short) 3,
				"Antall");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null), (short) 4,
				"Sum");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null), (short) 5,
				"Antall");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null), (short) 6,
				"Sum");

		Map<TakstoltegnerVSum, Collection<TakstoltegnerV>> dataList = (Map<TakstoltegnerVSum, Collection<TakstoltegnerV>>) data
				.get("Reportdata");
		currentRow = createSumLines(productAreaColumns, sheet, currentRow,
				cellStyle, dataList);
		currentRow = createBasisHeading(sheet, currentRow, cellStyle);
		row = sheet.createRow((short) currentRow++);
		int column = 0;
		for (Collection<TakstoltegnerV> tegnerListe : dataList.values()) {
			for (TakstoltegnerV tegner : tegnerListe) {
				column = 0;
				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, tegner.getTrossDrawer());
				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, tegner.getOrderNr());
				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, tegner.getCustomerName());

				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, null, tegner.getCustomerNr()
								.doubleValue());

				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, tegner.getPostalCode());

				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, null, tegner.getCostAmount()
								.doubleValue());

				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, Util.SHORT_DATE_FORMAT.format(tegner
								.getTrossReady()));

				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, null,
						tegner.getTakProsjektering() != null ? tegner
								.getTakProsjektering().doubleValue() : 0);

				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, null,
						tegner.getMaxTrossHeight() != null ? tegner
								.getMaxTrossHeight().doubleValue() : 0);

				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, null, tegner.getProbability()
								.doubleValue());

				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, tegner.getProductAreaGroupName());

				createCell(
						row,
						cellStyle.getStyle((short) 10,
								HSSFFont.BOLDWEIGHT_NORMAL,
								HSSFCellStyle.ALIGN_LEFT, (short) -1, null,
								null, null, null, null),
						(short) column++,
						tegner.getPacklistReady() != null ? Util.SHORT_DATE_FORMAT
								.format(tegner.getPacklistReady())
								: "");

				createCell(row, cellStyle.getStyle((short) 10,
						HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
						(short) -1, null, null, null, null, null),
						(short) column++, tegner.getSalesman());

				row = sheet.createRow((short) currentRow++);

			}
		}

		openExcelFile(reportSetting.getExcelReportType().getExcelFileName()
				+ ".xls", getExcelPath(), wb, useUniqueFileName);
	}

	private int createSumLines(
			ImmutableMap<String, Integer> productAreaColumns, HSSFSheet sheet,
			int currentRow, CellStyle cellStyle,
			Map<TakstoltegnerVSum, Collection<TakstoltegnerV>> dataList) {
		Map<String, HSSFRow> tegnerlinjeMap = Maps.newHashMap();
		for (TakstoltegnerVSum tegner : dataList.keySet()) {
			HSSFRow tegnerlinje = tegnerlinjeMap.get(tegner.getTrossDrawer());
			tegnerlinje = tegnerlinje != null ? tegnerlinje : sheet
					.createRow((short) currentRow++);

			createCell(tegnerlinje, cellStyle.getStyle((short) 10,
					HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
					(short) -1, null, null, null, null, null), (short) 0,
					tegner.getTrossDrawer());

			createCell(tegnerlinje, cellStyle.getStyle((short) 10,
					HSSFFont.BOLDWEIGHT_NORMAL, HSSFCellStyle.ALIGN_LEFT,
					(short) -1, null, null, null, null, null),
					(short) productAreaColumns.get(
							tegner.getProductAreaGroupName()).shortValue(),
					null, tegner.getNumberOf().doubleValue());

			createCell(
					tegnerlinje,
					cellStyle.getStyle((short) 10, HSSFFont.BOLDWEIGHT_NORMAL,
							HSSFCellStyle.ALIGN_LEFT, (short) -1, null, null,
							null, null, null),
					(short) (productAreaColumns.get(
							tegner.getProductAreaGroupName()).shortValue() + 1),
					null, tegner.getSumCostAmount().doubleValue());

			tegnerlinjeMap.put(tegner.getTrossDrawer(), tegnerlinje);

		}
		return currentRow;
	}

	private int createBasisHeading(HSSFSheet sheet, int currentRow,
			CellStyle cellStyle) {
		HSSFRow row;
		currentRow = createEmptyRows(sheet, currentRow, 2);
		row = sheet.createRow((short) currentRow++);

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, null, null), (short) 0, "Grunnlag");

		row = sheet.createRow((short) currentRow++);
		int column = 0;
		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Tegner");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Ordrenr");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Kunde");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Kundenr");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Postnr");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "EP-verdi");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Takstoltegning klar");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Timer");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Maxhøyde");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Prosent");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Produktområde");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Pakkliste klar");

		createCell(row, cellStyle.getStyle((short) 10,
				HSSFFont.BOLDWEIGHT_BOLD, HSSFCellStyle.ALIGN_LEFT, (short) -1,
				null, null, null, HSSFCellStyle.BORDER_THIN, null),
				(short) column++, "Selger");

		return currentRow;
	}
}
