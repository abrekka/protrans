package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditDeviationStatusView;
import no.ugland.utransprod.gui.model.DeviationStatusModel;
import no.ugland.utransprod.model.DeviationStatus;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.DeviationStatusManager;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.apache.commons.lang.StringUtils;
import org.jdesktop.swingx.JXTable;

import com.google.inject.internal.Lists;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;

/**
 * Hjelpeklasse for editering av avvikstatus
 * @author atle.brekka
 */
public class DeviationStatusViewHandler
        extends DefaultAbstractViewHandler<DeviationStatus, DeviationStatusModel> {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    /**
     * @param userType
     */
    public DeviationStatusViewHandler(UserType userType,DeviationStatusManager deviationStatusManager) {
        super("Avikstatus", deviationStatusManager, userType, true);
    }

    /**
     * Lager tekstfelt for navn
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldName(PresentationModel presentationModel) {
        JTextField textField = BasicComponentFactory.createTextField(presentationModel
                .getBufferedModel(DeviationStatusModel.PROPERTY_DEVIATION_STATUS_NAME));
        textField.setEnabled(hasWriteAccess());
        return textField;
    }

    /**
     * Lager tekstfelt for beskrivelse
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldDescription(PresentationModel presentationModel) {
        JTextField textField = BasicComponentFactory.createTextField(presentationModel
                .getBufferedModel(DeviationStatusModel.PROPERTY_DEVIATION_STATUS_DESCRIPTION));
        textField.setEnabled(hasWriteAccess());
        return textField;
    }

    /**
     * Lager sjekkboks for å sette om status er for leder
     * @param presentationModel
     * @return sjekkboks
     */
    public JCheckBox getCheckBoxForManager(PresentationModel presentationModel) {
        JCheckBox checkBox = BasicComponentFactory.createCheckBox(presentationModel
                .getBufferedModel(DeviationStatusModel.PROPERTY_FOR_MANAGER_BOOL), "For leder");
        checkBox.setEnabled(hasWriteAccess());
        checkBox.setName("CheckBoxForManager");
        return checkBox;
    }

    public JCheckBox getCheckBoxDeviationDone(PresentationModel presentationModel) {
        JCheckBox checkBox = BasicComponentFactory.createCheckBox(presentationModel
                .getBufferedModel(DeviationStatusModel.PROPERTY_DEVIATION_DONE_BOOL), "Ferdig");
        checkBox.setEnabled(hasWriteAccess());
        checkBox.setName("CheckBoxDeviationDone");
        return checkBox;
    }
    public JCheckBox getCheckBoxForDeviation(PresentationModel presentationModel) {
        JCheckBox checkBox = BasicComponentFactory.createCheckBox(presentationModel
                .getBufferedModel(DeviationStatusModel.PROPERTY_FOR_DEVIATION_BOOL), "For avvik");
        checkBox.setEnabled(hasWriteAccess());
        checkBox.setName("CheckBoxForDeviation");
        return checkBox;
    }
    public JCheckBox getCheckBoxForAccident(PresentationModel presentationModel) {
        JCheckBox checkBox = BasicComponentFactory.createCheckBox(presentationModel
                .getBufferedModel(DeviationStatusModel.PROPERTY_FOR_ACCIDENT_BOOL), "For ulykke");
        checkBox.setEnabled(hasWriteAccess());
        checkBox.setName("CheckBoxForAccident");
        return checkBox;
    }

    /**
     * @param object
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
     */
    @Override
    public CheckObject checkDeleteObject(DeviationStatus object) {
        if (((DeviationStatusManager) overviewManager).countUsedByDeviation(object) != 0) {
            return new CheckObject("Kan ikke slette status som er brukt av avvik", false);
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
    public CheckObject checkSaveObject(
            DeviationStatusModel object, PresentationModel presentationModel, WindowInterface window) {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
     */
    @Override
    public String getAddRemoveString() {
        return "avikstatus";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
     */
    @Override
    public String getClassName() {
        return "DeviationStatus";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
     */
    @Override
    public DeviationStatus getNewObject() {
        return new DeviationStatus();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public TableModel getTableModel(WindowInterface window) {
        return new DeviationStatusTableModel(objectSelectionList);
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
        return "Avikstatus";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
     */
    @Override
    public Dimension getWindowSize() {
        return new Dimension(600, 270);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
     */
    @Override
    public void setColumnWidth(JXTable table) {
        DeviationStatusTableModel.DeviationStatusColumn[] columns = DeviationStatusTableModel.DeviationStatusColumn.values();
        for(int i=0;i<columns.length;i++){
            columns[i].setPrefferedWidth(table);
        }

    }

    /**
     * Tabellmodell for avvikstatus
     * @author atle.brekka
     */
    private static final class DeviationStatusTableModel extends AbstractTableAdapter {

        private static final long serialVersionUID = 1L;

        //private static final String[] COLUMNS = { "Navn", "Leder", "Ferdig", "Beskrivelse" };

        /**
         * @param listModel
         */
        DeviationStatusTableModel(ListModel listModel) {
            super(listModel, DeviationStatusColumn.getColumNames());
        }

        /**
         * Henter verdi
         * @param rowIndex
         * @param columnIndex
         * @return verdi
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            DeviationStatus deviationStatus = (DeviationStatus) getRow(rowIndex);
            String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_");
            return DeviationStatusColumn.valueOf(columnName).getValue(deviationStatus);
        }

        /**
         * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
         */
        @Override
        public Class<?> getColumnClass(int columnIndex) {
            String columnName = StringUtils.upperCase(getColumnName(columnIndex)).replaceAll(" ", "_");
            return DeviationStatusColumn.valueOf(columnName).getColumnClass();
        }

        public enum DeviationStatusColumn {
            NAVN("Navn") {
                @Override
                public Class<?> getColumnClass() {
                    return String.class;
                }

                @Override
                public Object getValue(DeviationStatus deviationStatus) {
                    return deviationStatus.getDeviationStatusName();
                }

                @Override
                public void setPrefferedWidth(JXTable table) {
                    table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(100);
                    
                }
            },
            LEDER("Leder") {
                @Override
                public Class<?> getColumnClass() {
                    return Boolean.class;
                }

                @Override
                public Object getValue(DeviationStatus deviationStatus) {
                    return Util.convertNumberToBoolean(deviationStatus.getForManager());
                }
                @Override
                public void setPrefferedWidth(JXTable table) {
                    table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(50);
                    
                }
            },
            FERDIG("Ferdig") {
                @Override
                public Class<?> getColumnClass() {
                    return Boolean.class;
                }

                @Override
                public Object getValue(DeviationStatus deviationStatus) {
                    return Util.convertNumberToBoolean(deviationStatus.getDeviationDone());
                }
                @Override
                public void setPrefferedWidth(JXTable table) {
                    table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(50);
                    
                }
            },
            AVVIK("Avvik") {
            	@Override
                public Class<?> getColumnClass() {
                    return Boolean.class;
                }

                @Override
                public Object getValue(DeviationStatus deviationStatus) {
                    return Util.convertNumberToBoolean(deviationStatus.getForDeviation());
                }
                @Override
                public void setPrefferedWidth(JXTable table) {
                    table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(50);
                    
                }
            },
            
            ULYKKE("Ulykke") {
            	@Override
                public Class<?> getColumnClass() {
                    return Boolean.class;
                }

                @Override
                public Object getValue(DeviationStatus deviationStatus) {
                    return Util.convertNumberToBoolean(deviationStatus.getForAccident());
                }
                @Override
                public void setPrefferedWidth(JXTable table) {
                    table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(50);
                    
                }
            },
            BESKRIVELSE("Beskrivelse") {
                @Override
                public Class<?> getColumnClass() {
                    return String.class;
                }

                @Override
                public Object getValue(DeviationStatus deviationStatus) {
                    return deviationStatus.getDeviationStatusDescription();
                }
                @Override
                public void setPrefferedWidth(JXTable table) {
                    table.getColumnExt(table.getColumnExt(getColumnName()).getModelIndex()).setPreferredWidth(100);
                    
                }
            };
            private String columnName;
            private static final List<String> COLUMN_NAMES = Lists.newArrayList();
            
            static{
            	for(DeviationStatusColumn col:DeviationStatusColumn.values()){
            		COLUMN_NAMES.add(col.getColumnName());
            	}
            }
            private DeviationStatusColumn(String aColumnName){
                columnName=aColumnName;
            }
            public String getColumnName(){
                return columnName;
            }
            public abstract Object getValue(DeviationStatus deviationStatus);

            public abstract Class<?> getColumnClass();
            public abstract void setPrefferedWidth(JXTable table);
            
            public static String[] getColumNames(){
            	String[] columNames=new String[COLUMN_NAMES.size()];
            	return COLUMN_NAMES.toArray(columNames);
            }
        }
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
     */
    @Override
    public Boolean hasWriteAccess() {
        return UserUtil.hasWriteAccess(userType, "Avikstatus");
    }

    @Override
    protected AbstractEditView<DeviationStatusModel, DeviationStatus> getEditView(
            AbstractViewHandler<DeviationStatus, DeviationStatusModel> handler, DeviationStatus object,
            boolean searching) {
        return new EditDeviationStatusView(searching, new DeviationStatusModel(object), this);
    }

}
