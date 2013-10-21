package no.ugland.utransprod.gui.handlers;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

import no.ugland.utransprod.gui.Closeable;
import no.ugland.utransprod.gui.IconEnum;
import no.ugland.utransprod.gui.Login;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.buttons.CancelButton;
import no.ugland.utransprod.gui.edit.AbstractEditView;
import no.ugland.utransprod.gui.edit.EditCommentView;
import no.ugland.utransprod.gui.model.ICommentModel;
import no.ugland.utransprod.gui.model.OrderCommentModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.IComment;
import no.ugland.utransprod.model.OrderComment;
import no.ugland.utransprod.model.UserType;
import no.ugland.utransprod.service.OverviewManager;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXTable;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.validation.ValidationResultModel;

public class CommentViewHandler extends
        DefaultAbstractViewHandler<IComment, ICommentModel> {
    /**
     * 
     */
    private Login login;
    boolean ok = false;
    ValidationResultModel validationResultModel;

    @Inject
    public CommentViewHandler(final Login aLogin,@Assisted final OverviewManager aOverviewManager) {
        super("Kommentar", aOverviewManager, aLogin.getUserType(), true);
        login=aLogin;
    }
    public OrderComment showAndEditOrderComment(WindowInterface window,
            OrderComment orderComment, String managerName) {
        if (orderComment == null) {
            orderComment = OrderCommentModel
                    .createOrderCommentWithUserAndDate(login.getApplicationUser());
        }
        CommentViewHandler commentViewHandler = new CommentViewHandler(login,overviewManager);

        Util.showEditViewable(new EditCommentView(new OrderCommentModel(orderComment),
                commentViewHandler), window);

        if (commentViewHandler.isCanceled()) {
            orderComment = null;
        }
        return orderComment;

    }
    public boolean isOk() {
        return ok;
    }
    /**
     * Sjekker om dialog er ok
     * @return true dersom ok
     */
    public boolean isCanceled() {
        return !ok;
    }

    /**
     * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
     *      no.ugland.utransprod.gui.WindowInterface)
     */
    public boolean canClose(String actionString, WindowInterface window) {
        return true;
    }

    
    /**
     * Lager tekstfelt for brukernavn
     * @param presentationModel
     * @return tekstfelt
     */
    public JTextField getTextFieldUserName(PresentationModel presentationModel) {
        JTextField textField = BasicComponentFactory
                .createTextField(presentationModel
                        .getModel(OrderCommentModel.PROPERTY_USER_NAME));
        textField.setName("TextFieldDeviationCommentUserName");
        if (login.getApplicationUser().getGroupUser().equalsIgnoreCase("Nei")) {
            textField.setEnabled(false);
        }
        return textField;
    }
    /**
     * Lager tekstområde for kommentar
     * @param presentationModel
     * @return tekstområde
     */
    public JTextArea getTextAreaComment(PresentationModel presentationModel) {
        JTextArea textArea = BasicComponentFactory
                .createTextArea(presentationModel
                        .getModel(OrderCommentModel.PROPERTY_COMMENT),false);
        textArea.setName("TextAreaComment");
        return textArea;
    }
    /**
     * Lager avbrytknapp
     * @param window
     * @return knapp
     */
    public JButton getButtonCancel(WindowInterface window) {
        return new CancelButton(window, this, true);
    }
    /**
     * Lager ok-knapp
     * @param window
     * @return knapp
     */
    public JButton getButtonOk(WindowInterface window,ValidationResultModel aValidationResultModel) {
        validationResultModel=aValidationResultModel;
        JButton button = new CancelButton(window, new OkAction(), true, "Ok",
                IconEnum.ICON_OK, null, true);
        button.setName("ButtonCommentOk");
        return button;
    }
    /**
     * Lager sjekkboks for transportkommentar
     * @param presentationModel
     * @return sjekkboks
     */
    public JCheckBox getCheckBoxTransport(PresentationModel presentationModel) {
        JCheckBox checkBox =BasicComponentFactory.createCheckBox(presentationModel
                .getModel(OrderCommentModel.PROPERTY_FOR_TRANSPORT_BOOL),
        "Transport");
        checkBox.setName("CheckBoxTransport");
        return checkBox;
    }
    public JCheckBox getCheckBoxPackage(PresentationModel presentationModel) {
        JCheckBox checkBox =BasicComponentFactory.createCheckBox(presentationModel
                .getModel(OrderCommentModel.PROPERTY_FOR_PACKAGE_BOOL),
        "Pakking");
        checkBox.setName("CheckBoxPackage");
        return checkBox;
    }

    @Override
    public CheckObject checkDeleteObject(IComment object) {
        return null;
    }

    @Override
    public CheckObject checkSaveObject(ICommentModel object, PresentationModel presentationModel, WindowInterface window) {
        return null;
    }

    @Override
    public String getAddRemoveString() {
        return null;
    }

    @Override
    public String getClassName() {
        return "Comment";
    }

    @Override
    protected AbstractEditView<ICommentModel, IComment> getEditView(AbstractViewHandler<IComment, ICommentModel> handler, IComment object, boolean searching) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IComment getNewObject() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TableModel getTableModel(WindowInterface window) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTableWidth() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public String getTitle() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Dimension getWindowSize() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Boolean hasWriteAccess() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setColumnWidth(JXTable table) {
        // TODO Auto-generated method stub
        
    }

    /**
     * Håndterer ok-knapp
     * @author atle.brekka
     */
    class OkAction implements Closeable {

        /**
         * @see no.ugland.utransprod.gui.Closeable#canClose(java.lang.String,
         *      no.ugland.utransprod.gui.WindowInterface)
         */
        public boolean canClose(String actionString, WindowInterface window) {
            if (validationResultModel.hasErrors()) {
                Util.showErrorDialog((Component) null, "Rett feil",
                        "Rett alle feil før lagring!");
                return false;
            }
            ok = true;
            return true;
        }

    }

    
}
