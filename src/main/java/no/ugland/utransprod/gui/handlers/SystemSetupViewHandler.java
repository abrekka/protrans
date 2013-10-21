package no.ugland.utransprod.gui.handlers;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.ListModel;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.Updateable;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.buttons.SaveButton;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.WindowAccessManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.PrefsUtil;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.validation.util.ValidationUtils;

public class SystemSetupViewHandler implements Closeable, Updateable {
    private List<WindowAccess> windowList;

    private PresentationModel presentationModel;

    private JButton buttonSave;

    private JButton buttonAddColumn;

    private JButton buttonRemoveColumn;

    private SystemSetup systemSetup;

    public SystemSetupViewHandler() {
        systemSetup = new SystemSetup();
        presentationModel = new PresentationModel(systemSetup);
        presentationModel.addBeanPropertyChangeListener(
                SystemSetup.PROPERTY_COLUMNS, new ColumnChangeListener());

        systemSetup.getColumns().addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_EMPTY,
                new EmptySelectionListener());
        presentationModel.addBeanPropertyChangeListener(SystemSetup.PROPERTY_PRODUCT_AREA_GROUP, new ProductAreaGroupChangeListener());
        /*systemSetup.tableNames.addPropertyChangeListener(
                SelectionInList.PROPERTYNAME_SELECTION_INDEX,
                );*/
        init();
    }

    private void init() {
        WindowAccessManager windowAccessManager = (WindowAccessManager) ModelUtil
                .getBean("windowAccessManager");
        windowList = windowAccessManager.findAllWithTableNames();
    }

    public JComboBox getComboBoxWindows() {
        JComboBox comboBox = new JComboBox(new ComboBoxAdapter(windowList,
                presentationModel.getModel(SystemSetup.PROPERTY_WINDOW_ACCESS)));
        comboBox.setName("ComboBoxWindows");
        return comboBox;
    }

    public JComboBox getComboBoxTables() {
        JComboBox comboBox = BasicComponentFactory
                .createComboBox((SelectionInList) presentationModel
                        .getValue(SystemSetup.PROPERTY_TABLE_NAMES));
        comboBox.setName("ComboBoxTableNames");
        return comboBox;
    }

    public JComboBox getComboBoxProductAreaGroup() {
        JComboBox comboBox = Util
                .createComboBoxProductAreaGroup(presentationModel
                        .getModel(SystemSetup.PROPERTY_PRODUCT_AREA_GROUP));
        comboBox.setName("ComboBoxProductAreaGroup");
        return comboBox;
    }

    public JButton getButtonCancel(WindowInterface window) {
        JButton button = new CancelButton(window, this, true);
        button.setName("ButtonCancel");
        return button;
    }

    public JButton getButtonSave(WindowInterface window) {
        buttonSave = new SaveButton(this, window);
        buttonSave.setName("ButtonSave");
        buttonSave.setEnabled(false);
        return buttonSave;
    }

    public JList getListColumns() {
        JList list = BasicComponentFactory
                .createList((SelectionInList) presentationModel
                        .getValue(SystemSetup.PROPERTY_COLUMNS));
        list.setName("ListColumns");
        return list;
    }

    public JButton getButtonAddColumn(WindowInterface window) {
        buttonAddColumn = new JButton(new AddColumnAction(window));
        buttonAddColumn.setName("ButtonAddColumn");
        buttonAddColumn.setEnabled(false);
        return buttonAddColumn;
    }

    public JButton getButtonRemoveColumn() {
        buttonRemoveColumn = new JButton(new RemoveColumnAction());
        buttonRemoveColumn.setName("ButtonRemoveColumn");
        buttonRemoveColumn.setEnabled(false);
        return buttonRemoveColumn;
    }

    public class SystemSetup extends Model {
        public static final String PROPERTY_WINDOW_ACCESS = "windowAccess";

        public static final String PROPERTY_TABLE_NAMES = "tableNames";

        public static final String PROPERTY_PRODUCT_AREA_GROUP = "productAreaGroup";

        public static final String PROPERTY_COLUMNS = "columns";

        private WindowAccess windowAccess;

        private final ArrayListModel tableNameList;

        private final SelectionInList tableNames;

        private final ArrayListModel columnList;

        private final SelectionInList columns;

        private ProductAreaGroup productAreaGroup;

        public SystemSetup() {
            tableNameList = new ArrayListModel();
            tableNames = new SelectionInList((ListModel) tableNameList);
            
            tableNames.addPropertyChangeListener(
                    SelectionInList.PROPERTYNAME_SELECTION_INDEX,
                    new TableNameSelectionListener());

            columnList = new ArrayListModel();
            columns = new SelectionInList((ListModel) columnList);
        }

        public WindowAccess getWindowAccess() {
            return windowAccess;
        }

        public void setWindowAccess(final WindowAccess aWindow) {
            WindowAccess oldWindow = getWindowAccess();
            windowAccess = aWindow;
            setTableNameList();

            firePropertyChange(PROPERTY_WINDOW_ACCESS, oldWindow, aWindow);

        }

        private void setTableNameList() {
            tableNameList.clear();
            if (windowAccess != null) {
                tableNameList.addAll(Util.convertStringListToList(windowAccess
                        .getTableNames()));

            }
            firePropertyChange(PROPERTY_TABLE_NAMES, null, tableNames);
        }

        public SelectionInList getTableNames() {
            return tableNames;
        }

        public SelectionInList getColumns() {
            return columns;
        }

        public ProductAreaGroup getProductAreaGroup() {
            return productAreaGroup;
        }

        public void setProductAreaGroup(ProductAreaGroup aProductAreaGroup) {
            ProductAreaGroup oldGroup = getProductAreaGroup();
            productAreaGroup = aProductAreaGroup;
            setColumns();
            firePropertyChange(PROPERTY_PRODUCT_AREA_GROUP, oldGroup,
                    aProductAreaGroup);
        }

        private void setColumns() {
            String tableName = (String) tableNames.getSelection();
            String productAreaGroupName = "Alle";
            columnList.clear();
            if (tableName != null) {
                if (productAreaGroup != null) {
                    productAreaGroupName = productAreaGroup
                            .getProductAreaGroupName();
                }
                List<String> prefList = PrefsUtil.getSystemList(
                        productAreaGroupName, tableName);
                if (prefList != null) {
                    columnList.addAll(prefList);
                }
            }
        }

        private class TableNameSelectionListener implements
                PropertyChangeListener {

            public void propertyChange(PropertyChangeEvent evt) {
                setColumns();

            }

        }

        public void addColumn(String aColumnName) {
            columnList.add(aColumnName);
            firePropertyChange(PROPERTY_COLUMNS, null, columns);
        }

        public void removeColumn(String aColumnName) {
            columnList.remove(aColumnName);
            firePropertyChange(PROPERTY_COLUMNS, null, columns);
        }

        public List getColumnList() {
            return columnList;
        }

        public String getTableName() {
            return (String) tableNames.getSelection();
        }
    }

    public boolean canClose(String actionString, WindowInterface window) {
        return true;
    }

    public boolean doDelete(WindowInterface window) {
        // TODO Auto-generated method stub
        return false;
    }

    public void doNew(WindowInterface window) {
        // TODO Auto-generated method stub

    }

    public void doRefresh(WindowInterface window) {
        // TODO Auto-generated method stub

    }

    public void doSave(WindowInterface window) {
        List<Integer> columns = systemSetup.getColumnList();
        PrefsUtil.setSystemPrefArray(systemSetup.getProductAreaGroup()
                .getProductAreaGroupName(), systemSetup.getTableName(), columns
                .toArray(new String[columns.size()]));
        buttonSave.setEnabled(false);

    }

    private class ColumnChangeListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            buttonSave.setEnabled(true);

        }

    }

    private class ProductAreaGroupChangeListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            buttonAddColumn.setEnabled(true);

        }

    }

    private class AddColumnAction extends AbstractAction {
        private WindowInterface window;

        public AddColumnAction(WindowInterface aWindow) {
            super("Legg til kolonne...");
            window = aWindow;
        }

        public void actionPerformed(ActionEvent e) {
            String columnName = Util.showInputDialog(window, "Kolonne",
                    "Kolonnenavn");

            if(columnName.length()!=0){
            systemSetup.addColumn(columnName);
            }

        }
    }

    private class RemoveColumnAction extends AbstractAction {
        public RemoveColumnAction() {
            super("Fjern kolonne...");
        }

        public void actionPerformed(ActionEvent e) {
            String column = (String) systemSetup.getColumns().getSelection();
            if (column != null) {
                systemSetup.removeColumn(column);
            }
        }
    }

    private class EmptySelectionListener implements PropertyChangeListener {

        public void propertyChange(PropertyChangeEvent evt) {
            buttonRemoveColumn.setEnabled(systemSetup.getColumns()
                    .hasSelection());

        }

    }
}
