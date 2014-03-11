package no.ugland.utransprod.gui.edit;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import no.ugland.utransprod.ProTransException;
import no.ugland.utransprod.gui.OrderArticleView;
import no.ugland.utransprod.gui.OrderCostsView;
import no.ugland.utransprod.gui.WindowInterface;
import no.ugland.utransprod.gui.handlers.OrderViewHandler;
import no.ugland.utransprod.gui.model.OrderModel;
import no.ugland.utransprod.model.Customer;
import no.ugland.utransprod.model.Order;
import no.ugland.utransprod.model.ProductAreaGroup;
import no.ugland.utransprod.model.Project;
import no.ugland.utransprod.model.validators.OrderValidator;
import no.ugland.utransprod.util.IconFeedbackPanel;
import no.ugland.utransprod.util.Util;

import org.jdesktop.swingx.JXCollapsiblePane;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.adapter.ComboBoxAdapter;
import com.jgoodies.binding.beans.PropertyConnector;
import com.jgoodies.binding.value.BufferedValueModel;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.validation.Validator;
import com.jgoodies.validation.view.ValidationComponentUtils;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JYearChooser;

/**
 * Klassesom håndterer editeringsvindu for ordre
 * 
 * @author atle.brekka
 */
public class EditOrderView extends AbstractEditView<OrderModel, Order> {
    private JTextField textFieldCustomerNr;

    private JTextField textFieldCustomerFirstName;

    private JTextField textFieldCustomerLastName;

    private JTextField textFieldOrderNr;

    private JTextField textFieldAddress;

    private JTextField textFieldPostalCode;

    private JTextField textFieldPostoffice;

    private JCheckBox checkBoxAssembly;

    private JComboBox comboBoxConstructionType;

    private JComboBox comboBoxTransport;

    private OrderViewHandler orderViewHandler;

    private JComponent panelAssembly;

    private JDateChooser orderDate;

    private JCheckBox checkBoxCosts;

    private JXCollapsiblePane collapsiblePaneCost;

    private JDateChooser dateChooserInvoiced;

    private JCheckBox checkBoxLock;

    private JComboBox comboBoxAssemblyTeam;

    private JYearChooser yearChooser;

    private JComboBox comboBoxAssemblyWeek;

    private JDateChooser agreementDate;

    private JTextField textFieldTelephoneNr;

    private JComboBox comboBoxDeliveryWeek;

    private JDateChooser dateChooserPacklist;

    private JComboBox comboBoxProductArea;

    private JTextField textFieldSalesman;

    private JDateChooser dateChooserPaid;

    private JList listComments;

    private JButton buttonAddComment;

    private JTextField textFieldPackageDate;

    private JTextField textFieldPackedBy;

    private JTextField textFieldGavlDone;

    // private JTextField textFieldTakstolDone;

    private JTextField textFieldFrontDone;

    private JTextField textFieldVeggDone;

    private JTextField textFieldAssemblyDone;

    private JTextField textFieldTakstolPackaged;

    private JTextField textFieldRegistered;

    private JDateChooser productionDateChooser;

    private JTextField textFieldLoadingDate;

    private JTextField textFieldProjectNr;

    private JTextField textFieldProjectName;

    private JTextField textFieldCuttingFile;

    private JButton buttonImportCuttingFile;

    private JButton buttonOpenCuttingFile;

    private JTextField textFieldTelephoneNrSite;

    private JTextField textFieldMaxTrossHeight;

    public EditOrderView(final OrderViewHandler handler, final Order order, final boolean searchDialog, final Project project) {
	super(searchDialog, new OrderModel(order, searchDialog, true, true, project.getProjectNumber(), project.getProjectName()), handler);

	orderViewHandler = handler;
    }

