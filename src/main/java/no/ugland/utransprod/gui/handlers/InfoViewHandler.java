package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;
import java.util.Date;

import javax.swing.JTextArea;
import javax.swing.ListModel;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditInfoView;
import no.ugland.utransprod.gui.model.InfoModel;
import no.ugland.utransprod.model.Info;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.InfoManager;
import no.ugland.utransprod.util.UserUtil;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.AbstractTableAdapter;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.PropertyConnector;
import com.toedter.calendar.JDateChooser;

/**
 * Håndterer info som skal vises i informasjonsvindu
 * @author atle.brekka
 */
public class InfoViewHandler extends DefaultAbstractViewHandler<Info, InfoModel> {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * @param aUserType
     */
    public InfoViewHandler(UserType aUserType,InfoManager infoManager) {
        super("Produksjoninformasjon", infoManager, aUserType, true);
    }

    /**
     * Lager tekstområde for info
     * @param presentationModel
     * @return tekstområse
     */
    public JTextArea getTextAreaInfo(PresentationModel presentationModel) {
        return BasicComponentFactory.createTextArea(presentationModel
                .getBufferedModel(InfoModel.PROPERTY_INFO_TEXT));
    }

    /**
     * Lager datovelger for fra dato
     * @param presentationModel
     * @return datovelger
     */
    public JDateChooser getDateChooserFrom(PresentationModel presentationModel) {
        JDateChooser chooser = new JDateChooser();
        PropertyConnector conn = new PropertyConnector(chooser, "date",
                presentationModel
                        .getBufferedModel(InfoModel.PROPERTY_DATE_FROM),
                "value");
        conn.updateProperty1();
        return chooser;
    }

    /**
     * Lager datovelger for til dato
     * @param presentationModel
     * @return datovelger
     */
    public JDateChooser getDateChooserTo(PresentationModel presentationModel) {
        JDateChooser chooser = new JDateChooser();
        PropertyConnector conn = new PropertyConnector(chooser, "date",
                presentationModel.getBufferedModel(InfoModel.PROPERTY_DATE_TO),
                "value");
        conn.updateProperty1();
        return chooser;
    }

    /**
     * @param object
     * @return feilmedling
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
     */
    @Override
    public CheckObject checkDeleteObject(Info object) {
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
    public CheckObject checkSaveObject(InfoModel object,
            PresentationModel presentationModel, WindowInterface window) {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getAddRemoveString()
     */
    @Override
    public String getAddRemoveString() {
        return "informasjon";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getClassName()
     */
    @Override
    public String getClassName() {
        return "Info";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getNewObject()
     */
    @Override
    public Info getNewObject() {
        return new Info();
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getTableModel(no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public TableModel getTableModel(WindowInterface window) {
        return new InfoTableModel(objectSelectionList);
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
        return "Produksjoninformasjon";
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#getWindowSize()
     */
    @Override
    public Dimension getWindowSize() {
        return new Dimension(550, 270);
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#hasWriteAccess()
     */
    @Override
    public Boolean hasWriteAccess() {
        return UserUtil.hasWriteAccess(userType, "Informasjon");
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#setColumnWidth(org.jdesktop.swingx.JXTable)
     */
    @Override
    public void setColumnWidth(JXTable table) {
        // Informasjon
        table.getColumnExt(0).setPreferredWidth(200);

    }

    /**
     * Tabellmodell for info
     * @author atle.brekka
     */
    private static final class InfoTableModel extends AbstractTableAdapter {

        /**
         * 
         */
        private static final long serialVersionUID = 1L;

        /**
         * 
         */
        private static final String[] COLUMNS = {"Informasjon", "Fra dato",
                "Til dato"};

        /**
         * @param listModel
         */
        InfoTableModel(ListModel listModel) {
            super(listModel, COLUMNS);
        }

        /**
         * Henter verdi
         * @param rowIndex
         * @param columnIndex
         * @return verdi
         */
        public Object getValueAt(int rowIndex, int columnIndex) {
            Info info = (Info) getRow(rowIndex);
            switch (columnIndex) {
            case 0:
                return info.getInfoText();
            case 1:
                return info.getDateFrom();
            case 2:
                return info.getDateTo();
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
                return String.class;
            case 1:
            case 2:
                return Date.class;
            default:
                throw new IllegalStateException("Unknown column");
            }
        }

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
    protected AbstractEditView<InfoModel, Info> getEditView(
            AbstractViewHandler<Info, InfoModel> handler, Info object,
            boolean searching) {
        return new EditInfoView(searching, object, this);
    }

}
