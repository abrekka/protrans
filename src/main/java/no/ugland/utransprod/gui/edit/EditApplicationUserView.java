package no.ugland.utransprod.gui.edit;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.AbstractViewHandler;
import no.ugland.utransprod.gui.handlers.ApplicationUserViewHandler;
import no.ugland.utransprod.gui.model.AbstractModel;
import no.ugland.utransprod.gui.model.ApplicationUserModel;
import no.ugland.utransprod.model.ApplicationUser;
import no.ugland.utransprod.model.Packagetype;
import no.ugland.utransprod.model.validators.ApplicationUserValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;
import no.ugland.utransprod.util.Util;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.forms.builder.ButtonStackBuilder;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * Klasse som håndterer editering av brukere
 * 
 * @author atle.brekka
 */
public class EditApplicationUserView extends AbstractEditView<ApplicationUserModel, ApplicationUser> {
    private JTextField textFieldUserName;

    private JTextField textFieldFirstName;

    private JTextField textFieldLastName;

    private JComboBox comboBoxGroupUser;

    private JList listUserRoles;

    private JList listProductArea;

    private JPasswordField passwordField;

    private JButton buttonAddUserType;

    private JButton buttonAddProductAreaGroup;

    private JButton buttonRemoveUserType;

    private JButton buttonRemoveProductAreaGroup;

    private JComboBox comboBoxJobFunction;

    private JComboBox comboBoxProductArea;
    private JComboBox comboBoxPakketype;

    /**
     * @param searchDialog
     * @param object
     * @param aViewHandler
     */
    public EditApplicationUserView(final boolean searchDialog, final AbstractModel<ApplicationUser, ApplicationUserModel> object,
	    final AbstractViewHandler<ApplicationUser, ApplicationUserModel> aViewHandler) {
	super(searchDialog, object, aViewHandler);
    }

    @Override
    protected final JComponent buildEditPanel(final WindowInterface window) {
	FormLayout layout = new FormLayout("10dlu,p,3dlu,120dlu,3dlu,p,10dlu",
		"10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,100dlu,3dlu,p,5dlu");
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Brukernavn:", cc.xy(2, 2));
	builder.add(textFieldUserName, cc.xy(4, 2));
	builder.addLabel("Fornavn:", cc.xy(2, 4));
	builder.add(textFieldFirstName, cc.xy(4, 4));
	builder.addLabel("Etternavn:", cc.xy(2, 6));
	builder.add(textFieldLastName, cc.xy(4, 6));
	builder.addLabel("Passord:", cc.xy(2, 8));
	builder.add(passwordField, cc.xy(4, 8));
	builder.addLabel("Gruppebruker:", cc.xy(2, 10));
	builder.add(comboBoxGroupUser, cc.xy(4, 10));
	builder.addLabel("Funksjon:", cc.xy(2, 12));
	builder.add(comboBoxJobFunction, cc.xy(4, 12));
	builder.addLabel("Produktområde:", cc.xy(2, 14));
	builder.add(comboBoxProductArea, cc.xy(4, 14));
	builder.addLabel("Pakketype:", cc.xy(2, 16));
	builder.add(comboBoxPakketype, cc.xy(4, 16));

	JTabbedPane tabbedPane = new JTabbedPane();
	tabbedPane.add("Profiler", buildUserProfilePanel());
	tabbedPane.add("Produktområder", buildProductAreaPanel());
	builder.add(tabbedPane, cc.xyw(2, 18, 3));

	builder.add(ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel), cc.xyw(2, 20, 6));