    @Override
    protected final void initEditComponents(final WindowInterface window1) {
	orderViewHandler.checkAddresses(presentationModel, window1);
	comboBoxAssemblyTeam = new JComboBox(new ComboBoxAdapter(orderViewHandler.getSupplierList((ProductAreaGroup) presentationModel
		.getBufferedValue(OrderModel.PROPERTY_PRODUCT_AREA_GROUP)), presentationModel.getBufferedModel(OrderModel.PROPERTY_SUPPLIER)));
	comboBoxAssemblyTeam.setName("ComboBoxAssemblyTeam");
	orderViewHandler.addEditComponent(comboBoxAssemblyTeam);

	createAssemblyFields();

	checkBoxLock = orderViewHandler.getCheckBoxLock();
	BufferedValueModel bufferedValueModel = presentationModel.getBufferedModel(OrderModel.PROPERTY_CUSTOMER);

	createCustomerFields();
	createOrderFields(window1);
	checkBoxAssembly = BasicComponentFactory.createCheckBox(presentationModel.getBufferedModel(OrderModel.PROPERTY_DO_ASSEMBLY), "Montering");
	checkBoxAssembly.setName("Assembly");
	orderViewHandler.addEditComponent(checkBoxAssembly);

	comboBoxConstructionType = orderViewHandler.getComboBoxConstructionType(presentationModel);

	boolean onlyNewTransport = false;
	if (presentationModel.getBufferedValue(OrderModel.PROPERTY_ORDER_ID) == null && !search) {
	    onlyNewTransport = true;
	}
	comboBoxTransport = new JComboBox(new ComboBoxAdapter(orderViewHandler.getTransportList(onlyNewTransport),
		presentationModel.getBufferedModel(OrderModel.PROPERTY_TRANSPORT)));
	comboBoxTransport.setName("Transport");
	orderViewHandler.addEditComponent(comboBoxTransport);

	panelAssembly = buildAssemblyPanel();

	panelAssembly.setName("AssemblyPanel");
	panelAssembly.setVisible(false);

	orderDate = new JDateChooser();

	orderDate.setName("OrderDate");
	orderViewHandler.addEditComponent(orderDate);

	collapsiblePaneCost = new JXCollapsiblePane(new BorderLayout());
	Action toggleCostAction = collapsiblePaneCost.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION);

	checkBoxCosts = new JCheckBox(toggleCostAction);
	checkBoxCosts.setText("Kostnader");
	checkBoxCosts.setSelected(true);

	PropertyConnector connOrderDate = new PropertyConnector(orderDate, "date",
		presentationModel.getBufferedModel(OrderModel.PROPERTY_ORDER_DATE), "value");

	connOrderDate.updateProperty1();

	PropertyConnector connAssembly = new PropertyConnector(panelAssembly, "visible",
		presentationModel.getBufferedModel(OrderModel.PROPERTY_DO_ASSEMBLY), "value");
	connAssembly.updateProperty1();

	dateChooserInvoiced = new JDateChooser();
	orderViewHandler.addEditComponent(dateChooserInvoiced);

	PropertyConnector connInvoiceDate = new PropertyConnector(dateChooserInvoiced, "date",
		presentationModel.getBufferedModel(OrderModel.PROPERTY_INVOICE_DATE), "value");

	connInvoiceDate.updateProperty1();

	dateChooserPacklist = new JDateChooser();
	orderViewHandler.addEditComponent(dateChooserPacklist);

	PropertyConnector connPacklistDate = new PropertyConnector(dateChooserPacklist, "date",
		presentationModel.getBufferedModel(OrderModel.PROPERTY_PACKLIST_READY), "value");

	connPacklistDate.updateProperty1();

	agreementDate = new JDateChooser();
	agreementDate.setName("AgreementDate");
	if (!search) {
	    agreementDate.setEnabled(false);
	}
	PropertyConnector connAgreementDate = new PropertyConnector(agreementDate, "date",
		presentationModel.getBufferedModel(OrderModel.PROPERTY_AGREEMENT_DATE), "value");

	connAgreementDate.updateProperty1();

