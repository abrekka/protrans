package no.ugland.utransprod.gui.handlers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.swing.ListModel;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.adapter.AbstractTableAdapter;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.handlers.VeggProductionViewHandler.VeggProductionTableModel;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.ProductionTime;
import no.ugland.utransprod.model.VeggProductionV;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.ProductionTimeManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;

public class VeggProductionViewHandler extends ProductionViewHandler {
	private ProductionTimeManager productionTimeManager;
	public VeggProductionViewHandler(ApplyListInterface<Produceable> productionInterface, String title, Login login,
			ArticleType articleType, ManagerRepository managerRepository,
			DeviationViewHandlerFactory deviationViewHandlerFactory,
			SetProductionUnitActionFactory aSetProductionUnitActionFactory) {
		super(productionInterface, title, login, (String) null, "produksjon", TableEnum.TABLEPRODUCTIONVEGG,
				articleType, managerRepository, deviationViewHandlerFactory, aSetProductionUnitActionFactory);
		productionTimeManager = (ProductionTimeManager) ModelUtil
				.getBean(ProductionTimeManager.MANAGER_NAME);
	}

	protected Integer getApplyColumn() {
		return 4;
	}

	protected TableModel getTableModel(WindowInterface window) {
		return new VeggProductionTableModel(this.getObjectSelectionList(), window);
	}

	protected void initColumnWidthExt() {
		this.table.getColumnExt(this.table.getModel().getColumnName(0)).setPreferredWidth(100);

		this.table.getColumnExt(this.table.getModel().getColumnName(1)).setPreferredWidth(200);

		DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
		tableCellRenderer.setHorizontalAlignment(0);
		this.table.getColumnExt(this.table.getModel().getColumnName(2)).setCellRenderer(tableCellRenderer);
		this.table.getColumnExt(this.table.getModel().getColumnName(2)).setPreferredWidth(70);

		this.table.getColumnExt(this.table.getModel().getColumnName(3)).setPreferredWidth(70);

		this.table.getColumnExt(this.table.getModel().getColumnName(4)).setPreferredWidth(100);

		this.table.getColumnExt(this.table.getModel().getColumnName(7)).setPreferredWidth(100);

		tableCellRenderer.setHorizontalAlignment(0);
		this.table.getColumnExt(this.table.getModel().getColumnName(8)).setCellRenderer(tableCellRenderer);
		this.table.getColumnExt(this.table.getModel().getColumnName(8)).setPreferredWidth(120);
	}

	protected int getProductAreaColumn() {
		return 5;
	}

	protected Integer getStartColumn() {
		return 7;
	}

	/**
	 * Tabellmodell for veggproduksjon
	 * 
	 * @author atle.brekka
	 * 
	 */
	final class VeggProductionTableModel extends AbstractTableAdapter {
		private static final long serialVersionUID = 1L;

		private StatusCheckerInterface<Transportable> frontChecker;

		private WindowInterface window;

		public VeggProductionTableModel(ListModel listModel, WindowInterface aWindow) {
			super(listModel, new String[] { "Transport", "Ordre", "Prod. uke",
					// "Antall", "Spesifikasjon",
					// "Front",
					"Opplasting", "Produsert", "Produktområde", "Prod.enhet", "Startet", "Reell tidsforbruk",
					"Gjort av" });
			frontChecker = Util.getFrontChecker();
			window = aWindow;
			// initStatus(listModel);
		}

