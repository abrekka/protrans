package no.ugland.utransprod.gui.model;

import java.text.DecimalFormat;
import java.util.Date;

import javax.swing.ListModel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.model.GulvsponPackageV;
import no.ugland.utransprod.service.GulvsponPackageVManager;
import no.ugland.utransprod.service.ManagerRepository;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndtrer setting av at pakking av gulvspon er klar
 * @author atle.brekka
 */
public class GulvsponPackageApplyList extends PackageApplyList {

    /**
     * @param aUserType
     * @param manager
     */
	@Inject
    public GulvsponPackageApplyList(final Login login,
            final GulvsponPackageVManager manager,ManagerRepository aManagerRepository) {
        super(login, manager, "Gulvspon", "Gulvspon", ReportEnum.GULVSPON,null,aManagerRepository);
    }

    /**
     * Tabellmodell for rapport
     * @author atle.brekka
     */
    private final class GulvsponPackageTableModelReport extends
            AbstractTableModel {

        private static final long serialVersionUID = 1L;

        private String[] columnNames;

        private JXTable table;

        private SelectionInList objectSelectionList;

        /**
         * @param listModel
         * @param aTable
         * @param aSelectionInList
         */
        public GulvsponPackageTableModelReport(final ListModel listModel,
                final JXTable aTable, final SelectionInList aSelectionInList) {
            columnNames = new String[] { "TRANSPORT", "ORDER",
                    "NUMBER_OF_ITEMS", "LOADING_DATE", "PACKAGED" };
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
            GulvsponPackageV gulvsponPackageV = (GulvsponPackageV) objectSelectionList
                    .getElementAt(table.convertRowIndexToModel(rowIndex));

            DecimalFormat decimalFormat = new DecimalFormat();
            decimalFormat.setDecimalSeparatorAlwaysShown(false);
            decimalFormat.setParseIntegerOnly(true);

            switch (columnIndex) {
            case 0:
                return gulvsponPackageV.getTransportDetails();
            case 1:
                StringBuffer buffer = new StringBuffer(gulvsponPackageV
                        .getCustomerDetails());

                buffer.append(" - ").append(gulvsponPackageV.getOrderNr())
                        .append(", ").append(gulvsponPackageV.getAddress())
                        .append(" ,").append(
                                gulvsponPackageV.getConstructionTypeName())
                        .append(",").append(gulvsponPackageV.getInfo());

                return buffer.toString();
            case 2:
                return gulvsponPackageV.getNumberOfItems();
            case 3:
                if (gulvsponPackageV.getTransportDetails() != null) {
                    Date loadingDate = gulvsponPackageV.getLoadingDate();
                    if (loadingDate != null) {
                        return Util.SHORT_DATE_FORMAT.format(loadingDate);
                    }
                    return null;
                }
                return null;
            case 4:
                if (gulvsponPackageV.getColli() != null) {
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
        return new GulvsponPackageTableModelReport(listModel, table,
                objectSelectionList);
    }

    /**
     * @see no.ugland.utransprod.gui.model.AbstractApplyList#setInvisibleColumns(org.jdesktop.swingx.JXTable)
     */
    @Override
    public void setInvisibleColumns(final JXTable table) {
        table.getColumnExt(3).setVisible(false);

    }

}