	textFieldTelephoneNr = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_TELEPHONE_NR), !search);
	textFieldTelephoneNr.setName("TelephoneNr");
	orderViewHandler.addEditComponent(textFieldTelephoneNr);

	comboBoxDeliveryWeek = new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
		presentationModel.getBufferedModel(OrderModel.PROPERTY_DELIVERY_WEEK)));
	comboBoxDeliveryWeek.setName("DeliveryWeek");
	orderViewHandler.addEditComponent(comboBoxDeliveryWeek);

	bufferedValueModel.addValueChangeListener(new CustomerSelectionHandler());

	comboBoxProductArea = orderViewHandler.getComboBoxProductArea(presentationModel);

	textFieldSalesman = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_SALESMAN));
	orderViewHandler.addEditComponent(textFieldSalesman);

	dateChooserPaid = new JDateChooser();
	dateChooserPaid.setName("DateChooserPaid");
	orderViewHandler.addEditComponent(dateChooserPaid);

	PropertyConnector connPaidDate = new PropertyConnector(dateChooserPaid, "date",
		presentationModel.getBufferedModel(OrderModel.PROPERTY_PAID_DATE), "value");

	connPaidDate.updateProperty1();

	listComments = orderViewHandler.getListComments(presentationModel);
	orderViewHandler.addEditComponent(listComments);
	buttonAddComment = orderViewHandler.getButtonAddComment(window1, presentationModel);
	orderViewHandler.addEditComponent(buttonAddComment);

	createStatusFields();
	productionDateChooser = orderViewHandler.getProductionDateChooser(presentationModel);

	textFieldProjectNr = orderViewHandler.getTextFieldProjectNr(presentationModel);
	textFieldProjectName = orderViewHandler.getTextFieldProjectName(presentationModel);

	textFieldCuttingFile = orderViewHandler.getTextFieldCuttingFile(presentationModel);

	buttonImportCuttingFile = orderViewHandler.getButtonImportCuttingFile(window1, presentationModel);

	buttonOpenCuttingFile = orderViewHandler.getButtonOpenCuttingFile(presentationModel, window1);

	textFieldTelephoneNrSite = orderViewHandler.getTextFieldTelephonenrSite(presentationModel);
	textFieldMaxTrossHeight = orderViewHandler.getTextFieldMaxTrossHeight(presentationModel);

	updateCustomerFieldsEnablement();
    }

    private void createOrderFields(final WindowInterface window1) {
	textFieldOrderNr = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_ORDER_NR), !search);

	textFieldOrderNr.setName("OrderNr");

	addOrderNrChangeListener(window1);

	orderViewHandler.addEditComponent(textFieldOrderNr);

	SwingUtilities.invokeLater(new Runnable() {
	    public void run() {
		textFieldOrderNr.requestFocus();
	    }
	});

	textFieldAddress = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_DELIVERY_ADDRESS), !search);
	textFieldAddress.setName("Address");
	orderViewHandler.addEditComponent(textFieldAddress);

	textFieldPostalCode = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_POSTAL_CODE), !search);
	textFieldPostalCode.setName("PostalCode");
	orderViewHandler.addEditComponent(textFieldPostalCode);

	textFieldPostoffice = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_POST_OFFICE), !search);
	textFieldPostoffice.setName("Postoffice");
	orderViewHandler.addEditComponent(textFieldPostoffice);
    }

    private void createCustomerFields() {
	textFieldCustomerNr = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_CUSTOMER_NR), !search);

	textFieldCustomerNr.setName("CustomerNr");
	orderViewHandler.addEditComponent(textFieldCustomerNr);

	textFieldCustomerFirstName = BasicComponentFactory.createTextField(
		presentationModel.getBufferedModel(OrderModel.PROPERTY_CUSTOMER_FIRST_NAME), !search);

	textFieldCustomerFirstName.setName("CustomerFirstName");
	orderViewHandler.addEditComponent(textFieldCustomerFirstName);

	textFieldCustomerLastName = BasicComponentFactory.createTextField(presentationModel.getBufferedModel(OrderModel.PROPERTY_CUSTOMER_LAST_NAME),
		!search);
	textFieldCustomerLastName.setName("CustomerLastName");
	orderViewHandler.addEditComponent(textFieldCustomerLastName);
    }

    private void createAssemblyFields() {
	ValueModel yearModel = presentationModel.getBufferedModel(OrderModel.PROPERTY_ASSEMBLY_YEAR);

	yearChooser = new JYearChooser();

	yearChooser.setName("AssemblyYear");
	orderViewHandler.addEditComponent(yearChooser);

	PropertyConnector conn = new PropertyConnector(yearChooser, "year", yearModel, "value");
	conn.updateProperty2();

	comboBoxAssemblyWeek = new JComboBox(new ComboBoxAdapter(Util.getWeeks(),
		presentationModel.getBufferedModel(OrderModel.PROPERTY_ASSEMBLY_WEEK)));
	comboBoxAssemblyWeek.setName("AssemblyWeek");
	orderViewHandler.addEditComponent(comboBoxAssemblyWeek);
    }

    private void createStatusFields() {
	textFieldPackageDate = orderViewHandler.getTextFieldPackageDate(presentationModel);
	textFieldPackedBy = orderViewHandler.getTextFieldPackedBy(presentationModel);
	textFieldGavlDone = orderViewHandler.getTextFieldGavlDone(presentationModel);
	// textFieldTakstolDone = orderViewHandler
	// .getTextFieldTakstolDone(presentationModel);
	textFieldFrontDone = orderViewHandler.getTextFieldFrontDone(presentationModel);
	textFieldVeggDone = orderViewHandler.getTextFieldVeggDone(presentationModel);
	textFieldAssemblyDone = orderViewHandler.getTextFieldAssemblyDone(presentationModel);
	textFieldTakstolPackaged = orderViewHandler.getTextFieldTakstolPackaged(presentationModel);
	textFieldRegistered = orderViewHandler.getTextFieldRegistered(presentationModel);
	textFieldLoadingDate = orderViewHandler.getTextFieldLoadingDate(presentationModel);
    }

    private void addOrderNrChangeListener(final WindowInterface window1) {
	// dersom ny ordre
	if (((OrderModel) presentationModel.getBean()).getOrderId() == null && !search) {
	    presentationModel.getBufferedModel(OrderModel.PROPERTY_ORDER_NR).addValueChangeListener(
		    ((OrderViewHandler) viewHandler).getOrderNrChangeListener(window1));
	}
    }

    /**
     * Bygger monteringspanel
     * 
     * @return panel
     */
    public final JComponent buildAssemblyPanel() {

	FormLayout layout = new FormLayout("min(p;95dlu),3dlu,30dlu,3dlu,p,3dlu,p,3dlu,p,3dlu,85dlu", "p");// ,3dlu,p,3dlu,p");
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();
	builder.addLabel("År:", cc.xy(1, 1));
	builder.add(yearChooser, cc.xy(3, 1));
	builder.addLabel("Uke:", cc.xy(5, 1));
	builder.add(comboBoxAssemblyWeek, cc.xy(7, 1));

	builder.addLabel("Monteringsteam:", cc.xy(9, 1));
	builder.add(comboBoxAssemblyTeam, cc.xy(11, 1));
	return builder.getPanel();
    }

    @Override
    protected final JComponent buildEditPanel(final WindowInterface window) {
	FormLayout layout;
	String tabbedPaneSize = "270dlu";
	String commentPaneSize = "80dlu:grow";
	if (search) {
	    tabbedPaneSize = "p";
	    commentPaneSize = "p";
	}
	layout = new FormLayout("10dlu,145dlu,3dlu,150dlu,3dlu," + tabbedPaneSize + ",10dlu", "10dlu,p,3dlu,fill:200dlu:grow,3dlu,15dlu,3dlu,"
		+ commentPaneSize + ",5dlu:grow,p:grow,5dlu");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
	CellConstraints cc = new CellConstraints();

	if (!search) {
	    builder.add(buildCustomerComboPanel(), cc.xy(2, 2));
	}
	builder.add(buildCustomerOrderPanel(), cc.xywh(2, 4, 3, 3));

	if (!search) {
	    builder.add(buildCommentsPanel(), cc.xywh(2, 8, 3, 1));
	    builder.add(buildTabbedPanel(window), cc.xy(6, 4));

	    builder.add(checkBoxCosts, cc.xy(6, 6));
	    builder.add(buildCostPanel(window), cc.xy(6, 8));
	}

	if (!search) {
	    builder.add(ButtonBarFactory.buildCenteredBar(buttonRefresh, buttonSave, buttonCancel), cc.xyw(2, 10, 5));
	} else {
	    builder.add(ButtonBarFactory.buildCenteredBar(buttonSave, buttonCancel), cc.xyw(2, 10, 5));
	    window.getRootPane().setDefaultButton(buttonSave);
	}

	if (!search) {
	    orderViewHandler.setComponentEnablement();
	}

	return new IconFeedbackPanel(validationResultModel, builder.getPanel());
    }

    public void resetBuffering() {
	presentationModel.triggerFlush();
    }

    /**
     * Bygger tabbedpane med avvik og artikler
     * 
     * @param window
     * @return panel
     */
    private JComponent buildTabbedPanel(final WindowInterface window) {
	JTabbedPane tabbedPane = new JTabbedPane();
	tabbedPane.setName("TabbedPaneDeviationArticle");
	tabbedPane.add("Avvik", buildDeviationPane(window));
	tabbedPane.add("Artikler", buildArticlePanel(window));
	tabbedPane.add("Produksjonsdetaljer", buildProductionPanel());
	tabbedPane.add("Filer", buildAttachmentPanel(window));
	return tabbedPane;
    }

    private JPanel buildAttachmentPanel(WindowInterface aWindow) {
	try {
	    return orderViewHandler.getAttachmentView(presentationModel).buildPanel(aWindow);
	} catch (ProTransException e) {
	    e.printStackTrace();
	    Util.showErrorDialog(aWindow, "Feil", e.getMessage());
	}
	return new JPanel();
    }

    /**
     * Bygger avvikspanel
     * 
     * @param window
     * @return panel
     */
    private JPanel buildDeviationPane(final WindowInterface window) {
	return orderViewHandler.getDeviationPane(window, presentationModel);

    }

    /**
     * Bygger panel med kundevalg
     * 
     * @return panel
     */
    private JPanel buildCustomerComboPanel() {
	FormLayout layout = new FormLayout("p,3dlu,100dlu", "p");
	PanelBuilder builder = new PanelBuilder(layout);

	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
	CellConstraints cc = new CellConstraints();

	builder.add(checkBoxLock, cc.xy(3, 1));

	return builder.getPanel();
    }

    /**
     * Lager panel for kommentarer
     * 
     * @return panel
     */
    private JPanel buildCommentsPanel() {
	FormLayout layout = new FormLayout("200dlu,3dlu,p", "p,3dlu,top:50dlu");
	PanelBuilder builder = new PanelBuilder(layout);
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Kommentarer:", cc.xy(1, 1));
	builder.add(new JScrollPane(listComments), cc.xy(1, 3));

	builder.add(buttonAddComment, cc.xy(3, 3));

	return builder.getPanel();
    }

    private JPanel buildCustomerOrderPanel() {
	FormLayout layout = new FormLayout("p,3dlu,80dlu,3dlu,p,3dlu,90dlu",
		"p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p");

	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(new FormDebugPanel(),layout);
	CellConstraints cc = new CellConstraints();
	builder.addLabel("Ordernr:", cc.xy(1, 1));
	builder.add(textFieldOrderNr, cc.xy(3, 1));
	builder.addLabel("Kundenr:", cc.xy(1, 3));
	builder.add(textFieldCustomerNr, cc.xy(3, 3));
	builder.addLabel("Fornavn:", cc.xy(1, 5));
	builder.add(textFieldCustomerFirstName, cc.xy(3, 5));
	builder.addLabel("Etternavn", cc.xy(1, 7));
	builder.add(textFieldCustomerLastName, cc.xy(3, 7));
	builder.addLabel("Telefon", cc.xy(1, 9));
	builder.add(textFieldTelephoneNr, cc.xy(3, 9));

	builder.addLabel("Tlf byggeplass", cc.xy(1, 11));
	builder.add(textFieldTelephoneNrSite, cc.xy(3, 11));

	builder.addLabel("Leveringsadresse:", cc.xy(1, 13));
	builder.add(textFieldAddress, cc.xy(3, 13));
	builder.addLabel("Postnummer:", cc.xy(1, 15));
	builder.add(textFieldPostalCode, cc.xy(3, 15));
	builder.addLabel("Poststed:", cc.xy(1, 17));
	builder.add(textFieldPostoffice, cc.xy(3, 17));
	builder.addLabel("Selger:", cc.xy(1, 19));
	builder.add(textFieldSalesman, cc.xy(3, 19));
	builder.addLabel("Produktområde:", cc.xy(1, 21));
	builder.add(comboBoxProductArea, cc.xy(3, 21));

	builder.addLabel("Ordedato:", cc.xy(5, 1));
	builder.add(orderDate, cc.xy(7, 1));
	builder.addLabel("Avropsdato:", cc.xy(5, 3));
	builder.add(agreementDate, cc.xy(7, 3));
	builder.addLabel("Ønsket uke:", cc.xy(5, 5));
	builder.add(comboBoxDeliveryWeek, cc.xy(7, 5));
	builder.addLabel("Konstruksjonstype:", cc.xy(5, 7));
	builder.add(comboBoxConstructionType, cc.xy(7, 7));
	builder.addLabel("Transport:", cc.xy(5, 9));
	builder.add(comboBoxTransport, cc.xy(7, 9));
	builder.addLabel("Fakturert:", cc.xy(5, 11));
	builder.add(dateChooserInvoiced, cc.xy(7, 11));
	builder.addLabel("Pakkliste:", cc.xy(5, 13));
	builder.add(dateChooserPacklist, cc.xy(7, 13));
	builder.addLabel("Betalt:", cc.xy(5, 15));
	builder.add(dateChooserPaid, cc.xy(7, 15));
	builder.addLabel("Produksjonsdato:", cc.xy(5, 17));
	builder.add(productionDateChooser, cc.xy(7, 17));
	if (!search) {
	    builder.addLabel("Registrert:", cc.xy(5, 19));
	    builder.add(textFieldRegistered, cc.xy(7, 19));
	    builder.addLabel("Makshøyde takstol:", cc.xy(5, 21));
	    builder.add(textFieldMaxTrossHeight, cc.xy(7, 21));
	    builder.addLabel("Prosjektnr:", cc.xy(1, 23));
	    builder.add(textFieldProjectNr, cc.xy(3, 23));
	    builder.addLabel("Prosjektnavn:", cc.xy(5, 23));
	    builder.add(textFieldProjectName, cc.xy(7, 23));
	}
	builder.add(checkBoxAssembly, cc.xy(1, 25));
	builder.add(panelAssembly, cc.xyw(3, 25, 5));

	return builder.getPanel();
    }

    /**
     * Bygger panel for kostnader
     * 
     * @param window
     * @return panel
     */
    private JPanel buildCostPanel(final WindowInterface window) {
	OrderCostsView orderCostsView = new OrderCostsView(orderViewHandler.getOrderCostsViewHandler(presentationModel), true);
	try {
	    collapsiblePaneCost.add(orderCostsView.buildPanel(window), BorderLayout.CENTER);
	} catch (ProTransException e) {
	    Util.showErrorDialog(window, "Feil", e.getMessage());
	    e.printStackTrace();
	}
	collapsiblePaneCost.setName("PanelOrderCosts");
	return collapsiblePaneCost;
    }

    /**
     * Lager panel med produksjonsdetaljer
     * 
     * @return panel
     */
    private JPanel buildProductionPanel() {
	FormLayout layout = new FormLayout("p,3dlu,50dlu,3dlu,p,3dlu,80dlu", "10dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p,3dlu,p");
	PanelBuilder builder = new PanelBuilder(layout);
	// PanelBuilder builder = new PanelBuilder(layout,new FormDebugPanel());
	CellConstraints cc = new CellConstraints();

	builder.addLabel("Gavl ferdig:", cc.xy(1, 2));
	builder.add(textFieldGavlDone, cc.xy(3, 2));
	builder.addLabel("Takstol pakket:", cc.xy(1, 4));
	// builder.add(textFieldTakstolDone, cc.xy(3, 4));
	// builder.addLabel("pakket:", cc.xy(5, 4));
	builder.add(textFieldTakstolPackaged, cc.xy(3, 4));
	builder.addLabel("Front ferdig:", cc.xy(1, 6));
	builder.add(textFieldFrontDone, cc.xy(3, 6));
	builder.addLabel("Vegg ferdig:", cc.xy(1, 8));
	builder.add(textFieldVeggDone, cc.xy(3, 8));
	builder.addLabel("Pakket:", cc.xy(1, 10));
	builder.add(textFieldPackageDate, cc.xy(3, 10));
	builder.addLabel("av", cc.xy(5, 10));
	builder.add(textFieldPackedBy, cc.xy(7, 10));
	builder.addLabel("Opplastdato:", cc.xy(1, 12));
	builder.add(textFieldLoadingDate, cc.xy(3, 12));
	builder.addLabel("Kappfil:", cc.xy(1, 14));
	builder.add(textFieldCuttingFile, cc.xy(3, 14));

	if ((Boolean) presentationModel.getBufferedValue(OrderModel.PROPERTY_DO_ASSEMBLY)) {
	    builder.addLabel("Montert:", cc.xy(1, 16));
	    builder.add(textFieldAssemblyDone, cc.xy(3, 16));
	}
	builder.add(ButtonBarFactory.buildCenteredBar(buttonImportCuttingFile, buttonOpenCuttingFile), cc.xyw(1, 18, 7));

	return builder.getPanel();
    }

    /**
     * Bygger panel for visning av ordrelinjer
     * 
     * @param window
     * @return panel
     */
    private JPanel buildArticlePanel(final WindowInterface window) {
	OrderArticleView<Order, OrderModel> orderArticleView = new OrderArticleView<Order, OrderModel>(
		orderViewHandler.getOrderArticleViewHandler(presentationModel), false, true);
	return orderArticleView.buildPanel(window);
    }

    /**
     * @param object
     * @return validator
     * @see no.ugland.utransprod.gui.edit.AbstractEditView#getValidator(java.lang.Object)
     */
    @Override
    protected final Validator getValidator(final OrderModel object, final boolean search) {
	return new OrderValidator(object, search);
    }

    /**
     * @see no.ugland.utransprod.gui.edit.AbstractEditView#initComponentAnnotations()
     */
    @Override
    protected final void initComponentAnnotations() {
	ValidationComponentUtils.setMandatory(textFieldCustomerNr, true);
	ValidationComponentUtils.setMessageKey(textFieldCustomerNr, "Ordre.kunde");

	ValidationComponentUtils.setMandatory(textFieldOrderNr, true);
	ValidationComponentUtils.setMessageKey(textFieldOrderNr, "Ordre.ordrenummer");

	ValidationComponentUtils.setMandatory(textFieldAddress, true);
	ValidationComponentUtils.setMessageKey(textFieldAddress, "Ordre.adresse");

	ValidationComponentUtils.setMandatory(textFieldPostalCode, true);
	ValidationComponentUtils.setMessageKey(textFieldPostalCode, "Ordre.postnummer");

	ValidationComponentUtils.setMandatory(textFieldPostoffice, true);
	ValidationComponentUtils.setMessageKey(textFieldPostoffice, "Ordre.poststed");

	ValidationComponentUtils.setMandatory(comboBoxConstructionType, true);
	ValidationComponentUtils.setMessageKey(comboBoxConstructionType, "Ordre.garasjetype");

	ValidationComponentUtils.setMandatory(orderDate, true);
	ValidationComponentUtils.setMessageKey(orderDate, "Ordre.ordredato");

	ValidationComponentUtils.setMandatory(comboBoxAssemblyWeek, true);
	ValidationComponentUtils.setMessageKey(comboBoxAssemblyWeek, "Ordre.monteringsuke");

	ValidationComponentUtils.setMandatory(comboBoxAssemblyTeam, true);
	ValidationComponentUtils.setMessageKey(comboBoxAssemblyTeam, "Ordre.monteringsteam");

	ValidationComponentUtils.setMandatory(comboBoxProductArea, true);
	ValidationComponentUtils.setMessageKey(comboBoxProductArea, "Ordre.produktområde");

    }

    /**
     * Oppdaterer enable/disable av felter for kunde
     */
    final void updateCustomerFieldsEnablement() {
	boolean enabled = false;
	Customer customer = (Customer) presentationModel.getBufferedValue(OrderModel.PROPERTY_CUSTOMER);
	if (customer == null) {
	    enabled = true;
	} else {
	    textFieldCustomerNr.setText(String.valueOf(customer.getCustomerNr()));
	    textFieldCustomerFirstName.setText(customer.getFirstName());
	    textFieldCustomerLastName.setText(customer.getLastName());
	}
	textFieldCustomerNr.setEnabled(enabled);
	textFieldCustomerFirstName.setEnabled(enabled);
	textFieldCustomerLastName.setEnabled(enabled);
    }

    /**
     * Klassesom håndterer valg av kunde
     * 
     * @author atle.brekka
     */
    class CustomerSelectionHandler implements PropertyChangeListener {

	/**
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.PropertyChangeEvent)
	 */
	public void propertyChange(final PropertyChangeEvent evt) {
	    updateCustomerFieldsEnablement();

	}

    }

    /**
     * Henter ut gjeldende ordremodell
     * 
     * @return ordremodell
     */
    public final OrderModel getOrderModel() {
	return (OrderModel) presentationModel.getBean();
    }

    public final String getDialogName() {
	return "EditOrderView";
    }

    public final String getHeading() {
	return "Ordre";
    }
}
