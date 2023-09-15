package no.ugland.utransprod.gui.handlers;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Map;

import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.action.SetProductionUnitActionFactory;
import no.ugland.utransprod.gui.checker.StatusCheckerInterface;
import no.ugland.utransprod.gui.model.ApplyListInterface;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.ArticleType;
import no.ugland.utransprod.model.FrontProductionV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.Produceable;
import no.ugland.utransprod.model.VeggProductionV;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.Tidsforbruk;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.adapter.AbstractTableAdapter;

/**
 * Hjelpeklasse for frontproduksjon
 * 
 * @author atle.brekka
 */
public class FrontProductionViewHandler extends ProductionViewHandler {

	/**
	 * @param productionInterface
	 * @param title
	 * @param deviationViewHandlerFactory
	 * @param userType
	 * @param applicationUser
	 */
	public FrontProductionViewHandler(ApplyListInterface<Produceable> productionInterface, String title, Login login,
			ArticleType articleType, ManagerRepository managerRepository,
			DeviationViewHandlerFactory deviationViewHandlerFactory,
			SetProductionUnitActionFactory aSetProductionUnitActionFactory) {
		super(productionInterface, title, login, null, "produksjon", TableEnum.TABLEPRODUCTIONFRONT, articleType,
				managerRepository, deviationViewHandlerFactory, aSetProductionUnitActionFactory);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.ProductionViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected TableModel getTableModel(WindowInterface window) {
		return new FrontProductionTableModel(getObjectSelectionList(), window);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#initColumnWidth()
	 */
	@Override
	protected void initColumnWidthExt() {
		// Transportdato
		table.getColumnExt(table.getModel().getColumnName(0)).setPreferredWidth(100);

		// Ordre
		table.getColumnExt(table.getModel().getColumnName(1)).setPreferredWidth(200);
		// Pro.dato
		DefaultTableCellRenderer tableCellRenderer = new DefaultTableCellRenderer();
		tableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
		table.getColumnExt(table.getModel().getColumnName(2)).setPreferredWidth(80);
		table.getColumnExt(table.getModel().getColumnName(2)).setCellRenderer(tableCellRenderer);

		// Antall
		table.getColumnExt(table.getModel().getColumnName(3)).setPreferredWidth(50);

		// vegg
		table.getColumnExt(table.getModel().getColumnName(4)).setPreferredWidth(70);
		// opplasting
		table.getColumnExt(table.getModel().getColumnName(5)).setPreferredWidth(100);
		// produsert
		table.getColumnExt(table.getModel().getColumnName(6)).setPreferredWidth(110);
		// produsert
		table.getColumnExt(table.getModel().getColumnName(8)).setPreferredWidth(110);
		// reell tidsforbruk
		table.getColumnExt(table.getModel().getColumnName(10)).setPreferredWidth(110);
		table.getColumnExt(table.getModel().getColumnName(10)).setCellRenderer(tableCellRenderer);
	}

	/**
	 * Tabellmodell for frontproduksjon
	 * 
	 * @author atle.brekka
	 */
	final class FrontProductionTableModel extends AbstractTableAdapter {

		private static final long serialVersionUID = 1L;

		private StatusCheckerInterface<Transportable> veggChecker;

		private WindowInterface window;

		public FrontProductionTableModel(ListModel listModel, WindowInterface aWindow) {
			super(listModel, new String[] { "Transport", "Ordre", "Prod. uke", "Antall", "Vegg", "Opplasting",
					"Produsert", "Produktområde", "Prod.enhet", "Startet", "Reell tidsforbruk", "Gjort av" });
			window = aWindow;
			veggChecker = Util.getVeggChecker();
			// initStatus(listModel);

		}

		private void initStatus(ListModel list) {
			if (list != null) {
				Map<String, String> statusMap;
				String status;
				int rowCount = getRowCount();

				FrontProductionV prod;
				for (int i = 0; i < rowCount; i++) {
					prod = (FrontProductionV) getRow(i);
					statusMap = Util.createStatusMap(prod.getOrderStatus());
					status = statusMap.get(veggChecker.getArticleName());

					if (status == null) {
						Order order = managerRepository.getOrderManager().findByOrderNr(prod.getOrderNr());
						if (order != null) {
							managerRepository.getOrderManager().lazyLoad(order, new LazyLoadEnum[][] {
									{ LazyLoadEnum.ORDER_LINES, LazyLoadEnum.ORDER_LINE_ATTRIBUTES } });
							status = veggChecker.getArticleStatus(order);
							statusMap.put(veggChecker.getArticleName(), status);
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
			FrontProductionV frontProductionV = (FrontProductionV) getRow(rowIndex);
			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalSeparatorAlwaysShown(false);
			decimalFormat.setParseIntegerOnly(true);
			Map<String, String> statusMap = Util.createStatusMap(frontProductionV.getOrderStatus());

			switch (columnIndex) {
			case 0:
				return frontProductionV.getTransportDetails();

			case 1:
				return frontProductionV;
			case 2:
				return frontProductionV.getProductionWeek();
			case 3:
				if (frontProductionV.getNumberOfItems() != null) {
					return decimalFormat.format(frontProductionV.getNumberOfItems());
				}
				return "";

			case 4:
				String veggstatus = statusMap.get(veggChecker.getArticleName());
				return veggstatus == null ? "MANGLER" : veggstatus;
			case 5:

				Date loadingDate = frontProductionV.getLoadingDate();
				if (loadingDate != null) {
					return Util.SHORT_DATE_FORMAT.format(loadingDate);
				}
				return null;

			case 6:
				if (frontProductionV.getProduced() != null) {
					return Util.SHORT_DATE_TIME_FORMAT.format(frontProductionV.getProduced());
				}
				return "---";
			case 7:
				if (frontProductionV.getProductAreaGroupName() != null) {
					return frontProductionV.getProductAreaGroupName();
				}
				return "";
			case 8:
				return frontProductionV.getProductionUnitName();
			case 9:
				if (frontProductionV.getActionStarted() != null) {
					return Util.SHORT_DATE_TIME_FORMAT.format(frontProductionV.getActionStarted());
				}
				return "---";
			case 10:
				return frontProductionV.getRealProductionHours();
//				== null		? Tidsforbruk.beregnTidsforbruk(frontProductionV.getActionStarted(),
//			frontProductionV.getProduced()) : frontProductionV.getRealProductionHours();
			case 11:
				return frontProductionV.getDoneBy();
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

			case 3:
			case 4:
			case 5:
			case 7:
			case 8:
			case 9:
			case 11:
				return String.class;
			case 2:
				return Integer.class;
			case 1:
				return VeggProductionV.class;
			case 6:
				return Object.class;
			case 10:
				return BigDecimal.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	@Override
	protected int getProductAreaColumn() {
		return 7;
	}

	@Override
	protected Integer getStartColumn() {
		return 9;
	}

	@Override
	protected Integer getApplyColumn() {
		return 6;
	}
}
