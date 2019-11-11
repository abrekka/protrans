package no.ugland.utransprod.gui.handlers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.ListModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.PatternPredicate;

import com.google.inject.Inject;
import com.jgoodies.binding.adapter.AbstractTableAdapter;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.model.Applyable;
import no.ugland.utransprod.gui.model.ColorEnum;
import no.ugland.utransprod.gui.model.InvoiceApplyList;
import no.ugland.utransprod.gui.model.TextPaneRendererOrder;
import no.ugland.utransprod.gui.model.Transportable;
import no.ugland.utransprod.model.FaktureringV;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.ApplicationParamUtil;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;
import no.ugland.utransprod.util.excel.ExcelUtil;

/**
 * Hjelpeklasse for visning og administrering av fakturerte ordre
 * 
 * @author atle.brekka
 */
public class InvoiceViewHandler extends AbstractProductionPackageViewHandlerShort<FaktureringV> {

	private JButton buttonSendtMail;
	private JButton buttonIkkeSendtMail;

	/**
	 * @param productionInterface
	 * @param userType
	 * @param applicationUser
	 * @param accidentManager
	 * @param deviationManager
	 */
	@Inject
	public InvoiceViewHandler(Login login, InvoiceApplyList aInvoiceApplyList, ManagerRepository managerRepository,
			DeviationViewHandlerFactory deviationViewHandlerFactory) {
		super(login, managerRepository, deviationViewHandlerFactory, aInvoiceApplyList, "Fakturering",
				TableEnum.TABLEINVOICE);
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
	protected void setApplied(FaktureringV faktureringV, boolean applied, WindowInterface window) {
		if (faktureringVExists(faktureringV, window)) {
			Order order = managerRepository.getOrderManager().findByOrderNr(faktureringV.getOrderNr());
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

	private boolean faktureringVExists(FaktureringV faktureringV, WindowInterface window) {
		FaktureringV currentFaktureringV = managerRepository.getFaktureringVManager()
				.findByOrderNr(faktureringV.getOrderNr());
		if (currentFaktureringV == null) {
			Util.showErrorDialog(window, "Finnes ikke", "Objekt finnes ikke, kjør oppdater av listen");
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
	void initColumnWidth() {
		table.getColumnExt(0).setPreferredWidth(100);
		table.getColumnExt(1).setPreferredWidth(150);
		DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
		centerRenderer.setHorizontalAlignment( JLabel.CENTER );
		table.getColumn(1).setCellRenderer(centerRenderer);
		table.getColumnExt(2).setPreferredWidth(50);
		table.getColumn(3).setCellRenderer(centerRenderer);
		table.getColumn(4).setCellRenderer(centerRenderer);
		table.getColumnExt(3).setPreferredWidth(150);
		table.getColumnExt(4).setPreferredWidth(150);
		table.getColumnExt(5).setPreferredWidth(150);
	}

	@Override
	void initColumnWidthExt() {
		table.getColumnExt(5).setPreferredWidth(100);
	}

	/**
	 * Tabellmodell for tabell med order til fakturering
	 * 
	 * @author atle.brekka
	 */
	private final class InvoiceTableModel extends AbstractTableAdapter {
		private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * @param listModel
		 */
		public InvoiceTableModel(ListModel listModel) {
			super(listModel, new String[] { "Ordre", "Levert", "Beløp", "Fakturert", "Montert", "Produktområde",
					"Ordrekorodinator", "Sendt mail" });
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
				if (faktureringV.getLevert() != null) {
					return Util.SHORT_DATE_FORMAT.format(faktureringV.getLevert());
				}
				return null;
			case 2:
				return faktureringV.getCustomerCost();
			case 3:
				if (faktureringV.getInvoiceDate() != null) {
					return Util.SHORT_DATE_FORMAT.format(faktureringV.getInvoiceDate());
				}
				return "---";
			case 4:
				if (faktureringV.getAssembliedDate() != null) {
					return Util.SHORT_DATE_FORMAT.format(faktureringV.getAssembliedDate());
				}
				return null;
			case 5:
				if (faktureringV.getProductAreaGroupName() != null) {
					return faktureringV.getProductAreaGroupName();
				}
				return "";
			case 6:
				if (faktureringV.getOrdrekoordinator() != null) {
					return faktureringV.getOrdrekoordinator();
				}
				return "";
			case 7:
				if (faktureringV.getSentMail() != null) {
					return simpleDateFormat.format(faktureringV.getSentMail());
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
			case 6:
			case 7:
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
		return new Dimension(900, 500);
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
	protected void searchOrder(String orderNr, String customerNr, WindowInterface window) {
		try {
			List<FaktureringV> list = applyListInterface.doSearch(orderNr, customerNr,
					(ProductAreaGroup) productAreaGroupModel
							.getValue(ProductAreaGroupModel.PROPERTY_PRODUCT_AREA_GROUP));

			Applyable faktureringV = getSearchObject(window, list);
			if (faktureringV != null) {
				int selectedIndex = objectList.indexOf(faktureringV);
				table.getSelectionModel().setSelectionInterval(table.convertRowIndexToView(selectedIndex),
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
	public FaktureringV getApplyObject(Transportable transportable, WindowInterface window) {
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

	@Override
	protected void setRealProductionHours(FaktureringV object, BigDecimal overstyrtTidsforbruk) {
		// TODO Auto-generated method stub

	}

	public JButton getButtonExcel(WindowInterface window) {
		JButton buttonExcel = new JButton(new ExcelAction(window));
		buttonExcel.setIcon(IconEnum.ICON_EXCEL.getIcon());
		return buttonExcel;
	}

	public final JButton getButtonSendtMail() {
		buttonSendtMail = new JButton(new SendtMailAction());
		buttonSendtMail.setEnabled(false);
		buttonSendtMail.setName("ButtonSendtMail");
		return buttonSendtMail;
	}

	public final JButton getButtonIkkeSendtMail() {
		buttonIkkeSendtMail = new JButton(new SendtIkkeMailAction());
		buttonIkkeSendtMail.setEnabled(false);
		buttonIkkeSendtMail.setName("ButtonIkkeSendtMail");
		return buttonIkkeSendtMail;
	}

	private class SendtMailAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SendtMailAction() {
			super("Sendt mail");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			setSendtMail(true);

		}

	}

	private class SendtIkkeMailAction extends AbstractAction {
		private static final long serialVersionUID = 1L;

		public SendtIkkeMailAction() {
			super("Ikke sendt mail");
		}

		/**
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(final ActionEvent arg0) {
			setSendtMail(false);

		}

	}

	@Override
	protected void buttonsEnabled(final FaktureringV object, final boolean hasSelection) {
		if (hasSelection) {
			if(object.getSentMail()==null){
			buttonSendtMail.setEnabled(true);
			buttonIkkeSendtMail.setEnabled(false);
			}else{
				buttonSendtMail.setEnabled(false);
				buttonIkkeSendtMail.setEnabled(true);
			}
		} else {
			buttonSendtMail.setEnabled(false);
		}
	}

	private void setSendtMail(boolean sendt) {
		FaktureringV object = getSelectedObject();

		Order order = managerRepository.getOrderManager().findByOrderNr(object.getOrderNr());
		if (sendt) {
			order.setSentMail(new Date());
		} else {
			order.setSentMail(null);
		}
		managerRepository.getOrderManager().saveOrder(order);

		// BigDecimal realProductionHours = object.getRealProductionHours();
		//
		// if (realProductionHours == null) {
		// realProductionHours =
		// Tidsforbruk.beregnTidsforbruk(object.getActionStarted(),
		// object.getProduced());
		// }
		//
		// String overstyrtTidsforbrukString =
		// Util.showInputDialogWithdefaultValue(null, "Sett reell tidsforbruk",
		// "Tidsforbruk:", realProductionHours == null ? "" :
		// String.valueOf(realProductionHours));
		//
		// BigDecimal overstyrtTidsforbruk =
		// StringUtils.isBlank(overstyrtTidsforbrukString) ? null
		// : new BigDecimal(overstyrtTidsforbrukString);
		//
		// setRealProductionHours(object, overstyrtTidsforbruk);
		objectSelectionList.clearSelection();

		this.doRefresh(null);

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
				exportToExcel(window);
				Util.showMsgFrame(window.getComponent(), "Excel generert",
						"Dersom excelfil ikke kom opp ligger den i katalog definert for excel");
			} catch (ProTransException e) {
				e.printStackTrace();
				Util.showErrorDialog(window, "Feil", e.getMessage());
			}
			Util.setDefaultCursor(window.getComponent());

		}
	}

	@Override
	void setFirstHighlighters() {
		ColorHighlighter pattern = new ColorHighlighter(new PatternPredicate("^(?!\\s*$).+", 7),
				ColorEnum.YELLOW.getColor(), Color.BLACK);
		table.addHighlighter(pattern);
	}

	private void exportToExcel(WindowInterface window) throws ProTransException {
		String fileName = "Pakkliste_" + Util.getCurrentDateAsDateTimeString() + ".xlsx";
		String excelDirectory = ApplicationParamUtil.findParamByName("excel_path");

		// JXTable tableReport = new JXTable(new
		// PacklistTableModel(getObjectSelectionList(), window, true));

		// ExcelUtil.showDataInExcel(excelDirectory, fileName, null,
		// "Pakkliste", tableReport, null, null, 16, false);
		ExcelUtil.showTableDataInExcel(excelDirectory, fileName, null, "Fakturering", table, null, null, 16, false);
		// ExcelUtil.showDataInExcelInThread(window, fileName, getTitle(),
		// getExcelTable(), null, null, 16, false);
	}
}