		private void initStatus(ListModel list) {
			if (list != null) {
				Map<String, String> statusMap;
				String status;
				int rowCount = getRowCount();

				VeggProductionV prod;
				for (int i = 0; i < rowCount; i++) {
					prod = (VeggProductionV) getRow(i);
					statusMap = Util.createStatusMap(prod.getOrderStatus());
					status = statusMap.get(frontChecker.getArticleName());

					if (status == null) {
						Order order = managerRepository.getOrderManager().findByOrderNr(prod.getOrderNr());
						if (order != null) {
							managerRepository.getOrderManager().lazyLoad(order, new LazyLoadEnum[][] {
									{ LazyLoadEnum.ORDER_LINES, LazyLoadEnum.ORDER_LINE_ATTRIBUTES } });
							status = frontChecker.getArticleStatus(order);
							statusMap.put(frontChecker.getArticleName(), status);
							order.setStatus(Util.statusMapToString(statusMap));
							try {
								managerRepository.getOrderManager().saveOrder(order);
							} catch (ProTransException e) {
								Util.showErrorDialog(window, "Feil", e.getMessage());
								e.printStackTrace();
							}
							applyListInterface.refresh(prod);
						}
					}
				}
			}

		}

		public Object getValueAt(int rowIndex, int columnIndex) {
			VeggProductionV veggProductionV = (VeggProductionV) getRow(rowIndex);
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalSeparatorAlwaysShown(false);
			decimalFormat.setParseIntegerOnly(true);
			// Map<String, String> statusMap =
			// Util.createStatusMap(veggProductionV.getOrderStatus());

			switch (columnIndex) {
			case 0:
				return veggProductionV.getTransportDetails();
			case 1:
				return veggProductionV;
			case 2:

				return veggProductionV.getProductionWeek();
			// case 3:
			// if (veggProductionV.getNumberOfItems() != null) {
			// return
			// decimalFormat.format(veggProductionV.getNumberOfItems());
			// }
			// return "";

			// case 4:
			// if (veggProductionV.getOrdln() != null) {
			// return veggProductionV.getOrdln().getDescription();
			// }
			// return
			// Util.removeNoAttributes(veggProductionV.getAttributeInfo());
			// case 3:
			// return statusMap.get(frontChecker.getArticleName());
			case 3:
				Date loadingDate = veggProductionV.getLoadingDate();
				if (loadingDate != null) {
					return Util.SHORT_DATE_FORMAT.format(loadingDate);
				}
				return null;

			case 4:
				if (veggProductionV.getProduced() != null) {
					return Util.SHORT_DATE_TIME_FORMAT.format(veggProductionV.getProduced());
				}
				return "---";
			case 5:
				if (veggProductionV.getProductAreaGroupName() != null) {
					return veggProductionV.getProductAreaGroupName();
				}
				return "";
			case 6:
				return veggProductionV.getProductionUnitName();
			case 7:
//				List<ProductionTime> produksjonstider = productionTimeManager.findByOrderNrAndProductionname(veggProductionV.getOrderNr(),"Vegg");
//				
//				List<ProductionTime> tiderForBruker = Lists.newArrayList(Iterables.filter(produksjonstider, new Predicate<ProductionTime>() {
//
//					public boolean apply(ProductionTime input) {
//						return input.getUsername().equalsIgnoreCase(login.getApplicationUser().getUserName());
//					}
//				}));
//				
//				if(!tiderForBruker.isEmpty()) {
//					return Util.SHORT_DATE_TIME_FORMAT.format(tiderForBruker.get(0).getStarted());
//				}
				
				if (veggProductionV.getActionStarted() != null) {
					return Util.SHORT_DATE_TIME_FORMAT.format(veggProductionV.getActionStarted());
				}
				return "---";
			case 8:
				return veggProductionV.getRealProductionHours();
//						veggProductionV.getRealProductionHours() == null
//						? Tidsforbruk.beregnTidsforbruk(veggProductionV.getActionStarted(),
//								veggProductionV.getProduced())
//						: veggProductionV.getRealProductionHours();
			case 9:
				return veggProductionV.getDoneBy();
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {

			switch (columnIndex) {
			case 0:

				// case 3:
			case 3:
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
			case 11:
				return String.class;
			case 2:
				return Integer.class;
			case 1:
				return VeggProductionV.class;
			case 10:
				return BigDecimal.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}
	}

	/*     */ }
