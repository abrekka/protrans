package no.ugland.utransprod.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.record.WindowProtectRecord;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.SingleListSelectionAdapter;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler;
import no.ugland.utransprod.gui.handlers.ProductionOverviewViewHandler2.ProductionColumn;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.ProductionApplyList;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.ProductionOverviewV;
import no.ugland.utransprod.model.ProductionTime;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.OrderManager;
import no.ugland.utransprod.service.ProductionTimeManager;
import no.ugland.utransprod.service.enums.LazyLoadOrderEnum;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.InternalFrameBuilder;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelUtil;

public class ProductionTimeView implements Viewer, Closeable {
	private static Logger LOGGER = Logger.getLogger(ProductionTimeView.class);
	private JXTable productionTimeTable;
	private JButton buttonClose;
	private JButton buttonExcel;
	private boolean loaded = false;
	private final ArrayListModel objectList;
	private SelectionInList objectSelectionList;
	private List<ProductionTime> productionoverviewList = Lists.newArrayList();
	private ProductionTimeManager productionTimeManager;
	private OrderManager orderManager;
	private OrderLineManager orderLineManager;
	private TableModel productionTimeTableModel;
	private JPopupMenu popupMenuProduction;
	private JMenuItem menuItemSetStarted;
	private JMenuItem menuItemSetStopped;
	private JMenuItem menuItemSetOvertime;
	private JMenuItem menuItemFilterNotDone;
	private JMenuItem menuItemFilterProductionWeek;
	private JMenuItem menuItemClearFilter;
	private JMenuItem menuItemFilterOrderNr;
	private JMenuItem menuItemFilterName;
	private JMenuItem menuItemFilterProduction;

	public ProductionTimeView() {
		productionTimeManager = (ProductionTimeManager) ModelUtil.getBean(ProductionTimeManager.MANAGER_NAME);
		orderManager = (OrderManager) ModelUtil.getBean(OrderManager.MANAGER_NAME);
		orderLineManager = (OrderLineManager) ModelUtil.getBean(OrderLineManager.MANAGER_NAME);
		objectList = new ArrayListModel();

		objectSelectionList = new SelectionInList((ListModel) objectList);
		setupMenus();
	}

	private void setupMenus() {
		popupMenuProduction = new JPopupMenu("Produksjonstid");
		popupMenuProduction.setName("PopupMenuProductionTime");

		menuItemSetStarted = new JMenuItem("Sett startet...");
		menuItemSetStarted.setName("MenuItemSetStarted");
		popupMenuProduction.add(menuItemSetStarted);

		menuItemSetStopped = new JMenuItem("Sett stoppet...");
		menuItemSetStopped.setName("MenuItemSetStopped");
		popupMenuProduction.add(menuItemSetStopped);

		menuItemSetOvertime = new JMenuItem("Sett overtid...");
		menuItemSetOvertime.setName("MenuItemSetOvertime");
		popupMenuProduction.add(menuItemSetOvertime);

		menuItemFilterOrderNr = new JMenuItem("Filtrer på ordrenr...");
		menuItemFilterOrderNr.setName("menuItemFilterOrderNr");
		popupMenuProduction.add(menuItemFilterOrderNr);

		menuItemFilterName = new JMenuItem("Filtrer på navn...");
		menuItemFilterName.setName("menuItemFilterName");
		popupMenuProduction.add(menuItemFilterName);

		menuItemFilterProduction = new JMenuItem("Filtrer på produksjonstype...");
		menuItemFilterProduction.setName("menuItemFilterProduction");
		popupMenuProduction.add(menuItemFilterProduction);

		menuItemFilterProductionWeek = new JMenuItem("Filtrer på produksjonsuke...");
		menuItemFilterProductionWeek.setName("menuItemFilterProductionWeek");
		popupMenuProduction.add(menuItemFilterProductionWeek);

		menuItemFilterNotDone = new JMenuItem("Filtrer ikke ferdig");
		menuItemFilterNotDone.setName("menuItemFilterNotDone");
		popupMenuProduction.add(menuItemFilterNotDone);

		menuItemClearFilter = new JMenuItem("Fjern filter");
		menuItemClearFilter.setName("menuItemClearFilter");
		popupMenuProduction.add(menuItemClearFilter);

	}

