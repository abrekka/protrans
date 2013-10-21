package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.StartWindowEnum;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditUserTypeView;
import no.ugland.utransprod.gui.model.UserTypeModel;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.model.UserTypeAccess;
import no.ugland.utransprod.model.WindowAccess;
import no.ugland.utransprod.service.UserTypeManager;
import no.ugland.utransprod.service.WindowAccessManager;
import no.ugland.utransprod.service.enums.LazyLoadEnum;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.ArrayListModel;
import com.jgoodies.binding.list.SelectionInList;

/**
 * Håndterer brukertyper
 * @author atle.brekka
 */
public class UserTypeViewHandler extends
DefaultAbstractViewHandler<UserType, UserTypeModel> {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */
    private ArrayListModel windowList;

    /**
     * 
     */
    JXTable tableWindows;

    /**
     * @param userType
     */
    public UserTypeViewHandler(UserType userType,UserTypeManager userTypeManager) {
        super("Brukertyper", userTypeManager, userType, true);
    }

    /**
     * Lager tekstfelt for beskrivelse
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldDescription(
            PresentationModel presentationModel) {
        return BasicComponentFactory.createTextField(presentationModel
                .getBufferedModel(UserTypeModel.PROPERTY_DESCRIPTION));
    }

    /**
     * Lager komboboks for oppstartsvindu
     * @param presentationModel
     * @return komboboks
     */
    public JComboBox getComboBoxStartupWindow(
            PresentationModel presentationModel) {
        return BasicComponentFactory
                .createComboBox(new SelectionInList(
                        StartWindowEnum.getAll(),
                        presentationModel
                                .getBufferedModel(UserTypeModel.PROPERTY_STARTUP_WINDOW_ENUM)));
    }

    /**
     * Lager sjekkboks for om bruker er administrator
     * @param presentationModel
     * @return sjekkboks
     */
    public JCheckBox getCheckBoxAdmin(PresentationModel presentationModel) {
        return BasicComponentFactory.createCheckBox(presentationModel
                .getBufferedModel(UserTypeModel.PROPERTY_IS_ADMIN_BOOLEAN),
                "Administrator");
    }

    /**
     * Initierer vindusliste
     */
    private void initWindowList() {
        WindowAccessManager windowAccessManager = (WindowAccessManager) ModelUtil
                .getBean("windowAccessManager");
        List<WindowAccess> windows = windowAccessManager.findAll();
        windowList = new ArrayListModel();
        if (windows != null) {
            windowList.addAll(windows);
        }
    }

    /**
     * Lager tabell for vindusakksess
     * @param presentationModel
     * @return tabell
     */
    @SuppressWarnings("unchecked")
    public JXTable getTableWindows(PresentationModel presentationModel) {
        if (windowList == null) {
            initWindowList();
        }
        List<UserTypeAccess> userTypeAccesses = (List<UserTypeAccess>) presentationModel
                .getBufferedValue(UserTypeModel.PROPERTY_USER_TYPE_ACCESS_LIST);

        Map<WindowAccess, UserTypeAccess> userTypeAccessMap = new HashMap<WindowAccess, UserTypeAccess>();
        List<WindowAccess> windowAccessList = windowList;
        for (WindowAccess windowAccess : windowAccessList) {
            userTypeAccessMap.put(windowAccess, null);
        }
        if (userTypeAccesses != null) {

            for (UserTypeAccess userTypeAccess : userTypeAccesses) {
                userTypeAccessMap.put(userTypeAccess.getWindowAccess(),
                        userTypeAccess);
            }
        }

        tableWindows = new JXTable(new WindowsTableModel(windowList,
                userTypeAccessMap, presentationModel));
        tableWindows.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tableWindows.getColumnExt(0).setPreferredWidth(250);
        tableWindows.getColumnExt(1).setPreferredWidth(70);
        tableWindows.getColumnExt(2).setPreferredWidth(70);
        if ((Boolean) presentationModel
                .getBufferedValue(UserTypeModel.PROPERTY_IS_ADMIN_BOOLEAN)) {
            tableWindows.setEnabled(false);
        }

        presentationModel.getBufferedModel(
                UserTypeModel.PROPERTY_IS_ADMIN_BOOLEAN)
                .addPropertyChangeListener(new IsAdminListener());

        return tableWindows;
    }

    /**
     * @param userType1
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
     */
    @Override
    public CheckObject checkDeleteObject(UserType userType1) {
        int count = ((UserTypeManager) overviewManager)
                .getNumberOfUsers(userType1);
        if (count != 0) {
            return new CheckObject("Kan ikke slette brukertype som brukes av brukere",false);
        }
        return null;
    }

    /**
     * @param object
     * @param presentationModel
     * @param window
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
     *      com.jgoodies.binding.PresentationModel,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public CheckObject checkSaveObject(UserTypeModel object,
            PresentationModel presentationModel, WindowInterface window) {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
     */
    @Override
    public String getAddRemoveString() {
        return "brukertype";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
     */
    @Override
    public String getClassName() {
        return "UserType";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
     */
    @Override
    public UserType getNewObject() {
        return new UserType();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public TableModel getTableModel(WindowInterface window) {
        return new UserTypeTableModel(objectSelectionList);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
     */
    @Override
    public String getTableWidth() {
        return "250dlu";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
     */
    @Override
    public String getTitle() {
        return "Brukertyper";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
     */
    @Override
    public Dimension getWindowSize() {
        return new Dimension(600, 400);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
     */
    @Override
    public void setColumnWidth(JXTable table) {
        // Beskrivelse
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setPreferredWidth(120);

        // Oppstartsvindu
        col = table.getColumnModel().getColumn(1);
        col.setPreferredWidth(200);

        // Administrator
        col = table.getColumnModel().getColumn(2);
        col.setPreferredWidth(100);

    }

    /**
     * Tabellmodell for brukertyper
     * @author atle.brekka
     */
    private static final class UserTypeTableModel extends AbstractTableAdapter {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        private static final String[] COLUMNS = {"Beskrivelse",
                "Oppstartsvindu", "Administrator"};

        /**
         * @param listModel
         */
        UserTypeTableModel(ListModel listModel) {
            super(listModel, COLUMNS);
        }

        /**
         * Henter verdi
         * @param rowIndex
         * @param columnIndex
         * @return verdi
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            UserType userType = (UserType) getRow(rowIndex);
            switch (columnIndex) {
            case 0:
                return userType.getDescription();
            case 1:
                return userType.getStartupWindowEnum();
            case 2:
                return Util.convertNumberToBoolean(userType.getIsAdmin());
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
            case 1:
                return String.class;
            case 2:
                return Boolean.class;
            default:
                throw new IllegalStateException("Unknown column");
            }
        }

    }

    /**
     * Tabellmodell for vindusakksess
     * @author atle.brekka
     */
    private static final class WindowsTableModel extends AbstractTableAdapter {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        private static final String[] COLUMNS = {"Vindu", "Lese", "Skrive"};

        /**
         * 
         */
        private Map<WindowAccess, UserTypeAccess> userTypeAccessMap;

        /**
         * 
         */
        private PresentationModel presentationModel;

        /**
         * @param listModel
         * @param accessMap
         * @param aPresentationModel
         */
        WindowsTableModel(ListModel listModel,
                Map<WindowAccess, UserTypeAccess> accessMap,
                PresentationModel aPresentationModel) {
            super(listModel, COLUMNS);
            userTypeAccessMap = accessMap;
            presentationModel = aPresentationModel;
        }

        /**
         * Henter verdi
         * @param rowIndex
         * @param columnIndex
         * @return verdi
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            WindowAccess windowAccess = (WindowAccess) getRow(rowIndex);
            UserTypeAccess userTypeAccess = userTypeAccessMap.get(windowAccess);
            switch (columnIndex) {
            case 0:
                return windowAccess.getWindowName();
            case 1:
                if (userTypeAccess != null) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            case 2:
                if (userTypeAccess != null) {
                    return Util.convertNumberToBoolean(userTypeAccess
                            .getWriteAccess());
                }
                return Boolean.FALSE;
            default:
                throw new IllegalStateException("Unknown column");
            }

        }

        /**
         * @see javax.swing.table.AbstractTableModel#isCellEditable(int, int)
         */
        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            if (columnIndex != 0) {
                return true;
            }
            return false;
        }

        /**
         * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
         */
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            switch (columnIndex) {
            case 0:

                return String.class;
            case 1:
            case 2:

                return Boolean.class;
            default:
                throw new IllegalStateException("Unknown column");
            }
        }

        /**
         * @see javax.swing.table.AbstractTableModel#setValueAt(java.lang.Object,
         *      int, int)
         */
        @SuppressWarnings("unchecked")
        @Override
        public void setValueAt(Object object, int rowIndex, int columnIndex) {
            Boolean access = (Boolean) object;
            WindowAccess windowAccess = (WindowAccess) getRow(rowIndex);
            UserTypeAccess userTypeAccess = userTypeAccessMap.get(windowAccess);
            UserType userType = ((UserTypeModel) presentationModel.getBean())
                    .getObject();
            List<UserTypeAccess> userTypeAccessList = (List<UserTypeAccess>) presentationModel
                    .getBufferedValue(UserTypeModel.PROPERTY_USER_TYPE_ACCESS_LIST);
            if (access) {
                if (userTypeAccess == null) {
                    userTypeAccess = new UserTypeAccess(null, 0, userType,
                            windowAccess);
                    userTypeAccessList.add(userTypeAccess);
                    userTypeAccessMap.put(windowAccess, userTypeAccess);
                }
            }
            switch (columnIndex) {
            case 1:
                if (!access) {
                    userTypeAccessList.remove(userTypeAccess);
                    userTypeAccessMap.put(windowAccess, null);
                }
                break;
            case 2:
                if (access) {
                    userTypeAccess.setWriteAccess(1);
                } else {
                    userTypeAccess.setWriteAccess(0);
                }
                break;
            }
            presentationModel.setBufferedValue(
                    UserTypeModel.PROPERTY_USER_TYPE_ACCESS_LIST,
                    userTypeAccessList);
        }

    }

    /**
     * Håndterer setting av administrator
     * @author atle.brekka
     */
    class IsAdminListener implements PropertyChangeListener {

        /**
         * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
         */
        public void propertyChange(PropertyChangeEvent event) {
            tableWindows.setEnabled(!(Boolean) event.getNewValue());

        }

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
     */
    @Override
    public Boolean hasWriteAccess() {
        return Util.convertNumberToBoolean(userType.getIsAdmin());
    }

    /**
     * @param handler
     * @param object
     * @param searching
     * @return view
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getEditView(no.ugland.utransprod.gui.handlers.AbstractViewHandler,
     *      java.lang.Object, boolean)
     */
    @Override
    protected AbstractEditView<UserTypeModel, UserType> getEditView(
            AbstractViewHandler<UserType, UserTypeModel> handler,
            UserType object, boolean searching) {
        overviewManager
                .lazyLoad(
                        object,
                        new LazyLoadEnum[][] {{LazyLoadEnum.USER_TYPE_ACCESSES,LazyLoadEnum.NONE}});

        return new EditUserTypeView(searching, object, this);
    }

}
