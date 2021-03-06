package no.ugland.utransprod.gui.model;

import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.ListModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;
import com.jgoodies.binding.list.SelectionInList;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.model.SutakPackageV;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.SutakPackageVManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

public class SutakPackageApplyList extends PackageApplyList {

	/**
	 * @param aUserType
	 * @param manager
	 */
	@Inject
	public SutakPackageApplyList(final Login login, final SutakPackageVManager manager,
			ManagerRepository aManagerRepository, final VismaFileCreator vismaFileCreator) {
		super(login, manager, "Sutaksplater", "Sutaksplater", ReportEnum.GULVSPON, vismaFileCreator, aManagerRepository);
	}

	/**
	 * Tabellmodell for rapport
	 * 
	 * @author atle.brekka
	 */
	private final class SutakPackageTableModelReport extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private String[] columnNames;

		private JXTable table;

		private SelectionInList objectSelectionList;

		/**
		 * @param listModel
		 * @param aTable
		 * @param aSelectionInList
		 */
		public SutakPackageTableModelReport(final ListModel listModel, final JXTable aTable,
				final SelectionInList aSelectionInList) {
			columnNames = new String[] { "TRANSPORT", "ORDER", "NUMBER_OF_ITEMS", "LOADING_DATE", "PACKAGED" };
			table = aTable;
			objectSelectionList = aSelectionInList;

		}

		/**
		 * @see javax.swing.table.TableModel#getRowCount()
		 */
		public int getRowCount() {
			return table.getRowCount();
		}

		/**
		 * @see javax.swing.table.TableModel#getValueAt(int, int)
		 */
		public Object getValueAt(final int rowIndex, final int columnIndex) {
			SutakPackageV sutakPackageV = (SutakPackageV) objectSelectionList
					.getElementAt(table.convertRowIndexToModel(rowIndex));

			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalSeparatorAlwaysShown(false);
			decimalFormat.setParseIntegerOnly(true);

			switch (columnIndex) {
			case 0:
				return sutakPackageV.getTransportDetails();
			case 1:
				StringBuffer buffer = new StringBuffer(sutakPackageV.getCustomerDetails());

				buffer.append(" - ").append(sutakPackageV.getOrderNr()).append(", ")
						.append(sutakPackageV.getAddress()).append(" ,")
						.append(sutakPackageV.getConstructionTypeName()).append(",")
						.append(sutakPackageV.getInfo());

				return buffer.toString();
			case 2:
				return sutakPackageV.getNumberOfItems();
			case 3:
				if (sutakPackageV.getTransportDetails() != null) {
					Date loadingDate = sutakPackageV.getLoadingDate();
					if (loadingDate != null) {
						return Util.SHORT_DATE_FORMAT.format(loadingDate);
					}
					return null;
				}
				return null;
			case 4:
				if (sutakPackageV.getColli() != null) {
					return "X";
				}
				return "";
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		/**
		 * @see javax.swing.table.TableModel#getColumnCount()
		 */
		public int getColumnCount() {
			return columnNames.length;
		}

		/**
		 * @see javax.swing.table.AbstractTableModel#getColumnName(int)
		 */
		@Override
		public String getColumnName(final int colIndex) {
			return columnNames[colIndex];
		}
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#getTableModelReport(javax.swing.ListModel,
	 *      org.jdesktop.swingx.JXTable,
	 *      com.jgoodies.binding.list.SelectionInList)
	 */
	@Override
	public TableModel getTableModelReport(final ListModel listModel, final JXTable table,
			final SelectionInList objectSelectionList) {
		return new SutakPackageTableModelReport(listModel, table, objectSelectionList);
	}

	/**
	 * @see no.ugland.utransprod.gui.model.AbstractApplyList#setInvisibleColumns(org.jdesktop.swingx.JXTable)
	 */
	@Override
	public void setInvisibleColumns(final JXTable table) {
		table.getColumnExt(3).setVisible(false);

	}

	@Override
	public void setApplied(PackableListItem object, boolean applied, WindowInterface window) {
		super.setApplied(object, applied, window);
		OrderLineManager orderLineManager = (OrderLineManager) ModelUtil.getBean("orderLineManager");
		OrderLine orderLine = orderLineManager.findByOrderLineId(object.getOrderLineId());
		if (orderLine != null) {
			vismaFileCreator.createVismaFile(Lists.newArrayList(orderLine), 1, false);
		}
	}

}
