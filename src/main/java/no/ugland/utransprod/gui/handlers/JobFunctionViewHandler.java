package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditJobFunctionView;
import no.ugland.utransprod.gui.model.JobFunctionModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.JobFunction;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ApplicationUserManager;
import no.ugland.utransprod.service.JobFunctionManager;
import no.ugland.utransprod.util.ModelUtil;
import no.ugland.utransprod.util.UserUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;

/**
 * Håndterer jobfunksjoner
 * @author atle.brekka
 */
public class JobFunctionViewHandler extends
DefaultAbstractViewHandler<JobFunction, JobFunctionModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param userType
     */
    public JobFunctionViewHandler(UserType userType,JobFunctionManager jobFunctionManager) {
        super("Funksjon", jobFunctionManager, userType, true);

    }

    /**
     * Lager tekstfelt for navn
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldName(PresentationModel presentationModel) {
        JTextField textField = BasicComponentFactory
                .createTextField(presentationModel
                        .getBufferedModel(JobFunctionModel.PROPERTY_JOB_FUNCTION_NAME));
        textField.setEnabled(hasWriteAccess());
        return textField;
    }

    /**
     * Lager tekstfelt for beskrivelse
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldDescription(
            PresentationModel presentationModel) {
        JTextField textField = BasicComponentFactory
                .createTextField(presentationModel
                        .getBufferedModel(JobFunctionModel.PROPERTY_JOB_FUNCTION_DESCRIPTION));
        textField.setEnabled(hasWriteAccess());
        return textField;
    }

    /**
     * Lager komboboks for leder
     * @param presentationModel
     * @return komboboks
     */
    public JComboBox getComboBoxManager(PresentationModel presentationModel) {
        JComboBox comboBox = new JComboBox(new ComboBoxAdapter(
                getApplicationUserList(), presentationModel
                        .getBufferedModel(JobFunctionModel.PROPERTY_MANAGER)));
        comboBox.setEnabled(hasWriteAccess());
        return comboBox;
    }

    /**
     * Finner alle brukere som ikke er gruppebrukere
     * @return brukere
     */
    public List<ApplicationUser> getApplicationUserList() {
        ApplicationUserManager applicationUserManager = (ApplicationUserManager) ModelUtil
                .getBean("applicationUserManager");
        return applicationUserManager.findAllNotGroup();
    }

    /**
     * @param object
     * @return feilmelding
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
     */
    @Override
    public CheckObject checkDeleteObject(JobFunction object) {
        String errorString = null;
        if (object.getDeviations() != null
                && object.getDeviations().size() != 0) {
            errorString = "Kan ikke slette funksjon som brukes av et avvik";
        } else if (object.getOwnDeviations() != null
                && object.getOwnDeviations().size() != 0) {
            errorString = "Kan ikke slette funksjon som brukes av et avvik";
        }

        return new CheckObject(errorString, false);
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
    public CheckObject checkSaveObject(JobFunctionModel object,
            PresentationModel presentationModel, WindowInterface window) {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
     */
    @Override
    public String getAddRemoveString() {
        return "funksjon";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
     */
    @Override
    public String getClassName() {
        return "JobFunction";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
     */
    @Override
    public JobFunction getNewObject() {
        return new JobFunction();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public TableModel getTableModel(WindowInterface window) {
        return new JobFunctionTableModel(objectSelectionList);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableWidth()
     */
    @Override
    public String getTableWidth() {
        return "220dlu";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTitle()
     */
    @Override
    public String getTitle() {
        return "Funksjon";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
     */
    @Override
    public Dimension getWindowSize() {
        return new Dimension(550, 270);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
     */
    @Override
    public void setColumnWidth(JXTable table) {
        // Navn
        TableColumn col = table.getColumnModel().getColumn(0);
        col.setPreferredWidth(150);

        // Beskrivelse
        col = table.getColumnModel().getColumn(1);
        col.setPreferredWidth(100);

        // Leder
        col = table.getColumnModel().getColumn(2);
        col.setPreferredWidth(100);

    }

    /**
     * Tabellmodell for jobbfunksjoner
     * @author atle.brekka
     */
    private static final class JobFunctionTableModel extends
            AbstractTableAdapter {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        private static final String[] COLUMNS = {"Navn", "Beskrivelse", "Leder"};

        /**
         * @param listModel
         */
        JobFunctionTableModel(ListModel listModel) {
            super(listModel, COLUMNS);
        }

        /**
         * Henter verdi
         * @param rowIndex
         * @param columnIndex
         * @return verdi
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            JobFunction jobFunction = (JobFunction) getRow(rowIndex);
            switch (columnIndex) {
            case 0:
                return jobFunction.getJobFunctionName();
            case 1:
                return jobFunction.getJobFunctionDescription();
            case 2:
                return jobFunction.getManager();
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
                return ApplicationUser.class;
            default:
                throw new IllegalStateException("Unknown column");
            }
        }

    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
     */
    @Override
    public Boolean hasWriteAccess() {
        return UserUtil.hasWriteAccess(userType, "Funksjon");
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
    protected AbstractEditView<JobFunctionModel, JobFunction> getEditView(
            AbstractViewHandler<JobFunction, JobFunctionModel> handler,
            JobFunction object, boolean searching) {
        return new EditJobFunctionView(searching, new JobFunctionModel(object),
                this);
    }

}
