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
import no.ugland.utransprod.model.IgarasjenPackageV;
import no.ugland.utransprod.model.OrderLine;
import no.ugland.utransprod.model.PackableListItem;
import no.ugland.utransprod.service.IgarasjenPackageVManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.service.OrderLineManager;
import no.ugland.utransprod.service.VismaFileCreator;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

public class IgarasjenPackageApplyList extends PackageApplyList {

	@Inject
	public IgarasjenPackageApplyList(final Login login, final IgarasjenPackageVManager manager,
			ManagerRepository aManagerRepository, final VismaFileCreator vismaFileCreator) {
		super(login, manager, "Igarasjen", "Igarasjen", ReportEnum.IGARASJEN, vismaFileCreator, aManagerRepository);
	}

	private final class IgarasjenPackageTableModelReport extends AbstractTableModel {

		private static final long serialVersionUID = 1L;

		private String[] columnNames;

		private JXTable table;

		private SelectionInList objectSelectionList;

		public IgarasjenPackageTableModelReport(final ListModel listModel, final JXTable aTable,
				final SelectionInList aSelectionInList) {
			columnNames = new String[] { "TRANSPORT", "ORDER", "NUMBER_OF_ITEMS", "LOADING_DATE", "PACKAGED" };
			table = aTable;
			objectSelectionList = aSelectionInList;

		}

		public int getRowCount() {
			return table.getRowCount();
		}

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			IgarasjenPackageV igarasjenPackageV = (IgarasjenPackageV) objectSelectionList
					.getElementAt(table.convertRowIndexToModel(rowIndex));

			DecimalFormat decimalFormat = new DecimalFormat();
			decimalFormat.setDecimalSeparatorAlwaysShown(false);
			decimalFormat.setParseIntegerOnly(true);

			switch (columnIndex) {
			case 0:
				return igarasjenPackageV.getTransportDetails();
			case 1:
				StringBuffer buffer = new StringBuffer(igarasjenPackageV.getCustomerDetails());

				buffer.append(" - ").append(igarasjenPackageV.getOrderNr()).append(", ")
						.append(igarasjenPackageV.getAddress()).append(" ,")
						.append(igarasjenPackageV.getConstructionTypeName()).append(",")
						.append(igarasjenPackageV.getInfo());

				return buffer.toString();
			case 2:
				return igarasjenPackageV.getNumberOfItems();
			case 3:
				if (igarasjenPackageV.getTransportDetails() != null) {
					Date loadingDate = igarasjenPackageV.getLoadingDate();
					if (loadingDate != null) {
						return Util.SHORT_DATE_FORMAT.format(loadingDate);
					}
					return null;
				}
				return null;
			case 4:
				if (igarasjenPackageV.getColli() != null) {
					return "X";
				}
				return "";
			default:
				throw new IllegalStateException("Unknown column");
			}

		}

		public int getColumnCount() {
			return columnNames.length;
		}

		@Override
		public String getColumnName(final int colIndex) {
			return columnNames[colIndex];
		}
	}

	@Override
	public TableModel getTableModelReport(final ListModel listModel, final JXTable table,
			final SelectionInList objectSelectionList) {
		return new IgarasjenPackageTableModelReport(listModel, table, objectSelectionList);
	}

	@Override
	public void setInvisibleColumns(final JXTable table) {
		table.getColumnExt(3).setVisible(false);
//		table.getColumnExt(7).setVisible(false);

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
