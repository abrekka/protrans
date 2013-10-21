package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.InvoiceApplyList;
import no.ugland.utransprod.gui.model.TextPaneRendererOrder;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import com.google.inject.Inject;
import com.jgoodies.binding.adapter.AbstractTableAdapter;

/**
 * Hjelpeklasse for visning og administrering av fakturerte ordre
 * 
 * @author atle.brekka
 */
public class InvoiceViewHandler extends
		AbstractProductionPackageViewHandlerShort<FaktureringV> {
	/**
	 * @param productionInterface
	 * @param userType
	 * @param applicationUser
	 * @param accidentManager
	 * @param deviationManager
	 */
	@Inject
	public InvoiceViewHandler(Login login, InvoiceApplyList aInvoiceApplyList,
			ManagerRepository managerRepository,
			DeviationViewHandlerFactory deviationViewHandlerFactory) {
		super(login, managerRepository, deviationViewHandlerFactory,
				aInvoiceApplyList, "Fakturering", TableEnum.TABLEINVOICE);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyText()
	 */
	@Override
	protected String getApplyText() {
		return "Sett fakturert";
	}

	/**
	 * @param faktureringV
	 * @return true dersom knapp skal være enablet
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getButtonApplyEnabled(no.ugland.utransprod.gui.model.Applyable)
	 */
	@Override
	protected boolean getButtonApplyEnabled(FaktureringV faktureringV) {
		if (faktureringV.getInvoiceDate() == null) {
			return true;
		}
		return false;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getCheckBoxText()
	 */
	@Override
	protected String getCheckBoxText() {
		return "Vis fakturert";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getUnapplyText()
	 */
	@Override
	protected String getUnapplyText() {
		return "Sett ikke fakturert";
	}

	/**
	 * @param faktureringV
	 * @param applied
	 * @param window
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#setApplied(no.ugland.utransprod.gui.model.Applyable,
	 *      boolean, no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void setApplied(FaktureringV faktureringV, boolean applied,
			WindowInterface window) {
		if (faktureringVExists(faktureringV, window)) {
			Order order = managerRepository.getOrderManager().findByOrderNr(
					faktureringV.getOrderNr());
			if (applied) {
				Date invoiceDate = Util.getDate(window);
				order.setInvoiceDate(invoiceDate);
			} else {
				order.setInvoiceDate(null);
			}
			try {
				managerRepository.getOrderManager().saveOrder(order);
			} catch (ProTransException e) {
				Util.showErrorDialog(window, "Feil", e.getMessage());
				e.printStackTrace();
			}
			managerRepository.getFaktureringVManager().refresh(faktureringV);
		}
	}

	private boolean faktureringVExists(FaktureringV faktureringV,
			WindowInterface window) {
		FaktureringV currentFaktureringV = managerRepository
				.getFaktureringVManager().findByOrderNr(
						faktureringV.getOrderNr());
		if (currentFaktureringV == null) {
			Util.showErrorDialog(window, "Finnes ikke",
					"Objekt finnes ikke, kjør oppdater av listen");
			return false;
		}
		return true;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected TableModel getTableModel(WindowInterface window) {
		return new InvoiceTableModel(getObjectSelectionList());
	}

	@Override
	void initColumnWidthExt() {
		table.getColumnExt(0).setPreferredWidth(200);
	}

	/**
	 * Tabellmodell for tabell med order til fakturering
	 * 
	 * @author atle.brekka
	 */
	private final class InvoiceTableModel extends AbstractTableAdapter {

		/**
         * 
         */
		private static final long serialVersionUID = 1L;

		/**
		 * @param listModel
		 */
		public InvoiceTableModel(ListModel listModel) {
			super(listModel, new String[] { "Ordre", "Sent", "Beløp",
					"Fakturert", "Montert", "Produktområde" });
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(int rowIndex, int columnIndex) {
			FaktureringV faktureringV = (FaktureringV) getRow(rowIndex);

			switch (columnIndex) {
			case 0:
				return faktureringV;
			case 1:
				if (faktureringV.getSent() != null) {
					return Util.SHORT_DATE_FORMAT
							.format(faktureringV.getSent());
				}
				return null;
			case 2:
				return faktureringV.getCustomerCost();
			case 3:
				if (faktureringV.getInvoiceDate() != null) {
					return Util.SHORT_DATE_FORMAT.format(faktureringV
							.getInvoiceDate());
				}
				return "---";
			case 4:
				if (faktureringV.getAssembliedDate() != null) {
					return Util.SHORT_DATE_FORMAT.format(faktureringV
							.getAssembliedDate());
				}
				return null;
			case 5:
				if (faktureringV.getProductAreaGroupName() != null) {
					return faktureringV.getProductAreaGroupName();
				}
				return "";

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
				return FaktureringV.class;
			case 1:
			case 3:
			case 4:
			case 5:
				return String.class;
			case 2:
				return BigDecimal.class;
			default:
				throw new IllegalStateException("Unknown column");
			}
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyColumn()
	 */
	@Override
	protected Integer getApplyColumn() {
		return 3;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getWindowSize()
	 */
	@Override
	public Dimension getWindowSize() {
		return new Dimension(720, 420);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getTableWidth()
	 */
	@Override
	public String getTableWidth() {
		return "200dlu";
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#searchOrder(java.lang.String,
	 *      java.lang.String, no.ugland.utransprod.gui.WindowInterface)
	 */
	@Override
	protected void searchOrder(String orderNr, String customerNr,
			WindowInterface window) {
		try {
			List<FaktureringV> list = applyListInterface
					.doSearch(
							orderNr,
							customerNr,
							(ProductAreaGroup) productAreaGroupModel
									.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));

			Applyable faktureringV = getSearchObject(window, list);
			if (faktureringV != null) {
				int selectedIndex = objectList.indexOf(faktureringV);
				table.getSelectionModel().setSelectionInterval(
						table.convertRowIndexToView(selectedIndex),
						table.convertRowIndexToView(selectedIndex));

				table.scrollRowToVisible(table.getSelectedRow());
			}
		} catch (ProTransException e) {
			Util.showErrorDialog(window, "Feil", e.getMessage());
		}

	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getOrderCellRenderer()
	 */
	@Override
	protected TableCellRenderer getOrderCellRenderer() {
		return new TextPaneRendererOrder();
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getOrderInfoCell()
	 */
	@Override
	protected int getOrderInfoCell() {
		return 0;
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#hasWriteAccess()
	 */
	@Override
	protected boolean hasWriteAccess() {
		return UserUtil.hasWriteAccess(login.getUserType(), "Fakturering");
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getApplyObject(no.ugland.utransprod.gui.model.Transportable)
	 */
	@Override
	public FaktureringV getApplyObject(Transportable transportable,
			WindowInterface window) {
		return applyListInterface.getApplyObject(transportable, window);
	}

	/**
	 * @see no.ugland.utransprod.gui.handlers.AbstractProductionPackageViewHandler#getProductAreaColumn()
	 */
	@Override
	protected int getProductAreaColumn() {
		return 5;
	}

	@Override
	public void clearApplyObject() {
	}

}
