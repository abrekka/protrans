package no.ugland.utransprod.gui.handlers;

import java.awt.Dimension;

import javax.swing.JComboBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.model.ProcentDoneModel;
import no.ugland.utransprod.model.ProcentDone;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.ProcentDoneManager;
import no.ugland.utransprod.util.UserUtil;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.toedter.calendar.JYearChooser;

public class ProcentDoneViewHandler extends
DefaultAbstractViewHandler<ProcentDone, ProcentDoneModel> {

    public ProcentDoneViewHandler(UserType aUserType,ProcentDoneManager procentDoneManager) {
        super("Prosent ferdig", procentDoneManager, aUserType, true);
    }

    public ProcentDone checkSave(ProcentDoneModel procentDoneModel,
            WindowInterface window) {
        ProcentDone procentDone = procentDoneModel.getObject();
        CheckObject checkObject = checkSaveObject(procentDoneModel, null,
                window);
        
        if (checkObject != null && checkObject.getMsg() != null) {
            if (handleSaveCheckObject(window, checkObject)) {
                procentDone = (ProcentDone) checkObject.getRefObject();
            }else{
                procentDone=null;
            }
        }
        return procentDone;
    }

    public JYearChooser getYearChooser(PresentationModel presentationModel) {
        JYearChooser yearChooser = Util.createYearChooser(presentationModel
                .getBufferedModel(ProcentDoneModel.PROPERTY_PROCENT_DONE_YEAR));
        yearChooser.setName("YearChooser");
        return yearChooser;

    }

    public JComboBox getComboBoxWeek(PresentationModel presentationModel) {
        JComboBox comboBox = new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
                presentationModel
                        .getModel(ProcentDoneModel.PROPERTY_PROCENT_DONE_WEEK)));
        comboBox.setName("ComboBoxWeek");
        comboBox.setSelectedItem(Util.getCurrentWeek());
        return comboBox;
    }

    public JTextField getTextFieldProcent(PresentationModel presentationModel) {
        JTextField textField = BasicComponentFactory
                .createTextField(presentationModel
                        .getBufferedModel(ProcentDoneModel.PROPERTY_PROCENT_STRING));
        textField.setName("TextFieldProcent");
        return textField;
    }

    public JTextArea getTextAreaComment(PresentationModel presentationModel) {
        JTextArea textArea = BasicComponentFactory
                .createTextArea(presentationModel
                        .getBufferedModel(ProcentDoneModel.PROPERTY_PROCENT_DONE_COMMENT));
        textArea.setName("TextAreaProcentDoneComment");
        return textArea;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkDeleteObject(java.lang.Object)
     */
    @Override
    public CheckObject checkDeleteObject(ProcentDone object) {
        return null;
    }

    /**
     * @see no.ugland.utransprod.gui.handlers.AbstractViewHandler#checkSaveObject(java.lang.Object,
     *      com.jgoodies.binding.PresentationModel,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    @Override
    public CheckObject checkSaveObject(ProcentDoneModel object,
            PresentationModel presentationModel, WindowInterface window) {
        String warning = null;
        ProcentDone procentDone = object.getObject();
        if (object.getProcentDoneId() == null) {
            procentDone = ((ProcentDoneManager) overviewManager)
                    .findByYearWeekOrder(object.getProcentDoneYear(), object
                            .getProcentDoneWeek(), object.getOrder());

            if (procentDone != null) {
                warning = "Det finnes allerede en prosent for denne uken!";
            }
        }
        return new CheckObject(warning, true, procentDone);
    }

    @Override
    public String getAddRemoveString() {
        return "prosent ferdig";
    }

    @Override
    public String getClassName() {
        return "ProcentDone";
    }

    @Override
    protected AbstractEditView<ProcentDoneModel, ProcentDone> getEditView(
            AbstractViewHandler<ProcentDone, ProcentDoneModel> handler,
            ProcentDone object, boolean searching) {
        return null;
    }

    @Override
    public ProcentDone getNewObject() {
        return new ProcentDone();
    }

    @Override
    public TableModel getTableModel(WindowInterface window) {
        return null;
    }

    @Override
    public String getTableWidth() {
        return null;
    }

    @Override
    public String getTitle() {
        return "Prosent ferdig";
    }

    @Override
    public Dimension getWindowSize() {
        return null;
    }

    @Override
    public Boolean hasWriteAccess() {
        return UserUtil.hasWriteAccess(userType, "Prosent ferdig");
    }

    @Override
    public void setColumnWidth(JXTable table) {

    }

}