	return new IconFeedbackPanel(validationResultModel, builder.getPanel());
    }

    /**
     * Bygger panel for produktområde
     * 
     * @return panel
     */
    private JPanel buildProductAreaPanel() {
	FormLayout layout = new FormLayout("60dlu,3dlu,p", "p");
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.add(new JScrollPane(listProductArea), cc.xy(1, 1));
	builder.add(buildProductAreaButtonPanel(), cc.xy(3, 1));

	return builder.getPanel();
    }

    /**
     * Lager panel for profiler
     * 
     * @return panel
     */
    private JPanel buildUserProfilePanel() {
	FormLayout layout = new FormLayout("60dlu,3dlu,p", "p");
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.add(new JScrollPane(listUserRoles), cc.xy(1, 1));
	builder.add(buildUserTypeButtonPanel(), cc.xy(3, 1));

	return builder.getPanel();
    }

    /**
     * Bygger knappepanel
     * 
     * @return panel
     */
    private JPanel buildUserTypeButtonPanel() {
	ButtonStackBuilder builder = new ButtonStackBuilder();
	builder.addGridded(buttonAddUserType);
	builder.addRelatedGap();
	builder.addGridded(buttonRemoveUserType);
	return builder.getPanel();
    }

    /**
     * Bygger knappepanel for produktområde
     * 
     * @return panel
     */
    private JPanel buildProductAreaButtonPanel() {
	ButtonStackBuilder builder = new ButtonStackBuilder();
	builder.addGridded(buttonAddProductAreaGroup);
	builder.addRelatedGap();
	builder.addGridded(buttonRemoveProductAreaGroup);
	return builder.getPanel();
    }

    /**
     * @param object
     * @return validator
     * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
     */
    @Override
    protected final Validator getValidator(final ApplicationUserModel object, boolean search) {
	return new ApplicationUserValidator(object);
    }

    /**
     * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
     */
    @Override
    protected final void initComponentAnnotations() {
	ValidationComponentUtils.setMandatory(textFieldFirstName, true);
	ValidationComponentUtils.setMessageKey(textFieldFirstName, "Bruker.fornavn");

	ValidationComponentUtils.setMandatory(textFieldUserName, true);
	ValidationComponentUtils.setMessageKey(textFieldUserName, "Bruker.brukernavn");

	ValidationComponentUtils.setMandatory(textFieldLastName, true);
	ValidationComponentUtils.setMessageKey(textFieldLastName, "Bruker.etternavn");

	ValidationComponentUtils.setMandatory(passwordField, true);
	ValidationComponentUtils.setMessageKey(passwordField, "Bruker.passord");

	ValidationComponentUtils.setMandatory(comboBoxGroupUser, true);
	ValidationComponentUtils.setMessageKey(comboBoxGroupUser, "Bruker.gruppebruker");

	ValidationComponentUtils.setMandatory(listUserRoles, true);
	ValidationComponentUtils.setMessageKey(listUserRoles, "Bruker.rolle");

	ValidationComponentUtils.setMandatory(comboBoxProductArea, true);
	ValidationComponentUtils.setMessageKey(comboBoxProductArea, "Bruker.produktområde");

    }

    @Override
    protected final void initEditComponents(final WindowInterface window1) {
	textFieldUserName = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_USER_NAME));
	textFieldFirstName = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_FIRST_NAME));
	textFieldLastName = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_LAST_NAME));
	comboBoxGroupUser = new JComboBox(new ComboBoxAdapter(Util.getYesNoList(),
		presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_GROUP_USER)));

	listUserRoles = BasicComponentFactory.createList(((ApplicationUserViewHandler) viewHandler).getUserRoleSelectionList(presentationModel));
	listProductArea = BasicComponentFactory.createList(((ApplicationUserViewHandler) viewHandler)
		.getUserProductAreaGroupSelectionList(presentationModel));
	passwordField = BasicComponentFactory.createPasswordField(presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_PASSWORD));

	buttonAddUserType = ((ApplicationUserViewHandler) viewHandler).getButtonAddUserType(window1, presentationModel);
	buttonRemoveUserType = ((ApplicationUserViewHandler) viewHandler).getButtonRemoveUserType(window1, presentationModel);

	buttonAddProductAreaGroup = ((ApplicationUserViewHandler) viewHandler).getButtonAddProductAreaGroup(window1, presentationModel);
	buttonRemoveProductAreaGroup = ((ApplicationUserViewHandler) viewHandler).getButtonRemoveProductAreaGroup(window1, presentationModel);

	comboBoxJobFunction = new JComboBox(new ComboBoxAdapter(((ApplicationUserViewHandler) viewHandler).getJobFunctionList(),
		presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_JOB_FUNCTION)));

	comboBoxProductArea = new JComboBox(new ComboBoxAdapter(((ApplicationUserViewHandler) viewHandler).getProductAreaList(),
		presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_PRODUCT_AREA)));
	comboBoxPakketype = new JComboBox(new ComboBoxAdapter(Packagetype.values(),
		presentationModel.getBufferedModel(ApplicationUserModel.PROPERTY_PACKAGE_TYPE)));

    }

    public final String getDialogName() {
	return "EditApplicationUserView";
    }

    public final String getHeading() {
	return "Bruker";
    }

}