	private class MenuItemListenerSetOvertime implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerSetOvertime(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {

			ProductionTime productionTime = getSelectedProductionTime();
			if (productionTime != null) {
				String overtid = (String) Util.showOptionsDialogCombo(window, Arrays.asList("Ja", "Nei"), "Overtid",
						true, productionTime.getOvertimeBoolean() ? "Ja" : "Nei");

				if (overtid != null) {
					productionTime.setOvertime("Ja".equalsIgnoreCase(overtid) ? 1 : 0);

					productionTimeManager.saveProductionTime(productionTime);

					updateTotalProductionTime(productionTime);
				}
			}
		}

	}

	private class MenuItemListenerFilterProductionWeek implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerFilterProductionWeek(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			final String produksjonsuke = Util.showInputDialogWithdefaultValue(window, "Produksjonsuke", "Uke:", null);
			objectList.clear();
			objectSelectionList.clearSelection();

			productionoverviewList = productionTimeManager.findAll();

			List<ProductionTime> filtered = Lists
					.newArrayList(Iterables.filter(productionoverviewList, new Predicate<ProductionTime>() {

						public boolean apply(ProductionTime input) {
							return Integer.valueOf(produksjonsuke).equals(input.getProductionWeek());
						}
					}));

			if (productionoverviewList != null) {
				objectList.addAll(filtered);
			}

			if (productionTimeTable != null) {
				productionTimeTable.scrollRowToVisible(0);
			}

		}

	}

	private class MenuItemListenerFilterOrderNr implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerFilterOrderNr(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			final String ordrenr = Util.showInputDialogWithdefaultValue(window, "Ordrenr", "Ordrenr:", null);
			objectList.clear();
			objectSelectionList.clearSelection();

			productionoverviewList = productionTimeManager.findAll();

			List<ProductionTime> filtered = Lists
					.newArrayList(Iterables.filter(productionoverviewList, new Predicate<ProductionTime>() {

						public boolean apply(ProductionTime input) {
							return ordrenr.equals(input.getOrderNr());
						}
					}));

			if (productionoverviewList != null) {
				objectList.addAll(filtered);
			}

			if (productionTimeTable != null) {
				productionTimeTable.scrollRowToVisible(0);
			}

		}

	}

	private class MenuItemListenerFilterName implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerFilterName(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			final String navn = Util.showInputDialogWithdefaultValue(window, "Navn", "Navn:", null);
			objectList.clear();
			objectSelectionList.clearSelection();

			productionoverviewList = productionTimeManager.findAll();

			List<ProductionTime> filtered = Lists
					.newArrayList(Iterables.filter(productionoverviewList, new Predicate<ProductionTime>() {

						public boolean apply(ProductionTime input) {
							return navn.equals(input.getName());
						}
					}));

			if (productionoverviewList != null) {
				objectList.addAll(filtered);
			}

			if (productionTimeTable != null) {
				productionTimeTable.scrollRowToVisible(0);
			}

		}

	}

	private class MenuItemListenerFilterProduction implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerFilterProduction(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			final String produksjon = Util.showInputDialogWithdefaultValue(window, "Produksjonstype",
					"Produksjonstype:", null);
			objectList.clear();
			objectSelectionList.clearSelection();

			productionoverviewList = productionTimeManager.findAll();

			List<ProductionTime> filtered = Lists
					.newArrayList(Iterables.filter(productionoverviewList, new Predicate<ProductionTime>() {

						public boolean apply(ProductionTime input) {
							return produksjon.equals(input.getProductionname());
						}
					}));

			if (productionoverviewList != null) {
				objectList.addAll(filtered);
			}

			if (productionTimeTable != null) {
				productionTimeTable.scrollRowToVisible(0);
			}

		}

	}

	private class MenuItemListenerFilterNotDone implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerFilterNotDone(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			objectList.clear();
			objectSelectionList.clearSelection();

			productionoverviewList = productionTimeManager.findAll();

			List<ProductionTime> filtered = Lists
					.newArrayList(Iterables.filter(productionoverviewList, new Predicate<ProductionTime>() {

						public boolean apply(ProductionTime input) {
							return input.getStopped() == null;
						}
					}));

			if (productionoverviewList != null) {
				objectList.addAll(filtered);
			}

			if (productionTimeTable != null) {
				productionTimeTable.scrollRowToVisible(0);
			}

		}

	}

	private class MenuItemListenerClearFilter implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerClearFilter(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {
			objectList.clear();
			objectSelectionList.clearSelection();

			productionoverviewList = productionTimeManager.findAll();

			if (productionoverviewList != null) {
				objectList.addAll(productionoverviewList);
			}

			if (productionTimeTable != null) {
				productionTimeTable.scrollRowToVisible(0);
			}

		}

	}

	private class MenuItemListenerSetStarted implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerSetStarted(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {

			ProductionTime productionTime = getSelectedProductionTime();
			if (productionTime != null) {
				Date nyttTidspunkt = Util.getDate(window, productionTime.getStarted());
				String tidspunkt = Util.showInputDialogWithdefaultValue(window, "Tidspunkt", "Tidspunkt(HH:MM)",
						String.format("%02d:%02d", productionTime.getStarted().getHours(),
								productionTime.getStarted().getMinutes()));

				nyttTidspunkt.setHours(Integer.valueOf(tidspunkt.substring(0, tidspunkt.indexOf(":"))));
				nyttTidspunkt.setMinutes(Integer.valueOf(tidspunkt.substring(tidspunkt.indexOf(":") + 1)));

				if (productionTime.getStopped() != null && nyttTidspunkt.after(productionTime.getStopped())) {
					Util.showErrorDialog(window, "Feil tidspunkt", "Starttidspunkt kan ikke være etter sluttidspunkt");
					return;
				}

				productionTime.setStarted(nyttTidspunkt);
				productionTimeManager.saveProductionTime(productionTime);

				updateTotalProductionTime(productionTime);
			}
		}

	}

	class MenuItemListenerSetStopped implements ActionListener {
		private WindowInterface window;

		public MenuItemListenerSetStopped(WindowInterface aWindow) {
			window = aWindow;
		}

		public void actionPerformed(ActionEvent actionEvent) {

			ProductionTime productionTime = getSelectedProductionTime();
			if (productionTime != null) {
				Date nyttStopptidspunkt = Util.getDate(window, productionTime.getStopped());
				String tidspunkt = Util.showInputDialogWithdefaultValue(window, "Stoppet tidspunkt", "Tidspunkt(HH:MM)",
						String.format("%02d:%02d",
								productionTime.getStopped() == null ? 0 : productionTime.getStopped().getHours(),
								productionTime.getStopped() == null ? 0 : productionTime.getStopped().getMinutes()));

				nyttStopptidspunkt.setHours(Integer.valueOf(tidspunkt.substring(0, tidspunkt.indexOf(":"))));
				nyttStopptidspunkt.setMinutes(Integer.valueOf(tidspunkt.substring(tidspunkt.indexOf(":") + 1)));

				if (nyttStopptidspunkt != null && productionTime.getStarted().after(nyttStopptidspunkt)) {
					Util.showErrorDialog(window, "Feil tidspunkt", "Starttidspunkt kan ikke være etter sluttidspunkt");
					return;
				}

				productionTime.setStopped(nyttStopptidspunkt);
				productionTimeManager.saveProductionTime(productionTime);

				updateTotalProductionTime(productionTime);
			}
		}

	}

	protected void updateTotalProductionTime(ProductionTime productionTime) {
		Order order = orderManager.findByOrderNr(productionTime.getOrderNr());
		orderManager.lazyLoadOrder(order, new LazyLoadOrderEnum[] { LazyLoadOrderEnum.ORDER_LINES });

		List<OrderLine> produksjonslinjer = order.getOrderLineList(Arrays.asList(productionTime.getProductionname()));

		List<ProductionTime> produksjonstider = productionTimeManager.findByOrderNrAndProductionname(order.getOrderNr(),
				productionTime.getProductionname());

		List<ProductionTime> tiderSomIkkeErFerdig = Lists
				.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {

					public boolean apply(ProductionTime input) {
						return input.getStopped() == null;
					}
				}));

		if (!tiderSomIkkeErFerdig.isEmpty()) {
			LOGGER.info("Det finnes ordretider som ikke er ferdig for ordre: " + order.getOrderNr());
			return;
		}
		
		Collections.sort(produksjonstider, new Comparator<ProductionTime>() {

			public int compare(ProductionTime o1, ProductionTime o2) {
				return o2.getStarted().compareTo(o1.getStarted());
			}
		});

		ProductionTime sisteProduksjon = produksjonstider.isEmpty() ? null : produksjonstider.get(0);

		BigDecimal totalTidsforbruk = BigDecimal.ZERO;

		for (ProductionTime currentProductionTime : produksjonstider) {
			totalTidsforbruk = totalTidsforbruk.add(Tidsforbruk.beregnTidsforbruk(currentProductionTime.getStarted(),
					currentProductionTime.getStopped(), currentProductionTime.getOvertimeBoolean()));
		}

		for (OrderLine produksjonslinje : produksjonslinjer) {
			produksjonslinje.setRealProductionHours(totalTidsforbruk);
			produksjonslinje.setProduced(sisteProduksjon.getStopped());
			orderLineManager.saveOrderLine(produksjonslinje);
		}

	}

	public WindowInterface buildWindow() {
		WindowInterface window = InternalFrameBuilder.buildInternalFrame("Produksjonstid", new Dimension(930, 600),
				true);
		window.add(buildPanel(window), BorderLayout.CENTER);

		return window;
	}

	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	public void initWindow() {
		// TODO Auto-generated method stub

	}

	public void cleanUp() {
		// TODO Auto-generated method stub

	}

	public boolean useDispose() {
		// TODO Auto-generated method stub
		return false;
	}

	public Component buildPanel(WindowInterface window) {
		initComponents(window);
		FormLayout layout = new FormLayout(
				"10dlu,p,3dlu,p,3dlu,p,3dlu,p,30dlu,p,3dlu,p,30dlu,p,3dlu,p,30dlu,p,3dlu,p:grow,10dlu",
				"10dlu,p,3dlu,fill:300dlu:grow,3dlu,p");
		PanelBuilder builder = new PanelBuilder(layout);
		// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),
		// layout);
		CellConstraints cc = new CellConstraints();
		JScrollPane scrollPane = new JScrollPane(productionTimeTable);
		scrollPane.setName("ScrollPaneTable");
		// builder.addLabel("Produktområde:", cc.xy(2, 2));
		// builder.add(comboBoxProductAreaGroup, cc.xy(4, 2));
		// builder.add(buttonSearch, cc.xy(6, 2));
//		builder.add(checkBoxFilter, cc.xy(2, 2));
//		builder.add(buttonFilter, cc.xy(4, 2));
//		builder.addLabel("Antall garasjer:", cc.xy(6, 2));
//		builder.add(labelAntallGarasjer, cc.xy(8, 2));
//		builder.addLabel("Sum estimert tid vegg:", cc.xy(10, 2));
//		builder.add(labelSumTidVegg, cc.xy(12, 2));
//		builder.addLabel("Sum estimert tid gavl:", cc.xy(14, 2));
//		builder.add(labelSumTidGavl, cc.xy(16, 2));
//		builder.addLabel("Sum estimert tid pakk:", cc.xy(18, 2));
//		builder.add(labelSumTidPakk, cc.xy(20, 2));
		builder.add(scrollPane, cc.xyw(2, 4, 20));
		builder.add(ButtonBarFactory.buildCenteredBar(buttonExcel, buttonClose), cc.xyw(2, 6, 20));

		return builder.getPanel();
	}

	private void initComponents(WindowInterface window) {
		productionTimeTable = getTable(window);
		buttonClose = getCancelButton(window);
		buttonExcel = getButtonExcel(window);
	}

	public JButton getButtonExcel(WindowInterface window) {
		buttonExcel = new JButton(new ExcelAction(window));
		buttonExcel.setIcon(IconEnum.ICON_EXCEL.getIcon());
		return buttonExcel;
	}

	private class ExcelAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public ExcelAction(WindowInterface aWindow) {
			super("Excel");
			window = aWindow;
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent arg0) {
			Util.setWaitCursor(window.getComponent());
			try {
				String fileName = "Produksjonsoversikt_" + Util.getCurrentDateAsDateTimeString() + ".xlsx";
				String excelDirectory = ApplicationParamUtil.findParamByName("excel_path");

				// JXTable tableReport = new JXTable(new
				// ProductionOverviewTableModel(objectList));

				// ExcelUtil.showDataInExcel(excelDirectory, fileName, null,
				// "Produksjonsoversikt", table, null, null, 16, false);
				ExcelUtil.showTableDataInExcel(excelDirectory, fileName, null, "Produksjonstid", productionTimeTable,
						null, null, 16, false);
			} catch (ProTransException e) {
				e.printStackTrace();
				Util.showErrorDialog(window, "Feil", e.getMessage());
			}
			Util.setDefaultCursor(window.getComponent());

		}
	}

	public JButton getCancelButton(WindowInterface window) {
		if (buttonClose == null) {
			buttonClose = new CancelButton(window, this, true);
			buttonClose.setName("ButtonCancel");
			menuItemSetStarted.addActionListener(new MenuItemListenerSetStarted(window));
			menuItemSetStopped.addActionListener(new MenuItemListenerSetStopped(window));
			menuItemSetOvertime.addActionListener(new MenuItemListenerSetOvertime(window));
			menuItemFilterProductionWeek.addActionListener(new MenuItemListenerFilterProductionWeek(window));
			menuItemFilterNotDone.addActionListener(new MenuItemListenerFilterNotDone(window));
			menuItemClearFilter.addActionListener(new MenuItemListenerClearFilter(window));
			menuItemFilterOrderNr.addActionListener(new MenuItemListenerFilterOrderNr(window));
			menuItemFilterName.addActionListener(new MenuItemListenerFilterName(window));
			menuItemFilterProduction.addActionListener(new MenuItemListenerFilterProduction(window));
		}
		return buttonClose;
	}

	public JXTable getTable(WindowInterface window) {
		if (productionTimeTable == null) {
			initObjects();
			// initOrders(objectList, window);

			ColorHighlighter readyHighlighter = new ColorHighlighter(
					new PatternPredicate("Ja", ProductionColumn.KOMPLETT.ordinal()), ColorEnum.GREEN.getColor(), null);
			ColorHighlighter startedPackingHighlighter = new ColorHighlighter(
					new PatternPredicate("Ja", ProductionColumn.KLAR.ordinal()), ColorEnum.YELLOW.getColor(), null);

			productionTimeTable = new JXTable();
			productionTimeTableModel = new ProductionTimeTableModel(objectList);
			productionTimeTable.setModel(productionTimeTableModel);
			productionTimeTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			productionTimeTable
					.setSelectionModel(new SingleListSelectionAdapter(objectSelectionList.getSelectionIndexHolder()));
			productionTimeTable.setColumnControlVisible(true);
//		productionTimeTable.setColumnControl(new UBColumnControlPopup(productionTimeTable, this));

			productionTimeTable.addMouseListener(new TableClickHandler(window));

//		productionTimeTable.setRowHeight(30);

//		table.addHighlighter(HighlighterFactory.createAlternateStriping());
//		table.addHighlighter(startedPackingHighlighter);
//		table.addHighlighter(readyHighlighter);

			DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
			tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);

			for (ProductionTimeColumn column : ProductionTimeColumn.values()) {
				if (column.hasCenterAlignment()) {
					productionTimeTable.getColumnExt(column.ordinal()).setCellRenderer(tableCellRenderer);
				}
				productionTimeTable.getColumnExt(column.ordinal()).setPreferredWidth(column.getWidth());
			}

			// ordre
			// table.getColumnExt(ProductionColumn.ORDRE.ordinal()).setPreferredWidth(220);
//		productionTimeTable.getColumnModel().getColumn(ProductionColumn.ORDRENR.ordinal())
//				.setCellRenderer(new TextPaneRendererProductionoverviewV());

//		table.getColumnExt(20).setVisible(false);
//		table.getColumnExt(20).setVisible(false);
//		table.getColumnExt(20).setVisible(false);
//		table.getColumnExt(20).setVisible(false);
//		table.getColumnExt(20).setVisible(false);
//		table.getColumnExt(20).setVisible(false);
//		table.getColumnExt(27).setVisible(false);
//		table.getColumnExt(26).setVisible(false);
//		table.getColumnExt(25).setVisible(false);
//
//		table.setName(TableEnum.TABLEPRODUCTIONOVERVIEW.getTableName());

		}
		return productionTimeTable;

	}

	protected void initObjects() {
		if (!loaded) {
			objectList.clear();
			objectSelectionList.clearSelection();

			productionoverviewList = productionTimeManager.findAll();

			if (productionoverviewList != null) {
				objectList.addAll(productionoverviewList);
			}

			if (productionTimeTable != null) {
				productionTimeTable.scrollRowToVisible(0);
			}

		}
	}

	final class TableClickHandler extends MouseAdapter {
		/**
		 * 
		 */
		private WindowInterface window;

		/**
		 * @param aWindow
		 */
		public TableClickHandler(WindowInterface aWindow) {
			window = aWindow;
		}

		/**
		 * @see java.awt.event.MouseAdapter#mouseClicked(java.awt.event.MouseEvent)
		 */
		@SuppressWarnings({ "synthetic-access" })
		@Override
		public void mouseClicked(MouseEvent mouseEvent) {
			Util.setWaitCursor(window.getComponent());
			ProductionTime productionTime = getSelectedProductionTime();
//			if (SwingUtilities.isLeftMouseButton(mouseEvent) && mouseEvent.getClickCount() == 2) {
//				openOrderView(transportable, window);
//
//			} else 

			if (productionTime != null && SwingUtilities.isRightMouseButton(mouseEvent)) {
				setMenuItems(mouseEvent, productionTime);

			}
			Util.setDefaultCursor(window.getComponent());
		}

		private void setMenuItems(MouseEvent mouseEvent, ProductionTime productionTime) {
			int col = productionTimeTable.columnAtPoint(new Point(mouseEvent.getX(), mouseEvent.getY()));

//			removeMenuItems();

//			if (transportable instanceof PostShipment) {
//				popupMenuProduction.add(menuItemShowContent);
//			} else {
//				popupMenuProduction.add(menuItemDeviation);
//			}
//			String columnHeader = StringUtils.upperCase((String) table.getColumnExt(col).getHeaderValue())
//					.replaceAll(" ", "_").replaceAll("\\.", "_").replaceAll("\\%", "PROCENT");
//			;
//			ProductionColumn productionColumn = ProductionColumn.valueOf(columnHeader);
//			boolean success = transportable != null
//					? productionColumn.setMenus(transportable, menuItemMap, window, productionPackageHandlers,
//							popupMenuProduction)
//					: false;
//			if (success) {
			popupMenuProduction.show((JXTable) mouseEvent.getSource(), mouseEvent.getX(), mouseEvent.getY());
//			}
		}

//		private void removeMenuItems() {
//			Collection<JMenuItem> menuItems = menuItemMap.values();
//			for (JMenuItem menuItem : menuItems) {
//				popupMenuProduction.remove(menuItem);
//			}
//
//			popupMenuProduction.remove(menuItemShowContent);
//			popupMenuProduction.remove(menuItemDeviation);
//		}

	}

//	private Transportable getSelectedTransportable() {
//		Transportable transportable = null;
//		if (objectSelectionList.getSelection() != null) {
//			ProductionTime productionTime = getSelectedProductionTime();
//
//			if (productionOverviewV.isPostShipment()) {
//				transportable = managerRepository.getPostShipmentManager()
//						.loadById(productionOverviewV.getPostshipmentId());
//			} else {
//				transportable = managerRepository.getOrderManager().findByOrderNr(productionOverviewV.getOrderNr());
//			}
//		}
//		return transportable;
//	}

	private ProductionTime getSelectedProductionTime() {
		int index = productionTimeTable.convertRowIndexToModel(objectSelectionList.getSelectionIndex());
		if (index != -1) {
			return (ProductionTime) objectSelectionList.getElementAt(index);
		}
		return null;
	}

	public final class ProductionTimeTableModel extends AbstractTableAdapter {

		ProductionTimeTableModel(ListModel listModel) {
			super(listModel, ProductionTimeColumn.getColumnNames());

		}

		public Transportable getTransportable(int rowIndex) {
			return (Transportable) getRow(rowIndex);
		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			ProductionTime productionTime = (ProductionTime) getRow(rowIndex);

//			Map<String, String> statusMap = Util.createStatusMap(transportable.getStatus());
			String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_").replaceAll("\\%", "PROCENT");
			// if (!Hibernate.isInitialized(transportable.getOrderLines()) ||
			// !Hibernate.isInitialized(transportable.getOrder().getOrderCosts()))
			// {
			// if (Order.class.isInstance(transportable)) {
			// ((OrderManager) overviewManager).lazyLoadOrder((Order)
			// transportable, new LazyLoadOrderEnum[] {
			// LazyLoadOrderEnum.ORDER_LINES,
			// LazyLoadOrderEnum.ORDER_COSTS, LazyLoadOrderEnum.PROCENT_DONE });
			// } else {
			// PostShipmentManager postShipmentManager = (PostShipmentManager)
			// ModelUtil.getBean("postShipmentManager");
			// postShipmentManager.lazyLoad((PostShipment) transportable,
			// new LazyLoadPostShipmentEnum[] {
			// LazyLoadPostShipmentEnum.ORDER_LINES });
			// }
			// }
			return ProductionTimeColumn.valueOf(columnName).getValue(productionTime);

		}

		@Override
		public Class<?> getColumnClass(int columnIndex) {
			String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_")
					.replaceAll("\\.", "_").replaceAll("\\%", "PROCENT");
			return ProductionTimeColumn.valueOf(columnName).getColumnClass();
		}

		public Transportable getObjectAtRow(int rowIndex) {
			return (Transportable) getRow(rowIndex);
		}

	}

	public enum ProductionTimeColumn {
		ORDRENR("Ordrenr", false, 80, true) {
			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

			@Override
			public Object getValue(ProductionTime productionTime) {
				return productionTime.getOrderNr();
			}

//			@SuppressWarnings("unchecked")
//			@Override
//			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
//					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
//					JPopupMenu popupMenuProduction) {
//				AbstractProductionPackageViewHandler<Applyable> handler = getHandler(productionPackageHandlers,
//						getColumnName());
//				Applyable applyable = getApplyObject(transportable, handler, window);
//				setMenuItem(transportable, menuItemMap.get(getColumnName()), "Sett pakkliste klar...",
//						"Sett pakkliste ikke klar", handler, popupMenuProduction, applyable);
//				return true;
//			}

//			@Override
//			public Component getFilterComponent(PresentationModel presentationModel) {
//				return BasicComponentFactory
//						.createTextField(presentationModel.getModel(ProductionoverviewFilter.PACKLIST_READY), false);
//			}

//			@Override
//			public boolean filter(ProductionTime productionOverviewV,
//					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
//					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
//				if (StringUtils.isNotBlank(productionoverviewFilter.getPacklistReady())) {
//					return productionOverviewV.getPacklistReady() != null
//							&& simpleDateFormat.format(productionOverviewV.getPacklistReady()).matches(
//									productionoverviewFilter.getPacklistReady().toLowerCase().replaceAll("%", ".*")
//											+ ".*");
//				}
//				return true;
//			}

//			@Override
//			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
//					Map<String, String> statusMap1, Map<String, String> statusMap2,
//					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
//				return productionOverviewV1.getPacklistReady() == null
//						&& productionOverviewV2.getPacklistReady() == null
//								? 0
//								: productionOverviewV1.getPacklistReady() == null ? -1
//										: productionOverviewV2.getPacklistReady() == null ? 1
//												: productionOverviewV1.getPacklistReady()
//														.compareTo(productionOverviewV2.getPacklistReady());
//			}
		},
		PRODUKSJONSTYPE("Produksjonstype", false, 110, true) {
			@Override
			public Object getValue(ProductionTime productionTime) {
				return productionTime.getProductionname();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}

//			@Override
//			public boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
//					WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
//					JPopupMenu popupMenuProduction) {
//				return true;
//			}

//			@Override
//			public Component getFilterComponent(PresentationModel presentationModel) {
//				return BasicComponentFactory
//						.createTextField(presentationModel.getModel(ProductionoverviewFilter.PACKLIST_DONE_BY), false);
//			}

//			@Override
//			public boolean filter(ProductionOverviewV productionOverviewV,
//					ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
//					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
//				if (StringUtils.isNotBlank(productionoverviewFilter.getPacklistDoneBy())) {
//					return productionOverviewV.getPacklistDoneBy() != null
//							&& productionOverviewV.getPacklistDoneBy().toLowerCase().matches(
//									productionoverviewFilter.getPacklistDoneBy().toLowerCase().replaceAll("%", ".*")
//											+ ".*");
//				}
//				return true;
//			}

//			@Override
//			public int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
//					Map<String, String> statusMap1, Map<String, String> statusMap2,
//					Map<String, StatusCheckerInterface<Transportable>> statusCheckers) {
//				return productionOverviewV1.getPacklistDoneBy() == null
//						&& productionOverviewV2.getPacklistDoneBy() == null
//								? 0
//								: productionOverviewV1.getPacklistDoneBy() == null ? -1
//										: productionOverviewV2.getPacklistDoneBy() == null ? 1
//												: productionOverviewV1.getPacklistDoneBy()
//														.compareTo(productionOverviewV2.getPacklistDoneBy());
//			}
		},
		NAVN("Navn", false, 110, true) {
			@Override
			public Object getValue(ProductionTime productionTime) {
				return productionTime.getName();
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
//		OPPRETTET("Opprettet", false, 110, true) {
//			@Override
//			public Object getValue(ProductionTime productionTime) {
//				return productionTime.getCreated();
//			}
//
//			@Override
//			public Class<?> getColumnClass() {
//				return String.class;
//			}
//		},
//		OPPDATERT("Oppdatert", false, 110, true) {
//			@Override
//			public Object getValue(ProductionTime productionTime) {
//				return productionTime.getUpdated();
//			}
//
//			@Override
//			public Class<?> getColumnClass() {
//				return String.class;
//			}
//		},
		STARTET("Startet", false, 110, true) {
			@Override
			public Object getValue(ProductionTime productionTime) {
				return Util.SHORT_DATE_TIME_FORMAT.format(productionTime.getStarted());
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		STOPPET("Stoppet", false, 110, true) {
			@Override
			public Object getValue(ProductionTime productionTime) {
				return productionTime.getStopped() == null ? null
						: Util.SHORT_DATE_TIME_FORMAT.format(productionTime.getStopped());
			}

			@Override
			public Class<?> getColumnClass() {
				return String.class;
			}
		},
		OVERTID("Overtid", false, 110, true) {
			@Override
			public Object getValue(ProductionTime productionTime) {
				return productionTime.getOvertime() != null && productionTime.getOvertime().equals(Integer.valueOf(1));
			}

			@Override
			public Class<?> getColumnClass() {
				return Boolean.class;
			}
		},
		PRODUKSJONSUKE("Produksjonsuke", true, 110, true) {
			@Override
			public Object getValue(ProductionTime productionTime) {
				return productionTime.getProductionWeek();
			}

			@Override
			public Class<?> getColumnClass() {
				return Integer.class;
			}
		}

		;

		private String columnName;
		private boolean centerAlignment;
		private int width;
		private boolean visible;

		private ProductionTimeColumn(String aColumnName, boolean centerAlignment, int width, boolean visible) {
			columnName = aColumnName;
			this.centerAlignment = centerAlignment;
			this.width = width;
			this.visible = visible;
		}

		public String getColumnName() {
			return columnName;
		}

		@Override
		public String toString() {
			return columnName;
		}

//		private static void setMenuItem(final Transportable transportable, final JMenuItem menuItem,
//				final String applyString, final String unapplyString,
//				final AbstractProductionPackageViewHandler<Applyable> handler, JPopupMenu popupMenuProduction,
//				final Applyable applyable) {
//			if (applyable != null) {
//				if ((transportable instanceof PostShipment && applyable.isForPostShipment())
//						|| transportable instanceof Order) {
//					if (handler.getButtonApplyEnabled(applyable)) {
//						menuItem.setText(applyString);
//					} else {
//						menuItem.setText(unapplyString);
//					}
//					popupMenuProduction.add(menuItem);
//				}
//
//			}
//		}

		private static Applyable getApplyObject(final Transportable transportable,
				final AbstractProductionPackageViewHandler<Applyable> handler, final WindowInterface window) {
			return handler != null ? handler.getApplyObject(transportable, window) : null;

		}

		@SuppressWarnings("unchecked")
		private static AbstractProductionPackageViewHandler<Applyable> getHandler(
				final Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
				final String menuItemName) {
			return productionPackageHandlers.get(menuItemName);
		}

		public static String[] getColumnNames() {
			ProductionTimeColumn[] columns = ProductionTimeColumn.values();

			List<String> columnNameList = new ArrayList<String>();
			for (int i = 0; i < columns.length; i++) {
				columnNameList.add(columns[i].getColumnName());
			}
			String[] columnNames = new String[columnNameList.size()];
			return columnNameList.toArray(columnNames);
		}

		public abstract Object getValue(ProductionTime productionTime);

		public abstract Class<?> getColumnClass();

//		@SuppressWarnings("unchecked")
//		public abstract boolean setMenus(Transportable transportable, Map<String, JMenuItem> menuItemMap,
//				WindowInterface window, Map<String, AbstractProductionPackageViewHandler> productionPackageHandlers,
//				JPopupMenu popupMenuProduction);

		public boolean hasCenterAlignment() {
			return centerAlignment;
		}

		public int getWidth() {
			return width;
		}

		public static List<ProductionTimeColumn> getVisibleColumns() {
			List<ProductionTimeColumn> columns = Lists.newArrayList();
			for (ProductionTimeColumn column : ProductionTimeColumn.values()) {
				if (column.isVisible()) {
					columns.add(column);
				}
			}
			return columns;
		}

		private boolean isVisible() {
			return visible;
		}

//		public abstract Component getFilterComponent(PresentationModel presentationModel);

//		public abstract boolean filter(ProductionTime productionTime,
//				ProductionoverviewFilter productionoverviewFilter, Map<String, String> statusMap,
//				Map<String, StatusCheckerInterface<Transportable>> statusCheckers);

//		public abstract int sort(ProductionOverviewV productionOverviewV1, ProductionOverviewV productionOverviewV2,
//				Map<String, String> statusMap1, Map<String, String> statusMap2,
//				Map<String, StatusCheckerInterface<Transportable>> statusCheckers);
	}

	public boolean canClose(String actionString, WindowInterface window) {
		return true;
	}
}
